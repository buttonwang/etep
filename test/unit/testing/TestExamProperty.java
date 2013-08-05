package testing;

import com.ambow.trainingengine.exam.web.data.ExamProperty;
import com.ambow.trainingengine.exam.web.data.SubAnswer;

import junit.framework.TestCase;

/*
 * TestExamProperty.java
 * 
 * Created on 2009-3-19 下午02:13:48
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

public class TestExamProperty extends TestCase {
	
	public void testA() {
		String typeCode = "MPC3X";
		String correctAnswer = "A$B$C；X$Y$Z；Z$X$Y；C$A$B";
		String score2 = "1；1；2；2";
		Float score = 10f;
		String answerGroup = "(1，4)； （2, 3）";
		
		ExamProperty examProperty = new ExamProperty(typeCode, correctAnswer, score2, score, answerGroup);
				
		examProperty.removeRightAnswerByGroupId(1, "A");
		examProperty.removeRightAnswerByGroupId(1, "B");
		examProperty.removeRightAnswerByGroupId(1, "C");
		
		examProperty.removeRightAnswerByGroupId(2, "X");
		examProperty.removeRightAnswerByGroupId(2, "Y");
		examProperty.removeRightAnswerByGroupId(2, "Z");
		int id = 1;
		for (SubAnswer subAnswer: examProperty.getSubAnswers()) {
			System.out.println("SubAnswer：" + String.valueOf(id));
			System.out.println(subAnswer.getCorrectAnswer());
			System.out.println(subAnswer.getScore());
			System.out.println(subAnswer.getGroupId());
			id++;
		}
	}

}
