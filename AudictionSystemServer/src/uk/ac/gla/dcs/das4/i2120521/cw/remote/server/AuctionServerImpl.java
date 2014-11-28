/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionNotificationListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
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
        }
    }

    @Override
    public RemoteSession login(String username, AuctionNotificationListener listener) throws RemoteException {

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
            throw new Exception("SERVERN NOT INITIALIZED");
        }
        synchronized (usersessions) {
            return usersessions.get(username);
        }
    }

}
