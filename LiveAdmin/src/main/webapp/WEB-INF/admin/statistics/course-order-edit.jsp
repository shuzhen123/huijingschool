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

<title>添加代理商</title>
</head>
<body>
<article class="page-container">
	<form action="courseorderpay" method="post" class="form form-horizontal" id="form">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">用户账号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${order.telno }
				<input type="text" class="hidden" value="${order.userid }" name="userid">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">用户昵称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${order.nickname }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">订单号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				${order.orderno }
				<input type="text" class="hidden" value="${order.orderno }" name="orderno">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">订单原金额（￥）：</label>
			<div class="formControls col-xs-3 col-sm-3">
				${order.money }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">订单金额（￥）：</label>
			<div class="formControls col-xs-3 col-sm-3">
				<input type="text" class="input-text" value="${order.money }" placeholder="请填写付款金额" name="input_money">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">实付金额（￥）：</label>
			<div class="formControls col-xs-3 col-sm-3">
				<input type="text" class="input-text disabled" onfocus="this.blur()" value="${order.money }" name="money">
			</div>
			<div class="formControls col-xs-5 col-sm-5 cpflag" style="color: red;"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">订单有效期：</label>
			<div class="formControls col-xs-3 col-sm-3">
				<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})" value="${order.validitytime }" name="validitytime" class="input-text Wdate">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">支付方式：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="paytype" size="1">
						<option value="3" <c:if test="${order.paytype == 3 }">selected</c:if> >线下打款</option>
						<option value="1" <c:if test="${order.paytype == 1 }">selected</c:if> >支付宝</option>
						<option value="2" <c:if test="${order.paytype == 2 }">selected</c:if> >微信</option>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">使用代金券：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="cashcouponid" size="1">
						<option value="" data-price="0">不使用代金券</option>
						<c:forEach var="item" items="${coupon }">
							<option value="${item.id}" data-price="${item.price }">${item.cpname }（￥ ${item.price }）</option> 
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">订单状态：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:auto;">
					<select class="select" name="paystatus" size="1">
						<option value="0" <c:if test="${order.paystatus == 0 }">selected</c:if> >未支付</option>
						<option value="1" <c:if test="${order.paystatus == 1 }">selected</c:if> >已支付</option>
					</select>
				</span>
			</div>
		</div>
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	//手动填写订单金额
	$('input[name="input_money"]').change(function(){
		//手动填写订单金额
		var money = parseFloat($(this).val().replace(/[^0-9.]/g,'') || 0).toFixed(2)
		$(this).val(money);
		//代金券金额
		var price = parseFloat($("select[name='cashcouponid'] option:selected").attr('data-price')) * 100;
		//实付金额
		$('input[name="money"]').val((money*100 - price)<0?0:((money*100 - price)/100).toFixed(2));
	})
	//代金券选择
	$("select[name='cashcouponid']").change(function() {
		//代金券金额
		var price = parseFloat($(this).find("option:selected").attr('data-price')) * 100;
		//原价
		var money = parseFloat($("input[name='input_money']").val()) * 100;
		//折后价
		$("input[name='money']").val(((money - price)/100)<0?0:((money - price)/100).toFixed(2))
		
		if(price == 0) $(".cpflag").html('');
		else $(".cpflag").html('已使用代金券');
	})
	
	$("#form").validate({
		rules:{
			money:{
				required: true,
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$("input[type='submit']").addClass('disabled');
			$(form).ajaxSubmit({
				success: function(data) {
	                if(data.code == 200){
	                	var index = parent.layer.getFrameIndex(window.name);
	                	parent.$("#outflush").click(); 
	                	parent.layer.close(index);
	                }else{
	                	$("input[type='submit']").removeClass('disabled');
	                	layer.msg(data.msg,{icon: 5,time:1500});
	                }
	            }
			});
		}
	});
});
</script> 
</body>
</html>