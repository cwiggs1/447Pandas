import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.awt.BorderLayout; 
import java.awt.Frame; 
import java.awt.Button; 
import java.awt.Color; 
   
// class extends JFrame 
public class PeoplePeeker extends JFrame { 
   
    // Constructor of BorderDemo class. 
    public PeoplePeeker() {
   
    	//would have a loop to create a tab for each person
    	
    	//code to add tabs
    	JTextArea ta=new JTextArea(200,200);  
        JPanel p1=new JPanel();  
        p1.add(ta);  
        JPanel p2=new JPanel();  
        JPanel p3=new JPanel();  
        JTabbedPane tp=new JTabbedPane();  
        tp.setBounds(50,50,200,200);  
        
        //text for the tabs
        tp.add("main",p1);  
        tp.add("visit",p2);  
        tp.add("help",p3);    
        
        //add tabs to view
        add(tp);  
        setSize(400,400);  
        setLayout(null);  
        setVisible(true);  
    	
    	//ignore this block
    	/*
        // set the layout 
        setLayout(new BorderLayout()); 
   
        // set the background 
        setBackground(Color.red); 
   
        // creates Button (btn1) 
        Button btn1 = new Button("Calendar"); 
   
        // creates Button (btn2) 
        Button btn2 = new Button("Edit"); 
   
        // creates Button (btn3) 
        Button btn3 = new Button("People Peeker"); 
   
        // creates Button (btn4) 
        Button btn4 = new Button("Schedule"); 
   
        // creates Button (btn5) 
        Button btn5 = new Button("Other"); 
   
        // Adding JButton "btn1" on JFrame. 
        add(btn1, "North"); 
   
        // Adding JButton "btn2" on JFrame. 
        add(btn2, "South"); 
   
        // Adding JButton "btn3" on JFrame. 
        add(btn3, "East"); 
   
        // Adding JButton "btn4" on JFrame. 
        add(btn4, "West"); 
   
        // Adding JButton "btn5" on JFrame. 
        add(btn5, "Center"); 
   
        // function to set the title 
        setTitle("Learning a Border Layout"); 
   
        // Function to set size of JFrame. 
        setSize(350, 300); 
   
        // Function to set visible status of JFrame 
        setVisible(true); */
    } 
   
    // Main Method 
    public static void main(String args[]) 
    { 
   
        // calling the constructor 
        new PeoplePeeker(); 
    } 
}