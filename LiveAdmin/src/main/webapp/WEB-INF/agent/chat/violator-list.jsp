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

<title>违规记录</title>
</head>
<body>
<div id="addModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">处理违规记录</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" enctype="multipart/form-data" method="post" id="form" >
					<input type="text" class="hidden" name="id">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">处理方式：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width:auto;">
								<select class="select" name="methodid" size="1">
									<c:forEach var="item" items="${method }">
										<option value="${item.id}">${item.name }</option> 
									</c:forEach>
								</select>
							</span>
						</div>
					</div>
				</form>
				
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="confirm">确定</button>
				<button class="hidden" id="confirm-video"></button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<article class="page-container">
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>时间</th>
					<th>违规内容</th>
					<th>处罚方式</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(violator) > 0}">
						<c:forEach var="item" items="${violator }">
							<tr>
								<td>
									<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm"/>
								</td>
								<td>${item.detail }</td>
								<td>
									<c:choose>
										<c:when test="${item.handlemethodid == null}">
											<button class="btn btn-danger size-MINI perdeal" data-id="${item.id }" type="button">待处理</button>
										</c:when>
										<c:otherwise>
											<c:if test="${item.type == 1 }">
												罚款 - ￥ ${item.money }
											</c:if>
											<c:if test="${item.type == 2 }">
												封号
											</c:if>
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan=3>无违规记录</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	$(".go-back").click(function() {
		var index = parent.layer.getFrameIndex(window.name);
    	parent.layer.close(index);
	});
	//弹出处理面板
	$(".perdeal").click(function() {
		var id = $(this).attr('data-id');
		$("#addModal input[name='id']").val(id);
		$("#addModal").modal('show');
	})
	
	//违规处理
	$("#addModal #confirm").click(function() {
		$.ajax({
            url: 'dealtoviolator',
            type: "POST",
            data: {
            	id: $("#addModal input[name='id']").val(), 
            	methodid: $("#addModal select[name='methodid']").val()
            },
            success: function (result) {
            	if(result.code == 200){
					layer.msg('操作成功！',{icon: 1,time:2000});
					$("#addModal").modal('hide');
					location.reload();
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
		})
	})
});
</script> 
</body>
</html>