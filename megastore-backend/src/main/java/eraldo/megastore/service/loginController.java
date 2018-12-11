package eraldo.megastore.service;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eraldo.megastore.model.ErrorMessage;

// klase qe permban nje metode qe merr si parameter username dhe password dhe kthen true nqs useri eshte loguar

public class loginController {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// konstruktor

	public loginController() {

		con = null;
		pst = null;
		rs = null;

	}

	// metode qe merr si parameter username dhe pasword dhe kthen username nqs
	// llogaria gjendet ne databaze
	// null ne te kundert
	public Status login(String username, String password) {

		String sql = "select username,password from megastore.cashiers where username=? and password=?";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "Installer");
			pst = con.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();

			if (rs.next()) {
				return Status.OK;
			} else {
				return Status.NOT_FOUND;
			}

		} catch (SQLException | HeadlessException ex) {
			ex.printStackTrace();

		}

		ErrorMessage errorMessage = new ErrorMessage("Cashier not found!", 404, "http//google.com");

		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		throw new WebApplicationException(response);
	}

}
