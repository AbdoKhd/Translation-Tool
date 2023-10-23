package model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private static final Map<String, User> USERS = new HashMap<String, User>();

    public static User of(String username) {
        User user = USERS.get(username);
        if (user == null) {
            user = new User(username);
            USERS.put(username, user);
        }
        return user;
    }
    
    private int id = 0;
    private String username = "";
    private String email = "";
    
    private User(String username) {
        this.username = username;
    }  

    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String Username) {
        this.username = Username;
    }
}

