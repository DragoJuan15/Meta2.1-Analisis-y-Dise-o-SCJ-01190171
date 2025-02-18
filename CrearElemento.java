package com.example.meta1_1;

public class CrearElemento {

    private GestorDirecciones gestorDirecciones;
    private GestorTelefonos gestorTelefonos;
    private GestorVehiculos gestorVehiculos;

    public CrearElemento(GestorDirecciones gestorDirecciones, GestorTelefonos gestorTelefonos, GestorVehiculos gestorVehiculos) {
        this.gestorDirecciones = gestorDirecciones;
        this.gestorTelefonos = gestorTelefonos;
        this.gestorVehiculos = gestorVehiculos;
    }

    public void crearDireccion(int id, String nombre, String direccionPrincipal) {
        Direccion direccion = new Direccion(id, nombre, direccionPrincipal);
        boolean exito = gestorDirecciones.crearDireccion(direccion);
        if (exito) {
            System.out.println("Dirección creada correctamente");
        } else {
            System.out.println("Error al crear la dirección");
        }
    }

    public void crearTelefono(int id, String numeroTelefono, int idPersona) {
        Telefono telefono = new Telefono(id, numeroTelefono, idPersona);
        boolean exito = gestorTelefonos.crearTelefono(telefono);
        if (exito) {
            System.out.println("Teléfono creado correctamente");
        } else {
            System.out.println("Error al crear el teléfono");
        }
    }

    public void crearVehiculo(int id, String tipoVehiculo, String marca, int numeroDeLlantas, int idPersona) {
        Vehiculo vehiculo = new Vehiculo(id, tipoVehiculo, marca, numeroDeLlantas, idPersona);
        boolean exito = gestorVehiculos.crearVehiculo(vehiculo);
        if (exito) {
            System.out.println("Vehículo creado correctamente");
        } else {
            System.out.println("Error al crear el vehículo");
        }
    }
}