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
import java.util.concurrent.atomic.AtomicBoolean;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionItem;

public class AuctionItemImpl extends UnicastRemoteObject implements AuctionItem {

    private final String owner;
    private final String name;
    private final double minimumValue;
    private final Date openingDate;
    private final Date closingDate;
    private final UID id;
    private final BidMngr bidMngr;

    private final AtomicBoolean purge;

    protected AuctionItemImpl(String owner, String name, double minimumValue, Date closingDate) throws RemoteException {
        super();
        this.owner = owner;
        this.id = new UID();
        this.name = name;
        this.minimumValue = minimumValue;
        this.openingDate = Calendar.getInstance().getTime();
        this.closingDate = closingDate;

        purge = new AtomicBoolean(false);
        

        bidMngr = new BidMngr(minimumValue, owner);
    }

    public BidMngr getBidMngr() {
        return bidMngr;
    }

    @Override
    public UID getId() {
        return id;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getMinimumValue() {
        return minimumValue;
    }

    @Override
    public Date getClosingDate() throws RemoteException {
        return closingDate;
    }

    @Override
    public Date getOpeningDate() throws RemoteException {
        return openingDate;
    }

    @Override
    public boolean isOver() throws RemoteException {
        return bidMngr.isClosed();
    }

    @Override
    public String getCurrentWinner() throws RemoteException {
        return bidMngr.getCurrentWinner();
    }

    @Override
    public boolean isPriceMet() throws RemoteException {
        return bidMngr.isPriceMet();
    }

    public boolean isPurged() {
        synchronized (purge) {
            return purge.get();
        }
    }

    public void purge() {
        synchronized (purge) {
            purge.set(true);
        }
    }

}
