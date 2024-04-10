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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository userRepository;

    private UserMock userMock;

    @BeforeEach
    public void setup() {
        userMock = new UserMock();
    }

    @Test
    void testFindAllWhenSuccessful() {
        var expectedUsers = userMock.generateList(5);

        doReturn(expectedUsers).when(userRepository).findAll();

        var actualUsers = sut.findAll();

        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testFindAllWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userRepository).findAll();
        assertThrows(InternalServerErrorException.class, () -> sut.findAll());
    }

    @Test
    void testFindAllPaginatedWhenSuccessful() {
        var mockedPageable = Pageable.unpaged();
        var expectedUsers = new PageImpl<>(userMock.generateList(5));

        doReturn(expectedUsers).when(userRepository).findAll(any(Pageable.class));

        var actualUsers = sut.findAll(mockedPageable);

        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testFindAllPaginatedWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userRepository).findAll(any(Pageable.class));
        assertThrows(InternalServerErrorException.class, () -> sut.findAll(any(Pageable.class)));
    }

    @Test
    void testFindWhenSuccessful() {
        var expectedUser = userMock.generate();

        doReturn(Optional.of(expectedUser)).when(userRepository).findById(anyLong());

        var actualUser = sut.find(expectedUser.getId());

        assertNotNull(actualUser);
        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());
    }

    @Test
    void testFindWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userRepository).findById(anyLong());
        assertThrows(InternalServerErrorException.class, () -> sut.find(1L));
    }

    @Test
    public void testFindOrThrowWhenFoundUser() {
        var expectedUser = userMock.generate();

        doReturn(Optional.of(expectedUser)).when(userRepository).findById(anyLong());

        var actualUser = sut.findOrThrow(expectedUser.getId());

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindOrThrowWhenNotFoundUser() {
        var expectedUser = userMock.generate();
        doReturn(Optional.empty()).when(userRepository).findById(anyLong());
        assertThrows(NotFoundException.class, () -> sut.findOrThrow(expectedUser.getId()));
    }

    @Test
    void testCreateWhenSuccessful() {
        var expectedUser = userMock.generate();

        doReturn(expectedUser).when(userRepository).save(any(User.class));

        var actualUser = sut.create(expectedUser.getName());

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testCreateWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userRepository).save(any(User.class));
        assertThrows(InternalServerErrorException.class, () -> sut.create("test"));
    }

    @Test
    void testUpdateWhenSuccessful() {
        var expectedUser = userMock.generate();

        doReturn(Optional.of(expectedUser)).when(userRepository).findById(anyLong());

        var newName = "test";
        expectedUser.setName(newName);

        doReturn(expectedUser).when(userRepository).save(expectedUser);

        var actualUser = sut.update(expectedUser.getId(), expectedUser.getName());

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testUpdateWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userRepository).findById(anyLong());
        assertThrows(InternalServerErrorException.class, () -> sut.update(1L, "test"));
    }

    @Test
    void testExistsByNameWhenFoundUser() {
        var expectedUser = userMock.generate();
        doReturn(Optional.of(expectedUser)).when(userRepository).findByName(anyString());
        assertThrows(ConflictException.class, () -> sut.existsByName(expectedUser.getName()));
    }

    @Test
    void testExistsByNameWhenNotFoundUser() {
        doReturn(Optional.empty()).when(userRepository).findByName(anyString());
        sut.existsByName("test");
    }

    @Test
    void testExistsByNameWhenExceptionThrown() {
        doThrow(new SimulatedException()).when(userRepository).findByName(anyString());
        assertThrows(InternalServerErrorException.class, () -> sut.existsByName("test"));
    }
}