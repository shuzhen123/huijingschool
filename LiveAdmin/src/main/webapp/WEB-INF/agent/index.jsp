﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico">
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
<title>慧鲸学堂-代理商系统</title>
<style type="text/css">
	dt > a:hover{
		text-decoration:none;
	}
</style>
</head>
<body>
	<header class="navbar-wrapper">
		<div class="navbar navbar-fixed-top">
			<div class="container-fluid cl">
				<a class="logo navbar-logo f-l mr-10 hidden-xs" href="index">慧鲸学堂</a>
				<a class="logo navbar-logo-m f-l mr-10 visible-xs" href="index.do">H-ui</a>
				<span class="logo navbar-slogan f-l mr-10 hidden-xs">代理商系统</span> <a
					aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs"
					href="javascript:;">&#xe667;</a>
				<nav id="Hui-userbar"
					class="nav navbar-nav navbar-userbar hidden-xs">
					<ul class="cl">
						<li>主播代理商</li>
						<li class="dropDown dropDown_hover"><a href="#"
							class="dropDown_A nameshow">${sessionScope.pc_session_key.realname } <i
								class="Hui-iconfont">&#xe6d5;</i></a>
							<ul class="dropDown-menu menu radius box-shadow">
								<li><a href="javascript:;" data-href="mydata" data-title="个人信息" onClick="openNewWindow(this)">个人信息</a></li>
								<li><a href="${pageContext.request.contextPath}/logout">切换账户</a></li>
								<li><a href="${pageContext.request.contextPath}/logout">退出</a></li>
							</ul></li>
						<li id="Hui-skin" class="dropDown right dropDown_hover"><a
							href="javascript:;" class="dropDown_A" title="换肤"><i
								class="Hui-iconfont" style="font-size: 18px">&#xe62a;</i></a>
							<ul class="dropDown-menu menu radius box-shadow">
								<li><a href="javascript:;" data-val="default"
									title="默认（黑色）">默认（黑色）</a></li>
								<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
								<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
								<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
								<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
								<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</div>
	</header>

	<aside class="Hui-aside">
		<div class="menu_dropdown bk_2">
			<dl id="menu-user">
				<dt>
					<a data-href="customerpool" data-title="资源池" href="javascript:void(0)">
						|- 资源池
					</a>
				</dt>
			</dl>
			<dl id="menu-user">
				<dt>
					<a data-href="courseuser" data-title="客户管理" href="javascript:void(0)">
						|- 客户管理
					</a>
				</dt>
			</dl>
			<dl id="menu-user">
				<dt>
					<a data-href="teacher" data-title="讲师管理" href="javascript:void(0)">
						|- 讲师管理
					</a>
				</dt>
			</dl>
			<dl id="menu-popu">
				<dt>|- 推广管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a data-href="agentpopu?type=1"
							data-title="二维码推广" href="javascript:void(0)">二维码推广</a></li>
						<li><a data-href="agentpopu?type=2"
							data-title="直播间推广" href="javascript:void(0)">直播间推广</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-chat">
				<dt>|- 员工通讯管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a data-href="salerchatmanage?type=tell"
							data-title="通话记录管理" href="javascript:void(0)">通话记录管理</a></li>
						<li><a data-href="salerchatmanage"
							data-title="聊天记录管理" href="javascript:void(0)">聊天记录管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-personnel">
				<dt>|- 人事管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a data-href="deptmanage"
							data-title="部门管理" href="javascript:void(0)">部门管理</a></li>
						<li><a data-href="salermanage"
							data-title="员工管理" href="javascript:void(0)">员工管理</a></li>
						<li><a data-href="dashboardsalerlevel"
							data-title="员工等级" href="javascript:void(0)">员工等级</a></li>
						<li><a data-href="salertaskmanage"
							data-title="员工任务" href="javascript:void(0)">员工任务</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-statement">
				<dt>|- 报表管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a data-href="orderstatistics"
							data-title="课程消费统计" href="javascript:void(0)">课程消费统计</a></li>
						<li><a data-href="dashboardgoods"
							data-title="礼物订单" href="javascript:void(0)">礼物订单</a></li>
						<li><a data-href="dashboardwagecollect"
							data-title="提成汇总" href="javascript:void(0)">提成汇总</a></li>
						<li><a data-href="dashboardsalerviolator"
							data-title="提成汇总" href="javascript:void(0)">业务员合规</a></li>
					</ul>
				</dd>
			</dl>
			<!-- <dl id="menu-finance">
				<dt>|- 财务管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a data-href="/LiveAdmin/buding.html?finance=2"
							data-title="账户流水" href="javascript:void(0)">账户流水</a></li>
						<li><a data-href="/LiveAdmin/buding.html?finance=1"
							data-title="提现管理" href="javascript:void(0)">提现管理</a></li>
					</ul>
				</dd>
			</dl> -->
		</div>
	</aside>
	<div class="dislpayArrow hidden-xs">
		<a class="pngfix" href="javascript:void(0);" ></a>
	</div>
	<section class="Hui-article-box">
		<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active"><span title="我的桌面" data-href="welcome.do">我的桌面</span>
						<em></em></li>
				</ul>
			</div>
			<div class="Hui-tabNav-more btn-group">
				<a id="js-tabNav-prev" class="btn radius btn-default size-S"
					href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a
					id="js-tabNav-next" class="btn radius btn-default size-S"
					href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
			</div>
		</div>
		<div id="iframe_box" class="Hui-article">
			<div class="show_iframe">
				<div style="display: none" class="loading"></div>
				<iframe scrolling="yes" frameborder="0" src="welcome.do"></iframe>
			</div>
		</div>
	</section>

	<div class="contextMenu" id="Huiadminmenu">
		<ul>
			<li id="closethis">关闭当前</li>
			<li id="closeall">关闭全部</li>
		</ul>
	</div>
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
	<script type="text/javascript">
		/*新tab*/
		function openNewWindow(e) {
			var href = $(e).attr('data-href');
			$("#min_title_list").find('span[data-href="' + href + '"]')
					.parent().remove();
			$("#iframe_box iframe[src='" + href + "']").parent().remove();
			creatIframe(href, $(e).attr('data-title'))
		}

		/*个人信息*/
		function myselfinfo() {
			layer.open({
				type : 1,
				area : [ '300px', '200px' ],
				fix : false, //不固定
				maxmin : true,
				shade : 0.4,
				title : '查看信息',
				content : '<div>管理员信息</div>'
			});
		}
	</script>

</body>
</html>