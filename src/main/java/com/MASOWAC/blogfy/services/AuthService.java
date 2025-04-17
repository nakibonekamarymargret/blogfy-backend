package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.dto.LoginRequest;
import com.MASOWAC.blogfy.dto.LoginResponse;
import com.MASOWAC.blogfy.dto.RegisterDto;
import com.MASOWAC.blogfy.enums.UserRoles;
import com.MASOWAC.blogfy.exceptions.DuplicateEmailException;
import com.MASOWAC.blogfy.exceptions.DuplicateUsernameException;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import com.MASOWAC.blogfy.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsersRepository usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthService(UsersRepository usersRepo, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authManager,
                       CustomUserDetailsService userDetailsService) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    //Default profile
    private static final String DEFAULT_AVATAR_URL = "https://res.cloudinary.com/masowac/image/upload/v1744702625/rktq3arqosi1w3gc20nn.jpg";

    public RegisterDto registerUser(Users user) {
        if (usersRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("Username already exists");
        }
        if (usersRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(usersRepo.count() == 0 ? UserRoles.admin : UserRoles.subscriber);
        user.setProfilePicUrl(DEFAULT_AVATAR_URL);
        user.setActive(true);
        user.setEnabled(true);

        Users savedUser = usersRepo.save(user);

        return new RegisterDto(
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getUsername(),
                savedUser.getRole().name(),
                savedUser.getEnabled()
        );
    }

    public LoginResponse login(LoginRequest request) {
        String loginDetails = request.getLoginDetails();
        String email;

        if (loginDetails.contains("@")) {
            // Login with email
            email = loginDetails;
        } else {
            // Login with username -> resolve to email
            Optional<Users> userOpt = usersRepo.findByUsername(loginDetails);
            if (userOpt.isEmpty()) {
                throw new BadCredentialsException("Invalid username or password");
            }
            email = userOpt.get().getEmail();
        }

        // Authenticate using resolved email and password
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
        );

        Users user = usersRepo.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new LoginResponse(user.getUsername(), user.getEmail(), user.getRole().name(), accessToken, refreshToken);
    }

    public String validateAndExtractEmail(String refreshToken) {
        String email = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!jwtUtil.validateToken(refreshToken, userDetails)) {
            throw new RuntimeException("Invalid token");
        }
        return email;
    }

    public String generateAccessToken(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtUtil.generateToken(userDetails);
    }
}
