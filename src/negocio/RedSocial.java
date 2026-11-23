package negocio;

import enums.TipoRelacion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import modelo.Arista;
import modelo.Persona;
import modelo.Vertice;

/**
 * Lógica de negocio de la red social dinámica (APE 4 · Fase 2 · Grupo 4).
 * Implementada exclusivamente con multilista de nodos propios (Vertice y Arista),
 * sin HashMap, HashSet, ArrayList ni LinkedList.
 */
public class RedSocial {
    private Vertice cabeza;

    public RedSocial() {
        this.cabeza = null;
    }

    // ==================== AUXILIARES PRIVADOS ====================

    private Vertice buscar(String usuario) {
        if (usuario == null) {
            return null;
        }
        String clave = usuario.trim().toLowerCase();
        for (Vertice v = cabeza; v != null; v = v.sig) {
            if (v.persona.usuario.equals(clave)) {
                return v;
            }
        }
        return null;
    }

    private boolean existe(Vertice origen, Vertice destino, TipoRelacion tipo) {
        for (Arista a = origen.aristas; a != null; a = a.sig) {
            if (a.destino == destino && a.tipo == tipo) {
                return true;
            }
        }
        return false;
    }

    private void insertar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista a = new Arista();
        a.destino = destino;
        a.tipo = tipo;
        a.sig = origen.aristas;
        origen.aristas = a;
    }

    private boolean quitarArista(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista anterior = null;
        Arista actual = origen.aristas;
        while (actual != null) {
            if (actual.destino == destino && actual.tipo == tipo) {
                if (anterior == null) {
                    origen.aristas = actual.sig;
                } else {
                    anterior.sig = actual.sig;
                }
                return true;
            }
            anterior = actual;
            actual = actual.sig;
        }
        return false;
    }

    private void quitarHacia(Vertice origen, Vertice objetivo) {
        Arista anterior = null;
        Arista actual = origen.aristas;
        while (actual != null) {
            if (actual.destino == objetivo) {
                if (anterior == null) {
                    origen.aristas = actual.sig;
                } else {
                    anterior.sig = actual.sig;
                }
                actual = actual.sig;
            } else {
                anterior = actual;
                actual = actual.sig;
            }
        }
    }

    // ==================== OPERACIONES SOBRE PERSONAS ====================

    public boolean registrarPersona(String usuario, String nombre) {
        return registrarPersona(usuario, nombre, "");
    }

    public boolean registrarPersona(String usuario, String nombre, String simbolo) {
        if (usuario == null || nombre == null) {
            return false;
        }
        String uLimpio = usuario.trim();
        String nLimpio = nombre.trim();
        if (uLimpio.isEmpty() || nLimpio.isEmpty()) {
            return false;
        }
        if (uLimpio.contains(" ") || uLimpio.contains(",") || uLimpio.contains("\t")) {
            return false;
        }
        String clave = uLimpio.toLowerCase();
        if (buscar(clave) != null) {
            return false;
        }
        Vertice nuevo = new Vertice(new Persona(clave, nLimpio, simbolo));
        nuevo.sig = cabeza;
        cabeza = nuevo;
        return true;
    }

    public boolean eliminarPersona(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) {
            return false;
        }
        for (Vertice w = cabeza; w != null; w = w.sig) {
            quitarHacia(w, objetivo);
        }
        Vertice anterior = null;
        Vertice actual = cabeza;
        while (actual != objetivo) {
            anterior = actual;
            actual = actual.sig;
        }
        if (anterior == null) {
            cabeza = objetivo.sig;
        } else {
            anterior.sig = objetivo.sig;
        }
        return true;
    }

    // ==================== OPERACIONES SOBRE RELACIONES ====================

    public boolean agregarAmistad(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb || existe(va, vb, TipoRelacion.AMISTAD)) {
            return false;
        }
        insertar(va, vb, TipoRelacion.AMISTAD);
        insertar(vb, va, TipoRelacion.AMISTAD);
        return true;
    }

    /** Alias de contrato heredado de la Fase 1. */
    public boolean proponerAmistad(String a, String b) {
        return agregarAmistad(a, b);
    }

    public boolean seguir(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb || existe(va, vb, TipoRelacion.SEGUIR)) {
            return false;
        }
        insertar(va, vb, TipoRelacion.SEGUIR);
        return true;
    }

    public boolean eliminarAmistad(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb) {
            return false;
        }
        boolean q1 = quitarArista(va, vb, TipoRelacion.AMISTAD);
        boolean q2 = quitarArista(vb, va, TipoRelacion.AMISTAD);
        return q1 && q2;
    }

    public boolean dejarDeSeguir(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb) {
            return false;
        }
        return quitarArista(va, vb, TipoRelacion.SEGUIR);
    }

    // ==================== CONSULTAS FASE 1 Y FASE 2 ====================

    public Boolean existeRelacion(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null) {
            return null;
        }
        return existe(va, vb, TipoRelacion.AMISTAD);
    }

    public boolean sonAmigos(String a, String b) {
        Boolean rel = existeRelacion(a, b);
        return Boolean.TRUE.equals(rel);
    }

    public boolean sigueA(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null) {
            return false;
        }
        return existe(va, vb, TipoRelacion.SEGUIR);
    }

    public String vecinos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("[");
        boolean primero = true;
        for (Arista a = v.aristas; a != null; a = a.sig) {
            if (a.tipo == TipoRelacion.AMISTAD) {
                if (!primero) {
                    sb.append(", ");
                }
                sb.append(a.destino.persona.usuario.toUpperCase());
                primero = false;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public int grado(String usuario) {
        return contarAmigos(usuario);
    }

    public String listarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) {
            return null;
        }
        return concatenarRelacionesSalientes(v, TipoRelacion.AMISTAD);
    }

    public String listarSeguidos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) {
            return null;
        }
        return concatenarRelacionesSalientes(v, TipoRelacion.SEGUIR);
    }

    public String listarSeguidores(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Vertice w = cabeza; w != null; w = w.sig) {
            if (existe(w, objetivo, TipoRelacion.SEGUIR)) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(w.persona.nombre);
            }
        }
        return sb.length() == 0 ? "Ninguno" : sb.toString();
    }

    public int contarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) {
            return -1;
        }
        int contador = 0;
        for (Arista a = v.aristas; a != null; a = a.sig) {
            if (a.tipo == TipoRelacion.AMISTAD) {
                contador++;
            }
        }
        return contador;
    }

    public int contarVertices() {
        int total = 0;
        for (Vertice v = cabeza; v != null; v = v.sig) {
            total++;
        }
        return total;
    }

    public int contarAmistades() {
        int aristasAmistad = 0;
        for (Vertice v = cabeza; v != null; v = v.sig) {
            for (Arista a = v.aristas; a != null; a = a.sig) {
                if (a.tipo == TipoRelacion.AMISTAD) {
                    aristasAmistad++;
                }
            }
        }
        return aristasAmistad / 2;
    }

    public int[][] construirMatrizAdyacencia(String[] ordenIds) {
        int n = ordenIds.length;
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Boolean rel = existeRelacion(ordenIds[i], ordenIds[j]);
                m[i][j] = (rel != null && rel) ? 1 : 0;
            }
        }
        return m;
    }

    public int[][] construirMatrizIncidencia(String[] ordenIds) {
        int n = ordenIds.length;
        int totalAristas = contarAmistades();
        int[][] inc = new int[n][totalAristas];
        int col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Boolean rel = existeRelacion(ordenIds[i], ordenIds[j]);
                if (rel != null && rel && col < totalAristas) {
                    inc[i][col] = 1;
                    inc[j][col] = 1;
                    col++;
                }
            }
        }
        return inc;
    }

    public void cargarDesdeCsv(String rutaPersonas, String rutaAmistades) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaPersonas))) {
            String linea = br.readLine();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String simbolo = partes.length >= 3 ? partes[2].trim() : "";
                    registrarPersona(partes[0].trim(), partes[1].trim(), simbolo);
                }
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(rutaAmistades))) {
            String linea = br.readLine();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    agregarAmistad(partes[1].trim(), partes[2].trim());
                }
            }
        }
    }

    public String mostrarRed() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADO ACTUAL DE LA RED SOCIAL ===\n");
        if (cabeza == null) {
            sb.append("(Red vacía)\n");
            return sb.toString();
        }
        for (Vertice v = cabeza; v != null; v = v.sig) {
            sb.append("[").append(v.persona.usuario.toUpperCase())
              .append(" - ").append(v.persona.nombre).append("] ")
              .append("Amigos (").append(contarAmigosVertice(v)).append("): ")
              .append(concatenarRelacionesSalientes(v, TipoRelacion.AMISTAD))
              .append(" | Sigue a: ")
              .append(concatenarRelacionesSalientes(v, TipoRelacion.SEGUIR))
              .append("\n");
        }
        sb.append("Total vértices: ").append(contarVertices())
          .append(" | Total amistades: ").append(contarAmistades()).append("\n");
        return sb.toString();
    }

    private String concatenarRelacionesSalientes(Vertice v, TipoRelacion tipo) {
        StringBuilder sb = new StringBuilder();
        for (Arista a = v.aristas; a != null; a = a.sig) {
            if (a.tipo == tipo) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(a.destino.persona.nombre);
            }
        }
        return sb.length() == 0 ? "Ninguno" : sb.toString();
    }

    private int contarAmigosVertice(Vertice v) {
        int c = 0;
        for (Arista a = v.aristas; a != null; a = a.sig) {
            if (a.tipo == TipoRelacion.AMISTAD) {
                c++;
            }
        }
        return c;
    }
}
