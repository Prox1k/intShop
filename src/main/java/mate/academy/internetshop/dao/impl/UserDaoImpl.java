package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.User;

import java.util.NoSuchElementException;
import java.util.Optional;

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
        return Optional.ofNullable(Storage.users
                .stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(()
                        -> new NoSuchElementException("Can't find user with id " + userId)));
    }

    @Override
    public User update(User user) {
        Optional<User> updatedUserOptional = get(user.getUserId());
        if (updatedUserOptional.isPresent()) {
            User updatedUser = updatedUserOptional.get();
            updatedUser.setUserId(user.getUserId());
            updatedUser.setName(user.getName());
            updatedUser.setBucket(user.getBucket());
            return updatedUser;
        }
        return user;
    }

    @Override
    public boolean delete(Long userId) {
        Optional<User> deletedUserOptional = get(userId);
        if (deletedUserOptional.isPresent()) {
            User deletedUser = deletedUserOptional.get();
            Storage.users.removeIf(user -> user.getUserId().equals(deletedUser.getUserId()));
            return true;
        }
        return false;
    }
}
