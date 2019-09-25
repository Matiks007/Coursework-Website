import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsersController {

    /*
     * List the contents of the users database
     */
    public static void listUsers() {

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, UserName, Password, Email, FirstName, LastName, Gender, DateofBirth FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
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

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            System.out.println("Something went wrong with listing the users");
        }


    }

    //Add to the database
    public static void insertUser(String TUsername, String TPassword, String TEmail, String TFirstName, String TLastName, String TGender, String TDateofBirth ){
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, Password, Email, FirstName, LastName, Gender, DateofBirth) VALUES (?, ?, ?,?,?,?,?)");

            ps.setString(1,  TUsername);
            ps.setString(2,  TPassword);
            ps.setString(3,  TEmail);
            ps.setString(4,  TFirstName);
            ps.setString(5,  TLastName);
            ps.setString(6,  TGender);
            ps.setString(7,  TDateofBirth);
            ps.executeUpdate();
            System.out.println("Record added to Users table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with inserting new user to the database.");
        }

    }

    //Update the database
    public static void updateUser(String TUsername, String TPassword, String TEmail, String TFirstName, String TLastName, String TGender, String TDateofBirth){

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Password = ?, Email = ?,  FirstName = ?, LastName = ?, Gender, DateofBirth WHERE UserName = ?");
            ps.setString(1,  TPassword);
            ps.setString(2,  TEmail);
            ps.setString(3,  TFirstName);
            ps.setString(4,  TLastName);
            ps.setString(5,  TGender);
            ps.setString(6,  TDateofBirth);
            ps.setString(7,  TUsername);
            ps.executeUpdate();
            System.out.println("Record updated in Users table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something went wrong with updating user in the database");
        }

    }

    //Remove a user

    public static void deleteUser(String TUsername) {

        try {
        PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
        ps.setString(1, TUsername);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: Something went wrong when removing user from database");
        }

    }
}
