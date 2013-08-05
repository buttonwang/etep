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
 * ExampleSentence entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "example_sentence")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ExampleSentence implements java.io.Serializable {

	// Fields

	private Integer id;
	private ChineseMeaning chineseMeaning;
	private String content;
	private String translation;
	private String source;

	// Constructors

	/** default constructor */
	public ExampleSentence() {
	}

	/** full constructor */
	public ExampleSentence(ChineseMeaning chineseMeaning, String content,
			String translation, String source) {
		this.chineseMeaning = chineseMeaning;
		this.content = content;
		this.translation = translation;
		this.source = source;
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
	@JoinColumn(name = "meaning_id")
	public ChineseMeaning getChineseMeaning() {
		return this.chineseMeaning;
	}

	public void setChineseMeaning(ChineseMeaning chineseMeaning) {
		this.chineseMeaning = chineseMeaning;
	}

	@Column(name = "content", length = 2000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "translation", length = 2000)
	public String getTranslation() {
		return this.translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

    @Column(name = "source", length = 200)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}