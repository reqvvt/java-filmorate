package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final List<User> usersList = new ArrayList<>();
    private int id;

    @GetMapping
    public List<User> findAll() {
        log.debug("На данный момент зарегистрировано " + usersList.size() + " пользователей.");
        return usersList;
    }
    @PostMapping
    public User add(@RequestBody @Valid User user) {
        validate(user);
        user.setId(++id);
        usersList.add(user);
        log.info("Пользователь " + user.getName() + " добавлен");
        return user;
    }
    @PutMapping
    public User update(@RequestBody @Valid User user) {
        usersList.replaceAll(e -> e.getId() == user.getId() ? user : e);
        log.info("Информация пользователя " + user.getName() + " обновлена");
        return user;
    }
    void validate(User user) {
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
