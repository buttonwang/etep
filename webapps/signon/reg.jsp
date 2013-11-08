<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户登录</title>
<meta name="description" content="" />
<meta name="keywords" content="" />
<!-- Favicons --> 
<link rel="shortcut icon" type="image/png" HREF="img/favicons/favicon.png"/>
<link rel="icon" type="image/png" HREF="img/favicons/favicon.png"/>
<link rel="apple-touch-icon" HREF="img/favicons/apple.png" />
<!-- Main Stylesheet --> 
<link rel="stylesheet" href="css/style.css" type="text/css" />
<!-- Colour Schemes
Default colour scheme is blue. Uncomment prefered stylesheet to use it.
<link rel="stylesheet" href="css/brown.css" type="text/css" media="screen" />  
<link rel="stylesheet" href="css/gray.css" type="text/css" media="screen" />  
<link rel="stylesheet" href="css/green.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/pink.css" type="text/css" media="screen" />  
<link rel="stylesheet" href="css/red.css" type="text/css" media="screen" />
-->
<!-- Your Custom Stylesheet --> 
<link rel="stylesheet" href="css/custom.css" type="text/css" />
<!--swfobject - needed only if you require <video> tag support for older browsers -->
<script type="text/javascript" SRC="js/swfobject.js"></script>
<!-- jQuery with plugins -->
<script type="text/javascript" SRC="js/jquery-1.4.2.min.js"></script>
<!-- Could be loaded remotely from Google CDN : <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> -->
<script type="text/javascript" SRC="js/jquery.ui.core.min.js"></script>
<script type="text/javascript" SRC="js/jquery.ui.widget.min.js"></script>
<script type="text/javascript" SRC="js/jquery.ui.tabs.min.js"></script>
<!-- jQuery tooltips -->
<script type="text/javascript" SRC="js/jquery.tipTip.min.js"></script>
<!-- Superfish navigation -->
<script type="text/javascript" SRC="js/jquery.superfish.min.js"></script>
<script type="text/javascript" SRC="js/jquery.supersubs.min.js"></script>
<!-- jQuery form validation -->
<script type="text/javascript" SRC="js/jquery.validate_pack.js"></script>
<!-- jQuery popup box -->
<script type="text/javascript" SRC="js/jquery.nyroModal.pack.js"></script>
<!-- Internet Explorer Fixes --> 
<!--[if IE]>
<link rel="stylesheet" type="text/css" media="all" href="css/ie.css"/>
<script src="js/html5.js"></script>
<![endif]-->
<!--Upgrade MSIE5.5-7 to be compatible with MSIE8: http://ie7-js.googlecode.com/svn/version/2.1(beta3)/IE8.js -->
<!--[if lt IE 8]>
<script src="js/IE8.js"></script>
<![endif]-->
<script type="text/javascript">

$(document).ready(function(){
	/* setup navigation, content boxes, etc... */
	Administry.setup();
	
	/* progress bar animations - setting initial values */
	Administry.progress("#progress1", 1, 6);
	
	// validate form on keyup and submit
	var validator = $("#registerform").validate({
		rules: {			
			loginName: {
				required: true,
				minlength: 2
			},
			userName: {
				required: true,
				minlength: 2
			},
			password: {
				required: true,
				minlength: 5
			},
			password_confirm: {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
			},
			branch: "required",
			dept: "required"
		},
		messages: {			
			loginName: {
				required: "登录名不可以为空",
				minlength: jQuery.format("输入至少{0}个字符")
			},
			userName: {
				required: "真实姓名不可以为空",
				minlength: jQuery.format("输入至少{0}个字符")
			},
			password: {
				required: "密码不可以为空",
				rangelength: jQuery.format("输入至少{0}个字符")
			},
			password_confirm: {
				required: "确认密码不可以密码",
				minlength: jQuery.format("输入至少{0}个字符"),
				equalTo: "输入同样的密码"
			},
			email: {
				required: "电子邮箱地址不可以为空",
				minlength: "输入有效的电子邮箱地址"
			},
			branch: "选择本部或分子公司",
			dept: "选择一个部门"
		},
		// the errorPlacement has to take the layout into account
		errorPlacement: function(error, element) {
			error.insertAfter(element.parent().find('label:first'));
		},
		// specifying a submitHandler prevents the default submit, good for the demo
		//submitHandler: function() {
			//alert("Data submitted!");
		//},
		// set new class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("ok");
		}
	});
		
});
</script>
</head>
<body>
	<!-- Header -->
	<header id="top">
		<div class="wrapper-login">
			<!-- Title/Logo - can use text instead of image -->
			<div id="title"><img SRC="img/logo.png" alt="山西地方电力" /></div>
			<!-- Main navigation -->
			<nav id="menu">
				<ul class="sf-menu">
					<li><a href="index.jsp">登录</a></li>
					<li class="current"><a href="#">注册</a></li>
				</ul>
			</nav>
			<!-- End of Main navigation -->
		</div>
	</header>
	<!-- End of Header -->
	<!-- Page title -->
	<div id="pagetitle">
		<div class="wrapper-login"></div>
	</div>
	<!-- End of Page title -->
	
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper-login">
		<!-- Login form -->
		<section class="full">																	
			<form id="registerform" method="post" action="../signon/loginFlow!register.jhtml">			
			<fieldset>
				<legend>立即注册安规学习系统</legend>
				<c:if test="${error!=null}">
             		<div class="box box-info"><font color="red">${error}</font></div>
          		</c:if>		
				<p>
					<label class="required" for="loginName">登录名:</label><br/>
					<input type="text" id="loginName" class="half" value="${loginName}" name="loginName"/>
				</p>
				<p>
					<label class="required" for="userName">真实姓名:</label><br/>
					<input type="text" id="userName" class="half" value="${userName}" name="userName"/>					
				</p>				
				<p>
					<label class="required" for="password">密码:</label><br/>
					<input type="password" id="password" class="half" value="" name="password"/>
				</p>
				<p>
					<label class="required" for="password_confirm">确认密码:</label><br/>
					<input type="password" id="password_confirm" class="half" value="" name="password_confirm"/>
				</p>
				<p>
					<label class="required" for="email">电子邮箱:</label><br/>
					<input type="text" id="email" class="half" value="${email}" name="email"/>
				</p>				
				<p>
					<label class="required" for="branch">所在分子公司:</label><br/>					
					<select id="branch" class="half" name="branch">
						<option value="地电本部">地电本部</option>
						<option value="安泽">安泽</option>
						<option value="方山">方山</option>
						<option value="交口">交口</option>					
						<option value="柳林">柳林</option>
						<option value="离石">离石</option>
						<option value="临县">临县</option>
						<option value="蒲县">蒲县</option>
						<option value="石楼">石楼</option>
						<option value="朔州">朔州</option>
						<option value="乡宁">乡宁</option>
						<option value="兴县">兴县</option>
						<option value="中阳">中阳</option>
						<option value="电网">电网</option>
						<option value="修试">修试</option>
						<option value="其他">其他</option>										
					</select>					
				</p>				
				<p>
					<label class="required" for="dept">所在部门:</label><br/>					
					<select id="dept" class="half" name="dept">
						<option value="生产技术部">生产技术部</option>
						<option value="安全监察部">安全监察部</option>
						<option value="调度信息中心">调度信息中心</option>
						<option value="营销管理部">营销管理部</option>
						<option value="规划建设部">规划建设部</option>					
						<option value="其他">其他</option>																
					</select>
				</p>				
				<p class="box">
					<input type="submit" class="btn btn-green big" value="注册"/> or 
					<input type="reset" class="btn" value="重填"/>
				</p>

			</fieldset>
			</form>									
		</section>
		<!-- End of login form -->
		
		</div>
		<!-- End of Wrapper -->
	</div>
	<!-- End of Page content -->
	
	<!-- Page footer -->
	<footer id="bottom">
		<div class="wrapper-login">
			<p>Copyright &copy; 山西地方电力有限公司 2013 <b></p>
		</div>
	</footer>
	<!-- End of Page footer -->

<!-- User interface javascript load -->
<script type="text/javascript" src="js/administry.js"></script>
</body>
</html>