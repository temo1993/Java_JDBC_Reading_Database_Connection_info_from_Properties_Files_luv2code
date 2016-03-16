import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectionDemo {
    public static void main(String[] args) {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try{
            // 1. Load the properties file
            Properties props = new Properties();
            props.load(new FileInputStream("demo.properties"));

            // 2. Read the props
            String theUSer = props.getProperty("user");
            String thePassword = props.getProperty("password");
            String theDBURL = props.getProperty("dburl");

            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + theDBURL);
            System.out.println("User: " + theUSer);

            // 3. Get a connection to database
            myConnection = DriverManager.getConnection(theDBURL, theUSer, thePassword);

            System.out.println("\nConnection successful!\n");

            // 4. Create a statement
            myStatement = myConnection.createStatement();

            // 5. Execute SQL query
            myResultSet = myStatement.executeQuery("select * from employees");

            // 6. Process the result set
            while (myResultSet.next()) {
                System.out.println(myResultSet.getString("last_name") + ", " + myResultSet.getString("first_name"));
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found!!!");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Sql error: " + sqle.getMessage() + " ,code: " + sqle.getErrorCode());
            sqle.printStackTrace();
        } finally {
            try {
                if (myConnection != null) {
                    myConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (myStatement != null) {
                    myStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (myResultSet != null) {
                    myResultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
