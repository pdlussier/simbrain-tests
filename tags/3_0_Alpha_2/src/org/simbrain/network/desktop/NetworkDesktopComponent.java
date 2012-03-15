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
package org.simbrain.network.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.simbrain.network.NetworkComponent;
import org.simbrain.network.gui.NetworkPanel;
import org.simbrain.network.gui.actions.ShowNetworkPreferencesAction;
import org.simbrain.workspace.component_actions.CloseAction;
import org.simbrain.workspace.component_actions.OpenAction;
import org.simbrain.workspace.component_actions.SaveAction;
import org.simbrain.workspace.component_actions.SaveAsAction;
import org.simbrain.workspace.gui.CouplingMenuComponent;
import org.simbrain.workspace.gui.GenericFrame;
import org.simbrain.workspace.gui.GuiComponent;

/**
 * Network desktop component. An extension of the Gui component for this class
 * which is used in the Simbrain desktop.
 */
public final class NetworkDesktopComponent extends GuiComponent<NetworkComponent> {

    private static final long serialVersionUID = 1L;

    /** Network panel. */
    private final NetworkPanelDesktop networkPanel;

    /** Menu bar. */
    private JMenuBar menuBar;

    /** Default height. */
    private static final int DEFAULT_HEIGHT = 450;

    /** Default width. */
    private static final int DEFAULT_WIDTH = 450;

    /**
     * Create a new network frame.
     */
    public NetworkDesktopComponent(final GenericFrame frame, final NetworkComponent component) {
        super(frame, component);
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        networkPanel = new NetworkPanelDesktop(this, component.getRootNetwork());

        // component.setCurrentFile(currentFile);

        // Place networkPanel in a buffer so that toolbars don't get in the way
        // of canvas elements
        setLayout(new BorderLayout());

        // Put it all together
        add("Center", networkPanel);
        createAndAttachMenus();
    }

    /**
     * Create and attach the menus for this network frame.
     */
    private void createAndAttachMenus() {

        menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(networkPanel.createEditMenu());
        menuBar.add(networkPanel.createInsertMenu());
        menuBar.add(networkPanel.createViewMenu());
        menuBar.add(NetworkScriptMenu.getNetworkScriptMenu(this.getNetworkPanel()));
        //menuBar.add(createAttributeMenu());
        menuBar.add(new CouplingMenuComponent("Couple", this.getWorkspaceComponent()
                .getWorkspace(), this.getWorkspaceComponent()));
        menuBar.add(networkPanel.createHelpMenu());
        getParentFrame().setJMenuBar(menuBar);
    }

    /**
     * Create and return a new File menu for this rootNetwork panel.
     *
     * @return a new File menu for this rootNetwork panel
     */
    JMenu createFileMenu() {

        JMenu fileMenu = new JMenu("File");

        fileMenu.add(new OpenAction(this));
        fileMenu.add(new SaveAction(this));
        fileMenu.add(new SaveAsAction(this));
        fileMenu.addSeparator();
        fileMenu.add(new ShowNetworkPreferencesAction(networkPanel));
        fileMenu.addSeparator();

        fileMenu.add(new CloseAction(this.getWorkspaceComponent()));

        return fileMenu;
    }

    @Override
    public void postAddInit() {
        if (this.getParentFrame().getJMenuBar() == null) {
            createAndAttachMenus();
        }

        // TODO: Below only needs to happen when opening; but currently it
        // happens also when creating a new network
        networkPanel.clearPanel();
        if (networkPanel.getRootNetwork() != this.getWorkspaceComponent()
                .getRootNetwork()) {
            networkPanel.setRootNetwork(this.getWorkspaceComponent()
                    .getRootNetwork().getRootNetwork());
        }
        networkPanel.syncToModel();
        networkPanel.initGui();
    }

    /**
     * Return the network panel for this network frame.
     *
     * @return the network panel for this network frame
     */
    public NetworkPanel getNetworkPanel() {
        return networkPanel;
    }

    @Override
    public void closing() {
    }

}