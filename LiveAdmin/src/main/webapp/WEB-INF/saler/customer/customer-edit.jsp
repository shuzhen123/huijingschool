<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--_meta 作为公共模版分离出去-->
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

<title></title>
</head>
<body>
<article class="page-container">
	<form action="${pageContext.request.contextPath}/saler/edituser" method="post" enctype="multipart/form-data" class="form form-horizontal" id="form">
	
		<input type="text" class="hidden" value="${userInfo.userid }" name="userid">
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">手机号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${userInfo.telno }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${userInfo.password }" placeholder="请填写密码" name="password">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">昵称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${userInfo.nickname }" placeholder="请填写昵称" name="nickname">
			</div>
		</div>
		<!-- --------------- -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">用户姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${userInfo.realname }" placeholder="请填写名称" name="realname">
			</div>
		</div>
		<!-- -用户性别-- -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">用户性别：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="sex" size="1" id = "sex" >
						<option value="0" <c:if test="${userInfo.sex == 0 }">selected</c:if>>未填写</option>
						<option value="1"  <c:if test="${userInfo.sex == 1 }">selected</c:if>>男</option>
						<option value="2"  <c:if test="${userInfo.sex == 2 }">selected</c:if>>女</option>
					</select>
				</span>	
			</div>
		</div> 
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">资源类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="restype" size="1" id = "restype" >
						<option value="x" <c:if test="${userInfo.restype == 'x' }">selected</c:if>>x</option>
						<option value="y" <c:if test="${userInfo.restype == 'y' }">selected</c:if>>y</option>
						<option value="z" <c:if test="${userInfo.restype == 'z' }">selected</c:if>>z</option>
					</select>
				</span>	
			</div>
		</div>
				
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">所在省份：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${userInfo.prov }" placeholder="请填写省市" name="prov">
			</div>
		</div>
		
		<!-- --------------- -->
		<!-- -用户等级-- -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">用户等级：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="levelid" size="1">
						<option value="">无等级</option>
						<c:forEach var="item" items="${levels }">
							<option value="${item.id }" <c:if test="${item.id==userInfo.levelid}">selected</c:if> >${item.name }</option> 
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">头像：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img src="${userInfo.iconurl }"  style="border-radius: 50%;margin-bottom: 10px; width: 100px; height: 100px;"/>
				<div>
					<span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请添加附件！" style="width:200px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传头像</a>
						<input type="file" multiple name="avator" class="input-file">
					</span>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">用户备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="remark" cols="" rows="3" class="textarea"  placeholder="请填写备注" style="height: auto;">${userInfo.remark }</textarea>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>

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
	
	$("#form").validate({
		rules:{
			username:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$("input[type='submit']").addClass('disabled');
			$(form).ajaxSubmit({
				success: function(data) {
	                if(data.code == 200){
	                	var index = parent.layer.getFrameIndex(window.name);
	                	parent.$("#outflush").click(); 
	                	parent.layer.close(index);
	                }else{
	                	$("input[type='submit']").removeClass('disabled');
	                	layer.msg(data.msg,{icon: 5,time:1500});
	                }
	            }
			});
		}
	});
});
</script> 
</body>
</html>