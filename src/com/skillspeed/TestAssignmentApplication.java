package com.skillspeed;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TestAssignmentApplication {

	SessionFactory factory;

	public void setup() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}

	public Skill findSkill(Session session, String name) {
		Query query = session.createQuery("from Skill s where s.name=:name");
		query.setParameter("name", name);
		Skill skill = (Skill) query.uniqueResult();
		return skill;
	}

	public Skill saveSkill(Session session, String name) {
		Skill skill = findSkill(session, name);
		if (skill == null) {
			skill = new Skill();
			skill.setName(name);
			session.save(skill);
		}
		return skill;
	}

	public Employee findEmployee(Session session, String name) {
		Query query = session.createQuery("from Employee e where e.name=:name");
		query.setParameter("name", name);
		Employee student = (Employee) query.uniqueResult();
		return student;
	}

	public Employee saveEmployee(Session session, String name) {
		Employee employee = findEmployee(session, name);
		if (employee == null) {
			employee = new Employee();
			employee.setName(name);
			session.save(employee);
		}
		return employee;
	}

	public void createData(Session session, String observerName, String subjectName, String skillName, int rank) {
		Employee subject = saveEmployee(session, subjectName);
		Employee observer = saveEmployee(session, observerName);
		Skill skill = saveSkill(session, skillName);

		Ranking ranking = new Ranking();
		ranking.setSubject(subject);
		ranking.setObserver(observer);
		ranking.setSkill(skill);
		ranking.setRating(rank);

		session.save(ranking);

	}

	public void changeRank(Session session, String observerName, String subjectName, String skillName, int newRating) {
		Query query = session.createQuery("from Ranking r " + "where r.subject.name=:subject and "
				+ "r.observer.name=:observer and " + "r.skill.name=:skill");
		query.setString("subject", subjectName);
		query.setString("observer", observerName);
		query.setString("skill", skillName);

		Ranking ranking = (Ranking) query.uniqueResult();

		if (ranking == null) {
			System.out.println("Invalid Change Request");
		}

		ranking.setRating(newRating);

	}

	public static void main(String[] args) {

		TestAssignmentApplication runAssign = new TestAssignmentApplication();
		runAssign.setup();

		Session session = runAssign.factory.openSession();
		Transaction tx = session.beginTransaction();
		runAssign.createData(session, "Sara", "jerry", "Java", 8);
		runAssign.createData(session, "Tom", "Marry", "Hibernate", 8);
		runAssign.createData(session, "Nuru", "Nabu", "Spring", 8);

		tx.commit();
		session.clear();

	}

}