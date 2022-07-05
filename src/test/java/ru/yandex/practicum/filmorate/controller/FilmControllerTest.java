package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {
    @Autowired
    private FilmController filmController;

    @Test
    void addWithoutEmptyName() {
        Film film = new Film("Film", "Description",
                LocalDate.of(2001, 12, 12), Duration.ofHours(2));
        Film response = filmController.add(film);

        Assertions.assertEquals(response, film);
    }

    @Test
    void notAddWithEmptyName() {
        final ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> filmController.add(new Film("", "Description",
                        LocalDate.of(2001, 12, 12), Duration.ofHours(2))));

        Assertions.assertEquals(exception.getMessage(), "add.film.name: не должно быть пустым");
    }

    @Test
    void updateWithoutEmptyName() {
        Film film = new Film("Film", "Description",
                LocalDate.of(2001, 12, 12), Duration.ofHours(2));
        Film response = filmController.add(film);

        Film updatedFilm = new Film("Film 1", "Description",
                LocalDate.of(2001, 12, 12), Duration.ofHours(2));
        updatedFilm.setId(response.getId());
        Film updateResponse = filmController.update(updatedFilm);

        Assertions.assertEquals(updateResponse, updatedFilm);
    }

    @Test
    void notUpdateWithEmptyName() {
        Film film = new Film("Film", "Description",
                LocalDate.of(2001, 12, 12), Duration.ofHours(2));
        filmController.add(film);

        final ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> filmController.add(new Film("", "Description",
                        LocalDate.of(2001, 12, 12), Duration.ofHours(2))));

        Assertions.assertEquals(exception.getMessage(), "add.film.name: не должно быть пустым");
    }

    @Test
    void addWithDescriptionLengthLess200chars() {
        Film film = new Film("Film", "Автослесарь Николай Сергеев живет со второй женой Лилей и " +
                "сыном от первого брака в маленьком северном городке на берегу моря.",
                LocalDate.of(2001, 12, 12), Duration.ofHours(2));
        Film response = filmController.add(film);

        Assertions.assertEquals(response, film);
    }

    @Test
    void notAddWithDescriptionLengthMore200chars() {
        final ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> filmController.add(new Film("Film", "Автослесарь Николай Сергеев живет со второй женой Лилей и " +
                        "сыном от первого брака в маленьком северном городке на берегу моря. Нечистый на руку мэр города " +
                        "Шелевят пытается изъять его дом и землю для собственных нужд. После двух проигранных судов Николай " +
                        "обращается за помощью к бывшему сослуживцу Дмитрию",
                        LocalDate.of(2001, 12, 12), Duration.ofHours(2))));

        Assertions.assertEquals(exception.getMessage(), "add.film.description: размер должен находиться в диапазоне от 0 до 200");
    }

    @Test
    void notAddWithEmptyDescription() {
        final ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> filmController.add(new Film("Film", "",
                        LocalDate.of(2001, 12, 12), Duration.ofHours(2))));

        Assertions.assertEquals(exception.getMessage(), "add.film.description: не должно быть пустым");
    }

    @Test
    void addWithReleaseDateAfter1895_12_28() {
        Film film = new Film("Film", "Description",
                LocalDate.of(1895, 12, 29), Duration.ofHours(2));
        Film response = filmController.add(film);

        Assertions.assertEquals(response, film);
    }

    @Test
    void notAddWithReleaseDateBefore1895() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.add(new Film("Film", "Description",
                        LocalDate.of(1895, 12, 27), Duration.ofHours(2))));

        Assertions.assertEquals(exception.getMessage(), "Введена неверная дата релиза");
    }

    @Test
    void addWithPositiveDuration() {
        Film film = new Film("Film", "Description",
                LocalDate.of(1895, 12, 29), Duration.ofSeconds(1));
        Film response = filmController.add(film);

        Assertions.assertEquals(response, film);

    }

    @Test
    void notAddWithNegativeDuration() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.add(new Film("Film", "Description",
                        LocalDate.of(1895, 12, 29), Duration.ofSeconds(-1))));

        Assertions.assertEquals(exception.getMessage(), "Продолжительность не может быть отрицательной или равно нулю");
    }
}

