package org.medeiros.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.medeiros.persistence.QTax.tax;
import static org.mockito.Mockito.when;

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

import com.querydsl.core.types.Predicate;

public class TaxServiceTest {

	private static final long ID = 1L;
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
		MatcherAssert.assertThat(newTax.id, Matchers.equalTo(ID));
	}

	@Test
	public void editTax() throws AppException {
		Tax tax = Tax.builder().id(ID).name("IPI").percentage(BigDecimal.TEN).build();
		Mockito.when(repository.edit(tax)).thenReturn(createSimpleTax());

		Tax newTax = service.edit(tax);

		Mockito.verify(repository).edit(tax);
		MatcherAssert.assertThat(newTax.id, Matchers.equalTo(ID));
	}

	@Test
	public void deleteTax() throws AppException {
		Mockito.when(repository.findById(ID, Tax.class)).thenReturn(createSimpleTax());

		service.delete(ID);

		Mockito.verify(repository).delete(createSimpleTax());
	}

	@Test
	public void listAllTaxs() {
		Mockito.when(repository.all(QTax.tax, QTax.tax)).thenReturn(getList());
		List<Tax> list = service.list("");

		Mockito.verify(repository).all(QTax.tax, QTax.tax);

		MatcherAssert.assertThat(list, Matchers.hasSize(1));
		MatcherAssert.assertThat(list, Matchers.contains(createSimpleTax()));
	}

	@Test
	public void find() throws AppException {
		Mockito.when(repository.findById(ID, Tax.class)).thenReturn(createSimpleTax());

		Tax tax = service.find(ID);

		Mockito.verify(repository).findById(ID, Tax.class);
		MatcherAssert.assertThat(tax, Matchers.equalTo(createSimpleTax()));
	}

	@Test
	public void getFilter() throws AppException {
		String schearch = "Lala";
		Predicate filter = service.getFilter(schearch);
		MatcherAssert.assertThat(filter,
				Matchers.equalTo(tax.name.containsIgnoreCase(schearch).or(tax.category.containsIgnoreCase(schearch))));
	}

	@Test
	public void validateExntedsClass() {
		assertThat(service, Matchers.instanceOf(AbstractService.class));
	}

	@Test(expected = AppException.class)
	public void findByIdWithError() throws AppException {
		when(repository.findById(ID, Tax.class)).thenThrow(new IllegalArgumentException("Error"));
		service.find(ID);
	}

	@Test(expected = AppException.class)
	public void editWithError() throws AppException {
		when(repository.edit(createSimpleTax())).thenThrow(new IllegalArgumentException("Error"));
		service.edit(createSimpleTax());
	}

	@Test(expected = AppException.class)
	public void createWithError() throws AppException {
		when(repository.save(createSimpleTax())).thenThrow(new IllegalArgumentException("Error"));
		service.create(createSimpleTax());
	}

	@Test
	public void listWithError() {
		String schearch = "search";
		Mockito.when(repository.list(QTax.tax, service.getFilter(schearch), QTax.tax)).thenReturn(getList());
		List<Tax> list = service.list(schearch);

		Mockito.verify(repository).list(QTax.tax, service.getFilter(schearch), QTax.tax);

		MatcherAssert.assertThat(list, Matchers.hasSize(1));
		MatcherAssert.assertThat(list, Matchers.contains(createSimpleTax()));
	}

	private List<Tax> getList() {
		return Collections.singletonList(createSimpleTax());
	}

	private Tax createSimpleTax() {
		return Tax.builder().id(ID).build();
	}
}
