
package scheduler;
import java.awt.*; 
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel; 

  

// class extends JFrame 
public class PeoplePeeker extends JSplitPane { 
   
	JComboBox<String> searchBox;
	ArrayList<Employee> empls;
	ArrayList<Boolean> emplLoaded;
	
	
	JPanel peopleList;
	JTabbedPane peopleData;
	JLabel listLabel, dataLabel; 
	
	ListSelectionModel listSelModel;
	private static final long serialVersionUID = 1L;
 
    public PeoplePeeker(ArrayList<Employee> empls) {
   
    	//would have a loop to create a tab for each person
    	this.empls = empls;
    	this.emplLoaded = new ArrayList<Boolean>();
    	for (int i = 0; i < empls.size(); i++) {
    		this.emplLoaded.add(false);
    	}
    	
    	this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    	peopleList = new JPanel();
    	
    	loadPersonList(empls);
    	peopleData = new JTabbedPane();
    	JLabel dataLbl = new JLabel("Select a name and/or click on their tab to view their info here");
    	peopleData.add(dataLbl);
    	
    	this.setLeftComponent(peopleList);
    	this.setRightComponent(peopleData);
    	
    	
    	/*//code to add tabs
    	JTextArea ta=new JTextArea(200,200);  
        JPanel p1=new JPanel();  
        p1.add(ta);  
        JPanel p2=new JPanel();  
        JPanel p3=new JPanel();  
        JTabbedPane tp=new JTabbedPane();  
        //tp.setBounds(50,50,200,200);  
        
        //text for the tabs
        tp.add("main",p1);  
        tp.add("visit",p2);  
        tp.add("help",p3);    
        tp.setVisible(true);
        //add tabs to view
        add(tp);  
        setSize(100,100);  
        setLayout(null);  
        setVisible(true);  */
    	
    
    } 
    
    
    void loadPersonList(ArrayList<Employee> empls) {
    	String [] col = {"Employees"};
    	DefaultTableModel dtm = new DefaultTableModel(null, col) {
    		@Override
    		public boolean isCellEditable(int row, int col) {
    			return false;
    		}
    	};
    	JTable peopleTbl = new JTable(dtm);
    	dtm.setRowCount(empls.size());
    	for (int i = 0; i < empls.size(); i++) {
    		dtm.setValueAt(empls.get(i).getName(), i, 0);
    	}
    	listSelModel = peopleTbl.getSelectionModel();
    	listSelModel.addListSelectionListener(new SharedListSelectionHandler());
    	peopleTbl.setSelectionModel(listSelModel);
    	
    	peopleList.add(peopleTbl);
    	peopleList.setVisible(true);
    	
    }
    
    
    void loadPersonData(Employee empl) {
    	
    	String[] cols = {"Data", "Value"};
    	DefaultTableModel dtm = new DefaultTableModel(null, cols);
    	JTable DataTbl = new JTable(dtm);

    	dtm.setRowCount(10);
    	
    	dtm.setValueAt("ID", 0, 0);
    	dtm.setValueAt("Name", 1, 0);
    	dtm.setValueAt("Moonlighter", 2, 0);
    	dtm.setValueAt("Hour Count", 3, 0);
    	dtm.setValueAt("Shift Count", 4, 0);

    	dtm.setValueAt(empl.getEmpl_id(), 0, 1);
    	dtm.setValueAt(empl.getName(), 1, 1);
    	dtm.setValueAt((empl.isMoonlighter() ? "True" : "False") , 2, 1);
    	dtm.setValueAt(empl.getShift_count(), 4, 1);
    	
    	peopleData.addTab(empl.getName(), DataTbl);
    }
    
   
    class SharedListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) { 
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            if (lsm.isSelectionEmpty()) {
                //output.append(" <none>");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                    	if (!emplLoaded.get(i)) {
                    		loadPersonData(empls.get(i));
                    		emplLoaded.set(i, true);
                    	}
                    	 
                    }
                }
            }
            
        }
    }
    
    
     //Main Method 
    /*public static void main(String args[]) 
    { 
   
        // calling the constructor 
        new PeoplePeeker(); 
    } */
}