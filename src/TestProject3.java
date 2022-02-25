

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
		
		CashRegister register = new CashRegister(itemsList);
		
		register.displayMenu();
		
		input.close();
	}

	public File getFile(String type) {
		
		File file = null;
		
		System.out.println("Enter an " + type + " file path:");
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
			System.out.println("Illegal path:");
			System.out.println(e.getMessage());
		}
		
		return file;
	}
	
	public List<StoreItem> getItems(File inventoryFile) {
		
		List<StoreItem> itemList = new ArrayList<StoreItem>();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
			
			try {
				
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
				
			} catch (IOException | NumberFormatException e) {
				System.out.print("[ ERROR READING FILE ]: " + e.getMessage() + "\n");
				System.out.println("[ NO ITEMS UPLOADED ]");
			}
			
		} catch (FileNotFoundException | NullPointerException e) {
			System.out.print("[ FILE NOT FOUND ]\n");
		}
		
		return itemList;
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
      System.out.println("Project: Three\n");

   } // End of the developerInfo method
}
