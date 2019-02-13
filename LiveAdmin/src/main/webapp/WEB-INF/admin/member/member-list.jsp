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
<style type="text/css">
	table#datatable tbody tr td.info_row {
	    text-align: left;
	}
</style>
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">用户昵称</option>
		    <option value="2">手机号码</option>
		    <option value="3">注册时间</option>
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
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<!-- <span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a> 
			<a href="javascript:;" onclick="member_add('添加用户','adduserpage.do','','510')" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe600;</i> 添加用户
			</a>
		</span> --> 
		<span class="r">
			<a href="javascript:;" class="btn btn-default radius export" title="导出到Excel"><i class="Hui-iconfont">&#xe644;</i></a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>手机号</th>
					<th>昵称</th>
					<th>姓名</th>
					<th>性别</th>
					<th>省份</th>
					<th>头像</th>
					<th>等级</th>
					<th>风险评测</th>
					<th>备注</th>
					<th>注册时间</th>
					<th>所属代理商</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script> 
<script type="text/javascript">
$(function(){
	//导出Excel 
	$(".export").click(function() {
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'exportmembertoexcel', true);    // 也可以使用POST方式，根据接口
		xhr.responseType = "blob";  // 返回类型blob
		// 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
		xhr.onload = function (e) {
			// 请求完成
			if (this.status === 200) {
			  // 返回200
			  var blob = this.response;
			  var reader = new FileReader();
			  reader.readAsDataURL(blob);  // 转换为base64，可以直接放入a表情href
			  reader.onload = function (e) {
			  	// 转换完成，创建一个a标签用于下载
			    var a = document.createElement('a');
			    a.download = '用户信息-'+ new Date().format("yyyyMMddhhmmss") +'.xlsx';
			    a.href = e.target.result;
			    $("body").append(a);  // 修复firefox中无法触发click
			    a.click();
			    $(a).remove();
			  }
			}else if(this.status === 900){
				layer.open({
					title: '无法下载',
					content: '暂无可用数据提供下载！'
				});
			}
		};
		// 发送ajax请求
		xhr.send()
	})
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "memberlist",
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
            {"data": 'realname'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.sex == 1) return '男';
                	else if(full.sex == 2) return '女';
                	else return '未填写';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.prov == null || full.prov == '' ) return '未填写';
                	else return full.prov;
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	return '<img src="'+ full.iconurl +'" class="avatar size-M radius" alt="icon">';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                    if (full.levelname == null) {
                        return '<span class="label label-default radius">未设置</span>';
                    } else {
                        return '<span class="label label-secondary radius">'+full.levelname+'</span>';
                    }
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                    if (full.riskratingfalg == 1) {
                        //return '<span class="label label-success radius">已评测</span>';
                    	return '<button class="btn btn-primary size-MINI agent-teacher" type="button">已评测</button>'
                    } else {
                        return '<span class="label label-default radius">未评测</span>';
                    }
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                    if (full.remark == null || full.remark == '') {
                        return '未填写';
                    } else {
                        return '<button class="btn btn-primary size-MINI remark" type="button">查看备注</button>';
                    }
                }
            },
            {"data": 'registertime'},
            {"data": 'agentname'},
            {
                "data": null,
                "render": function (base_data, type, full) {
                     var str = 
                    	'<a style="text-decoration:none" class="ml-5 user-detail" href="javascript:;" title="查看详情">' +
	                    '	<i class="Hui-iconfont">&#xe6e0;</i>' +
	                    '</a> ' +
	                    '<a title="编辑" href="javascript:;" class="ml-5 user-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' + 
	                    '<a style="text-decoration:none" class="ml-5 user-chat-info" href="javascript:;" title="查看客户跟踪记录">' +
	                    '	<i class="Hui-iconfont">&#xe715;</i>' +
	                    '</a> '
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
    		//使用用户名搜索
    		var nickname = $.trim($(".search-text input").val());
    		if(nickname == '') return false;
    		param = {nickname: nickname};
    	}else if(sel == 2){
    		//使用手机号码搜索
    		var phone = $(".search-text input").val();
    		if(phone == '') return false;
    		param = {phone: phone};
    	}else if(sel == 3){
    		//使用注册时间搜索
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
	$("select.select").change(function() {
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
    		//用户名
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3){
    		//input展示
    		$(".search-date").removeClass("hidden");
    	}
	})
    
    //用户详情
    $("body").on("click", "a.user-detail", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('用户详情', "${pageContext.request.contextPath}/saler/userdatapage?type=detail&userid=" +sData.userid,'','510');
    })
    //用户修改
    $("body").on("click", "a.user-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('信息修改','${pageContext.request.contextPath}/saler/userdatapage?type=edit&userid='+sData.userid,'','510');
    })
    //查看备注
	$("body").on('click', 'button.remark', function() {
		var obj = $(this).parents('tr');
        if (api_table.fnIsOpen(obj)) {
            api_table.fnClose(obj);
        } else {
            var sData = api_table.fnGetData(obj);
            api_table.fnOpen(obj, sData.remark, "info_row order-info");
        }
	})
	//查看客户跟踪记录
    $("body").on("click", "a.user-chat-info", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var index = layer.open({
    		type: 2,
    		title: '客户跟踪记录',
    		content: "${pageContext.request.contextPath}/saler/customerchatPage?userid=" +sData.userid
    	});
    	layer.full(index);
    })
    
     //评测列表
    $("body").on("click", "button.agent-teacher", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('评测结果', "${pageContext.request.contextPath}/saler/userEvaluationResultPage?userid=" +sData.userid,'','550');
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