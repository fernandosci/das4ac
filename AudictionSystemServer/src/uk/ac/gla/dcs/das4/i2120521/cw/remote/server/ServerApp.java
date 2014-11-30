/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.server.AuctionMngr;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.server.AuctionServerImpl;
import uk.ac.gla.dcs.das4.i2120521.cw.remote.server.RemoteSessionImpl;

/**
 *
 * @author ito
 */
public class ServerApp extends javax.swing.JFrame {

    AuctionServerImpl server;
    AuctionMngr mngr;
    Timer t;

    final HashMap<UID, VisAuctionItem> allItems;

    JList<VisAuctionItem> jListActive;
    final HashMap<UID, VisAuctionItem> activeItems;
    DefaultListModel<VisAuctionItem> activeItemsModel;

    JList<VisAuctionItem> jListClosed;
    final HashMap<UID, VisAuctionItem> closedItems;
    DefaultListModel<VisAuctionItem> closedItemsModel;

    /**
     * Creates new form ServerApp
     */
    public ServerApp(AuctionServerImpl server, AuctionMngr mngr) {
        initComponents();

        activeItemsModel = new DefaultListModel<>();
        jListActive = new JList<>(activeItemsModel);

        closedItemsModel = new DefaultListModel<>();
        jListClosed = new JList<>(closedItemsModel);

        jListActive.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListActive.setDoubleBuffered(true);
        jScrollPane2.setViewportView(jListActive);

        jListClosed.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListClosed.setDoubleBuffered(true);
        jScrollPane3.setViewportView(jListClosed);

        this.mngr = mngr;
        this.server =  server;

        allItems = new HashMap<>();
        activeItems = new HashMap<>();
        closedItems = new HashMap<>();

        t = new Timer(1, new Updater());
        t.start();
    }


    class Updater implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<RemoteSessionImpl> sessions = server.getSessions();
            jTxtUsers.setText("");
            for (RemoteSessionImpl i : sessions)
            {
                jTxtUsers.append(i.getLocalUsername()+ "\n");
            }

            Set<UID> allAuctionItems = mngr.getAllAuctionItems();
            Set<UID> availableAuctionItems = mngr.getAvailableAuctionItems();
            Set<UID> closedAuctionItems = mngr.getLegacyAuctionItems();

            synchronized (activeItems) {

                Collection<VisAuctionItem> values = activeItems.values();
                List<UID> toRemoveFromActive = new ArrayList<>();
                for (VisAuctionItem vis : values) {
                    if (!availableAuctionItems.contains(vis.getItem().getId())) {
                        toRemoveFromActive.add(vis.getItem().getId());
                    }
                }

                values = closedItems.values();
                List<UID> toRemoveFromClosed = new ArrayList<>();
                for (VisAuctionItem vis : values) {
                    if (!closedAuctionItems.contains(vis.getItem().getId())) {
                        toRemoveFromClosed.add(vis.getItem().getId());
                    }
                }

                values = allItems.values();
                List<UID> toRemoveFromAll = new ArrayList<>();
                for (VisAuctionItem vis : values) {
                    if (!allAuctionItems.contains(vis.getItem().getId())) {
                        toRemoveFromAll.add(vis.getItem().getId());
                    }
                }

                for (UID uid : allAuctionItems) {
                    if (!allItems.containsKey(uid)) {

                        VisAuctionItem i = new VisAuctionItem(mngr.getAuctionItem(uid));

                        allItems.put(uid, i);
                    }
                }

                for (UID uid : availableAuctionItems) {
                    if (!activeItems.containsKey(uid)) {

                        VisAuctionItem get = allItems.get(uid);
                        activeItems.put(uid, get);
                        activeItemsModel.addElement(get);
                    }
                }

                for (UID uid : closedAuctionItems) {

                    if (!closedItems.containsKey(uid)) {

                        VisAuctionItem get = allItems.get(uid);
                        closedItems.put(uid, get);
                        closedItemsModel.addElement(get);
                    }
                }

                for (UID uid : toRemoveFromActive) {
                    activeItemsModel.removeElement(activeItems.remove(uid));
                }
                for (UID uid : toRemoveFromClosed) {
                    closedItemsModel.removeElement(closedItems.remove(uid));
                }
                for (UID uid : toRemoveFromAll) {
                    allItems.remove(uid);
                }
            }
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtUsers = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTxtFilename = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setDoubleBuffered(true);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(jScrollPane2);

        jTabbedPane1.addTab("Active Auctions", jPanel1);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(jScrollPane3);

        jTabbedPane1.addTab("Closed Auctions", jPanel2);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jTxtUsers.setEditable(false);
        jTxtUsers.setColumns(20);
        jTxtUsers.setRows(5);
        jScrollPane1.setViewportView(jTxtUsers);

        jPanel4.add(jScrollPane1);

        jTabbedPane1.addTab("Users", jPanel4);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Save State");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setText("Load State");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jTxtFilename.setText("auctionstate1.state");
        jPanel3.add(jTxtFilename);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTxtFilename.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Filename empty!!");
        } else {
            try {
                mngr.exportAuctions(jTxtFilename.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Export failed. " + ex.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Export failed. " + ex.toString());
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTxtFilename.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Filename empty!!");
        } else {
            try {
                mngr.importAuctions(jTxtFilename.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Import failed. " + ex.toString());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Import failed. " + ex.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Import failed. " + ex.toString());
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTxtFilename;
    private javax.swing.JTextArea jTxtUsers;
    // End of variables declaration//GEN-END:variables
}
