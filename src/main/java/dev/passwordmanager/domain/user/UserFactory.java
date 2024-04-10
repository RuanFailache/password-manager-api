package dev.passwordmanager.domain.user;

import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User create(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }
}
