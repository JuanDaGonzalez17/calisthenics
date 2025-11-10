import { useState } from "react";
import { Search } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

interface PlayerSearchProps {
  onSearch: (playerId: string) => void;
  isLoading?: boolean;
}

export const PlayerSearch = ({ onSearch, isLoading }: PlayerSearchProps) => {
  const [playerId, setPlayerId] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (playerId.trim()) {
      onSearch(playerId.trim());
    }
  };

  return (
    <form onSubmit={handleSubmit} className="w-full max-w-2xl mx-auto">
      <div className="flex gap-2">
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-muted-foreground" />
          <Input
            type="text"
            placeholder="Ingresa el nombre o ID del jugador..."
            value={playerId}
            onChange={(e) => setPlayerId(e.target.value)}
            className="pl-10 h-12 bg-card border-border text-foreground placeholder:text-muted-foreground"
          />
        </div>
        <Button 
          type="submit" 
          disabled={isLoading || !playerId.trim()}
          className="h-12 px-8 bg-gradient-to-r from-primary to-accent hover:opacity-90 transition-opacity"
        >
          {isLoading ? "Buscando..." : "Buscar"}
        </Button>
      </div>
    </form>
  );
};
