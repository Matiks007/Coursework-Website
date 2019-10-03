//Import sql stuff
package Controllers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommentsController {

    /*
     * List the contents of the Comments Table
     */
    public static void listComments() {

        try {

            PreparedStatement ps = Server.Main.db.prepareStatement("SELECT CommentID, Username, Likes, Dislikes, Date FROM Comments");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int CommentID = results.getInt(1);
                String Username = results.getString(2);
                int likes = results.getInt(3);
                int dislikes = results.getInt(4);
                String date = results.getString(5);
                System.out.println(CommentID + " " + Username + " " + likes + " " + dislikes + " " + date);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            System.out.println("Something went wrong with listing comments");
        }


    }


    //Add to the database
    public static void insertComment(String TUsername, int TLikes, int TDislikes, String Tdate){

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("INSERT INTO Comments (Username, Likes, Dislikes, Date) VALUES (?, ?, ?,?)");

            ps.setString(1,  TUsername);
            ps.setInt(2,  TLikes);
            ps.setInt(3,  TDislikes);
            ps.setString(4, Tdate);
            ps.executeUpdate();
            System.out.println("Record added to comments table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with inserting new comments to the database.");
        }

    }

    //Update the database
    public static void updateComment(String TUsername, int TLikes, int TDislikes, String Tdate){

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("UPDATE Comments SET Username = ?, Likes = ?,  Dislikes = ?, Date = ? WHERE CommentID = ?");
            ps.setString(1,  TUsername);
            ps.setInt(2,  TLikes);
            ps.setInt(3,  TDislikes);
            ps.setString(4, Tdate);
            ps.executeUpdate();
            System.out.println("Record updated in Comments table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with updating Comment in the database");
        }

    }

    //Remove a Comment

    public static void deleteComment(String TCommentID) {

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("DELETE FROM Comments WHERE CommentID = ?");
            ps.setString(1, TCommentID);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: Something went wrong when removing comments from database");
        }

    }



}
