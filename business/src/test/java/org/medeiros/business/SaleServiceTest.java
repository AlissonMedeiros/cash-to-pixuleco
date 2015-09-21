package org.medeiros.business;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.medeiros.persistence.sale.QSaleItem.saleItem;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Validator;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.medeiros.business.exception.AppException;
import org.medeiros.persistence.Product;
import org.medeiros.persistence.Tax;
import org.medeiros.persistence.sale.QSale;
import org.medeiros.persistence.sale.Sale;
import org.medeiros.persistence.sale.SaleItem;
import org.medeiros.persistence.save.dto.SaleListDTO;
import org.medeiros.repository.DefaultRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.common.collect.Lists;

public class SaleServiceTest {

	private static final long ID = 1L;
	private static final BigDecimal ERROR_MARGIN = new BigDecimal(0.0001);
	@InjectMocks
	private SaleService service;
	@Mock
	private DefaultRepository<Sale> repository;
	@Mock
	private Validator validator;
	@Mock
	protected DefaultRepository<SaleItem> saleItemRepository;

	@Before
	public void init() {
		initMocks(this);
	}

	@Test
	public void find() throws AppException {
		when(repository.findById(ID, Sale.class)).thenReturn(createSimpleSale());
		when(saleItemRepository.list(saleItem, saleItem.sale.id.eq(ID), service.getSaleItemConstructorExpression()))
				.thenReturn(newArrayList(SaleItem.builder().id(2L).build()));

		Sale sale = service.find(ID);

		verify(repository).findById(ID, Sale.class);
		verify(saleItemRepository).list(saleItem, saleItem.sale.id.eq(ID), service.getSaleItemConstructorExpression());
		assertThat(sale.id, equalTo(ID));
	}

	@Test
	public void list() throws AppException {
		when(repository.all(QSale.sale, QSale.sale)).thenReturn(createSaleList());
		when(saleItemRepository.list(saleItem, saleItem.sale.id.eq(ID), service.getSaleItemConstructorExpression()))
				.thenReturn(createItems(new BigDecimal(10D)));

		when(saleItemRepository.list(saleItem, saleItem.sale.id.eq(2L), service.getSaleItemConstructorExpression()))
				.thenReturn(createItems(new BigDecimal(99D)));
		List<SaleListDTO> sales = service.list();

		verify(repository).all(QSale.sale, QSale.sale);
		verify(saleItemRepository).list(saleItem, saleItem.sale.id.eq(ID), service.getSaleItemConstructorExpression());
		verify(saleItemRepository).list(saleItem, saleItem.sale.id.eq(2L), service.getSaleItemConstructorExpression());
		assertThat(sales, hasSize(2));
		assertThat(sales.get(0).total.taxTotal, Matchers.closeTo(new BigDecimal(11.0D), ERROR_MARGIN));
		assertThat(sales.get(0).total.total, equalTo(new BigDecimal(110D)));
		assertThat(sales.get(1).total.taxTotal, Matchers.closeTo(new BigDecimal(108.9D), ERROR_MARGIN));
		assertThat(sales.get(1).total.total, equalTo(new BigDecimal(110D)));
	}

	@Test
	public void getFilter() {
		assertThat(service.getFilter(""), nullValue());
	}

	@Test
	public void validateExntedsClass() {
		assertThat(service, Matchers.instanceOf(AbstractService.class));
	}

	private List<Sale> createSaleList() {
		return Lists.newArrayList(createSimpleSale(), Sale.builder().id(2L).build());
	}

	private Sale createSimpleSale() {
		return Sale.builder().id(ID).build();
	}

	private List<SaleItem> createItems(BigDecimal percentage) {
		List<SaleItem> items = Lists.newArrayList();
		items.add(SaleItem.builder().quantity(BigDecimal.TEN).value(BigDecimal.TEN).product(getProduct(percentage))
				.build());
		items.add(SaleItem.builder().quantity(BigDecimal.ONE).value(BigDecimal.TEN).product(getProduct(percentage))
				.build());
		return items;
	}

	private Product getProduct(BigDecimal percentage) {
		return Product.builder().tax(Tax.builder().percentage(percentage).build()).build();
	}
}
