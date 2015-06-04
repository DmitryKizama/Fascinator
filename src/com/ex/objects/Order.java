package com.ex.objects;

import java.sql.Date;

public class Order {
	private String customerName;
	private String phoneNumber;
	private Date date;
	private int dlitelnost;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDlitelnost() {
		return dlitelnost;
	}

	public void setDlitelnost(int dlitelnost) {
		this.dlitelnost = dlitelnost;
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
