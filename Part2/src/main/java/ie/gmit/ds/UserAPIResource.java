package ie.gmit.ds;
// Adapted from https://github.com/john-french/artistAPI-dropwizard

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserAPIResource {

    private HashMap<Integer, User> userMap = new HashMap<>();

    public UserAPIResource() {
        User testUser = new User(1, "con", "ckkor20@live.ie", "con123");
        userMap.put(testUser.getUserID(), testUser);
    }


    @GET
    public Collection<User> getUsers() {
        // artistsMap.values() returns Collection<Artist>
        // Collection is the interface implemented by Java collections like ArrayList, LinkedList etc.
        // it's basically a generic list.
        // https://docs.oracle.com/javase/7/docs/api/java/util/Collection.html

        return userMap.values();
    }



}


