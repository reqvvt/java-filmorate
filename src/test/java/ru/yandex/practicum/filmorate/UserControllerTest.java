package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    @Test
    void validateBirthday() {
        final UserController uc = new UserController();
        final User user = new User();
        user.setBirthday(LocalDate.MAX);
        assertThrows(RuntimeException.class, () -> uc.validate(user));
    }

}
