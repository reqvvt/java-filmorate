package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserDbStorage implements UserStorage {
    private int id;
    private final Map<Integer, User> usersMap = new HashMap<>();

    public Integer generateId() {
        return ++id;
    }

    @Override
    public Collection<User> getAll() {
        return usersMap.values();
    }

    @Override
    public User getUser(Integer userId) {
        return usersMap.get(userId);
    }

    @Override
    public User addUser(User user) {
        Integer userId = generateId();
        user.setId(userId);
        usersMap.put(userId, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        usersMap.put(user.getId(), user);
        return user;
    }
}
