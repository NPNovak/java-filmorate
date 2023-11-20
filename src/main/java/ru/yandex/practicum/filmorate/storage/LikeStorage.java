package ru.yandex.practicum.filmorate.storage;

public interface LikeStorage {
    void add(Long userId, Long filmId);

    void remove(Long userId, Long filmId);

    Integer getLikesByFilm(Long filmId);
}
