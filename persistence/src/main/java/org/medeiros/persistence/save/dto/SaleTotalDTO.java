package org.medeiros.persistence.save.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public class SaleTotalDTO {

	public BigDecimal total;
	public BigDecimal taxTotal;

}
