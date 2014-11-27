/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.BidResult;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.BidError;

/**
 *
 * @author ito
 */
public class BidMngr {

    String owner;
    double minimumValue;

    double currentBid;
    String currentWinner;

    boolean closed;

    Set<String> bidders = new HashSet<>();
    List<uservalue> bids = new ArrayList<>();

    BidMngr(double minimumValue, String owner) {
        this.minimumValue = minimumValue;
        this.closed = false;
        currentBid = -1;
        currentWinner = "";
    }

    synchronized BidResult close() {
        closed = true;
        return new BidResult(owner, (currentBid > minimumValue), currentWinner, currentBid);
    }

    synchronized BidError bid(String username, double value) {
        if (!closed) {
            if (value > minimumValue && value > currentBid && username.equals(owner)) {

                currentBid = value;
                currentWinner = username;
                if (!bidders.contains(username)) {
                    bidders.add(username);
                }
                bids.add(new uservalue(username, value));

                return BidError.NONE;

            } else {
                return BidError.LOWVALUE;
            }

        } else {
            return BidError.CLOSED;
        }
    }

    class uservalue {

        String username;
        Double value;

        public uservalue(String username, Double value) {
            this.username = username;
            this.value = value;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

    }

}
