<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<title>直播课程修改</title>
</head>
<body>
<article class="page-container">
	<form action="${pageContext.request.contextPath}/tea/editlivecourse" method="post" enctype="multipart/form-data" class="form form-horizontal" id="form">
		<input type="text" class="hidden" value="${course.courseid }" name="courseid">
		<input type="text" class="hidden" value="${course.auth }" name="auth">
		<c:if test="${course.auth == 3 }">
			<div class="row cl" style="color: red;">
				<label class="form-label col-xs-4 col-sm-3" style="color: red;">提示：</label>
				<div class="formControls col-xs-8 col-sm-9">
					当前课程审核未通过，原因：${course.cause }<br/>
					修改提交后可再次进入等待审核状态
				</div>
			</div>
		</c:if>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${course.coursename }" placeholder="请填写课程名称" name="coursename">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width: auto;">
					<select class="select" name="coursekind" size="1">
						<!-- 1：免费直播课程2：vip实战直播课程 --> 
						<option value="1" <c:if test="${course.coursekind == 1 }">selected</c:if> >免费直播</option>
						<option value="2" <c:if test="${course.coursekind == 2 }">selected</c:if> >vip实战直播</option>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程等级：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="coursetypecode" size="1">
						<c:forEach var="item" items="${kind }">
							<option value="${item.id }" <c:if test="${item.id==course.coursetypecode}">selected</c:if> >${item.levelname }（${item.money }元）</option> 
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程状态：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${course.liveflag == 1}">正在直播</c:when>
					<c:when test="${course.liveflag == 2}">往期直播</c:when>
					<c:when test="${course.liveflag == 3}">预告直播</c:when>
				</c:choose>
				<input type="text" class="hidden" value="${course.liveflag }" name="liveflag">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">直播时间：</label>
			<c:choose>
				<c:when test="${course.liveflag == 2}">
					<!-- 往期直播 -->
					<div class="formControls col-xs-3 col-sm-3" style="text-align: right;">
						<fmt:formatDate value="${course.starttime }" pattern="yyyy-MM-dd HH:mm"/>
					</div>
					<div class="formControls col-xs-1 col-sm-1" style="text-align: center;"><i class="Hui-iconfont">&#xe606;</i></div>
					<div class="formControls col-xs-3 col-sm-3">
						<fmt:formatDate value="${course.endtime }" pattern="yyyy-MM-dd HH:mm"/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="formControls col-xs-3 col-sm-3">
						<input type="text" value="<fmt:formatDate value="${course.starttime }" pattern="yyyy-MM-dd HH:mm"/>" onfocus="WdatePicker({minDate:'%y-%M-%d %H:%m', dateFmt:'yyyy-MM-dd HH:mm'})" id="datemin" name="starttime" class="input-text Wdate" placeholder="直播开始时间">
					</div>
					<div class="formControls col-xs-1 col-sm-1" style="text-align: center;"><i class="Hui-iconfont">&#xe606;</i></div>
					<div class="formControls col-xs-3 col-sm-3">
						<input type="text" value="<fmt:formatDate value="${course.endtime }" pattern="yyyy-MM-dd HH:mm"/>" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'datemin\')}', dateFmt:'yyyy-MM-dd HH:mm'})" id="datemax" name="endtime" class="input-text Wdate" placeholder="直播结束时间">
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程审核：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<!-- 审核状态（1待审核，2审核通过，3审核失败） -->
				<c:choose>
					<c:when test="${course.auth==1}">待审核</c:when>
					<c:when test="${course.auth==2}">通过</c:when>
					<c:when test="${course.auth==3}">未通过</c:when>
				</c:choose>
			</div>
		</div>
		
		<c:if test="${course.auth != 3}">
			<c:if test="${course.liveflag != 1}">
				<div class="recommend <c:if test="${course.auth==3}">hidden</c:if> ">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">课程上下架：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<span class="select-box" style="width:auto;">
								<select class="select" name="flag" size="1">
									<option value="1" <c:if test="${course.flag==1}">selected</c:if> >上架</option> 
									<option value="2" <c:if test="${course.flag==2}">selected</c:if> >下架</option>
								</select>
							</span>
						</div>
					</div>
				</div>
			</c:if>
		</c:if>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="coursedes" class="textarea"  placeholder="请填写课程描述">${course.coursedes}</textarea>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程展示图：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img src="${course.coursepic}"  style="margin-bottom: 10px; max-width: 150px;"/>
				<div>
					<span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请添加附件！" style="width:200px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传展示图</a>
						<input type="file" name="file" class="input-file">
					</span>
				</div>
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
	//课程审核状态切换
	$('select[name="auth"]').change(function() {
		var val = $(this).val();
		if(val == 2){
			//通过
			$("div.recommend").removeClass("hidden");
		}else{
			//不通过
			$("div.recommend").addClass("hidden");
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
				max: '0.99',
				min: '0.00'
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