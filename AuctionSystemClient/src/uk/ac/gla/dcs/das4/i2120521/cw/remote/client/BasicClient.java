/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client;

import java.rmi.RemoteException;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;

/**
 *
 * @author ito
 */
public class BasicClient extends Client {

    public BasicClient(AuctionServer server, String username) throws RemoteException {
        super(server, username, 1);
    }

    @Override
    public void initialize() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void run_stup() {
       Log.LogMessage(this.getClass(), "Expecting an exception loggin again: " + username);
    }

}
