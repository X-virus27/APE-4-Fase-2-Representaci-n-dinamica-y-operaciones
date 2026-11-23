package modelo;

import enums.TipoRelacion;

/**
 * Nodo propio para la lista enlazada de aristas salientes de un vértice.
 */
public class Arista {
    public Vertice destino;
    public TipoRelacion tipo;
    public Arista sig;

    public Arista() {
    }

    public Arista(Vertice destino, TipoRelacion tipo, Arista sig) {
        this.destino = destino;
        this.tipo = tipo;
        this.sig = sig;
    }
}
