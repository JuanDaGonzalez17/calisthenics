import ExerciseList from "@/components/ExerciseList";
import { Activity } from "lucide-react";

const Index = () => {
  return (
      <div className="min-h-screen bg-background">
        <div className="container mx-auto px-4 py-12">
          <header className="text-center mb-12 space-y-4">
            <div className="inline-flex items-center justify-center p-3 rounded-2xl gradient-primary mb-4">
              <Activity className="w-8 h-8 text-white" />
            </div>
            <h1 className="text-4xl md:text-5xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent inline-block">
              Biblioteca de Ejercicios
            </h1>
            <p className="text-muted-foreground text-lg max-w-2xl mx-auto">
              Explora nuestra colección completa de ejercicios con descripciones detalladas
            </p>
          </header>

          <main>
            <ExerciseList />
          </main>
        </div>
      </div>
  );
};

export default Index;