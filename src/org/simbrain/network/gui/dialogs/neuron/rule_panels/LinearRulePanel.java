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
package org.simbrain.network.gui.dialogs.neuron.rule_panels;

import java.util.List;

import javax.swing.JTabbedPane;

import org.simbrain.network.core.Neuron;
import org.simbrain.network.core.NeuronUpdateRule;
import org.simbrain.network.gui.dialogs.neuron.AbstractNeuronRulePanel;
import org.simbrain.network.gui.dialogs.neuron.NoiseGeneratorPanel;
import org.simbrain.network.neuron_update_rules.LinearRule;
import org.simbrain.util.LabelledItemPanel;

/**
 * <b>LinearNeuronPanel</b>.
 */
public class LinearRulePanel extends AbstractNeuronRulePanel {

    /** Tabbed pane. */
    private JTabbedPane tabbedPane = new JTabbedPane();

    /** Main tab. */
    private LabelledItemPanel mainTab = new LabelledItemPanel();

    /** A reference to the neuron update rule being edited. */
    private static final LinearRule prototypeRule = new LinearRule();

    /**
     * Creates an instance of this panel.
     */
    public LinearRulePanel() {
        this.add(tabbedPane);
        
        // accumulators, javafx, javabeans, pojo.  Maybe can use that.
        // helper methods somewhere in there that will do all that.  

      // JTextField slopeField = registerDoublePropery(prototypeRule::getSlope, prototypeRule::setSlope) {
      // mainTab.addItem("Slope", slopeField);
      // JTextField biasField = getBooleanProperty(prototypeRule::getBias, prototypeRule::setBias) {
      // mainTab.addItem("Bias", biasField);
      // JTextField noiseField = getNoisePanel(prototypeRule::getSlope, prototypeRule::getSlope) {
      // mainTab.addItem("Add noise", noiseField);

        init(LinearRule.editorList);
        mainTab.addItem("Slope", componentMap.get("slope"));
        mainTab.addItem("Bias", componentMap.get("bias"));
        mainTab.addItem("Add noise", componentMap.get("addNoise"));
        tabbedPane.add(mainTab, "Main");
        
        // below is superclass. Maybe call superclass method to make that more clear?
        noisePanel = new NoiseGeneratorPanel();
        tabbedPane.add(noisePanel, "Noise");
       
    }


    @Override
    protected NeuronUpdateRule getPrototypeRule() {
        return prototypeRule.deepCopy();
    }

    @Override
    public void fillDefaultValues() {
        fillDefault();     
    }

    @Override
    public void commitChanges(Neuron neuron) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void writeValuesToRules(List<Neuron> neurons) {
        // TODO Auto-generated method stub
    }

}
