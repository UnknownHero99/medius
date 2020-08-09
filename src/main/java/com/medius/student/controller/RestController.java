package com.medius.student.controller;

import java.util.ArrayList;
import java.util.List;

import com.medius.student.model.Player;
import com.medius.student.model.Problem;
import com.medius.student.repository.ProblemRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import com.medius.student.repository.PlayerRepository;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@GetMapping("players/{username}")
	public Player getPlayerByUsername(@PathVariable("username") String username) {
		return playerRepository.retrieve(username);
	}

	@GetMapping("players")
	public List<Player> getAllPlayers() {
		return playerRepository.getAll();
	}

	@PostMapping("players")
	public String createPlayer(@RequestBody String Body) {
		JSONObject json = new JSONObject(Body);
		Player player = new Player(json.getString("username"), json.getInt("age"));
		playerRepository.store(player);
		return json.toString();
	}

	@GetMapping("problem/{id}")
	public Problem getProblemById(@PathVariable("id") long id) {
		return problemRepository.retrieve(id);
	}

	@GetMapping("problem")
	public List<Problem> getAllProblems() {
		return problemRepository.getAll();
	}

	@PostMapping("problem")
	public Problem createProblem(@RequestBody String Body) {
		JSONObject json = new JSONObject(Body);
		JSONArray jsonproblem = json.getJSONArray("problem");

		List<List<Boolean>> problemList = new ArrayList<List<Boolean>>();
		for(Object row : jsonproblem){
			if ( row instanceof JSONArray ) {
				List<Boolean> rowList = new ArrayList<Boolean>();
				for (Object value : (JSONArray) row) {
					if (value instanceof Boolean) {
						rowList.add((Boolean) value);
					}
				}
				problemList.add(rowList);
			}
			else return null;
		}

		Problem problem = new Problem(problemList, json.getString("username"));
		problemRepository.store(problem);
		return problem;
	}
}

