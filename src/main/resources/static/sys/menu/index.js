$(function () {
    var zNodess;
    $.ajax({
        type:'post',
        url:'/Sys/menu/showTreeData',
        async:false,
        contentType: "application/json",
        success:function (data) {
            zNodess = data;
        },
        error:function(){
            layer.msg("失败！",{icon:6});
        }
    });

    layui.use('tree', function(){
        layui.tree({
            elem: '#demoTree' //传入元素选择器
            , nodes: zNodess
            ,click: function (node) {
                toMenu(node.menuId,"")
            }
        });
    });


    /**
     * 添加菜单
     */
    $("#addPPer").click(function () {
        var table = layui.table;
        layer.open({
            type: 0,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            title:"添加菜单",
            offset: 'auto' ,//水平居中
            id: 'updateMenu', //防止重复弹出，唯一表示
            content:'<div style="width:250px;">'+
            '<div class="detailMes fl">'+
            '<form action="" method="post">'+
            '<tr>'+
            '<td class="fl" style="width:250px;">'+
            '<p class="fl" style="width:250px;">菜单名称：</p>'+
            '<input type="text" name="name" required="true" id="name" value="" lay-verify="required" placeholder="请输入菜单名称" autocomplete="off" class="layui-input fl"/>'+
            ' </td>'+
            '<td class="fl">'+
            '<p class="fl" style="width:250px;">路径：</p>'+
            '<input type="text" name="url" required="true" id="url" value="" lay-verify="required" placeholder="请输入路径" autocomplete="off" class="layui-input fl"/>'+
            '</td>'+
            '<td>'+
            '<p class="fl" style="width:250px;">上级菜单：</p>'+
            '<input type="text" name="parentName" required="true" id="parentName" value="" lay-verify="required" placeholder="请选择上级菜单" autocomplete="off" class="layui-input midd fl"/>'+
            '<input type="hidden" name="parentId" value="0" id="parentId"/>'+
            '<div class="layui-input-inline fl f2"><button type="button" class="layui-btn" id="chooseMenu" onclick="choosenMenu()">菜单选择</button></div>'+
            '</td>'+
            '</tr>'+
            '</form>'+
            '</div>'+
            '</div>',
            btn: ['提交','关闭','重置'],
            btnAlign: 'c' ,//按钮居中
            shade: 0 ,//不显示遮罩
            yes: function(index, layero){
                var name = $("#name") .val();
                var url = $("#url").val();
                var parentId = $("#parentId").val();
                if(name == '' || name == null){
                    layer.msg("请填写菜单名称!");
                    return false;
                }
                var menuData = {'name':name,'url':url,'parentId':parentId};
                $.ajax({
                    type:'post',
                    url:'/Sys/menu/post',
                    data:JSON.stringify(menuData),
                    dataType:'json',
                    contentType: "application/json",
                    success:function (data) {
                        if(data == 'true'){
                            table.reload("menuId"); //此处是上文提到的 初始化标识id
                        }
                    }
                });
            }
        });
    });

    $("#search").click(function(){
        var name = $("#searchName").val();
        var checkValue=$("#searchTitle").val();  //获取Select选择的Value
        if(checkValue == ''){
            layer.msg("请选择搜索条件",{icon:5})
            return false;
        }

        var url = '/Sys/menu/showSearch/?name='+name;
        loadTable("0",url);
    });

    $("#retrunEvent").click(function () {

        loadTable(data,url);
    });

});

/**
 * 跳转到子菜单
 * @param data
 * @param url
 */
function toMenu(data,url) {
    loadTable(data,"");
}

/**
 * 加载数据表格
 * @param data
 * @param url
 */
function loadTable(data,url) {
    if(url === null || url === ''){
        url ='/Sys/menu/showData/'+data;
    }



    layui.use("table", function() {
        var table = layui.table
            ,form = layui.form;
        table.render({
            id:"menuId",
            elem: "#menu",
            url: url,
            loading:true,
            page: true,
            limit: 10,
            limits: [10, 15, 20],
            cols: [[
                {checkbox: true, fixed: "true"},
                {field: "name", title: "菜单名",templet: '#usernameTpl'},
                {field: "url", title: "访问路径"},
                {field: "createTime", title: "创建时间"},
                {field: "updateTime", title: "修改时间"},
                {field:'status', title:'状态',  templet: '#switchTpl', unresize: true},
                {field: '', title: '操作', toolbar: "#bar"}
            ]]
            ,even: true
            ,done: function(){
                layer.msg('加载完成');
            }
        });

        //监听启用禁用操作
        form.on('switch(statusFilter)', function(obj){
            layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
            var status = obj.elem.checked ? 1 : 0;
            var menuData = {'menuId':this.id,'status':status};
            $.ajax({
                type:'post',
                url:'/Sys/menu/updateStatu',
                data:menuData,
                beforeSend:function () {
                    index = layer.load(1);
                },
                success:function (data) {
                    layer.close(index);
                    table.reload("menuId"); //此处是上文提到的 初始化标识id
                }
            });
        });

        table.on('tool(menuControl)',function (obj) {
            var data = obj.data;
            var id = data.menuId;
            var name = data.name;
            var url = data.url;
            var parentId = data.parentId;
            if(parentId === null || parentId === '0')parentId = "一级菜单";
            layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'update'){
                layer.open({
                    type: 0,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    title:"菜单修改",
                    offset: 'auto' ,//水平居中
                    id: 'updateMenu', //防止重复弹出，唯一表示
                    content:
                    '<div style="width:250px;">'+
                    '<div class="detailMes fl">'+
                    '<form  action="">'+
                    '<tr>'+
                    '<td class="fl" style="width:250px;">'+
                    '<p class="fl" style="width:250px;">菜单名称：</p>'+
                    '<input type="text" name="name" required="true" id="name" value="'+name+'" lay-verify="required" placeholder="请输入菜单名称" autocomplete="off" class="layui-input fl"/>'+
                    ' </td>'+
                    '<td class="fl">'+
                    '<p class="fl" style="width:250px;">路径：</p>'+
                    '<input type="text" name="url" required="true" id="url" value="'+url+'" lay-verify="required" placeholder="请输入路径" autocomplete="off" class="layui-input fl"/>'+
                    '</td>'+
                    '<td>'+
                    '<p class="fl" style="width:250px;">上级菜单：</p>'+
                    '<input type="text" name="parentName" required="true" id="parentName" value="'+parentId+'" lay-verify="required" placeholder="请选择上级菜单" autocomplete="off" class="layui-input midd fl"/>'+
                    '<input type="hidden" name="parentId" value="0" id="parentId"/>'+
                    '<div class="layui-input-inline fl f2"><button type="button" class="layui-btn" id="chooseMenu" onclick="choosenMenu()">菜单选择</button></div>'+
                    '</td>'+
                    '</tr>'+
                    '</form>'+
                    '</div>'+
                    '</div>',
                    btn: ['提交','关闭','重置'],
                    btnAlign: 'c' ,//按钮居中
                    shade: 0 ,//不显示遮罩
                    yes: function(index, layero){
                        var name = $("#name") .val();
                        var url = $("#url").val();
                        var parentId = $("#parentId").val();
                        var menuData = {'name':name,'url':url,'parentId':parentId,'menuId':id};
                        $.ajax({
                            type:'post',
                            url:'/Sys/menu/update',
                            data:JSON.stringify(menuData),
                            dataType:'json',
                            contentType: "application/json",
                            success:function (data) {
                                if(data === 'true'){
                                    // layer.msg("修改成功!",{icon: 6, time: 3000});
                                    table.reload("menuId"); //此处是上文提到的 初始化标识id
                                }
                            }
                        });
                    }
                });
            }
            if(layEvent === 'del'){
                layer.confirm('确认删除吗？', {
                    btn: ['删除','放弃'] //按钮
                }, function(){
                    $.ajax({
                        type:'post',
                        url:'/Sys/menu/delete/'+id,
                        contentType: "application/json",
                        beforeSend:function () {
                            index = layer.load(1);
                        },
                        success:function(data) {
                            if(data === 1){
                                layer.close(index);
                                table.reload("menuId"); //此处是上文提到的 初始化标识id
                            }
                            if(data === 0){
                                layer.close(index);
                                layer.msg('删除失败');
                                return false;
                            }
                            if(data === 2){
                                layer.close(index);
                                layer.msg('请先删除子菜单',{icon:5});
                                return false;
                            }
                        }
                    });
                }, function(){
                    layer.msg('放弃删除',{icon:1});
                });
            }
        });

    })
}

/**
 * 选择树菜单
 */
function choosenMenu() {
    layer.open({
        type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        title:"菜单选择",
        shadeClose: true,//点击遮罩关闭层
        area: ['250px', '350px'],
        content:"/Sys/menu/showTree",
        iframe:{
        }
    });
}