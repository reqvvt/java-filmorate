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
    public Collection<Film> getAllFilms() {
        log.debug("Получен запрос GET /films");
        return filmService.getAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable("filmId") Integer filmId) {
        log.info("Получен запрос GET /films по id {}", filmId);
        return filmService.getFilmById(filmId);
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("Получен запрос POST /films");
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.debug("Получен запрос PUT (updateFilm). Фильм обновлен {}", film);
        return filmService.updateFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable("filmId") Integer filmId, @PathVariable("userId") Integer userId) {
        log.debug("Получен запрос PUT (addLike). От пользователя {} к фильму {}", userId, filmId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable("filmId") Integer filmId, @PathVariable("userId") Integer userId) {
        log.info("Получен запрос DELETE (removeLike). От пользователя {} к фильму {}", userId, filmId);
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopRatedFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        log.info("Получен запрос GET для /films/popular (getTopRatedFilms)");
        return filmService.getTopRatedFilms(count);
    }
}
