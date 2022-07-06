package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private List<Film> filmsList = new ArrayList<>();
    private int id;

    @GetMapping
    public List<Film> films() {
        log.debug("На данный момент зарегистрировано " + filmsList.size() + " фильмов.");
        return filmsList;
    }
    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        validate(film);
        film.setId(++id);
        filmsList.add(film);
        log.info("Фильм " + film.getName() + " зарегистрирован");
        return film;
    }
    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        filmsList.replaceAll(e -> e.getId() == film.getId() ? film : e);
        log.info("Информация фильма " + film.getName() + " обновлена");
        return film;
    }
    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Введена неверная дата релиза");
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность не может быть отрицательной или равно нулю");
        }
    }
}
