//******************************************************************************
//
//  Developer:     Jeremy Aubrey
//
//  Project #:     Project 3 
//
//  File Name:     CashRegister.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      2/27/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   Simulates a cash register allowing a user to interact through 
//                 a menu. Users can purchase items, view the items in the register's
//                 internal list, view the store inventory, clear the register, and
//                 checkout. Upon check out, a receipt is generated in a new file.
//
//******************************************************************************

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CashRegister {
	
	private List<StoreItem> selectedItems;
	private List<StoreItem> inventoryList;
	private List<String> cashierList;
	private double taxRate = 0.0825;
	private Scanner userIn = new Scanner(System.in);
	
	// constructor
	public CashRegister(List<StoreItem> inventoryList, List<String> cashierList) {
		
		selectedItems = new ArrayList<StoreItem>();
		this.inventoryList = inventoryList; 
		this.cashierList = cashierList;
		
	} // end constructor

    //***************************************************************
    //
    //  Method:       selectItems (Non Static)
    // 
    //  Description:  Allows users to select items available in the
    //                inventory list. Users can exit by entering a negative
    //                value.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void selectItems()
	{
		boolean quit = false;
		int selection = 0;
		int quantity = 0;
		if(!inventoryList.isEmpty()) { // only for non-empty list
			
			while(!quit) { // display all the items in the inventory list
				System.out.println("\n[ SELECT AN ITEM ]");
				for(int i = 0; i < inventoryList.size(); i++) {
					StoreItem item = inventoryList.get(i);
					System.out.printf("%-20s%-5s%n",
							item.getItemDescription(),
							"["+(i+1)+"]");
				}
				
				System.out.println("\n[ USE NEGATIVE NUMBER TO EXIT ]");
				
				// get the user's selection (item and quantity)
				selection = getUserSelection("\nEnter item: ");
				if(selection != 0) {
					quantity = getUserSelection("Enter quantity: ");
				}
				
				// if selection is valid add to internal register list
				// verifies item is in list range and units are available
				if(selection > 0 && selection <= inventoryList.size() && quantity > 0) {
					
					StoreItem item = inventoryList.get(selection - 1); // menu index -> list index 
					purchaseItem(item, quantity);
					
				} else if (selection < 0 || quantity < 0) {
					quit = true; // quit flag entered (negative) 
				}
			}
		
		//list is empty 
		} else {
			System.out.println("\n[ NO ITEMS FOUND ]");
		}
	
	}// end selectItems method
	
    //***************************************************************
    //
    //  Method:       displayMenu (Non Static)
    // 
    //  Description:  Displays the menu to the user until the user
    //                selects a quit option.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
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
		
	}// end displayMenu method
	
    //***************************************************************
    //
    //  Method:       dispatchAction (Non Static)
    // 
    //  Description:  A method that calls cash register action methods 
    //                based on user's selection from menu. Returns a
    //                flag to displayMenu used to quit or continue.
    //
    //  Parameters:   None
    //
    //  Returns:      boolean
    //
    //**************************************************************
	private boolean dispatchAction() {
		
		boolean quit = false;
		int selection = getUserSelection("\nEnter selection: ");
		switch(selection) {
			case 1: 
				selectItems(); // select items
				break;
			case 2: 
				showItems(); // show cash register
				break;
			case 3: 
				clearRegister(); // clear cash register
				break;
			case 4: 
				showInventory(); // show inventory
				break;
			case 5:
				checkOut(); // check out
				break;
			case 6: // quit
				System.out.println("Good bye");
				quit = true;
				break;
			default:
				// for all unrecognized input
				System.out.println("Choose an option from the menu"); 
		}
		
		return quit;
		
	}// end dispatchAction method
	
    //***************************************************************
    //
    //  Method:       getUserSelection (Non Static)
    // 
    //  Description:  Gets user input. The caller can pass a message 
    //                to be printed. All non-integer entries result 
    //                in a value of 0 returned. 
    //
    //  Parameters:   String
    //
    //  Returns:      int
    //
    //**************************************************************
	private int getUserSelection(String msg) {
		
		int selection = 0;
		System.out.print(msg);
		try {
			
			selection = Integer.parseInt(userIn.nextLine());
			
		} catch (NumberFormatException e) {
			
			System.out.println("Invalid selection");
		}
		
		return selection;
		
	}// end getUserSelection method
	
    //***************************************************************
    //
    //  Method:       printHeader (Non Static)
    // 
    //  Description:  A helper method to display header information.
    //
    //  Parameters:   String
    //
    //  Returns:      int
    //
    //**************************************************************
	private void printHeader(String content) {
		
		System.out.println("-------------------------------------");
		System.out.println(content.toUpperCase());
		System.out.println("-------------------------------------");
		
	}// end printHeader method
	
    //***************************************************************
    //
    //  Method:       printColumns (Non Static)
    // 
    //  Description:  A helper method to display column descriptors 
    //                for items.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
	private void printColumns() {
		
		System.out.printf("%-8s%-15s%-8s%-10s%n", 
				"ItemNo",
				"Description",
				"Qty",
				"Price");
		System.out.println("-------------------------------------");
		
	}// end printColumns method
	
    //***************************************************************
    //
    //  Method:       getTotal (Non Static)
    // 
    //  Description:  Uses streams and lambdas to get a sum of the 
    //                items in the registers internal list.
    //
    //  Parameters:   None
    //
    //  Returns:      String
    //
    //**************************************************************
	public String getTotal() {
		
		double total = 0.00;
		DecimalFormat df = new DecimalFormat("0.00"); // format
		
		if(!selectedItems.isEmpty()) {
			total = selectedItems.stream() // create a stream
			.mapToDouble(item -> item.getPrice() * item.getUnits()) // map each item to a double (price)
			.sum(); // sum the doubles
		} 
		
		return df.format(total); // format to 2 decimal places
		
	}//end getTotal method

    //***************************************************************
    //
    //  Method:       purchaseItem (Non Static)
    // 
    //  Description: Adds a new StoreItem to the register's internal
    //               list if the units in stock are sufficient. The 
    //               method receives a StoreItem and a quantity that 
    //               the user wishes to purchase. 
    //
    //  Parameters:   StoreItem, int
    //
    //  Returns:      String
    //
    //**************************************************************
	public void purchaseItem(StoreItem item, int quantity) {
		
		if(item != null) { // if a valid item was selected
			
			//only true if units in stock >= quantity to purchase
			boolean inStock = decrementInventory(item.getItemNo(), quantity);
			
			if(inStock) {
				
				// report item purchase success
				System.out.println("[ PURCHASING ITEM ] : " + item.getItemDescription());
				
				// add a new item to the internal list
				selectedItems.add(new StoreItem(item.getItemNo(), 
					item.getItemDescription(),
					quantity,
					item.getPrice()));
				
			} else {
				
				// report item purchase failure
				System.out.println("[ INSUFFICIENT STOCK ] : " + item.getUnits());
			}
		}
	}// end purchaseItem method
	
    //***************************************************************
    //
    //  Method:       decrementInventory (Non Static)
    // 
    //  Description:  Decrements a StoreItem's 'units' field in the main
    //                inventory list if its value is greater than or equal
    //                to the quantity it receives (prevent over selling).
    //
    //  Parameters:   int, int
    //
    //  Returns:      boolean
    //
    //**************************************************************
	private boolean decrementInventory(int itemNo, int quantity) {
		
		boolean updated = false; // flag for successful item update
		for(StoreItem item : inventoryList) {
			
			//check if item exists and if quantity is available
			if(itemNo == item.getItemNo() && quantity <= item.getUnits()) {
				int newQty = item.getUnits() - quantity; // calculate new quantity
				item.setUnits(newQty); // set new quantity
				updated = true;  
			}
		}
		
		return updated;
		
	}// end decrementInventory
	
    //***************************************************************
    //
    //  Method:       incrementInventory (Non Static)
    // 
    //  Description:  Increments a StoreItem's 'units' field in the main
    //                inventory list. Invoked whenever the register is cleared
    //                in order to re-populate the main inventory list. (Re-stock 
    //                cancelled orders). 
    //
    //  Parameters:   int, int
    //
    //  Returns:      boolean
    //
    //**************************************************************
	private boolean incrementInventory(int itemNo, int quantity) {
		
		boolean updated = false; // flag for successful item update
		
		for(StoreItem item : inventoryList) {
			
			// find item and increment by quantity
			if(itemNo == item.getItemNo()) {
				int newQty = item.getUnits() + quantity; // calculate new quantity
				item.setUnits(newQty); // set new quantity (re-stock)
				updated = true;
			}
		}
		
		return updated;
		
	}// end incrementInventory method
	
    //***************************************************************
    //
    //  Method:       showItems (Non Static)
    // 
    //  Description:  Displays the current StoreItems in the cash 
    //                register's internal list.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void showItems() {
		
		// for readability
		printHeader("register items");
		printColumns(); 
		
		if(!selectedItems.isEmpty()) { // only for non-empty list
			
			for(StoreItem item : selectedItems) {
				System.out.println(item);
			}
			
		} else {
			
			System.out.println("< NO ITEMS >");
		}
		
		System.out.println("-------------------------------------");
		
	}// end showItems method
	
    //***************************************************************
    //
    //  Method:       clearRegister (Non Static)
    // 
    //  Description:  Displays the current StoreItems in the cash 
    //                register's internal list.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void clearRegister() {
		
		for(StoreItem item : selectedItems) { // using internal register list
			incrementInventory(item.getItemNo(), item.getUnits()); // re-stock main inventory
		}
		
		selectedItems.clear(); //clear internal list
		System.out.println("\n[ REGISTER CLEARED ]");
		
	}// end clearRegister method
	
    //***************************************************************
    //
    //  Method:       showInventory (Non Static)
    // 
    //  Description:  Displays the StoreItems in the main inventory list.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void showInventory() {
		
		// for readability
		printHeader("store inventory");
		printColumns();
		
		for(StoreItem item : inventoryList) { // using main inventory list
			System.out.println(item);
		}
		
	}//end showInventory
	
    //***************************************************************
    //
    //  Method:       checkOut (Non Static)
    // 
    //  Description:  Displays the StoreItems and totals generated from 
    //                the cash register's internal list using streams. 
    //                Prompts the user to accept or reject the order.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void checkOut() {
		
		printHeader("checkout");
		
		// use stream to order items by description and store as String
		String items = selectedItems.stream()
		.sorted(Comparator.comparing(StoreItem::getItemDescription))
		.map(StoreItem::toString)
		.collect(Collectors.joining("\n"));
		
		// get totals and taxes
		double itemsTotal = Double.parseDouble(getTotal()); // getTotal uses streams and lambdas
		double taxes = itemsTotal * taxRate;
		double taxedTotal = itemsTotal + taxes;
		DecimalFormat df = new DecimalFormat("0.00"); // format (2 decimal places)

		// format totals and store as String
		String totals = String.format("%n%-31s%-10s%n%-31s%-10s%n%-31s%-10s%n%-31s%-10s%n", 
				"Subtotal", df.format(itemsTotal),
				"Tax rate:", (taxRate * 100) + "%",
				"Total taxes:", df.format(taxes),
				"Total:", df.format(taxedTotal));
		
		// display order details to user
		System.out.println(items);
		System.out.println(totals);
		
		// accept or reject
		printHeader("proceed ? y (yes) | n (no)");
		String choice = userIn.nextLine().toUpperCase();
		
		// get receipt if accept and clear register if reject
		if(choice.equals("Y")) {
			
			String recieptData = items.concat("\n" + totals);
			getReciept(recieptData);
			
		} else if(choice.equals("N")) {
			
			clearRegister();
			
		} else {
			System.out.println("[ RETURNING TO MAIN MENU ]");
		}
		
	}// end checkOut method

    //***************************************************************
    //
    //  Method:       getReciept (Non Static)
    // 
    //  Description:  Generates a receipt as a new file using data
    //                from checkOut method.
    //
    //  Parameters:   String
    //
    //  Returns:      N/A
    //
    //**************************************************************
	private void getReciept(String receiptData) {
		
		// receipt path
		File receiptPath = new File("./Project3-Output.txt");
		
		if(receiptPath != null) {
			//populate with data (write)
			try {
				Date date = new Date();
				FileWriter fw = new FileWriter(receiptPath);
				fw.write("SALES RECEIPT\n\n");
				fw.write("Cashier: " + getRandomCashier() + "\n");
				fw.write("Date: " + date.toString() + "\n");
				fw.write("-------------------------------------\n");
				fw.write(receiptData);
				fw.write("-------------------------------------\n");
				fw.write("THANK YOU FOR SHOPPING WITH US");
				System.out.println("\n[ GENERATING RECEIPT ]");
				System.out.println("\n[ COMPLETE ] >> " + receiptPath.getAbsolutePath());
				fw.close();
				
			} catch (IOException e) {
				
				System.out.println("\n[ ERROR GENERATING RECEIPT ]");
			}
		} 	
	}// end getReceipt method
	
    //***************************************************************
    //
    //  Method:       getRandomCashier (Non Static)
    // 
    //  Description:  Gets a random cashier from the list of cashiers.
    //
    //  Parameters:   None
    //
    //  Returns:      String
    //
    //**************************************************************
	private String getRandomCashier() {
			
		String cashier = "";
		
		if(!cashierList.isEmpty()) {
			cashier = cashierList.get(new Random().nextInt(cashierList.size()));
		}
			
		return cashier;	
		
	}// end getRandomCashier method
	
}// end CashRegister class