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
<title>用户分类管理</title>
</head>
<body>
<div id="addModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加用户分类</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="addform" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">分类名称：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写分类名称" name="classname">
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
				<h3 class="modal-title">修改用户分类</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="addform" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">分类名称：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="hidden" name="id">
							<input type="text" class="input-text" placeholder="请填写分类名称" name="classname">
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 用户分类管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a> 
		</span> 
		<span class="r">
			<a href="javascript:;" class="btn btn-primary radius" data-toggle="modal" data-target="#addModal">
				<i class="Hui-iconfont">&#xe600;</i> 添加类型
			</a>
		</span> 
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>类型名称</th>
					<th>操作</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script> 
<script type="text/javascript">
$(function(){
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "memberclassifylist",
			type: "POST",
			data: {role: 1}
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
            {"data": 'name'},
            {
                "data": null,
                "render": function (base_data, type, full) {
                     var str = 
	                    '<a title="编辑" href="javascript:;" class="ml-5 class-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="删除" href="javascript:;" class="ml-5 class-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    
    $('#addModal').on('shown.bs.modal', function () {
  	  $('#addModal input[name="classname"]').val('')
  	})
  	
    //添加分类
    $("#addModal .confirm").click(function name() {
    	var classname = $.trim($('#addModal input[name="classname"]').val());
    	if(classname == ''){
			layer.msg('请填写分类名称！',{icon: 7,time:2000});
			return false;
		}
    	$.ajax({
            url: 'addmemberclassify',
            type: "POST",
            data: {classname: classname},
            success: function (result) {
            	if(result.code == 200){
            		layer.msg("添加成功",{icon: 1,time:2000});
            		$("#addModal").modal('hide');
            		jq_table.ajax.reload(null, false);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
        })
    	
    })
    
    
    //删除
    $("body").on("click", "a.class-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除此分类？删除后此分类下的客户将不设分类！', {icon: 3, title:'提示'}, function(index){
    		betchdel('delclassify', [sData.id], jq_table);
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
    		var userids = [];
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			userids.push(sData.id);
			})
			layer.confirm('确定批量删除用户？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delclassify', userids, jq_table);
	    	});
    	}
	})
    
    //类修改
    $("body").on("click", "a.class-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	$("#editModal input[name='id']").val(sData.id);
    	$("#editModal input[name='classname']").val(sData.name);
    	$("#editModal").modal('show');
    })
    //修改分类
    $("#editModal .confirm").click(function name() {
    	var classname = $.trim($('#editModal input[name="classname"]').val());
    	if(classname == ''){
			layer.msg('请填写分类名称！',{icon: 7,time:2000});
			return false;
		}
    	$.ajax({
            url: 'editmemberclassify',
            type: "POST",
            data: {classname: classname, id: $('#editModal input[name="id"]').val()},
            success: function (result) {
            	if(result.code == 200){
            		layer.msg("修改成功",{icon: 1,time:2000});
            		$("#editModal").modal('hide');
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