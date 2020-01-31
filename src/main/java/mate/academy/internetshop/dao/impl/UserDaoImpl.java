package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.User;

public class UserDaoImpl {

    public User create(User user) {
        user.setUserId(IdGenerator.incUserId());
        Storage.users.add(user);
        return user;
    }

    public Optional<User> get(Long userId) {
        return Storage.users
                .stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    public User update(User user) {
        Optional<User> updatedUserOptional = get(user.getUserId());
        User updatedUser = updatedUserOptional.orElseThrow(NoSuchElementException::new);
        updatedUser.setUserId(user.getUserId());
        updatedUser.setName(user.getName());
        return updatedUser;
    }

    public boolean deleteById(Long userId) {
        return Storage.users.removeIf(u -> u.getUserId().equals(userId));
    }

    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    public List<User> getAll() {
        return Storage.users;
    }

    public Optional<User> findByLogin(String login) {
        return Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    public Optional<User> findByToken(String token) {
        return Storage.users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
