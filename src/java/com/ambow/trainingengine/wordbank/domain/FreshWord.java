package com.ambow.trainingengine.wordbank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * FreshWord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fresh_word")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class FreshWord implements java.io.Serializable {

	// Fields

	private Integer id;
	private WordExtension wordExtension;
	private Long processInstance;
	// Constructors

	/** default constructor */
	public FreshWord() {
	}

	/** full constructor */
	public FreshWord(WordExtension wordExtension,Long processInstance) {
		this.wordExtension = wordExtension;
		this.processInstance = processInstance;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_extend_id")
	public WordExtension getWordExtension() {
		return this.wordExtension;
	}

	public void setWordExtension(WordExtension wordExtension) {
		this.wordExtension = wordExtension;
	}
	@Column(name="process_instance")
	public Long getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(Long processInstance) {
		this.processInstance = processInstance;
	}


}