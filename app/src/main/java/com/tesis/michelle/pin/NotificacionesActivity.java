package com.tesis.michelle.pin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;

public class NotificacionesActivity extends AppCompatActivity {
//    private String pdf_url = "";
//    PDFView pfdPlanograma;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notificaciones);
//
//        pfdPlanograma = findViewById(R.id.pdfPlanograma);
//        pfdPlanograma.fromAsset("PlanogramasTT2023_y_LineamientosOnpacks.pdf").load(); //este pdf se borró del proyecto
//    }

    private String pdf_url = "";
    private String modulo = "NOTIFICACIONES";
    WebView pfdPlanograma;
    DatabaseHelper handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);
        pdf_url = handler.getLinkOnedrive(modulo);
        Log.i("LINK ONEDRIVE", pdf_url);

        pfdPlanograma = (WebView) findViewById(R.id.wbPlanograma);
        pfdPlanograma.getSettings().setJavaScriptEnabled(true);
        pfdPlanograma.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
//        pdf_url = "https://drive.google.com/embeddedfolderview?id=1Z0EvTXkCKPMqgvh6BLp_Nz9D6m3PrY8a#list";
        //Carga url de .PDF en WebView  mediante Google Drive Viewer.
        pfdPlanograma.loadUrl(pdf_url);
    }
}
