package eraldo.megastore.service;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eraldo.megastore.exception.DataNotFoundException;
import eraldo.megastore.model.ErrorMessage;
import eraldo.megastore.model.Item;

/*
Klase qe do te perbaje metoda qe do te kryejne veprime me tabelat qe mbajne items ne databaze

*/
public class ItemController {

	private String name;
	private float price;
	private int discount;
	private int partlyIndependence;
	private String inOffer;
	private int itemId;

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// ArrayLIst qe do te mbaje te gjithe emrat e items

	private ArrayList<Item> allItems;

	// konstruktor
	public ItemController() {
		allItems = new ArrayList<Item>();
	}

	// metode qe kthen nje ArrayList me emrat e te gjithe items ne databaze

	public ArrayList<Item> getAllItems() {

		String sql = "SELECT  *  FROM  megastore.items";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
				price = rs.getFloat("price");
				discount = rs.getInt("discount");
				partlyIndependence = rs.getInt("partly_independence");
				inOffer = rs.getString("in_offer");
				itemId = rs.getInt("item_id");

				allItems.add(new Item(name, price, discount, partlyIndependence, inOffer, itemId));
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error!");
			ex.printStackTrace();
		}

		if (allItems.isEmpty()) {

			ErrorMessage message = new ErrorMessage("No items in database!", 404, "http//google.com");
			Response response = Response.status(Status.NOT_FOUND).entity(message).build();
			throw new WebApplicationException(response);
		}

		return allItems;
	}

	public Item getItem(int id) {

		String sql = "SELECT  *  FROM megastore.items WHERE item_id = ?";

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);

			rs = pst.executeQuery();

			if (rs.next()) {

				name = rs.getString("name");
				price = rs.getFloat("price");
				discount = rs.getInt("discount");
				partlyIndependence = rs.getInt("partly_independence");
				inOffer = rs.getString("in_offer");
				itemId = rs.getInt("item_id");
				return new Item(name, price, discount, partlyIndependence, inOffer, itemId);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error while getting last basket_id");
			ex.printStackTrace();
		}

		ErrorMessage errorMessage = new ErrorMessage("Item not found!", 404, "http//google.com");

		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		throw new WebApplicationException(response);
	}

	public Status addItemToDatabase(Item i) {

		// krijojme nje Objekt date qe ruan daten qe nje basket eshte blere ne momentin
		// qe eshte thirrur kjo metode

		String sql = "INSERT INTO megastore.items (name, price, discount,partly_independence,in_offer,item_id)"
				+ "VALUES (?, ?, ?, ?, ?,?)";

		System.out.println(i.getName());
		System.out.println(i.getPrice());
		System.out.println(i.getDiscount());
		System.out.println(i.getPartlyIndependence());
		System.out.println(i.getInOffer());
		System.out.println(i.getItemId());

		/*
		 * Nqs vjen nje item qe ka vetem emrin dhe ska price psh: qe eshte required, nuk
		 * krijohet objekt
		 */

		if (i.getPrice() == 0.0) {
			return Status.BAD_REQUEST;
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");

			pst = con.prepareStatement(sql);

			pst.setString(1, i.getName());
			pst.setFloat(2, i.getPrice());
			pst.setInt(3, i.getDiscount());
			pst.setInt(4, i.getPartlyIndependence());
			pst.setString(5, i.getInOffer());
			pst.setInt(6, i.getItemId());

			int accept = pst.executeUpdate();

			if (accept > 0)
				return Status.CREATED;

			else {
				ErrorMessage message = new ErrorMessage("Wrong input, can't insert item", 400, "http/gogole.com");
				Response r = Response.status(Status.BAD_REQUEST).entity(message).build();
				throw new WebApplicationException(r);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error adding Items to database.!");
			ex.printStackTrace();
		}

		ErrorMessage message = new ErrorMessage("Wrong input, can't insert item", 400, "http/gogole.com");
		Response r = Response.status(Status.BAD_REQUEST).entity(message).build();
		throw new WebApplicationException(r);
	}

	public Status updateItem(Item i, int itemID) {

		String sql = "UPDATE megastore.items SET name = ? , price = ?, discount = ? , partly_independence = ? , in_offer = ? , item_id = ? WHERE item_id = ?";

		System.out.println(i.getName());
		System.out.println(i.getPrice());
		System.out.println(i.getDiscount());
		System.out.println(i.getPartlyIndependence());
		System.out.println(i.getInOffer());
		System.out.println(i.getItemId());

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");

			pst = con.prepareStatement(sql);

			pst.setString(1, i.getName());
			pst.setFloat(2, i.getPrice());
			pst.setInt(3, i.getDiscount());
			pst.setInt(4, i.getPartlyIndependence());
			pst.setString(5, i.getInOffer());
			pst.setInt(6, i.getItemId());
			pst.setInt(7, i.getItemId());

			int accept = pst.executeUpdate();

			if (accept > 0)
				return Status.OK;

			else {

				ErrorMessage message = new ErrorMessage("Wrong input, can't update item", 404, "http/gogole.com");
				Response r = Response.status(Status.NOT_FOUND).entity(message).build();
				throw new WebApplicationException(r);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error adding Items to database.!");
			ex.printStackTrace();
		}

		ErrorMessage message = new ErrorMessage("Wrong input, can't update item", 404, "http/gogole.com");
		Response r = Response.status(Status.NOT_FOUND).entity(message).build();
		throw new WebApplicationException(r);
	}

	public Status deleteItem(int itemID) {

		String sql = "DELETE FROM megastore.items WHERE item_id = ?";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);
			pst.setInt(1, itemID);

			int i = pst.executeUpdate();

			if (i < 0) {
				ErrorMessage errorMessage = new ErrorMessage("Item not found", 404, "http//google.com");
				Response r = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
				throw new WebApplicationException(r);
			} else
				return Status.ACCEPTED;

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error deleting Item!");
			ex.printStackTrace();
		}
		return Status.NOT_FOUND;
	}
}
