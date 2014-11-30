/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ito
 */
public class BidResult implements Serializable{

    private final boolean priceMet;
    private final String winner;
    private final String owner;
    private final double value;
    private final Set<String> bidders;

    public BidResult(String owner, boolean priceMet, String winner, double value, Set<String> bidders) {
        this.priceMet = priceMet;
        this.winner = winner;
        this.owner = owner;
        this.value = value;
        this.bidders = new HashSet<String>(bidders);
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

    public Set<String> getBidders() {
        return new HashSet<String>(bidders);
    }

}
