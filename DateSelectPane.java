package scheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DateSelectPane extends JPanel {
	JComboBox dayBox, monthBox, yearBox;
	String dateString;
	
	static final String DAY_COMM = "DAY";
	static final String MONTH_COMM = "MONTH";
	static final String YEAR_COMM = "YEAR";
	
	DateSelectPane() {
        
		setLayout(new FlowLayout());

		String days[] = new String[31];
        String months[] = new String[12];
        String years[] = new String[50];
        for (int i = 0; i < 31; i++) {
        	days[i] = Integer.toString(i + 1);
        }
        for (int i = 0; i < 12; i++) {
        	months[i] = Integer.toString(i + 1);
        }
        for (int i = 0, y = 2050; i < 50 && y > 2000; i++) {
        	years[i] = Integer.toString(y);
        	y--;
        }
        
        dayBox = new JComboBox(days);
        monthBox = new JComboBox(months);
        yearBox = new JComboBox(years);
        yearBox.setSelectedItem("2019");
		dayBox.setActionCommand(DAY_COMM);
		monthBox.setActionCommand(MONTH_COMM);
		yearBox.setActionCommand(YEAR_COMM);
		
		BoxListener bl = new BoxListener();
		dayBox.addActionListener(bl);
		monthBox.addActionListener(bl);
		
        add(new JLabel("Day:"));
        add(dayBox);
        add(new JLabel("Month:"));
        add(monthBox);
        add(new JLabel("Year:"));
        add(yearBox);
        
	}
	
	public String getDateString() {
		return dateString;
	}
	
	
	public Date getDate() {
		Calendar cal = new GregorianCalendar();
		cal.set(Integer.parseInt((String)yearBox.getSelectedItem()), 
				Integer.parseInt((String)monthBox.getSelectedItem()),
				Integer.parseInt((String)dayBox.getSelectedItem()));
		return cal.getTime();
	}
	
	
	class BoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dateString = monthBox.getSelectedItem() + "/" + dayBox.getSelectedItem() + "/" + yearBox.getSelectedItem();
		}
	}
	
	
	
	
}
