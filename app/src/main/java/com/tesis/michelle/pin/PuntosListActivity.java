package com.tesis.michelle.pin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Utils.AlertChangeTime;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.ServiceRastreo.LocationService;
import com.tesis.michelle.pin.Notificaciones.Session.SessionManagement;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.GuardarLog;
import com.tesis.michelle.pin.Utils.RequestPermissions;
import com.tesis.michelle.pin.Utils.UniqueDevice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PuntosListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String ACTION_FILTER = "com.tesis.michelle.pin";

    FloatingActionButton fab;

    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem tPdvs, tCalendar, tMapa;
    PagerAdapter adapter;

    private BroadcastReceiver receptorSync;
    private int pdv = 0;
    private int productos = 0;
    private int justificaciones = 0;
    private int tracking = 0;
    private int convenios = 0;
    private int usuarios = 0;
    private int tipo_inventario = 0;
    private int promocional_ventas = 0;
    private int campos_x_modulos = 0;
    private int tipo_novedades = 0;

    private int prod_secundarios = 0;
    private int link = 0;
    private int preguntas = 0;

    private int tests = 0;

    private int tipos_exhibicion = 0;
    private int promociones = 0;
    private int tipo_ventas = 0;
    private int registro1 = 0;
    private int tipo_actividades = 0;
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

    private String operator, fecha_sync;
    private String actualizado;

    TextView nav_header_textView;

    final String[] lblLatitud = {"Latitud"};
    final String[] lblLongitud = {"Longitud"};

    DatabaseHelper handler;

    private BatteryLevelReceiver batteryLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "onCreatePUNTOSLIST");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos_list);
        handler = new DatabaseHelper(this, Provider.DATABASE_NAME, null, 1);

        LoadData();

        batteryLow = new BatteryLevelReceiver();
        registerReceiver(batteryLow,new IntentFilter(Intent.ACTION_BATTERY_LOW));

        new DeveloperOptions().modalDevOptions(PuntosListActivity.this);
        new UniqueDevice().modalUniqueDevice(PuntosListActivity.this, operator);
        RequestPermissions requestPermissions = new RequestPermissions(getApplicationContext(), PuntosListActivity.this);
        requestPermissions.showPermissionDialog();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablalayout);
        tPdvs = findViewById(R.id.pdv);
        tCalendar = findViewById(R.id.calendar);
        tMapa = findViewById(R.id.mapa);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);

//        Toast.makeText(getApplicationContext(), "ACTUALIZADO: " + actualizado, Toast.LENGTH_SHORT).show();

        if (actualizado.equalsIgnoreCase("NO")) {
            handler.updateCourse();
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constantes.ACTUALIZADO, "SI");
            editor.commit();
        }

        fab = findViewById(R.id.fab);

        navigationView.setNavigationItemSelectedListener(this);

        nav_header_textView = navigationView.getHeaderView(0).findViewById(R.id.nav_header_textView);
        nav_header_textView.setText(operator);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount(), fab);
        pager.setAdapter(adapter);

        showPermissionDialog();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Log.i("LOCATION 1", "SE EJECUTA");
            startService(new Intent(getApplicationContext(), LocationService.class));
        } else {
            Log.i("LOCATION 2", "SE EJECUTA");
            startForegroundService(new Intent(getApplicationContext(), LocationService.class));
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
                        pdv = 1;
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
                        progressDialog.setMessage("Descargando tipos de registros, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_REGISTRO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoRegistro") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        registro1 = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_actividades, operator);
                        progressDialog.setMessage("Descargando tipo actividades, espere un momento...");
                    }
                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_ACTVIDADES) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoActividades") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_actividades = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_implementaciones, operator);
                        progressDialog.setMessage("Descargando tipo implementaciones, espere un momento...");
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
                        progressDialog.setMessage("Descargando modulos  roles, espere un momento...");
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
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Justificaciones")|| mensaje.equals("No se encontraron registros en el servidor.")) {
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
                        progressDialog.setMessage("Descargando Tipo inventario, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_USER) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Usuarios")) {
                        usuarios = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_inventario, operator);
                        progressDialog.setMessage("Descargando User, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPO_INVENTARIO) ||
                            mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "TipoInventario") || mensaje.equals("No se encontraron registros en el servidor.")) {
                        tipo_inventario = 1;
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_promocional_ventas, operator);

                        progressDialog.setMessage("Descargando Tipo de Novedades, espere un momento...");

//                        verificarLogin(user, pass);
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
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_tipo_novedades, operator);
                        progressDialog.setMessage("Descargando Tipo Novedades, espere un momento...");
                    }

                    if (mensaje.equals(Mensajes.SYNC_FINALIZADA_TIPONOVEDADES) || mensaje.equals(Mensajes.SYNC_NOREQUERIDA + "Tipo Novedades")) {
                        tipo_novedades = 1;
                        progressDialog.dismiss();
                        new GuardarLog(PuntosListActivity.this).saveLog(operator, "", "Descarga de información");
                        Toast.makeText(getApplicationContext(), Mensajes.SYNC_FINALIZADA, Toast.LENGTH_SHORT).show();
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

                    Log.i("PDV", pdv + "");
                    Log.i("PRODUCTOS", productos + "");
                    Log.i("JUSTIFICACIONES", justificaciones + "");
                    Log.i("PRODUCTOS SECUNDARIOS", prod_secundarios + "");
                    Log.i("LINK ONEDRIVE", link + "");
                    Log.i("TRACKING", tracking + "");
                    Log.i("CONVENIOS", convenios + "");
                    Log.i("USUARIOS", usuarios + "");
                    Log.i("PROMO VENT", promocional_ventas + "");
                    Log.i("CAMPOS MOD", campos_x_modulos + "");
                    Log.i("TIPOINVENTARIO", tipo_inventario + "");
                    Log.i("TIPOACTIVIDADES", tipo_actividades + "");
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

//        new AlertChangeTime(PuntosListActivity.this);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImplementacionActivity.class);
                startActivity(intent);

//                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
//                startActivity(intent);
            }
        });

        //Verificar los permisos
    /*    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
            return;
        }*/// else {
   //     startService(new Intent(getApplicationContext(), MyService.class));
//        locationStart();
        //Si los permisos estan otorgados, llamar al evento onClick del boton
        //clickButton();
        //}

        checkFecha();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                mostrarDialogoSalida();

            }
        });




    }
    private void mostrarDialogoSalida() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Salir");
        builder.setMessage("¿Desea salir de la aplicación?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    new GuardarLog(getApplicationContext()).saveLog(operator, "", "Cierre de la Aplicación");
                    finishAffinity();
                } catch (Exception e) {
                    Log.i("Salir", e.getMessage());
                }
            }
        });

        builder.setNeutralButton("NO", null);

        AlertDialog ad = builder.create();
        ad.show();
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.dashboard, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) PuntosListActivity.this.getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(PuntosListActivity.this.getComponentName()));
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.mCargar) {
            if (VerificarNet.hayConexion(this)) {
                try {
                    new GuardarLog(PuntosListActivity.this).saveLog(operator, "", "Sincronización de información");
                    SyncAdapter.sincronizarAhora(this, true, Constantes.SUBIR_TODO, null);
                    Snackbar.make(findViewById(R.id.coordinatorPos), Mensajes.ON_SYNC_UP, Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.coordinatorPos), Mensajes.ERROR_RED, Snackbar.LENGTH_LONG).show();
            }
        }
        if (item.getItemId() == R.id.mDescargar) {
            if (VerificarNet.hayConexion(this)) {
                if (!operator.equals("")) {
                    Log.i("LOGIN", "ENTRA AUTH LOGIN");

                    progressDialog = new ProgressDialog(PuntosListActivity.this,R.style.MyAlertDialogStyle);
                    progressDialog.setTitle("Sincronizando");
                    progressDialog.setMessage("Validando credenciales, espere un momento...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, operator);
                }else{
                    Toast.makeText(getApplicationContext(),Mensajes.SYNC_NO_USER ,Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(drawerLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG).setAction("Action", null).show();//                Toast.makeText(getApplicationContext(),"No hay conexión de Internet.",Toast.LENGTH_LONG).show();
            }
        }
        if (item.getItemId() == R.id.mVersion) {
            mostrarVersion();
        }

//        if (item.getItemId() == R.id.mMateriales) {
//            Intent intent = new Intent(PuntosListActivity.this, MaterialesRecibidosActivity.class);
//            startActivity(intent);
//        }

        /*
        if (item.getItemId() == R.id.mEjecucion) {
            Intent intent = new Intent(PuntosListActivity.this, EjecucionMaterialesActivity.class);
            startActivity(intent);
        }*/

        if (item.getItemId() == R.id.mTest) {

            Intent intent=new Intent(PuntosListActivity.this, StartQuizActivity.class);
            startActivity(intent);


/*
            String canal = handler.getChannelSegmentPreguntas(operator);

            ArrayList<Base_tests> listArrayQuiz = new ArrayList<>();
            listArrayQuiz = (ArrayList<Base_tests>) handler.getAllQuizByCanal(canal,operator);

            Log.i("CANAL", canal);

            if (listArrayQuiz.size() > 0) {
                Intent intent=new Intent(PuntosListActivity.this, StartQuizActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(drawerLayout, "No hay un test asignado", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
*/
        }
        if (item.getItemId() == R.id.mNotificaciones) {
            Intent intent = new Intent(PuntosListActivity.this, NotificacionesActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mStatus) {
            Intent intent = new Intent(PuntosListActivity.this, HistorialActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mLineaTiempo) {
            Intent intent = new Intent(PuntosListActivity.this, TimelineActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mStatusGeneral) {
            Intent intent = new Intent(PuntosListActivity.this, StatusGeneral.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mRelevoTardio) {
            Intent intent = new Intent(PuntosListActivity.this, RelevoTardioActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mPortafolio) {
            Intent intent = new Intent(getApplicationContext(), PortafolioPDFActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mCerrarSesion) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("Cerrar Sesión");
            builder.setMessage("¿Desea cerrar sesión?");
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new GuardarLog(PuntosListActivity.this).saveLog(operator, "", "Cierre de Sesión");
                    ClufActivity.signed = 0;
                    SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
                    sessionManagement.removeSession();
                    moveToLogin();
                }
            });

            builder.setNeutralButton("NO",null);

            AlertDialog ad = builder.create();
            ad.show();

            Button pButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
            Button cButton = ad.getButton(DialogInterface.BUTTON_NEUTRAL);
        }
        return false;
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
        new GuardarLog(PuntosListActivity.this).saveLog(operator, "", "Cierre de sesion");
        ClufActivity.signed = 0;
        SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
        sessionManagement.removeSession();
        moveToLogin();
    }

    public void mostrarVersion() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.action_version))
                .setMessage(getString(R.string.user) + ": " + operator + "\n" +
                        getString(R.string.version))
                .setPositiveButton("ACEPTAR", null)
                .show();
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        operator = sharedPreferences.getString(Constantes.USER, Constantes.NODATA).toUpperCase();
        actualizado = sharedPreferences.getString(Constantes.ACTUALIZADO, "NO").toUpperCase();
        fecha_sync = sharedPreferences.getString(Constantes.FECHA_SYNC,Constantes.NODATA);

    }
    private void moveToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar receptor
        IntentFilter filtroSync = new IntentFilter(Intent.ACTION_SYNC);
        LocalBroadcastManager.getInstance(this).registerReceiver(receptorSync, filtroSync);
        new AlertChangeTime(PuntosListActivity.this);
        new DeveloperOptions().modalDevOptions(PuntosListActivity.this);
        new UniqueDevice().modalUniqueDevice(PuntosListActivity.this, operator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar receptor
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receptorSync);
    }

//    @Override
//    public void onBackPressed() {
//        // ELIMINA O MUEVE super.onBackPressed(); de aquí.
//        // Si lo dejas aquí, la actividad se cerrará ANTES de que se muestre tu diálogo.
//        // super.onBackPressed(); // <-- ¡QUITAR ESTA LÍNEA O COMENTARLA!
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
//        builder.setTitle("Salir");
//        builder.setMessage("¿Desea salir de la aplicación?");
//        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Cerrar aplicativo
//                try {
//                    // stopLocationService(); // Si esta línea es necesaria, mantenla.
//                    new GuardarLog(getApplicationContext()).saveLog(operator, "", "Cierre de la Aplicación");
//
//                    // CAMBIAR moveTaskToBack(true) por finishAffinity() para salir de la app
//                    // moveTaskToBack(true); // Esta línea envía la app al fondo, no la cierra.
//                    PuntosListActivity.this.finishAffinity(); // <--- ¡LA SOLUCIÓN PARA SALIR!
//
//                } catch (Exception e) {
//                    Log.i("Salir", e.getMessage());
//                }
//            }
//        });
//
//        builder.setNeutralButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Si el usuario selecciona "NO", simplemente se cierra el diálogo.
//                // No se llama a super.onBackPressed() aquí, por lo que la actividad no se cierra.
//                dialog.dismiss();
//            }
//        });
//
//        // Muestra el diálogo
//        AlertDialog ad = builder.create();
//        ad.show();
//
//        // Puedes dejar esto para personalizar los botones si lo deseas, pero no afectan la lógica de salida.
//        Button pButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
//        //pButton.setTextColor(Color.rgb(79, 195, 247));
//        Button cButton = ad.getButton(DialogInterface.BUTTON_NEUTRAL);
//        //cButton.setTextColor(Color.rgb(79, 195, 247));
//    }


//    private void stopLocationService() {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                Intent intent = new Intent(PuntosListActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_STOP_FOREGROUND_SERVICE);
//                startService(intent);
//                //startService(new Intent(getApplicationContext(), LocationService.class));
//            } else {
//                Intent intent = new Intent(PuntosListActivity.this, LocationService.class);
//                intent.setAction(LocationService.ACTION_STOP_FOREGROUND_SERVICE);
//                startForegroundService(intent);
//            }
//        }catch (Exception e) {
//            Log.i("LocationService",e.getMessage());
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
            progressDialog = new ProgressDialog(PuntosListActivity.this, R.style.MyAlertDialogStyle);
            progressDialog.setTitle("Sincronizando");
            progressDialog.setMessage("Validando credenciales, espere un momento...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
}