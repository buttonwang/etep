package com.ambow.trainingengine.itembank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
 * SubItemRevise.java
 * 
 * Created on 2010-3-4 上午10:57:52
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "item_revise_answers")
public class ItemReviseAnswers implements java.io.Serializable {

	private static final long serialVersionUID = -6143542237376959508L;

	private Integer id;
	private ItemRevise itemRevise;
	private Item item;
	private SubItem subItem;
	private String answer;
	private boolean isCorrect;
	private Float score;
	private String itemScore2;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
		
	@ManyToOne
	@JoinColumn(name = "item_revise_id")
	public ItemRevise getItemRevise() {
		return itemRevise;
	}

	public void setItemRevise(ItemRevise itemRevise) {
		this.itemRevise = itemRevise;
	}
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne
	@JoinColumn(name = "sub_item_id")
	public SubItem getSubItem() {
		return subItem;
	}

	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;
	}
	
	@Column(name="answer")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name="is_correct")
	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	@Column(name="score")
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name="item_score2")
	public String getItemScore2() {
		return itemScore2;
	}

	public void setItemScore2(String itemScore2) {
		this.itemScore2 = itemScore2;
	}
}
