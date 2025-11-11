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

  // Handle OAuth callback (extract token from URL query parameters)
  async handleCallback(): Promise<User | null> {
    try {
      // Check if we have a token in the URL (backend redirects with ?token=...)
      const urlParams = new URLSearchParams(window.location.search);
      const token = urlParams.get("token");

      if (token) {
        // Store the token
        this.setToken(token);

        // Decode JWT to get user info (JWT format: header.payload.signature)
        try {
          const payload = JSON.parse(atob(token.split(".")[1]));
          const user: User = {
            id: payload.sub || payload.userId || 0,
            email: payload.email || payload.sub,
            name: payload.name || "",
            picture: payload.picture || undefined,
          };

          this.setUser(user);

          // Clean up URL by removing token parameter
          window.history.replaceState({}, document.title, window.location.pathname);

          return user;
        } catch (decodeError) {
          console.error("Error decoding token:", decodeError);
        }
      }

      // If no token in URL, try to fetch user info from backend using stored token
      const storedToken = this.getToken();
      if (storedToken) {
        const response = await fetch(`${API_BASE_URL}/api/auth/user`, {
          headers: {
            Authorization: `Bearer ${storedToken}`,
          },
        });

        if (response.ok) {
          const userData = await response.json();
          const user: User = {
            id: userData.id || 0,
            email: userData.email,
            name: userData.name,
            picture: userData.picture,
          };
          this.setUser(user);
          return user;
        }
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

