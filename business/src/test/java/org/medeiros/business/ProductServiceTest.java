package org.medeiros.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.medeiros.persistence.QProduct.product;

import javax.validation.Validator;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.medeiros.business.exception.AppException;
import org.medeiros.persistence.Product;
import org.medeiros.persistence.QProduct;
import org.medeiros.repository.DefaultRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;

public class ProductServiceTest {

	private static final long ID = 1L;
	@InjectMocks
	private ProductService service;
	@Mock
	private DefaultRepository<Product> repository;
	@Mock
	private Validator validator;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getSelect() throws AppException {
		Expression<Product> select = service.getSelect();
		MatcherAssert.assertThat(select, equalTo(Projections.constructor(Product.class, product.id, product.name,
				product.value, product.tax.id, product.tax.name, product.tax.category, product.tax.percentage)));
	}

	@Test
	public void getFilter() throws AppException {
		String schearch = "Lala";
		Predicate filter = service.getFilter(schearch);
		MatcherAssert
				.assertThat(filter,
						equalTo(product.name.containsIgnoreCase(schearch)
								.or(product.tax.name.containsIgnoreCase(schearch))
								.or(product.tax.category.containsIgnoreCase(schearch))));
	}

	@Test
	public void validateExntedsClass() {
		assertThat(service, Matchers.instanceOf(AbstractService.class));
	}

	public void getEntityPathBase() {
		assertThat(service.getEntityPathBase(), Matchers.equalTo(QProduct.product));
	}

	public void getEntityClass() {
		assertThat(service.getEntityClass(), Matchers.equalTo(Product.class));

	}
}
