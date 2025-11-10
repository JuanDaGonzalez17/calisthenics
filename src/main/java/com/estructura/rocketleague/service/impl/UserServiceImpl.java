package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.UserDTO;
import com.estructura.rocketleague.entity.User;
import com.estructura.rocketleague.repository.UserRepository;
import com.estructura.rocketleague.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    @Override
    public User findOrCreateUser(String email, String name, String googleId, String pictureUrl) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setGoogleId(googleId);
                    newUser.setPictureUrl(pictureUrl);
                    return userRepository.save(newUser);
                });
    }

    @Override
    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPictureUrl()
        );
    }
}

