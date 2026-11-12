public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("   SISTEMA DE GESTIÓN Y PROYECCIÓN ACADÉMICA - UNSA");
        System.out.println("   Nota mínima aprobatoria: " + ReglamentoUNSA.NOTA_APROBATORIA);
        System.out.println("=========================================================\n");

        // 1. Inicializar el Gestor Académico
        GestorAcademico gestor = new GestorAcademico();

        // 2. Crear un curso (Ej. 40% Continua, 60% Examen Parcial)
        Curso dsoo = new Curso("Desarrollo de Software Orientado a Objetos", 0.40, 0.60);
        gestor.agregarCurso(dsoo);

        // 3. Registrar las 3 Fases Académicas
        // Fase 1: peso 30% del ciclo | Continua: 12.0 | Parcial: 08.0 (nota baja)
        NotaContinua nc1 = new NotaContinua(0.40, 12.0);
        ExamenParcial ep1 = new ExamenParcial(1, 0.60, 8.0);
        Fase fase1 = new Fase(1, 0.30, nc1, ep1);
        fase1.setCerrada(true);
        dsoo.agregarFase(fase1);

        // Fase 2: peso 30% del ciclo | Continua: 13.0 | Parcial: 07.0 (nota más baja)
        NotaContinua nc2 = new NotaContinua(0.40, 13.0);
        ExamenParcial ep2 = new ExamenParcial(2, 0.60, 7.0);
        Fase fase2 = new Fase(2, 0.30, nc2, ep2);
        fase2.setCerrada(true);
        dsoo.agregarFase(fase2);

        // Fase 3: peso 40% del ciclo | Continua: 11.0 | Parcial: 10.0
        NotaContinua nc3 = new NotaContinua(0.40, 11.0);
        ExamenParcial ep3 = new ExamenParcial(3, 0.60, 10.0);
        Fase fase3 = new Fase(3, 0.40, nc3, ep3);
        fase3.setCerrada(true);
        dsoo.agregarFase(fase3);

        // 4. Buscar el curso desde el gestor y mostrar resultados por fase
        Curso cursoBuscado = gestor.buscarCurso("Desarrollo de Software Orientado a Objetos");

        if (cursoBuscado != null) {
            System.out.println("Curso: " + cursoBuscado.getNombre());
            System.out.println("---------------------------------------------------------");

            for (Fase f : cursoBuscado.getListaFases()) {
                System.out.printf("Fase %d (Peso %.0f%%) -> Continua: %.2f | Parcial: %.2f | Prom. Fase: %.2f | Aporte Final: %.2f%n",
                        f.getNumeroFase(),
                        f.getPorcentajePonderado() * 100,
                        f.getContinua().getNota(),
                        f.getExamen().getNota(),
                        f.calcularPromedioFase(),
                        f.calcularAporteFinal());
            }

            // 5. Calcular promedio actual y proyección para aprobar
            double promedioActual = cursoBuscado.calcularPromedioPonderadoActual();
            double puntosFaltantes = cursoBuscado.calcProyeccionAprobar(promedioActual);

            System.out.println("\n--- ESTADO ANTES DEL SUSTITUTORIO ---");
            System.out.printf("Promedio Ponderado Actual: %.2f%n", promedioActual);
            if (puntosFaltantes > 0) {
                System.out.printf("Estado: DESAPROBADO (Faltan %.2f puntos para llegar a %.1f)%n",
                        puntosFaltantes, ReglamentoUNSA.NOTA_APROBATORIA);
            } else {
                System.out.println("Estado: APROBADO");
            }

            // 6. Simular y aplicar Examen Sustitutorio (Ej. nota de 15.0)
            System.out.println("\n--- APLICANDO EXAMEN SUSTITUTORIO ---");
            double notaSusti = 15.0;
            System.out.printf("Nota obtenida en Examen Sustitutorio: %.2f%n", notaSusti);

            boolean reemplazado = cursoBuscado.registrarSustitutorio(notaSusti);

            if (reemplazado) {
                ExamenSustitutorio susti = cursoBuscado.getSustitutorio();
                ExamenParcial epReemplazado = susti.getExamenReemplazado();

                System.out.printf("¡Reemplazo exitoso! Se sustituyó el Examen Parcial %d (Nota original: %.2f -> Nueva nota: %.2f)%n",
                        epReemplazado.getNumeroParcial(),
                        epReemplazado.getNotaOriginal(),
                        epReemplazado.getNota());

                double nuevoPromedio = cursoBuscado.calcularPromedioPonderadoActual();
                System.out.printf("Nuevo Promedio Ponderado Final: %.2f%n", nuevoPromedio);
                System.out.println("Estado Final: " + (nuevoPromedio >= ReglamentoUNSA.NOTA_APROBATORIA ? "APROBADO" : "DESAPROBADO"));
            } else {
                System.out.println("No se aplicó el sustitutorio (no mejora ningún examen parcial).");
            }
        }
    }
}