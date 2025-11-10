// API Response Types
export interface Country {
  id: number;
  pais: string;
}

export interface Player {
  id: number;
  nombre: string;
  email: string;
  telefono: string;
  userName: string;
  fechaNacimiento: string;
  pais: Country;
}

export interface Rank {
  id: number;
  rango: string;
  division: number;
}

export interface GameMode {
  id: number;
  gameMode: string;
}

export interface Body {
  id: number;
  body: string;
}

export interface Wheels {
  id: number;
  wheels: string;
}

export interface Boost {
  id: number;
  boost: string;
}

export interface Decal {
  id: number;
  decal: string;
}

export interface Stadium {
  id: number;
  estadio: string;
}

export interface Season {
  id: number;
  temporada: string;
  fechaInicio: string;
  fechaFin: string;
}

export interface PlayerRank {
  id: number;
  jugador: Player;
  rango: Rank;
  gamemode: GameMode;
  puntuacion: number;
}

export interface PlayerStats {
  jugadorId: number;
  userName: string;
  goles: number;
  asistencias: number;
  salvadas: number;
  victorias: number;
  partidosJugados: number;
  winPercentage: number;
  tiros: number;
  goalShotRatio: number;
}

export interface TopCar {
  carroId: number;
  bodyId: number;
  decalId: number;
  wheelsId: number;
  boostId: number;
  goles: number;
  victorias: number;
  partidos: number;
  tiros: number;
  winPct: number;
  goalShotRatio: number;
  score: number;
}

export interface TopSeason {
  temporadaId: number;
  temporadaName: string;
  goles: number;
  victorias: number;
  partidos: number;
  tiros: number;
  winPct: number;
  goalShotRatio: number;
  score: number;
}

export interface RankGameMode {
  id: number;
  rango: Rank;
  gamemode: GameMode;
  puntuacion_inicial: number;
  puntuacion_final: number;
}

export interface StatsFilters {
  idEstadio?: number;
  idGamemode?: number;
  idDecal?: number;
  idBoost?: number;
  idWheels?: number;
  idBody?: number;
  idTemporada?: number;
}

export interface LeaderboardEntry {
  jugadorId: number;
  userName: string;
  score: number;
  goles: number;
  asistencias: number;
  salvadas: number;
  victorias: number;
  partidosJugados: number;
  tiros: number;
}

