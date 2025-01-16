package com.example.aquamaris;

public class Province {
    private int imagen;  // Recurso de la imagen
    private String nombre; // Nombre de la provincia

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
