package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;

    @Email(message = "email should be like: example@mail.com")
    @NotBlank(message = "email can't be empty")
    @NotNull(message = "email can't be null")
    private String email;

    @NotBlank(message = "login can't be empty")
    @NotNull(message = "login can't be null")
    private String login;

    @NotBlank(message = "name can't be empty")
    @NotNull(message = "name can't be null")
    private String name;

    @NotNull(message = "birthday can't be null")
    private LocalDate birthday;

    private Set<Integer> friendIds = new HashSet<>();
}
