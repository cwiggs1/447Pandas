package scheduler;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainButtons extends JPanel
							implements ActionListener{
	
	static final protected String ALGO_COMM = "Gen";
	static final protected String EMPL_COMM = "AddEmpl";
	static final protected String CONST_COMM = "Add Const";
	static final protected String SINGLE_CON_PANEL = "Single Constraint";
	static final protected String WEEK_CON_PANEL = "Weekly Constraint";	
	MainButtons() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton runAlgo = new JButton("Generate Schedule");
		JButton addEmpl = new JButton("Add Employee");
		JButton addSingleConst = new JButton("Add Single Constraint");
		//JButton addWeekConst = new JButton("Add Weekly Constraint");
		runAlgo.setActionCommand(ALGO_COMM);
		addEmpl.setActionCommand(EMPL_COMM);
		addSingleConst.setActionCommand(CONST_COMM);
		//addWeekConst.setActionCommand(W_CONST_COMM);
		runAlgo.addActionListener(this);
		addEmpl.addActionListener(this);
		addSingleConst.addActionListener(this);
		//addWeekConst.addActionListener(this);
		//mainButtons.add(runAlgo);
		//mainButtons.add(addEmpl);
		
		//this.setLayout();
		this.add(runAlgo);
		this.add(addEmpl);
		this.add(addSingleConst);
		//this.add(addWeekConst);
		 
	}
	
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
        int retVal = 0;
        
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
        }
           
	}
	
	public JDialog createAddEmplDialog() {
		JDialog emplDialog = new JDialog();
		//emplDialog.setLayout(new BoxLayout(emplDialog, BoxLayout.X_AXIS));
		
		JTextField firstName = new JTextField();
		JLabel firstLbl = new JLabel("First Name:");
		firstName.add(firstLbl);
		
		JTextField lastName = new JTextField();
		JLabel lastLbl = new JLabel("Last Name:");
		lastName.add(lastLbl);
		//JCheckBox canMoonlight = new JCheckBox();
		
		emplDialog.add(firstName);
		emplDialog.add(lastName);
		//emplDialog.add(canMoonlight);
		
		return emplDialog;
	}
	
	
	public JDialog createAddConstDialog() {
		JDialog constDialog = new JDialog();
		JPanel cards = new JPanel();
		JPanel comboPane = new JPanel();
		JPanel singleCon = new JPanel();
		JPanel weeklyCon = new JPanel();
		
		constDialog.setLayout(new BorderLayout());
		cards.setLayout(new CardLayout());
		
		String comboOpts[] = {SINGLE_CON_PANEL, WEEK_CON_PANEL};
		JComboBox cb = new JComboBox(comboOpts);
        cb.setEditable(false);
        cb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, (String)evt.getItem());
            }
        });
        comboPane.add(cb);
        
        singleCon.add(new JButton("single"));
        weeklyCon.add(new JButton("weekly"));
        
        cards.add(singleCon, SINGLE_CON_PANEL);
        cards.add(weeklyCon, WEEK_CON_PANEL);
        constDialog.add(comboPane, BorderLayout.PAGE_START);
        constDialog.add(cards, BorderLayout.CENTER);
        
		return constDialog;
	}
	
	
	
}
