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
<title>业务员等级管理</title>
</head>
<body>
<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加新等级</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="form" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">等级名称：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写等级名称" name="levelname">
						</div>
					</div>
					<!-- ----------------------------------下添加------------------------------------- -->
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">x型资源：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="xcount">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">y型资源：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="ycount">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">z型资源：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="zcount">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
						</div>
					</div>
					<!-- ----------------------------------上添加------------------------------------- -->
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">数量：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="limitpeople">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
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
				<h3 class="modal-title">修改等级</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="editform" >
					<input type="text" class="hidden" name="id">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">等级名称：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写等级名称" name="levelname">
						</div>
					</div>
					<!-- ----------------------------------下添加------------------------------------- -->
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">x型资源：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="xcount">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">y型资源：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="ycount">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">z型资源：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="zcount">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
						</div>
					</div>
					<!-- ----------------------------------上添加------------------------------------- -->
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">数量：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写数量" name="limitpeople">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
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

<div id="defModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">默认任务数修改</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal">
					<input type="text" class="hidden" name="id">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">当前默认任务数：</label>
						<div class="formControls col-xs-8 col-sm-7 def">
							${count }
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">新数量：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写新数量" name="count">
							<span style="color: #c62b26;">填写整数且数量不能小于0</span>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<span style="color: red; float: left;">注：修改后的数量将于明天凌晨生效</span>
				<button class="btn btn-primary confirm">确定</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 业务员等级管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		
		<span class="dropDown r"> 
 			<!-- <a href="javascript:;" class="btn btn-default radius add-coupon" data-toggle="modal" data-target="#defModal">
				<i class="Hui-iconfont">&#xe61d;</i> 设置默认任务数
			</a> -->
 			<a href="javascript:;" class="btn btn-secondary radius add-coupon" data-toggle="modal" data-target="#myModal">
				<i class="Hui-iconfont">&#xe600;</i> 添加
			</a>
		</span>
		
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>等级名称</th>
					<th>x型资源拾取量</th>
					<th>y型资源拾取量</th>
					<th>z型资源拾取量</th>
					<th>每日推荐用户数量</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "salerlevellist",
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
            {"data": 'levelname'},
            {"data": 'xcount'},
            {"data": 'ycount'},
            {"data": 'zcount'},
            {"data": 'limitpeople'},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a title="编辑" href="javascript:;" class="ml-5 level-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="删除" href="javascript:;" class="ml-5 level-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    
    //卡券等级
    $("body").on("click", "a.level-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除等级？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delsalerlevel', [sData.id], jq_table);
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
    		var couponids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			couponids[i] = sData.id;
    			i++
			})
			layer.confirm('确定批量删除卡券？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delsalerlevel', couponids, jq_table);
	    	});
    	}
    	
	})
    //添加等级
    $("#myModal .confirm").click(function() {
    	var levelname = $.trim($("#myModal input[name='levelname']").val());
    	if(levelname == ''){
    		layer.msg('请填写等级名称！',{icon: 7,time:2000}); return false;
    	}
    	//下添加
    	var xcount = $.trim($("#myModal input[name='xcount']").val());
    	if(xcount === '' || !(/^[0-9]\d*$/.test(xcount))){
    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
    	var ycount = $.trim($("#myModal input[name='ycount']").val());
    	if(ycount === '' || !(/^[0-9]\d*$/.test(ycount))){
    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
    	var zcount = $.trim($("#myModal input[name='zcount']").val());
    	if(zcount === '' || !(/^[0-9]\d*$/.test(zcount))){
    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
    	//上添加
    	
    	var limitpeople = $.trim($("#myModal input[name='limitpeople']").val());
    	if(limitpeople === '' || !(/^[0-9]\d*$/.test(limitpeople))){
    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
    	//检测通过
    	$.ajax({
			type: 'POST',
			url: 'addsalerlevel',
			data: {levelname: levelname, xcount:xcount, ycount:ycount, zcount:zcount,limitpeople: limitpeople},//此处添加
			success: function(data){
				if(data.code == 200){
					$("#myModal").modal('hide');
					document.getElementById('form').reset()
					layer.msg('操作成功!',{icon: 6,time:1000});
					jq_table.ajax.reload(null, false);
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			}
		});
    });
    
  	//礼物修改框
    $("body").on("click", "a.level-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	$("#editModal input[name='id']").val(sData.id);
    	$("#editModal input[name='levelname']").val(sData.levelname);
    	//下修改
    	$("#editModal input[name='xcount']").val(sData.xcount);
		$("#editModal input[name='ycount']").val(sData.ycount);
		$("#editModal input[name='zcount']").val(sData.zcount);
    	//上修改
    	$("#editModal input[name='limitpeople']").val(sData.limitpeople);
    	$("#editModal").modal('show');
    })
    //修改礼物
    $("#editModal .confirm").click(function(){
    	var levelname = $.trim($("#editModal input[name='levelname']").val());
    	if(levelname == ''){
    		layer.msg('请填写等级名称！',{icon: 7,time:2000}); return false;
    	}
    	//下添加
    	var xcount = $.trim($("#editModal input[name='xcount']").val());
    	if(xcount === '' || !(/^[0-9]\d*$/.test(xcount))){
    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
		var ycount = $.trim($("#editModal input[name='ycount']").val());
		    	if(ycount === '' || !(/^[0-9]\d*$/.test(ycount))){
		    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
		var zcount = $.trim($("#editModal input[name='zcount']").val());
		    	if(zcount === '' || !(/^[0-9]\d*$/.test(zcount))){
		    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
    	//上添加
    	
    	var limitpeople = $.trim($("#editModal input[name='limitpeople']").val());
    	if(limitpeople === '' || !(/^[0-9]\d*$/.test(limitpeople))){
    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
    	//检测数据通过
    	var form=document.getElementById('editform');
        var data =new FormData(form);
		$.ajax({
            url: 'editsalerlevel',
            type: "POST",
            data: data,
            processData: false,
            contentType: false,
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
    //修改默认任务数
    $("#defModal .confirm").click(function() {
    	var count = $.trim($("#defModal input[name='count']").val());
    	if(count === '' || !(/^[0-9]\d*$/.test(count))){
    		layer.msg('请填写正确的数量！',{icon: 7,time:2000}); return false;
    	}
    	//检测数据通过
		$.ajax({
            url: 'setdefsalercount',
            type: "POST",
            data: {count: count},
            success: function (result) {
            	if(result.code == 200){
            		layer.msg("修改成功",{icon: 1,time:2000});
            		$("#defModal").modal('hide');
            		$("#defModal .def").html(count);
            		$("#defModal input[name='count']").val('')
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