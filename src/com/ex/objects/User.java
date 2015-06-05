package com.ex.objects;

public class User {
	private String objectId;
	private String username;
	private String password;
	private boolean isAdmin;
	private static User instance;

	public User() {
	}

	public static User getInstance() {
		if (instance == null) {
			instance = new User();
		}
		return instance;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [objectId=" + objectId + ", username=" + username + "]";
	}
}
