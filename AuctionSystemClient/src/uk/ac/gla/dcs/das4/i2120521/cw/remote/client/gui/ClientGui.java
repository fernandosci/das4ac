/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.client.gui;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionItemInfo;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionOverNotificationEvent;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.AuctionServer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.BidError;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.ClientListener;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.commom.RemoteSession;

/**
 *
 * @author ito
 */
public class ClientGui extends javax.swing.JFrame {

    private final AuctionServer server;
    private RemoteSession session;

    private boolean open = true;

    JList<Itemss> jListAuctions;
    DefaultListModel<Itemss> auctionItemsModel;

    /**
     * Creates new form ServerApp
     *
     * @param server
     */
    public ClientGui(AuctionServer server) {
        initComponents();

        this.server = server;
        this.session = null;

        auctionItemsModel = new DefaultListModel<Itemss>();
        jListAuctions = new JList<Itemss>(auctionItemsModel);

        jListAuctions.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListAuctions.setDoubleBuffered(true);
        jScrollPane1.setViewportView(jListAuctions);
    }

    public void login() {
        if (!jTxtUsername.getText().isEmpty()) {

            try {
                session = server.login(jTxtUsername.getText(), new Serverlistener());
                jBtnLogin.setEnabled(false);
                jTxtUsername.setEnabled(false);

                jBtnShowAvailable.setEnabled(true);
                jBtnShowClosed.setEnabled(true);
                jBtnLogoff.setEnabled(true);
                jBtnNewAuction.setEnabled(true);
                jTxtBidValue.setEnabled(true);

            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Login failed. " + ex.toString());
            }

        } else {
            JOptionPane.showMessageDialog(this, "username empty!");
        }
    }

    public void display(Set<UID> ids) {

        auctionItemsModel.clear();

        try {
            for (UID id : ids) {
                AuctionItemInfo item = session.getAuctionItemInfoProvider();

                StringBuilder b = new StringBuilder();
                String space = "         ";
                b.append("ID-> ");
                b.append(id.toString());
                b.append(space);
                b.append(space);
                b.append("OWNER-> ");
                b.append(item.getOwner(id));
                b.append(space);
                b.append("AUCTIONAME-> ");
                b.append(item.getName(id));
                b.append(space);
                b.append("MINIMUMVALUE-> ");
                b.append(item.getMinimumValue(id));
                b.append(space);
                b.append("OPENING DATE-> ");
                b.append(item.getOpeningDate(id));
                b.append(space);
                b.append("CLOSING DATE-> ");
                b.append(item.getClosingDate(id));
                b.append(space);
                b.append("CURRENT WINNER-> ");
                b.append(item.getCurrentWinner(id));
                b.append(space);
                b.append("CURRENT BID-> ");
                b.append(item.getCurrentBid(id));

                auctionItemsModel.addElement(new Itemss(b.toString(), id));
            }
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Failed. (NOT LOGGED IN??)");
        }
    }

    class TimerWorkerUpdater extends TimerTask {

        @Override
        public void run() {
            if (open) {
            } else {
            }

        }

    }

    class Serverlistener extends UnicastRemoteObject implements ClientListener {

        public Serverlistener() throws RemoteException {
        }

        @Override
        public void auctionOverNotification(AuctionOverNotificationEvent event) throws RemoteException {
            if (event.isPriceMet()) {
                jTxtAreaLog.append(String.format("%s:: Auction over Notification::: ID-> %s\tWINNER->%s\tVALUE->%s \n", jTxtUsername.getText(), event.getID().toString(), event.getWinner(), event.getWinningbid()));
            } else {
                jTxtAreaLog.append(String.format("%s:: Auction over Notification::: ID-> %s\tNO WINNER\t \n", jTxtUsername.getText(), event.getID().toString(), event.getWinner(), event.getWinningbid()));
            }
        }

        @Override
        public boolean isAlive() throws RemoteException {
            return true;
        }
    }

    class Itemss {

        private String txt;
        private UID uid;

        public Itemss(String txt, UID uid) {
            this.txt = txt;
            this.uid = uid;
        }

        @Override
        public String toString() {
            return txt;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtnLogin = new javax.swing.JButton();
        jTxtUsername = new javax.swing.JTextField();
        jBtnShowAvailable = new javax.swing.JButton();
        jBtnShowClosed = new javax.swing.JButton();
        jBtnLogoff = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTxtBidValue = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtAreaLog = new javax.swing.JTextArea();
        jBtnBid = new javax.swing.JButton();
        jBtnNewAuction = new javax.swing.JButton();
        jTxtAuctionName = new javax.swing.JTextField();
        jTxtAuctionMinValue = new javax.swing.JTextField();
        jTxtActionTimeUnit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBtnLogin.setText("Login");
        jBtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLoginActionPerformed(evt);
            }
        });

        jTxtUsername.setText("username");

        jBtnShowAvailable.setText("Available Auctions");
        jBtnShowAvailable.setEnabled(false);
        jBtnShowAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnShowAvailableActionPerformed(evt);
            }
        });

        jBtnShowClosed.setText("Closed Auctions");
        jBtnShowClosed.setEnabled(false);
        jBtnShowClosed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnShowClosedActionPerformed(evt);
            }
        });

        jBtnLogoff.setText("LogOff");
        jBtnLogoff.setEnabled(false);
        jBtnLogoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLogoffActionPerformed(evt);
            }
        });

        jLabel1.setText("BID VALUE");

        jTxtBidValue.setText("10");

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setMinimumSize(new java.awt.Dimension(300, 300));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(jScrollPane1);

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jTxtAreaLog.setEditable(false);
        jTxtAreaLog.setColumns(20);
        jTxtAreaLog.setRows(5);
        jScrollPane2.setViewportView(jTxtAreaLog);

        jPanel2.add(jScrollPane2);

        jSplitPane1.setRightComponent(jPanel2);

        jBtnBid.setText("Bid");
        jBtnBid.setEnabled(false);
        jBtnBid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBidActionPerformed(evt);
            }
        });

        jBtnNewAuction.setText("New Auction");
        jBtnNewAuction.setEnabled(false);
        jBtnNewAuction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewAuctionActionPerformed(evt);
            }
        });

        jTxtAuctionName.setText("AuctionName");

        jTxtAuctionMinValue.setText("1");

        jTxtActionTimeUnit.setText("30");

        jLabel2.setText("Name");

        jLabel3.setText("Minimum value");

        jLabel4.setText("TimeUnit(SEC)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnLogin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnShowAvailable)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnShowClosed)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtBidValue, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnBid, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jBtnLogoff))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnNewAuction)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtAuctionName, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtAuctionMinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtActionTimeUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnLogin)
                    .addComponent(jTxtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnShowAvailable)
                    .addComponent(jBtnShowClosed)
                    .addComponent(jBtnLogoff)
                    .addComponent(jLabel1)
                    .addComponent(jTxtBidValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNewAuction)
                    .addComponent(jTxtAuctionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtAuctionMinValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtActionTimeUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLoginActionPerformed
        login();
    }//GEN-LAST:event_jBtnLoginActionPerformed

    private void jBtnLogoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLogoffActionPerformed
        try {
            session.logoff();
            System.exit(0);
        } catch (RemoteException ex) {
            System.exit(1);
        }


    }//GEN-LAST:event_jBtnLogoffActionPerformed

    private void jBtnShowAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnShowAvailableActionPerformed
        try {
            display(session.getAvailableAuctionItems());
            open = true;

            jBtnBid.setEnabled(true);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Failed. (NOT LOGGED IN??)");
        }
    }//GEN-LAST:event_jBtnShowAvailableActionPerformed

    private void jBtnShowClosedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnShowClosedActionPerformed
        try {
            display(session.getLegacyAuctionItems());
            open = false;
            jBtnBid.setEnabled(false);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Failed. (NOT LOGGED IN??)");
        }
    }//GEN-LAST:event_jBtnShowClosedActionPerformed

    private void jBtnBidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBidActionPerformed
        List<Itemss> selectedValuesList = jListAuctions.getSelectedValuesList();

        if (selectedValuesList.size() != 1) {
            JOptionPane.showMessageDialog(this, "Select one!");
        } else {

            try {
                BidError bid = session.bid(selectedValuesList.get(0).uid, Double.parseDouble(jTxtBidValue.getText()));
                jBtnShowAvailableActionPerformed(null);
                JOptionPane.showMessageDialog(this, "BID RESULT: " + bid.toString());

            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Failed. (NOT LOGGED IN??)");
            }
        }

    }//GEN-LAST:event_jBtnBidActionPerformed

    private void jBtnNewAuctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewAuctionActionPerformed

        try {
            Calendar instance = Calendar.getInstance();

            instance.add(Calendar.SECOND, Integer.parseInt(jTxtActionTimeUnit.getText()));
            UID newAuction = session.newAuction(jTxtAuctionName.getText(), Double.parseDouble(jTxtAuctionMinValue.getText()), instance.getTime());
            if (newAuction != null) {
                jBtnShowAvailableActionPerformed(null);
                JOptionPane.showMessageDialog(this, "NEW AUCTION RESULT: " + newAuction.toString());
            } else {
                JOptionPane.showMessageDialog(this, "NEW AUCTION FAILED:");
            }
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Failed. (NOT LOGGED IN??)");
        }
    }//GEN-LAST:event_jBtnNewAuctionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnBid;
    private javax.swing.JButton jBtnLogin;
    private javax.swing.JButton jBtnLogoff;
    private javax.swing.JButton jBtnNewAuction;
    private javax.swing.JButton jBtnShowAvailable;
    private javax.swing.JButton jBtnShowClosed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTxtActionTimeUnit;
    private javax.swing.JTextArea jTxtAreaLog;
    private javax.swing.JTextField jTxtAuctionMinValue;
    private javax.swing.JTextField jTxtAuctionName;
    private javax.swing.JTextField jTxtBidValue;
    private javax.swing.JTextField jTxtUsername;
    // End of variables declaration//GEN-END:variables
}
