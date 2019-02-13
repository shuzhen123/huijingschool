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
<title>岗位管理</title>
</head>
<body>
<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加新岗位门</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="form" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">新岗位名称：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写岗位名称" name="deptname">
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
				<h3 class="modal-title">修改岗位名称</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="editform" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">岗位名称：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写部门名称" name="deptname">
							<input type="text" class="hidden" name="id">
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
<div id="changeModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<!-- <div class="modal-header">
				<h3 class="modal-title">停用部门</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div> -->
			<div class="modal-body">
				<form class="form form-horizontal" id="editform" >
					<div class="row cl">
						<div class="formControls col-xs-10 col-sm-10 content-area col-md-offset-2">
						</div>
						<input type="text" class="hidden" name="olddeptid">
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 岗位管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		<span class="r">
			<a href="javascript:;" class="btn btn-secondary radius"  data-toggle="modal" data-target="#myModal">
				<i class="Hui-iconfont">&#xe600;</i> 添加新岗位
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>岗位名称</th>
					<!-- <th>员工人数</th> -->
					<th>添加时间</th>
					<!-- <th>启/禁用</th> -->
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
			url: "deptlist",
			type: "POST",
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.deptenable == 0)
                    return '<input type="checkbox" value="1" name="">';
                    else
                    return '';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
            {"data": 'deptname'},
            /* {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.count == null){
                		return 0;
                	}else{
                		return full.count;
                	}
                }
            }, */
            {"data": 'createtime'},
           /*  {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.deptenable == 0){
                		return '<span class="label label-default  radius">已禁用</span>';
                	}else if(full.deptenable == 1){
                		return '<span class="label label-success radius">使用中</span>';
                	}
                }
            }, */
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = 
	                    '<a title="编辑" href="javascript:;" class="ml-5 dept-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ';
	                if(full.deptenable == 0)
	                	str += 
	                		'<a title="点击启用" href="javascript:;" class="ml-5 dept-enable" style="text-decoration:none">' +
		                    '	<i class="Hui-iconfont" style="color: #5eb95e;">&#xe6e1;</i>' +
		                    '</a> ' +
		                    '<a title="删除" href="javascript:;" class="ml-5 dept-del" style="text-decoration:none">' +
		                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
		                    '</a>';
	                else if(full.deptenable == 1)
	                	str += 
	                		'<a title="点击禁用" href="javascript:;" class="ml-5 dept-enable" style="text-decoration:none">' +
		                    '	<i class="Hui-iconfont" style="color: #999;">&#xe631;</i>' +
		                    '</a> ';
	            	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    
   
    
    //删除
    $("body").on("click", "a.dept-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除此部门？', {icon: 3, title:'提示'}, function(index){
    		betchdel('deldept', [sData.deptid], jq_table);
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
    		var ids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			ids[i] = sData.deptid;
    			i++
			})
			layer.confirm('确定批量删除部门？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('deldept', ids, jq_table);
	    	});
    	}
    	
	})
    //添加新部门
    $("#myModal .confirm").click(function() {
    	var name = $.trim($("#myModal input[name='deptname']").val());
    	if(name == ''){
    		layer.msg('请填写部门名称！',{icon: 7,time:2000}); return false;
    	}
    	var sDatas = api_table.fnGetData();
    	if(sDatas.length > 0){
    		var bool = false;
    		$.each(sDatas, function(k, v) {
				if(v.deptname == name){
					bool = true;
					return false;
				}
			})
			if(bool){
				layer.msg('类型名称已重复！',{icon: 7,time:2000}); return false;
			}
    	}
    	//检测通过
    	$.ajax({
			type: 'POST',
			url: 'adddept',
			data: {name: name},
			success: function(data){
				if(data.code == 200){
					$("#myModal").modal('hide');
					$("#myModal input[name='deptname']").val('');
					layer.msg('操作成功!',{icon: 6,time:1000});
					jq_table.ajax.reload(null, false);
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			},
			error:function(data) {
				layer.msg(data.msg,{icon: 5,time:2000});
			},
		});
    })
    //修改部门
    $("body").on("click", "a.dept-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	$("#editModal input[name='deptname']").val(sData.deptname);
    	$("#editModal input[name='id']").val(sData.deptid);
    	$("#editModal").modal('show');
    })
    $("#editModal .confirm").click(function() {
    	var id = $("#editModal input[name='id']").val(), name = $.trim($("#editModal input[name='deptname']").val());
    	if(name == ''){
    		layer.msg('请填写部门名称！',{icon: 7,time:2000}); return false;
    	}
    	var sDatas = api_table.fnGetData(), bool = false;
   		$.each(sDatas, function(k, v) {
			if(v.deptid != id && v.coursetypename == name){
				bool = true;
				return false;
			}
		})
		if(bool){
			layer.msg('部门名称重复！',{icon: 7,time:2000}); return false;
		}
    	//检测通过
    	$.ajax({
			type: 'POST',
			url: 'editdept',
			data: {deptid: id, deptname: name},
			success: function(data){
				if(data.code == 200){
					$("#editModal").modal('hide');
					layer.msg('操作成功!',{icon: 6,time:2000});
					jq_table.ajax.reload(null, false);
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			},
			error:function(data) {
				layer.msg(data.msg,{icon: 5,time:2000});
			},
		});
    })
});
</script> 
</body>
</html>