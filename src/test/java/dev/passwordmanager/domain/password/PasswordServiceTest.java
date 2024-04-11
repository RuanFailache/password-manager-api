package dev.passwordmanager.domain.password;

import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.shared.exceptions.NotFoundException;
import dev.passwordmanager.utils.exceptions.SimulatedException;
import dev.passwordmanager.utils.mocks.PasswordMock;
import dev.passwordmanager.utils.mocks.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {
    @InjectMocks
    private PasswordService sut;

    @Mock
    private PasswordRepository passwordRepository;

    private PasswordMock passwordMock;
    private UserMock userMock;

    @BeforeEach
    public void setup() {
        passwordMock = new PasswordMock();
        userMock = new UserMock();
    }

    @Test
    void testCreateWhenSuccessful() {
        var user = userMock.generate();
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
        var user = userMock.generate();
        var description = "description";
        var password = "password";

        doThrow(new SimulatedException()).when(passwordRepository).save(any(Password.class));

        assertThrows(InternalServerErrorException.class, () -> sut.create(user, description, password));
    }

    @Test
    void testUpdateWhenSuccessful() {
        var password = passwordMock.generate();
        var description = "description";
        var passwordValue = "password";

        doReturn(password).when(passwordRepository).save(any(Password.class));

        sut.update(password, description, passwordValue);

        verify(passwordRepository, atLeastOnce()).save(any(Password.class));
    }

    @Test
    void testUpdateWhenExceptionIsThrown() {
        var password = passwordMock.generate();
        var description = "description";
        var passwordValue = "password";

        doThrow(new SimulatedException()).when(passwordRepository).save(any(Password.class));

        assertThrows(InternalServerErrorException.class, () -> sut.update(password, description, passwordValue));
    }

    @Test
    public void testFindOrThrowWhenFoundUser() {
        var expectedPassword = passwordMock.generate();

        doReturn(Optional.of(expectedPassword)).when(passwordRepository).findById(anyLong());

        var actualUser = sut.findOrThrow(expectedPassword.getId());

        assertNotNull(actualUser);
        assertEquals(expectedPassword, actualUser);
    }

    @Test
    public void testFindOrThrowWhenNotFoundUser() {
        var expectedPassword = passwordMock.generate();
        doReturn(Optional.empty()).when(passwordRepository).findById(anyLong());
        assertThrows(NotFoundException.class, () -> sut.findOrThrow(expectedPassword.getId()));
    }

    @Test
    public void testFindOrThrowWhenExceptionIsThrown() {
        var expectedPassword = passwordMock.generate();
        doThrow(new SimulatedException()).when(passwordRepository).findById(anyLong());
        assertThrows(InternalServerErrorException.class, () -> sut.findOrThrow(expectedPassword.getId()));
    }
}