/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2003 Jeff Yoshimi <www.jeffyoshimi.net>
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

package org.simbrain.world;

import org.simbrain.network.NetworkPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.simbrain.util.SFileChooser;
import org.simbrain.util.Utils;
import org.simbrain.workspace.Workspace;

/**
 * <b>WorldPanel</b> is the container for the world component.  
 * Handles toolbar buttons, and serializing of world data.  The main
 * environment codes is in {@link World}.
 */
public class WorldFrame extends JInternalFrame implements ActionListener {

	private static final String FS = "/"; //System.getProperty("file.separator");Separator();
	private static File current_file = null;
	private String currentDirectory = "." + FS + "simulations" + FS + "worlds";
	private JScrollPane worldScroller = new JScrollPane();
	private Workspace workspace;
	private World world;
	JMenuBar mb = new JMenuBar();
	JMenu fileMenu = new JMenu("File  ");
	JMenuItem saveItem = new JMenuItem("Save");
	JMenuItem saveAsItem = new JMenuItem("Save As");
	JMenuItem openItem = new JMenuItem("Open world");
	JMenuItem prefsItem = new JMenuItem("World preferences");
	
	JMenu scriptMenu = new JMenu("Script ");
	JMenuItem scriptItem = new JMenuItem("Open script dialog");
	
	private String path;
	
	public WorldFrame() {
	}
	
	/**
	 * Construct a new world panel.  Set up the toolbars.  Create an 
	 * instance of a world object.
	 */
	public WorldFrame(Workspace ws) { 
		
		workspace = ws;
		init();
	}
	
	public void init() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("Center", worldScroller);
		world = new World();
		world.setPreferredSize(new Dimension(700, 700));
		worldScroller.setViewportView(world);
		worldScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		worldScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		worldScroller.setEnabled(false);
		setJMenuBar(mb);
		mb.add(fileMenu);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		fileMenu.add(prefsItem);
		mb.add(scriptMenu);
		scriptMenu.add(scriptItem);
		saveItem.addActionListener(this);
		saveAsItem.addActionListener(this);
		openItem.addActionListener(this);
		prefsItem.addActionListener(this);
		scriptItem.addActionListener(this);
		
		setVisible(true);
		
	}

	public File getCurrentFile() {
		return current_file;
	}
	
	public void setNetworkPanel(NetworkPanel p)
	{
		world.setNetworkPanel(p);
	}
	
	public World getWorldRef() {
		return world;
	}

	/**
	 * Show the dialog for choosing a world to open
	 */
	public void openWorld() {
		SFileChooser chooser = new SFileChooser(currentDirectory, "xml");
		File theFile = chooser.showOpenDialog();
		if (theFile != null) {
			readWorld(theFile);
			currentDirectory = chooser.getCurrentLocation();
		}
	}

	/**
	 * Creates a world based on a .wld file
	 * 
	 * @param file the world file to be read
	 */	
	public void readWorld(File theFile) {
		
		setTitle("" + theFile.getName());		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(false);
		spf.setNamespaceAware(true);
		try {
			
			// parse the document
			SAXParser parser = spf.newSAXParser();
			WorldFileReader handler = new WorldFileReader(world);
			parser.parse(theFile, handler);
			world.setObjectList(handler.getEntityList());
			world.initAgentList();
			current_file = theFile;
			
			//set Path information; used by Castor for workspace persistence
			String localDir = new String(System.getProperty("user.dir"));
			setPath(Utils.getRelativePath(localDir, getCurrentFile().getAbsolutePath()));

		} catch (NullPointerException ex) {
		    JOptionPane.showMessageDialog(null, "Could not read world file \n" 
		            + theFile, "Warning", JOptionPane.ERROR_MESSAGE);
		    ex.printStackTrace();
		    return;
		} catch (FileNotFoundException ex){
		    JOptionPane.showMessageDialog(null, "Could not find world file \n" 
		            + theFile, "Warning", JOptionPane.ERROR_MESSAGE);
		    return;
		} catch (Exception ex){
		    ex.printStackTrace();
		    return;
		}
		world.repaint();
	}



	/**
	 * Opens a file-save dialog and saves world information to the specified file
	 * 
	 * Called by "Save As"
	 */
	public void saveWorld() {
		SFileChooser chooser = new SFileChooser(currentDirectory, "xml");
		File worldFile = chooser.showSaveDialog();
		if (worldFile != null){
		    saveWorld(worldFile);
		    current_file = worldFile;
		    currentDirectory = chooser.getCurrentLocation();
		}

	}

	/**
	 * Save a specified file
	 * 
	 * Called by "save"
	 * 
	 * @param worldFile
	 */
	public void saveWorld(File worldFile) {
		try {
			FileOutputStream f = new FileOutputStream(worldFile);
			WorldFileWriter.write(f, world );
		} catch (Exception e) {
			System.out.println("Could not open file stream: " + e.toString());
		}
		setTitle("" + worldFile.getName());	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		Object e1 = e.getSource();
		
		if(e1 == openItem) {
			openWorld();
		} else if (e1 == saveItem) {
			saveWorld(current_file);
		} else if (e1 == saveAsItem) {
			saveWorld();
		} else if (e1 == prefsItem) {
			world.showGeneralDialog();
		} else if (e1 == scriptItem) {
			world.showScriptDialog();
		}
		
	}
	/**
	 * @param path The path to set; used in persistence.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * 
	 * @return path information; used in persistence
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * 
	 * @return platform-specific path
	 */
	public String getGenericPath() {
		String ret =  path;
		ret.replace('/', System.getProperty("file.separator").charAt(0));
		return ret;
	}
	
	/**
	 * @return Returns the workspace.
	 */
	public Workspace getWorkspace() {
		return workspace;
	}
	
	/**
	 * @param workspace The workspace to set.
	 */
	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}
}
	
	
