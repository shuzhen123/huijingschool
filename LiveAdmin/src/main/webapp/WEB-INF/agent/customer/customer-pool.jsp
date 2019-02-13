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
<title>客户资源池</title>
</head>
<body>
<div id="restypeModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">切换资源类型</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal">
					<input type="text" class="hidden" name="userid">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">新资源类型：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width:auto;">
								<select class="select" name="restype" size="1">
									<option value="x" >x</option> 
									<option value="y" >y</option> 
									<option value="z" >z</option> 
								</select>
							</span>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="confirm">确定</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 资源池管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">手机号码</option>
		    <option value="2">姓名</option>
		    <option value="3">性别</option>
		  </select>
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-sex hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="sex" size="1">
					<option value="0" >未填写</option> 
					<option value="1" >男</option> 
					<option value="2" >女</option> 
				</select>
			</span>
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜资源</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">
			<a href="javascript:;" class="btn btn-secondary radius batch-import">
				<i class="Hui-iconfont">&#xe645;</i> 导入用户
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>手机号码</th>
					<th>昵称</th>
					<th>姓名</th>
					<th>性别</th>
					<th>省份</th>
					<th>资源类型</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "customerpoollist",
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
                "render": function (base_data, type, full) {
                	if(full.sex == 1)return '男';
                	else if(full.sex == 2) return '女';
                	else return '未填写';
                }
            },
            {"data": 'prov'},
            {"data": 'restype'},
            {"data": 'createtime'},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                	return '<a style="text-decoration:none" class="ml-5 customer-class-edit" href="javascript:;" title="资源类型修改">' +
	                    '	<i class="Hui-iconfont">&#xe61d;</i>' +
	                    '</a> ';
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
    		//使用手机号号搜索
    		var telno = $.trim($(".search-text input").val());
    		if(telno == '') return false;
    		param = {telno: telno};
    	}else if(sel == 2){
    		//使用姓名搜索
    		var realname = $.trim($(".search-text input").val());
    		if(realname == '') return false;
    		param = {realname: realname};
    	}else if(sel == 3){
    		//所属部门
    		var sex = $("select[name='sex']").val();
    		param = {sex: sex};
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
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3){
    		//选择框
    		$(".search-sex").removeClass("hidden");
    	}
	})
    //导入资源
    $('.batch-import').click(function() {
    	layer_show('批量导入手机号','telnobatchimportpage','','510');
	})
	
	//资源类型修改modal
    $("body").on("click", "a.customer-class-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
		$("#restypeModal input[name='userid']").val(sData.userid);
    	$("#restypeModal").modal('show');
    })
    //资源类型修改
    $("#restypeModal #confirm").click(function() {
		;
		$.ajax({
			type: 'post',
			url: 'changeuserrestype',
			data: {userid: $("#restypeModal input[name='userid']").val(), restype: $("#restypeModal select").val()},
			success: function(data){
				if(data.code == 200){
					$("#restypeModal").modal('hide');
					jq_table.ajax.reload(null, false);
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			}
		});
    })
    
    
	
	//显示导入结果
	window.resultShow = function(data) {
    	jq_table.ajax.reload(null, false);
    	var str = '<div>导入成功（<span style="color: #429842;">'+data.success.length+'</span>）<br/>'+data.success.join(',')+'<br/>'+
    			  '号码已注册（<span style="color: #429842;">'+data.failed.length+'</span>）<br/>'+data.failed.join(',')+'<br/>'+
    			  '有效号码（<span style="color: #429842;">'+data.valid.length+'</span>）<br/>'+data.valid.join(',')+'<br/>'+
    			  '无效号码（<span style="color: #429842;">'+data.unvalid.length+'</span>）<br/>'+data.unvalid.join(',') + "</div>";
    	layer.open({
   		  title: '导入结果',
   		  //area: ['500px', window.innerHeight*0.8+"px"],
   		  content: str
   		}); 
    	
	}
    
});
</script> 
</body>
</html>