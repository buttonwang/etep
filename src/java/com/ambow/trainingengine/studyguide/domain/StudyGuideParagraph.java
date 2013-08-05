package com.ambow.trainingengine.studyguide.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import com.ambow.trainingengine.util.HtmlUtil;

/**
 * 
 * @author WeiShaoying 2009年12月16日16:53:37
 */

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "study_guide_paragraph")
public class StudyGuideParagraph implements Serializable {

	private static final long serialVersionUID = 1940631064180443507L;
	
	private Integer id;
	private StudyGuide studyGuide;
	private String title;
	private String content;
	private Integer orderNum;
	private List<StudyGuideItem> items = new ArrayList<StudyGuideItem>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne()
	@JoinColumn(name = "study_guide_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public StudyGuide getStudyGuide() {
		return studyGuide;
	}

	public void setStudyGuide(StudyGuide studyGuide) {
		this.studyGuide = studyGuide;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "order_num")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "studyGuideParagraph")
	@OrderBy("orderNum")
	public List<StudyGuideItem> getItems() {
		return items;
	}

	public void setItems(List<StudyGuideItem> items) {
		this.items = items;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Transient
	public String getTitle2() {
		return HtmlUtil.beautyHtml(this.title);
	}

}
