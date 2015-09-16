package org.medeiros.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;

@Stateless
public class DefaultRepository<T> {

	@PersistenceContext(name = "ExampleDS")
	private EntityManager enityManaget;

	public T findById(Long id, Class<T> entityClass) {
		return enityManaget.find(entityClass, id);
	}

	public T save(T entity) {
		return createNew(entity);
	}

	public T edit(T entity) {
		return merge(entity);
	}

	public void delete(T entity) {
		remove(entity);
	}

	public List<T> findAll(Predicate expr, EntityPathBase<T> entityPathBase) {
		return selectFrom(entityPathBase).where(expr).fetch();
	}

	public List<T> all(EntityPathBase<T> entityPathBase) {
		return selectFrom(entityPathBase).fetch();
	}

	protected <T> JPAQuery<T> selectFrom(EntityPath<T> entity) {
		return select(entity).from(entity);
	}

	protected <T> JPAQuery<T> select(Expression<T> select) {
		return new JPAQuery<T>(enityManaget, HQLTemplates.DEFAULT).select(select);
	}

	protected void remove(T entity) {
		enityManaget.remove(entity);
	}

	protected void detach(Object entity) {
		enityManaget.detach(entity);
	}

	protected <E> E find(Class<E> type, Long id) {
		return enityManaget.find(type, id);
	}

	protected void persist(Object entity) {
		enityManaget.persist(entity);
	}

	protected <E> E createNew(E entity) {
		persist(entity);
		return entity;
	}

	protected <E> E merge(E entity) {
		return enityManaget.merge(entity);
	}

}
