package org.medeiros.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	public String name;
	@ManyToOne
	public Tax tax;
	@ManyToOne
	public ProductType type;
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
