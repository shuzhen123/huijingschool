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
<title>直播节目列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 直播节目管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">代理商</option>
		    <option value="2">讲师</option>
		    <option value="3">课程名称</option>
		    <option value="4">直播类型</option>
		    <option value="5">课程等级</option>
		    <option value="6">课程上下架</option>
		    <option value="7">直播状态</option>
		    <option value="8">审核状态</option>
		    <option value="9">添加时间</option>
		  </select>
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-type hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="coursekind" size="1">
					<!-- 1：免费直播课程2：vip实战直播课程3：精品课4：私教课 -->
					<option value="1" >免费直播课程</option> 
					<option value="2" >vip实战直播课程</option> 
				</select>
			</span>
		</span>
		<span class="search-param search-kind hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="kind" size="1">
					<c:forEach var="item" items="${kind }">
						<option value="${item.id }" >${item.levelname }（${item.money }元）</option> 
					</c:forEach>
				</select>
			</span>
		</span>
		<span class="search-param search-used hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="used" size="1">
					<option value="0" >下架</option> 
					<option value="1" >上架</option> 
				</select>
			</span>
		</span>
		<span class="search-param search-status hidden">
			<!-- 直播课程flag(1正在直播，2往期直播，3预告直播) -->
			<span class="select-box" style="width:auto;">
				<select class="select" name="status" size="1">
					<option value="1" >正在直播</option> 
					<option value="2" >往期直播</option> 
					<option value="3" >预告直播</option> 
				</select>
			</span>
		</span>
		<span class="search-param search-auth hidden">
			<!-- 审核状态（1待审核，2审核通过，3审核失败） -->
			<span class="select-box" style="width:auto;">
				<select class="select" name="auth" size="1">
					<option value="1" >待审核</option> 
					<option value="2" >审核通过</option> 
					<option value="3" >审核失败</option> 
				</select>
			</span>
		</span>
		<span class="search-param search-date hidden">
			日期范围：
			<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
			-
			<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;">
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜直播</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>代理商</th>
					<th>教师</th>
					<th>课程名称</th>
					<th>直播类型</th>
					<th>课程等级</th>
					<th>上架状态</th>
					<th>直播状态</th>
					<th>预告直播时间</th>
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
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "livecourselist",
			type: "POST",
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return '<input type="checkbox" value="1" name="">';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
            
            {"data": 'agentname'},
            {"data": 'realname'},
            {"data": 'coursename'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	 /* 1：免费直播课程2：vip实战直播课程 */
                    if (full.coursekind == 1) {
                        return '免费直播';
                    } else if(full.coursekind == 2){
                        return 'vip实战直播';
                    }
                }
            },
            {"data": 'coursetypename'},
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
               		/* (1正在直播，2往期直播，3预告直播) */
               		if(full.auth == 2){
	               		if(full.liveflag == 1){
	                		return '<span class="label label-success radius">正在直播</span>';
	                   	}else if(full.liveflag == 2){
	                		return '<span class="label label-default radius">往期直播</span>';
	                   	}else if(full.liveflag == 3){
	                		return '<span class="label label-primary radius">预告直播</span>';
	                   	}
                   	}else{
                   		return '-';
                   	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	return full.starttime.substring(5,16) + ' <i class="Hui-iconfont">&#xe606;</i> ' + full.endtime.substring(5,16);
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	/* 1待审核，2审核通过，3审核失败 */
                	if(full.auth == 1){
                		return '<span class="label label-danger radius">待审核</span>';
                	}else if(full.auth == 2){
                		return '<span class="label label-success radius">审核通过</span>';
                	}else if(full.auth == 3){
                		return '<span class="label label-primary radius">审核失败</span>';
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
	                    '<a title="删除" href="javascript:;" class="ml-5 user-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
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
    	}else if(sel == 1){
    		//代理商
    		var agentname = $.trim($(".search-text input").val());
    		if(agentname == '') return false;
    		param = {agentname: agentname};
    	}else if(sel == 2){
    		//讲师
    		var teachername = $.trim($(".search-text input").val());
    		if(teachername == '') return false;
    		param = {teachername: teachername};
    	}else if(sel == 3){
    		//课程名称
    		var coursename = $.trim($(".search-text input").val());
    		if(coursename == '') return false;
    		param = {coursename: coursename};
    	}else if(sel == 4){
    		//直播类型
    		var coursekind = $("select[name='coursekind']").val();
    		param = {coursekind: coursekind};
    	}else if(sel == 5){
    		//课程类型
    		var kind = $("select[name='kind']").val();
    		param = {kind: kind};
    	}else if(sel == 6){
    		//课程上下架
    		var flag = $("select[name='used']").val();
    		param = {flag: flag};
    	}else if(sel == 7){
    		//直播状态
    		var liveflag = $("select[name='status']").val();
    		param = {liveflag: liveflag};
    	}else if(sel == 8){
    		//审核状态
    		var auth = $("select[name='auth']").val();
    		param = {auth: auth};
    	}else if(sel == 9){
    		//添加时间
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
		}else if(val == 1 || val == 2 || val == 3){
			//输入框
    		$(".search-text").removeClass("hidden");
    	}else if(val == 4){
    		//直播类型
    		$(".search-type").removeClass("hidden");
    	}else if(val == 5){
    		//课程类型
    		$(".search-kind").removeClass("hidden");
    	}else if(val == 6){
    		//课程上下架
    		$(".search-used").removeClass("hidden");
    	}else if(val == 7){
    		//直播状态
    		$(".search-status").removeClass("hidden");
    	}else if(val == 8){
    		//审核状态
    		$(".search-auth").removeClass("hidden");
    	}else if(val == 9){
    		//添加时间
    		$(".search-date").removeClass("hidden");
    	}
	})
    
    //删除
    $("body").on("click", "a.user-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除课程'+ sData.coursename +'？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delcourse.do', [sData.courseid], jq_table);
    	});
    });
    
    $("#datatable").on("change", "input[type='checkbox']", function() {
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
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
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
    	if(cblist.length > 0){
    		var courseids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			courseids[i] = sData.courseid;
    			i++
			})
			layer.confirm('确定批量删除课程？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delcourse.do', courseids, jq_table);
	    	});
    	}
    	
	})
    
    //直播课程详情
    $("body").on("click", "a.course-detail", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var href = '${pageContext.request.contextPath}/tea/livecoursedetail?courseid=' +sData.courseid;
    	parent.$("#min_title_list").find('span[data-href="' + href + '"]').parent().remove();
    	parent.$("#iframe_box iframe[src='" + href + "']").parent().remove();
    	creatIframe(href, sData.coursename + '-详情')
    })
    //课程修改
    $("body").on("click", "a.course-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('课程修改','livecoursedata?type=edit&courseid='+sData.courseid,'','510');
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