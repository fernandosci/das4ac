/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionNotificationListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.RemoteSession;

/**
 *
 * @author ito
 */
public class AuctionServerImpl extends UnicastRemoteObject implements AuctionServer, UserSessionProvider {

    private final HashMap<String, RemoteSessionImpl> usersessions;

    private final AuctionMngr aucMngr;

    public AuctionServerImpl() throws RemoteException {
        super();

        usersessions = new HashMap<>();
        aucMngr = new AuctionMngr(this);
    }

    @Override
    public RemoteSession login(String username, AuctionNotificationListener listener) throws RemoteException {

        synchronized (usersessions) {
            if (!usersessions.containsKey(username)) {

                RemoteSessionImpl session = new RemoteSessionImpl(username, aucMngr, listener);

                usersessions.put(username, session);
                //TODO - instantiate new session return it
                return session;

            } else {
                throw new RemoteException("username already in use.");
            }
        }

    }

    @Override
    public RemoteSessionImpl getUserSession(String username) {
        synchronized (usersessions) {
            return usersessions.get(username);
        }
    }

}
