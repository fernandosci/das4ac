/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.commom;

/**
 *
 * @author ito
 */
public class Log {
    
    public static boolean enable = true;
    
    public static void LogMessage(Class cl, String msg)
    {
        if (enable)
            System.out.println(String.format("%s: %s", cl.getSimpleName(),msg));
    }
    
}
