layui.use('form', function() {
    var form = layui.form;
    //监听提交
    form.verify({
        phone: [/^1[3|4|5|7|8]\d{9}$/, '请输入正确的手机号!']
        ,email: [/^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\d{9}$/, '邮箱格式不对!']
    });

    form.on('submit(userForm)', function (data) {
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var datas = data.field;
        var uAddress = datas.provid+"-"+datas.cityid+"-"+datas.areaid+"-"+datas.address;
        var dataStr = {id:datas.id,userLoginName:datas.userLoginName,nickname:datas.nickname,pswd:datas.pswd,rId:datas.rId,uAge:datas.uAge,
            phone:datas.phone,email:datas.email,uAddress:uAddress,selfIntroduction:datas.selfIntroduction};
        var returnData ;
        $.ajax({
            type:'post',
            url:'/Sys/user/update',
            data:JSON.stringify(dataStr),
            cache:false,
            async:false,
            dataType:'json',
            contentType: "application/json",
            error:function (userData) {
                top.layer.close(index);
                layer.msg("请求失败",{ icon: 5 });
            } ,
            success:function(userData) {
                returnData = userData;
            }

        });
        setTimeout(function(){
            top.layer.close(index);
            if (returnData == '0') {
                layer.msg("添加成功!",{icon: 6, time: 3000});
                window.location.href="/Sys/user/show";
                return;
            }
            if (returnData == '1'){
                layer.msg("用户名已经被注册过！",{ icon: 5 });
                return;
            }
            if (returnData == '2'){
                layer.msg("手机号已存在!",{ icon: 5 });
                return;
            }
            if (returnData == '3'){
                returnData.msg("邮箱验证码错误！",{ icon: 5 });
                return;
            }
            if(returnData == '5'){
                layer.msg("请先验证邮箱!",{ icon: 5 });
                return;
            }
            if(returnData == '4'){
                layer.msg("修改失败",{ icon: 5 });
                return;
            }
        },500);
        return false;
    });
});
$(function(){
    $("#sendEmail").click(function(){
        var email = $("#email").val();
        if(email == 0){
            layer.msg("请输入邮箱！",{ icon: 5 });
            return false;
        }
        var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if(!myreg.test(email)){
            layer.msg("请输入正确的邮箱格式！",{ icon: 5 });
            return false;
        }
        $.ajax({
            type:'get',
            url:'/Sys/user/sendEmail?recive='+email,
            cache:false,
            contentType: "json",
            success:function (data) {
                layer.msg("发送成功，注意查收！",{ icon: 6 });
            }
        });
    });
});