package menuItems;

import javax.swing.ImageIcon;

public class Item {
	private String name;
	private double price;
	private ImageIcon icon;
	
	
	public Item(String name, double price, ImageIcon icon) {
		this.setName(name);
		this.setPrice(price);
		this.setIcon(icon);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public ImageIcon getIcon() {
		return icon;
	}


	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
}
