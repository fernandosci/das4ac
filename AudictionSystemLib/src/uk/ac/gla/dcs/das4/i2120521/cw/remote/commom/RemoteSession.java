/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.commom;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author ito
 */
public interface RemoteSession extends Remote {

    String getUsername() throws RemoteException;

    Set<UID> getAvailableAuctionItems() throws RemoteException;

    Set<UID> getLegacyAuctionItems() throws RemoteException;

    Set<UID> getAllAuctionItems() throws RemoteException;

    UID newAuction(String name, double minimumValue, Date closingDate) throws RemoteException;

    public BidError bid(UID auctionItemId, double value) throws RemoteException;

    AuctionItemInfo getAuctionItemInfoProvider() throws RemoteException;

}
