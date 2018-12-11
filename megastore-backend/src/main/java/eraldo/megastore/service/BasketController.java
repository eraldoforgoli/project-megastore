package eraldo.megastore.service;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eraldo.megastore.model.Basket;
import eraldo.megastore.model.BasketItems;
import eraldo.megastore.model.ErrorMessage;
import eraldo.megastore.model.Item;

/*
Klase qe permban metoda qe ruajne nje basket ne databaze dhe items per cdo basket
*/

public class BasketController {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	StringBuilder build = new StringBuilder();

	private float netAmount;
	private float totalAmount;
	private int VAT;
	private Timestamp date;
	private String address;
	private String cashier;
	private int basketID;

	// ArrayList me stringje qe perdoret per te marre te dhenat nga nje baskt i
	// caktuar .
	private ArrayList<StringBuilder> allBaskets;

	// konstruktor
	public BasketController() {
		allBaskets = new ArrayList<StringBuilder>();
	}

	// metode qe merr si parameter cmimin total dhe emrin e cashierit, dhe shton nje
	// kosh te ri ne databaze ( shtohet nje rrjesht ne databaze)

	public Status addBasketToDatabase(Basket b) {

		// krijojme nje Objekt date qe ruan daten qe nje basket eshte blere ne momentin
		// qe eshte thirrur kjo metode

		String sql = "INSERT INTO megastore.baskets (net_amount, total_amount, vat,date,address,cashier)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		// net price llogaritet si total price plus VAT qe eshte marre 10%
		System.out.println(b.getNetAmount());
		System.out.println(b.getTotalAmount());
		System.out.println(b.getVAT());
		// System.out.println(b.getDate());
		System.out.println(b.getCashier());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		if (b.getNetAmount() == 0 || b.getTotalAmount() == 0 || b.getVAT() == 0)
			return Status.BAD_REQUEST;

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);

			pst.setFloat(1, b.getNetAmount());
			pst.setFloat(2, b.getTotalAmount());
			pst.setInt(3, b.getVAT());
			pst.setTimestamp(4, timestamp);
			pst.setString(5, b.getAddress());
			pst.setString(6, b.getCashier());

			int accept = pst.executeUpdate();
			if (accept > 0)
				return Status.CREATED;

			else {
				ErrorMessage message = new ErrorMessage("Wrong input, can't insert basket", 400, "http/gogole.com");
				Response r = Response.status(Status.BAD_REQUEST).entity(message).build();
				throw new WebApplicationException(r);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error adding basket to database.!");
			ex.printStackTrace();
		}

		ErrorMessage message = new ErrorMessage("Wrong input, can't insert basket", 400, "http/gogole.com");
		Response r = Response.status(Status.BAD_REQUEST).entity(message).build();
		throw new WebApplicationException(r);
	}

	// kthen id e basketit te fundit te shtuar , pasi duhet te shtohen te gjithe
	// items te ketij koshi ne tabelen basket_items
	public int getLastBasketId() {

		String sql = "SELECT basket_id FROM megastore.baskets ORDER BY basket_id DESC LIMIT 1;";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getInt("basket_id");
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error while getting last basket_id");
			ex.printStackTrace();
		}
		return 0;
	}

	// metode qe kthen nje basket me nje id te caktuar

	public Basket getBasket(int id) {

		String sql = "SELECT  *  FROM megastore.baskets WHERE basket_id = ? ";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);

			rs = pst.executeQuery();

			if (rs.next()) {
				netAmount = (float) rs.getFloat("net_amount");
				totalAmount = rs.getFloat("total_amount");
				VAT = rs.getInt("vat");
				date = rs.getTimestamp("date");
				address = rs.getString("address");
				cashier = rs.getString("cashier");
				basketID = rs.getInt("basket_id");
				return new Basket(netAmount, totalAmount, VAT, date, address, cashier, basketID);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error while getting last basket_id");
			ex.printStackTrace();
		}

		// nese blloku try deshton, do kthejme nje perjashtim qe njihet paraprakisht nga
		// Jersey, WebApplicationException
		// krijojme nje response ku do kthejme mesazh json duke perdorur klasen
		// ErrorMessage
		// nuk ka nevoje per mapper
		ErrorMessage errorMessage = new ErrorMessage("Basket found!", 404, "http//google.com");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		throw new WebApplicationException(response);
	}

	// shton ne tabelen basket_items te gjithe items te nje koshis
	public Status addItemsToBasket(BasketItems bi) {

		String sql = "INSERT INTO megastore.basket_items(item_name, price, basket_id)" + "VALUES (?, ?, ?)";

		int id = this.getLastBasketId();

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);

			for (int i = 0; i < bi.getNames().length; i++) {

				pst.setString(1, bi.getNames()[i]);
				pst.setFloat(2, (float) bi.getPrices()[i]);
				pst.setInt(3, id);
				pst.executeUpdate();
			}

			return Status.OK;

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error adding items to basket");
			ex.printStackTrace();
		}
		ErrorMessage message = new ErrorMessage("Error, can't insert  purchases items to databaze", 400,
				"http//google.com");
		Response response = Response.status(Status.BAD_REQUEST).entity(message).build();
		throw new WebApplicationException(response);

	}

	public ArrayList<Basket> getAllBaskets() {

		ArrayList<Basket> allBaskets = new ArrayList<Basket>();

		String sql = "SELECT   *  FROM  megastore.baskets";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				netAmount = (float) rs.getFloat("net_amount");
				totalAmount = rs.getFloat("total_amount");
				VAT = rs.getInt("vat");
				date = rs.getTimestamp("date");
				address = rs.getString("address");
				cashier = rs.getString("cashier");
				basketID = rs.getInt("basket_id");

				Basket b = new Basket(netAmount, totalAmount, VAT, date, address, cashier, basketID);
				allBaskets.add(b);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error reading from baskets!");
		}

		if (allBaskets.isEmpty()) { // nese nuk ka baskets ne databaze
			ErrorMessage message = new ErrorMessage("No baskets in database", 404, "http//google.com");
			Response response = Response.status(Status.NOT_FOUND).entity(message).build();
			throw new WebApplicationException(response);
		} else
			return allBaskets;
	}

	public Status deleteBasket(int basketID) {

		String sql = "DELETE FROM megastore.baskets WHERE basket_id = ?";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);
			pst.setInt(1, basketID);

			int i = pst.executeUpdate();
			if (i > 0)
				return Status.OK; // nese e fshijme kthejme OK 200

			else {
				ErrorMessage message = new ErrorMessage("Basket does not exist!", 404, "http//google.com");
				Response response = Response.status(Status.NOT_FOUND).entity(message).build();
				throw new WebApplicationException(response);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error adding Items to database.!");
			ex.printStackTrace();
		}

		ErrorMessage message = new ErrorMessage("Basket does not exist!", 404, "http//google.com");
		Response response = Response.status(Status.NOT_FOUND).entity(message).build();
		throw new WebApplicationException(response);
	}

	public Status updateItem(Basket basket, int basketID) {

		String sql = "UPDATE megastore.baskets SET net_amount = ? , total_amount = ?, vat = ? , date = ? , address = ? , cashier = ?, basket_id = ? WHERE basket_id = ?";

		System.out.println(basket.getNetAmount());
		System.out.println(basket.getTotalAmount());
		System.out.println(basket.getVAT());
		System.out.println(basket.getAddress());
		System.out.println(basket.getCashier());
		System.out.println(basket.getBasketID());
		System.out.println(basketID);

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");

			pst = con.prepareStatement(sql);

			pst.setFloat(1, basket.getNetAmount());
			pst.setFloat(2, basket.getTotalAmount());
			pst.setInt(3, basket.getVAT());
			pst.setTimestamp(4, basket.getDate());
			pst.setString(5, basket.getAddress());
			pst.setString(6, basket.getCashier());
			pst.setInt(7, basket.getBasketID());
			pst.setInt(8, basketID);

			int accept = pst.executeUpdate();

			if (accept > 0) {
				return Status.OK;
			} else {

				ErrorMessage message = new ErrorMessage("Wrong input, can't update basket", 404, "http/gogole.com");
				Response r = Response.status(Status.NOT_FOUND).entity(message).build();
				throw new WebApplicationException(r);
			}

		} catch (SQLException | HeadlessException ex) {
			System.out.println("Error adding Items to database.!");
			ex.printStackTrace();
		}

		ErrorMessage message = new ErrorMessage("Wrong input, can't update basket", 404, "http/gogole.com");
		Response r = Response.status(Status.NOT_FOUND).entity(message).build();
		throw new WebApplicationException(r);
	}
}
