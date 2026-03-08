package com.vinicius.minilink.controller;

import com.vinicius.minilink.model.User;
import com.vinicius.minilink.service.FriendshipService;
import com.vinicius.minilink.service.PostService;
import com.vinicius.minilink.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final PostService postService;
    private final UserService userService;
    private final FriendshipService friendshipService;

    public WebController(PostService postService, UserService userService, FriendshipService friendshipService) {
        this.postService = postService;
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    // Página Principal
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

    // Busca de usuários
    @GetMapping("/search")
    public String search(@RequestParam String query, Model model, HttpSession session) {
        User userLogado = (User) session.getAttribute("userLogado");

        if (userLogado == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", userLogado);
        model.addAttribute("results", userService.searchUsers(query));
        return "search";
    }

    // Ação de adicionar amizade
    @PostMapping("/friends/add/{id}")
    public String addFriend(@PathVariable Long id, HttpSession session) {
        User userLogado = (User) session.getAttribute("userLogado");

        if (userLogado != null) {
            friendshipService.addFriend(userLogado.getId(), id);
        }

        return "redirect:/home";
    }

    // Redireciona a raiz para a home
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
}