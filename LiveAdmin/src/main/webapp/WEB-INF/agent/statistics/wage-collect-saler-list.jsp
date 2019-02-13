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
<title>业务员分润汇总列表</title>
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
			
			<span class="r">
				<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</span>
		</div>
	</div>
	<div class="mt-20">
		<div class="row">
			<div class="col-md-12">
				<table id="all" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th  colspan="2">统计</th>
						</tr>
						<tr class="text-c">
							<th>类型</th>
							<th>资金（元）</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>直播消费提成</td>
							<td>0</td>
						</tr>
						<tr>
							<td>礼物订单提成</td>
							<td>0</td>
						</tr>
						<tr>
							<td>总计提成</td>
							<td>0</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
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
	
	//获取分润数据
	var wage = function(starttime, endtime) {
		$.ajax({
			type: 'get',
			url: 'wagecollect',
			data: {starttime: starttime, endtime: endtime},
			success: function(result){
				if(result.code == 200){
					var data = result.data;
					if(data != null){
						var str = 
							'<tr>' +
							'	<td>直播消费提成</td>' +
							'	<td>'+ data.dept +'</td>' +
							'</tr>' +
							'<tr>' +
							'	<td>礼物订单提成</td>' +
							'	<td>'+ data.saler +'</td>' +
							'</tr>' +
							'<tr>' +
							'	<td>总计提成</td>' +
							'	<td>'+ data.count +'</td>' +
							'</tr>';
						$("#all tbody").html(str);
					}else{
						var str = 
							'<tr>' +
							'	<td>直播消费提成</td>' +
							'	<td>0</td>' +
							'</tr>' +
							'<tr>' +
							'	<td>礼物订单提成</td>' +
							'	<td>0</td>' +
							'</tr>' +
							'<tr>' +
							'	<td>总计提成</td>' +
							'	<td>0</td>' +
							'</tr>';
						$("#all tbody").html(str);
					}
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			}
		});
	}
	
	wage($("#datemin").val(), $("#datemax").val());
	
	$("#search").click(function() {
		var deptid = $.trim($("select[name='deptid']").val());
		wage($("#datemin").val(), $("#datemax").val());
	})
	
});
</script> 
</body>
</html>