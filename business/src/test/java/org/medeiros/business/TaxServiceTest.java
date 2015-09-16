package org.medeiros.business;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.medeiros.persistence.Tax;
import org.medeiros.repository.TaxRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TaxServiceTest {

	private static final long TAX_ID = 1L;
	@InjectMocks
	private TaxService service;
	@Mock
	public TaxRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createTax() {
		Tax tax = Tax.builder().name("IPI").percentage(BigDecimal.TEN).build();
		Mockito.when(repository.save(tax)).thenReturn(createSimpleTax());

		Tax newTax = service.create(tax);

		Mockito.verify(repository).save(tax);
		MatcherAssert.assertThat(newTax.id, Matchers.equalTo(TAX_ID));
	}

	@Test
	public void editTax() {
		Tax tax = Tax.builder().id(TAX_ID).name("IPI").percentage(BigDecimal.TEN).build();
		Mockito.when(repository.edit(tax)).thenReturn(createSimpleTax());

		Tax newTax = service.edit(tax);

		Mockito.verify(repository).edit(tax);
		MatcherAssert.assertThat(newTax.id, Matchers.equalTo(TAX_ID));
	}

	@Test
	public void deleteTax() {
		service.delete(TAX_ID);
		Mockito.verify(repository).delete(TAX_ID);
	}

	@Test
	public void listAllTaks() {
		Mockito.when(repository.all()).thenReturn(getList());
		List<Tax> list = service.all();

		Mockito.verify(repository).all();

		MatcherAssert.assertThat(list, Matchers.hasSize(1));
		MatcherAssert.assertThat(list, Matchers.contains(createSimpleTax()));
	}

	private List<Tax> getList() {
		return Collections.singletonList(createSimpleTax());
	}

	private Tax createSimpleTax() {
		return Tax.builder().id(TAX_ID).build();
	}
}
