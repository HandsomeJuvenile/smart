package com.ace.smart.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ace.smart.common.json.entity.Content;

import java.util.List;

public class Json {

    public static String getStr(String json){
        JSONObject jsonObj = JSON.parseObject(json);
        JSONObject jsShowBody = (JSONObject) jsonObj.get("showapi_res_body");
        JSONArray jsData = jsShowBody.getJSONArray("data");
        List<Content> contentList  = JSON.parseArray(jsData.toJSONString(), Content.class);
        StringBuilder stringBuilder = new StringBuilder(16);
        for (Content content:contentList) {
            stringBuilder.append(content.getChinese()+"\n");
            stringBuilder.append(content.getEnglish()+"\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String str = "{\"showapi_res_code\":0," +
                "\"showapi_res_error\":\"\"," +
                "\"showapi_res_body\":{\"ret_code\":0," +
                "\"ret_message\":\"Success\"," +
                "\"data\":[{\"english\":\"Hard but don't pay attention to the efficiency of the result is that: to the, then missing.\",\"chinese\":\"勤奋但不讲究效率的结果就是：笨鸟先飞，然后不知所踪。\"}," +
                "{\"english\":\"Someone makes me comfortable, so I want to rely on him; while another one makes me feel lonely, so I want to embrace him.\",\"chinese\":\"有一个人让我觉得很安心，所以想要依靠他，而另一个人让我觉得很孤单，所以想要拥抱他。\"}," +
                "{\"english\":\"I did not stop loving you I just decidecided not to show my love.\",\"chinese\":\"我没有停止爱你，我只是决定不再表现出来。\"}," +
                "{\"english\":\"Three solutions to every problem: accept it, change it, leave it. If you can't accept it, change it. If you can't change it, leave it.\",\"chinese\":\"有三个方法可以解决所有的问题。接受，改变，放开。不能接受那就改变，不能改变，那就放开。\"}," +
                "{\"english\":\"Doesnot belong to me,i will let go.\",\"chinese\":\"不属于我的 我会离开。\"},{\"english\":\"The princess of pure feeling is written on the face, the witch's deeply planted in the in the mind.\",\"chinese\":\"公主的纯情写在脸上，巫婆的深情种在心里。\"}," +
                "{\"english\":\"Everything ought to be beautiful in a human being: face, dress, soul and idea.\",\"chinese\":\"人的一切都应当是美丽的：容貌、衣着、心灵和思想。\"}," +
                "{\"english\":\"Grasp all, lose all.\",\"chinese\":\"欲尽得，必尽失 。\"}]}}\n";
        System.out.println(getStr(str));
    }
}
