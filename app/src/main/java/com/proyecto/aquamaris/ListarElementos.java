package com.proyecto.aquamaris;

public class ListarElementos {
    private Object imagen; // Puede ser String (URL) o Integer (resource ID)
    private String name;
    private String city;

    public ListarElementos(Object imagen, String name, String city) {
        this.imagen = imagen;
        this.name = name;
        this.city = city;
    }

    public Object getImagen() {
        return imagen;
    }

    public void setImagen(Object imagen) {
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

}
