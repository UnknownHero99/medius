package com.medius.student.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class View {
	interface Summary {}
}

@Entity
@Table(name = "players")
public class Player {

	@Id
	private String username;

	@JsonView(View.class)
	private int age;

	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Problem> problems = new ArrayList<>();


	public Player(String username, int age) {
		this.username = username;
		this.age = age;
	}

	public Player() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}
}
