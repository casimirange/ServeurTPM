package com.example.demo.util;

public enum RoleEnum {

	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_USER("ROLE_USER"),
	ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN");
	
	private String name;

	private RoleEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
