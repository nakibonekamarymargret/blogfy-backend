package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Find user by ID
    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    // Find user by email
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    // Get all users
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Save or update user
    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
