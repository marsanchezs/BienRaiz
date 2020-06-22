package cl.proyectos.bienraiz;

public class Negocio {
    //ATRIBUTOS
    String tipo, proyecto, dimension, seguro, monto;

    //CONSTRUCTOR
    public Negocio(String tipoViv, String proyectoViv, String dimensionViv, String seguroViv, String montoViv) {
        this.tipo = tipoViv;
        this.proyecto = proyectoViv;
        this.dimension = dimensionViv;
        this.seguro = seguroViv;
        this.monto = montoViv;
    }

    //MÉTODOS
    public int calcularValorFinal(){
        int valorFinal = 0;
        Validaciones validar = new Validaciones();
        String respuestaDimensionVivienda = validar.asociarDimensionYValor(tipo, dimension);
        int montoInt = Integer.parseInt(monto);
        int valorViv = Integer.parseInt(respuestaDimensionVivienda);

        if(seguro.equals("Marcado")){
            valorFinal = (int) (valorViv - ((valorViv * 0.15) + montoInt));
        }else if(seguro.equals("No Marcado")){
            valorFinal = valorViv - montoInt;
        }
        return valorFinal;
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

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
