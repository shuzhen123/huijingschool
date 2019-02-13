<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link href="${pageContext.request.contextPath}/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/h-ui.admin/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>推广链接页面</title>
</head>
<body>
<section class="container-fluid page-404 minWP text-c">
	<c:choose>
		<c:when test="${data.code != 200 }">
			<p class="error-title"><i class="Hui-iconfont va-m" style="font-size:80px">&#xe688;</i>
				<span class="va-m"> ${data.code }</span>
			</p>
			<p class="error-description">${data.msg }</p>
		</c:when>
		<c:when test="${data.code == 200 }">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
				<thead>
					<tr class="text-c">
						<th>参数名</th>
						<th>参数值</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>类型</td>
						<td>
							<c:if test="${data.data.type == 1 }">二维码推广</c:if>
							<c:if test="${data.data.type == 2 }">直播间码推广</c:if>
						</td>
					</tr>
					<c:if test="${data.data.type == 2 }">
						<tr>
							<td>直播讲师idid</td>
							<td>${data.data.id }</td>
						</tr>
					</c:if>
					<tr>
						<td>业务员id</td>
						<td>${data.data.userid }</td>
					</tr>
					<tr>
						<td>时间戳</td>
						<td>${data.data.updatetime }</td>
					</tr>
					<tr>
						<td>token</td>
						<td>${data.data.checkcode }</td>
					</tr>
				</tbody>
			</table>
		</c:when>
	</c:choose>
</section>
</body>
</html>