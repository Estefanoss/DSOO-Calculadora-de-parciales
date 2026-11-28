public class ConstruirTextoAnalisis {

    public static String generar(Curso curso, GestorAcademico gestor) {
        if (curso == null) {
            return "Agrega un curso y sus fases para ver el resumen dinámico en tiempo real.";
        }

        double acumulado = curso.calcularPromedioPonderadoActual();
        double pesoReg = curso.calcularPesoRegistrado();
        double pesoRest = curso.calcularPesoRestante();
        double faltante = curso.calcularPuntosFaltantes();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CURSO: %s (Créditos: %d)%n", curso.getNombre().toUpperCase(), curso.getCreditos()));
        sb.append(String.format("Acumulado: %.2f | Peso reg.: %.0f%% | Peso rest.: %.0f%%%n%n",
                acumulado, pesoReg * 100, pesoRest * 100));

        for (Fase f : curso.getListaFases()) {
            sb.append(String.format("Fase %d -> Cont (%.0f%%): %.2f [%.2f] | Ex %d (%.0f%%): %.2f [%.2f]%n",
                    f.getNumeroFase(),
                    f.getContinua().getPeso() * 100, f.getContinua().getNota(), f.getContinua().calcularAporte(),
                    f.getExamen().getNumeroParcial(), f.getExamen().getPeso() * 100, f.getExamen().getNota(), f.getExamen().calcularAporte()
            ));
        }

        sb.append("\n");
        if (faltante <= 0.0) {
            sb.append("ESTADO: APROBADO (superaste el mínimo de 10.5).\n");
        } else {
            sb.append(String.format("Faltan %.2f pts ponderados para llegar a 10.5.%n", faltante));
            if (pesoRest > 0.0) {
                double necesaria = faltante / pesoRest;
                sb.append(String.format("Nota promedio necesaria en peso restante: %.2f%s%n",
                        necesaria, necesaria > 20.0 ? " (Supera 20.00)" : ""));
            }
        }

        sb.append("Sustitutorio (solo pre-Fase 3, menor de Parcial 1 o 2): ")
          .append(curso.puedeRendirSustitutorio() ? "DISPONIBLE" : "NO DISPONIBLE").append("\n");

        if (curso.getSustitutorio() != null && curso.getSustitutorio().isAplicado()) {
            ExamenParcial rep = curso.getSustitutorio().getExamenReemplazado();
            sb.append(String.format("Sustitutorio aplicado en %s: %.2f -> %.2f%n",
                    rep.getEtiqueta(), rep.getNotaOriginal(), rep.getNota()));
        }

        sb.append(String.format("PPS del ciclo: %.2f%n", gestor.calcularPromedioPonderadoSemestral()));
        return sb.toString();
    }
}