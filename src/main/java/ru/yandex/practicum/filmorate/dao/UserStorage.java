package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@Component
public class UserStorage {
    private int generator;
    HashMap<Integer, User> userMap = new HashMap<>();

    public User get(int userId) {
        return userMap.get(userId);
    }

    public User save(User user) {
        user.setId(++generator);
        userMap.put(user.getId(), user);
        return user;
    }

    public void addFriend(User user, User friend) {
        user.getFriendIds().add(friend.getId());
        friend.getFriendIds().add(friend.getId());
    }

    public void deleteFriend(User user, User friend) {
        user.getFriendIds().remove(friend.getId());
        friend.getFriendIds().remove(user.getId());
    }
}
