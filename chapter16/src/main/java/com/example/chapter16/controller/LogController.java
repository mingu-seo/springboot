package com.example.chapter16.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/log")
public class LogController {

    @GetMapping("/test")
    public String test() {
        log.trace("trace log");
        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");

        return "log test";
    }
}