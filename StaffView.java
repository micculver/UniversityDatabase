import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class StaffView extends JFrame implements ActionListener {
    private JButton addStudent;
    private JButton addFaculty;
    private JButton addStaff;

    private JButton deleteStudent;
    private JButton deleteFaculty;
    private JButton deleteStaff;

    private JButton updateStudent;
    private JButton updateFaculty;
    private JButton updateStaff;

    private JButton searchButton;
    private JComboBox<String> categoryBox;

    private int width;
    private int height;

    private int uid;
    private JDBC database;

    public StaffView(int uid, JDBC database) {
        this.uid = uid;
        this.database = database;
        width = 850;
        height = 700;

        this.setTitle("Staff View");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(null);

        createWelcomePanel();
        createActionsPanel();
        createResultsPanel();

        this.setVisible(true);
        this.setResizable(false);
    }

    private void createWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout());
        welcomePanel.setBounds(0, 0, width, 100);

        String name = database.getName("staff", uid);
        JLabel welcomeLabel = new JLabel("Welcome, " + name + "!");
        welcomePanel.add(welcomeLabel);

        this.add(welcomePanel);
    }

    private void createActionsPanel() {
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridLayout(1,2));
        actionsPanel.setBounds(0, 100, width, 300);

        createManipulationPanel(actionsPanel);
        createSearchPanel(actionsPanel);

        this.add(actionsPanel);
    }

    JPanel resultsPanel;

    private void createResultsPanel() {
        resultsPanel = new JPanel(new FlowLayout());
        resultsPanel.setBounds(0, 400, width, height - 400);
        defaultStudentTable();
        this.add(resultsPanel);
    }

    private void createTable(String category, String[] queryAttributes) {
        JTable table;
        String[] columnNames = {};

        Object[][] data = {};

        if(category.equals("student")) {
            String[] attributes = {"ID", "Name", "Major", "Level", "Age"};
            columnNames = attributes;
            data = database.searchStudent(queryAttributes);
        }
        else if(category.equals("faculty") || category.equals("staff")) {
            String[] attributes = {"ID", "Name", "Department"};
            columnNames = attributes;

            if(category.equals("faculty")) {
                data = database.searchFaculty(queryAttributes);
            } 
            else if(category.equals("staff")) {
                data = database.searchStaff(queryAttributes);
            }
        }

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(width - 100, 220));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        resultsPanel.add(scrollPane);
    }

    private void defaultStudentTable() {
        String[] columnNames = {"ID", "Name", "Major", "Level", "Age"};
        Object[][] data = database.searchAllStudent();

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(width - 100, 220));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        resultsPanel.add(scrollPane);
    }

    private void defaultFacultyTable() {
        String[] columnNames = {"ID", "Name", "Department"};
        Object[][] data = database.searchAllFaculty();

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(width - 100, 220));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        resultsPanel.add(scrollPane);
    }

    private void defaultStaffTable() {
        String[] columnNames = {"ID", "Name", "Department"};
        Object[][] data = database.searchAllStaff();

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(width - 100, 220));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        resultsPanel.add(scrollPane);
    }

    private JPanel manipulationInteract;
    private JPanel manipulationPanel;

    private void createManipulationPanel(JPanel actionsPanel) {
        manipulationPanel = new JPanel();
        manipulationPanel.setLayout(new GridLayout(2, 1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        int buttonWidth = 125;
        int buttonHeight = 25;

        //create add buttons
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout());
        addStudent = new JButton("Add Student");
        addFaculty = new JButton("Add Faculty");
        addStaff = new JButton("Add Staff");
        addStudent.addActionListener(this);
        addFaculty.addActionListener(this);
        addStaff.addActionListener(this);
        addStudent.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addFaculty.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addStaff.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addPanel.add(addStudent);
        addPanel.add(addFaculty);
        addPanel.add(addStaff);
        buttonPanel.add(addPanel);

        //create delete buttons
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new FlowLayout());
        deleteStudent = new JButton("Delete Student");
        deleteFaculty = new JButton("Delete Faculty");
        deleteStaff = new JButton("Delete Staff");
        deleteStudent.addActionListener(this);
        deleteFaculty.addActionListener(this);
        deleteStaff.addActionListener(this);
        deleteStudent.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        deleteFaculty.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        deleteStaff.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        deletePanel.add(deleteStudent);
        deletePanel.add(deleteFaculty);
        deletePanel.add(deleteStaff);
        buttonPanel.add(deletePanel);

        //create update buttons
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new FlowLayout());
        updateStudent = new JButton("Update Student");
        updateFaculty = new JButton("Update Faculty");
        updateStaff = new JButton("Update Staff");
        updateStudent.addActionListener(this);
        updateFaculty.addActionListener(this);
        updateStaff.addActionListener(this);
        updateStudent.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        updateFaculty.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        updateStaff.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        updatePanel.add(updateStudent);
        updatePanel.add(updateFaculty);
        updatePanel.add(updateStaff);
        buttonPanel.add(updatePanel);

        manipulationPanel.add(buttonPanel);
        manipulationInteract = new JPanel(new BorderLayout());
        manipulationPanel.add(manipulationInteract);
        
        actionsPanel.add(manipulationPanel);
    }

    private JPanel manipulateScreen;
    private JTextField addNameField;
    private JTextField addMajorField;
    private JComboBox<String> addLevelBox;
    private JTextField addAgeField;
    private JButton addStudentButton;
    private JButton addFacultyButton;
    private JButton addStaffButton;

    private void createAddStudentPanel() {
        JLabel title = new JLabel("Add Student");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new GridLayout(2, 2));

        JPanel addNamePanel = new JPanel(new FlowLayout());
        JLabel addNameLabel = new JLabel("Name: "); 
        addNamePanel.add(addNameLabel);
        addNameField = new JTextField();
        addNameField.setPreferredSize(new Dimension(125, 25));
        addNamePanel.add(addNameField);

        JPanel addMajorPanel = new JPanel(new FlowLayout());
        JLabel addMajorLabel = new JLabel("Major: "); 
        addMajorPanel.add(addMajorLabel);
        addMajorField = new JTextField();
        addMajorField.setPreferredSize(new Dimension(125, 25));
        addMajorPanel.add(addMajorField);

        JPanel addLevelPanel = new JPanel(new FlowLayout());
        JLabel addLevelLabel = new JLabel("Level: "); 
        addLevelPanel.add(addLevelLabel);
        String[] levels = {"", "Freshman", "Sophomore", "Junior", "Senior"};
        addLevelBox = new JComboBox<String>(levels);
        addLevelPanel.add(addLevelBox);

        JPanel addAgePanel = new JPanel(new FlowLayout());
        JLabel addAgeLabel = new JLabel("Age: ");
        addAgePanel.add(addAgeLabel);
        addAgeField = new JTextField();
        addAgeField.setPreferredSize(new Dimension(125, 25));
        addAgePanel.add(addAgeField);

        JPanel addButtonPanel = new JPanel(new FlowLayout());
        addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(this);
        addButtonPanel.add(addStudentButton);
        manipulationInteract.add(addButtonPanel, BorderLayout.SOUTH);

        manipulateScreen.add(addNamePanel);
        manipulateScreen.add(addMajorPanel);
        manipulateScreen.add(addLevelPanel);
        manipulateScreen.add(addAgePanel);

        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
    }

    JComboBox<String> addDeptBox;

    private void createAddStaffPanel() {
        JLabel title = new JLabel("Add Staff Member");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new GridLayout(2, 2));

        JPanel addNamePanel = new JPanel(new FlowLayout());
        JLabel addNameLabel = new JLabel("Name: "); 
        addNamePanel.add(addNameLabel);
        addNameField = new JTextField();
        addNameField.setPreferredSize(new Dimension(125, 25));
        addNamePanel.add(addNameField);

        JPanel addDeptPanel = new JPanel(new FlowLayout());
        JLabel addDeptLabel = new JLabel("Department: ");
        addDeptPanel.add(addDeptLabel);
        String[] depts = {"", "CompSci", "English", "Math", "Biology", "Chemistry", 
                    "Theater", "Linguistics", "Psychology", "Law", "Engineering"};
        addDeptBox = new JComboBox<String>(depts);
        addDeptPanel.add(addDeptBox);

        JPanel addButtonPanel = new JPanel(new FlowLayout());
        addStaffButton = new JButton("Add Staff");
        addStaffButton.addActionListener(this);
        addButtonPanel.add(addStaffButton);
        manipulationInteract.add(addButtonPanel, BorderLayout.SOUTH);

        manipulateScreen.add(addNamePanel);
        manipulateScreen.add(addDeptPanel);

        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
    }

    private void createAddFacultyPanel() {
        JLabel title = new JLabel("Add Faculty Member");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new GridLayout(2, 2));

        JPanel addNamePanel = new JPanel(new FlowLayout());
        JLabel addNameLabel = new JLabel("Name: "); 
        addNamePanel.add(addNameLabel);
        addNameField = new JTextField();
        addNameField.setPreferredSize(new Dimension(125, 25));
        addNamePanel.add(addNameField);

        JPanel addDeptPanel = new JPanel(new FlowLayout());
        JLabel addDeptLabel = new JLabel("Department: ");
        addDeptPanel.add(addDeptLabel);
        String[] depts = {"", "CompSci", "English", "Math", "Biology", "Chemistry", 
                    "Theater", "Linguistics", "Psychology", "Law", "Engineering"};
        addDeptBox = new JComboBox<String>(depts);
        addDeptPanel.add(addDeptBox);

        JPanel addButtonPanel = new JPanel(new FlowLayout());
        addFacultyButton = new JButton("Add Faculty");
        addFacultyButton.addActionListener(this);
        addButtonPanel.add(addFacultyButton);
        manipulationInteract.add(addButtonPanel, BorderLayout.SOUTH);

        manipulateScreen.add(addNamePanel);
        manipulateScreen.add(addDeptPanel);

        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
    }

    JTextField manipulationIdField;
    JButton deleteStudentButton;
    JButton deleteFacultyButton;
    JButton deleteStaffButton;

    private void createDeleteStudentPanel() {
        JLabel title = new JLabel("Delete Student");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new FlowLayout());

        JLabel deleteStudent = new JLabel("Delete Student: ");
        manipulateScreen.add(deleteStudent);
        manipulationIdField = new JTextField();
        manipulationIdField.setPreferredSize(new Dimension(125, 25));
        manipulateScreen.add(manipulationIdField);
        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
        
        JPanel deleteButtonPanel = new JPanel(new FlowLayout());
        deleteStudentButton = new JButton("Delete Student");
        deleteStudentButton.addActionListener(this);
        deleteButtonPanel.add(deleteStudentButton);
        manipulationInteract.add(deleteButtonPanel, BorderLayout.SOUTH);
    }

    private void createDeleteFacultyPanel() {
        JLabel title = new JLabel("Delete Faculty");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new FlowLayout());

        JLabel deleteFaculty = new JLabel("Delete Faculty: ");
        manipulateScreen.add(deleteFaculty);
        manipulationIdField = new JTextField();
        manipulationIdField.setPreferredSize(new Dimension(125, 25));
        manipulateScreen.add(manipulationIdField);
        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
        
        JPanel deleteButtonPanel = new JPanel(new FlowLayout());
        deleteFacultyButton = new JButton("Delete Faculty");
        deleteFacultyButton.addActionListener(this);
        deleteButtonPanel.add(deleteFacultyButton);
        manipulationInteract.add(deleteButtonPanel, BorderLayout.SOUTH);
    }

    private void createDeleteStaffPanel() {
        JLabel title = new JLabel("Delete Staff");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new FlowLayout());

        JLabel deleteStaff = new JLabel("Delete Staff: ");
        manipulateScreen.add(deleteStaff);
        manipulationIdField = new JTextField();
        manipulationIdField.setPreferredSize(new Dimension(125, 25));
        manipulateScreen.add(manipulationIdField);
        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
        
        JPanel deleteButtonPanel = new JPanel(new FlowLayout());
        deleteStaffButton = new JButton("Delete Staff");
        deleteStaffButton.addActionListener(this);
        deleteButtonPanel.add(deleteStaffButton);
        manipulationInteract.add(deleteButtonPanel, BorderLayout.SOUTH);
    }

    JButton updateStudentButton;
    JButton updateFacultyButton;
    JButton updateStaffButton;

    private void createUpdateStudentPanel() {
        JLabel title = new JLabel("Update Student");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new FlowLayout());

        JLabel updateStudent = new JLabel("Update Student: ");
        manipulateScreen.add(updateStudent);
        manipulationIdField = new JTextField();
        manipulationIdField.setPreferredSize(new Dimension(125, 25));
        manipulateScreen.add(manipulationIdField);
        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
        
        JPanel updateButtonPanel = new JPanel(new FlowLayout());
        updateStudentButton = new JButton("Update Student");
        updateStudentButton.addActionListener(this);
        updateButtonPanel.add(updateStudentButton);
        manipulationInteract.add(updateButtonPanel, BorderLayout.SOUTH);
    }

    private void createUpdateFacultyPanel() {
        JLabel title = new JLabel("Update Faculty");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new FlowLayout());

        JLabel updateFaculty = new JLabel("Update Faculty: ");
        manipulateScreen.add(updateFaculty);
        manipulationIdField = new JTextField();
        manipulationIdField.setPreferredSize(new Dimension(125, 25));
        manipulateScreen.add(manipulationIdField);
        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
        
        JPanel updateButtonPanel = new JPanel(new FlowLayout());
        updateFacultyButton = new JButton("Update Faculty");
        updateFacultyButton.addActionListener(this);
        updateButtonPanel.add(updateFacultyButton);
        manipulationInteract.add(updateButtonPanel, BorderLayout.SOUTH);
    }

    private void createUpdateStaffPanel() {
        JLabel title = new JLabel("Update Staff");
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        manipulationInteract.add(titlePanel, BorderLayout.NORTH);

        manipulateScreen = new JPanel(new FlowLayout());

        JLabel updateStaff = new JLabel("Update Staff: ");
        manipulateScreen.add(updateStaff);
        manipulationIdField = new JTextField();
        manipulationIdField.setPreferredSize(new Dimension(125, 25));
        manipulateScreen.add(manipulationIdField);
        manipulationInteract.add(manipulateScreen, BorderLayout.CENTER);
        
        JPanel updateButtonPanel = new JPanel(new FlowLayout());
        updateStaffButton = new JButton("Update Staff");
        updateStaffButton.addActionListener(this);
        updateButtonPanel.add(updateStaffButton);
        manipulationInteract.add(updateButtonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel categoryContents;
    private JPanel searchPanel;

    private void createSearchPanel(JPanel actionsPanel) {
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());

        //create combo box of categories
        JPanel categoryWrapper = new JPanel();
        categoryWrapper.setLayout(new FlowLayout());
        JLabel categoryLabel = new JLabel("Category: ");
        categoryWrapper.add(categoryLabel);
        String[] categories = {"Student", "Faculty", "Staff"};
        categoryBox = new JComboBox<String>(categories);
        categoryBox.addActionListener(this);
        categoryWrapper.add(categoryBox);
        searchPanel.add(categoryWrapper, BorderLayout.NORTH);

        //create the search button
        searchButton = new JButton("Search User");
        searchButton.addActionListener(this);
        JPanel searchWrapper = new JPanel();
        searchWrapper.setLayout(new FlowLayout());
        searchWrapper.add(searchButton);
        searchPanel.add(searchWrapper, BorderLayout.SOUTH);

        //dependent on which category is currently selected
        categoryContents = new JPanel(new GridLayout(5, 1));
        searchPanel.add(categoryContents, BorderLayout.CENTER);

        setSearchToStudent();

        actionsPanel.add(searchPanel);
    }

    JTextField idField;
    JTextField nameField;
    JTextField majorField;
    JComboBox<String> levelBox;
    JTextField ageField;
    JComboBox<String> deptBox;

    private void setSearchToStudent() {
        createUidInput();
        createNameInput();
        createMajorInput();
        createLevelInput();
        createAgeInput();
    }

    private void setSearchToFacStaff() {
        createUidInput();
        createNameInput();
        createDeptInput();
    }   
    
    private void createUidInput() {
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout());
        JLabel idLabel = new JLabel("ID: ");
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(125, 25));
        idPanel.add(idLabel);
        idPanel.add(idField);
        categoryContents.add(idPanel);
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
        String[] level = {"All", "Freshman", "Sophomore", "Junior", "Senior"};
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
        String[] depts = {"All", "CompSci", "English", "Math", "Biology", "Chemistry", 
            "Theater", "Linguistics", "Psychology", "Law", "Engineering"};
        deptBox = new JComboBox<String>(depts);
        deptPanel.add(deptBox);
        categoryContents.add(deptPanel);
    }

    public void manipulationFail(String msg) {
        manipulationInteract.removeAll();
        manipulationInteract.revalidate();
        manipulationInteract.repaint();

        JLabel message = new JLabel(msg);
        message.setForeground(Color.RED);
        JPanel messagePanel = new JPanel(new FlowLayout());
        messagePanel.add(message);
        manipulationInteract.add(messagePanel, BorderLayout.CENTER);
    }

    public void manipulationSuccess(String msg) {
        manipulationInteract.removeAll();
        manipulationInteract.revalidate();
        manipulationInteract.repaint();

        JLabel message = new JLabel(msg);
        message.setForeground(Color.GREEN);
        JPanel messagePanel = new JPanel(new FlowLayout());
        messagePanel.add(message);
        manipulationInteract.add(messagePanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == categoryBox) {
            String category = (String)categoryBox.getSelectedItem();

            //clear JPanel categoryContents
            categoryContents.removeAll();
            categoryContents.revalidate();
            categoryContents.repaint();

            resultsPanel.removeAll();
            resultsPanel.revalidate();
            resultsPanel.repaint();

            if(category.equals("Student")) {
                setSearchToStudent();
                defaultStudentTable();
                
            }
            else if(category.equals("Faculty") || category.equals("Staff")) {
                setSearchToFacStaff();

                if(category.equals("Faculty")) {
                    defaultFacultyTable();
                }
                else {
                    defaultStaffTable();
                }
            }
        }


        else if(e.getSource() == searchButton) {
            String category = (String)categoryBox.getSelectedItem();

            resultsPanel.removeAll();
            resultsPanel.revalidate();
            resultsPanel.repaint();

            if(category.equals("Student")) {
                String[] queryAttributes = {idField.getText(), nameField.getText(), 
                    majorField.getText(), (String)levelBox.getSelectedItem(), ageField.getText()};
                
                createTable("student", queryAttributes);
            }
            else if(category.equals("Faculty")) {
                String[] queryAttributes = {idField.getText(), nameField.getText(), (String)deptBox.getSelectedItem()};
                createTable("faculty", queryAttributes);
            }
            else if(category.equals("Staff")) {
                String[] queryAttributes = {idField.getText(), nameField.getText(), (String)deptBox.getSelectedItem()};
                createTable("staff", queryAttributes);
            }
        }


        else if(e.getSource() == addStudent) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createAddStudentPanel();
        }


        else if(e.getSource() == addFaculty) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createAddFacultyPanel();
        }


        else if(e.getSource() == addStaff) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createAddStaffPanel();
        }


        else if(e.getSource() == addStudentButton) {
            int rowsAdded = -1;

            if(addAgeField.getText().equals("")) {
                String[] values = {addNameField.getText(), addMajorField.getText(), 
                    (String)addLevelBox.getSelectedItem()};
                rowsAdded = database.insertStudent(values);
            }
            else {
                String[] values = {addNameField.getText(), addMajorField.getText(), 
                    (String)addLevelBox.getSelectedItem(), addAgeField.getText()};
                rowsAdded = database.insertStudent(values);
            }

            if(rowsAdded < 1) {
                manipulationFail("INSERT FAILED");
            } else {
                manipulationSuccess("INSERT SUCCESSFUL!");
            }
        }


        else if(e.getSource() == addFacultyButton) {
            int rowsAdded;

            String[] values = {addNameField.getText(), (String)addDeptBox.getSelectedItem()};
            rowsAdded = database.insertFaculty(values);

            if(rowsAdded < 1) {
                manipulationFail("INSERT FAILED");
            } else {
                manipulationSuccess("INSERT SUCCESSFUL!");
            }
        }


        else if(e.getSource() == addStaffButton) {
            int rowsAdded;

            String[] values = {addNameField.getText(), (String)addDeptBox.getSelectedItem()};
            rowsAdded = database.insertStaff(values);

            if(rowsAdded < 1) {
                manipulationFail("INSERT FAILED");
            } else {
                manipulationSuccess("INSERT SUCCESSFUL!");
            }
        }


        else if(e.getSource() == deleteStudent) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createDeleteStudentPanel();
        }


        else if(e.getSource() == deleteFaculty) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createDeleteFacultyPanel();
        }


        else if(e.getSource() == deleteStaff) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createDeleteStaffPanel();
        }


        else if(e.getSource() == deleteStudentButton) {
            int rowsDeleted = database.deleteStudent(Integer.parseInt(manipulationIdField.getText())); 

            if(rowsDeleted < 1) {
                manipulationFail("DELETE FAILED");
            } else {
                manipulationSuccess("DELETE SUCCESSFUL!");
            }
        }


        else if(e.getSource() == deleteFacultyButton) {
            int rowsDeleted = database.deleteFaculty(Integer.parseInt(manipulationIdField.getText())); 

            if(rowsDeleted < 1) {
                manipulationFail("DELETE FAILED");
            } else {
                manipulationSuccess("DELETE SUCCESSFUL!");
            }
        }


        else if(e.getSource() == deleteStaffButton) {
            int rowsDeleted = database.deleteStaff(Integer.parseInt(manipulationIdField.getText())); 

            if(rowsDeleted < 1) {
                manipulationFail("DELETE FAILED");
            } else {
                manipulationSuccess("DELETE SUCCESSFUL!");
            }
        }


        else if(e.getSource() == updateStudent) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createUpdateStudentPanel();
        }


        else if(e.getSource() == updateFaculty) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createUpdateFacultyPanel();
        }


        else if(e.getSource() == updateStaff) {
            manipulationInteract.removeAll();
            manipulationInteract.revalidate();
            manipulationInteract.repaint();

            createUpdateStaffPanel();
        }


        else if(e.getSource() == updateStudentButton) {
            new UpdateView("student", Integer.parseInt(manipulationIdField.getText()), this, database);
        }


        else if(e.getSource() == updateFacultyButton) {
            new UpdateView("faculty", Integer.parseInt(manipulationIdField.getText()), this, database);
        }

        
        else if(e.getSource() == updateStaffButton) {
            new UpdateView("staff", Integer.parseInt(manipulationIdField.getText()), this, database);
        }
    }
}