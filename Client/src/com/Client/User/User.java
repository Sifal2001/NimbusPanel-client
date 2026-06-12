package com.Client.User;

import java.util.List;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<FavouriteLocations> favouriteLocations;
	
	public User(String firstName, String lastName, String email, String password,
			List<FavouriteLocations> favouriteLocations) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.favouriteLocations = favouriteLocations;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<FavouriteLocations> getFavouriteLocations() {
		return favouriteLocations;
	}
	
	public void setFavouriteLocations(List<FavouriteLocations> favouriteLocations) {
		this.favouriteLocations = favouriteLocations;
	}
	
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password
				+ ", favouriteLocations=" + favouriteLocations + "]";
	}

}