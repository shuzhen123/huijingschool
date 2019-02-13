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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/jquery-video/css/video-js.css" >
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->

<title>直播课程详情</title>
</head>
<body>
<article class="page-container">
	<div class="row">
		<div class="col-md-4">
			<form class="form form-horizontal">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">课程名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${course.coursename }
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">课程类型：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<c:choose>
							<c:when test="${course.coursekind == 1}">免费直播课程</c:when>
							<c:when test="${course.coursekind == 2}">vip实战直播课程</c:when>
						</c:choose>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">课程等级：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${course.coursetypename }
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">点赞数量：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${course.thumbsupcount}
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">课程上下架：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<c:choose>
							<c:when test="${course.flag == 1}">上架</c:when>
							<c:when test="${course.flag == 0}">下架</c:when>
						</c:choose>
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
					</div>
				</div>
				<c:if test="${course.liveflag != 2}">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">直播时间：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<fmt:formatDate value="${course.starttime }" pattern="yyyy-MM-dd HH:mm"/> <i class="Hui-iconfont">&#xe606;</i> <fmt:formatDate value="${course.endtime }" pattern="yyyy-MM-dd HH:mm"/>
						</div>
					</div>
				</c:if>
				
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">审核状态：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<c:choose>
							<c:when test="${course.auth == 1}">待审核</c:when>
							<c:when test="${course.auth == 2}">审核通过</c:when>
							<c:when test="${course.auth == 3}">审核失败</c:when>
						</c:choose>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">课程描述：</label>
					<div class="formControls col-xs-8 col-sm-9">
						${course.coursedes}
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">课程展示图：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<img src="${course.coursepic}" style="max-width: 150px;">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">创建时间：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<fmt:formatDate value="${course.createtime }" pattern="yyyy-MM-dd HH:mm"/>
					</div>
				</div>
			</form>
		</div>
		<div class="col-md-8">
			<c:choose>
				<c:when test="${course.auth == 1 }">
					直播课程待审核，无法开启直播
				</c:when>
				<c:when test="${course.auth == 2 }">
					<!-- 1正在直播，2往期直播，3预告直播 -->
					<c:if test="${course.liveflag == 1 }">
						
					</c:if>
					<c:if test="${course.liveflag == 2 }">
						<video id="my-player" class="video-js vjs-default-skin vjs-big-play-centered"  controls preload="auto"  poster="${course.coursepic }" width='100%' height='100%'>
							<source src="${course.videourl }" type="video/mp4">
							<p class="vjs-no-js">
							   	播放视频需要启用 JavaScript，推荐使用<a href="http://videojs.com/html5-video-support/" target="_blank">支持HTML5</a>的浏览器访问。
							</p>
						</video>
					</c:if>
					<c:if test="${course.liveflag == 3 }">
						直播未开始
					</c:if>
				</c:when>
				<c:when test="${course.auth == 3 }">
					直播课程审核失败，无法开启直播
				</c:when>
			</c:choose>
		</div>
	</div>
	
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-video/js/video.min.js"></script> 
<script type="text/javascript">
$(function(){
	$(".go-back").click(function() {
		var index = parent.layer.getFrameIndex(window.name);
    	parent.layer.close(index);
	});
	//播放器就绪
	var player = videojs('my-player', {
			controlBar: {
				captionsButton: false,
				chaptersButton : false,
				liveDisplay:false,
				playbackRateMenuButton: false,
				subtitlesButton:false,
				fullscreenToggle: true
			},
			aspectRatio: '16:9',
		}, function onPlayerReady() {
			videojs.log('Your player is ready!');
			this.height($("body").height() * 0.5);
		   }
		);
	
	//视频播放
	$("body").on('click', 'li .picbox a', function() {
		var videourl = $(this).parents('li').attr('data-video');
		var imgurl = $(this).find('img').attr('src');
		player.poster(imgurl);
		$(".lb-caption").html($(this).parents('li').attr('data-name'));
		player.src(videourl);
		$(".video-area").fadeToggle('fast');
	})
});
</script> 
</body>
</html>