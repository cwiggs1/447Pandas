package scheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.lang.ref.Reference;
import java.io.*;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;


public class SchedulerMenu {
	
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel; 

	JFileChooser fc;
	
	public JMenuBar createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		fc = new JFileChooser();
		
	    //create menus
		JMenu fileMenu = new JMenu("File");
	    JMenu editMenu = new JMenu("Edit"); 
	    final JMenu aboutMenu = new JMenu("About");
	    final JMenu linkMenu = new JMenu("Links");
	  	 
	    //create menu items
	    JMenuItem newMenuItem = new JMenuItem("New");
	    newMenuItem.setMnemonic(KeyEvent.VK_N);
	    newMenuItem.setActionCommand("New");
	  	
	    JMenuItem openMenuItem = new JMenuItem("Open");
	    openMenuItem.setActionCommand("Open");
	  	
	  	  JMenuItem saveMenuItem = new JMenuItem("Save");
	  	  saveMenuItem.setActionCommand("Save");
	  	
	  	  JMenuItem exitMenuItem = new JMenuItem("Exit");
	  	  exitMenuItem.setActionCommand("Exit");
	  	
	  	  MenuItemListener menuItemListener = new MenuItemListener();
	  	  
	  	  newMenuItem.addActionListener(menuItemListener);
	  	  openMenuItem.addActionListener(menuItemListener);
	  	  saveMenuItem.addActionListener(menuItemListener);
	  	  exitMenuItem.addActionListener(menuItemListener);

	  	
	  	  final JCheckBoxMenuItem showWindowMenu = new JCheckBoxMenuItem("Show About", true);
	  	  showWindowMenu.addItemListener(new ItemListener() {
	  	     public void itemStateChanged(ItemEvent e) {
	  	        
	  	        if(showWindowMenu.getState()){
	  	           menuBar.add(aboutMenu);
	  	        } else {
	  	           menuBar.remove(aboutMenu);
	  	        }
	  	     }
	  	  });
	  	  final JRadioButtonMenuItem showLinksMenu = new JRadioButtonMenuItem(
	  	     "Show Links", true);
	  	  showLinksMenu.addItemListener(new ItemListener() {
	  	     public void itemStateChanged(ItemEvent e) {
	  	        
	  	        if(menuBar.getMenu(3)!= null){
	  	           menuBar.remove(linkMenu);
	  	           //mainFrame.repaint();
	  	        } else {                   
	  	           menuBar.add(linkMenu);
	  	           //mainFrame.repaint();
	  	        }
	  	     }
	  	  });
	  	  //add menu items to menus
	  	  fileMenu.add(newMenuItem);
	  	  fileMenu.add(openMenuItem);
	  	  fileMenu.add(saveMenuItem);
	  	  fileMenu.addSeparator();
	  	  fileMenu.add(showWindowMenu);
	  	  fileMenu.addSeparator();
	  	  fileMenu.add(showLinksMenu);       
	  	  fileMenu.addSeparator();
	  	  fileMenu.add(exitMenuItem);        
	  	  
	      Action cutAction = new DefaultEditorKit.CutAction();
	      cutAction.putValue(Action.NAME, "Cut");
	      editMenu.add(cutAction);

	      Action copyAction = new DefaultEditorKit.CopyAction();
	      copyAction.putValue(Action.NAME, "Copy");
	      editMenu.add(copyAction);

	      Action pasteAction = new DefaultEditorKit.PasteAction();
	      pasteAction.putValue(Action.NAME, "Paste");
	      editMenu.add(pasteAction);
	  	  
	  	
	  	  //add menu to menubar
	  	  menuBar.add(fileMenu);
	  	  menuBar.add(editMenu);
	  	  menuBar.add(aboutMenu);       
	  	  menuBar.add(linkMenu);


	  	  return menuBar;
    }
	
    class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
           statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
           String command = e.getActionCommand();
           int retVal = 0;
           
           switch (command) {
           case "Open":
        	   retVal = fc.showOpenDialog(null);
        	   if (retVal == JFileChooser.APPROVE_OPTION) {
        		   File toOpen = fc.getSelectedFile();
        		   //Load data
        	   }
        	   break;
           default:	break;
           
           
           }
           
           
        }    
    }
    
}

