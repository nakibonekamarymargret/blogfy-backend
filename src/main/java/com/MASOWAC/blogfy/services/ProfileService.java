package com.MASOWAC.blogfy.services;


import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.ProfileView;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ProfileService {

    private final UsersRepository userRepo;
    private final Cloudinary cloudinary;

    public ProfileService(UsersRepository userRepo, Cloudinary cloudinary) {
        this.userRepo = userRepo;
        this.cloudinary = cloudinary;
    }

//    Get currently logged user
    private Users getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //    view profile
    public ProfileView viewProfile(String username) {
        return userRepo.getUserProfile(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //    Update profile
    public Users updateProfile(MultipartFile image, String name, String username) throws IOException {
        Users user = getCurrentUser();
        String currentUsername = user.getUsername();
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }

        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }

        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(
                    image.getBytes(),
                    ObjectUtils.asMap("folder", "blogify/users",
                            "public_id", currentUsername)
            );
            String imageUrl = (String) uploadResult.get("secure_url");
            user.setProfilePicUrl(imageUrl);
        }

        return userRepo.save(user);
    }

//    delete profile
    public void deleteProfile(){
        Users user = getCurrentUser();
        userRepo.delete(user);
    }
}
