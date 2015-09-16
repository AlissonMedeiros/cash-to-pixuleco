package org.medeiros.repository;

import static org.medeiros.persistence.QTax.tax;

import java.util.List;

import org.medeiros.persistence.Tax;

import com.querydsl.core.types.Predicate;

public class TaxRepository extends AbstractRepository<Tax> {

	@Override
	public Tax findById(Long id) {
		return find(Tax.class, id);
	}

	public Tax save(Tax tax) {
		persist(tax);
		return tax;
	}

	public List<Tax> findAll(Predicate expr) {
		return selectFrom(tax).where(expr).fetch();
	}

	public List<Tax> all() {
		return selectFrom(tax).fetch();
	}

}
