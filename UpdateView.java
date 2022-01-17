import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateView extends JFrame implements ActionListener{
    private JPanel categoryContents;

    private JTextField nameField;
    private JTextField majorField;
    private JComboBox<String> levelBox;
    private JTextField ageField;
    private JComboBox<String> deptBox;

    private JButton updateButton;

    private JDBC database;
    private String category;
    private int id;
    StaffView parentWindow;

    public UpdateView(String category, int id, StaffView parentWindow, JDBC database) {
        this.setSize(300, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.database = database;
        this.category = category;
        this.id = id;
        this.parentWindow = parentWindow;


        categoryContents = new JPanel(new GridLayout(4, 1));
        JLabel title = new JLabel();
        JPanel titlePanel = new JPanel(new FlowLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        if(category.equals("student")) {
            createStudentPanel();
            title = new JLabel("Update Student: " + id);
            this.setTitle("Update Student: " + id);
            updateButton = new JButton("Update Student");
        } else if(category.equals("faculty") || category.equals("staff")) {
            createFacStaffPanel();

            if(category.equals("faculty")) {
                title = new JLabel("Update Faculty: " + id);
                this.setTitle("Update Faculty: " + id);
                updateButton = new JButton("Update Faculty");
            } 
            else if(category.equals("staff")) {
                title = new JLabel("Update Staff: " + id);
                this.setTitle("Update Staff: " + id);
                updateButton = new JButton("Update Staff");
            }
        }

        updateButton.addActionListener(this);
        buttonPanel.add(updateButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        this.add(categoryContents, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void createStudentPanel() {
        createNameInput();
        createMajorInput();
        createLevelInput();
        createAgeInput();
    }

    private void createFacStaffPanel() {
        createNameInput();
        createDeptInput();
    }

    private void createNameInput() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel nameLabel = new JLabel("Name: ");
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(125, 25));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        categoryContents.add(namePanel);
    }

    private void createMajorInput() {
        JPanel majorPanel = new JPanel();
        majorPanel.setLayout(new FlowLayout());
        JLabel majorLabel = new JLabel("Major: ");
        majorField = new JTextField();
        majorField.setPreferredSize(new Dimension(125, 25));
        majorPanel.add(majorLabel);
        majorPanel.add(majorField);
        categoryContents.add(majorPanel);
    }

    private void createLevelInput() {
        JPanel levelWrapper = new JPanel();
        levelWrapper.setLayout(new FlowLayout());
        JLabel levelLabel = new JLabel("Level: ");
        levelWrapper.add(levelLabel);
        String[] level = {"", "Freshman", "Sophomore", "Junior", "Senior"};
        levelBox = new JComboBox<String>(level);
        levelWrapper.add(levelBox);
        categoryContents.add(levelWrapper);
    }

    private void createAgeInput() {
        JPanel agePanel = new JPanel();
        agePanel.setLayout(new FlowLayout());
        JLabel ageLabel = new JLabel("Age: ");
        ageField = new JTextField();
        ageField.setPreferredSize(new Dimension(125, 25));
        agePanel.add(ageLabel);
        agePanel.add(ageField);
        categoryContents.add(agePanel);
    }

    private void createDeptInput() {
        JPanel deptPanel = new JPanel();
        deptPanel.setLayout(new FlowLayout());
        JLabel deptLabel = new JLabel("Dept: ");
        deptPanel.add(deptLabel);
        String[] depts = {"", "CompSci", "English", "Math", "Biology", "Chemistry", 
            "Theater", "Linguistics", "Psychology", "Law", "Engineering"};
        deptBox = new JComboBox<String>(depts);
        deptPanel.add(deptBox);
        categoryContents.add(deptPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == updateButton) {
            int rowsAffected = 0;

            if(category.equals("student")) {
                String[] values = {nameField.getText(), majorField.getText(), 
                    (String)levelBox.getSelectedItem(), ageField.getText()};

                rowsAffected = database.updateStudent(id, values);
            }
            else if(category.equals("faculty")) {
                String[] values = {nameField.getText(), (String)deptBox.getSelectedItem()};

                rowsAffected = database.updateFaculty(id, values);
            }
            else if(category.equals("staff")) {
                String[] values = {nameField.getText(), (String)deptBox.getSelectedItem()};

                rowsAffected = database.updateStaff(id, values);
            }

            if(rowsAffected < 1) {
                parentWindow.manipulationFail("UPDATE FAILED");
            } else {
                parentWindow.manipulationSuccess("UPDATE SUCCESSFUL!");
            }

            this.dispose();
        }
    }
}
