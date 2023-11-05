package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long generatedId;

    public Map<Long, Film> getAll() {
        return films;
    }

    public Film getFilmById(Long filmId) {
        return films.get(filmId);
    }

    public void likeFilmById(Long filmId, Long userId) {
        films.get(filmId).getLikes().add(userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return films.values().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public void deleteLikeFromFilmById(Long filmId, Long userId) {
        films.get(filmId).getLikes().remove(userId);
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
