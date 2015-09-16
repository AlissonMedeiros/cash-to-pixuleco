package org.medeiros.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;

public abstract class AbstractRepository<T> {

	@PersistenceContext(name = "ExampleDS")
	private EntityManager enityManaget;

	protected <T> JPAQuery<T> selectFrom(EntityPath<T> entity) {
		return select(entity).from(entity);
	}

	protected <T> JPAQuery<T> select(Expression<T> select) {
		return new JPAQuery<T>(enityManaget, HQLTemplates.DEFAULT).select(select);
	}

	protected JPADeleteClause delete(EntityPath<?> entity) {
		return new JPADeleteClause(enityManaget, entity, HQLTemplates.DEFAULT);
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
