package com.example.meta1_1;

public class ActualizarElemento {

    private GestorDirecciones gestorDirecciones;
    private GestorTelefonos gestorTelefonos;
    private GestorVehiculos gestorVehiculos;

    public ActualizarElemento(GestorDirecciones gestorDirecciones, GestorTelefonos gestorTelefonos, GestorVehiculos gestorVehiculos) {
        this.gestorDirecciones = gestorDirecciones;
        this.gestorTelefonos = gestorTelefonos;
        this.gestorVehiculos = gestorVehiculos;
    }

    public void actualizarDireccion(int id, String nombre, String direccionPrincipal) {
        boolean exito = gestorDirecciones.actualizarDireccion(id, nombre, direccionPrincipal);
        if (exito) {
            System.out.println("Dirección actualizada correctamente");
        } else {
            System.out.println("Error al actualizar la dirección");
        }
    }

    public void actualizarTelefono(int id, String numeroTelefono) {
        boolean exito = gestorTelefonos.actualizarTelefono(id, numeroTelefono);
        if (exito) {
            System.out.println("Teléfono actualizado correctamente");
        } else {
            System.out.println("Error al actualizar el teléfono");
        }
    }

    public void actualizarVehiculo(int id, String tipoVehiculo, String marca, int numeroDeLlantas, int idPersona) {
        boolean exito = gestorVehiculos.actualizarVehiculo(id, tipoVehiculo, marca, numeroDeLlantas, idPersona);
        if (exito) {
            System.out.println("Vehículo actualizado correctamente");
        } else {
            System.out.println("Error al actualizar el vehículo");
        }
    }
}