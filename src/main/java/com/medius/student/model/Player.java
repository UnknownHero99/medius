package com.medius.student.model;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {

	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private String username;

	private int age;


	public Player(String username, int age) {
		this.username = username;
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
