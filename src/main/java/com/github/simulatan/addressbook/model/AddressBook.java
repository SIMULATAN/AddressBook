package com.github.simulatan.addressbook.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedList;

public class AddressBook {

	private static AddressBook instance;

	public static AddressBook getInstance() {
		return (instance != null ? instance : (instance = new AddressBook()));
	}

	private final Connection connection;

	private AddressBook() {
		try {
			connection = DriverManager.getConnection("jdbc:derby:db");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try (PreparedStatement ps = connection.prepareStatement("SELECT id, name, phonenr, email FROM contact"); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				contacts.add(new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("phonenr"), rs.getString("email")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private final ObservableList<Contact> contacts = FXCollections.observableList(new LinkedList<>());

	public void addContact(Contact contact) {
		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (name, phonenr, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, contact.getName());
			ps.setString(2, contact.getPhone());
			ps.setString(3, contact.getEmail());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					contact.setId(rs.getInt(1));
					contacts.add(contact);
				} else {
					throw new IllegalStateException("No generated keys returned!");
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void deleteContact(Contact item) {
		contacts.remove(item);
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM contact WHERE id = ?")) {
			ps.setInt(1, item.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void updateContact(Contact item) {
		try (PreparedStatement ps = connection.prepareStatement("UPDATE contact SET name = ?, phonenr = ?, email = ? WHERE id = ?")) {
			ps.setString(1, item.getName());
			ps.setString(2, item.getPhone());
			ps.setString(3, item.getEmail());
			ps.setInt(4, item.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
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
