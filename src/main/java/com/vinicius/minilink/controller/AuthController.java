package com.vinicius.minilink.controller;

import com.vinicius.minilink.model.User;
import com.vinicius.minilink.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        try {
            User user = userService.authenticate(username, password);
            session.setAttribute("userLogado", user); // Salva o usuário na sessão
            return "redirect:/home";
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destrói a sessão
        return "redirect:/login";
    }

    @GetMapping("/cadastro")
    public String cadastroPage() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(User user) {
        try {
            userService.createUser(user); // Salva o novo usuário no banco
            return "redirect:/login?success=true";
        } catch (Exception e) {
            return "redirect:/cadastro?error=true";
        }
    }
}