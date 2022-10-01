package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@Slf4j
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa getMpaById(Integer mpaId) {
        validateMpaId(mpaId);
        return mpaStorage.getMpaById(mpaId);
    }

    public List<Mpa> getMpaValues() {
        return mpaStorage.getMpaValues();
    }

    private void validateMpaId(Integer mpaId) {
        if (mpaStorage.getMpaById(mpaId) == null) {
            throw new NotFoundException(String.format("Рейтинг MPA с id %s не найден", mpaId));
        }
    }
}
