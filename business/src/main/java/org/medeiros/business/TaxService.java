package org.medeiros.business;

import static org.medeiros.persistence.QTax.tax;

import javax.ejb.Stateless;

import org.medeiros.persistence.QTax;
import org.medeiros.persistence.Tax;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;

@Stateless
public class TaxService extends AbstractService<Tax> {

	@Override
	protected EntityPathBase<Tax> getEntityPathBase() {
		return tax;
	}

	@Override
	protected Class<Tax> getEntityClass() {
		return Tax.class;
	}

	@Override
	protected Expression<Tax> getSelect() {
		return QTax.tax;
	}

	@Override
	protected Predicate getFilter(String schearch) {
		return tax.name.containsIgnoreCase(schearch).or(tax.category.containsIgnoreCase(schearch));
	}

}
