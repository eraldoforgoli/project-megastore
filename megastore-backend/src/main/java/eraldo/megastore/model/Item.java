package eraldo.megastore.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Item {

	private String name;
	private float price;
	private int discount;
	private int partlyIndependence;
	private String inOffer;
	private int itemId;

	public Item() {

	}

	public Item(String name, float price, int discount, int partlyIndependence, String inOffer, int itemId) {

		this.name = name;
		this.price = price;
		this.discount = discount;
		this.partlyIndependence = partlyIndependence;
		this.inOffer = inOffer;
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPartlyIndependence() {
		return partlyIndependence;
	}

	public void setPartlyIndependence(int partlyIndependence) {
		this.partlyIndependence = partlyIndependence;
	}

	public String getInOffer() {
		return inOffer;
	}

	public void setInOffer(String inOffer) {
		this.inOffer = inOffer;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
