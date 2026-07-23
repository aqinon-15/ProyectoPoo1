package org.andresaquino.model;

/**
 * Clase que representa una superficie en forma de triángulo.
 * * @author Andres Aquino
 */
public class Triangular extends Superficie {

    private double anchoBase;
    private double alturaTriangulo;

    /**
     * Constructor para inicializar las dimensiones del triángulo.
     * * @param anchoBase Medida de la base
     * @param alturaTriangulo Medida de la altura
     */
    public Triangular(double anchoBase, double alturaTriangulo) {
        super("Superficie Triangular");
        this.anchoBase = anchoBase;
        this.alturaTriangulo = alturaTriangulo;
    }

    // --- Getters y Setters ---

    public double getAnchoBase() {
        return anchoBase;
    }

    public void setAnchoBase(double anchoBase) {
        this.anchoBase = anchoBase;
    }

    public double getAlturaTriangulo() {
        return alturaTriangulo;
    }

    public void setAlturaTriangulo(double alturaTriangulo) {
        this.alturaTriangulo = alturaTriangulo;
    }

    // --- Cálculo de Área ---

    @Override
    public double calcularArea() {
        return (this.anchoBase * this.alturaTriangulo) * 0.5;
    }
}