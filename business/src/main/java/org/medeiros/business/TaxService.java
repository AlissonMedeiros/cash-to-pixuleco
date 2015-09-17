package org.medeiros.business;

import javax.ejb.Stateless;

import org.medeiros.persistence.QTax;
import org.medeiros.persistence.Tax;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;

@Stateless
public class TaxService extends AbstractService<Tax> {

	@Override
	protected EntityPathBase<Tax> getEntityPathBase() {
		return QTax.tax;
	}

	@Override
	protected Class<Tax> getEntityClass() {
		return Tax.class;
	}

	@Override
	protected Expression<Tax> getSelect() {
		return QTax.tax;
	}

}
