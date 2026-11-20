public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("   SISTEMA DE GESTIÓN Y PROYECCIÓN ACADÉMICA - UNSA");
        System.out.println("   Nota mínima aprobatoria: " + ReglamentoUNSA.NOTA_APROBATORIA);
        System.out.println("=========================================================\n");

        GestorAcademico gestor = new GestorAcademico();

        Curso dsoo = new Curso("Desarrollo de Software Orientado a Objetos");
        gestor.agregarCurso(dsoo);

        NotaContinua nc1 = new NotaContinua(0.10, 11.0);
        ExamenParcial ep1 = new ExamenParcial(1, 0.15, 11.0);
        Fase fase1 = new Fase(1, nc1, ep1);
        fase1.setCerrada(true);
        dsoo.agregarFase(fase1);

        NotaContinua nc2 = new NotaContinua(0.10, 11.0);
        ExamenParcial ep2 = new ExamenParcial(2, 0.20, 11.0);
        Fase fase2 = new Fase(2, nc2, ep2);
        fase2.setCerrada(true);
        dsoo.agregarFase(fase2);

        NotaContinua nc3 = new NotaContinua(0.15, 11.0);
        ExamenParcial ep3 = new ExamenParcial(3, 0.30, 11.0);
        Fase fase3 = new Fase(3, nc3, ep3);
        fase3.setCerrada(true);
        dsoo.agregarFase(fase3);

        Curso cursoBuscado = gestor.buscarCurso("Desarrollo de Software Orientado a Objetos");

        if (cursoBuscado != null) {
            System.out.println("Curso: " + cursoBuscado.getNombre());
            System.out.println("---------------------------------------------------------");

            for (Fase f : cursoBuscado.getListaFases()) {
                System.out.printf("Fase %d -> Continua: %.2f | Parcial: %.2f | Aporte Final: %.2f%n",
                        f.getNumeroFase(),
                        f.getContinua().getNota(),
                        f.getExamen().getNota(),
                        f.calcularAporteTotal());
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