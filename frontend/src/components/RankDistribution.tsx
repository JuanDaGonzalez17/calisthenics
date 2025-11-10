import { useState, useEffect } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { apiService } from "@/services/api";
import type { GameMode, RankGameMode } from "@/types/api";

export const RankDistribution = () => {
  const [gameModes, setGameModes] = useState<GameMode[]>([]);
  const [selectedGameMode, setSelectedGameMode] = useState<number | null>(null);
  const [rankData, setRankData] = useState<RankGameMode[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const loadGameModes = async () => {
      try {
        const modes = await apiService.getAllGameModes();
        setGameModes(modes);
        if (modes.length > 0) {
          setSelectedGameMode(modes[0].id);
        }
      } catch (error) {
        console.error("Error loading game modes:", error);
      }
    };

    loadGameModes();
  }, []);

  useEffect(() => {
    if (selectedGameMode) {
      loadRankData(selectedGameMode);
    }
  }, [selectedGameMode]);

  const loadRankData = async (gamemodeId: number) => {
    setLoading(true);
    try {
      const data = await apiService.getRanksByGameMode(gamemodeId);
      console.log("Raw rank data from backend:", data);
      console.log("Data type:", Array.isArray(data) ? "Array" : typeof data);
      setRankData(data);
    } catch (error) {
      console.error("Error loading rank data:", error);
    } finally {
      setLoading(false);
    }
  };

  // Group ranks by rank name and division
  const getRankMatrix = () => {
    console.log("Processing rankData:", rankData);

    if (!rankData || rankData.length === 0) {
      return { uniqueRanks: [], matrix: {} };
    }

    const matrix: { [key: string]: { [division: number]: RankGameMode } } = {};

    rankData.forEach(item => {
      console.log("Processing item:", item);

      // Ensure the item has the expected structure
      if (!item || !item.rango || !item.rango.rango) {
        console.warn("Invalid item structure:", item);
        return;
      }

      const rankName = item.rango.rango;
      const division = item.rango.division;
      
      console.log(`Rank: ${rankName}, Division: ${division}`);

      if (!matrix[rankName]) {
        matrix[rankName] = {};
      }
      matrix[rankName][division] = item;
    });

    const uniqueRanks = Object.keys(matrix);
    console.log("Unique ranks:", uniqueRanks);
    console.log("Final matrix:", matrix);

    return { uniqueRanks, matrix };
  };

  const { uniqueRanks, matrix } = getRankMatrix();
  const selectedGameModeName = gameModes.find(gm => gm.id === selectedGameMode)?.gameMode || "";

  return (
    <Card className="bg-card border-border">
      <CardHeader>
        <CardTitle className="text-xl">Distribución de Rangos por MMR</CardTitle>
        <div className="space-y-2 pt-4">
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
      </CardHeader>
      <CardContent>
        {loading ? (
          <div className="text-center py-8 text-muted-foreground">Cargando...</div>
        ) : uniqueRanks.length === 0 ? (
          <div className="text-center py-8 text-muted-foreground">
            No hay datos de rangos disponibles para este modo de juego.
          </div>
        ) : (
          <>
            <div className="mb-4 text-center">
              <h3 className="text-lg font-semibold text-foreground">
                {selectedGameModeName}
              </h3>
            </div>
            <div className="overflow-x-auto">
              <table className="w-full border-collapse">
                <thead>
                  <tr className="border-b border-border">
                    <th className="p-3 text-left font-semibold text-foreground bg-muted/50">Rango</th>
                    <th className="p-3 text-center font-semibold text-foreground bg-muted/50">División 1</th>
                    <th className="p-3 text-center font-semibold text-foreground bg-muted/50">División 2</th>
                    <th className="p-3 text-center font-semibold text-foreground bg-muted/50">División 3</th>
                    <th className="p-3 text-center font-semibold text-foreground bg-muted/50">División 4</th>
                  </tr>
                </thead>
                <tbody>
                  {uniqueRanks.map((rankName, idx) => (
                    <tr key={rankName} className={idx % 2 === 0 ? "bg-card" : "bg-muted/20"}>
                      <td className="p-3 font-medium text-foreground border-r border-border">
                        {rankName}
                      </td>
                      {[1, 2, 3, 4].map((division) => {
                        const cell = matrix[rankName]?.[division];
                        return (
                          <td key={division} className="p-3 text-center text-sm border-r border-border">
                            {cell ? (
                              <div className="space-y-1">
                                <div className="font-semibold text-primary">
                                  {cell.puntuacion_inicial} - {cell.puntuacion_final}
                                </div>
                                <div className="text-xs text-muted-foreground">MMR</div>
                              </div>
                            ) : (
                              <span className="text-muted-foreground">-</span>
                            )}
                          </td>
                        );
                      })}
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </>
        )}
      </CardContent>
    </Card>
  );
};
