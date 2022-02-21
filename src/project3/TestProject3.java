package project3;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TestProject3 {
	public static void main(String[] args) {
		
		TestProject3 obj = new TestProject3();
		obj.developerInfo();
		
		File inventoryFile = obj.getInventoryFile();
		
		CashRegister register = new CashRegister();
		register.displayMenu();
	}
	
	public File getInventoryFile() {
		
		File inventoryFile = null;
		Scanner input = new Scanner(System.in);
		System.out.println("Enter an inventory file path:");
		Path path = null;
	
		//get legal path
		try {
			path = Paths.get(input.nextLine().trim());
			boolean exists = Files.exists(path);
			boolean isReadable = Files.isReadable(path);
			boolean isDirectory = Files.isDirectory(path);
			
			//instantiate File if its readable
			if(exists && isReadable && !isDirectory) {
				inventoryFile = new File(path.toString());
				System.out.println("Using: " + path);
			} else {
				System.out.println("Unable to read file");
			}
			
		} catch (InvalidPathException | SecurityException | NullPointerException e) {
			System.out.println("Illegal path:");
			System.out.println(e.getMessage());
		}
		
		input.close();
		return inventoryFile;
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
