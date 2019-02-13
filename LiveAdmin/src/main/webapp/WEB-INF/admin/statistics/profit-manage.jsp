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
<title>分润管理</title>
</head>
<body>
<div id="modal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">设置分润比率</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">代理商分润比例（%）：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写代理商分润比例" name="agentprofit">
							<span style="color: #c62b26;">填写0-100之间的数字，且两级分润总和不大于100</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">业务员分润比例（%）：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写业务员分润比例" name="salerprofit">
							<span style="color: #c62b26;">填写0-100之间的数字，且两级分润总和不大于100</span>
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 分润管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th colspan="2">
						课程分润设置 
						<input class="btn radius btn-secondary-outline r" type="button" data-agent="${agent }" data-saler="${saler }" value="设定新值">
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>代理商分润比率（%）</td>
					<td>${agent }</td>
				</tr>
				<tr>
					<td>业务员分润比率（%）</td>
					<td>${saler }</td>
				</tr>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript">
$(function(){
	$("th input").click(function() {
		$("#modal input[name='agentprofit']").val($(this).attr('data-agent'));
    	$("#modal input[name='salerprofit']").val($(this).attr('data-saler'));
		$("#modal").modal('show');
	})
	
	//修改
    $("#modal .confirm").click(function name() {
    	var agentprofit = parseInt($.trim($('#modal input[name="agentprofit"]').val()));
    	if(agentprofit === '' || !(/^[0-9]\d*$/.test(agentprofit)) || agentprofit >= 100){
			layer.msg('请填写正确的代理商分润比例！',{icon: 7,time:2000});
			return false;
		}
    	var salerprofit = parseInt($.trim($('#modal input[name="salerprofit"]').val()));
    	if(salerprofit === '' || !(/^[0-9]\d*$/.test(salerprofit)) || salerprofit >= 100){
			layer.msg('请填写正确的业务员分润比例！',{icon: 7,time:2000});
			return false;
		}
    	if(agentprofit + salerprofit >= 100){
			layer.msg('两级分润比例不正确！',{icon: 7,time:2000});
			return false;
		}
    	//检测数据通过
		$.ajax({
            url: 'setvipprofit',
            type: "POST",
            data: {agent: agentprofit, saler: salerprofit},
            success: function (result) {
           		layer.msg("修改成功",{icon: 1,time:2000});
           		document.location.reload();
            }
        })
	})
	
});

</script> 
</body>
</html>