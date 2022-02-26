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
//  Description:   A class that maintains data about a store item in inventory.
//
//******************************************************************************

public class StoreItem {
	
	private int units;
	private int itemNo;
	private String itemDescription;
	private double price;
	
	// constructor 
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
		
		//ensure non-negative units
		if(units >= 0) {
			this.units = units;
		} else {
			this.units = 0;
		}
		
		//ensure non-null description
		if(itemDescription != null) {
			this.itemDescription = itemDescription;
		} else {
			this.itemDescription = "(Missing description)";
		}
		
	}// end constructor
	
	public int getUnits() {
		return units;
	}
	
	public void setUnits(int quantity) {
		units = quantity;
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
		
		return String.format("%-8s%-15s%-8s%-10s", 
			getItemNo(), 
			getItemDescription(),
			getUnits(),
			String.format("%.2f", getPrice()));
		
	}// end toString method

}// end StoreItem class