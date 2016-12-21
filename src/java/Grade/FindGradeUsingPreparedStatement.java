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
public class FindGradeUsingPreparedStatement extends JFrame {
    
    private JTextField jtfid = new JTextField(9);
    private JTextField jtfcourseid = new JTextField(5);
    private JButton jbtShowGrade = new JButton("Show Grade - PS");
    
    //PreparedStatement for executing queries
    private PreparedStatement preparedstatement;
    
    public FindGradeUsingPreparedStatement() {
        //Initialise database connection and create a statement object
        initializeDB();
        
        jbtShowGrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtShowGrade_actionPerformed(e);
            }
        });
        
        JPanel jPanel1 = new JPanel();
        jPanel1.add(new JLabel("Student Id"));
        jPanel1.add(jtfid);
        jPanel1.add(new JLabel("Course Id"));
        jPanel1.add(jtfcourseid);
        jPanel1.add(jbtShowGrade);
        
        add(jPanel1, BorderLayout.CENTER);
    }
    private void initializeDB() {
        try {
            //Load the JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded");
            
            //Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/registration", "root", "Peppy@!05");
            System.out.println("Dtabase connected");
            
            String queryString = "select firstname, lastname, subjectId, courseNumber, grade "
                                    +"from student, enrollment, course"
                                    +"where student.id=? and enrollment.courseId=?"
                                    +"and enrollment.courseId=course.courseId"
                                    +"and enrollment.id=student.id";
            //create a statement
            preparedstatement = connection.prepareStatement(queryString);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    private void jbtShowGrade_actionPerformed(ActionEvent e) {
        String studentId = jtfid.getText();
        String courseId = jtfcourseid.getText();
        try {
            preparedstatement.setString(1, studentId);
            preparedstatement.setString(2, courseId);
            ResultSet rset = preparedstatement.executeQuery();
            
            if(rset.next()) {
                String firstName = rset.getString(1);
                String lastName = rset.getString(2);
                String subject = rset.getString(3);
                String number = rset.getString(4);
                String grade = rset.getString(5);
                
                //Display result in a dialog box
                JOptionPane.showMessageDialog(null, firstName + " " + lastName
                                + "'s grade on course " + subject + number + " is " + grade);
            }
            else {
                //Display result in a dialog box
                JOptionPane.showMessageDialog(null, "Not Found");
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        FindGradeUsingPreparedStatement frame = new FindGradeUsingPreparedStatement();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Find Grade Using Prepared Statement");
        frame.setSize(380, 120);
        frame.setLocationRelativeTo(null);//center the frame
        frame.setVisible(true);
    }
}
