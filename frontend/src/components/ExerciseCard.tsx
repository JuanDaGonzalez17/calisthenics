import { Exercise } from "@/types/exercise";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Dumbbell } from "lucide-react";

interface ExerciseCardProps {
  exercise: Exercise;
  index: number;
}

const ExerciseCard = ({ exercise, index }: ExerciseCardProps) => {
  return (
    <Card 
      className="gradient-card shadow-card hover:shadow-card-hover transition-all duration-300 hover:-translate-y-1 border-border/50 overflow-hidden group"
      style={{
        animationDelay: `${index * 100}ms`,
        animation: "fadeInUp 0.6s ease-out forwards",
        opacity: 0
      }}
    >
      <div className="absolute top-0 right-0 w-32 h-32 bg-primary/5 rounded-full -mr-16 -mt-16 group-hover:bg-primary/10 transition-colors duration-300" />
      <CardHeader className="relative">
        <div className="flex items-start gap-3">
          <div className="p-2 rounded-lg bg-primary/10 text-primary">
            <Dumbbell className="w-5 h-5" />
          </div>
          <div className="flex-1">
            <CardTitle className="text-xl font-bold text-foreground group-hover:text-primary transition-colors">
              {exercise.nombre}
            </CardTitle>
          </div>
        </div>
      </CardHeader>
      <CardContent className="relative">
        <CardDescription className="text-muted-foreground leading-relaxed">
          {exercise.descripcion}
        </CardDescription>
      </CardContent>
    </Card>
  );
};

export default ExerciseCard;
