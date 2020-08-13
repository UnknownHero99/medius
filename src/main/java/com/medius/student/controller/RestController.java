package com.medius.student.controller;

import java.util.ArrayList;
import java.util.List;

import com.medius.student.model.*;
import com.medius.student.service.HibernateDatabase;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
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
	public ResponseEntity<Object> createPlayer(@RequestBody String Body) {
		JSONObject json = new JSONObject(Body);
		if(getPlayerByUsername(json.getString("username")) != null){
			JSONObject response = new JSONObject();
			response.put("status", "Error: username already taken");
			return new ResponseEntity<Object>(response.toString(), HttpStatus.EXPECTATION_FAILED);
		}
		Player player = new Player(json.getString("username"), json.getInt("age"));
		HibernateDatabase.addPlayer(player);
		return new ResponseEntity<Object>(player, HttpStatus.OK);
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
	public ResponseEntity<Object> createProblem(@RequestBody String Body) {
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
		if(player == null){
			JSONObject response = new JSONObject();
			response.put("status", "Error: user does not exists");
			return new ResponseEntity<Object>(response.toString(), HttpStatus.NOT_FOUND);
		}
		Problem problem = new Problem(problemList);
		List<Problem> problems = player.getProblems();
		problems.add(problem);
		player.setProblems(problems);
		HibernateDatabase.createProblem(problem);

		JSONObject response = new JSONObject();

		StopWatch watch = new StopWatch();
		watch.start();
		System.out.println("Started solving problem");

		if(Solver.solve(problem)) response.put("solvable", true);
		else response.put("solvable", false);

		watch.stop();
		System.out.println("Time Elapsed in seconds: " + watch.getTotalTimeSeconds());

		return new ResponseEntity<Object>(response.toString(), HttpStatus.OK);
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
	public ResponseEntity<Object> createSolution(@RequestBody String Body) {
		JSONObject response = new JSONObject();
		JSONObject json = new JSONObject(Body);
		JSONArray steps = json.getJSONArray("solutionSteps");

		List<SolutionStep> solutionSteps = new ArrayList<>();
		for(int i = 0; i < steps.length(); i++){
			JSONObject step = steps.getJSONObject(i);
			SolutionStep solutionStep = new SolutionStep(step.getInt("toggleCoordinateX"),step.getInt("toggleCoordinateY"),i);
			solutionSteps.add(solutionStep);
		}
		Problem problem = HibernateDatabase.getProblem(json.getInt("problemId"));
		if(problem == null){
			response.put("status", "Error: problem does not exists");
			return new ResponseEntity<Object>(response.toString(), HttpStatus.NOT_FOUND);
		}
		Player player = HibernateDatabase.getPlayer(json.getString("username"));
		Solution solution = new Solution(problem, player, solutionSteps);

		if(Solver.solutionValid(solution)){
			HibernateDatabase.createSolution(solution);
			response.put("validSolution", true);
			return new ResponseEntity<Object>(response.toString(), HttpStatus.OK);
		}

		response.put("validSolution", false);
		return new ResponseEntity<Object>(response.toString(), HttpStatus.EXPECTATION_FAILED);
	}

}

