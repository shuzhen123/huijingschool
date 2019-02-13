<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<style type="text/css">
table#datatable td {
	text-align: left;
}
</style>
<title>课程评论列表</title>
</head>
<body>
<input id="course" class="hidden" value="${courseid }" >
<article class="page-container">
	<div class="panel panel-secondary">
		<div class="panel-header">评论列表</div>
		<div class="panel-body">
			<table id="datatable" class="table" style="width: 100%;">
			<thead>
				<tr role="row"><th style="width: 1614px;height: 0;padding: 0; border: 0;"></th></tr>
			</thead>
			<tbody>
			</tbody>
			</table>
			<div style=" clear:both"></div>
		</div>
	</div>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	$(".go-back").click(function() {
		var index = parent.layer.getFrameIndex(window.name);
    	parent.layer.close(index);
	});
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "${pageContext.request.contextPath}/tea/coursecommentlist",
			type: "POST",
			data: {courseid: $('#course').val()}
		},
		columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    var str = '<div class="panel panel-default">'+
                    	'<div class="panel-header">'+full.telno;
                    if(full.nickname != null && full.nickname != ''){
                    	str += '（'+full.nickname+'）';
                    }
                    str += 
                    '		<span style="font-weight: 400">评论于 '+ full.createtime +'</span> '+
                    '		<div class="star-bar star-bar-show size-S  va-m mr-10" style="display: inline-block;margin-left: 15px;">'+
                    '			<span class="star" style="width: '+ (parseInt(full.coursescore) * 2) +'0%"></span>'+
                    '		</div>'+
                    /* '		<strong class="va-m">'+ full.coursescore +'</strong>'+ */
                    <c:if test="${not empty role}">
                    '		<a style="text-decoration:none;color:#dd514c;" class="ml-5 r del-commont" href="javascript:;" title="删除评论">'+
                    '        	<i class="Hui-iconfont">&#xe609;</i>'+
                    '        </a> '+ 
                    </c:if>
                    '	</div>'+
                    '	<div class="panel-body" style="text-align: left;">'+ full.commentcontent
                    '	</div>'+
                    '</div>';
                    return str;
                }
            }
		]
	})
	var api_table = $('#datatable').dataTable();
	//删除评论
	$("#datatable").on('click', '.del-commont', function() {
		var sData = api_table.fnGetData( $(this).parents("tr") );
		layer.confirm('确定删除此用户评论？', {icon: 3, title:'删除提示'}, function(index){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/tea/delcoursecomment',
				data: {commentid: sData.id},
				success: function(data){
					if(data.code == 200){
						layer.msg('操作成功!',{icon: 6,time:1000});
						jq_table.ajax.reload(null, false);
					}else{
						layer.msg(data.msg,{icon: 5,time:1500});
					}
				},
				error:function(data) {
					layer.msg(data.msg,{icon: 5,time:1500});
				},
			});
    	});
	})
});
</script> 
</body>
</html>