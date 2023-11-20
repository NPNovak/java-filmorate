package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.utils.Validation;

import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping()
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    public Film findById(@PathVariable Long filmId) {
        return filmService.getFilmById(filmId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") String count) {
        Integer intCount = Integer.parseInt(count);
        return filmService.getPopularFilms(intCount);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void likeFilm(@PathVariable Long filmId, @PathVariable Long userId) throws ValidationException {
        filmService.likeFilmById(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLikeFromFilm(@PathVariable Long filmId, @PathVariable Long userId) throws ValidationException {
        filmService.deleteLikeFromFilmById(filmId, userId);
    }

    @PostMapping()
    public Film create(@RequestBody Film film) throws ValidationException {
        log.info("Создание фильма: " + film);
        Validation.filmValidation(film);
        log.info("Результат создания фильма: " + film);

        return filmService.create(film);
    }

    @PutMapping()
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Обновление фильма: " + film);
        Validation.filmValidation(film);
        log.info("Результат создания фильма: " + film);

        return filmService.update(film);
    }
}
