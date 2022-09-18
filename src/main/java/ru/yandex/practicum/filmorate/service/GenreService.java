package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
@Slf4j
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenreById(Integer genreId) {
        validateGenreId(genreId);
        return genreStorage.getGenreById(genreId);
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    private void validateGenreId(Integer genreId) {
        if (genreStorage.getGenreById(genreId) == null) {
            throw new NotFoundException(String.format("Жанр с id %s не найден", genreId));
        }
    }
}
