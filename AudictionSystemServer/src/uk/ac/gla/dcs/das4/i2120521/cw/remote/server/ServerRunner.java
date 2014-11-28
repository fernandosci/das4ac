/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.GlobalParameters;

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
            String name = GlobalParameters.servername;
            AuctionServerImpl srv = new AuctionServerImpl();
            srv.initialize();
            
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();            
            registry.rebind(name, srv);
            //Naming.bind(name, srv);
            System.out.println(name + " bound");
        } catch (Exception e) {
            System.err.println("ServerRunner exception: " + e);
        }
    }
}
