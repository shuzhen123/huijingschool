﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
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
<!--/meta 作为公共模版分离出去-->

<title>基本设置</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span> 个人信息
</nav>
<div class="page-container">
	<div id="tab-system" class="HuiTab">
		<div class="tabBar cl">
			<span>基本信息</span>
			<span>密码修改</span>
		</div>
		<div class="tabCon">
			<form class="form form-horizontal" id="base-form" enctype="multipart/form-data" action="editmydata" method="post">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">账号：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${userinfo.username }
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">账号所属：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<c:choose>
							<c:when test="${userinfo.role == 2}">后台管理员</c:when>
							<c:when test="${userinfo.role == 3}">业务员</c:when>
							<c:when test="${userinfo.role == 4}">主播代理商</c:when>
						</c:choose>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">真实姓名：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${userinfo.realname }
					</div>
				</div>
				<%-- <div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">昵称：</label>
					<div class="formControls col-xs-2 col-sm-2" data-name="nickname">
						${userinfo.nickname }
					</div>
				</div> --%>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">身份证号码：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${userinfo.cardid }
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">电话号码：</label>
					<div class="formControls col-xs-2 col-sm-2" data-name="telno">
						${userinfo.telno }
					</div>
				</div>
				
				<c:if test="${userinfo.role == 4}">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2">房间号：</label>
						<div class="formControls col-xs-8 col-sm-9">
							${userinfo.cid }
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2">房间密码：</label>
						<div class="formControls col-xs-3 col-sm-3" data-name="cidpassword">
							${userinfo.cidpassword }
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2">网易云token：</label>
						<div class="formControls col-xs-8 col-sm-9">
							${userinfo.tokenid }
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2">头像：</label>
						<div class="formControls col-xs-8 col-sm-9" data-icon="iconurl">
							<img src="${userinfo.iconurl }" style="width: 128px;">
						</div>
					</div>
				</c:if>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">注册时间：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${userinfo.registertime }
					</div>
				</div>
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<button class="btn btn-primary radius edit" type="button"><i class="Hui-iconfont">&#xe6df;</i> 修改</button>
						<button class="btn btn-primary radius hidden save" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
						<button class="btn btn-default radius hidden cancel" type="button"><i class="Hui-iconfont">&#xe66b;</i> 取消</button>
					</div>
				</div>
			</form>
		</div>
		<div class="tabCon">
			<form class="form form-horizontal" id="pwd-form" action="editadminpwd" method="post">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">原密码：</label>
					<div class="formControls col-xs-2 col-sm-2">
						<input type="password" class="input-text" name="oldpwd" >
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">新密码：</label>
					<div class="formControls col-xs-2 col-sm-2">
						<input type="password" class="input-text" id="newpwd" name="newpwd" >
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">确认密码：</label>
					<div class="formControls col-xs-2 col-sm-2">
						<input type="password" class="input-text" name="password" >
					</div>
				</div>
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<button class="btn btn-primary radius change" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	$("#tab-system").Huitab({
		index:0
	});
	//进入编辑模式
	$("button.edit").click(function() {
		var prev = $(this).parents('.row').prevAll();
		$.each(prev, function(k, v) {
			var val = $.trim($(v).find("div").text());
			var name = $(v).find("div").attr("data-name");
			if(name == undefined) return;
			//绑定原数据
			$(v).find("div").data('data', val);
			$(v).find("div").html('<input type="text" class="input-text" name="'+ name +'" value="'+ val +'">');
		});
		
		var icon = $('#base-form div[data-icon="iconurl"]');
		if(icon.length > 0){
			icon.append(
					'<div style="margin-top: 10px;">'+
					'	<span class="btn-upload form-group">'+
					'		<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请添加附件！" style="width:200px">'+
					'		<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传头像</a>'+
					'		<input type="file" multiple name="avator" class="input-file">'+
					'	</span>'+
					'</div>');
		}
		$(this).addClass('hidden');
		$(this).nextAll().removeClass('hidden');
	});
	//退出编辑模式
	$("button.cancel").click(function() {
		var prev = $(this).parents('.row').prevAll();
		$.each(prev, function(k, v) {
			var name = $(v).find("div").attr("data-name");
			if(name == undefined) return;
			//获取原数据
			var val = $(v).find("div").data('data');
			$(v).find("div").html(val);
		});
		$(this).addClass('hidden');
		$(this).prev().addClass('hidden');
		$(this).prev().prev().removeClass('hidden');
		var icon = $('#base-form div[data-icon="iconurl"]');
		if(icon.length > 0){
			icon.find("div").remove();
		}
	});
	//保存信息
	$("button.save").click(function() {
		var obj = $(this);
		$("#base-form").validate({
			rules:{
				telno:{
					required: false,
					isMobile: true
				},
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				obj.addClass('disabled');
				$(form).ajaxSubmit({
					success: function(data) {
		                if(data.code == 200){
		                	window.location.reload();
		                }else{
		                	obj.removeClass('disabled');
		                	layer.msg(data.msg,{icon: 5,time:1500});
		                }
		            }
				});
			}
		});
	});
	
	//修改密码 
	$("#pwd-form button.change").click(function() {
		var obj = $(this);
		$("#pwd-form").validate({
			rules:{
				oldpwd:{
					required: true,
				},
				newpwd:{
					required: true,
				},
				password:{
					required: true,
					equalTo: '#newpwd'
				},
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				obj.addClass('disabled');
				$(form).ajaxSubmit({
					success: function(data) {
						obj.removeClass('disabled');
		                if(data.code == 200){
		                	layer.msg('密码已修改',{icon: 6,time:1500});
		                	$("#pwd-form").find('input').val("");
		                }else{
		                	layer.msg(data.msg,{icon: 5,time:1500});
		                }
		            }
				});
			}
		});
	});
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
