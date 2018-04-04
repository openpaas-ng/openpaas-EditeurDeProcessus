package org.linagora.dao;

import com.google.gson.Gson;

public class OpenPaasUser {

	private String id;
	private String username;
	private String password;

	public OpenPaasUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String generateJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
