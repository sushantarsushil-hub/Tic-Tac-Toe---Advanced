import java.io.*;
import java.util.HashMap;


public class UserManager {

    private static final String FILE_NAME = "users.dat";
    private HashMap<String, User> users;
    private User currentUser; 

    public UserManager() {
        users = loadUsers();
    }

    
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        users.put(username, new User(username, password));
        saveUsers();
        return true;
    }

    
    public boolean login(String username, String password) {
        User u = users.get(username);
        if (u != null && u.password.equals(password)) {
            currentUser = u;
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public HashMap<String, User> getAllUsers() {
        return users;
    }

    
    public void saveUsers() {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }

    }

    
    @SuppressWarnings("unchecked")
    private HashMap<String, User> loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (HashMap<String, User>) in.readObject();
        } 
        
        catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
            return new HashMap<>();
        }

    }
    
}