/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.ClientListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.Log;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.RemoteSession;

/**
 *
 * @author ito
 */
public abstract class Client extends UnicastRemoteObject implements ClientListener {

    protected AuctionServer server = null;
    protected String username = "";
    protected RemoteSession session = null;
    protected Random r = new Random();
    protected boolean leave = false;

    protected int codeerror = 0;
    protected String errormessage = "";

    protected int timeout;
    protected boolean ttout = false;

    public Client(AuctionServer server, String username, int timeout) throws RemoteException {
        this.server = server;
        this.username = username;
        this.timeout = timeout;

    }

    public abstract void initialize() throws RemoteException;

    public final void run() throws RemoteException, InterruptedException {
        initialize();

        Timer t = new Timer(true);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, timeout);

        t.schedule(new TimerTask() {

            @Override
            public void run() {
                leave = true;
                ttout = true;
            }
        }, instance.getTime());

        while (!leave) {

            run_stup();
        }

        while (!ttout) {
            Thread.sleep(1);
        }

        Log.LogMessage(this.getClass(), "LEAVING WITH CODE ERROR " + codeerror + " Message: " + errormessage);
    }

    protected abstract void run_stup();

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

    protected void error(int code, String msg) {
        leave = true;

        codeerror = code;
        errormessage = msg;

    }

}
