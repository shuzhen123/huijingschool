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
<title>合规列表</title>
</head>
<body>

<div id="addModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加合规处罚项</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="form" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频类型：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width: auto;">
								<select class="select" name="type" size="1">
									<option value="1" >罚款</option> 
									<option value="2" >封号</option>
								</select>
							</span>
						</div>
					</div>
					<div class="row cl money">
						<label class="form-label col-xs-4 col-sm-3">金额：</label>
						<div class="formControls col-xs-4 col-sm-4">
							<input type="text" class="input-text" placeholder="请填写处罚金额" name="money">
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
<div id="editModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加合规处罚项</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="editform" >
					<input type="text" class="hidden" name="id">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">视频类型：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width: auto;">
								<select class="select" name="type" size="1">
									<option value="1" >罚款</option> 
									<option value="2" >封号</option>
								</select>
							</span>
						</div>
					</div>
					<div class="row cl money">
						<label class="form-label col-xs-4 col-sm-3">金额：</label>
						<div class="formControls col-xs-4 col-sm-4">
							<input type="text" class="input-text" placeholder="请填写处罚金额" name="money">
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 合规管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		<span class="r">
			<a href="javascript:;" class="btn btn-secondary radius add-compliance" data-toggle="modal" data-target="#addModal">
				<i class="Hui-iconfont">&#xe600;</i> 添加新规
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>处罚方式</th>
					<th>处罚金额</th>
					<th>添加时间</th>
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
			url: "compliancelist",
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
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.type == 1){
                		return '罚款';
                	}else if(full.type == 2){
                		return '封号';
                	}else{
                		return '未知';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.type == 1){
                		return full.money;
                	}else{
                		return '-';
                	}
                }
            },
            {"data": 'createtime'},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = 
	                    '<a title="编辑" href="javascript:;" class="ml-5 compliance-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="删除" href="javascript:;" class="ml-5 compliance-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    
    //删除
    $("body").on("click", "a.compliance-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除此合规？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delcompliance', [sData.id], jq_table);
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
    		var userids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			userids[i] = sData.id;
    			i++
			})
			layer.confirm('确定批量删除业务员？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delcompliance', userids, jq_table);
	    	});
    	}
    	
	})
    
	$('select[name="type"]').change(function() {
		var sel = $(this).val();
		if(sel == 1){
			$('.money').removeClass('hidden');
		}else{
			$('.money').addClass('hidden');
		}
	})
	//添加合规项
    $("#addModal .confirm").click(function () {
    	var sel = $('#addModal select[name="type"]').val(), price;
    	if(sel == 1){
	    	price = parseFloat($.trim($('#addModal input[name="money"]').val()));
	    	if(price == '' || !(/^[0-9]+\.?[0-9]*$/.test(price)) || price <= 0){
				layer.msg('请填写正确的金额！',{icon: 7,time:2000});
				return false;
			}
    	}
    	//检测数据通过
		$.ajax({
            url: 'addcompliance',
            type: "POST",
            data: {type: sel, money: price},
            success: function (result) {
            	if(result.code == 200){
            		layer.msg("添加成功",{icon: 1,time:2000});
            		$("#addModal").modal('hide');
            		document.getElementById('form').reset()
            		jq_table.ajax.reload(null, false);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
        })
	})
    
    //修改合规
    $("body").on('click', 'a.compliance-edit', function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	console.log(sData);
    	$("#editModal").find('input[name="id"]').val(sData.id);
    	$("#editModal").find('select option[value="'+ sData.type +'"]').prop('selected', true);
    	$("#editModal").find('input[name="id"]').val(sData.id);
    	if(sData.type == 2){
    		$("#editModal div.money").addClass('hidden')
    	}else{
    		$("#editModal div.money").removeClass('hidden')
    		console.log($('#editModal input[name="money"]'));
    		$('#editModal input[name="money"]').val(sData.money);
    	}
    	$("#editModal").modal('show');
	})
	
	//修改动作
	$("#editModal .confirm").click(function () {
    	var sel = $('#editModal select[name="type"]').val(), price;
    	if(sel == 1){
	    	price = parseFloat($.trim($('#editModal input[name="money"]').val()));
	    	if(price == '' || !(/^[0-9]+\.?[0-9]*$/.test(price)) || price <= 0){
				layer.msg('请填写正确的金额！',{icon: 7,time:2000});
				return false;
			}
    	}
    	//检测数据通过
		$.ajax({
            url: 'editcompliance',
            type: "POST",
            data: {id: $('#editModal input[name="id"]').val(), type: sel, money: price},
            success: function (result) {
            	if(result.code == 200){
            		layer.msg("添加成功",{icon: 1,time:2000});
            		$("#editModal").modal('hide');
            		document.getElementById('editform').reset()
            		jq_table.ajax.reload(null, false);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
        })
	})
});
</script> 
</body>
</html>