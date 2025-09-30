import { useQuery } from "@tanstack/react-query";
import { Exercise } from "@/types/exercise";
import ExerciseCard from "./ExerciseCard";
import { Loader2, AlertCircle } from "lucide-react";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";

const mockExercises: Exercise[] = [
    {
        id: 1,
        nombre: "Press de Banca",
        descripcion: "Ejercicio fundamental para el desarrollo del pecho, hombros y tríceps. Acuéstate en un banco plano y empuja la barra hacia arriba desde el pecho."
    },
    {
        id: 2,
        nombre: "Sentadillas",
        descripcion: "Ejercicio completo para piernas y glúteos. Baja el cuerpo doblando las rodillas hasta que los muslos estén paralelos al suelo."
    },
    {
        id: 3,
        nombre: "Peso Muerto",
        descripcion: "Ejercicio compuesto que trabaja toda la cadena posterior. Levanta la barra desde el suelo manteniendo la espalda recta."
    },
    {
        id: 4,
        nombre: "Dominadas",
        descripcion: "Excelente para desarrollar la espalda y bíceps. Cuelga de una barra y eleva tu cuerpo hasta que la barbilla supere la barra."
    },
    {
        id: 5,
        nombre: "Press Militar",
        descripcion: "Desarrollo de hombros. De pie o sentado, empuja la barra o mancuernas desde los hombros hacia arriba."
    },
    {
        id: 6,
        nombre: "Remo con Barra",
        descripcion: "Fortalece la espalda media y alta. Inclínate hacia adelante y tira de la barra hacia tu abdomen."
    }
];

const fetchExercises = async (): Promise<Exercise[]> => {
    try {
        console.log("Intentando conectar con el servidor...");
        const response = await fetch("http://localhost:8080/Ejercicio/listAll");

        console.log("Respuesta del servidor:", response.status, response.statusText);

        if (!response.ok) {
            console.error("Error del servidor:", response.status);
            return mockExercises;
        }

        const data = await response.json();
        console.log("Datos recibidos del servidor:", data);
        console.log("Cantidad de ejercicios:", data.length);

        return data;
    } catch (error) {
        console.error("Error al conectar con el servidor:", error);
        console.log("Posible problema de CORS o servidor no disponible");
        return mockExercises;
    }
};

const ExerciseList = () => {
    const { data: exercises, isLoading, error } = useQuery({
        queryKey: ["exercises"],
        queryFn: fetchExercises,
    });

    console.log("Estado de exercises:", exercises);
    console.log("isLoading:", isLoading);
    console.log("error:", error);

    if (isLoading) {
        return (
            <div className="flex items-center justify-center min-h-[400px]">
                <div className="text-center space-y-4">
                    <Loader2 className="w-12 h-12 animate-spin text-primary mx-auto" />
                    <p className="text-muted-foreground">Cargando ejercicios...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <Alert variant="destructive" className="max-w-2xl mx-auto">
                <AlertCircle className="h-4 w-4" />
                <AlertTitle>Error</AlertTitle>
                <AlertDescription>
                    No se pudieron cargar los ejercicios. Verifica que el servidor esté funcionando en http://localhost:8080
                </AlertDescription>
            </Alert>
        );
    }

    if (!exercises || exercises.length === 0) {
        console.log("No hay ejercicios para mostrar");
        return (
            <Alert className="max-w-2xl mx-auto">
                <AlertCircle className="h-4 w-4" />
                <AlertTitle>Sin ejercicios</AlertTitle>
                <AlertDescription>
                    No hay ejercicios disponibles en este momento.
                </AlertDescription>
            </Alert>
        );
    }

    console.log("Renderizando ejercicios:", exercises.length);

    return (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {exercises.map((exercise, index) => {
                console.log(`Renderizando ejercicio ${index}:`, exercise);
                return (
                    <ExerciseCard
                        key={exercise.id || index}
                        exercise={exercise}
                        index={index}
                    />
                );
            })}
        </div>
    );
};

export default ExerciseList;
