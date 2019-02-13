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
<title>卡券管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 卡券管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		
		<span class="dropDown r"> 
			<!-- <a class="btn btn-success radius" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><i class="Hui-iconfont">&#xe709;</i> 分类</a>
			<ul class="dropDown-menu menu radius box-shadow">
				<li><a class="disabled show-coupon show-all" href="#">全部</a></li>
				<li><a class="show-coupon show-cash-coupon" href="#">代金券</a></li>
				<li><a class="show-coupon show-experience-coupon" href="#">体验券</a></li>
 			</ul> -->
 			
 			<a href="javascript:;" class="btn btn-secondary radius add-coupon" onClick="layer_show('添加卡券','addcouponpage.do','','510');">
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
					<th>类型</th>
					<th>名称</th>
					<th>面值（元）（代金券）</th>
					<th>体验天数（体验券）</th>
					<th>卡券有效时长</th>
					<th>创建日期</th>
					<th>卡券状态</th>
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
			url: "couponlist.do",
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
                		return '体验券';
                	}else{
                		return '代金券';
                	}
                }
            },
            {"data": 'cpname'},
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.type == 1){
                		return '-';
                	}else{
                		return full.price;
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.type == 1){
                		return full.servicedays;
                	}else{
                		return '-';
                	}
                }
            },
            {"data": 'validity'},
            {"data": 'createtime'},
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.entkbn == 0){
                		return '<span class="label label-success radius">使用中</span>';
                	}else{
                		return '<span class="label label-default radius">已失效</span>';
                	}
                }
            },
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a title="编辑" href="javascript:;" class="ml-5 coupon-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> ' +
	                    '<a title="删除" href="javascript:;" class="ml-5 coupon-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    jq_table.search('').draw();
    var api_table = $('#datatable').dataTable();
    
    //条件搜索
    
    $(".show-coupon").click(function() {
    	$('.show-coupon').removeClass('disabled');
    	var param = -1;
    	if($(this).hasClass('show-all')){
    		param = -1; //全部显示
    	}else if($(this).hasClass('show-cash-coupon')){
    		param = 2; //显示代金券
    	}else if($(this).hasClass('show-experience-coupon')){
    		param = 1; //显示体验券
    	}
    	$(this).addClass('disabled');
    	jq_table.search(param).draw();
	})
    
    //卡券删除
    $("body").on("click", "a.coupon-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除卡券？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delcoupon.do', [sData.id], jq_table);
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
				betchdel('delcoupon.do', couponids, jq_table);
	    	});
    	}
    	
	})
    
    
    //卡券修改
    $("body").on("click", "a.coupon-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('卡券修改','editcouponpage.do?couponid='+sData.id,'','510');
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