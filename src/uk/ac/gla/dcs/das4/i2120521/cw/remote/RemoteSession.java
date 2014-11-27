/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ito
 */
public interface RemoteSession extends Remote{
    
    String getUsername() throws RemoteException;
    
    List<AuctionItem> getAvailableAuctionItems() throws RemoteException;
    
    AuctionItem newAuction(String name, double minimumValue, Date closingDate) throws RemoteException;
    
    BidResult bid(double value, TransactionMngr wallet) throws RemoteException;
}
