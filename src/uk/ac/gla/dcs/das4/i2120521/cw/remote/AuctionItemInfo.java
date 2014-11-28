/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Date;

/**
 *
 * @author ito
 */
public interface AuctionItemInfo extends Remote {

    public String getOwner(UID uid) throws RemoteException;

    public String getName(UID uid) throws RemoteException;

    public double getMinimumValue(UID uid) throws RemoteException;

    public Date getOpeningDate(UID uid) throws RemoteException;

    public Date getClosingDate(UID uid) throws RemoteException;

    public boolean isOver(UID uid) throws RemoteException;

    public String getCurrentWinner(UID uid) throws RemoteException;

    public boolean isPriceMet(UID uid) throws RemoteException;
}
