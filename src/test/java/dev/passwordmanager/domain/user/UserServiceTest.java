package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.ConflictException;
import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.shared.exceptions.NotFoundException;
import dev.passwordmanager.utils.exceptions.SimulatedException;
import dev.passwordmanager.utils.mocks.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
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

    private UserMock userMock;

    @BeforeEach
    public void setup() {
        userMock = new UserMock();
    }

    @Test
    public void testFindOrThrowWhenFoundUser() {
        var expectedUser = userMock.generate();

        doReturn(Optional.of(expectedUser)).when(userRepository).findById(anyLong());

        var actualUser = sut.findOrThrow(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindOrThrowWhenNotFoundUser() {
        var expectedUser = userMock.generate();

        doReturn(Optional.empty()).when(userRepository).findById(anyLong());

        assertThrows(NotFoundException.class, () -> sut.findOrThrow(expectedUser.getId()));
    }

    @Test
    void testFindOrThrowWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userRepository).findById(anyLong());
        assertThrows(InternalServerErrorException.class, () -> sut.findOrThrow(1L));
    }

    @Test
    void testCreateWhenSuccessful() {
        var expectedUser = userMock.generate();

        doReturn(Optional.empty()).when(userRepository).findByName(anyString());
        doReturn(expectedUser).when(userFactory).create(anyString());
        doReturn(expectedUser).when(userRepository).save(expectedUser);

        var actualUser = sut.create(expectedUser.getName());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testCreateWhenNameAlreadyIsRegistered() {
        var expectedUser = userMock.generate();

        doReturn(Optional.of(expectedUser)).when(userRepository).findByName(anyString());

        assertThrows(ConflictException.class, () -> sut.create(expectedUser.getName()));
    }

    @Test
    void testCreateWhenExceptionThrown() {
        doReturn(Optional.empty()).when(userRepository).findByName(anyString());
        doThrow(new SimulatedException()).when(userFactory).create(anyString());
        assertThrows(InternalServerErrorException.class, () -> sut.create("test"));
    }
}