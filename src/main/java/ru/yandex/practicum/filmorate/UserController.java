package ru.yandex.practicum.filmorate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("users")
    User getUser() {
        return new User(1, "User");
    }

    @GetMapping("films")
    User getFilms() {
        return new User(1, "Film");
    }

}
