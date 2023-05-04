package com.github.simulatan.addressbook.controller;

import com.github.simulatan.addressbook.model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

	@FXML
	void newContact(ActionEvent event) {
		txtId.clear();
		txtMail.clear();
		txtName.clear();
		txtPhone.clear();
	}

	@FXML
	void deleteContact(ActionEvent event) {

	}

	@FXML
	void saveContact(ActionEvent event) {
		String name = txtName.getText();
		String phone = txtPhone.getText();
		String mail = txtMail.getText();

		if (name == null || name.isEmpty()) {
			error("Name is unset!");
			return;
		}
	}

	@FXML
	void searchContacts(ActionEvent event) {

	}

	private static void error(String s) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setContentText(s);
		alert.show();
	}
}
