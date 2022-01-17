import java.sql.*;

public class JDBC {
    private String url;
    private String uname;
    private String password;

    private Connection con;

    public JDBC() {
        this.url = "jdbc:mysql://localhost:3306/University";
        this.uname = "root";
        this.password = "abcdefg";
        this.createConnection();
    }

    //returns ID of user-entry that matches category and id, returns -1 otherwise
    public int searchByID(String category, int id) {
        String idColumn = "";

        if(category.equalsIgnoreCase("student") || category.equalsIgnoreCase("staff")) {
            idColumn = "sid";
        }
        else if(category.equalsIgnoreCase("faculty")){
            idColumn = "fid";
        }

        String query = "SELECT " + idColumn + " FROM " + category + " WHERE " + idColumn + " = " + id + ";";

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();

            int temp = result.getInt(idColumn);

            statement.close();
            return temp;
        } catch (SQLException err) {
            return -1;
        }
    }

    public Object[][] searchFaculty(String[] queryAttributes) {
        String query1 = "SELECT fid, fname, dname FROM faculty, department";
        String query2 = "SELECT COUNT(*) FROM faculty, department";
        String WHERE = " WHERE deptid = did";

        if(!queryAttributes[0].equals("")) {
            WHERE += " AND fid = " + queryAttributes[0];
        }

        if(!queryAttributes[1].equals("")) {
            WHERE += " AND fname = \"" + queryAttributes[1] + "\"";
        }

        if(!queryAttributes[2].equals("All")) {
            WHERE += " AND dname = \"" + queryAttributes[2] + "\"";
        }

        query1 += WHERE + " ORDER BY fid";
        query2 += WHERE + " ORDER BY fid";

        try {

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            Statement countStatement = con.createStatement();
            ResultSet count = countStatement.executeQuery(query2);
            count.next();

            int rowCount = count.getInt(1);
            int columnCount = result.getMetaData().getColumnCount();

            Object[][] data = new Object[rowCount][columnCount];

            int row = 0;

            while(result.next()) {
                data[row][0] = result.getInt(1);
                data[row][1] = result.getString(2);
                data[row][2] = result.getString(3);

                row++;
            }

            statement.close();
            countStatement.close();

            return data;
        } catch(SQLException err) {
            return null;
        }
    }

    public Object[][] searchStaff(String[] queryAttributes) {
        String query1 = "SELECT sid, sname, dname FROM staff, department";
        String query2 = "SELECT COUNT(*) FROM staff, department";
        String WHERE = " WHERE deptid = did";

        if(!queryAttributes[0].equals("")) {
            WHERE += " AND sid = " + queryAttributes[0];
        }

        if(!queryAttributes[1].equals("")) {
            WHERE += " AND sname = \"" + queryAttributes[1] + "\"";
        }

        if(!queryAttributes[2].equals("All")) {
            WHERE += " AND dname = \"" + queryAttributes[2] + "\"";
        }

        query1 += WHERE + " ORDER BY sid";
        query2 += WHERE + " ORDER BY sid";

        try {

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            Statement countStatement = con.createStatement();
            ResultSet count = countStatement.executeQuery(query2);
            count.next();

            int rowCount = count.getInt(1);
            int columnCount = result.getMetaData().getColumnCount();

            Object[][] data = new Object[rowCount][columnCount];

            int row = 0;

            while(result.next()) {
                data[row][0] = result.getInt(1);
                data[row][1] = result.getString(2);
                data[row][2] = result.getString(3);
 
                row++;
            }

            statement.close();
            countStatement.close();

            return data;
        } catch(SQLException err) {
            return null;
        }
    }

    public Object[][] searchStudent(String[] queryAttributes) {
        String query1 = "SELECT * FROM student";
        String query2 = "SELECT COUNT(*) FROM student";
        String WHERE = " WHERE ";
        int attributeCount = 0;

        if(!queryAttributes[0].equals("")) {
            WHERE += " sid = " + queryAttributes[0];
            attributeCount++;
        }

        if(!queryAttributes[1].equals("")) {
            if(attributeCount > 0) {
                WHERE += " AND ";
            }

            WHERE += " sname = \"" + queryAttributes[1] + "\"";
            attributeCount++;
        }

        if(!queryAttributes[2].equals("")) {
            if(attributeCount > 0) {
                WHERE += " AND ";
            }

            WHERE += " major = \"" + queryAttributes[2] + "\"";
            attributeCount++;
        }

        if(!queryAttributes[3].equals("All")) {
            if(attributeCount > 0) {
                WHERE += " AND ";
            }

            WHERE += " s_level = \"" + queryAttributes[3] + "\"";
            attributeCount++;
        }

        if(!queryAttributes[4].equals("")) {
            if(attributeCount > 0) {
                WHERE += " AND ";
            }

            WHERE += " age = " + queryAttributes[4];
        }
        
        if(!WHERE.equals(" WHERE ")) {
            query1 += WHERE;
            query2 += WHERE;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            Statement countStatement = con.createStatement();
            ResultSet count = countStatement.executeQuery(query2);
            count.next();

            int rowCount = count.getInt(1);
            int columnCount = result.getMetaData().getColumnCount();

            Object[][] data = new Object[rowCount][columnCount];

            int row = 0;

            while(result.next()) {
                data[row][0] = result.getInt(1);
                data[row][1] = result.getString(2);
                data[row][2] = result.getString(3);
                data[row][3] = result.getString(4);
                data[row][4] = result.getInt(5);

                row++;
            }

            statement.close();
            countStatement.close();

            return data;
            
        } catch(SQLException err) {
            return null;
        }
    }

    public Object[][] searchAllStudent() {
        String query1 = "SELECT * FROM student";
        String query2 = "SELECT COUNT(*) FROM student";

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            Statement countStatement = con.createStatement();
            ResultSet count = countStatement.executeQuery(query2);
            count.next();

            int rowCount = count.getInt(1);
            int columnCount = result.getMetaData().getColumnCount();

            Object[][] data = new Object[rowCount][columnCount];

            int row = 0;

            while(result.next()) {
                data[row][0] = result.getInt(1);
                data[row][1] = result.getString(2);
                data[row][2] = result.getString(3);
                data[row][3] = result.getString(4);
                data[row][4] = result.getInt(5);

                row++;
            }

            statement.close();
            countStatement.close();

            return data;

        } catch (SQLException err) {
            return null;
        }
    }

    public Object[][] searchAllFaculty() {
        String query1 = "SELECT fid, fname, dname FROM faculty, department WHERE deptid = did ORDER BY fid";
        String query2 = "SELECT COUNT(*) FROM faculty";

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            Statement countStatement = con.createStatement();
            ResultSet count = countStatement.executeQuery(query2);
            count.next();

            int rowCount = count.getInt(1);
            int columnCount = result.getMetaData().getColumnCount();

            Object[][] data = new Object[rowCount][columnCount];

            int row = 0;

            while(result.next()) {
                data[row][0] = result.getInt(1);
                data[row][1] = result.getString(2);
                data[row][2] = result.getString(3);

                row++;
            }

            statement.close();
            countStatement.close();

            return data;

        } catch (SQLException err) {
            return null;
        }
    }

    public Object[][] searchAllStaff() {
        String query1 = "SELECT sid, sname, dname FROM staff, department WHERE deptid = did ORDER BY sid";
        String query2 = "SELECT COUNT(*) FROM staff";

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            Statement countStatement = con.createStatement();
            ResultSet count = countStatement.executeQuery(query2);
            count.next();

            int rowCount = count.getInt(1);
            int columnCount = result.getMetaData().getColumnCount();

            Object[][] data = new Object[rowCount][columnCount];

            int row = 0;

            while(result.next()) {
                data[row][0] = result.getInt(1);
                data[row][1] = result.getString(2);
                data[row][2] = result.getString(3);

                row++;
            }

            statement.close();
            countStatement.close();

            return data;

        } catch (SQLException err) {
            return null;
        }
    }
    
    public int insertStudent(String[] values) {
        if(values[0].equals("")) {
            return 0;
        }

        String update = "";

        if(values.length == 4)
            update += "INSERT into student(sname, major, s_level, age) VALUES(\"" +
            values[0] + "\", \"" + values[1] + "\", \"" + values[2] + "\", " + values[3] + ")";
        else
            update += "INSERT into student(sname, major, s_level) VALUES(\"" +
            values[0] + "\", \"" + values[1] + "\", \"" + values[2] + "\")";

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch(SQLException err) {
            err.printStackTrace();
            return 0;
        }
    }

    public int insertFaculty(String[] values) {
        if(values[0].equals("") || values[1].equals("")) {
            return 0;
        }
        
        String update = "";
        if(!values[1].equals("")) {
            String get_did = "SELECT did FROM department WHERE dname = \"" + values[1] + "\"";
            int deptid;
        

            try {
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(get_did);
                result.next();
                deptid = result.getInt(1);
                statement.close();
                result.close();
            } catch(SQLException err) {
                return 0;
            }
        

            update = "INSERT INTO faculty(fname, deptid) VALUES(\"" + values[0] + "\", " + deptid + ")";
        }
        else {
            update = "INSERT INTO faculty(fname) VALUES(\"" + values[0] + "\")";
        }

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch(SQLException err) {
            return 0;
        }
    }

    public int insertStaff(String[] values) {
        if(values[0].equals("") || values[1].equals("")) {
            return 0;
        }
        
        String update = "";
        if(!values[1].equals("")) {
            String get_did = "SELECT did FROM department WHERE dname = \"" + values[1] + "\"";
            int deptid;
        

            try {
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(get_did);
                result.next();
                deptid = result.getInt(1);
                statement.close();
                result.close();
            } catch(SQLException err) {
                return 0;
            }
        

            update = "INSERT INTO staff(sname, deptid) VALUES(\"" + values[0] + "\", " + deptid + ")";
        }
        else {
            update = "INSERT INTO STAFF(sname) VALUES(\"" + values[0] + "\")";
        }

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch(SQLException err) {
            return 0;
        }
    }

    public int deleteStudent(int id) {
        String update = "DELETE FROM student WHERE sid = " + id;

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch (SQLException err) {
            return 0;
        }
    }

    public int deleteFaculty(int id) {
        String update = "DELETE FROM faculty WHERE fid = " + id;

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch (SQLException err) {
            return 0;
        }
    }

    public int deleteStaff(int id) {
        String update = "DELETE FROM staff WHERE sid = " + id;

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch (SQLException err) {
            return 0;
        }
    }
    
    public int updateStudent(int id, String[] values) {
        String update = "UPDATE student SET";

        int n = 0;

        if(!values[0].equals("")) {
            update += " sname = \"" + values[0] + "\"";
            n++;
        }

        if(!values[1].equals("")) {
            if(n > 0) {
                update += ",";
            }
            update += " major = \"" + values[1] + "\"";
        }

        if(!values[2].equals("")) {
            if(n > 0) {
                update += ",";
            }
            update += " s_level = \"" + values[2] + "\"";
        }

        if(!values[3].equals("")) {
            if(n > 0) {
                update += ",";
            }
            update += " age = " + values[3];
        }

        update += " WHERE sid = " + id;

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch(SQLException err) {
            return 0;
        }
    }

    public int updateFaculty(int id, String[] values) {
        String update = "UPDATE faculty SET";

        int n = 0;
        int deptid = 0;

        if(!values[1].equals("")) {
            String get_did = "SELECT did FROM department WHERE dname = \"" + values[1] + "\"";

            try {
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(get_did);
                result.next();

                deptid = result.getInt(1);

                statement.close();
                result.close();
            } catch(SQLException err) {
                return 0;
            }
        }

        if(!values[0].equals("")) {
            update += " fname = \"" + values[0] + "\"";
            n++;
        }

        if(!values[1].equals("")) {
            if(n > 0) {
                update += ",";
            }
            update += " deptid = " + deptid;
        }

        update += " WHERE fid = " + id;

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch(SQLException err) {
            return 0;
        }
    }

    public int updateStaff(int id, String[] values) {
        String update = "UPDATE staff SET";

        int n = 0;
        int deptid = 0;

        if(!values[1].equals("")) {
            String get_did = "SELECT did FROM department WHERE dname = \"" + values[1] + "\"";

            try {
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(get_did);
                result.next();

                deptid = result.getInt(1);

                statement.close();
                result.close();
            } catch(SQLException err) {
                return 0;
            }
        }

        if(!values[0].equals("")) {
            update += " sname = \"" + values[0] + "\"";
            n++;
        }

        if(!values[1].equals("")) {
            if(n > 0) {
                update += ",";
            }
            update += " deptid = " + deptid;
        }

        update += " WHERE sid = " + id;

        try {
            Statement statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch(SQLException err) {
            return 0;
        }
    }
   
    public Object[][] fetchGrades(int id) {
        String query1 = "SELECT fname, courses.cid, cname, exam1_score, exam2_score, final_score"
                    + " FROM enrolled, faculty, courses"
                    + " WHERE courses.cid = enrolled.cid AND faculty.fid = courses.fid AND sid = " + id;
        
        //query2 used to capture row count
        String query2 = "SELECT COUNT(*) FROM enrolled WHERE sid = " + id;

        int rowCount = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query2);
            result.next();

            rowCount = result.getInt(1);
            statement.close();
            result.close();
        } catch(SQLException err) {
            return null;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            int columnCount = result.getMetaData().getColumnCount();
            Object[][] data = new Object[rowCount][columnCount];

            int rowIndex = 0;

            while(result.next()) {
                data[rowIndex][0] = result.getString(1);
                data[rowIndex][1] = result.getString(2);
                data[rowIndex][2] = result.getString(3);
                data[rowIndex][3] = result.getInt(4);
                data[rowIndex][4] = result.getInt(5);
                data[rowIndex][5] = result.getInt(6);

                rowIndex++;
            }

            statement.close();
            result.close();
            return data;
        } catch (SQLException err) {
            return null;
        }
    }

    public Object[][] fetchStaff() {
        String query1 = "SELECT sname, dname FROM staff, department WHERE deptid = did";
        String query2 = "SELECT COUNT(*) FROM staff";

        int rowCount = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query2);
            result.next();

            rowCount = result.getInt(1);
            statement.close();
            result.close();
        } catch(SQLException err) {
            return null;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            int columnCount = result.getMetaData().getColumnCount();
            Object[][] data = new Object[rowCount][columnCount];

            int rowIndex = 0;

            while(result.next()) {
                data[rowIndex][0] = result.getString(1);
                data[rowIndex][1] = result.getString(2);

                rowIndex++;
            }

            statement.close();
            result.close();

            return data;
        } catch(SQLException err) {
            return null;
        }
    }

    public Object[][] fetchFaculty() {
        String query1 = "SELECT fname, dname FROM faculty, department WHERE deptid = did";
        String query2 = "SELECT COUNT(*) FROM faculty";

        int rowCount = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query2);
            result.next();

            rowCount = result.getInt(1);
            statement.close();
            result.close();
        } catch(SQLException err) {
            return null;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            int columnCount = result.getMetaData().getColumnCount();
            Object[][] data = new Object[rowCount][columnCount];

            int rowIndex = 0;

            while(result.next()) {
                data[rowIndex][0] = result.getString(1);
                data[rowIndex][1] = result.getString(2);

                rowIndex++;
            }

            statement.close();
            result.close();

            return data;
        } catch(SQLException err) {
            return null;
        }
    }

    public Object[][] fetchCourses() {
        String query1 = "SELECT courses.cid, cname, fname" + 
                        " FROM courses, faculty" +
                        " WHERE courses.fid = faculty.fid";

        String query2 = "SELECT COUNT(*) from courses";

        int rowCount = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query2);
            result.next();

            rowCount = result.getInt(1);
            statement.close();
            result.close();
        } catch(SQLException err) {
            return null;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            int columnCount = result.getMetaData().getColumnCount();
            Object[][] data = new Object[rowCount][columnCount];

            int rowIndex = 0;

            while(result.next()) {
                data[rowIndex][0] = result.getString(1);
                data[rowIndex][1] = result.getString(2);
                data[rowIndex][2] = result.getString(3);

                rowIndex++;
            }

            statement.close();
            result.close();

            return data;
        } catch(SQLException err) {
            return null;
        }
    }

    //returns name of user-entry that matches category and id, returns [null] otherwise
    public String getName(String category, int id) {
        String nameColumn = "";
        String idColumn = "";

        if(category.equalsIgnoreCase("student") || category.equalsIgnoreCase("staff")) {
            nameColumn = "sname";
            idColumn = "sid";
        }
        else {
            nameColumn = "fname";
            idColumn = "fid";
        }

        String query = "SELECT " + nameColumn + " FROM " + category + " WHERE " + idColumn + " = " + id + ";";

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            return result.getString(nameColumn);
        } catch(SQLException err) {
            return "[null]";
        }
    }

    public int enrollStudent(int id, String cid) {
        String update = "INSERT INTO enrolled(sid, cid) VALUES(" + id + ", \"" + cid + "\")";
        
        try {
            Statement statement = con.createStatement();
            int rowsAffected = statement.executeUpdate(update);

            statement.close();

            return rowsAffected;
        } catch(SQLException err) {
            return 0;
        }
    }

    public String[] fetchCID(int fid) {
        String query1 = "SELECT cid FROM courses WHERE fid = " + fid;
        String query2 = "SELECT COUNT(*) FROM courses WHERE fid = " + fid;

        int rowCount = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query2);
            result.next();

            rowCount = result.getInt(1);

            statement.close();
            result.close();

            if(rowCount == 0) {
                return null;
            }
        } catch(SQLException err) {
            return null;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            String[] data = new String[rowCount];

            int index = 0;

            while(result.next()) {
                data[index] = result.getString(1);
                index++; 
            }

            statement.close();
            result.close();

            return data;
        } catch(SQLException err) {
            return null;
        }
    }

    public int[] fetchEnrolledSID(String cid) {
        String query1 = "SELECT sid FROM enrolled WHERE cid = \"" + cid + "\"";
        String query2 = "SELECT COUNT(*) FROM enrolled WHERE cid = \"" + cid + "\"";
        int rowCount = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query2);
            result.next();

            rowCount = result.getInt(1);
            statement.close();
            result.close();
        } catch(SQLException err) {
            return null;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            int[] sidArr = new int[rowCount];
            
            int index = 0;

            while(result.next()) {
                sidArr[index] = result.getInt(1);
                index++;
            }

            statement.close();
            result.close();

            return sidArr;
        } catch (SQLException err) {
            return null;
        }
    }
    
    public int updateGrades(int sid, String cid, String exam, int score) {
        String update = "UPDATE enrolled SET " + exam + " = " + score + " WHERE sid = " + sid + " AND cid = \"" + cid + "\"";

        try {
            Statement statement = con.createStatement();
            int rowsAffected = statement.executeUpdate(update);

            statement.close();
            return rowsAffected;
        } catch(SQLException err) {
            return 0;
        }
    }

    public Object[][] fetchEnrolled(String cid) {
        String query1 = "SELECT enrolled.sid, sname, exam1_score, exam2_score, final_score" +
                    " FROM enrolled, student" +
                    " WHERE student.sid = enrolled.sid AND cid = \"" + cid + "\"";
        String query2 = "SELECT COUNT(*) FROM enrolled WHERE cid = \"" + cid + "\"";

        int rowCount = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query2);
            result.next();

            rowCount = result.getInt(1);

            statement.close();
            result.close();

            if(rowCount == 0) {
                return null;
            }
        } catch (SQLException err) {
            return null;
        }

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query1);

            int columnCount = result.getMetaData().getColumnCount();
            Object[][] data = new Object[rowCount][columnCount];
            int index = 0;

            while(result.next()) {
                data[index][0] = result.getInt(1);
                data[index][1] = result.getString(2);
                data[index][2] = result.getInt(3);
                data[index][3] = result.getInt(4);
                data[index][4] = result.getInt(5);

                index++;
            }

            statement.close();
            result.close();

            return data;
        } catch (SQLException err) {
            return null;
        }

    }

    private void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException err) {
            err.printStackTrace();
        }

        try { 
            con = DriverManager.getConnection(this.url, this.uname, this.password);
        } catch(SQLException err) {
            err.printStackTrace();
        }
    }
}