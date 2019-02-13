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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/lightbox2/2.8.1/css/lightbox.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/jquery-video/css/video-js.css" >
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<title>图片展示</title>

<style type="text/css">
	.portfolio-area li{
		margin-right: 20px;
		margin-top: 70px;
	}
</style>
</head>
<body>

<div id="addModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加视频</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" enctype="multipart/form-data" method="post" id="form" >
					<input type="text" class="hidden" value="${courseid }" name="courseid">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频名称：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写视频名称" name="videoname">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频介绍：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写视频介绍" name="videointroduce">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频内容：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<textarea name="videocontent" class="textarea"  placeholder="请填写视频内容"></textarea>
						</div>
					</div>
					<!-- <div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频类型：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width: auto;">
								<select class="select" name="videotype" size="1">
									<option value="1" >普通视频</option> 
									<option value="2" >vip视频</option>
								</select>
							</span>
						</div>
					</div> -->
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频展示图：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div class="input-text" id="videologo"  style="width:200px;display: inline-block;line-height: 25px;overflow: hidden;"></div>
							<a href="javascript:void(0);" class="btn btn-primary radius file-btn"><i class="Hui-iconfont">&#xe642;</i> 选择展示图</a>
							<input type="file" name="logo" id="logo" class="hidden">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频文件：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div class="input-text" id="videoname" style="width:200px;display: inline-block;line-height: 25px;overflow: hidden;"></div>
							<a href="javascript:void(0);" class="btn btn-primary radius file-btn"><i class="Hui-iconfont">&#xe642;</i> 选择视频</a>
							<input type="file" name="video" id="video" class="hidden">
						</div>
					</div>
				</form>
				
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="confirm">确定</button>
				<button class="hidden" id="confirm-video"></button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<div id="editModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">修改视频</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" enctype="multipart/form-data" method="post" id="editform" >
					<input type="text" class="hidden" name="videoid">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频名称：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写视频名称" name="videoname">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频介绍：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写视频介绍" name="videointroduce">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频内容：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<textarea name="videocontent" class="textarea"  placeholder="请填写视频内容"></textarea>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频可用性：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width: auto;">
								<select class="select" name="enableflag" size="1">
									<option value="1" >可用</option> 
									<option value="2" >不可用</option>
								</select>
							</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频展示图：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div class="input-text" id="revideologo"  style="width:200px;display: inline-block;line-height: 25px;overflow: hidden;"></div>
							<a href="javascript:void(0);" class="btn btn-primary radius file-btn"><i class="Hui-iconfont">&#xe642;</i> 选择展示图</a>
							<input type="file" name="logo" id="relogo" class="hidden">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频文件：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div class="input-text" id="revideoname" style="width:200px;display: inline-block;line-height: 25px;overflow: hidden;"></div>
							<a href="javascript:void(0);" class="btn btn-primary radius file-btn"><i class="Hui-iconfont">&#xe642;</i> 选择视频</a>
							<input type="file" name="video" id="revideo" class="hidden">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<span id="progressInfo"></span>
				<button class="btn btn-primary " id="reconfirm">确定</button>
				<button class="hidden" id="reconfirm-video"></button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l"> 
			<a href="javascript:;" class="btn btn-danger disabled radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a> 
		</span> 
		<span class="r"> 
			<a href="javascript:;" class="btn btn-primary radius add-video-model">
				<i class="Hui-iconfont">&#xe600;</i> 上传视频
			</a> 
		</span> 
	</div>
	<div class="portfolio-content">
		<ul class="cl portfolio-area">
			<c:forEach var="item" items="${videos }">
				<li class="item hover" data-id="${item.videoid }" data-name="${item.videoname }" 
				data-intro="${item.videointroduce }" data-content="${item.videocontent }" data-video="${item.videourl }" data-enabled="${item.enableflag }" >
					<div class="portfoliobox">
						<input class="checkbox" type="checkbox" >
						<div class="picbox">
							<a href="javascript:;" title="点击播放视频" >
								<img src="${pageContext.request.contextPath}/${item.videoppicurl }" <c:if test="${item.enableflag == 2 }">style="filter: alpha(opacity=50);opacity: .5;"</c:if>  >
								<c:if test="${item.enableflag == 2 }"><span style="position: absolute;top: 6px;left: 25px; color: #c62b26;">视频已设为不可用</span></c:if>
							</a>
						</div>
						<div class="textbox">
							${item.videoname }
							<a class="r edit btn btn-success-outline radius size-MINI edit-video-btn"  title="编辑视频"><i class="Hui-iconfont">&#xe6df;</i></a>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="video-area " style="display: none;">
	<div style="position: absolute;top: 0;left: 0;width: 100%; height: 100%; z-index: 9999;background-color: black;filter: alpha(opacity=80);opacity: .8;"></div>
	<div class="lightbox" style="display: block; top: 100px; left: 0px;">
		<div class="lb-outerContainer" style="width: 50%; height: 100%;">
			<div class="lb-container">
				<video id="my-player" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="auto"  poster="" width='100%' height='100%'>
					<source src="" type="video/mp4">
					<p class="vjs-no-js">
					   	播放视频需要启用 JavaScript，推荐使用<a href="http://videojs.com/html5-video-support/" target="_blank">支持HTML5</a>的浏览器访问。
					</p>
				</video>
			</div>
		</div>
		<div class="lb-dataContainer" style="display: block; width: 50%;">
			<div class="lb-data">
				<div class="lb-details">
					<span class="lb-caption" style="display: inline;"></span>
				</div>
				<div class="lb-closeContainer">
					<a href="javascript:;" class="lb-close"></a>
				</div>
			</div>
		</div>
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="https://blueimp.github.io/JavaScript-MD5/js/md5.js"></script>
<!-- <script type="text/javascript" src="http://nos.netease.com/vod163/upload.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/upload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-video/js/video.min.js"></script> 
<script type="text/javascript">

$(function(){
	
	$('.file-btn').click(function() {
		$(this).next('input[type="file"]').trigger('click');
	})
	
	//监听图片文件上传
	$('input[name="logo"]').change(function(e) {
		var file = this.files[0];
		if(!(/^image\/\w+/.test(this.files[0].type))) {
			$(this).siblings('div').html('');
			$(this).val('');
			layer.msg('图片文件格式不正确，请上传png, jpg, jpeg, gif, bmp等格式的图片文件',{icon: 5,time:2000});
		}else{
			$(this).siblings('div').html(file.name);
		}
	})
	
	
	var opt = {
	    headers: {'AppKey': '${appKey }', 'Nonce': '${nonce }', 'CurTime': '${curTime }', 'CheckSum': '${checkSum }'},
	    onProgress: function(curFile){//上传进度回调处理函数
	    	$('li[data-key="'+ curFile.fileKey +'"]').find('.r').html(curFile.progress+"%");
	    	$('li[data-key="'+ curFile.fileKey +'"]').find('.sr-only').css('width', curFile.progress+"%");
	    },
	    onAdd: function(curFile){//文件添加成功的回调函数
	    	$('#'+opt.fileInputId).siblings('div').html(curFile.fileName);
	    }, 
	    noUploadFn: function(){//无文件上传时的处理函数
	    	layer.msg('您还未选择视频文件！',{icon: 7,time:2000});
	    },
	    mismatchFn: function(){//文件格式不匹配的处理函数  1√，2x, 3?, 4lock, 5哭脸， 6笑脸， 7！
	    	layer.msg('视频文件格式不支持！',{icon: 5,time:2000});
	    }
	}
	
	//打开上传model
	var addservice;
	$(".add-video-model").click(function() {
		$("#addModal").modal('show');
		if(addservice == undefined){
			opt.fileInputId = 'video', //关联文件选择输入框
			opt.fileUploadId = 'confirm-video', //上传按钮元素ID
			opt.onError = function(errObj){ //错误处理函数
		    	layer.msg('出错了！原因：'+errObj.errMsg, {icon: 7,time:2000});
		    	var key = service.fileList[0].fileKey;
		    	$('li[data-key="'+ key +'"]').find('.textbox').html($('li[data-key="'+ key +'"]').find('.textbox').text() +
				'<a class="r edit btn btn-danger-outline radius  size-MINI"  title="删除此视频"><i class="Hui-iconfont">&#xe609;</i></a>');
		    	//启用添加按钮
        		$(".add-video-model").removeClass('disabled');
		    }
			opt.onUploaded = function(curFile){//单文件上传成功的回调函数
		    	//删除进度条
		    	$('li[data-key="'+ curFile.fileKey +'"]').find('.pg-bar').remove();
		    	//更改视频状态
		    	$.ajax({
		            url: 'updatevideourl',
		            type: "POST",
		            data: {"videoid": $('li[data-key="'+ curFile.fileKey +'"]').attr('data-id'), "videourl": curFile.objectName},
		            dataType: "json",
		            success: function (result) {
		            	if(result.code == 200){
		            		$('li[data-id="'+ result.data.videoid +'"]').attr('data-video', result.data.videourl);
		            		$('li[data-id="'+ result.data.videoid +'"]').find('img').wrap('<a href="javascript:;" title="点击播放视频" ></a>');
		            		$('li[data-id="'+ result.data.videoid +'"]').find('.checkbox').removeClass('hidden');
		            		$('li[data-id="'+ result.data.videoid +'"]').find('.textbox').html($('li[data-id="'+ result.data.videoid +'"]').find('.textbox').html()+
		            				'<a class="r edit btn btn-success-outline radius  size-MINI edit-video-btn"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
		            	}else{
		            		layer.msg('视频数据更新出错，请重新上传！',{icon: 5,time:2000});
		            		$('li[data-id="'+ result.data.videoid +'"]').remove();
		            	}
		            	//启用添加按钮
		        		$(".add-video-model").removeClass('disabled');
		            }
		    	})
		    }
			addservice = Uploader(opt);
			addservice.init();
		}
		
	})
	
	//确定上传
	$("#confirm").click(function() {
		//验证输入信息 1√，2x, 3?, 4lock, 5哭脸， 6笑脸， 7！
		if($.trim($('#addModal input[name="videoname"]').val()) == ''){
			layer.msg('请填写视频名称！',{icon: 7,time:2000});
			return false;
		}
		if($.trim($("#logo").val()) == ''){
			layer.msg('请选择视频展示图！',{icon: 7,time:2000});
			return false;
		}
		
		//验证视频文件
		if(!addservice.checkedPending()){
			opt.noUploadFn();
            return false;
		}
		
		//检测通过，
		//开始上传
		var form=document.getElementById('form');
        var data =new FormData(form);
		$.ajax({
            url: 'addvideo',
            type: "POST",
            data: data,
            dataType: "json",
            processData: false,
            contentType: false,
            success: function (result) {
            	if(result.code == 200){
            		//获取视频特性
            		var data = result.data;
            		var str = 
            			'<li class="item hover" data-key="'+ addservice.fileList[0].fileKey +'" data-id="'+ data.videoid +'" data-name="'+ data.videoname +
            			'" data-intro="'+ data.videointroduce +'" data-content="'+ data.videocontent +'" data-enabled="1">' +
            			'  <div class="portfoliobox">' +
            			'    <input class="checkbox hidden" name="" type="checkbox" value="">' +
            			'    <div class="picbox">' +
            			'        <img src="'+ data.videoppicurl +'">' +
            			'    </div>' +
            			'    <div class="pg-bar">' +
            			'      <span class="r">0</span>' +
            			'      <div class="progress" style="width: 100%;">' +
            			'        <div class="progress-bar">' +
            			'          <span class="sr-only" style="width:0;"></span>' +
            			'        </div>' +
            			'      </div>' +
            			'    </div>' +
            			'    <div class="textbox">'+ data.videoname + '</div>' +
            			'  </div>' +
            			'</li>';
            		$("ul").append(str);
            		//上传视频
            		$('#addModal #confirm-video').trigger('click');
            		//禁用添加按钮
            		$(".add-video-model").addClass('disabled');
            		//清除输入框数据
            		document.getElementById('form').reset();
            		$("#videologo, #videoname").html('');
            		$('#addModal').modal('hide');
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
        })
		
	})
	
	var editservice;
	//编辑视频
	$("body").on('click', 'li a.edit-video-btn', function() {
		var p = $(this).parents('li');
		$('#editModal input[name="videoid"]').val(p.attr('data-id'));
		$('#editModal input[name="videoname"]').val(p.attr('data-name'));
		$('#editModal input[name="videointroduce"]').val(p.attr('data-intro'));
		$('#editModal textarea[name="videocontent"]').val(p.attr('data-content'));
		if(p.attr('data-enabled') == 1){
			$('#editModal select[name="enableflag"]').html('<option value="1" selected >可用</option><option value="2" >不可用</option>');
		}else{
			$('#editModal select[name="enableflag"]').html('<option value="1" >可用</option><option value="2" selected >不可用</option>');
		}
		$("#revideologo, #revideoname").html('');
		$('#editModal').modal('show');
		
		if(editservice == undefined){
			opt.fileInputId = 'revideo', //关联文件选择输入框
			opt.fileUploadId = 'reconfirm-video', //上传按钮元素ID
			opt.onError = function(errObj){ //错误处理函数
		    	layer.msg('出错了！原因：'+errObj.errMsg, {icon: 7,time:2000});
		    	var key = editservice.fileList[0].fileKey;
		    	$('li[data-key="'+ key +'"]').find('input[type="checkbox"]').removeClass('hidden');
		    	$('li[data-key="'+ key +'"]').find('.textbox').html($('li[data-key="'+ key +'"]').attr('data-name') +
		    			'<a class="r edit btn btn-success-outline radius  size-MINI edit-video-btn"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
		    }
			opt.onUploaded = function(curFile){//单文件上传成功的回调函数
		    	//删除进度条
		    	$('li[data-key="'+ curFile.fileKey +'"]').find('.pg-bar').remove();
		    	$('li[data-key="'+ curFile.fileKey +'"]').find('input[type="checkbox"]').removeClass('hidden');
		    	//更改视频状态
		    	$.ajax({
		            url: 'updatevideourl',
		            type: "POST",
		            data: {"videoid": $('li[data-key="'+ curFile.fileKey +'"]').attr('data-id'), "videourl": curFile.objectName},
		            dataType: "json",
		            success: function (result) {
		            	if(result.code == 200){
		            		$('li[data-id="'+ result.data.videoid +'"]').attr('data-video', result.data.videourl);
		            		$('li[data-id="'+ result.data.videoid +'"]').find('.textbox').html($('li[data-id="'+ result.data.videoid +'"]').attr('data-name')+
		            				'<a class="r edit btn btn-success-outline radius  size-MINI edit-video-btn"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
		            	}else{
		            		layer.msg('视频数据更新出错，请重新上传！',{icon: 5,time:2000});
		            	}
		            }
		    	})
		    }
			editservice = Uploader(opt);
			editservice.init();
		}
	})
	
	//确定上传
	$("#reconfirm").click(function() {
		//验证输入信息 1√，2x, 3?, 4lock, 5哭脸， 6笑脸， 7！
		if($.trim($('#editModal input[name="videoname"]').val()) == ''){
			layer.msg('请填写视频名称！',{icon: 7,time:2000});
			return false;
		}
		//检测通过，
		//开始上传
		var form=document.getElementById('editform');
        var data =new FormData(form);
		$.ajax({
            url: 'editvideo',
            type: "POST",
            data: data,
            dataType: "json",
            processData: false,
            contentType: false,
            success: function (result) {
            	if(result.code == 200){
            		//获取视频特性
            		var data = result.data;
            		if($("li[data-id='"+ data.videoid +"']").attr("data-enabled") != data.enableflag){
            			if(data.enableflag == 1){
	            			//不可用 -> 可用
            				$("li[data-id='"+ data.videoid +"']").find('img').removeAttr('style');
            				$("li[data-id='"+ data.videoid +"']").find('img').next('span').remove();
            			}else{
	            			//可用 -> 不可用
            				$("li[data-id='"+ data.videoid +"']").find('img').css({filter: "alpha(opacity=50)", opacity: ".5"});
            				$("li[data-id='"+ data.videoid +"']").find('img').after('<span style="position: absolute;top: 6px;left: 25px; color: #c62b26;">视频已设为不可用</span>')
            			}
            		}
            		//更新
            		$("li[data-id='"+ data.videoid +"']").attr("data-name", data.videoname);
            		$("li[data-id='"+ data.videoid +"']").attr("data-intro", data.videointroduce);
            		$("li[data-id='"+ data.videoid +"']").attr("data-content", data.videocontent);
            		$("li[data-id='"+ data.videoid +"']").attr("data-enabled", data.enableflag);
            		
            		if(data.videoppicurl != null){
            			$("li[data-id='"+ data.videoid +"']").find('img').attr('src', data.videoppicurl);
            		}
            		
            		if(editservice.fileList.length > 0){
            			$('li[data-id="'+ data.videoid +'"]').find('input[type="checkbox"]').addClass('hidden');
            			$('li[data-id="'+ data.videoid +'"]').find('.textbox').html(data.videoname);
            			//上传视频
	            		$("li[data-id='"+ data.videoid +"']").attr("data-key", editservice.fileList[0].fileKey);
	            		//添加进度条
	            		$("li[data-id='"+ data.videoid +"']").find('.picbox').after(
	            				'<div class="pg-bar">' +
	            				'  <span class="r">0</span>' +
	            				'  <div class="progress" style="width: 100%;">' +
	            				'    <div class="progress-bar">' +
	            				'      <span class="sr-only" style="width:0;"></span>' +
	            				'    </div>' +
	            				'  </div>' +
	            				'</div>');
            			$('#reconfirm-video').trigger('click');
            		}else{
            			//无上传视频
                		$('li[data-id="'+ data.videoid +'"]').find('.textbox').html(data.videoname + '<a class="r edit btn btn-success-outline radius  size-MINI edit-video-btn"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
            		}
            		//清除输入框数据
            		document.getElementById('editform').reset();
            		$('#editModal').modal('hide');
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
        })
	})
	
	
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
	//关闭视频播放
	$("body").on('click', '.lb-close', function() {
		var player = videojs('my-player');
		player.pause();
		$(".video-area").fadeToggle('fast');
	})
	//监听复选框
	$("body").on("change", "input[type='checkbox']", function() {
    	var cblist = $(".portfoliobox input[type='checkbox']:checked");
    	if(cblist.length == 0){
    		//全不选
    		$(".batch-del").addClass("disabled");
    	}else{
    		//有选中项
    		$(".batch-del").removeClass("disabled");
    	}
	})
	
	//批量删除
    $(".batch-del").click(function() {
    	var cblist = $(".portfoliobox input[type='checkbox']:checked");
    	if(cblist.length > 0){
    		var ids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			ids[i] = $(v).parents("li").attr('data-id');
    			i++
			})
			//delfn(ids);
			layer.confirm('确定删除课程视频？', {icon: 3, title:'删除'}, function(index){
				$.ajax({
		            url: 'delvideo',
		            type: "POST",
		            data: {ids: ids},
		            success: function (result) {
		            	if(result.code == 200){
		            		$("#layui-layer-shade1, #layui-layer1").remove();
		            		//删除区块
		            		$.each(ids, function(k, v) {
				    			$('li[data-id="'+ v +'"]').remove();
							})
							layer.msg('操作成功！',{icon: 1,time:2000});
		            	}else{
		            		layer.msg('操作失败！',{icon: 5,time:2000});
		            	}
		            	return true;
		            }
				})
	    	});
    	}
	})
	
	
});
</script>
</body>
</html>