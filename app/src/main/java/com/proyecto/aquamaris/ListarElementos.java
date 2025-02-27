package com.proyecto.aquamaris;

public class ListarElementos
{
    public String imagen;
    public String name;
    public String city;
    public String status;

    public ListarElementos(String imagen, String name, String city, String status)
    {
        this.imagen = imagen;
        this.name = name;
        this.city = city;
        this.status = status;
    }

    public String getImagen() {
        return imagen;
    }

    public void setColor(String color) {
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
