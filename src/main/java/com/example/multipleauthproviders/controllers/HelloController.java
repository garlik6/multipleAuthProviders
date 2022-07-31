package com.example.multipleauthproviders.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HelloController {

    @GetMapping("/hello")
//    @Async
    public String hello() {
        Runnable r = () -> {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            System.out.println(authentication.getName());
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(r);
        executorService.shutdown();

        return "Hello! ";
    }
}

