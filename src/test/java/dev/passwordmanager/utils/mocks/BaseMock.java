package dev.passwordmanager.utils.mocks;

import com.github.javafaker.Faker;
import dev.passwordmanager.domain.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

abstract public class BaseMock<T> {
    protected static final Faker faker = new Faker();

    abstract public T generate();
}
