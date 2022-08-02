package com.example.bien_raiz;

import java.io.Serializable;

public class Cotizacion implements Serializable {
    private int monto, dimension, descuento, montoFinal, valorVivienda;
    private String tipo, proyecto;
    private boolean compraVerde;

    public Cotizacion(){ }

    public int calcular(String tipo, int dimension, boolean compraVerde, int monto){
        int valorPropiedad = traerValorVivienda(tipo, dimension);
        int retorno = 0;
        if(monto < (valorPropiedad * 0.2)){
            System.out.println("El valor NO puede ser menor a:"+(valorPropiedad * 0.2));
            retorno = 1;
        }else if(monto > (valorPropiedad * 0.8)){
            System.out.println("El valor NO puede ser mayor a:"+(valorPropiedad * 0.8));
            retorno = 2;
        }else{
            retorno = calcularMonto(monto, valorPropiedad, compraVerde);
        }
        System.out.println("RETORNO: "+retorno);
        return retorno;
    }

    public int calcularMonto(int monto, int valorPropiedad, boolean compraVerde){
        int montoAPagar;
        if(compraVerde){
            montoAPagar = (int) ((valorPropiedad - (valorPropiedad * 0.015)) - monto);
        }else{
            montoAPagar = valorPropiedad - monto;
        }
        return montoAPagar;
    }

    public int traerValorVivienda(String tipo, int dimension){
        int valorVivienda = 0;
        if(tipo.equals("Casa")){
            switch(dimension){
                case 110:
                    valorVivienda = 3200;
                    break;
                case 130:
                    valorVivienda = 3800;
                    break;
                case 170:
                    valorVivienda = 4200;
                    break;
                default:
                    valorVivienda = 1;
                    break;
            }
        }else if(tipo.equals("Departamento")){
            switch(dimension){
                case 100:
                    valorVivienda = 1000;
                    break;
                case 120:
                    valorVivienda = 1200;
                    break;
                default:
                    valorVivienda = 1;
                    break;
            }
        }
        return valorVivienda;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public boolean isCompraVerde() {
        return compraVerde;
    }

    public void setCompraVerde(boolean compraVerde) {
        this.compraVerde = compraVerde;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(int montoFinal) {
        this.montoFinal = montoFinal;
    }

    public int getValorVivienda() {
        return valorVivienda;
    }

    public void setValorVivienda(int valorVivienda) {
        this.valorVivienda = valorVivienda;
    }
}
