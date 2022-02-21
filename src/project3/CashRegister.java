package project3;

import java.util.ArrayList;
import java.util.List;

public class CashRegister {
	
	List<StoreItem> itemsList;
	
	public CashRegister() 
	{
		itemsList = new ArrayList<StoreItem>();
	}
	
	public void displayMenu(List<StoreItem> itemsList)
	{
		System.out.println("=========");
		System.out.println("MAIN MENU");
		System.out.println("=========");
		
		System.out.println("Available items:");
		for(StoreItem item : itemsList) {
			System.out.println(" - " + item.getItemDescription());
		}
		
		
	};
	
	public double getTotal() 
	{
		return 0.0;
	};
	
	public void purchaseItem(StoreItem item) 
	{
		
	};
	
	public void showItems()
	{
		
	};
	
	private void clearRegister()
	{
		
	};
	
	private void showInventory()
	{
		
	};
	
	public boolean checkOut()
	{
		return true;
	};
	

}
