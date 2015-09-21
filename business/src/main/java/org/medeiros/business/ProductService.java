package org.medeiros.business;

import static org.medeiros.persistence.QProduct.product;

import javax.ejb.Stateless;

import org.medeiros.persistence.Product;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;

@Stateless
public class ProductService extends AbstractService<Product> {

	protected EntityPathBase<Product> getEntityPathBase() {
		return product;
	}

	protected Class<Product> getEntityClass() {
		return Product.class;
	}

	protected Expression<Product> getSelect() {
		return Projections.constructor(Product.class, product.id, product.name, product.value, product.tax.id,
				product.tax.name, product.tax.category, product.tax.percentage);
	}

	@Override
	protected Predicate getFilter(String schearch) {
		return product.name.containsIgnoreCase(schearch).or(product.tax.name.containsIgnoreCase(schearch))
				.or(product.tax.category.containsIgnoreCase(schearch));
	}

}
