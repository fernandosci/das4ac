/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.GlobalParameters;

public class AuctionItem implements Serializable {

    private final String owner;
    private final String name;
    private final double minimumValue;
    private final Date openingDate;
    private final Date closingDate;
    private final Date deleteDate;
    private final UID id;
    private final BidMngr bidMngr;

    private final AtomicBoolean purge;

    protected AuctionItem(String owner, String name, double minimumValue, Date closingDate) {
        this.owner = owner;
        this.id = new UID();
        this.name = name;
        this.minimumValue = minimumValue;
        this.openingDate = Calendar.getInstance().getTime();
        this.closingDate = closingDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(closingDate);
        cal.add(GlobalParameters.purgeUnit, GlobalParameters.purgeValue);
        this.deleteDate = cal.getTime();

        purge = new AtomicBoolean(false);

        bidMngr = new BidMngr(minimumValue, owner);
    }

    public BidMngr getBidMngr() {
        return bidMngr;
    }

    public UID getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public double getMinimumValue() {
        return minimumValue;
    }

    public Date getClosingDate() {
        synchronized (closingDate) {
            return (Date) closingDate.clone();
        }
    }

    public Date getOpeningDate() {
        synchronized (openingDate) {
            return (Date) openingDate.clone();
        }
    }

    public boolean isOver() {
        return bidMngr.isClosed();
    }

    public String getCurrentWinner() {
        return bidMngr.getCurrentWinner();
    }

    public boolean isPriceMet() {
        return bidMngr.isPriceMet();
    }

    public boolean isPurged() {
        synchronized (purge) {
            return purge.get();
        }
    }

    protected void purge() {
        synchronized (purge) {
            purge.set(true);
        }
    }

    protected void setOpeningDate(Date date) {
        synchronized (openingDate) {
            this.openingDate.setTime(date.getTime());
        }
    }

    protected void setClosingDate(Date date) {
        synchronized (closingDate) {
            this.closingDate.setTime(date.getTime());
        }
    }

    protected void setDeleteDate(Date date) {
        synchronized (deleteDate) {
            this.deleteDate.setTime(date.getTime());
        }
    }

    protected Date getDeleteDate() {
        synchronized (deleteDate) {
            return (Date) deleteDate.clone();
        }
    }

}
