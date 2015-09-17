package org.medeiros.persistence;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

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
	public String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "Todo produto deve ter um tributo")
	public Tax tax;
	public Date creation;
	public Date lastUpdate;

	public Product(Long id, String name, Long taxId, String taxName, String taxCategory, BigDecimal taxPercentage) {
		this.id = id;
		this.name = name;
		this.tax = Tax.builder().id(taxId).category(taxCategory).name(taxName).percentage(taxPercentage).build();
	}

	@PrePersist
	public void prePersist() {
		creation = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		lastUpdate = new Date();
	}
}
