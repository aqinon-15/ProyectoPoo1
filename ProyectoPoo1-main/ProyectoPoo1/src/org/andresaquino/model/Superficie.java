package org.andresaquino.model;

/**
 * Clase base abstracta que define la estructura general de una superficie.
 * * @author Andres Aquino
 */
public abstract class Superficie {

    private String categoria;

    /**
     * Constructor base para asignar la categoría o nombre de la figura.
     * * @param categoria Nombre que identifica el tipo de superficie
     */
    public Superficie(String categoria) {
        this.categoria = categoria;
    }

    // --- Métodos Getters y Setters ---

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // --- Métodos Abstractos ---

    /**
     * Método abstracto a implementar por las clases hijas para calcular el área.
     * * @return El área calculada de la figura
     */
    public abstract double calcularArea();

    // --- Sobrescritura de Métodos ---

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.categoria)
          .append(" - Área: ")
          .append(String.format("%.2f", calcularArea()))
          .append(" m²");
        
        return sb.toString();
    }
}