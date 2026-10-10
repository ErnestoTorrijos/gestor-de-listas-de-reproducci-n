/**
 * Pruebas reproducibles de ListaReproduccion (sin librerías externas).
 * Cada prueba imprime: estado inicial, operación, resultado esperado y obtenido,
 * y además verifica la cantidad de elementos y la consistencia de los enlaces.
 */
public class PruebasLista {

    private static int total = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        listaVacia();
        primeraCancion();
        navegacionUnNodo();
        insercionesInicioYFinal();
        recorridoCincoCanciones();
        avanceDesdeUltima();
        retrocesoDesdePrimera();
        eliminarPrimero();
        eliminarUltimo();
        eliminarIntermedio();
        eliminarActualIntermedia();
        eliminarActualQueEsUltima();
        eliminarUnicaCancion();
        idInexistente();
        reproduccionMayorQueTamanio();
        entradasInvalidas();

        System.out.println("RESULTADO FINAL: " + (total - fallidas) + "/" + total + " pruebas exitosas.");
        if (fallidas > 0) System.exit(1);
    }

    // ------------------------- Pruebas -------------------------

    private static void listaVacia() {
        ListaReproduccion l = new ListaReproduccion();
        String ini = l.resumen();
        String obt = "avanzar=" + l.avanzar() + ", retroceder=" + l.retroceder()
                + ", actual=" + l.consultarActual() + ", elimActual=" + l.eliminarActual()
                + ", buscar=" + l.buscar(1) + ", elimId=" + l.eliminarPorId(1)
                + ", seleccionar=" + l.seleccionar(1) + ", reproducir=" + l.reproducir(3)
                + ", n=" + l.getTamanio();
        registrar("Operaciones sobre lista vacía", ini,
                "avanzar, retroceder, actual, eliminar, buscar, seleccionar, reproducir(3)",
                "avanzar=null, retroceder=null, actual=null, elimActual=null, buscar=null, "
                        + "elimId=null, seleccionar=false, reproducir=null, n=0",
                obt, l);
    }

    private static void primeraCancion() {
        ListaReproduccion l = new ListaReproduccion();
        String ini = l.resumen();
        l.agregarAlFinal("A", "X", 10);
        registrar("Inserción de la primera canción", ini, "agregarAlFinal(A)",
                "adelante=[1] atrás=[1] | actual=1 | n=1", l.resumen(), l);
    }

    private static void navegacionUnNodo() {
        ListaReproduccion l = new ListaReproduccion();
        l.agregarAlFinal("A", "X", 10);
        String ini = l.resumen();
        String obt = "avanzar=" + l.avanzar().getId() + ", retroceder=" + l.retroceder().getId()
                + " | " + l.resumen();
        registrar("Navegación en ambos sentidos con un solo nodo", ini, "avanzar y luego retroceder",
                "avanzar=1, retroceder=1 | adelante=[1] atrás=[1] | actual=1 | n=1", obt, l);
    }

    private static void insercionesInicioYFinal() {
        ListaReproduccion l = new ListaReproduccion();
        String ini = l.resumen();
        l.agregarAlFinal("A", "X", 10);   // id 1  -> [1]
        l.agregarAlInicio("B", "X", 10);  // id 2  -> [2,1]
        l.agregarAlFinal("C", "X", 10);   // id 3  -> [2,1,3]
        l.agregarAlInicio("D", "X", 10);  // id 4  -> [4,2,1,3]
        registrar("Inserciones al inicio y al final (la actual no cambia)", ini,
                "final(A), inicio(B), final(C), inicio(D)",
                "adelante=[4, 2, 1, 3] atrás=[3, 1, 2, 4] | actual=1 | n=4", l.resumen(), l);
    }

    private static void recorridoCincoCanciones() {
        ListaReproduccion l = listaConCinco();
        String ini = l.resumen();
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i > 0) ids.append(",");
            ids.append(l.avanzar().getId());
        }
        registrar("Recorrido con 5 canciones (una vuelta completa)", ini, "avanzar x5",
                "2,3,4,5,1 | " + ini, ids + " | " + l.resumen(), l);
    }

    private static void avanceDesdeUltima() {
        ListaReproduccion l = listaConCinco();
        l.seleccionar(5);
        String ini = l.resumen();
        l.avanzar();
        registrar("Avance desde la última canción hacia la primera", ini, "avanzar",
                "adelante=[1, 2, 3, 4, 5] atrás=[5, 4, 3, 2, 1] | actual=1 | n=5", l.resumen(), l);
    }

    private static void retrocesoDesdePrimera() {
        ListaReproduccion l = listaConCinco();
        String ini = l.resumen();
        l.retroceder();
        registrar("Retroceso desde la primera canción hacia la última", ini, "retroceder",
                "adelante=[1, 2, 3, 4, 5] atrás=[5, 4, 3, 2, 1] | actual=5 | n=5", l.resumen(), l);
    }

    private static void eliminarPrimero() {
        ListaReproduccion l = listaConCinco();
        l.seleccionar(3);
        String ini = l.resumen();
        l.eliminarPorId(1);
        registrar("Eliminar el primer nodo (la actual se conserva)", ini, "eliminarPorId(1)",
                "adelante=[2, 3, 4, 5] atrás=[5, 4, 3, 2] | actual=3 | n=4", l.resumen(), l);
    }

    private static void eliminarUltimo() {
        ListaReproduccion l = listaConCinco();
        l.seleccionar(3);
        String ini = l.resumen();
        l.eliminarPorId(5);
        registrar("Eliminar el último nodo (la actual se conserva)", ini, "eliminarPorId(5)",
                "adelante=[1, 2, 3, 4] atrás=[4, 3, 2, 1] | actual=3 | n=4", l.resumen(), l);
    }

    private static void eliminarIntermedio() {
        ListaReproduccion l = listaConCinco();
        l.seleccionar(3);
        String ini = l.resumen();
        l.eliminarPorId(2);
        registrar("Eliminar un nodo intermedio (la actual se conserva)", ini, "eliminarPorId(2)",
                "adelante=[1, 3, 4, 5] atrás=[5, 4, 3, 1] | actual=3 | n=4", l.resumen(), l);
    }

    private static void eliminarActualIntermedia() {
        ListaReproduccion l = listaConCinco();
        l.seleccionar(3);
        String ini = l.resumen();
        l.eliminarActual();
        registrar("Eliminar la canción actual (la nueva actual es la siguiente)", ini, "eliminarActual",
                "adelante=[1, 2, 4, 5] atrás=[5, 4, 2, 1] | actual=4 | n=4", l.resumen(), l);
    }

    private static void eliminarActualQueEsUltima() {
        ListaReproduccion l = listaConCinco();
        l.seleccionar(5);
        String ini = l.resumen();
        l.eliminarActual();
        registrar("Eliminar la actual cuando es la última (la siguiente es la primera)", ini, "eliminarActual",
                "adelante=[1, 2, 3, 4] atrás=[4, 3, 2, 1] | actual=1 | n=4", l.resumen(), l);
    }

    private static void eliminarUnicaCancion() {
        ListaReproduccion l = new ListaReproduccion();
        l.agregarAlFinal("A", "X", 10);
        String ini = l.resumen();
        l.eliminarActual();
        registrar("Eliminar la única canción", ini, "eliminarActual",
                "vacía | actual=null | n=0", l.resumen(), l);
    }

    private static void idInexistente() {
        ListaReproduccion l = listaConCinco();
        String ini = l.resumen();
        String obt = "buscar=" + l.buscar(99) + ", eliminar=" + l.eliminarPorId(99)
                + ", seleccionar=" + l.seleccionar(99) + " | " + l.resumen();
        registrar("Búsqueda, selección y eliminación de un id inexistente", ini,
                "buscar(99), eliminarPorId(99), seleccionar(99)",
                "buscar=null, eliminar=null, seleccionar=false | " + ini, obt, l);
    }

    private static void reproduccionMayorQueTamanio() {
        ListaReproduccion l = new ListaReproduccion();
        l.agregarAlFinal("A", "X", 10);
        l.agregarAlFinal("B", "X", 10);
        l.agregarAlFinal("C", "X", 10);
        String ini = l.resumen();
        String salida = l.reproducir(7);
        System.out.println("  Salida de reproducir(7):");
        for (String linea : salida.split("\n")) System.out.println("    " + linea);
        int lineas = salida.split("\n").length;
        registrar("Reproducción simulada con k=7 > n=3 (más de una vuelta)", ini, "reproducir(7)",
                "lineas=7 | adelante=[1, 2, 3] atrás=[3, 2, 1] | actual=2 | n=3",
                "lineas=" + lineas + " | " + l.resumen(), l);
    }

    private static void entradasInvalidas() {
        ListaReproduccion l = listaConCinco();
        String ini = l.resumen();
        String d0 = intentar(() -> l.agregarAlFinal("T", "A", 0));
        String neg = intentar(() -> l.agregarAlInicio("T", "A", -5));
        String tit = intentar(() -> l.agregarAlFinal("   ", "A", 10));
        String art = intentar(() -> l.agregarAlFinal("T", "", 10));
        String k0 = intentar(() -> l.reproducir(0));
        l.agregarAlFinal("Válida", "A", 10);   // el id debe ser 6: los fallos no consumen ids
        String esperadoEx = "IllegalArgumentException";
        registrar("Entradas inválidas (no modifican la lista ni consumen ids)", ini,
                "duración 0, duración -5, título vacío, artista vacío, k=0, y luego una canción válida",
                String.join(", ", esperadoEx, esperadoEx, esperadoEx, esperadoEx, esperadoEx)
                        + " | adelante=[1, 2, 3, 4, 5, 6] atrás=[6, 5, 4, 3, 2, 1] | actual=1 | n=6",
                String.join(", ", d0, neg, tit, art, k0) + " | " + l.resumen(), l);
    }

    // ------------------------- Apoyo -------------------------

    private static ListaReproduccion listaConCinco() {
        ListaReproduccion l = new ListaReproduccion();
        for (int i = 1; i <= 5; i++) {
            l.agregarAlFinal("Cancion " + i, "Artista " + i, 100 + i);
        }
        return l;
    }

    private static String intentar(Runnable r) {
        try {
            r.run();
            return "sin excepción";
        } catch (IllegalArgumentException e) {
            return "IllegalArgumentException";
        }
    }

    private static void registrar(String nombre, String inicial, String operacion,
                                  String esperado, String obtenido, ListaReproduccion lista) {
        boolean enlacesOk = lista.verificarIntegridad();
        boolean ok = esperado.equals(obtenido) && enlacesOk;
        total++;
        if (!ok) fallidas++;
        System.out.println("Prueba " + total + ": " + nombre);
        System.out.println("  Estado inicial : " + inicial);
        System.out.println("  Operación      : " + operacion);
        System.out.println("  Esperado       : " + esperado);
        System.out.println("  Obtenido       : " + obtenido);
        System.out.println("  Enlaces        : " + (enlacesOk ? "consistentes" : "INCONSISTENTES"));
        System.out.println("  Resultado      : " + (ok ? "OK" : "FALLÓ"));
        System.out.println();
    }
}
