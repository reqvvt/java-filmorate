package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Collection<Film> getAllFilms();

    Film getFilmById(Integer filmId);

    Film addFilm(@Valid @RequestBody Film film);

    Film updateFilm(@Valid @RequestBody Film film);

    void deleteFilm(Integer id);

    List<Film> getTopRatedFilms(Integer count);

    void addLike(Integer filmId, Integer userId);

    void removeLike(Integer filmId, Integer userId);

    List<Integer> getFilmLikes(Integer filmId);
}
