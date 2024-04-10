package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.utils.exceptions.SimulatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        doReturn(expectedUser).when(userFactory).create(anyString());
        doReturn(expectedUser).when(userRepository).save(expectedUser);

        var actualUser = sut.create("test");

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testCreateWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userFactory).create(anyString());
        assertThrows(InternalServerErrorException.class, () -> sut.create("test"));
    }
}