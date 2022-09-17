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
import java.util.stream.Collectors;

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

    public Collection<Film> getAll() {
        return filmStorage.getAllFilms();
    }

    public Film getFilm(Integer filmId) throws NotFoundException {
        return filmStorage.getFilmById(filmId);
    }

    public Film addFilm(Film film) {
        validate(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(@Valid @RequestBody Film film) {
        validate(film);
        return filmStorage.updateFilm(film);
    }

    public void addLike(Integer filmId, Integer userId) {
        if (userId < 0 || userStorage.getUserById(userId) == null) {
            throw new NotFoundException(String.format("User id = %s не найден", userId));
        }
        if (filmStorage.getFilmById(filmId) == null) {
            throw new NotFoundException(String.format("Film id = %s не найден", filmId));
        }
        filmStorage.getFilmById(filmId).getLikes().add(userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (userId < 0 || userStorage.getUserById(userId) == null) {
            throw new NotFoundException(String.format("User id = %s не найден", userId));
        }
        if (filmStorage.getFilmById(filmId) == null) {
            throw new NotFoundException(String.format("Film id = %s не найден", filmId));
        }
        filmStorage.getFilmById(filmId).getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getAllFilms()
                          .stream()
                          .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                          .limit(count)
                          .collect(Collectors.toList());
    }

    private void validate(Film film) {
        if (film.getReleaseDate()
                .isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Введена неверная дата релиза");
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность не может быть отрицательной или равна нулю");
        }
    }
}