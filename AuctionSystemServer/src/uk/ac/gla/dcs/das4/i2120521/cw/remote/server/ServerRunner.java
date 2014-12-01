/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
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

        boolean gui = true;

        if (args.length > 0) {
            try {
                int t = Integer.parseInt(args[0]);
                gui = t == 1;
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Wrong argument 1: " + numberFormatException.toString());
            }
        }
        if (args.length > 1) {
            try {
                GlobalParameters.purgeValue = Integer.parseInt(args[1]);
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Wrong argument 2: " + numberFormatException.toString());
            }
        }
        if (args.length > 2) {
            try {
                GlobalParameters.failedAliveMAxCount = Integer.parseInt(args[2]);
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Wrong argument 3: " + numberFormatException.toString());
            }
        }
        if (args.length > 3) {
            try {
                GlobalParameters.sessionCleanoutFreq = Integer.parseInt(args[3]);
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Wrong argument 4: " + numberFormatException.toString());
            }
        }

        try {
            String name = GlobalParameters.servername;
            AuctionServerImpl srv = new AuctionServerImpl();
            srv.initialize();

            try {
                LocateRegistry.createRegistry(GlobalParameters.regPort);
                Registry registry = LocateRegistry.getRegistry();
                registry.rebind(name, srv);
                System.out.println(name + " bound");
            } catch (RemoteException remoteException) {

                Naming.rebind("name", srv);
            }

            

            if (gui) {
                ServerApp sp = new ServerApp(srv, srv.getAuctionMngr());
                sp.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("ServerRunner exception: " + e);
        }
    }
}
