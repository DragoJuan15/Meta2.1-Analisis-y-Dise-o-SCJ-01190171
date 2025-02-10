package com.example.meta1_1;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Telefono {
    private final IntegerProperty id;
    private final StringProperty numeroTelefono;
    private final IntegerProperty idPersona;

    public Telefono(int id, String numeroTelefono, int idPersona) {
        this.id = new SimpleIntegerProperty(id);
        this.numeroTelefono = new SimpleStringProperty(numeroTelefono);
        this.idPersona = new SimpleIntegerProperty(idPersona);
    }
    // Getters para las propiedades
    public int getId() {
        return id.get();
    }

    public String getNumeroTelefono() {
        return numeroTelefono.get();
    }

    public int getIdPersona() {
        return idPersona.get();
    }
}
