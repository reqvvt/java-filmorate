package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
