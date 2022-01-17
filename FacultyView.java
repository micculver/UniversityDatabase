import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.awt.*;

public class FacultyView extends JFrame implements ActionListener {
    private int width = 1000;
    private int height = 550;

    private JDBC database;

    private JComboBox<String> cidBox;
    private JScrollPane gradesView;
    private JButton enterButton;
    private JPanel errorPanel;
    private JScrollPane enrolledView;

    public FacultyView(int id, JDBC database) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setTitle("Faculty View");
        this.setLayout(new BorderLayout());

        this.database = database;

        String name = database.getName("faculty", id);
        JLabel welcomeText = new JLabel("Welcome, " + name);
        JPanel welcomeWrapper = new JPanel(new FlowLayout());
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        welcomeWrapper.add(welcomeText);
        topPanel.add(welcomeWrapper);
        this.add(topPanel, BorderLayout.NORTH);

        //set up the structure of the UI
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        JPanel gradesPanel = new JPanel(new GridLayout(4, 1));
        JPanel viewPanel = new JPanel(new FlowLayout());
        contentPanel.add(gradesPanel);
        contentPanel.add(viewPanel);
        this.add(contentPanel, BorderLayout.CENTER);

        String[] cidArr = database.fetchCID(id);

        cidBox = new JComboBox<String>(cidArr);
        cidBox.addActionListener(this);
        JPanel cidBoxWrapper = new JPanel(new FlowLayout());
        cidBoxWrapper.add(cidBox);
        topPanel.add(cidBoxWrapper);

        gradesView = new JScrollPane(enterGradesView(cidBox.getSelectedItem().toString()));
        gradesPanel.add(gradesView);

        enterButton = new JButton("Enter Grades");
        enterButton.addActionListener(this);
        JPanel buttonWrapper = new JPanel(new FlowLayout());
        buttonWrapper.add(enterButton);
        gradesPanel.add(buttonWrapper);

        errorPanel = new JPanel(new FlowLayout());
        gradesPanel.add(errorPanel);

        enrolledView = new JScrollPane(makeTable());
        viewPanel.add(enrolledView);

        this.setVisible(true);
        this.setResizable(false);
    }

    private int[][] rowColumn;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cidBox) {
            gradesView.setViewportView(enterGradesView(cidBox.getSelectedItem().toString()));
            enrolledView.setViewportView(makeTable());
        }
        

        //Detect valid input responses via gradeFields and add them to the database
        //invalid responses generate an error message and are left in the textfields
        else if(e.getSource() == enterButton) {
            errorPanel.removeAll();
            errorPanel.revalidate();
            errorPanel.repaint();
            

            rowColumn = new int[sidArr.length * 3][2]; //save indexes of updated values to the array, rowColumn
            int rcIndex = 0;

            //populate rowColumn with indexes where textfields contain text
            for(int row = 0; row < sidArr.length; row++) {
                for(int column = 0; column < gradeFields[row].length; column++) {
                    if(!gradeFields[row][column].getText().toString().equals("")) {
                        rowColumn[rcIndex][0] = row;
                        rowColumn[rcIndex][1] = column;
                        rcIndex++;
                    }
                }
            }

            rowColumn = Arrays.copyOfRange(rowColumn, 0, rcIndex);
            
            String cid = cidBox.getSelectedItem().toString(); // get value of course id where update happens

            for(int i = 0; i < rowColumn.length; i++) {
                int sid;
                String exam = "";
                int score;

                sid = sidArr[rowColumn[i][0]]; //get value of student id where the update happens

                //get the name of the column to update in the database
                if(rowColumn[i][1] == 0) {
                    exam = "exam1_score";
                } 
                else if(rowColumn[i][1] == 1) {
                    exam = "exam2_score";
                }
                else if(rowColumn[i][1] == 2) {
                    exam = "final_score";
                }

                try {
                    score = Integer.parseInt(gradeFields[rowColumn[i][0]][rowColumn[i][1]].getText());
                } catch(NumberFormatException err) {
                    addError("Invalid response on " + cid + " " + exam + " on Student ID: " + sid);
                    
                    continue;
                }

                if(score < 0 || score > 100) {
                    addError("Score must be within the range of 0-100 " + cid + " " + exam + " on Student ID: " + sid);
                    continue;
                }

                int rowsAffected = database.updateGrades(sid, cid, exam, score);

                if(rowsAffected == 0) {
                    addError("Error performing update on " + exam + " on Student ID: " + sid);
                    continue;
                }

                //clear edited textfields where grades were successfully updated
                gradeFields[rowColumn[i][0]][rowColumn[i][1]].setText("");
            }

            enrolledView.setViewportView(makeTable()); //refresh enrolledView scrollpane
        }
    }

    private void addError(String msg) {
        JLabel message = new JLabel(msg);
        message.setForeground(Color.RED);
        errorPanel.add(message);
    }

    private int[] sidArr; //array of student ID's
    private JTextField[][] gradeFields; //2D array of textfields to input grades

    //populate gradeFields with textfields and sidArr with student ID's
    //each row corresponds with a certain student ID
    private JPanel enterGradesView(String cid) {
        int[] fetchedSID = database.fetchEnrolledSID(cid);

        JPanel content = new JPanel(new BorderLayout());
        JPanel titleWrapper = new JPanel(new GridLayout(1, 4));
        JPanel gradesPanel = new JPanel(new GridLayout(fetchedSID.length, 4));
        content.add(titleWrapper, BorderLayout.NORTH);
        content.add(gradesPanel, BorderLayout.CENTER);

        JLabel sidTitle = new JLabel("Student ID");
        JLabel exam1Title = new JLabel("Exam 1 Score");
        JLabel exam2Title = new JLabel("Exam 2 Score");
        JLabel finalTitle = new JLabel("Final Exam Score");

        titleWrapper.add(sidTitle);
        titleWrapper.add(exam1Title);
        titleWrapper.add(exam2Title);
        titleWrapper.add(finalTitle);

        sidArr = new int[fetchedSID.length];
        gradeFields = new JTextField[sidArr.length][3];

        //populate enterGradesEntries with SID and textfields to enter grades for each row
        for(int i = 0; i < sidArr.length; i++) {
            sidArr[i] = fetchedSID[i];
            gradeFields[i][0] = new JTextField();
            gradeFields[i][1] = new JTextField();
            gradeFields[i][2] = new JTextField();

            JLabel sidLabel = new JLabel("" + fetchedSID[i]);

            gradesPanel.add(sidLabel);
            gradesPanel.add(gradeFields[i][0]);
            gradesPanel.add(gradeFields[i][1]);
            gradesPanel.add(gradeFields[i][2]);
        }

        return content;
    }

    private JTable makeTable() {
        String[] columnNames = {"Student ID", "Name", "Exam Score 1", "Exam Score 2", "Final Exam Score"};
        Object[][] data = database.fetchEnrolled(cidBox.getSelectedItem().toString());

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);
        
        return table;
    }
}
