package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

@Service
public class FilmService {
    @Autowired
    UserStorage userStorage;
    @Autowired
    FilmStorage filmStorage;

    public Film get(int filmId) {
        final Film film = filmStorage.get(filmId);
        if (film == null) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        return film;
    }

    public Film save(Film film) {
        return filmStorage.save(film);
    }

    public void addLike(int userId, int filmId) {
        User user = userStorage.get(userId);
        Film film = filmStorage.get(filmId);
        // TODO check userId and  friendId
        filmStorage.addLike(film, user);
    }

    public void deleteLike(int userId, int filmId) {
        User user = userStorage.get(userId);
        Film film = filmStorage.get(filmId);
        // TODO check userId and  friendId
        filmStorage.deleteLike(film, user);
    }
}