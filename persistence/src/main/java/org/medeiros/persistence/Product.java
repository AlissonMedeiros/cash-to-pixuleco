package org.medeiros.persistence;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@NotNull(message = "Nome do produto não pode ser nulo.")
	@Size(min = 1, message = "Nomo do produto deve conter letras, exemplo Carro")
	public String name;
	@NotNull(message = "Todo produto deve ter um valor")
	public BigDecimal value;
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "Todo produto deve ter um tributo")
	public Tax tax;

	public Product(Long id, String name, BigDecimal value, Long taxId, String taxName, String taxCategory,
			BigDecimal taxPercentage) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.tax = Tax.builder().id(taxId).category(taxCategory).name(taxName).percentage(taxPercentage).build();
	}
	
}
