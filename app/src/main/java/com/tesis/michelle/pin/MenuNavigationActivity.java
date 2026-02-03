package com.tesis.michelle.pin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.SphericalUtil;
import com.tesis.michelle.pin.Clase.Base_Modulos_Roles;
import com.tesis.michelle.pin.Notificaciones.Session.SessionManagement;
import com.tesis.michelle.pin.Utils.AlertChangeTime;
import com.tesis.michelle.pin.Utils.UniqueDevice;
import com.tesis.michelle.pin.databinding.ActivityMenuNavigationBinding;
import com.tesis.michelle.pin.Clase.BasePharmaValue;
import com.tesis.michelle.pin.Clase.BasePortafolioPrioritario;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.MarshMallowPermission;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Contracts.ContractInsertGps;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.ServiceRastreo.LocationService;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Utils.GuardarLog;
import com.tesis.michelle.pin.Utils.ImageMark;
import com.tesis.michelle.pin.Utils.RequestPermissions;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MenuNavigationActivity extends AppCompatActivity{

    private static final int PERMISSIONS_REQUEST = 1;
    private static final int DIALOG_ALERT = 0 ;

    ProgressDialog progressDialog;
    AlertDialog alert_marcacion;

    String ACTION_FILTER = "versiones.luckyec.com.cronometro";
    
    ListViewAdapterExh dataAdapter;
    ListViewAdapterPop dataAdapterpop;
    ArrayList<BasePortafolioPrioritario> selectedItems = new ArrayList<BasePortafolioPrioritario>();

    private ListView listViewPrioritario;
    private ListView listViewPop;
    private TextView tvPrioritario;
    private TextView tvPop;
    private TextView lDistancia;

    private RadioGroup radioGroup;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuNavigationBinding binding;

    NavigationView navigationView;

    private String id_pdv, user, codigo_pdv, channel, rol, punto_venta, fecha, hora, canal, device_id, device_name, perimetro_pdv, id_ruta, tipo_relevo, pref_pdv, operator, fecha_sync;

    DatabaseHelper handler;

    private String tipo_registro, causal, modulo;
    private boolean falta_salida;
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle toggle;

//    String latitude, longitude;
//    final String[] lblLatitud = {"Latitud"};
//    final String[] lblLongitud = {"Longitud"};

//    private boolean interno;

    private BroadcastReceiver receptorSync;
    private int pdvs = 0;
    private int productos = 0;
    private int justificaciones = 0;
    private int tracking = 0;
    private int convenios = 0;
    private int usuarios = 0;
    private int tipo_inventario = 0;
    private int promocional_ventas = 0;
    private int campos_x_modulos = 0;
    private int motivos_sugerido = 0;

    private int prod_secundarios = 0;
    private int link = 0;
    private int preguntas = 0;

    private int tests = 0;

    private int tipos_exhibicion = 0;
    private int promociones = 0;
    private int tipo_ventas = 0;
    private int tipo_registro1 = 0;
    private int tipo_actividades= 0;
    private int tipo_implementaciones = 0;
    private int tipo_unidades = 0;
    private int modulos_roles = 0;
    private int permitido = 0;
    private int suma = 0;
    private int rotacion = 0;
    private int tareas = 0;
    private int popsugerido = 0;
    private int prioritario = 0;
    private int combo_canjes = 0;
    private int causales_mci = 0;
    private int materiales_alertas = 0;
    private int pdi = 0;
    private int sincronizado = 0;

    private boolean show_prioritarios = false;

    //Photo Camera
    public static ImageView imageView;
    private Bitmap bitmap;
    private Bitmap bitmapfinal;
    long lengthbmp;
    int TAKE_PHOTO_CODE = 0;
    private int PICK_IMAGE_REQUEST = 1;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private final String CARPETA_RAIZ="bassaApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Jornada";
    String path;
    ImageButton btnCamera;
    Button btnGallery;
    MarshMallowPermission marshMallowPermission;

    BasePharmaValue pdv = new BasePharmaValue();

    private double latitud_pdv, longitud_pdv, distance_pdv;
    private double latitud = 0, longitud = 0, distance;
    private int contador = 0;
    private String causal_fuera_pdv = "";
    private boolean salida = false;

    ActivityResultLauncher<Intent> startActivityIntent;
    ActivityResultLauncher<Intent> startActivityIntent2;
    String current = null;

    private boolean tiene_entrada = false;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int LOCATION_INTERVAL = 0; //3Minutos
    private static final float LOCATION_DISTANCE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

//            interno = extras.getBoolean(Constantes.INTERNO, false);
            show_prioritarios = extras.getBoolean(Constantes.SHOW_PRIORITARIOS, false);
            modulo = extras.getString(Constantes.MODULO, Constantes.NODATA);
//            channel = extras.getString(Constantes.CANAL);
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constantes.MODULO, modulo);
            editor.commit();

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        this.falta_salida = false;
        Log.i("1. FALTA SALIDA ON CREATE", String.valueOf(falta_salida));
        obtenerFecha();
        LoadData();
        Log.i("2. FALTA SALIDA ON CREATE", String.valueOf(falta_salida));
        registerReceiver(mMessageReceiver, new IntentFilter(Constantes.ACTION_GPS_UPDATE),RECEIVER_EXPORTED);
        new DeveloperOptions().modalDevOptions(MenuNavigationActivity.this);
        RequestPermissions requestPermissions = new RequestPermissions(getApplicationContext(), MenuNavigationActivity.this);
        requestPermissions.showPermissionDialog();

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerLayout = navigationView.getHeaderView(0);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_osa,
                R.id.nav_precios,
                R.id.nav_sod,
                R.id.nav_promo,
                R.id.nav_codificados,
                R.id.nav_onPacks,
                R.id.nav_exh,
                R.id.nav_logros,
                R.id.nav_sms,
                R.id.nav_tareas,
                R.id.nav_inv,
                R.id.nav_canjes,
                R.id.nav_mci,
                R.id.nav_ejecucion_materiales,
                R.id.nav_pdi,
                R.id.nav_impulso,
                R.id.nav_evidencias,
                R.id.nav_tracking,
                R.id.nav_convenios)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView tvCodigo = (TextView) headerLayout.findViewById(R.id.nav_header_codigo);
        TextView tvPdv = (TextView) headerLayout.findViewById(R.id.nav_header_pdv2);
        lDistancia = (TextView) headerLayout.findViewById(R.id.nav_header_distancia);

        tvCodigo.setText("Código: " + codigo_pdv);
        tvPdv.setText("Nombre del Barrio: " + punto_venta);

        //Verificar los permisos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
            return;
        } else {
//            locationStart();
            //Si los permisos estan otorgados, llamar al evento onClick del boton
            //clickButton();
        }

        // Check GPS is enabled
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            finish();
        }

        getLocation();

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
//        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        if (permission == PackageManager.PERMISSION_GRANTED) {
//            startLocationService();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST);
//        }

        if (!isMyServiceRunning(LocationService.class)) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                startService(new Intent(getApplicationContext(), LocationService.class));
            } else {
                startForegroundService(new Intent(getApplicationContext(), LocationService.class));
            }
        }

        receptorSync = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                try {
                    final String mensaje = intent.getStringExtra("extra.mensaje");
                    if (mensaje.equalsIgnoreCase(Mensajes.SYNC_FINALIZADA_PDV) ||
                            mensaje.equalsIgnoreCase(Mensajes.SYNC_NOREQUERIDA + "PuntoVenta")) {
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_floo, operator);
                        progressDialog.setMessage("Descargando productos, espere un momento...");
                        pdvs = 1;
                        permitido = 1;
                    } else if (mensaje.equals("No se encontraron registros en el servidor para este usuario.")) {
                        if (progressDialog!=null && progressDialog.isShowing()) {
                            Log.i("PROGRESS_DIALOG", "CIERRE");
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "No se encontraron registros en el servidor para este usuario.", Toast.LENGTH_SHORT).show();
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROD) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Productos")) {
                        productos = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_preg, operator);
//                        progressDialog.setMessage("Descargando test, espere un momento...");
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_promociones, operator);
                        progressDialog.setMessage("Descargando promociones, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROMO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Promociones") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        promociones = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_ventas, operator);
                        progressDialog.setMessage("Descargando tipo ventas, espere un momento...");
                    }
                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_VENTAS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoVentas") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_ventas = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_registro, operator);
                        progressDialog.setMessage("Descargando tipos de registro, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_REGISTRO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoRegistro") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_registro1 = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_actividades, operator);
                        progressDialog.setMessage("Descargando tipo de actividades, espere un momento...");
                    }
                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_ACTVIDADES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoActividades") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_actividades = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_implementaciones, operator);
                        progressDialog.setMessage("Descargando tipo de implementaciones, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_IMPLEMENTACIONES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoImplementaciones") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_implementaciones = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_unidades, operator);
                        progressDialog.setMessage("Descargando tipos de unidades, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_UNIDADES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoUnidades") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_unidades = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_modulo_roles, operator);
                        progressDialog.setMessage("Descargando modulos roles, espere un momento...");
                    }
                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_MODELOS_ROLES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "ModulosRoles") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        modulos_roles = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_prod_secundarios, operator);
                        progressDialog.setMessage("Descargando productos secundarios, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROD_SEC) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Productos Secundarios") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        prod_secundarios = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_link_onedrive, operator);
                        progressDialog.setMessage("Descargando link de Onedrive, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_LINK) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Link Onedrive") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        link = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_justificaciones, operator);
                        progressDialog.setMessage("Descargando justificaciones, espere un momento...");
                    }

//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PREG) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Preguntas")) {
//                        preguntas = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tests, operator);
//                        progressDialog.setMessage("Descargando tests, espere un momento...");
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TESTS) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tests")) {
//                        tests = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_justificaciones, operator);
//                        progressDialog.setMessage("Descargando tipos de exhibiciones, espere un momento...");
//                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_JUST) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Justificaciones") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        justificaciones = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tracking, operator);
                        progressDialog.setMessage("Descargando tracking, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TRA) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tracking") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tracking = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_convenios, operator);
                        progressDialog.setMessage("Descargando Convenios, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CONVENIOS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Convenios") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        convenios = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_user, operator);
                        progressDialog.setMessage("Descargando Usuarios, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_USER) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Usuarios")) {
                        usuarios = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_inventario, operator);
                        progressDialog.setMessage("Descargando Tipo inventario, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_INVENTARIO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoInventario")) {
                        tipo_inventario = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_promocional_ventas, operator);
                        progressDialog.setMessage("Descargando Motivos sugerido, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PROMO_VENTAS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Promocional ventas") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "Promociones")) {
                        promocional_ventas = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_campos_x_modulos, operator);
                        progressDialog.setMessage("Descargando Campos por modulo, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CAMPOS_X_MODULOS) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Campos x modulos") || mensaje.equals(Mensajes.SYNC_NOREGISTROS + "Campos x modulos")) {
                        campos_x_modulos = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_motivos_sugerido, operator);
                        progressDialog.setMessage("Descargando Tipo Novedades, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_MOTIVOS_SUGERIDO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "MotivosSugerido") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        motivos_sugerido = 1;
                        progressDialog.dismiss();
                        new GuardarLog(MenuNavigationActivity.this).saveLog(operator, "", "Descarga de información");
                        Toast.makeText(getApplicationContext(), Mensajes.SYNC_FINALIZADA, Toast.LENGTH_SHORT).show();
//                        verificarLogin(user, pass);
                    }


//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_EXH) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tipo Exhibicion")) {
//                        tipos_exhibicion = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_rotacion, operator);
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_ROTACION) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Rotacion")) {
//                        rotacion = 1;
////                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tareas, operator);
////                        progressDialog.setMessage("Descargando tareas, espere un momento...");
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_pop_sugerido, operator);
//                    }
//
////                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TAREAS) ||
////                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tarea")) {
////                        tareas = 1;
////                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_pop_sugerido, operator);
////                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_POPSUGERIDO) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Pop_sugerido")) {
//                        popsugerido = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_prioritario, operator);
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PRIORITARIO) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Portafolio Prioritario")) {
//                        prioritario = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_combo_canjes, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_COMBO_CANJES) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Combo Canjes")) {
//                        combo_canjes = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_causales_mci, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CAUSALES_MCI) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Causales MCI")) {
//                        causales_mci = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_causales_osa, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_CAUSALES_OSA) ||
//                        mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Causales OSA")) {
//                        causales_mci = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_materiales_alertas, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_MATERIALES_ALERTAS) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Materiales Alertas")) {
//                        materiales_alertas = 1;
//                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_pdi, operator);
//                        progressDialog.setMessage("Validando información, espere un momento...");
//                    }
//
//                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_PDI) ||
//                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "PDI")) {
//                        pdi = 1;
//                        progressDialog.dismiss();
//                        new GuardarLog(PuntosListActivity.this).saveLog(operator, "", "Descarga de información");
//                        Toast.makeText(getApplicationContext(), Mensajes.SYNC_FINALIZADA, Toast.LENGTH_SHORT).show();
//                    }

                    Log.i("PDV", pdvs + "");
                    Log.i("PRODUCTOS", productos + "");
                    Log.i("JUSTIFICACIONES", justificaciones + "");
                    Log.i("PRODUCTOS SECUNDARIOS", prod_secundarios + "");
                    Log.i("LINK ONEDRIVE", link + "");
                    Log.i("TRACKING", tracking + "");
                    Log.i("CONVENIOS", convenios + "");
                    Log.i("USUARIOS", usuarios + "");
                    Log.i("MOTIVOS", motivos_sugerido + "");
                    Log.i("TIPOINVENTARIO", tipo_inventario + "");
                    Log.i("PROMO VENT", promocional_ventas + "");
                    Log.i("CAMPOS MOD", campos_x_modulos + "");
                    Log.i("ACTIVIDADES", tipo_actividades + "");
                    Log.i("MODULOS ROLES", modulos_roles + "");
//                    Log.i("PREGUNTAS", preguntas + "");
//                    Log.i("TESTS", tests + "");
//                    Log.i("TIPOS EXHIBICION", tipos_exhibicion + "");
//                    Log.i("PROMOCIONES", promociones + "");
//                    Log.i("ROTACION", rotacion + "");
//                    Log.i("TAREAS", tareas + "");
//                    Log.i("POPSUGERIDO", popsugerido + "");
//                    Log.i("PRIORITARIO", prioritario + "");
//                    Log.i("COMBO_CANJES", combo_canjes + "");
//                    Log.i("CAUSALES_MCI", causales_mci + "");
//                    Log.i("MATERIALES_ALERTAS", materiales_alertas + "");
//                    Log.i("PDI", pdi + "");
                    Log.i("MENSAJE", mensaje);
                } catch (Exception e) {
                    Log.i("Receptor", e.getMessage());
                }
            }
        };
//        pdv = handler.getPdv(codigo_pdv);
//
//        distance_pdv = Double.parseDouble(pdv.getDistancia());
//        latitud_pdv = Double.parseDouble(pdv.getLatitud());
//        longitud_pdv = Double.parseDouble(pdv.getLongitud());
//        entrada();
//        if (interno) {
//            validadorPDV();
//            tipo_relevo = "Interno";
//        }

        if (modulo != null && modulo.equalsIgnoreCase(Constantes.MODULO_PUNTOS_PRINCIPAL)) {
            validadorPDV();
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i("fusedLocationClient", "ENTRA");
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    if (modulo != null && modulo.equalsIgnoreCase(Constantes.MODULO_PUNTOS_PRINCIPAL)) {
                        distancia(latitud, longitud);
                    }
                }
            }
        });

//        if (show_prioritarios) {
//            mostrarPopup("Información");
//        }

        device_id = getDeviceId(getApplicationContext());
        device_name = getDeviceName();
        Log.i("DEVICE ID", device_id);
        Log.i("DEVICE NAME", device_name);

        hideItem(channel);
        checkFecha();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    Log.d("MenuNavActivity", "Drawer abierto, cerrando Drawer.");
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    Log.d("MenuNavActivity", "handleOnBackPressed() llamado para Salir PDV.");
                    mostrarDialogoSalidaPDV();
                }
            }
        });
    }

    private void mostrarDialogoSalidaPDV() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Salir PDV");
        builder.setMessage("¿Desea salir PDV?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("FALTA SALIDA", String.valueOf(falta_salida));
                if (falta_salida) {
                    alertDialog("Salida");
                } else {
                    closeActivity();
                }
            }
        });

        builder.setNeutralButton("NO", null);

        AlertDialog ad = builder.create();
        ad.show();
    }

    private void getLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try{
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    locationManager.removeUpdates(locationListener);
                }catch (Exception e) {
                    Log.e("MenuNavigationActivity", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override
            public void onProviderEnabled(String provider) { }
            @Override
            public void onProviderDisabled(String provider) {}
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);
        } catch (SecurityException ex) {
            Log.i("POST RECYCLER", "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d("POST RECYCLER", "network provider does not exist, " + ex.getMessage());
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);
        } catch (SecurityException ex) {
            Log.i("POST RECYCLER", "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d("POST RECYCLER", "gps provider does not exist " + ex.getMessage());
        }
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public void validadorPDV() {
        try {
            if (!tiene_entrada) {

                // Toast.makeText(this, "prueba", Toast.LENGTH_SHORT).show();
             //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                progressDialog = new ProgressDialog(MenuNavigationActivity.this, R.style.MyAlertDialogStyle);
                progressDialog.setTitle("INFORMATIVO");
                progressDialog.setMessage("Cargando información");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ACTIVITY", "ON RESUME");
        new DeveloperOptions().modalDevOptions(MenuNavigationActivity.this);
//        new UniqueDevice().modalUniqueDevice(MenuActivity.this, user);
        registerReceiver(mMessageReceiver, new IntentFilter(Constantes.ACTION_GPS_UPDATE),RECEIVER_EXPORTED);
//        if (interno) {
//            validadorPDV();
//        }

        IntentFilter filtroSync = new IntentFilter(Intent.ACTION_SYNC);
        LocalBroadcastManager.getInstance(this).registerReceiver(receptorSync, filtroSync);
        new AlertChangeTime(MenuNavigationActivity.this);
        new DeveloperOptions().modalDevOptions(MenuNavigationActivity.this);
        new UniqueDevice().modalUniqueDevice(MenuNavigationActivity.this, operator);    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
    }

    protected BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if(intent.hasExtra(Constantes.LATITUD)){
                latitud = intent.getDoubleExtra(Constantes.LATITUD, 0);
                longitud = intent.getDoubleExtra(Constantes.LONGITUD, 0);
                distancia(latitud, longitud);
            }
        }
    };

    private void distancia(double latitud, double longitud) {
        pdv = handler.getPdv(codigo_pdv);
        distance_pdv = Double.parseDouble(pdv.getDistancia());
        latitud_pdv = Double.parseDouble(pdv.getLatitud());
        longitud_pdv = Double.parseDouble(pdv.getLongitud());

        MarkerOptions place1 = new MarkerOptions().position(new LatLng(latitud_pdv, longitud_pdv)).title("Posicion actual");
        MarkerOptions place2 = new MarkerOptions().position(new LatLng(latitud, longitud)).title("Posicion PDV");

        distance = SphericalUtil.computeDistanceBetween(place1.getPosition(), place2.getPosition());
        distance = (int)Math.round(distance);

        String textDistancia = "";
        if (distance >= distance_pdv) {
            contador = 0;
            causal_fuera_pdv = " - SALIDA FUERA DEL PERÍMETRO A " + distance + " MTS. DEL PDV";
            textDistancia = "Fuera del rango del barrio: " + distance + " mts.";
            if (alert_marcacion!=null && alert_marcacion.isShowing() && !salida) {
                alert_marcacion.dismiss();
            }
            tipo_relevo = "Externo";
        } else {
            textDistancia = "Dentro del rango del barrio: " + distance + " mts.";
            causal_fuera_pdv = "";
            if (contador == 0 && modulo != null && modulo.equalsIgnoreCase(Constantes.MODULO_PUNTOS_PRINCIPAL)) {
                entrada();
            }
            tipo_relevo = "Interno";
            contador++;
        }
        lDistancia.setText(textDistancia);
        try {
            Thread.sleep(2000);
            if (progressDialog!=null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void hideItem(String canal) {


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();


        // Ocultar todos los módulos por defecto
        ocultarTodosLosModulos(nav_Menu);

        // Obtener los módulos permitidos para este rol
        ArrayList<String> modulosPermitidos = handler.getModulosPermitidosPorRol(rol);
        // Mostrar solo los módulos permitidos
        // Mostrar solo los módulos permitidos
        for (String nombreModulo : modulosPermitidos) {
            mostrarModuloSegunNombre(nav_Menu, nombreModulo);
        }

        Log.i("CANAL", canal);
        if (canal.equalsIgnoreCase("MAYORISTA")) {
//            nav_Menu.findItem(R.id.nav_impulso).setVisible(false);
        }
        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
//            nav_Menu.findItem(R.id.nav_inv).setVisible(false);
            nav_Menu.findItem(R.id.nav_impulso).setVisible(false);
            nav_Menu.findItem(R.id.nav_sms).setVisible(false);
         //   nav_Menu.findItem(R.id.nav_evidencias).setVisible(true); //preguntar esta linea a ROMINA
        }
        if (user.toUpperCase().contains("IMPULSO")) {
            nav_Menu.findItem(R.id.nav_osa).setVisible(false);
            nav_Menu.findItem(R.id.nav_precios).setVisible(false);
            nav_Menu.findItem(R.id.nav_sod).setVisible(false);
            nav_Menu.findItem(R.id.nav_promo).setVisible(false);
            nav_Menu.findItem(R.id.nav_exh).setVisible(false);
            nav_Menu.findItem(R.id.nav_sms).setVisible(false);
            nav_Menu.findItem(R.id.nav_inv).setVisible(false);
            nav_Menu.findItem(R.id.nav_canjes).setVisible(false);
            nav_Menu.findItem(R.id.nav_evidencias).setVisible(false);
        }
        if (canal.equalsIgnoreCase("IMPULSO")) {
            nav_Menu.findItem(R.id.nav_osa).setVisible(false);
            nav_Menu.findItem(R.id.nav_precios).setVisible(false);
            nav_Menu.findItem(R.id.nav_sod).setVisible(false);
            nav_Menu.findItem(R.id.nav_promo).setVisible(false);
            nav_Menu.findItem(R.id.nav_exh).setVisible(false);
            nav_Menu.findItem(R.id.nav_sms).setVisible(false);
            nav_Menu.findItem(R.id.nav_tareas).setVisible(false);
            nav_Menu.findItem(R.id.nav_inv).setVisible(false);
            nav_Menu.findItem(R.id.nav_canjes).setVisible(false);
            nav_Menu.findItem(R.id.nav_mci).setVisible(false);
         //   nav_Menu.findItem(R.id.nav_ejecucion_materiales).setVisible(false);
            nav_Menu.findItem(R.id.nav_evidencias).setVisible(false);
        }
    }

    private void ocultarTodosLosModulos(Menu nav_Menu) {
        // Inicialmente ocultar todos los módulos configurables
        nav_Menu.findItem(R.id.nav_home).setVisible(false);
        nav_Menu.findItem(R.id.nav_ventas).setVisible(false);
        nav_Menu.findItem(R.id.nav_evidencias).setVisible(false);
        nav_Menu.findItem(R.id.nav_novedades).setVisible(false);
        nav_Menu.findItem(R.id.nav_muestras_medicas).setVisible(false);
        nav_Menu.findItem(R.id.nav_probadores).setVisible(false);
        nav_Menu.findItem(R.id.nav_inv).setVisible(false);
        nav_Menu.findItem(R.id.nav_precios).setVisible(false);
        nav_Menu.findItem(R.id.nav_sod).setVisible(false);
        nav_Menu.findItem(R.id.nav_promo).setVisible(false);
        nav_Menu.findItem(R.id.nav_onPacks).setVisible(false);
        nav_Menu.findItem(R.id.nav_exh).setVisible(false);
        nav_Menu.findItem(R.id.nav_logros).setVisible(false);
        nav_Menu.findItem(R.id.nav_sms).setVisible(false);
        nav_Menu.findItem(R.id.nav_tareas).setVisible(false);
        nav_Menu.findItem(R.id.nav_canjes).setVisible(false);
        nav_Menu.findItem(R.id.nav_codificados).setVisible(false);
        nav_Menu.findItem(R.id.nav_pdi).setVisible(false);
        nav_Menu.findItem(R.id.nav_mci).setVisible(false);
        nav_Menu.findItem(R.id.nav_tracking).setVisible(false);
        nav_Menu.findItem(R.id.nav_convenios).setVisible(false);
        nav_Menu.findItem(R.id.nav_materiales_recibidos).setVisible(false);
        nav_Menu.findItem(R.id.nav_ejecucion_materiales).setVisible(false);
        nav_Menu.findItem(R.id.nav_impulso).setVisible(false);
        nav_Menu.findItem(R.id.nav_encuesta_app).setVisible(false);
    }
    private void mostrarModuloSegunNombre(Menu nav_Menu, String nombreModuloOriginal) {
        if (nombreModuloOriginal == null || nombreModuloOriginal.trim().isEmpty()) {
            return;
        }

        String nombreUpper = nombreModuloOriginal.toUpperCase().trim();

        // Mapeo específico para los módulos de tu servidor
        if (nombreUpper.contains("VENTAS") || nombreUpper.equals("VENTA")) {
            nav_Menu.findItem(R.id.nav_ventas).setVisible(true);
            Log.i("MODULO_ACTIVADO", "VENTAS -> nav_ventas");
        }
        else if (nombreUpper.contains("PROBADORES") || nombreUpper.contains("PROBADOR")) {
            nav_Menu.findItem(R.id.nav_probadores).setVisible(true);
            Log.i("MODULO_ACTIVADO", "PROBADORES -> nav_probadores");
        }
        else if (nombreUpper.contains("GESTION") ||
                nombreUpper.contains("EVIDENCIAS") ||
                nombreUpper.equals("GESTION EN PDV")) {
            nav_Menu.findItem(R.id.nav_evidencias).setVisible(true);
            Log.i("MODULO_ACTIVADO", "GESTION EN PDV -> nav_evidencias");
        }
        else if (nombreUpper.contains("NOVEDADES") || nombreUpper.contains("NOVEDAD")) {
            nav_Menu.findItem(R.id.nav_novedades).setVisible(true);
            Log.i("MODULO_ACTIVADO", "NOVEDADES -> nav_novedades");
        }
        else if (nombreUpper.contains("MUESTRAS") && nombreUpper.contains("MEDICAS")) {
            nav_Menu.findItem(R.id.nav_muestras_medicas).setVisible(true);
            Log.i("MODULO_ACTIVADO", "MUESTRAS MEDICAS -> nav_muestras_medicas");
        }
        else if (nombreUpper.contains("MUESTRAS")) {
            nav_Menu.findItem(R.id.nav_muestras_medicas).setVisible(true);
            Log.i("MODULO_ACTIVADO", "MUESTRAS -> nav_muestras_medicas");
        }
        else if (nombreUpper.contains("INVENTARIO") ||
                nombreUpper.contains("SUGERIDO") ||
                nombreUpper.equals("INVENTARIO+SUGERIDO")) {
            nav_Menu.findItem(R.id.nav_inv).setVisible(true);
            Log.i("MODULO_ACTIVADO", "INVENTARIO+SUGERIDO -> nav_inv");
        }
        else if (nombreUpper.contains("PRECIOS") || nombreUpper.contains("PRECIO")) {
            nav_Menu.findItem(R.id.nav_precios).setVisible(true);
            Log.i("MODULO_ACTIVADO", "PRECIOS -> nav_precios");
        }
        else if (nombreUpper.contains("PROMOCIONES") ||
                nombreUpper.contains("PROMO")) {
            nav_Menu.findItem(R.id.nav_promo).setVisible(true);
            Log.i("MODULO_ACTIVADO", "PROMOCIONES -> nav_promo");
        }
        else if (nombreUpper.contains("EXHIBICIONES") ||
                nombreUpper.contains("EXH")) {
            nav_Menu.findItem(R.id.nav_exh).setVisible(true);
            Log.i("MODULO_ACTIVADO", "EXHIBICIONES -> nav_exh");
        }
        else if (nombreUpper.contains("ENCUESTA") ||
                nombreUpper.equals("ENCUESTA APP")) {
            nav_Menu.findItem(R.id.nav_encuesta_app).setVisible(true);
            Log.i("MODULO_ACTIVADO", "ENCUESTA APP -> nav_encuesta_app");
        }
        else if (nombreUpper.contains("HOME")) {
            nav_Menu.findItem(R.id.nav_home).setVisible(true);
            Log.i("MODULO_ACTIVADO", "HOME -> nav_home");
        }
        else if (nombreUpper.contains("SOD") || nombreUpper.contains("CARAS")) {
            nav_Menu.findItem(R.id.nav_sod).setVisible(true);
            Log.i("MODULO_ACTIVADO", "SOD -> nav_sod");
        }
        else if (nombreUpper.contains("ONPACK")) {
            nav_Menu.findItem(R.id.nav_onPacks).setVisible(true);
            Log.i("MODULO_ACTIVADO", "ONPACKS -> nav_onPacks");
        }
        else if (nombreUpper.contains("LOGRO")) {
            nav_Menu.findItem(R.id.nav_logros).setVisible(true);
            Log.i("MODULO_ACTIVADO", "LOGROS -> nav_logros");
        }
        else if (nombreUpper.contains("SMS")) {
            nav_Menu.findItem(R.id.nav_sms).setVisible(true);
            Log.i("MODULO_ACTIVADO", "SMS -> nav_sms");
        }
        else if (nombreUpper.contains("TAREA")) {
            nav_Menu.findItem(R.id.nav_tareas).setVisible(true);
            Log.i("MODULO_ACTIVADO", "TAREAS -> nav_tareas");
        }
        else if (nombreUpper.contains("CANJE")) {
            nav_Menu.findItem(R.id.nav_canjes).setVisible(true);
            Log.i("MODULO_ACTIVADO", "CANJES -> nav_canjes");
        }
        else if (nombreUpper.contains("CODIFICAD")) {
            nav_Menu.findItem(R.id.nav_codificados).setVisible(true);
            Log.i("MODULO_ACTIVADO", "CODIFICADOS -> nav_codificados");
        }
        else if (nombreUpper.contains("PDI")) {
            nav_Menu.findItem(R.id.nav_pdi).setVisible(true);
            Log.i("MODULO_ACTIVADO", "PDI -> nav_pdi");
        }
        else if (nombreUpper.contains("MCI")) {
            nav_Menu.findItem(R.id.nav_mci).setVisible(true);
            Log.i("MODULO_ACTIVADO", "MCI -> nav_mci");
        }
        else if (nombreUpper.contains("TRACKING")) {
            nav_Menu.findItem(R.id.nav_tracking).setVisible(true);
            Log.i("MODULO_ACTIVADO", "TRACKING -> nav_tracking");
        }
        else if (nombreUpper.contains("CONVENIO")) {
            nav_Menu.findItem(R.id.nav_convenios).setVisible(true);
            Log.i("MODULO_ACTIVADO", "CONVENIOS -> nav_convenios");
        }
        else if (nombreUpper.contains("MATERIAL")) {
            nav_Menu.findItem(R.id.nav_materiales_recibidos).setVisible(true);
            Log.i("MODULO_ACTIVADO", "MATERIALES -> nav_materiales_recibidos");
        }
        else if (nombreUpper.contains("EJECUCION")) {
            nav_Menu.findItem(R.id.nav_ejecucion_materiales).setVisible(true);
            Log.i("MODULO_ACTIVADO", "EJECUCION -> nav_ejecucion_materiales");
        }
        else if (nombreUpper.contains("IMPULSO")) {
            nav_Menu.findItem(R.id.nav_impulso).setVisible(true);
            Log.i("MODULO_ACTIVADO", "IMPULSO -> nav_impulso");
        }
        else {
            Log.w("MODULO_NO_RECONOCIDO", "Módulo no mapeado: " + nombreModuloOriginal);
        }
    }
    private void entrada() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha = date.format(currentLocalTime);


        boolean tiene_entrada = handler.tieneEntrada(codigo_pdv, user, fecha);
        if (!tiene_entrada) {
            alertDialog("Entrada");
        }

        /*if (!tiene_entrada && interno) {
            alertDialog("Entrada");
        }/* else {
            mostrarPopup("Información");
        }*/
    }

//    private void startLocationService() {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                Intent intent = new Intent(MenuNavigationActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_START_FOREGROUND_SERVICE);
//                startService(intent);
//                Log.i("ENTRA1","ENTRA1");
//                //startService(new Intent(getApplicationContext(), LocationService.class));
//            } else {
//                Intent intent = new Intent(MenuNavigationActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_START_FOREGROUND_SERVICE);
//                startForegroundService(intent);
//                Log.i("ENTRA2","ENTRA2");
//            }
//        }catch (Exception e){
//            Log.i("LocationService",e.getMessage());
//        }
//    }
//
//    private void stopLocationService() {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                Intent intent = new Intent(MenuNavigationActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_STOP_FOREGROUND_SERVICE);
//                startService(intent);
//                //startService(new Intent(getApplicationContext(), LocationService.class));
//            } else {
//                Intent intent = new Intent(MenuNavigationActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_STOP_FOREGROUND_SERVICE);
//                startForegroundService(intent);
//            }
//        } catch (Exception e){
//            Log.i("LocationService",e.getMessage());
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.nav_herramientas_exh) {
//            Intent in = new Intent(MenuNavigationActivity.this, MenuHerramientasActivity.class);
//            in.putExtra(Constantes.CODE, codigo_pdv);
//            in.putExtra(Constantes.SUPERVISOR, user);
//            startActivity(in);
//            return true;
//        }
//        return false;
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

        if (id == R.id.action_sync_subida1) {
            if (VerificarNet.hayConexion(this)) {
                try {
                    new GuardarLog(MenuNavigationActivity.this).saveLog(user, "", "Sincronización de información");
                    SyncAdapter.sincronizarAhora(this, true, Constantes.SUBIR_TODO, null);
                    Snackbar.make(findViewById(R.id.drawer_layout), Mensajes.ON_SYNC_UP, Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Snackbar.make(findViewById(R.id.drawer_layout), "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(MenuNavigationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.drawer_layout), Mensajes.ERROR_RED, Snackbar.LENGTH_LONG).show();
            }
            return true;
        }

        if (item.getItemId() == R.id.action_sync_bajada) {
            if (VerificarNet.hayConexion(this)) {
                if (!operator.equals("")) {
//                    AsyncTaskBajarOper bajarOper = new AsyncTaskBajarOper();
//                    bajarOper.execute();
//                    Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC,Toast.LENGTH_SHORT).show();
                    progressDialog = new ProgressDialog(MenuNavigationActivity.this,R.style.MyAlertDialogStyle);
                    progressDialog.setTitle("Sincronizando");
                    progressDialog.setMessage("Validando credenciales, espere un momento...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, operator);

                }else{
                    Toast.makeText(getApplicationContext(),Mensajes.SYNC_NO_USER ,Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(drawerLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG).setAction("Action", null).show();//                Toast.makeText(getApplicationContext(),"No hay conexión de Internet.",Toast.LENGTH_SHORT).show();
            }
        }


        if (id == R.id.action_log) {
            Intent intent = new Intent(MenuNavigationActivity.this, HistorialActivity.class);
            startActivity(intent);
            return true;
        }
/*
        if (id == R.id.action_cluf) {
            Intent intent = new Intent(MenuNavigationActivity.this, ClufActivity.class);
            intent.putExtra("menu", "1");
            startActivity(intent);
            return true;
        }
*/
        if (id == R.id.action_version) {
            mostrarVersion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mostrarVersion() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.action_version))
                .setMessage(getString(R.string.user) + ": " + user + "\n" +
                        getString(R.string.version))
                .setPositiveButton("ACEPTAR", null)
                .show();
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        user = sharedPreferences.getString(Constantes.USER,Constantes.NODATA);
        operator = sharedPreferences.getString(Constantes.USER, Constantes.NODATA);
        fecha_sync = sharedPreferences.getString(Constantes.FECHA_SYNC,Constantes.NODATA);
        id_pdv = sharedPreferences.getString(Constantes.PHARMA_ID, Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        rol = sharedPreferences.getString(Constantes.ROLNUEVO, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        canal =sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        channel =sharedPreferences.getString(Constantes.CANAL,Constantes.NODATA);
        falta_salida = sharedPreferences.getBoolean(Constantes.FALTA_SALIDA, false);
        Log.i("SHARED PREFERENCE", String.valueOf(falta_salida));
        Log.i("michi", String.valueOf(rol)); // si lo traeee
        id_ruta = sharedPreferences.getString(Constantes.ID_RUTA, Constantes.NODATA);
        modulo = sharedPreferences.getString(Constantes.MODULO, Constantes.NODATA);
        tiene_entrada = handler.tieneEntrada(codigo_pdv, user, fecha);
        if (tiene_entrada) {
            falta_salida = handler.faltaSalida(codigo_pdv, user, fecha);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void mostrarPopup(final String informacion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_popup, null);
        //Title
        //builder.setIcon(android.R.drawable.ic_menu_set_as);
        //builder.setTitle(tipo_registro.toUpperCase());
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alertdialog_popup,null));

        TextView tittle = (TextView) dialogView.findViewById(R.id.lblModelo);
//        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.radio);

        tittle.setText(informacion.toUpperCase());

        TextView lblSegmentacion=(TextView) dialogView.findViewById(R.id.lblSegmentacion);
        TextView lblCompras=(TextView) dialogView.findViewById(R.id.lblCompras);
        listViewPrioritario = (ListView) dialogView.findViewById(R.id.listView);
        listViewPop = (ListView) dialogView.findViewById(R.id.listViewPop);
        tvPrioritario = (TextView) dialogView.findViewById(R.id.data_empty);
        tvPop = (TextView) dialogView.findViewById(R.id.data_empty2);

        showListView();
        showListViewPop();

        String Segmentacion = handler.getSegmentacionMenu();
        String Compras = handler.getComprasMenu();

        lblSegmentacion.setText(Segmentacion);
        lblCompras.setText(Compras);

        listViewPrioritario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ENTRA", "ENTRA");
                String selectedItemSku = ((TextView)view.findViewById(R.id.lblSku)).getText().toString();
                // String selectedItemNumExh = ((EditText)view.findViewById(R.id.lblNumExh)).getText().toString();

                BasePortafolioPrioritario listPort = new BasePortafolioPrioritario();
                listPort.setSku(selectedItemSku);
                // listSku.setNumExh(selectedItemNumExh);

                for (int j = 0; j < selectedItems.size(); j++) {
                    if (selectedItems.get(j).getSku().contains(selectedItemSku)) {
                        selectedItems.remove(j);
                        //  ((CheckBox) view.findViewById(R.id.respuesta)).setChecked(false);
                    }else{
                        // selectedItems.add(listSku);
                        //  ((CheckBox) view.findViewById(R.id.respuesta)).setChecked(true);
                    }
                }
            }
        });

        builder.setPositiveButton(R.string.next,null);
        builder.setNeutralButton(R.string.cancel,null);

        //builder.setNegativeButton(R.string.cancel,null);

        builder.setView(dialogView);
        AlertDialog ad = builder.create();
        ad.show();

        Button pButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
        ////pButton.setTextColor(Color.rgb(79,195,247));
        //pButton.setBackgroundColor(Color.rgb(79,195,247));
        pButton.setPadding(4,2,4,2);
        Button cButton = ad.getButton(DialogInterface.BUTTON_NEUTRAL);
        ////cButton.setTextColor(Color.rgb(79,195,247));
        //cButton.setBackgroundColor(Color.rgb(79,195,247));
        cButton.setPadding(4,2,4,2);
    }

    public void showListView() {
        List<String> lista_prioritario = handler.filtrarListPortafolioPrioritario(canal, codigo_pdv);
        if (lista_prioritario.size()>0) {
            dataAdapter = new ListViewAdapterExh(this, lista_prioritario);
            listViewPrioritario.setAdapter(dataAdapter);
        } else {
            tvPrioritario.setVisibility(View.VISIBLE);
            listViewPrioritario.setVisibility(View.GONE);
        }
    }

    public void showListViewPop() {
        List<String> listPop = handler.filtrarListPop(canal, codigo_pdv);
        if (listPop.size()>0) {
            dataAdapterpop = new ListViewAdapterPop(this,listPop);
            listViewPop.setAdapter(dataAdapterpop);
        } else {
            tvPop.setVisibility(View.VISIBLE);
            listViewPop.setVisibility(View.GONE);
        }
    }

    public void alertDialog(final String tipo_registro) {

        this.tipo_registro = tipo_registro;
        if (tipo_registro.equalsIgnoreCase("SALIDA")) {
            salida = true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_causales, null);
        //Title
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alertdialog_causales,null));

        //Traer Views
        TextView tittle = (TextView) dialogView.findViewById(R.id.lblModelo);
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.radio);

        imageView = (ImageView) dialogView.findViewById(R.id.ivFotoRegistro);
        btnCamera = (ImageButton) dialogView.findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                opciones();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (imageView.getDrawable() != null) {
                    android.app.AlertDialog.Builder alertadd = new android.app.AlertDialog.Builder(MenuNavigationActivity.this);
                    alertadd.setTitle("Vista Previa");
                    LayoutInflater factory = LayoutInflater.from(MenuNavigationActivity.this);
                    final View dialog_view = factory.inflate(R.layout.vista_previa, null);
                    ImageView dialog_imageview = (ImageView) dialog_view.findViewById(R.id.dialog_imageview);
                    dialog_imageview.setImageDrawable(imageView.getDrawable());
                    alertadd.setView(dialog_view);
                    alertadd.setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {}
                    });
                    alertadd.show();
                } else {
                    Toast.makeText(MenuNavigationActivity.this, "No hay foto cargada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tittle.setText(tipo_registro.toUpperCase());

        builder.setPositiveButton(R.string.save,
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (esFormularioValido()) {
                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            RadioButton radioButton = (RadioButton) dialogView.findViewById(selectedId);
                            causal = radioButton.getText().toString();
                            insertDataRegistro(latitud, longitud, causal);
                        }
                    }
                }
        );

        builder.setNeutralButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if (tipo_registro.equalsIgnoreCase("ENTRADA")) {
                    Log.i("PRESIONASTE CANCELAR","SALIENDO");
                    Intent intent = new Intent(MenuNavigationActivity.this, PuntosListActivity.class);
                    startActivity(intent);
                    //Stop the activity
                    finish();

                }
            }
        });

        builder.setView(dialogView);
        alert_marcacion = builder.create();
        alert_marcacion.show();

        Button button = alert_marcacion.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new CustomListener(alert_marcacion, dialogView));

        Button cButton = alert_marcacion.getButton(DialogInterface.BUTTON_NEUTRAL);
        cButton.setPadding(4, 2, 4, 2);
    }

    public boolean esFormularioValido() {
        if(radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(),"No selecccionó una causal",Toast.LENGTH_LONG).show();
            return false;
        }
        if (imageView.getDrawable() == null) {
            Toast.makeText(getApplicationContext(),"No tomó una foto",Toast.LENGTH_LONG).show();
            return false;
        }
        if (latitud==0 && longitud==0) {
            Toast.makeText(getApplicationContext(), "Genere las Coordenadas", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void checkFecha(){
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        if (fechaActual.equalsIgnoreCase(fecha_sync)){
            Log.i("fechaSync","fecha actual es igual a sp");
        }else{
            //guardarNuevaFecha(fechaActual);
            cerrarSesion();
            Log.i("fechaSync","fecha actual es diferente a sp -> cerrar sesion");
        }
    }

    public void cerrarSesion(){
        new GuardarLog(MenuNavigationActivity.this).saveLog(operator, "", "Cierre de sesion");
        ClufActivity.signed = 0;
        SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
        sessionManagement.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    class CustomListener implements View.OnClickListener {

        private final Dialog dialog;
        private final View dialogView;

        public CustomListener(Dialog dialog, View dialogView){
            this.dialog = dialog;
            this.dialogView = dialogView;
        }

        @Override
        public void onClick(View v) {
            if (esFormularioValido()) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialogView.findViewById(selectedId);
                causal = radioButton.getText().toString();
                Log.i("NO SALE NADA","ENTRA10000");
                if(insertDataRegistro(latitud, longitud, causal)){
                    dialog.dismiss();

                    if (tipo_registro.equalsIgnoreCase("SALIDA")) {
                        closeActivity();
                    }
                }
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
//        builder.setTitle("Salir PDV");
//        builder.setMessage("¿Desea salir PDV?");
//        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Log.i("FALTA SALIDA", String.valueOf(falta_salida));
//                if (falta_salida) {
//                    alertDialog("Salida");
//                } else {
//                    closeActivity();
//                }
//
////                Intent intent = new Intent(MenuNavigationActivity.this, PuntosListActivity.class);
////                startActivity(intent);
////                //Stop the activity
////                finish();
//            }
//        });
//
//        builder.setNeutralButton("NO",null);
//
//        AlertDialog ad = builder.create();
//        ad.show();
//    }

    private void closeActivity() {
        Log.i("MODULO CLOSE ACTIVITY", modulo);
        if (modulo != null && modulo.equalsIgnoreCase(Constantes.MODULO_PUNTOS_PRINCIPAL)) {
            Intent intent = new Intent(MenuNavigationActivity.this, PuntosListActivity.class);
            startActivity(intent);
        } else if (modulo != null && modulo.equalsIgnoreCase(Constantes.MODULO_PUNTOS_TARDIO)) {
            Intent intent = new Intent(MenuNavigationActivity.this, RelevoTardioActivity.class);
            startActivity(intent);
        }
        finish();
    }

    public void sendBroadcastMessageDataLoaded(String tipo_registro) {
        double latitud_pdv = this.latitud_pdv;
        double longitud_pdv = this.longitud_pdv;
        String codigo_pdv = this.codigo_pdv;
        String nombre_pdv = this.punto_venta;
        String perimetro_pdv = this.perimetro_pdv;
        double distance_pdv = this.distance_pdv;
        if (tipo_registro.equalsIgnoreCase("SALIDA")) {
            latitud_pdv = 0;
            longitud_pdv = 0;
            codigo_pdv = "";
            nombre_pdv = "";
            perimetro_pdv = "";
            distance_pdv = 0;
        }
        Intent intent = new Intent(Constantes.ACTION_PDV_LOCATION_UPDATE);
        intent.putExtra(Constantes.LATITUD_PDV_ACTUAL, latitud_pdv);
        intent.putExtra(Constantes.LONGITUDE_PDV_ACTUAL, longitud_pdv);
        intent.putExtra(Constantes.CODIGO_PDV_ACTUAL, codigo_pdv);
        intent.putExtra(Constantes.NOMBRE_PDV_ACTUAL, nombre_pdv);
        intent.putExtra(Constantes.PERIMETRO_PDV_ACTUAL, perimetro_pdv);
        intent.putExtra(Constantes.DISTANCE_PDV_ACTUAL, distance_pdv);
        sendBroadcast(intent);
    }

    public boolean insertDataRegistro(double latitud, double longitud, String causal) {
        try {
            Toast.makeText(getApplicationContext(), "Registrando "+ tipo_registro + " " + latitud + " " + longitud, Toast.LENGTH_LONG).show();
            obtenerFecha();
            String ciudad = "Ciudad: " + pdv.getCity();
            String local = "Local: " + pdv.getPos_name();
            String usuario = "Usuario: " + user;
            String fechaHora = "Fecha y hora: " + fecha + " " + hora;

            Bitmap temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ImageMark im = new ImageMark();
            Bitmap watermark = im.mark(temporal, ciudad, local, usuario, fechaHora, Color.YELLOW, 100, 85, false);
            int mheight = (int) (watermark.getHeight() * (1024.0 / watermark.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(watermark, 1024, mheight, true);

            String image = getStringImage(scaled);
            String registro = tipo_registro.toUpperCase();

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fechaser = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            //String causalFinal = causal.substring(3);

            if (salida) {
                causal = causal + causal_fuera_pdv;
            }

            sendBroadcastMessageDataLoaded(registro);

            ContentValues values = new ContentValues();

            values.put(ContractInsertGps.Columnas.IDPDV, codigo_pdv);
            values.put(ContractInsertGps.Columnas.USUARIO, user);
            values.put(ContractInsertGps.Columnas.TIPO, registro);
            values.put(ContractInsertGps.Columnas.VERSION, getString(R.string.version));
            values.put(ContractInsertGps.Columnas.LATITUDE, latitud + "");
            values.put(ContractInsertGps.Columnas.LONGITUDE, longitud + "");
            values.put(ContractInsertGps.Columnas.FOTO, image);
            values.put(ContractInsertGps.Columnas.FECHA, fechaser);
            values.put(ContractInsertGps.Columnas.HORA, horaser);
            values.put(ContractInsertGps.Columnas.CAUSAL, causal);
            values.put(ContractInsertGps.Columnas.DISTANCIA, distance);
            values.put(ContractInsertGps.Columnas.TIPO_RELEVO, tipo_relevo);
            values.put(ContractInsertGps.Columnas.POS_NAME, punto_venta);
            values.put(Constantes.ID_REMOTA_RUTA, id_ruta);
            values.put(Constantes.PENDIENTE_INSERCION, 1);
            getContentResolver().insert(ContractInsertGps.CONTENT_URI, values);

            Toast.makeText(getApplicationContext(), "Registrada su "+registro, Toast.LENGTH_SHORT).show();

            if (VerificarNet.hayConexion(getApplicationContext())) {
                SyncAdapter.sincronizarAhora(this, true, Constantes.insertGps, null);
                Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
            }
            if (registro.equalsIgnoreCase("ENTRADA")) {
                faltaSalida(true, fechaser);
//                    mostrarPopup("Información");
            } else {
                faltaSalida(false, fechaser);
            }
            return true;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    private void faltaSalida(boolean salida, String fecha_falta_salida) {
        this.falta_salida = salida;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constantes.FALTA_SALIDA, salida);
        editor.putString(Constantes.FECHA_FALTA_SALIDA, fecha_falta_salida);
        editor.commit();
    }

    //************METODOS PARA TAKE-PHOTO Y UPLOAD
    private void opciones(){
        final CharSequence []opciones={"Abrir Camara","Cancelar"};
        final androidx.appcompat.app.AlertDialog.Builder alertOpciones=new androidx.appcompat.app.AlertDialog.Builder(MenuNavigationActivity.this);
        alertOpciones.setTitle("Seleccione una opcion");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Abrir Camara")) {
                    //permiso();
                    //openCamera();
                    Intent n = new Intent(MenuNavigationActivity.this, CameraActivity.class);
                    n.putExtra("activity", "menu");
                    startActivity(n);
                }else if(opciones[i].equals("Abrir Galeria")){
                    galeria();
                }else if(opciones[i].equals("Cancelar")){
                    dialogInterface.dismiss();
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void galeria(){
        Intent gallery = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityIntent2.launch(gallery);
    }

//    //Tomar Foto
//    private void takePhoto(){
//        if (!marshMallowPermission.checkPermissionForCamera()) {
//            marshMallowPermission.requestPermissionForCamera();
//        } else {
//            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
//                marshMallowPermission.requestPermissionForExternalStorage();
//            } else {
//                //Abrir Camera
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
//            }
//        }
//    }
//
//    //SELECT FROM GALLERY
//    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode==RESULT_OK){
//
//            switch (requestCode){
//                case COD_SELECCIONA:
//                   /* Uri miPath=data.getData();
//                    imageView.setImageURI(miPath);*/
//                    Uri filePath = data.getData();
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                        //Setear el ImageView con el Bitmap
//                        scaleImage(bitmap);
//                    }catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case COD_FOTO:
//                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
//                            new MediaScannerConnection.OnScanCompletedListener() {
//                                @Override
//                                public void onScanCompleted(String path, Uri uri) {
//                                    Log.i("Ruta de almacenamiento","Path: "+path);
//                                }
//                            });
//                    Bitmap bitmap= BitmapFactory.decodeFile(path);
//                    scaleImage(bitmap);
//                    break;
//            }
//        }
//
//    }
//
//    private void cargarImagen() {
//        //final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
//        final CharSequence[] opciones={"Tomar Foto","Cancelar"};
//        final androidx.appcompat.app.AlertDialog.Builder alertOpciones=new androidx.appcompat.app.AlertDialog.Builder(this);
//        alertOpciones.setTitle("Seleccione una Opción");
//        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (opciones[i].equals("Tomar Foto")){
//                    tomarFotografia();
//                }else{
//                    if (opciones[i].equals("Cargar Imagen")){
//                        /*Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        intent.setType("image/");
//                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);*/
//                        openGallery();
//                    }else{
//                        dialogInterface.dismiss();
//                    }
//                }
//            }
//        });
//        alertOpciones.show();
//    }
//
//    private void openGallery(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), COD_SELECCIONA);
//    }
//
//    private void tomarFotografia() {
//        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
//        boolean isCreada=fileImagen.exists();
//        String nombreImagen="";
//        if(isCreada==false){
//            isCreada=fileImagen.mkdirs();
//        }
//
//        if(isCreada==true){
//            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
//        }
//
//
//        path=Environment.getExternalStorageDirectory()+
//                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
//
//        File imagen=new File(path);
//
//        Intent intent=null;
//        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        ////
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
//            String authorities=getApplicationContext().getPackageName()+".provider";
//            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        }else{
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
//        }
//        startActivityForResult(intent,COD_FOTO);
//
//        ////
//    }
//
//    //Permite hacer la imagen mas pequeña
//    public void scaleImage(Bitmap bitmap) {
//        try{
//            obtenerFecha();
//            String ciudad = "Ciudad: " + pdv.getCity();
//            String local = "Local: " + pdv.getPos_name();
//            String usuario = "Usuario: " + user;
//            String fechaHora = "Fecha y hora: " + fecha + " " + hora;
//
////            Point point = new Point(200, 200);
////            Bitmap watermark = applyWaterMarkEffect(bitmap, "Water mark text", 20, 20, Color.GREEN, 80, 50, true);
////            Bitmap watermark = mark(bitmap, ciudad, local, usuario, fechaHora, Color.GREEN, 100, 40, false);
//            ImageMark im = new ImageMark();
//            Bitmap watermark = im.mark(bitmap, ciudad, local, usuario, fechaHora, Color.YELLOW, 100, 85, false);
//            int mheight = (int) (watermark.getHeight() * (1024.0 / watermark.getWidth()) );
//            Bitmap scaled = Bitmap.createScaledBitmap(watermark, 1024, mheight, true);
//            imageView.setImageBitmap(scaled);
//            bitmapfinal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        } catch (Exception e) {
//            androidx.appcompat.app.AlertDialog alertDialog1;
//            alertDialog1 = new androidx.appcompat.app.AlertDialog.Builder(getApplicationContext()).create();
//            alertDialog1.setTitle("Message");
//            alertDialog1.setMessage("Notificar \t "+e.toString());
//            alertDialog1.show();
//            Log.e("compressBitmap", "Error on compress file");
//        }
//    }
//
    //Metodo que sube la imagen al servidor
    public String getStringImage(Bitmap bmp){
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Comprime la Imagen tipo, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

//    //Metodo para verificar size de la imagen
//    public String getSizeImage(Bitmap bmp){
//        String mensaje;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //Comprime la Imagen tipo, calidad y outputstream
//        bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
//        byte[] imageBytes = baos.toByteArray();
//        //Convierte a KB:
//        lengthbmp = imageBytes.length/1024;
//        //Solo permite imagenes hasta 243KB
//        if (lengthbmp<600){
//            mensaje = "1";
//        }else{
//            mensaje = "0";
//        }
//        return mensaje;
//    }

    public void obtenerFecha() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        fecha = date.format(currentLocalTime);

        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        hora = hour.format(currentLocalTime);
//            return localTime;
    }

    public class ListViewAdapterExh extends ArrayAdapter<String> {

        private List<String> sku;
        // private String num_exhibicion;
        public Context context;

        public ListViewAdapterExh(Context context, List<String> sku) {
            super(context, 0, sku);
            this.sku = sku;
            //   this.num_exhibicion = num_exhibicion;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = convertView;

            if (null == convertView) {
                v = inflater.inflate(R.layout.list_row_prioritario, parent, false);
            }

            final TextView lblSku = (TextView) v.findViewById(R.id.lblSku);
//            final EditText lblNumExh = (EditText) v.findViewById(R.id.lblNumExh);
//            final CheckBox btnSave = (CheckBox) v.findViewById(R.id.respuesta);

            if (sku.size() > 0) {
                lblSku.setText(sku.get(position));

            }
            return v;
        }
    }

    public class ListViewAdapterPop extends ArrayAdapter<String> {

        private List<String> popsugerido;
        // private String num_exhibicion;
        public Context context;

        public ListViewAdapterPop(Context context, List<String> popsugerido) {
            super(context, 0, popsugerido);
            this.popsugerido = popsugerido;
            //   this.num_exhibicion = num_exhibicion;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = convertView;

            if (null == convertView) {
                v = inflater.inflate(R.layout.list_row_popsugerido, parent, false);
            }

            final TextView lblPOPSugerido = (TextView) v.findViewById(R.id.lblPOPSugerido);
//            final EditText lblNumExh = (EditText) v.findViewById(R.id.lblNumExh);
//            final CheckBox btnSave = (CheckBox) v.findViewById(R.id.respuesta);

            if (popsugerido.size() > 0) {
                lblPOPSugerido.setText(popsugerido.get(position));

            }
            return v;
        }
    }

//    private void locationStart() {
//        //registerReceiver(new ProximityReciever(), new IntentFilter(ACTION_FILTER));
//        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Localizacion Local = new Localizacion();
//        Local.setMenuNavigationActivity(this);
//        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        final boolean networkEnabled = mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        if (!gpsEnabled) {
//            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(settingsIntent);
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
//            return;
//        }
//        if (networkEnabled) {
//            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
//        }
//        if (gpsEnabled) {
//            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
//        }
//
//        //Setting up My Broadcast Intent
//        Intent i = new Intent(ACTION_FILTER);
//        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), -1, i, PendingIntent.FLAG_MUTABLE);
//
//        //setting up proximituMethod
//        //mlocManager.addProximityAlert(lat1, long1, radius, -1, pi);
//    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationStart();
                return;
            }
        }
    }

//    /**
//     * Aqui empieza la Clase Localizacion
//     */
//    public class Localizacion implements LocationListener {
//
//        MenuNavigationActivity MenuNavigationActivity;
//        private String fecha,hora,name;
//
//        public MenuNavigationActivity getMenuNavigationActivity() {
//            return MenuNavigationActivity;
//        }
//
//        public void setMenuNavigationActivity(MenuNavigationActivity MenuNavigationActivity) {
//            this.MenuNavigationActivity = MenuNavigationActivity;
//        }
//
//        @Override
//        public void onLocationChanged(Location loc) {
//            if(!lblLatitud[0].equals(loc.getLatitude())&&!lblLongitud[0].equals(loc.getLongitude())){
//                lblLatitud[0] = ""+loc.getLatitude();
//                lblLongitud[0] = ""+loc.getLongitude();
//            }
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            // Este metodo se ejecuta cuando el GPS es desactivado
//            Toast.makeText(getApplicationContext(), "GPS Desactivado", Toast.LENGTH_SHORT).show();
//            LoadName();
//            obtenerFecha();
////            String name = "Ana Pinzon";
//            ContentValues values = new ContentValues();
//            values.put(ContractInsertRastreo.Columnas.USUARIO, name);
//            values.put(ContractInsertRastreo.Columnas.LATITUD, "GPS DESACTIVADO");
//            values.put(ContractInsertRastreo.Columnas.LONGITUD, "GPS DESACTIVADO");
//            values.put(ContractInsertRastreo.Columnas.FECHA, fecha);
//            values.put(ContractInsertRastreo.Columnas.HORA, hora);
//            values.put(Constantes.PENDIENTE_INSERCION, 1);
//
////            Toast.makeText(getApplicationContext(),"Saving Location..",Toast.LENGTH_SHORT).show();
//            getContentResolver().insert(ContractInsertRastreo.CONTENT_URI, values);
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//            // Este metodo se ejecuta cuando el GPS es activado
//            Toast.makeText(getApplicationContext(), "GPS Activado", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            switch (status) {
//                case LocationProvider.AVAILABLE:
//                    Log.d("debug", "LocationProvider.AVAILABLE");
//                    break;
//                case LocationProvider.OUT_OF_SERVICE:
//                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
//                    break;
//                case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
//                    break;
//            }
//        }
//
//        public void LoadName(){
////            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
//            name = sharedPreferences.getString(Constantes.USER,Constantes.NODATA);
//        }
//
//        public void obtenerFecha(){
//            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
//            Date currentLocalTime = cal.getTime();
//            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
//            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
//            fecha = date.format(currentLocalTime);
//
//            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
//            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
//            hora = hour.format(currentLocalTime);
////            return localTime;
//        }
//    }
//
//    public class ProximityReciever extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String key = LocationManager.KEY_PROXIMITY_ENTERING;
//
//            Boolean entering = intent.getBooleanExtra(key, false);
//
//            if (entering) {
//                Toast.makeText(context, "LocationReminderReceiver entering", Toast.LENGTH_SHORT).show();
//                Log.i("ReceptorProximidad", "entering");
//            } else {
//                Toast.makeText(context, "LocationReminderReceiver exiting", Toast.LENGTH_SHORT).show();
//                Log.i("ReceptorProximidad", "exiting");
//            }
//        }
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
            progressDialog = new ProgressDialog(MenuNavigationActivity.this, R.style.MyAlertDialogStyle);
            progressDialog.setTitle("Sincronizando");
            progressDialog.setMessage("Validando credenciales, espere un momento...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

}