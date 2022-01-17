import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class LaunchWindow extends JFrame implements ActionListener{
    ButtonGroup categories;
    JRadioButton studentButton;
    JRadioButton staffButton;
    JRadioButton facultyButton;

    JTextField idField;
    JButton submit;

    JPanel errorPanel;

    JDBC database;

    public LaunchWindow() {
        database = new JDBC();
        this.setTitle("University Database");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(4, 1));
        this.setSize(300, 200);
        this.setResizable(false);

        JPanel welcomePanel = new JPanel(new FlowLayout());
        JLabel welcomeText = new JLabel("Welcome to University Database!");
        welcomePanel.add(welcomeText);
        this.add(welcomePanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        studentButton = new JRadioButton("Student");
        staffButton = new JRadioButton("Staff");
        facultyButton = new JRadioButton("Faculty");
        categories = new ButtonGroup();
        categories.add(studentButton);
        categories.add(staffButton);
        categories.add(facultyButton);
        buttonPanel.add(studentButton);
        buttonPanel.add(staffButton);
        buttonPanel.add(facultyButton);
        this.add(buttonPanel);

        JPanel submitPanel = new JPanel(new FlowLayout());
        JLabel idText = new JLabel("ID: ");
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(100, 25));
        submit = new JButton("Submit");
        submit.addActionListener(this);
        submitPanel.add(idText);
        submitPanel.add(idField);
        submitPanel.add(submit);
        this.add(submitPanel);

        errorPanel = new JPanel(new FlowLayout());
        this.add(errorPanel);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit) {
            //clear JPanel errorPanel
            errorPanel.removeAll();
            errorPanel.revalidate();
            errorPanel.repaint();

            String categoryStr = "";

            if(studentButton.isSelected()) {
                categoryStr = "student";
            }
            else if(staffButton.isSelected()) {
                categoryStr = "staff";
            }
            else if(facultyButton.isSelected()) {
                categoryStr = "faculty";
            }
            else {
                error("Must select category");
            }

            try {
                int id = database.searchByID(categoryStr, Integer.parseInt(idField.getText()));

                if(id == -1) {
                    error("User does not exist");
                }
                else if(categoryStr.equals("student")) {
                    new StudentView(id, database);
                }
                else if(categoryStr.equals("staff")) {
                    new StaffView(id, database);
                }
                else if(categoryStr.equals("faculty")) {
                    new FacultyView(id, database);
                }
            } catch(NumberFormatException err) {
                error("Invalid input for ID");
            }

        }
    }

    private void error(String message) {
        JLabel error = new JLabel(message);
        error.setForeground(Color.RED);
        errorPanel.add(error);
    }
}
    

