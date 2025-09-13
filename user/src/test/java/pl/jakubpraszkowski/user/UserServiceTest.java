package pl.jakubpraszkowski.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.jakubpraszkowski.user.entity.User;
import pl.jakubpraszkowski.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .firstName("testuser")
                .lastName("testuser")
                .email("test@user")
                .build();
    }

    @Test
    public void registerUserTest() {
        //given
    }
}
