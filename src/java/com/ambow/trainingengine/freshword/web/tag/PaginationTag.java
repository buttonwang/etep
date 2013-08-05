/**
 * PaginationTag.java
 * 
 * Created on 2008-7-24 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
package com.ambow.trainingengine.freshword.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings( { "serial", "unused" })
public class PaginationTag extends TagSupport {
	/** 处理分页的action path */
	private String actionName;

	/** 页面数 */
	private Integer total;

	/** 当前页码 */
	private Integer num;
	
	/** 降序排序字段*/
	private String orderByItem;
	
	/** 其他参数,格式 &key1=value1&key2=value2*/
	private String otherParams;
	
	private Integer type;
	
	private Integer totalCount;

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public void setOtherParams(String params) {
		this.otherParams = params;
	}

	public void setOrderByItem(String orderByItem) {
		this.orderByItem = orderByItem;
	}

	public String getActionName(){
		int pos = actionName.indexOf("?");
		if(pos > 0){
			actionName = actionName.substring(0,pos-1);
		}
		return actionName + ".jhtml";
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public int getPreviousPage() {
		if (num - 1 <= 0) {
			return 1;
		} else {
			return (num - 1);
		}
	}

	public int getNextPage() {
		if (num + 1 >= total) {
			return total;
		} else {
			return (num + 1);
		}
	}
	
	/**
	 * 产生类似如下的html代码 <div class="turn"><b><a href="#"><< 上一页</a></b><span>1</span>
	 * <a href="#">2</a><a href="#">3</a><b>...</b><a href="#">4</a> <a
	 * href="#">5</a><a href="#">6</a><a href="#">7</a><a href="#">8</a>
	 * <a href="#">9</a><a href="#">10</a><b><a href="#">下一页 >></a></b>
	 * </div>
	 * 
	 */
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		//sb.append("<div class='pblist_L_1'>");
		sb.append("<!--b><a href='").append(getActionName()).append("?pageNo=1");
		if (orderByItem != null) {
			sb.append("&orderByItem=").append(orderByItem);
		}
		if (otherParams != null) {
			sb.append(otherParams);
		}
		
		sb.append("'>首页</a>&nbsp;&nbsp;&nbsp;</b-->");
		
		if (total > 1) {
			sb.append("<b><a href='").append(getActionName())
					.append("?pageNo=").append(getPreviousPage());
			if (orderByItem != null) {
				sb.append("&orderByItem=").append(orderByItem);
			}
			if (otherParams != null) {
				sb.append(otherParams);
			}
			sb.append("'><</a>&nbsp;&nbsp;&nbsp;</b>");
		}
		for (int i = 1; i <= total; i++) {
			
			if (i == num ) {
				sb.append("<span>").append(i).append("</span>&nbsp;&nbsp;&nbsp;");
				continue;
			}

			if (num < 10 && i <= 10) {
				sb.append("<a href='").append(getActionName());
				
				sb.append("?pageNo=").append(i);
				if(orderByItem != null){
					sb.append("&orderByItem=").append(orderByItem);
				}
				if(otherParams != null){
					sb.append(otherParams);
				}
				if(i == num){
					sb.append("'>");
					sb.append(i).append("</a>&nbsp;&nbsp;&nbsp;   ");
				}else{
					sb.append("'>");
					sb.append(i).append("</a>&nbsp;&nbsp;&nbsp;  ");
				}

				if (i == 10) {
					break;
				}
			} else {
				if (i <= 2 || (i > num - 5 && i <= num + 4) || i > total - 2) {
					sb.append("<a href='").append(getActionName()).append("?pageNo=")
							.append(i);
					if(orderByItem != null){
						sb.append("&orderByItem=").append(orderByItem);
					}
					if(otherParams != null){
						sb.append(otherParams);
					}
					if(i == num){
						sb.append("'>");
						sb.append(i).append("</a>&nbsp;&nbsp;&nbsp;  ");
					}else{
						sb.append("'>");
						sb.append(i).append("</a>&nbsp;&nbsp;&nbsp;  ");
					}
				} else {
					if (i > 2 && i < num) {
						sb.append("<b>...</b>");
						i = num - 5;
					} else {
						if (i > num + 8 && i <= total - 2) {
							sb.append("<b>...</b>");
							i = total - 2;
						}
					}
				}

			}
		}
		if (total > 1) {
			sb.append("<b><a href='").append(getActionName())
					.append("?pageNo=").append(getNextPage());
			if (orderByItem != null) {
				sb.append("&orderByItem=").append(orderByItem);
			}
			if (otherParams != null) {
				sb.append(otherParams);
			}
			sb.append("'>></a>&nbsp;&nbsp;&nbsp;</b>");
			//sb.append("</div>");
		}
		sb.append("<!--b><a href='").append(getActionName()).append("?pageNo=").append(total);
		if (orderByItem != null) {
			sb.append("&orderByItem=").append(orderByItem);
		}
		if (otherParams != null) {
			sb.append(otherParams);
		}
		sb.append("'>尾页</a>&nbsp;&nbsp;&nbsp;</b-->");
		try {
			JspWriter out = pageContext.getOut();
			out.write(sb.toString());
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException(e);
		}	
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}