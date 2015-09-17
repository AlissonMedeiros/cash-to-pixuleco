package org.medeiros.persistence.sale;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.medeiros.persistence.Product;
import org.medeiros.persistence.Tax;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	@NotNull(message = "Item da venda precisa de um produto")
	@ManyToOne(fetch = FetchType.LAZY)
	public Product product;
	@NotNull(message = "Item da venda precisa de um valor")
	public BigDecimal value;
	@NotNull(message = "Item da venda precisa de uma quantidade")
	public BigDecimal quantity;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Sale sale;

	public SaleItem(Long id, BigDecimal value, BigDecimal quantity, Long saleId, Long productId, String productName,
			Long taxId, BigDecimal percentage, String taxName, String taxCategory) {
		this.id = id;
		this.value = value;
		this.quantity = quantity;
		this.sale = Sale.builder().id(saleId).build();
		this.product = Product.builder().id(productId).name(productName).build();
		this.product.tax = Tax.builder().id(taxId).name(taxName).category(taxCategory).percentage(percentage).build();
	}
}
