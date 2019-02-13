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
<link href="${pageContext.request.contextPath}/lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css" >
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>反馈列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 用户反馈管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" class="btn btn-danger disabled  radius batch-del">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			</a>
		</span>
		<span class="dropDown r"> 
			<a class="btn btn-primary radius" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><i class="Hui-iconfont">&#xe709;</i> 分类</a>
			<ul class="dropDown-menu menu radius box-shadow">
				<li><button class="btn btn-default size-S show-all" type="button" style="width:100%;">全部</button></li>
				<li><button class="btn btn-danger size-S show-wait" type="button" style="width:100%;">待处理</button></li>
				<li><button class="btn btn-warning size-S show-dealing" type="button" style="width:100%;">处理中</button></li>
				<li><button class="btn btn-success size-S show-success" type="button" style="width:100%;">处理完成</button></li>
 			</ul>
		</span>
	</div>
	<div class="mt-20">
		<table id="datatable" class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th>序号</th>
					<th>用户姓名</th>
					<th>反馈内容</th>
					<th>图片</th>
					<th>联系电话</th>
					<th>创建时间</th>
					<th>状态</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/lightbox2/2.8.1/js/lightbox.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.15/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/my.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	var jq_table = $("#datatable").DataTable({
		ajax:{
			url: "feedbacklist.do",
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
                	if(full.realname == null){
                		return '匿名用户';
                	}else{
	                	return full.realname;
                	}
                }
            },
            {"data": 'content'},
            {
                "data": null,
                "mRender": function (data, type, full, meta) {
                	if(full.picurl == ''){
                		return '未上传图片';
                	}else{
                		var imgs = full.picurl.split(','), str=''; 
                		$.each(imgs, function(k, v) {
							str += '<a href="'+ v +'" data-lightbox="gallery" data-title="用户反馈图片"><img src="'+ v +'" style="width: 60px;"></a>';
						})
						return str;
                	}
                }
            },
            {"data": 'teloremail'},
            {"data": 'createtime'},
            {
                "data": null,
                "mRender": function (data, type, full) {
                	/* 0待处理，1处理中，2处理完成 */
                	if(full.entkbn == 0){
                		return '<span class="label label-danger radius">待处理</span>';
                	}else if(full.entkbn == 1){
                		return '<span class="label label-warning radius">处理中</span>';
                	}else if(full.entkbn == 2){
                		return '<span class="label label-success radius">处理完成</span>';
                	}else{
                		return "";
                	}
                }
            },
            {
                "data": null,
                "render": function (base_data, type, full) { 
                     var str = '';
                     /* 0待处理，1处理中，2处理完成 */
                     if(full.entkbn == 0){
                    	 str +=
                    		 '<a title="标为正在处理中" href="javascript:;" class="ml-5 fb-dealing" style="text-decoration:none">' +
     	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe60c;</i>' +
     	                    '</a> ';
                     }else if(full.entkbn == 1){
                    	 str +=
                    		 '<a title="标为处理完成" href="javascript:;" class="ml-5 fb-success" style="text-decoration:none">' +
     	                    '	<i class="Hui-iconfont" style="color: #3bb4f2;">&#xe6a7;</i>' +
     	                    '</a> ';
                     }
	                    
	                 str += 
	                	 '<a title="删除" href="javascript:;" class="ml-5 fb-del" style="text-decoration:none">' +
	                    '	<i class="Hui-iconfont" style="color: #dd514c;">&#xe6e2;</i>' +
	                    '</a>'
                	return  str;
                }
            }
        ]
    });
    jq_table.search('').draw();
    var api_table = $('#datatable').dataTable();
    
    //反馈删除
    $("body").on("click", "a.fb-del", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定删除用户反馈？', {icon: 3, title:'提示'}, function(index){
    		betchdel('delfeedback.do', [sData.id], jq_table);
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
    		var ids = [], i = 0;
    		$.each(cblist, function(k, v) {
    			var sData = api_table.fnGetData( $(v).parents("tr") );
    			ids[i] = sData.id;
    			i++
			})
			layer.confirm('确定批量删除用户？', {icon: 3, title:'批量删除'}, function(index){
				betchdel('delfeedback.do', ids, jq_table);	
	    	});
    	}
    	
	})
    
	var dealfeedback = function (id, status, jq_table) {
		$.ajax({
			type: 'POST',
			url: 'changefbstatus.do',
			data: {id: id, status: status},
			success: function(data){
				if(data.code == 200){
					if(status == 1){
						//更新父窗口显示数字
						if(data.data == 0){
							parseInt(parent.$("span.fb-count").remove());
						}else if(data.data > 0){
							parent.$("span.fb-count").html(data.data);
						}
					}
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
	}
    
    //标为处理中
    $("body").on("click", "a.fb-dealing", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定将次反馈信息标为处理中？', {icon: 3, title:'提示'}, function(index){
    		dealfeedback(sData.id, 1, jq_table);
    	});
    })
    //标为处理完成
    $("body").on("click", "a.fb-success", function() {
    	var sData = api_table.fnGetData( $(this).parents("tr") );
    	layer.confirm('确定将次反馈信息标为处理完成？', {icon: 3, title:'提示'}, function(index){
    		dealfeedback(sData.id, 2, jq_table);
    	});
    })
    
    //分类搜索 0待处理，1处理中，2处理完成
    $(".menu button").click(function() {
    	var tbdata = jq_table.ajax.params(), 
    		olddata = tbdata.search.value;
    	var param;
		if($(this).hasClass('show-all')){
			//全部数据
			if(olddata == '') return false;
	    	param = '';
		}else if($(this).hasClass('show-wait')){
			//待处理数据
    		param = 'wait';
    		if(olddata == param) return false;
		}else if($(this).hasClass('show-dealing')){
			//处理中数据
    		param = 'deal';
    		if(olddata == param) return false;
		}else if($(this).hasClass('show-success')){
			//处理完成数据
    		param = 'success';
    		if(olddata == param) return false;
		}else{return true;}
		jq_table.search(param).draw();
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