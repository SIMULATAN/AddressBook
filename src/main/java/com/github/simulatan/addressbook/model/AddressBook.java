package com.github.simulatan.addressbook.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class AddressBook {

	private static AddressBook instance;

	public static AddressBook getInstance() {
		return (instance != null ? instance : (instance = new AddressBook()));
	}

	private final ObservableList<Contact> contacts = FXCollections.observableList(new LinkedList<>());

	public void addContact(Contact contact) {
		contacts.add(contact);
	}

	public void deleteContact(Contact item) {
		contacts.remove(item);
	}

	public Contact getContact(int id) {
		return contacts
			.stream()
			.filter(c -> c.getId() == id)
			.findFirst()
			.orElse(null);
	}

	public ObservableList<Contact> getContacts() {
		return FXCollections.unmodifiableObservableList(contacts);
	}
}
