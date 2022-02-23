package project3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CashRegister {
	
	private List<StoreItem> selectedItems;
	private List<StoreItem> inventoryList;
	private double taxRate = 0.0825;
	
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
	
	public String getTotal() 
	{
		double total = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		
		if(!selectedItems.isEmpty()) {
			total = selectedItems.stream()
			.mapToDouble(item -> item.getPrice() * item.getUnits())
			.sum();
		} 
		
		return df.format(total);
	};

	public void purchaseItem(StoreItem item, int quantity) 
	{
		if(item != null) {
			boolean inStock = decrementInventory(item.getItemNo(), quantity);
			if(inStock) {
			selectedItems.add(new StoreItem(item.getItemNo(), 
					item.getItemDescription(),
					quantity,
					item.getPrice()
					));
			} 
		}
	};
	
	
	private boolean decrementInventory(int itemNo, int quantity) {
		boolean updated = false;
		for(StoreItem item : inventoryList) {
			if(itemNo == item.getItemNo() && quantity <= item.getUnits()) {
				int newQty = item.getUnits() - quantity;
				item.setUnits(newQty);
				updated = true;
			}
		}
		
		return updated;
	}
	
	private boolean incrementInventory(int itemNo, int quantity) {
		boolean updated = false;
		for(StoreItem item : inventoryList) {
			if(itemNo == item.getItemNo()) {
				int newQty = item.getUnits() + quantity;
				item.setUnits(newQty);
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
	
	public void clearRegister()
	{
		for(StoreItem item : selectedItems) {
			incrementInventory(item.getItemNo(), item.getUnits());
		}
		selectedItems.clear();
	};
	
	public void showInventory()
	{
		printHeader("store inventory");
		printColumns();
		for(StoreItem item : inventoryList) {
			System.out.println(item);
		}
	};
	
	public void checkOut()
	{
		printHeader("checkout");
		String items = selectedItems.stream()
		.sorted(Comparator.comparing(StoreItem::getItemDescription))
		.map(StoreItem::toString)
		.collect(Collectors.joining("\n"));
		
		System.out.println(items);
		
		double itemsTotal = Double.parseDouble(getTotal());
		double taxes = itemsTotal * taxRate;
		double taxedTotal = itemsTotal + taxes;
		DecimalFormat df = new DecimalFormat("0.00");

		String totals = String.format("%n%-31s%-10s%n%-31s%-10s%n%-31s%-10s%n%-31s%-10s%n", 
				"Subtotal", df.format(itemsTotal),
				"Tax rate:", (taxRate * 100) + "%",
				"Total taxes:", df.format(taxes),
				"Total:", df.format(taxedTotal));
		
		System.out.println(totals);
	};
	
}
