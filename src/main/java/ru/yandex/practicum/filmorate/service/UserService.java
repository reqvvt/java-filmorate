package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(Integer userId) {
        validateUserId(userId);
        return userStorage.getUserById(userId);
    }

    public User addUser(User user) {
        log.debug("Запрос на добавление пользователя: {}", user);
        validateUser(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        log.debug("Запрос на обновление пользователя: {}", user);
        validateUserId(user.getId());
        return userStorage.updateUser(user);
    }

    public void deleteUser(Integer userId) {
        validateUserId(userId);
        userStorage.deleteUser(userId);
    }

    public void addFriend(@NotNull Integer userId, @NotNull Integer friendId) {
        validateUserId(userId);
        validateUserId(friendId);
        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(@NotNull Integer userId, @NotNull Integer friendId) {
        validateUserId(userId);
        validateUserId(friendId);
        userStorage.removeFriend(userId, friendId);
    }

    public List<User> getUserFriends(Integer userId) {
        validateUserId(userId);
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherUserId) {
        log.info("Запрос на общих друзей у пользователей {} и {}", userId, otherUserId);
        validateUserId(userId);
        validateUserId(otherUserId);
        Set<User> userFriends = new HashSet<>(getUserFriends(userId));
        Set<User> otherUserFriends = new HashSet<>(getUserFriends(otherUserId));
        userFriends.retainAll(otherUserFriends);
        return new ArrayList<>(userFriends);
    }

    private void validateUser(User user) {
        if (user.getLogin().trim().isEmpty()) {
            throw new ValidationException("Логин пуст");
        } else if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен содержать пробел");
        } else if (user.getBirthday().toLocalDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Указана дата рождения из будущего");
        }
        if (user.getName().trim().isEmpty()) {
            log.info("Имени присвоено значение логина");
            user.setName(user.getLogin());
        }
    }

    private void validateUserId(Integer userId) {
        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }

    }
}
