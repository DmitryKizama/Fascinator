package com.ex.objects;

public class Order {
	private String customerName;
	private String phoneNumber;
	private String date;
	private String numberofhourse;
	private static final int ANIMATORS = 1;
	private String character;
	private String Adress;
	private String AdditionalServices;
	private int price;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNumberofhourse() {
		return numberofhourse;
	}

	public void setNumberofhours(String dlitelnost) {
		this.numberofhourse = dlitelnost;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getAdress() {
		return Adress;
	}

	public void setAdress(String adress) {
		Adress = adress;
	}

	public String getAdditionalServices() {
		return AdditionalServices;
	}

	public void setAdditionalServices(String additionalServices) {
		AdditionalServices = additionalServices;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public static int getAnimators() {
		return ANIMATORS;
	}

}
