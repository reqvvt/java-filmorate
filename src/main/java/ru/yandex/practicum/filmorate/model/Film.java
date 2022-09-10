package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {
    private int id;
    @NotBlank(message = "title can't be empty")
    @NotNull(message = "title can't be null")
    private String title;

    @NotBlank(message = "description can't be empty")
    @NotNull(message = "description can't be null")
    @Size(min = 1, max = 200)
    private String description;

    @NotNull(message = "release date can't be null")
    private LocalDate releaseDate;

    @Positive(message = "duration should be more than 0")
    private Duration duration;

    private Mpa mpa;
    private List<Genre> genres;

    private Set<Integer> likes = new HashSet<>();
}
