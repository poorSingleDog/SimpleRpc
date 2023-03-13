package Core;

import Center.CallCenterApplication;
import Center.ServerMonitor;
import FastFramer.Message.FastRequest;
import FastFramer.Message.FastResponse;

import java.util.UUID;

public class FastChannel {

    private final CallCenterApplication application;

    public FastChannel(CallCenterApplication application){
        this.application=application;
    }

    /**
     *
     * @param interfaceClass 接口类
     * @param referenceClass 被代理类
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T proxy(Class<T>interfaceClass,Class<?>referenceClass){
        return (T) java.lang.reflect.Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                (proxy, method, args) -> {
            //代理对象调用自身方法时执行下列代码
            //此处实现被代理对象的方法调用的扩展（远程执行对应方法并返回）
            FastRequest request=FastRequest.builder()
                    .requestId(UUID.randomUUID().toString())
                    .interfaceClass(method.getDeclaringClass().getName())
                    .referenceClass(referenceClass.getName())
                    .methodName(method.getName())
                    .params(args)
                    .paramsType(method.getParameterTypes())
                    .build();

            ServerMonitor monitor = application.Provider();

            String host= monitor.getHost();
            Integer port= monitor.getPort();

            FastStream client=new FastStream(host,port);

            // 执行回调函数
            FastResponse response=client.remoteExecute(request);

            Throwable error=response.getError();
            if (error!=null){
                throw error;
            }
            //远程方法调用返回结果
            return response.getRes();
        });
    }
}
