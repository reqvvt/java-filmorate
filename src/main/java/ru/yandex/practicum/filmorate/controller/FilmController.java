package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getAll() {
        log.debug("На данный момент зарегистрировано " + filmService.getAll().size() + " фильмов.");
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable Integer filmId) {
        log.info("Фильм " + filmService.getFilm(filmId).getTitle() + " возвращён");
        return filmService.getFilm(filmId);
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("Фильм " + film.getTitle() + " добавлен");
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.info("Фильм " + film.getTitle() + " обновлён");
        return filmService.updateFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Лайк к фильму " + filmService.getFilm(filmId).getTitle() + " добавлен");
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Лайк к фильму " + filmService.getFilm(filmId).getTitle() + " удалён");
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> getPopularFilms(@PathVariable(required = false) @RequestParam(defaultValue = "10") Integer count) {
        log.info("Список популярных фильмов возвращен");
        return filmService.getPopularFilms(count);
    }
}
