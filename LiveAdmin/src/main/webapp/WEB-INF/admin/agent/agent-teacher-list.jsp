<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>讲师列表</title>
</head>
<body>
<article class="page-container">
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>讲师名称</th>
					<th colspan="2">直播推荐</th>
					<th colspan="2">名师推荐</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${teaList }">
					<tr>
						<td>${item.realname }</td>
						<td>
							<c:choose>
								<c:when test="${item.trecflag == 1}">
									已推荐
								</c:when>
								<%-- <c:when test="${item.trecflag == 0}">
									未推荐
								</c:when> --%>
								<c:otherwise>
									未推荐
								</c:otherwise>
							</c:choose>
						</td>
						<td data-id="${item.userid }">
							<c:choose>
								<c:when test="${item.trecflag == 1}">
									<button class="btn btn-secondary size-MINI untrecflag" type="button">不推荐</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-success size-MINI trecflag" type="button">推荐</button>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.famousteacherflag == 1}">
									已推荐
								</c:when>
								<%-- <c:when test="${item.famousteacherflag == 0}">
									未推荐
								</c:when> --%>
								<c:otherwise>
									未推荐
								</c:otherwise>
							</c:choose>
						</td>
						<td data-id="${item.userid }">
							<c:choose>
								<c:when test="${item.famousteacherflag == 1}">
									<button class="btn btn-secondary size-MINI unfamous" type="button">不推荐</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-success size-MINI famous" type="button">推荐</button>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<script type="text/javascript">
$(function(){
	//名师不推荐
	$("body").on("click", "button.untrecflag", function() {
		var obj = $(this).parents('td');
		var teaname = obj.parent('tr').find('td:first-child').text();
    	layer.confirm('确定将名师:'+ teaname +' 设为不推荐？', {icon: 3, title:'提示'}, function(index){
    		$.ajax({
    			type: 'POST',
    			url: 'changeteacommend',
    			data: {teacherid: obj.attr("data-id"), type: 'untrecflag'},
    			success: function(data){
    				if(data.code == 200){
    					layer.msg('操作成功!',{icon: 6,time:1000});
    					obj.prev().html('未推荐');
    					obj.html('<button class="btn btn-success size-MINI trecflag" type="button">推荐</button>');
    				}else{
    					layer.msg(data.msg,{icon: 5,time:1500});
    				}
    			}
    		});
    	});
    });
	//名师推荐
	$("body").on("click", "button.trecflag", function() {
		var obj = $(this).parents('td');
		var teaname = obj.parent('tr').find('td:first-child').text();
    	layer.confirm('确定将名师:'+ teaname +' 设为推荐？', {icon: 3, title:'提示'}, function(index){
    		$.ajax({
    			type: 'POST',
    			url: 'changeteacommend',
    			data: {teacherid: obj.attr("data-id"), type: 'trecflag'},
    			success: function(data){
    				if(data.code == 200){
    					layer.msg('操作成功!',{icon: 6,time:1000});
    					obj.prev().html('已推荐');
    					obj.html('<button class="btn btn-secondary size-MINI untrecflag" type="button">不推荐</button>');
    				}else{
    					layer.msg(data.msg,{icon: 5,time:1500});
    				}
    			}
    		});
    	});
    });
	//直播不推荐
	$("body").on("click", "button.unfamous", function() {
		var obj = $(this).parents('td');
		var teaname = obj.parent('tr').find('td:first-child').text();
    	layer.confirm('确定将名师:'+ teaname +' 的直播设为不推荐？', {icon: 3, title:'提示'}, function(index){
    		$.ajax({
    			type: 'POST',
    			url: 'changeteacommend',
    			data: {teacherid: obj.attr("data-id"), type: 'unfamous'},
    			success: function(data){
    				if(data.code == 200){
    					layer.msg('操作成功!',{icon: 6,time:1000});
    					obj.prev().html('未推荐');
    					obj.html('<button class="btn btn-success size-MINI famous" type="button">推荐</button>');
    				}else{
    					layer.msg(data.msg,{icon: 5,time:1500});
    				}
    			}
    		});
    	});
    });
	//直播推荐
	$("body").on("click", "button.famous", function() {
		var obj = $(this).parents('td');
		var teaname = obj.parent('tr').find('td:first-child').text();
    	layer.confirm('确定将名师:'+ teaname +' 的直播设为推荐？', {icon: 3, title:'提示'}, function(index){
    		$.ajax({
    			type: 'POST',
    			url: 'changeteacommend',
    			data: {teacherid: obj.attr("data-id"), type: 'famous'},
    			success: function(data){
    				if(data.code == 200){
    					layer.msg('操作成功!',{icon: 6,time:1000});
    					obj.prev().html('已推荐');
    					obj.html('<button class="btn btn-secondary size-MINI unfamous" type="button">不推荐</button>');
    				}else{
    					layer.msg(data.msg,{icon: 5,time:1500});
    				}
    			}
    		});
    	});
    });
});
</script> 
</body>
</html>