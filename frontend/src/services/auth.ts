// Auth service to handle backend authentication
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export interface User {
    id: number;
    email: string;
    name: string;
    picture?: string;
}

class AuthService {
    // Check if user is authenticated
    isAuthenticated(): boolean {
        return !!this.getToken();
    }

    // Get stored JWT token
    getToken(): string | null {
        return localStorage.getItem("authToken");
    }

    // Store JWT token
    setToken(token: string): void {
        localStorage.setItem("authToken", token);
    }

    // Get stored user info
    getUser(): User | null {
        const userJson = localStorage.getItem("user");
        if (!userJson) return null;
        try {
            return JSON.parse(userJson);
        } catch {
            return null;
        }
    }

    // Store user info
    setUser(user: User): void {
        localStorage.setItem("user", JSON.stringify(user));
    }

    // Redirect to Google OAuth login on backend
    loginWithGoogle(): void {
        // Save the current URL to return after login
        localStorage.setItem("returnUrl", window.location.pathname);
        // Redirect to backend OAuth endpoint
        window.location.href = `${API_BASE_URL}/oauth2/authorization/google`;
    }

    // Handle OAuth callback (extract token from URL parameters)
    handleCallback(): User | null {
        try {
            // Extract token and user info from URL parameters
            // Backend redirects to: /auth?token=xxx&userId=123&email=...&name=...&picture=...
            const urlParams = new URLSearchParams(window.location.search);
            const token = urlParams.get('token');
            const userId = urlParams.get('userId');
            const email = urlParams.get('email');
            const name = urlParams.get('name');
            const picture = urlParams.get('picture');

            if (token && userId && email && name) {
                // Store token
                this.setToken(token);

                // Store user info
                const user: User = {
                    id: parseInt(userId, 10),
                    email,
                    name,
                    picture: picture || undefined,
                };
                this.setUser(user);

                // Clean up URL (remove query parameters)
                window.history.replaceState({}, document.title, window.location.pathname);

                return user;
            }

            return null;
        } catch (error) {
            console.error("Error handling callback:", error);
            return null;
        }
    }

    // Validate current token with backend
    async validateToken(): Promise<User | null> {
        const token = this.getToken();
        if (!token) return null;

        try {
            const response = await fetch(`${API_BASE_URL}/api/auth/user`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });

            if (!response.ok) {
                // Token is invalid or expired
                this.logout();
                return null;
            }

            const data = await response.json();

            // Update stored user info
            if (data.user) {
                this.setUser(data.user);
                return data.user;
            }

            return null;
        } catch (error) {
            console.error("Error validating token:", error);
            this.logout();
            return null;
        }
    }

    // Logout
    logout(): void {
        // Clear local storage
        localStorage.removeItem("authToken");
        localStorage.removeItem("user");
        localStorage.removeItem("returnUrl");
    }

    // Get return URL after login
    getReturnUrl(): string {
        return localStorage.getItem("returnUrl") || "/";
    }

    // Clear return URL
    clearReturnUrl(): void {
        localStorage.removeItem("returnUrl");
    }
}

export const authService = new AuthService();

