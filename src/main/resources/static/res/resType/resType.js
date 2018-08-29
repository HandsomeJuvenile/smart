layui.use(['form','layer','laydate','table','upload'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;

    var tableIns = table.render({
        id: "resTypeId",
        elem: "#resType",
        url: '/res/type/show',
        loading: true,
        page: true,
        limit: 10,
        limits: [10, 15, 20],
        cols: [[
            {checkbox: true, fixed: "true"},
            {field: "resId", title: "编号"},
            {field: "resName", title: "名称"},
            {field: '', title: '操作', toolbar: "#bar"}
        ]]
        , even: true
        , done: function () {
            layer.msg('加载完毕');
        }
    });

    //添加任务计划
    function addTask(edit){
        var title = "添加分类";
        var content = "/res/type/add";
        if (edit) {
            title = "修改分类";
            content = "/res/type/update";
        }
        var index = layer.open({
            title : title,
            type : 2,
            area : ["300px","250px"],
            content : content,
            success : function(layero, index){
                var body = $($(".layui-layer-iframe",parent.document).find("iframe")[0].contentWindow.document.body);
                if(edit){
                    body.find(".resId").val(edit.resId);
                    body.find(".resName").val(edit.resName);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回任务列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
    }

    $(".addResType_btn").click(function(){
        addTask();
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('taskListTab'),
            data = checkStatus.data,
            linkId = [];
        if(data.length > 0) {
            for (var i in data) {
                linkId.push(data[i].id);
            }
            layer.confirm('确定删除选中的任务？', {icon: 3, title: '提示信息'}
                , function(){
                    $.ajax({
                        type: 'DELETE',
                        url: '/Sys/task/batchDelete',
                        data:JSON.stringify(linkId),
                        success: function (data) {
                            layer.closeAll();
                            if (data > 0) {
                                layer.msg("删除成功", {icon: 6});
                            } else {
                                layer.msg("删除失败", {icon: 5});
                            }
                            tableIns.reload();
                        }
                    });
                }
                , function(){
                    layer.closeAll();
                    layer.msg('放弃删除',{icon:1});
                });
        }else{
            layer.msg("请选择需要删除的任务");
        }
    })

    //列表操作
    table.on('tool(resTContro)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'update'){ //编辑
            addTask(data);
        } else if(layEvent === 'del') { //删除
            layer.confirm('确定删除此任务？', {icon: 3, title: '提示信息'}
                , function(){
                    $.ajax({
                        type: 'delete',
                        url: '/res/type/del/' + data.resId,
                        contentType: "application/json",
                        success: function (data) {
                            layer.closeAll();
                            if (data > 0) {
                                layer.msg("删除成功", {icon: 6});
                            } else {
                                layer.msg("删除失败", {icon: 5});
                            }
                            tableIns.reload();

                        }
                    });
                    tableIns.reload();
                    layer.close(index);
                }, function(){
                    layer.closeAll();
                    layer.msg('放弃删除',{icon:1});
                });
        }
    });

    form.on("submit(addResType)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var datas = data.field;
        var restypeData = {resId:datas.resId,resName:datas.resName };
        var returnData;
        $.ajax({
            type:'post',
            url: '/res/type/add',
            data:JSON.stringify(restypeData),
            dataType:'json',
            async:false,
            contentType: "application/json;charset=utf-8",
            success:function (data) {
                returnData = data;
            }
        });
        setTimeout(function(){
            //top.layer.close(index);
            if (returnData.code == "0") {
                top.layer.msg(returnData.msg,{icon:6})
            }else if (returnData.code == "1"){
                top.layer.msg(returnData.msg,{icon:5})
            }
            //刷新父页面
            $(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
            layer.closeAll("iframe");
        },500);
        return false;
    })

    form.on("submit(updateResType)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var datas = data.field;
        var restypeData = {resId:datas.resId,resName:datas.resName };
        var returnData;
        $.ajax({
            type:'post',
            url: '/res/type/update',
            data:JSON.stringify(restypeData),
            dataType:'json',
            async:false,
            contentType: "application/json;charset=utf-8",
            success:function (data) {
                returnData = data;
            }
        });
        setTimeout(function(){
            //top.layer.close(index);
            if (returnData.code == "0") {
                top.layer.msg(returnData.msg,{icon:6})
            }else if (returnData.code == "1"){
                top.layer.msg(returnData.msg,{icon:5})
            }
            //刷新父页面
            $(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
            layer.closeAll("iframe");
        },500);
        return false;
    })
})