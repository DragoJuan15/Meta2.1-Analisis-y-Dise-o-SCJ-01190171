package com.example.meta1_1;

public class Vehiculo {
    private int id;
    private String tipoDeVehiculo;
    private String marca;
    private int numeroDeLlantas;
    private int idPersona;

    public Vehiculo(int id, String tipoDeVehiculo, String marca, int numeroDeLlantas, int idPersona) {
        this.id = id;
        this.tipoDeVehiculo = tipoDeVehiculo;
        this.marca = marca;
        this.numeroDeLlantas = numeroDeLlantas;
        this.idPersona = idPersona;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipoDeVehiculo() { return tipoDeVehiculo; }
    public void setTipoDeVehiculo(String tipoDeVehiculo) { this.tipoDeVehiculo = tipoDeVehiculo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public int getNumeroDeLlantas() { return numeroDeLlantas; }
    public void setNumeroDeLlantas(int numeroDeLlantas) { this.numeroDeLlantas = numeroDeLlantas; }
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }
}

