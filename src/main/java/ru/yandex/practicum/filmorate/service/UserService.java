package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User getUser(Integer userId) throws NotFoundException {
        return userStorage.getUser(userId);
    }

    public User addUser(User user) {
        validate(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        validate(user);
        return userStorage.updateUser(user);
    }

    public void addFriend(@NotNull Integer userId, @NotNull Integer friendId) throws NotFoundException {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException(String.format("User id = %s или Friend id = %s не могут быть меньше 0", userId,
                    friendId));
        }
        try {
            userStorage.getUser(userId).getFriendIds().add(friendId);
            userStorage.getUser(friendId).getFriendIds().add(userId);
        } catch (NotFoundException e) {
            System.out.println(String.format("User id = %s или Friend id = %s не найден", userId, friendId));
        }
    }

    public void deleteFriend(@NotNull Integer userId, @NotNull Integer friendId) throws NotFoundException {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException(String.format("User id = %s или Friend id = %s не могут быть меньше 0", userId,
                    friendId));
        }
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException(String.format("User с id = %s не найден", userId));
        }
        try {
            userStorage.getUser(userId).getFriendIds().remove(friendId);
            userStorage.getUser(friendId).getFriendIds().remove(userId);
        } catch (NotFoundException e) {
            System.out.println(String.format("User id = %s или Friend id = %s не найден", userId, friendId));
        }
    }

    public List<User> getUserFriends(Integer userId) throws NotFoundException {
        return userStorage.getAll()
                          .stream()
                          .filter(user -> userStorage.getUser(userId).getFriendIds().contains(userId))
                          .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) throws NotFoundException {
        Collection<Integer> userFriendList = new ArrayList<>(userStorage.getUser(userId).getFriendIds());
        userFriendList.retainAll(userStorage.getUser(friendId).getFriendIds());
        return userStorage.getAll()
                          .stream()
                          .filter(user -> userFriendList.contains(user.getId()))
                          .collect(Collectors.toList());
    }

    private void validate(User user) {
        if (user.getLogin().trim().isEmpty()) {
            throw new ValidationException("Логин пуст");
        } else if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен содержать пробел");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Указана дата рождения из будущего");
        }
        if (user.getName().trim().isEmpty()) {
            log.info("Имени присвоено значение логина");
            user.setName(user.getLogin());
        }
    }
}
