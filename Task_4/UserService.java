import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users = new HashMap<>();
    private int nextId = 1;

    public UserService() {
        // Default demo user
        addUser("student1", "pass123", "John Doe", "john@example.com");
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    public boolean addUser(String username, String password, String name, String email) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(nextId++, username, password, name, email));
        return true;
    }

    public boolean updateProfile(User user, String newName, String newEmail) {
        if (newName != null && !newName.isEmpty()) user.setName(newName);
        if (newEmail != null && !newEmail.isEmpty()) user.setEmail(newEmail);
        return true;
    }

    public boolean updatePassword(User user, String currentPassword, String newPassword) {
        if (!user.getPassword().equals(currentPassword)) return false;
        user.setPassword(newPassword);
        return true;
    }
}