/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gla.dcs.das4.i2120521.cw.remote.server.gui;

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

/**
 *
 * @author ito
 */
public class ServerApp extends javax.swing.JFrame {

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
    public ServerApp(AuctionMngr mngr) {
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

        allItems = new HashMap<>();
        activeItems = new HashMap<>();
        closedItems = new HashMap<>();

        t = new Timer(50, new Updater());
        t.start();
    }


    class Updater implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

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

        jButton1 = new javax.swing.JButton();
        jTxtFilename = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Save State");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTxtFilename.setText("auctionstate1.state");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setDoubleBuffered(true);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(jScrollPane2);

        jTabbedPane1.addTab("Active Auctions", jPanel1);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(jScrollPane3);

        jTabbedPane1.addTab("Closed Auctions", jPanel2);

        jButton2.setText("Load State");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 874, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtFilename, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTxtFilename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTxtFilename;
    // End of variables declaration//GEN-END:variables
}
