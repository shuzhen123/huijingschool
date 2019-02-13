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
table#datatable td.info_row{
	text-align: left;
}
</style>
<title>聊天跟踪记录</title>
</head>
<body>
<div class="page-container">
	 <div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">
			<a href="javascript:;" class="btn btn-primary radius add-cus">
				<i class="Hui-iconfont">&#xe600;</i> 添加聊天记录
			</a>
		</span> 
		
	 </div>  

	
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>跟踪业务员</th>
					<th>聊天记录</th>
					<th>记录创建时间</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-audioplayer/audioplayer.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootbox/bootbox.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "${pageContext.request.contextPath}/saler/customerchatList",
			type: "POST",
			data: {'userid': '${userid }'}
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
			{"data": 'salername'},
			 {
	               "data": null,
	               "mRender": function (data, type, full) {
	               	return '<button type="button" class="btn size-MINI show btn-primary-outline radius">点击查看</button>';
	               }
             },
            {"data": 'createtime'}
        ]
    });
    var api_table = $('#datatable').dataTable();
    
  	//查看记录
	$("body").on('click', 'button.record', function() {
		var obj = $(this).parents('tr');
        if (api_table.fnIsOpen(obj)) {
            api_table.fnClose(obj);
        } else {
            var sData = api_table.fnGetData(obj);
            api_table.fnOpen(obj, sData.words, "info_row order-info");
        }
	})
    
	//查看违规记录
	$("body").on("click", "button.violator", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('违规记录', '${pageContext.request.contextPath}/saler/violatorpage?chatid=' +sData.wordid,'','510');
    })

 	//添加记录
 	$(".add-cus").click(function() {
		layer_show('添加记录', '${pageContext.request.contextPath}/saler/addTrackRecordPage?userid=${userid }');
	})

  	
	  //点击跟踪记录详细
    $("#datatable").on("click", ".show", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.open({
    		type: 1,
     		area: ['600px','476px'],
    		fix: true, //不固定
    		maxmin: true,
    		shade:0.4,
    		title: '内容详情',
    		content: '<div style="padding: 10px;">'+sData.record+'</div>'
    	});
    	$("a.layui-layer-max").remove();
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