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
    public Collection<User> getAll() {
        log.debug("На данный момент зарегистрировано " + userService.getAllUsers().size() + " пользователей.");
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId) {
        log.info("Пользователь " + userService.getUser(userId).getName() + " возвращен");
        return userService.getUser(userId);
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        log.info("Пользователь " + user.getName() + " добавлен");
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Пользователь " + user.getName() + " обновлён");
        return userService.updateUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Друг " + userService.getUser(friendId).getName() + " добавлен");
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Друг " + userService.getUser(friendId).getName() + " удалён");
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getUserFriends(@PathVariable Integer userId) {
        log.info("У вас " + userService.getUser(userId).getFriendIds().size() + " друзей");
        return userService.getUserFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{friendId}")
    public List<User> getCommonFriends(@PathVariable Integer userId, @PathVariable Integer friendId) {
        log.info("У вас " + userService.getUser(userId).getFriendIds().size() + " общих друзей");
        return userService.getCommonFriends(userId, friendId);
    }
}
