/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.RemoteSession;

/**
 *
 * @author ito test for : SR4	Maintain the state of all clients. SR5	Accept
 * multiple clients.
 */
public class TestClientLogin extends Client {

    public TestClientLogin(AuctionServer server, String username, int timeout) throws RemoteException {
        super(server, username, timeout);
    }

    @Override
    public void initialize() throws RemoteException {
        session = null;

    }

    @Override
    protected void run_stup() {

        int opt = r.nextInt(500);

        RemoteSession loggedoff = null;

        //test regular login, no exception should be expected
        if (session == null) {
            Log.LogMessage(this.getClass(), "Logging in with username: " + username);
            try {
                session = server.login(username, this);
            } catch (RemoteException remoteException) {
                Log.LogMessage(this.getClass(), "Login failed: " + remoteException.toString());
                error(1, "not expected exception");
            }
            if (session != null) {
                Log.LogMessage(this.getClass(), "Logged as: " + username);
            }
        } else {
            try {
                Log.LogMessage(this.getClass(), "Logging of with username: " + username);
                session.logoff();
                loggedoff = session;
                session = null;
            } catch (RemoteException remoteException) {
                Log.LogMessage(this.getClass(), "log off failed: " + remoteException.toString());
                error(1, "not expected exception");
            }
        }

        double prop = r.nextDouble();

        //test wrong operations, exceptions should be expected
        if (prop > 0.8) {

            if (session != null) {
                Log.LogMessage(this.getClass(), "Expecting an exception login: " + username);
                try {
                    RemoteSession login = server.login(username, this);
                    Log.LogMessage(this.getClass(), "SHOULD NEVER BE SEEN: ");
                    error(2, "expected exception");
                    login.logoff();
                } catch (RemoteException remoteException) {
                    Log.LogMessage(this.getClass(), "(EXPECTED) Login failed: " + remoteException.toString());
                }
            } else {
                if (loggedoff != null) {
                    Log.LogMessage(this.getClass(), "Expecting an exception logoff: " + username);
                    try {
                        loggedoff.logoff();
                        Log.LogMessage(this.getClass(), "SHOULD NEVER BE SEEN: ");
                        error(2, "expected exception");
                    } catch (RemoteException remoteException) {
                        Log.LogMessage(this.getClass(), "(EXPECTED) Logoff failed: " + remoteException.toString());
                    }
                }
            }
        }

        try {
            Thread.sleep(opt);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestClientLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean isAlive() throws RemoteException {
        return !leave; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void auctionOverNotification(AuctionOverNotificationEvent event) {

    }

}
