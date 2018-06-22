layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //添加验证规则
    form.verify({
        newPwd : function(value, item){
            if(value.length < 6){
                return "密码长度不能小于6位";
            }
        },
        confirmPwd : function(value, item){
            if(!new RegExp($("#newPwd").val()).test(value)){
                return "两次输入密码不一致，请重新输入！";
            }
        }
    })

    //修改密码
    form.on("submit(changePwd)",function(data){
        var datas = data.field;
        var pswd = datas.newPwd;
        var oldPswd = datas.oldPwd;
        var dataStr = {pswd:pswd,oldpswd:oldPswd};
        $.ajax({
            type:'post',
            url:'/index/updatePwd',
            data:dataStr,
            cache:false,
            async:false,
            beforeSend:function () {
                index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
            }
            ,error:function (userData) {
                layer.closeAll();
                layer.msg("请求失败",{ icon: 5 });
            } ,
            success:function(userData) {
                layer.closeAll();
                if (userData.code == "0") {
                    layer.msg(userData.msg, {icon:6});
                }else if (userData.code == "1"){
                    layer.msg(userData.msg, {icon:5});
                }else if (userData.code == "2"){
                    layer.msg(userData.msg, {icon:5});
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })


    //控制表格编辑时文本的位置【跟随渲染时的位置】
    $("body").on("click",".layui-table-body.layui-table-main tbody tr td",function(){
        $(this).find(".layui-table-edit").addClass("layui-"+$(this).attr("align"));
    });

})