package Controllers;


import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;



@Path("users/")
public class UsersController {

    /*
     * List the contents of the users database
     */
    @GET
    @Path("list/")
    @Produces(MediaType.APPLICATION_JSON)
    public String listUsers() {
        System.out.println("users/list");
        JSONArray list = new JSONArray();

        try {
            //curl -s localhost:8081/users/list
            PreparedStatement ps = Server.Main.db.prepareStatement("SELECT UserID, UserName, Password, Email, FirstName, LastName, Gender, DateofBirth FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {

                JSONObject item = new JSONObject();
                item.put("id", results.getInt(1));
                item.put("User Name", results.getString(2));
                item.put("Password", results.getString(3));
                item.put("Email", results.getString(4));
                item.put("First Name", results.getString(5));
                item.put("Last Name", results.getString(6));
                item.put("Gender", results.getString(7));
                item.put("Date of Birth", results.getString(8));
                list.add(item);

                int userID = results.getInt(1);
                String userName = results.getString(2);
                String password = results.getString(3);
                String email = results.getString(4);
                String firstName = results.getString(5);
                String lastName = results.getString(6);
                String gender = results.getString(7);
                String dob = results.getString(8);
                System.out.println(userID + " " + firstName + " " + lastName + " " + userName + " "+ password + " " + gender + " " + email + " " + dob);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            System.out.println("Something went wrong with listing the users");
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }


    }

    //Add to the database
    @POST
    @Path("add/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertUser(@FormDataParam("TUsername") String TUsername, @FormDataParam("TPassword") String TPassword, @FormDataParam("TEmail") String TEmail, @FormDataParam("TFirstName") String TFirstName, @FormDataParam("TLastName") String TLastName, @FormDataParam("TGender") String TGender, @FormDataParam("TDateOfBirth")String TDateofBirth ){
        try {
            //curl -s localhost:8081/users/add/ -F TUsername="Adeel" -F TPassword="Mypassword" -F TEmail="myown@mail.com" -F TFirstName="Mati" -F TLastName="Smith" -F TGender="Male" -F TDateOfBirth="01/08/2002"

            if (TUsername == null || TPassword == null || TEmail == null || TFirstName == null || TLastName == null || TGender == null || TDateofBirth == null) {
                System.out.println(TUsername + " / " + TPassword + " / " + TEmail + " / " + TFirstName + " / " + TLastName + " / " + TGender + " / " + TDateofBirth);
                throw new Exception("One or more form data parameters are missing in the HTTP request.");

            }
            System.out.println("users/add user= " + TUsername );

            PreparedStatement ps = Server.Main.db.prepareStatement("INSERT INTO Users (Username, Password, Email, FirstName, LastName, Gender, DateofBirth) VALUES (?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1,  TUsername);
            ps.setString(2,  TPassword);
            ps.setString(3,  TEmail);
            ps.setString(4,  TFirstName);
            ps.setString(5,  TLastName);
            ps.setString(6,  TGender);
            ps.setString(7,  TDateofBirth);
            ps.executeUpdate();
            System.out.println(TUsername + " / " + TPassword + " / " + TEmail + " / " + TFirstName + " / " + TLastName + " / " + TGender + " / " + TDateofBirth);
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to create User, please see server console for more info.\"}";
        }

    }

    //Update the database
    @POST
    @Path("update/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(@FormDataParam("TUsername") String TUsername, @FormDataParam("TPassword") String TPassword, @FormDataParam("TEmail") String TEmail, @FormDataParam("TFirstName") String TFirstName, @FormDataParam("TLastName") String TLastName, @FormDataParam("TGender") String TGender, @FormDataParam("TDateOfBirth") String TDateofBirth){

        try {
            //curl -s localhost:8081/users/update/ -F TUsername="Adeel" -F TPassword="Mypassword" -F TEmail="myown@mail.com" -F TFirstName="Mati" -F TLastName="Smith" -F TGender="Male" -F TDateOfBirth="01/08/2002"

            if (TUsername == null || TPassword == null || TEmail == null || TFirstName == null || TLastName == null || TGender == null || TDateofBirth == null) {
                System.out.println(TUsername + " / " + TPassword + " / " + TEmail + " / " + TFirstName + " / " + TLastName + " / " + TGender + " / " + TDateofBirth);
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("users/insert user= " + TUsername );

            PreparedStatement ps = Server.Main.db.prepareStatement("UPDATE Users SET Password = ?, Email = ?,  FirstName = ?, LastName = ?, Gender = ?, DateofBirth = ? WHERE UserName = ?");
            ps.setString(1,  TPassword);
            ps.setString(2,  TEmail);
            ps.setString(3,  TFirstName);
            ps.setString(4,  TLastName);
            ps.setString(5,  TGender);
            ps.setString(6,  TDateofBirth);
            ps.setString(7,  TUsername);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
            //System.out.println("Record updated in Users table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            //System.out.println("Error: Something went wrong with updating user in the database");
            return "{\"error\": \"Unable to update User, please see server console for more info.\"}";
        }

    }

    //Remove a user
    @POST
    @Path("remove/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@FormDataParam("TUsername") String TUsername) {

        try {

            if (TUsername == null) {
                System.out.println(TUsername);
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

        PreparedStatement ps = Server.Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
        ps.setString(1, TUsername);
        ps.executeUpdate();
        return "{\"status\": \"OK\"}";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: Something went wrong when removing user from database");
            return "{\"error\": \"Unable to remove User, please see server console for more info.\"}";
        }

    }
}
