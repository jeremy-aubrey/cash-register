package project3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CashRegister {
	
	private List<StoreItem> selectedItems;
	private List<StoreItem> inventoryList;
	private double taxRate = 0.0825;
	private Scanner userIn = new Scanner(System.in);
	
	public CashRegister(List<StoreItem> inventoryList) 
	{
		selectedItems = new ArrayList<StoreItem>();
		this.inventoryList = inventoryList; 
	}
	
	public void selectItems()
	{
		boolean quit = false;
		int selection = 0;
		int quantity = 0;
		if(!inventoryList.isEmpty()) {
			
			while(!quit) {
				System.out.println("\n[ SELECT AN ITEM ]");
				for(int i = 0; i < inventoryList.size(); i++) {
					StoreItem item = inventoryList.get(i);
					System.out.printf("%-20s%-5s%n",
							item.getItemDescription(),
							"["+(i+1)+"]");
				}
				System.out.println("\n[ USE NEGATIVE NUMBER TO EXIT ]");
				
				selection = getUserSelection("\nEnter item: ");
				if(selection != 0) {
					quantity = getUserSelection("Enter quantity: ");
				}
				
				if(selection > 0 && selection <= inventoryList.size() && quantity > 0) {
					
					StoreItem item = inventoryList.get(selection - 1); //convert to index
					purchaseItem(item, quantity);
					
				} else if (selection < 0 || quantity < 0) {
					quit = true;
				}
			}
		} else {
			System.out.println("\n[ NO ITEMS FOUND ]");
		}
		
	}
	
	public void displayMenu() {
		boolean quit = false;
		while(!quit) {
			
			System.out.println("\n[ MENU OPTIONS ]");
			System.out.printf("%-20s%-5s%n%-20s%-5s%n%-20s%-5s%n%-20s%-5s%n%-20s%-5s%n%-20s%-5s%n",
					"Select Items", "[1]",
					"Show Cash Register", "[2]",
					"Clear Cash Register", "[3]",
					"Show Inventory", "[4]",
					"Check Out", "[5]",
					"Quit", "[6]");
			quit = dispatchAction();
		}
		
	};
	
	private int getUserSelection(String msg) {
		int selection = 0;
		System.out.print(msg);
		try {
			selection = Integer.parseInt(userIn.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid selection");
		}
		
		return selection;
	}
	
	private boolean dispatchAction() {
		boolean quit = false;
		int selection = getUserSelection("\nEnter selection: ");
		switch(selection) {
			case 1: 
				selectItems(); //select items
				break;
			case 2: 
				showItems(); //show cash register
				break;
			case 3: 
				clearRegister(); //clear cash register
				break;
			case 4: 
				showInventory(); //show inventory
				break;
			case 5:
				checkOut();
				break;
			case 6: //quit
				System.out.println("Good bye");
				quit = true;
				break;
			default:
				System.out.println("Choose an option from the menu");
		}
		
		return quit;
	}
	
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
				System.out.println("[ PURCHASING ITEM ] : " + item.getItemDescription());
				selectedItems.add(new StoreItem(item.getItemNo(), 
					item.getItemDescription(),
					quantity,
					item.getPrice()
					));
			} else {
				System.out.println("[ INSUFFICIENT STOCK ] : " + item.getUnits());
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
		System.out.println("-------------------------------------");
	};
	
	public void clearRegister()
	{
		for(StoreItem item : selectedItems) {
			incrementInventory(item.getItemNo(), item.getUnits());
		}
		selectedItems.clear();
		System.out.println("\n[ REGISTER CLEARED ]");
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
		boolean accept = false;
		printHeader("checkout");
		String items = selectedItems.stream()
		.sorted(Comparator.comparing(StoreItem::getItemDescription))
		.map(StoreItem::toString)
		.collect(Collectors.joining("\n"));
		
		double itemsTotal = Double.parseDouble(getTotal());
		double taxes = itemsTotal * taxRate;
		double taxedTotal = itemsTotal + taxes;
		DecimalFormat df = new DecimalFormat("0.00");

		String totals = String.format("%n%-31s%-10s%n%-31s%-10s%n%-31s%-10s%n%-31s%-10s%n", 
				"Subtotal", df.format(itemsTotal),
				"Tax rate:", (taxRate * 100) + "%",
				"Total taxes:", df.format(taxes),
				"Total:", df.format(taxedTotal));
		
		System.out.println(items);
		System.out.println(totals);
		printHeader("proceed ? y (yes) | n (no)");
		String choice = userIn.nextLine().toUpperCase();
		
		if(choice.equals("Y")) {
			String recieptData = items.concat("\n" + totals);
			getReciept(recieptData);
		} else if(choice.equals("N")) {
			clearRegister();
		} else {
			System.out.println("[ RETURNING TO MAIN MENU ]");
		}
	};
	
	private void getReciept(String receiptData) {
		//get receipt path
		File receiptPath = getFile("receipt");
		
		if(receiptPath != null) {
			//populate with data (write)
			try {
				FileWriter fw = new FileWriter(receiptPath);
				fw.write("SALES RECEIPT\n");
				fw.write("-------------------------------------\n");
				fw.write(receiptData);
				fw.write("-------------------------------------\n");
				fw.write("THANK YOU FOR SHOPPING WITH US");
				System.out.println("\n[ GENERATING RECEIPT ]");
				fw.close();
				
			} catch (IOException e) {
				System.out.println("\n[ ERROR GENERATING RECEIPT ]");

			}
		} 	
	}
	
	public File getFile(String type) {
		
		File file = null;
		
		System.out.println("Enter an " + type + " file path:");
		Path path = null;
	
		//get legal path
		try {
			path = Paths.get(userIn.nextLine().trim());
			boolean exists = Files.exists(path);
			
			//instantiate File if its readable
			if(!exists) {
				file = new File(path.toString());
				System.out.println("Using: " + path);
			} else {
				System.out.println("\n[ FILE EXISTS, USE ANOTHER PATH ]");
			}
			
		} catch (InvalidPathException | SecurityException | NullPointerException e) {
			System.out.print("\n[ ILLEGAL FILE PATH ]: ");
			System.out.println(e.getMessage());
		}
		
		return file;
	}
	
	

	
}
