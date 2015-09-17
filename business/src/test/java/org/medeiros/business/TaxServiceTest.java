package org.medeiros.business;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.validation.Validator;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.medeiros.business.exception.AppException;
import org.medeiros.persistence.QTax;
import org.medeiros.persistence.Tax;
import org.medeiros.repository.DefaultRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TaxServiceTest {

	private static final long TAX_ID = 1L;
	@InjectMocks
	private TaxService service;
	@Mock
	private DefaultRepository<Tax> repository;
	@Mock
	private Validator validator;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createTax() throws AppException {
		Tax tax = Tax.builder().name("IPI").percentage(BigDecimal.TEN).build();
		Mockito.when(repository.save(tax)).thenReturn(createSimpleTax());

		Tax newTax = service.create(tax);

		Mockito.verify(repository).save(tax);
		MatcherAssert.assertThat(newTax.id, Matchers.equalTo(TAX_ID));
	}

	@Test
	public void editTax() throws AppException {
		Tax tax = Tax.builder().id(TAX_ID).name("IPI").percentage(BigDecimal.TEN).build();
		Mockito.when(repository.edit(tax)).thenReturn(createSimpleTax());

		Tax newTax = service.edit(tax);

		Mockito.verify(repository).edit(tax);
		MatcherAssert.assertThat(newTax.id, Matchers.equalTo(TAX_ID));
	}

	@Test
	public void deleteTax() {
		Mockito.when(repository.findById(TAX_ID, Tax.class)).thenReturn(createSimpleTax());

		service.delete(TAX_ID);

		Mockito.verify(repository).delete(createSimpleTax());
	}

	@Test
	public void listAllTaks() {
		Mockito.when(repository.all(QTax.tax, QTax.tax)).thenReturn(getList());
		List<Tax> list = service.all();

		Mockito.verify(repository).all(QTax.tax, QTax.tax);

		MatcherAssert.assertThat(list, Matchers.hasSize(1));
		MatcherAssert.assertThat(list, Matchers.contains(createSimpleTax()));
	}

	@Test
	public void find() {
		Mockito.when(repository.findById(TAX_ID, Tax.class)).thenReturn(createSimpleTax());

		Tax tax = service.find(TAX_ID);

		Mockito.verify(repository).findById(TAX_ID, Tax.class);
		MatcherAssert.assertThat(tax, Matchers.equalTo(createSimpleTax()));
	}

	private List<Tax> getList() {
		return Collections.singletonList(createSimpleTax());
	}

	private Tax createSimpleTax() {
		return Tax.builder().id(TAX_ID).build();
	}
}
