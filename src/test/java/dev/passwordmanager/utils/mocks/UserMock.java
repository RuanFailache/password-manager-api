package dev.passwordmanager.utils.mocks;

import com.github.javafaker.Faker;
import dev.passwordmanager.domain.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class UserMock extends BaseMock<User> {
    private static final Faker faker = new Faker();

    public User generate() {
        var user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(faker.name().username());
        return user;
    }
}
