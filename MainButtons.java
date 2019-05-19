package scheduler;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

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
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
        //int retVal = 0;
        
        switch (command) {
        case ALGO_COMM:
        	//generate(
        	break;
        case EMPL_COMM:
        	//JOptionPane.showInputDialog(this, "uhhh");
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
		//emplDialog.setLayout(new BoxLayout(emplDialog, BoxLayout.X_AXIS));
		emplDialog.setSize(200, 200);
		JPanel textEntries = new JPanel();
		textEntries.setLayout(new BoxLayout(textEntries, BoxLayout.Y_AXIS));
		textEntries.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		
		JTextField firstName = new JTextField(10);
		JLabel firstLbl = new JLabel("First Name:");
		firstLbl.setLabelFor(firstName);
		//firstName.add(firstLbl);
		
		JTextField lastName = new JTextField(10);
		JLabel lastLbl = new JLabel("Last Name:");
		lastLbl.setLabelFor(lastName);
		//lastName.add(lastLbl);
		//JCheckBox canMoonlight = new JCheckBox();
		textEntries.add(firstName);
		textEntries.add(lastName);
		
		emplDialog.add(textEntries);
		
		//emplDialog.add(firstName);
		//emplDialog.add(lastName);
		//emplDialog.add(canMoonlight);
		
		return emplDialog;
	}
	
	
	
	
	
	
	public JDialog createAddConstDialog() {
		JDialog constDialog = new JDialog();
		
		JPanel constPanel = new JPanel();
		constPanel.setSize(200, 200);
		JPanel cards = new JPanel();
		JPanel comboPane = new JPanel(new FlowLayout());
		JPanel singleCon = new JPanel(new FlowLayout());
		JPanel weeklyCon = new JPanel();
		JOptionPane affirmOpts;
		
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
        String weekdays[] = {"Sun", "Mon", "Tue","Wed","Thu","Fri","Sat"};
        
        String dayCol[] = {"Shifts"};
        String days[] = new String[31];
        String months[] = new String[12];
        for (int i = 0; i < 31; i++) {
        	days[i] = Integer.toString(i + 1);
        }
        for (int i = 0; i < 12; i++) {
        	months[i] = Integer.toString(i + 1);
        }
        
        String weekConstLabels[] = {"Weekday", "Morning", "Evening", "Night"}; 
        String singConstLabels[] = {"Date", "Morning", "Evening", "Night"};
        JComboBox constNum = new JComboBox(constNumType);
        
        // Defining single constraint content
        DefaultTableModel singModel = new DefaultTableModel(null, weekdays);
        singModel.setColumnCount(2);
        singModel.setRowCount(4);
        JTable singDayTbl = new JTable(singModel);
        
        TableColumn singCol = singDayTbl.getColumnModel().getColumn(1);
        singCol.setCellEditor(new DefaultCellEditor(constNum));

        DefaultTableCellRenderer singRenderer =
                new DefaultTableCellRenderer();
        singRenderer.setToolTipText("Click for combo box");
        singCol.setCellRenderer(singRenderer);
        
        for (int i = 0; i < 4; i++) {
        	singModel.setValueAt(singConstLabels[i], i, 0 );
        }
        
        JComboBox dayBox = new JComboBox(days);
        dayBox.setActionCommand("DAY");
        JComboBox monthBox = new JComboBox(months);
        monthBox.setActionCommand("MONTH");
        
        
        singleCon.add(singDayTbl);
        singleCon.add(new JLabel("Day: "));
        singleCon.add(dayBox);
        singleCon.add(new JLabel("Month: "));
        singleCon.add(monthBox);
        
        
        ActionListener al = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String comm = e.getActionCommand();
        		singModel.setValueAt(monthBox.getSelectedItem() + "/" + dayBox.getSelectedItem(), 0, 1);
        	}
        };
        dayBox.addActionListener(al);
        monthBox.addActionListener(al);
		singModel.setValueAt(monthBox.getSelectedItem() + "/" + dayBox.getSelectedItem(), 0, 1);        
        
        
        
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
        		} else if (i == 2 && j > 5) {
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
        

        String options [] = {ENTER, CANCEL};
		affirmOpts = new JOptionPane(constPanel,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);
        
        
		constDialog.setContentPane(affirmOpts);
		
        //constDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        constDialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    affirmOpts.setValue(JOptionPane.CLOSED_OPTION);
            }
        });
        
        
        PropertyChangeListener pcl = new PropertyChangeListener() {
        
            public void propertyChange(PropertyChangeEvent e) {
                String prop = e.getPropertyName();

                if (isVisible()
                 && (e.getSource() == affirmOpts)
                 && (affirmOpts.VALUE_PROPERTY.equals(prop) ||
                     affirmOpts.INPUT_VALUE_PROPERTY.equals(prop))) {
                    Object value = affirmOpts.getValue();

                    if (value == affirmOpts.UNINITIALIZED_VALUE) {
                        //ignore reset
                        return;
                    }

                    //Reset the affirmOpts's value.
                    //If you don't do this, then if the user
                    //presses the same button next time, no
                    //property change event will be fired.
                    affirmOpts.setValue(affirmOpts.UNINITIALIZED_VALUE);

                    if (ENTER.equals(value)) {

                    	if (constTypeBox.getSelectedItem().equals(SINGLE_CON_PANEL)) {
                    		
                    		empls.get(emplBox.getSelectedIndex());
                    		System.out.println("Single constraint saved!");
                    	} else if (constTypeBox.getSelectedItem().equals(WEEK_CON_PANEL)) {
                    		
                    		empls.get(emplBox.getSelectedIndex());
                    		System.out.println("Weekly constraint saved!");
                    	}
                    	
                    	
                    	constDialog.setVisible(false);
                    } else if (CANCEL.equals(value)) { //user closed dialog or clicked cancel
                        constDialog.setVisible(false);
                    }
                }
            }
        	
        };
		
        return constDialog;
	}
	
	
	
}
