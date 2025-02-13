package com.proyecto.aquamaris;

public class Usuario
{
    private String nombre;
    private int edad;

    // Constructor vacío (necesario para Firebase)
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    // Getter para el nombre
    public String getNombre() {
        return nombre;
    }

    // Setter para el nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para la edad
    public int getEdad() {
        return edad;
    }

    // Setter para la edad
    public void setEdad(int edad) {
        this.edad = edad;
    }
}
