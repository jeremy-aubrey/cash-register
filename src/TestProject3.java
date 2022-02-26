//******************************************************************************
//
//  Developer:     Jeremy Aubrey
//
//  Project #:     Project 2 
//
//  File Name:     ShapeTest.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      2/20/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   This program displays the attributes of each shape type,
//                 including type, dimension type, area, and volume if
//                 applicable.
//
//                 You are allowed to modify only line 84. If you modify
//                 any other part of the class, you will not receive credit
//                 for this project.
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
	
	public static void main(String[] args) {
		
		TestProject3 obj = new TestProject3();
		obj.developerInfo();
		
		File inventoryFile = obj.getFile("inventory");
		List<StoreItem> itemsList = obj.getItems(inventoryFile);
		
		File cashiersFile = obj.getFile("cashier");
		List<String> cashierList = obj.getCashiers(cashiersFile);
		
		CashRegister register = new CashRegister(itemsList, cashierList);
		
		register.displayMenu();
		
		input.close();
	}

	public File getFile(String label) {
		
		File file = null;
		
		System.out.println("\nEnter an " + label + " file path:");
		Path path = null;
	
		//get legal path
		try {
			path = Paths.get(input.nextLine().trim());
			boolean exists = Files.exists(path);
			boolean isReadable = Files.isReadable(path);
			boolean isDirectory = Files.isDirectory(path);
			
			//instantiate File if its readable
			if(exists && isReadable && !isDirectory) {
				file = new File(path.toString());
				System.out.println("Using: " + path);
			} 
			
		} catch (InvalidPathException | SecurityException | NullPointerException e) {
			System.out.println("[ ILLEGAL PATH ]: " + e.getMessage());
		}
		
		return file;
	}
	
	public List<StoreItem> getItems(File inventoryFile) {
		
		List<StoreItem> itemList = new ArrayList<StoreItem>();
			
		BufferedReader reader;
			
			try {
				reader = new BufferedReader(new FileReader(inventoryFile));
				String line = reader.readLine();
				while(line != null) {
					String[] itemData = line.replaceAll("\\s+", ",").split(",");
					if(itemData.length == 4) {
						StoreItem item = new StoreItem(
								Integer.parseInt(itemData[0]), 
								itemData[1], 
								Integer.parseInt(itemData[2]), 
								Double.parseDouble(itemData[3]));
						
						itemList.add(item);
					}
					line = reader.readLine();
				}
				System.out.println("[ " + itemList.size() + " ITEMS UPLOADED ]");
				
				if(reader != null) {
					reader.close();
				}
				
			} catch (IOException | NumberFormatException | NullPointerException e) {
				System.out.print("[ ERROR READING FILE ]: " + e.getMessage() + "\n");
				System.out.println("[ NO ITEMS UPLOADED ]");
			}
		
		return itemList;
	}
	
	private List<String> getCashiers(File cashiersFile) {
		
		BufferedReader reader = null;
		List<String> cashierList = new ArrayList<String>();
		
		try {
			reader = new BufferedReader(new FileReader(cashiersFile));
			String line = reader.readLine();
			while(line != null) {
				cashierList.add(line);
				line = reader.readLine();
			}
			System.out.println("[ " + cashierList.size() + " CASHIERS UPLOADED ]");
			
			if(reader != null) {
				reader.close();
			}
			
		} catch (IOException | InvalidPathException | NullPointerException e) {
			
			System.out.print("[ FILE NOT FOUND ]: " + e.getMessage() + "\n");
			System.out.println("[ NO CASHIERS UPLOADED ]");
		}
		
		return cashierList;

	}
	
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

   } // End of the developerInfo method
}
