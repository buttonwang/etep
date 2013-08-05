package com.ambow.trainingengine.wordbank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
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
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.wordbank.util.WordCategory;
import com.ambow.trainingengine.wordbank.util.WordLevel;

/**
 * WordExtension entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "word_extension")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class WordExtension implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Grade grade;
	private WordBasic wordBasic;
	private String wordLevel;
	private String wordCategory;
	private Set<FreshWord> freshWords = new HashSet<FreshWord>(0);
	private Set<WordType> wordTypes = new LinkedHashSet<WordType>(0);

	private WordLevel wordLevelName;
	private WordCategory wordCategoryName;
	// Constructors
	
	/** default constructor */
	public WordExtension() {
	}

	/** minimal constructor */
	public WordExtension(Grade grade, WordBasic wordBasic) {
		this.grade = grade;
		this.wordBasic = wordBasic;
	}

	/** full constructor */
	public WordExtension(Grade grade, WordBasic wordBasic, String wordLevel,
			String wordCategory, Set<FreshWord> freshWords,
			Set<WordType> wordTypes) {
		this.grade = grade;
		this.wordBasic = wordBasic;
		this.wordLevel = wordLevel;
		this.wordCategory = wordCategory;
		this.freshWords = freshWords;
		this.wordTypes = wordTypes;
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
	@JoinColumn(name = "grade_code", nullable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id", nullable = false)
	public WordBasic getWordBasic() {
		return this.wordBasic;
	}

	public void setWordBasic(WordBasic wordBasic) {
		this.wordBasic = wordBasic;
	}
		
	@Column(name = "word_level", length = 2)
	public String getWordLevel() {
		return this.wordLevel;
	}

	public void setWordLevel(String wordLevel) {
		this.wordLevel = wordLevel;
	}

	@Column(name = "word_category", length = 2)
	public String getWordCategory() {
		return this.wordCategory;
	}

	public void setWordCategory(String wordCategory) {
		this.wordCategory = wordCategory;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "wordExtension")
	public Set<FreshWord> getFreshWords() {
		return this.freshWords;
	}

	public void setFreshWords(Set<FreshWord> freshWords) {
		this.freshWords = freshWords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "wordExtension")
	@OrderBy("id")
	public Set<WordType> getWordTypes() {
		return this.wordTypes;
	}

	public void setWordTypes(Set<WordType> wordTypes) {
		this.wordTypes = wordTypes;
	}

	@Transient
	public WordLevel getWordLevelName() {
		if (this.wordLevel==null) return null;
		else return WordLevel.getWordLevel(this.wordLevel);
	}

	public void setWordLevelName(WordLevel wordLevelName) {
		this.wordLevelName = wordLevelName;
	}
	
	@Transient
	public WordCategory getWordCategoryName() {
		if (this.wordCategory==null) return null;
		return WordCategory.getWordCategory(this.wordCategory);
	}

	public void setWordCategoryName(WordCategory wordCategoryName) {
		this.wordCategoryName = wordCategoryName;
	}
}