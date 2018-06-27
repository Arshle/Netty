# Netty
Netty基本使用二次开发,封装Netty服务端和客户端的使用

使用说明
若格式有问题可在idea中查看

一、基本消息传输协议及编解码
  1.所有传输的基本业务数据由于业务设计是通过二进制字节数组进行传输,在本工程中若客户端
与服务端都是Netty协议可封装为NettyRequest和NettyResponse进行传输和开发;
  2.对于客户端与服务端的基础配置:
netty:
  server:
    port: 18000
    decode:
      request: false
    encode:
      response: false
  client:
    server:
      address: localhost
      port: 18000
    encode:
      request: false
    decode:
      response: false
在服务端中的netty.server.decode.request与客户端中的netty.client.encode.request
需要统一,不可出现一个为true一个为false导致客户端与服务端协议不一致的情况,同理服务端中的
netty.server.encode.reponse和客户端中的netty.client.decode.reponse也要统一;
  3.无论引用服务端还是客户端的组件,都需要在spring boot启动类或是在spring的配置文件中
对com.jsptpd.netty包进行扫描,扫描顺序不做要求,如lora-netty中的LoraNettyApplication
添加的注解@ComponentScan({"com.jsptpd.netty"})
  
二、服务端组件引用
  1.添加maven依赖:
<!--netty-server-->
<dependency>
    <groupId>com.jsptpd.netty</groupId>
    <artifactId>netty-server</artifactId>
    <version>${netty.server.version}</version>
</dependency>

  2.在spring boot配置文件或spring工程配置文件中添加服务端最简配置:
netty:
  server:
    port: 18000
若客户端为普通Socket连接,需要对服务端配置添加编解码的配置:
netty:
  server:
    port: 18000
    decode:
      request: false
    encode:
      response: false
socket接口仅会发送二进制字节数组数据,若配置服务端netty.server.decode.request
为false服务端组件会使用SimpleRequestDecoder将二进制字节数组解码成NettyRequest
对象给开发进行数据处理,根据业务进行处理,若客户端和服务端皆为Netty协议,可参照本条
开始的最简配置进行处理;
  3.其余关于Netty服务端配置在NettyServerConfiguration类中,参考成员变量对应的
配置名进行配置修改,例如需要修改服务端connectTimeout属性,可在配置文件中添加如下:
netty:
  server:
    port: 18000
    connect: 
      timeout: 
        ms: 5000
  4.服务端数据处理类的编写需要实现NettyServerMessageHandler接口,并实现handleRequest
和handleException方法,并且必须添加@NettyServerHandler注解,若有多个处理类形成处理链
并想要保证处理类的执行顺序,可在注解中加入order属性:@NettyServerHandler(order = 1),
组件会根据注解中标注的顺序进行执行,具体示例参考lora-netty中的LoraServerMessageHandler
~LoraServerMessageHandler3;

三、客户端组件引用
  1.添加maven依赖:
<!--netty-client-->
<dependency>
    <groupId>com.jsptpd.netty</groupId>
    <artifactId>netty-client</artifactId>
    <version>${netty.client.version}</version>
</dependency>
  2.在spring boot配置文件或spring工程配置文件中添加服务端最简配置:
netty: 
  client:
    server:
      address: localhost
      port: 18000
客户端的client.encode.request和client.decode.response配置需要和服务
端进行统一,用于统一编解码;
  3.若想修改客户端其他属性,可通过参考客户端配置类NettyClientConfiguration
进行配置修改,具体方法同服务端配置修改;
  4.完成以上配置可通过NettyClient.sendString()/NettyClient.sendBytes()/
NettyClient.sendNettyRequest()方法进行数据的传输,注:若客户端的netty.client.
encode.request配置为false则组件会阻止NettyClient.sendNettyRequest()方式发送
数据,数据会以ByteBuf的形式在通道中传输,在客户端不会编码成NettyRequest对象;
  5.客户端发送消息参考LoraNettyApplicationTests类中的形式,模拟了Netty客户端以及
普通socket客户端发送数据处理过程;
  6.若需要处理服务端的响应消息处理类需要实现NettyClientMessageHandler接口并实现
handleResponse和handleException方法,并且必须添加@NettyClientHandler注解,同样
如果处理类需要按顺序执行可添加注解顺序属性:@NettyClientHandler(order = 1),具体使
用实例参考lora-netty工程的ClientHandler1~ClientHandler3;
  
四、Demo实例
lora-netty工程为Demo示例,基于单独的spring boot工程使用