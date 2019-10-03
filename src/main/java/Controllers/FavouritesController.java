package Controllers;//Import sql stuff
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FavouritesController {


    //List the contents of the Favourites Table

    public static void listComments() {

        try {

            PreparedStatement ps = Server.Main.db.prepareStatement("SELECT FavourID, User, Job FROM Favourites");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int FavourID = results.getInt(1);
                String User = results.getString(2);
                String Job = results.getString(3);

                System.out.println(FavourID + " " + User + " " + Job);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            System.out.println("Something went wrong with listing Favourites");
        }


    }

    //Add to the database
    public static void insertFavourites(String User, String Job){

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("INSERT INTO Favourites (User, Job) VALUES (?, ?)");

            ps.setString(1,  User);
            ps.setString(2,  Job);
            ps.executeUpdate();
            System.out.println("Record added to comments table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with inserting new Favourites to the database.");
        }

    }

    //Update the database
    public static void updateFavourites(String User, String Job){

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("UPDATE Favourites SET User = ?, Job = ? WHERE FavourID = ?");
            ps.setString(1,  User);
            ps.setString(2,  Job);
            ps.executeUpdate();
            System.out.println("Record updated in Comments table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with updating Favourites in the database");
        }

    }

    //Remove

    public static void deleteFavourite(String TFavourID) {

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("DELETE FROM Favourites WHERE FavourID = ?");
            ps.setString(1, TFavourID);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: Something went wrong when removing favourites from database");
        }

    }

}
