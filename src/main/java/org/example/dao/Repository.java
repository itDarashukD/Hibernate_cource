package org.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.example.entity.BaseEntity;

// we are going to use it for vary entity's, therefore use generics
public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

    E save(E value);

    List<E> findAll();

    Optional<E> findById(K id);

    void update(E value);

    void delete(K id);


}
