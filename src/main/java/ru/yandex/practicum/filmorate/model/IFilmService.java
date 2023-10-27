package ru.yandex.practicum.filmorate.model;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IFilmService {
    public Collection<Film> getAll();

    public Map<Long, Film> getFilms();

    public Film getFilmById(Long filmId);

    public void likeFilmById(Long filmId, Long userId) throws ValidationException;

    public List<Film> getPopularFilms(Integer count);

    public void deleteLikeFromFilmById(Long filmId, Long userId) throws ValidationException;

    public Film create(Film film);

    public Film update(@RequestBody Film film) throws ValidationException;
}
