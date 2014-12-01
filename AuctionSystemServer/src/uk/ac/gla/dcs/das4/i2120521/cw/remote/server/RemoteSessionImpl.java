/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionItemInfo;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.ClientListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.BidError;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.RemoteSession;

/**
 *
 * @author ito
*/
public class RemoteSessionImpl extends UnicastRemoteObject implements RemoteSession {

    private final String username;
    private final AuctionMngr aucMngr;
    private final ClientListener listener;
    private final AuctionItemInfoImpl aucItemInfo;

    private final AtomicBoolean loggedoff;
    private final AtomicInteger failedAlived;

    RemoteSessionImpl(String username, AuctionMngr aucMngr, AuctionItemInfoImpl aucItemInfo, ClientListener listener) throws RemoteException {
        super();
        this.username = username;
        this.aucMngr = aucMngr;
        this.listener = listener;
        this.aucItemInfo = aucItemInfo;
        this.loggedoff = new AtomicBoolean(false);
        this.failedAlived = new AtomicInteger(0);
    }

    protected ClientListener getUserListener() {

        return listener;
    }

    @Override
    public String getUsername() throws RemoteException {
        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        }
        return username;
    }

    @Override
    public Set<UID> getAvailableAuctionItems() throws RemoteException {
        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        }
        return aucMngr.getAvailableAuctionItems();
    }

    @Override
    public Set<UID> getLegacyAuctionItems() throws RemoteException {
        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        }
        return aucMngr.getLegacyAuctionItems();
    }

    @Override
    public Set<UID> getAllAuctionItems() throws RemoteException {
        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        }
        return aucMngr.getAllAuctionItems();
    }

    @Override
    public UID newAuction(String name, double minimumValue, Date closingDate) throws RemoteException {
        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        }
        return aucMngr.newAuction(username, name, minimumValue, closingDate);
    }

    @Override
    public BidError bid(UID auctionItemId, double value) throws RemoteException {
        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        }
        return aucMngr.bid(username, auctionItemId, value);
    }

    @Override
    public AuctionItemInfo getAuctionItemInfoProvider() throws RemoteException {
        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        }
        return aucItemInfo;
    }

    @Override
    public void logoff() throws RemoteException {

        if (loggedoff.get()) {
            throw new RemoteException("Logged off session accessed.");
        } else {
            Log.LogMessage(this.getClass(), getUsername() + " logged off.");
        }

        loggedoff.set(true);
    }

    protected boolean isLoggedOff() {
        return loggedoff.get();
    }

    protected void incFailedAlive() {
        failedAlived.incrementAndGet();
    }

    protected void resetFailedAlive() {
        failedAlived.set(0);
    }

    protected int getFailedAlive() {
        return failedAlived.get();
    }

    protected String getLocalUsername() {
        return username;
    }

}
