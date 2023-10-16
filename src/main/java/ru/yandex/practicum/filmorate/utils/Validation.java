package ru.yandex.practicum.filmorate.utils;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class Validation {
    private static final LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public static void filmValidation(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            log.info("У фильма нет названия");
            throw new ValidationException("У фильма нет названия");
        }

        if (film.getDescription().length() > 200) {
            log.info("Длина описания больше 200 символов");
            throw new ValidationException("Длина описания больше 200 символов");
        }

        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.info("Дата релиза раньше даты создания кино");
            throw new ValidationException("Дата релиза раньше даты создания кино");
        }

        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.info("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    public static void userValidation(User user) throws ValidationException {
        if ((user.getEmail() == null || user.getEmail().isBlank()) || !user.getEmail().contains("@")) {
            log.info("Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if ((user.getLogin() == null || user.getLogin().isBlank()) || user.getLogin().contains(" ")) {
            log.info("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if ((user.getName() == null || user.getName().isBlank()) && !user.getLogin().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
