package com.tesis.michelle.pin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Utils.AlertChangeTime;
import com.tesis.michelle.pin.Utils.AuthLogin;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Conexion.MarshMallowPermission;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Notificaciones.AlarmNotificationReceiver;
import com.tesis.michelle.pin.ServiceRastreo.LocationService;
import com.tesis.michelle.pin.Notificaciones.Session.SessionManagement;
import com.tesis.michelle.pin.Notificaciones.Session.User;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.GuardarLog;
import com.tesis.michelle.pin.Utils.RequestPermissions;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    public static String MY_PREFS_NAME= "nameOfSharedPreferences";

    ProgressDialog progressDialog;
    CoordinatorLayout coordinatorLayout;
    DatabaseHelper handler;
    private EditText txtUser;
    private EditText txtPass;
    private Button btnIngresar;
    private String user, pass, name, nombres;
    String operator;
    private AlertDialog dialog;

    private static final String TAG ="LoginActivity";

    private ImageButton btnSincronizar;
    SharedPreferences sharedPref;
    SharedPreferences.Editor mEditor;

    private BroadcastReceiver receptorSync;
    LocationService LocationService = new LocationService();
    MarshMallowPermission marshMallowPermission;

    private boolean falta_salida;
    private String fecha_falta_salida;

    private int pdv = 0;
    private int productos = 0;
    private int preguntas = 0;

    private int tests = 0;
    private int promociones = 0;
    private int prod_secundarios = 0;
    private int link = 0;
    private int motivos = 0;
    private int promocional_ventas = 0;
    private int campos_x_modulos = 0;
    private int permitido = 0;
    private int suma = 0;
    private int rotacion = 0;
    private int tareas = 0;

    private int tipos_exhibicion = 0;
    private int popsugerido = 0;
    private int prioritario = 0;
    private int combo_canjes = 0;
    private int causales_mci = 0;
    private int materiales_alertas = 0;
    private int pdi = 0;
    private int justificacion = 0;
    private int convenios= 0;
    private int tipo_inventario = 0;
    private int tracking = 0;
    private int tipo_ventas = 0;
    private int tipo_registro = 0;
    private int tipo_actividades = 0;
    private int tipo_implementaciones = 0;
    private int tipo_unidades = 0;
    private int modulos_roles = 0;
    private int sincronizado = 0;

    private BatteryLevelReceiver batteryLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        batteryLow = new BatteryLevelReceiver();
        registerReceiver(batteryLow,new IntentFilter(Intent.ACTION_BATTERY_LOW));

        //setToolbar();
        Context context= this.getApplicationContext();
        LoadData();

        new DeveloperOptions().modalDevOptions(LoginActivity.this);
        RequestPermissions requestPermissions = new RequestPermissions(getApplicationContext(), LoginActivity.this);
        requestPermissions.showPermissionDialog();

        handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);

       // btnSincronizar = (ImageButton) findViewById(R.id.ib_sincronizacion);
        txtUser = (EditText) findViewById(R.id.txt_usuario);
        txtPass = (EditText) findViewById(R.id.txt_clave);

        sharedPref = context.getSharedPreferences("Nombre de Local", Context.MODE_PRIVATE);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = sharedPref.edit();

        checkSharedPreferences();

        if (!txtUser.getText().toString().trim().isEmpty()) {
            txtPass.requestFocus();
        }else{
            txtUser.requestFocus();
        }

        showPermissionDialog();
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLogin);

        txtUser.setOnKeyListener(this);
        txtPass.setOnKeyListener(this);
        btnIngresar = (Button) findViewById(R.id.btn_empezar);
        btnIngresar.setOnClickListener(this);
        //btnSincronizar.setOnClickListener(this);
        marshMallowPermission = new MarshMallowPermission(this);

        if (!marshMallowPermission.checkPermissionForReadPhoneState()) {
            marshMallowPermission.requestPermissionForReadPhoneState();
        }

        receptorSync = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                try {
                    final String mensaje = intent.getStringExtra("extra.mensaje");
                    if (mensaje.equalsIgnoreCase(Mensajes.SYNC_FINALIZADA_PDV) ||
                        mensaje.equalsIgnoreCase(Mensajes.SYNC_NOREQUERIDA + "PuntoVenta")) {
                       // SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_floo, operator);
                        progressDialog.setMessage("Descargando productos, espere un momento...");
                        pdv = 1;
                        permitido = 1;
                        String supervisor = handler.getSupervisorByUser(user);
                        SaveData(user, supervisor);
                    } else if (mensaje.equals("No se encontraron registros en el servidor para este usuario.")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "No se encontraron registros en el servidor para este usuario.", Toast.LENGTH_SHORT).show();
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROD) ||
                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Productos") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        productos = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_preg, operator);
//                        progressDialog.setMessage("Descargando test, espere un momento...");
                       // SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_promociones, operator);
                        progressDialog.setMessage("Descargando promociones, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROMO) ||
                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Promociones") || mensaje.equals("No se encontraron registros en el servidor.")) {
                       promociones = 1;
                       // SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_prod_secundarios, operator);
                        progressDialog.setMessage("Descargando productos secundarios, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROD_SEC) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Productos Secundarios") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        prod_secundarios = 1;
                    //    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_link_onedrive, operator);
                        progressDialog.setMessage("Descargando link de Onedrive, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_LINK) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Link Onedrive") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        link = 1;
                    //    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_justificaciones, operator);
                        progressDialog.setMessage("Descargando justificaciones, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_JUST) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Justificaciones") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        justificacion = 1;
                        //    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_justificaciones, operator);
                        progressDialog.setMessage("Descargando justificaciones, espere un momento...");
                    }


//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PREG) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Preguntas")) {
//                        preguntas = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tests, operator);
//                        progressDialog.setMessage("Descargando tests, espere un momento...");
//                    }
//
//
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TESTS) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tests")) {
//                        tests = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_justificaciones, operator);
//                        progressDialog.setMessage("Descargando tipos de exhibiciones, espere un momento...");
//                    }



//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_EXH) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tipo Exhibicion") || mensaje.equals("No se encontraron registros en el servidor.") ) {
//                        tipos_exhibicion = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_rotacion, operator);
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_ROTACION) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Rotacion") ||
//                        mensaje.equals("No se encontraron registros en el servidor SMS.")) {
//                        rotacion = 1;
////                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tareas, operator);
////                        progressDialog.setMessage("Descargando tareas, espere un momento...");
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_pop_sugerido, operator);
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TAREAS) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tarea")) {
//                        tareas = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_pop_sugerido, operator);
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_POPSUGERIDO) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Pop_sugerido")) {
//                        popsugerido = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_prioritario, operator);
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PRIORITARIO) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Portafolio Prioritario")) {
//                        prioritario = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_combo_canjes, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_COMBO_CANJES) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Combo Canjes")) {
//                        combo_canjes = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_causales_mci, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CAUSALES_MCI) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Causales MCI")) {
//                        causales_mci = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_causales_osa, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CAUSALES_OSA) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Causales OSA")) {
//                        causales_mci = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_materiales_alertas, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_MATERIALES_ALERTAS) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Materiales Alertas")) {
//                        materiales_alertas = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_pdi, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PDI) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "PDI")) {
//                        pdi = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_justificaciones, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_VENTAS) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoVentas") || mensaje.equals("No se encontraron registros en el servidor.")) {
//                        tipo_ventas = 1;
//                       // SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tracking, operator);
//                        progressDialog.setMessage("Descargando tipo ventas, espere un momento...");
//                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TRA) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tracking") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tracking = 1;
                      //  SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_inventario, operator);
                        progressDialog.setMessage("Descargando tipo inventario, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_EXH) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tipo Exhibicion") || mensaje.equals("No se encontraron registros en el servidor.") ) {
                        justificacion = 1;
                     //  SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_convenios, operator);
                       progressDialog.setMessage("Descargando justificaciones, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CONVENIOS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Convenios") || mensaje.equals("No se encontraron registros en el servidor.")) {
                     //   convenios = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_promocional_ventas, operator);
                        progressDialog.setMessage("Descargando Tipo promociones, espere un momento...");
                        Log.i("hola c","si");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROMO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Promociones") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "Promociones")) {
                        promociones = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_ventas, user);
                    }


                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_VENTAS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoVentas") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "TipoVentas")) {
                        tipo_ventas = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_registro, user);
                    }
                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_REGISTRO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoRegistro") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "TipoRegistro")) {
                        tipo_registro = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_actividades, user);
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_ACTVIDADES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoActividades") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "TipoActividades")) {
                        tipo_actividades = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_implementaciones, user);
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_IMPLEMENTACIONES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoImplementaciones") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "TipoImplementaciones")) {
                        tipo_implementaciones = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_unidades, user);
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_UNIDADES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoUnidades") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "TipoUnidades")) {
                        tipo_unidades = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_modulo_roles, user);
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_MODELOS_ROLES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "ModulosRoles") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "ModulosRoles")) {
                        modulos_roles = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_inventario, user);
                    }


                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_INVENTARIO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoInventario") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        //   convenios = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_novedades, operator);
                        progressDialog.setMessage("Descargando Tipo novedades, espere un momento...");
                        Log.i("hola ti","si");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPONOVEDADES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoNovedades") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        //   convenios = 1;
                       SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_canales_fotos, operator);
                        progressDialog.setMessage("Descargando Tipo exhibicion Supervisor, espere un momento...");
                        Log.i("hola tn","si");
                    }
                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PRODUCTOSCANALES) || mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "CANALES FOTOGRAFIA")) {
                        //canales = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_motivos_sugerido, operator);
                        progressDialog.setMessage("Descargando Motivos sugerido, espere un momento...");
                        Log.i("hola ms","si");
                    }
                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_MOTIVOS_SUGERIDO) || mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "MotivosSugerido")) {
                        motivos = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_promocional_ventas, operator);
                        progressDialog.setMessage("Descargando Promocional Ventas, espere un momento...");
                        Log.i("hola pv","si");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROMO_VENTAS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Promocional ventas") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "Promociones")) {
                        promocional_ventas = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_campos_x_modulos, user);
                        progressDialog.setMessage("Descargando Campos por modulo, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CAMPOS_X_MODULOS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Campos x modulos") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "Campos x modulos")) {
                        campos_x_modulos = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Exh_Supervisor, user);
                        progressDialog.setMessage("Descargando Herramientas Exh, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_HERR_EXH) ||
                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "HerramientasExhibicion") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_inventario = 1;
                        obtenerMercaderista(user);
//                        if(dialog != null) {
//                            dialog.dismiss();
//                        }
                        if (progressDialog != null){
                            progressDialog.dismiss();
                        }
                        new GuardarLog(LoginActivity.this).saveLog(name, "", "Descarga de información, Login");
                        Toast.makeText(getApplicationContext(), Mensajes.SYNC_FINALIZADA, Toast.LENGTH_SHORT).show();
                        closeSync(user);
//                        verificarLogin(user, pass);
                    }

                    Log.i("PDV", pdv + "");
                    Log.i("PRODUCTOS", productos + "");
                    Log.i("PROMOCIONES", promociones + "");
                    Log.i("PRODUCTOS SECUNDARIOS", prod_secundarios + "");
                    Log.i("LINK ONEDRIVE", link + "");
                    Log.i("PROMO VENT", promocional_ventas + "");
                    Log.i("CAMPOS MOD", campos_x_modulos + "");
                    Log.i("TIPO VENTA", tipo_ventas + "");
                    Log.i("TIPO IMPLEMENTACIONES", tipo_implementaciones + "");
                    Log.i("TIPO UNIDADES", tipo_unidades + "");
                    Log.i("TIPO ACTIVIDADES-MPIN", tipo_actividades + "");
                    Log.i("TIPO REGISTRO-MPIN", tipo_registro + "");
                    Log.i("TIPO MODULOS ROLES", modulos_roles + "");
//                    Log.i("PREGUNTAS", preguntas + "");
//                    Log.i("TESTS", tests + "");
//                    Log.i("TIPOS DE EXHIBICIONES", tipos_exhibicion + "");
//                    Log.i("ROTACION", rotacion + "");
//                    Log.i("TAREAS", tareas + "");
//                    Log.i("POPSUGERIDO", popsugerido + "");
//                    Log.i("PRIORITARIO", prioritario + "");
//                    Log.i("COMBO_CANJES", combo_canjes + "");
//                    Log.i("CAUSALES_MCI", causales_mci + "");
//                    Log.i("MATERIALES_ALERTAS", materiales_alertas + "");
//                    Log.i("PDI", pdi + "");
                    Log.i("JUSTIFICACIONES", justificacion + "");
                    Log.i("TRACKING", tracking + "");
                    Log.i("TIPOINVENTARIO", tipo_inventario + "");
                    Log.i("MOTIVOS SUGERIDO", motivos + "");
                    Log.i("MENSAJE", mensaje);
                } catch (Exception e) {
                    Log.i("Receptor", e.getMessage());
                }
            }
        };
        Log.i("DEVICE ID", getDeviceId(getApplicationContext()));
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private void closeSync(String usuario) {

        String name = txtUser.getText().toString();
        mEditor.putString(getString(R.string.name),name);
        if (!this.name.equalsIgnoreCase(name)) {
            mEditor.putString(getString(R.string.cluf), "0");
        }
        mEditor.commit();

        new GuardarLog(LoginActivity.this).saveLog(name, "", "Login correcto");
        //new GuardarLog(LoginActivity.this).saveLog(name, Constantes.NODATA,"", "Login correcto");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();

                guardarFecha();

                User user = new User(name);
                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                sessionManagement.saveSession(user);


                Intent intent = new Intent(LoginActivity.this, ClufActivity.class);
                intent.putExtra("menu", "0");
                intent.putExtra("user", name);
                startActivity(intent);

                btnIngresar.setText(R.string.login);
                btnIngresar.setBackgroundResource(R.drawable.button_active);
                btnIngresar.setTextColor(getResources().getColor(R.color.colorWhite));
                btnIngresar.setEnabled(true);

            }
        }, 5000);   //5 seconds

    }

    private void checkSharedPreferences() {
        name = sharedPref.getString(getString(R.string.name),"");
        txtUser.setText(name);
    }

//    private void stopLocationService() {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                Intent intent = new Intent(LoginActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_STOP_FOREGROUND_SERVICE);
//                startService(intent);
//                //startService(new Intent(getApplicationContext(), LocationService.class));
//            } else {
//                Intent intent = new Intent(LoginActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_STOP_FOREGROUND_SERVICE);
//                startForegroundService(intent);
//            }
//        }catch (Exception e){
//            Log.i("LocationService",e.getMessage());
//        }
//    }

    private boolean isServiceRunning(String serviceName) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = i.next();
            if(runningServiceInfo.service.getClassName().equals(serviceName)){
                serviceRunning = true;
                if(runningServiceInfo.foreground){
                    //service run in foreground
                    Log.i("SERVICE","SI");
                }
            }
        }
        return serviceRunning;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Salir");
        builder.setMessage("¿Desea salir de la aplicación?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cerrar aplicativo
                try {
//                    stopLocationService();
                    moveTaskToBack(true); // I don't think you're looking for this.
                } catch (Exception e){
                    Log.i("Salir", e.getMessage());
                }
            }
        });

        builder.setNeutralButton("NO",null);

        AlertDialog ad = builder.create();
        ad.show();

        Button pButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
        //pButton.setTextColor(Color.rgb(79, 195, 247));
        Button cButton = ad.getButton(DialogInterface.BUTTON_NEUTRAL);
        //cButton.setTextColor(Color.rgb(79, 195, 247));
    }

    /*public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }*/

//    private boolean esFormularioValido() {
//        if (txtUser.getText().toString().trim().isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Debe ingresar un usuario", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (txtPass.getText().toString().trim().isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        return true;
//    }
//

    private boolean esFormularioValido() {
        user = txtUser.getText().toString().toUpperCase().trim();
        pass = txtPass.getText().toString().trim();
        if (user.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar un usuario", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
//    @Override
//    public void onClick(View view) {
//        if (view==btnIngresar) {
//            if (esFormularioValido()) {
////                sincronizar();
//                user = txtUser.getText().toString().toUpperCase().trim();
//                pass = txtPass.getText().toString().trim();
//                verificarLogin(user, pass);
//            }
//        }
//        if (view==btnSincronizar) {
//            sincronizar();
//        }
//    }

    @Override
    public void onClick(View view) {
        if(view==btnIngresar){
            //user = txtUser.getText().toString().toUpperCase().trim();
            //pass = txtPass.getText().toString().trim();
            //verificarLogin(user);
            if (esFormularioValido()) {

                btnIngresar.setText("Cargando, espere por favor...");
                btnIngresar.setBackgroundResource(R.drawable.button_disabled);
                btnIngresar.setTextColor(getResources().getColor(R.color.colorBlack));
                btnIngresar.setEnabled(false);

                user = txtUser.getText().toString().toUpperCase().trim();
                pass = txtPass.getText().toString().trim();

                AuthLogin authLogin = new AuthLogin(getApplicationContext());
                authLogin.login(user, pass, new AuthLogin.LoginCallback() {
                    @Override
                    public void onLoginResponse(boolean login) {
                        if (login)
                        {
                            Log.i("LOGIN", "ENTRA AUTH LOGIN");

                            progressDialog = new ProgressDialog(LoginActivity.this,R.style.MyAlertDialogStyle);
                            progressDialog.setTitle("Sincronizando");
                            progressDialog.setMessage("Validando credenciales, espere un momento...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, user);

                        } else {

                            btnIngresar.setText(R.string.login);
                            btnIngresar.setBackgroundResource(R.drawable.button_active);
                            btnIngresar.setTextColor(getResources().getColor(R.color.colorWhite));
                            btnIngresar.setEnabled(true);

                        }
                    }
                });
            }

        }
    }

//    public void verificarLogin(String user1) {
//        try{
//            handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);
//            boolean resp = handler.verificarLogin(user1);
//            if (resp) {
//                if (pass.equals("1234")) {
//                    String name = txtUser.getText().toString();
//                    mEditor.putString(getString(R.string.name),name);
//                    if (!this.name.equalsIgnoreCase(name)) {
//                        mEditor.putString(getString(R.string.cluf), "0");
//                    }
//                    mEditor.commit();
//                    Intent intent = new Intent(LoginActivity.this, ClufActivity.class);
//                    intent.putExtra("menu", "0");
//                    startActivity(intent);
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                        startService(new Intent(getApplicationContext(), MyService.class));
//                    } else {
//                        startForegroundService(new Intent(getApplicationContext(), MyService.class));
//                    }
//                    startAlarm(true,true);
//                }else{
//                    String name = txtUser.getText().toString();
//                    mEditor.putString(getString(R.string.name),"");
//                    mEditor.commit();
//                    Toast.makeText(this,Mensajes.ERROR_LOGIN,Toast.LENGTH_SHORT).show();
//                }
//            }else{
//                Toast.makeText(this,Mensajes.ERROR_LOGIN,Toast.LENGTH_SHORT).show();
//            }
//        }catch (Exception e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        String userID = sessionManagement.gerSession();

        if (!userID.trim().isEmpty()) {
            moveToMainActivity();
        }
    }

//    public void verificarLogin(String user1, String pass) {
//        try {
//            handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);
//            /*if (VerificarNet.hayConexion(getApplicationContext())) {
//                SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Oper, operator);
//            }*/
//            boolean resp = handler.verificarLogin(user1, pass);
//            if (resp) {
//                String name = txtUser.getText().toString();
//                mEditor.putString(getString(R.string.name),name);
//                if (!this.name.equalsIgnoreCase(name)) {
//                    mEditor.putString(getString(R.string.cluf), "0");
//                }
//                mEditor.commit();
//
//                new GuardarLog(LoginActivity.this).saveLog(name, "", "Login correcto");
//
//                User user = new User(name);
//                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
//                sessionManagement.saveSession(user);
//
//                Intent intent = new Intent(LoginActivity.this, ClufActivity.class);
//                intent.putExtra("menu", "0");
//                intent.putExtra("user", this.user);
//                startActivity(intent);
//
//             /*   if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                    startService(new Intent(getApplicationContext(), MyService.class));
//                } else {
//                    startForegroundService(new Intent(getApplicationContext(), MyService.class));
//                }
//                startAlarm(true,true);*/
//            }else{
//                String name = txtUser.getText().toString();
//                mEditor.putString(getString(R.string.name),"");
//                mEditor.commit();
//                Toast.makeText(this,Mensajes.ERROR_LOGIN,Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

    public void verificarLogin(String user1) {
        try{
            handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);
            /*if (VerificarNet.hayConexion(getApplicationContext())) {
                SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Oper, operator);
            }*/
            boolean resp = handler.verificarLogin(user1);
            if(resp){
                if(pass.equals("1234")){
                    String name = txtUser.getText().toString();
                    mEditor.putString(getString(R.string.name),name);
                    mEditor.commit();

                    User user = new User(name);
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(user);


                    Intent intent = new Intent(LoginActivity.this, ClufActivity.class);
                    intent.putExtra("menu", "0");
                    intent.putExtra("user", this.user);
                    startActivity(intent);

                    guardarFecha();
                    Log.i("sas","vff");

//                    moveToMainActivity();
                }else{
                    String name = txtUser.getText().toString();
                    mEditor.putString(getString(R.string.name),"");
                    mEditor.commit();
                    Toast.makeText(this,Mensajes.ERROR_LOGIN,Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,Mensajes.ERROR_LOGIN,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        falta_salida = sharedPreferences.getBoolean(Constantes.FALTA_SALIDA, false);
        fecha_falta_salida = sharedPreferences.getString(Constantes.FECHA_FALTA_SALIDA, Constantes.NODATA);
    }

    private void moveToMainActivity() {
        new GuardarLog(LoginActivity.this).saveLog(user, "", "Ingreso al app");

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha = date.format(currentLocalTime);

        if (falta_salida && fecha.equalsIgnoreCase(fecha_falta_salida)) {
            Intent intent = new Intent(LoginActivity.this, MenuNavigationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Log.i("MOVE TO MAIN ACTIVITY", "MENU");
        } else {
            Intent intent = new Intent(LoginActivity.this, ClufActivity.class);
            intent.putExtra("menu", "0");
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Log.i("MOVE TO MAIN ACTIVITY", "CLUF");
        }
    }

    private void startAlarm(boolean isNotification, boolean isRepeat) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        // SET TIME HERE
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,20);

        myIntent = new Intent(LoginActivity.this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent, PendingIntent.FLAG_IMMUTABLE);


        if (!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    private void SaveData(String user, String supervisor) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constantes.USER, user);
        editor.putString(Constantes.SUPERVISOR, supervisor);
        editor.commit();
    }

    public void guardarFecha(){

        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constantes.FECHA_SYNC, fechaActual);
        editor.commit();
    }

    private void obtenerMercaderista(String user){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        nombres =  handler.getNombreMercaderista(user);
        editor.putString(Constantes.NOM_MERCADERISTA, nombres);
        editor.commit();
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_ENTER  && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            //"enter" key has ignored
            if ( ((EditText)view).getLineCount() >=1 )
                return true;
        }
        return false;
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bajada, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_bajada) {
            if (VerificarNet.hayConexion(this)) {
                alertDialog();
            } else {
                Snackbar.make(coordinatorLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Toast.makeText(getApplicationContext(),"No hay conexión de Internet.",Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /*public void sincronizacion(View view) {
        if (VerificarNet.hayConexion(this)) {
            alertDialog();
        } else {
            Snackbar.make(coordinatorLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
//                Toast.makeText(getApplicationContext(),"No hay conexión de Internet.",Toast.LENGTH_LONG).show();
        }
    }*/

    /*public void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_sync, null);
        //Title
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setTitle(R.string.app_name);
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alertdialog_sync,null));

        //Traer Views
        user = (EditText)dialogView.findViewById(R.id.txtOperador);
        btnPdv = (ImageButton)dialogView.findViewById(R.id.btnPdvSync);
        btnProductos =(ImageButton)dialogView.findViewById(R.id.btnProductosSync);

        progressBar = (ProgressBar) dialogView.findViewById(R.id.barra);

        btnPdv.setOnClickListener(this);

        btnProductos.setOnClickListener(this);

        builder.setNegativeButton(R.string.salir,null);

        builder.setView(dialogView);
        AlertDialog ad = builder.create();
        ad.show();
    }*/


    @Override
    protected void onResume() {
        super.onResume();
        // Registrar receptor
        IntentFilter filtroSync = new IntentFilter(Intent.ACTION_SYNC);
        LocalBroadcastManager.getInstance(this).registerReceiver(receptorSync, filtroSync);

        new AlertChangeTime(LoginActivity.this);
        new DeveloperOptions().modalDevOptions(LoginActivity.this);
        if (user!=null) {
          //  new UniqueDevice().modalUniqueDevice(LoginActivity.this, user);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar receptor
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receptorSync);
    }

    /*private void mostrarProgreso(boolean mostrar) {
        progressBar.setVisibility(mostrar ? View.VISIBLE : View.GONE);
    }*/

    public static boolean hasPermissions(Context context, String... permissions) {
        Log.i("PERMISOS 2", "PERMISOS 2");
        if (context != null && permissions != null) {
            Log.i("PERMISOS 3", "PERMISOS 3");
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISO", permission + " DENEGADO");
                    return false;
                }
            }
        }
        return true;
    }

    private void showPermissionDialog() {
        Log.i("PERMISOS", "PERMISOS");
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            PERMISSIONS = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.SEND_SMS
            };
            // If you have access to the external storage, do whatever you need
            if (Environment.isExternalStorageManager()){
            // If you don't have access, launch a new activity to show the user the system's dialog
            // to allow access to the external storage
            }else{
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            PERMISSIONS = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.SEND_SMS
            };
        } else {
            PERMISSIONS = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.SEND_SMS
            };
        }

//        String[] PERMISSIONS = {
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
//                android.Manifest.permission.INTERNET,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                android.Manifest.permission.CAMERA,
//                android.Manifest.permission.SEND_SMS
//        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

//    public void sincronizar() {
//        if (VerificarNet.hayConexion(this)) {
//            user = txtUser.getText().toString().toUpperCase().trim();
//            pass = txtPass.getText().toString().trim();
//            if (!user.equals("") && user!=null) {
//                //Sincronizar datos de bajada
//                operator = user.toUpperCase().trim();
//                AsyncTaskBajarOper bajarOper = new AsyncTaskBajarOper();
//                bajarOper.execute();
//            }else{
//                Toast.makeText(getApplicationContext(),Mensajes.SYNC_NO_USER ,Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Snackbar.make(coordinatorLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        }
//    }
        public void sincronizar(View view){
            if (VerificarNet.hayConexion(this)) {
                user = txtUser.getText().toString().toUpperCase().trim();
                pass = txtPass.getText().toString().trim();
                if(!user.equals("") && user!=null){
                    operator = user.toUpperCase().trim();
                    AsyncTaskBajarOper bajarOper = new AsyncTaskBajarOper();
                    bajarOper.execute();
                }else{
                    Toast.makeText(getApplicationContext(),Mensajes.SYNC_NO_USER ,Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(coordinatorLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG).setAction("Action", null).show();//                Toast.makeText(getApplicationContext(),"No hay conexión de Internet.",Toast.LENGTH_LONG).show();
            }
        }

    public void rotarImagen(View view, int rotacion) {
        RotateAnimation animation = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        if (rotacion==1) {
            animation.setDuration(1000);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.RESTART);
            view.startAnimation(animation);
            //view.clearAnimation();
        }else if (rotacion==0) {
            animation.setDuration(1000);
            animation.setRepeatCount(Animation.ABSOLUTE);
            animation.setRepeatMode(Animation.RESTART);
            view.startAnimation(animation);
            //view.clearAnimation();
        }
    }

//    private class AsyncTaskBajarOper extends AsyncTask<String, String, String> {
//
//        private String resp;
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Oper, operator);
//                Thread.sleep(100000);
//                resp = "Slept for 10 seconds";
//            } catch (Exception e) {
//                e.printStackTrace();
//                resp = e.getMessage();
//            }
//            return resp;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            progressDialog.dismiss();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(LoginActivity.this,R.style.MyAlertDialogStyle);
//            progressDialog.setTitle("Sincronizando");
//            progressDialog.setMessage("Validando credenciales, espere un momento...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }
//
//    }

    private class AsyncTaskBajarOper extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
                SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Oper, operator);
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {
            /*
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setContentView(R.layout.alert_dialog_sincronizar);
            progressDialog.setTitle("Sincronizando");
            progressDialog.setMessage("Espere un momento");
            progressDialog.setCancelable(false);
            progressDialog.show();
            */


            AlertDialog.Builder builder =  new AlertDialog.Builder(LoginActivity.this,R.style.alertDialogSincronizar);

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.alert_dialog_sincronizar,null);
            builder.setCancelable(false);
            builder.setView(view);
            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }

    }

}