package ru.security.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getProfile() {
        return "Profile page";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String moderateContent() {
        return "Moderation page";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String adminPanel() {
        return "Admin panel";
    }

}
