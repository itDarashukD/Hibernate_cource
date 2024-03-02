package org.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import lombok.Cleanup;
import org.example.entity.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//@Cleanup - close resources- (like a  try() )
// since we don't want to write repo for all Entities- we have created the base class for all repositories,
public class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private SessionFactory factory;
    private Class<E> clazz;

    @Override
    public E save(E value) {
        @Cleanup final Session session = factory.openSession();
        session.save(value);

        return value;
    }

    @Override
    public List<E> findAll() {
        @Cleanup final Session session = factory.openSession();
        final CriteriaQuery<E> criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);

        // to get as a list
        final List<E> resultList = session.createQuery(criteria).getResultList();

        return resultList;
    }

    @Override
    public Optional<E> findById(E id) {
        @Cleanup final Session session = factory.openSession();
        final E entity = session.find(clazz, id);

        return Optional.ofNullable(entity);
    }

    @Override
    public void update(E entity) {

        @Cleanup final Session session = factory.openSession();
        session.merge(entity);
    }

    @Override
    public void delete(K id) {
        @Cleanup final Session session = factory.openSession();
        final E entity = session.find(clazz, id);
        session.delete(entity);
    }


}
