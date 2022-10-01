package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Integer filmId) {
        validateFilmId(filmId);
        return filmStorage.getFilmById(filmId);
    }

    public Film addFilm(Film film) {
        log.debug("Запрос на добавление фильма: {}", film);
        validateReleaseDate(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Запрос на обновление фильма: {}", film);
        validateFilmId(film.getId());
        validateReleaseDate(film);
        return filmStorage.updateFilm(film);
    }

    public void addLike(Integer userId, Integer filmId) {
        validateUserId(userId);
        validateFilmId(filmId);
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(Integer userId, Integer filmId) {
        validateUserId(userId);
        validateFilmId(filmId);
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getTopRatedFilms(Integer count) {
        return filmStorage.getTopRatedFilms(count);
    }

    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Введена некорректная дата релиза");
        }
    }

    private void validateFilmId(Integer id) {
        if (filmStorage.getFilmById(id) == null) {
            throw new NotFoundException(String.format("Фильм c id %s не найден.", id));
        }
    }

    private void validateUserId(Integer id) {
        if (userStorage.getUserById(id) == null) {
            throw new NotFoundException(String.format("Пользователь c id %s не найден.", id));
        }
    }


}