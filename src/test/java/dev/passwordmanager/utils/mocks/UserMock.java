package dev.passwordmanager.utils.mocks;

import com.github.javafaker.Faker;
import dev.passwordmanager.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMock extends BaseMock<User> {
    private static final Faker faker = new Faker();

    public User generate() {
        var user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(faker.name().username());
        return user;
    }

    public List<User> generateList(int count) {
        var users = new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            var user = this.generate();
            users.add(user);
        }
        return users;
    }
}
