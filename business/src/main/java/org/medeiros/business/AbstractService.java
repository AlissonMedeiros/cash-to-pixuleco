package org.medeiros.business;

import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.medeiros.business.exception.AppException;
import org.medeiros.repository.DefaultRepository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;

public abstract class AbstractService<T> {

	@Inject
	protected DefaultRepository<T> repository;
	@Inject
	private Validator validator;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public T find(Long id) {
		return repository.findById(id, getEntityClass());
	}

	public T create(T entity) throws AppException {
		validation(entity);
		return repository.save(entity);
	}

	private void validation(T entity) throws AppException {
		Set<ConstraintViolation<T>> violations = validator.validate(entity);
		if (violations.size() > 0)
			throw new AppException(violations.iterator().next().getMessage());
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> all() {
		return repository.all(getEntityPathBase(), getSelect());
	}

	public T edit(T entity) throws AppException {
		validation(entity);
		return repository.edit(entity);
	}

	public void delete(Long id) {
		repository.delete(find(id));
	}

	protected abstract EntityPathBase<T> getEntityPathBase();

	protected abstract Class<T> getEntityClass();

	protected abstract Expression<T> getSelect();
}
