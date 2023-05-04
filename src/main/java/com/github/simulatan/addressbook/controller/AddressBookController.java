package com.github.simulatan.addressbook.controller;

import com.github.simulatan.addressbook.model.AddressBook;
import com.github.simulatan.addressbook.model.Contact;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddressBookController {

	@FXML
	private ListView<Contact> lsContacts;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtMail;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtSearchQuery;

	private final SimpleIntegerProperty contactNumber = new SimpleIntegerProperty();
	@FXML
	private Label lblNumberOfContacts;

	private FilteredList<Contact> contacts;

	@FXML
	void initialize() {
		contactNumber.bind(Bindings.size(AddressBook.getInstance().getContacts()));

		this.lblNumberOfContacts.textProperty().bind(new StringBinding() {
			{
				bind(contactNumber);
			}
			@Override
			protected String computeValue() {
				return "Number of contacts: " + contactNumber.get();
			}
		});
		contacts = new FilteredList<>(AddressBook.getInstance().getContacts());
		this.lsContacts.setItems(contacts);
		this.lsContacts.getSelectionModel().selectedItemProperty().addListener((observableValue, old, contact) -> {
			if (contact == null) {
				clearFields();
				return;
			}
			this.txtId.setText(String.valueOf(contact.getId()));
			this.txtName.setText(contact.getName());
			this.txtPhone.setText(contact.getPhone());
			this.txtMail.setText(contact.getEmail());
		});
	}

	@FXML
	void newContact(ActionEvent event) {
		clearFields();
		this.lsContacts.getSelectionModel().select(null);
	}

	private void clearFields() {
		txtId.clear();
		txtMail.clear();
		txtName.clear();
		txtPhone.clear();
	}

	@FXML
	void deleteContact(ActionEvent event) {
		Contact item = lsContacts.getSelectionModel().getSelectedItem();
		if (item == null) {
			error("No item selected.");
		}

		AddressBook.getInstance().deleteContact(item);
		clearFields();
		lsContacts.getSelectionModel().select(null);
	}

	@FXML
	void saveContact(ActionEvent event) {
		String idString = txtId.getText();
		Integer id;
		try {
			id = idString == null || idString.isEmpty() ? null : Integer.parseInt(idString);
		} catch (NumberFormatException ignored) {
			error("Invalid ID entered!");
			return;
		}

		String name = txtName.getText();
		String phone = txtPhone.getText();
		String mail = txtMail.getText();

		if (name == null || name.isEmpty()) {
			error("Name is unset!");
			return;
		}

		if (id == null) {
			Contact contact;
			try {
				contact = new Contact(name, phone, mail);
			} catch (IllegalArgumentException e) {
				error(e.getMessage());
				return;
			}
			AddressBook.getInstance().addContact(contact);
			clearFields();
		} else {
			Contact contact = AddressBook.getInstance().getContact(id);
			try {
				contact.setName(name);
				contact.setPhone(phone);
				contact.setEmail(mail);
			} catch (IllegalArgumentException e) {
				error(e.getMessage());
				return;
			}
			lsContacts.refresh();
		}
	}

	@FXML
	void searchContacts(ActionEvent event) {
		String query = txtSearchQuery.getText().toLowerCase();
		this.contacts.setPredicate(contact -> contact.getName().toLowerCase().contains(query)
			|| contact.getEmail().toLowerCase().contains(query));
	}

	private static void error(String s) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setContentText(s);
		alert.show();
	}
}
