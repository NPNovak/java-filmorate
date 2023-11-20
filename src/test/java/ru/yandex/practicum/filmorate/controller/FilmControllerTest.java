package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {
    private static final  LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @BeforeEach
    void setUp() {
    }

    @Test()
    void emptyFilmName() {
        Film film = Film.builder()
                .description("test")
                .releaseDate(LocalDate.of(1900, 1, 1))
                .duration(100)
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if (film.getName() == null || film.getName().isBlank()) {
                throw new ValidationException("У фильма нет названия");
            }
        });

    }

    @Test()
    void descriptionLengthMoreThan200() {
        Film film = Film.builder()
                .name("test")
                .description("fasfaskfaskfaskfaskfaslfasfsafasfasfaskfaskfaskfaskfaslfasfsafasfasfaskfaskfaskfaskfas" +
                        "lfasfsafasfasfaskfaskfaskfaskfaslfasfsafasfasfaskfaskfaskfaskfaslfasfsafasfasfaskfas" +
                        "kfaskfaskfaslfasfsafas412241241")
                .releaseDate(LocalDate.of(1900, 1, 1))
                .duration(100)
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if (film.getDescription().length() > 200) {
                throw new ValidationException("Длина описания больше 200 символов");
            }
        });

    }

    @Test()
    void dateBeforeReleaseDate() {
        Film film = Film.builder()
                .description("test")
                .releaseDate(LocalDate.of(1894, 1, 1))
                .duration(100)
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
                throw new ValidationException("Дата релиза раньше даты создания кино");
            }
        });
    }

    @Test()
    void negativeDuration() {
        Film film = Film.builder()
                .description("test")
                .releaseDate(LocalDate.of(1900, 1, 1))
                .duration(-1)
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if (film.getDuration() == null || film.getDuration() <= 0) {
                throw new ValidationException("Продолжительность фильма должна быть положительной");
            }
        });
    }
}
