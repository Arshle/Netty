package com.jsptpd.netty.lora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.jsptpd.netty"})
public class LoraNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoraNettyApplication.class, args);
    }
}
