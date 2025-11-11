package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.AuthResponseDTO;
import com.estructura.rocketleague.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        String token = (String) session.getAttribute("jwt_token");
        Long userId = (Long) session.getAttribute("user_id");
        String email = (String) session.getAttribute("user_email");
        String name = (String) session.getAttribute("user_name");
        String picture = (String) session.getAttribute("user_picture");

        if (token == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        UserDTO userDTO = new UserDTO(userId, email, name, picture);
        AuthResponseDTO response = new AuthResponseDTO(token, userDTO);

        // Clear session after retrieving
        session.invalidate();

        return ResponseEntity.ok(response);
    }
}

