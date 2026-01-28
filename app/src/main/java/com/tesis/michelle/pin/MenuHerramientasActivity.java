package com.tesis.michelle.pin;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.GuardarLog;
//import com.tesis.michelle.pin.Utils.SyncAutomatica;

public class MenuHerramientasActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST = 1;

    private ImageButton ivExhibiciones;
    private ImageButton ivAdicionales;
    private ImageButton ivExhibicionesCompetencia;
    private ImageButton ivNuevaHerramienta;

    androidx.appcompat.app.AlertDialog syncDialog;

    private TextView lblExhibiciones;
    private TextView lblAdicionales;
    private TextView lblExhibicionesCompetencia;
    private TextView lblNuevaHerramienta;
    DrawerLayout drawerLayout;

    DatabaseHelper handler;

    //Extras
    public String id_servidor,reExtra,codeExtra,localExtra, fecha, hora, usuarioCursor;

    private BroadcastReceiver receptorSync;
//    MyReceiver myReceiver;

    private ImageButton btnPdv;
    private ImageButton btnGeo;
    private ImageButton btnPrecio;
    private ImageButton btnShare;
    private ImageButton btnTracking;
    private ImageButton btnExh;
    private ImageButton btnProm;
    private ImageButton btnPOP;
    private ImageButton btnExhSupervisores;

    private String tipo_usuario = "Mercaderista";

    BroadcastReceiver tickReceiver;

    private int cont_rep_sync = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_herramientas);
        setToolbar();
        LoadData();
//        Init();

        handler = new DatabaseHelper(getApplicationContext(), Provider.DATABASE_NAME, null, 1);

        ivExhibiciones = (ImageButton) findViewById(R.id.ivExhibiciones);
        ivAdicionales = (ImageButton) findViewById(R.id.ivAdicionales);
        ivExhibicionesCompetencia = (ImageButton) findViewById(R.id.ivExhibicionesCompetencia);
        ivNuevaHerramienta = (ImageButton) findViewById(R.id.ivNuevaHerramienta);

        lblExhibiciones = (TextView) findViewById(R.id.lblExhibiciones);
        lblAdicionales = (TextView) findViewById(R.id.lblAdicionales);
        lblExhibicionesCompetencia = (TextView) findViewById(R.id.lblExhibicionesCompetencia);
        lblNuevaHerramienta = (TextView) findViewById(R.id.lblNuevaHerramienta);

        ivExhibiciones.setOnClickListener(this);
        ivAdicionales.setOnClickListener(this);
        ivExhibicionesCompetencia.setOnClickListener(this);
        ivNuevaHerramienta.setOnClickListener(this);

//        receptorSync = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String mensaje = intent.getStringExtra("extra.mensaje");
//                if(mensaje.equals(Mensajes.SYNC_NODATOSCOLA)){
//                    Log.i("MENSAJE",mensaje);
//                    Snackbar.make(findViewById(R.id.coordinatorPos),mensaje, Snackbar.LENGTH_LONG)
//                            .setAction("STATUS", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent(MenuHerramientasActivity.this, HistorialActivity.class);
//                                    startActivity(intent);
//                                }
//                            }).show();
//                }else{
//                    Log.i("MENSAJE1",mensaje);//Mensaje 1 ON_SYNC_TRUE
//                    Snackbar.make(findViewById(R.id.coordinatorPos),
//                            mensaje + "Guardando...", Snackbar.LENGTH_SHORT).show();
//                }
//            }
//        };

        receptorSync = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                final String mensaje = intent.getStringExtra(Mensajes.EXTRA_MENSAJE);
                Log.i("SyncReport", mensaje);
                switch (mensaje) {
                    //PDV
                    case Mensajes.SYNC_FINALIZADA_PDV:
                    case Mensajes.SYNC_NOREQUERIDA + "PuntoVenta":
                        btnPdv.setImageResource(android.R.drawable.checkbox_on_background);
                        closeSync();
                        Log.i("SyncReport", "PDV: " + cont_rep_sync);
                        break;

                    //PRODUCTOS
                    case Mensajes.SYNC_FINALIZADA_PRODUCTOS:
                    case Mensajes.SYNC_NOREQUERIDA + "Productos":
                        btnPrecio.setImageResource(android.R.drawable.checkbox_on_background);
                        closeSync();
                        Log.i("SyncReport", "Productos: " + cont_rep_sync);
                        break;

                    //EXHIBICIONES
//                    case Mensajes.SYNC_FINALIZADA_EXHIBICIONES:
//                    case Mensajes.SYNC_NOREQUERIDA + "Exhibiciones":
//                        btnExh.setImageResource(android.R.drawable.checkbox_on_background);
//                        closeSync();
//                        Log.i("SyncReport", "Exhibiciones: " + cont_rep_sync);
//                        break;

                    //SHARE
                    case Mensajes.SYNC_FINALIZADA_SHARE:
                    case Mensajes.SYNC_NOREQUERIDA + "Share":
                        btnShare.setImageResource(android.R.drawable.checkbox_on_background);
                        closeSync();
                        Log.i("SyncReport", "Share: " + cont_rep_sync);
                        break;

                    //TRACKING
//                    case Mensajes.SYNC_FINALIZADA_TRACKING:
//                    case Mensajes.SYNC_NOREQUERIDA + "Tracking":
//                        btnTracking.setImageResource(android.R.drawable.checkbox_on_background);
//                        closeSync();
//                        Log.i("SyncReport", "Tracking: " + cont_rep_sync);
//                        break;

                    //MATERIAL POP
                    case Mensajes.SYNC_FINALIZADA_POP:
                    case Mensajes.SYNC_NOREQUERIDA + "POP":
                        btnPOP.setImageResource(android.R.drawable.checkbox_on_background);
                        closeSync();
                        Log.i("SyncReport", "POP: " + cont_rep_sync);
                        break;

                    //HERRAMIENTAS EXHIBICIONES
                    case Mensajes.SYNC_FINALIZADA_HERR_EXH:
                    case Mensajes.SYNC_NOREQUERIDA + "HerramientasExhibicion":
                        btnExhSupervisores.setImageResource(android.R.drawable.checkbox_on_background);
                        closeSync();
                        Log.i("SyncReport", "HerramientasExhibicion: " + cont_rep_sync);
                        break;

                    //GEO
                    case Mensajes.SYNC_FINALIZADA_GEOSUPERVISION:
                    case Mensajes.SYNC_NOREQUERIDA + "Geosupervision":
                        SyncAdapter.NewsincronizarAhora(getApplicationContext(), false, Constantes.bajar_Precio, usuarioCursor, tipo_usuario, null, null, null);
                        break;

                    default:
//                        Toast.makeText(getApplicationContext(), "No data: " + mensaje, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };



        //Verificar los permisos
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
//            }
//            return;
//        } else {
////            locationStart();
//            //startService(new Intent(getApplicationContext(), MyService.class));
//            //Si los permisos estan otorgados, llamar al evento onClick del boton
//            //clickButton();
//        }

        // Check GPS is enabled
//        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
//            finish();
//        }

//        if (!isMyServiceRunning(LocationService.class)) {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                startService(new Intent(getApplicationContext(), LocationService.class));
//            } else {
//                startForegroundService(new Intent(getApplicationContext(), LocationService.class));
//            }
//        }


        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
//        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        if (permission == PackageManager.PERMISSION_GRANTED) {
//           // startTrackerService();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST);
//        }

    }

//    private void startTrackerService() {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                Intent intent = new Intent(MenuHerramientasActivity.this, TrackerService.class);
//                intent.setAction(TrackerService.ACTION_START_FOREGROUND_SERVICE);
//                startService(intent);
//                //startService(new Intent(getApplicationContext(), TrackerService.class));
//            } else {
//                Intent intent = new Intent(MenuHerramientasActivity.this, TrackerService.class);
//                intent.setAction(TrackerService.ACTION_START_FOREGROUND_SERVICE);
//                startForegroundService(intent);
//            }
//        }catch (Exception e){
//            Log.i("TrackerService",e.getMessage());
//        }
//    }

//    private void stopTrackerService() {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                Intent intent = new Intent(MenuHerramientasActivity.this, TrackerService.class);
//                intent.setAction(TrackerService.ACTION_STOP_FOREGROUND_SERVICE);
//                startService(intent);
//                //startService(new Intent(getApplicationContext(), TrackerService.class));
//            } else {
//                Intent intent = new Intent(MenuHerramientasActivity.this, TrackerService.class);
//                intent.setAction(TrackerService.ACTION_STOP_FOREGROUND_SERVICE);
//                startForegroundService(intent);
//            }
//        }catch (Exception e){
//            Log.i("TrackerService",e.getMessage());
//        }
//    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void closeSync() {
        cont_rep_sync++;
        if (cont_rep_sync == 4) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    syncDialog.dismiss();
                }
            }, 2000);   //5 seconds
        }
    }

    public void alertSyncDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_progress_sync, null);
        //Title
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setTitle(R.string.app_name);
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alertdialog_sync,null));

        //Traer Views
        btnPdv = dialogView.findViewById(R.id.btnPdvSync);
        btnGeo = dialogView.findViewById(R.id.btnGeoSync);
        btnPrecio = dialogView.findViewById(R.id.btnPrecioSync);
        btnProm = dialogView.findViewById(R.id.btnPromSync);
        //btnExh = dialogView.findViewById(R.id.btnExhSync);
        btnShare = dialogView.findViewById(R.id.btnShareSync);
        //btnTracking = dialogView.findViewById(R.id.btnTrackingSync);
        btnPOP = dialogView.findViewById(R.id.btnMaterialPOPSync);
        btnExhSupervisores = dialogView.findViewById(R.id.btnHerramientasSync);
        //progressBar = dialogView.findViewById(R.id.barra);

        builder.setView(dialogView);
        syncDialog = builder.create();
        syncDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Registrar receptor
        IntentFilter filtroSync = new IntentFilter(Intent.ACTION_SYNC);
        LocalBroadcastManager.getInstance(this).registerReceiver(receptorSync, filtroSync);
//        myReceiver.borrarRegistro(myReceiver);

        tickReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {

                    // Verificamos si ya se realizo la sync automatica;
                    //boolean value = new SyncAutomatica(MenuHerramientasActivity.this).verificarSyncAutomatica();

                    // Si no se ha realizado , se ejecutara el siguiente codigo
                    /*if (value){
                        //consulta si ya sincronizo en el login, no hacer doble sincronizacion
                        if(!handler.getSyncExterna()){
                            AsyncTaskBajarOper bajarOper = new AsyncTaskBajarOper();
                            bajarOper.execute();
                        }else{
                            Log.i("PuntosListActivity cds","ya sincronizo en login");
                        }

                    }*/

                }
            }
        };

        registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar receptor
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receptorSync);
//        myReceiver.registrar(myReceiver);
        if(tickReceiver!=null)
            unregisterReceiver(tickReceiver);
    }

    private void Init(){
//        myReceiver = new MyReceiver(MenuHerramientasActivity.this);
//        myReceiver.registrar(myReceiver);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        usuarioCursor = sharedPreferences.getString("operador","No Name");
        reExtra = sharedPreferences.getString(Constantes.RE, Constantes.NO_DATA);

        codeExtra = sharedPreferences.getString(Constantes.CODE, Constantes.NO_DATA);
        localExtra = sharedPreferences.getString(Constantes.LOCAL, Constantes.NO_DATA);
    }

    @Override
    public void onClick(View view) {
        if(view == ivExhibiciones){
            try{
                Intent intent = new Intent(MenuHerramientasActivity.this, ExhibicionesBassaActivity.class);
               startActivity(intent);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        if(view == ivAdicionales){
            try{
                Intent intent = new Intent(MenuHerramientasActivity.this, ExhibicionesBassaAdicionalesActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        if(view == ivExhibicionesCompetencia){
            try{
//                Intent intent = new Intent(MenuHerramientasActivity.this, ExhibicionesCompetenciaActivity.class);
//                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        if(view == ivNuevaHerramienta){
            try{
//                Intent intent = new Intent(MenuHerramientasActivity.this, ExhCompetenciaNuevaActivity.class);
//                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //MenuItem tracking = (MenuItem) findViewById(R.id.action_tracking);
     /*   if (reExtra!=null) {
            switch (reExtra) {
                case "Hipermercados":
                    tracking.setVisible(true);
                    break;
                case "Bazares":
                    tracking.setVisible(false);
                    break;
                default:
            }
        }*/

        if (id == R.id.action_sync_subida1) {
            if (VerificarNet.hayConexion(this)) {
                try{
                    SyncAdapter.NewsincronizarAhora(this, true,Constantes.SUBIR_TODO, null, null, null, null, null);
                    Snackbar.make(findViewById(R.id.coordinatorPos),
                            Mensajes.ON_SYNC_UP, Snackbar.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }else{
                Snackbar.make(findViewById(R.id.coordinatorPos),
                        Mensajes.ERROR_RED, Snackbar.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_log) {
            Intent intent = new Intent(MenuHerramientasActivity.this, HistorialActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_version) {
            mostrarVersion();
            return true;
        }

        /*if (id == R.id.action_descarga) {
            myReceiver.Descargar();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void mostrarVersion() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.action_version))
                .setMessage(getString(R.string.user) + ": "+ usuarioCursor +"\n" +
                        getString(R.string.version))
                .setPositiveButton("ACEPTAR", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.salir)
                .setMessage(R.string.mng_salirherramientas)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MenuHerramientasActivity.this, MenuNavigationActivity.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("NO", null)
                .show();
    }

    private class AsyncTaskBajarOper extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
//                SyncAdapter.sincronizarAhora(this, false, Constantes.bajar_Oper, operator, tipo_usuario, null, null, null);
                SyncAdapter.NewsincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, usuarioCursor, tipo_usuario, null, null, null);

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {}

//        @Override
//        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(PuntosListActivity.this, R.style.MyAlertDialogStyle);
//            progressDialog.setTitle("Sincronizando");
//            progressDialog.setMessage("Validando credenciales, espere un momento...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }

        @Override
        protected void onPreExecute() {
            if (VerificarNet.hayConexion(getApplicationContext())) {
                if (!usuarioCursor.equals("")) {
                    alertSyncDialog();
                    SyncAdapter.NewsincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, usuarioCursor, tipo_usuario, null, null, null);
                    new GuardarLog(MenuHerramientasActivity.this).saveLog(usuarioCursor, "", "Sincronizacion En Puntos Finalizada");
                    Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),Mensajes.SYNC_NO_USER ,Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(drawerLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG).setAction("Action", null).show();//                Toast.makeText(getApplicationContext(),"No hay conexión de Internet.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}