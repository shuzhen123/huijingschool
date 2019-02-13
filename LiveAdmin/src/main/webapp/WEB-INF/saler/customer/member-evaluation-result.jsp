<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
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
<!--/meta 作为公共模版分离出去-->

<title>用户测评答案列表</title>
</head>
<body>
<article class="page-container">
	<div class="mt-20">
	<span>测评通过线：${scoreline }</span> &nbsp;&nbsp;&nbsp;&nbsp;
	<span>用户测评总分：${userScore }</span>
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>大类类型</th>
					<th>问题</th>
					<th>所选答案</th>
					<th>单项得分</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${answers}" varStatus="stauts">
					<tr>
						<td>${stauts.count}</td>
						<td>${item.classname }</td>
						<td>${item.questionname }</td>
						<td>${item.answername }</td>
						<td>${item.score }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</article>



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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>


<script type="text/javascript">
$(function(){
	
	$.extend($.fn.dataTable.defaults, {
        "language": {
            "paginate": {
                "first": "首页",
                "last": "尾页",
                "next": "下一页",
                "previous": "上一页",
            },
            "emptyTable": "无数据",
            "info": "从 _START_ 到 _END_ 共 _TOTAL_ 条",
            "infoEmpty": "无数据",
            "infoFiltered": " - 共 _MAX_ 条",
            // "infoPostFix": "——记录来自服务器",
            "lengthMenu": "显示 _MENU_ 条记录",
            "loadingRecords": "加载数据入中...",
            "processing": "数据载入中...",
            "search": "",
            "zeroRecords": "无数据"
        },
        "lengthMenu": [[5, 10, 20, 30, 50], [5, 10, 20, 30, 50]],
//            "pageLength": 4,
        "lengthChange": true,
        "sDom": "rtilp",
        "pagingType": "full_numbers",
        "ordering": false, //全局禁用排序
        "stateSave": true, //操作当前页，不跳到第一页
//            "dataSrc": "data",
        "deferRender": true,
        "processing": false,
        "bServerSide": false,
    });
	
	//初始化数据
	$("#datatable").DataTable();
   
    
});
</script>




</body>
</html>