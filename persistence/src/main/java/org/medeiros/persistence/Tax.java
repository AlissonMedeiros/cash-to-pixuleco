package org.medeiros.persistence;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Tax {

	// http://docs.oracle.com/javaee/7/api/javax/persistence/GeneratedValue.html
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "TAX_SEQ")
	public Long id;
	public String name;
	@OneToOne
	public Audit audit;

}
