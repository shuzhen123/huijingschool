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
</head>
<body>
<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">设置课程购买时长</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="form" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">时长（天）：</label>
						<div class="formControls col-xs-4 col-sm-4">
							<input type="text" class="input-text" placeholder="请填写时长" name="days">
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 课程管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">代理商</option>
		    <option value="2">教师名称</option>
		    <option value="3">课程名称</option>
		    <option value="4">课程类型</option>
		    <option value="5">课程分类</option>
		    <option value="6">推荐分类</option>
		    <option value="7">审核状态</option>
		    <option value="8">添加时间</option>
		  </select>
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-type hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="coursekind" size="1">
					<option value="3" >精品课</option> 
					<option value="4" >私教课</option> 
				</select>
			</span>
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
		<span class="search-param search-recommend hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="recommend" size="1">
					<option value="0" >未推荐</option> 
					<option value="1" >已推荐</option> 
				</select>
			</span>
		</span>
		<span class="search-param search-check hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="check" size="1">
					<!-- 1待审核，2审核通过，3审核失败 -->
					<option value="1" >待审核</option> 
					<option value="2" >已通过</option> 
					<option value="3" >未通过</option> 
				</select>
			</span>
		</span>
		<span class="search-param search-date hidden">
			日期范围：
			<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
			-
			<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;">
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜课程</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		<span class="r by-time-limit">
			课程购买时长：${time_limit } 天
			<a href="javascript:;" class="btn btn-success radius time-limit" data-time="${time_limit }">
				<i class="Hui-iconfont">&#xe61d;</i> 更改
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
					<th>教师姓名</th>
					<th>课程名称</th>
					<th>课程金额</th>
					<th>课程类型</th>
					<th>课程分类</th>
					<th>推荐课程</th>
					<th>课程评论</th>
					<th>上架状态</th>
					<th>审核状态</th>
					<th>发布时间</th>
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
	$("span.by-time-limit").on('click', 'a.time-limit', function() {
		$('#myModal input[name="days"]').val($(this).attr('data-time'))
		$('#myModal').modal('show')
	})
	
	$("#myModal .confirm").click(function () {
		var days = $.trim($("#myModal input[name='days']").val());
    	if(!(/^[0-9]{1,}$/.test(days))){
    		layer.msg('请填写正确的天数！',{icon: 7,time:2000}); return false;
    	}
		$.ajax({
	        url: 'setcoursebytimelimit',
	        type: "POST",
	        data: {days: days},
	        success: function (result) {
	        	if(result.code == 200){
	        		layer.msg('操作成功',{icon: 1,time:1500});
	        		str = 
	        			'课程购买时长：'+ days +' 天  '+
	        			' <a href="javascript:;" class="btn btn-success radius time-limit" data-time="'+ days +'">'+
	        			'  <i class="Hui-iconfont">&#xe61d;</i> 更改'+
	        			'</a>';
	        		$("span.by-time-limit").html(str);	
	        		$('#myModal').modal('hide')
	        	}else{
	        		layer.msg(result.msg, {icon: 5,time:1500});
	        	}
	        }
		})
	})
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "courselist",
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
                "mRender": function (data, type, full, meta) {
                	if(full.courselimit == 1){
                		return '免费';
                	}else{
                		return full.coursemoney;
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	 /* 1：免费直播课程2：vip实战直播课程3：精品课4：私教课 */
                    if (full.coursekind == 1) {
                        return '免费直播';
                    } else if(full.coursekind == 2){
                        return 'vip实战直播';
                    } else if(full.coursekind == 3){
                        return '精品课';
                    } else if(full.coursekind == 4){
                        return '私教课';
                    }
                }
            },
            {"data": 'coursetypename'},
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
                	return '<button class="btn btn-primary size-MINI course-comment" type="button">查看评论</button>';
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
                		return '<span class="label label-danger radius">审核失败</span>';
                	}
                }
            },
            {"data": 'createtime'},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a style="text-decoration:none" class="ml-5 copy" href="javascript:;" title="复制链接">' +
	                    '	<i class="Hui-iconfont">&#xe616;</i>' +
	                    '</a> ' +
                     	'<a style="text-decoration:none" class="ml-5 course-detail" href="javascript:;" title="查看详情">' +
	                    '	<i class="Hui-iconfont">&#xe6e0;</i>' +
	                    '</a> ';
	                 if(full.auth == 1){
	                	 str +=
	                		 '<a title="审核" href="javascript:;" class="ml-5 course-edit" style="text-decoration:none">' +
	 	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6a7;</i>' +
	 	                    '</a> ';
	                 }else{
	                	 str +=
	                		 '<a title="课程编辑" href="javascript:;" class="ml-5 course-edit" style="text-decoration:none">' +
	 	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	 	                    '</a> '; 
	                 }
	                 str +=
	                	'<a title="查看课程视频" href="javascript:;" class="ml-5 course-video" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #5a98de;">&#xe6c2;</i>' +
	                    '</a> '+
	                    '<a title="删除" href="javascript:;" class="ml-5 course-del" style="text-decoration:none">' +
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
    		//回复默认
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
    		//教师名称
    		var realname = $.trim($(".search-text input").val());
    		if(realname == '') return false;
    		param = {realname: realname};
    	}else if(sel == 3){
    		//课程名称
    		var coursename = $.trim($(".search-text input").val());
    		if(coursename == '') return false;
    		param = {coursename: coursename};
    	}else if(sel == 4){
    		//课程类型 
    		var coursetype = $("select[name='coursekind']").val();
    		if(coursetype == '') return false;
    		param = {coursetype: coursetype};
    	}else if(sel == 5){
    		//课程分类
    		var courseclass = $("select[name='coursetypecode']").val();
    		if(courseclass == '') return false;
    		param = {courseclass: courseclass};
    	}else if(sel == 6){
    		//推荐分类
    		var recommend = $("select[name='recommend']").val();
    		if(recommend == '') return false;
    		param = {recommend: recommend};
    	}else if(sel == 7){
    		//审核状态
    		var check = $("select[name='check']").val();
    		if(check == '') return false;
    		param = {check: check};
    	}else if(sel == 8){
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
		}else if(val == 1 || val == 2 || val == 3){
    		//代理商、教师名称、课程名称
    		$(".search-text").removeClass("hidden");
    	}else if(val == 4){
    		//课程类型
    		$(".search-type").removeClass("hidden");
    	}else if(val == 5){
    		//课程分类
    		$(".search-class").removeClass("hidden");
    	}else if(val == 6){
    		//推荐分类
    		$(".search-recommend").removeClass("hidden");
    	}else if(val == 7){
    		//审核状态
    		$(".search-check").removeClass("hidden");
    	}else if(val == 8){
    		//时间input展示
    		$(".search-date").removeClass("hidden");
    	}
	})
    
    //课程删除
    $("body").on("click", "a.course-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除此课程'+ (sData.realname == null?'':sData.realname) +'？', {icon: 3, title:'提示'}, function(index){
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
    
    
    //课程详情
    $("body").on("click", "a.course-detail", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('课程详情', '${pageContext.request.contextPath}/tea/coursedata?courseid=' +sData.courseid,'','510');
    })
    
    //课程修改
    $("body").on("click", "a.course-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('课程修改','courseeditpage?courseid='+sData.courseid,'','510');
    })
    
    //课程修改
    $("body").on("click", "a.copy", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.open({
   		  title: '公众号课程链接',
   		  content: 'http://mp.huijingschool.com/#/courseDetails?selected=2&courseid='+sData.courseid
   		}); 
    })
    
    //查看课程评论
    $("#datatable").on('click', '.course-comment', function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var index = layer.open({
    		type: 2,
    		title: sData.coursename + '-课程评论',
    		content: '${pageContext.request.contextPath}/tea/coursecommentpage?role=admin&courseid=' +sData.courseid
    	});
    	layer.full(index);
	})
	
	//查看课程视频列表
    $("#datatable").on('click', '.course-video', function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var index = layer.open({
    		type: 2,
    		title: sData.coursename + '-课程视频列表',
    		content: '${pageContext.request.contextPath}/tea/coursevideopage?courseid=' +sData.courseid
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