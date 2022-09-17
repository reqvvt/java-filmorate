package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;
import java.util.List;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreStorage = genreStorage;
    }

    //TODO сделать методы
    @Override
    public Collection<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film getFilmById(Integer filmId) {
        return null;
    }

    @Override
    public Film addFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public void deleteFilm(Integer id) {

    }

    @Override
    public List<Film> getTopRatedFilms(Integer count) {
        return null;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {

    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {

    }

    @Override
    public List<Integer> getFilmLikes(Integer filmId) {
        return null;
    }
}