package com.luckyecuador.app.bassaApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luckyecuador.app.bassaApp.ui.puntos.PdvsFragment;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.R;

public class RelevoTardioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevo_tardio);

        String modulo = Constantes.MODULO_PUNTOS_TARDIO;
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PdvsFragment(modulo)).commit();
    }
}