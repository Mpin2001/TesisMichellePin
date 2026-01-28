package com.luckyecuador.app.bassaApp.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AlertChangeTime {

    public AlertChangeTime(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Fecha y Hora");
        builder.setMessage("La fecha y la hora han sido cambiadas, por favor, colocarlas en automático");
        builder.setPositiveButton("OK", null);

        AlertDialog ad = builder.create();

        boolean autotime = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            autotime = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            autotime = Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }

        boolean finalAutotime = autotime;
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) ad).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                        if (finalAutotime){
                            ad.dismiss();
                        }
                    }
                });
            }
        });

        if (!autotime) {
            Log.i("AUTOTIME", "DISABLE");
            ad.show();
        } else {
            Log.i("AUTOTIME", "ENABLE");
            if (ad.isShowing()) {
                ad.dismiss();
            }
        }
    }
}
