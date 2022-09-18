package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreStorage = genreStorage;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return jdbcTemplate.query("SELECT * FROM FILMS AS F " +
                "LEFT JOIN FILMS_GENRES FG ON F.FILM_ID = FG.FILM_ID " +
                "LEFT JOIN MPA M ON M.RATING_ID = F.RATING_ID " +
                "LEFT JOIN GENRES G ON G.GENRE_ID = FG.GENRE_ID", new BeanPropertyRowMapper<>(Film.class));
    }

    @Override
    public Film getFilmById(Integer filmId) {
        return jdbcTemplate.query("SELECT * FROM FILMS AS F " +
                "LEFT JOIN FILMS_GENRES FG ON F.FILM_ID = FG.FILM_ID " +
                "LEFT JOIN MPA M ON M.RATING_ID = F.RATING_ID " +
                "LEFT JOIN GENRES G ON G.GENRE_ID = FG.GENRE_ID " +
                "LEFT JOIN LIKES L on F.FILM_ID = L.FILM_ID " +
                "WHERE F.FILM_ID = ?", new BeanPropertyRowMapper<>(Film.class), filmId).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Фильм c id %s не найден.", filmId)));
    }

    @Override
    public Film addFilm(Film film) {
        jdbcTemplate.update("INSERT INTO  FILMS (TITLE, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) VALUES (?, ?, ?, ?, ?)",
                film.getTitle(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        return getFilmById(film.getId());
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("UPDATE FILMS SET TITLE = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING_ID = ? " +
                "WHERE FILM_ID = ?", film.getTitle(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        return getFilmById(film.getId());
    }

    @Override
    public void deleteFilm(Integer filmId) {
        jdbcTemplate.update("DELETE FROM FILMS WHERE FILM_ID = ?", filmId);
    }

    @Override
    public List<Film> getTopRatedFilms(Integer count) {
        List<Integer> filmIds = jdbcTemplate.queryForList("SELECT F.FILM_ID FROM FILMS F " +
                "LEFT JOIN LIKES L on F.FILM_ID = L.FILM_ID " +
                "GROUP BY F.FILM_ID " +
                "ORDER BY COUNT(L.FILM_ID) " +
                "DESC LIMIT ?", Integer.class, count);
        return filmIds.stream().map(this::getFilmById).collect(Collectors.toList());
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        System.out.println(filmId + userId);
        jdbcTemplate.update("INSERT INTO LIKES (FILM_ID, USER_ID) VALUES (?, ?)", filmId, userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?", filmId, userId);
    }

    @Override
    public List<Integer> getFilmLikes(Integer filmId) {
        return jdbcTemplate.queryForList("SELECT USER_ID FROM LIKES WHERE FILM_ID = ?", Integer.class, filmId);
    }
}