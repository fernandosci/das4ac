/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.commom;

import java.util.Calendar;

/**
 *
 * @author ito
 */
public class GlobalParameters {

    public static final String servername = "AuctionServer";
    public static final int port = 0;

    public static final int regPort = 1099;

    public static int purgeUnit = Calendar.SECOND;
    public static int purgeValue = 60;

    public static int failedAliveMAxCount = 5; //times

    public static int sessionCleanoutFreq = 1000; //ms

}
