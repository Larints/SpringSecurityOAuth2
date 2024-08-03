package com.example.oauth2.controller;

import com.example.oauth2.service.SocialAppService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SocialApplicationController {

    private final SocialAppService socialAppService;

    private static final Logger logger = LoggerFactory.getLogger(SocialApplicationController.class);

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
