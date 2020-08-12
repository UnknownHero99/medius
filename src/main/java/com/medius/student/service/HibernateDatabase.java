package com.medius.student.service;

import com.medius.student.model.Player;
import com.medius.student.model.Problem;
import com.medius.student.model.Solution;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class HibernateDatabase {
    static SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
    static Session session = sessionFactory.openSession();

    public static boolean addPlayer(Player player){
        session.beginTransaction();
        try {
            session.save(player);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return true;
    }
    public static List<Player> getAllPlayers(){
        session.beginTransaction();
        List<Player> players;
        try {
            players = session.createQuery("SELECT a FROM Player a", Player.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return players;
    }
    public static Player getPlayer(String username){
        session.beginTransaction();
        Player player;
        try {
            player = session.get(Player.class, username);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return player;
    }

    public static Problem getProblem(long id){
        session.beginTransaction();
        Problem problem;
        try {
            problem = session.get(Problem.class, id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return problem;
    }
    public static List<Problem> getAllProblems(){
        session.beginTransaction();
        List<Problem> problems;
        try {
            problems = session.createQuery("SELECT a FROM Problem a", Problem.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return problems;
    }

    public static Problem createProblem(Problem problem){ ;
        session.beginTransaction();
        try {
            session.save(problem);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return problem;
    }

    public static List<Solution> getAllSolutions() {
        session.beginTransaction();
        List<Solution> solutions;
        try {
            solutions = session.createQuery("SELECT a FROM Solution a", Solution.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return solutions;
    }

    public static List<Solution> getSolutionsByPlayer(String username) {
        List<Solution> solutions;
        try {
            session.beginTransaction();
            Player player = getPlayer(username);
            solutions = session.createQuery("SELECT a FROM Solution a", Solution.class).getResultList();
            session.getTransaction().commit();

            List<Solution> playersSolutions = new ArrayList<Solution>();
            for (Solution solution : solutions) if (solution.getPlayer() == player) playersSolutions.add(solution);

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return solutions;
    }
    public static Solution createSolution(Solution solution){
        session.beginTransaction();
        try {
            session.save(solution);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        return solution;
    }
}
