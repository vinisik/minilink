package com.vinicius.minilink.controller;

import com.vinicius.minilink.model.User;
import com.vinicius.minilink.service.PostService;
import jakarta.servlet.http.HttpSession;
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
    public String getHome(Model model, HttpSession session) {
        User userLogado = (User) session.getAttribute("userLogado");
        if (userLogado == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userLogado);
        model.addAttribute("posts", postService.getFeed(userLogado.getId()));

        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
}