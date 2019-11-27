package ie.gmit.ds.api;
// https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UserLogin {

    @NotNull
    private int userID;
    @NotBlank
    private String password;



    public UserLogin() {
    }

    @JsonProperty
    public int getUserID() {
        return userID;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }
}
