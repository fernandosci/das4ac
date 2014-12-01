/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.BidError;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;

/**
 *
 * @author ito
 */
public class AuctionMngr {

    private final ConcurrentHashMap<UID, AuctionItem> allAuctionItems;
    private final ConcurrentHashMap<UID, AuctionItem> activeAuctionItems;
    private final ConcurrentHashMap<UID, AuctionItem> legacyAuctionItems;
    private final UserSessionProvider usProvider;

    public AuctionMngr(UserSessionProvider usProvider) {

        activeAuctionItems = new ConcurrentHashMap<UID, AuctionItem>();
        legacyAuctionItems = new ConcurrentHashMap<UID, AuctionItem>();
        allAuctionItems = new ConcurrentHashMap<UID, AuctionItem>();
        this.usProvider = usProvider;
    }

    public AuctionItem getAuctionItem(UID id) {
        return allAuctionItems.get(id);
    }

    public Set<UID> getAvailableAuctionItems() {

        Set<UID> set = new HashSet<UID>();

        set.addAll(activeAuctionItems.keySet());

        return set;
    }

    public Set<UID> getLegacyAuctionItems() {

        Set<UID> set = new HashSet<UID>();

        set.addAll(legacyAuctionItems.keySet());

        return set;
    }

    public Set<UID> getAllAuctionItems() {
        Set<UID> set = new HashSet<UID>();

        set.addAll(allAuctionItems.keySet());

        return set;
    }

    public UID newAuction(String username, String name, double minimumValue, Date closingDate){

        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        if (time.after(closingDate)) {
            return null;
        }

        AuctionItem item = new AuctionItem(username, name, minimumValue, closingDate);
        Log.LogMessage(this.getClass(), String.format("New Auction::: USERNAME->%s\tNAME->%s\tMVALUE->%f\tClosingDate->%s ", username, name, minimumValue, closingDate.toString()));

        handleNewItem(item);

        return item.getId();
    }

    private void handleNewItem(AuctionItem item) {

        if (!allAuctionItems.containsKey(item.getId())) {
            allAuctionItems.put(item.getId(), item);
            activeAuctionItems.put(item.getId(), item);

            new Timer(true).schedule(new TaskerClose(item.getId()), item.getClosingDate());
        } else {
            System.err.println("Dublicated Auction Item found. KEY: " + item.getId().toString());
        }

    }

    public BidError bid(String username, UID auctionItemId, double value) {

        AuctionItem auc = activeAuctionItems.get(auctionItemId);

        if (auc != null) {
            BidError biderr = auc.getBidMngr().bid(username, value);
            return biderr;
        } else {
            AuctionItem auc1 = allAuctionItems.get(auctionItemId);
            if (auc1 == null) {
                return BidError.NOTFOUND;
            } else {
                return BidError.CLOSED;
            }
        }
    }

    public void exportAuctions(String filename) throws IOException, Exception {
        Collection<AuctionItem> values = allAuctionItems.values();

        if (values.isEmpty()) {
            throw new Exception("No auctions!");
        }

        FileOutputStream f = new FileOutputStream(filename);
        ObjectOutput s = new ObjectOutputStream(f);

        s.writeInt(values.size());

        Calendar inst = Calendar.getInstance();
        Date timenow = inst.getTime();
        s.writeObject(timenow);

        for (AuctionItem i : values) {
            s.writeObject(i);
        }

        s.flush();
        s.close();
        f.close();

    }

    public void importAuctions(String filename) throws FileNotFoundException, IOException, ClassNotFoundException, Exception {

        FileInputStream in = new FileInputStream(filename);
        ObjectInputStream s = new ObjectInputStream(in);

        Calendar inst = Calendar.getInstance();
        Date timenow = inst.getTime();

        int nauc = s.readInt();

        Date past = (Date) s.readObject();
        long msDiff = timenow.getTime() - past.getTime();

        if (msDiff < 0) {
            s.close();
            in.close();
            throw new Exception("File created in the future!");
        }

        List<AuctionItem> l = new ArrayList<AuctionItem>();

        for (int c = 0; c < nauc; c++) {
            AuctionItem i = (AuctionItem) s.readObject();

            Date openingDate = i.getOpeningDate();
            openingDate.setTime(openingDate.getTime() + msDiff);
            i.setOpeningDate(openingDate);
            Date closingDate = i.getClosingDate();
            closingDate.setTime(closingDate.getTime() + msDiff);
            i.setClosingDate(closingDate);
            Date deleteDate = i.getDeleteDate();
            deleteDate.setTime(deleteDate.getTime() + msDiff);
            i.setDeleteDate(deleteDate);

            Log.LogMessage(this.getClass(), String.format("New Auction Imported::: USERNAME->%s\tNAME->%s\tMVALUE->%f\tClosingDate->%s ", i.getOwner(), i.getName(), i.getMinimumValue(), i.getClosingDate().toString()));
            l.add(i);
        }
        for (AuctionItem i : l) {
            handleNewItem(i);
        }

        s.close();
        in.close();

    }

    class TaskerClose extends TimerTask {

        private final UID id;

        public TaskerClose(UID id) {
            this.id = id;
        }

        @Override
        public void run() {
            if (activeAuctionItems.containsKey(id)) {

                AuctionItem item = activeAuctionItems.remove(id);

                BidResult bidResult = item.getBidMngr().close();

                legacyAuctionItems.put(id, item);

                try {
                    notifyBidders(bidResult);
                } catch (Exception ex) {
                    System.err.println("notifyBidders exception: " + ex);
                }

                configureNextTimer();

            } else {
                System.err.println("TaskerClose: " + id.toString() + " not found!. Should never reach here!");
            }
        }

        protected void configureNextTimer() {

            AuctionItem i = allAuctionItems.get(id);

            new Timer(true).schedule(new TaskerPurge(id), i.getDeleteDate());
        }

        private void notifyBidders(BidResult bidResults) throws Exception {

            Set<String> bidders = bidResults.getBidders();

            RemoteSessionImpl userSession = usProvider.getUserSession(bidResults.getOwner());
            if (userSession != null) {
                userSession.getUserListener().auctionOverNotification(AuctionOverNotificationEvent.owner(bidResults.getWinner(), bidResults.getValue(), id));
            } else {
                System.err.println("notifyBidders: Could not find Owner -> " + bidResults.getOwner());
            }

            if (bidResults.isPriceMet()) {
                bidders.remove(bidResults.getWinner());
                userSession = usProvider.getUserSession(bidResults.getWinner());
                if (userSession != null) {
                    userSession.getUserListener().auctionOverNotification(AuctionOverNotificationEvent.winner(bidResults.getWinner(), bidResults.getValue(), id));
                } else {
                    System.err.println("notifyBidders: Could not find winner -> " + bidResults.getWinner());
                }
            }

            for (String b : bidders) {
                userSession = usProvider.getUserSession(b);
                if (userSession != null) {
                    userSession.getUserListener().auctionOverNotification(AuctionOverNotificationEvent.notWinner(bidResults.getWinner(), bidResults.getValue(), id));
                } else {
                    System.err.println("notifyBidders: Could not find bidder -> " + b);
                }
            }
        }
    }

    class TaskerPurge extends TimerTask {

        private final UID id;

        public TaskerPurge(UID id) {
            this.id = id;
        }

        @Override
        public void run() {
            AuctionItem item1 = legacyAuctionItems.remove(id);

            AuctionItem item2 = allAuctionItems.remove(id);

            if (item1 == null) {
                System.err.println("TaskerPurge: Could not find legacy item -> " + id + ". Should never reach HERE!");
            }

            if (item2 == null) {
                System.err.println("TaskerPurge: Could not find item -> " + id + ". Should never reach HERE!");
            } else {
                item2.purge();
            }
        }

    }

}
