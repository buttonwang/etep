package com.ambow.trainingengine.wordbank.service;

import java.util.ArrayList;
import java.util.List;

import com.ambow.trainingengine.itembank.service.GradeService;
import com.ambow.trainingengine.wordbank.domain.ChineseMeaning;
import com.ambow.trainingengine.wordbank.domain.ExampleSentence;
import com.ambow.trainingengine.wordbank.domain.WordBasic;
import com.ambow.trainingengine.wordbank.domain.WordExtension;
import com.ambow.trainingengine.wordbank.domain.WordType;
import com.ambow.trainingengine.wordbank.util.ParseObject;



/*
 * ParseWordService.java
 * 
 * Created on 2008-7-21 下午01:49:44
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
public class ParseWordService {

	private Object wordList;
	
	private ArrayList<WordBasic> words = new ArrayList<WordBasic>(0);
	
	private WordBasic curWordBasic;
	
	private WordExtension curWordExtension;
	
	private WordType curWordType; 
	
	private ChineseMeaning curChineseMeaning;
	
	private ExampleSentence curExampleSentence;
	
	private Object[] curTuple;
	
	private WordBasicService wordBasicService;
	
	private GradeService gradeService;
	
	public ParseWordService(){
		
	}
	
	public void clear() {
		wordList = null;
		words	 =  new ArrayList<WordBasic>(0);
		
		curWordBasic = null;
		curWordExtension = null;
		curWordType = null;
		curChineseMeaning = null;
		curExampleSentence = null;
		curTuple = null;
	}
	
	@SuppressWarnings("unchecked")
	public void parse() {
		List<Object[]> elist =  (List<Object[]>)wordList;
		
		for (Object[] tuple: elist) {
			setCurTuple(tuple);
			parsecurWordObject();
			if (!words.contains(curWordBasic)) this.words.add(curWordBasic);
		}
	}

	public Object getWordList() {
		return wordList;
	}

	public void setWordList(Object wordList) {
		this.wordList = wordList;
	}

	public ArrayList<WordBasic> getWords() {
		return words;
	}

	public void setWords(ArrayList<WordBasic> words) {
		this.words = words;
	}

	public WordBasic getCurWordBasic() {
		return curWordBasic;
	}

	public void setCurWordBasic(WordBasic curWordBasic) {
		this.curWordBasic = curWordBasic;
	}

	public WordExtension getCurWordExtension() {
		return curWordExtension;
	}

	public void setCurWordExtension(WordExtension curWordExtension) {
		this.curWordExtension = curWordExtension;
	}

	public WordType getCurWordType() {
		return curWordType;
	}

	public void setCurWordType(WordType curWordType) {
		this.curWordType = curWordType;
	}

	public ChineseMeaning getCurChineseMeaning() {
		return curChineseMeaning;
	}

	public void setCurChineseMeaning(ChineseMeaning curChineseMeaning) {
		this.curChineseMeaning = curChineseMeaning;
	}

	public ExampleSentence getCurExampleSentence() {
		return curExampleSentence;
	}

	public void setCurExampleSentence(ExampleSentence curExampleSentence) {
		this.curExampleSentence = curExampleSentence;
	}
	
	public Object[] getCurTuple() {
		return curTuple;
	}

	public void setCurTuple(Object[] curTuple) {
		this.curTuple = curTuple;
	}

	public WordBasicService getWordBasicService() {
		return wordBasicService;
	}

	public void setWordBasicService(WordBasicService wordBasicService) {
		this.wordBasicService = wordBasicService;
	}
	
	public GradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
	
	private void createword() {
		String wordstr = ParseObject.ObjecttoString(curTuple[3]);
		String wordtype= ParseObject.ObjecttoString(curTuple[5]);
		String chinesem= ParseObject.ObjecttoString(curTuple[6]);
		String examples= ParseObject.ObjecttoString(curTuple[7]);
		
		//单词不为空，词性为空的情况，暂用‘pre.’ 代替。
		if(!wordstr.equals("") && wordtype.equals("")) {
			wordtype = "pre.";
		}
		
		if (!wordstr.equals("")) {
			List<WordBasic> curWordBasics = wordBasicService.findBy("word", wordstr);
			if (curWordBasics.size()>0) curWordBasic=curWordBasics.get(0);
			else curWordBasic= new WordBasic();
			curWordExtension = new WordExtension();
			curWordExtension.setWordBasic(curWordBasic);
			curWordBasic.getWordExtensions().add(curWordExtension);
			
			curWordType = null;
			curChineseMeaning = null;
			curExampleSentence = null;
		}
		
		if (!wordtype.equals("")) {
			curWordType = new WordType();
			curWordType.setWordExtension(curWordExtension);
			curWordExtension.getWordTypes().add(curWordType);
						
			curChineseMeaning = null;
			curExampleSentence = null;
		}
		
		if (!chinesem.equals("")) {
			curChineseMeaning = new ChineseMeaning();
			curChineseMeaning.setWordType(curWordType);
			curWordType.getChineseMeanings().add(curChineseMeaning);
			
			curExampleSentence = null;
		}
		
		if (!examples.equals("")) {
			curExampleSentence = new ExampleSentence();
			curExampleSentence.setChineseMeaning(curChineseMeaning);
			curChineseMeaning.getExampleSentences().add(curExampleSentence);
		} else {
			curExampleSentence = null;
		}
		
	}
	
	private void parsecurWordObject() {
		createword();
	
		String tuple0 = ParseObject.ObjecttoString(curTuple[0]);
		String tuple1 = ParseObject.ObjecttoString(curTuple[1]);
		String tuple2 = ParseObject.ObjecttoString(curTuple[2]);
		String tuple3 = ParseObject.ObjecttoString(curTuple[3]);
		String tuple4 = ParseObject.ObjecttoString(curTuple[4]);
		String tuple5 = ParseObject.ObjecttoString(curTuple[5]);
		String tuple6 = ParseObject.ObjecttoString(curTuple[6]);
		String tuple7 = ParseObject.ObjecttoString(curTuple[7]);
		String tuple8 = ParseObject.ObjecttoString(curTuple[8]);
		String tuple9 = ParseObject.ObjecttoString(curTuple[9]);
		String tuple10= ParseObject.ObjecttoString(curTuple[10]);
		String tuple11= ParseObject.ObjecttoString(curTuple[11]);
		String tuple12= ParseObject.ObjecttoString(curTuple[12]);
		String tuple13= ParseObject.ObjecttoString(curTuple[13]);
		String tuple14= ParseObject.ObjecttoString(curTuple[14]);
		String tuple15= ParseObject.ObjecttoString(curTuple[15]);
		String tuple16= ParseObject.ObjecttoString(curTuple[16]);
		String tuple17= ParseObject.ObjecttoString(curTuple[17]);
		String tuple18= ParseObject.ObjecttoString(curTuple[18]);
		
		if (curWordBasic!=null) {
			if (!tuple3.equals(""))  curWordBasic.setWord(tuple3);
			if (!tuple12.equals("")) curWordBasic.setCommonUsage(tuple12);
			if (!tuple13.equals("")) curWordBasic.setWordUsage(tuple13);
			if (!tuple18.equals("")) curWordBasic.setAssociationMemory(tuple18);		
		}
		
		if (curWordExtension!=null) {
			if (!tuple0.equals(""))  curWordExtension.setGrade(gradeService.get(tuple0));
			if (!tuple1.equals(""))  curWordExtension.setWordLevel(tuple1);
			if (!tuple2.equals(""))  curWordExtension.setWordCategory(tuple2);
		}
		
		if (curWordType!=null) {
			if (!tuple5.equals(""))  curWordType.setCode(tuple5);
		}
		
		if (curChineseMeaning!=null) {
			if (!tuple6.equals(""))  curChineseMeaning.setMeaning(tuple6);		
			if (!tuple10.equals("")) curChineseMeaning.setSynonym(tuple10);
			if (!tuple11.equals("")) curChineseMeaning.setAntonym(tuple11);				
			if (!tuple14.equals("")) curChineseMeaning.setConfusableWord(tuple14);
			if (!tuple15.equals("")) curChineseMeaning.setConfusableWordAnalysis(tuple15);
		}
		
		if (curExampleSentence!=null) {
			if (!tuple7.equals(""))  curExampleSentence.setContent(tuple7);
			if (!tuple8.equals(""))  curExampleSentence.setTranslation(tuple8);
			if (!tuple9.equals(""))  curExampleSentence.setSource(tuple9);
		}
		
		if (!tuple4.equals(""))  ;
		if (!tuple16.equals("")) ;
		if (!tuple17.equals("")) ;			
	}

	
}
