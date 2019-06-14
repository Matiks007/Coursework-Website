/*
* This is Mateusz Kraszewski's second year project courswork.
* You are currently in the Main method.
*
*/

import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class main {

        //Global Vars
        public static Connection db = null;


        //Main Method
        public static void main(String[] args) {

            insertUser("Mateusz","Kraszewski","Matiks007", "MyPassword123","Male");
            listUsers();



        }


        /*
        Establish a database connection
        14.06.2019
        */
        private static void openDatabase(String dbFile) {
            try  {
                //Load database driver
                Class.forName("org.sqlite.JDBC");
                //This does database settings
                SQLiteConfig config = new SQLiteConfig();
                config.enforceForeignKeys(true);
                //Open the database file
                db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
               //Output message when succesfully finished
                System.out.println("Database connection successfully established.");
            } catch (Exception exception) {
                //Output error when established incorrectly
                System.out.println("Database connection error: " + exception.getMessage());
            }

        }


        /*
        Close the established database connection
        14.06.2019
        */
        private static void closeDatabase(){
            try {
                db.close(); //Close database
                System.out.println("Disconnected from database."); //Message displayed when succesfully closed
            } catch (Exception exception) {
                System.out.println("Database disconnection error: " + exception.getMessage()); //Message displayed when error occurs
            }
        }

        /*
        * List the contents of the users database
        */
        public static void listUsers() {
            openDatabase("Users.db");

            try {

                PreparedStatement ps = db.prepareStatement("SELECT UserID, FirstName, LastName, UserName, Password, Gender FROM Users");

                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    int userID = results.getInt(1);
                    String firstName = results.getString(2);
                    String lastName = results.getString(3);
                    String userName = results.getString(4);
                    String password = results.getString(5);
                    String gender = results.getString(6);
                    System.out.println(userID + " " + firstName + " " + lastName + " " + userName + " "+ password + " " + gender);
                }

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
            }

            closeDatabase();
        }

        //Add to the database
        public static void insertUser(String TempFirstName, String TempLastName, String TempUserName, String TempPassword, String TempGender ){
            openDatabase("Users.db");
            try {
                PreparedStatement ps = db.prepareStatement("INSERT INTO Users (FirstName, LastName, UserName, Password, Gender) VALUES (?, ?, ?,?,?)");
                ps.setString(1,  TempFirstName);
                ps.setString(2,  TempLastName);
                ps.setString(3,  TempUserName);
                ps.setString(4,  TempPassword);
                ps.setString(5,  TempGender);
                ps.executeUpdate();
                System.out.println("Record added to Weights table");

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
            }
            closeDatabase();
        }

    //Update the database
    public static void updateUser(String TempFirstName, String TempLastName, String TempUserName, String TempPassword, String TempGender ){
        openDatabase("Users.db");
        try {
            PreparedStatement ps = db.prepareStatement("UPDATE Users SET FirstName = ?, LastName = ?,  Password = ?, Gender = ? WHERE UserName = ?");
            ps.setString(1,  TempFirstName);
            ps.setString(2,  TempLastName);
            ps.setString(3,  TempPassword);
            ps.setString(4,  TempGender);
            ps.setString(5, TempUserName);
            ps.executeUpdate();
            System.out.println("Record added to Weights table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
        closeDatabase();
    }



} //End of main class


