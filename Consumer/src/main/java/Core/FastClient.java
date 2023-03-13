package Core;

import Center.CallCenterApplication;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class FastClient {

    //从zookeeper获得服务代理对象的途径
    private FastChannel channel;

    //存储 服务接口-服务代理类
    private final Map<Class<?>,Object> cache;


    private FastClient(){
        cache=new HashMap<>();
    }

    public void initChannel(CallCenterApplication application){
        channel=new FastChannel(application);
    }

    // protected : 自身、子类、同一个package
    protected static FastClient newInstance(){
        return new FastClient();
    }

    /**
     * 更新缓存
     */
    public void put(Class<?>interfaceProxy,Class<?>referenceClass){
        synchronized (FastClient.class){
            cache.put(referenceClass,channel.proxy(interfaceProxy,referenceClass));
        }
    }

    /**
     * 返回一个代理对象
     *
     * @param referenceClass 被代理的对象类
     * @param interfaceProxy 代理接口
     * @param <T> 代理对象
     */
    public <T> T get(Class<T> interfaceProxy,Class<?> referenceClass){
        synchronized (FastClient.class){
            if(cache.containsKey(referenceClass)){
                return (T) cache.get(referenceClass);
            }else {
                T instance = channel.proxy(interfaceProxy,referenceClass);
                cache.put(referenceClass,instance);
                return instance;
            }
        }
    }
}
