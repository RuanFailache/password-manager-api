package dev.passwordmanager.utils.mocks;

import dev.passwordmanager.domain.password.Password;

public class PasswordMock extends BaseMock<Password> {
    public Password generate() {
        var password = new Password();
        password.setId(faker.number().randomNumber());
        password.setDescription(faker.lorem().sentence());
        password.setPassword(faker.internet().password());
        return password;
    }
}
