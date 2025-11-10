# Implementation Checklist

## ✅ Frontend (COMPLETED)

- [x] Created auth service (`src/services/auth.ts`)
- [x] Updated API service to include JWT tokens
- [x] Removed Supabase authentication
- [x] Updated App.tsx for JWT auth
- [x] Updated Auth.tsx for backend OAuth
- [x] Updated Index.tsx for backend logout
- [x] Created .env files
- [x] Fixed array response handling in API

---

## 🔨 Backend (YOUR TURN - Follow BACKEND_IMPLEMENTATION_GUIDE.md)

### Step 1: Dependencies
- [ ] Add `spring-boot-starter-oauth2-client` to pom.xml
- [ ] Add JWT dependencies (jjwt-api, jjwt-impl, jjwt-jackson)
- [ ] Run `mvn clean install`

### Step 2: Configuration
- [ ] Add Google credentials to `application.properties`:
  ```properties
  spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
  spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
  spring.security.oauth2.client.registration.google.scope=profile,email
  spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:8080/login/oauth2/code/google
  
  jwt.secret=your-secret-key-min-256-bits-long-please-change-this
  jwt.expiration=86400000
  
  cors.allowed-origins=http://localhost:8081
  ```

### Step 3: Database Updates
- [ ] Add columns to `jugador` table:
  ```sql
  ALTER TABLE jugador ADD COLUMN email VARCHAR(255) UNIQUE;
  ALTER TABLE jugador ADD COLUMN google_id VARCHAR(255) UNIQUE;
  ALTER TABLE jugador ADD COLUMN picture_url VARCHAR(500);
  ```

### Step 4: Entity Updates
- [ ] Update `Jugador` entity to include:
  - `private String email;`
  - `private String googleId;`
  - `private String pictureUrl;`
  - Getters and setters for all

### Step 5: Create Security Classes
- [ ] Create `JwtTokenProvider.java` (see guide for full code)
- [ ] Create `JwtAuthenticationFilter.java` (see guide)
- [ ] Create `SecurityConfig.java` (see guide)
- [ ] Create `OAuth2AuthenticationSuccessHandler.java` (see guide)

### Step 6: Create Controller
- [ ] Create `AuthController.java` with `/api/auth/user` endpoint

### Step 7: Update Repository
- [ ] Update `JugadorRepository` to add:
  ```java
  Optional<Jugador> findByEmail(String email);
  Optional<Jugador> findByGoogleId(String googleId);
  ```

### Step 8: Testing
- [ ] Start backend: `mvn spring-boot:run`
- [ ] Verify backend is running on port 8080
- [ ] Check logs for any errors
- [ ] Test endpoint: http://localhost:8080/api/auth/user (should return 401)

---

## 🧪 Integration Testing

### Frontend Testing
- [ ] Start frontend: `npm run dev` (in frontend folder)
- [ ] Verify frontend is running on port 8081
- [ ] Navigate to: http://localhost:8081
- [ ] Should redirect to /auth page

### OAuth Flow Testing
- [ ] Click "Sign in with Google" button
- [ ] Should redirect to backend OAuth endpoint
- [ ] Should redirect to Google login
- [ ] Login with your Google account
- [ ] Should redirect back to frontend
- [ ] Should see success toast with your name
- [ ] Should be on the main page (/)
- [ ] Open browser DevTools → Application → Local Storage
- [ ] Verify `authToken` is stored
- [ ] Verify `user` object is stored

### API Testing
- [ ] While logged in, search for a player
- [ ] Open DevTools → Network tab
- [ ] Look at API requests
- [ ] Verify `Authorization: Bearer ...` header is present
- [ ] Verify data loads correctly
- [ ] Test logout button
- [ ] Verify you're redirected to /auth
- [ ] Verify localStorage is cleared

---

## 🐛 Troubleshooting

### If OAuth fails:
- [ ] Check Google Console redirect URI matches exactly: `http://localhost:8080/login/oauth2/code/google`
- [ ] Verify client ID and secret are correct
- [ ] Check backend logs for errors
- [ ] Try incognito/private browser window

### If CORS errors appear:
- [ ] Verify `cors.allowed-origins=http://localhost:8081` in application.properties
- [ ] Check SecurityConfig CORS configuration
- [ ] Verify frontend is on port 8081

### If JWT validation fails:
- [ ] Ensure JWT secret is at least 32 characters
- [ ] Check that Authorization header format is: `Bearer {token}`
- [ ] Verify token is being stored in localStorage
- [ ] Check backend logs for JWT validation errors

### If data doesn't load:
- [ ] Check Network tab in DevTools
- [ ] Verify JWT token is included in request headers
- [ ] Check backend logs for errors
- [ ] Verify database connection is working

---

## 📚 Quick Reference

### Important Files

**Frontend:**
- `src/services/auth.ts` - Authentication service
- `src/services/api.ts` - API client with JWT
- `src/pages/Auth.tsx` - Login page
- `src/App.tsx` - Protected routes

**Backend (to create):**
- `JwtTokenProvider.java` - JWT utilities
- `JwtAuthenticationFilter.java` - JWT validation
- `SecurityConfig.java` - Security configuration
- `OAuth2AuthenticationSuccessHandler.java` - OAuth callback handler
- `AuthController.java` - Auth endpoints

### Important URLs

- Frontend: http://localhost:8081
- Backend: http://localhost:8080
- OAuth start: http://localhost:8080/oauth2/authorization/google
- OAuth callback: http://localhost:8080/login/oauth2/code/google
- Get user info: http://localhost:8080/api/auth/user

---

## 🎯 Success Criteria

You'll know it's working when:
1. ✅ You can click "Sign in with Google"
2. ✅ Google login page appears
3. ✅ After login, redirects back to your app
4. ✅ You see "Bienvenido! Has iniciado sesión como [Your Name]"
5. ✅ Main page loads with player search
6. ✅ Can search for players and see stats
7. ✅ Can logout and get redirected to login page
8. ✅ After logout, can't access main page without logging in again

---

**Current Status:** Frontend is complete ✅  
**Next Step:** Implement backend following BACKEND_IMPLEMENTATION_GUIDE.md 🔨

Good luck! 🚀

