/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.GlobalParameters;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.BidError;

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

        activeAuctionItems = new ConcurrentHashMap<>();
        legacyAuctionItems = new ConcurrentHashMap<>();
        allAuctionItems = new ConcurrentHashMap<>();
        this.usProvider = usProvider;
    }

    public AuctionItem getAuctionItem(UID id) {
        return allAuctionItems.get(id);
    }

    public Set<UID> getAvailableAuctionItems() {

        Set<UID> set = new HashSet<>();

        set.addAll(activeAuctionItems.keySet());

        return set;
    }

    public Set<UID> getLegacyAuctionItems() {

        Set<UID> set = new HashSet<>();

        set.addAll(legacyAuctionItems.keySet());

        return set;
    }

    public Set<UID> getAllAuctionItems() {
        Set<UID> set = new HashSet<>();

        set.addAll(allAuctionItems.keySet());

        return set;
    }

    public UID newAuction(String username, String name, double minimumValue, Date closingDate) throws RemoteException {

        if (Calendar.getInstance().after(closingDate)) {
            throw new RemoteException("newAuction: Invalid Closing Date");
        }

        AuctionItem item = new AuctionItem(username, name, minimumValue, closingDate);

        allAuctionItems.put(item.getId(), item);
        activeAuctionItems.put(item.getId(), item);

        new Timer(true).schedule(new TaskerClose(item.getId()), closingDate);

        return item.getId();
    }

    public BidError bid(String username, UID auctionItemId, double value) throws RemoteException {

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
            Calendar cal = Calendar.getInstance();
            cal.add(GlobalParameters.purgeUnit, GlobalParameters.purgeValue);
            new Timer(true).schedule(new TaskerPurge(id), cal.getTime());
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
