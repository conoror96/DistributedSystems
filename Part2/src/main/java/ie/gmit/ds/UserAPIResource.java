package ie.gmit.ds;
// Adapted from https://github.com/john-french/artistAPI-dropwizard

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAPIResource {

    private HashMap<Integer, User> userMap = new HashMap<>();

    public UserAPIResource() {
        User testUser = new User(1, "con", "ckkor@live.ie", "con123");

        userMap.put(testUser.getUserID(), testUser);
    }


    // gets collection of users
    @GET
    public Collection<User> getUsers() {
        return userMap.values();
    }

    // gets userID
    @GET
    @Path("{userID}")
    public User getUserId(@PathParam("userID") int userID) {
        return userMap.get(userID);
    }

    // Add a new user
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User add){

        userMap.put(add.userID, add);

        String entity = "User Created ";

        return Response.status(Response.Status.CREATED).type(MediaType.TEXT_PLAIN).entity(entity).build();
    }


    // Delete a user
    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(User delete){

        userMap.remove(delete.getUserID());

        String entity = "User Deleted ";


        return Response.status(Response.Status.ACCEPTED).type(MediaType.TEXT_PLAIN).entity(entity).build();


    }

    // Changes a specific users info
    @Path("/update")
    @POST
    public Response updateUser(User update)
    {
        return null;
    }


    // Login a user
    @Path("/login")
    @POST
    public Response loginUser(User login)
    {
        return null;
    }

}


