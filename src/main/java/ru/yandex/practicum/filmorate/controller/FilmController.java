package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Validation;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long generatedId;

    @GetMapping()
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping()
    public Film create(@RequestBody Film film) throws ValidationException {
        log.info("Создание фильма: " + film);

        Validation.filmValidation(film);

        film.setId(++generatedId);
        films.put(film.getId(), film);

        log.info("Результат создания фильма: " + film);
        return film;
    }

    @PutMapping()
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Обновление фильма: " + film);
        if (!films.containsKey(film.getId())) {
            log.info("Такого фильма не существует");
            throw new ValidationException("Такого фильма не существует");
        }

        Validation.filmValidation(film);

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        }

        log.info("Результат создания фильма: " + film);
        return film;
    }
}
