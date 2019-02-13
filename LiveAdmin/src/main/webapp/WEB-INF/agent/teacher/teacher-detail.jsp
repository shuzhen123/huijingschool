<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>讲师详情</title>
</head>
<body>
<article class="page-container">
	<form action="addteacher" class="form form-horizontal">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">用户账号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.username }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.realname }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.password }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">手机号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.telno }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">房间号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.cid }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">房间密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.cidpassword }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">简介：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.introduction }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">头像：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img src="${userInfo.iconurl }" alt="头像" class="round" style="max-width: 80px;"> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">展示图：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img src="${userInfo.teacherurl }" alt="展示图" class="thumbnail" style="max-width: 100px;"> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">注册时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<fmt:formatDate value="${userInfo.registertime }" pattern="yyyy-MM-dd HH:mm"/>
			</div>
		</div>
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius go-back" type="button" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> 
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
$(function(){
	$(".go-back").click(function() {
		var index = parent.layer.getFrameIndex(window.name);
    	parent.layer.close(index);
	});
});
</script> 
</body>
</html>