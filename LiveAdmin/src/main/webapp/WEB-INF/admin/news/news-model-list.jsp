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
<title>资讯模块列表</title>
</head>
<body>

<div id="modal-demo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title"></h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">模块名称：</label>
						<div class="formControls col-xs-6 col-sm-6">
							<input type="text" class="input-text" placeholder="请填写模块名称" name="newskindname">
						</div>
						<span class="error-info" style="color: #c62b26;"></span>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn accept btn-primary" data-loading-text="提交中...">确定</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 资讯模块管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		<span class="r">
			<a href="javascript:;" class="btn btn-primary radius add-model-btn">
				<i class="Hui-iconfont">&#xe600;</i> 添加模块
			</a>
		</span>
	</div>
	<div class="mt-20">
	<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th><input type="checkbox" name="" value=""></th>
				<th>序号</th>
				<th>模块名称</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootbox/bootbox.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "newsmodellist.do",
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
            {"data": "newskindname"},
            {
                "data": null,
                "render": function (base_data, type, full) {
                     var str = 
	                    '<a title="编辑" href="javascript:;" class="ml-5 model-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="删除" href="javascript:;" class="ml-5 user-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
	var api_table = $('#datatable').dataTable();
	
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
    //单个删除
    $("body").on("click", "a.user-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除此模块？删除后期模块下的所有资讯信息将被删除！', {icon: 3, title:'删除'}, function(index){
    		betchdel('delnewsmodel.do',[sData.informationid], jq_table);
    	});
    });
    
    //批量删除
    $(".batch-del").click(function() {
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
    	if(cblist.length > 0){
    		var modelids = [];
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			modelids.push(sData.informationid);
			})
			layer.confirm('确定批量删除模块？删除后期模块下的所有资讯信息将被删除！', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delnewsmodel.do',modelids, jq_table);
	    	});
    	}
    	
	})
	
	//modal框关闭清楚数据
	$('#modal-demo').on('hidden.bs.modal', function () {
		$('.modal-title').html('');
	  	$('#modal-demo .error-info').html("");
	  	$('#modal-demo input').val("");
	  	$('#modal-demo .accept').removeClass("add-model edit-model");
	})
		
	//添加模块输入框
	$(".add-model-btn").click(function() {
		$('.modal-title').html('添加模块');
		$('.accept').addClass('add-model');
		$('#modal-demo').modal('show');
	});
    //确认添加
    $('#modal-demo').on('click', '.add-model', function() {
		var newname = $.trim($('#modal-demo input').val());
		if(newname == '') return;
		$.ajax({
			type: 'POST',
			url: 'addnewsmodel.do',
			data: {modelname: newname},
			beforeSend: function() {
				$('#modal-demo .accept').button('loading');
			},
			success: function(data){
				if(data.code == 200){
					$('#modal-demo').modal('hide');
					layer.msg('已添加!',{icon: 6,time:1000});
					jq_table.ajax.reload(null, false);
				}else{
					$('#modal-demo .error-info').html(data.msg);
				}
			},
			complete: function() {
				$('#modal-demo .accept').button('reset');
			},
			error:function(data) {
				layer.msg(data.msg,{icon: 5,time:1500});
			},
		});
	});
    
    
    //修改模块输入框
    $("body").on("click", "a.model-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
	  	$('.modal-title').html('修改模块');
	  	$('.accept').addClass('edit-model');
		$('#modal-demo input').val(sData.newskindname);
		$('#modal-demo input').data('data', sData.newskindname);
		$('#modal-demo input').data('id', sData.informationid);
  		$('#modal-demo').modal('show');
    })
  	//确认修改
    $('#modal-demo').on('click', '.edit-model', function() {
		var newname = $.trim($('#modal-demo input').val());
		if(newname == '' || newname == $('#modal-demo input').data('data')) return;
		$.ajax({
			type: 'POST',
			url: 'editnewsmodel.do',
			data: {modelname: newname, id: $('#modal-demo input').data('id')},
			beforeSend: function() {
				$('#modal-demo .accept').button('loading');
			},
			success: function(data){
				if(data.code == 200){
					$('#modal-demo').modal('hide');
					layer.msg('已修改!',{icon: 6,time:1000});
					jq_table.ajax.reload(null, false);
				}else{
					$('#modal-demo .error-info').html(data.msg);
				}
			},
			complete: function() {
				$('#modal-demo .accept').button('reset');
			}
		});
	});
});

</script> 
</body>
</html>