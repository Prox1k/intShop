package mate.academy.internetshop.dao.impl;

import java.util.Optional;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        user.setUserId(IdGenerator.getUserId());
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long userId) {
        return Storage.users
                .stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public User update(User user) {
        Optional<User> updatedUserOptional = get(user.getUserId());
        User updatedUser = updatedUserOptional.get();
        updatedUser.setUserId(user.getUserId());
        updatedUser.setName(user.getName());
        return updatedUser;
    }

    @Override
    public boolean delete(Long userId) {
        Storage.users.removeIf(u -> u.getUserId().equals(userId));
        return true;
    }
}
