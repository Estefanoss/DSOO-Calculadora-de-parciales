public class MainConsola {
    public static void main(String[] args) {
        System.out.println("=== UNSA - SIMULADOR ACADEMICO (MODO CONSOLA) ===\n");

        GestorAcademico gestor = new GestorAcademico();
        Curso discretas = new Curso("Estructuras Discretas II", 4);
        gestor.agregarCurso(discretas);

        // Fase 1: Continua 20% (nota 16), Examen 1 10% (nota 12)
        NotaContinua c1 = new NotaContinua("Continua 1", 0.20, 16.0);
        ExamenParcial e1 = new ExamenParcial(1, "Examen 1", 0.10, 12.0);
        discretas.agregarOActualizarFase(new Fase(1, c1, e1));

        // Fase 2: Continua 25% (nota 14), Examen 2 10% (nota 08)
        NotaContinua c2 = new NotaContinua("Continua 2", 0.25, 14.0);
        ExamenParcial e2 = new ExamenParcial(2, "Examen 2", 0.10, 08.0);
        discretas.agregarOActualizarFase(new Fase(2, c2, e2));

        System.out.printf("Puntaje acumulado tras Fase 1 y Fase 2: %.2f%n", discretas.calcularPromedioPonderadoActual());
        System.out.println("¿Habilitado para sustitutorio (antes de Fase 3)? " + discretas.puedeRendirSustitutorio());

        if (discretas.puedeRendirSustitutorio()) {
            ExamenParcial objetivo = discretas.obtenerExamenObjetivoSustitutorio();
            System.out.println("Examen menor entre Parcial 1 y 2: " + objetivo.getEtiqueta() + " (nota actual: " + objetivo.getNota() + ")");
            discretas.registrarSustitutorio(15.0);
            System.out.printf("Nuevo puntaje acumulado tras sustitutorio (15.0): %.2f%n", discretas.calcularPromedioPonderadoActual());
        }

        // Fase 3: Continua 3 18% (nota 15), Examen 3 17% (nota 14) -> suma pesos = 100%
        NotaContinua c3 = new NotaContinua("Continua 3", 0.18, 15.0);
        ExamenParcial e3 = new ExamenParcial(3, "Examen 3", 0.17, 14.0);
        discretas.agregarOActualizarFase(new Fase(3, c3, e3));

        System.out.printf("Puntaje final del curso: %.2f (Redondeado: %d)%n",
                discretas.calcularPromedioPonderadoActual(),
                discretas.obtenerNotaFinalRedondeada());
        System.out.println("¿Ya se puede rendir sustitutorio después de Fase 3? " + discretas.puedeRendirSustitutorio());
    }
}