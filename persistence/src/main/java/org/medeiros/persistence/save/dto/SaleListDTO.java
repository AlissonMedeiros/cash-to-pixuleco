package org.medeiros.persistence.save.dto;

import java.util.List;

import org.medeiros.persistence.sale.SaleItem;

import lombok.Builder;

@Builder
public class SaleListDTO {

	public Long id;
	public List<SaleItem> items;
	public SaleTotalDTO total;

}
