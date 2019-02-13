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

<title>用户权限管理</title>
</head>
<body>
<article class="page-container" data-id="${userid }">
	<div class="row cl" >
		<div class="col-xs-12 col-sm-6">
			<div class="panel panel-default useing">
				<div class="panel-header">已拥有权限列表</div>
				<div class="panel-body">
					<c:if test="${role.own != null }">
						<c:forEach var="item" items="${role.own }">
							<span class="label  badge-default radius" style="margin: 10px;">
								<a href="javascript:;" class="cancel" title="移除"  data-id="${item.jurisdictionid }">
									<i class="Hui-iconfont" style="color: #c62b26;">&#xe631;</i>
								</a> 
								${item.jurisdictionname }
							</span>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-6">
			<div class="panel panel-default unused">
				<div class="panel-header">未拥用权限列表</div>
				<div class="panel-body">
					<c:if test="${role.none != null }">
						<c:forEach var="item" items="${role.none }">
							<span class="label  badge-default radius" style="margin: 10px;">
								<a href="javascript:;" class="auth" title="授权"  data-id="${item.jurisdictionid }">
									<i class="Hui-iconfont" style="color: #429842;">&#xe615;</i>
								</a> 
								${item.jurisdictionname }
							</span>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	
</article>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/messages_zh.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/ueditor/1.4.3/ueditor.all.js"> </script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	//撤销权限
	$(".useing").on('click', 'a.cancel', function() {
		var roleid = $(this).attr('data-id')
		layer.confirm('确定移除此管理员的权限？', {icon: 3, title:'提示'}, function(index){
			auth(roleid, "cancel");
    	});
	})
	//授权
	$(".unused").on('click', 'a.auth', function() {
		var roleid = $(this).attr('data-id')
		layer.confirm('确定将此权限授予该管理员？', {icon: 3, title:'提示'}, function(index){
			auth(roleid, "auth");
    	});
	})
	//发送权限修改数据
	var auth = function(roleid, action) {
		$.ajax({
			type: 'POST',
			url: 'authrole.do',
			data: {userid: $('.page-container').attr('data-id'), roleid: roleid, action: action},
			success: function(data){
				if(data.code == 200){
					layer.msg('操作成功!',{icon: 6,time:1000});
					var obj = $('a[data-id="'+ roleid +'"]');
					if(action == 'cancel'){
						//移至停用区
						obj.parent().appendTo(".unused .panel-body");
						obj.removeClass('cancel').addClass('auth');
						obj.attr('title', '授权');
						obj.html('<i class="Hui-iconfont" style="color: #429842;">&#xe615;</i>');
					}else{
						//移至启用区
						obj.parent().appendTo(".useing .panel-body");
						obj.removeClass('auth').addClass('cancel');
						obj.attr('title', '移除');
						obj.html('<i class="Hui-iconfont" style="color: #c62b26;">&#xe631;</i>');
					}
					
				}else{
					layer.msg(data.msg,{icon: 5,time:1500});
				}
			},
			error:function(data) {
				layer.msg(data.msg,{icon: 5,time:1500});
			},
		});	
	}
	
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>