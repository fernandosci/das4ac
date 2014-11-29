/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.commom;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ito
 */
public interface AuctionServer extends Remote {

    RemoteSession login(String username, ClientListener listener) throws RemoteException;
}
