package ru.yandex.practicum.filmorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

@RestController
@RequestMapping("users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final HashSet<User> users = new HashSet<>();

    @GetMapping
    public HashSet<User> returnUsers() {
        log.debug("На данный момент зарегистрировано " + users.size() + " пользователей.");
        return users;
    }
    @PostMapping
    User createUser(@RequestBody User user) {
        validate(user);
        user.setId(user.getId() + 1);
        log.info("Пользователь " + user.getName() + " зарегистрирован");
        return user;
    }
    @PutMapping
    void updateUser(@RequestBody User user) {
        validate(user);
        log.info("Информация пользователя " + user.getName() + " обновлена");
        users.add(user);
    }
    void validate(User user) {
        if (user.getEmail().isEmpty()) {
            throw new ValidationException("invalid e-mail");
        } else if (!(user.getEmail().contains("@"))) {
            throw new ValidationException("invalid e-mail");
        } else if (user.getLogin().trim().isEmpty()) {
            throw new ValidationException("invalid login");
        } else if (user.getName().trim().isEmpty()) {
            user.setName(user.getLogin());
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("invalid birthday");
        }
    }

}
