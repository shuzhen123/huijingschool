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
<title>业务员任务列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 业务员任务管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			查看：
			年<select class="select" id="earnings-year" size="1" style="width:auto;">
				<option value="2018">2018</option> 
				<option value="2019">2019</option> 
				<option value="2020">2020</option> 
			</select>
			月<select class="select" id="earnings-month" size="1" style="width:auto;">
				<option value="1">1</option> 
				<option value="2">2</option> 
				<option value="3">3</option> 
				<option value="4">4</option> 
				<option value="5">5</option> 
				<option value="6">6</option> 
				<option value="7">7</option> 
				<option value="8">8</option> 
				<option value="9">9</option> 
				<option value="10">10</option> 
				<option value="11">11</option> 
				<option value="12">12</option> 
			</select>
		</span>
	</div>
	<div class="mt-20 dt">
		<!-- <table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table> -->
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	var myDate = new Date(), year = myDate.getFullYear(), month = myDate.getMonth()+1;
	$("#earnings-year option[value="+ year +"]").attr('selected', true);
	$("#earnings-month option[value="+ month +"]").attr('selected', true);
	
	$.extend($.fn.dataTable.defaults, {
        "processing": false,
        "bServerSide": false,
    });
	var table;
	var loaddata = function(year, month) {
		$.ajax({
	        url: 'salertasklist',
	        type: "POST",
	        data: {year: year, month: month},
	        success: function (result) {
	        	if(result.code == 200){
	        		if(table !== undefined){
        				table.destroy();
        				$('#datatable').empty();
        			}
	        		if(result.data.htab.length > 0){
	        			var str = 
	        				'<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">' +
	        				'	<thead>' +
	        				'		<tr class="text-c">' +
	        				'			<th></th>';
	        			$.each(result.data.htab, function(k, v) {
	        				str += '<th>'+ v.substring(5) +'</th>';
						})
						str += '<th>本月已完成/总任务</th></thead><tbody>';
	        			$.each(result.data.btab, function(k, data) {
	        				str += '<tr>';
		        			$.each(data, function(k1, v) {
		        				str += '<td>'+ v +'</td>';
							})
							str += '</tr>';
						})
						str += '</tbody></table>';
						$(".dt").html(str);
	        			table = $("#datatable").DataTable();
	        		}else{
	        			$(".dt").html('<div>无数据</div>');
	        		}
	        	}
	        }
		});
	}
	
	loaddata(year, month);
	//日期切换
	$("#earnings-year, #earnings-month").change(function() {
		loaddata($("#earnings-year").val(), $("#earnings-month").val());
	})
	
	
});
</script> 
</body>
</html>