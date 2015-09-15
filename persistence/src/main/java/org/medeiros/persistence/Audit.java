package org.medeiros.persistence;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Audit {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "TAX_SEQ")
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
