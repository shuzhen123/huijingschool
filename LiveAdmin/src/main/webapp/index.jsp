<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>Insert title here</title>
<script type="text/javascript" src="jquery-1.8.0.min.js"></script>
</head>
<body>
<button type="wxpay">微信支付</button>
<button type="alipay">支付宝支付</button>
<button type="upop">银联支付</button> 
<hr/>
accesstoken：<input type="text" id="token"><br/>
订单号：<input type="text" id="orderid">
<hr/>
支付类型：<label><input type="radio" name="repay" value="0" checked="checked">新支付 </label>
		<label><input type="radio" name="repay" value="1">重新支付 </label>

<div></div>
<script type="text/javascript">

$("button").click(function() {
	$.ajax({
		url: "/LiveServer/payment",
		type: "POST",
		data: {
			accesstoken: $("#token").val(), 
			orderid: $("#orderid").val(), 
			paytype: $(this).attr("type"), 
			repay: $("input[type='radio']:checked").val()
		},
		contentType: "application/x-www-form-urlencoded",
		dataType: "json",
		success: function(e) {
			$("div").html(e.data.cashier_url);
		}
	});
})

</script>
</body>
</html>