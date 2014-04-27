package com.example.realdaydb;

public class User {
	public User(String name, String email, String username, String password) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setPassword(String password) {
		this.password = password;

	}

	private String username;
	private String password;
	private String email;
	private String name;

}
