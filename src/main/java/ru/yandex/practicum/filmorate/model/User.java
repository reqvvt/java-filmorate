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
    private String email;

    @NotBlank
    @NonNull
    private String login;

    @NonNull
    private String name;

    @NonNull
    private LocalDate birthday;
}
