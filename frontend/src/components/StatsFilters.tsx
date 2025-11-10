import { useEffect, useState } from "react";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { apiService } from "@/services/api";
import type { Body, Wheels, Boost, Decal, Stadium, GameMode, Season } from "@/types/api";

interface StatsFiltersProps {
  filters: {
    idEstadio?: number;
    idGamemode?: number;
    idDecal?: number;
    idBoost?: number;
    idWheels?: number;
    idBody?: number;
    idTemporada?: number;
  };
  onFilterChange: (key: string, value: number | undefined) => void;
}

export const StatsFilters = ({ filters, onFilterChange }: StatsFiltersProps) => {
  const [bodies, setBodies] = useState<Body[]>([]);
  const [wheels, setWheels] = useState<Wheels[]>([]);
  const [boosts, setBoosts] = useState<Boost[]>([]);
  const [decals, setDecals] = useState<Decal[]>([]);
  const [stadiums, setStadiums] = useState<Stadium[]>([]);
  const [gameModes, setGameModes] = useState<GameMode[]>([]);
  const [seasons, setSeasons] = useState<Season[]>([]);

  useEffect(() => {
    const loadFilters = async () => {
      try {
        const [bodiesData, wheelsData, boostsData, decalsData, stadiumsData, gameModesData, seasonsData] = 
          await Promise.all([
            apiService.getAllBodies(),
            apiService.getAllWheels(),
            apiService.getAllBoosts(),
            apiService.getAllDecals(),
            apiService.getAllStadiums(),
            apiService.getAllGameModes(),
            apiService.getAllSeasons(),
          ]);
        
        setBodies(bodiesData);
        setWheels(wheelsData);
        setBoosts(boostsData);
        setDecals(decalsData);
        setStadiums(stadiumsData);
        setGameModes(gameModesData);
        // Sort seasons by ID to maintain chronological order (Legacy first, then Free to Play)
        setSeasons(seasonsData.sort((a, b) => a.id - b.id));
      } catch (error) {
        console.error("Error loading filters:", error);
      }
    };

    loadFilters();
  }, []);

  return (
    <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-7 gap-3">
      <div className="space-y-2">
        <label className="text-sm font-medium text-foreground">Estadio</label>
        <Select
        value={filters.idEstadio?.toString() || "all"}
        onValueChange={(value) => onFilterChange("idEstadio", value === "all" ? undefined : parseInt(value))}
      >
        <SelectTrigger className="bg-card border-border">
          <SelectValue placeholder="Estadio" />
        </SelectTrigger>
        <SelectContent className="bg-popover border-border">
          <SelectItem value="all">Todos</SelectItem>
          {stadiums.map((stadium) => (
            <SelectItem key={stadium.id} value={stadium.id.toString()}>
              {stadium.estadio}
            </SelectItem>
          ))}
        </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <label className="text-sm font-medium text-foreground">Modo de Juego</label>
        <Select
        value={filters.idGamemode?.toString() || "all"}
        onValueChange={(value) => onFilterChange("idGamemode", value === "all" ? undefined : parseInt(value))}
      >
        <SelectTrigger className="bg-card border-border">
          <SelectValue placeholder="Modo de Juego" />
        </SelectTrigger>
        <SelectContent className="bg-popover border-border">
          <SelectItem value="all">Todos</SelectItem>
          {gameModes.map((mode) => (
            <SelectItem key={mode.id} value={mode.id.toString()}>
              {mode.gameMode}
            </SelectItem>
          ))}
        </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <label className="text-sm font-medium text-foreground">Decal</label>
        <Select
        value={filters.idDecal?.toString() || "all"}
        onValueChange={(value) => onFilterChange("idDecal", value === "all" ? undefined : parseInt(value))}
      >
        <SelectTrigger className="bg-card border-border">
          <SelectValue placeholder="Decal" />
        </SelectTrigger>
        <SelectContent className="bg-popover border-border">
          <SelectItem value="all">Todos</SelectItem>
          {decals.map((decal) => (
            <SelectItem key={decal.id} value={decal.id.toString()}>
              {decal.decal}
            </SelectItem>
          ))}
        </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <label className="text-sm font-medium text-foreground">Boost</label>
        <Select
        value={filters.idBoost?.toString() || "all"}
        onValueChange={(value) => onFilterChange("idBoost", value === "all" ? undefined : parseInt(value))}
      >
        <SelectTrigger className="bg-card border-border">
          <SelectValue placeholder="Boost" />
        </SelectTrigger>
        <SelectContent className="bg-popover border-border">
          <SelectItem value="all">Todos</SelectItem>
          {boosts.map((boost) => (
            <SelectItem key={boost.id} value={boost.id.toString()}>
              {boost.boost}
            </SelectItem>
          ))}
        </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <label className="text-sm font-medium text-foreground">Wheels</label>
        <Select
        value={filters.idWheels?.toString() || "all"}
        onValueChange={(value) => onFilterChange("idWheels", value === "all" ? undefined : parseInt(value))}
      >
        <SelectTrigger className="bg-card border-border">
          <SelectValue placeholder="Wheels" />
        </SelectTrigger>
        <SelectContent className="bg-popover border-border">
          <SelectItem value="all">Todos</SelectItem>
          {wheels.map((wheel) => (
            <SelectItem key={wheel.id} value={wheel.id.toString()}>
              {wheel.wheels}
            </SelectItem>
          ))}
        </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <label className="text-sm font-medium text-foreground">Body</label>
        <Select
        value={filters.idBody?.toString() || "all"}
        onValueChange={(value) => onFilterChange("idBody", value === "all" ? undefined : parseInt(value))}
      >
        <SelectTrigger className="bg-card border-border">
          <SelectValue placeholder="Body" />
        </SelectTrigger>
        <SelectContent className="bg-popover border-border">
          <SelectItem value="all">Todos</SelectItem>
          {bodies.map((body) => (
            <SelectItem key={body.id} value={body.id.toString()}>
              {body.body}
            </SelectItem>
          ))}
        </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <label className="text-sm font-medium text-foreground">Temporada</label>
        <Select
        value={filters.idTemporada?.toString() || "all"}
        onValueChange={(value) => onFilterChange("idTemporada", value === "all" ? undefined : parseInt(value))}
      >
        <SelectTrigger className="bg-card border-border">
          <SelectValue placeholder="Temporada" />
        </SelectTrigger>
        <SelectContent className="bg-popover border-border">
          <SelectItem value="all">Todas</SelectItem>
          {seasons.map((season) => (
            <SelectItem key={season.id} value={season.id.toString()}>
              {season.temporada}
            </SelectItem>
          ))}
        </SelectContent>
        </Select>
      </div>
    </div>
  );
};
