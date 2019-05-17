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
// Edited by Alex Donaldson

package scheduler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

//import examples.TableListSelectionDemo.SharedListSelectionHandler;
 
public class DayDetailCalendar extends JPanel {
 
  DefaultTableModel model;
  Calendar cal = new GregorianCalendar();
  JLabel label;
  //JCheckBox [] weekSelect = {new JCheckBox(), new JCheckBox(), new JCheckBox(), new JCheckBox(), new JCheckBox()}; 
  WeekTable wt;
  ListSelectionModel listSelectionModel;
  
  DayDetailCalendar() {
 
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setTitle("Swing Calandar");
    this.setSize(300,150);
    this.setLayout(new BorderLayout());
    this.setVisible(true);
 
    label = new JLabel();
    label.setHorizontalAlignment(SwingConstants.CENTER);
    wt = new WeekTable();
 
    JButton b1 = new JButton("<-");
    b1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        cal.add(Calendar.MONTH, -1);
        updateMonth();
      }
    });
 
    JButton b2 = new JButton("->");
    b2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        cal.add(Calendar.MONTH, +1);
        updateMonth();
      }
    });
 
    JPanel calPanel = new JPanel();
    calPanel.setLayout(new BorderLayout());
    calPanel.add(b1,BorderLayout.WEST);
    calPanel.add(label,BorderLayout.CENTER);
    calPanel.add(b2,BorderLayout.EAST);
 
    String [] columns = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    model = new DefaultTableModel(null,columns);
    JTable table = new JTable(model);
    table.setFillsViewportHeight(true);
    listSelectionModel = table.getSelectionModel();
    listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
    table.setSelectionModel(listSelectionModel);
    
    JPanel calPane = new JPanel();
    calPane.add(table);
 
    this.add(calPanel,BorderLayout.NORTH);
    this.add(calPane,BorderLayout.CENTER);
    
    
    this.updateMonth();
 
  }
 
  //@SuppressWarnings("deprecation")
  void updateMonth() {
    cal.set(Calendar.DAY_OF_MONTH, 1);
 
    String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
    int year = cal.get(Calendar.YEAR);
    label.setText(month + " " + year);
 
    int startDay = cal.get(Calendar.DAY_OF_WEEK);
    int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
 
    model.setRowCount(0);
    model.setRowCount(weeks);
 
    int i = startDay-1;
    for(int day=1;day<=numberOfDays;day++){
      model.setValueAt(day, i/7 , i%7);    
      i = i + 1;
    }
    
    /*for (int j = 0; j < weeks; j++) {
    	model.setValueAt(weekSelect[j], j, 7);
    }*/
 
  }
  
  class SharedListSelectionHandler implements ListSelectionListener {
      public void valueChanged(ListSelectionEvent e) { 
          ListSelectionModel lsm = (ListSelectionModel)e.getSource();

          /*int firstIndex = e.getFirstIndex();
          int lastIndex = e.getLastIndex();
          boolean isAdjusting = e.getValueIsAdjusting(); */

          if (lsm.isSelectionEmpty()) {
              //output.append(" <none>");
          } else {
              // Find out which indexes are selected.
              int minIndex = lsm.getMinSelectionIndex();
              int maxIndex = lsm.getMaxSelectionIndex();
              for (int i = minIndex; i <= maxIndex; i++) {
                  if (lsm.isSelectedIndex(i)) {
                	  int col = 0;
                	  if (i == 0) {
                		  //need error checking for if first week is selected 
                		  col = 6;
                	  }
                	  int daySelect = (int) model.getValueAt(i, col);
                	  cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), daySelect);
                	  wt.updateDays(cal.getTime());
                  }
              }
          }
          
      }
  }
 
}


