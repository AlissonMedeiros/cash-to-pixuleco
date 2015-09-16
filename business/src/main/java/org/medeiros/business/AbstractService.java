package org.medeiros.business;

import java.util.List;

import javax.inject.Inject;

import org.medeiros.repository.DefaultRepository;

import com.querydsl.core.types.dsl.EntityPathBase;

public abstract class AbstractService<T> {

	@Inject
	protected DefaultRepository<T> repository;

	public T find(Long id) {
		return repository.findById(id, getEntityClass());
	}

	public T create(T entity) {
		return repository.save(entity);
	}

	public List<T> all() {
		return repository.all(getEntityPathBase());
	}

	public T edit(T entity) {
		return repository.edit(entity);
	}

	public void delete(Long id) {
		repository.delete(find(id));
	}

	protected abstract EntityPathBase<T> getEntityPathBase();

	protected abstract Class<T> getEntityClass();
}
