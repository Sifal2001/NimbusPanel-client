package com.Client.User;



public class FavouriteLocations {
    private Long id;
    private String location;


    private User user;


	public FavouriteLocations(Long id, String location, User user) {
		super();
		this.id = id;
		this.location = location;
		this.user = user;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "FavouriteLocations [id=" + id + ", location=" + location + ", user=" + user + "]";
	}
    
}