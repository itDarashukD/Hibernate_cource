package org.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.example.entity.BaseEntity;

// since we don't want to write repo for all Entities- we have created the base class for all repositories,
public class BaseRepository <K extends Serializable,E extends BaseEntity<K>> implements Repository<K,E>{


    @Override
    public E save(E value) {
        return null;
    }

    @Override
    public List<E> findAll() {
        return null;
    }

    @Override
    public Optional<E> findById(E id) {
        return Optional.empty();
    }

    @Override
    public void update(E value) {

    }

    @Override
    public void delete(E entity) {

    }
}
