package cl.proyectos.bienraiz;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Detalle extends AppCompatActivity {

    String datos;
    TextView txtFecha, txtTipo, txtProyecto, txtDimension, txtValor, txtPie, txtDescuento, txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       datos = getIntent().getStringExtra("Datos");

       txtFecha = (TextView) findViewById(R.id.txtFecha);
       txtTipo = (TextView) findViewById(R.id.txtTipoViv);
       txtProyecto = (TextView) findViewById(R.id.txtProyecto);
       txtDimension = (TextView) findViewById(R.id.txtDimension);
       txtValor = (TextView) findViewById(R.id.txtValor);
       txtPie = (TextView) findViewById(R.id.txtPie);
       txtDescuento = (TextView) findViewById(R.id.txtDescuento);
       txtTotal = (TextView) findViewById(R.id.txtValorFinal);

       cargarMedidasPopUp();
       cargarDatos();
    }

    //MÉTODOS
    public void cargarDatos(){
        //respuesta = String.valueOf(valorFinal)+";"+tipoViv+";"+proyectoViv+";"+dimensionViv+";"+seguroViv+";"+pieViv;
        String[] partesDatos = datos.split(";");
        String total = partesDatos[0];
        String tipo = partesDatos[1];
        String proyecto = partesDatos[2];
        String dimension = partesDatos[3];
        String descuento = partesDatos[4];
        String desc = "";
        String pie = partesDatos[5];
        Validaciones validar = new Validaciones();
        String valorVivUF = validar.asociarDimensionYValor(tipo, dimension);
        if(descuento.equals("No Marcado")){
            desc = "SIN DESCUENTO";
        }else if(descuento.equals("Marcado")){
            desc = generarDescuento(valorVivUF);
            txtDescuento.setGravity(Gravity.END);
            desc = desc+" UF";
        }

        //Asignar fecha a Textview
        Date date = new Date();
        SimpleDateFormat fechaF = new SimpleDateFormat("dd-MMM-yyyy", java.util.Locale.getDefault());
        String sFecha = fechaF.format(date);

        dimension = dimension+" M2";
        valorVivUF = valorVivUF+" UF";
        pie = pie+" UF";
        total = total+" UF";
        txtFecha.setText(sFecha);
        txtTipo.setText(tipo);
        txtProyecto.setText(proyecto);
        txtDimension.setText(dimension);
        txtValor.setText(valorVivUF);
        txtPie.setText(pie);
        txtDescuento.setText(desc);
        txtTotal.setText(total);

    }

    public String generarDescuento(String valorVivUF){
        String respuesta = "";
        int valorUF = Integer.parseInt(valorVivUF);
        int descuento;
        descuento = (int) (valorUF * 0.15);
        respuesta = String.valueOf(descuento);
        return  respuesta;
    }

    public void cargarMedidasPopUp(){
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.95), (int)(alto * 0.60));
    }
    
}