
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
/**
 * This example, like all Swing examples, exists in a package:
 * in this case, the "start" package.
 * If you are using an IDE, such as NetBeans, this should work 
 * seamlessly.  If you are compiling and running the examples
 * from the command-line, this may be confusing if you aren't
 * used to using named packages.  In most cases,
 * the quick and dirty solution is to delete or comment out
 * the "package" line from all the source files and the code
 * should work as expected.  For an explanation of how to
 * use the Swing examples as-is from the command line, see
 * http://docs.oracle.com/javase/javatutorials/tutorial/uiswing/start/compile.html#package
 */
package scheduler;
 
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.io.*;
import java.util.ArrayList;
//import java.nio.*;



public class Scheduler {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	static JFrame topFrame;
	public ButtonGroup mainButtons;
	public Schedule sched; 
	
	static class SchedulerMenu extends JMenuBar {
		
		private JLabel headerLabel;
		private JPanel controlPanel;

		JFileChooser fc;
		StatusBar sb; 
		JLabel statusLabel;
		
	    private void saveFile(File file) {
	    	try (FileWriter fr = new FileWriter(file.getAbsolutePath())) {
	    		BufferedWriter writer = new BufferedWriter(fr);
	    	    String line = null;
	    	    
	    	    //
	    	    
	    	} catch (IOException x) {
	    	    System.err.format("IOException: %s%n", x);
	    	}
	    }
	    
	    private void openFile(File file) {
	    	try (FileReader fr = new FileReader(file.getAbsolutePath())) {
	    		BufferedReader reader = new BufferedReader(fr);
	    	    String line = null;
	    	    while ((line = reader.readLine()) != null) {
	    	        System.out.println(line);
	    	        //parse line here        
	    	    }
	    	} catch (IOException x) {
	    	    System.err.format("IOException: %s%n", x);
	    	}
	    }

	    private JPanel showNewDialog() {
	    	JPanel newDial = new JPanel(new BorderLayout());
	    	
	    	return newDial;
	    }
	    
	    
		public JMenuBar createMenuBar() {
			final JMenuBar menuBar = new JMenuBar();
			fc = new JFileChooser();
			
	        StatusBar sb = new StatusBar();
	        statusLabel = new JLabel("uh");
	        sb.add(statusLabel);
			
		    //create menus
			JMenu fileMenu = new JMenu("File");
		    JMenu editMenu = new JMenu("Edit"); 
		    final JMenu aboutMenu = new JMenu("About");
		  	 
		    //create menu items
		    JMenuItem newMenuItem = new JMenuItem("New Schedule");
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

		  	
		  	  //add menu items to menus
		  	  fileMenu.add(newMenuItem);
		  	  fileMenu.add(openMenuItem);
		  	  fileMenu.add(saveMenuItem);
		  	  fileMenu.addSeparator();      
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

		  	  return menuBar;
	    }
		
	    class MenuItemListener implements ActionListener {
	        public void actionPerformed(ActionEvent e) {            
	           statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
	           
	           String command = e.getActionCommand();
	           int retVal = 0;
	           
	           switch (command) {
	           	   case "New":
	           		   
	           		   break;
	           
		           case "Open":
		        	   retVal = fc.showOpenDialog(SchedulerMenu.this);
		        	   if (retVal == JFileChooser.APPROVE_OPTION) {
		        		   File toOpen = fc.getSelectedFile();
		        		   openFile(toOpen);
		        	   }
		        	   break;
		           case "Save":
		        	   retVal = fc.showSaveDialog(SchedulerMenu.this);
		        	   if (retVal == JFileChooser.APPROVE_OPTION) {
		        		   File toSave = fc.getSelectedFile();
		        		   saveFile(toSave);
		        	   }
		        	   break;
		           case "Exit":
		        	   break;
		           default:	break;
	           }
	        }    
	    }
	    
	}
	
	
	private void loadMainButtons() {
		JButton runAlgo = new JButton("Generate Schedule");
		JButton addEmpl = new JButton("Add Employee");
		
		mainButtons.add(runAlgo);
		mainButtons.add(addEmpl);
		
		
	}
	
	
	
    private static void createAndShowGUI() {
        //Create and set up the window.
        topFrame = new JFrame("Scheduler");
        topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DayDetailCalendar cal = new DayDetailCalendar();
        WeekTable weekTbl = cal.wt;
        cal.setSize(300, 200);
        cal.setVisible(true);
        
        //weekTbl.setSize(50, 50);
 
        ArrayList<Employee> test_empls = new ArrayList<Employee>();
        test_empls.add(new Employee(69, "Arthur Dent", false));
        test_empls.add(new Employee(420, "Ford Prefect", true));
        
        PeoplePeeker pplPeekPanel = new PeoplePeeker(test_empls);
        pplPeekPanel.setVisible(true);
        
        MainButtons mainButt = new MainButtons();
        mainButt.setVisible(true);
        
        JPanel mainPanel = new JPanel(); 
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(cal, BorderLayout.LINE_START);
        mainPanel.add(mainButt, BorderLayout.CENTER);
        mainPanel.add(weekTbl, BorderLayout.PAGE_END);
        mainPanel.add(pplPeekPanel, BorderLayout.LINE_END);
        
        SchedulerMenu menu = new SchedulerMenu();
        topFrame.setJMenuBar(menu.createMenuBar());
        mainPanel.setVisible(true);
        topFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        //topFrame.getContentPane().add(menu.getStatusBar(), BorderLayout.SOUTH);
        topFrame.getContentPane().add(new JLabel("Welcome to the Scheduler!"), BorderLayout.PAGE_START);
      
        
        
        //Display the window.
        topFrame.pack();
        topFrame.setSize(1000, 720);
        topFrame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    

    
    
    
    
    
}