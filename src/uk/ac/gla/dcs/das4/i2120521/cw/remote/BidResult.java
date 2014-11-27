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
public class BidResult {

    private final boolean priceMet;
    private final String winner;
    private final String owner;
    private final double value;

    public BidResult(String owner, boolean priceMet, String winner, double value) {
        this.priceMet = priceMet;
        this.winner = winner;
        this.owner = owner;
        this.value = value;
    }

    public boolean isPriceMet() {
        return priceMet;
    }

    public String getWinner() {
        return winner;
    }

    public String getOwner() {
        return owner;
    }

    public double getValue() {
        return value;
    }

}
