/**
 * 
 */
package com.ambow.trainingengine.exam.util;

/* 
 * AnswerInstruction.java <br/>
 * 
 * Created on Jul 11, 2008,1:06:25 PM <br/>
 *
 * Copyright(C) 2008, by Ambow Research&Development Branch. <br/>
 *
 * Original Author: Li Xin <br/>
 * Contributor(s): 参与者的名称，参与者名称2， <br/>
 *
 * Changes  <br/> 
 * -------
 * $log$ <br/>
 */
public enum AnswerInstruction {
	
	//ky	
	INS_Z1E12A("Beneach each of the following sentences, there are four choices marked [A],[B],[C] and [D]. Choose the one that best completes the sentence."),
	
	INS_Z1E13A("Each of the following sentences has four underlined parts marked [A],[B],[C] and [D]. Identify the part of the sentence that is incorrect and mark your answer."),
	
	INS_Z1E25A("Read the following text. Answer the questions below the text by choosing [A], [B], [C], or [D]. Mark your answers."),
	
	INS_Z1E26A("Read the following text. Choose the best word (s) for each numbered blank and mark A, B, C or D."),
	
	INS_Z1E34A("仔细阅读下面的句子，从所给的选项中，找出一处相对于句子整体结构拆分不合理或有误的A、B、C、D等选项填入空格处。"),
	
	INS_Z1E15A("仔细阅读下列句子，选择最佳译文选项。"),
	
	INS_Z1E34C("翻译下列句子。 "),
	
	INS_Z1E39A("Writing 小作文"),
	
	INS_Z1E39B("Writing 大作文"),
	
	INS_Z1E44A("Read the following text carefully and then translate the underlined segments into Chinese."),
	
	INS_Z1E47A("In the following article, some sentences have been removed. For the  following questions, choose the most suitable one from the list A～G to fit into each of the numbered blanks. There are two extra choices which do not fit in any of the gaps. Mark your answers."),
	
	INS_Z1E47B("The following paragraphs are given in a wrong order. For the following questions. you are required to reorganize these paragraphs into a coherent article by choosing from the list A～G to fill in each numbered box. The first and the last paragraphs have been placed for you in Boxes. Mark your answers."),
	
	INS_Z1E47C("You are going to read a text, followed by a list of examples. Choose the best example from the list A～F for each numbered subheading. There is one extra heading which you do not need to use. Mark your answers."),
	
	INS_Z1E47D("Choose the most suitable heading from the list A～F for each numbered paragraph. The first and last paragraphs of the text are not numbered. There is one extra heading which you do not need to use. Mark your answers."),
	
	//mpc
	INS_MPC11("每小题只有一个选项符合题意。 "),
	INS_MPC12("每小题有一个或两个选项符合题意。若正确答案只包括一个选项，多选时，该题得0分；若正确答案包括两个选项，只选一个且正确的得一半分，选两个且都正 确的得满分，但只要选错一个，该小题就得0分。 "),
	INS_MPC3X(""),
	INS_MPC4X("");
	
		
	String instruction;
	
	private AnswerInstruction(String ins){
		this.instruction=ins;
	}
	
	public static String getInstructions(String typeCode){
		String name="INS_"+typeCode.toUpperCase();
		return AnswerInstruction.valueOf(name).instruction;
	}
}
