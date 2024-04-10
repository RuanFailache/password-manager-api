package dev.passwordmanager.domain.password;

import dev.passwordmanager.domain.user.User;
import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.utils.exceptions.SimulatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {
    @InjectMocks
    private PasswordService sut;

    @Mock
    private PasswordRepository passwordRepository;

    @Test
    void testCreateWhenSuccessful() {
        var user = new User("user");
        var description = "description";
        var password = "password";

        var expected = new Password(user, description, password);

        doReturn(expected).when(passwordRepository).save(any(Password.class));

        var result = sut.create(user, description, password);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(description, result.getDescription());
        assertEquals(password, result.getPassword());
    }

    @Test
    void testCreateWhenExceptionIsThrown() {
        var user = new User("user");
        var description = "description";
        var password = "password";

        doReturn(new SimulatedException()).when(passwordRepository).save(any(Password.class));

        assertThrows(InternalServerErrorException.class, () -> sut.create(user, description, password));
    }
}