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
<title>业务员合规处罚列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 提成汇总 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<div class="mt-10">
			按时间：
			<span class="search-param search-date">
				日期范围：
				<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%MM-%dd\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
				-
				<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%MM-%dd' })" id="datemax" class="input-text Wdate" style="width:120px;">
			</span>
		</div>
		<div class="mt-10">
			按部门：
			<span class="select-box" style="width:auto;">
				<select class="select" name="deptid" size="1">
					<option value="null" >--不选择--</option> 
					<option value="" >无部门</option> 
					<c:forEach var="item" items="${depts }">
						<option value="${item.deptid }" >${item.deptname }</option> 
					</c:forEach>
				</select>
			</span>
			人员：
			<span class="select-box" style="width:auto;">
				<select class="select" name="salerid" size="1">
					<option value="" >--不选择--</option>
				</select>
			</span>
			<span class="r">
				<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</span>
		</div>
		
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>业务员账号</th>
					<th>业务员名称</th>
					<th>违规次数</th>
					<th>奖惩金额（元）</th>
					<th>明细</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
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
	var now = new Date();             //获取当前时间
	var beginTimes = now.getFullYear();     //开始计算
	var Month = now.getMonth() +1 ;           //getMonth()是以0开始的月份
	var day = now.getDate(); //获取当前日(1-31)
	
	$("#datemin").val(beginTimes + "-" +(Month<10?"0"+Month:Month) +"-01");
	$("#datemax").val(beginTimes + "-" + (Month<10?"0"+Month:Month)  + "-" + (day<10?"0"+day:day));
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "salerviolatorlist",
			type: "POST",
			data: {starttime: $("#datemin").val(), endtime: $("#datemax").val()}
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
            {"data": 'money'},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a style="text-decoration:none" class="ml-5 detail" href="javascript:;" title="查看详情">' +
	                    '	<i class="Hui-iconfont">&#xe6e0;</i>' +
	                    '</a> '
                	return  str;
                }
            }
        ]
    });
	var api_table = $('#datatable').dataTable();
	
	
	$("#search").click(function() {
		var param = {
				starttime: $("#datemin").val(), 
				endtime: $("#datemax").val(), 
				deptid:  $.trim($("select[name='deptid']").val()),
				salerid: $.trim($("select[name='salerid']").val())};
		jq_table.settings()[0].ajax.data = param;
    	jq_table.ajax.reload();
	})
	
	//根据部门id联动部门下的业务员
	$("select[name='deptid']").change(function() {
		var deptid = $.trim($(this).val());
		if(deptid == 'null'){
			//不选择部门
			$("select[name='salerid']").html('<option value="" >--不选择--</option>');
		}else{
			$.ajax({
				type: 'get',
				url: 'agentdeptidsaler',
				data: {deptid: deptid},
				success: function(data){
					if(data.code == 200){
						var str = '<option value="">--不选择--</option>';
						if(data.data.length > 0){
							$.each(data.data, function(k, v) {
								str += '<option value="'+v.userid+'">'+v.realname+'</option>';
							})
						}
						$("select[name='salerid']").html(str);
					}else{
						layer.msg(data.msg,{icon: 5,time:2000});
					}
				}
			});	
		}
	})
	
	//违规详情
    $("body").on("click", "a.detail", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('违规明细','salerviolatordetail?salerid='+sData.userid+'&starttime='+$("#datemin").val()+'&endtime='+$("#datemax").val(),'','510');
    })
	
});
</script> 
</body>
</html>