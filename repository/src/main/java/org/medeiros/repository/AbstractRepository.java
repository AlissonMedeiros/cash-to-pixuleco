package org.medeiros.repository;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;

public abstract class AbstractRepository<T> implements Repository<T, Long> {

	@Inject
	private Provider<EntityManager> enityManaget;

	protected <T> JPAQuery<T> selectFrom(EntityPath<T> entity) {
		return select(entity).from(entity);
	}

	protected <T> JPAQuery<T> select(Expression<T> select) {
		return new JPAQuery<T>(enityManaget.get(), HQLTemplates.DEFAULT).select(select);
	}

	protected JPADeleteClause delete(EntityPath<?> entity) {
		return new JPADeleteClause(enityManaget.get(), entity, HQLTemplates.DEFAULT);
	}

	protected void detach(Object entity) {
		enityManaget.get().detach(entity);
	}

	protected <E> E find(Class<E> type, Long id) {
		return enityManaget.get().find(type, id);
	}

	protected void persist(Object entity) {
		enityManaget.get().persist(entity);
	}

	protected <E> E merge(E entity) {
		return enityManaget.get().merge(entity);
	}

}
