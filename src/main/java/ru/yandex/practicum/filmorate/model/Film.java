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
    final private String name;

    @NotBlank
    @NonNull
    @Size(max = 200)
    final private String description;

    @NonNull
    final private LocalDate releaseDate;

    @NonNull
    final private Duration duration;
}
