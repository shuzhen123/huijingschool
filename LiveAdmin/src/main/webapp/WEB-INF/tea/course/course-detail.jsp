<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<title>课程详情</title>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${course.coursename }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程分类：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${course.coursetypename }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${course.coursekind == 3}">精品课</c:when>
					<c:when test="${course.coursekind == 4}">私教课</c:when>
				</c:choose>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程金额：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${course.courselimit == 1}">免费</c:when>
					<c:when test="${course.courselimit == 2}">￥ ${course.coursemoney }</c:when>
				</c:choose>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">推荐课程：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${course.recommendflag == 1}">是</c:when>
					<c:otherwise>否</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">推荐至滚动栏：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${course.bannerflag == 1}">是</c:when>
					<c:otherwise>否</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">点赞数量：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${course.thumbsupcount}
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程上下架：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${course.flag == 1}">上架</c:when>
					<c:when test="${course.flag == 0}">下架</c:when>
				</c:choose>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">审核状态：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${course.auth == 1}">待审核</c:when>
					<c:when test="${course.auth == 2}">审核通过</c:when>
					<c:when test="${course.auth == 3}">审核失败</c:when>
				</c:choose>
			</div>
		</div>
		<c:if test="${course.auth == 3}">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">审核失败原因：</label>
				<div class="formControls col-xs-8 col-sm-9">
					${course.cause }
				</div>
			</div>
		</c:if>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${course.coursedes}
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程展示图：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img src="${course.coursepic}" style="max-width: 150px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">创建时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<fmt:formatDate value="${course.createtime}" pattern="yyyy-MM-dd HH:mm"/>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
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