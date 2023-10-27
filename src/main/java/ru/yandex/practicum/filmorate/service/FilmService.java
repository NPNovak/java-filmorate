package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.IFilmService;
import ru.yandex.practicum.filmorate.model.IUserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService implements IFilmService {
    private final FilmStorage inMemoryFilmStorage;
    private final IUserService userService;

    public Collection<Film> getAll() {
        return inMemoryFilmStorage.getAll().values();
    }

    public Map<Long, Film> getFilms() {
        return inMemoryFilmStorage.getAll();
    }

    public Film getFilmById(Long filmId) {
        if (!inMemoryFilmStorage.getAll().containsKey(filmId)) {
            log.info("Такого " + filmId + " не существует");
            throw new NotFoundException("Такого " + filmId + " не существует");
        }
        return inMemoryFilmStorage.getFilmById(filmId);
    }

    public void likeFilmById(Long filmId, Long userId) throws ValidationException {
        if (!inMemoryFilmStorage.getAll().containsKey(filmId)) {
            throw new ValidationException("Такого " + filmId + " не существует");
        }

        if (!userService.getUsers().containsKey(userId)) {
            throw new ValidationException("Такого " + userId + " не существует");
        }

        inMemoryFilmStorage.likeFilmById(filmId, userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return inMemoryFilmStorage.getPopularFilms(count);
    }

    public void deleteLikeFromFilmById(Long filmId, Long userId) {
        if (!inMemoryFilmStorage.getAll().containsKey(filmId)) {
            throw new NotFoundException("Такого " + filmId + " не существует");
        }

        if (!userService.getUsers().containsKey(userId)) {
            throw new NotFoundException("Такого " + userId + " не существует");
        }

        inMemoryFilmStorage.deleteLikeFromFilmById(filmId, userId);
    }

    public Film create(Film film) {
        return inMemoryFilmStorage.create(film);
    }

    public Film update(@RequestBody Film film) throws ValidationException {
        if (!inMemoryFilmStorage.getAll().containsKey(film.getId())) {
            log.info("Такого " + film.getId() + " не существует");
            throw new ValidationException("Такого " + film.getId() + " не существует");
        }
        return inMemoryFilmStorage.update(film);
    }
}
