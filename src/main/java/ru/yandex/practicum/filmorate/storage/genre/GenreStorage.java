package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    List<Genre> getAllGenres();

    Genre getGenreById(Integer genreId);

    List<Genre> getGenreByFilm(Integer filmId);

    void assignGenreToTheFilm(Integer filmId, List<Genre> genres);

    void deleteGenreByFilm(Integer filmId);
}
