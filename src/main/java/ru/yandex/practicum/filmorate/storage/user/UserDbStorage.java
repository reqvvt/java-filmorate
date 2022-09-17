package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Collection<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM USERS", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getUserById(Integer userId) {
        return jdbcTemplate.query("SELECT * FROM USERS WHERE USER_ID = ?", new BeanPropertyRowMapper<>(User.class))
                           .stream().findAny().orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь с id %s не найден", userId)));
    }

    @Override
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY) " +
                    "VALUES (?, ?, ?, ?)", new String[]{"USER_ID"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            final Date birthday = user.getBirthday();
            if (birthday == null) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, birthday);
            }
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update("UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return getUserById(user.getId());
    }

    @Override
    public void deleteUser(Integer userId) {
        jdbcTemplate.update("DELETE FROM USERS WHERE USER_ID = ?", userId);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        jdbcTemplate.update("INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (?, ?)", userId, friendId);
    }

    @Override
    public List<User> getFriends(Integer userId) {
        List<Integer> friends = jdbcTemplate.queryForList("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?", Integer.class, userId);
        return friends.stream().map(this::getUserById).collect(Collectors.toList());
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?", userId, friendId);
    }
}
