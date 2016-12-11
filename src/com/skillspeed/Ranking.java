package com.skillspeed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ranking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	Employee subject;
	
	@ManyToOne
	Employee observer;
	
	@ManyToOne
	Skill skill;
	
	@Column
	Integer rating;
	
	public Ranking() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getSubject() {
		return subject;
	}

	public void setSubject(Employee subject) {
		this.subject = subject;
	}

	public Employee getObserver() {
		return observer;
	}

	public void setObserver(Employee observer) {
		this.observer = observer;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
