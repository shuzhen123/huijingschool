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
<title>直播海报管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 直播海报管理</nav>
<div class="page-container">

<div class="row">
	<div class="col-md-6" style="text-align: center;">
		<img src="${logol }" alt="" class="thumbnail l-logo" >
		<div class="" style="text-align: center;margin-top: 10px;">
			<a href="javascript:;" data-type="l" class="btn btn-success uploadbtn">上传/更改展示图</a>
		</div>
	</div>
	<div class="col-md-6" style="text-align: center;">
		<img src="${logor }" alt="" class="thumbnail r-logo">
		<div class="" style="text-align: center;margin-top: 10px;">
			<a href="javascript:;" data-type="r" class="btn btn-success uploadbtn">上传/更改展示图</a>
		</div>
	</div>
</div>
<input type="file" name="logo" id="logo" class="hidden">

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
	
	$(".uploadbtn").click(function() {
		$("#logo").trigger('click');
		$("#logo").attr('data-type', $(this).attr('data-type'));
	})
	
	//上传新二维码1√，2x, 3?, 4lock, 5哭脸， 6笑脸， 7！
	$("#logo").change(function(e) {
		var file=document.getElementById('logo').files[0];
		if(!(/^image\/\w+/.test(file.type))) {
			layer.msg('图片文件格式不正确，请上传png, jpg, jpeg, gif, bmp等格式的图片文件',{icon: 5,time:2000});
			return false;
		}
		//检测通过
        var data = new FormData(), type = $(this).attr('data-type');
		data.append('type', type);
		data.append('logo', document.getElementById('logo').files[0]);
		$.ajax({
            url: 'changelivelogo',
            type: "POST",
            data: data,
            processData: false,
            contentType: false,
            beforeSend: function() {
				//禁止按钮点击
            	$(".uploadbtn").addClass('disabled');
            	layer.load(2);
			},
            success: function (result) {
            	if(result.code == 200){
            		$("img."+ type +"-logo").attr('src', result.data);
					layer.msg('操作成功！',{icon: 1,time:2000});
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            },
            complete: function() {
            	$(".uploadbtn").removeClass('disabled');
            	layer.close(layer.load(2));
			}
		})
	})
});
</script> 
</body>
</html>