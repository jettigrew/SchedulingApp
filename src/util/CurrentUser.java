package util;

public class CurrentUser {
    private static String userName;

    public CurrentUser() {}

    public CurrentUser(String userName) {
        this.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
