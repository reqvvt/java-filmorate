package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class FilmDbStorage implements FilmStorage {
    private int id;
    private final Map<Integer, Film> filmsMap = new HashMap<>();

    public Integer generateId() {
        return ++id;
    }

    @Override
    public Collection<Film> getAll() {
        return filmsMap.values();
    }

    @Override
    public Film getFilm(Integer filmId) {
        return filmsMap.get(filmId);
    }

    @Override
    public Film addFilm(Film film) {
        Integer filmId = generateId();
        film.setId(filmId);
        filmsMap.put(filmId, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        filmsMap.put(film.getId(), film);
        return film;
    }
}