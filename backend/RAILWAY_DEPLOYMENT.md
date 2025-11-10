# Railway Deployment Guide for Rocket League Backend

## Prerequisites
- GitHub account
- Railway account (sign up at railway.app)
- Your code pushed to a GitHub repository

## Step 1: Prepare Your Project (COMPLETED ✅)

The following files have been configured:
- ✅ `application.properties` - Uses environment variables
- ✅ `SecurityConfig.java` - Dynamic CORS configuration
- ✅ `Procfile` - Tells Railway how to start the app
- ✅ `railway.toml` - Build and deployment configuration

## Step 2: Push to GitHub

```bash
git add .
git commit -m "Configure for Railway deployment"
git push origin main
```

## Step 3: Deploy on Railway

1. Go to [railway.app](https://railway.app)
2. Click "Start a New Project"
3. Select "Deploy from GitHub repo"
4. Choose your repository
5. Railway will auto-detect it as a Spring Boot/Gradle project

## Step 4: Configure Environment Variables

In the Railway dashboard, go to your project → Variables tab and add:

### Required Variables:
```
PORT=8080
DB_PASSWORD=your-database-password
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
JWT_SECRET=your-super-secure-jwt-secret-key-min-256-bits
```

### Optional Variables (if different from defaults):
```
DATABASE_URL=jdbc:postgresql://your-db-host:5432/your-db-name
DB_USERNAME=your-db-username
CORS_ALLOWED_ORIGINS=https://your-frontend-url.com,http://localhost:8081
OAUTH_REDIRECT_URI=https://your-railway-app.railway.app/login/oauth2/code/google
```

## Step 5: Update Google OAuth Redirect URI

1. Go to [Google Cloud Console](https://console.cloud.google.com)
2. Navigate to APIs & Services → Credentials
3. Edit your OAuth 2.0 Client ID
4. Add authorized redirect URI:
   ```
   https://your-app-name.railway.app/login/oauth2/code/google
   ```

## Step 6: Deploy

Railway will automatically:
- Detect the Gradle project
- Run `./gradlew clean bootJar`
- Start the application using the Procfile
- Assign a public URL

## Step 7: Verify Deployment

1. Check the deployment logs in Railway dashboard
2. Visit your assigned URL: `https://your-app-name.railway.app`
3. Test the OAuth flow
4. Test API endpoints

## Troubleshooting

### Build Fails
- Check the Railway build logs
- Ensure Java 21 is specified in `build.gradle`
- Verify all dependencies are correct

### App Crashes on Startup
- Check environment variables are set correctly
- Verify database connection string
- Check Railway logs for error messages

### CORS Issues
- Update `CORS_ALLOWED_ORIGINS` environment variable
- Add your frontend URL to the allowed origins
- Format: `https://frontend1.com,https://frontend2.com`

### OAuth Issues
- Verify Google OAuth redirect URI matches your Railway URL
- Check `GOOGLE_CLIENT_ID` and `GOOGLE_CLIENT_SECRET` are set
- Update `OAUTH_REDIRECT_URI` environment variable

## Important Notes

1. **Database**: The app is configured for PostgreSQL. If using Railway's PostgreSQL addon, it will automatically inject `DATABASE_URL`
2. **Port**: Railway automatically assigns a port via the `PORT` environment variable
3. **HTTPS**: Railway provides HTTPS by default
4. **Custom Domain**: You can add a custom domain in Railway settings
5. **Logs**: Access real-time logs in the Railway dashboard

## Environment Variable Summary

| Variable | Required | Description | Example |
|----------|----------|-------------|---------|
| PORT | Auto | Railway auto-assigns | 8080 |
| DATABASE_URL | Optional | Full database URL | jdbc:postgresql://host:5432/db |
| DB_USERNAME | Optional | Database username | postgres.xxxxx |
| DB_PASSWORD | Yes | Database password | your-password |
| GOOGLE_CLIENT_ID | Yes | OAuth client ID | xxxxx.apps.googleusercontent.com |
| GOOGLE_CLIENT_SECRET | Yes | OAuth client secret | GOCSPX-xxxxx |
| JWT_SECRET | Yes | JWT signing key | min-256-bits-long-secret |
| CORS_ALLOWED_ORIGINS | Optional | Allowed origins (comma-separated) | https://frontend.com |
| OAUTH_REDIRECT_URI | Optional | OAuth callback URL | https://app.railway.app/login/oauth2/code/google |

## Next Steps After Deployment

1. Set up a PostgreSQL database (Railway addon or external)
2. Configure environment variables
3. Update Google OAuth settings
4. Test all endpoints
5. Set up monitoring (Railway provides basic monitoring)
6. Consider setting up a custom domain

## Useful Railway Commands

- **View Logs**: Click on "Deployments" → Select deployment → View logs
- **Redeploy**: Trigger a new deployment from the dashboard
- **Restart**: Restart the service without rebuilding
- **Rollback**: Revert to a previous deployment

## Support

If you encounter issues:
1. Check Railway documentation: https://docs.railway.app
2. Review application logs in Railway dashboard
3. Verify all environment variables are correctly set
4. Test locally first with production-like environment variables

