//******************************************************************************
//
//  Developer:     Jeremy Aubrey
//
//  Project #:     Project 3 
//
//  File Name:     TestProject3.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      2/27/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   A driver for the CashRegister class. The driver is responsible
//                 for prompting the user for file paths that will be used to populate
//                 data that will be used by the CashRegister.
//
//******************************************************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestProject3 {
	
	private static Scanner input = new Scanner(System.in);
	
   //***************************************************************
   //
   //  Method:       main
   // 
   //  Description:  The main method of the program
   //
   //  Parameters:   String array
   //
   //  Returns:      N/A 
   //
   //***************************************************************
	public static void main(String[] args) {
		
		TestProject3 obj = new TestProject3();
		obj.developerInfo();
		
		// user provides a path to an item inventory file
		File inventoryFile = obj.getFile("inventory");
		List<StoreItem> itemsList = obj.getItems(inventoryFile);
		
		// user provides a path to a cashiers list file
		File cashiersFile = obj.getFile("cashier");
		List<String> cashierList = obj.getCashiers(cashiersFile);
		
		// instantiate a CashRegister object
		CashRegister register = new CashRegister(itemsList, cashierList);
		
		// allow the user to interact using a menu
		register.displayMenu();
		
		input.close(); //close scanner
		
	}// end main

    //***************************************************************
    //
    //  Method:       getFile (Non Static)
    // 
    //  Description:  Attempts to get a readable file path from the user.
    //                Prompts the user with a customizable message.
    //
    //  Parameters:   String
    //
    //  Returns:      File 
    //
    //**************************************************************
	public File getFile(String label) {
		
		File file = null; // file to return
		Path path = null; // path to validate
		System.out.println("\nEnter an " + label + " file path:");
	
		// attempt to get a legal file path
		try {
			
			path = Paths.get(input.nextLine().trim());
			boolean exists = Files.exists(path);
			boolean isReadable = Files.isReadable(path);
			boolean isDirectory = Files.isDirectory(path);
			
			//instantiate File if path if valid
			if(exists && isReadable && !isDirectory) {
				file = new File(path.toString());
				System.out.println("Using: " + path);
			} 
			
		} catch (InvalidPathException | SecurityException | NullPointerException e) {
			System.out.println("[ ILLEGAL PATH ]: " + e.getMessage());
		}
		
		return file;
		
	}// end getFile method
	
    //***************************************************************
    //
    //  Method:       getItems (Non Static)
    // 
    //  Description:  Attempts to populate a new ArrayList with Item 
    //                objects from data in inventory file. Guaranteed 
    //                to return a new ArrayList even if its empty
    //                (unsuccessful file read or null).
    //
    //  Parameters:   File
    //
    //  Returns:      List<StoreItem>
    //
    //**************************************************************
	public List<StoreItem> getItems(File inventoryFile) {
		
		List<StoreItem> itemList = new ArrayList<StoreItem>();
		BufferedReader reader;
			
		try {
			
			reader = new BufferedReader(new FileReader(inventoryFile));
			String line = reader.readLine();
			while(line != null) {
				// generate an array from each line and create a new StoreItem
				String[] itemData = line.replaceAll("\\s+", ",").split(",");
				if(itemData.length == 4) {
					StoreItem item = new StoreItem(
							Integer.parseInt(itemData[0]), 
							itemData[1], 
							Integer.parseInt(itemData[2]), 
							Double.parseDouble(itemData[3]));
					
					itemList.add(item); // add item to the ArrayList
				}
				
				line = reader.readLine(); // move to next line
			}
			
			// report number of items uploaded
			System.out.println("[ " + itemList.size() + " ITEMS UPLOADED ]");
			
			if(reader != null) {
				reader.close();
			}
			
		} catch (IOException | NumberFormatException | NullPointerException e) {
			
			System.out.print("[ ERROR READING FILE ]: " + e.getMessage() + "\n");
			System.out.println("[ NO ITEMS UPLOADED ]");
		}
		
		return itemList;
		
	}// end getItems method
	
    //***************************************************************
    //
    //  Method:       getCashiers (Non Static)
    // 
    //  Description:  Attempts to populate a new ArrayList with Strings 
    //                (cashier names) from cashiers file. Guaranteed 
    //                to return a new ArrayList even if its empty
    //                (unsuccessful file read or null).
    //
    //  Parameters:   File
    //
    //  Returns:      List<String>
    //
    //**************************************************************
	private List<String> getCashiers(File cashiersFile) {
		
		BufferedReader reader = null;
		List<String> cashierList = new ArrayList<String>();
		
		try {
			
			reader = new BufferedReader(new FileReader(cashiersFile));
			String line = reader.readLine();
			while(line != null) { 
				cashierList.add(line); // add String to the ArrayList
				line = reader.readLine(); // move to next line
			}
			
			// report number of items uploaded
			System.out.println("[ " + cashierList.size() + " CASHIERS UPLOADED ]");
			
			if(reader != null) {
				reader.close();
			}
			
		} catch (IOException | InvalidPathException | NullPointerException e) {
			
			System.out.print("[ FILE NOT FOUND ]: " + e.getMessage() + "\n");
			System.out.println("[ NO CASHIERS UPLOADED ]");
		}
		
		return cashierList;

	}// end getCashiers method
	
   //***************************************************************
   //
   //  Method:       developerInfo (Non Static)
   // 
   //  Description:  The developer information method of the program
   //
   //  Parameters:   None
   //
   //  Returns:      N/A 
   //
   //**************************************************************
   public void developerInfo() {
	   
      System.out.println("Name:    Jeremy Aubrey");
      System.out.println("Course:  COSC 4301 Modern Programming");
      System.out.println("Project: Three");

   }// end developerInfo method
   
}// end TestProject3 class
