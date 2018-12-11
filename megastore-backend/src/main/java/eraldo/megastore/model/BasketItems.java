package eraldo.megastore.model;

public class BasketItems {

	private String[] names;
	private double[] prices;

	public BasketItems() {

	}

	public BasketItems(String[] names, double[] prices) {
		this.names = names;
		this.prices = prices;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public double[] getPrices() {
		return prices;
	}

	public void setPrices(double[] prices) {
		this.prices = prices;
	}

}
