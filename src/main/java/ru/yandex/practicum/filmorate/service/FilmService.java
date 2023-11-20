package ru.yandex.practicum.filmorate.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> getAll();

    Film getFilmById(Long filmId);

    void likeFilmById(Long filmId, Long userId) throws ValidationException;

    Collection<Film> getPopularFilms(Integer count);

    void deleteLikeFromFilmById(Long filmId, Long userId) throws ValidationException;

    Film create(Film film);

    Film update(@RequestBody Film film) throws ValidationException;
}
