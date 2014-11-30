/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.commom;

import java.rmi.server.UID;

/**
 *
 * @author ito
 */
public class AuctionOverNotificationEvent extends AuctionNotificationEvent {

    private final boolean priceMet;
    private final boolean isOwn;
    private final boolean isWinner;
    private final String winner;
    private final double winningbid;

    public static AuctionOverNotificationEvent notWinner(String winner, double winningbid, UID id) {
        return new AuctionOverNotificationEvent(winner != null, false, false, winner, winningbid, id);
    }

    public static AuctionOverNotificationEvent winner(String winner, double winningbid, UID id) {
        return new AuctionOverNotificationEvent(true, false, true, winner, winningbid, id);
    }

    public static AuctionOverNotificationEvent owner(String winner, double winningbid, UID id) {
        return new AuctionOverNotificationEvent(winner != null, true, false, winner, winningbid, id);
    }

    public AuctionOverNotificationEvent(boolean priceMet, boolean isOwn, boolean isWinner, String winner, double winningbid, UID id) {
        super(id);
        this.priceMet = priceMet;
        this.isOwn = isOwn;
        this.isWinner = isWinner;
        this.winner = winner;
        this.winningbid = winningbid;
    }

    public boolean isPriceMet() {
        return priceMet;
    }

    public boolean isOwn() {
        return isOwn;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public String getWinner() {
        return winner;
    }

    public double getWinningbid() {
        return winningbid;
    }

}
