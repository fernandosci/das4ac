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
public interface AuctionItem extends Remote {
    
    public String getOwner() throws RemoteException;

    public String getName() throws RemoteException;

    public double getMinimumValue() throws RemoteException;
    
    public Date getOpeningDate() throws RemoteException;

    public Date getClosingDate() throws RemoteException;

    public UID getId() throws RemoteException;
    
    public boolean isOver() throws RemoteException;
    
    public String getCurrentWinner() throws RemoteException;
    
    public boolean isPriceMet() throws RemoteException;
}
