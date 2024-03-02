package org.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.BaseEntity;
import org.hibernate.Session;

//@Cleanup - close resources- (like a  try() )
// since we don't want to write repo for all Entities- we have created the base class for all repositories,
@AllArgsConstructor
public class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private Class<E> clazz;
    //    private SessionFactory factory;
    @Getter
    private EntityManager manager;

    @Override
    public E save(E value) {
//        final Session session = factory.getCurrentSession();
//        session.save(value);

        manager.persist(value);

        return value;
    }

    @Override
    public List<E> findAll() {
//        final Session session = factory.getCurrentSession();
//        final CriteriaQuery<E> criteria = session.getCriteriaBuilder().createQuery(clazz);

        final CriteriaQuery<E> criteria = manager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);

        // to get as a list
//        final List<E> resultList = session.createQuery(criteria).getResultList();
        final List<E> resultList = manager.createQuery(criteria).getResultList();

        return resultList;
    }

    @Override
    public Optional<E> findById(K id) {
//        final Session session = factory.getCurrentSession();
//        final E entity = session.find(clazz, id);

        final E entity = manager.find(clazz, id);

        return Optional.ofNullable(entity);
    }

    @Override
    public void update(E entity) {
//        final Session session = factory.getCurrentSession();
//        session.merge(entity);

        manager.merge(entity);
    }

    @Override
    public void delete(K id) {
//        final Session session = factory.getCurrentSession();
//        final E entity = session.find(clazz, id);
//        session.delete(entity);

        final E entity = manager.find(clazz, id);
        manager.remove(entity);
    }


}
