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
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>基础数据管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 基础数据管理</nav>
<div class="page-container">
	<div class="row">
		<div class="col col-md-3">
			<div class="panel panel-primary">
				<div class="panel-header">基础信息管理</div>
				<div class="panel-body info">
					<input class="btn radius btn-secondary btn-block" type="button" data-href="basedatapage.do?type=intro" value="公司简介">
					<input class="btn radius btn-secondary btn-block mt-10" type="button" data-href="basedatapage.do?type=protocol" value="服务协议">
					<input class="btn radius btn-secondary btn-block mt-10" type="button" data-href="basedatapage.do?type=other" value="其它">
				</div>
			</div>
		</div>
		<div class="col col-md-4">
			<div class="panel panel-primary">
				<div class="panel-header">联系信息管理</div>
				<div class="panel-body">
					<div class="form form-horizontal" >
						<div class="row cl">
							<label class="form-label col-xs-3 col-sm-3">联系我们电话：</label>
							<div class="formControls col-xs-6 col-sm-6">
								<input type="text" class="input-text" value="${contact_phone }" name="contact">
							</div>
							<div class="formControls col-xs-2 col-sm-2">
								<button class="btn btn-success contact" >修改</button>
							</div>
						</div>
						<div class="row cl">
							<label class="form-label col-xs-3 col-sm-3">监督电话：</label>
							<div class="formControls col-xs-6 col-sm-6">
								<input type="text" class="input-text" value="${supervision_phone }" name="supervision">
							</div>
							<div class="formControls col-xs-2 col-sm-2">
								<button class="btn btn-success supervision" >修改</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
$(function(){
    //打开新窗口
    $("div.info .btn").click(function() {
    	var href = $(this).attr('data-href');
    	parent.$("#min_title_list").find('span[data-href="'+ href +'"]').parent().remove();
    	parent.$("#iframe_box iframe[src='"+ href +"']").parent().remove();
		creatIframe(href, $(this).val())
    })
    
    //联系我们电话修改
    $("button.contact").click(function(e) {
		$.ajax({
            url: 'changecompanyphone',
            type: "POST",
            data: {phone: $.trim($("input[name='contact']").val()),type: "contact"},
            success: function (result) {
            	if(result.code == 200){
					layer.msg('操作成功！',{icon: 1,time:2000});
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
		})
	})
    //监督电话修改
    $("button.supervision").click(function(e) {
		$.ajax({
            url: 'changecompanyphone',
            type: "POST",
            data: {phone: $.trim($("input[name='supervision']").val()),type: "supervision"},
            success: function (result) {
            	if(result.code == 200){
					layer.msg('操作成功！',{icon: 1,time:2000});
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