package org.medeiros.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.medeiros.business.TaxService;
import org.medeiros.business.exception.AppException;
import org.medeiros.persistence.Tax;

@Path("taxes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaxResource {

	@Inject
	private TaxService service;

	@POST
	public Tax create(@NotNull Tax tax) throws AppException {
		return service.create(tax);
	}

	@PUT
	public Tax edit(@NotNull Tax tax) throws AppException {
		return service.edit(tax);
	}

	@GET
	public List<Tax> list(@QueryParam("search") String search) {
		return service.list(search);
	}

	@GET
	@Path("{id}")
	public Tax find(@NotNull @PathParam("id") Long id) throws AppException {
		return service.find(id);
	}

	@DELETE
	@Path("{id}")
	public void delete(@NotNull @PathParam("id") Long id) throws AppException {
		service.delete(id);
	}

}
