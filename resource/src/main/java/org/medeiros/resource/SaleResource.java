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
import javax.ws.rs.core.MediaType;

import org.medeiros.business.SaleService;
import org.medeiros.business.exception.AppException;
import org.medeiros.persistence.sale.Sale;
import org.medeiros.persistence.save.dto.SaleListDTO;

@Path("sales")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaxResource {

	@Inject
	private SaleService service;

	@POST
	public Sale create(@NotNull Sale sale) throws AppException {
		return service.create(sale);
	}

	@PUT
	public Sale edit(@NotNull Sale sale) throws AppException {
		return service.edit(sale);
	}

	@GET
	public List<SaleListDTO> all() {
		return service.list();
	}

	@GET
	@Path("{id}")
	public Sale find(@NotNull @PathParam("id") Long id) {
		return service.find(id);
	}

	@DELETE
	@Path("{id}")
	public void delete(@NotNull @PathParam("id") Long id) {
		service.delete(id);
	}

}
