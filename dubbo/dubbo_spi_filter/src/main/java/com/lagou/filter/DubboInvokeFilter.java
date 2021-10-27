package com.lagou.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;


@Activate(group = {CommonConstants.CONSUMER,CommonConstants.PROVIDER}) // 通过group指定生产端、消费端
public class DubboInvokeFilter   implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long   startTime  = System.currentTimeMillis();
        try {
            // 执行方法
            return  invoker.invoke(invocation);
        } finally {
            System.out.println("invoke time:"+(System.currentTimeMillis()-startTime) + "毫秒");
        }

    }
}
