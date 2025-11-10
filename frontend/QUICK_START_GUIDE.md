# Quick Start Guide

## 🚀 How to Run and Test Your Application

### Prerequisites
- ✅ Backend code implemented (all classes from BACKEND_IMPLEMENTATION_GUIDE.md)
- ✅ Google OAuth credentials configured in application.properties
- ✅ Database updated with new columns (email, google_id, picture_url)
- ✅ Maven dependencies installed

---

## Step 1: Start the Backend

Open terminal in your backend folder:

```bash
# Clean and compile
mvn clean install

# Start Spring Boot application
mvn spring-boot:run
```

**Expected output:**
```
...
Tomcat started on port(s): 8080 (http)
Started YourApplication in X.XXX seconds
```

**Verify backend is running:**
- Open browser: http://localhost:8080
- Should see Spring Boot error page (this is normal if no root endpoint)

---

## Step 2: Start the Frontend

Open a **NEW** terminal in your frontend folder:

```bash
# Install dependencies (first time only)
npm install

# Start development server
npm run dev
```

**Expected output:**
```
VITE v5.x.x  ready in xxx ms

➜  Local:   http://localhost:5173/
➜  Network: use --host to expose
```

---

## Step 3: Test the Application

### 3.1 Open Browser
Navigate to: **http://localhost:8081**

### 3.2 You Should See
- Login page with hero image
- "Sign in with Google" button

### 3.3 Click "Sign in with Google"

**What happens:**
1. Browser redirects to: `http://localhost:8080/oauth2/authorization/google`
2. Then redirects to Google login page
3. You log in with your Google account
4. Google asks for permissions (email, profile)
5. You approve
6. Redirects back to your backend: `http://localhost:8080/login/oauth2/code/google`
7. Backend processes OAuth callback
8. Redirects to: `http://localhost:8081/auth`
9. Frontend gets JWT token
10. Shows success toast: "Bienvenido! Has iniciado sesión como [Your Name]"
11. Redirects to main page: `http://localhost:8081/`

### 3.4 On Main Page
- You should see the player search interface
- Header shows "Cerrar Sesión" button
- You can search for players (enter player ID like 1)

---

## Step 4: Verify It's Working

### Check Browser DevTools

#### Console Tab:
✅ No errors  
✅ You might see logs from your auth service

#### Application Tab → Local Storage → http://localhost:8081:
✅ `authToken`: Should contain a JWT token  
✅ `user`: Should contain your user info as JSON

#### Network Tab:
✅ Request to `/api/auth/user` succeeded (200)  
✅ All API requests have `Authorization: Bearer ...` header

### Check Backend Logs:
✅ No errors  
✅ You might see OAuth logs  
✅ SQL queries for finding/creating user  

---

## Step 5: Test Logout

1. Click "Cerrar Sesión" button
2. Should redirect to login page
3. Check Local Storage - should be empty
4. Try accessing main page directly
5. Should redirect back to login (protected route working!)

---

## 🐛 Common Issues and Solutions

### Issue 1: "Cannot connect to backend"
**Symptoms:** Network errors, CORS errors, fetch failed

**Solutions:**
- [ ] Verify backend is running on port 8080
- [ ] Check backend logs for errors
- [ ] Verify CORS configuration in SecurityConfig
- [ ] Check application.properties has: `cors.allowed-origins=http://localhost:8081`

### Issue 2: "Google OAuth fails"
**Symptoms:** OAuth error page, redirect fails

**Solutions:**
- [ ] Verify Google Console redirect URI: `http://localhost:8080/login/oauth2/code/google`
- [ ] Check client ID and secret in application.properties
- [ ] Try in incognito/private window
- [ ] Check backend logs for OAuth errors

### Issue 3: "JWT validation fails"
**Symptoms:** 401 errors on API requests, "Not authenticated"

**Solutions:**
- [ ] Check JWT secret is at least 32 characters
- [ ] Verify token is in localStorage
- [ ] Check Authorization header format: `Bearer {token}`
- [ ] Check backend logs for JWT validation errors

### Issue 4: "User not found in database"
**Symptoms:** Error creating user, null pointer exceptions

**Solutions:**
- [ ] Verify database columns exist: email, google_id, picture_url
- [ ] Check JugadorRepository has findByEmail method
- [ ] Check OAuth handler is saving user correctly
- [ ] Look at backend logs for SQL errors

### Issue 5: "Frontend redirects in a loop"
**Symptoms:** Keeps redirecting between /auth and /

**Solutions:**
- [ ] Check authService.isAuthenticated() logic
- [ ] Verify token is being stored correctly
- [ ] Clear localStorage and try again
- [ ] Check browser console for errors

---

## 🔍 Debugging Checklist

### Backend Checks:
- [ ] Spring Boot starts without errors
- [ ] Port 8080 is not in use by another app
- [ ] Database connection is working
- [ ] OAuth configuration is present
- [ ] JWT secret is configured
- [ ] All security classes are present

### Frontend Checks:
- [ ] Vite dev server starts without errors
- [ ] Port 8081 is not in use
- [ ] .env file exists with VITE_API_URL
- [ ] No TypeScript errors
- [ ] Browser console has no errors

### Google OAuth Checks:
- [ ] Credentials are for "Web application" type
- [ ] Authorized JavaScript origins: `http://localhost:8081`
- [ ] Authorized redirect URIs: `http://localhost:8080/login/oauth2/code/google`
- [ ] Credentials are not expired

### Database Checks:
- [ ] jugador table has email column
- [ ] jugador table has google_id column
- [ ] jugador table has picture_url column
- [ ] Database is running and accessible

---

## 📊 Testing Scenarios

### Scenario 1: First Time Login
1. Click "Sign in with Google"
2. Complete Google OAuth
3. **Expected:** New user created in database
4. **Verify:** Check jugador table for new entry with your email

### Scenario 2: Returning User
1. Login again with same Google account
2. **Expected:** Uses existing user from database
3. **Verify:** No duplicate user created

### Scenario 3: Protected Routes
1. Logout
2. Try to access http://localhost:8081/ directly
3. **Expected:** Redirects to /auth
4. **Verify:** Cannot access without login

### Scenario 4: API Requests
1. Login
2. Search for a player (ID: 1)
3. Open Network tab
4. **Expected:** Request has Authorization header
5. **Verify:** Data loads successfully

### Scenario 5: Token Expiration
1. Login
2. Wait 24 hours (or change JWT expiration to 1 minute)
3. Make an API request
4. **Expected:** Token expires, 401 error
5. **Verify:** User needs to login again

---

## 🎯 Success Metrics

Your implementation is working correctly when:

1. ✅ Login flow completes without errors
2. ✅ User is created/found in database
3. ✅ JWT token is generated and stored
4. ✅ Main page loads with user authenticated
5. ✅ API requests include Authorization header
6. ✅ Data loads from backend successfully
7. ✅ Logout clears authentication
8. ✅ Protected routes redirect to login
9. ✅ No console errors
10. ✅ No backend errors

---

## 📝 Important URLs Reference

| URL | Purpose |
|-----|---------|
| http://localhost:8081 | Frontend (React) |
| http://localhost:8081/auth | Login page |
| http://localhost:8080 | Backend (Spring Boot) |
| http://localhost:8080/oauth2/authorization/google | Start OAuth |
| http://localhost:8080/login/oauth2/code/google | OAuth callback |
| http://localhost:8080/api/auth/user | Get user info + JWT |
| http://localhost:8080/api/* | Protected API endpoints |

---

## 🆘 Still Having Issues?

### Check These Files:
1. `BACKEND_IMPLEMENTATION_GUIDE.md` - Verify all classes are implemented
2. `IMPLEMENTATION_CHECKLIST.md` - Ensure all steps are checked
3. Backend `application.properties` - Verify all config is present
4. Frontend `.env` - Verify API URL is set

### Debug Steps:
1. Check backend logs - any errors?
2. Check frontend console - any errors?
3. Check Network tab - which request fails?
4. Check database - is user created?
5. Check localStorage - is token stored?

### Common Config Mistakes:
❌ Wrong redirect URI in Google Console  
❌ Client ID/secret mismatch  
❌ JWT secret too short  
❌ CORS not configured  
❌ Database columns missing  
❌ Wrong port numbers  

---

## 🎉 Once Everything Works

### Production Deployment Checklist:
- [ ] Change JWT secret to secure random string
- [ ] Use HTTPS for both frontend and backend
- [ ] Update Google OAuth redirect URIs to production domains
- [ ] Update CORS allowed origins to production domain
- [ ] Increase JWT expiration time (optional)
- [ ] Add refresh token mechanism (optional)
- [ ] Add rate limiting (optional)
- [ ] Add logging and monitoring (optional)

---

**You're ready to go! Start both servers and test the flow.** 🚀

**Questions?** Refer back to:
- `BACKEND_IMPLEMENTATION_GUIDE.md` - Complete backend code
- `IMPLEMENTATION_CHECKLIST.md` - Step-by-step tasks
- `FRONTEND_CHANGES_SUMMARY.md` - What changed in frontend

