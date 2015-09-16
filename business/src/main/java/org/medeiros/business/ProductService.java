package org.medeiros.business;

import javax.ejb.Stateless;

import org.medeiros.persistence.Product;
import org.medeiros.persistence.QProduct;

import com.querydsl.core.types.dsl.EntityPathBase;

@Stateless
public class ProductService extends AbstractService<Product> {

	@Override
	protected EntityPathBase<Product> getEntityPathBase() {
		return QProduct.product;
	}

	@Override
	protected Class<Product> getEntityClass() {
		return Product.class;
	}

}
