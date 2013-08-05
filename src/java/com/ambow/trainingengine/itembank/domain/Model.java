/**
 * 
 */
package com.ambow.trainingengine.itembank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author yuanjunqi
 *
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "model")
public class Model {

	private Integer id;
	private String name;
	private Subject subject;
	private Grade grade;
	private Set<KnowledgePoint> knowledgePoints = new HashSet<KnowledgePoint>(0);
	
	@ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "model_knowledge_point_ref",
			joinColumns = @JoinColumn(name="model_id"),
			inverseJoinColumns = @JoinColumn(name="knowledge_point_code")
	)
	public Set<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}
	public void setKnowledgePoints(Set<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name = "subject_code")
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	@ManyToOne
	@JoinColumn(name = "grade_code")
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	
}
