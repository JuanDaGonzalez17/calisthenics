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

    // Handle OAuth callback (extract token from URL or fetch from backend)
    async handleCallback(): Promise<User | null> {
        try {
            // After OAuth, Spring Security should redirect back to frontend
            // You'll need to implement a backend endpoint that returns user info + JWT
            const response = await fetch(`${API_BASE_URL}/api/auth/user`, {
                credentials: "include", // Include cookies for session
            });

            if (!response.ok) {
                throw new Error("Failed to get user info");
            }

            const data = await response.json();

            // Store token and user info
            if (data.token) {
                this.setToken(data.token);
            }

            if (data.user) {
                this.setUser(data.user);
                return data.user;
            }

            return null;
        } catch (error) {
            console.error("Error handling callback:", error);
            return null;
        }
    }

    // Logout
    async logout(): Promise<void> {
        try {
            // Call backend logout endpoint
            await fetch(`${API_BASE_URL}/logout`, {
                method: "POST",
                credentials: "include",
            });
        } catch (error) {
            console.error("Error logging out:", error);
        } finally {
            // Clear local storage
            localStorage.removeItem("authToken");
            localStorage.removeItem("user");
            localStorage.removeItem("returnUrl");
        }
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

