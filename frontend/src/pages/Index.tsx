import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Button } from "@/components/ui/button";
import { PlayerSearch } from "@/components/PlayerSearch";
import { StatsFilters } from "@/components/StatsFilters";
import { StatCard } from "@/components/StatCard";
import { RankDisplay } from "@/components/RankDisplay";
import { RankDistribution } from "@/components/RankDistribution";
import { CarPerformance } from "@/components/CarPerformance";
import { SeasonPerformance } from "@/components/SeasonPerformance";
import { Leaderboard } from "@/components/Leaderboard";
import { Target, TrendingUp, Award, Zap, LogOut } from "lucide-react";
import heroImage from "@/assets/hero-rocket-league.jpg";
import { apiService } from "@/services/api";
import { authService } from "@/services/auth";
import type { PlayerStats, PlayerRank, StatsFilters as FilterType } from "@/types/api";
import { useToast } from "@/hooks/use-toast";

const Index = () => {
  const navigate = useNavigate();
  const [selectedPlayerId, setSelectedPlayerId] = useState<number | null>(null);
  const [playerStats, setPlayerStats] = useState<PlayerStats | null>(null);
  const [playerRanks, setPlayerRanks] = useState<PlayerRank[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [filters, setFilters] = useState<FilterType>({});
  const { toast } = useToast();

  const handleLogout = async () => {
    try {
      await authService.logout();
      navigate("/auth");
    } catch (error) {
      console.error("Error logging out:", error);
      toast({
        title: "Error",
        description: "Error al cerrar sesión",
        variant: "destructive",
      });
    }
  };

  const handlePlayerSearch = async (playerIdStr: string) => {
    const playerId = parseInt(playerIdStr);
    if (isNaN(playerId)) {
      toast({
        title: "Error",
        description: "Por favor ingresa un ID de jugador válido",
        variant: "destructive",
      });
      return;
    }

    setIsLoading(true);
    try {
      const [stats, ranks] = await Promise.all([
        apiService.getPlayerStats(playerId),
        apiService.getPlayerRanks(playerId),
      ]);
      
      setSelectedPlayerId(playerId);
      setPlayerStats(stats);
      setPlayerRanks(ranks);
    } catch (error) {
      console.error("Error fetching player data:", error);
      toast({
        title: "Error",
        description: "No se pudo cargar la información del jugador",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleFilterChange = (key: string, value: number | undefined) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  // Reload stats when filters change
  useEffect(() => {
    if (selectedPlayerId) {
      apiService.getPlayerStats(selectedPlayerId, filters)
        .then(setPlayerStats)
        .catch(error => console.error("Error updating stats:", error));
    }
  }, [filters, selectedPlayerId]);

  return (
    <div className="min-h-screen bg-background">
      {/* Hero Section */}
      <div 
        className="relative h-[400px] bg-cover bg-center flex items-center justify-center"
        style={{ 
          backgroundImage: `linear-gradient(to bottom, rgba(0,0,0,0.7), rgba(0,0,0,0.9)), url(${heroImage})` 
        }}
      >
        <Button
          variant="outline"
          size="sm"
          onClick={handleLogout}
          className="absolute top-4 right-4 gap-2"
        >
          <LogOut className="h-4 w-4" />
          Cerrar Sesión
        </Button>
        <div className="container mx-auto px-4 text-center space-y-6">
          <h1 className="text-5xl md:text-6xl font-bold text-foreground">
            Rocket League <span className="bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">Stats</span>
          </h1>
          <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
            Analiza tu rendimiento, compara estadísticas y domina el campo
          </p>
          <div className="pt-4">
            <PlayerSearch onSearch={handlePlayerSearch} isLoading={isLoading} />
          </div>
        </div>
      </div>

      {/* Main Content */}
      <div className="container mx-auto px-4 py-8 space-y-8">
        {selectedPlayerId && playerStats ? (
          <>
            {/* Player Info Header */}
            <div className="flex items-center justify-between flex-wrap gap-4">
              <div>
                <h2 className="text-3xl font-bold text-foreground">{playerStats.userName}</h2>
                <p className="text-muted-foreground">ID: {playerStats.jugadorId}</p>
              </div>
            </div>

            {/* Filters */}
            <StatsFilters filters={filters} onFilterChange={handleFilterChange} />

            {/* Stats Overview */}
            <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
              <StatCard
                title="Goles Totales" 
                value={playerStats.goles.toLocaleString()} 
                icon={Target} 
              />
              <StatCard 
                title="Asistencias" 
                value={playerStats.asistencias.toLocaleString()} 
                icon={Zap} 
              />
              <StatCard 
                title="Victorias" 
                value={playerStats.victorias.toLocaleString()} 
                icon={Award} 
              />
              <StatCard 
                title="Salvadas" 
                value={playerStats.salvadas.toLocaleString()} 
                icon={TrendingUp} 
              />
              <StatCard
                title="Tiros"
                value={playerStats.tiros.toLocaleString()}
                icon={Target}
              />
              <StatCard
                title="Ratio Gol/Tiro"
                value={playerStats.goalShotRatio.toFixed(2)}
                icon={TrendingUp}
              />
              <StatCard
                title="Partidos Jugados"
                value={playerStats.partidosJugados.toLocaleString()}
                icon={Award}
              />
              <StatCard
                title="Win Rate"
                value={`${playerStats.winPercentage.toFixed(1)}%`}
                icon={Award}
              />
            </div>

            {/* Tabs Navigation */}
            <Tabs defaultValue="ranks" className="w-full">
              <TabsList className="grid w-full grid-cols-2 md:grid-cols-5 bg-card border border-border">
                <TabsTrigger value="ranks">Rangos</TabsTrigger>
                <TabsTrigger value="distribution">Distribución</TabsTrigger>
                <TabsTrigger value="cars">Carros</TabsTrigger>
                <TabsTrigger value="seasons">Temporadas</TabsTrigger>
                <TabsTrigger value="leaderboard">Leaderboard</TabsTrigger>
              </TabsList>

              <TabsContent value="ranks" className="mt-6">
                <RankDisplay ranks={playerRanks} />
              </TabsContent>

              <TabsContent value="distribution" className="mt-6">
                <RankDistribution />
              </TabsContent>

              <TabsContent value="cars" className="mt-6">
                <CarPerformance playerId={selectedPlayerId} />
              </TabsContent>

              <TabsContent value="seasons" className="mt-6">
                <SeasonPerformance playerId={selectedPlayerId} />
              </TabsContent>

              <TabsContent value="leaderboard" className="mt-6">
                <Leaderboard />
              </TabsContent>
            </Tabs>
          </>
        ) : (
          /* Landing State - Leaderboard by default */
          <div className="space-y-8">
            <div className="text-center space-y-2">
              <h2 className="text-3xl font-bold text-foreground">Explora las Estadísticas</h2>
              <p className="text-muted-foreground">Busca un jugador por ID o explora el ranking global</p>
            </div>
            <Leaderboard />
          </div>
        )}
      </div>
    </div>
  );
};

export default Index;
