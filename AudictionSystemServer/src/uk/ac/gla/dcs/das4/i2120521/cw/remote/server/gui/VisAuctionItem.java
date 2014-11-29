/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server.gui;

import uk.ac.gla.dcs.das4.i2120521.cw.remote.server.AuctionItem;

/**
 *
 * @author ito
 */
class VisAuctionItem {
    private final AuctionItem item;

    public VisAuctionItem(AuctionItem item) {
        this.item = item;
    }

    public AuctionItem getItem() {
        return item;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        String space = "         ";
        b.append("ID-> ");
        b.append(item.getId().toString());
        b.append(space);
        b.append(space);
        b.append("OWNER-> ");
        b.append(item.getOwner());
        b.append(space);
        b.append("AUCTIONAME-> ");
        b.append(item.getName());
        return b.toString();
    }
    
}
