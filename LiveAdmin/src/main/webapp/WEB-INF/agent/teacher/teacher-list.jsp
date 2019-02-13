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
<title>讲师列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 讲师管理</nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">讲师账号</option>
		    <option value="2">讲师名称</option>
		    <option value="3">直播推荐</option>
		    <option value="4">名师推荐</option>
		    <option value="5">添加时间</option>
		  </select>
		</span>
		
		<span class="search-param search-date hidden">
			日期范围：
			<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
			-
			<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;">
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-recommend hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="recommend" size="1">
					<option value="0" >未推荐</option> 
					<option value="1" >已推荐</option> 
				</select>
			</span>
		</span>
		
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜讲师</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<!-- <span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span> -->
		<span class="r">
			<a href="javascript:;" class="btn btn-primary radius add-teacher">
				<i class="Hui-iconfont">&#xe600;</i> 添加讲师
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>讲师账号</th>
					<th>讲师名称</th>
					<th>课程数</th>
					<th>讲师头像</th>
					<th>讲师展示图</th>
					<th>直播推荐</th>
					<th>名师推荐</th>
					<th>上传课程</th>
					<th>直播课程</th>
					<th>注册时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<button class="hidden" id="outflush"></button>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> 
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootbox/bootbox.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "teacherlist",
			type: "POST",
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
			{"data": 'username'},
			{"data": 'realname'},
			{"data": 'count'},
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    //return '<img src="http://img4.imgtn.bdimg.com/it/u=1373411777,3992091759&fm=27&gp=0.jpg" alt="头像" class="round" style="max-height: 50px;">';
                    return '<img src="'+ full.iconurl +'" alt="头像" class="round" style="width: 50px; height: 50px;">';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    //return '<img src="http://pic.58pic.com/58pic/15/24/50/43Q58PICkj4_1024.jpg" alt="展示图" class="thumbnail" style="max-height: 50px;">';
                    return '<img src="'+ full.teacherurl +'" alt="展示图" class="thumbnail" style="max-height: 50px;">';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.trecflag == 1) return '<span class="label label-success radius">已推荐</span>';
                	else return '<span class="label label-default radius">未推荐</span>';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.famousteacherflag == 1) return '<span class="label label-success radius">已推荐</span>';
                	else return '<span class="label label-default radius">未推荐</span>';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	return '<button class="btn btn-success size-MINI normal-course" type="button">查看课程</button>';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	return '<button class="btn btn-success size-MINI live-course" type="button">查看课程</button>';
                }
            },
            {"data": "registertime"},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a style="text-decoration:none" class="ml-5 course-detail" href="javascript:;" title="查看详情">' +
	                    '	<i class="Hui-iconfont">&#xe6e0;</i>' +
	                    '</a> ' +
	                    '<a title="编辑" href="javascript:;" class="ml-5 course-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ';
	                    /* '<a title="删除" href="javascript:;" class="ml-5 user-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>' */
                	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    var param = undefined;
    //条件搜索
    $("#search").click(function() {
    	var sel = $("select").val();
    	if(sel == 0){
    		//回复默认
    		var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				param = undefined;
			}else return false;
    	}else if(sel == 1){
    		//讲师账号
    		var username = $.trim($(".search-text input").val());
    		if(username == '') return false;
    		param = {username: username};
    	}else if(sel == 2){
    		//讲师名称
    		var realname = $.trim($(".search-text input").val());
    		if(realname == '') return false;
    		param = {realname: realname};
    	}else if(sel == 3){
    		//直播推荐
    		var live = $("select[name='recommend']").val();
    		param = {live: live};
    	}else if(sel == 4){
    		//名师推荐
    		var tea = $("select[name='recommend']").val();
    		param = {tea: tea};
    	}else if(sel == 5){
    		//使用时间搜索
    		var starttime = $.trim($("#datemin").val());
    		var endtime = $.trim($("#datemax").val());
    		if(starttime == '' || endtime == '') return false;
    		param = {starttime: starttime, endtime: endtime};
    	}else{
    		//不进行搜索
    		return false;
    	}
    	jq_table.settings()[0].ajax.data = param;
    	jq_table.ajax.reload();
	})
	$("select.sel").change(function() {
		var val = $(this).val();
		$(".search-param").addClass("hidden");
		$(".search-param input").val("");
		if(val == 0){
			var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				jq_table.settings()[0].ajax.data = undefined;
		    	jq_table.ajax.reload();
			}
		}else if(val == 1 || val == 2){
    		//输入框
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3 || val == 4){
    		//直播推荐、名师推荐
    		$(".search-recommend").removeClass("hidden");
    	}else if(val == 5){
    		//时间input展示
    		$(".search-date").removeClass("hidden");
    	}
	})
    
    //查看普通课程
    $("body").on("click", "button.normal-course", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var href = 'teachercoursepage?teacherid=' +sData.userid;
    	parent.$("#min_title_list").find('span[data-href="' + href + '"]').parent().remove();
    	parent.$("#iframe_box iframe[src='" + href + "']").parent().remove();
    	creatIframe(href, sData.realname + '-上传课程列表')
    })
    //查看直播课程
    $("body").on("click", "button.live-course", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var href = 'teachercoursepage?type=live&teacherid=' +sData.userid;
    	parent.$("#min_title_list").find('span[data-href="' + href + '"]').parent().remove();
    	parent.$("#iframe_box iframe[src='" + href + "']").parent().remove();
    	creatIframe(href, sData.realname + '-直播课程列表')
    })
    
    //讲师详情
    $("body").on("click", "a.course-detail", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('讲师信息详情','teacherdate?teacherid='+sData.userid,'','510');
    })
    //讲师信息修改
    $("body").on("click", "a.course-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('讲师信息修改','teacherdate?type=live&teacherid='+sData.userid,'','510');
    })
    //讲师添加
    $("body").on("click", "a.add-teacher", function() {
    	layer_show('添加讲师','addteacherpage','','510');
    })
    
    //查看课程视频列表
    $("#datatable").on('click', '.course-video', function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var index = layer.open({
    		type: 2,
    		title: sData.coursename + '-课程视频列表',
    		content: 'coursevideopage?courseid=' +sData.courseid
    	});
    	layer.full(index);
	})
    
    //外围刷新
    $("#outflush").click(function() {
    	layer.msg("操作成功！",{icon: 6,time:2000});
    	jq_table.ajax.reload(null, false);
	})
    
});
</script> 
</body>
</html>