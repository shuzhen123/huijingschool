//ajax全局错误处理
$.ajaxSetup({
	error:function(data) {
		if(data.status == 402){
			layer.msg("权限不足！",{icon: 4,time:2000});
		}else if(data.status == 400){
			layer.msg("客户发送的请求在语法上是错误的！",{icon: 4,time:2000});
		}else if(data.status == 404){
			layer.msg("会话超时，请重新登录！",{icon: 4,time:2000});
		}else if(data.status == 405){
			layer.msg("请求方式不允许！",{icon: 4,time:2000});
		}
	},
});	

//时间格式化
Date.prototype.format = function(fmt) { 
	var o = { 
		"M+" : this.getMonth()+1,                 //月份 
		"d+" : this.getDate(),                    //日 
		"h+" : this.getHours(),                   //小时 
		"m+" : this.getMinutes(),                 //分 
		"s+" : this.getSeconds(),                 //秒 
		"q+" : Math.floor((this.getMonth()+3)/3), //季度 
		"S"  : this.getMilliseconds()             //毫秒 
	}; 
	if(/(y+)/.test(fmt)) {
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	}
	for(var k in o) {
		if(new RegExp("("+ k +")").test(fmt)){
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		}
	}
	return fmt; 
}   
