package modelo;

/**
 * Nodo propio para la lista enlazada de vértices (personas) de la multilista.
 */
public class Vertice {
    public Persona persona;
    public Arista aristas;
    public Vertice sig;

    public Vertice(Persona persona) {
        this.persona = persona;
        this.aristas = null;
        this.sig = null;
    }
}
