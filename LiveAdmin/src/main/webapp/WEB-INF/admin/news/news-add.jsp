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

<title>新增资讯</title>
</head>
<body>
<c:if test="${model == null }">
	<section class="container-fluid page-404 minWP text-c">
		<p class="error-description">没有可用的资讯模块，无法添加资讯信息</p>
		<p class="error-info">您可以：
			<a href="newsmodel" class="c-primary ml-20">去添加模块  &gt;</a>
		</p>
	</section>
</c:if>
<c:if test="${model != null }">
<article class="page-container">
	<form action="addnews.do" method="post" enctype="multipart/form-data" class="form form-horizontal" id="form">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">模块：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="select-box" style="width: auto;">
					<select name="informationmodelid" class="select">
						<c:forEach var="item" items="${model}">
							<option value="${item.informationid }">${item.newskindname }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>资讯标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="请输入资讯标题" name="infomationtitle">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">指定讲师：</label>
			<div class="formControls col-xs-8 col-sm-9">
				代理商：
				<span class="select-box" style="width: auto;">
					<select class="select" id="agent">
						<option value="">不指定</option>
						<c:forEach var="item" items="${agent}">
							<option value="${item.id }">${item.name }</option>
						</c:forEach>
					</select>
				</span> 
				讲师：
				<span class="select-box" style="width: auto;">
					<select name="userid" class="select">
						<option value="">未指定</option>
					</select>
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">作者姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="请输入作者姓名" name="singname">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">标签：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="请输入标签" name="tag">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="descrption" class="textarea" placeholder="请输入描述信息" ></textarea>
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">缩略图：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div>
					<span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请添加附件！" style="width:200px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传图片</a>
						<input type="file" multiple name="pic" class="input-file">
					</span>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>文章内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<script id="editor" type="text/plain" style="width:100%;height:400px;"></script>
				
			</div>
		</div>
		
		<input type="text" name="content" class="hidden">
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
				<button class="btn btn-default radius" type="button"><i class="Hui-iconfont">&#xe66b;</i> 取消</button>
			</div>
		</div>
	</form>
</article>
</c:if>
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
	
	var ue = UE.getEditor('editor');
	
	//代理商变更，获取旗下讲师列表
	$("#agent").change(function() {
		var agentid = $.trim($(this).val());
		if(agentid == ''){
			//不指定代理商
			$("select[name='userid']").html('<option value="">未指定</option>');
			$("input[name='singname']").val("");
		}else{
			$.ajax({
				type: 'POST',
				url: 'findteachers',
				data: {agentid: agentid},
				success: function(data){
					if(data.code == 200){
						var str = '<option value="">未指定</option>';
						$.each(data.data, function (k, v) {
							str += '<option value="'+ v.id +'">'+ v.name +'</option>';
						})
						$("select[name='userid']").html(str);
					}else{
						$("select[name='userid']").html('<option value="">未指定</option>');
						$("input[name='singname']").val("");
					}
				}
			});
		}
	})
	//选择讲师，填充作者
	$("select[name='userid']").change(function() {
		var name = $.trim($("select[name='userid'] option:selected").text());
		if(name != '未指定') $("input[name='singname']").val(name);
		else $("input[name='singname']").val("");
	})
	
	//表单验证
	$("#form").validate({
		rules:{
			infomationtitle:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			//检查编辑区域中是否有内容
			if(!ue.hasContents()) {layer.msg("文章内容不能为空",{icon: 5,time:1500}); return false;}
			$('input[name="content"]').val(ue.getContent());
			$("input[type='submit']").addClass('disabled');
			$(form).ajaxSubmit({
				success: function(data) {
	                if(data.code == 200){
	                	var index = parent.layer.getFrameIndex(window.name);
	                	parent.$("#outflush").click(); 
	                	parent.layer.close(index);
	                }else{
	                	$("input[type='submit']").removeClass('disabled');
	                	layer.msg(data.msg,{icon: 5,time:1500});
	                }
	            }
			});
			return false;
		}
	});
	
	
	
	
	
	
	
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>