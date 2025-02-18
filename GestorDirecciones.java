package com.example.meta1_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GestorDirecciones {
    private ConectorBaseDeDatos conector;

    public GestorDirecciones(ConectorBaseDeDatos conector) {
        this.conector = conector;
    }

    public ObservableList<Direccion> cargarDirecciones(String whereClause) {
        ObservableList<Direccion> direccionesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM direccion";
        if (whereClause != null && !whereClause.isEmpty()) {
            sql += " WHERE " + whereClause;
        }

        try (Connection conn = conector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                String direccionPrincipal = rs.getString("Direccion");

                Direccion direccion = new Direccion(id, nombre, direccionPrincipal);
                direccionesList.add(direccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return direccionesList;
    }

    public boolean crearDireccion(Direccion direccion) {
        String sql = "INSERT INTO direccion (ID, Nombre, Direccion) VALUES (?, ?, ?)";
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, direccion.getId());
            pstmt.setString(2, direccion.getNombre());
            pstmt.setString(3, direccion.getDireccionPrincipal());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarDireccion(int id, String nombre, String direccionPrincipal) {
        String sql = "UPDATE direccion SET Nombre = ?, Direccion = ? WHERE ID = ?";
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, direccionPrincipal);
            pstmt.setInt(3, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarDireccion(int id) {
        String sql = "DELETE FROM direccion WHERE ID = ?";
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