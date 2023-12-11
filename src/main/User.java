package main;

/*
*   File: User.java
*   Authors: Matthew Homan, Bethany Van Waes, and Michael Rach
*   Date: 12/12/2023
*   Purpose: This file creates the User object for signing into the program. 
*/

public class User {
	private String name;
	private String password;

	//constructor sets name and password
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	//getter for getting name
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
}
