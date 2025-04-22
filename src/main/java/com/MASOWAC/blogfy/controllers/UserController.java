package com.MASOWAC.blogfy.controllers;

import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UsersRepository usersRepo;
    private static final String DEFAULT_AVATAR_URL = "https://res.cloudinary.com/masowac/image/upload/v1744702625/rktq3arqosi1w3gc20nn.jpg";

    public UserController(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Principal principal) {
        Users user = usersRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("profilePicUrl", user.getProfilePicUrl() != null ? user.getProfilePicUrl() : DEFAULT_AVATAR_URL);
        return ResponseEntity.ok(response);
    }
}