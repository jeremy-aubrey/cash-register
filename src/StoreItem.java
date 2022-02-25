

public class StoreItem {
	
	private int units;
	private int itemNo;
	private String itemDescription;
	private double price;
	
	public StoreItem(int itemNo, String itemDescription, int units, double price) {
		
		//ensure non-negative item number
		if(itemNo > 0) {
			this.itemNo = itemNo;
		} else {
			this.itemNo = Math.abs(itemNo);
		}
		
		//ensure non-negative item price
		if(price >= 0.0) {
			this.price = price;
		} else {
			this.price = 0.00;
		}
		
		this.itemDescription = itemDescription;
		
		//increment the class inventory count
		this.units = units;
	}
	
	public int getUnits() {
		return units;
	}
	
	public void setUnits(int quantity) {
		units = quantity;
	}

	public int getItemNo() {
		return itemNo;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		
		return String.format("%-8s%-15s%-8s%-10s", 
			getItemNo(), 
			getItemDescription(),
			getUnits(),
			String.format("%.2f", getPrice()));
		
	}// end toString method
	

}
