/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionNotificationListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.RemoteSession;

/**
 *
 * @author ito
 */
public class AuctionServerImpl extends UnicastRemoteObject implements AuctionServer {

    private final HashSet<String> usersessions;

    private final AuctionMngr aucMngr;

    public AuctionServerImpl() throws RemoteException {
        super();

        users = new HashSet<>(10000);
        usersessions = new HashSet<>(10000);

        aucMngr = new AuctionMngr();
    }

    @Override
    public RemoteSession login(String username, AuctionNotificationListener listener) throws RemoteException {

        boolean exist;
        boolean hasSession = false;

        synchronized (users) {
            if (users.contains(username)) {
                exist = true;
            } else {
                exist = false;
                users.add(username);
            }
        }

        if (exist) {
            synchronized (usersessions) {
                if (usersessions.contains(username)) {
                    hasSession = true;
                } else {
                    hasSession = false;
                    usersessions.add(username);
                }
            }
        }

        if (!hasSession) {

            RemoteSession session = new RemoteSessionImpl(username, exist);

            //TODO - instantiate new session return it
            return session;

        } else {
            throw new RemoteException("username already in use.");
        }
    }

}
