package com.medius.student.repository;

import com.medius.student.model.Problem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProblemRepository implements ObjectRepository<Problem> {

    private Map<Long, Problem> repository;

    public ProblemRepository() {
        this.repository = new HashMap<>();
    }

    @Override
    public void store(Problem problem) {
        repository.put(problem.getId(), problem);
    }

    public Problem retrieve(long id) {
        return repository.get(id);
    }


    public List<Problem> getAll() {
        List<Problem> problems = new ArrayList<>();

        for(Problem problem : this.repository.values())
            problems.add(problem);

        return problems;
    }

}
