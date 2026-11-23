# Contrato de Operaciones — APE 4 · Fase 2 (Grupo 4)

## Estructura de datos
- **Multilista dinámica con nodos propios** (`modelo.Vertice` y `modelo.Arista`), sin `HashMap`, `HashSet`, `ArrayList` ni `LinkedList`.
- **`AMISTAD`** (`enums.TipoRelacion.AMISTAD`): relación recíproca almacenada como dos aristas dirigidas internas (`u -> v` y `v -> u`).
- **`SEGUIR`** (`enums.TipoRelacion.SEGUIR`): relación dirigida almacenada como una sola arista (`u -> v`).

## Reglas de validación y normalización
- Todos los identificadores se normalizan con `trim().toLowerCase()` (`P01` y `p01` corresponden al mismo usuario).
- `registrarPersona(u, n)` rechaza valores nulos, vacíos, usuarios con espacios o comas, o identificadores duplicados (`R6`, `R7`).
- `agregarAmistad(a, b)` / `proponerAmistad(a, b)` y `seguir(a, b)` rechazan extremos inexistentes (`R1`), lazos (`R2`) y duplicados del mismo tipo (`R4`).
- `eliminarPersona(u)` recorre todos los vértices e invoca `quitarHacia(w, objetivo)` antes de desenlazar el vértice objetivo (`O(V + A)`).
- `listarAmigos`, `listarSeguidos`, `listarSeguidores`, `vecinos` y `existeRelacion` devuelven `null` si el usuario no existe (`Q4`); `contarAmigos` y `grado` devuelven `-1`.
