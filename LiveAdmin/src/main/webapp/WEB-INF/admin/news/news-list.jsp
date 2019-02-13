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
<style type="text/css">
table#datatable tbody td.desc{
	max-width: 50px;
	text-align: left;
}
</style>
<title>资讯管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 资讯管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select param" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">模块类型</option>
		    <option value="6">讲师名称</option>
		    <option value="2">标题</option>
		    <option value="3">标签</option>
		    <option value="4">作者</option>
		    <option value="5">发布时间</option>
		  </select>
		</span>
		<span class="search-param search-model hidden">
			<!-- 模块类型 -->
			<span class="select-box" style="width:auto;">
				<select class="select" name="model" size="1">
					<c:forEach var="item" items="${model}">
						<option value="${item.informationid }">${item.newskindname }</option>
					</c:forEach>
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
		<span class="r">
			<a href="javascript:;" class="btn btn-primary radius add-news">
				<i class="Hui-iconfont">&#xe600;</i> 添加资讯
			</a>
		</span> 
	</div>
	<div class="mt-20">
	<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th><input type="checkbox" name="" value=""></th>
				<th>序号</th>
				<th>模块</th>
				<th>讲师</th>
				<th>标题</th>
				<th>内容</th>
				<th>缩略图</th>
				<th style="max-width: 300px;">描述</th>
				<th>标签</th>
				<th>作者</th>
				<th>阅读量</th>
				<th>点赞数</th>
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
			url: "newslist",
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
            {"data": 'newskindname'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.userid != null){
                		return full.realname;
                	}else{
                		return '<i class="Hui-iconfont">&#xe6a1;</i>';
                	}
                }
            },
            {"data": 'infomationtitle'},
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
	                	return '<img src="'+ full.picurl +'" style="width: 60px;">';
                	}
                }
            },
            {"data": 'descrption', "class": "desc"},
            {"data": 'tag'},
            {"data": 'singname'},
            {"data": 'readcounts'},
            {"data": 'thumbsupcounts'},
            {"data": 'createtime'},
            {
                "data": null,
                "render": function (base_data, type, full) {
                     var str = 
	                    '<a title="编辑" href="javascript:;" class="ml-5 user-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="删除" href="javascript:;" class="ml-5 news-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    var param = undefined;
    //条件搜索
    $("#search").click(function() {
    	var sel = $("select").val();
    	if(sel == 0){
    		//恢复默认
    		var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				param = undefined;
			}else return false;
    	}else if(sel == 1){
    		//模块类型
    		var informationmodelid = $('select[name="model"]').val();
    		param = {informationmodelid: informationmodelid};
    	}else if(sel == 6){
    		//讲师名称
    		var realname = $.trim($(".search-text input").val());
    		if(realname == '') return false;
    		param = {realname: realname};
    	}else if(sel == 2){
    		//标题
    		var infomationtitle = $(".search-text input").val();
    		if(infomationtitle == '') return false;
    		param = {infomationtitle: infomationtitle};
    	}else if(sel == 3){
    		//标签
    		var tag = $(".search-text input").val();
    		if(tag == '') return false;
    		param = {tag: tag};
    	}else if(sel == 4){
    		//作者
    		var singname = $(".search-text input").val();
    		if(singname == '') return false;
    		param = {singname: singname};
    	}else if(sel == 5){
    		//发布时间
    		var starttime = $.trim($("#datemin").val());
    		var endtime = $.trim($("#datemax").val());
    		if(starttime == '' || endtime == '') return false;
    		param = {starttime: starttime, endtime: endtime};
    	}else{
    		//不进行搜索
    		return false;
    	}
    	jq_table.settings()[0].ajax.data = param;
    	jq_table.ajax.reload();
	})
	$("select.param").change(function() {
		var val = $(this).val();
		$(".search-param").addClass("hidden");
		$(".search-param input").val("");
		if(val == 0){
			var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				jq_table.settings()[0].ajax.data = undefined;
		    	jq_table.ajax.reload();
			}
		}else if(val == 1){
    		//模块类型
    		$(".search-model").removeClass("hidden");
    	}else if(val == 2 || val == 3 || val == 4 || val== 6){
    		$(".search-text").removeClass("hidden");
    	}else if(val == 5){
    		//发布时间
    		$(".search-date").removeClass("hidden");
    	}
	})
    
    //资讯删除
    $("body").on("click", "a.news-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除资讯？', {icon: 3, title:'删除'}, function(index){
    		betchdel('delnews', [sData.id], jq_table);
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
    			ids.push(sData.id);
			})
			layer.confirm('确定批量删除资讯？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delnews', ids, jq_table);
	    	});
    	}
    	
	})
	
    
	//添加资讯
	$(".add-news").click(function() {
		var index = layer.open({
			type: 2,
			title: "添加资讯",
			content: 'addnewspage'
		});
		layer.full(index);
	});
    
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
    
    //资讯修改
    $("body").on("click", "a.user-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('资讯修改','editnewspage?id='+sData.id);
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