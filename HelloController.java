package com.example.meta1_1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {
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

    private CrearElemento crearElemento;
    private LeerElemento leerElemento;
    private ActualizarElemento actualizarElemento;
    private EliminarElemento eliminarElemento;

    @FXML
    public void initialize() {
        ConectorBaseDeDatos conector = new ConectorBaseDeDatos();
        GestorDirecciones gestorDirecciones = new GestorDirecciones(conector);
        GestorTelefonos gestorTelefonos = new GestorTelefonos(conector);
        GestorVehiculos gestorVehiculos = new GestorVehiculos(conector);

        crearElemento = new CrearElemento(gestorDirecciones, gestorTelefonos, gestorVehiculos);
        leerElemento = new LeerElemento(gestorDirecciones, gestorTelefonos, gestorVehiculos);
        actualizarElemento = new ActualizarElemento(gestorDirecciones, gestorTelefonos, gestorVehiculos);
        eliminarElemento = new EliminarElemento(gestorDirecciones, gestorTelefonos, gestorVehiculos);

        tipoDeTabla.getItems().addAll("Direccion", "Telefono", "Vehiculo");
        tipoDeTabla.setValue("Direccion");

        tipoDeVehiculo.getItems().addAll("Auto", "Camion", "Barco", "Submarino", "Moto", "Bicicleta", "Helicoptero", "Avion");
        tipoDeVehiculo.setValue("Auto");

        // Configurar columnas
        colDireccionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDireccionNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccionDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionPrincipal"));

        colTelefonoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTelefonoNumero.setCellValueFactory(new PropertyValueFactory<>("numeroTelefono"));
        colTelefonoIdPersona.setCellValueFactory(new PropertyValueFactory<>("idPersona"));

        colVehiculoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVehiculoTipoDeVehiculo.setCellValueFactory(new PropertyValueFactory<>("tipoDeVehiculo"));
        colVehiculoMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colVehiculoNumeroDeLlantas.setCellValueFactory(new PropertyValueFactory<>("numeroDeLlantas"));
        colVehiculoIdPersona.setCellValueFactory(new PropertyValueFactory<>("idPersona"));

        actualizarTablas();
    }

    @FXML
    public void crearElemento() {
        String tipo = tipoDeTabla.getValue();
        try {
            int id = Integer.parseInt(texto1.getText());
            String valor1 = texto2.getText();
            String valor2 = texto3.getText();

            switch (tipo) {
                case "Direccion":
                    crearElemento.crearDireccion(id, valor1, valor2);
                    break;
                case "Telefono":
                    crearElemento.crearTelefono(id, valor1, Integer.parseInt(valor2));
                    break;
                case "Vehiculo":
                    String tipoVehiculo = tipoDeVehiculo.getValue();
                    int numeroDeLlantas = getNumeroDeLlantas(tipoVehiculo);
                    crearElemento.crearVehiculo(id, tipoVehiculo, valor1, numeroDeLlantas, Integer.parseInt(valor2));
                    break;
            }
            welcomeText.setText(tipo + " creado correctamente");
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: Datos inválidos");
        }
        actualizarTablas();
    }

    @FXML
    public void leerElemento() {
        String tipo = tipoDeTabla.getValue();
        try {
            int id = Integer.parseInt(texto1.getText());
            switch (tipo) {
                case "Direccion":
                    Direccion direccion = leerElemento.leerDirecciones("ID = " + id).stream().findFirst().orElse(null);
                    if (direccion != null) {
                        texto2.setText(direccion.getNombre());
                        texto3.setText(direccion.getDireccionPrincipal());
                        welcomeText.setText("Dirección encontrada");
                    } else {
                        welcomeText.setText("Dirección no encontrada");
                    }
                    break;
                case "Telefono":
                    Telefono telefono = leerElemento.leerTelefonos("ID = " + id).stream().findFirst().orElse(null);
                    if (telefono != null) {
                        texto2.setText(telefono.getNumeroTelefono());
                        texto3.setText(String.valueOf(telefono.getIdPersona()));
                        welcomeText.setText("Teléfono encontrado");
                    } else {
                        welcomeText.setText("Teléfono no encontrado");
                    }
                    break;
                case "Vehiculo":
                    Vehiculo vehiculo = leerElemento.leerVehiculos("ID = " + id).stream().findFirst().orElse(null);
                    if (vehiculo != null) {
                        tipoDeVehiculo.setValue(vehiculo.getTipoDeVehiculo());
                        texto2.setText(vehiculo.getMarca());
                        texto3.setText(String.valueOf(vehiculo.getIdPersona()));
                        welcomeText.setText("Vehículo encontrado");
                    } else {
                        welcomeText.setText("Vehículo no encontrado");
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: ID inválido");
        }
        actualizarTablas();
    }

    @FXML
    public void sobreescribirElemento() {
        String tipo = tipoDeTabla.getValue();
        try {
            int id = Integer.parseInt(texto1.getText());
            String valor1 = texto2.getText();
            String valor2 = texto3.getText();

            switch (tipo) {
                case "Direccion":
                    actualizarElemento.actualizarDireccion(id, valor1, valor2);
                    break;
                case "Telefono":
                    actualizarElemento.actualizarTelefono(id, valor1);
                    break;
                case "Vehiculo":
                    String tipoVehiculo = tipoDeVehiculo.getValue();
                    int numeroDeLlantas = getNumeroDeLlantas(tipoVehiculo);
                    actualizarElemento.actualizarVehiculo(id, tipoVehiculo, valor1, numeroDeLlantas, Integer.parseInt(valor2));
                    break;
            }
            actualizarTablas();
            welcomeText.setText(tipo + " actualizado correctamente");
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: Datos inválidos");
        }
        actualizarTablas();
    }

    @FXML
    public void eliminarElemento() {
        String tipo = tipoDeTabla.getValue();
        try {
            int id = Integer.parseInt(texto1.getText());
            switch (tipo) {
                case "Direccion":
                    eliminarElemento.eliminarDireccion(id);
                    break;
                case "Telefono":
                    eliminarElemento.eliminarTelefono(id);
                    break;
                case "Vehiculo":
                    eliminarElemento.eliminarVehiculo(id);
                    break;
            }
            welcomeText.setText(tipo + " eliminado correctamente");
        } catch (NumberFormatException e) {
            welcomeText.setText("Error: ID inválido");
        }
        actualizarTablas();
    }

    private void actualizarTablas() {
        direcciones.setItems(FXCollections.observableArrayList(leerElemento.leerDirecciones(null)));
        telefonos.setItems(FXCollections.observableArrayList(leerElemento.leerTelefonos(null)));
        vehiculos.setItems(FXCollections.observableArrayList(leerElemento.leerVehiculos(null)));
        limpiarCampos();
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

    private void limpiarCampos() {
        texto1.clear();
        texto2.clear();
        texto3.clear();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}