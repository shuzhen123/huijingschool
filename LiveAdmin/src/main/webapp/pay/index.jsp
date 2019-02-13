<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
		success: function(e) {
			$("div").html(e.data);
		}
	});
})

</script>
</body>
</html>