import { useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { apiService } from "@/services/api";
import type { TopCar, Body, Wheels, Boost, Decal } from "@/types/api";

interface CarPerformanceProps {
  playerId: number;
}

export const CarPerformance = ({ playerId }: CarPerformanceProps) => {
  const [topCars, setTopCars] = useState<TopCar[]>([]);
  const [bodies, setBodies] = useState<Body[]>([]);
  const [wheels, setWheels] = useState<Wheels[]>([]);
  const [boosts, setBoosts] = useState<Boost[]>([]);
  const [decals, setDecals] = useState<Decal[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        const [carsData, bodiesData, wheelsData, boostsData, decalsData] = await Promise.all([
          apiService.getTopCars(playerId),
          apiService.getAllBodies(),
          apiService.getAllWheels(),
          apiService.getAllBoosts(),
          apiService.getAllDecals(),
        ]);
        
        setTopCars(carsData);
        setBodies(bodiesData);
        setWheels(wheelsData);
        setBoosts(boostsData);
        setDecals(decalsData);
      } catch (error) {
        console.error("Error loading car performance:", error);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [playerId]);

  const getCarName = (car: TopCar) => {
    const body = bodies.find(b => b.id === car.bodyId)?.body || "Unknown";
    return `${body}`;
  };

  const chartData = topCars.slice(0, 5).map(car => ({
    name: getCarName(car),
    goles: car.goles,
    victorias: car.victorias,
    tiros: car.tiros,
    winRate: car.winPct,
  }));

  if (loading) {
    return <div className="text-center py-8 text-muted-foreground">Cargando rendimiento por carro...</div>;
  }

  if (topCars.length === 0) {
    return <div className="text-center py-8 text-muted-foreground">No hay datos de carros disponibles</div>;
  }

  return (
    <div className="space-y-6">
      <Card className="bg-card border-border">
        <CardHeader>
          <CardTitle className="text-xl">Rendimiento por Carro</CardTitle>
        </CardHeader>
        <CardContent>
          <ResponsiveContainer width="100%" height={400}>
            <BarChart data={chartData}>
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
              <Bar dataKey="goles" fill="hsl(var(--primary))" name="Goles" />
              <Bar dataKey="victorias" fill="hsl(var(--secondary))" name="Victorias" />
              <Bar dataKey="tiros" fill="hsl(var(--accent))" name="Tiros" />
            </BarChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {topCars.slice(0, 6).map((car) => (
          <Card key={car.carroId} className="bg-card border-border hover:border-primary/50 transition-all">
            <CardHeader>
              <CardTitle className="text-lg">{getCarName(car)}</CardTitle>
              <p className="text-xs text-muted-foreground">
                {bodies.find(b => b.id === car.bodyId)?.body} • 
                {wheels.find(w => w.id === car.wheelsId)?.wheels} • 
                {boosts.find(b => b.id === car.boostId)?.boost}
              </p>
            </CardHeader>
            <CardContent className="space-y-2 text-sm">
              <div className="flex justify-between">
                <span className="text-muted-foreground">Goles:</span>
                <span className="font-semibold">{car.goles}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Victorias:</span>
                <span className="font-semibold">{car.victorias}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Partidos:</span>
                <span className="font-semibold">{car.partidos}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Tiros:</span>
                <span className="font-semibold">{car.tiros}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Win Rate:</span>
                <span className="font-bold text-accent">{car.winPct.toFixed(1)}%</span>
              </div>
              <div className="flex justify-between">
                <span className="text-muted-foreground">Score:</span>
                <span className="font-bold text-primary">{car.score.toFixed(1)}</span>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};
