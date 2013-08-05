<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="ambow" prefix="ambow"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();
	session.setAttribute("basePath",basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title> </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${basePath}/admin/css/admin.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/interface/SecurityService.js"/>'></script>
<script type="text/javascript">
		function ClickUser(){
			strItem();
			if(document.all.username.value==""){
				alert("登录名不能为空!");
				document.all.username.focus();
				document.all.username.select();
				return false;
			}
			if(document.all.password.value==""){
				alert("密码不能为空!");
				document.all.password.focus();
				document.all.password.select();
				return false;
			}
			var phoneNum = document.all.phoneNumber.value;
			if( phoneNum == "")
 			{
	 			alert("请输入电话号码!");
	 			document.all.phoneNumber.focus();
	 			return false;
 			}
 	
 			var phoneReg = new RegExp("^[0-9]{3,4}-[0-9]{7,8}$");
			var mobileReg = new RegExp("^1[35]{1}[0-9]{9}$");
			//if(!mobileReg.test(phoneNum) && !phoneReg.test(phoneNum))
			//{
			//	alert("电话号码格式不正确!");
			//	return false;
			//}
			if(document.all.realName.value==""){
				alert("请输入真实姓名!");
				document.all.realName.focus();
				document.all.realName.select();
				return false;
			}
			if(document.all.email.value==""){
				alert("输入邮件地址格式不正确");
				document.all.email.focus();
				document.all.email.select();
				return false;
			}
			if(document.all.roleids.value==""){
				alert("请为用户赋予角色");
				return false;
			}
			document.userinfo.submit();
		}
		
		//function isEmail(str){
		//        var reg = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		//        return reg.test(str);
		//	}
		function nameOnly(){
			var rname=document.all.rname.value;
			var uname=document.all.username.value;
			if(rname!=null&&rname!=""){
				if(rname==uname){
					return;
				}
			}
			SecurityService.nameOnly("SysUser","username",document.all.username.value,function(val)
			{
				var vale = val*1;
				if(vale!=0){
					document.all.username.value="";
					DWRUtil.setValue("meg", "用户名已存在");
				}else{
					DWRUtil.setValue("meg", "");
				}
			});
		}
		
		function toR(){
			for(i=0;i<document.all.item1.length;i++)
			{
				if(document.all.item1.options(i).selected==true)
				{
					var oOption=document.createElement('OPTION');
					oOption.text=document.all.item1.options(i).text;
					oOption.value=document.all.item1.value;
					document.all.item2.add(oOption);
					document.all.item1.remove(i);
					i--;
				}
			}
		}
		function toL(){
			for(i=0;i<document.all.item2.length;i++)
			{
				if(document.all.item2.options(i).selected==true)
				{
					var oOption=document.createElement('OPTION');
					oOption.text=document.all.item2.options(i).text;
					oOption.value=document.all.item2.options(i).value;
					document.all.item1.add(oOption);
					document.all.item2.remove(i);
					i--;
				}
			}
		}
		function strItem(){
			var n='';
    	 	var length = document.all.item1.length;
	 		for(i=0;i<length;i++){
	 			n=n+document.all.item1.options(i).value+",";
	 		}
	 		n = n.substr(0,n.length-1);
	 		document.all.roleids.value=n;
		}
		</script>
</head>

<body>
<form method="post" name="userinfo" action="${basePath}/admin/adminManage!save.jhtml">
<input type="hidden" name="id" value="${sysUser.id}"/>
<input type="hidden" name="rname" value="${sysUser.username}"/>
<input type="hidden" name="roleids" value=""/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="location">当前位置：系统管理 &gt; 系统用户 &gt; <c:choose><c:when test="${sysUser.id>0}">用户编辑</c:when><c:otherwise>新增用户</c:otherwise></c:choose></td>
  </tr>
</table>
<table class="txt12555555line-height"  width="100%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#E3E3E3">
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">登录名：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="text" name="username" id="username" onblur="nameOnly()" value="${sysUser.username}" size="30"/>
    	<font color="red"><span class="message" id="meg"></span></font>
    </td>
   </tr>
   <tr>
   <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">密  码：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input class="logininputmanage" type="password" name="password" value="" id="password" size="32"/>
    </td>
    
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">真实名称：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
      <input class="logininputmanage" type="text" name="realName" id="realName" value="${sysUser.realName}" size="30"/>
    </td>
   </tr>
    <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">性  别：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    <select name="gender">
	            	<option value="1" >男</option>
	            	<option value="0" <c:if test="${sysUser.gender==0}">selected="selected"</c:if>>女</option>
         </select>
    </td>
  </tr>
  <c:choose><c:when test="${sysUser.id>0}"><tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">状  态：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<select name="status">
	            	<option value="0">启用</option>
	            	<option value="1" <c:if test="${sysUser.status==1}">selected="selected"</c:if>>未启用</option>
         </select>
    </td>
   </tr></c:when><c:otherwise>
  <input type="hidden" name="status" value="0"/>
   </c:otherwise></c:choose>
   <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">电  话：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
      <input type="text" class="logininputmanage" name="phoneNumber" id="phoneNumber" value="${sysUser.phoneNumber}" size="30">
    </td>
  </tr>
  <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">电子邮件：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<input type="text" class="logininputmanage" name="email" id="email" value="${sysUser.email}" size="30">
    </td>
    </tr>
    <tr>
    <td width="17%" align="right" valign="top" bgcolor="#F7F7F7"  class="txt12blue">选择角色：</td>
    <td width="83%" align="left" valign="top" bgcolor="#FFFFFF">
    	<table>
      	 <tr><td class="txt12blue" align="center">未选角色</td><td></td><td align="center" class="txt12blue">已选角色</td></tr>
		 <tr>
		 <td><select name="ITEM" size="8" multiple id="item2" style="width:150px" class="logininputmanage">
              <c:forEach items="${rolelist}" var="role">
              	<c:set var="flag" value="1"/>
              	<c:forEach items="${sysUser.sysRole}" var="urole">
              	<c:if test="${role.id==urole.id}"><c:set var="flag" value="0"/></c:if>	
              	</c:forEach>
              	<c:if test="${flag==1}"><option value="${role.id}">${role.name}</option></c:if>
              </c:forEach>
          </select>
         </td>
         <td><input name="button2" type="button" onClick="toL()" value=">>" class="btn_2k3">
         <br><input name="button" type="button" onClick="toR()" value="<<" class="btn_2k3">
        </td>
		<td><select id="item1" class="logininputmanage" multiple size="8" name="ITEM" style="width:150px">
				<c:forEach items="${sysUser.sysRole}" var="role">
					<option value="${role.id}">${role.name}</option>
				</c:forEach>
			</select>
        </td>
        </tr>
      </table>
    </td>
    </tr>
  <tr>
    <td height="70" colspan="4" align="center" bgcolor="#FFFFFF"><table width="121" border="0">
      <tr>
        <td>
        	<input type="button" value="  保 存  " class="btn_2k3" onclick=" ClickUser()"/>&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="button" value="  返 回  " class="btn_2k3" onClick="history.back();"/>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>