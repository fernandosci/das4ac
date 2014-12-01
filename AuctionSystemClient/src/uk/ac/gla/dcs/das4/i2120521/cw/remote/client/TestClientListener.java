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
public class TestClientListener extends Client {

    public TestClientListener(AuctionServer server, String username, int timeout) throws RemoteException {
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

            for (UID id : availableAuctionItems) {
                AuctionItemInfo item = session.getAuctionItemInfoProvider();

                StringBuilder b = new StringBuilder();
                String space = "         ";
                b.append("ID-> ");
                b.append(id.toString());
                b.append(space);
                b.append(space);
                b.append("OWNER-> ");
                b.append(item.getOwner(id));
                b.append(space);
                b.append("AUCTIONAME-> ");
                b.append(item.getName(id));
                b.append(space);
                b.append("MINIMUMVALUE-> ");
                b.append(item.getMinimumValue(id));
                b.append(space);
                b.append("OPENING DATE-> ");
                b.append(item.getOpeningDate(id));
                b.append(space);
                b.append("CLOSING DATE-> ");
                b.append(item.getClosingDate(id));
                b.append(space);
                b.append("CURRENT WINNER-> ");
                b.append(item.getCurrentWinner(id));
                b.append(space);
                b.append("CURRENT BID-> ");
                b.append(item.getCurrentBid(id));

                Log.LogMessage(this.getClass(), "AVAILABLE ITEMS:\n " + b.toString());
            }
            Thread.sleep(1000);
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
