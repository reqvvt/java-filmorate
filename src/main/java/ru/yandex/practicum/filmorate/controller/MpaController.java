package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/{mpaId}")
    public Mpa getMpaById(@PathVariable("mpaId") Integer mpaId) {
        log.info("Получен запрос GET /mpa по id {}", mpaId);
        return mpaService.getMpaById(mpaId);
    }

    @GetMapping
    public List<Mpa> getMpaValues() {
        log.debug("Получен запрос GET /mpa");
        return mpaService.getMpaValues();
    }
}
