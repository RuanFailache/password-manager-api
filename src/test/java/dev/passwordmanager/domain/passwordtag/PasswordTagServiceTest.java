package dev.passwordmanager.domain.passwordtag;

import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.utils.mocks.PasswordMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordTagServiceTest {
    @InjectMocks
    private PasswordTagService sut;

    @Mock
    private PasswordTagRepository passwordTagRepository;

    private PasswordMock passwordMock;

    @BeforeEach
    public void setup() {
        passwordMock = new PasswordMock();
    }

    @Test
    void testOnCreatePasswordWhenSuccessful() {
        var password = passwordMock.generate();
        var tag = "tag";

        doReturn(new PasswordTag()).when(passwordTagRepository).save(any(PasswordTag.class));

        sut.onCreatePassword(password, tag);

        verify(passwordTagRepository).save(any(PasswordTag.class));
    }

    @Test
    void testOnCreatePasswordWhenExceptionIsThrown() {
        var password = passwordMock.generate();
        var tag = "tag";

        doThrow(new RuntimeException()).when(passwordTagRepository).save(any(PasswordTag.class));

        assertThrows(InternalServerErrorException.class, () -> sut.onCreatePassword(password, tag));
    }
}