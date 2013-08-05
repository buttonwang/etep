/*
 * PaginationTag.java
 *
 * Created on 2007-6-6
 *
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 *
 * Original Author: zhangrui
 * Contributor(s):
 *
 * Changes
 * -------
 * $Log: PaginationTag.java,v $
 * Revision 1.4  2008/01/10 09:45:12  guoqingyun
 * �޸ķ�ҳ�����ĳ���
 *
 * Revision 1.3  2008/01/08 09:46:06  guoqingyun
 * *** empty log message ***
 *
 * Revision 1.2  2007/12/20 08:19:53  guoqingyun
 * *** empty log message ***
 *
 * Revision 1.1  2007/12/11 06:55:22  lixin
 * evaluating 2.0 ��Ŀ�ļ���
 *
 * Revision 1.2  2007/09/13 01:42:40  wanglirong
 * *** empty log message ***
 *
 * Revision 1.1  2007/08/15 10:03:36  wangxiaodong
 * *** empty log message ***
 *
 * Revision 1.4  2007/08/13 12:14:58  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.3  2007/08/13 00:59:45  xiaosa
 * *** empty log message ***
 *
 * Revision 1.2  2007/08/07 03:09:21  xiaosa
 * *** empty log message ***
 *
 * Revision 1.1  2007/08/06 07:07:06  xiaosa
 * *** empty log message ***
 *
 * Revision 1.6  2007/06/26 09:31:26  zhangrui
 * *** empty log message ***
 *
 * Revision 1.5  2007/06/26 09:26:23  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.4  2007/06/12 08:17:34  zhangrui
 * *** empty log message ***
 *
 * Revision 1.3  2007/06/12 08:01:40  zhangrui
 * *** empty log message ***
 *
 * Revision 1.2  2007/06/11 06:09:54  zhangrui
 * *** empty log message ***
 *
 * Revision 1.1  2007/06/06 11:03:12  zhangrui
 * *** empty log message ***
 *
 */
/**
 * 
 */
package com.ambow.trainingengine.web.tag;

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

	/** 分类的id */
	private Integer catalogId;

	/** 降序排序字段 */
	private String orderByItem;

	/** 其他参数,格式 &key1=value1&key2=value2 */
	private String otherParams;
	/**总记录数*/
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

	public String getActionName() {
		int pos = actionName.indexOf("?");
		if (pos > 0) {
			actionName = actionName.substring(0, pos - 1);
		}
		return actionName + ".jhtml";
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setCatalogId(Integer id) {
		catalogId = id;
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
		/*
		 * if (total > 1) { sb.append("<div class='turn'>"); sb.append("<b><a
		 * href='").append(getActionName())
		 * .append("?pageNo=").append(getPreviousPage()); if (catalogId != null) {
		 * sb.append("&catalogId=").append(catalogId); } if (orderByItem !=
		 * null) { sb.append("&orderByItem=").append(orderByItem); } if
		 * (otherParams != null) { sb.append(otherParams); } sb.append("'><<
		 * 上一页</a>"); } for (int i = 1; i <= total; i++) { if (total == 1) {
		 * break; } if (i == num) { sb.append("<span>").append(i).append("</span>");
		 * continue; }
		 * 
		 * if (num < 10 && i <= 10) { sb.append("<a
		 * href='").append(getActionName()).append( "?pageNo=").append(i); if
		 * (catalogId != null) { sb.append("&catalogId=").append(catalogId); }
		 * if (orderByItem != null) {
		 * sb.append("&orderByItem=").append(orderByItem); } if (otherParams !=
		 * null) { sb.append(otherParams); } sb.append("'>");
		 * sb.append(i).append("</a>"); if (i == 10) { break; } } else { if (i <=
		 * 2 || (i > num - 5 && i <= num + 4) || i > total - 2) { sb.append("<a
		 * href='").append(getActionName()).append( "?pageNo=").append(i); if
		 * (catalogId != null) { sb.append("&catalogId=").append(catalogId); }
		 * if (orderByItem != null) {
		 * sb.append("&orderByItem=").append(orderByItem); } if (otherParams !=
		 * null) { sb.append(otherParams); } sb.append("'>");
		 * sb.append(i).append("</a>"); } else { if (i > 2 && i < num) {
		 * sb.append("<b>...</b>"); i = num - 5; } else { if (i > num + 4 && i <=
		 * total - 2) { sb.append("<b>...</b>"); i = total - 2; } } } } } if
		 * (total > 1) { sb.append("<b><a href='").append(getActionName())
		 * .append("?pageNo=").append(getNextPage()); if (catalogId != null) {
		 * sb.append("&catalogId=").append(catalogId); } if (orderByItem !=
		 * null) { sb.append("&orderByItem=").append(orderByItem); } if
		 * (otherParams != null) { sb.append(otherParams); } sb.append("'>下一页 >></a></b>"); }
		 */
		double k=Math.random();		
		if (total > 1) {

		//	sb.append("<form id='pageForm' action='" + getActionName()
		//			+ "' method='post'>");
			sb.append("共");
			if(totalCount!=null&&totalCount.intValue()>0)
				sb.append(totalCount+"条");
			sb.append(total+"页，当前第"+num+"页&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:page(1)");

			sb.append("'>首页</a> | ");
			sb.append("<a href='javascript:page(" + getPreviousPage() + ")");

			sb.append("'>上一页</a> | ");
			sb.append("<a href='javascript:page(" + getNextPage() + ")");

			sb.append("'>下一页</a> | ");
			sb.append("<a href='javascript:page(" + total + ")");

			sb.append("'>末页</a> ");
			int numSize=String.valueOf(total).length();
			sb.append("<input id='p"+k+"' name='pageNo' type='text' style='width:"+(32+numSize)+"px;' align='absmiddle' maxlength='"+numSize+"' value='"+num+"'>&nbsp;");
			sb.append("<input name='go'  class='btn_2k3' type='button' id='go' value='GO' align='absmiddle' onclick=\"page(-1,'p"+k+"');\">&nbsp;&nbsp;&nbsp;&nbsp;");

			if (otherParams != null && !otherParams.equals("")) {
				String[] params = otherParams.split("&");
				String name = "";
				String value = "";
				for (String param : params) {
					name = param.split("=")[0];
					if (param.split("=").length > 1) {
						value = param.split("=")[1];
					}

					sb.append("<input type='hidden' name='" 
							+ name + "' value='" + value + "'>");
					value="";
				}
			}
		//	sb.append("</form>");
			sb.append("<script>");
			sb.append("function page(toPageNo,inputId){");
			sb.append("if(toPageNo!='-1'){document.getElementsByName('pageNo')[0].value = toPageNo;}else{");
			sb.append("if(!isPlus(document.getElementById(inputId).value)){alert('请您输入大于0的数字');return;}");
			sb.append("if(document.getElementById(inputId).value > " + total + "){alert('您输入的页数已经超过最大页数，最大页数是" + total + "');return;}");
			//sb.append("if(document.getElementById(inputId).value!=null)document.getElementsByName('pageNo')[0].value=document.getElementById(inputId).value;");
			sb.append("document.getElementsByName('pageNo')[0].value=document.getElementById(inputId).value;}document.getElementById('pageForm').submit();}");
			
			sb.append("function isPlus(value){");
			sb.append("ValidationExpression=/^[0-9]+$/;");
			sb.append("if (ValidationExpression.test(value))return true;");
			sb.append("return false;");
			sb.append("}");
			 
			sb.append("</script>");
		}

		try {
			JspWriter out = pageContext.getOut();
			out.write(sb.toString());
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException(e);
		}

	}

	public String getOtherParams() {
		return otherParams;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
