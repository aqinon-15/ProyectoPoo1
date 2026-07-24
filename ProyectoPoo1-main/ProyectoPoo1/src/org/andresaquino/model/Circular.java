package org.andresaquino.model;

public class Circular extends Superficie {

    private double radioMedida;

    public Circular() {
        super("Figura Circular");
    }

    public Circular(double radioMedida) {
        super("Figura Circular");
        this.radioMedida = radioMedida;
    }

    public double getRadioMedida() {
        return radioMedida;
    }

    public void setRadioMedida(double radioMedida) {
        this.radioMedida = radioMedida;
    }

    @Override
    public double calcularArea() {
        return Math.PI * (this.radioMedida * this.radioMedida);
    }
}