/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katlyn;

/**
 *
 * @author katlyn
 */

class Estudiante {
    private int numero;
    private String carnet;
    private String nombre;
    private double nota;

    // Constructor
    public Estudiante(int numero, String carnet, String nombre, double nota) {
        this.numero = numero;
        this.carnet = carnet;
        this.nombre = nombre;
        this.nota = nota;
    }

    // Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    // Método toString
    @Override
    public String toString() {
        return numero + "-" + carnet + "-" + nombre + "-" + nota;
    }
}