package ru.yandex.practicum.filmorate.storage;

public interface LikeStorage {
    public void add(Long userId, Long filmId);

    public void remove(Long userId, Long filmId);

    public Integer getLikesByFilm(Long filmId);
}
