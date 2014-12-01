/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Calendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionItemInfo;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.RemoteSession;

/**
 *
 * @author ito test for : SR4	Maintain the state of all clients. SR5	Accept
 * multiple clients.
 */
public class TestClientBidder extends Client {

    public TestClientBidder(AuctionServer server, String username, int timeout) throws RemoteException {
        super(server, username, timeout);
    }

    @Override
    public void initialize() throws RemoteException {
        session = server.login(username, this);
    }

    @Override
    protected void run_stup() {

        try {
            Set<UID> availableAuctionItems = session.getAvailableAuctionItems();

            AuctionItemInfo info = session.getAuctionItemInfoProvider();

            for (UID uid : availableAuctionItems) {
                double value = info.getMinimumValue(uid);

                if (value < info.getCurrentBid(uid)) {
                    value = info.getCurrentBid(uid) + 1;
                }

                if (!info.getCurrentWinner(uid).equals(username)) {
                    session.bid(uid, value);
                }
            }

            Thread.sleep(500);

        } catch (RemoteException ex) {
            error(1, username + " " + ex.toString());
        } catch (InterruptedException ex) {
            error(1, "Sleep error (...)");
        }

    }

    @Override
    public boolean isAlive() throws RemoteException {
        return !leave; //To change body of generated methods, choose Tools | Templates.
    }

}
