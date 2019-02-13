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
<div id="addModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">通话记录处理</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="form" >
					<input type="text" class="hidden" name="salerid">
					<input type="text" class="hidden" name="tellid">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">通话名称：</label>
						<div class="formControls col-xs-8 col-sm-7 tellname"></div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">违规业务员：</label>
						<div class="formControls col-xs-8 col-sm-7 repsaler"></div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">处罚方式：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width:auto;">
								<select class="select" name="methodid" size="1">
									<c:forEach var="item" items="${method }">
										<option value="${item.id}">${item.name }</option> 
									</c:forEach>
								</select>
							</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">违规详情：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<textarea name="content" class="textarea"  placeholder="请填写违规内容"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="confirm">确定</button>
				<button class="hidden" id="confirm-video"></button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 通话记录管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">业务员名称</option>
		    <option value="2">客户手机号</option>
		    <option value="3">客户昵称</option>
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
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜通话记录</button>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>业务员名称</th>
					<th>客户电话</th>
					<th>昵称</th>
					<th>通话名称</th>
					<th>通话时长（s）</th>
					<th>拨通时间</th>
					<th>挂机时间</th>
					<th>通话录音</th>
					<th>违规记录</th>
					<th>记录创建时间</th>
					<th>处理</th>
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
			{"data": 'realname'},
			{"data": 'account'},
			{"data": 'nickname'},
			{
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.status == 0){
                		return '-';
                	}else{
                		return full.voicetitle;
                	}
                }
            },
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
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.status == 0 || full.status == 1){
                		return '-';
                	}else if(full.status == 2){
                		if(full.count > 0){
    	               		return '<button class="btn btn-primary size-MINI violator" type="button">查看记录（'+ full.count +'条）</button>';
                    	}else{
    	               		return '无记录';
                    	}
                	}else{
	                	return '-';
                	}
                }
            },
            {"data": "createtime"},
            {
                "data": null,
                "render": function (base_data, type, full) {
                	if(full.status == 2){
                		return '<a title="处理" href="javascript:;" class="ml-5 tell-deal" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ';
                	}else{
                		return '-';
                	}
                    
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    var param = undefined;
    //条件搜索
    $("#search").click(function() {
    	var sel = $("select.sel").val();
    	if(sel == 0){
    		//恢复默认
    		var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				param = undefined;
			}else return false;
    	}else if(sel == 1){
    		//业务员名称
    		var realname = $.trim($(".search-text input").val());
    		if(realname == '') return false;
    		param = {realname: realname};
    	}else if(sel == 2){
    		//客户手机号
    		var telno = $.trim($(".search-text input").val());
    		if(telno == '') return false;
    		param = {telno: telno};
    	}else if(sel == 3){
    		//客户昵称
    		var nickname = $.trim($(".search-text input").val());
    		if(nickname == '') return false;
    		param = {nickname: nickname};
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
		}else if(val == 1 || val == 2 || val == 3){
    		//输入框
    		$(".search-text input").val('');
    		$(".search-text").removeClass("hidden");
    	}else if(val == 4){
    		//通话状态选择框
    		$(".search-status").removeClass("hidden");
    	}else if(val == 5){
    		//时间input展示
    		$(".search-date").removeClass("hidden");
    	}
	})
    
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
	
    //记录处理框
	$("body").on("click", "a.tell-deal", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	$("#addModal input[name='salerid']").val(sData.salerid);
    	$("#addModal input[name='tellid']").val(sData.id);
    	$("#addModal .tellname").html(sData.voicetitle);
    	$("#addModal .repsaler").html(sData.realname);
    	$("#addModal textarea[name='content']").val('')
    	$("#addModal").modal('show');
    })
    //确定违规处理
    $("#addModal #confirm").click(function() {
		var cause = $.trim($("#addModal textarea[name='content']").val());
		if(cause == ''){
			layer.msg('请填写违规原因！',{icon: 5,time:1500});return false;
		}
		$.ajax({
            url: 'addviolatorinfo',
            type: "POST",
            data: {
            	saleid: $("#addModal input[name='salerid']").val(), 
            	salecustomer_tellog_id: $("#addModal input[name='tellid']").val(), 
            	handlemethodid: $("#addModal select[name='methodid']").val(),
            	detail: cause
            },
            success: function (result) {
            	if(result.code == 200){
					layer.msg('操作成功！',{icon: 1,time:2000});
					jq_table.ajax.reload();
					$("#addModal").modal('hide');
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
		})
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