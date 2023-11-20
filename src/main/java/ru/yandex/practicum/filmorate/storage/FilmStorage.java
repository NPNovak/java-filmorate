package ru.yandex.practicum.filmorate.storage;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getAll();

    Film getFilmById(Long filmId);

    Collection<Film> getPopularFilms(Integer count);

    Film create(Film film);

    Film update(@RequestBody Film film) throws ValidationException;
}
