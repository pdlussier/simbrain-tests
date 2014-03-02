/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005,2007 The Authors.  See http://www.simbrain.net/credits
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.simbrain.network.gui.dialogs.network;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.simbrain.network.gui.NetworkPanel;
import org.simbrain.network.gui.dialogs.TestInputPanel;
import org.simbrain.network.gui.trainer.DataPanel;
import org.simbrain.network.gui.trainer.subnetworkTrainingPanels.CompetitiveTrainerControlsPanel;
import org.simbrain.network.subnetworks.CompetitiveNetwork;
import org.simbrain.network.trainers.CompetitiveTrainer;
import org.simbrain.util.ShowHelpAction;
import org.simbrain.util.StandardDialog;
import org.simbrain.util.table.NumericTable;

/**
 * Dialog for training a Competitive network.
 *
 * @author Jeff Yoshimi
 *
 */
public class CompetitiveTrainingDialog extends StandardDialog {

    /** Main tabbed pane. */
    private JTabbedPane tabbedPane = new JTabbedPane();

    /** Panel for setting properties of the competitive network. */
    private CompetitivePropertiesPanel competitivePropsPanel;

    /** Reference to network panel. */
    private NetworkPanel panel;

    /** Reference to the Competitive Network. */
    private CompetitiveNetwork network;

    /**
     * Construct the dialog.
     *
     * @param np parent network panel
     * @param network the Competitive network
     */
    public CompetitiveTrainingDialog(NetworkPanel np, CompetitiveNetwork network) {

        this.panel = np;
        this.network = network;

        setTitle("Edit / Train Competitive Network");

        // Set to modeless so the dialog can be left open
        setModalityType(ModalityType.MODELESS);

        // Set up properties tab
        competitivePropsPanel = new CompetitivePropertiesPanel(np,
                network.getCompetitive());
        tabbedPane.addTab("Network Properties", competitivePropsPanel);

        // Set up training tab
        CompetitiveTrainerControlsPanel controlPanel = new CompetitiveTrainerControlsPanel(
                panel, new CompetitiveTrainer(network), network);
        tabbedPane.addTab("Train Network", controlPanel);

        // Input data tab
        final DataPanel inputPanel = new DataPanel(network.getInputNeurons(),
                network.getTrainingSet().getInputDataMatrix(), 5, "Input");
        inputPanel.setFrame(this);
        tabbedPane.addTab("Training data", inputPanel);

        // Testing tab
        final TestInputPanel testInputPanel = new TestInputPanel(np,
                network.getInputNeurons(), network.getTrainingSet()
                        .getInputData());
        tabbedPane.addTab("Test data", testInputPanel);

        // Listen for tab changed events. Load inputs to test tab
        // If inputs have been loaded
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent
                        .getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                // Just clicked out of Properties tab
                if (index == 2) {
                    competitivePropsPanel.commitChanges();
                }
                // Just clicked out of input tab
                if (index == 3) {
                    if (inputPanel.getTable().getData() != null) {
                        testInputPanel.setData(((NumericTable) inputPanel
                                .getTable().getData()).asDoubleArray());
                    }
                }
            }
        };
        tabbedPane.addChangeListener(changeListener);

        // Set up help
        Action helpAction = new ShowHelpAction(
                "Pages/Network/network/CompetitveMnetwork.html");
        addButton(new JButton(helpAction));

        // Finish configuration
        setContentPane(tabbedPane);

    }

    @Override
    protected void closeDialogOk() {
        super.closeDialogOk();
        competitivePropsPanel.commitChanges();
    }

}
