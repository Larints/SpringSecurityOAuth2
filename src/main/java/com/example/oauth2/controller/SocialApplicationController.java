package com.example.oauth2.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocialApplicationController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        return "user";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
