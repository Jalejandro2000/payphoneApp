package com.example.payphoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.payphoneapp.model.Celular;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    String url ="";
    Celular[] celular = new Celular[1];

    TextView modelo;
    TextView valor;

    String numeroCell = "990407518";
    String codPais ="593";
    String cedula ="0804370492";
    double monto;
    double montoConIva;
    double montoSinIva;
    double iva;
    int idTransaccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modelo = findViewById(R.id.txtProducto);
        valor = findViewById(R.id.txtValor);
        celular[0] = new Celular("Samsung j1 ace", 100,12);
        modelo.setText("Modelo: " + celular[0].getModelo());
        valor.setText("Valor: " +String.valueOf(celular[0].getCosto()));
    }

    public void pagarP (View view){
        iva = (celular[0].getIva()*celular[0].getCosto());
        monto = celular[0].getCosto()*(celular[0].getIva() + 100);
        montoConIva = (celular[0].getCosto()*100);
        Random rnd = new Random(10000);
        confirmarPago();
        Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
        startActivity(intent);

    }

    public void confirmarPago(){
        url = "https://pay.payphonetodoesposible.com/api/Sale";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    getIdTransaccion(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Hubo un error" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phoneNumber", numeroCell);
                params.put("countryCode", codPais);
                params.put("clientUserId", cedula);
                params.put("reference", "none");
                params.put("responseUrl", "http://paystoreCZ.com/confirm.php");
                params.put("amount",""+ monto+"");
                params.put("amountWithTax",""+ montoConIva+"");
                params.put("amountWithoutTax", ""+montoSinIva+"");
                params.put("tax",""+iva+"");
                params.put("clientTransactionId", ""+idTransaccion+"");
                return params;

            }
            @Override
            public  Map<String, String> getHeaders() throws AuthFailureError

            {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer aEqIcPjAbh1ZOgxrRw5WwYaXzmXS4lmsKbMXihdaFay5J3XZGm1gdWNPeV_-9p1rjalLkIOBpPo2kCQ56lCggn2o-qPAFmCFHn63NUs1O2ZzslfZGdebOmOpKvapOgDsxk7pWRxRvB81reskifd8BCQxbL5kepHk2evJ4zuFgrE14MYF6s-m7H9W5n2aq2Lf8rvD1_BySl7mBIYNICtZdWyuYpRGojmIHxfp3H7mD5z41l6iztw3ofQQo3SIBiaeJ8HxOxKweH3DoMHfuqOhwXe3Ysr2eEXaLli31i9kNNe8K_hhfsZWYm9C7gza78JvLF1fvs4r4hSpjk-gXFPzparT9so";
                headers.put("Authorization", auth);
                return headers;
            }


        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public  void getIdTransaccion(String stringResponse) throws JSONException {

        JSONObject obJson=new JSONObject(stringResponse);
        System.out.println(obJson.get("transactionId"));


    }
}