package com.jsptpd.netty.lora;

import com.jsptpd.netty.client.NettyClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.*;
import java.net.Socket;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoraNettyApplicationTests {

    @Test
    public void contextLoads() {
        /*try {
            Thread.sleep(3000);
            System.out.println("开始连接到Netty服务端");
            Socket socket = new Socket("localhost",18000);
            String message = "{\"data\":\"test\"}";
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            for(int i = 0; i < 5; i++){
                out.write(message + i);
                out.flush();
            }
            String line;
            while((line = reader.readLine()) != null){
                System.out.println("接受消息:" + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            Thread.sleep(3000);
            NettyClient.connect();
            Thread.sleep(3000);
            NettyClient.sendString("数据发送自客户端");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
