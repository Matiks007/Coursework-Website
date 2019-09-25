/*
* This is Mateusz Kraszewski's second year project coursework.
* You are currently in the Main method.
*
*/

import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;

//Check if works 22 22


public class Main {

        //Global Vars
        public static Connection db = null;


        //Main Method
        public static void main(String[] args) {
            openDatabase("Users.db");
            UsersController.listUsers();
            JobsController.listJobs();
            CommentsController.listComments();
            closeDatabase();

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





} //End of Main class


