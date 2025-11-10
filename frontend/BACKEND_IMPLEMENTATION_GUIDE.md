# Backend Implementation Guide for Google OAuth Integration

## ✅ Frontend Changes Completed

Your React frontend has been updated to work with your Spring Boot backend:

1. **Removed Supabase authentication** - No longer using Supabase for auth
2. **Created `authService`** - Handles authentication via your backend
3. **Updated all API calls** - Now includes JWT tokens in headers
4. **Fixed array response handling** - Properly extracts single object from array

---

## 📋 What You Need to Implement in Your Backend

### 1. Add Dependencies to `pom.xml`

```xml
<dependencies>
    <!-- Spring Security OAuth2 Client -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    
    <!-- JWT Support -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.3</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.3</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.3</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### 2. Configure `application.properties` or `application.yml`

```properties
# Google OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email
spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:8080/login/oauth2/code/google

# JWT Configuration
jwt.secret=your-secret-key-min-256-bits-long-please-change-this-in-production
jwt.expiration=86400000

# CORS Configuration
cors.allowed-origins=http://localhost:8081
```

### 3. Create User Entity (if not exists)

```java
package com.yourpackage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jugador") // Your existing table
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jugadorId;
    
    private String userName;
    private String email;
    private String googleId;
    private String pictureUrl;
    
    // Getters and setters
}
```

### 4. Create JWT Utility Class

```java
package com.yourpackage.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    public String generateToken(Long userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
    
    public Long getUserIdFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        return Long.parseLong(claims.getSubject());
    }
    
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
```

### 5. Create Security Configuration

```java
package com.yourpackage.config;

import com.yourpackage.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(OAuth2AuthenticationSuccessHandler successHandler,
                         JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.successHandler = successHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/oauth2/**", "/login/**", "/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(successHandler)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

### 6. Create OAuth2 Success Handler

```java
package com.yourpackage.config;

import com.yourpackage.model.Jugador;
import com.yourpackage.repository.JugadorRepository;
import com.yourpackage.security.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
    private final JugadorRepository jugadorRepository;
    
    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider,
                                             JugadorRepository jugadorRepository) {
        this.tokenProvider = tokenProvider;
        this.jugadorRepository = jugadorRepository;
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
        Jugador jugador = jugadorRepository.findByEmail(email)
                .orElseGet(() -> {
                    Jugador newJugador = new Jugador();
                    newJugador.setEmail(email);
                    newJugador.setUserName(name);
                    newJugador.setGoogleId(googleId);
                    newJugador.setPictureUrl(picture);
                    return jugadorRepository.save(newJugador);
                });
        
        // Generate JWT token
        String token = tokenProvider.generateToken(jugador.getJugadorId(), jugador.getEmail());
        
        // Store user info in session for the auth endpoint to retrieve
        request.getSession().setAttribute("jwt_token", token);
        request.getSession().setAttribute("user_id", jugador.getJugadorId());
        request.getSession().setAttribute("user_email", jugador.getEmail());
        request.getSession().setAttribute("user_name", jugador.getUserName());
        request.getSession().setAttribute("user_picture", jugador.getPictureUrl());
        
        // Redirect back to frontend
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:5173/auth");
    }
}
```

### 7. Create JWT Authentication Filter

```java
package com.yourpackage.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider tokenProvider;
    
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromToken(jwt);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

### 8. Create Auth Controller

```java
package com.yourpackage.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
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
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        
        Map<String, Object> user = new HashMap<>();
        user.put("id", userId);
        user.put("email", email);
        user.put("name", name);
        user.put("picture", picture);
        
        response.put("user", user);
        
        // Clear session after retrieving
        session.invalidate();
        
        return ResponseEntity.ok(response);
    }
}
```

### 9. Update JugadorRepository

```java
package com.yourpackage.repository;

import com.yourpackage.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    Optional<Jugador> findByEmail(String email);
    Optional<Jugador> findByGoogleId(String googleId);
}
```

---

## 🔧 Configuration Checklist

- [ ] Add dependencies to `pom.xml`
- [ ] Configure Google OAuth credentials in `application.properties`
- [ ] Update `Jugador` entity to include OAuth fields (email, googleId, pictureUrl)
- [ ] Create `JwtTokenProvider` class
- [ ] Create `JwtAuthenticationFilter` class
- [ ] Create `SecurityConfig` class
- [ ] Create `OAuth2AuthenticationSuccessHandler` class
- [ ] Create `AuthController` class
- [ ] Update `JugadorRepository` with email finder method
- [ ] Run database migration to add new columns if needed

---

## 🔒 Your Redirect URI is Correct!

✅ **Keep it as**: `http://localhost:8080/login/oauth2/code/google`

This is the **standard Spring Security OAuth2 callback URL**. Don't change it!

---

## 🚀 How It Works

### Flow Diagram:

```
1. User clicks "Sign in with Google" in React
   ↓
2. Frontend redirects to: http://localhost:8080/oauth2/authorization/google
   ↓
3. Spring Security redirects to Google OAuth
   ↓
4. User logs in with Google
   ↓
5. Google redirects to: http://localhost:8080/login/oauth2/code/google
   ↓
6. OAuth2AuthenticationSuccessHandler processes:
   - Creates/finds user in database
   - Generates JWT token
   - Stores in session
   - Redirects to: http://localhost:8081/auth
   ↓
7. Frontend calls: http://localhost:8080/api/auth/user
   ↓
8. Backend returns JWT + user info
   ↓
9. Frontend stores JWT in localStorage
   ↓
10. All API requests include: Authorization: Bearer {JWT}
```

---

## 🧪 Testing Steps

1. Start your backend: `mvn spring-boot:run`
2. Start your frontend: `npm run dev` (in the frontend folder)
3. Navigate to: `http://localhost:8081`
4. Click "Sign in with Google"
5. Complete Google OAuth
6. You should be redirected back and logged in!

---

## 📝 Database Schema Update

Add these columns to your `jugador` table:

```sql
ALTER TABLE jugador ADD COLUMN email VARCHAR(255) UNIQUE;
ALTER TABLE jugador ADD COLUMN google_id VARCHAR(255) UNIQUE;
ALTER TABLE jugador ADD COLUMN picture_url VARCHAR(500);
```

---

## ⚠️ Important Notes

1. **JWT Secret**: Change the JWT secret in production to a secure random string
2. **HTTPS**: In production, use HTTPS for both frontend and backend
3. **CORS**: Update allowed origins for production domain
4. **Session**: The session is only used temporarily to pass data after OAuth callback
5. **Token Expiration**: Current setting is 24 hours (86400000 ms)

---

## 🐛 Troubleshooting

### If you get CORS errors:
- Check that `cors.allowed-origins` matches your frontend URL
- Ensure `@CrossOrigin` is present on controllers

### If Google OAuth fails:
- Verify redirect URI in Google Console matches: `http://localhost:8080/login/oauth2/code/google`
- Check client ID and secret in `application.properties`

### If JWT validation fails:
- Ensure JWT secret is at least 256 bits (32 characters)
- Check that `Authorization` header format is: `Bearer {token}`

---

## 📚 Additional Resources

- [Spring Security OAuth2 Client](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/index.html)
- [JWT with Spring Boot](https://github.com/jwtk/jjwt#quickstart)
- [Google OAuth2 Setup](https://developers.google.com/identity/protocols/oauth2)

---

Good luck with your implementation! 🚀

