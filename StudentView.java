import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class StudentView extends JFrame implements ActionListener {
    private int width = 800;
    private int height = 350;
    private int user_id;

    private ButtonGroup group;
    private JRadioButton viewStaffButton;
    private JRadioButton viewFacultyButton;
    private JRadioButton viewGradesButton;
    private JRadioButton viewCoursesButton;

    private JTextField cidField;
    private JButton enrollButton;

    private JPanel tablePanel;

    private JDBC database;

    public StudentView(int id, JDBC database) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setTitle("Student View");
        this.setLayout(new BorderLayout());
        this.database = database;

        //welcome message
        String name = database.getName("student", id);
        this.user_id = id;
        JLabel welcomeText = new JLabel("Welcome, " + name);
        JPanel welcomePanel = new JPanel(new FlowLayout());
        welcomePanel.add(welcomeText);
        this.add(welcomePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));

        //add radio buttons
        group = new ButtonGroup();
        viewStaffButton = new JRadioButton("View Staff");
        viewFacultyButton = new JRadioButton("View Instructors");
        viewGradesButton = new JRadioButton("View Grades");
        viewCoursesButton = new JRadioButton("View Courses");
        viewGradesButton.setSelected(true);
        viewStaffButton.addActionListener(this);
        viewFacultyButton.addActionListener(this);
        viewGradesButton.addActionListener(this);
        viewCoursesButton.addActionListener(this);
        group.add(viewStaffButton);
        group.add(viewFacultyButton);
        group.add(viewGradesButton);
        group.add(viewCoursesButton);
        JPanel radioPanel = new JPanel(new GridLayout(2, 2));
        JPanel viewStaffPanel = new JPanel(new FlowLayout());
        JPanel viewTeachersPanel = new JPanel(new FlowLayout());
        JPanel viewGradesPanel = new JPanel(new FlowLayout());
        JPanel viewCoursesPanel = new JPanel(new FlowLayout());
        viewStaffPanel.add(viewStaffButton);
        viewTeachersPanel.add(viewFacultyButton);
        viewGradesPanel.add(viewGradesButton);
        viewCoursesPanel.add(viewCoursesButton);
        radioPanel.add(viewStaffPanel);
        radioPanel.add(viewTeachersPanel);
        radioPanel.add(viewGradesPanel);
        radioPanel.add(viewCoursesPanel);
        centerPanel.add(radioPanel);

        //add course enroll textfield & button
        JLabel cidLabel = new JLabel("Course ID: ");
        cidField = new JTextField();
        cidField.setPreferredSize(new Dimension(125, 25));
        enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(this);
        JPanel enrollPanel = new JPanel(new FlowLayout());
        enrollPanel.add(cidLabel);
        enrollPanel.add(cidField);
        enrollPanel.add(enrollButton);
        centerPanel.add(enrollPanel);

        tablePanel = new JPanel(new FlowLayout());
        tablePanel.add(gradesTable());
        this.add(tablePanel, BorderLayout.SOUTH);

        this.add(centerPanel, BorderLayout.CENTER);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //change table to show grades when 'view grades' radio button is selected
        if(e.getSource() == viewGradesButton) {
            tablePanel.removeAll();
            tablePanel.revalidate();
            tablePanel.repaint();

            tablePanel.add(gradesTable());
        }

        //change table to show staff members when 'view staff' radio button is selected
        else if(e.getSource() == viewStaffButton) {
            tablePanel.removeAll();
            tablePanel.revalidate();
            tablePanel.repaint();

            tablePanel.add(staffTable());
        }

        //change table to show faculty members when 'view instructors' radio button is selected
        else if(e.getSource() == viewFacultyButton) {
            tablePanel.removeAll();
            tablePanel.revalidate();
            tablePanel.repaint();

            tablePanel.add(facultyTable());
        }
        
        //change table to show courses when 'view courses' radio button is selected
        else if(e.getSource() == viewCoursesButton) {
            tablePanel.removeAll();
            tablePanel.revalidate();
            tablePanel.repaint();

            tablePanel.add(coursesTable());
        }


        else if(e.getSource() == enrollButton) {
            int rowsAffected = database.enrollStudent(user_id, cidField.getText());

            if(rowsAffected == 0) {
                cidField.setText("Enroll failed.");
            } else {
                cidField.setText("Enroll success!");

                viewGradesButton.setSelected(true);
                
                tablePanel.removeAll();
                tablePanel.revalidate();
                tablePanel.repaint();
    
                tablePanel.add(gradesTable());
            }
        }
    }

    private JScrollPane gradesTable() {
        String[] columnNames = {"Instructor", "CID", "Course", "Exam 1", "Exam 2", "Exam 3"};
        Object[][] data = database.fetchGrades(user_id);

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(750, 100));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    private JScrollPane staffTable() {
        String[] columnNames = {"Staff Name", "Department"};
        Object[][] data = database.fetchStaff();

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(300, 100));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    private JScrollPane facultyTable() {
        String[] columnNames = {"Instructor Name", "Department"};
        Object[][] data = database.fetchStaff();

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(300, 100));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    private JScrollPane coursesTable() {
        String[] columnNames = {"CID", "Course Name", "Instructor"};
        Object[][] data = database.fetchCourses();

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(600, 100));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }
}
