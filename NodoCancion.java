/**
 * Nodo de la lista doblemente enlazada circular.
 * Guarda una canción y dos referencias: al nodo anterior y al siguiente.
 * Sus campos son visibles solo dentro del paquete: únicamente ListaReproduccion
 * debe manipular los enlaces.
 */
public class NodoCancion {

    Cancion dato;
    NodoCancion anterior;
    NodoCancion siguiente;

    NodoCancion(Cancion dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
}
