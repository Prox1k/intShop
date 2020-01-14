package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, I> {
    T create(T entity);

    T get(I id);

    T update(T entity);

    boolean deleteById(I id);

    boolean delete(T entity);

    List<T> getAll();
}
