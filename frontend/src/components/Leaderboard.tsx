import { useState, useEffect } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Trophy, Medal } from "lucide-react";
import { apiService } from "@/services/api";
import type { GameMode, Country, LeaderboardEntry } from "@/types/api";

export const Leaderboard = () => {
  const [countryFilter, setCountryFilter] = useState<number | undefined>(undefined);
  const [gameModes, setGameModes] = useState<GameMode[]>([]);
  const [countries, setCountries] = useState<Country[]>([]);
  const [selectedGameMode, setSelectedGameMode] = useState<number | undefined>(undefined);
  const [leaderboardData, setLeaderboardData] = useState<LeaderboardEntry[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadInitialData = async () => {
      try {
        const [modesData, countriesData] = await Promise.all([
          apiService.getAllGameModes(),
          apiService.getAllCountries(),
        ]);
        setGameModes(modesData);
        setCountries(countriesData);
        if (modesData.length > 0) {
          setSelectedGameMode(modesData[0].id);
        }
      } catch (error) {
        console.error("Error loading initial data:", error);
      }
    };

    loadInitialData();
  }, []);

  useEffect(() => {
    const loadLeaderboard = async () => {
      try {
        setLoading(true);
        const data = await apiService.getLeaderboard(
          selectedGameMode,
          countryFilter,
          50 // limit to top 50 players
        );
        console.log("Leaderboard data received:", data);
        console.log("First entry:", data[0]);
        setLeaderboardData(data);
      } catch (error) {
        console.error("Error loading leaderboard:", error);
        setLeaderboardData([]);
      } finally {
        setLoading(false);
      }
    };

    if (selectedGameMode !== undefined) {
      loadLeaderboard();
    }
  }, [selectedGameMode, countryFilter]);

  const getRankIcon = (rank: number) => {
    if (rank === 1) return <Trophy className="h-5 w-5 text-secondary" />;
    if (rank === 2) return <Medal className="h-5 w-5 text-muted-foreground" />;
    if (rank === 3) return <Medal className="h-5 w-5 text-accent" />;
    return <span className="text-muted-foreground">#{rank}</span>;
  };

  const selectedGameModeName = gameModes.find(gm => gm.id === selectedGameMode)?.gameMode || "";

  return (
    <Card className="bg-card border-border">
      <CardHeader className="space-y-4">
        <div className="flex flex-row items-center justify-between">
          <CardTitle className="text-xl">Leaderboard Global</CardTitle>
          <Select
            value={countryFilter?.toString() || "all"}
            onValueChange={(value) => setCountryFilter(value === "all" ? undefined : parseInt(value))}
          >
            <SelectTrigger className="w-[200px] bg-popover border-border">
              <SelectValue placeholder="Filtrar por país" />
            </SelectTrigger>
            <SelectContent className="bg-popover border-border">
              <SelectItem value="all">Todos los países</SelectItem>
              {countries.map((country) => (
                <SelectItem key={country.id} value={country.id.toString()}>
                  {country.pais}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
        <div className="space-y-2">
          <label className="text-sm font-medium text-foreground">Modo de Juego</label>
          <Select
            value={selectedGameMode?.toString() || ""}
            onValueChange={(value) => setSelectedGameMode(parseInt(value))}
          >
            <SelectTrigger className="bg-card border-border max-w-xs">
              <SelectValue placeholder="Selecciona un modo" />
            </SelectTrigger>
            <SelectContent className="bg-popover border-border">
              {gameModes.map((mode) => (
                <SelectItem key={mode.id} value={mode.id.toString()}>
                  {mode.gameMode}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
        {selectedGameModeName && (
          <div className="text-sm text-muted-foreground">
            Mostrando: {selectedGameModeName}
          </div>
        )}
      </CardHeader>
      <CardContent>
        {loading ? (
          <div className="text-center py-8 text-muted-foreground">Cargando leaderboard...</div>
        ) : leaderboardData.length === 0 ? (
          <div className="text-center py-8 text-muted-foreground">
            No hay datos disponibles para este modo de juego y país.
          </div>
        ) : (
          <Table>
            <TableHeader>
              <TableRow className="border-border hover:bg-muted/50">
                <TableHead className="w-[80px]">Posición</TableHead>
                <TableHead>Jugador</TableHead>
                <TableHead className="text-right">Puntuación</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {leaderboardData.map((entry, index) => (
                <TableRow key={entry.jugadorId} className="border-border hover:bg-muted/50">
                  <TableCell className="font-medium">
                    <div className="flex items-center gap-2">
                      {getRankIcon(index + 1)}
                    </div>
                  </TableCell>
                  <TableCell className="font-semibold">{entry.userName}</TableCell>
                  <TableCell className="text-right font-bold text-accent">
                    {entry.score.toLocaleString()}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </CardContent>
    </Card>
  );
};
