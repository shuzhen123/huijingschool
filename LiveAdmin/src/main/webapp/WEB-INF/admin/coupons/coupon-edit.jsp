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

<title>卡券修改</title>
</head>
<body>
<article class="page-container">
	<form action="editcoupon" method="post" class="form form-horizontal" id="form">
		<input type="text" class="hidden" value="${coupon.id }" name="id">
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-4">卡券名称：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" value="${coupon.cpname }" placeholder="请填写卡券名称" name="cpname">
			</div>
		</div>
		<c:choose>
			<c:when test="${coupon.type == 1 }">
				<!-- 体验券 -->
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-4">体验天数：</label>
					<div class="formControls col-xs-4 col-sm-4">
						<input type="text" class="input-text" value="${coupon.servicedays }" placeholder="请填写体验天数" name="servicedays">
					</div>
				</div>
			</c:when>
			<c:when test="${coupon.type == 2 }">
				<!-- 代金券 -->
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-4">面值（元）：</label>
					<div class="formControls col-xs-4 col-sm-4">
						<input type="text" class="input-text" value="${coupon.price }" placeholder="请填写面值" name="price">
					</div>
				</div>
			</c:when>
		</c:choose>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-4">有效时长：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" value="${coupon.validity }" placeholder="请填写有效时长" name="validity">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-4">卡券状态：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<span class="select-box" style="width:auto;">
					<select class="select" name="entkbn" size="1">
						<option value="0" <c:if test="${coupon.entkbn==0}">selected</c:if> >使用中</option> 
						<option value="1" <c:if test="${coupon.entkbn==1}">selected</c:if> >失效</option> 
					</select>
				</span>
			</div>
		</div>
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-8 col-xs-offset-4 col-sm-offset-4">
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
			cpname:{
				required: true
			},
			price:{
				required: true,
				isPrice: true,
				min: '0.01'
				
			},
			servicedays:{
				required: true,
				isDay: true
			},
			validity:{
				required: true,
				isDay: true
			}
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