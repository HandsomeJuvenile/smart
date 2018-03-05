layui.use("table", function() {
    var table = layui.table;
    table.render({
        id: "roleId",
        elem: "#role",
        url: '/Sys/role/data/',
        loading: true,
        page: true,
        limit: 10,
        limits: [10, 15, 20],
        cols: [[
            {checkbox: true, fixed: "true"},
            {field: "rName", title: "权限名"},
            {field: "rStatus", title: "状态"},
            {field: "remark", title: "备注"},
            {field: "createTime", title: "创建时间"},
            {field: '', title: '操作', toolbar: "#bar"}
        ]]
        , even: true
        , done: function () {
            layer.msg('加载完毕');
        }
    });

    table.on('checkbox(roleControl)', function(obj) {
        console.log(obj);
    });

    table.on('tool(roleControl)',function (obj) {
        var data = obj.data;
        var id = data.rId;
        var ids = new Array();
        ids.push(id);
        layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'update'){
            var url = "/Sys/role/showOne?rid="+id;
            window.location.href=url;
        }
        if(layEvent === 'del'){
            layer.confirm('确认删除吗？', {
                btn: ['删除','放弃'] //按钮
            }, function(){
                delet(ids);
            }, function(){
                layer.msg('放弃删除',{icon:1});
            });
        }
    });

    var $ = layui.$, active = {
        getCheckData: function(){ //获取选中数据
            var checkStatus = table.checkStatus('roleId')
                ,data = checkStatus.data;
            var rids = new Array();
            for (var i=0;i<data.length;i++) {
                rids.push(data[i].rId);
            }
            if (rids.length<1){
                layer.msg("请选择要删除的行!",{icon:5})
                return false;
            }
            layer.confirm('确认删除吗？', {
                btn: ['删除','放弃'] //按钮
            }, function(){
                delet(rids);
            }, function(){
                layer.msg('放弃删除',{icon:1});
            });
        },
        search:function () {
            var search = $('#searchrName').val();
            var checkValue=$("#searchTitle").val();  //获取Select选择的Value
            if(checkValue == ''){
                layer.msg("请选择搜索条件",{icon:5})
                return false;
            }

            table.reload('roleId', {
                where: {
                    rName: search
                }
            });
        }

    };

    $('#roleTableBtn .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    function delet(ids) {
        $.ajax({
            type:'DELETE',
            url:'/Sys/role/deleteRole',
            data:JSON.stringify(ids),
            contentType: "application/json",
            beforeSend:function () {
                index = layer.load(1);
            },
            success:function(data) {
                if(data > 0){
                    layer.close(index);
                    table.reload("roleId"); //此处是上文提到的 初始化标识id
                }
                if(data === 0){
                    layer.close(index);
                    layer.msg('删除失败',{icon:5});
                    return false;
                }
            }
        });
    }
})
$(function () {

    $("#createRole").click(function(){
        window.location.href = "/Sys/role/toCreate";
    });

    $("#return").click(function () {
        window.history.back();
    });



});

