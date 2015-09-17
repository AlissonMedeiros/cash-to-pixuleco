package org.medeiros.persistence;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Tax {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@NotNull(message = "Nome do tributo não pode ser nulo")
	public String name;
	public String category;
	@NotNull(message = "Porcentagem do tributo não pode ser nula")
	public BigDecimal percentage;
	public Date creation;
	public Date lastUpdate;

	@PrePersist
	public void prePersist() {
		creation = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		lastUpdate = new Date();
	}

}
