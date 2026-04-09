package com.example.chapter04;

import com.example.chapter04.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Chapter04Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Chapter04Application.class, args);

        OrderService orderService = ctx.getBean(OrderService.class);
        orderService.order();

        System.out.println(orderService.getClass().getName());

        AppInfoProperties aip = ctx.getBean(AppInfoProperties.class);
        System.out.println(aip.getName());
        System.out.println(aip.getVersion());
    }

}

