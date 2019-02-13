<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>慧鲸学堂-管理员系统</title>
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
				<a class="logo navbar-logo-m f-l mr-10 visible-xs" href="index">H-ui</a>
				<span class="logo navbar-slogan f-l mr-10 hidden-xs">管理员系统</span> <a
					aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs"
					href="javascript:;">&#xe667;</a>
				<nav id="Hui-userbar"
					class="nav navbar-nav navbar-userbar hidden-xs">
					<ul class="cl">
						<li>后台管理</li>
						<li class="dropDown dropDown_hover"><a href="#"
							class="dropDown_A">${sessionScope.pc_session_key.realname } <i
								class="Hui-iconfont">&#xe6d5;</i></a>
							<ul class="dropDown-menu menu radius box-shadow">
								<li><a href="javascript:;" data-href="mydata"
									data-title="个人信息" onClick="openNewWindow(this)">个人信息</a></li>
								<li><a href="${pageContext.request.contextPath}/logout">切换账户</a></li>
								<li><a href="${pageContext.request.contextPath}/logout">退出</a></li>
							</ul></li>
						<li id="Hui-msg"><a href="javascript:void(0)" title="消息"
							data-href="feedback" data-title="消息管理"
							onClick="openNewWindow(this)"> <c:if test="${feedback != 0 }">
									<span class="badge badge-danger fb-count">${feedback}</span>
								</c:if> <i class="Hui-iconfont" style="font-size: 18px">&#xe68a;</i>
						</a></li>
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
			<dl id="menu-member">
				<dt>|- 用户管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="member"
							data-title="用户管理" href="javascript:void(0)">用户管理</a></li>
						<li><a data-href="turnplateuserpage"
							data-title="大转盘用户管理" href="javascript:void(0)">大转盘用户管理</a></li>
						<li><a data-href="memberclassify"
							data-title="用户分类管理" href="javascript:void(0)">用户分类管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-live">
				<dt>|- 直播管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="livecourse"
							data-title="直播节目管理" href="javascript:void(0)">直播节目管理</a></li>
						<li><a data-href="liveposterpage"
							data-title="直播节目管理" href="javascript:void(0)">直播展示图管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-course">
				<dt>|- 学堂管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="dashboardcourse"
							data-title="课程管理" href="javascript:void(0)">课程管理</a></li>
						<li><a data-href="coursetypepage"
							data-title="课程分类管理" href="javascript:void(0)">课程分类管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-coupon">
				<dt>
					<a data-href="coupon" data-title="卡券管理" href="javascript:void(0)">
						|- 卡券管理
					</a>
				</dt>
			</dl>
			<dl id="menu-goods">
				<dt>
					<a data-href="dashboardgoods" data-title="礼物管理" href="javascript:void(0)">
						|- 礼物管理
					</a>
				</dt>
			</dl>
			<dl id="menu-news">
				<dt>|- 资讯管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="newsmodel" data-title="咨询模块管理"
							href="javascript:void(0)">资讯模块管理</a></li>
						<li><a data-href="news" data-title="资讯管理"
							href="javascript:void(0)">资讯管理</a></li>
						<li><a data-href="vipnews" data-title="VIP资讯策略管理"
							href="javascript:void(0)">VIP资讯策略管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-viplevelpage">
				<dt>
					<a data-href="viplevelpage" data-title="vip赠送管理" href="javascript:void(0)">
						|- vip赠送管理
					</a>
				</dt>
			</dl>
			<dl id="menu-shares">
				<dt>
					<a data-href="shares" data-title="诊股管理" href="javascript:void(0)">
						|- 诊股管理
					</a>
				</dt>
			</dl>
			<dl id="menu-agent">
				<dt>
					<a data-href="agent" data-title="代理商管理" href="javascript:void(0)">
						|- 代理商管理
					</a>
				</dt>
			</dl>
			<dl id="menu-dashboardsensitive">
				<dt>
					<a data-href="dashboardsensitive" data-title="敏感文字管理" href="javascript:void(0)">
						|- 敏感文字管理
					</a>
				</dt>
			</dl>
			<dl id="menu-dashboardsensitive">
				<dt>
					<a data-href="dashboardRiskEvaluate" data-title="风险评测题目管理" href="javascript:void(0)">
						|- 风险评测管理
					</a>
				</dt>
			</dl>
			<dl id="menu-profit">
				<dt>|- 业务管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="dashboardprofit" data-title="分润管理"
							href="javascript:void(0)">分润管理</a></li>
						<li><a data-href="dashboardcompliance" data-title="业务员合规设置"
							href="javascript:void(0)">业务员合规设置</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-statistics">
				<dt>|- 数据统计 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<!-- <li><a data-href="/LiveAdmin/buding.html?statistics=1" data-title="数据统计"
							href="javascript:void(0)">VIP用户</a></li>
						<li><a data-href="/LiveAdmin/buding.html?statistics=2" data-title="数据统计"
							href="javascript:void(0)">活跃量</a></li>
						<li><a data-href="/LiveAdmin/buding.html?statistics=3" data-title="数据统计"
							href="javascript:void(0)">收看量</a></li>
						<li><a data-href="/LiveAdmin/buding.html?statistics=4" data-title="数据统计"
							href="javascript:void(0)">体验券/代金券</a></li> -->
						<li><a data-href="coursestatistics" data-title="课程购买统计"
							href="javascript:void(0)">课程购买统计</a></li>
						<li><a data-href="goodsstatistics" data-title="礼物赠送统计"
							href="javascript:void(0)">礼物赠送统计</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-banner">
				<dt>|- 轮播图管理 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="mpbanner" data-title="公众号轮播图管理"
							href="javascript:void(0)">公众号轮播图管理</a></li>
						<li><a data-href="/LiveAdmin/buding.html?banner=1" data-title="轮播图管理"
							href="javascript:void(0)">app轮播图管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-role">
				<dt>|- 权限设置 <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="adminmanagepage" data-title="管理员权限管理"
							href="javascript:void(0)">管理员权限设置</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-basedata">
				<dt>
					<a data-href="basedata" data-title="基础数据" href="javascript:void(0)">
						|- 基础数据
					</a>
				</dt>
			</dl>
		</div>
	</aside>
	<div class="dislpayArrow hidden-xs"> 
		<a class="pngfix" href="javascript:void(0);"
			onClick="displaynavbar(this)"></a>
	</div>
	<section class="Hui-article-box">
		<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active"><span title="我的桌面" data-href="welcome">我的桌面</span>
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
				<iframe scrolling="yes" frameborder="0" src="welcome"></iframe>
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