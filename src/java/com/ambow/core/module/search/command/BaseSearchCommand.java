package com.ambow.core.module.search.command;

import java.util.ArrayList;
import java.util.List;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;

import com.ambow.core.module.search.util.IntParser;

public abstract class BaseSearchCommand {
	protected Sort sort;
	
	/**
	 * 获得Lucene 的查询对象
	 */
	public abstract Query buildQuery();
	
	public Sort getSort() {
		return this.sort;
	}
	
	public abstract Filter getFilter();
	
	public abstract String getIndexPath();
	
	public void addSortField(String fieldName, boolean isDesc) {
		if (sort == null) {
			sort = new Sort();
		}
		SortField[] sortFields = sort.getSort();
		List<SortField> sortFieldList = new ArrayList<SortField>();
		CollectionUtils.addAll(sortFieldList, sortFields);
		sortFieldList.add(new SortField(fieldName, isDesc));
		sort.setSort((SortField[]) sortFieldList.toArray(new SortField[0]));
	}
	
	public void appendCondition(BooleanQuery query, String name, String value,
			SearchType type) {
		if (StringUtils.isBlank(value)) {
			return;
		}

		Query subQuery = null;
		switch (type) {
		case INT:
			subQuery = createIntQuery(name, Integer.valueOf(value));
			break;
		case INTARRAY:
			subQuery = createIntArrayQuery(name, value);
			break;
		case STRING:
			try {
				subQuery = createPhraseQuery(name, value);
			} catch (ParseException e) {
				/*
				 * 如果通过QueryParser 构建Query 出错，将会抛出异常 在这种情况下将会非常罕见，直接无视
				 */
			}
			break;

		case STRINGARRAY:
			subQuery = createStringArrayQuery(name, value);
		default:
			break;
		}

		if (subQuery != null) {
			query.add(subQuery, Occur.MUST);
		}
	}

	public Query createIntQuery(String name, Integer intval) {
		if (intval == null) {
			intval = 0;
		}

		TermQuery termQuery = new TermQuery(new Term(name, IntParser
				.intToString(intval)));
		return termQuery;
	}

	/**
	 * 通过一个int 数组来查找<br>
	 * 比如查找cam 的时候，传过来的值为一组cam_id，记录中只需要匹配其中<br>
	 * 的一个cam_id 就认为这个记录是我们需要查找的
	 * 
	 * @param name
	 * @param intvals
	 * @return
	 * 
	 * @author Schweigen
	 */
	public Query createIntArrayQuery(String name, String intvals) {
		if (intvals == null) {
			intvals = "0";
		}

		String searchCondition = format(intvals);
		if (StringUtils.isBlank(searchCondition)) {
			return null;
		}

		BooleanQuery query = new BooleanQuery();
		for (String frags : org.springframework.util.StringUtils
				.commaDelimitedListToStringArray(intvals)) {
			frags = StringUtils.deleteWhitespace(frags);
			TermQuery termQuery = new TermQuery(new Term(name, frags));
			query.add(termQuery, Occur.SHOULD);
		}

		return query;
	}

	/**
	 * 构建短句查询的方法<br>
	 * 比如说查找公司名称，我们可以输入“北京 公司”这样的字串，那么将会返回一个PhraseQuery 的对象
	 * 
	 * @param name
	 *            查找字段名称
	 * @param value
	 *            查找值
	 * @return PhraseQuery
	 * @throws ParseException
	 * 
	 * @author Schweigen
	 */
	public Query createPhraseQuery(String name, String value)
			throws ParseException {
		if (StringUtils.isBlank(value)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(name).append(":").append("\"").append(value).append("\"");
		QueryParser parser = new QueryParser("NAME", new PaodingAnalyzer());
		return parser.parse(sb.toString());
	}

	public Query createStringArrayQuery(String name, String strArrays) {
		String[] strs = strArrays.split(",");
		if (strs == null) {
			return null;
		}
		if (strs.length == 0) {
			return null;
		}

		BooleanQuery query = new BooleanQuery();
		for (String str : strs) {
			TermQuery termQuery = new TermQuery(new Term(name, str));
			query.add(termQuery, Occur.SHOULD);
		}
		return query;
	}

	public String format(String intStr) {
		StringBuffer sb = new StringBuffer();

		String[] intStrs = intStr.split(",");
		if (intStrs.length > 0) {
			for (String str : intStrs) {
				try {
					int val = Integer.parseInt(str);
					sb.append(IntParser.intToString(val));
					sb.append(",");
				} catch (Exception e) {
					/*
					 * 出现异常的情况为分出的int 数组中有不为数字的情况 这种情况下将直接忽略
					 */
				}
			}

			int index = sb.lastIndexOf(",");
			if (index > 0) {
				sb.delete(index, sb.length());
			}
		}

		return sb.toString();
	}

	public static enum SearchType {
		INT, INTARRAY, STRING, STRINGARRAY
	}
}
