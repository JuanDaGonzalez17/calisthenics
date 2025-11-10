# Google OAuth2 Setup Guide

## 1. Create Google OAuth2 Credentials

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Navigate to "APIs & Services" > "Credentials"
4. Click "Create Credentials" > "OAuth 2.0 Client ID"
5. Configure the consent screen if you haven't already
6. For Application Type, select "Web application"
7. Add Authorized redirect URIs:
   - `http://localhost:8080/login/oauth2/code/google`
8. Click "Create"
9. Copy your Client ID and Client Secret

## 2. Set Environment Variables

You need to set the following environment variables before running the application:

### Windows (Command Prompt):
```cmd
set GOOGLE_CLIENT_ID=your-google-client-id-here
set GOOGLE_CLIENT_SECRET=your-google-client-secret-here
set JWT_SECRET=your-jwt-secret-at-least-32-characters-long-for-256-bit-encryption
set DB_PASSWORD=your-database-password
```

### Windows (PowerShell):
```powershell
$env:GOOGLE_CLIENT_ID="your-google-client-id-here"
$env:GOOGLE_CLIENT_SECRET="your-google-client-secret-here"
$env:JWT_SECRET="your-jwt-secret-at-least-32-characters-long-for-256-bit-encryption"
$env:DB_PASSWORD="your-database-password"
```

### Linux/Mac:
```bash
export GOOGLE_CLIENT_ID=your-google-client-id-here
export GOOGLE_CLIENT_SECRET=your-google-client-secret-here
export JWT_SECRET=your-jwt-secret-at-least-32-characters-long-for-256-bit-encryption
export DB_PASSWORD=your-database-password
```

## 3. Database Migration

Run the following SQL to create the users table:

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    google_id VARCHAR(255) UNIQUE,
    picture_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_google_id ON users(google_id);
```

## 4. Run the Application

```cmd
gradlew.bat bootRun
```

Or if using Maven:
```cmd
mvn spring-boot:run
```

## 5. Test the Authentication Flow

1. Start the backend: `http://localhost:8080`
2. Start the frontend: `http://localhost:5173`
3. Click "Sign in with Google"
4. Complete the Google OAuth flow
5. You should be redirected back and authenticated!

## What Was Created

### Entities
- **User** - Manages user authentication (completely separate from Jugador)

### DTOs
- **UserDTO** - User data transfer object
- **AuthResponseDTO** - Authentication response with token and user info

### Repository
- **UserRepository** - Database access for User entity

### Services
- **UserService** - Interface for user operations
- **UserServiceImpl** - Implementation with findOrCreateUser logic

### Security Classes
- **JwtTokenProvider** - Generates and validates JWT tokens
- **JwtAuthenticationFilter** - Filters requests and validates JWT
- **OAuth2AuthenticationSuccessHandler** - Handles successful OAuth2 login
- **SecurityConfig** - Main security configuration

### Controller
- **AuthController** - Handles `/api/auth/user` endpoint

## Important Notes

1. **No Relationship with Jugador**: The User entity is completely independent from Jugador entity
2. **JWT Expiration**: Set to 24 hours (86400000 ms)
3. **CORS**: Configured for `http://localhost:5173`
4. **Session**: Only used temporarily to pass OAuth data to frontend
5. **Production**: Make sure to use HTTPS and secure environment variables in production

## Frontend Integration

Your frontend is already configured to work with this backend! The authentication flow:

1. User clicks "Sign in with Google"
2. Redirected to `/oauth2/authorization/google`
3. Google OAuth login
4. Redirected to `/login/oauth2/code/google`
5. Backend creates/finds user, generates JWT
6. Redirected to frontend `/auth`
7. Frontend calls `/api/auth/user` to get token
8. Token stored in localStorage
9. All API requests include `Authorization: Bearer {token}`

## Troubleshooting

### "Invalid redirect URI"
- Make sure your Google Console redirect URI exactly matches: `http://localhost:8080/login/oauth2/code/google`

### "JWT secret too short"
- The JWT secret must be at least 32 characters (256 bits)

### CORS errors
- Check that your frontend URL matches in `application.properties` and `SecurityConfig.java`

### Database errors
- Make sure the users table is created in your PostgreSQL database
- Verify your database connection details in `application.properties`

