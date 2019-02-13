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
					<input type="text" class="hidden" name="createtime">
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
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="uploadimage" id="uploadimage" readonly  style="width:200px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传展示图</a>
									<input type="file" name="vfile" id="videoppicurl" class="input-file">
								</span>
							</div>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频文件：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="uploadvideo" id="uploadvideo" readonly  style="width:200px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传视频</a>
									<input type="file" name="vfile" id="videourl" class="input-file">
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary confirm">确定</button>
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
						<label class="form-label col-xs-4 col-sm-3">视频展示图：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="edituploadimage" id="edituploadimage" readonly  style="width:200px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传展示图</a>
									<input type="file" name="vfile" id="editvideoppicurl" class="input-file">
								</span>
							</div>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频文件：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="edituploadvideo" id="edituploadvideo" readonly  style="width:200px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传视频</a>
									<input type="file" name="vfile" id="editvideourl" class="input-file">
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary confirm">确定</button>
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
			<a href="javascript:;" class="btn btn-primary radius" data-toggle="modal" data-target="#addModal">
				<i class="Hui-iconfont">&#xe600;</i> 上传视频
			</a> 
		</span> 
	</div>
	<div class="portfolio-content">
		<ul class="cl portfolio-area">
			<c:forEach var="item" items="${videos }">
				<li class="item hover" data-id="${item.videoid }" data-name="${item.videoname }" 
				data-intro="${item.videointroduce }" data-content="${item.videocontent }" data-video="${item.videourl }">
					<div class="portfoliobox">
						<input class="checkbox" type="checkbox" >
						<div class="picbox">
							<a href="javascript:;" title="点击播放视频" >
								<img src="${pageContext.request.contextPath}/${item.videoppicurl }">
							</a>
						</div>
						<div class="textbox">
							${item.videoname }
							<a class="r edit btn btn-success-outline radius  size-MINI"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="hidden upload-content"></div>

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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-video/js/video.min.js"></script> 
<script type="text/javascript">

$(function(){
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
			/* poster: imgurl, */
			/* sources: [
				{
					src: '/LiveAdmin'+videourl,
				    type: 'video/mp4'
				},
			  	{
				    src: '/LiveAdmin'+videourl,
				    type: 'video/flv'
				}
			] */
		}, function onPlayerReady() {
			videojs.log('Your player is ready!');
			this.height($("body").height() * 0.5);
		   }
		);
	
	//视频播放
	$("body").on('click', 'li .picbox a', function() {
		var videourl = '/LiveAdmin' + $(this).parents('li').attr('data-video');
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
	
	//监听图片文件上传
	$("#videoppicurl, #editvideoppicurl").change(function(e) {
		var file = this.files[0];
		if(!(/^image\/\w+/.test(this.files[0].type))) {
			$(this).val('');
			$(this).siblings('input').val('');
			layer.msg('图片文件格式不正确，请上传png, jpg, jpeg, gif, bmp等格式的图片文件',{icon: 5,time:2000});
		}
	})
	//监听视频文件上传
	$("#videourl, #editvideourl").change(function(e) {
		var file = this.files[0];
		//var mime = ['video/x-flv', 'video/mp4', 'application/x-mpegURL', 'video/MP2T', 'video/3gpp', 'video/quicktime', 'video/x-msvideo', 'video/x-ms-wmv'];
		if(!(/^video\/\w+/.test(this.files[0].type))) {
			$(this).val('');
			$(this).siblings('input').val('');
			layer.msg('视频文件格式不正确，请上传flv, mp4, m3u8, ts, 3gp, mov, avi, wmv等格式的视频文件',{icon: 5,time:2000});
		}
	})
	
	//确认上传
	$("#addModal .confirm").click(function() {
		//验证输入信息 1√，2x, 3?, 4lock, 5哭脸， 6笑脸， 7！
		if($.trim($('#addModal input[name="videoname"]').val()) == ''){
			layer.msg('请填写视频名称！',{icon: 7,time:2000});
			return false;
		}
		if($.trim($('#addModal #videoppicurl').val()) == ''){
			layer.msg('请选择视频展示图！',{icon: 7,time:2000});
			return false;
		}
		if($.trim($('#addModal #videourl').val()) == ''){
			layer.msg('请选择上传视频！',{icon: 7,time:2000});
			return false;
		}
		
		//新增展示框
		var id = Date.parse(new Date());
		//将时间戳放入表单
		$('#addModal input[name="createtime"]').val(id);
		//随机id
		var str = 
			'<li class="item hover '+ id +'">' +
			'  <div class="portfoliobox">' +
			'    <input class="checkbox hidden" name="" type="checkbox" value="">' +
			'    <div class="picbox">' +
			'      <a href="javascript:;" title="点击播放视频">' +
			'        <img id="img'+ id +'" src="">' +
			'      </a>' +
			'    </div>' +
			'    <div class="pg-bar">' +
			'      <span class="r">0</span>' +
			'      <div class="progress" style="width: 100%;">' +
			'        <div class="progress-bar">' +
			'          <span class="sr-only" style="width:0;"></span>' +
			'        </div>' +
			'      </div>' +
			'    </div>' +
			'    <div class="textbox">'+ ($('#addModal input[name="videoname"]').val()) +
			'	 </div>' +
			'  </div>' +
			'</li>';
		$("ul").append(str);
		
		/*确定上传动作*/
		//获取展示图base64数据
        var img = $('#videoppicurl');
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        if (isIE) {
            img.select();
            var reallocalpath = document.selection.createRange().text;
            // IE6浏览器设置img的src为本地路径可以直接显示图片
            if (isIE6) {
                $('li.'+id).find("img").attr("src", reallocalpath);
            } else {
                // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
                document.getElementById("img"+id).style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
                // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
                document.getElementById("img"+id).src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
            }
        } else {
            var file = document.getElementById("videoppicurl").files[0];
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                $('#img'+id).attr('src', this.result);
            }
        }
        
      	//clone form 到上传区域
		$('.upload-content').append('<div id="'+ id +'"></div>');
		$('#'+ id).append($("#form").clone());
		$('#'+ id + " #form").attr('id', 'form'+id);
      	//隐藏输入框
      	document.getElementById("form").reset()
		$("#addModal").modal('hide');
		
		//开始上传
		var form=document.getElementById('form'+id);
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
            		var data = result.data, tp = data.createtime;
            		//上传成功
            		//更新videoid
            		$("li."+tp).addClass(data.videoid).removeClass(tp);
            		tp = data.videoid;
            		//添加data-id
            		$("li."+tp).attr('data-id', tp);
            		//添加data-name
            		$("li."+tp).attr('data-name', data.videoname);
            		//添加data-intro
            		$("li."+tp).attr('data-intro', data.videointroduce);
            		//添加data-content
            		$("li."+tp).attr('data-content', data.videocontent);
            		//添加data-video
            		$("li."+tp).attr('data-video', data.videourl);
            		//显示单选框
            		$("li."+tp).find('input[type="checkbox"]').removeClass('hidden');
            		//添加编辑按钮
            		$("li."+tp).find('.textbox').html(data.videoname + 
            				'<a class="r edit btn btn-success-outline radius  size-MINI"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
            	}else{
            		//上传失败
            		layer.msg(result.msg,{icon: 5,time:2000});
            		//删除li
            		$("li."+result.data).remove();
            	}
            },
            xhr: function () {
            	var xhr = jQuery.ajaxSettings.xhr();  
                xhr.upload.onload = function (){
                	//上传完成，删除进度条
                	$("ul li."+id).find('.pg-bar').remove();
                }  
                xhr.upload.onprogress = function (ev) { 
                	var per = Math.round(ev.loaded/ev.total * 100);
                	$("li."+id).find(".pg-bar .r").html(per+'%');
                	$("li."+id).find(".pg-bar .sr-only").css('width', per+'%');
                }  
                return xhr; 
            },
            complete: function (xhr, textStatus) {
            	//删除上传区域的form表单
            	$('.upload-content div#'+id).remove();
            },
        })
	})
	
	//视频修改modal
	$("body").on('click', 'a.edit', function() {
		var obj = $(this).parents('li');
		$("#editModal input[name='videoid']").val(obj.attr('data-id'));
		$("#editModal input[name='videoname']").val(obj.attr('data-name'));
		$("#editModal input[name='videointroduce']").val(obj.attr('data-intro'));
		$("#editModal textarea[name='videocontent']").val(obj.attr('data-content'));
		$("#editModal").modal('show');
	})
	
	//修改视频
	$("#editModal .confirm").click(function() {
		//验证输入信息 1√，2x, 3?, 4lock, 5哭脸， 6笑脸， 7！
		if($.trim($('#editModal input[name="videoname"]').val()) == ''){
			layer.msg('请填写视频名称！',{icon: 7,time:2000});
			return false;
		}
		//验证通过
		var videoid= $("#editModal input[name='videoid']").val(), id = Date.parse(new Date());;
		//1、更改名称
		$("li[data-id='"+ videoid +"']").find('.textbox').text($('#editModal input[name="videoname"]').val());
		//2、若重新上传了图片，更改图片
		var img = $('#editvideoppicurl');
		if(img.val() != ''){
			//隐藏原图片
			$("li[data-id='"+ videoid +"']").find('img').addClass('hidden');
			$("li[data-id='"+ videoid +"']").find('img').after('<img id="'+ id +'">');
	        if (navigator.userAgent.match(/MSIE/) != null) {
	            img.select();
	            var reallocalpath = document.selection.createRange().text;
	            // IE6浏览器设置img的src为本地路径可以直接显示图片
	            if (navigator.userAgent.match(/MSIE 6.0/) != null) {
	            	$("li[data-id='"+ videoid +"']").find("img#newimg"+id).attr("src", reallocalpath);
	            } else {
	                // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
	                document.getElementById("#"+id).style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
	                // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
	                document.getElementById("#"+id).src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
	            }
	        } else {
	            var file = document.getElementById("editvideoppicurl").files[0];
	            var reader = new FileReader();
	            reader.readAsDataURL(file);
	            reader.onload = function (e) {
	                $('#'+id).attr('src', this.result);
	            }
	        }
		}
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
            beforeSend: function() {
            	//禁用复选框
            	$("li[data-id='"+ videoid +"']").find('.checkbox').addClass('disabled');
            	//添加上传进度条
            	$("li[data-id='"+ videoid +"']").find('.picbox').after(
            			'    <div class="pg-bar">' +
            			'      <span class="r">0</span>' +
            			'      <div class="progress" style="width: 100%;">' +
            			'        <div class="progress-bar">' +
            			'          <span class="sr-only" style="width:0;"></span>' +
            			'        </div>' +
            			'      </div>' +
            			'    </div>');
            	document.getElementById("editform").reset();
            	$("#editModal").modal('hide');
			},
            success: function (result) {
            	var data = result.data;
            	if(result.code == 200){
            		var vid = data.videoid;
            		//上传成功
            		//更改data-name
            		$("li[data-id='"+ vid +"']").attr('data-name', data.videoname);
            		//更改data-intro
            		$("li[data-id='"+ vid +"']").attr('data-intro', data.videointroduce);
            		//更改data-content
            		$("li[data-id='"+ vid +"']").attr('data-content', data.videocontent);
            		//单选框可选
            		$("li[data-id='"+ vid +"']").find('input[type="checkbox"]').removeClass('disabled');
            		//img
            		if(data.videoppicurl != null){
	            		$("li[data-id='"+ vid +"']").find('img:last-child').remove();
	            		$("li[data-id='"+ vid +"']").find('img:first-child').attr('src', '/LiveAdmin/'+data.videoppicurl).removeClass('hidden');
            		}
            		//video
            		if(data.videourl != null){
	            		$("li[data-id='"+ vid +"']").attr('data-video', '/LiveAdmin/'+data.videourl);
            		}
            		//添加编辑按钮
            		$("li[data-id='"+ vid +"']").find('.textbox').html(data.videoname + 
            				'<a class="r edit btn btn-success-outline radius  size-MINI"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
            		
            		layer.msg("操作成功！",{icon: 1,time:2000});
            	}else{
            		//上传失败
            		layer.msg(result.msg,{icon: 5,time:2000});
            		//还原旧值
            		$("li[data-id='"+ data +"']").find('.textbox').html($("li[data-id='"+ data +"']").attr('data-name')+
            				'<a class="r edit btn btn-success-outline radius  size-MINI"  title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
            		$("li[data-id='"+ data +"']").find('img:first-child').removeClass('hidden');
            		$("li[data-id='"+ data +"']").find('img:last-child').remove();
            	}
            },
            xhr: function () {
            	var xhr = jQuery.ajaxSettings.xhr();  
                xhr.upload.onload = function (){
                	//上传完成，删除进度条
                	$("li[data-id='"+ videoid +"']").find('.pg-bar').remove();
                }  
                xhr.upload.onprogress = function (ev) { 
                	var per = Math.round(ev.loaded/ev.total * 100);
                	$("li[data-id='"+ videoid +"']").find(".pg-bar .r").html(per+'%');
                	$("li[data-id='"+ videoid +"']").find(".pg-bar .sr-only").css('width', per+'%');
                }  
                return xhr; 
            }
        })
	})
	
});
</script>
</body>
</html>