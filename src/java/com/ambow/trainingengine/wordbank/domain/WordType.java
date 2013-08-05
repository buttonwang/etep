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
 * WordType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "word_type")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class WordType implements java.io.Serializable {

	// Fields

	private Integer id;
	private WordExtension wordExtension;
	private String name;
	private String phoneticSymbol;
	private Integer hasDifferentPronunciations;
	private String code;
	private Set<ChineseMeaning> chineseMeanings = new LinkedHashSet<ChineseMeaning>(0);

	// Constructors

	/** default constructor */
	public WordType() {
	}

	/** full constructor */
	public WordType(WordExtension wordExtension, String name,
			String phoneticSymbol, Integer hasDifferentPronunciations,
			Set<ChineseMeaning> chineseMeanings) {
		this.wordExtension = wordExtension;
		this.name = name;
		this.phoneticSymbol = phoneticSymbol;
		this.hasDifferentPronunciations = hasDifferentPronunciations;
		this.chineseMeanings = chineseMeanings;
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
	@JoinColumn(name = "word_extension_id")
	public WordExtension getWordExtension() {
		return this.wordExtension;
	}

	public void setWordExtension(WordExtension wordExtension) {
		this.wordExtension = wordExtension;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "phonetic_symbol", length = 50)
	public String getPhoneticSymbol() {
		return this.phoneticSymbol;
	}

	public void setPhoneticSymbol(String phoneticSymbol) {
		this.phoneticSymbol = phoneticSymbol;
	}

	@Column(name = "has_different_pronunciations")
	public Integer getHasDifferentPronunciations() {
		return this.hasDifferentPronunciations;
	}

	public void setHasDifferentPronunciations(Integer hasDifferentPronunciations) {
		this.hasDifferentPronunciations = hasDifferentPronunciations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "wordType")
	@OrderBy("id")
	public Set<ChineseMeaning> getChineseMeanings() {
		return this.chineseMeanings;
	}

	public void setChineseMeanings(Set<ChineseMeaning> chineseMeanings) {
		this.chineseMeanings = chineseMeanings;
	}
	
	@Column(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}