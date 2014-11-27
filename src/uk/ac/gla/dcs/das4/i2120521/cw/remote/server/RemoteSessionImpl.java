/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionItem;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.RemoteSession;

/**
 *
 * @author ito
 */
public class RemoteSessionImpl extends UnicastRemoteObject implements RemoteSession{

    private String username;
    
    private AuctionMngr aucMngr;
    
    public RemoteSessionImpl(String username, AuctionMngr aucMngr ) throws RemoteException {
        
        this.username = username;
        this.aucMngr = aucMngr;
        
        
    }

    
    
    @Override
    public List<AuctionItem> getAvailableAuctionItems() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AuctionItem newAuction(String name, double minimumValue, Date closingDate) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUsername() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
