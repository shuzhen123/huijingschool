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
<title>课程列表</title>
<style type="text/css">
	table#datatable td.info_row{
		text-align: left;
	}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 课程管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="2">课程名称</option>
		    <option value="3">课程类型</option>
		    <option value="4">课程分类</option>
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
		<span class="search-param search-class hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="coursetypecode" size="1">
					<c:forEach var="item" items="${kind }">
						<option value="${item.id }" >${item.name }</option> 
					</c:forEach>
				</select>
			</span>
		</span>
		<span class="search-param search-type hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="coursekind" size="1">
					<!-- 3：精品课4：私教课 -->
					<option value="3" >精品课</option> 
					<option value="4" >私教课</option> 
				</select>
			</span>
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜课程</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<!-- <span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span> -->
		<span class="r">
			<a href="javascript:;" class="btn btn-primary radius add-course">
				<i class="Hui-iconfont">&#xe600;</i> 添加课程
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>课程名称</th>
					<th>课程金额</th>
					<th>课程分类</th>
					<th>课程类型</th>
					<th>点赞数量</th>
					<th>课程评论</th>
					<th>推荐课程</th>
					<th>滚动栏</th>
					<th>上架状态</th>
					<th>审核状态</th>
					<th>创建时间</th>
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
			url: "courselist",
			type: "POST",
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
            {"data": "coursename"},
            {
		        "data": null,
		        "mRender": function (data, type, full) {
		        	 /* 1:免费2:付费 */
		            if (full.courselimit == 1) {
		                return '免费';
		            } else{
		                return full.coursemoney;
		            }
		        }
		    },
            {"data": 'coursetypename'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	 /* 1：免费直播课程2：vip实战直播课程3：精品课4：私教课 */
                    if(full.coursekind == 3){
                        return '精品课';
                    } else if(full.coursekind == 4){
                        return '私教课';
                    }
                }
            },
            {"data": 'thumbsupcount'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	return '<button class="btn btn-primary size-MINI course-comment" type="button">查看评论</button>';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.recommendflag == 1){
                		return '是';
                	}else{
                		return '否';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.bannerflag == 1){
                		return '是';
                	}else{
                		return '否';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	/* 1上家，2下架 */
                	if(full.flag == 1){
                		return '上架';
                	}else{
                		return '下架';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	/* 1待审核，2审核通过，3审核失败 */
                	if(full.auth == 1){
                		return '<span class="label label-warning radius">待审核</span>';
                	}else if(full.auth == 2){
                		return '<span class="label label-success radius">审核通过</span>';
                	}else if(full.auth == 3){
                		return '<input class="btn btn-danger-outline radius size-MINI check-failed" type="button" title="点击查看审核失败原因" value="审核失败">';
                	}
                }
            },
            {"data": "createtime"},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a style="text-decoration:none" class="ml-5 course-detail" href="javascript:;" title="查看详情">' +
	                    '	<i class="Hui-iconfont">&#xe6e0;</i>' +
	                    '</a> ' +
	                    '<a title="编辑" href="javascript:;" class="ml-5 course-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="查看课程视频" href="javascript:;" class="ml-5 course-video" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #5a98de;">&#xe6c2;</i>' +
	                    '</a> ';
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
    		//恢复默认
    		var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				param = undefined;
			}else return false;
    	}else if(sel == 2){
    		//使用课程名称搜索
    		var coursename = $(".search-text input").val();
    		if(coursename == '') return false;
    		param = {coursename: coursename};
    	}else if(sel == 3){
    		//课程类型
    		var coursekind = $("select[name='coursekind']").val();
    		if(coursekind == '') return false;
    		param = {coursekind: coursekind};
    	}else if(sel == 4){
    		//课程分类
    		var coursetypecode = $("select[name='coursetypecode']").val();
    		if(coursetypecode == '') return false;
    		param = {coursetypecode: coursetypecode};
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
		}else if(val == 2){
    		//输入框
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3){
    		//选择框
    		$(".search-type").removeClass("hidden");
    	}else if(val == 4){
    		//选择框
    		$(".search-class").removeClass("hidden");
    	}else if(val == 5){
    		//时间input展示
    		$(".search-date").removeClass("hidden");
    	}
	})
    
	//点击查看审核失败详情
	$("body").on('click', 'input.check-failed', function() {
		var obj = $(this).parents('tr');
        if (api_table.fnIsOpen(obj)) {
            api_table.fnClose(obj);
        } else {
            var sData = api_table.fnGetData(obj);
            api_table.fnOpen(obj, sData.cause, "info_row order-info");
        }
	})
    
    //课程详情
    $("body").on("click", "a.course-detail", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('课程详情', 'coursedata?type=detail&courseid=' +sData.courseid,'','510');
    })
    //课程修改
    $("body").on("click", "a.course-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('课程修改','coursedata?type=edit&courseid='+sData.courseid,'','510');
    })
    
    //课程添加
    $("body").on("click", "a.add-course", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('添加课程','addcoursepage','','510');
    })
    
    //查看课程评论
    $("#datatable").on('click', '.course-comment', function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var index = layer.open({
    		type: 2,
    		title: sData.coursename + '-课程评论',
    		content: 'coursecommentpage?courseid=' +sData.courseid
    	});
    	layer.full(index);
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