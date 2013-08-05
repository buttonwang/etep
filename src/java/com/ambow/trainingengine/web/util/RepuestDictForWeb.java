package com.ambow.trainingengine.web.util;

public class RepuestDictForWeb {
	
	/*评测对应css className*/
	public final static String NODE_EVALUATE_CURRENT="func_red_01";
	public final static String NODE_EVALUATE_PAUSE="func_red_02";
	public final static String NODE_EVALUATE_PASS="func_red_03";
	public final static String NODE_EVALUATE_NOPASS="func_red_04";
	public final static String NODE_EVALUATE_UNDO="func_gray";
	/*训练对应css className*/
	public final static String NODE_PRACTICE_CURRENT="func_green_01";
	public final static String NODE_PRACTICE_PAUSE="func_green_02";
	public final static String NODE_PRACTICE_PASS="func_green_03";
	public final static String NODE_PRACTICE_NOPASS="func_green_04";
	public final static String NODE_PRACTICE_UNDO="func_gray_sm";
	/*阶段测试css className*/
	public final static String NODE_PHASETEST_CURRENT="func_ora_01";
	public final static String NODE_PHASETEST_PAUSE="func_ora_02";
	public final static String NODE_PHASETEST_PASS="func_ora_03";
	public final static String NODE_PHASETEST_NOPASS="func_ora_04";
	public final static String NODE_PHASETEST_UNDO="func_gray";
	
	/*单元测试对应css className*/
	public final static String NODE_UNITTEST_CURRENT="func_yellow_01";
	public final static String NODE_UNITTEST_PAUSE="func_yellow_02";
	public final static String NODE_UNITTEST_PASS="func_yellow_03";
	public final static String NODE_UNITTEST_NOPASS="func_yellow_04";
	public final static String NODE_UNITTEST_UNDO="func_gray_ss";
	
	
	/*0-初始状态;1-不通过;2-通过;3-跳过*/
	public final static int NODE_STATUS_PASS=2;
	public final static int NODE_STATUS_NOPASS=1;
	public final static int NODE_STATUS_UNDO=0;
	public final static int NODE_STATUS_SKIP=3;
	/*流程状态0-正常运行;1-挂起、暂停;2-终止*/
	public final static int PROCESS_STATUS_RUN=0;
	//public final static int PROCESS_STATUS_STOP=1;
	public final static int PROCESS_STATUS_FINISH=-1;
	public final static int PROCESS_STATUS_PAUSE=1;
	public final static int PROCESS_STATUS_WORDTRAINING=2;
	
	public final static String NODE_INSTANCE_INFO_LIST="nodeInstanceInfoList";
	public final static String SYS_TITLE="09冲刺";
	public final static String STAR_PPAPER_LIST="starPaperList";
	public final static String TOTAL_MASTERY_RATE_TOP_LIST="totalMasteryRateTopList";
	public final static String LEARNING_PROCESS_RATE_TOP_LIST="learningProcessRateTopList";
	public final static String ACCURACY_RATE_TOP_LIST="accuracyRateTopList";

	//考研日期
	public final static String KY_DATE="01-10";
	//高考日期
	public final static String GK_DATE="06-07";
}
