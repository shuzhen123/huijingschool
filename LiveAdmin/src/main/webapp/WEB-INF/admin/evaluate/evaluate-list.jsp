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
<title>风险测评管理</title>
</head>
<body>

<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">风险测评通过分数线</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<form class="form form-horizontal" id="form" >
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-4">分数线：</label>
						<div class="formControls col-xs-4 col-sm-4">
							<input type="text" class="input-text" placeholder="请填写分数线" name="score">
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

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span>风险测评管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r score-change-span">
			风险测评通过分数线：${score } 
			<a href="javascript:;" class="btn btn-success radius score-change" data-score="${score }">
				<i class="Hui-iconfont">&#xe61d;</i> 更改
			</a>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>问题类型</th>
					<th>问题名称</th>
					<th>答案</th>
					<th>分数</th>
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
			url: "riskEvaluateList",
			type: "POST",
		},
        columns: [
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                    return meta.settings._iDisplayStart + meta.row + 1;
                }
            },
            
            {"data": 'classname'},
            {"data": 'questionname'},
            {"data": 'answername'},
            {"data": 'score'},
            {
                "data": null,
                "render": function (base_data, type, full) {
                     var str = 
	                    '<a title="编辑" href="javascript:;" class="ml-5 user-edit" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6df;</i>' +
	                    '</a> '
                	return  str;
                }
            }
        ]
    });
    var api_table = $('#datatable').dataTable();
	
    //修改
    $("body").on("click", "a.user-edit", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer_show('分数修改','editRiskEvaluate?answerid='+sData.answerid);
    })
    
    $("body").on('click', 'a.score-change', function() {
		$('#myModal input[name="score"]').val($(this).attr('data-score'))
		$('#myModal').modal('show')
	})
    //分数线修改
    $("#myModal .confirm").click(function () {
		var score = $.trim($("#myModal input[name='score']").val());
    	if(!(/^[0-9]{1,}$/.test(score))){
    		layer.msg('请填写正确的分数线！',{icon: 7,time:2000}); return false;
    	}
		$.ajax({
	        url: 'editpassscore',
	        type: "POST",
	        data: {score: score},
	        success: function (result) {
	        	if(result.code == 200){
	        		layer.msg('操作成功',{icon: 1,time:1500});
	        		str = 
	        			'风险测评通过分数线：'+ score +
	        			' <a href="javascript:;" class="btn btn-success radius score-change" data-score="'+ score +'">'+
	        			'  <i class="Hui-iconfont">&#xe61d;</i> 更改'+
	        			'</a>';
	        		$("span.score-change-span").html(str);	
	        		$('#myModal').modal('hide')
	        	}else{
	        		layer.msg(result.msg, {icon: 5,time:1500});
	        	}
	        }
		})
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