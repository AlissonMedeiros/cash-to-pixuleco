package org.medeiros.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.medeiros.persistence.Tax;
import org.medeiros.repository.TaxRepository;

@Stateless
public class TaxService {

	@Inject
	public TaxRepository repository;

	public Tax create(Tax tax) {
		return repository.save(tax);
	}

	public List<Tax> all() {
		return repository.all();
	}

	public Tax edit(Tax tax) {
		return repository.edit(tax);
	}

	public void delete(Long id) {
		repository.delete(id);
	}

}
