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

<title>权限管理</title>
</head>
<body>

<div id="addrole" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加新权限</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form action="addadminrole.do" method="post" class="form form-horizontal" id="add-form">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">权限名称：</label>
						<div class="formControls col-xs-8 col-sm-6">
							<input type="text" class="input-text" placeholder="请输入权限名称" name="rolename">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">权限资源：</label>
						<div class="formControls col-xs-8 col-sm-6">
							<input type="text" class="input-text" placeholder="请输入权限资源" name="rolevalue">
						</div>
					</div>
					
					<div class="row cl">
						<div class="col-xs-8 col-sm-6 col-xs-offset-4 col-sm-offset-3">
							<button class="btn btn-default radius"  data-dismiss="modal" aria-hidden="true"><i class="Hui-iconfont">&#xe66b;</i> 取消</button>
							<button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
						</div>
					</div>
				</form>
			</div>
			<!-- <div class="modal-footer">
				<button class="btn btn-primary">确定</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div> -->
		</div>
	</div>
</div>

<article class="page-container">
	<!-- <div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">
			<a href="javascript:;" class="btn btn-primary radius"  data-toggle="modal" data-target="#addrole">
				<i class="Hui-iconfont">&#xe600;</i> 添加新权限
			</a>
		</span> 
	</div> -->
	<div class="row cl" >
		<div class="col-xs-12 col-sm-6">
			<div class="panel panel-default useing">
				<div class="panel-header">使用中权限列表</div>
				<div class="panel-body">
					<c:if test="${role != null }">
						<c:forEach var="item" items="${role }">
							<c:if test="${item.flag == 1 }">
								<span class="label  badge-default radius" style="margin: 10px;">
									<a href="javascript:;" class="stop" title="停用"  data-id="${item.jurisdictionid }">
										<i class="Hui-iconfont" style="color: #c62b26;">&#xe631;</i>
									</a> 
									${item.jurisdictionname }
								</span>
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-6">
			<div class="panel panel-default unused">
				<div class="panel-header">已禁用权限列表</div>
				<div class="panel-body">
					<c:if test="${role != null }">
						<c:forEach var="item" items="${role }">
							<c:if test="${item.flag == 0 }">
								<span class="label  badge-default radius" style="margin: 10px;">
									<a href="javascript:;" class="start" title="启用"  data-id="${item.jurisdictionid }">
										<i class="Hui-iconfont" style="color: #429842;">&#xe615;</i>
									</a> 
									${item.jurisdictionname }
								</span>
							</c:if>
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
<script type="text/javascript">
$(function(){
	//modal框隐藏清除数据
	$('#addrole').on('hidden.bs.modal', function (e) {
		$('#addrole input').val('')
	})
	
	//权限停、启用
	$(".useing").on('click', 'a.stop', function() {
		var roleid = $(this).attr('data-id')
		//权限停用
		layer.confirm('确定停用此权限？', {icon: 3, title:'提示'}, function(index){
			changestatus(roleid, 0);
    	});
	})
	$(".unused").on('click', 'a.start', function() {
		//权限启用
		var roleid = $(this).attr('data-id')
		layer.confirm('确定启用此权限？', {icon: 3, title:'提示'}, function(index){
			changestatus(roleid, 1);
    	});
	})
	//发送状态修改数据
	var changestatus = function(roleid, status) {
		$.ajax({
			type: 'POST',
			url: 'changerolestatus.do',
			data: {roleid: roleid, status: status},
			success: function(data){
				if(data.code == 200){
					layer.msg('操作成功!',{icon: 6,time:1000});
					var obj = $('a[data-id="'+ roleid +'"]');
					if(status == 0){
						//移至停用区
						obj.parent().appendTo(".unused .panel-body");
						obj.removeClass('stop').addClass('start');
						obj.attr('title', '启用');
						obj.html('<i class="Hui-iconfont" style="color: #429842;">&#xe615;</i>');
					}else{
						//移至启用区
						obj.parent().appendTo(".useing .panel-body");
						obj.removeClass('start').addClass('stop');
						obj.attr('title', '停用');
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
	
	//添加权限
	/* $("#add-form").validate({
		rules:{
			rolename:{
				required:true,
			},
			rolevalue:{
				required:true,
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$("#add-form input[type='submit']").addClass('disabled');
			$(form).ajaxSubmit({
				success: function(data) {
	                if(data.code == 200){
	                	
	                	var str = 
	                		'<span class="label  badge-default radius" style="margin: 10px;">' +
	                		'	<a href="javascript:;" title="停用"  data-id="'+data.data+'" data-val="'+ ($("#add-form input[name='rolevalue']").val()) +'">
	                		'   	<i class="Hui-iconfont" style="color: #c62b26;">&#xe631;</i>
	                		'	</a>' + ($("#add-form input[name='rolename']").val())
	                		'</span>'
	                	$(".useing .panel-body").append(str);
	                	$('#addrole').modal('hide');
	                	layer.msg(data.msg,{icon: 6,time:2000});
	                }else{
	                	$("#add-form input[type='submit']").removeClass('disabled');
	                	layer.msg(data.msg,{icon: 5,time:2000});
	                }
	            }
			});
		}
	}); */
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>