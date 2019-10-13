package Login.Model;

// Simple model that only holds the username of a user when he/she logs in
public class CurrentUser {
    // We will set this value to the username of the user after the login sequence
    // and use it to determine who is the current user so we can fetch his/her data from the database later
    public static String username;
}
