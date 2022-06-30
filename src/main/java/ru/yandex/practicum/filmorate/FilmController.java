package ru.yandex.practicum.filmorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;;

import java.time.LocalDate;
import java.util.HashSet;

public class FilmController {

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private final HashSet<Film> films = new HashSet<>();

    @GetMapping
    public HashSet<Film> returnFilms() {
        log.debug("На данный момент зарегистрировано " + films.size() + " фильмов.");
        return films;
    }
    @PostMapping
    Film createFilm(@RequestBody Film film) {
        validate(film);
        film.setId(film.getId() + 1);
        log.info("Фильм " + film.getName() + " зарегистрирован");
        return film;
    }
    @PutMapping
    void updateFilm(@RequestBody Film film) {
        validate(film);
        log.info("Информация фильма " + film.getName() + " обновлена");
        films.add(film);
    }
    void validate(Film film) {
        if (film.getName().isEmpty()) {
            throw new ValidationException("invalid name");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("invalid description");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("invalid releaseDate");
        } else if (film.getDuration() <= 0) {
            throw new ValidationException("invalid duration");
        }
    }
}
