/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionItemInfo;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionNotificationListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.BidError;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.RemoteSession;

/**
 *
 * @author ito
 */
public class RemoteSessionImpl extends UnicastRemoteObject implements RemoteSession {

    private final String username;
    private final AuctionMngr aucMngr;
    private final AuctionNotificationListener listener;
    private final AuctionItemInfoImpl aucItemInfo;

    RemoteSessionImpl(String username, AuctionMngr aucMngr, AuctionItemInfoImpl aucItemInfo, AuctionNotificationListener listener) throws RemoteException {
        super();
        this.username = username;
        this.aucMngr = aucMngr;
        this.listener = listener;
        this.aucItemInfo = aucItemInfo;
    }

    protected AuctionNotificationListener getUserListener() {
        return listener;
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    @Override
    public Set<UID> getAvailableAuctionItems() throws RemoteException {
        return aucMngr.getAvailableAuctionItems();
    }

    @Override
    public Set<UID> getLegacyAuctionItems() throws RemoteException {
        return aucMngr.getLegacyAuctionItems();
    }

    @Override
    public Set<UID> getAllAuctionItems() throws RemoteException {
        return aucMngr.getAllAuctionItems();
    }

    @Override
    public UID newAuction(String name, double minimumValue, Date closingDate) throws RemoteException {        
        return aucMngr.newAuction(username, name, minimumValue, closingDate);
    }

    @Override
    public BidError bid(UID auctionItemId, double value) throws RemoteException {
        return aucMngr.bid(username, auctionItemId, value);
    }

    @Override
    public AuctionItemInfo getAuctionItemInfoProvider() throws RemoteException {
        return aucItemInfo;
    }

}
