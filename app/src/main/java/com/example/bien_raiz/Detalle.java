package com.example.bien_raiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Detalle extends AppCompatActivity {

    private ImageView imgCasa, imgDepartamento;
    private TextView txtTipo, txtProyecto, txtDimension, txtValorVivienda, txtMonto, txtDescuento,
            txtTotal, txtFecha, txtHora;
    private final Utilidades utilidades = new Utilidades();
    private Cotizacion cotizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        cotizacion = (Cotizacion) getIntent().getExtras().getSerializable("cotizacion");

        imgCasa = (ImageView) findViewById(R.id.imgCasaD);
        imgDepartamento = (ImageView) findViewById(R.id.imgDepartamentoD);
        txtFecha = (TextView) findViewById(R.id.txtFechaD);
        txtHora = (TextView) findViewById(R.id.txtHoraD);
        txtTipo = (TextView) findViewById(R.id.txtTipo1D);
        txtProyecto = (TextView) findViewById(R.id.txtProyecto1D);
        txtDimension = (TextView) findViewById(R.id.txtDimensiones1D);
        txtValorVivienda = (TextView) findViewById(R.id.txtValorVivienda1D);
        txtMonto = (TextView) findViewById(R.id.txtMontoFinanciar1D);
        txtDescuento = (TextView) findViewById(R.id.txtDescuento1D);
        txtTotal = (TextView) findViewById(R.id.txtTotalPagar1D);

        cargarMedidas();
        cargarDatos();
    }

    //MÉTODOS
    private void cargarDatos(){
        if(cotizacion.getTipo().equals("Casa")){
            imgCasa.setVisibility(View.VISIBLE);
            imgDepartamento.setVisibility(View.GONE);
        }else if(cotizacion.getTipo().equals("Departamento")){
            imgCasa.setVisibility(View.GONE);
            imgDepartamento.setVisibility(View.VISIBLE);
        }

        String fecha = utilidades.traerFecha();
        String hora = utilidades.traerHora();
        String tipo = cotizacion.getTipo();
        String proyecto = cotizacion.getProyecto();
        String dimension = String.valueOf(cotizacion.getDimension());
        String valorVivienda = String.valueOf(cotizacion.getValorVivienda());
        String montoFinanciar = String.valueOf(cotizacion.getMonto());
        String descuento = String.valueOf(cotizacion.getDescuento());
        String totalPagar = String.valueOf(cotizacion.getMontoFinal());
        String mensajeDimension = dimension+ " M2";
        String mensajeValorVivienda = valorVivienda+" UF";
        String mensajeMontoFinanciar = montoFinanciar+" UF";
        String mensajeDescuento = descuento+" UF";
        String mensajeTotalPagar = totalPagar+" UF";

        txtFecha.setText(fecha);
        txtHora.setText(hora);
        txtTipo.setText(tipo);
        txtProyecto.setText(proyecto);
        txtDimension.setText(mensajeDimension);
        txtValorVivienda.setText(mensajeValorVivienda);
        txtMonto.setText(mensajeMontoFinanciar);
        txtDescuento.setText(mensajeDescuento);
        txtTotal.setText(mensajeTotalPagar);
    }

    private void cargarMedidas(){
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        int orientacion = getWindowManager().getDefaultDisplay().getRotation();
        if (orientacion == Surface.ROTATION_0 || orientacion == Surface.ROTATION_180) {
            getWindow().setLayout((int)(ancho * 0.95), (int)(alto * 0.65));
        } else {
            getWindow().setLayout((int)(ancho * 0.85), (int)(alto * 0.95));
        }
    }

}
