package org.medeiros.repository;

import static org.medeiros.persistence.QTax.tax;

import java.util.List;

import javax.ejb.Stateless;

import org.medeiros.persistence.Tax;

import com.querydsl.core.types.Predicate;

@Stateless
public class TaxRepository extends AbstractRepository<Tax> {

	public Tax findById(Long id) {
		return find(Tax.class, id);
	}

	public Tax save(Tax tax) {
		return createNew(tax);
	}

	public Tax edit(Tax tax) {
		return merge(tax);
	}

	public void delete(Long id) {
		delete(tax).where(tax.id.eq(id)).execute();
	}

	public List<Tax> findAll(Predicate expr) {
		return selectFrom(tax).where(expr).fetch();
	}

	public List<Tax> all() {
		return selectFrom(tax).fetch();
	}

}
