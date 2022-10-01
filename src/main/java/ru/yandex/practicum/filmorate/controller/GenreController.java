package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{genreId}")
    public Genre getGenreById(@PathVariable("genreId") Integer genreId) {
        log.info("Получен запрос GET /genres по id {}", genreId);
        return genreService.getGenreById(genreId);
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        log.debug("Получен запрос GET /genres");
        return genreService.getAllGenres();
    }
}
