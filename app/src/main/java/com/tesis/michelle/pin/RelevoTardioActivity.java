package com.tesis.michelle.pin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tesis.michelle.pin.ui.puntos.PdvsFragment;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.R;

public class RelevoTardioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevo_tardio);

        String modulo = Constantes.MODULO_PUNTOS_TARDIO;
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PdvsFragment(modulo)).commit();
    }
}