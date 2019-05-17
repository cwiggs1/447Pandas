package scheduler;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WeekTable extends JPanel{

	DefaultTableModel model;
	JLabel label;
	Calendar cal = new GregorianCalendar();
	//var for schedule data1
	
	WeekTable() {
		
		this.setSize(100, 100);
	    this.setLayout(new BorderLayout());
	    this.setVisible(true);
		
	    label = new JLabel();
	    label.setHorizontalAlignment(SwingConstants.CENTER);
		
		String [] columns = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		model = new DefaultTableModel(null,columns);
		JTable table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
		
	    JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.add(label,BorderLayout.CENTER);
	    
	    this.add(panel,BorderLayout.NORTH);
	    this.add(pane,BorderLayout.CENTER);
	   
	    
	    this.updateDays();
		
	}

	void updateDays() {
		Date date = new Date();
		this.updateDays(date);
	}
	
	void updateDays(Date date) {
		
		cal.setTime(date);
		while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		Date weekStart = cal.getTime();
		label.setText("Week of " + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
		
		model.setRowCount(5);
		
		for (int j = 0; j < model.getRowCount(); j++) {
			
			for (int i = cal.get(Calendar.DAY_OF_WEEK); i <= Calendar.SATURDAY; i++) {
				if (j == 0) {
					//might change arg0 to value gotten from Schedule.EventAt(cal.getTime()).info() 
					model.setValueAt((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH), j, i - 1);
				} else {
					//inserts shift info on each row
					//model.setValueAt("Cell (" + i + ", " + j + ")", j, i - 1);
					
				}
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			cal.setTime(weekStart);			
		}

		
		
		
	}


}
