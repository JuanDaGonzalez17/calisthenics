# Railway Deployment - Quick Start

## ✅ Files Created/Modified

1. **`application.properties`** - Updated with environment variable support
2. **`SecurityConfig.java`** - Dynamic CORS configuration
3. **`Procfile`** - Tells Railway how to start the app
4. **`railway.toml`** - Build and deployment configuration
5. **`.env.example`** - Template for environment variables
6. **`RAILWAY_DEPLOYMENT.md`** - Complete deployment guide

## 🚀 Next Steps

### 1. Push to GitHub
```bash
git add .
git commit -m "Configure for Railway deployment"
git push origin main
```

### 2. Deploy on Railway
1. Go to [railway.app](https://railway.app)
2. Click "New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Railway will detect the Spring Boot project

### 3. Set Environment Variables
In Railway Dashboard → Variables, add:

**Required:**
- `DB_PASSWORD` - Your database password
- `GOOGLE_CLIENT_ID` - From Google Cloud Console
- `GOOGLE_CLIENT_SECRET` - From Google Cloud Console
- `JWT_SECRET` - Generate with: `openssl rand -base64 32`

**Recommended:**
- `CORS_ALLOWED_ORIGINS` - Your frontend URL(s), comma-separated
- `OAUTH_REDIRECT_URI` - `https://your-app.railway.app/login/oauth2/code/google`

### 4. Update Google OAuth
1. Google Cloud Console → Credentials
2. Add redirect URI: `https://your-app.railway.app/login/oauth2/code/google`

### 5. Deploy & Test
Railway will automatically build and deploy. Check logs for any issues.

## 📋 Environment Variables Summary

| Variable | Required | Example |
|----------|----------|---------|
| DB_PASSWORD | ✅ Yes | `your-password` |
| GOOGLE_CLIENT_ID | ✅ Yes | `xxx.apps.googleusercontent.com` |
| GOOGLE_CLIENT_SECRET | ✅ Yes | `GOCSPX-xxxxx` |
| JWT_SECRET | ✅ Yes | `base64-encoded-secret` |
| CORS_ALLOWED_ORIGINS | 📌 Recommended | `https://frontend.com,http://localhost:8081` |
| OAUTH_REDIRECT_URI | 📌 Recommended | `https://app.railway.app/login/oauth2/code/google` |
| PORT | ⚙️ Auto | Railway auto-assigns |
| DATABASE_URL | ⚙️ Optional | Uses default from properties |

## 🔍 Verification Checklist

- [x] `application.properties` uses environment variables
- [x] `SecurityConfig.java` has dynamic CORS
- [x] `Procfile` created
- [x] `railway.toml` created
- [x] Build successful (`gradlew clean build`)
- [ ] Code pushed to GitHub
- [ ] Railway project created
- [ ] Environment variables set
- [ ] Google OAuth redirect URI updated
- [ ] Deployment successful
- [ ] API endpoints tested

## 🆘 Troubleshooting

**Build fails on Railway?**
- Check Railway logs
- Verify Java 21 is being used
- Ensure all dependencies are in `build.gradle`

**App crashes on start?**
- Verify all required environment variables are set
- Check database connection string
- Review Railway application logs

**CORS errors?**
- Add your frontend URL to `CORS_ALLOWED_ORIGINS`
- Format: `https://site1.com,https://site2.com` (no spaces)

**OAuth not working?**
- Verify Google OAuth redirect URI matches Railway URL
- Check `GOOGLE_CLIENT_ID` and `GOOGLE_CLIENT_SECRET`
- Ensure `OAUTH_REDIRECT_URI` is set correctly

## 📚 Documentation

- Full deployment guide: `RAILWAY_DEPLOYMENT.md`
- Environment variables template: `.env.example`
- Railway docs: https://docs.railway.app

## 🎯 Ready to Deploy!

Your Spring Boot backend is now configured for Railway deployment. Follow the steps above to get it live! 🚀

