package com.example.payphoneapp.model;

public class Celular {

    String modelo;
    double costo;
    double iva;


    public Celular(String modelo, double costo, double iva) {
        this.modelo = modelo;
        this.costo = costo;
        this.iva = iva;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
}
