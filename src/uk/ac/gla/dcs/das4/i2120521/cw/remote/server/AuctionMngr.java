/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import uk.ac.gla.dcs.das4.i2120521.cw.core.GlobalParameters;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionItem;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.BidResult;

/**
 *
 * @author ito
 */
public class AuctionMngr {

    private final ConcurrentHashMap<UID, AuctionItem> activeAuctionItems;
    private final ConcurrentHashMap<UID, AuctionItem> legacyAuctionItems;
    private final UserSessionProvider usProvider;

    public AuctionMngr(UserSessionProvider usProvider) {

        activeAuctionItems = new ConcurrentHashMap<>();
        legacyAuctionItems = new ConcurrentHashMap<>();
        this.usProvider = usProvider;
    }

    public List<AuctionItem> getAvailableAuctionItems() {

        ArrayList<AuctionItem> list = new ArrayList<>();

        list.addAll(activeAuctionItems.values());

        return Collections.unmodifiableList(list);
    }

    public AuctionItem newAuction(String username, String name, double minimumValue, Date closingDate) throws RemoteException {

        AuctionItemImpl item = new AuctionItemImpl(username, name, minimumValue, closingDate);

        new Timer(true).schedule(new TaskerClose(item), closingDate);

        activeAuctionItems.put(item.getId(), item);

        return item;
    }

    class TaskerClose extends TimerTask {

        private AuctionItemImpl i;

        public TaskerClose(AuctionItemImpl item) {
            this.i = item;
        }

        @Override
        public void run() {
            if (activeAuctionItems.containsKey(i.getId())) {

                activeAuctionItems.remove(i.getId());

                BidResult bidResult = i.getBidMngr().close();

                legacyAuctionItems.put(i.getId(), i);
                configureNextTimer();
                notifyBidders(bidResult);
            }
        }

        protected void configureNextTimer() {
            Calendar cal = Calendar.getInstance();
            cal.add(GlobalParameters.purgeUnit, GlobalParameters.purgeValue);
            new Timer(true).schedule(new TaskerPurge(i), cal.getTime());
        }

        private void notifyBidders(BidResult bidResults) {
            //TODO
            Set<String> bidders = bidResults.getBidders();

            RemoteSessionImpl userSession = usProvider.getUserSession(bidResults.getOwner());
            if (userSession != null) {
                userSession.getUserListener().auctionOverNotification(AuctionOverNotificationEvent.owner(bidResults.getWinner(), i));
            }

            if (bidResults.isPriceMet()) {
                bidders.remove(bidResults.getWinner());
                userSession = usProvider.getUserSession(bidResults.getWinner());
                if (userSession != null) {
                    userSession.getUserListener().auctionOverNotification(AuctionOverNotificationEvent.winner(bidResults.getWinner(), i));
                }
            }

            for (String b : bidders) {
                userSession = usProvider.getUserSession(b);
                if (userSession != null) {
                    userSession.getUserListener().auctionOverNotification(AuctionOverNotificationEvent.notWinner(bidResults.getWinner(), i));
                }
            }
        }
    }

    class TaskerPurge extends TimerTask {

        private AuctionItemImpl i;

        public TaskerPurge(AuctionItemImpl i) {
            this.i = i;
        }

        @Override
        public void run() {
            legacyAuctionItems.remove(i.getId());

            i.purge();
        }

    }

}
