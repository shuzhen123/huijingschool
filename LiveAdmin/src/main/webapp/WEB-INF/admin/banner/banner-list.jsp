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
<title>轮播图管理</title>
</head>
<body>

<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">添加外链轮播图</h3>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" enctype="multipart/form-data" method="post" id="form" >
					<input type="text" class="hidden" value="1" name="link">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">轮播图：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="uploadimage" id="uploadimage" readonly  style="width:200px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传轮播图</a>
									<input type="file" name="vfile" id="vfile" class="input-file">
								</span>
							</div>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">外链地址：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写外链地址" name="content">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary confirm">确定</button>
				<button class="hidden" type="reset"></button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<div id="myModalEdit" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">编辑外链轮播图</h3>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" enctype="multipart/form-data" method="post" id="editform" >
					<input type="text" class="hidden" name="id">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3"></label>
						<div class="formControls col-xs-8 col-sm-7">
							<img src="" style="width: 150px;">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">新轮播图：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<div>
								<span class="btn-upload form-group">
									<input class="input-text upload-url" type="text" name="uploadimage" id="uploadimage" readonly  style="width:200px">
									<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 上传轮播图</a>
									<input type="file" name="vfile" id="vfile" class="input-file">
								</span>
							</div>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">新外链地址：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<input type="text" class="input-text" placeholder="请填写外链地址" name="content">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary confirm">确定</button>
				<button class="hidden" type="reset"></button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 轮播图管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		
		<span class="dropDown r"> 
			<a class="btn btn-secondary radius" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
			<ul class="dropDown-menu menu radius box-shadow">
				<li><button class="btn btn-default size-S" type="button" style="width:100%;"  data-toggle="modal" data-target="#myModal">外链</button></li>
				<li><button class="btn btn-default size-S add-image-word" type="button" style="width:100%;">图文</button></li>
 			</ul>
		</span>
		
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>类型</th>
					<th>展示图</th>
					<th>发布日期</th>
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
			url: "mpbannerlist",
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
                	if(full.link == 0){
                		return '图文';
                	}else{
                		return '外链';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	return '<a class="show"><img src="'+ full.picurl +'" style="height: 60px;"></a>';
                }
            },
            {"data": 'createtime'},
            {
                "data": null,
                "render": function (base_data, type, full) {
                	var str = '';
                	if(full.link == 0){
                		//否
                		str = 
                			'<a title="编辑" href="javascript:;" class="ml-5 img-banner-edit" style="text-decoration:none">' +
    	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
    	                    '</a> ';
                	}else if(full.link == 1){
                		//是
                		str = 
                			'<a title="编辑" href="javascript:;" class="ml-5 link-banner-edit" style="text-decoration:none">' +
    	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
    	                    '</a> ';
                	}
                    str +=
	                    '<a title="删除" href="javascript:;" class="ml-5 coupon-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    
    //删除轮播图
    $("body").on("click", "a.coupon-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除轮播图？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delmpbanner', [sData.id], jq_table);
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
    //批量删除轮播图
    $(".batch-del").click(function() {
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
    	if(cblist.length > 0){
    		var couponids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			couponids[i] = sData.id;
    			i++
			})
			layer.confirm('确定批量删除轮播图？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delmpbanner', couponids, jq_table);
	    	});
    	}
    	
	})
	//重置添加外链输入框
	$("#myModal").on('hidden.bs.modal', function (e) {
		document.getElementById("form").reset()
	})
	//检测展示图属性
	$("body").on('change', 'input[name="vfile"]', function(e) {
		var file = document.getElementById("vfile").files[0];
		if(!(/^image\/\w+/.test(file.type))) {
			$('#vfile').val('');
			$('input[name="uploadimage"]').val('');
			layer.msg('视频文件格式不正确，请上传png, jpg, jpeg, gif, bmp等格式的图片文件',{icon: 5,time:2000});
		}
	})
	//添加外链
	$("#myModal .confirm").click(function() {
		if($.trim($('#myModal #vfile').val()) == ''){
			layer.msg('请选择展示图！',{icon: 7,time:2000});
			return false;
		}
		var match = /^((ht|f)tps?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-\.,@?^=%&:\/~\+#]*[\w\-\@?^=%&\/~\+#])?$/;
		if(!match.test($('#myModal input[name="content"]').val())){
			layer.msg('请填写正确的外链地址！',{icon: 7,time:2000});
			return false;
		}
		$.ajax({
            url: 'addmpbanner',
            type: "POST",
            data: new FormData(document.getElementById('form')),
            dataType: "json",
            processData: false,
            contentType: false,
            beforeSend: function() {
            	$("#myModal .confirm").addClass('disabled');
			},
            success: function (result) {
            	if(result.code == 200){
            		layer.msg('添加成功！',{icon: 1,time:2000});
            		$("#myModal").modal('hide');
            		jq_table.ajax.reload(null, false);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            },
			complete: function() {
				$("#myModal .confirm").removeClass('disabled');
			}
        })
	})
    
	//添加图文轮播图
	$(".add-image-word").click(function () {
		var index = layer.open({
			type: 2,
			title: "添加公众号轮播图",
			content: 'addmpmpbannerpage'
		});
		layer.full(index);
	});
    
    //外链轮播图修改
    $("body").on("click", "a.link-banner-edit", function() {
    	var sData = api_table.fnGetData($(this).parents("tr"));
    	$('#myModalEdit input[name="id"]').val(sData.id);
    	$('#myModalEdit img').attr('src', sData.picurl);
    	$('#myModalEdit input[name="content"]').val(sData.content);
    	$("#myModalEdit").modal('show');
    })
    //确认修改外链轮播图
    $("#myModalEdit .confirm").click(function() {
		var match = /^((ht|f)tps?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-\.,@?^=%&:\/~\+#]*[\w\-\@?^=%&\/~\+#])?$/;
		if(!match.test($('#myModalEdit input[name="content"]').val())){
			layer.msg('请填写正确的外链地址！',{icon: 7,time:2000});
			return false;
		}
		$.ajax({
            url: 'editmpbanner',
            type: "POST",
            data: new FormData(document.getElementById('editform')),
            dataType: "json",
            processData: false,
            contentType: false,
            beforeSend: function() {
            	$("#myModalEdit .confirm").addClass('disabled');
			},
            success: function (result) {
            	if(result.code == 200){
            		layer.msg('添加成功！',{icon: 1,time:2000});
            		$("#myModalEdit").modal('hide');
            		jq_table.ajax.reload(null, false);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            },
			complete: function() {
				$("#myModalEdit .confirm").removeClass('disabled');
			}
        })
	})
    
    
    //图文轮播图修改
    $("body").on("click", "a.img-banner-edit", function() {
    	var sData = api_table.fnGetData($(this).parents("tr"));
    	var index = layer.open({
			type: 2,
			title: "编辑公众号轮播图",
			content: 'editmpbannerpage?id='+sData.id
		});
		layer.full(index);
    })
    
    //查看轮播图内容
    $("#datatable").on("click", ".show", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	if(sData.link == 0){
    		var content = '<div style="padding: 10px;">'+sData.content+'</div>', type = 1;
    	}else{
    		var content = sData.content, type = 2;
    	}
    	layer.open({
    		type: type,
     		area: ['414px','736px'],
    		fix: true, //不固定
    		maxmin: true,
    		shade:0.4,
    		title: '轮播图详情',
    		content: content
    	});
    	$("a.layui-layer-max, a.layui-layer-min").remove();
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