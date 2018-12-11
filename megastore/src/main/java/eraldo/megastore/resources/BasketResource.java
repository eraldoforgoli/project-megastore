package eraldo.megastore.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import eraldo.megastore.model.Basket;
import eraldo.megastore.model.BasketItems;
import eraldo.megastore.service.BasketController;

@Path("/baskets")
public class BasketResource {

	BasketController basketController = new BasketController();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Basket> getAllBaskets() {
		return basketController.getAllBaskets();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON) // e ben dhe @Produces("application/json")..
	public Basket getBasket(@PathParam("id") int id) {
		return basketController.getBasket(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status addBasketToDatabase(Basket b) {
		return basketController.addBasketToDatabase(b);
	}

	@POST
	@Path("/addItems")
	@Consumes(MediaType.APPLICATION_JSON)
	public Status addItemsToBasket(BasketItems b) {
		return basketController.addItemsToBasket(b);
	}

	@PUT
	@Path("/{basketID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Status updateBasket(Basket basket, @PathParam("basketID") int basketID) {
		return basketController.updateItem(basket, basketID);
	}

	@DELETE
	@Path("/{basketID}")
	public void deleteBasket(@PathParam("basketID") int basketID) {
		basketController.deleteBasket(basketID);
	}
}
