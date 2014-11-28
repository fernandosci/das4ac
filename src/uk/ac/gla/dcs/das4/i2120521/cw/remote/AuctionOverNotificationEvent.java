/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote;

/**
 *
 * @author ito
 */
public class AuctionOverNotificationEvent extends AuctionNotificationEvent {

    private final boolean priceMet;
    private final boolean isOwn;
    private final boolean isWinner;
    private final String winner;

    public static AuctionOverNotificationEvent notWinner(String winner, AuctionItem item) {
        return new AuctionOverNotificationEvent(winner != null, false, false, winner, item);
    }

    public static AuctionOverNotificationEvent winner(String winner, AuctionItem item) {
        return new AuctionOverNotificationEvent(true, false, true, winner, item);
    }

    public static AuctionOverNotificationEvent owner(String winner, AuctionItem item) {
        return new AuctionOverNotificationEvent(winner != null, true, false, winner, item);
    }

    public AuctionOverNotificationEvent(boolean priceMet, boolean isOwn, boolean isWinner, String winner, AuctionItem item) {
        super(item);
        this.priceMet = priceMet;
        this.isOwn = isOwn;
        this.isWinner = isWinner;
        this.winner = winner;
    }

    public boolean isPriceMet() {
        return priceMet;
    }

    public boolean isIsOwn() {
        return isOwn;
    }

    public boolean isIsWinner() {
        return isWinner;
    }

    public String getWinner() {
        return winner;
    }
}
