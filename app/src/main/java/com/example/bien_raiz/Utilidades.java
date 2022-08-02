package com.example.bien_raiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilidades {

    public void ocultarTeclado(Context contexto, EditText edt){
        InputMethodManager adminMetodoEntrada = (InputMethodManager)contexto.getSystemService(Context.INPUT_METHOD_SERVICE);
        adminMetodoEntrada.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }

    public String traerFecha(){
        String fecha = "";
        Date dFecha = new Date();
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy" , Locale.GERMAN);
        fecha = sdfFecha.format(dFecha);
        return fecha;
    }

    public String traerHora(){
        String hora = "";
        Date dHora = new Date();
        SimpleDateFormat sdfHora = new SimpleDateFormat("hh:mm:ss" , Locale.GERMAN);
        hora = sdfHora.format(dHora);
        return hora;
    }

    public void mostrarMensajePersonalizado(Context contexto, String msj, String tipoIcono) {
        Toast mensaje = new Toast(contexto);
        LayoutInflater inflador = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vistaMensaje = inflador.inflate( R.layout.mensaje_personalizado, null );
        mensaje.setView(vistaMensaje);
        ImageView imgIcono = (ImageView) vistaMensaje.findViewById(R.id.imgMP);
        if(tipoIcono.equals("OK")){
            imgIcono.setImageResource(R.drawable.icono_aceptar);
        }else{
            imgIcono.setImageResource(R.drawable.icono_cancelar);
        }
        TextView txtMensaje = (TextView) vistaMensaje.findViewById(R.id.txtM1MP);
        txtMensaje.setText(msj);
        mensaje.setDuration(Toast.LENGTH_LONG);
        mensaje.show();
    }

}
