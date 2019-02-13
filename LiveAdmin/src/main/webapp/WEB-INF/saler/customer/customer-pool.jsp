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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 资源池管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l" style="margin-top: 17px;">
			<a href="javascript:;" class="btn btn-secondary disabled radius batch-gain">
				<i class="Hui-iconfont">&#xe645;</i> 批量拾取
			</a>
		</span>
		<span class="r">
			今日x型资源可拾取：
			<span class="xcount"><c:choose>
				<c:when test="${not empty resource.xcount}">${resource.xcount }</c:when>
				<c:otherwise>0</c:otherwise>
			</c:choose></span>
			个，已拾取：
			<span class="xres"><c:choose>
				<c:when test="${not empty counts.x}">${counts.x }</c:when>
				<c:otherwise>0</c:otherwise>
			</c:choose></span>
			 个。<br/>
			今日y型资源可拾取：
			<span class="ycount"><c:choose>
				<c:when test="${not empty resource.ycount}">${resource.ycount }</c:when>
				<c:otherwise>0</c:otherwise>
			</c:choose></span>
			个，已拾取：
			<span class="yres"><c:choose>
				<c:when test="${not empty counts.y}">${counts.y }</c:when>
				<c:otherwise>0</c:otherwise>
			</c:choose></span>
			 个。<br/>
			今日z型资源可拾取：
			<span class="zcount"><c:choose>
				<c:when test="${not empty resource.zcount}">${resource.zcount }</c:when>
				<c:otherwise>0</c:otherwise>
			</c:choose></span>
			个，已拾取：
			<span class="zres"><c:choose>
				<c:when test="${not empty counts.z}">${counts.z }</c:when>
				<c:otherwise>0</c:otherwise>
			</c:choose></span>
			个。
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>账号</th>
					<th>姓名</th>
					<th>昵称</th>
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
                    return '<input type="checkbox" value="1" name="">';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
            {"data": 'telno'},
            {"data": 'realname'},
            {"data": 'nickname'},
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
                	return '<a style="text-decoration:none" class="ml-5 customer-del" href="javascript:;" title="拾取资源">' +
	                    '	<i class="Hui-iconfont">&#xe645;</i>' +
	                    '</a> ';
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    
  //拾取资源
    $("body").on("click", "a.customer-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定拾取客户资源？', {icon: 3, title:'提示'}, function(index){
    		gain([{userid:sData.userid, restype: sData.restype}]);
    	});
    });
    $("#datatable").on("change", "input[type='checkbox']", function() {
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
    	if(cblist.length == 0){
    		//全不选
    		$(".batch-gain").addClass("disabled");
    	}else{
    		//有选中项
    		$(".batch-gain").removeClass("disabled");
    	}
	})
    //批量拾取
    $(".batch-gain").click(function() {
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
    	if(cblist.length > 0){
    		var userids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			userids[i] = [];
    			userids[i].push({userid:sData.userid, restype: sData.restype});
    			i++
			})
			layer.confirm('确定批量拾取客户资源？', {icon: 3, title:'批量拾取'}, function(index){
				gain(userids);
	    	});
    	}
    	
	})
	
	var gain = function(userids) {
    	//检测xyz型资源获取量
    	var x=0, y=0, z=0, ids=[];
    	$.each(userids, function(k, v) {
			ids.push(v[0].userid);
			if(v[0].restype == 'x') x++;
			if(v[0].restype == 'y') y++;
			if(v[0].restype == 'z') z++;
		})
		var xcount = parseInt($('span.xcount').text()), xres=parseInt($('span.xres').text());
    	if(xcount < (xres + x)) {
    		layer.open({
    			title: '警告',
    			content: '今日x型资源可拾取数量：'+xcount+'，已拾取：'+xres+'，您当前拾取量为：'+x+'，拾取量已超上线！'
    		});
	    	return;
    	}
		var ycount = parseInt($('span.ycount').text()), yres=parseInt($('span.yres').text());
    	if(ycount < (yres + y)) {
    		layer.open({
    			title: '警告',
    			content: '今日y型资源可拾取数量：'+ycount+'，已拾取：'+yres+'，您当前拾取量为：'+y+'，拾取量已超上线！'
    		});
	    	return;
    	}
		var zcount = parseInt($('span.zcount').text()), zres=parseInt($('span.zres').text());
    	if(zcount < (zres + z)) {
    		layer.open({
    			title: '警告',
    			content: '今日z型资源可拾取数量：'+zcount+'，已拾取：'+zres+'，您当前拾取量为：'+z+'，拾取量已超上线！'
    		});
	    	return;
    	}
		$.ajax({
			type: 'POST',
			url: 'collectcustomer',
			data: {ids: ids},
			success: function(data){
				if(data.code == 200){
					layer.msg('操作成功!',{icon: 6,time:1000});
					location.reload();
				}else{
					layer.msg(data.msg,{icon: 5,time:1500});
				}
			}
		});	
	}
});
</script> 
</body>
</html>