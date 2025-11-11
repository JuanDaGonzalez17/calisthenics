package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.UserDTO;
import com.estructura.rocketleague.entity.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleId(String googleId);
    User findById(Long id);
    User findOrCreateUser(String email, String name, String googleId, String pictureUrl);
    UserDTO convertToDTO(User user);
}

