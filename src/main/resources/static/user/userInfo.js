var form, $,areaData;
layui.config({
    base : "/layuicms/js/"
}).extend({
    "address" : "address"
})
layui.use(['form','layer','upload','laydate',"address"],function(){
    form = layui.form;
    $ = layui.jquery;
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        upload = layui.upload,
        laydate = layui.laydate,
        address = layui.address;

    //上传头像
    upload.render({
        elem: '.userFaceBtn',
        url: '/upload/user',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            layer.load(); //上传loading
        },
        done: function(res, index, upload){
            layer.closeAll('loading'); //关闭loading
            if (res.code == "0") {
                $('#userFace').attr('src',"/userImg/"+res.src);
                layer.msg(res.msg,{icon:6});
            }else if(res.code == "1") {
                layer.msg(res.msg,{icon:5});
            }
        },
        error: function(index, upload){
        layer.closeAll('loading'); //关闭loading
        layer.msg("请稍后再试",{icon:5});
        }
    });

    //添加验证规则
    form.verify({
        userBirthday : function(value){
            if(!/^(\d{4})[\u4e00-\u9fa5]|[-\/](\d{1}|0\d{1}|1[0-2])([\u4e00-\u9fa5]|[-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/.test(value)){
                return "出生日期格式不正确！";
            }
        }
    })
    //选择出生日期
    laydate.render({
        elem: '.userBirthday',
        format: 'yyyy年MM月dd日',
        trigger: 'click',
        max : 0,
        mark : {"0-12-15":"生日"},
        done: function(value, date){
            if(date.month === 12 && date.date === 15){ //点击每年12月15日，弹出提示语
                layer.msg('今天是马哥的生日，也是layuicms2.0的发布日，快来送上祝福吧！');
            }
        }
    });


    //提交个人资料
    form.on("submit(changeUser)",function(data){
        var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //将填写的用户信息存到session以便下次调取
        var datas = data.field;
        var uAddress = datas.provid+"-"+datas.cityid+"-"+datas.areaid+"-"+datas.address;
        var dataStr = {id:datas.id,userLoginName:datas.userLoginName,nickname:datas.nickname,uAge:datas.uAge,
            phone:datas.phone,email:datas.email,uAddress:uAddress,selfIntroduction:datas.selfIntroduction};
        $.ajax({
            type:'post',
            url:'/Sys/user/update',
            data:JSON.stringify(dataStr),
            cache:false,
            async:false,
            dataType:'json',
            contentType: "application/json",
            beforeSend:function () {
                index = layer.load(1);
            }
            ,error:function (userData) {
                layer.msg("请求失败",{ icon: 5 });
            } ,
            success:function(userData) {
                if (userData == '0') {
                    layer.close(index);
                    layer.msg("修改成功!",{icon: 6, time: 3000});
                    window.location.href="/index/userInfo";
                    return;
                }
                if (userData == '1'){
                    layer.close(index);
                    layer.msg("用户名已经被注册过！",{ icon: 5 });
                    return;
                }
                if (userData == '2'){
                    layer.close(index);
                    layer.msg("手机号已存在!",{ icon: 5 });
                    return;
                }
                if (userData == '3'){
                    layer.close(index);
                    layer.msg("邮箱验证码错误！",{ icon: 5 });
                    return;
                }
                if(userData == '4'){
                    layer.close(index);
                    layer.msg("修改失败",{ icon: 5 });
                    return;
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

})