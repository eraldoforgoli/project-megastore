package eraldo.megastore.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Basket {

	private float netAmount;
	private float totalAmount;
	private int VAT;
	private Timestamp date;
	private String address;
	private String cashier;
	private int basketID;

	public Basket() {

	}

	public Basket(float netAmount, float totalAmount, int VAT, Timestamp date, String address, String cashier,
			int basketID) {

		this.netAmount = netAmount;
		this.totalAmount = totalAmount;
		this.VAT = VAT;
		this.date = date;
		this.address = address;
		this.cashier = cashier;
		this.basketID = basketID;

	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getVAT() {
		return VAT;
	}

	public void setVAT(int vAT) {
		VAT = vAT;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public int getBasketID() {
		return basketID;
	}

	public void setBasketID(int basketID) {
		this.basketID = basketID;
	}

}