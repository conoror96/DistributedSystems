package ie.gmit.ds.database;


import ie.gmit.ds.api.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserDatabase {

    public static HashMap<Integer, User> userMap = new HashMap<>();

    static {
        userMap.put(1, new User(1, "Conor O'Reilly", "ckkor@gmail.com", "pass123"));
        userMap.put(2, new User(2, "Ronoc YlleiR'O", "rokkoc@gmail.com", "pass321"));
    }

    public static List<User> getUsers(){
        return new ArrayList<User>(userMap.values());
    }

    public static User getUser(Integer userID){
        return userMap.get(userID);
    }

    public static void updateUser(Integer id, User user){
        userMap.put(id, user);
    }

    public static void createUser(Integer id, User user){
        userMap.put(id, user);
    }

    public static void removeUser(Integer id){
        userMap.remove(id);
    }
}