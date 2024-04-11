package dev.passwordmanager.utils.mocks;

import dev.passwordmanager.domain.user.User;

public class UserMock extends BaseMock<User> {
    public User generate() {
        var user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(faker.name().username());
        return user;
    }
}
