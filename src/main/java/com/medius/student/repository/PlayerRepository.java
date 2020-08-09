package com.medius.student.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medius.student.model.Player;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository implements ObjectRepository<Player> {

	private Map<String, Player> repository;
	
	public PlayerRepository() {
		this.repository = new HashMap<>();
	}
	
	@Override
	public void store(Player player) {
		repository.put(player.getUsername(), player);
	}

	public Player retrieve(String username) {
		return repository.get(username);
	}

	
	public List<Player> getAll() {
		List<Player> players = new ArrayList<>();
		
		for(Player player : this.repository.values())
			players.add(player);
		
		return players;
	}

}
