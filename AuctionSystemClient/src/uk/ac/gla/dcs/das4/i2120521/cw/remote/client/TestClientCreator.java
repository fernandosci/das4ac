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
public class TestClientCreator extends Client {

    boolean torun = true;

    int duration = 10;
    int nbids = 10;

    public TestClientCreator(AuctionServer server, String username, int timeout, int nbids, int duration) throws RemoteException {
        super(server, username, timeout);
        this.duration = duration;
        this.nbids = nbids;
    }

    @Override
    public void initialize() throws RemoteException {
        session = server.login(username, this);
    }

    @Override
    protected void run_stup() {

        if (torun) {
            for (int c = 0; c < nbids; c++) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, duration);
                try {
                    UID newAuction = session.newAuction("auction " + c + " from " + username + c, 1, cal.getTime());

                    Log.LogMessage(this.getClass(), "New auction ID: " + newAuction.toString());

                } catch (RemoteException ex) {
                    error(1, "Bid error: " + ex.toString());
                }
            }
            torun = false;
        } else {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return !leave; //To change body of generated methods, choose Tools | Templates.
    }

}
