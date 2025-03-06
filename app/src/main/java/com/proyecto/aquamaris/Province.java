package com.proyecto.aquamaris;

public class Province {
    private final int imagen;  // Recurso de la imagen
    private final String nombre; // Nombre de la provincia

    // Constructor para inicializar los valores
    public Province(int imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    // Métodos getter
    public int getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }
}
