package modelo;

/**
 * Entidad Persona con identificador normalizado, nombre y símbolo de Fase 1.
 */
public class Persona {
    public String usuario;
    public String nombre;
    public String simbolo;

    public Persona(String usuario, String nombre) {
        this(usuario, nombre, "");
    }

    public Persona(String usuario, String nombre, String simbolo) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.simbolo = simbolo == null ? "" : simbolo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return nombre + " (" + usuario.toUpperCase() + ")";
    }
}
