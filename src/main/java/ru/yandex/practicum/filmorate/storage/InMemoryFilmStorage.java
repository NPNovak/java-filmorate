package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long generatedId;

    public Map<Long, Film> getAll() {
        return films;
    }

    public Film getFilmById(Long filmId) {
        return films.get(filmId);
    }

    public Film create(Film film) {
        film.setId(++generatedId);
        films.put(film.getId(), film);

        return film;
    }

    public Film update(@RequestBody Film film) throws ValidationException {
        films.put(film.getId(), film);

        return film;
    }
}
