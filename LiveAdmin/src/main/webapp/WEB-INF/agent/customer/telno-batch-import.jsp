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

<title>批量导入手机号</title>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form">
	
		<div class="row cl">
			<div class="formControls col-xs-12 col-sm-12" style="color: red;">
				注：导入手机号码格式为  电话 姓名 性别 省份，请使用制表符，逗号，空格
			</div>
		</div>
		<div class="row cl">
			<div class="formControls col-xs-12 col-sm-12">
				<textarea name="telnos" class="textarea"  placeholder="请填写手机号码" style="height: 200px;"></textarea>
			</div>
		</div>
		<div class="row cl">
			<div class="formControls col-xs-12 col-sm-12 msg">
				共输入行数<span style="color:red;">0</span>，有效行数<span style="color:red;">0</span>，无效行数<span style="color:red;">0</span>
			</div>
		</div>
	
		<div class="row cl">
			<div class="col-xs-12 col-sm-12" style="text-align: center;">
				<input class="btn btn-primary radius disabled" type="button" value="批量导入">
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>
<script type="text/javascript">
$(function(){
	 function randStr(l) {
        var str = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
        var rand = ""
        for (let i = 0; i < l; i++) {
            rand += str[parseInt(Math.random() * 62)]
        }
        return rand
	}
	
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	var temptel = [];
	
	//提交按钮状态
	$("textarea[name='telnos']").keyup(function() {
		if($(this).val() == ''){
			$("input[type='button']").addClass('disabled'); return false;
		}
        var n = randStr(32)
        var r = $(this).val().replace(/\n/g, n).split(n)
        temptel = []
        var counts=0, valid=0, brace=0;
        
        for (i = 0; i < r.length; i++) {
            var col_arr = r[i].replace(/[\t,\s]+/g, n).split(n)
            if($.trim(col_arr[0]) == '') continue;
            counts++;
            if(/^1(3[0-9]|4[57]|5[0-3 5-9]|7[01678]|8[0-9])\d{8}$/.test(col_arr[0])){
            	temptel[i] = [];
            	temptel[i][0] = col_arr[0];
            	temptel[i][1] = col_arr[1];
            	temptel[i][2] = col_arr[2];
            	temptel[i][3] = col_arr[3];
	            valid++
	            $("input[type='button']").removeClass('disabled');
			}else{
				brace++
			}
        }
		$('.msg').html('共输入行数<span style="color:red;">'+counts+'</span>，有效行数<span style="color:red;">'+valid+'</span>，无效行数<span style="color:red;">'+brace+'</span>');
	})
	
	//批量导入
	$("input[type='button']").click(function() {
		//layer.confirm($('.msg').html(), {icon: 3, title:'导入提示'}, function(index){
			var loading;
    		$.ajax({
    			type: 'POST',
    			url: 'telnobatchimport',
    			data: {temptel: JSON.stringify(temptel), restype: 'x'},
    			beforeSend: function() {
    				loading = layer.load(1);
    				$("input[type='button']").addClass('disabled');
				},
    			success: function(data){
    				if(data.code == 200){
    					layer.msg('操作成功!',{icon: 6,time:1000});
    					var index = parent.layer.getFrameIndex(window.name);
	                	parent.resultShow(data.data); 
	                	parent.layer.close(index);
    				}else{
    					layer.msg(data.msg,{icon: 5,time:1500});
    				}
    			},
    			complete: function() {
    				layer.close(loading);
    				$("input[type='button']").removeClass('disabled');
				}
    		});
    	//});
	})
	
});
</script> 
</body>
</html>