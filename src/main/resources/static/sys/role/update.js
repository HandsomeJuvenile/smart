layui.use('form', function() {
    var form = layui.form;
    var xtree3 = new layuiXtree({
        elem: 'xtree1'                  //必填
        , form: form                    //必填
        , data: zNodes
        , isopen: false  //加载完毕后的展开状态，默认值：true
        , ckall: true    //启用全选功能，默认值：false
        , ckallback: function () { } //全选框状态改变后执行的回调函数
        , icon: {        //三种图标样式，更改几个都可以，用的是layui的图标
            open: "&#xe7a0;"       //节点打开的图标
            , close: "&#xe622;"    //节点关闭的图标
            , end: "&#xe621;"      //末尾节点的图标
        }
        , color: {       //三种图标颜色，独立配色，更改几个都可以
            open: "#EE9A00"        //节点图标打开的颜色
            , close: "#EEC591"     //节点图标关闭的颜色
            , end: "#828282"       //末级节点图标的颜色
        }
        , click: function (data) {  //节点选中状态改变事件监听，全选框有自己的监听事件
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //开关是否开启，true或者false
            console.log(data.value); //开关value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
        }
    });


    form.on('submit(roleForm)', function (data) {
        var datas = data.field;
        var pper = xtree3.GetChecked(); //这是方法
        var ppers = "";
        for (var i=0;i<pper.length;i++) {
            if(i<pper.length-1){
                ppers += pper[i].value+",";
            }else{
                ppers += pper[i].value;
            }
        }
        var roleData = {'rId':rid,'rName':datas.rName,'rStatus':datas.rStatus,'remark':datas.remark,'ppers':ppers};
        $.ajax({
            type:'post',
            url:'/Sys/role/updateRole',
            data:JSON.stringify(roleData),
            dataType:'json',
            async:false,
            contentType: "application/json;charset=utf-8",
            success:function (data) {
                if(data === '1'){
                    layer.msg("角色修改成功！",{icon:6})
                }else if (data === '0') {
                    layer.msg("角色修改失败！",{icon:5})
                }
            },
            error:function(){
                layer.msg("角色修改失败！",{icon:5});
            }
        });
        return false;
    });
});
var zNodes;

$(function () {
    $.ajax({
        type:'post',
        url:'/Sys/menu/showCheckTreeData?rid='+rid,
        async:false,
        contentType: "application/json",
        success:function (data) {
            zNodes = data;
        },
        error:function(){
            layer.msg("失败！",{icon:6});
        }
    });

    $("#return").click(function () {
        window.history.back();
    });
});