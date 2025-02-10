package com.example.meta1_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private TableView<Vehiculo> vehiculos;
    @FXML
    private TableView<Telefono> telefonos;
    @FXML
    private TableView<Direccion> direcciones;
    @FXML
    private TextField texto1;
    @FXML
    private TextField texto2;
    @FXML
    private TextField texto3;
    @FXML
    private ComboBox<String> tipoDeTabla;
    @FXML
    private ComboBox<String> tipoDeVehiculo;
    @FXML
    private Label welcomeText;
    private List<Direccion> datosGuardados1 = new ArrayList<>();
    private List<Telefono> datosGuardados2 = new ArrayList<>();
    private List<Vehiculo> datosGuardados3 = new ArrayList<>();

    private static final String URL = "jdbc:mysql://localhost:3307/personas";
    private static final String USER = "root";
    private static final String PASSWORD = "Marciano15.";

    @FXML
    private TableColumn<Direccion, Integer> colDireccionId;
    @FXML
    private TableColumn<Direccion, String> colDireccionNombre;
    @FXML
    private TableColumn<Direccion, String> colDireccionDireccion;
    @FXML
    private TableColumn<Telefono, Integer> colTelefonoId;
    @FXML
    private TableColumn<Telefono, String> colTelefonoNumero;
    @FXML
    private TableColumn<Telefono, Integer> colTelefonoIdPersona;
    @FXML
    private TableColumn<Vehiculo, Integer> colVehiculoId;
    @FXML
    private TableColumn<Vehiculo, String> colVehiculoTipoDeVehiculo;
    @FXML
    private TableColumn<Vehiculo, String> colVehiculoMarca;
    @FXML
    private TableColumn<Vehiculo, Integer> colVehiculoNumeroDeLlantas;
    @FXML
    private TableColumn<Vehiculo, Integer> colVehiculoIdPersona;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize() {
        tipoDeTabla.getItems().addAll("Direccion", "Telefono", "Vehiculo");
        tipoDeTabla.setValue("Direccion");

        tipoDeVehiculo.getItems().addAll("Auto", "Camion", "Barco", "Submarino", "Moto", "Bicicleta", "Helicoptero", "Avion");
        tipoDeVehiculo.setValue("Auto");

        // Vincular columnas de Direccion
        colDireccionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDireccionNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccionDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionPrincipal"));

        // Vincular columnas de Telefono
        colTelefonoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTelefonoNumero.setCellValueFactory(new PropertyValueFactory<>("numeroTelefono"));
        colTelefonoIdPersona.setCellValueFactory(new PropertyValueFactory<>("idPersona"));

        // Vincular columnas de Vehiculo
        colVehiculoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVehiculoTipoDeVehiculo.setCellValueFactory(new PropertyValueFactory<>("tipoDeVehiculo"));
        colVehiculoNumeroDeLlantas.setCellValueFactory(new PropertyValueFactory<>("numeroDeLlantas"));
        colVehiculoMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colVehiculoIdPersona.setCellValueFactory(new PropertyValueFactory<>("idPersona"));

        // Cargar datos en los TableView
        direcciones.setItems(cargarDirecciones(null)); // Sin filtro
        telefonos.setItems(cargarTelefonos(null)); // Sin filtro
        vehiculos.setItems(cargarVehiculos(null)); // Sin filtro
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @FXML
    public void crearElemento() {
        String tipo = tipoDeTabla.getValue();
        if (tipo.equals("Direccion")) {
            crearDireccion();
            direcciones.setItems(cargarDirecciones(null)); // Actualizar TableView
        } else if (tipo.equals("Telefono")) {
            crearTelefono();
            telefonos.setItems(cargarTelefonos(null)); // Actualizar TableView
        }else if (tipo.equals("Vehiculo")) {
            crearVehiculo();
            vehiculos.setItems(cargarVehiculos(null));
        }
    }

    private void crearDireccion() {
        if (texto1.getText().isEmpty() || texto2.getText().isEmpty() || texto3.getText().isEmpty()) {
            welcomeText.setText("Introduzca una dirección válida");
            return;
        }
        try {
            int id = Integer.parseInt(texto1.getText());
            String nombre = texto2.getText();
            String direccionPrincipal = texto3.getText();

            Direccion direccion = new Direccion(id, nombre, direccionPrincipal);
            datosGuardados1.add(direccion);

            String sql = "INSERT INTO direccion (ID, Nombre, Direccion) VALUES (?, ?, ?)";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, direccion.getId());
                pstmt.setString(2, direccion.getNombre());
                pstmt.setString(3, direccion.getDireccionPrincipal());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    welcomeText.setText("Dirección guardada correctamente");
                } else {
                    welcomeText.setText("Error al guardar la dirección");
                }
            } catch (SQLException e) {
                welcomeText.setText("Error de base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: ingresaste mal los datos");
        }
        limpiarCampos();
    }

    private void crearTelefono() {
        if (texto1.getText().isEmpty() || texto2.getText().isEmpty() || texto3.getText().isEmpty()) {
            welcomeText.setText("Introduzca un teléfono válido");
            return;
        }
        try {
            int id = Integer.parseInt(texto1.getText());
            String numeroTelefono = texto2.getText();
            int idPersona = Integer.parseInt(texto3.getText());

            Telefono telefono = new Telefono(id, numeroTelefono, idPersona);
            datosGuardados2.add(telefono);

            String sql = "INSERT INTO telefono (ID, Telefono, IDPersona) VALUES (?, ?, ?)";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, telefono.getId());
                pstmt.setString(2, telefono.getNumeroTelefono());
                pstmt.setInt(3, telefono.getIdPersona());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    welcomeText.setText("Teléfono guardado correctamente");
                } else {
                    welcomeText.setText("Error al guardar el teléfono");
                }
            } catch (SQLException e) {
                welcomeText.setText("Error de base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: ingresaste mal los datos");
        }
        limpiarCampos();
    }

    @FXML
    public void crearVehiculo() {
        // Verificar que todos los campos necesarios estén llenos
        if (texto1.getText().isEmpty() || texto2.getText().isEmpty() || texto3.getText().isEmpty() || tipoDeVehiculo.getValue() == null) {
            welcomeText.setText("Introduzca datos válidos para el vehículo");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Parsear los datos ingresados
            int id = Integer.parseInt(texto1.getText());
            String tipoVehiculo = tipoDeVehiculo.getValue();
            String marca = texto2.getText();
            int idPersona = Integer.parseInt(texto3.getText());
            int numeroDeLlantas = getNumeroDeLlantas(tipoVehiculo);

            // Crear objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo(id, tipoVehiculo, marca, numeroDeLlantas, idPersona);

            // Imprimir los datos del vehículo para verificar
            System.out.println("Datos del vehículo a insertar: " + vehiculo);

            // Preparar la consulta SQL
            String sql = "INSERT INTO vehiculos (ID, TipoDeVehiculo, MarcaDelVehiculo, NumeroDeLlantas, IDPersona) VALUES (?, ?, ?, ?, ?)";

            // Obtener conexión
            conn = getConnection();
            if (conn == null) {
                welcomeText.setText("Error: No se pudo conectar a la base de datos");
                return;
            }

            // Preparar la declaración
            pstmt = conn.prepareStatement(sql);

            // Establecer los parámetros de la consulta
            pstmt.setInt(1, vehiculo.getId());
            pstmt.setString(2, vehiculo.getTipoDeVehiculo());
            pstmt.setString(3, vehiculo.getMarca());
            pstmt.setInt(4, vehiculo.getNumeroDeLlantas());
            pstmt.setInt(5, vehiculo.getIdPersona());

            // Imprimir la consulta SQL para verificar
            System.out.println("Consulta SQL a ejecutar: " + pstmt.toString());

            // Ejecutar la consulta y obtener el número de filas afectadas
            int affectedRows = pstmt.executeUpdate();

            // Verificar si la inserción fue exitosa
            if (affectedRows > 0) {
                welcomeText.setText("Vehículo guardado correctamente");
                datosGuardados3.add(vehiculo);
                // Actualizar la tabla de vehículos en la interfaz
                vehiculos.setItems(cargarVehiculos(null));
            } else {
                welcomeText.setText("Error al guardar el vehículo: No se insertaron filas");
            }
        } catch (SQLException e) {
            // Manejar errores de SQL
            welcomeText.setText("Error de base de datos: " + e.getMessage());
            System.out.println("Error SQL: " + e.getMessage());
            System.out.println("Código de error SQL: " + e.getErrorCode());
            System.out.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // Manejar errores de formato de número
            welcomeText.setText("Error: ingresaste mal los datos numéricos");
            System.out.println("Error de formato de número: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Capturar cualquier otra excepción no prevista
            welcomeText.setText("Error inesperado: " + e.getMessage());
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de la base de datos
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
            // Limpiar los campos de entrada
            limpiarCampos();
        }
    }

    private int getNumeroDeLlantas(String tipoVehiculo) {
        switch (tipoVehiculo) {
            case "Auto":
            case "Camion":
                return 4;
            case "Moto":
            case "Bicicleta":
                return 2;
            case "Avion":
                return 3;
            default:
                return 0;
        }
    }

    @FXML
    public void leerElemento() {
        String tipo = tipoDeTabla.getValue();
        if (texto1.getText().isEmpty()) {
            welcomeText.setText("Introduzca un ID válido");
            return;
        }

        try {
            int id = Integer.parseInt(texto1.getText());

            if (tipo.equals("Direccion")) {
                Direccion direccion = buscarDireccionPorId(id);
                if (direccion != null) {
                    texto2.setText(direccion.getNombre());
                    texto3.setText(direccion.getDireccionPrincipal());
                    welcomeText.setText("Dirección encontrada");
                } else {
                    welcomeText.setText("Dirección no encontrada");
                }
            } else if (tipo.equals("Telefono")) {
                Telefono telefono = buscarTelefonoPorId(id);
                if (telefono != null) {
                    texto2.setText(telefono.getNumeroTelefono());
                    texto3.setText(String.valueOf(telefono.getIdPersona()));
                    welcomeText.setText("Teléfono encontrado");
                } else {
                    welcomeText.setText("Teléfono no encontrado");
                }
            } else if (tipo.equals("Vehiculo")) {
                Vehiculo vehiculo = buscarVehiculoPorId(id);
                if (vehiculo != null) {
                    tipoDeVehiculo.setValue(vehiculo.getTipoDeVehiculo()); // Establecer el tipo de vehículo en el ComboBox
                    texto2.setText(vehiculo.getMarca());
                    texto3.setText(String.valueOf(vehiculo.getIdPersona()));
                    welcomeText.setText("Vehículo encontrado");
                } else {
                    welcomeText.setText("Vehículo no encontrado");
                }
            }
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: el ID debe ser un número");
        }
    }

    private Vehiculo buscarVehiculoPorId(int id) {
        String sql = "SELECT * FROM vehiculos WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String tipoDeVehiculo = rs.getString("TipoDeVehiculo");
                String marca = rs.getString("MarcaDelVehiculo");
                int numeroDeLlantas = rs.getInt("NumeroDeLlantas");
                int idPersona = rs.getInt("IDPersona");

                return new Vehiculo(id, tipoDeVehiculo, marca, numeroDeLlantas, idPersona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Direccion buscarDireccionPorId(int id) {
        String sql = "SELECT * FROM direccion WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                String direccionPrincipal = rs.getString("Direccion");
                return new Direccion(id, nombre, direccionPrincipal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Telefono buscarTelefonoPorId(int id) {
        String sql = "SELECT * FROM telefono WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String numeroTelefono = rs.getString("Telefono");
                int idPersona = rs.getInt("IDPersona");
                return new Telefono(id, numeroTelefono, idPersona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void sobreescribirElemento() {
        String tipo = tipoDeTabla.getValue();
        if (texto1.getText().isEmpty() || texto2.getText().isEmpty() || texto3.getText().isEmpty()) {
            welcomeText.setText("Todos los campos son obligatorios");
            return;
        }

        try {
            int id = Integer.parseInt(texto1.getText());

            if (tipo.equals("Direccion")) {
                String nombre = texto2.getText();
                String direccionPrincipal = texto3.getText();
                boolean exito = actualizarDireccion(id, nombre, direccionPrincipal);
                if (exito) {
                    welcomeText.setText("Dirección actualizada correctamente");
                    direcciones.setItems(cargarDirecciones(null)); // Actualizar TableView
                } else {
                    welcomeText.setText("Error al actualizar la dirección");
                }
            } else if (tipo.equals("Telefono")) {
                String numeroTelefono = texto2.getText();
                boolean exito = actualizarTelefono(id, numeroTelefono);
                if (exito) {
                    welcomeText.setText("Teléfono actualizado correctamente");
                    telefonos.setItems(cargarTelefonos(null)); // Actualizar TableView
                } else {
                    welcomeText.setText("Error al actualizar el teléfono");
                }
            } else if (tipo.equals("Vehiculo")) {
                String tipoVehiculo = tipoDeVehiculo.getValue();
                String marca = texto2.getText();
                int idPersona = Integer.parseInt(texto3.getText());
                int numeroDeLlantas = getNumeroDeLlantas(tipoVehiculo);

                boolean exito = actualizarVehiculo(id, tipoVehiculo, marca, numeroDeLlantas, idPersona);
                if (exito) {
                    welcomeText.setText("Vehículo actualizado correctamente");
                    vehiculos.setItems(cargarVehiculos(null)); // Actualizar TableView
                } else {
                    welcomeText.setText("Error al actualizar el vehículo");
                }
            }
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: el ID debe ser un número");
        }
    }

    private boolean actualizarVehiculo(int id, String tipoVehiculo, String marca, int numeroDeLlantas, int idPersona) {
        String sql = "UPDATE vehiculos SET TipoDeVehiculo = ?, MarcaDelVehiculo = ?, NumeroDeLlantas = ?, IDPersona = ? WHERE ID = ?";
        try (Connection conn = getConnection();
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

    private boolean actualizarDireccion(int id, String nombre, String direccionPrincipal) {
        String sql = "UPDATE direccion SET Nombre = ?, Direccion = ? WHERE ID = ?";
        try (Connection conn = getConnection();
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

    private boolean actualizarTelefono(int id, String numeroTelefono) {
        String sql = "UPDATE telefono SET Telefono = ? WHERE ID = ?";
        try (Connection conn = getConnection();
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

    @FXML
    public void eliminarElemento() {
        String tipo = tipoDeTabla.getValue();
        if (texto1.getText().isEmpty()) {
            welcomeText.setText("Introduzca un ID válido");
            return;
        }

        try {
            int id = Integer.parseInt(texto1.getText());

            if (tipo.equals("Direccion")) {
                boolean exito = eliminarDireccion(id);
                if (exito) {
                    welcomeText.setText("Dirección eliminada correctamente");
                    direcciones.setItems(cargarDirecciones(null)); // Actualizar TableView
                } else {
                    welcomeText.setText("Error al eliminar la dirección");
                }
            } else if (tipo.equals("Telefono")) {
                boolean exito = eliminarTelefono(id);
                if (exito) {
                    welcomeText.setText("Teléfono eliminado correctamente");
                    telefonos.setItems(cargarTelefonos(null)); // Actualizar TableView
                } else {
                    welcomeText.setText("Error al eliminar el teléfono");
                }
            } else if (tipo.equals("Vehiculo")) {
                boolean exito = eliminarVehiculo(id);
                if (exito) {
                    welcomeText.setText("Vehículo eliminado correctamente");
                    vehiculos.setItems(cargarVehiculos(null)); // Actualizar TableView
                } else {
                    welcomeText.setText("Error al eliminar el vehículo");
                }
            }
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: el ID debe ser un número");
        }
    }

    private boolean eliminarVehiculo(int id) {
        String sql = "DELETE FROM vehiculos WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean eliminarDireccion(int id) {
        String sql = "DELETE FROM direccion WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean eliminarTelefono(int id) {
        String sql = "DELETE FROM telefono WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ObservableList<Direccion> cargarDirecciones(String whereClause) {
        ObservableList<Direccion> direccionesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM direccion";
        if (whereClause != null && !whereClause.isEmpty()) {
            sql += " WHERE " + whereClause;
        }

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                String direccionPrincipal = rs.getString("Direccion");

                Direccion direccion = new Direccion(id, nombre, direccionPrincipal);
                direccionesList.add(direccion);

                // Imprimir en consola para verificar
                System.out.println("Dirección cargada: " + direccion.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return direccionesList;
    }

    private ObservableList<Telefono> cargarTelefonos(String whereClause) {
        ObservableList<Telefono> telefonosList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM telefono"; // Consulta base

        // Añadir la cláusula WHERE si se proporciona
        if (whereClause != null && !whereClause.isEmpty()) {
            sql += " WHERE " + whereClause;
        }

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Recorrer el ResultSet y crear objetos Telefono
            while (rs.next()) {
                int id = rs.getInt("ID");
                String numeroTelefono = rs.getString("Telefono");
                int idPersona = rs.getInt("IDPersona");

                Telefono telefono = new Telefono(id, numeroTelefono, idPersona);
                telefonosList.add(telefono); // Añadir a la lista observable

                // Imprimir en consola para verificar
                System.out.println("Teléfono cargado: " + telefono.getNumeroTelefono() + ", ID Persona: " + telefono.getIdPersona());
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar errores de SQL
        }

        return telefonosList; // Devolver la lista de teléfonos
    }

    private ObservableList<Vehiculo> cargarVehiculos(String filtro) {
        ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM vehiculos";
        if (filtro != null && !filtro.isEmpty()) {
            sql += " WHERE MarcaDelVehiculo LIKE ?";
        }

        try (Connection conn = getConnection();
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

                    // Imprimir para depuración
                    System.out.println("Vehículo cargado: ID=" + id +
                            ", Tipo=" + tipoDeVehiculo +
                            ", Marca=" + marca +
                            ", Llantas=" + numeroDeLlantas +
                            ", IDPersona=" + idPersona);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cargar vehículos: " + e.getMessage());
        }

        return vehiculos;
    }

    private void limpiarCampos() {
        texto1.clear();
        texto2.clear();
        texto3.clear();
    }
}