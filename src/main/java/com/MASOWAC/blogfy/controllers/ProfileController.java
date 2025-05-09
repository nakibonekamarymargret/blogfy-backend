package com.MASOWAC.blogfy.controllers;


import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.ProfileView;
import com.MASOWAC.blogfy.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //    Get current user profile
    @GetMapping
    public ResponseEntity<ProfileView> getProfile(Principal principal) {
        ProfileView profile = profileService.viewProfile(principal.getName());
        return ResponseEntity.ok(profile);
    }

    //    Update profile
    @PatchMapping ("/update-profile")
    public ResponseEntity<Users> updateProfile(@RequestParam(required = false) MultipartFile image, @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String username) {
        try {
            Users updatedUser = profileService.updateProfile(image, name, username);
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

    }
//    Delete profile
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(){
        profileService.deleteProfile();
        return ResponseEntity.ok("Successfully  deleted");
    }
}
