package scheduler;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

//import examples.CustomDialog;

public class MainButtons extends JPanel
							implements ActionListener{
	
	static final protected String ALGO_COMM = "Gen";
	static final protected String EMPL_COMM = "AddEmpl";
	static final protected String CONST_COMM = "AddConst";
	static final protected String CSV_COMM = "CSV";
	
	static final protected String SINGLE_CON_PANEL = "Single Constraint";
	static final protected String WEEK_CON_PANEL = "Weekly Constraint";	
	
	static final protected String ENTER = "Enter";
	static final protected String CANCEL = "Cancel";
	
	protected ArrayList<Employee> empls;
	protected Date startDate;
	protected Schedule sched;
	
	MainButtons() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton runAlgo = new JButton("Generate Schedule");
		JButton addEmpl = new JButton("Add Employee");
		JButton addSingleConst = new JButton("Add Single Constraint");
		JButton csvExport = new JButton("Export Current Schedule to CSV");
		//JButton addWeekConst = new JButton("Add Weekly Constraint");
		runAlgo.setActionCommand(ALGO_COMM);
		addEmpl.setActionCommand(EMPL_COMM);
		addSingleConst.setActionCommand(CONST_COMM);
		csvExport.setActionCommand(CSV_COMM);

		runAlgo.addActionListener(this);
		addEmpl.addActionListener(this);
		addSingleConst.addActionListener(this);
		csvExport.addActionListener(this);
		
		//this.setLayout();
		this.add(runAlgo);
		this.add(addEmpl);
		this.add(addSingleConst);
		this.add(csvExport);
		this.setSize(200, 200);
		 
	}
	
	
	public void setEmpls(ArrayList<Employee> empls) {
		this.empls = empls;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void setSched(Schedule sched) {
		this.sched = sched;
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
        //int retVal = 0;
        
        switch (command) {
        case ALGO_COMM:
        	sched.generate();
        	break;
        case EMPL_COMM:
        	createAddEmplDialog().setVisible(true);
        	break;
        case CONST_COMM:
        	createAddConstDialog().setVisible(true);
        	break;
        case CSV_COMM:
        	break;
        }
           
	}
	
	public JDialog createAddEmplDialog() {
		JDialog emplDialog = new JDialog();
		JButton enter = new JButton(ENTER);
		JButton cancel = new JButton(CANCEL);
		String moonOpts [] = {"Yes", "No"};
		JComboBox moon = new JComboBox(moonOpts);
		JPanel emplPanel = new JPanel();
		JPanel acceptPane = new JPanel(new FlowLayout());
		
		emplDialog.setSize(200, 200);
		JPanel textEntries = new JPanel(new FlowLayout());
		JPanel selectEntries = new JPanel();
		textEntries.setLayout(new BoxLayout(textEntries, BoxLayout.Y_AXIS));
		textEntries.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		
		JTextField firstName = new JTextField(10);
		JLabel firstLbl = new JLabel("First Name:");
		JTextField lastName = new JTextField(10);
		JLabel lastLbl = new JLabel("Last Name:");
		JLabel moonLbl = new JLabel("Can Moonlight: ");
		
		textEntries.add(firstLbl);
		textEntries.add(firstName);
		textEntries.add(lastLbl);
		textEntries.add(lastName);
		textEntries.add(moonLbl);
		textEntries.add(moon);
		
		emplPanel.add(textEntries, BorderLayout.CENTER);
		
	 	class EnterCancelListener implements ActionListener {
    		public void actionPerformed(ActionEvent e) {
    			String comm = e.getActionCommand();
    			
    			switch (comm) {
    			case ENTER:	
    				Boolean moonlight = (moon.getSelectedIndex() == 0 ? true : false); 
    				empls.add(new Employee(empls.size(), firstName.getText() + " " + lastName.getText(), moonlight));
    				emplDialog.setVisible(false);
    				break;
    			case CANCEL:
    				emplDialog.setVisible(false);
    				break;
    			}
    		}
    	}
	 	enter.addActionListener(new EnterCancelListener());
	 	cancel.addActionListener(new EnterCancelListener());
        acceptPane.add(enter);
		acceptPane.add(cancel);
	 	
		emplPanel.add(acceptPane, BorderLayout.SOUTH);
		
	 	emplDialog.add(emplPanel);
		
		return emplDialog;
	}
	
	
	
	
	public JDialog createAddConstDialog() {
		JDialog constDialog = new JDialog();
		constDialog.setSize(700, 300);
		
		JPanel constPanel = new JPanel();
		constPanel.setSize(200, 200);
		JPanel cards = new JPanel();
		JPanel comboPane = new JPanel(new FlowLayout());
		JPanel singleCon = new JPanel(new FlowLayout());
		DateSelectPane dsp = new DateSelectPane();
		JPanel weeklyCon = new JPanel();
		
		JPanel acceptPane = new JPanel(new FlowLayout());
		JButton enter = new JButton(ENTER);
		JButton cancel = new JButton(CANCEL);
		
		//JOptionPane affirmOpts;
		
		constPanel.setLayout(new BorderLayout());
		cards.setLayout(new CardLayout());
		
		String constTypeOpts[] = {SINGLE_CON_PANEL, WEEK_CON_PANEL};
		JComboBox constTypeBox = new JComboBox(constTypeOpts);
		
        constTypeBox.setEditable(false);
        constTypeBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, (String)evt.getItem());
            }
        });
        comboPane.add(new JLabel("Constraint Type:"));
        comboPane.add(constTypeBox);
        
        
		String persons[] = new String[empls.size()];
		for (int i = 0; i < empls.size(); i++) {
			persons[i] = empls.get(i).getName();
		}
		JComboBox emplBox = new JComboBox(persons);
		emplBox.setEditable(false);
		comboPane.add(new JLabel("Employee:"));
		comboPane.add(emplBox);
        
		
        String constNumType[] = {"3", "2", "1", "0"};
        String weekdays[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        
        String dayCol[] = {"Shifts"};
        
        String weekConstLabels[] = {"Weekday", "Morning", "Evening", "Night"}; 
        String singConstLabels[] = {"Date", "Morning", "Evening", "Night"};
        JComboBox constNum = new JComboBox(constNumType);
        
        
        
        // Defining single constraint content
        DefaultTableModel singModel = new DefaultTableModel(null, weekdays);
        singModel.setColumnCount(2);
        singModel.setRowCount(3);
        JTable singDayTbl = new JTable(singModel);
        
        TableColumn singCol = singDayTbl.getColumnModel().getColumn(1);
        singCol.setCellEditor(new DefaultCellEditor(constNum));

        DefaultTableCellRenderer singRenderer =
                new DefaultTableCellRenderer();
        singRenderer.setToolTipText("3: Completely available; 2: Prefer not to; "
        		+ "1: Really prefer not to; 0: Completely unavailable");
        singCol.setCellRenderer(singRenderer);
        
        for (int i = 0; i < 3; i++) {
        	singModel.setValueAt(singConstLabels[i + 1], i, 0);
        	singModel.setValueAt("3", i, 1);
        }
        
        
        singleCon.add(singDayTbl);
        singleCon.add(dsp);
        
        
        
        // Defining Weekly Constraint Dialog content
        DefaultTableModel dtm = new DefaultTableModel(null, weekdays);
        dtm.setColumnCount(8);
        dtm.setRowCount(4);
        JTable weekTbl = new JTable(dtm);
        
        for (int i = 1; i < 8; i++) {
            TableColumn checkCol = weekTbl.getColumnModel().getColumn(i);
            checkCol.setCellEditor(new DefaultCellEditor(constNum));

            DefaultTableCellRenderer renderer =
                    new DefaultTableCellRenderer();
            renderer.setToolTipText("Click for combo box");
            checkCol.setCellRenderer(renderer);
            
        }

        for (int i = 0; i < 4; i++) {
        	dtm.setValueAt(weekConstLabels[i], i, 0);
        	for (int j = 1; j < 8; j++) {
        		if (i == 0) {
        			dtm.setValueAt(weekdays[j - 1], i, j);
        		} else if (i == 2 && (j == 1 || j == 7)) {
        			dtm.setValueAt("N/A", i, j);
        		} else {
        			dtm.setValueAt("3", i, j);
        		}
        		
        	}
        }
        weeklyCon.add(weekTbl);
        
        
        cards.add(singleCon, SINGLE_CON_PANEL);
        cards.add(weeklyCon, WEEK_CON_PANEL);
        constPanel.add(comboPane, BorderLayout.PAGE_START);
        constPanel.add(cards, BorderLayout.CENTER);
        
    	class EnterCancelListener implements ActionListener {
    		public void actionPerformed(ActionEvent e) {
    			String comm = e.getActionCommand();
    			
    			switch (comm) {
    			case ENTER:	
					Employee constrainer = findEmpl((String) emplBox.getSelectedItem());
    				if (constTypeBox.getSelectedIndex() == 0) {
    					//Save Single 
    					Calendar dateCal = new GregorianCalendar();
    					dateCal.setTime(dsp.getDate());
    					int numConsts;
    					if (dateCal.get(Calendar.DAY_OF_WEEK) == 1 || dateCal.get(Calendar.DAY_OF_WEEK) == 7) {
    						numConsts = 2;
    					} else {
    						numConsts = 3;
    					}
    					
    					int consts[] = new int[numConsts];
    					for (int i = 0; i < numConsts; i++) {
    						consts[i] = Integer.parseInt((String) singModel.getValueAt(i, 1));
    					}
    					setSingleConst(constrainer, dsp.getDate(), consts);
    					System.out.println("uh");
    				} else if (constTypeBox.getSelectedIndex() == 1) {
    					//Save Weekly
    					int consts[] = new int[21];
    					int constMask[] = new int[19];
    					int maskOutIdices[] = {7, 13};
    					
    					
    					for (int i = 0; i < 7; i++) {
    						for (int j = 0; j < 3; j++) {
    							if (!((i == 0 || i == 6) && j == 1) ) {
    								consts[j * 7 + i] = Integer.parseInt((String) dtm.getValueAt(j + 1, i + 1));
    							}
    						}
    					}
    					
    					for (int i = 0, mask_count = 0; i < 21; i++) {
    						if (i != maskOutIdices[0] && i != maskOutIdices[1]) {
    							constMask[mask_count] = consts[i];
    							mask_count++;
    						}
    					}
    					
    					setWeeklyConst(constrainer, constMask);
    					
    				}
    				
    				constDialog.setVisible(false);
    				break;
    			case CANCEL:
    				constDialog.setVisible(false);
    				break;
    			}
    		}
    	}
    	
        
        
        enter.addActionListener(new EnterCancelListener());
        cancel.addActionListener(new EnterCancelListener());
        acceptPane.add(enter);
		acceptPane.add(cancel);
        
		constPanel.add(acceptPane, BorderLayout.PAGE_END);
		constDialog.add(constPanel);
		
        return constDialog;
	}
	
	
	public Employee findEmpl(String emplName) {
		for (int i = 0; i < empls.size(); i++) {
			if (empls.get(i).getName().equals(emplName)) {
				return empls.get(i);
			}
		}
		return new Employee(0, "", false);
	}
	
	
	public void setSingleConst(Employee empl, Date date, int[] consts) {
		Calendar dateCal = new GregorianCalendar();
		Calendar startCal = new GregorianCalendar();
		dateCal.setTime(date);
		startCal.setTime(startDate);
		int diff = dateCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR);
		if (diff < 0) {
			diff = dateCal.get(Calendar.DAY_OF_YEAR) + (startCal.getActualMaximum(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR));
		}
		
		int weeksApart = diff / 7;
		int daysApart = diff % 7;
		
		int priorityIdx = weeksApart * 19 + ((daysApart < 5) ? daysApart * 3 : 15 + (daysApart - 4) * 2);
		
		int numConsts;
		if (dateCal.get(Calendar.DAY_OF_WEEK) == 1 || dateCal.get(Calendar.DAY_OF_WEEK) == 7) {
			numConsts = 2;
		} else {
			numConsts = 3;
		}
		for (int i = 0; i < numConsts; i++) {
			empl.setPriority(priorityIdx, consts[i]);
		}
		
	}
	
	public void setWeeklyConst(Employee empl, int[] consts) {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 19; j++) {
				empl.setPriority(i * 19 + j, consts[j]);
			}
		}
	}
	
}
