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

    /**
     *
     * Return on all users
     */
    @GET
    public Response getUsers(){
        return Response.ok(UserDatabase.getUsers()).build();
    }
    /**
     *
     * Return a specific user by ID
     */
    @GET
    @Path("/{userID}")
    public Response getUserById(@PathParam("userID") Integer id){
        User user = UserDatabase.getUser(id);
        if(user != null){
            return Response.ok(user).entity("User ID found").build();
        }else{
            return Response.status(NOT_FOUND).entity("No such user exists").build();
        }

    }
    /**
     *
     * Create a new user
     */
    @POST
    public Response createUser(User user)throws URISyntaxException {
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
            .entity("User Created").build();
        } else
            return Response.status(NOT_FOUND).entity("A user with this ID already exists").build();

    }
    /**
     *
     * Update a user by ID
     */
    @PUT
    @Path("/{userID}")
    public Response updateUserByID(@PathParam("userID") Integer id, User user) throws URISyntaxException {
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
            return Response.ok(user).entity("User Updated").build();
        } else
            return Response.status(NOT_FOUND).entity("Could not update user, No such ID exists").build();

    }
    /**
     *
     * Delete a user by ID
     */
    @DELETE
    @Path("/{userID}")
    public Response removeUserByID(@PathParam("userID") Integer id){
        User user = UserDatabase.getUser(id);
        if(user != null){
            UserDatabase.removeUser(id);
            return Response.ok().entity("User Deleted").build();
        }else
            return Response.status(NOT_FOUND).entity("No such user exists").build();
    }
    /**
     *
     * Login a user by ID and Password
     */
    @POST
    @Path("/login")
    public Response loginUser(UserLogin userLogin) {
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
            if (client.Validate(userLogin.getPassword(), e.getHashed_Password(), e.getSalt())) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(NO_CONTENT).build();
            }

        } else {
            return Response.status(NO_CONTENT).build();
        }
    }
}


