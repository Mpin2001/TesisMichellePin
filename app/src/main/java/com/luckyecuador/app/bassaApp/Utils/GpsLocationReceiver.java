package com.luckyecuador.app.bassaApp.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.luckyecuador.app.bassaApp.ServiceRastreo.LocationService;

public class GpsLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Toast.makeText(context, "in android.location.PROVIDERS_CHANGED", Toast.LENGTH_SHORT).show();
            Intent pushIntent = new Intent(context, LocationService.class);
            context.startService(pushIntent);
        }
    }
}
