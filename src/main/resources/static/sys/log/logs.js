layui.use(['table'],function(){
	var table = layui.table;

	//系统日志
    table.render({
        elem: '#logs',
        url : '/Sys/log/data',
        cellMinWidth : 95,
        page : true,
        height : "full-20",
        limit : 20,
        limits : [10,15,20,25],
        id : "systemLog",
        cols : [[
            {type: "checkbox", fixed:"left", width:70},
            {field: 'id', title: '序号', width:96, align:"center"},
            {field: 'userId', title: '用户id', width:200 ,align:'center'},
            {field: 'username', title: '操作人', width:164,align:'center'},
            {field: 'operation', title: '操作',  align:'center',width:151,minWidth:130},
            {field: 'time', title: '耗时(ms)',width:143,align:'center',templet:function(d){
                return '<span class="layui-btn layui-btn-normal layui-btn-xs">'+d.timeConsuming+'</span>'
            }},
            {field: 'method', title: '方法',  align:'center',minWidth:420},
            {field: 'params', title: '参数',  align:'center',width:94, minWidth:50},
            {field: 'ip', title: 'IP地址',  align:'center',width:120, minWidth:100},
            {field: 'gmtCreate', title: '操作时间', align:'center', width:210}
        ]]
    });
 	
})
