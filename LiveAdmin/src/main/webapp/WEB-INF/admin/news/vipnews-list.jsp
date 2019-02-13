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
<title>vip资讯管理</title>
</head>
<body>
<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">审核</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="form" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">审核状态：</label>
						<div class="formControls col-xs-8 col-sm-9 skin-minimal">
							<div class="radio-box">
								<input name="checkflag" type="radio" id="pass" checked value="1">
								<label for="sex-1">通过</label>
							</div>
							<div class="radio-box">
								<input type="radio" id="down" name="checkflag" value="2">
								<label for="sex-2">不通过</label>
							</div>
						</div>
					</div>
					<div class="row cl hidden">
						<label class="form-label col-xs-4 col-sm-3">审核原因：</label>
						<div class="formControls col-xs-8 col-sm-9 skin-minimal">
							<textarea name="cause" cols="" rows="" class="textarea" placeholder="请说明不通过原因" ></textarea>
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> vip资讯管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select param" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">显示状态</option>
		    <option value="2">标题</option>
		    <option value="3">发布时间</option>
		    <option value="4">审核状态</option>
		  </select>
		</span>
		<span class="search-param search-show hidden">
			<!-- 显示状态 -->
			<span class="select-box" style="width:auto;">
				<select class="select" name="showflag" size="1">
					<option value="0">不显示</option>
					<option value="1">显示</option>
				</select>
			</span>
		</span>
		<span class="search-param search-check hidden">
			<!-- 审核状态 0待审核， 1审核通过，2审核不通过 -->
			<span class="select-box" style="width:auto;">
				<select class="select" name="checkflag" size="1">
					<option value="0">待审核</option>
					<option value="1">已通过</option>
					<option value="2">未通过</option>
				</select>
			</span>
		</span>
		<span class="search-param search-text hidden">
			<!-- 标题 、标签、作者 -->
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-date hidden">
			日期范围：
			<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
			-
			<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;">
		</span>
		<button type="button" class="btn btn-success radius" id="search"><i class="Hui-iconfont">&#xe665;</i> 搜资讯</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a> 
		</span> 
		<span class="r check-type">
			自动审核：
			<c:if test="${check == 0 }">已禁用</c:if>
			<c:if test="${check == 1 }">已启用</c:if>
			<a href="javascript:;" class="btn btn-success radius auto-check" data-auto="${check }">
				<i class="Hui-iconfont">&#xe61d;</i> 更改
			</a> 
		</span> 
	</div>
	<div class="mt-20">
	<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th><input type="checkbox" name="" value=""></th>
				<th>序号</th>
				<th>发布者</th>
				<th>级别</th>
				<th>标题</th>
				<th>内容</th>
				<th>缩略图</th>
				<th>阅读量</th>
				<th>显示状态</th>
				<th>审核状态</th>
				<th>发布时间</th>
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
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "vipnewslist",
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
            {"data": 'realname'},
            {"data": 'kindname'},
            {"data": 'articaltitle'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	return '<button type="button" class="btn size-MINI show btn-primary-outline radius">点击查看</button>';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.picurl == null){
                		return '无缩略图';
                	}else{
	                	return '<img src="'+ full.picurl +'" style="max-width: 60px;">';
                	}
                }
            },
            {"data": 'readcount'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.showflag == 0){
                		return '隐藏';
                	}else{
	                	return '显示';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.checkflag == 0){
                		return '<span class="label label-primary radius">待审核</span>';
                	}else if(full.checkflag == 1){
                		return '<span class="label label-success radius">审核通过</span>';
                	}else if(full.checkflag == 2){
                		return '<button class="btn btn-warning size-MINI radius cause" title="点击查看原因">审核未通过</button>';
                	}
                }
            },
            {"data": 'tdays'},
            {
                "data": null,
                "render": function (base_data, type, full) {
                	var str= '';
                	if(full.checkflag == 0){
                    	str += 
                    		'<a title="审核" href="javascript:;" class="ml-5 news-check" style="text-decoration:none">' +
		                    '	<i class="Hui-iconfont" style="color: #c85e0b;">&#xe6a7;</i>' +
		                    '</a> ' ;
                 	}
                	str += 
 	                    '<a title="删除" href="javascript:;" class="ml-5 news-del" style="text-decoration:none">' +
 	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
 	                    '</a>';
                	return  str;
                }
            }
        ]
    });
    jq_table.search('').draw();
    var api_table = $('#datatable').dataTable();
    
    //查看原因
    $("#datatable").on('click', 'button.cause', function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm(sData.cause);
	})
    
    //已搜索标志
    var boo = false;
    //条件搜索
    $("#search").click(function() {
    	var sel = $("select").val();
    	var param = {};
    	if(sel == 0){
    		//恢复默认
    	}else if(sel == 1){
    		//使用显示状态搜索
    		var showflag = $('select[name="showflag"]').val();
    		param = {showflag: showflag};
    	}else if(sel == 2){
    		//使用标题搜索
    		var articaltitle = $(".search-text input").val();
    		param = {articaltitle: articaltitle};
    	}else if(sel == 3){
    		//使用注册时间搜索
    		var starttime = $("#datemin").val();
    		var endtime = $("#datemax").val();
    		param = {starttime: starttime, endtime: endtime};
    	}else if(sel == 4){
    		var check = $('select[name="checkflag"]').val();
    		param = {check: check};
    	}else{
    		//不进行搜索
    		return false;
    	}
    	boo = true;
    	jq_table.settings()[0].ajax.data = param;
    	jq_table.ajax.reload();
	})
	$("select.param").change(function() {
		var val = $(this).val();
		$(".search-param").addClass("hidden");
		$(".search-param input").val("");
		if(val == 0 && boo){
			jq_table.settings()[0].ajax.data = {};
	    	jq_table.ajax.reload();
	    	boo = false;
		}else if(val == 1){
    		//显示状态
    		$(".search-show").removeClass("hidden");
    	}else if(val == 2){
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3){
    		//发布时间
    		$(".search-date").removeClass("hidden");
    	}else if(val == 4){
    		//审核状态
    		$(".search-check").removeClass("hidden");
    	}
	})
    
    //资讯删除
    $("body").on("click", "a.news-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除资讯？', {icon: 3, title:'删除'}, function(index){
    		betchdel('delvipnews', [sData.articalid], jq_table);
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
    		var ids = [];
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			ids.push(sData.articalid);
			})
			layer.confirm('确定批量删除资讯？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delvipnews', ids, jq_table);
	    	});
    	}
	})
    
    //点击查看资讯详情
    $("#datatable").on("click", ".show", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.open({
    		type: 1,
     		area: ['414px','736px'],
    		fix: true, //不固定
    		maxmin: true,
    		shade:0.4,
    		title: '内容详情',
    		content: '<div style="padding: 10px;">'+sData.content+'</div>'
    	});
    	$("a.layui-layer-max").remove();
	})
	//审核框显示
	$("body").on('click', 'a.news-check', function() {
		var sData = api_table.fnGetData( $(this).parents("tr") );
		$("#myModal").attr('data-id', sData.articalid);
		$("#myModal").modal('show');
	})
	//审核输入框
	$("input[name='checkflag']").change(function() {
		var val = $(this).val();
		if(val == 2){
			//不通过
			$(this).parents('.cl').next().removeClass('hidden');
		}else{
			$(this).parents('.cl').next().addClass('hidden');
		}
	})
	//审核动作
	$("#myModal .confirm").click(function() {
		var checkflag = $("input[name='checkflag']:checked").val(), cause;
		if(checkflag == 0){
			//获取原因
			cause = $.trim($("#myModal textarea").val());
			if(cause == ''){
				layer.msg('请填写审核未通过具体原因！',{icon: 5,time:1500});return false;
			}
		}
		//通过
		var id= $("#myModal").attr('data-id');
		$.ajax({
	        url: 'vipnewscheck',
	        type: "POST",
	        data: {checkflag: checkflag, cause: cause, id: id},
	        success: function (result) {
	        	if(result.code == 200){
	        		$("#myModal textarea").val('');
	        		$("#myModal").modal('hide');
	        		layer.msg('操作成功',{icon: 1,time:1500});
	        		jq_table.ajax.reload(null, false);
	        	}else{
	        		layer.msg(result.msg, {icon: 5,time:1500});
	        	}
	        }
		})
	})
	
	//更改自动审核
	$("body").on('click', 'a.auto-check', function() {
		var auto = $(this).attr('data-auto'), str = '';
		if(auto == 0){
			str = '是否启用自动审核？';
			auto = 1;
		}else if(auto == 1){
			str = '是否禁用自动审核？';
			auto = 0;
		}else return false;
		layer.confirm(str, {icon: 3, title:'更改自动审核'}, function(index){
			$.ajax({
		        url: 'changautocheck',
		        type: "POST",
		        data: {check: auto},
		        success: function (result) {
		        	if(result.code == 200){
		        		layer.msg('操作成功',{icon: 1,time:1500});
		        		str = 
		        			'自动审核：' + (auto == 0 ? '已禁用' : '已启用') +
		        			'&nbsp;<a href="javascript:;" class="btn btn-success radius auto-check" data-auto="'+ auto +'">' +
		        			'	<i class="Hui-iconfont">&#xe61d;</i> 更改' +
		        			'</a>';
		        		$(".check-type").html(str);	
		        	}else{
		        		layer.msg(result.msg, {icon: 5,time:1500});
		        	}
		        }
			})
		});
	})
	
	
});

</script> 
</body>
</html>