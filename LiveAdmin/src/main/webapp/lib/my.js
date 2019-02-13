$.extend($.fn.dataTable.defaults, {
        "language": {
            "paginate": {
                "first": "首页",
                "last": "尾页",
                "next": "下一页",
                "previous": "上一页",
            },
            "emptyTable": "无数据",
            "info": "从 _START_ 到 _END_ 共 _TOTAL_ 条",
            "infoEmpty": "无数据",
            "infoFiltered": " - 共 _MAX_ 条",
            // "infoPostFix": "——记录来自服务器",
            "lengthMenu": "显示 _MENU_ 条记录",
            "loadingRecords": "加载数据入中...",
            "processing": "数据载入中...",
            "search": "",
            "zeroRecords": "无数据"
        },
        "lengthMenu": [[5, 10, 20, 30, 50], [5, 10, 20, 30, 50]],
//            "pageLength": 4,
        "lengthChange": true,
        "sDom": "rtilp",
        "pagingType": "full_numbers",
        "ordering": false, //全局禁用排序
        "stateSave": true, //操作当前页，不跳到第一页
//            "dataSrc": "data",
        "deferRender": true,
        "processing": true,
        "bServerSide": true,
    });
//当你需要多条件查询，你可以调用此方法，动态修改参数传给服务器
/*
var oTable = $("#example").DataTable({
    ajax: {
        url: "dataList.action",
        data: {
            args1: "我是固定传参的值，在服务器接收参数[args1]"
        }
    }
});
function reloadTable() {
    var name = $("#seName").val();
    var admin = $("#seAdmin").val();
    var param = {
        "obj.name": name,
        "obj.admin": admin
    };
    oTable.settings()[0].ajax.data = param;
    oTable.ajax.reload();
}


$("#accounty").select2({
        placeholder: "请输入手机号码",
        language: "zh-CN",
        minimumInputLength: 3,//输入多少长度的值的时候执行后台请求查询
        maximumInputLength: 11,//输入多少长度的值的时候执行后台请求查询
        params: {
            contentType: 'application/json',//此处可修改contentType类型（这个地方坑了我好久，不知道还要加params包起来）
        },
        allowClear: true,
        //select2动态加载数据
        ajax: {
            url: "findphones",
            dataType: 'json',
            type: 'post',
            delay: 250,
            cache:true,
            data: function (params) {
                return {
                    key: params.term,
                    page: params.page || 1,
                };
            },
            processResults: function (result, params) {
                params.page = params.page || 1;
                return {
                    results: result.items,
                    pagination: {
                        more: (params.page * result.length) < result.total //每页30条数据
                    }
                };
            },
        },
        escapeMarkup: function (markup) { return markup; },
        //显示的值（加载的在下拉框的值）
        templateResult: function (obj) {
            return obj.text;
        },
        templateSelection: function(resule) {
            return resule.text;
        },

    });

*/

var betchdel = function (url, ids, jq_table) {
	$.ajax({
		type: 'POST',
		url: url,
		data: {ids: ids},
		success: function(data){
			if(data.code == 200){
				layer.msg('操作成功!',{icon: 6,time:1000});
				jq_table.ajax.reload(null, false);
			}else{
				layer.msg(data.msg,{icon: 5,time:1500});
			}
		}
	});	
}