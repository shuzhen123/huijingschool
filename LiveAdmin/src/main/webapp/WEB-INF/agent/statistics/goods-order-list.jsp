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
<title>礼物消费订单列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 礼物消费订单 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<!--
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">员工账号</option>
		    <option value="2">员工姓名</option>
		    <option value="3">所属部门</option>
		  </select>
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-class hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="dept">
					<option value="undefined" >未分配</option> 
					<c:forEach var="item" items="${dept }">
						<option value="${item.deptid}" >${item.deptname }</option> 
					</c:forEach>
				</select>
			</span>
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜员工</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		<span class="r">
			<a href="javascript:;" class="btn btn-secondary radius add-saler">
				<i class="Hui-iconfont">&#xe600;</i> 添加新员工
			</a>
		</span>
	</div>
	-->
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>用户账号</th>
					<th>用户昵称</th>
					<th>订单号</th>
					<th>支付金额（元）</th>
					<th>数量</th>
					<th>礼物名称</th>
					<th>礼物单价（元）</th>
					<th>业务员</th>
					<th>业务员提成</th>
					<th>代理商提成</th>
					<th>订单时间</th>
					<!-- <th>操作</th> -->
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
			url: "goodsorderlist",
			type: "POST",
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
            {"data": 'telno'},
            {"data": 'nickname'},
            {"data": 'orderno'},
            {"data": 'money'},
            {"data": 'counts'},
            {"data": 'goodsname'},
            {"data": 'price'},
            {"data": 'realname'},
            {"data": 'profit'},
            {"data": 'aprofit'},
            {"data": 'paytime'},
            /* {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '<a style="text-decoration:none" class="ml-5 saler-detail" href="javascript:;" title="查看详情">' +
	                    '	<i class="Hui-iconfont">&#xe6e0;</i>' +
	                    '</a> '
                	return  str;
                }
            } */
        ]
    });
    jq_table.search('').draw();
    var api_table = $('#datatable').dataTable();
    
    //条件搜索
    $("#search").click(function() {
    	var tbdata = jq_table.ajax.params(), olddata = tbdata.search.value;
    	var sel = $("select").val();
    	var param;
    	if(sel == 0){
    		//恢复默认
    		if(olddata == '') return false;
	    	param = '';
    	}else if(sel == 1){
    		//使用员工账号搜索
    		var username = $(".search-text input").val();
    		param = "username^*"+username;
    		if(username == '' || olddata == param) return false;
    	}else if(sel == 2){
    		//使用员工姓名搜索
    		var realname = $(".search-text input").val();
    		param = "realname^*"+realname;
    		if(realname == '' || olddata == param) return false;
    	}else if(sel == 3){
    		//所属部门
    		var dept = $("select[name='dept']").val();
    		param = "dept^*"+dept;
    		if(dept == '' || olddata == param) return false;
    	}else{
    		//不进行搜索
    		return false;
    	}
    	jq_table.search(param).draw();
	})
	$("select.sel").change(function() {
		var val = $(this).val();
		$(".search-param").addClass("hidden");
		$(".search-param input").val("");
		if(val == 0){
			var tbdata = jq_table.ajax.params(), olddata = tbdata.search.value;
	    	if(olddata == '') return false;
			jq_table.search('').draw();
		}else if(val == 1 || val == 2){
    		//输入框
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3){
    		//选择框
    		$(".search-class").removeClass("hidden");
    	}
	})
    
    //用户删除
    $("body").on("click", "a.saler-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除此业务员？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delsaler', [sData.userid], jq_table);
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
    		var userids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			userids[i] = sData.userid;
    			i++
			})
			layer.confirm('确定批量删除业务员？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delsaler', userids, jq_table);
	    	});
    	}
    	
	})
    
    
    //员工详情页
    $("body").on("click", "a.saler-detail1", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('员工信息修改', 'salerdatapage?type=detail&salerid=' +sData.userid,'','510');
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