package com.ace.smart.mapper.intercept;

import com.ace.smart.entity.PRole;
import org.apache.ibatis.plugin.*;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executor;
@Intercepts({
        @Signature(type= Executor.class,
                method = "query",
                args = {})
  })

public class MybatisIntercept implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //执行请求方法，并将所得结果保存到result中
        Object result = invocation.proceed();
        if(result instanceof ArrayList){
            ArrayList resultList = (ArrayList) result;
            for (int i = 0; i < resultList.size(); i++) {
                if(resultList.get(i) instanceof PRole){
                    PRole pRole = (PRole) resultList.get(i);
                    if (pRole.getrStatus().equals("1")){
                        pRole.setrStatus("启用");
                    } else if (pRole.getrStatus().equals("0")) {
                        pRole.setrStatus("禁用");
                    }
                }

            }
        }
        return result;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
