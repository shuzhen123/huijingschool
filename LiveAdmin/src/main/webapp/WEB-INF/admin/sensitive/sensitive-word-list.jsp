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

<title>敏感文字管理</title>
</head>
<body>

<article class="page-container">
	<div class="row cl" >
		<div class="col-xs-12 col-sm-12">
			<div class="panel panel-default useing">
				<div class="panel-header">敏感文字列表</div>
				<div class="panel-body">
					<c:forEach var="item" items="${words }">
						<span class="label  badge-default radius" style="margin: 10px;">
							<a href="javascript:;" class="stop" title="停用"  data-id="${item.id }">
								<i class="Hui-iconfont" style="color: #c62b26;">&#xe631;</i>
							</a> 
							${item.name }
						</span>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-md-offset-4">
			<form class="form form-horizontal">
				<div class="row cl">
					<div class="formControls col-xs-9 col-sm-9">
						<input type="text" class="input-text" id="word">
					</div>
					<div class="formControls col-xs-3 col-sm-3">
						<a href="javascript:;" class="btn btn-success add-btn">添加敏感文字</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</article>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/ueditor/1.4.3/ueditor.all.js"> </script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
$(function(){

	//添加文字
	$(".add-btn").click(function() {
		var word = $.trim($("#word").val());
		if(word == ''){
			layer.msg('文字不能为空',{icon: 5,time:2000});
		}
		$.ajax({
			type: 'POST',
			url: 'addsensitiveword',
			data: {word: word},
			beforeSend: function() {
				$(".add-btn").addClass('disabled');
			},
			success: function(data){
				if(data.code == 200){
					$("#word").val('');
					layer.msg('添加成功!',{icon: 6,time:1000});
					var str = 
						'<span class="label  badge-default radius" style="margin: 10px;">' +
						'  <a href="javascript:;" class="stop" title="停用"  data-id="'+ data.data.id +'">' +
						'    <i class="Hui-iconfont" style="color: #c62b26;">&#xe631;</i>' +
						'  </a>' + data.data.name +
						'</span>';
					$(".panel-body").append(str);
				}else{
					layer.msg(data.msg,{icon: 5,time:1500});
				}
			},
			complete: function() {
				$(".add-btn").removeClass('disabled');
			}
		});	
	})
	//文字停用
	$(".panel-body").on('click', 'a.stop', function() {
		var wordid = $(this).attr('data-id')
		//权限停用
		layer.confirm('确定停用此文字？', {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type: 'POST',
				url: 'stopsensitiveword',
				data: {wordid: wordid},
				success: function(data){
					if(data.code == 200){
						layer.msg('操作成功!',{icon: 6,time:1000});
						$("a[data-id='"+ wordid +"']").parent().remove();
					}else{
						layer.msg(data.msg,{icon: 5,time:1500});
					}
				}
			});	
    	});
	})
	
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>