import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Trophy } from "lucide-react";
import type { PlayerRank } from "@/types/api";

interface RankDisplayProps {
  ranks: PlayerRank[];
}

export const RankDisplay = ({ ranks }: RankDisplayProps) => {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      {ranks.map((rankData) => (
        <Card key={rankData.id} className="bg-card border-border hover:border-primary/50 transition-all">
          <CardHeader className="pb-3">
            <CardTitle className="text-lg flex items-center gap-2">
              <Trophy className="h-5 w-5 text-secondary" />
              {rankData.gamemode.gameMode}
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="flex items-center justify-between">
              <span className="text-muted-foreground">Rango:</span>
              <Badge className="bg-primary/20 text-primary border-primary/50">
                {rankData.rango.rango}
              </Badge>
            </div>
            <div className="flex items-center justify-between">
              <span className="text-muted-foreground">División:</span>
              <span className="font-semibold">{rankData.rango.division}</span>
            </div>
            <div className="flex items-center justify-between">
              <span className="text-muted-foreground">Puntuación:</span>
              <span className="font-bold text-accent">{rankData.puntuacion.toFixed(0)}</span>
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  );
};
