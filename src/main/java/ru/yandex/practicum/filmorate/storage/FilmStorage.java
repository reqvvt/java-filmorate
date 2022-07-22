package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getAll();

    Film getFilm(Integer filmId);

    Film addFilm(@Valid @RequestBody Film film);

    Film updateFilm(@Valid @RequestBody Film film);
}
