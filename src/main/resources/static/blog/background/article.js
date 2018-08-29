layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#articleList',
        url : '/blog/show/s',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "articleListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'articleId', title: 'ID', width:60, name:"article_id", align:"center",sort: true},
            {field: 'articleTitle', title: '文章标题', width:350},
            {field: 'articleAuthor', title: '发布者', align:'center'},
            {field: 'viewCount', title: '浏览量', align:'center',name:'view_count',sort: true},
            {field: 'likesCount', title: '喜爱', align:'center',name:'likes_count',sort: true},
            {field: 'wordage', title: '字数', align:'center',sort: true},
            {field: 'articleType', title: '文章类型', align:'center',templet:"#articleTypeS"},
            {field: 'articleTime', title: '发布时间', align:'center', minWidth:110,sort: true},
            {field: 'articleSource', title: '来源',  align:'center',templet:"#articleStatus"},
            {title: '操作', width:170, templet:'#articleListBar',fixed:"right",align:"center"}
        ]]
    });

    table.on('sort(articleList)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        console.log(obj.field); //当前排序的字段名
        console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
        console.log(this); //当前排序的 th 对象

        //尽管我们的 table 自带排序功能，但并没有请求服务端。
        //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
        var field = obj.field;
        /*if (obj.field == 'articleId') {
            field = "article_id";
        }else if (obj.field == 'wordage'){
            field = "wordage";
        }else if (obj.field == 'likesCount'){
            field = "likes_count";
        }else if (obj.field == 'viewCount'){
            field = "view_count";
        }else if (obj.field == 'articleTime'){
            field = "article_time";
        }*/
        table.reload('articleListTable', {
            initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
            ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                field: field //排序字段
                ,order: obj.type //排序方式
            }
        });
    });

    //是否置顶
    form.on('switch(articleTop)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            if(data.elem.checked){
                layer.msg("置顶成功！");
            }else{
                layer.msg("取消置顶成功！");
            }
        },500);
    })

    //搜索
    $(".search_btn").on("click",function(){
        var beginViewCount = $("#beginViewCount").val();
        var endViewCount = $("#endViewCount").val();
        var articleTitle = $(".articleTitle").val();
        var articleType = $("#articleType option:selected").val();
        table.reload("articleListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                    beginViewCount:beginViewCount,
                    endViewCount:endViewCount,
                    articleTitle:articleTitle,
                    articleType:articleType
        //搜索的关键字
            }
        })
    });

    //添加文章
    function addNews(edit){
        var index = layui.layer.open({
            title : "添加文章",
            type : 2,
            content : "newsAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".newsName").val(edit.newsName);
                    body.find(".abstract").val(edit.abstract);
                    body.find(".thumbImg").attr("src",edit.newsImg);
                    body.find("#news_content").val(edit.content);
                    body.find(".newsStatus select").val(edit.newsStatus);
                    body.find(".openness input[name='openness'][title='"+edit.newsLook+"']").prop("checked","checked");
                    body.find(".newsTop input[name='newsTop']").prop("checked",edit.newsTop);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addNews_btn").click(function(){
        addNews();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(articleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addNews(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            });
        } else if(layEvent === 'look'){ //预览
            window.open("/blog/show/"+data.articleId);
        }
    });

})