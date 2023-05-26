package com.github.simulatan.addressbook.model;

import java.util.Objects;

public class Contact {

	private static int currentId = 0;

	private static synchronized int getNextId() {
		return currentId++;
	}

	private int id;
	private String name;
	private String phone;
	private String email;

	public Contact(String name, String phone, String email) {
		this(getNextId(), name, phone, email);
	}

	public Contact(int id, String name, String phone, String email) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	// package-private to allow AddressBook to set the id from the database
	void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		AddressBook.getInstance().updateContact(this);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != null && phone.isEmpty()) phone = null;
		if (phone != null && !phone.matches("\\+?[\\d-]+")) throw new IllegalArgumentException("Invalid phone number!");

		this.phone = phone;
		AddressBook.getInstance().updateContact(this);
	}

	public String getEmail() {
		return email;
	}

	private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	public void setEmail(String email) {
		if (email != null && email.isEmpty()) email = null;
		if (email != null && !email.matches(EMAIL_REGEX)) throw new IllegalArgumentException("Invalid E-Mail!");

		this.email = email;
		AddressBook.getInstance().updateContact(this);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contact contact = (Contact) o;
		return id == contact.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
