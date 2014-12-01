/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Random;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.ClientListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.RemoteSession;

/**
 *
 * @author ito
 */
public class Client extends UnicastRemoteObject implements ClientListener {

    private AuctionServer server;
    private String username;
    private RemoteSession session;

    public Client(AuctionServer server, String username) throws RemoteException {
        this.server = server;
        this.username = username;

    }

    private void initialize() throws RemoteException {
        session = server.login(username, this);
        Log.LogMessage(this.getClass(), "Client " + username + " connected.");
    }

    public void run() throws RemoteException, InterruptedException {

        initialize();
        
        Random r = new Random();
        

        int c = 0;
        while (true) {
            
            double opt = r.nextDouble();
            
            if (c < 20) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 30);
                UID newAuction = session.newAuction("auction" + c, 100, cal.getTime());

                if (newAuction != null) {
                    Log.LogMessage(this.getClass(), "Auction Created. ID: " + newAuction.toString());
                } else {
                    Log.LogMessage(this.getClass(), "Auction creation FAILED. ID: ");
                }
            }

            Log.LogMessage(this.getClass(), String.valueOf(c));
            c++;
            Thread.sleep(1);
        }
    }

    @Override
    public void auctionOverNotification(AuctionOverNotificationEvent event) {

        if (event.isPriceMet()) {
            Log.LogMessage(this.getClass(), String.format("%s:: Auction over Notification::: ID-> %s\tWINNER->%s\tVALUE->%s ", username, event.getID().toString(), event.getWinner(), event.getWinningbid()));
            System.out.println();
        } else {
            Log.LogMessage(this.getClass(), String.format("%s:: Auction over Notification::: ID-> %s\tNO WINNER\t ", username, event.getID().toString(), event.getWinner(), event.getWinningbid()));
        }

    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

}
