# SimpleRpc
简易的远程过程调用框架
1. simpleRpc是一个基于Java语言的分布式RPC框架demo，供RPC初学者学习使用
2. simpleRpc框架目前基于netty+zookeeper实现
3. 目前simpleRpc分为客户端、服务端和注册中心，未来可能会加入服务治理监控中心，以及支持Restful API

### simpleRpc RPC分布式框架的实现基础
1. Call ID映射，在远程调用的过程中，每个方法都需要对应一个具有唯一性的ID，这个ID在客户端和服务端中都是唯一确定的，客户端在做远程调用时必须附上这个ID，fastCall框架使用方法签名作为ID
2. 序列化和反序列化，在参数传递和结果返回的过程中需要将传输的数据进行序列化和反序列化，以字节流的形式进行传输，simpleRpc采用protocol buffer作为序列化方式
3. 网络传输，采用netty框架实现网络传输
4. 分布式实现，simpleRpc采用ZooKeeper作为注册中心实现分布式，分布式集群中的所有服务都要在ZooKeeper中进行注册，向ZooKeeper提供IP地址、端口等一系列信息，当发送方发送请求时，会从ZooKeeper注册中心中获取服务提供方的IP、端口，再向服务提供方请求服务

### 未来待完成 <br>
1. 基于事件响应的end point状态监控<br>
2. 扩展负载均衡算法<br>
3. 注册中心与服务治理监控中心解耦<br>
4. side car模式<br>
5. restful gateway<br>
6. 限流削峰工具<br>
7. 分布式锁<br>
