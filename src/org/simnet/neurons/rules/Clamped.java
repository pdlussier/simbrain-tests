/*
 * Created on Sep 13, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.simnet.neurons.rules;

import org.simnet.interfaces.*;

/**
 * @author yoshimi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Clamped extends ActivationRule {

	protected void apply(Neuron n) {
		n.setBuffer(n.getActivation());
	}
	public String getHelp() {
		return "Clamped: Activation stays at whatever level you or the environment set it at. "
			+ "Useful for input nodes, testing, or as a source of constant output.";
	}
}
