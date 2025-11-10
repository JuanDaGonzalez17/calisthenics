package com.estructura.rocketleague.config;

import com.estructura.rocketleague.entity.User;
import com.estructura.rocketleague.security.JwtTokenProvider;
import com.estructura.rocketleague.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider,
                                             UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");
        String picture = oAuth2User.getAttribute("picture");

        // Find or create user
        User user = userService.findOrCreateUser(email, name, googleId, picture);

        // Generate JWT token
        String token = tokenProvider.generateToken(user.getId(), user.getEmail());

        // Store user info in session for the auth endpoint to retrieve
        request.getSession().setAttribute("jwt_token", token);
        request.getSession().setAttribute("user_id", user.getId());
        request.getSession().setAttribute("user_email", user.getEmail());
        request.getSession().setAttribute("user_name", user.getName());
        request.getSession().setAttribute("user_picture", user.getPictureUrl());

        // Redirect back to frontend
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:8081/auth");
    }
}

