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
package org.simbrain.network.update_actions;

import org.simbrain.network.core.NetworkUpdateAction;
import org.simbrain.network.groups.NeuronGroup;

public class NeuronGroupRecorder implements NetworkUpdateAction {

    private final NeuronGroup group;

    public NeuronGroupRecorder(final NeuronGroup group) {
        this.group = group;
    }

    @Override
    public void invoke() {
        if (group.isRecording()) {
            group.writeActsToFile();
        }
    }

    @Override
    public String getDescription() {
        return "NeuronGroup:" + group.getLabel()
                + " (Record Activations)";
    }

    @Override
    public String getLongDescription() {
        return "Write " + group.getLabel() + "'s activities to file";
    }

}
