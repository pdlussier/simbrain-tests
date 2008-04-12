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
package org.simbrain.network.dialog.synapse;

import javax.swing.JTextField;

import org.simbrain.network.NetworkUtils;
import org.simnet.synapses.SignalSynapse;

/**
 * <b>ClampedSynapsePanel</b>.
 */
public class SignalSynapsePanel extends AbstractSynapsePanel {

    /** Signal synapse label field. */
    private JTextField tfLabel = new JTextField();

    /**
     * This method is the default constructor.
     */
    public SignalSynapsePanel() {
        addItem("Label", tfLabel);
    }

    /**
     * Populate fields with current data.
     */
    public void fillFieldValues() {
        SignalSynapse synapseRef = (SignalSynapse) synapseList.get(0);

        tfLabel.setText(synapseRef.getLabel());

        //Handle consistency of multiply selections
        if (!NetworkUtils.isConsistent(synapseList, SignalSynapse.class, "getLabel")) {
            tfLabel.setText(NULL_STRING);
        }
    }

    /**
     * Fill field values to default values for this synapse type.
     */
    public void fillDefaultValues() {
//        SignalSynapse synapseRef = new SignalSynapse();

        tfLabel.setText(SignalSynapse.DEFAULT_LABEL);
    }

    /**
     * Called externally when the dialog is closed, to commit any changes made.
     */
    public void commitChanges() {
        for (int i = 0; i < synapseList.size(); i++) {
            SignalSynapse synapseRef = (SignalSynapse) synapseList.get(i);

            if (!tfLabel.getText().equals(NULL_STRING)) {
                synapseRef.setLabel(tfLabel.getText());
            }
        }
    }
}