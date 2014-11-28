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
public abstract class AuctionNotificationEvent {

    private final UID id;

    public AuctionNotificationEvent(UID id) {
        this.id = id;
    }

    public UID getID() {
        return id;
    }

}
