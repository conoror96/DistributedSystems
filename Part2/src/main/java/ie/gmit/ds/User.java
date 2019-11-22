package ie.gmit.ds;
// Adapted from https://github.com/john-french/artistAPI-dropwizard

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    int userID;
    String userName;
    String email;
    String password;

    // a no-argument constructor needed for Jackson deserialisation
    public User(){}

    // constructor that takes all 4 fields as arguments
    public User(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // getters for all 4 fields
    // @JsonProperty allows the class to be serialised/deserialised by Jackson.
    @JsonProperty
    public int getUserID() {
        return userID;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

}