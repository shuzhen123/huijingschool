<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico">
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/static/h-ui.admin/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet"
	type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>后台登录 - 慧鲸学堂</title>

</head>
<body>
<%  
    String name="";    
    String psw="";    
    Cookie[] cookies=request.getCookies();    
    if(cookies!=null&&cookies.length>0){     
        //遍历Cookie    
        for(int i=0;i<cookies.length;i++){    
            Cookie cookie=cookies[i];    
            //此处类似与Map有name和value两个字段,name相等才赋值,并处理编码问题     
            if("liveaccount".equals(cookie.getName())){    
                name=cookie.getValue();    
            }    
            if("livepwd".equals(cookie.getName())){    
                psw=cookie.getValue();    
            }    
        }    
    }   
 %>
	<input type="hidden" id="TenantId" name="TenantId" value="" />
	<div class="header"></div>
	<div class="loginWraper">
		<div id="loginform" class="loginBox">
			<form class="form form-horizontal" action="${pageContext.request.contextPath}/hxloginaction" method="post">
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
					<div class="formControls col-xs-8">
						<input name="account" type="text"
						<%
							if(!"".equals(name)){
								out.print("value='"+name+"'");	
							}
						%>
						placeholder="账户" class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
					<div class="formControls col-xs-8">
						<input name="password" type="password" 
						<%
							if(!"".equals(psw)){
								out.print("value='"+psw+"'");	
							}
						%>
						placeholder="密码" class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<input class="input-text size-L" type="text" placeholder="验证码" name="verifycode" style="width: 150px;"> 
						<img src="${pageContext.request.contextPath}/verifycode"> <a id="kanbuq" href="javascript:;">看不清，换一张</a>
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<label for="online"> <input type="checkbox" name="online" 
						<%
							if(!"".equals(name) && !"".equals(psw)){
								out.print("checked");	
							}
						%>
						> 使我保持登录状态</label>
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<input name="" type="submit" class="btn btn-success radius size-L"
							value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
					</div>
				</div>
				
				<c:if test="${error_msg != null && error_msg != ''}">
					<div class="row cl" style="margin-top: 10px; color: #dd514c;">
						<div class="formControls col-xs-8 col-xs-offset-3">
							<span style="background-color: white; padding: 2px 5px;">${error_msg}</span>
						</div>
					</div>
				</c:if>
				
			</form>
		</div>
	</div>
	<div class="footer">Copyright 慧鲸学堂</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script>

	<script>
		$(function() {
			$("#kanbuq").click(function() {
				$("img").attr("src","${pageContext.request.contextPath}/verifycode?" + Math.random());
			});
			
			$("input").focus(function() {
				$(this).removeClass("error");
			})
			
			<%-- 表单提交 --%>
			$("form").submit(function(){
				var boo = true;
				$.each($("input.input-text"), function(index, e) {
					/* if($.trim($(e).val()) == ""){
						boo = false;
						$(e).addClass("error");
					} */
				})
				return boo;
			});
		})
	</script>

</body>
</html>