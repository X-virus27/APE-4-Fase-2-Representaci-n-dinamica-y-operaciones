# Acta de Recepción y Continuidad — Fase 1 → Fase 2 (Grupo 4)

- **Repositorio base (Fase 1):** https://github.com/shirleymikaela2/APE4-Fase-1-Grupo4
- **Grupo:** Grupo 4 — Tercero B, Estructura de Datos (UTA)
- **Integrantes (orden alfabético):**
  4. Oto, Cristopher

## Contratos preservados de la Fase 1
- Catálogo base de 5 personas (`P01–P05`) y 4 amistades únicas (`e1–e4`) en `modelo/personas.csv` y `modelo/amistades.csv`.
- Reglas `R1–R7`, consultas `Q1–Q4`, métricas `M1–M4` y los **18 casos de prueba (`CP01–CP18`)** automatizados en `pruebas.RedSocialTest`.

## Evolución en Fase 2
- Migración a **multilista dinámica con nodos propios** organizada en 5 paquetes: `enums`, `modelo`, `negocio`, `app` y `pruebas`.
- Nuevas operaciones dinámicas: `seguir`, `dejarDeSeguir`, `eliminarAmistad`, `eliminarPersona` (con `quitarHacia`), `listarAmigos`, `listarSeguidos`, `listarSeguidores`, `contarAmigos`, `mostrarRed` y menú interactivo (`app.Menu`).
