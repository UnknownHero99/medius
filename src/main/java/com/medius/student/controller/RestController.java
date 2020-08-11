package com.medius.student.controller;

import java.util.ArrayList;
import java.util.List;

import com.medius.student.model.Matrix;
import com.medius.student.model.Player;
import com.medius.student.model.Problem;
import com.medius.student.model.Solution;
import com.medius.student.service.HibernateDatabase;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;


@org.springframework.web.bind.annotation.RestController
public class RestController {

	@GetMapping("players/{username}")
	public Player getPlayerByUsername(@PathVariable("username") String username) {
		return HibernateDatabase.getPlayer(username);
	}

	@GetMapping("players")
	public List<Player> getAllPlayers() {
		return HibernateDatabase.getAllPlayers();
	}

	@PostMapping("players")
	public Player createPlayer(@RequestBody String Body) {
			JSONObject json = new JSONObject(Body);
			Player player = new Player(json.getString("username"), json.getInt("age"));
			HibernateDatabase.addPlayer(player);
			return player;
	}

	@GetMapping("problems/{id}")
	public Problem getProblemById(@PathVariable("id") long id) {
		return HibernateDatabase.getProblem(id);
	}
	@GetMapping("problems/creator/{username}")
	public List<Problem> getProblemsByUsername(@PathVariable("username") String username) {
		Player player = HibernateDatabase.getPlayer(username);
		return player.getProblems();
	}

	@GetMapping("problems")
	public List<Problem> getAllProblems() {
		return HibernateDatabase.getAllProblems();
	}

	@PostMapping("problems")
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


		Player player = HibernateDatabase.getPlayer(json.getString("username"));
		Problem problem = new Problem(problemList);
		List<Problem> problems = player.getProblems();
		problems.add(problem);
		player.setProblems(problems);
		HibernateDatabase.createProblem(problem);
		Solution solution = new Solution(problem, player, null);
		HibernateDatabase.createSolution(solution);
		Solver.solve(problem.getProblem());
		return problem;
	}

	@GetMapping("solutions")
	public List<Solution> getAllSolutions() {
		return HibernateDatabase.getAllSolutions();
	}

	@GetMapping("solutions/solver/{username}")
	public List<Solution> getSolutionsByPlayer(@RequestBody String username) {
		return HibernateDatabase.getSolutionsByPlayer(username);
	}

	@PostMapping("/solutions")
	public Problem createSolution(@RequestBody String Body) {
		JSONObject json = new JSONObject(Body);
		JSONArray solutionSteps = json.getJSONArray("solutionSteps");

		List<List<Boolean>> problemList = new ArrayList<List<Boolean>>();
		for(Object row : solutionSteps){
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


		Player player = HibernateDatabase.getPlayer(json.getString("username"));
		Problem problem = new Problem(problemList);
		List<Problem> problems = player.getProblems();
		problems.add(problem);
		player.setProblems(problems);
		HibernateDatabase.createProblem(problem);
		return problem;
	}

}

