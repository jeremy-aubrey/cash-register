package project3;

import java.util.ArrayList;
import java.util.List;

public class CashRegister {
	
	private List<StoreItem> selectedItems;
	private List<StoreItem> inventoryList;
	
	public CashRegister(List inventoryList) 
	{
		selectedItems = new ArrayList<StoreItem>();
		this.inventoryList = inventoryList; 
	}
	
	public void displayMenu()
	{
		
	};
	
	private void printHeader(String content) {
		System.out.println("-------------------------------------");
		System.out.println(content.toUpperCase());
		System.out.println("-------------------------------------");
	}
	
	private void printColumns() {
		System.out.printf("%-8s%-15s%-8s%-10s%n", 
				"ItemNo",
				"Description",
				"Qty",
				"Price");
		System.out.println("-------------------------------------");
	}
	
	public double getTotal() 
	{
		return 0.0;
	};

	public void purchaseItem(StoreItem item, int quantity) 
	{
		if(item != null) {
			boolean inStock = decrementQuantity(item.getItemNo(), quantity);
			if(inStock) {
			selectedItems.add(new StoreItem(item.getItemNo(), 
					item.getItemDescription(),
					quantity,
					item.getPrice()
					));
				System.out.println("");
				System.out.println("Item added: " + item.getItemDescription());
				System.out.println("");
			} else {
				System.out.println("");
				System.out.println("Unable to purchase: " + quantity + " " + item.getItemDescription());
				System.out.println("");
			}
		}
	};
	
	
	private boolean decrementQuantity(int itemNo, int quantity) {
		boolean updated = false;
		for(StoreItem item : inventoryList) {
			if(itemNo == item.getItemNo() && quantity <= item.getUnitsInInventory()) {
				int newQty = item.getUnitsInInventory() - quantity;
				item.setUnitsInInventory(newQty);
				updated = true;
			}
		}
		
		return updated;
	}
	
	public void showItems()
	{	
		printHeader("register items");
		printColumns();
		if(!selectedItems.isEmpty()) {
			for(StoreItem item : selectedItems) {
				System.out.println(item);
			}
		} else {
			System.out.println("< NO ITEMS >");
		}	
	};
	
	private void clearRegister()
	{
		
	};
	
	public void showInventory()
	{
		printHeader("store inventory");
		printColumns();
		for(StoreItem item : inventoryList) {
			System.out.println(item);
		}
	};
	
	public boolean checkOut()
	{
		return true;
	};
	

}
