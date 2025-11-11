package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.AuthResponseDTO;
import com.estructura.rocketleague.dto.UserDTO;
import com.estructura.rocketleague.entity.User;
import com.estructura.rocketleague.security.JwtTokenProvider;
import com.estructura.rocketleague.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public AuthController(JwtTokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        // Get JWT from Authorization header
        String jwt = getJwtFromRequest(request);

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(401).body("No token provided");
        }

        // Validate token
        if (!tokenProvider.validateToken(jwt)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // Get user ID from token
        Long userId = tokenProvider.getUserIdFromToken(jwt);

        // Fetch user from database
        User user = userService.findById(userId);

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getPictureUrl());
        AuthResponseDTO response = new AuthResponseDTO(jwt, userDTO);

        return ResponseEntity.ok(response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

