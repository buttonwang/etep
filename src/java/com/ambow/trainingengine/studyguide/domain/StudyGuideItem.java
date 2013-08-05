package com.ambow.trainingengine.studyguide.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * @author WeiShaoying 2009年12月16日17:05:23
 */

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "study_guide_item")
public class StudyGuideItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8650972675721471687L;
	private Integer id;
	private StudyGuideParagraph studyGuideParagraph;
	private String content;
	private String analys;
	private String answer;
	private int type;
	private Integer orderNum;

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
	@JoinColumn(name = "study_guide_p_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public StudyGuideParagraph getStudyGuideParagraph() {
		return studyGuideParagraph;
	}

	public void setStudyGuideParagraph(StudyGuideParagraph studyGuideParagraph) {
		this.studyGuideParagraph = studyGuideParagraph;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "analysis_at_large")
	public String getAnalys() {
		return analys;
	}

	public void setAnalys(String analys) {
		this.analys = analys;
	}

	@Column(name = "correct_answer")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "order_num")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
