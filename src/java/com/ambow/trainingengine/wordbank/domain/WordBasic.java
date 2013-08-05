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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * WordBasic entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "word_basic")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class WordBasic implements java.io.Serializable {

	// Fields

	private Integer id;
	private String word;
	private String phoneticSymbol;
	private String commonUsage;
	private String associationMemory;
	private String wordUsage;
	private Set<WordExtension> wordExtensions = new LinkedHashSet<WordExtension>(0);

	// Constructors

	/** default constructor */
	public WordBasic() {
	}

	/** full constructor */
	public WordBasic(String word, String phoneticSymbol, String commonUsage,
			String associationMemory, String wordUsage, Set<WordExtension> wordExtensions) {
		this.word = word;
		this.phoneticSymbol = phoneticSymbol;
		this.commonUsage = commonUsage;
		this.associationMemory = associationMemory;
		this.wordExtensions = wordExtensions;
		this.wordUsage = wordUsage;
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

	@Column(name = "word", length = 50)
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "phonetic_symbol", length = 50)
	public String getPhoneticSymbol() {
		return this.phoneticSymbol;
	}

	public void setPhoneticSymbol(String phoneticSymbol) {
		this.phoneticSymbol = phoneticSymbol;
	}

	@Column(name = "common_usage", length = 5000)
	public String getCommonUsage() {
		return this.commonUsage;
	}

	public void setCommonUsage(String commonUsage) {
		this.commonUsage = commonUsage;
	}

	@Column(name = "association_memory", length = 100)
	public String getAssociationMemory() {
		return this.associationMemory;
	}

	public void setAssociationMemory(String associationMemory) {
		this.associationMemory = associationMemory;
	}

	@Column(name = "word_usage", length = 5000)
	public String getWordUsage() {
		return this.wordUsage;
	}

	public void setWordUsage(String wordUsage) {
		this.wordUsage = wordUsage;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "wordBasic")
	@OrderBy("id")
	public Set<WordExtension> getWordExtensions() {
		return this.wordExtensions;
	}

	public void setWordExtensions(Set<WordExtension> wordExtensions) {
		this.wordExtensions = wordExtensions;
	}

}