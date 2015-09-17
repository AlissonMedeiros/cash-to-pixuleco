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

import org.medeiros.business.ProductService;
import org.medeiros.business.exception.AppException;
import org.medeiros.persistence.Product;

@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

	@Inject
	private ProductService service;

	@POST
	public Product create(@NotNull Product product) throws AppException {
		return service.create(product);
	}

	@PUT
	public Product edit(@NotNull Product product) throws AppException {
		return service.edit(product);
	}

	@GET
	public List<Product> all() {
		return service.all();
	}

	@GET
	@Path("{id}")
	public Product find(@NotNull @PathParam("id") Long id) {
		return service.find(id);
	}

	@DELETE
	@Path("{id}")
	public void delete(@NotNull @PathParam("id") Long id) {
		service.delete(id);
	}

}
