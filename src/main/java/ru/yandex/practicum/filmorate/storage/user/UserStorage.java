package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface UserStorage {

    Collection<User> getAllUsers();

    User getUserById(Integer userId);

    User addUser(@Valid @RequestBody User user);

    User updateUser(@Valid @RequestBody User user);

    void deleteUser(Integer userId);

    void addFriend(Integer userId, Integer friendId);

    List<User> getFriends(Integer userId);

    void removeFriend(Integer userId, Integer friendId);
}
