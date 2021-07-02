package com.demidrolll.myphotos.ejb.repository.jpa;

import com.demidrolll.myphotos.ejb.repository.EntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public abstract class AbstractJpaRepository<T, ID> implements EntityRepository<T, ID> {

    @PersistenceContext(unitName = "com.demidrolll.myphotos.persistence")
    protected EntityManager em;

    protected abstract Class<T> getEntityClass();

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(getEntityClass(), id));
    }

    @Override
    public void create(T entity) {
        em.persist(entity);
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public T getProxyInstance(ID id) {
        return em.getReference(getEntityClass(), id);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
