﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<style type="text/css">
	table#datatable td.course{
		text-align: left;
	}
</style>
<title>课程订单列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 直播消费 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">用户账号</option>
		    <option value="2">用户昵称</option>
		    <!-- <option value="7">业务员</option> -->
		    <option value="3">订单号</option>
		    <option value="4">支付状态</option>
		    <option value="5">订单状态</option>
		    <option value="6">下单时间</option>
		  </select>
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-paystatus hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="paystatus" size="1">
					<option value="0">待支付</option>
					<option value="1">已支付</option>
				</select>
			</span>
		</span>
		<span class="search-param search-ostatus hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="ostatus" size="1">
					<option value="0">正常</option>
					<option value="9">已删除</option>
				</select>
			</span>
		</span>
		<span class="search-param search-date hidden">
			日期范围：
			<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
			-
			<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;">
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜订单</button>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>用户账号</th>
					<th>用户昵称</th>
					<th>课程订单号</th>
					<th>订单实付款（元）</th>
					<th>支付状态</th>
					<th>下单时间</th>
					<th>支付时间</th>
					<th>订单有效期</th>
					<th>订单状态</th>
					<th>支付方式</th>
					<th>代金券名称</th>
					<th>优惠金额（元）</th>
					<th>订单课程</th>
					<th>业务员</th>
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
			url: "orderlist",
			type: "POST",
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
            {"data": 'telno'},
            {"data": 'nickname'},
            {"data": 'orderno'},
            {"data": 'money'},
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.paystatus == 0){
	                	return '<span class="label label-danger radius">待支付</span>';
                	}else {
	                	return '<span class="label label-success radius">已支付</span>';
                	}
                }
            },
            {"data": 'createtime'},
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.paystatus == 0){
                		return '-'
                	}else{
	                	return full.paytime;
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.paystatus == 0){
                		return '-'
                	}else{
	                	return full.validitytime;
                	}
                }
            },
			{
                "data": null,
                "mRender": function (data, type, full, meta) {
                	var str = '';
                	if(full.paystatus == 1) str = '生效中 - ';
                	else if(full.paystatus == 2) str = '已到期 - ';
                	
                	if(full.entkbn == 0){
                		return '<span class="label label-success radius">'+ str +'正常</span>';
                	}else if(full.entkbn == 9){
                		return '<span class="label label-default radius">'+ str +'已删除</span>';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.paystatus != 0){
                		if(full.paytype == 1) return '支付宝';
                		else return '微信';
                	}else{
                		return '-'
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.paystatus != 0){
                		if(full.cpname == null)
	                		return '-';
                		else
	                		return full.cpname;
                	}else{
                		return '-'
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.paystatus != 0){
                		if(full.price == null)
	                		return '-';
                		else
	                		return full.price;
                	}else{
                		return '-'
                	}
                }
            },
            {
                "data": null,
                "class": "course",
                "mRender": function (data, type, full, meta) {
                	return full.coursename.replace(/,/g, '<br/>');
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.realname == null) return '-';
                	else return full.realname;
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
    		//用户账号
    		var telno = $.trim($(".search-text input").val());
    		if(telno == '') return false;
    		param = {telno: telno};
    	}else if(sel == 2){
    		//用户昵称
    		var nickname = $.trim($(".search-text input").val());
    		if(nickname == '') return false;
    		param = {nickname: nickname};
    	}else if(sel == 3){
    		//订单号
    		var orderid = $.trim($(".search-text input").val());
    		if(orderid == '') return false;
    		param = {orderid: orderid};
    	}else if(sel == 4){
    		//支付状态
    		var paystatus = $('select[name="paystatus"]').val();
    		param = {paystatus: paystatus};
    	}else if(sel == 5){
    		//订单状态
    		var ostatus = $('select[name="ostatus"]').val();
    		param = {ostatus: ostatus};
    	}else if(sel == 6){
    		//下单时间
    		var starttime = $.trim($("#datemin").val());
    		var endtime = $.trim($("#datemax").val());
    		if(starttime == '' || endtime == '') return false;
    		param = {starttime: starttime, endtime: endtime};
    	}else if(sel == 7){
    		//业务员
    		var realname = $.trim($(".search-text input").val());
    		if(realname == '') return false;
    		param = {realname: realname};
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
		}else if(val == 1 || val == 2 || val == 3 || val== 7){
    		//输入框
    		$(".search-text").removeClass("hidden");
    	}else if(val == 4){
    		//支付状态
    		$(".search-paystatus").removeClass("hidden");
    	}else if(val == 5){
    		//订单状态
    		$(".search-ostatus").removeClass("hidden");
    	}else if(val == 6){
    		//下单时间
    		$(".search-date").removeClass("hidden");
    	}
	})
    
});
</script> 
</body>
</html>