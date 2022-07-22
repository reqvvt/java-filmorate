package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonIgnore
    private Set<Integer> friendIds = new HashSet<>();
}
