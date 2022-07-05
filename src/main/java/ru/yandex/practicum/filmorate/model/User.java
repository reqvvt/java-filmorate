package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    private int id;

    @Email
    @NotBlank
    @NonNull
    String email;

    @NotBlank
    @NonNull
    String login;

    @NonNull
    String name;

    @NonNull
    LocalDate birthday;
}
