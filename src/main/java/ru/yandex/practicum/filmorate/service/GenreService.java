package ru.yandex.practicum.filmorate.service;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.Collection;

public interface GenreService {
    public Collection<Genre> getAll();

    public Genre getById(Long id);

}
