package app;

import java.io.IOException;
import java.util.Scanner;
import negocio.RedSocial;

/**
 * Menú interactivo por consola (usando únicamente Scanner) para probar en vivo
 * todas las operaciones de la red social dinámica durante la práctica/defensa.
 */
public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RedSocial red = new RedSocial();

        // Carga inicial opcional de los datos de Fase 1 (Grupo 4)
        try {
            red.cargarDesdeCsv("modelo/personas.csv", "modelo/amistades.csv");
            System.out.println("Catálogo base de Fase 1 (P01-P05) cargado desde CSV.");
        } catch (IOException e) {
            System.out.println("Red inicializada vacía (puede usar opción 9 para cargar P01-P05).");
        }

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n========== MENÚ CONEXIÓN AMBATO · GRUPO 4 (FASE 2) ==========");
            System.out.println("1. Registrar persona");
            System.out.println("2. Eliminar persona");
            System.out.println("3. Agregar amistad (recíproca)");
            System.out.println("4. Eliminar amistad");
            System.out.println("5. Seguir usuario (dirigida)");
            System.out.println("6. Dejar de seguir");
            System.out.println("7. Consultar persona (amigos, seguidos, seguidores y grado)");
            System.out.println("8. Mostrar toda la red");
            System.out.println("9. Cargar red base Fase 1 (P01-P05) en memoria");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            String entrada = sc.nextLine().trim();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un número válido (0-9).");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("ID de usuario (sin espacios ni comas, ej. P06): ");
                    String u = sc.nextLine();
                    System.out.print("Nombre: ");
                    String n = sc.nextLine();
                    System.out.println(red.registrarPersona(u, n)
                            ? "Usuario registrado correctamente."
                            : "No se pudo registrar (ID duplicado, vacío, con espacios/comas o nombre vacío).");
                    break;
                case 2:
                    System.out.print("ID del usuario a eliminar: ");
                    String uElim = sc.nextLine();
                    System.out.println(red.eliminarPersona(uElim)
                            ? "Usuario eliminado junto con todas sus aristas entrantes."
                            : "Usuario no encontrado.");
                    break;
                case 3:
                    System.out.print("ID usuario A: ");
                    String a1 = sc.nextLine();
                    System.out.print("ID usuario B: ");
                    String b1 = sc.nextLine();
                    System.out.println(red.agregarAmistad(a1, b1)
                            ? "Amistad recíproca creada."
                            : "No se pudo crear (usuario inexistente, lazo o amistad duplicada).");
                    break;
                case 4:
                    System.out.print("ID usuario A: ");
                    String a2 = sc.nextLine();
                    System.out.print("ID usuario B: ");
                    String b2 = sc.nextLine();
                    System.out.println(red.eliminarAmistad(a2, b2)
                            ? "Amistad eliminada en ambos sentidos."
                            : "No existe esa amistad o algún usuario no existe.");
                    break;
                case 5:
                    System.out.print("ID seguidor: ");
                    String seg1 = sc.nextLine();
                    System.out.print("ID seguido: ");
                    String sed1 = sc.nextLine();
                    System.out.println(red.seguir(seg1, sed1)
                            ? "Relación SEGUIR registrada."
                            : "No se pudo registrar (usuario inexistente, lazo o ya lo sigue).");
                    break;
                case 6:
                    System.out.print("ID seguidor: ");
                    String seg2 = sc.nextLine();
                    System.out.print("ID seguido: ");
                    String sed2 = sc.nextLine();
                    System.out.println(red.dejarDeSeguir(seg2, sed2)
                            ? "Se dejó de seguir correctamente."
                            : "No existía esa relación SEGUIR.");
                    break;
                case 7:
                    System.out.print("ID del usuario a consultar: ");
                    String uCons = sc.nextLine();
                    int grado = red.contarAmigos(uCons);
                    if (grado == -1) {
                        System.out.println("Usuario no encontrado (amigos=null, grado=-1).");
                    } else {
                        System.out.println("Amigos (" + grado + "): " + red.listarAmigos(uCons));
                        System.out.println("Vecinos (IDs):     " + red.vecinos(uCons));
                        System.out.println("Seguidos:          " + red.listarSeguidos(uCons));
                        System.out.println("Seguidores:        " + red.listarSeguidores(uCons));
                    }
                    break;
                case 8:
                    System.out.println(red.mostrarRed());
                    break;
                case 9:
                    red.registrarPersona("P01", "Ana", "A");
                    red.registrarPersona("P02", "Bruno", "B");
                    red.registrarPersona("P03", "Carla", "C");
                    red.registrarPersona("P04", "Diego", "D");
                    red.registrarPersona("P05", "Elena", "E");
                    red.proponerAmistad("P01", "P02");
                    red.proponerAmistad("P01", "P03");
                    red.proponerAmistad("P02", "P04");
                    red.proponerAmistad("P03", "P04");
                    System.out.println("Base Fase 1 (P01-P05 y e1-e4) cargada.");
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }
}
