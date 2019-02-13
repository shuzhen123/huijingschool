<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>我的桌面</title>
<style type="text/css">
	text.highcharts-credits{
		display: none;
	}
</style>
</head>
<body>
<div class="page-container">
	<p class="f-20 text-success">欢迎使用-慧鲸学堂 <span class="f-14">代理商系统</span></p>
	查看：
	<select class="select" id="resource-time-show" size="1" style="width:auto;">
		<option value="1" selected>今日</option> 
		<option value="2">近一周</option> 
		<option value="3">近一月</option> 
	</select>
	展示方式：
	<select class="select" id="resource-line-show" size="1" style="width:auto;">
		<option value="1" selected>折线图</option> 
		<option value="2">柱状图</option>
	</select>
	<div id="resource" style="min-width:700px;height:400px"></div>
	
	查看：
	年<select class="select" id="earnings-year" size="1" style="width:auto;">
		<option value="2018">2018</option> 
		<option value="2019">2019</option> 
		<option value="2020">2020</option> 
	</select>
	月<select class="select" id="earnings-month" size="1" style="width:auto;">
		<option value="1">1</option> 
		<option value="2">2</option> 
		<option value="3">3</option> 
		<option value="4">4</option> 
		<option value="5">5</option> 
		<option value="6">6</option> 
		<option value="7">7</option> 
		<option value="8">8</option> 
		<option value="9">9</option> 
		<option value="10">10</option> 
		<option value="11">11</option> 
		<option value="12">12</option> 
	</select>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	展示方式：
	<select class="select" id="earnings-line-show" size="1" style="width:auto;">
		<option value="1" selected>折线图</option> 
		<option value="2">柱状图</option>
	</select>
	<div id="earnings" style="min-width:700px;height:400px"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script> 

<script type="text/javascript" src="${pageContext.request.contextPath}/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/global.js"></script>

<script>
$(function () {
	var myDate = new Date(), year = myDate.getFullYear(), month = myDate.getMonth()+1;
	$("#earnings-year option[value="+ year +"]").attr('selected', true);
	$("#earnings-month option[value="+ month +"]").attr('selected', true);
	
	//highcharts全局配置
	Highcharts.setOptions({
	    lang : {
		    contextButtonTitle: "导出",//导出按钮文字
		    //decimalPoint: ".",//小数点
		    downloadJPEG: "导出为JPEG",
		    downloadPDF: "导出为 PDF",
		    downloadPNG: "导出为PNG",
		    downloadSVG: "导出为SVG",
		    drillUpText: "后退到 {series.name}",
		    invalidDate: "无效时间值",
		    loading: "加载中...",
		    months: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
		    noData: "无数据",
		    //numericSymbolMagnitude: "",//国际单位符基数
		    //numericSymbols: "",//国际单位符
		    printChart: "打印",
		    //resetZoom: "",//重置缩放比例
		    //resetZoomTitle: "",//重置缩放标题
		    shortMonths: ["一","二","三","四","五","六","七","八","九","十","十一","十二"],//月份缩写
		    shortWeekdays: ["一","二","三","四","五","六","日"],//星期缩写
		    //thousandsSep: "",//千分号
		    weekdays: ["星期一","星期二","星期三","星期四","星期五","星期六","星期日"]//星期
	    }
	});
	var chars = {
        chart: {
            //type: 'column'
        },
        title: {
            text: "资源情况明细表"
        },
        subtitle: {
            text: ''
        },
        xAxis: {
             categories: []
        },
        yAxis: {
            min: 0,
            title: {
                text: '数量'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: 
            	'<tr>' +
            	'  <td style="color:{series.color};padding:0">{series.name}：  </td>' +
            	'  <td style="padding:0"><b>{point.y}</b></td>' +
            	'</tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0,
                borderWidth: 0
            }
        }
    }
	
	/***************** 资源数据  start *****************/
	var chart = Highcharts.chart('resource', chars);
	//全局数据
	var user = {};
	//加载数据
	var loadData = function(type) {
		$.ajax({
            url: 'resourcedata',
            type: "GET",
            data: {type: type},
            beforeSend: function() {
            	chart.showLoading();
			},
            success: function (result) {
            	if(result.code == 200){
            		var d = [];
        			$.each(result.data, function(k, v) {
        				d.push(v.num);
					})
					user = {name: '新增用户', data: d};
					
					var timelimit = [];
           			$.each(result.data, function(k, v) {
           				timelimit.push(v.strtime);
					})
           			chars.xAxis.categories = timelimit;
           			chars.subtitle.text = '资源一览 ['+ ($("#resource-time-show option:selected").text()) +']';
           			chars.series = [user];
           			if($("#resource-line-show option:selected").val() == 1){
           				chars.chart.type = '';
           			}else{
           				chars.chart.type = 'column';
           			}
            		chart = Highcharts.chart('resource', chars);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            },
            complete: function() {
            	chart.hideLoading();
			}
		})
	}
	
	loadData(1);
	
	//日期切换
	$("#resource-time-show").change(function() {
		loadData(this.value);
	})
	//线型切换
	$("#resource-line-show").change(function() {
		if(this.value == 1){
			chars.chart.type = '';
		}else{
			chars.chart.type = 'column';
		}
		chart = Highcharts.chart('resource', chars);
	})
	/***************** 资源数据 end  *****************/
	
	/***************** 收益数据 start  *****************/
	var opt = {
        chart: {
            //type: 'column'
        },
        title: {
            text: "收益情况明细表"
        },
        subtitle: {
            text: ''
        },
        xAxis: {
             categories: []
        },
        yAxis: {
            min: 0,
            title: {
                text: '资金（元）'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: 
            	'<tr>' +
            	'  <td style="color:{series.color};padding:0">{series.name}：  </td>' +
            	'  <td style="padding:0"><b>{point.y}</b></td>' +
            	'</tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0,
                borderWidth: 0
            }
        }
    }
	var chart1 = Highcharts.chart('earnings', opt);
	//全局数据
	var money = {};
	//加载数据
	var loadData1 = function(year, month) {
		$.ajax({
            url: 'earningsdata',
            type: "GET",
            data: {year: year, month: month},
            beforeSend: function() {
            	chart1.showLoading();
			},
            success: function (result) {
            	if(result.code == 200){
            		var d = [];
        			$.each(result.data, function(k, v) {
        				d.push(v.money);
					})
					money = {name: '资金收入', data: d};
					
					var timelimit = [];
           			$.each(result.data, function(k, v) {
           				timelimit.push(v.strtime);
					})
           			opt.xAxis.categories = timelimit;
           			opt.subtitle.text = '资金一览 ['+ year + '-' + month +']';
           			opt.series = [money];
           			if($("#earnings-line-show option:selected").val() == 1){
           				opt.chart.type = '';
           			}else{
           				opt.chart.type = 'column';
           			}
            		chart1 = Highcharts.chart('earnings', opt);
            	}else{
            		layer.msg(result.msg,{icon: 5,time:2000});
            	}
            },
            complete: function() {
            	chart1.hideLoading();
			}
		})
	}
	
	loadData1($("#earnings-year").val(), $("#earnings-month").val());
	
	//日期切换
	$("#earnings-year, #earnings-month").change(function() {
		loadData1($("#earnings-year").val(), $("#earnings-month").val());
	})
	//线型切换
	$("#earnings-line-show").change(function() {
		if(this.value == 1){
			opt.chart.type = '';
		}else{
			opt.chart.type = 'column';
		}
		chart1 = Highcharts.chart('earnings', opt);
	})
	
});	


</script>
</body>
</html>