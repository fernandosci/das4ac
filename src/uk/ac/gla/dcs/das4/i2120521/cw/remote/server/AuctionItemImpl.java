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
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionItem;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.BidError;

public class AuctionItemImpl extends UnicastRemoteObject implements AuctionItem {

    private final String owner;
    private final String name;
    private final double minimumValue;
    private final Date openingDate;
    private final Date closingDate;
    private final UID id;
    private final BidMngr bidMngr;

    protected AuctionItemImpl(String owner, String name, double minimumValue, Date closingDate) throws RemoteException {
        super();
        this.owner = owner;
        this.id = new UID();
        this.name = name;
        this.minimumValue = minimumValue;
        this.openingDate = Calendar.getInstance().getTime();
        this.closingDate = closingDate;

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
}
