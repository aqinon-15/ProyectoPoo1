package org.andresaquino.model;

/**
 * Clase que representa una figura geométrica de cuatro lados.
 * * @author Andres Aquino
 */
public class Rectangular extends Superficie {

    private double largo;
    private double alto;

    /**
     * Constructor para inicializar la figura con sus dimensiones.
     * * @param largo Ancho o base de la figura
     * @param alto  Altura o profundidad de la figura
     */
    public Rectangular(double largo, double alto) {
        super("Superficie Rectangular");
        this.largo = largo;
        this.alto = alto;
    }

    // --- Métodos Getters y Setters ---

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    // --- Métodos Sobrescritos ---

    @Override
    public double calcularArea() {
        double resultadoArea = this.largo * this.alto;
        return resultadoArea;
    }
}