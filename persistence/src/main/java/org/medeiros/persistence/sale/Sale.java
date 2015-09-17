package org.medeiros.persistence.sale;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@NotNull(message = "Adicione items a venda")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID")
	public List<SaleItem> items;
	public Date creation;
	public Date lastUpdate;

	@QueryProjection
	public Sale(Long id, List<SaleItem> items) {
		this.id = id;
		this.items = items;
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
