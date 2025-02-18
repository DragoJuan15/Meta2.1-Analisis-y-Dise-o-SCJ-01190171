package com.example.meta1_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GestorVehiculos {
    private ConectorBaseDeDatos conector;

    public GestorVehiculos(ConectorBaseDeDatos conector) {
        this.conector = conector;
    }

    public ObservableList<Vehiculo> cargarVehiculos(String filtro) {
        ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM vehiculos";
        if (filtro != null && !filtro.isEmpty()) {
            sql += " WHERE MarcaDelVehiculo LIKE ?";
        }

        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (filtro != null && !filtro.isEmpty()) {
                pstmt.setString(1, "%" + filtro + "%");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String tipoDeVehiculo = rs.getString("TipoDeVehiculo");
                    String marca = rs.getString("MarcaDelVehiculo");
                    int numeroDeLlantas = rs.getInt("NumeroDeLlantas");
                    int idPersona = rs.getInt("IDPersona");

                    Vehiculo vehiculo = new Vehiculo(id, tipoDeVehiculo, marca, numeroDeLlantas, idPersona);
                    vehiculos.add(vehiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    public boolean crearVehiculo(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculos (ID, TipoDeVehiculo, MarcaDelVehiculo, NumeroDeLlantas, IDPersona) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehiculo.getId());
            pstmt.setString(2, vehiculo.getTipoDeVehiculo());
            pstmt.setString(3, vehiculo.getMarca());
            pstmt.setInt(4, vehiculo.getNumeroDeLlantas());
            pstmt.setInt(5, vehiculo.getIdPersona());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarVehiculo(int id, String tipoVehiculo, String marca, int numeroDeLlantas, int idPersona) {
        String sql = "UPDATE vehiculos SET TipoDeVehiculo = ?, MarcaDelVehiculo = ?, NumeroDeLlantas = ?, IDPersona = ? WHERE ID = ?";
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoVehiculo);
            pstmt.setString(2, marca);
            pstmt.setInt(3, numeroDeLlantas);
            pstmt.setInt(4, idPersona);
            pstmt.setInt(5, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVehiculo(int id) {
        String sql = "DELETE FROM vehiculos WHERE ID = ?";
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}