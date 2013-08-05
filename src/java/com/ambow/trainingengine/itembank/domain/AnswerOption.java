package com.ambow.trainingengine.itembank.domain;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AnswerOption entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity

@Table(name = "answer_option")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class AnswerOption implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3753569388327387356L;
	
	private Integer id;
	private SubItem subItem;
	private Item item;
	private String code;
	private String content;
	private String translation;

	private String pseudoCode;	//伪代码，颠倒答案时用
	// Constructors

	/** default constructor */
	public AnswerOption() {
	}

	/** minimal constructor */
	public AnswerOption(String code) {
		this.code = code;
	}

	/** full constructor */
	public AnswerOption(SubItem subItem, Item item, String code,
			String content, String translation) {
		this.subItem = subItem;
		this.item = item;
		this.code = code;
		this.content = content;
		this.translation = translation;
	}
	
	public static Comparator<AnswerOption> getAnswerOptionSorter() {
		return new Comparator<AnswerOption>() {
			public int compare(AnswerOption o1, AnswerOption o2) {
				if (o1.getPseudoCode() ==null) return o1.getCode().compareTo(o2.getCode());
				return o1.getPseudoCode().compareTo(o2.getPseudoCode());
			}			
		};	
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
	@JoinColumn(name = "sub_item_id")
	public SubItem getSubItem() {
		return this.subItem;
	}

	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "code", nullable = false, length = 2)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "translation", length = 65535)
	public String getTranslation() {
		return this.translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}
	
	@Transient
	public String getPseudoCode() {
		return pseudoCode;
	}

	public void setPseudoCode(String pseudoCode) {
		this.pseudoCode = pseudoCode;
	}

}