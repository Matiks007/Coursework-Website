package Controllers;//Imports for sql

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("jobs/")
public class JobsController {

    /*
     * List the contents of the Jobs Tables
     */
    @GET
    @Path("list/")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJobs() {
        System.out.println("users/list");
        JSONArray list = new JSONArray();

        try {
            //curl -s localhost:8081/jobs/list
            PreparedStatement ps = Server.Main.db.prepareStatement("SELECT JobID, JobName, JobDescription, JobPrice FROM Jobs");
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("JobID", results.getInt(1));
                item.put("JobName", results.getString(2));
                item.put("JobDescritpion", results.getString(3));
                item.put("JobPrice", results.getDouble(4));
                list.add(item);

                int JobID = results.getInt(1);
                String JobName = results.getString(2);
                String JobDescription = results.getString(3);
                Double JobPrice = results.getDouble(4);
                System.out.println(JobID + " " + JobName + " " + JobDescription + " " + JobPrice);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            System.out.println("Something went wrong with listing jobs");
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }


    }

    //Add to the database
    @POST
    @Path("add/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertJob(@FormDataParam("TJobName") String TJobName,@FormDataParam("TJobDescription") String TJobDescription,@FormDataParam("TPrice") Double TPrice){

        try {
            //curl -s localhost:8081/jobs/add -F TJobName="Painter" -F TJobDescription="The guy, whom paint things for you." -F TPrice="0.5"

            if (TJobName == null || TJobDescription == null || TPrice == null) {
                System.out.println(TJobName + " / " + TJobDescription + " / " + TPrice + " / ");
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("jobs/add job= " + TJobName );

            PreparedStatement ps = Server.Main.db.prepareStatement("INSERT INTO Jobs (JobName, JobDescription, JobPrice) VALUES (?, ?, ?)");

            ps.setString(1,  TJobName);
            ps.setString(2,  TJobDescription);
            ps.setDouble(3,  TPrice);
            ps.executeUpdate();
            System.out.println("Record added to Jobs table");
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with inserting new Job to the database.");
            return "{\"error\": \"Unable to create Job, please see server console for more info.\"}";
        }

    }

    //Update the database
    @POST
    @Path("update/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateJob(@FormDataParam("TJobName") String TJobName,@FormDataParam("TJobDescription") String TJobDescription,@FormDataParam("TPrice") Double TPrice){

        try {
            if (TJobName == null || TJobDescription == null || TPrice == null) {
                System.out.println(TJobName + " / " + TJobDescription + " / " + TPrice + " / ");
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("jobs/add update= " + TJobName );

            PreparedStatement ps = Server.Main.db.prepareStatement("UPDATE Jobs SET JobDescription = ?,  JobPrice = ? WHERE JobName = ?");
            ps.setString(1,  TJobDescription);
            ps.setDouble(2,  TPrice);
            ps.setString(3,  TJobName);
            ps.executeUpdate();
            System.out.println("Record updated in Jobs table");
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with updating Jobs in the database");
            return "{\"error\": \"Unable to update Job, please see server console for more info.\"}";
        }

    }

    //Remove a Job
    @POST
    @Path("remove/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteJob(@FormDataParam("TJobName") String TJobName) {

        try {
            if (TJobName == null) {
                System.out.println(TJobName);
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Server.Main.db.prepareStatement("DELETE FROM Jobs WHERE JobName = ?");
            ps.setString(1, TJobName);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: Something went wrong when removing job from database");
            return "{\"error\": \"Unable to remove Job, please see server console for more info.\"}";
        }

    }


}
