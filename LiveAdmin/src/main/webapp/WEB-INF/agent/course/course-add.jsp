<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<title>课程添加</title>
</head>
<body>
<article class="page-container">
	<form action="addcourse" method="post" class="form form-horizontal" id="form">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="请填写课程名称" name="coursename">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程分类：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="coursetypecode" size="1">
						<c:forEach var="item" items="${coursekind }">
							<option value="${item.id }" <c:if test="${item.id==course.coursetypecode}">selected</c:if> >${item.name }</option> 
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width: auto;">
					<select class="select" name="coursekind" size="1">
						<!-- 1：免费直播课程2：vip实战直播课程3：精品课4：私教课 -->
						<option value="3">精品课</option>
						<option value="4">私教课</option>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程限制：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:100px;">
					<select class="select" name="courselimit" size="1">
						<option value="1">免费</option> 
						<option value="2">付费</option>
					</select>
				</span>
			</div>
		</div>
		<div class="price hidden">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">课程金额：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" placeholder="请填写课程金额" name="coursemoney">
				</div>
			</div>
			<!-- <div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">折扣率：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" placeholder="请填写折扣率" name="discountrate">
				</div>
			</div> -->
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程展示图：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div>
					<span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请添加附件！" style="width:200px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传展示图</a>
						<input type="file" name="courseimg" class="input-file">
					</span>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="coursedes" class="textarea"  placeholder="请填写课程描述">${course.coursedes}</textarea>
				
			</div>
		</div>
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	//课程免付费切换
	$('select[name="courselimit"]').change(function() {
		var val = $(this).val();
		if(val == 1){
			//免费
			$("div.price").addClass("hidden");
		}else{
			//付费
			$("div.price").removeClass("hidden");
		}
	})
	
	$("#form").validate({
		rules:{
			coursename:{
				required: true
			},
			coursemoney:{
				required: true,
				isPrice: true
			},
			/* discountrate:{
				required: false,
				isDiscountrate: true,
				max: '1.00',
				min: '0.001'
			} */
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
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
		}
	});
});
</script> 
</body>
</html>