package com.github.simulatan.addressbook.model;

public class Contact {

	private static int currentId = 0;

	private static synchronized int getNextId() {
		return currentId++;
	}

	private final int id;
	private String name;
	private String phone;
	private String email;

	public Contact(String name, String phone, String email) {
		this.id = getNextId();
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
