/**
 * Lista doblemente enlazada circular de canciones (TAD lista de reproducción).
 *
 * Invariantes que SIEMPRE deben cumplirse:
 *  1. Lista vacía  -> inicio == fin == actual == null y tamanio == 0.
 *  2. Un solo nodo -> su anterior y su siguiente son él mismo.
 *  3. Lista no vacía -> fin.siguiente == inicio  e  inicio.anterior == fin.
 *  4. Para todo nodo p: p.siguiente.anterior == p  y  p.anterior.siguiente == p.
 *  5. actual pertenece a la lista.
 *
 * Esta clase NO imprime nada ni lee del teclado: eso es responsabilidad del menú.
 * Cuando una operación no se puede realizar (lista vacía, id inexistente) devuelve
 * null o false; cuando un argumento es inválido lanza IllegalArgumentException.
 */
public class ListaReproduccion {

    private NodoCancion inicio;   // primera canción
    private NodoCancion fin;      // última canción
    private NodoCancion actual;   // canción seleccionada
    private int tamanio;          // cantidad de canciones
    private int siguienteId;      // contador incremental para generar identificadores

    public ListaReproduccion() {
        inicio = null;
        fin = null;
        actual = null;
        tamanio = 0;
        siguienteId = 1;
    }

    // ------------------------------------------------------------------
    // Consultas básicas                                            O(1)
    // ------------------------------------------------------------------

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public int getTamanio() {
        return tamanio;
    }

    public Cancion consultarActual() {
        return estaVacia() ? null : actual.dato;
    }

    // ------------------------------------------------------------------
    // Inserción                                                    O(1)
    // ------------------------------------------------------------------

    public Cancion agregarAlInicio(String titulo, String artista, int duracion) {
        Cancion c = nuevaCancion(titulo, artista, duracion);
        NodoCancion nuevo = new NodoCancion(c);

        if (estaVacia()) {
            insertarPrimero(nuevo);
            return c;
        }
        // El nuevo se coloca entre fin (su anterior) e inicio (su siguiente).
        nuevo.siguiente = inicio;
        nuevo.anterior = fin;
        inicio.anterior = nuevo;
        fin.siguiente = nuevo;
        inicio = nuevo;           // la única diferencia con agregarAlFinal
        tamanio++;
        return c;
    }

    public Cancion agregarAlFinal(String titulo, String artista, int duracion) {
        Cancion c = nuevaCancion(titulo, artista, duracion);
        NodoCancion nuevo = new NodoCancion(c);

        if (estaVacia()) {
            insertarPrimero(nuevo);
            return c;
        }
        nuevo.anterior = fin;
        nuevo.siguiente = inicio;
        fin.siguiente = nuevo;
        inicio.anterior = nuevo;
        fin = nuevo;              // la única diferencia con agregarAlInicio
        tamanio++;
        return c;
    }

    /** Crea la canción. Si los datos son inválidos lanza excepción ANTES de gastar un id. */
    private Cancion nuevaCancion(String titulo, String artista, int duracion) {
        Cancion c = new Cancion(siguienteId, titulo, artista, duracion);
        siguienteId++;
        return c;
    }

    /** Caso especial: primer nodo de una lista vacía. Se apunta a sí mismo. */
    private void insertarPrimero(NodoCancion nuevo) {
        nuevo.siguiente = nuevo;
        nuevo.anterior = nuevo;
        inicio = nuevo;
        fin = nuevo;
        actual = nuevo;           // la primera canción insertada es la actual
        tamanio = 1;
    }

    // ------------------------------------------------------------------
    // Navegación                                                   O(1)
    // ------------------------------------------------------------------

    public Cancion avanzar() {
        if (estaVacia()) return null;
        actual = actual.siguiente;
        return actual.dato;
    }

    public Cancion retroceder() {
        if (estaVacia()) return null;
        actual = actual.anterior;
        return actual.dato;
    }

    // ------------------------------------------------------------------
    // Búsqueda y selección                                         O(n)
    // ------------------------------------------------------------------

    /** Localiza el nodo. Recorre como máximo 'tamanio' nodos (nunca espera null). */
    private NodoCancion buscarNodo(int id) {
        NodoCancion p = inicio;
        for (int i = 0; i < tamanio; i++) {
            if (p.dato.getId() == id) return p;
            p = p.siguiente;
        }
        return null;
    }

    public Cancion buscar(int id) {
        NodoCancion n = buscarNodo(id);
        return n == null ? null : n.dato;
    }

    public boolean seleccionar(int id) {
        NodoCancion n = buscarNodo(id);
        if (n == null) return false;
        actual = n;
        return true;
    }

    // ------------------------------------------------------------------
    // Eliminación
    // ------------------------------------------------------------------

    /** O(n): la búsqueda cuesta O(n) y desenlazar el nodo cuesta O(1). */
    public Cancion eliminarPorId(int id) {
        NodoCancion n = buscarNodo(id);
        if (n == null) return null;
        Cancion eliminada = n.dato;
        desenlazar(n);
        return eliminada;
    }

    /** O(1): ya tenemos la referencia al nodo, no hay que buscar nada. */
    public Cancion eliminarActual() {
        if (estaVacia()) return null;
        Cancion eliminada = actual.dato;
        desenlazar(actual);
        return eliminada;
    }

    /**
     * Quita el nodo n de la lista en O(1) y repara inicio, fin y actual.
     * Precondición: n pertenece a la lista.
     */
    private void desenlazar(NodoCancion n) {
        // Caso 1: único nodo -> la lista queda vacía.
        if (tamanio == 1) {
            inicio = null;
            fin = null;
            actual = null;
            tamanio = 0;
            return;
        }

        // Caso 2: hay al menos 2 nodos. Guardamos vecinos ANTES de tocar enlaces.
        NodoCancion ant = n.anterior;
        NodoCancion sig = n.siguiente;

        ant.siguiente = sig;      // saltamos a n en un sentido...
        sig.anterior = ant;       // ...y en el otro

        if (n == inicio) inicio = sig;
        if (n == fin)    fin = ant;
        if (n == actual) actual = sig;   // la nueva actual es la que seguía

        // Aislamos el nodo eliminado para que no conserve referencias a la lista.
        n.anterior = null;
        n.siguiente = null;
        tamanio--;
    }

    // ------------------------------------------------------------------
    // Recorridos y reproducción simulada
    // ------------------------------------------------------------------

    /** O(n). Recorre exactamente 'tamanio' nodos de inicio a fin. Marca la actual con ▶. */
    public String listarAdelante() {
        StringBuilder sb = new StringBuilder();
        NodoCancion p = inicio;
        for (int i = 0; i < tamanio; i++) {
            sb.append(p == actual ? " ▶ " : "   ").append(p.dato).append('\n');
            p = p.siguiente;
        }
        return sb.toString();
    }

    /** O(n). Recorre exactamente 'tamanio' nodos de fin a inicio. */
    public String listarAtras() {
        StringBuilder sb = new StringBuilder();
        NodoCancion p = fin;
        for (int i = 0; i < tamanio; i++) {
            sb.append(p == actual ? " ▶ " : "   ").append(p.dato).append('\n');
            p = p.anterior;
        }
        return sb.toString();
    }

    /**
     * O(k). Muestra la actual y avanza, exactamente k veces.
     * Al terminar, actual queda en la canción que tocaría después.
     * Devuelve null si la lista está vacía; lanza excepción si k no es positivo.
     */
    public String reproducir(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k debe ser un entero positivo.");
        }
        if (estaVacia()) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= k; i++) {
            sb.append(i).append(". ▶ ").append(actual.dato).append('\n');
            actual = actual.siguiente;
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------
    // Apoyo para las pruebas
    // ------------------------------------------------------------------

    /** Resumen del estado: ids hacia adelante, hacia atrás, actual y tamaño. */
    public String resumen() {
        if (estaVacia()) return "vacía | actual=null | n=0";
        StringBuilder adelante = new StringBuilder("[");
        NodoCancion p = inicio;
        for (int i = 0; i < tamanio; i++) {
            if (i > 0) adelante.append(", ");
            adelante.append(p.dato.getId());
            p = p.siguiente;
        }
        adelante.append("]");

        StringBuilder atras = new StringBuilder("[");
        p = fin;
        for (int i = 0; i < tamanio; i++) {
            if (i > 0) atras.append(", ");
            atras.append(p.dato.getId());
            p = p.anterior;
        }
        atras.append("]");

        return "adelante=" + adelante + " atrás=" + atras
                + " | actual=" + actual.dato.getId() + " | n=" + tamanio;
    }

    /** Comprueba todos los invariantes de la estructura. Siempre termina (recorrido acotado). */
    public boolean verificarIntegridad() {
        if (tamanio == 0) {
            return inicio == null && fin == null && actual == null;
        }
        if (inicio == null || fin == null || actual == null) return false;
        if (fin.siguiente != inicio || inicio.anterior != fin) return false;

        boolean actualPertenece = false;
        NodoCancion p = inicio;
        for (int i = 0; i < tamanio; i++) {
            if (p == null || p.siguiente == null || p.anterior == null) return false;
            if (p.siguiente.anterior != p || p.anterior.siguiente != p) return false;
            if (p == actual) actualPertenece = true;
            if (i == tamanio - 1 && p != fin) return false;   // el último visitado debe ser fin
            p = p.siguiente;
        }
        return actualPertenece && p == inicio;                // tras n pasos regresamos al inicio
    }
}
