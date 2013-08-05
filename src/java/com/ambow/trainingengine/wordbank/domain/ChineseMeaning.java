package com.ambow.trainingengine.wordbank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * ChineseMeaning entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "chinese_meaning")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ChineseMeaning implements java.io.Serializable {

	// Fields

	private Integer id;
	private WordType wordType;
	private String meaning;
	private String synonym;
	private String antonym;
	private String confusableWord;
	private String confusableWordAnalysis;
	private Set<ExampleSentence> exampleSentences = new LinkedHashSet<ExampleSentence>(0);

	// Constructors

	/** default constructor */
	public ChineseMeaning() {
	}

	/** full constructor */
	public ChineseMeaning(WordType wordType, String meaning, String synonym,
			String antonym, String confusableWord,
			String confusableWordAnalysis, Set<ExampleSentence> exampleSentences) {
		this.wordType = wordType;
		this.meaning = meaning;
		this.synonym = synonym;
		this.antonym = antonym;
		this.confusableWord = confusableWord;
		this.confusableWordAnalysis = confusableWordAnalysis;
		this.exampleSentences = exampleSentences;
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
	@JoinColumn(name = "word_type_id")
	public WordType getWordType() {
		return this.wordType;
	}

	public void setWordType(WordType wordType) {
		this.wordType = wordType;
	}

	@Column(name = "meaning", length = 2000)
	public String getMeaning() {
		return this.meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	@Column(name = "synonym", length = 2000)
	public String getSynonym() {
		return this.synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	@Column(name = "antonym", length = 2000)
	public String getAntonym() {
		return this.antonym;
	}

	public void setAntonym(String antonym) {
		this.antonym = antonym;
	}

	@Column(name = "confusable_word", length = 2000)
	public String getConfusableWord() {
		return this.confusableWord;
	}

	public void setConfusableWord(String confusableWord) {
		this.confusableWord = confusableWord;
	}

	@Column(name = "confusable_word_analysis", length = 2000)
	public String getConfusableWordAnalysis() {
		return this.confusableWordAnalysis;
	}

	public void setConfusableWordAnalysis(String confusableWordAnalysis) {
		this.confusableWordAnalysis = confusableWordAnalysis;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chineseMeaning")
	@OrderBy("id")
	public Set<ExampleSentence> getExampleSentences() {
		return this.exampleSentences;
	}

	public void setExampleSentences(Set<ExampleSentence> exampleSentences) {
		this.exampleSentences = exampleSentences;
	}

}