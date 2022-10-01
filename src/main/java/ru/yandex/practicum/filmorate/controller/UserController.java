package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        log.debug("Получен запрос GET /users");
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        log.info("Получен запрос GET /users по id {}", userId);
        return userService.getUserById(userId);
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        log.info("Получен запрос POST /users");
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Получен запрос PUT (updateUser). Пользователь обновлен {}", user);
        return userService.updateUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable("userId") Integer userId, @PathVariable("friendId") Integer friendId) {
        log.info("Получен запрос PUT (addFriend) для пользователей с id {} и {}", userId, friendId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeFriend(@PathVariable("userId") Integer userId, @PathVariable("friendId") Integer friendId) {
        log.info("Получен запрос DELETE (removeFriend) для пользователя с id {} и его друга с id {}", userId, friendId);
        userService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable("userId") Integer userId) {
        log.info("Получен запрос GET /friends для пользователя с id {}", userId);
        return userService.getUserFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public List<User> getCommonFriends(@PathVariable("userId") Integer userId, @PathVariable("otherUserId") Integer otherUserId) {
        log.debug("Получен запрос GET (getCommonFriends) для пользователей с id {} и {}", userId, otherUserId);
        return userService.getCommonFriends(userId, otherUserId);
    }
}
