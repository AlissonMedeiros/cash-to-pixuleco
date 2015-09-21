package org.medeiros.business;

import static org.medeiros.persistence.sale.QSale.sale;
import static org.medeiros.persistence.sale.QSaleItem.saleItem;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.medeiros.business.exception.AppException;
import org.medeiros.persistence.sale.Sale;
import org.medeiros.persistence.sale.SaleItem;
import org.medeiros.persistence.save.dto.SaleListDTO;
import org.medeiros.persistence.save.dto.SaleTotalDTO;
import org.medeiros.repository.DefaultRepository;

import com.google.common.collect.Lists;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;

public class SaleService extends AbstractService<Sale> {

	@Inject
	protected DefaultRepository<SaleItem> saleItemRepository;

	@Override
	public Sale find(Long id) throws AppException {
		Sale entity = super.find(id);
		entity.items = saleItemRepository.list(saleItem, saleItem.sale.id.eq(id), getSaleItemConstructorExpression());
		return entity;
	}

	public List<SaleListDTO> list() {
		List<SaleListDTO> list = Lists.newArrayList();
		List<Sale> sales = list(null);
		sales.stream().forEach((sale) -> {
			list.add(createSaleList(sale));
		});
		return list;
	}

	private SaleListDTO createSaleList(Sale sale) {
		List<SaleItem> items = saleItemRepository.list(saleItem, saleItem.sale.id.eq(sale.id),
				getSaleItemConstructorExpression());
		return SaleListDTO.builder().id(sale.id).items(items).total(getSaleTotal(sale, items)).build();
	}

	private SaleTotalDTO getSaleTotal(Sale sale, List<SaleItem> items) {
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		for (SaleItem saleItem : items) {
			BigDecimal itemTotal = saleItem.quantity.multiply(saleItem.value);
			total = total.add(itemTotal);
			tax = tax.add(itemTotal.multiply(saleItem.product.tax.percentage.divide(new BigDecimal(100D))));
		}
		return SaleTotalDTO.builder().taxTotal(tax).total(total).build();
	}

	protected ConstructorExpression<SaleItem> getSaleItemConstructorExpression() {
		return Projections.constructor(SaleItem.class, saleItem.id, saleItem.value, saleItem.quantity, saleItem.sale.id,
				saleItem.product.id, saleItem.product.name, saleItem.product.tax.id, saleItem.product.tax.percentage,
				saleItem.product.tax.name, saleItem.product.tax.category);
	}

	protected EntityPathBase<Sale> getEntityPathBase() {
		return sale;
	}

	protected Class<Sale> getEntityClass() {
		return Sale.class;
	}

	protected Expression<Sale> getSelect() {
		return sale;
	}

	@Override
	protected Predicate getFilter(String schearch) {
		return null;
	}
}
