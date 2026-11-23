package pruebas;

import negocio.RedSocial;

/**
 * Suite de pruebas automatizadas sin dependencias externas (sin JUnit).
 * Ejecuta los 18 casos heredados de Fase 1 (CP01-CP18) y las validaciones de Fase 2.
 */
public class RedSocialTest {
    private static int pasadas = 0;
    private static int fallidas = 0;

    private static void verificar(String idYDescripcion, boolean condicion) {
        if (condicion) {
            pasadas++;
            System.out.println("[OK]    " + idYDescripcion);
        } else {
            fallidas++;
            System.out.println("[FALLO] " + idYDescripcion);
        }
    }

    private static RedSocial crearRedFase1Grupo4() {
        RedSocial red = new RedSocial();
        red.registrarPersona("P01", "Ana", "A");
        red.registrarPersona("P02", "Bruno", "B");
        red.registrarPersona("P03", "Carla", "C");
        red.registrarPersona("P04", "Diego", "D");
        red.registrarPersona("P05", "Elena", "E");
        red.proponerAmistad("P01", "P02");
        red.proponerAmistad("P01", "P03");
        red.proponerAmistad("P02", "P04");
        red.proponerAmistad("P03", "P04");
        return red;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS AUTOMATIZADAS: FASE 1 (CP01-CP18) + FASE 2 ===\n");

        RedSocial g4 = crearRedFase1Grupo4();

        // -------- BLOQUE 1: 18 CASOS DE PRUEBA DE LA FASE 1 --------
        verificar("CP01: existeRelacion(P01,P02) == true [R3, Q1]",
                Boolean.TRUE.equals(g4.existeRelacion("P01", "P02")));
        verificar("CP02: existeRelacion(P01,P04) == false [Q1]",
                Boolean.FALSE.equals(g4.existeRelacion("P01", "P04")));
        String vP01 = g4.vecinos("P01");
        verificar("CP03: vecinos(P01) contiene P02 y P03 [Q2]",
                vP01 != null && vP01.contains("P02") && vP01.contains("P03") && g4.grado("P01") == 2);
        verificar("CP04: vecinos(P05) es conjunto vacío [] [Q2]",
                "[]".equals(g4.vecinos("P05")));
        verificar("CP05: grado(P05) == 0 [Q3]", g4.grado("P05") == 0);
        verificar("CP06: grado(P04) == 2 [Q3]", g4.grado("P04") == 2);
        verificar("CP07: existeRelacion(P02,P01) == true por reciprocidad [R3, Q1]",
                Boolean.TRUE.equals(g4.existeRelacion("P02", "P01")));
        verificar("CP08: registrarPersona(P01, Ana) rechaza duplicado [R6]",
                !g4.registrarPersona("P01", "Ana"));
        verificar("CP09: registrarPersona(P06, \"\") rechaza nombre vacío [R7]",
                !g4.registrarPersona("P06", ""));
        verificar("CP10: proponerAmistad(P01,P01) rechaza lazo [R2]",
                !g4.proponerAmistad("P01", "P01"));
        verificar("CP11: proponerAmistad(P02,P01) rechaza duplicado de e1 [R4]",
                !g4.proponerAmistad("P02", "P01"));
        verificar("CP12: proponerAmistad(P01,P99) rechaza extremo inexistente [R1]",
                !g4.proponerAmistad("P01", "P99"));
        verificar("CP13: existeRelacion(P99,P01) devuelve null por ID inexistente [Q4]",
                g4.existeRelacion("P99", "P01") == null);
        verificar("CP14: vecinos(P99) devuelve null por ID inexistente [Q4]",
                g4.vecinos("P99") == null);
        verificar("CP15: número de vértices == 5 (incluida P05 aislada) [M1]",
                g4.contarVertices() == 5);
        verificar("CP16: número de amistades == 4 aristas únicas [M2]",
                g4.contarAmistades() == 4);

        String[] ids = {"P01", "P02", "P03", "P04", "P05"};
        int[][] adj = g4.construirMatrizAdyacencia(ids);
        boolean cp17Ok = (adj.length == 5 && adj[0].length == 5);
        int[] gradosEsperados = {2, 2, 2, 2, 0};
        for (int i = 0; i < 5 && cp17Ok; i++) {
            if (adj[i][i] != 0) cp17Ok = false;
            int sumaFila = 0;
            for (int j = 0; j < 5; j++) {
                if (adj[i][j] != adj[j][i]) cp17Ok = false;
                sumaFila += adj[i][j];
            }
            if (sumaFila != gradosEsperados[i]) cp17Ok = false;
        }
        verificar("CP17: matriz adyacencia 5x5 simétrica, diag 0, grados [2,2,2,2,0] [M3]", cp17Ok);

        int[][] inc = g4.construirMatrizIncidencia(ids);
        boolean cp18Ok = (inc.length == 5 && inc[0].length == 4);
        for (int c = 0; c < 4 && cp18Ok; c++) {
            int sumaCol = 0;
            for (int f = 0; f < 5; f++) {
                sumaCol += inc[f][c];
            }
            if (sumaCol != 2) cp18Ok = false;
            if (inc[4][c] != 0) cp18Ok = false;
        }
        verificar("CP18: matriz incidencia 5x4, columnas suman 2, fila P05 en cero [M4]", cp18Ok);

        // -------- BLOQUE 2: OPERACIONES DINÁMICAS DE LA FASE 2 --------
        verificar("F2-01: normalización rechaza p01 en minúsculas",
                !g4.registrarPersona("p01", "Ana"));
        verificar("F2-02: rechaza usuario con espacios o comas",
                !g4.registrarPersona("P 06", "Luis") && !g4.registrarPersona("P06,1", "Luis"));
        verificar("F2-03: seguir(P02, P01) y seguir(P05, P01) dirigidos",
                g4.seguir("P02", "P01") && g4.seguir("P05", "P01") && !g4.seguir("P02", "P01"));
        verificar("F2-04: sigueA(P02, P01) true y sigueA(P01, P02) false (dirigida)",
                g4.sigueA("P02", "P01") && !g4.sigueA("P01", "P02"));
        String seguidoresAna = g4.listarSeguidores("p01");
        verificar("F2-05: listarSeguidores(p01) incluye Bruno y Elena",
                seguidoresAna != null && seguidoresAna.contains("Bruno") && seguidoresAna.contains("Elena"));
        verificar("F2-06: dejarDeSeguir(P05, P01) elimina solo la arista SEGUIR",
                g4.dejarDeSeguir("P05", "P01") && "Ninguno".equals(g4.listarSeguidos("P05")));
        verificar("F2-07: eliminarAmistad(P03, P04) quita ambas aristas",
                g4.eliminarAmistad("P03", "P04") && !g4.sonAmigos("P04", "P03"));
        verificar("F2-08: eliminarPersona(P01) borra vértice y aristas entrantes (AMISTAD y SEGUIR)",
                g4.eliminarPersona("P01")
                && g4.contarAmigos("P01") == -1
                && !g4.listarAmigos("P02").contains("Ana")
                && "Ninguno".equals(g4.listarSeguidos("P02"))
                && g4.contarVertices() == 4);

        System.out.println("\n========================================================");
        System.out.println("RESUMEN: " + pasadas + " pruebas superadas, " + fallidas + " fallidas.");
        System.out.println("========================================================");
        if (fallidas > 0) {
            System.exit(1);
        }
    }
}
