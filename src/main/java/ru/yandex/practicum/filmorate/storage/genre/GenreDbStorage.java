package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * FROM GENRES", new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        return jdbcTemplate.query("SELECT * FROM GENRES WHERE GENRE_ID = ?",
                new BeanPropertyRowMapper<>(Genre.class), genreId).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Жанр с id %s не найден", genreId)));
    }

    @Override
    public List<Genre> getGenreByFilm(Integer filmId) {
        return jdbcTemplate.query("SELECT * FROM GENRES LEFT JOIN FILMS_GENRES ON GENRES.GENRE_ID = FILMS_GENRES.GENRE_ID " +
                "WHERE FILM_ID = ?", new BeanPropertyRowMapper<>(Genre.class), filmId);
    }

    @Override
    public void assignGenreToTheFilm(Integer filmId, List<Genre> genres) {
        for (Genre genre : genres) {
            genre.setName(getGenreById(genre.getId()).getName());
            jdbcTemplate.update("UPDATE FILMS_GENRES SET FILM_ID = ?, GENRE_ID = ?", filmId, genre.getId());
        }
    }

    @Override
    public void deleteGenreByFilm(Integer filmId) {
        jdbcTemplate.update("DELETE FROM FILMS_GENRES WHERE FILM_ID = ?", filmId);
    }
}
