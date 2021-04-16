package com.test.social.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    @GetMapping
    public void getCurrentUser(@RequestParam String token) {
        System.out.println(token);
    }

    @GetMapping("/oauth2/redirect")
    public void getCurrentUser2(@RequestParam String token) {
        System.out.println(token);
    }
}
