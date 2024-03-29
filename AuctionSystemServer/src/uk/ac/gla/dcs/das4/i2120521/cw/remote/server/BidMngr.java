/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.BidError;

/**
 *
 * @author ito
 */
public class BidMngr implements Serializable{

    private final String owner;
    private final double minimumValue;

    private double currentBid;
    private String currentWinner;

    private boolean closed;
    private BidResult result;

    Set<String> bidders = new HashSet<String>();
    List<BidInfo> bids = new ArrayList<BidInfo>();

    BidMngr(double minimumValue, String owner) {
        this.minimumValue = minimumValue;
        this.owner = owner;
        this.closed = false;
        currentBid = -1;
        currentWinner = null;
        result = null;
    }

    public synchronized BidResult close() {
        if (!closed) {
            closed = true;
            result = new BidResult(owner, (currentBid > minimumValue), currentWinner, currentBid, bidders);
        }
        return result;
    }

    public synchronized BidError bid(String username, double value) {
        if (!closed) {
            if (!username.equals(owner)) {

                if (value > minimumValue && value > currentBid) {
                    currentBid = value;
                    currentWinner = username;
                    if (!bidders.contains(username)) {
                        bidders.add(username);
                    }
                    bids.add(new BidInfo(username, value));

                    return BidError.SUCCESSFUL;
                } else {
                    return BidError.LOWVALUE;
                }

            } else {
                return BidError.OWNERBID;
            }
        } else {
            return BidError.CLOSED;
        }
    }

    public synchronized boolean isClosed() {
        return closed;
    }

    public synchronized boolean isPriceMet() {
        return (currentBid > minimumValue);
    }

    public synchronized String getCurrentWinner() {
        return currentWinner;
    }
    
    public synchronized double getCurrentBid()
    {
        return currentBid;
    }

}
