package com.tesis.michelle.pin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.tesis.michelle.pin.R;

public class PortafolioPDFActivity extends AppCompatActivity {

    private String pdf_url = "";
    PDFView pfdPProdcutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portafolio_pdf);

        pfdPProdcutos = findViewById(R.id.pdfPortafolioProductos);
        pfdPProdcutos.fromAsset("CapacitaciónLucky-Abril2023.pdf").load();

//        wvPremiosPDF.getSettings().setJavaScriptEnabled(true);
//        wvPremiosPDF.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//        });
//        pdf_url = "https://luckyecuadorweb.blob.core.windows.net/pdf/Alicorp/PORTAFOLIO_PDF.pdf";
        //Carga url de .PDF en WebView  mediante Google Drive Viewer.
//        wvPremiosPDF.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf_url);
    }
}