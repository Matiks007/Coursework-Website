package Server;/*
* This is Mateusz Kraszewski's second year project coursework.
* You are currently in the Server.Main method.
*
*/

import Controllers.CommentsController;
import Controllers.JobsController;
import Controllers.UsersController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;


public class Main {

        //Global Vars
        public static Connection db = null;

         /*
        //Server.Main Method
        public static void main(String[] args) {
            openDatabase("Users.db");
            UsersController.listUsers();
            JobsController.listJobs();
            CommentsController.listComments();
            closeDatabase();

        }
          */

    public static void main(String[] args) {

        openDatabase("Users.db");

        ResourceConfig config = new ResourceConfig();
        config.packages("Controllers");
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
               //Output message when successfully finished
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
                System.out.println("Disconnected from database."); //Message displayed when successfully closed
            } catch (Exception exception) {
                System.out.println("Database disconnection error: " + exception.getMessage()); //Message displayed when error occurs
            }
        }





} //End of Server.Main class


