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
public interface UserSessionProvider {
    
    RemoteSessionImpl getUserSession(String username) throws Exception;
    
}
