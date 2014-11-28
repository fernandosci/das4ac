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
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionItemInfo;

public class AuctionItemInfoImpl extends UnicastRemoteObject implements AuctionItemInfo {

    private final AuctionMngr mngr;

    public AuctionItemInfoImpl(AuctionMngr mngr) throws RemoteException {
        super();
        this.mngr = mngr;
    }

    @Override
    public String getOwner(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.getOwner();
    }

    @Override
    public String getName(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.getName();
    }

    @Override
    public double getMinimumValue(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.getMinimumValue();
    }

    @Override
    public Date getOpeningDate(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.getOpeningDate();
    }

    @Override
    public Date getClosingDate(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.getClosingDate();
    }

    @Override
    public boolean isOver(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.isOver();
    }

    @Override
    public String getCurrentWinner(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.getCurrentWinner();
    }

    @Override
    public boolean isPriceMet(UID uid) throws RemoteException {
        AuctionItem auctionItem = mngr.getAuctionItem(uid);
        if (auctionItem == null) {
            throw new RemoteException("UID NOT FOUND.");
        }
        return auctionItem.isPriceMet();
    }

}
