package com.tesis.michelle.pin.Authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

/**
 * Created by Lucky Ecuador on 15/07/2016.
 */

//Bound service para gestionar la autenticación
public class AuthenticationService extends Service {
    private ExpenseAuthenticator autenticador;

    @Override
    public void onCreate() {
        // Nueva instancia del autenticador
        autenticador = new ExpenseAuthenticator(this);
    }

    /*
     * Ligando el servicio al framework de Android
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return autenticador.getIBinder();
    }
}
