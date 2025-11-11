import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { authService } from "@/services/auth";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";
import heroImage from "@/assets/hero-rocket-league.jpg";

export default function Auth() {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Check if user is already logged in
    if (authService.isAuthenticated()) {
      navigate("/");
      return;
    }

    // Check if returning from OAuth callback (token in URL)
    const user = authService.handleCallback();
    if (user) {
      toast({
        title: "Bienvenido!",
        description: `Has iniciado sesión como ${user.name}`,
      });
      const returnUrl = authService.getReturnUrl() || "/";
      authService.clearReturnUrl();
      navigate(returnUrl);
    }
  }, [navigate, toast]);

  const handleGoogleLogin = () => {
    setLoading(true);
    // Redirect to backend OAuth endpoint
    authService.loginWithGoogle();
  };

  return (
    <div className="min-h-screen bg-background">
      <div 
        className="relative h-screen bg-cover bg-center flex items-center justify-center"
        style={{ 
          backgroundImage: `linear-gradient(to bottom, rgba(0,0,0,0.7), rgba(0,0,0,0.9)), url(${heroImage})` 
        }}
      >
        <Card className="w-full max-w-md mx-4 bg-card/95 backdrop-blur border-border">
          <CardHeader className="space-y-1 text-center">
            <CardTitle className="text-3xl font-bold">
              Rocket League <span className="bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">Stats</span>
            </CardTitle>
            <CardDescription className="text-muted-foreground">
              Sign in to access your player statistics
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <Button
              onClick={handleGoogleLogin}
              disabled={loading}
              className="w-full"
              size="lg"
            >
              {loading ? "Signing in..." : "Sign in with Google"}
            </Button>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
