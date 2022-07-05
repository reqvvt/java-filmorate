package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserController userController;

    @Test
    void addFilledAndValidEmail() {
        User user = new User("user@gmail.com", "user", "Evgeniy",
                LocalDate.of(1996, 1, 1));
        User response = userController.add(user);

        Assertions.assertEquals(user, response);
    }

    @Test
    void notAddWithUnfilledEmail() {
        final ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> userController.add(new User("", "user", "Evgeniy",
                        LocalDate.of(1996, 1, 1))));

        Assertions.assertEquals(exception.getMessage(), "add.user.email: не должно быть пустым");
    }

    @Test
    void notAddIfBirthdayInFuture() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> userController.add(new User("user@gmail.com", "user", "Evgeniy",
                        LocalDate.of(2050, 1, 1))));

        Assertions.assertEquals(exception.getMessage(), "Указана дата рождения из будущего");
    }

    @Test
    void addWithoutAtInEmail() {
        final ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> userController.add(new User("privet.com", "user", "Evgeniy",
                        LocalDate.of(1996, 1, 1))));

        Assertions.assertEquals(exception.getMessage(), "add.user.email: должно иметь формат адреса электронной почты");
    }

    @Test
    void addWithFilledLoginWithoutSpaces() {
        User user = new User("user@gmail.com", "user", "Evgeniy",
                LocalDate.of(1996, 1, 1));
        User response = userController.add(user);

        Assertions.assertEquals(user, response);
    }

    @Test
    void notAddWithEmptyLogin() {
        final ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> userController.add(new User("user@gmail.com", "", "Evgeniy",
                        LocalDate.of(1996, 1, 1))));

        Assertions.assertEquals(exception.getMessage(), "add.user.login: не должно быть пустым");
    }

    @Test
    void notAddWithSpacesInLogin() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> userController.add(new User("user@gmail.com", "user 1", "Evgeniy",
                        LocalDate.of(1996, 1, 1))));

        Assertions.assertEquals(exception.getMessage(), "Логин не должен содержать пробел");
    }

    @Test
    void addLoginAsNameIfNameIsEmpty() {
        User user = new User("user@gmail.com", "user", "",
                LocalDate.of(1996, 1, 1));
        User response = userController.add(user);
        String savedName = response.getName();
        String login = response.getLogin();

        Assertions.assertEquals(login, savedName);
    }
}