/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import uk.ac.gla.dcs.das4.i2120521.cw.remote.AuctionItem;

/**
 *
 * @author ito
 */
public interface AuctionItemMaintainListener {
    
    void auctionItemTimeout(AuctionItem item);
    
    void auctionItemToRemove(AuctionItem item);
    
}
