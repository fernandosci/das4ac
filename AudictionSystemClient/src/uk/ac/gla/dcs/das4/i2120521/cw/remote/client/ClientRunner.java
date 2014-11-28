/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.GlobalParameters;

/**
 *
 * @author ito
 */
public class ClientRunner {

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            String name = GlobalParameters.servername;
            Registry registry = LocateRegistry.getRegistry(GlobalParameters.regPort);
            AuctionServer server = (AuctionServer) registry.lookup(name);

            Client c = new Client(server, "a");
            c.run();
        } catch (Exception e) {
            System.err.println("ClientRunner: Failed to connect. " + e);

        }
    }

}
