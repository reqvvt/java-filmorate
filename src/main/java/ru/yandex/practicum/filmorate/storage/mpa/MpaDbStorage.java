package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getMpaById(Integer mpaId) {
        return jdbcTemplate.query("SELECT * FROM MPA WHERE RATING_ID = ?",
                new BeanPropertyRowMapper<>(Mpa.class), mpaId).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Рейтинг MPA с id %s не найден", mpaId)));
    }

    @Override
    public List<Mpa> getMpaValues() {
        return jdbcTemplate.query("SELECT * FROM MPA", new BeanPropertyRowMapper<>(Mpa.class));
    }
}
