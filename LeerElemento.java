package com.example.meta1_1;

import javafx.collections.ObservableList;

public class LeerElemento {

    private GestorDirecciones gestorDirecciones;
    private GestorTelefonos gestorTelefonos;
    private GestorVehiculos gestorVehiculos;

    public LeerElemento(GestorDirecciones gestorDirecciones, GestorTelefonos gestorTelefonos, GestorVehiculos gestorVehiculos) {
        this.gestorDirecciones = gestorDirecciones;
        this.gestorTelefonos = gestorTelefonos;
        this.gestorVehiculos = gestorVehiculos;
    }

    public ObservableList<Direccion> leerDirecciones(String filtro) {
        return gestorDirecciones.cargarDirecciones(filtro);
    }

    public ObservableList<Telefono> leerTelefonos(String filtro) {
        return gestorTelefonos.cargarTelefonos(filtro);
    }

    public ObservableList<Vehiculo> leerVehiculos(String filtro) {
        return gestorVehiculos.cargarVehiculos(filtro);
    }
}