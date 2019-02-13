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
<title>礼物管理</title>
</head>
<body>

<div id="addModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加礼物</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="addform" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">礼物名称：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写礼物名称" name="goodsname">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">单价：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写单价" name="price">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">展示图：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请添加附件！" style="width:100px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传展示图</a>
									<input type="file" name="img" class="input-file">
								</span>
							</div>
						</div>
					</div>
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

<div id="editModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">修改礼物</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="editform" >
					<input type="text" class="hidden" name="goodsid">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">礼物名称：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写礼物名称" name="goodsname">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">单价：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写单价" name="price">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">展示图：</label>
						<div class="formControls col-xs-7 col-sm-7">
							<img src=""  style="margin-bottom: 10px; max-width: 150px;"/>
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请添加附件！" style="width:100px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传展示图</a>
									<input type="file" name="img" class="input-file">
								</span>
							</div>
						</div>
					</div>
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 礼物管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		
		<span class="dropDown r"> 
 			<a href="javascript:;" class="btn btn-secondary radius" data-toggle="modal" data-target="#addModal">
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
					<th>礼物名称</th>
					<th>单价（元）</th>
					<th>展示图</th>
					<th>代理商分润（%）</th>
					<th>业务员分润（%）</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "goodslist",
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
            {"data": 'goodsname'},
            {"data": 'price'},
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	return '<img src="'+ full.icon +'" style="width: 60px;">';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	return full.agentprofit;
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	return full.salerprofit;
                }
            },
            {"data": 'createtime'},
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a title="编辑" href="javascript:;" class="ml-5 goods-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="删除" href="javascript:;" class="ml-5 goods-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    jq_table.search('').draw();
    var api_table = $('#datatable').dataTable();
    
    //添加礼物
    $("#addModal .confirm").click(function name() {
    	if($.trim($('#addModal input[name="goodsname"]').val()) == ''){
			layer.msg('请填写礼物名称！',{icon: 7,time:2000});
			return false;
		}
    	var price = parseFloat($.trim($('#addModal input[name="price"]').val()));
    	if(price == '' || !(/^[0-9]+\.?[0-9]*$/.test(price)) || price <= 0){
			layer.msg('请填写正确的价格！',{icon: 7,time:2000});
			return false;
		}
    	var agentprofit = parseFloat($.trim($('#addModal input[name="agentprofit"]').val()));
    	if(agentprofit === '' || !(/^[0-9]\d*$/.test(agentprofit)) || agentprofit < 0 || agentprofit >= 100){
			layer.msg('请填写正确的代理商分润比例！',{icon: 7,time:2000});
			return false;
		}
    	var salerprofit = parseFloat($.trim($('#addModal input[name="salerprofit"]').val()));
    	if(salerprofit === '' || !(/^[0-9]\d*$/.test(salerprofit)) || salerprofit < 0 || salerprofit >= 100){
			layer.msg('请填写正确的业务员分润比例！',{icon: 7,time:2000});
			return false;
		}
    	if(agentprofit + salerprofit >= 100){
			layer.msg('两级分润比例不正确！',{icon: 7,time:2000});
			return false;
		}
    	//检测数据通过
    	var form=document.getElementById('addform');
        var data =new FormData(form);
		$.ajax({
            url: 'addgoods',
            type: "POST",
            data: data,
            processData: false,
            contentType: false,
            success: function (result) {
            	if(result.code == 200){
            		layer.msg("添加成功",{icon: 1,time:2000});
            		$("#addModal").modal('hide');
            		document.getElementById('addform').reset()
            		jq_table.ajax.reload(null, false);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            }
        })
	})
    
    //礼物删除
    $("body").on("click", "a.goods-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除卡券？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delgoods', [sData.goodsid], jq_table);
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
    			couponids[i] = sData.goodsid;
    			i++
			})
			layer.confirm('确定批量删除卡券？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delgoods', couponids, jq_table);
	    	});
    	}
    	
	})
    
    //礼物修改框
    $("body").on("click", "a.goods-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	$("#editModal input[name='goodsid']").val(sData.goodsid);
    	$("#editModal input[name='goodsname']").val(sData.goodsname);
    	$("#editModal input[name='price']").val(sData.price);
    	$("#editModal img").attr("src", sData.icon);
    	$("#editModal input[name='agentprofit']").val(sData.agentprofit);
    	$("#editModal input[name='salerprofit']"). val(sData.salerprofit);
    	$("#editModal").modal('show');
    })
    //修改礼物
    $("#editModal .confirm").click(function name() {
    	if($.trim($('#editModal input[name="goodsname"]').val()) == ''){
			layer.msg('请填写礼物名称！',{icon: 7,time:2000});
			return false;
		}
    	var price = parseFloat($.trim($('#editModal input[name="price"]').val()));
    	if(price == '' || !(/^[0-9]+\.?[0-9]*$/.test(price)) || price <= 0){
			layer.msg('请填写正确的价格！',{icon: 7,time:2000});
			return false;
		}
    	var agentprofit = parseInt($.trim($('#editModal input[name="agentprofit"]').val()));
    	if(agentprofit === '' || !(/^[0-9]\d*$/.test(agentprofit)) || agentprofit >= 100){
			layer.msg('请填写正确的代理商分润比例！',{icon: 7,time:2000});
			return false;
		}
    	var salerprofit = parseInt($.trim($('#editModal input[name="salerprofit"]').val()));
    	if(salerprofit === '' || !(/^[0-9]\d*$/.test(salerprofit)) || salerprofit >= 100){
			layer.msg('请填写正确的业务员分润比例！',{icon: 7,time:2000});
			return false;
		}
    	if(agentprofit + salerprofit >= 100){
			layer.msg('两级分润比例不正确！',{icon: 7,time:2000});
			return false;
		}
    	//检测数据通过
    	var form=document.getElementById('editform');
        var data =new FormData(form);
		$.ajax({
            url: 'editgoods',
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
});
</script> 
</body>
</html>