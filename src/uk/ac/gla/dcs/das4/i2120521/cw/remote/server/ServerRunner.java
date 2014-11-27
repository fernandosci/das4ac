/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import uk.ac.gla.dcs.das4.i2120521.cw.core.GlobalParameters;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionServer;

/**
 *
 * @author ito
 */
public class ServerRunner {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "AuctionServer";
            AuctionServerImpl srv = new AuctionServerImpl();
            AuctionServer stub = (AuctionServer) UnicastRemoteObject.exportObject(srv, GlobalParameters.port);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("AuctionServer bound");
        } catch (Exception e) {
            System.err.println("AuctionServer exception: " + e);
        }
    }
}
