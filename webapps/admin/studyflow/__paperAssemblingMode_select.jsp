<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,java.util.List;" errorPage="" %>
<%--
动态组卷类型 下拉列表值
--%>
<option value="0"  ${__paperAssemblingMode==0?"selected":""}>手工组卷</option>
<option value="3"  ${__paperAssemblingMode==3?"selected":""}>手工组卷-多卷随机</option>
<option value="1"  ${__paperAssemblingMode==1?"selected":""}>动态组卷</option>
<option value="11" ${__paperAssemblingMode==11?"selected":""}>动态组卷(过滤本级已做题)</option>
<option value="12" ${__paperAssemblingMode==12?"selected":""}>动态组卷(过滤上一级已做题)</option>
<option value="13" ${__paperAssemblingMode==13?"selected":""}>动态组卷(过滤上两级已做题)</option>
<option value="14" ${__paperAssemblingMode==14?"selected":""}>动态组卷(从所属训练中取题)</option>
<option value="2"  ${__paperAssemblingMode==2?"selected":""}>动态出题</option>
<option value="21" ${__paperAssemblingMode==21?"selected":""}>动态出题（过滤本级）</option>
<option value="22" ${__paperAssemblingMode==22?"selected":""}>动态出题（过滤上一级）</option>
<option value="23" ${__paperAssemblingMode==23?"selected":""}>动态出题（过滤上两级）</option>