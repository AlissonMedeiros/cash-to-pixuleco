package org.medeiros.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class ProductType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	public String name;
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
