/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.client.gui.ClientGui;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.GlobalParameters;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;

/**
 *
 * @author ito
 */
public class ClientRunner implements Runnable {

    int option;
    AuctionServer server;
    String user;

    public ClientRunner(int option, AuctionServer server, String user) {
        this.option = option;
        this.server = server;
        this.user = user;
    }

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        String host = "127.0.0.1";
        String username = "user";
        int opt = 1;
        int times = 1;

        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            username = args[1];
        }
        if (args.length > 2) {
            try {
                opt = Integer.parseInt(args[2]);
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Wrong argument 3: " + numberFormatException.toString());
            }
        }
        if (args.length > 3) {
            try {
                times = Integer.parseInt(args[3]);
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Wrong argument 4: " + numberFormatException.toString());
            }
        }

        try {
            String name = GlobalParameters.servername;

            Registry registry = LocateRegistry.getRegistry(host, GlobalParameters.regPort);
            AuctionServer server = (AuctionServer) registry.lookup(name);

            for (int c = 0; c < times; c++) {
                Thread thread = new Thread(new ClientRunner(opt, server, username + c));
                thread.start();
            }
        } catch (Exception e) {
            System.err.println("ClientRunner: Failed to connect. " + e);

        }
    }

    @Override
    public void run() {

        switch (option) {
            case 1: {
                ClientGui gui = new ClientGui(server, user);
                gui.setVisible(true);
            }
            break;
            case 2: {
                try {
                    Client c = new Client(server, user);
                    c.run();
                } catch (RemoteException ex) {
                    Log.LogMessage(this.getClass(), ex.toString());
                } catch (InterruptedException ex) {
                    Log.LogMessage(this.getClass(), ex.toString());
                }
            }
            break;
        }
    }
}
