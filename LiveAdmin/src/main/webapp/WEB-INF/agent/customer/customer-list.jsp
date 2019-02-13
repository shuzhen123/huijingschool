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
	table#datatable tbody tr td.info_row {
	    text-align: left;
	}
</style>
<title>代理商分流用户列表</title>
</head>
<body>
<div id="moveModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">批量转移资源</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-3">转移至：</label>
						<div class="formControls col-xs-8 col-sm-7">
							<span class="select-box" style="width:auto;">
								<select class="select" name="deptid" size="1">
									<option value="" >无部门</option> 
									<c:forEach var="item" items="${depts }">
										<option value="${item.deptid }" >${item.deptname }</option> 
									</c:forEach>
								</select>
							</span>
							<span class="select-box" style="width:auto;">
								<select class="select" name="salerid" size="1">
									<c:choose>
										<c:when test="${not empty salers }">
											<c:forEach var="item" items="${salers }">
												<option value="${item.userid }" >${item.realname }</option> 
											</c:forEach>
										</c:when>
										<c:otherwise><option value="" >无员工</option></c:otherwise>
									</c:choose>
								</select>
							</span>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="confirm">确定</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 客户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">

<!-- 搜索框添加 -->
	<div class="text-c"> 
		<span class="select-box" style="width: auto;">
		  <select class="select sel" size="1">
		    <option value="0" selected>请选择</option>
		    <option value="1">手机号码</option>
		    <option value="2">姓名</option>
		    <option value="3">用户等级</option>
		  </select>
		</span>
		<span class="search-param search-text hidden">
			<input type="text" class="input-text" style="width:250px" placeholder="请输入" id="search-data">
		</span>
		<span class="search-param search-levelname hidden">
			<span class="select-box" style="width:auto;">
				<select class="select" name="levelid" size="1">
					<option value="">无等级</option>
					<c:forEach var="item" items="${levels }">
						<option value="${item.id}" >${item.name }</option> 
					</c:forEach>
				</select>
			</span>
		</span>
		<button type="button" class="btn btn-success radius" id="search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜资源</button>
	</div>
	<!--以上为添加的搜索框  -->


	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-secondary disabled radius move-customer">
				<i class="Hui-iconfont">&#xe6f7;</i> 批量转移资源
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>手机号</th>
					<th>昵称</th>
					<th>姓名</th>
					<th>性别</th>
					<th>省份</th>
					<th>头像</th>
					<th>等级</th>
					<th>风险评测</th>
					<th>备注</th>
					<th>所属业务员</th>
					<th>注册时间</th>
					<th>账号状态</th>
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
			url: "agentcustomerlist",
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
            {"data": 'nickname'},
            {"data": 'realname'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.sex == 1) return '男';
                	else if(full.sex == 2) return '女';
                	else return '未填写';
                }
            },
            {"data": 'prov'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.entkbn == 0) return '<img src="'+ full.iconurl +'" class="avatar size-M radius" alt="icon">';
                	else return '-';
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                    if (full.levelname == null) {
                        return '<span class="label label-default radius">未设置</span>';
                    } else {
                        return '<span class="label label-secondary radius">'+full.levelname+'</span>';
                    }
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                	if(full.entkbn == 0) {
	                    if (full.riskratingfalg == 1) {
	                        //return '<span class="label label-success radius">已通过</span>';
	                    	return '<button class="btn btn-primary size-MINI agent-teacher" type="button">已评测</button>'
	                    } else {
	                        return '<span class="label label-default radius">未评测</span>';
	                    }
                	}else{
                		return '-';
                	}
                }
            },
            {
                "data": null,
                "mRender": function (data, type, full) {
                    if (full.remark == null) {
                        return '未填写';
                    } else {
                        return '<button class="btn btn-primary size-MINI remark" type="button">查看备注</button>';
                    }
                }
            },
            {"data": 'salername'},
            {"data": 'registertime'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                    if (full.entkbn == 0) {
                        return '正常';
                    } else if(full.entkbn == 1){
                        return '导入';
                    }else{
                        return '未知';
                    }
                }
            },
            {
                "data": null,
                "render": function (base_data, type, full) {
	                    return '<a title="转移资源" href="javascript:;" class="ml-5 user-move" style="text-decoration:none" >' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6f7;</i>' +
	                    '</a>' +
	                    '<a style="text-decoration:none" class="ml-5 user-detail" href="javascript:;" title="查看详情">' +
	                    '	<i class="Hui-iconfont">&#xe6e0;</i>' +
	                    '</a> ' +
	                    '<a style="text-decoration:none" class="ml-5 user-chat-info" href="javascript:;" title="查看客户跟踪记录">' +
	                    '	<i class="Hui-iconfont">&#xe715;</i>' +
	                    '</a> ' +
	                    '<a title="编辑" href="javascript:;" class="ml-5 user-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a>';
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
    
	//用户详情
    $("body").on("click", "a.user-detail", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('用户详情', "${pageContext.request.contextPath}/saler/userdatapage?type=detail&userid=" +sData.userid,'','510');
    })
	//查看客户跟踪记录
    $("body").on("click", "a.user-chat-info", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	var index = layer.open({
    		type: 2,
    		title: '客户跟踪记录',
    		content: "${pageContext.request.contextPath}/saler/customerchatPage?userid=" +sData.userid
    	});
    	layer.full(index);
    })
    
    //用户修改
    $("body").on("click", "a.user-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('信息修改','${pageContext.request.contextPath}/saler/userdatapage?type=edit&userid='+sData.userid,'','510');
    })
    
	//根据部门id联动部门下的业务员
	$("select[name='deptid']").change(function() {
		var deptid = $(this).val();
		$.ajax({
			type: 'get',
			url: 'agentdeptidsaler',
			data: {deptid: deptid},
			success: function(data){
				if(data.code == 200){
					var str = '';
					if(data.data.length > 0){
						$.each(data.data, function(k, v) {
							str += '<option value="'+v.userid+'">'+v.realname+'</option>';
						})
					}else{
						str = '<option value="">无员工</option>';
					}
					$("#moveModal select[name='salerid']").html(str);
					$("#moveModal").modal('show');
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			}
		});	
	})
	
	$("#datatable").on("change", "input[type='checkbox']", function() {
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
    	if(cblist.length == 0){
    		//全不选
    		$("move-customer").addClass("disabled");
    	}else{
    		//有选中项
    		$(".move-customer").removeClass("disabled");
    	}
	})
	
	//资源集合
	var dataids = [];
    //转移单个资源modal
    $("#datatable").on('click', 'a.user-move', function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	dataids = [];
    	dataids[0] = sData.userid;
    	$("#moveModal").modal('show');
	})
    //批量转移资源modal
    $(".move-customer").click(function() {
    	var cblist = $("#datatable tbody input[type='checkbox']:checked");
    	if(cblist.length <= 0) return false;
    	
    	var cblist = $("#datatable tbody input[type='checkbox']:checked"),
		i = 0;
    	dataids = [];
		$.each(cblist, function(k, v) {
			var sData = api_table.fnGetData( $(v).parents("tr") );
			dataids[i] = sData.userid;
			i++
		})
    	$("#moveModal").modal('show');
	})
	
	
	//确认转移
	$("#moveModal #confirm").click(function() {
		var salerid = $("#moveModal select[name='salerid']").val();
		if($.trim(salerid) == ''){
			layer.msg('未选择有效的业务员',{icon: 5,time:2000});return false;
		}
		$.ajax({
			type: 'POST',
			url: 'movecustomer',
			data: {ids: dataids, salerid: salerid},
			success: function(data){
				if(data.code == 200){
					jq_table.ajax.reload(null, false);
				}else{
					layer.msg(data.msg,{icon: 5,time:2000});
				}
			},
			complete: function() {
				$("#moveModal").modal('hide');
			}
		});	
	})
    
    //查看备注
	$("body").on('click', 'button.remark', function() {
		var obj = $(this).parents('tr');
        if (api_table.fnIsOpen(obj)) {
            api_table.fnClose(obj);
        } else {
            var sData = api_table.fnGetData(obj);
            api_table.fnOpen(obj, sData.remark, "info_row order-info");
        }
	})
	
	
	
	//搜索框条件搜索
    $("#search").click(function() {
	    var sel = $("select.sel").val();
    	if(sel == 0){
    		//恢复默认
    		var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				param = undefined;
			}else return false;
    	}else if(sel == 1){
    		//使用手机号号搜索
    		var telno = $.trim($(".search-text input").val());
    		if(telno == '') return false;
    		param = {telno: telno};
    	}else if(sel == 2){
    		//使用姓名搜索
    		var realname = $.trim($(".search-text input").val());
    		if(realname == '') return false;
    		param = {realname: realname};
    	}else if(sel == 3){
    		//用户等级
    		var levelid = $("select[name='levelid']").val();
    		param = {levelid: levelid};
    	}else{
    		//不进行搜索
    		return false;
    	}
    	jq_table.settings()[0].ajax.data = param;
    	jq_table.ajax.reload();
	})
	$("select.sel").change(function() {
	    var val = $(this).val();
		$(".search-param").addClass("hidden");
		$(".search-param input").val("");
		if(val == 0){
			var tbdata = jq_table.settings()[0].ajax.data;
			if(tbdata != undefined){
				jq_table.settings()[0].ajax.data = undefined;
		    	jq_table.ajax.reload();
			}
		}else if(val == 1 || val == 2){
    		//输入框
    		$(".search-text").removeClass("hidden");
    	}else if(val == 3){
    		//选择框 用户等级
    		$(".search-levelname").removeClass("hidden");
    	}
	})
	
	 //评测列表
	$("body").on("click", "button.agent-teacher", function() {
		var sData = api_table.fnGetData( $(this).parents("tr") );
		layer_show('评测结果', "${pageContext.request.contextPath}/saler/userEvaluationResultPage?userid=" +sData.userid,'','550');
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