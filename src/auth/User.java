package src.auth;

public class User {
    private String token;
    private int userID;
    private boolean verified;
    private String firstName;
    private String lastName;
    private String email;
    private int modePreference;
    private int userClass;
    
    public User(String token, int userID, boolean verified, String firstName, String lastName, String email, int modePreference, int userClass) {
        this.token = token;
        this.verified = verified;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.modePreference = modePreference;
        this.userClass = userClass;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public boolean isVerified() {
        return this.verified;
    }

    public void verify() {
        this.verified = true;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public int getModePreference() {
        return this.modePreference;
    }

    public int getUserClass() {
        return this.userClass;
    }

    public void setModePreference(int newP) {
        this.modePreference = newP;
    }

    public void setUserClass(int newC) {
        this.userClass = newC;
    }
}