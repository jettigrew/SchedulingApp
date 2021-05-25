package util;

public class CurrentUser{
    private static int userID;
    private static String userName;

    public CurrentUser() {}

    public CurrentUser(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public static int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
