package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotBlank
    @NonNull
    private String name;

    @NotBlank
    @NonNull
    @Size(max = 200)
    private String description;

    @NonNull
    private LocalDate releaseDate;

    @NonNull
    private Duration duration;
}
