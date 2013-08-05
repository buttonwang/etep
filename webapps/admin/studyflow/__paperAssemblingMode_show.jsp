<%@ page contentType="text/html; charset=utf-8" language="java" import="com.ambow.studyflow.domain.*,java.util.List;" errorPage="" %>
<%--
动态组卷类型
--%>
${__paperAssemblingMode==0?"手工组卷":""}
${__paperAssemblingMode==3?"手工组卷-多卷随机":""}
${__paperAssemblingMode==1?"动态组卷":""}
${__paperAssemblingMode==11?"动态组卷(过滤本级已做题)":""}
${__paperAssemblingMode==12?"动态组卷(过滤上一级已做题)":""}
${__paperAssemblingMode==13?"动态组卷(过滤上两级已做题)":""}
${__paperAssemblingMode==14?"动态组卷(从所属训练中取题)":""}
${__paperAssemblingMode==2?"动态出题":""}
${__paperAssemblingMode==21?"动态出题（过滤本级）":""}
${__paperAssemblingMode==22?"动态出题（过滤上一级）":""}
${__paperAssemblingMode==23?"动态出题（过滤上两级）":""}