package app;

import java.io.IOException;
import negocio.RedSocial;

/**
 * Ejecuta la demostración completa organizada para los Anexos 1 a 4:
 * - Parte A: Continuidad desde los CSV de la Fase 1 del Grupo 4 (P01–P05, e1–e4).
 * - Parte B: Secuencia de comprobación de 8 pasos de la Tabla 3 (A01–A06).
 */
public class Main {
    public static void main(String[] args) {
        ejecutarContinuidadGrupo4();
        ejecutarSecuenciaGuiaDocente();
    }

    private static void ejecutarContinuidadGrupo4() {
        System.out.println("############################################################");
        System.out.println("PARTE A: CONTINUIDAD FASE 1 -> FASE 2 (GRUPO 4: P01-P05)");
        System.out.println("############################################################");

        RedSocial red = new RedSocial();
        try {
            red.cargarDesdeCsv("modelo/personas.csv", "modelo/amistades.csv");
            System.out.println("Datos cargados desde modelo/personas.csv y modelo/amistades.csv.");
        } catch (IOException e) {
            System.out.println("Cargando catálogo base de Grupo 4 en memoria...");
            red.registrarPersona("P01", "Ana", "A");
            red.registrarPersona("P02", "Bruno", "B");
            red.registrarPersona("P03", "Carla", "C");
            red.registrarPersona("P04", "Diego", "D");
            red.registrarPersona("P05", "Elena", "E");
            red.proponerAmistad("P01", "P02");
            red.proponerAmistad("P01", "P03");
            red.proponerAmistad("P02", "P04");
            red.proponerAmistad("P03", "P04");
        }

        System.out.println("\n--- ANEXO 1: registrarPersona y mostrarRed (Base Fase 1) ---");
        System.out.println(red.mostrarRed());

        System.out.println("--- ANEXO 2: Amistades y seguimientos (SEGUIR dirigido) ---");
        System.out.println("seguir(P02, P01) [Bruno -> Ana]:   " + red.seguir("P02", "P01"));
        System.out.println("seguir(P05, P01) [Elena -> Ana]:   " + red.seguir("P05", "P01"));
        System.out.println("Seguidores de Ana (P01):           " + red.listarSeguidores("P01"));
        System.out.println("Seguidos por Elena (P05):          " + red.listarSeguidos("P05"));
        System.out.println("Amigos de Elena (P05):             " + red.listarAmigos("P05"));

        System.out.println("\n--- ANEXO 4: listarAmigos, listarSeguidos, listarSeguidores y contarAmigos ---");
        System.out.println("listarAmigos(P01) [Ana]:           " + red.listarAmigos("P01"));
        System.out.println("vecinos(P01) [contrato Fase 1]:    " + red.vecinos("P01"));
        System.out.println("listarSeguidos(P02) [Bruno]:       " + red.listarSeguidos("P02"));
        System.out.println("listarSeguidores(P01) [Ana]:       " + red.listarSeguidores("P01"));
        System.out.println("contarAmigos(P04) [Diego]:         " + red.contarAmigos("P04"));
        System.out.println("contarAmigos(P05) [Elena]:         " + red.contarAmigos("P05"));
        System.out.println("contarAmigos(P99) [Inexistente]:   " + red.contarAmigos("P99"));
        System.out.println("listarAmigos(P99) [Inexistente]:   " + red.listarAmigos("P99"));

        System.out.println("\n--- ANEXO 3: eliminarAmistad, dejarDeSeguir y eliminarPersona ---");
        System.out.println("eliminarAmistad(P03, P04):         " + red.eliminarAmistad("P03", "P04"));
        System.out.println("dejarDeSeguir(P05, P01):           " + red.dejarDeSeguir("P05", "P01"));
        System.out.println("eliminarPersona(P01) [Ana]:        " + red.eliminarPersona("P01"));
        System.out.println(red.mostrarRed());
    }

    private static void ejecutarSecuenciaGuiaDocente() {
        System.out.println("############################################################");
        System.out.println("PARTE B: SECUENCIA DE COMPROBACIÓN TABLA 3 (A01-A06)");
        System.out.println("############################################################");

        RedSocial red = new RedSocial();
        red.registrarPersona("A01", "Sofía");
        red.registrarPersona("A02", "Mateo");
        red.registrarPersona("A03", "Valeria");
        red.registrarPersona("A04", "Andrés");
        red.registrarPersona("A05", "Camila");
        red.registrarPersona("A06", "Diego");
        red.agregarAmistad("A01", "A02");
        red.agregarAmistad("A02", "A03");
        red.agregarAmistad("A03", "A04");
        red.agregarAmistad("A04", "A01");
        red.agregarAmistad("A04", "A05");

        System.out.println("Paso 1 - Grados (A01..A06): "
                + red.contarAmigos("A01") + "," + red.contarAmigos("A02") + ","
                + red.contarAmigos("A03") + "," + red.contarAmigos("A04") + ","
                + red.contarAmigos("A05") + "," + red.contarAmigos("A06"));
        System.out.println("Paso 2 - seguir(A02, A01): " + red.seguir("A02", "A01")
                + " | Seguidores A01: " + red.listarSeguidores("A01"));
        System.out.println("Paso 3 - seguir(A06, A01): " + red.seguir("A06", "A01")
                + " | Seguidos A06: " + red.listarSeguidos("A06")
                + " | Amigos A06: " + red.contarAmigos("A06"));
        System.out.println("Paso 4 - listarAmigos(A04): " + red.listarAmigos("A04"));
        System.out.println("Paso 5 - eliminarAmistad(A04, A05): " + red.eliminarAmistad("A04", "A05")
                + " | Amigos A04: " + red.listarAmigos("A04")
                + " | Amigos A05: " + red.listarAmigos("A05"));
        System.out.println("Paso 6 - eliminarPersona(A01): " + red.eliminarPersona("A01"));
        System.out.println(red.mostrarRed());
        System.out.println("Paso 7 - registrarPersona(A02, Otro): " + red.registrarPersona("A02", "Otro"));
        System.out.println("Paso 8 - agregarAmistad(A03, A03):    " + red.agregarAmistad("A03", "A03"));
    }
}
