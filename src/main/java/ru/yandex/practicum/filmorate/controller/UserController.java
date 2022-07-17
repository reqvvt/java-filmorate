package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    private final List<User> usersList = new ArrayList<>();
    private int userId;

    @GetMapping
    public List<User> users() {
        log.debug("На данный момент зарегистрировано " + usersList.size() + " пользователей.");
        return usersList;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        return userService.get(userId);
    }
    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        validate(user);
        User saved = userService.save(user);
        log.info("Пользователь " + user.getName() + " добавлен");
        return saved;
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.deleteFriend(userId, friendId);
    }
    @PutMapping
    public User update(@RequestBody @Valid User user) {
        usersList.replaceAll(e -> e.getId() == user.getId() ? user : e);
        log.info("Информация пользователя " + user.getName() + " обновлена");
        return user;
    }
    private void validate(User user) {
        if (user.getLogin().trim().isEmpty()) {
            throw new ValidationException("Логин пуст");
        } else if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен содержать пробел");
        } else if (user.getName().trim().isEmpty()) {
            log.info("Имени присвоено значение логина");
            user.setName(user.getLogin());
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Указана дата рождения из будущего");
        }
    }

}
