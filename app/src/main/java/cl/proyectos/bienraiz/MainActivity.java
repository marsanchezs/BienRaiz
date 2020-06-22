package cl.proyectos.bienraiz;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioButton rbCasa, rbDepartamento;
    Spinner spCasa, spDepartamento, spDimensionCasa, spDimensionDepartamento;
    CheckBox chbSeguro;
    EditText edtMonto;
    Validaciones validar = new Validaciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rbCasa = (RadioButton) findViewById(R.id.rbCasa);
        rbDepartamento = (RadioButton) findViewById(R.id.rbDepartamento);
        spCasa = (Spinner) findViewById(R.id.spCasa);
        spDepartamento = (Spinner) findViewById(R.id.spDepartamento);
        spDimensionCasa = (Spinner) findViewById(R.id.spDimensionCasa);
        spDimensionDepartamento = (Spinner) findViewById(R.id.spDimensionDepartamento);
        chbSeguro = (CheckBox) findViewById(R.id.chbSeguro);
        edtMonto = (EditText) findViewById(R.id.edtMonto);

        cargarDatos();

    }

    //MÉTODOS
    public String calcular(String tipo, String proyecto, String dimension, String seguro, String monto){
        String respuesta = "";
        int valorFinal;
        System.out.println("TIPO: "+tipo+" PROYECTO: "+proyecto+" DIMENSIÓN: "+dimension+" SEGURO: "+seguro+" MONTO: "+monto);
        Negocio calcular = new Negocio(tipo, proyecto, dimension, seguro, monto);
        valorFinal = calcular.calcularValorFinal();
        String tipoViv = calcular.getTipo();
        String proyectoViv = calcular.getProyecto();
        String dimensionViv = calcular.getDimension();
        String seguroViv = calcular.getSeguro();
        String pieViv = calcular.getMonto();
        respuesta = String.valueOf(valorFinal)+";"+tipoViv+";"+proyectoViv+";"+dimensionViv+";"+seguroViv+";"+pieViv;
        return respuesta;
    }

    public void validarDatos(View view){
        Context contexto = getApplicationContext();
        String respuestaCapturarDatos = capturarDatos(), respuesta = "";
        //System.out.println("RESPUESTA CAPTURAR DATOS: "+respuestaCapturarDatos);
        String[] partesDatos = respuestaCapturarDatos.split(";");
        String tipoVivienda = partesDatos[0];
        String proyecto = partesDatos[1];
        String dimensionVivienda = partesDatos[2];
        String[] partesDimensionVivienda = dimensionVivienda.split(" ");
        String metrosVivienda = partesDimensionVivienda[0];
        String seguro = partesDatos[3];
        String montoVivienda = partesDatos[4];
        //System.out.println("TIPO: "+tipoVivienda+" DIMENSIÓN: "+metrosVivienda+" MONTO: "+montoVivienda);
        String respuestaValidacion = validar.validarDatos(tipoVivienda, metrosVivienda, montoVivienda);
        System.out.println("RESPUESTA VALIDACIÓN: "+respuestaValidacion);
        String[] partesRespuestaValidacion = respuestaValidacion.split(";");
        String respuestaVal = partesRespuestaValidacion[0];
        String valorVivienda = partesRespuestaValidacion[1];
        if(respuestaVal.equals("MONTO VACÍO") && valorVivienda.equals("SIN VALOR")){
            Toast.makeText(contexto, "DEBE INGRESAR UN MONTO EN UF", Toast.LENGTH_SHORT).show();
            edtMonto.setError("DEBE INGRESAR UN MONTO EN UF");
        }else{
            int valorViv = Integer.parseInt(valorVivienda);
            switch (respuestaVal){
                case "MONTO MAYOR AL 80%":
                    edtMonto.setError("MONTO MÁXIMO "+(valorViv * 0.8)+" UF");
                    break;
                case "MONTO MENOR AL 20%":
                    edtMonto.setError("MONTO MÍNIMO "+(valorViv * 0.2)+" UF");
                    break;
                case "OK":
                    String respuestaCalcular = calcular(tipoVivienda, proyecto, metrosVivienda, seguro, montoVivienda);
                    System.out.println("VALOR FINAL: "+respuestaCalcular);
                    Intent detalle = new Intent(contexto, Detalle.class);
                    detalle.putExtra("Datos", respuestaCalcular);
                    startActivity(detalle);
                    break;
            }
        }
    }

    public String capturarDatos(){
        String tipoVivienda = "", proyecto = "", dimesionVivienda = "", seguro, monto, datos, respuestaValidacion;
        if(rbCasa.isChecked()){
           tipoVivienda = rbCasa.getText().toString();
           proyecto = spCasa.getSelectedItem().toString();
           dimesionVivienda = spDimensionCasa.getSelectedItem().toString();
        }else if(rbDepartamento.isChecked()){
            tipoVivienda = rbDepartamento.getText().toString();
            proyecto = spDepartamento.getSelectedItem().toString();
            dimesionVivienda = spDimensionDepartamento.getSelectedItem().toString();
        }

        seguro = (chbSeguro.isChecked() ? "Marcado" : "No Marcado");
        monto = edtMonto.getText().toString();
        if(monto.isEmpty()){
           monto = "VACÍO";
        }
        datos = tipoVivienda+";"+proyecto+";"+dimesionVivienda+";"+seguro+";"+monto;
        System.out.println("DATOS: "+datos);
        return datos;
    }

    private void cargarDatos(){
        Context contexto = getApplicationContext();
        String[] proyectosCasas, proyectosDeptos, dimensionCasa, dimensionDepto;
        
        proyectosCasas = new String[]{"LOS ALMENDROS", "LOS PINOS", "VILLA ALEGRE"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(contexto, R.layout.spinner_personalizado, proyectosCasas);
        spCasa.setAdapter(adaptador);

        proyectosDeptos = new String[]{"LAS ROSAS", "QUELÉN", "LOS CISNES"};
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(contexto, R.layout.spinner_personalizado, proyectosDeptos);
        spDepartamento.setAdapter(adaptador1);

        dimensionCasa = new String[]{"110 M2", "130 M2", "170 M2"};
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(contexto, R.layout.spinner_personalizado, dimensionCasa);
        spDimensionCasa.setAdapter(adaptador2);

        dimensionDepto = new String[]{"100 M2", "120 M2"};
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(contexto, R.layout.spinner_personalizado, dimensionDepto);
        spDimensionDepartamento.setAdapter(adaptador3);

    }

    public void mostrarOcultar(View view){
        if (rbCasa.isChecked()) {
            spCasa.setVisibility(View.VISIBLE);
            spDimensionCasa.setVisibility(View.VISIBLE);
            spDepartamento.setVisibility(View.INVISIBLE);
            spDimensionDepartamento.setVisibility(View.INVISIBLE);
        }else if(rbDepartamento.isChecked()){
            spCasa.setVisibility(View.INVISIBLE);
            spDimensionCasa.setVisibility(View.INVISIBLE);
            spDepartamento.setVisibility(View.VISIBLE);
            spDimensionDepartamento.setVisibility(View.VISIBLE);
        }
    }
}