package ie.gmit.ds;
// https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
// Adapted from https://github.com/john-french/artistAPI-dropwizard

import ie.gmit.ds.api.User;
import ie.gmit.ds.api.UserLogin;
import ie.gmit.ds.client.UserClient;
import ie.gmit.ds.database.UserDatabase;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAPIResource {

    //private HashMap<Integer, User> userMap = new HashMap<>();

    private final Validator validator;
    private UserClient client;

    private final String HOST = "localhost";
    private final int PORT = 50551;

    public UserAPIResource(Validator validator){
        this.validator = validator;
        this.client = new UserClient(HOST, PORT);
    }

    public UserAPIResource(Validator validator, UserClient client){
        this.validator = validator;
        this.client = client;
    }

    @GET
    public Response getUsers(){
        //Return All users
        return Response.ok(UserDatabase.getUsers()).build();
    }
    @GET
    @Path("/{userID}")
    public Response getUserById(@PathParam("userID") Integer id){
        User user = UserDatabase.getUser(id);
        if(user != null){
            return Response.ok(user).build();
        }else{
            return Response.status(NOT_FOUND).build();
        }

    }

    @POST
    public Response createUser(User user)throws URISyntaxException {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User u = UserDatabase.getUser(user.getUserID());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (u == null) {
            client.Hash(user);
            UserDatabase.createUser(user.getUserID(), user);

            return Response.created(new URI("/users/" + user.getUserID()))
                    .build();
        } else
            return Response.status(NOT_FOUND).build();

    }

    @PUT
    @Path("/{userID}")
    public Response updateUserByID(@PathParam("userID") Integer id, User user) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User e = UserDatabase.getUser(user.getUserID());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            UserDatabase.updateUser(id, user);
            client.Hash(user);
            return Response.ok(user).build();
        } else
            return Response.status(NOT_FOUND).build();

    }



    @DELETE
    @Path("/{userID}")
    public Response removeUserByID(@PathParam("userID") Integer id){
        User user = UserDatabase.getUser(id);
        if(user != null){
            UserDatabase.removeUser(id);
            return Response.ok().build();
        }else
            return Response.status(NOT_FOUND).build();
    }

    @POST
    @Path("/login")
    public Response loginUser(UserLogin userLogin) {
        // validation
        Set<ConstraintViolation<UserLogin>> violations = validator.validate(userLogin);
        User e = UserDatabase.getUser(userLogin.getUserID());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<UserLogin> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            if (client.Validate(userLogin.getPassword(), e.getHashedPassword(), e.getSalt())) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(NO_CONTENT).build();
            }

        } else {
            return Response.status(NO_CONTENT).build();
        }


    }

    // gets collection of users
  /*  @GET
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
    } */

}


