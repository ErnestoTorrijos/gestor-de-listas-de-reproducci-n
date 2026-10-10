import java.util.Scanner;

/**
 * Menú de consola, solo se encarga de leer datos, validarlos y mostrar mensajes.
 * Toda la lógica de la estructura esta en ListaReproduccion.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final ListaReproduccion lista = new ListaReproduccion();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            ejecutar(opcion);
        } while (opcion != 13);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== GESTOR DE LISTA DE REPRODUCCION =====");
        System.out.println(" 1. Agregar canción al inicio");
        System.out.println(" 2. Agregar canción al final");
        System.out.println(" 3. Buscar canción por id");
        System.out.println(" 4. Seleccionar canción por id (hacerla actual)");
        System.out.println(" 5. Eliminar canción por id");
        System.out.println(" 6. Eliminar la canción actual");
        System.out.println(" 7. Consultar la canción actual");
        System.out.println(" 8. Avanzar a la siguiente");
        System.out.println(" 9. Retroceder a la anterior");
        System.out.println("10. Mostrar lista completa (ambos sentidos)");
        System.out.println("11. Cantidad de canciones");
        System.out.println("12. Simular reproducción de k canciones");
        System.out.println("13. Salir");
    }

    private static void ejecutar(int opcion) {
        switch (opcion) {
            case 1: agregar(true); break;
            case 2: agregar(false); break;
            case 3: buscar(); break;
            case 4: seleccionar(); break;
            case 5: eliminarPorId(); break;
            case 6: eliminarActual(); break;
            case 7: consultarActual(); break;
            case 8: mover(true); break;
            case 9: mover(false); break;
            case 10: mostrarLista(); break;
            case 11: System.out.println("Canciones en la lista: " + lista.getTamanio()); break;
            case 12: simular(); break;
            case 13: System.out.println("¡Hasta luego!"); break;
            default: System.out.println("Opción inválida. Elige un número del 1 al 13.");
        }
    }

    // ---------------- Operaciones del menú ----------------

    private static void agregar(boolean alInicio) {
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Artista: ");
        String artista = sc.nextLine();
        int duracion = leerEntero("Duración en segundos: ");
        try {
            Cancion c = alInicio
                    ? lista.agregarAlInicio(titulo, artista, duracion)
                    : lista.agregarAlFinal(titulo, artista, duracion);
            System.out.println("Canción agregada: " + c);
        } catch (IllegalArgumentException e) {
            System.out.println("No se agregó la canción: " + e.getMessage());
        }
    }

    private static void buscar() {
        if (avisarSiVacia()) return;
        int id = leerEntero("Id a buscar: ");
        Cancion c = lista.buscar(id);
        System.out.println(c == null ? "No existe una canción con id " + id + "." : "Encontrada: " + c);
    }

    private static void seleccionar() {
        if (avisarSiVacia()) return;
        int id = leerEntero("Id a seleccionar: ");
        if (lista.seleccionar(id)) {
            System.out.println("Ahora la canción actual es: " + lista.consultarActual());
        } else {
            System.out.println("No existe una canción con id " + id + ". La lista no cambió.");
        }
    }

    private static void eliminarPorId() {
        if (avisarSiVacia()) return;
        int id = leerEntero("Id a eliminar: ");
        Cancion c = lista.eliminarPorId(id);
        if (c == null) {
            System.out.println("No existe una canción con id " + id + ". La lista no cambió.");
        } else {
            System.out.println("Eliminada: " + c);
            informarActual();
        }
    }

    private static void eliminarActual() {
        if (avisarSiVacia()) return;
        Cancion c = lista.eliminarActual();
        System.out.println("Eliminada la canción actual: " + c);
        informarActual();
    }

    private static void consultarActual() {
        if (avisarSiVacia()) return;
        System.out.println("Canción actual: " + lista.consultarActual());
    }

    private static void mover(boolean adelante) {
        if (avisarSiVacia()) return;
        Cancion c = adelante ? lista.avanzar() : lista.retroceder();
        System.out.println("Canción actual: " + c);
    }

    private static void mostrarLista() {
        if (avisarSiVacia()) return;
        System.out.println("--- De la primera a la última ---");
        System.out.print(lista.listarAdelante());
        System.out.println("--- De la última a la primera ---");
        System.out.print(lista.listarAtras());
    }

    private static void simular() {
        if (avisarSiVacia()) return;
        int k;
        do {
            k = leerEntero("¿Cuántas canciones reproducir? (k > 0): ");
            if (k <= 0) System.out.println("k debe ser un entero positivo.");
        } while (k <= 0);
        System.out.print(lista.reproducir(k));
        System.out.println("Siguiente canción por reproducir: " + lista.consultarActual());
    }

    // ---------------- Utilidades ----------------

    /** Muestra un mensaje claro si la lista está vacía. Devuelve true si lo estaba. */
    private static boolean avisarSiVacia() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. Agrega canciones primero.");
            return true;
        }
        return false;
    }

    private static void informarActual() {
        if (lista.estaVacia()) {
            System.out.println("La lista quedó vacía.");
        } else {
            System.out.println("Canción actual: " + lista.consultarActual());
        }
    }

    /** Lee una línea y la convierte a entero; repite hasta que la entrada sea válida. */
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextLine()) {          // fin de la entrada (Ctrl+D / archivo)
                System.out.println("\nFin de la entrada. Saliendo.");
                System.exit(0);
            }
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: escribe un número entero.");
            }
        }
    }
}
