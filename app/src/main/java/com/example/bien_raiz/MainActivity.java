package com.example.bien_raiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Context contexto;
    private ImageView imgCasa, imgDepartamento;
    private RadioButton rbCasa, rbDepartamento;
    private Spinner spCasa, spDepartamento, spDimensionCasa, spDimensionDepartamento;
    private CheckBox chbCompraVerde;
    private EditText edtMonto;
    private final Utilidades utilidades = new Utilidades();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexto = getApplicationContext();

        imgCasa = (ImageView) findViewById(R.id.imgCasaMA);
        imgDepartamento = (ImageView) findViewById(R.id.imgDepartamentoMA);
        rbCasa = (RadioButton) findViewById(R.id.rbCasaMA);
        rbDepartamento = (RadioButton) findViewById(R.id.rbDepartamentoMA);
        spCasa = (Spinner) findViewById(R.id.spCasaMA);
        spDepartamento = (Spinner) findViewById(R.id.spDepartamentoMA);
        spDimensionCasa = (Spinner) findViewById(R.id.spDimensionCasaMA);
        spDimensionDepartamento = (Spinner) findViewById(R.id.spDimensionDepartamentoMA);
        chbCompraVerde = (CheckBox) findViewById(R.id.chbCompraVerdeMA);
        edtMonto = (EditText) findViewById(R.id.edtMontoMA);
        TableRow btnCalcular = (TableRow) findViewById(R.id.trCalcularMA);

        edtMonto.requestFocus();
        cargarDatos();

        irDetalle();
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vista) {
                validar();
            }
        });

    }

    //MÉTODOS
    private void irDetalle(){
        edtMonto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    validar();
                    return true;
                }
                return false;
            }
        });
    }

    private void validar(){
        String mensaje = "";
        String monto = edtMonto.getText().toString();
        if(monto.isEmpty()){
            mensaje = "AGREGAR MONTO EN UF";
            utilidades.mostrarMensajePersonalizado(contexto, mensaje, "NOK");
            edtMonto.requestFocus();
        }else{
            String tipo = "", proyecto = "";
            int dimension = 0;
            int montoInt = Integer.parseInt(monto);
            boolean compraVerde = false;

            if(rbCasa.isChecked()){
                tipo = "Casa";
                proyecto = spCasa.getSelectedItem().toString();
                dimension = Integer.parseInt(spDimensionCasa.getSelectedItem().toString());
            }else if(rbDepartamento.isChecked()){
                tipo = "Departamento";
                proyecto = spDepartamento.getSelectedItem().toString();
                dimension = Integer.parseInt(spDimensionDepartamento.getSelectedItem().toString());
            }

            if(chbCompraVerde.isChecked()){
                compraVerde = true;
            }

            Cotizacion cotizacion = generarCotizacion(tipo, proyecto, dimension, compraVerde, montoInt);
            int respuestaCalcular = cotizacion.calcular(tipo, dimension, compraVerde, montoInt);
            int valorVivienda = cotizacion.traerValorVivienda(tipo, dimension);
            switch(respuestaCalcular){
                case 1:
                    mensaje = "EL MONTO NO PUEDE SER MENOR A "+Math.round(valorVivienda * 0.2)+ " UF";
                    utilidades.mostrarMensajePersonalizado(contexto, mensaje, "NOK");
                    edtMonto.requestFocus();
                    break;
                case 2:
                    mensaje = "EL MONTO NO PUEDE SER MAYOR A "+Math.round(valorVivienda * 0.8)+ " UF";
                    utilidades.mostrarMensajePersonalizado(contexto, mensaje, "NOK");
                    edtMonto.requestFocus();
                    break;
                default:
                    Intent irDetalle = new Intent(MainActivity.this, Detalle.class);
                    irDetalle.putExtra("cotizacion", cotizacion);
                    startActivity(irDetalle);
                    break;
            }
        }
    }

    private Cotizacion generarCotizacion(String tipo, String proyecto, int dimension, boolean compraVerde, int monto){
        Cotizacion cotizacion = new Cotizacion();
        int respuestaCalcular = cotizacion.calcular(tipo, dimension, compraVerde, monto);
        int valorVivienda = cotizacion.traerValorVivienda(tipo, dimension);
        int descuento = 0;
        if(compraVerde){
            descuento = (int) (valorVivienda * 0.015);
        }
        cotizacion.setTipo(tipo);
        cotizacion.setProyecto(proyecto);
        cotizacion.setDimension(dimension);
        cotizacion.setCompraVerde(compraVerde);
        cotizacion.setMonto(monto);
        cotizacion.setDescuento(descuento);
        cotizacion.setMontoFinal(respuestaCalcular);
        cotizacion.setValorVivienda(valorVivienda);
        return cotizacion;
    }

    private void cargarDatos(){
        String[] casas = new String[]{"Los Almendros", "Los Pinos", "Villa Alegre"};
        ArrayAdapter<String> adaptadorCasas;
        adaptadorCasas = new ArrayAdapter<>(contexto, R.layout.spinner_personalizado, R.id.txt, casas);
        spCasa.setAdapter(adaptadorCasas);

        Integer[] dimensionCasas = new Integer[]{110, 130, 170};
        ArrayAdapter<Integer> adaptadorDimensionCasas = new ArrayAdapter<>(contexto, R.layout.spinner_personalizado,
                R.id.txt, dimensionCasas);
        spDimensionCasa.setAdapter(adaptadorDimensionCasas);

        String[] departamentos = new String[]{"Las Rosas", "Quelén", "Los Cisnes"};
        ArrayAdapter<String> adaptadorDepartamentos;
        adaptadorDepartamentos = new ArrayAdapter<>(contexto, R.layout.spinner_personalizado, R.id.txt, departamentos);
        spDepartamento.setAdapter(adaptadorDepartamentos);

        Integer[] dimensionDepartamentos = new Integer[]{100, 120};
        ArrayAdapter<Integer> adaptadorDimensionDepartamentos = new ArrayAdapter<>(contexto, R.layout.spinner_personalizado,
                R.id.txt, dimensionDepartamentos);
        spDimensionDepartamento.setAdapter(adaptadorDimensionDepartamentos);
    }

    public void mostrarOcultar (View vista){
        if (rbCasa.isChecked()) {
            imgCasa.setVisibility(View.VISIBLE);
            spCasa.setVisibility(View.VISIBLE);
            spDimensionCasa.setVisibility(View.VISIBLE);
            imgDepartamento.setVisibility(View.GONE);
            spDepartamento.setVisibility(View.GONE);
            spDimensionDepartamento.setVisibility(View.GONE);
        } else if (rbDepartamento.isChecked()) {
            imgCasa.setVisibility(View.GONE);
            spCasa.setVisibility(View.GONE);
            spDimensionCasa.setVisibility(View.GONE);
            imgDepartamento.setVisibility(View.VISIBLE);
            spDepartamento.setVisibility(View.VISIBLE);
            spDimensionDepartamento.setVisibility(View.VISIBLE);
        }
    }
}
