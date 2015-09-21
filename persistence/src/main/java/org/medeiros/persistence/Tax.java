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
public class Tax {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@NotNull(message = "Nome do tributo não pode ser nulo")
	@Size(min = 1, message = "Nome do tributo deve conter letras, exemplo IPI")
	public String name;
	@Size(min = 1, message = "Nome da cateoria do tributo deve conter letras, exemplo Automóveis")
	public String category;
	@NotNull(message = "Porcentagem do tributo não pode ser nula")
	public BigDecimal percentage;

}
