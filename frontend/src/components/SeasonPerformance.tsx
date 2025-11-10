import { useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { apiService } from "@/services/api";
import type { TopSeason } from "@/types/api";

interface SeasonPerformanceProps {
  playerId: number;
}

export const SeasonPerformance = ({ playerId }: SeasonPerformanceProps) => {
  const [topSeasons, setTopSeasons] = useState<TopSeason[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        const data = await apiService.getTopSeasons(playerId);
        // Sort by temporadaId to maintain chronological order (Legacy first, then Free to Play)
        const sortedData = data.sort((a, b) => a.temporadaId - b.temporadaId);
        setTopSeasons(sortedData);
      } catch (error) {
        console.error("Error loading season performance:", error);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [playerId]);

  const chartData = topSeasons.map(season => ({
    name: season.temporadaName.replace(" (Legacy)", "").replace("Season ", "S"),
    goles: season.goles,
    victorias: season.victorias,
    tiros: season.tiros,
    winRate: season.winPct,
  }));

  if (loading) {
    return <div className="text-center py-8 text-muted-foreground">Cargando rendimiento por temporada...</div>;
  }

  if (topSeasons.length === 0) {
    return <div className="text-center py-8 text-muted-foreground">No hay datos de temporadas disponibles</div>;
  }

  return (
    <div className="space-y-6">
      <Card className="bg-card border-border">
        <CardHeader>
          <CardTitle className="text-xl">Progresión por Temporada</CardTitle>
        </CardHeader>
        <CardContent>
          <ResponsiveContainer width="100%" height={400}>
            <LineChart data={chartData}>
              <CartesianGrid strokeDasharray="3 3" stroke="hsl(var(--border))" />
              <XAxis dataKey="name" stroke="hsl(var(--muted-foreground))" />
              <YAxis stroke="hsl(var(--muted-foreground))" />
              <Tooltip 
                contentStyle={{ 
                  backgroundColor: "hsl(var(--popover))", 
                  border: "1px solid hsl(var(--border))",
                  borderRadius: "var(--radius)"
                }}
              />
              <Legend />
              <Line type="monotone" dataKey="goles" stroke="hsl(var(--primary))" strokeWidth={2} name="Goles" />
              <Line type="monotone" dataKey="victorias" stroke="hsl(var(--secondary))" strokeWidth={2} name="Victorias" />
              <Line type="monotone" dataKey="tiros" stroke="hsl(var(--accent))" strokeWidth={2} name="Tiros" />
            </LineChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>

      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {topSeasons.map((season) => (
          <Card key={season.temporadaId} className="bg-card border-border">
            <CardHeader className="pb-2">
              <CardTitle className="text-base">{season.temporadaName}</CardTitle>
            </CardHeader>
            <CardContent className="space-y-1 text-sm">
              <div className="flex justify-between">
                <span className="text-muted-foreground">Goles:</span>
                <span className="font-semibold">{season.goles}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Victorias:</span>
                <span className="font-semibold">{season.victorias}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Partidos:</span>
                <span className="font-semibold">{season.partidos}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Win Rate:</span>
                <span className="font-bold text-accent">{season.winPct.toFixed(1)}%</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Score:</span>
                <span className="font-bold text-primary">{season.score.toFixed(1)}</span>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};
