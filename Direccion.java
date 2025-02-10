package com.example.meta1_1;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Direccion {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty direccionPrincipal;

    public Direccion(int id, String nombre, String direccionPrincipal) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.direccionPrincipal = new SimpleStringProperty(direccionPrincipal);
    }
    // Getters para las propiedades
    public int getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal.get();
    }
}