# Gestor de lista de reproducción (lista doblemente enlazada circular)

Primer parcial – Examen práctico. Estructura de datos implementada desde cero:
sin `LinkedList`, `ArrayList`, arreglos ni otras colecciones.

## Integrantes

- Aguilar Avendaño Emiliano
- Gómez Antonio Jaime David
- Torrijos Munguia Ernesto
- Vigueras Martínez Alejandro

## Versión de Java

Java 8 o superior (probado con OpenJDK 21).

## Estructura del proyecto

| Archivo                  | Responsabilidad                                              |
|--------------------------|--------------------------------------------------------------|
| `Cancion.java`           | Modelo de datos (id, título, artista, duración > 0).         |
| `NodoCancion.java`       | Nodo con referencias `anterior` y `siguiente`.               |
| `ListaReproduccion.java` | Lista doblemente enlazada circular (inicio, fin, actual, n). |
| `Main.java`              | Menú de consola (lectura, validación y mensajes).            |
| `PruebasLista.java`      | Pruebas reproducibles con verificación de enlaces.           |

## Compilación

Desde la carpeta que contiene los `.java`:

```bash
mkdir out
javac -encoding UTF-8 -d out *.java
```

## Ejecución

> En Windows (CMD/PowerShell), si los acentos o el símbolo ▶ se ven mal, ejecutar antes `chcp 65001`.

Programa (menú):

```bash
java -Dfile.encoding=UTF-8 -cp out Main
```

Pruebas:

```bash
java -Dfile.encoding=UTF-8 -cp out PruebasLista
```

## Complejidades

| Operación                                  | Complejidad |
|--------------------------------------------|-------------|
| Agregar al inicio o al final               | O(1)        |
| Consultar la canción actual                | O(1)        |
| Avanzar o retroceder                       | O(1)        |
| Eliminar la canción actual                 | O(1)        |
| Consultar la cantidad de canciones         | O(1)        |
| Buscar o seleccionar por identificador     | O(n)        |
| Eliminar por identificador                 | O(n)        |
| Mostrar todas las canciones                | O(n)        |
| Simular `k` reproducciones                 | O(k)        |
