package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long generatedId;
    private static final  LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @GetMapping()
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping()
    public Film create(@RequestBody Film film) throws ValidationException {
        log.info("Создание фильма: " + film);
        if (film.getName() == null || film.getName().isBlank()) {
            log.info("У фильма нет названия");
            throw new ValidationException("У фильма нет названия");
        }

        if (film.getDescription().length() > 200) {
            log.info("Длина описания больше 200 символов");
            throw new ValidationException("Длина описания больше 200 символов");
        }

        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.info("Дата релиза раньше даты создания кино");
            throw new ValidationException("Дата релиза раньше даты создания кино");
        }

        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.info("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }

        film.setId(++generatedId);
        films.put(film.getId(), film);

        log.info("Результат создания фильма: " + film);
        return film;
    }

    @PutMapping()
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Создание фильма: " + film);
        if (!films.containsKey(film.getId())) {
            log.info("Такого фильма не существует");
            throw new ValidationException("Такого фильма не существует");
        }

        if (film.getName() == null || film.getName().isBlank()) {
            log.info("У фильма нет названия");
            throw new ValidationException("У фильма нет названия");
        }

        if (film.getDescription().length() > 200) {
            log.info("Длина описания больше 200 символов");
            throw new ValidationException("Длина описания больше 200 символов");
        }

        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.info("Дата релиза раньше даты создания кино");
            throw new ValidationException("Дата релиза раньше даты создания кино");
        }

        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.info("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        }

        log.info("Результат создания фильма: " + film);
        return film;
    }
}
