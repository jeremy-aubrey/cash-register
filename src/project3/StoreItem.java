package project3;

public class StoreItem {
	
	private static int unitsInInventory;
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
		this.unitsInInventory = units;
	}
	
	public static int getUnitsInInventory() {
		return unitsInInventory;
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
		
		return String.format("%-12s %s%n%-12s %s%n%-12s %s%n%-12s %s%n", 
			"Item no:", getItemNo(), 
			"Description:", getItemDescription(),
			"Units:", getUnitsInInventory(),
			"Price:", String.format("%.2f", getPrice()));
		
	}// end toString method
	

}
