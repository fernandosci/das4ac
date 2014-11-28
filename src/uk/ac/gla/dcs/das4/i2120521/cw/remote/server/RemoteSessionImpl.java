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
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionNotificationListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.BidResult;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.RemoteSession;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.TransactionMngr;

/**
 *
 * @author ito
 */
public class RemoteSessionImpl extends UnicastRemoteObject implements RemoteSession {

    private String username;
    private AuctionMngr aucMngr;
    private AuctionNotificationListener listener;

    public RemoteSessionImpl(String username, AuctionMngr aucMngr, AuctionNotificationListener listener) throws RemoteException {

        this.username = username;
        this.aucMngr = aucMngr;
        this.listener = listener;
    }

    protected AuctionNotificationListener getUserListener() {
        return listener;
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

    @Override
    public BidResult bid(double value, TransactionMngr wallet) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
