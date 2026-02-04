package com.tesis.michelle.pin;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.ui.puntos.CalendarFragment;
import com.tesis.michelle.pin.ui.puntos.MapsFragment;
import com.tesis.michelle.pin.ui.puntos.PdvsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int tabsNumber;
   // FloatingActionButton fab;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
     //   this.fab = fab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                String modulo = Constantes.MODULO_PUNTOS_PRINCIPAL;
                return new PdvsFragment(modulo);
//            case 1:
//                return new CalendarFragment();
            case 1:
                //fab.setVisibility(View.GONE);
                return new MapsFragment();

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
