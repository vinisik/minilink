package com.vinicius.minilink.controller;

import com.vinicius.minilink.service.PostService;
import com.vinicius.minilink.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final PostService postService;

    public WebController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        // Simulando o usuário logado com ID 1
        model.addAttribute("posts", postService.getFeed(1L));
        return "index"; // Busca index.html em templates
    }
}