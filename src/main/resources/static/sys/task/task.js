layui.use(['form','layer','laydate','table','upload'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;

    //列表
    var tableIns = table.render({
        elem: '#taskList',
        url : '/Sys/task/data',
        page : true,
        cellMinWidth : 95,
        height : "full-104",
        limit : 20,
        limits : [10,15,20,25],
        id : "taskListTab",
        cols : [[
            {type: "checkbox", fixed:"left", width:50,align:'center'},
            {field: 'jobName', title: '任务名称', width:180, align:"center"},
            {field: 'jobGroup', title: '任务分组', minWidth:240,align:'center'},
            {field: 'beanClass', title: '任务类',width:300,align:'center'},
            {field: 'cronExpression', title: 'cron表达式',minWidth:200, align:'center'},
            {field: 'jobStatus', title: '启停', align:'center', templet: '#switchTpl', unresize: true},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:110,width:100},
            {field: '', title: '操作', toolbar: "#bar",align:'center'}
        ]]
    });

    //监听启用禁用操作
    form.on('switch(statusFilter)', function(obj){
        layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        var status = obj.elem.checked ? 1 : 0;
        var taskData = {'id':this.id,'jobStatus':status};
        $.ajax({
            type:'post',
            url:'/Sys/task/updateStatu',
            data:taskData,
            beforeSend:function () {
                 index = layer.load(1);
            },
            success:function (data) {
                if (data > 0) {
                    layer.msg("修改成功",{icon:6})
                }else {
                    layer.msg("修改失败",{icon:5})
                }
                layer.close(index);
                table.reload("taskList"); //此处是上文提到的 初始化标识id
            }
        });
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("taskListTab",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容",{icon:5});
        }
    });

    //添加任务计划
    function addTask(edit){
        var index = layer.open({
            title : "添加任务",
            type : 2,
            area : ["800px","400px"],
            content : "/Sys/task/add",
            success : function(layero, index){
                var body = $($(".layui-layer-iframe",parent.document).find("iframe")[0].contentWindow.document.body);
                if(edit){
                    body.find(".jobName").val(edit.jobName);
                    body.find(".jobGroup").val(edit.jobGroup);
                    body.find(".beanClass").val(edit.beanClass);
                    body.find(".cronExpression").val(edit.cronExpression);
                    body.find(".description").val(edit.description);
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
    $(".addTask_btn").click(function(){
        addTask();
    })

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
    table.on('tool(taskList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'update'){ //编辑
            addTask(data);
        } else if(layEvent === 'del') { //删除
            layer.confirm('确定删除此任务？', {icon: 3, title: '提示信息'}
                , function(){
                    $.ajax({
                        type: 'post',
                        url: '/Sys/task/delete/' + data.id,
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


    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //添加时间
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());

    form.on("submit(addTask)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var datas = data.field;
        var taskData = {jobName:datas.jobName,jobGroup:datas.jobGroup ,beanClass:datas.beanClass,cronExpression:datas.cronExpression
                        ,description:datas.description}
        var returnData;
        $.ajax({
            type:'post',
            url: '/Sys/task/add',
            data:JSON.stringify(taskData),
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