package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.ConflictException;
import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.utils.exceptions.SimulatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFactory userFactory;

    @Test
    void testCreateWhenSuccessful() {
        var expectedUser = new User();

        doReturn(Optional.empty()).when(userRepository).findByName(anyString());
        doReturn(expectedUser).when(userFactory).create(anyString());
        doReturn(expectedUser).when(userRepository).save(expectedUser);

        var actualUser = sut.create("test");

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testCreateWhenNameAlreadyIsRegistered() {
        var testUsername = "test";

        var expectedUser = new User();
        expectedUser.setName(testUsername);

        doReturn(Optional.of(expectedUser)).when(userRepository).findByName(anyString());

        assertThrows(ConflictException.class, () -> sut.create(testUsername));
    }

    @Test
    void testCreateWhenExceptionThrown() {
        doReturn(Optional.empty()).when(userRepository).findByName(anyString());
        doThrow(new SimulatedException()).when(userFactory).create(anyString());
        assertThrows(InternalServerErrorException.class, () -> sut.create("test"));
    }
}