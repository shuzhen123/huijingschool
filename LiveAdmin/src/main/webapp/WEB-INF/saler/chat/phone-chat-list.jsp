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
<title>通话记录管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 通话记录管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">客户手机号</option>
		    <option value="2">客户昵称</option>
		    <!-- <option value="3">收藏</option> -->
		    <option value="4">通话状态</option>
		    <option value="5">创建时间</option>
		  </select>
		</span>
		<span class="search-param search-date hidden">
			日期范围：
			<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%yyyy-%MM-%dd\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
			-
			<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%yyyy-%MM-%dd' })" id="datemax" class="input-text Wdate" style="width:120px;">
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-status hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="status" size="1">
					<option value="0" >未接通</option> 
					<option value="1" >录音待上传</option> 
					<option value="2" >录音已上传</option> 
				</select>
			</span>
		</span>
		<span class="search-param search-collect hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="collect" size="1">
					<option value="1" >已收藏</option> 
					<option value="0" >未收藏</option> 
				</select>
			</span>
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜通话记录</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			业务员通话违规总次数：${count }
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>客户电话</th>
					<th>昵称</th>
					<th>通话名称</th>
					<th>通话时长（s）</th>
					<th>拨通时间</th>
					<th>挂机时间</th>
					<th>通话录音</th>
					<!-- <th>收藏</th> -->
					<th>违规记录</th>
					<th>记录创建时间</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-audioplayer/audioplayer.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootbox/bootbox.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "phonechatlist",
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
			{"data": 'voicetitle'},
          	//0未接听，1已接通，未上传录音，2已接通，已上传录音
			{
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.status == 0){
                		return '未接通';
                	}else if(full.status == 1 || full.status == 2){
                		return full.calltimes;
                	}else{
	                	return '-';
                	}
                }
            },
			{
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.status == 1 || full.status == 2){
                		return full.starttime;
                	}else{
	                	return '-';
                	}
                }
            },
			{
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.status == 1 || full.status == 2){
                		return full.endtime;
                	}else{
	                	return '-';
                	}
                }
            },
			{
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.status == 0){
                		return '-';
                	}else if(full.status == 1){
                		return '待上传';
                	}else if(full.status == 2){
                		return '<i class="btn btn-success-outline size-MINI radius Hui-iconfont saudio" title="播放录音">&#xe6e6;</i>';
                	}else{
	                	return '-';
                	}
                }
            },
            /* {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.collectionflag == 1){
                		return '<i class="Hui-iconfont uncollect" style="color: red; cursor: pointer;" title="已收藏，点击取消收藏">&#xe680;</i>';
                	}else{
                		return '<i class="Hui-iconfont collect" style="cursor: pointer;" title="未收藏，点击收藏">&#xe649;</i>';
                	}
                }
            }, */
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.count > 0){
	                	return '<button class="btn btn-primary size-MINI violator" type="button">查看记录（'+ full.count +'条）</button>';
                	}else{
                		return '无记录';
                	}
                	
                }
            },
            {"data": "createtime"},
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
    		//客户账号
    		var account = $.trim($(".search-text input").val());
    		if(account == '') return false;
    		param = {account: account};
    	}else if(sel == 2){
    		//客户昵称
    		var nickname = $.trim($(".search-text input").val());
    		if(nickname == '') return false;
    		param = {nickname: nickname};
    	}else if(sel == 3){
    		//收藏
    		var collect = $("select[name='collect']").val();
    		param = {collect: collect};
    	}else if(sel == 4){
    		//通话状态
    		var status = $("select[name='status']").val();
    		param = {status: status};
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
    		$(".search-text input").val('');
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3){
    		//收藏选择框
    		$(".search-collect").removeClass("hidden");
    	}else if(val == 4){
    		//通话状态选择框
    		$(".search-status").removeClass("hidden");
    	}else if(val == 5){
    		//时间input展示
    		$(".search-date").removeClass("hidden");
    	}
	})
    
    
    
    //通话记录收藏
    $("#datatable").on('click', 'i.collect', function() {
    	var obj = $(this);
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	collectAction(1, sData.id, obj);
	})
	//通话记录取消收藏
    $("#datatable").on('click', 'i.uncollect', function() {
    	var obj = $(this);
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	collectAction(0, sData.id, obj);
	})
	//收藏、取消收藏动作
	var collectAction = function(action, tellid, obj) {
    	$.ajax({
			type: 'POST',
			url: 'phonechatcollect',
			data: {tellid: tellid, action: action},
			success: function(data){
				if(data.code == 200){
					if(action == 1){
						//置为收藏状态
						obj.replaceWith('<i class="Hui-iconfont uncollect" style="color: red; cursor: pointer;" title="已收藏，点击取消收藏">&#xe680;</i>');
						layer.msg("已收藏",{icon: 1,time:1500});
					}else{
						//置为取消收藏状态
						obj.replaceWith('<i class="Hui-iconfont collect" style="cursor: pointer;" title="未收藏，点击收藏">&#xe649;</i>');
						layer.msg("已取消收藏",{icon: 1,time:1500});
					}
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			}
		});	
	}
	
  	//播放录音
	$("body").on('click', 'i.saudio', function() {
		var obj = $(this).parents('tr');
        if (api_table.fnIsOpen(obj)) {
            api_table.fnClose(obj);
        } else {
            var sData = api_table.fnGetData(obj);
            api_table.fnOpen(obj, '<audio src="'+ sData.voicepath +'" preload="auto" controls></audio>', "info_row order-info");
        }
	})
    
	//查看违规记录
	$("body").on("click", "button.violator", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show(sData.voicetitle+'-违规记录', 'violatorpage?tellid=' +sData.id,'','510');
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