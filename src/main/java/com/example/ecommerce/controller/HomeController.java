package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping("/")
    public Object getHomePageData() {
        return null;
    }

}
