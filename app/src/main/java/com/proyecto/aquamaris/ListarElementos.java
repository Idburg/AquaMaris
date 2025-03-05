package com.proyecto.aquamaris;

public class ListarElementos {
    private Object imagen; // Puede ser String (URL) o Integer (resource ID)
    private String name;
    private String city;
    private String status;

    public ListarElementos(Object imagen, String name, String city, String status) {
        this.imagen = imagen;
        this.name = name;
        this.city = city;
        this.status = status;
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

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
