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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eraldo.megastore.model.Item;
import eraldo.megastore.service.ItemController;

@Path("/items")
public class ItemResource {

	ItemController itemController = new ItemController();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Item> getAllItems() {
		return itemController.getAllItems();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItem(@PathParam("id") int id) {
		return itemController.getItem(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status addItemToDatabase(Item i) { // kthej nje response 201 CREATED bashke me objektin e futur ne databaze
		return itemController.addItemToDatabase(i);
	}

	@PUT
	@Path("/{itemID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status updateItem(Item i, @PathParam("itemId") int itemId) {
		return itemController.updateItem(i, itemId);
	}

	@DELETE
	@Path("/{itemID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Status deleteItem(@PathParam("itemID") int itemID) {
		return itemController.deleteItem(itemID);
	}
}
