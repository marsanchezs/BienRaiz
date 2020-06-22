package cl.proyectos.bienraiz;

public class Validaciones {

    public String validarDatos(String tipo, String dimension, String monto){
        String respuesta = "", valorVivienda = "", respuestaValidarMonto = "";

        if(monto.equals("VACÍO")){
            respuesta = "MONTO VACÍO";
        }else{
            valorVivienda = asociarDimensionYValor(tipo, dimension);
            respuestaValidarMonto = validarMonto(monto, valorVivienda);
            switch(respuestaValidarMonto){
                case "MONTO MAYOR AL 80%":
                    respuesta = "MONTO MAYOR AL 80%";
                    break;
                case "MONTO MENOR AL 20%":
                    respuesta = "MONTO MENOR AL 20%";
                    break;
                case "MONTO VÁLIDO":
                    respuesta = "OK";
                    break;
            }
        }
        if(valorVivienda.isEmpty()){
            valorVivienda = "SIN VALOR";
        }
        System.out.println("TIPO: "+tipo+" DIMENSIÓN: "+dimension+" MONTO: "+monto+" VALOR VIVIENDA: "+valorVivienda);
        return respuesta+";"+valorVivienda;
    }

    private String validarMonto(String monto, String valorVivienda){
        String respuesta = "MONTO VÁLIDO";
        int montoInt = Integer.parseInt(monto);
        int valorViviendaInt = Integer.parseInt(valorVivienda);

        if(montoInt > (valorViviendaInt * 0.8)){
            String limiteSuperior = String.valueOf(valorViviendaInt * 0.8);
            respuesta = "MONTO MAYOR AL 80%";
        }else if(montoInt < (valorViviendaInt * 0.2)){
            respuesta = "MONTO MENOR AL 20%";
        }
        return respuesta;
    }

    public String asociarDimensionYValor(String tipoVivienda, String dimensionVivienda){
        String respuesta = "";

        if(tipoVivienda.equals("CASA")){
            switch(dimensionVivienda){
                case "110":
                    respuesta = "3200";
                    break;
                case "130":
                    respuesta = "3800";
                    break;
                case "170":
                    respuesta = "4100";
                    break;
            }
        }else if(tipoVivienda.equals("DEPARTAMENTO")){
            switch(dimensionVivienda) {
                case "100":
                    respuesta = "1000";
                    break;
                case "120":
                    respuesta = "1200";
                    break;
            }
        }
        return respuesta;
    }
}
