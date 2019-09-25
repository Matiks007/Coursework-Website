//Imports for sql
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JobsController {

    /*
     * List the contents of the Jobs Tables
     */
    public static void listJobs() {

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT JobID, JobName, JobDescription, JobPrice FROM Jobs");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int JobID = results.getInt(1);
                String JobName = results.getString(2);
                String JobDescription = results.getString(3);
                Double JobPrice = results.getDouble(4);
                System.out.println(JobID + " " + JobName + " " + JobDescription + " " + JobPrice);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            System.out.println("Something went wrong with listing jobs");
        }


    }

    //Add to the database
    public static void insertJob(String TJobName, String TJobDescription, Double TPrice){

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Jobs (JobName, JobDescription, JobPrice) VALUES (?, ?, ?)");

            ps.setString(1,  TJobName);
            ps.setString(2,  TJobDescription);
            ps.setDouble(3,  TPrice);
            ps.executeUpdate();
            System.out.println("Record added to Jobs table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with inserting new Job to the database.");
        }

    }

    //Update the database
    public static void updateJob(String TJobName, String TJobDescription, Double TPrice){

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Jobs SET JobName = ?, JobDescription = ?,  JobPrice = ? WHERE JobID = ?");
            ps.setString(1,  TJobName);
            ps.setString(2,  TJobDescription);
            ps.setDouble(3,  TPrice);
            ps.executeUpdate();
            System.out.println("Record updated in Jobs table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with updating Jobs in the database");
        }

    }

    //Remove a Job

    public static void deleteJob(String TJobName) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Jobs WHERE JobName = ?");
            ps.setString(1, TJobName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: Something went wrong when removing job from database");
        }

    }


}
