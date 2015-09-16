package org.medeiros.business;

import javax.ejb.Stateless;

import org.medeiros.persistence.ProductType;
import org.medeiros.persistence.QProductType;

import com.querydsl.core.types.dsl.EntityPathBase;

@Stateless
public class ProductTypeService extends AbstractService<ProductType> {

	@Override
	protected EntityPathBase<ProductType> getEntityPathBase() {
		return QProductType.productType;
	}

	@Override
	protected Class<ProductType> getEntityClass() {
		return ProductType.class;
	}

}
