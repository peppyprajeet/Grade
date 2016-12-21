/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grade;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author praje
 */
public class FindGrade extends JApplet{
    private JTextField jtfid = new JTextField(9);
    private JTextField jtfcourseid = new JTextField(5);
    private JButton jbtShowGrade = new JButton("Show Grade");
    
    //Statement fpr executing Query
    private Statement stmt;
    
    //Initialise the Applet
    public void init() {
        //initilaise the databse connection and create a statement object
        initializeDB();
        
        jbtShowGrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtShowGrade_actionPerformed(e);
            }
        });
        
        JPanel jPanel1 = new JPanel();
        jPanel1.add(new JLabel("id"));
        jPanel1.add(jtfid);
        jPanel1.add(new JLabel("Course Id"));
        jPanel1.add(jtfcourseid);
        jPanel1.add(jbtShowGrade);
        
        add(jPanel1, BorderLayout.NORTH);
    }
    private void initializeDB() {
        try {
            //Load the JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded");
            
            //Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/registration", "root", "Peppy@!05");
            System.out.println("Dtabase connected");
            
            //Create a Statement
            stmt = connection.createStatement();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void jbtShowGrade_actionPerformed(ActionEvent e) {
        String ssn = jtfid.getText();
        String courseid = jtfcourseid.getText();
        
        try {
            String queryString = "select grade from enrollment where courseId"+"="+courseid+"' and "+" id = '"+ssn+"'";
            
            ResultSet rset = stmt.executeQuery(queryString);
            
            if(rset.next()) {
                String grade = rset.getString(1);
                
                //Display result in a dialog box
                JOptionPane.showMessageDialog(null,"Grade is :"+ grade);
            }else{
                //Display result in a dialog box
                JOptionPane.showMessageDialog(null, "Not found");
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        FindGrade applet = new FindGrade();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Find Grades");
        frame.getContentPane().add(applet, BorderLayout.CENTER);
        applet.init();
        applet.start();
        frame.setSize(380, 80);
        frame.setLocationRelativeTo(null); //center the frame
        frame.setVisible(true);
    }
}
