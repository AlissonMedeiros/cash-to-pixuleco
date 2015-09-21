package org.medeiros.repository;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import javax.persistence.EntityManager;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.medeiros.persistence.QTax;
import org.medeiros.persistence.Tax;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

public class DefaultRepositoryTest {

	private static final long DUMMY_ID = 1L;
	@Spy
	@InjectMocks
	private DefaultRepository<Tax> repository;
	@Mock
	private EntityManager entityManager;
	@Mock
	private JPAQuery<Tax> jpaQuery;

	@Before
	public void init() {
		initMocks(this);
		Mockito.when(repository.createQuery()).thenReturn(jpaQuery);
	}

	@Test
	public void findById() {
		Mockito.when(entityManager.find(Tax.class, DUMMY_ID)).thenReturn(createTax());
		Tax tax = repository.findById(1L, Tax.class);
		MatcherAssert.assertThat(tax.id, Matchers.equalTo(DUMMY_ID));
	}

	@Test
	public void save() {
		Tax entity = createTax();
		repository.save(entity);
		Mockito.verify(entityManager).persist(entity);
		Mockito.verify(entityManager, Mockito.never()).merge(entity);
	}

	@Test
	public void edit() {
		Tax entity = createTax();
		repository.edit(entity);
		Mockito.verify(entityManager, Mockito.never()).persist(entity);
		Mockito.verify(entityManager).merge(entity);
	}

	@Test
	public void delete() {
		Tax entity = createTax();
		repository.delete(entity);
		Mockito.verify(entityManager).remove(entity);
	}

	@Test
	public void list() {
		BooleanExpression expression = QTax.tax.id.eq(DUMMY_ID);
		Mockito.when(jpaQuery.select(QTax.tax)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.from(QTax.tax)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.where(expression)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.fetch()).thenReturn(Lists.newArrayList(createTax(), createTax()));
		List<Tax> list = repository.list(QTax.tax, expression, QTax.tax);
		MatcherAssert.assertThat(list, Matchers.hasSize(2));
		Mockito.verify(jpaQuery).select(QTax.tax);
		Mockito.verify(jpaQuery).from(QTax.tax);
		Mockito.verify(jpaQuery).where(expression);
		Mockito.verify(jpaQuery).fetch();
	}

	@Test
	public void all() {
		Mockito.when(jpaQuery.select(QTax.tax)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.from(QTax.tax)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.fetch()).thenReturn(Lists.newArrayList(createTax(), createTax()));
		List<Tax> list = repository.all(QTax.tax, QTax.tax);
		MatcherAssert.assertThat(list, Matchers.hasSize(2));
		Mockito.verify(jpaQuery).select(QTax.tax);
		Mockito.verify(jpaQuery).from(QTax.tax);
		Mockito.verify(jpaQuery).fetch();
	}

	private Tax createTax() {
		return Tax.builder().id(DUMMY_ID).build();
	}
}
