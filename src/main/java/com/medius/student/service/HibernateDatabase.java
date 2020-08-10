package com.medius.student.service;

import com.medius.student.model.Player;
import com.medius.student.model.Problem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateDatabase {
    static SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
    static Session session = sessionFactory.openSession();

    public static boolean addPlayer(Player player){
        session.beginTransaction();
        session.save(player);
        session.getTransaction().commit();
        return true;
    }
    public static List<Player> getAllPlayers(){
        session.beginTransaction();
        List<Player> players = session.createQuery("SELECT a FROM Player a", Player.class).getResultList();
        session.getTransaction().commit();
        return players;
    }
    public static Player getPlayer(String username){
        session.beginTransaction();
        Player player = session.get(Player.class, username);
        session.getTransaction().commit();
        return player;
    }

    public static Problem getProblem(long id){
        session.beginTransaction();
        Problem problem = session.get(Problem.class, id);
        session.getTransaction().commit();
        return problem;
    }
    public static List<Problem> getAllProblems(){
        //return (Player) sessionFactory.getCurrentSession().getAll;
        session.beginTransaction();
        List<Problem> problems = session.createQuery("SELECT a FROM Problem a", Problem.class).getResultList();
        session.getTransaction().commit();
        return problems;
    }

    public static Problem createProblem(Problem problem){
        session.beginTransaction();
        session.save(problem);
        session.getTransaction().commit();
        return problem;
    }
}
