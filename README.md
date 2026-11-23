# APE 4 · Fase 2 · Grupo 4 — Red Social Dinámica (Multilista en Java)

**Universidad Técnica de Ambato · Carrera de Software · Estructura de Datos · Tercero B**  
**Docente:** Ing. José Caiza, Mg.  
**Repositorio base (Fase 1):** [APE4-Fase-1-Grupo4](https://github.com/shirleymikaela2/APE4-Fase-1-Grupo4)

## Integrantes (orden alfabético)
- Caguana, Josué
- Maigua, Lenin
- Moyota, Andrés
- Oto, Cristopher
- Villacís, Héctor
- Yanchatipan, Shirley

## Arquitectura en 5 paquetes (`src/`)
- `enums/TipoRelacion.java`: `AMISTAD` (recíproca) y `SEGUIR` (dirigida).
- `modelo/`: nodos propios `Persona.java`, `Vertice.java` y `Arista.java` (sin `HashMap`, `HashSet`, `ArrayList` ni `LinkedList`).
- `negocio/RedSocial.java`: multilista dinámica con operaciones de Fase 1 y Fase 2, carga desde CSV y matrices de integridad.
- `app/Main.java`: secuencia de comprobación (Parte A: Grupo 4 `P01–P05`; Parte B: Tabla 3 `A01–A06`).
- `app/Menu.java`: menú interactivo en consola con `Scanner`.
- `pruebas/RedSocialTest.java`: suite automatizada sin JUnit (`CP01–CP18` + `F2-01` a `F2-08`).

## Complejidad temporal
| Operación | Costo | Descripción |
| :--- | :--- | :--- |
| `buscar` / `registrarPersona` | `O(V)` | Búsqueda lineal en lista de vértices |
| `agregarAmistad` / `seguir` | `O(V + grado)` | Localizar vértices y validar duplicados |
| `eliminarAmistad` / `dejarDeSeguir` | `O(V + grado)` | Buscar extremos y retirar aristas |
| `eliminarPersona` | `O(V + A)` | Limpiar aristas entrantes (`quitarHacia`) y desenlazar |
| `listarAmigos` / `listarSeguidos` / `contarAmigos` | `O(V + grado)` | Buscar vértice y recorrer su lista propia |
| `listarSeguidores` / `mostrarRed` | `O(V + A)` | Recorrer todos los vértices y aristas |

## Compilación y ejecución
```bash
mkdir -p bin
javac -encoding UTF-8 -d bin src/enums/*.java src/modelo/*.java src/negocio/*.java src/app/*.java src/pruebas/*.java

# Demostración para capturas (Anexos 1 a 4)
java -cp bin app.Main

# Pruebas automatizadas (Anexo 5: CP01-CP18 + Fase 2)
java -cp bin pruebas.RedSocialTest

# Menú interactivo en consola (para pruebas en vivo)
java -cp bin app.Menu
```
