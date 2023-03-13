package Core;

import Center.CallCenterApplication;
import ServiceStorage.ServiceConfig;

/**
 * 启动类，启动server实例并且将全部服务注册到zookeeper上去
 */
public class FastServerBootstrap {
    private final FastServer fastServer;

    public FastServerBootstrap(){
        fastServer=FastServer.newInstance();
    }

    public FastServerBootstrap setCallCenter(CallCenterApplication application){
        fastServer.setApplication(application);
        return this;
    }

    public FastServerBootstrap serviceRegister(ServiceConfig<?> ...services){
        for (ServiceConfig<?> service : services) {
            ServiceFactory.serviceRegister(service.getInterfaceClass(),service.getReference());
        }
        return this;
    }

    public FastServer build(){
        return fastServer;
    }
}
