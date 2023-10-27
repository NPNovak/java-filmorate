package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    public Map<Long, Film> getAll();

    public Film getFilmById(Long filmId);

    public void likeFilmById(Long filmId, Long userId) throws ValidationException;

    public List<Film> getPopularFilms(Integer count);

    public void deleteLikeFromFilmById(Long filmId, Long userId);

    public Film create(Film film);

    public Film update(@RequestBody Film film) throws ValidationException;
}
