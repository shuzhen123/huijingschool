<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/h-ui.admin/css/style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/clipboard/clipboard.min.js"></script>
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>二维码推广管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 
<c:choose>
	<c:when test="${popu.type == 1 }">二维码推广管理</c:when>
	<c:when test="${popu.type == 2 }">直播间推广管理</c:when>
</c:choose>
</nav>
<div class="page-container">

<div class="row">
	<c:if test="${popu.type == 1 }">
		<div class="col-md-4 col-md-offset-1" style="text-align: center;">
			<img src="${popu_wx.qrurl }" alt="" class="thumbnail"  style="width: 100%;">
		</div>
		<div class="col-md-4 col-md-offset-1" style="text-align: center;">
			<img src="${popu.qrurl }" alt="" class="thumbnail"  style="width: 100%;">
		</div>
	</c:if>
	<c:if test="${popu.type == 2 }">
		<div class="col-md-4 col-md-offset-4" style="text-align: center;">
			<img src="${popu.qrurl }" alt="" class="thumbnail"  style="width: 100%;">
		</div>
	</c:if>
</div>
<div style="clear:both;"></div>
<div class="row">
	<c:if test="${popu.type == 1 }">
		<div class="col-md-4 col-md-offset-1" style="text-align: center;margin-top: 10px;">
			<a href="downloadqr?type=3" class="btn btn-primary">下载微信推广二维码</a>
		</div>
		<div class="col-md-4 col-md-offset-1" style="text-align: center;margin-top: 10px;">
			<a href="downloadqr?type=${popu.type }" class="btn btn-primary">下载二维码</a>
			<a href="javascript:;" class="btn btn-success qr-update">更新二维码</a>
			<form action="addcourse" method="post" class="form form-horizontal" id="form">
				<div class="row cl">
					<div class="formControls col-xs-9 col-sm-9">
						<input type="text" class="input-text" id="popuurl" readonly="readonly" value="${popu.popuurl }">
					</div>
					<div class="formControls col-xs-3 col-sm-3">
						<a href="javascript:;" class="btn btn-success copy-btn" data-clipboard-target="#popuurl">复制推广链接</a>
					</div>
				</div>
				<c:if test="${popu.type == 1 }">
				<div class="row cl">
					<div class="formControls col-xs-9 col-sm-9">
						<input type="text" class="input-text" id="invitecode" readonly="readonly" value="${popu.invitecode }">
					</div>
					<div class="formControls col-xs-3 col-sm-3">
						<a href="javascript:;" class="btn btn-success copy-btn" data-clipboard-target="#invitecode">复&nbsp;制&nbsp;邀&nbsp;请&nbsp;码</a>
					</div>
				</div>
				</c:if>
			</form>
		</div>
	</c:if>
	<c:if test="${popu.type == 2 }">
		<div class="col-md-4 col-md-offset-4" style="text-align: center;margin-top: 10px;">
			<a href="downloadqr?type=${popu.type }" class="btn btn-primary">下载二维码</a>
			<a href="javascript:;" class="btn btn-success qr-update">更新二维码</a>
			<form action="addcourse" method="post" class="form form-horizontal" id="form">
				<div class="row cl">
					<div class="formControls col-xs-9 col-sm-9">
						<input type="text" class="input-text" id="popuurl" readonly="readonly" value="${popu.popuurl }">
					</div>
					<div class="formControls col-xs-3 col-sm-3">
						<a href="javascript:;" class="btn btn-success copy-btn" data-clipboard-target="#popuurl">复制推广链接</a>
					</div>
				</div>
				<c:if test="${popu.type == 1 }">
				<div class="row cl">
					<div class="formControls col-xs-9 col-sm-9">
						<input type="text" class="input-text" id="invitecode" readonly="readonly" value="${popu.invitecode }">
					</div>
					<div class="formControls col-xs-3 col-sm-3">
						<a href="javascript:;" class="btn btn-success copy-btn" data-clipboard-target="#invitecode">复&nbsp;制&nbsp;邀&nbsp;请&nbsp;码</a>
					</div>
				</div>
				</c:if>
			</form>
		</div>
	</c:if>
</div>



</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> 
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->

<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">

$(function(){
	var type = ${popu.type };
	//更新二维码1√，2x, 3?, 4lock, 5哭脸， 6笑脸， 7！
	$("a.qr-update").click(function(e) {
		$.ajax({
            url: 'updateqr',
            type: "GET",
            data: {type: type},
            beforeSend: function() {
				//禁止按钮点击
            	$("a.qr-update").addClass('disabled');
            	layer.load(2);
			},
            success: function (result) {
            	if(result.code == 200){
            		if(type == 1){
            			$($("img")[1]).attr('src', result.data.qrurl);
	            		$("input#popuurl").val(result.data.popuurl);
            		}else{
	            		$("img").attr('src', result.data.qrurl);
	            		$("input").val(result.data.popuurl);
            		}
					layer.msg('操作成功！',{icon: 1,time:2000});
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            },
            complete: function() {
            	$("a.qr-update").removeClass('disabled');
            	layer.close(layer.load(2));
			}
		})
	})
	
	//复制推广链接
	var clipboard = new Clipboard('.copy-btn');
	clipboard.on('success', function(e) {
		layer.msg('内容复制成功！',{icon: 1,time:2000});
	});

	clipboard.on('error', function(e) {
	    alert("当前浏览器不支持复制功能，请手动复制文本框中的内容！");
	});
});

</script> 
</body>
</html>