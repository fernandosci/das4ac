/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

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
                b.append(space);
                b.append("MINIMUMVALUE-> ");
                b.append(item.getMinimumValue());
                b.append(space);
                b.append("OPENING DATE-> ");
                b.append(item.getOpeningDate());
                b.append(space);
                b.append("CLOSING DATE-> ");
                b.append(item.getClosingDate());
                b.append(space);
                b.append("CURRENT WINNER-> ");
                b.append(item.getCurrentWinner());
                b.append(space);
                b.append("CURRENT BID-> ");
                b.append(item.getBidMngr().getCurrentBid());
                
        return b.toString();
    }
    
}
