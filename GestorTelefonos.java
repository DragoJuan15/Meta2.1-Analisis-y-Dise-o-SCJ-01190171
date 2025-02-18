package com.example.meta1_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GestorTelefonos {
    private ConectorBaseDeDatos conector;

    public GestorTelefonos(ConectorBaseDeDatos conector) {
        this.conector = conector;
    }

    public ObservableList<Telefono> cargarTelefonos(String whereClause) {
        ObservableList<Telefono> telefonosList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM telefono";
        if (whereClause != null && !whereClause.isEmpty()) {
            sql += " WHERE " + whereClause;
        }

        try (Connection conn = conector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String numeroTelefono = rs.getString("Telefono");
                int idPersona = rs.getInt("IDPersona");

                Telefono telefono = new Telefono(id, numeroTelefono, idPersona);
                telefonosList.add(telefono);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return telefonosList;
    }

    public boolean crearTelefono(Telefono telefono) {
        String sql = "INSERT INTO telefono (ID, Telefono, IDPersona) VALUES (?, ?, ?)";
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, telefono.getId());
            pstmt.setString(2, telefono.getNumeroTelefono());
            pstmt.setInt(3, telefono.getIdPersona());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarTelefono(int id, String numeroTelefono) {
        String sql = "UPDATE telefono SET Telefono = ? WHERE ID = ?";
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroTelefono);
            pstmt.setInt(2, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarTelefono(int id) {
        String sql = "DELETE FROM telefono WHERE ID = ?";
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