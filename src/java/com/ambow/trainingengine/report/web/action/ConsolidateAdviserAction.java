/**
 * 
 */
package com.ambow.trainingengine.report.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.report.service.ConsolidateAdviserService;
import com.ambow.trainingengine.util.CalculateUtil;

/**
 * @author yuanjunqi
 * 
 */
@SuppressWarnings("serial")
public class ConsolidateAdviserAction extends ReportBaseAction {

	private ConsolidateAdviserService consolidateAdviserService = null;

	private Long processInstanceId = null;

	private String actor = null;

	private Integer categoryId = null;
	
	private String version =null;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * 获取流程列表
	 * 
	 * @return
	 */
	public String execute() {

		List<Map<String, Object>> sxProcessList = this
				.getConsolidateAdviserService().getProcessList(actor, 8);
		List<Map<String, Object>> wlProcessList = this
				.getConsolidateAdviserService().getProcessList(actor, 7);
		List<Map<String, Object>> hxProcessList = this
				.getConsolidateAdviserService().getProcessList(actor, 6);
		List<Map<String, Object>> kyProcessList = this
				.getConsolidateAdviserService().getProcessList(actor, 4);
		if (categoryId == null) {
			if (sxProcessList.size() > 0) {
				this.setRequestAttribute("processList", sxProcessList);
				this.setRequestAttribute("version", "mpc");
				categoryId = 8;
			} else if (sxProcessList.size() == 0 && wlProcessList.size() > 0) {
				this.setRequestAttribute("processList", wlProcessList);
				this.setRequestAttribute("version", "mpc");
				categoryId = 7;
			} else if (wlProcessList.size() == 0 && hxProcessList.size() > 0) {
				this.setRequestAttribute("processList", hxProcessList);
				this.setRequestAttribute("version", "mpc");
				categoryId = 6;
			} else if (hxProcessList.size() == 0 && kyProcessList.size() > 0) {
				this.setRequestAttribute("processList", kyProcessList);
				this.setRequestAttribute("version", "ky");
				categoryId = 4;
			} else {
				this.setRequestAttribute("processList", sxProcessList);
				this.setRequestAttribute("version", "mpc");
				categoryId = 8;
			}
		} else if (categoryId == 8) {
			this.setRequestAttribute("processList", sxProcessList);
			this.setRequestAttribute("version", "mpc");
		} else if (categoryId == 7) {
			this.setRequestAttribute("processList", wlProcessList);
			this.setRequestAttribute("version", "mpc");
		} else if (categoryId == 6) {
			this.setRequestAttribute("processList", hxProcessList);
			this.setRequestAttribute("version", "mpc");
		} else if (categoryId == 4) {
			this.setRequestAttribute("processList", kyProcessList);
			this.setRequestAttribute("version", "ky");
		}
		this.setRequestAttribute("sxSize", sxProcessList.size());
		this.setRequestAttribute("wlSize", wlProcessList.size());
		this.setRequestAttribute("hxSize", hxProcessList.size());
		this.setRequestAttribute("kySize", kyProcessList.size());
		this.setRequestAttribute("actor", actor);
		this.setRequestAttribute("categoryId", categoryId);
		return "success";
	}

	/**
	 * 获取节点列表
	 * 
	 * @return
	 */
	public String node() {
		List<Map<String, Object>> nodeList = this
				.getConsolidateAdviserService().getNodeList(processInstanceId);
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> cList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> nList = new ArrayList<Map<String, Object>>();
		String mName = null;
		String cName = null;
		String nName = null;
		Integer mFiveItems = 0, mFourItems = 0, mThreeItems = 0, mTwoItems = 0, mOneItems = 0, mHalfItems = 0, mZeroItems = 0;
		Integer cFiveItems = 0, cFourItems = 0, cThreeItems = 0, cTwoItems = 0, cOneItems = 0, cHalfItems = 0, cZeroItems = 0;
		Integer nFiveItems = 0, nFourItems = 0, nThreeItems = 0, nTwoItems = 0, nOneItems = 0, nHalfItems = 0, nZeroItems = 0;
		Float mScore = 0f, cScore = 0f;
		Integer mTotalScore = 0, cTotalScore = 0;
		Map<String, Object> mMap = new HashMap<String, Object>();
		Map<String, Object> cMap = new HashMap<String, Object>();
		Map<String, Object> nMap = new HashMap<String, Object>();
		Integer cNum = 0;
		Long nSumItems = 0L;
		Integer nSumIncorrectItems = 0;
		if(version != null && version.equals("mpc")){
			this.nodeMpc(nodeList, mList, cList, nList, mName, cName, nName, mFiveItems, mFourItems, mThreeItems, mTwoItems, mOneItems, mHalfItems, mZeroItems, cFiveItems, cFourItems, cThreeItems, cTwoItems, cOneItems, cHalfItems, cZeroItems, nFiveItems, nFourItems, nThreeItems, nTwoItems, nOneItems, nHalfItems, nZeroItems, mScore, cScore, mTotalScore, cTotalScore, mMap, cMap, nMap, cNum, nSumItems, nSumIncorrectItems);
		}else if(version !=null && version.equals("ky")){
			this.nodeKy(nodeList, mList, cList, nList, mName, cName, nName, mFiveItems, mFourItems, mThreeItems, mTwoItems, mOneItems, mHalfItems, mZeroItems, cFiveItems, cFourItems, cThreeItems, cTwoItems, cOneItems, cHalfItems, cZeroItems, nFiveItems, nFourItems, nThreeItems, nTwoItems, nOneItems, nHalfItems, nZeroItems, mScore, cScore, mTotalScore, cTotalScore, mMap, cMap, nMap, cNum, nSumItems, nSumIncorrectItems);
		}
		return "node";
	}

	private void nodeMpc(List<Map<String, Object>> nodeList,
			List<Map<String, Object>> mList, List<Map<String, Object>> cList,
			List<Map<String, Object>> nList, String mName, String cName,
			String nName, Integer mFiveItems, Integer mFourItems,
			Integer mThreeItems, Integer mTwoItems, Integer mOneItems,
			Integer mHalfItems, Integer mZeroItems, Integer cFiveItems,
			Integer cFourItems, Integer cThreeItems, Integer cTwoItems,
			Integer cOneItems, Integer cHalfItems, Integer cZeroItems,
			Integer nFiveItems, Integer nFourItems, Integer nThreeItems,
			Integer nTwoItems, Integer nOneItems, Integer nHalfItems,
			Integer nZeroItems, Float mScore, Float cScore,
			Integer mTotalScore, Integer cTotalScore, Map<String, Object> mMap,
			Map<String, Object> cMap, Map<String, Object> nMap, Integer cNum,
			Long nSumItems, Integer nSumIncorrectItems) {
		for (int i = 0; i < nodeList.size(); i++) {
			Map<String, Object> nodeMap = nodeList.get(i);
			Long sumItems = (Long) nodeMap.get("sum_items") == null ? 0
					: (Long) nodeMap.get("sum_items");
			Integer sumIncorrectItems = (Integer) nodeMap
					.get("sum_incorrect_items") == null ? 0 : (Integer) nodeMap
					.get("sum_incorrect_items");
			Integer fiveItems = (Integer) nodeMap.get("sum_five_star_items") == null ? 0
					: (Integer) nodeMap.get("sum_five_star_items");
			Integer fourItems = (Integer) nodeMap.get("sum_four_star_items") == null ? 0
					: (Integer) nodeMap.get("sum_four_star_items");
			Integer threeItems = (Integer) nodeMap.get("sum_three_star_items") == null ? 0
					: (Integer) nodeMap.get("sum_three_star_items");
			Integer twoItems = (Integer) nodeMap.get("sum_two_star_items") == null ? 0
					: (Integer) nodeMap.get("sum_two_star_items");
			Integer oneItems = (Integer) nodeMap.get("sum_one_star_items") == null ? 0
					: (Integer) nodeMap.get("sum_one_star_items");
			Integer halfItems = (Integer) nodeMap.get("sum_half_star_items") == null ? 0
					: (Integer) nodeMap.get("sum_half_star_items");
			Integer zeroItems = (Integer) nodeMap.get("sum_zero_star_items") == null ? 0
					: (Integer) nodeMap.get("sum_zero_star_items");
			Float score = (Float) nodeMap.get("score") == null ? 0
					: (Float) nodeMap.get("score");
			Integer totalScore = (Integer) nodeMap.get("total_score") == null ? 0
					: (Integer) nodeMap.get("total_score");

			String newMName = null;
			if (nodeMap.get("node_type").equals("EVALUATE")) {
				newMName = (String) nodeMap.get("c_name");
			} else {
				newMName = (String) nodeMap.get("m_name");
			}
			if (mName != null && !mName.equals(newMName)) {
				float accuracyRate = getAccuracyRate(mScore, mTotalScore);
				float masteryRate = getMasteryRate(mZeroItems, mHalfItems,
						mOneItems, mTwoItems, mThreeItems, mFourItems,
						mFiveItems);
				mMap.put("mName", mName);
				mMap.put("masteryRate", masteryRate);
				mMap.put("accuracyRate", accuracyRate);
				mMap.put("cList", cList);
				mList.add(mMap);
				mFiveItems = 0;
				mFourItems = 0;
				mThreeItems = 0;
				mTwoItems = 0;
				mOneItems = 0;
				mHalfItems = 0;
				mZeroItems = 0;
				mScore = 0f;
				mTotalScore = 0;
				mMap = new HashMap<String, Object>();
				cList = new ArrayList<Map<String, Object>>();
			}
			mFiveItems = mFiveItems + fiveItems;
			mFourItems = mFourItems + fourItems;
			mThreeItems = mThreeItems + threeItems;
			mTwoItems = mTwoItems + twoItems;
			mOneItems = mOneItems + oneItems;
			mHalfItems = mHalfItems + halfItems;
			mZeroItems = mZeroItems + zeroItems;
			mScore = mScore + score;
			mTotalScore = mTotalScore + totalScore;
			mName = newMName;

			String newNName = null;
			if (nodeMap.get("node_type").equals("EVALUATE")) {
				newNName = (String) nodeMap.get("name");
			} else {
				newNName = (String) nodeMap.get("n_name");
			}
			if (nName != null && !nName.equals(newNName)) {
				float masteryRate = getMasteryRate(nZeroItems, nHalfItems,
						nOneItems, nTwoItems, nThreeItems, nFourItems,
						nFiveItems);
				nMap.put("nName", nName);
				nMap.put("masteryRate", masteryRate);
				nMap.put("sumItems", nSumItems);
				nMap.put("sumIncorrectItems", nSumIncorrectItems);
				nMap.put("fiveItems", nFiveItems);
				nMap.put("fourItems", nFourItems);
				nMap.put("threeItems", nThreeItems);
				nList.add(nMap);
				nSumItems = 0L;
				nSumIncorrectItems = 0;
				nFiveItems = 0;
				nFourItems = 0;
				nThreeItems = 0;
				nTwoItems = 0;
				nOneItems = 0;
				nHalfItems = 0;
				nZeroItems = 0;
				nMap = new HashMap<String, Object>();
			}
			nFiveItems = nFiveItems + fiveItems;
			nFourItems = nFourItems + fourItems;
			nThreeItems = nThreeItems + threeItems;
			nTwoItems = nTwoItems + twoItems;
			nOneItems = nOneItems + oneItems;
			nHalfItems = nHalfItems + halfItems;
			nZeroItems = nZeroItems + zeroItems;
			nSumItems = nSumItems + sumItems;
			nSumIncorrectItems = nSumIncorrectItems + sumIncorrectItems;
			nName = newNName;
			
			String newCName = null;
			if (nodeMap.get("node_type").equals("EVALUATE")) {
				newCName = (String) nodeMap.get("n_name");
			} else {
				newCName = (String) nodeMap.get("c_name");
			}
			if (cName != null && !cName.equals(newCName)) {
				float accuracyRate = getAccuracyRate(cScore, cTotalScore);
				float masteryRate = getMasteryRate(cZeroItems, cHalfItems,
						cOneItems, cTwoItems, cThreeItems, cFourItems,
						cFiveItems);
				cMap.put("cName", cName);
				cMap.put("masteryRate", masteryRate);
				cMap.put("accuracyRate", accuracyRate);
				cMap.put("cNum", cNum);
				cMap.put("nList", nList);
				cList.add(cMap);
				cFiveItems = 0;
				cFourItems = 0;
				cThreeItems = 0;
				cTwoItems = 0;
				cOneItems = 0;
				cHalfItems = 0;
				cZeroItems = 0;
				cScore = 0f;
				cTotalScore = 0;
				cMap = new HashMap<String, Object>();
				cNum = 0;
				nList = new ArrayList<Map<String, Object>>();
			}
			cFiveItems = cFiveItems + fiveItems;
			cFourItems = cFourItems + fourItems;
			cThreeItems = cThreeItems + threeItems;
			cTwoItems = cTwoItems + twoItems;
			cOneItems = cOneItems + oneItems;
			cHalfItems = cHalfItems + halfItems;
			cZeroItems = cZeroItems + zeroItems;
			cScore = cScore + score;
			cTotalScore = cTotalScore + totalScore;
			cNum = cNum + 1;
			cName = newCName;

			
		}
		if (nodeList.size() > 0) {
			float nMasteryRate = getMasteryRate(nZeroItems, nHalfItems,
					nOneItems, nTwoItems, nThreeItems, nFourItems, nFiveItems);
			nMap.put("nName", nName);
			nMap.put("masteryRate", nMasteryRate);
			nMap.put("sumItems", nSumItems);
			nMap.put("sumIncorrectItems", nSumIncorrectItems);
			nMap.put("fiveItems", nFiveItems);
			nMap.put("fourItems", nFourItems);
			nMap.put("threeItems", nThreeItems);
			nList.add(nMap);

			float cAccuracyRate = getAccuracyRate(cScore, cTotalScore);
			float cMasteryRate = getMasteryRate(cZeroItems, cHalfItems,
					cOneItems, cTwoItems, cThreeItems, cFourItems, cFiveItems);
			cMap.put("cName", cName);
			cMap.put("masteryRate", cMasteryRate);
			cMap.put("accuracyRate", cAccuracyRate);
			cMap.put("cNum", cNum + 1);
			cMap.put("nList", nList);
			cList.add(cMap);

			float mAccuracyRate = getAccuracyRate(mScore, mTotalScore);
			float mMasteryRate = getMasteryRate(mZeroItems, mHalfItems,
					mOneItems, mTwoItems, mThreeItems, mFourItems, mFiveItems);
			mMap.put("mName", mName);
			mMap.put("masteryRate", mMasteryRate);
			mMap.put("accuracyRate", mAccuracyRate);
			mMap.put("cList", cList);
			mList.add(mMap);
		}

		//System.out.println(mList.size());
		this.setRequestAttribute("mList", mList);
	}

	private void nodeKy(List<Map<String, Object>> nodeList,
			List<Map<String, Object>> mList, List<Map<String, Object>> cList,
			List<Map<String, Object>> nList, String mName, String cName,
			String nName, Integer mFiveItems, Integer mFourItems,
			Integer mThreeItems, Integer mTwoItems, Integer mOneItems,
			Integer mHalfItems, Integer mZeroItems, Integer cFiveItems,
			Integer cFourItems, Integer cThreeItems, Integer cTwoItems,
			Integer cOneItems, Integer cHalfItems, Integer cZeroItems,
			Integer nFiveItems, Integer nFourItems, Integer nThreeItems,
			Integer nTwoItems, Integer nOneItems, Integer nHalfItems,
			Integer nZeroItems, Float mScore, Float cScore,
			Integer mTotalScore, Integer cTotalScore, Map<String, Object> mMap,
			Map<String, Object> cMap, Map<String, Object> nMap, Integer cNum,
			Long nSumItems, Integer nSumIncorrectItems) {
		for(int i = 0; i < nodeList.size(); i++){
			Map<String, Object> nodeMap = nodeList.get(i);
			Long sumItems = (Long) nodeMap.get("sum_items") == null ? 0: (Long) nodeMap.get("sum_items");
			Integer sumIncorrectItems = (Integer) nodeMap.get("sum_incorrect_items") == null ? 0 : (Integer) nodeMap.get("sum_incorrect_items");
			Integer fiveItems = (Integer) nodeMap.get("sum_five_star_items") == null ? 0: (Integer) nodeMap.get("sum_five_star_items");
			Integer fourItems = (Integer) nodeMap.get("sum_four_star_items") == null ? 0: (Integer) nodeMap.get("sum_four_star_items");
			Integer threeItems = (Integer) nodeMap.get("sum_three_star_items") == null ? 0: (Integer) nodeMap.get("sum_three_star_items");
			Integer twoItems = (Integer) nodeMap.get("sum_two_star_items") == null ? 0: (Integer) nodeMap.get("sum_two_star_items");
			Integer oneItems = (Integer) nodeMap.get("sum_one_star_items") == null ? 0: (Integer) nodeMap.get("sum_one_star_items");
			Integer halfItems = (Integer) nodeMap.get("sum_half_star_items") == null ? 0: (Integer) nodeMap.get("sum_half_star_items");
			Integer zeroItems = (Integer) nodeMap.get("sum_zero_star_items") == null ? 0: (Integer) nodeMap.get("sum_zero_star_items");
			Float score = (Float) nodeMap.get("score") == null ? 0: (Float) nodeMap.get("score");
			Integer totalScore = (Integer) nodeMap.get("total_score") == null ? 0: (Integer) nodeMap.get("total_score");
			
			String newNName = null;
			if((nodeMap.get("c_name")==null || nodeMap.get("c_name").equals(""))&& (nodeMap.get("m_name") == null || nodeMap.get("m_name").equals(""))){
				newNName = (String) nodeMap.get("name");
			}else if (nodeMap.get("m_name")==null || nodeMap.get("m_name").equals("")) {
				newNName = (String) nodeMap.get("n_name");
				if(nodeMap.get("c_name").equals("阅读训练")){
					newNName =	(String) nodeMap.get("name");
				}
			} else {
				newNName = (String) nodeMap.get("n_name");
			}
			if (nName != null && !nName.equals(newNName)) {
				float masteryRate = getMasteryRate(nZeroItems, nHalfItems,
						nOneItems, nTwoItems, nThreeItems, nFourItems,
						nFiveItems);
				nMap.put("nName", nName);
				nMap.put("masteryRate", masteryRate);
				nMap.put("sumItems", nSumItems);
				nMap.put("sumIncorrectItems", nSumIncorrectItems);
				nMap.put("fiveItems", nFiveItems);
				nMap.put("fourItems", nFourItems);
				nMap.put("threeItems", nThreeItems);
				nList.add(nMap);
				nSumItems = 0L;nSumIncorrectItems = 0;
				nFiveItems = 0;nFourItems = 0;nThreeItems = 0;nTwoItems = 0;nOneItems = 0;nHalfItems = 0;nZeroItems = 0;
				nMap = new HashMap<String, Object>();
			}
			nFiveItems = nFiveItems + fiveItems;
			nFourItems = nFourItems + fourItems;
			nThreeItems = nThreeItems + threeItems;
			nTwoItems = nTwoItems + twoItems;
			nOneItems = nOneItems + oneItems;
			nHalfItems = nHalfItems + halfItems;
			nZeroItems = nZeroItems + zeroItems;
			nSumItems = nSumItems + sumItems;
			nSumIncorrectItems = nSumIncorrectItems + sumIncorrectItems;
			nName = newNName;
			
			String newCName = null;
			if((nodeMap.get("c_name")==null || nodeMap.get("c_name").equals(""))&& (nodeMap.get("m_name") == null || nodeMap.get("m_name").equals(""))){
				newCName = (String) nodeMap.get("n_name");
			}else if (nodeMap.get("m_name")==null || nodeMap.get("m_name").equals("")) {
				newCName = (String) nodeMap.get("c_name");
				if(newCName.equals("阅读训练")){
					newCName =	(String) nodeMap.get("n_name");
				}
			} else {
				newCName = (String) nodeMap.get("c_name");
			}
			if (cName != null && !cName.equals(newCName)) {
				float accuracyRate = getAccuracyRate(cScore, cTotalScore);
				float masteryRate = getMasteryRate(cZeroItems, cHalfItems,
						cOneItems, cTwoItems, cThreeItems, cFourItems,
						cFiveItems);
				cMap.put("cName", cName);
				cMap.put("masteryRate", masteryRate);
				cMap.put("accuracyRate", accuracyRate);
				cMap.put("cNum", cNum);
				cMap.put("nList", nList);
				cList.add(cMap);
				cFiveItems=0;cFourItems=0;cThreeItems=0;cTwoItems=0;cOneItems=0;cHalfItems=0;cZeroItems=0;
				cScore=0f;cTotalScore=0;
				cMap = new HashMap<String, Object>();
				cNum = 0;
				nList = new ArrayList<Map<String, Object>>();
			}
			cFiveItems = cFiveItems + fiveItems;
			cFourItems = cFourItems + fourItems;
			cThreeItems = cThreeItems + threeItems;
			cTwoItems = cTwoItems + twoItems;
			cOneItems = cOneItems + oneItems;
			cHalfItems = cHalfItems + halfItems;
			cZeroItems = cZeroItems + zeroItems;
			cScore = cScore + score;
			cTotalScore = cTotalScore + totalScore;
			cNum = cNum + 1;
			cName = newCName;
			
			String newMName = null;
			if((nodeMap.get("c_name")==null || nodeMap.get("c_name").equals(""))&& (nodeMap.get("m_name") == null || nodeMap.get("m_name").equals(""))){
				newMName = (String) nodeMap.get("n_name");
			}else if (nodeMap.get("m_name")==null || nodeMap.get("m_name").equals("")) {
				newMName = (String) nodeMap.get("c_name");
			} else {
				newMName = (String) nodeMap.get("m_name");
			}
			if (mName != null && !mName.equals(newMName)) {
				float accuracyRate = getAccuracyRate(mScore, mTotalScore);
				float masteryRate = getMasteryRate(mZeroItems, mHalfItems,
						mOneItems, mTwoItems, mThreeItems, mFourItems,
						mFiveItems);
				mMap.put("mName", mName);
				mMap.put("masteryRate", masteryRate);
				mMap.put("accuracyRate", accuracyRate);
				mMap.put("cList", cList);
				mList.add(mMap);
				mFiveItems=0;mFourItems=0;mThreeItems=0;mTwoItems=0;mOneItems=0;mHalfItems=0;mZeroItems=0;
				mScore=0f;mTotalScore=0;
				mMap = new HashMap<String, Object>();
				cList = new ArrayList<Map<String, Object>>();
			}
			mFiveItems = mFiveItems + fiveItems;
			mFourItems = mFourItems + fourItems;
			mThreeItems = mThreeItems + threeItems;
			mTwoItems = mTwoItems + twoItems;
			mOneItems = mOneItems + oneItems;
			mHalfItems = mHalfItems + halfItems;
			mZeroItems = mZeroItems + zeroItems;
			mScore = mScore + score;
			mTotalScore = mTotalScore + totalScore;
			mName = newMName;
		}
		if (nodeList.size() > 0) {
			float nMasteryRate = getMasteryRate(nZeroItems, nHalfItems,
					nOneItems, nTwoItems, nThreeItems, nFourItems, nFiveItems);
			nMap.put("nName", nName);
			nMap.put("masteryRate", nMasteryRate);
			nMap.put("sumItems", nSumItems);
			nMap.put("sumIncorrectItems", nSumIncorrectItems);
			nMap.put("fiveItems", nFiveItems);
			nMap.put("fourItems", nFourItems);
			nMap.put("threeItems", nThreeItems);
			nList.add(nMap);

			float cAccuracyRate = getAccuracyRate(cScore, cTotalScore);
			float cMasteryRate = getMasteryRate(cZeroItems, cHalfItems,
					cOneItems, cTwoItems, cThreeItems, cFourItems, cFiveItems);
			cMap.put("cName", cName);
			cMap.put("masteryRate", cMasteryRate);
			cMap.put("accuracyRate", cAccuracyRate);
			cMap.put("cNum", cNum + 1);
			cMap.put("nList", nList);
			cList.add(cMap);

			float mAccuracyRate = getAccuracyRate(mScore, mTotalScore);
			float mMasteryRate = getMasteryRate(mZeroItems, mHalfItems,
					mOneItems, mTwoItems, mThreeItems, mFourItems, mFiveItems);
			mMap.put("mName", mName);
			mMap.put("masteryRate", mMasteryRate);
			mMap.put("accuracyRate", mAccuracyRate);
			mMap.put("cList", cList);
			mList.add(mMap);
		}
		this.setRequestAttribute("mList", mList);

	}

	private float getAccuracyRate(Float score, Integer totalScore) {
		if (totalScore == null || totalScore == 0) {
			return 0;
		}
		float accuracyRate = score / totalScore * 100;
		return accuracyRate;
	}

	private float getMasteryRate(Integer zeroItems, Integer halfItems,
			Integer oneItems, Integer twoItems, Integer threeItems,
			Integer fourItems, Integer fiveItems) {
		int totalNum = zeroItems + halfItems + oneItems + twoItems + threeItems
				+ fourItems + fiveItems;
		return CalculateUtil.masteryRate(zeroItems, halfItems, oneItems,
				twoItems, threeItems, fourItems, fiveItems, totalNum);
	}

	public ConsolidateAdviserService getConsolidateAdviserService() {
		return consolidateAdviserService;
	}

	public void setConsolidateAdviserService(
			ConsolidateAdviserService consolidateAdviserService) {
		this.consolidateAdviserService = consolidateAdviserService;
	}
}
