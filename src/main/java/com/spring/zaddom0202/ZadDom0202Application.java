package com.spring.zaddom0202;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class ZadDom0202Application {
    public static void main(String[] args) {
        SpringApplication.run(ZadDom0202Application.class, args);
    }
}
