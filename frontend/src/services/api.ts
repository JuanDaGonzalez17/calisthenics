import type {
  PlayerStats,
  PlayerRank,
  TopCar,
  TopSeason,
  Body,
  Wheels,
  Boost,
  Decal,
  Stadium,
  GameMode,
  Country,
  Season,
  Rank,
  RankGameMode,
  StatsFilters,
  LeaderboardEntry,
} from "@/types/api";
import { authService } from "./auth";

const API_BASE_URL = "http://localhost:8080/api";

class ApiService {
  // Helper method to get headers with JWT token
  private getHeaders(): HeadersInit {
    const token = authService.getToken();
    return {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    };
  }

  // Player Stats
  async getPlayerStats(idJugador: number, filters?: StatsFilters): Promise<PlayerStats> {
    const params = new URLSearchParams({ idJugador: idJugador.toString() });
    
    if (filters) {
      Object.entries(filters).forEach(([key, value]) => {
        if (value !== undefined) {
          params.append(key, value.toString());
        }
      });
    }

    const response = await fetch(
      `${API_BASE_URL}/jugador_x_partido_x_carro/estadisticas?${params}`,
      { headers: this.getHeaders() }
    );
    if (!response.ok) throw new Error("Failed to fetch player stats");
    const data = await response.json();
    // Handle array response with single object
    return Array.isArray(data) ? data[0] : data;
  }

  // Player Ranks
  async getPlayerRanks(idJugador: number): Promise<PlayerRank[]> {
    const response = await fetch(
      `${API_BASE_URL}/jugador_x_rango_x_gamemode/byJugador?idJugador=${idJugador}`,
      { headers: this.getHeaders() }
    );
    if (!response.ok) throw new Error("Failed to fetch player ranks");
    return response.json();
  }

  // Top Cars
  async getTopCars(idJugador: number): Promise<TopCar[]> {
    const response = await fetch(
      `${API_BASE_URL}/jugador_x_partido_x_carro/top-cars?idJugador=${idJugador}`,
      { headers: this.getHeaders() }
    );
    if (!response.ok) throw new Error("Failed to fetch top cars");
    return response.json();
  }

  // Top Seasons
  async getTopSeasons(idJugador: number): Promise<TopSeason[]> {
    const response = await fetch(
      `${API_BASE_URL}/jugador_x_partido_x_carro/top-seasons?idJugador=${idJugador}`,
      { headers: this.getHeaders() }
    );
    if (!response.ok) throw new Error("Failed to fetch top seasons");
    return response.json();
  }

  // List All endpoints
  async getAllBodies(): Promise<Body[]> {
    const response = await fetch(`${API_BASE_URL}/body/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch bodies");
    return response.json();
  }

  async getAllWheels(): Promise<Wheels[]> {
    const response = await fetch(`${API_BASE_URL}/wheels/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch wheels");
    return response.json();
  }

  async getAllBoosts(): Promise<Boost[]> {
    const response = await fetch(`${API_BASE_URL}/boost/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch boosts");
    return response.json();
  }

  async getAllDecals(): Promise<Decal[]> {
    const response = await fetch(`${API_BASE_URL}/decal/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch decals");
    return response.json();
  }

  async getAllStadiums(): Promise<Stadium[]> {
    const response = await fetch(`${API_BASE_URL}/estadio/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch stadiums");
    return response.json();
  }

  async getAllGameModes(): Promise<GameMode[]> {
    const response = await fetch(`${API_BASE_URL}/gamemode/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch game modes");
    return response.json();
  }

  async getAllCountries(): Promise<Country[]> {
    const response = await fetch(`${API_BASE_URL}/pais/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch countries");
    return response.json();
  }

  async getAllSeasons(): Promise<Season[]> {
    const response = await fetch(`${API_BASE_URL}/temporada/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch seasons");
    return response.json();
  }

  async getAllRanks(): Promise<Rank[]> {
    const response = await fetch(`${API_BASE_URL}/rango/listAll`, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch ranks");
    return response.json();
  }

  async getRanksByGameMode(gamemodeId: number): Promise<RankGameMode[]> {
    const response = await fetch(
      `${API_BASE_URL}/rango_x_gamemode/byGamemode?gamemodeId=${gamemodeId}`,
      { headers: this.getHeaders() }
    );
    if (!response.ok) throw new Error("Failed to fetch ranks by game mode");
    const data = await response.json();
    console.log("getRanksByGameMode response:", data);
    // Ensure we always return an array
    return Array.isArray(data) ? data : [];
  }

  // Leaderboard
  async getLeaderboard(
    idGamemode?: number,
    idPais?: number,
    limit?: number
  ): Promise<LeaderboardEntry[]> {
    const params = new URLSearchParams();

    if (idGamemode !== undefined) {
      params.append("idGamemode", idGamemode.toString());
    }
    if (idPais !== undefined) {
      params.append("idPais", idPais.toString());
    }
    if (limit !== undefined) {
      params.append("limit", limit.toString());
    }

    const queryString = params.toString();
    const url = queryString
      ? `${API_BASE_URL}/jugador_x_partido_x_carro/leaderboard?${queryString}`
      : `${API_BASE_URL}/jugador_x_partido_x_carro/leaderboard`;

    const response = await fetch(url, { headers: this.getHeaders() });
    if (!response.ok) throw new Error("Failed to fetch leaderboard");
    const data = await response.json();
    console.log("getLeaderboard response:", data);
    // Ensure we always return an array
    return Array.isArray(data) ? data : [];
  }
}

export const apiService = new ApiService();
