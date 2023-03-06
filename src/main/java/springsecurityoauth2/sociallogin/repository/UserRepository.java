package springsecurityoauth2.sociallogin.repository;

import org.springframework.stereotype.Repository;
import springsecurityoauth2.sociallogin.model.User;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<String, Object> users = new HashMap<>();

    public User findByUsername(String username) {
        Object user = users.get(username);
        return user != null ? (User) user : null;
    }

    public void register(User user) {
        users.put(user.getUsername(), user);
    }
}
