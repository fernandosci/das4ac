/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.ClientListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.GlobalParameters;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.RemoteSession;

/**
 *
 * @author ito
 */
public class AuctionServerImpl extends UnicastRemoteObject implements AuctionServer, UserSessionProvider {

    private final HashMap<String, RemoteSessionImpl> usersessions;

    private AuctionMngr aucMngr;

    private AuctionItemInfoImpl aucItemInfo;

    private boolean initialized;

    public AuctionServerImpl() throws RemoteException {
        super();

        usersessions = new HashMap<>();
        initialized = false;
    }

    public void initialize() throws RemoteException {
        if (!initialized) {
            initialized = true;
            aucMngr = new AuctionMngr(this);
            aucItemInfo = new AuctionItemInfoImpl(aucMngr);

            Timer t = new Timer(true);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 3);
            t.schedule(new WorkerRefreshSessions(), cal.getTime(), GlobalParameters.sessionCleanoutFreq);
        }
    }

    protected AuctionMngr getAuctionMngr() {
        return aucMngr;
    }

    @Override
    public RemoteSession login(String username, ClientListener listener) throws RemoteException {

        if (!initialized) {
            throw new RemoteException("SERVERN NOT INITIALIZED");
        }

        synchronized (usersessions) {
            if (!usersessions.containsKey(username)) {

                RemoteSessionImpl session = new RemoteSessionImpl(username, aucMngr, aucItemInfo, listener);

                System.out.println();

                Log.LogMessage(this.getClass(), "New client Connected: " + username);

                usersessions.put(username, session);

                return session;

            } else {
                throw new RemoteException("username already in use.");
            }
        }

    }

    @Override
    public RemoteSessionImpl getUserSession(String username) throws Exception {
        if (!initialized) {
            throw new Exception("SERVER NOT INITIALIZED");
        }
        synchronized (usersessions) {

            RemoteSessionImpl rsess = usersessions.get(username);

            if (rsess == null || !rsess.isLoggedOff()) {
                return rsess;
            } else {
                usersessions.remove(username);
                Log.LogMessage(this.getClass(), "Cleanning dead session");
            }
            return null;
        }
    }

    class WorkerRefreshSessions extends TimerTask {

        @Override
        public void run() {
            Collection<RemoteSessionImpl> values;
            synchronized (usersessions) {
                values = usersessions.values();
            }

            Set<String> toRemove = new HashSet<>();

            for (RemoteSessionImpl rs : values) {

                if (!rs.isLoggedOff()) {
                    try {
                        if (!rs.getUserListener().isAlive()) {
                            rs.incFailedAlive();
                        } else {
                            rs.resetFailedAlive();
                        }
                    } catch (RemoteException ex) {
                        rs.incFailedAlive();
                    } finally {
                        if (rs.getFailedAlive() > GlobalParameters.failedAliveMAxCount) {
                            toRemove.add(rs.getLocalUsername());
                            Log.LogMessage(this.getClass(), rs.getLocalUsername() + " TIMED OUT! -> AUTOMATICALLY LOGGING OFF");
                        }
                    }
                } else {
                    toRemove.add(rs.getLocalUsername());
                }
            }

            synchronized (usersessions) {
                for (String s : toRemove) {
                    RemoteSessionImpl remove = usersessions.remove(s);
                    if (!remove.isLoggedOff()) {
                        try {
                            remove.logoff();
                        } catch (RemoteException ex) {
                        }
                    }
                }
            }
        }
    }
}
