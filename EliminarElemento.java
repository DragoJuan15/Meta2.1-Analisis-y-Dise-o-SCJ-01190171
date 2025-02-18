package com.example.meta1_1;

public class EliminarElemento {

    private GestorDirecciones gestorDirecciones;
    private GestorTelefonos gestorTelefonos;
    private GestorVehiculos gestorVehiculos;

    public EliminarElemento(GestorDirecciones gestorDirecciones, GestorTelefonos gestorTelefonos, GestorVehiculos gestorVehiculos) {
        this.gestorDirecciones = gestorDirecciones;
        this.gestorTelefonos = gestorTelefonos;
        this.gestorVehiculos = gestorVehiculos;
    }

    public void eliminarDireccion(int id) {
        boolean exito = gestorDirecciones.eliminarDireccion(id);
        if (exito) {
            System.out.println("Dirección eliminada correctamente");
        } else {
            System.out.println("Error al eliminar la dirección");
        }
    }

    public void eliminarTelefono(int id) {
        boolean exito = gestorTelefonos.eliminarTelefono(id);
        if (exito) {
            System.out.println("Teléfono eliminado correctamente");
        } else {
            System.out.println("Error al eliminar el teléfono");
        }
    }

    public void eliminarVehiculo(int id) {
        boolean exito = gestorVehiculos.eliminarVehiculo(id);
        if (exito) {
            System.out.println("Vehículo eliminado correctamente");
        } else {
            System.out.println("Error al eliminar el vehículo");
        }
    }
}