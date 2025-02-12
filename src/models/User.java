package models;

public class User {
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String pwd) {
        return this.password.equals(pwd);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String toFileString() {
        return username + "," + password + "," + isAdmin;
    }
}
