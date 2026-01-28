package com.luckyecuador.app.bassaApp;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;
import com.luckyecuador.app.bassaApp.Clase.ModuloExhibicion;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.MarshMallowPermission;
import com.luckyecuador.app.bassaApp.Conexion.Mensajes;
import com.luckyecuador.app.bassaApp.Conexion.VerificarNet;
import com.luckyecuador.app.bassaApp.Contracts.InsertExhBassa;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.Sync.SyncAdapter;
import com.luckyecuador.app.bassaApp.Utils.GuardarLog;
//import com.luckyecuador.app.bassaApp.Utils.SyncAutomatica;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ExhibicionesBassaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener, TextWatcher {

    ListViewAdapterExhColgate dataAdapter;
    ArrayList<ModuloExhibicion> selectedItems = new ArrayList<ModuloExhibicion>();

    DatabaseHelper handler;
    RelativeLayout lyNovedades;
    CheckBox chckNueva;
    CheckBox chckMantenimiento;
    Spinner spHerramienta;
    Spinner spTipoExh;
    Spinner spFabricante;
    Spinner spTipoHerramienta;
    Spinner spCampana;
    Spinner spConvenio;
    Spinner spTipoNovedad;
    //Spinner spZona;

    /*LinearLayout layoutpersonal;
    LinearLayout layoutestrella;*/
    //RelativeLayout layoutzona;

    String subCatexhibicion, tipoExhSelected, fabSelected, catSelected;
    String zona = "-", personalizacion, estrella, numeroexh, observacion, escorreccion;

    String herramienta, tipoexh, tipoherr, novedades, nueva, mantenimiento, campana;

    String eliminar = "NO";

    /*CheckBox r1;
    CheckBox r2;*/
    EditText txtNumExh;
    EditText txtObservacion;
    TextView textView;
    CheckBox chkEsCorreccion;
    CheckBox chckEliminar;

    List<String> listSubcategoria;
    List<String> listNumExh;
    List<String> listClasificacion;

    ListView listView;
    /*List<String> listTipoExh; //Lista general de productos
    ListViewAdapter dataAdapter;
    private TextView emptyView;*/

    //Photo Camera
    static ImageView imageView;
    private Bitmap bitmap;
    private Bitmap bitmapfinal;
    long lengthbmp;
    int TAKE_PHOTO_CODE = 0;
    private int PICK_IMAGE_REQUEST = 1;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    ActivityResultLauncher<Intent> startActivityIntent2;

    private final String CARPETA_RAIZ="5pgoApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"ExhibicionColgate";
    String path;
    Button btnCamera;
    Button btnGallery;
    Button btnSiguiente;

    private Button btnMostrarInformacion;

    private String fabricante = "Colgate";

    private String userCursor;
    MarshMallowPermission marshMallowPermission;

    //Extras
    public String id_servidor,reExtra,codeExtra,localExtra, fecha, hora, usuarioCursor,canal, nombre, supervisor_exh, nombre_pdv, punto_apoyo;

    private BroadcastReceiver receptorSync;

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
    AlertDialog syncDialog;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibiciones_bassa);
        setToolbar();
        LoadData();
        marshMallowPermission = new MarshMallowPermission(this);

        handler = new DatabaseHelper(this, Provider.DATABASE_NAME, null, 1);
        //lyNovedades = (RelativeLayout) findViewById(R.id.lynovedades);
        chckNueva = (CheckBox) findViewById(R.id.chckNueva);
        chckMantenimiento = (CheckBox) findViewById(R.id.chckMantenimiento);
        spHerramienta = (Spinner) findViewById(R.id.spHerramienta);
        spTipoExh = (Spinner) findViewById(R.id.spTipoExh);
        spFabricante = (Spinner) findViewById(R.id.spFabricanteExh);
        spTipoHerramienta = (Spinner) findViewById(R.id.spTipoHerramienta);
        spCampana = (Spinner) findViewById(R.id.spCampana);
       // spConvenio = (Spinner) findViewById(R.id.spConvenio);
        //spTipoNovedad = (Spinner) findViewById(R.id.spTipoNovedadM);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);

        btnMostrarInformacion = findViewById(R.id.btnMostrarInformacion);

        //spZona = (Spinner) findViewById(R.id.spZona);
        /*r1 = (CheckBox)findViewById(R.id.r1);
        r2 = (CheckBox)findViewById(R.id.r2);*/
        /*layoutpersonal = (LinearLayout) findViewById(R.id.personalizacionLayout);
        layoutestrella = (LinearLayout) findViewById(R.id.estrellaLayout);*/
        //layoutzona = (RelativeLayout)findViewById(R.id.zonaspinner);

        //listView = (ListView)findViewById(android.R.id.list);
        //emptyView = (TextView) findViewById(R.id.recyclerview_data_emptyexh);
        //listView.setOnItemClickListener(this);

        filtrarHerramienta(codeExtra);
        verificarEnable(reExtra);

        //chckNueva.setOnClickListener(this);
        chckMantenimiento.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);

        //obtenerYMostrarPendientes();

        new GuardarLog(getApplicationContext()).saveLog(usuarioCursor, "", "Ingreso a Exhibiciones Colgate");

//        spTipoNovedad.setEnabled(false);
      //  btnSiguiente.setEnabled(false);

//        btnMostrarInformacion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showAlertDialogWithWebView();
//            }
//        });

        btnMostrarInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String supervisor = usuarioCursor; // Obtén el supervisor dinámicamente
                String cpCode = codeExtra; // Obtén el cp_code dinámicamente

                //REVISAR DESPUES - DAVID LO DIJO
//                com.luckyecuador.app.bassaApp.Utils.FetchPendientesTask task = new com.luckyecuador.app.bassaApp.Utils.FetchPendientesTask(btnMostrarInformacion);
//                task.execute(supervisor, cpCode);

//                obtenerYMostrarPendientes();
//                if (VerificarNet.hayConexion(getApplicationContext())) {
//                    showAlertDialogWithWebView();
//                } else {
//                    Toast.makeText(getApplicationContext(),"No hay conexión a Internet, por favor conectate a una red ",Toast.LENGTH_SHORT).show();
//                }
               // showAlertDialogWithWebView();
            }
        });

//        btnMostrarInformacion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fetchPendientes();
//            }
//        });

        startActivityIntent2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Aquí manejas el resultado de la actividad
                        Intent data = result.getData();
                        // Puedes manejar los datos devueltos de la actividad aquí
                        try {
//                            Uri miPath = result.getData();
//                            imageView.setImageURI(miPath);
                            Uri filePath = data.getData();
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                            String imagePath = getPathFromURI(filePath);

                            //Setear el ImageView con el Bitmap
                            Log.i("FilePath Galeria", filePath.getPath());
                            Log.i("Bitmap Galeria", bitmap.toString());
                            scaleImage(bitmap, imagePath);
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

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
                        //SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Precio, usuarioCursor, tipo_usuario, null, null, null);
                        //SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Precio, userCursor);
                        break;

                    default:
//                        Toast.makeText(getApplicationContext(), "No data: " + mensaje, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private String getPathFromURI(Uri filePath) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(filePath, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(ExhibicionesBassaActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        id_servidor = sharedPreferences.getString(Constantes.ID, Constantes.NO_DATA);
        canal = sharedPreferences.getString(Constantes.CANAL, Constantes.NO_DATA);
        reExtra = sharedPreferences.getString(Constantes.RE, Constantes.NO_DATA);
        codeExtra = sharedPreferences.getString(Constantes.CODE, Constantes.NO_DATA);
        localExtra = sharedPreferences.getString(Constantes.LOCAL, Constantes.NO_DATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NO_DATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NO_DATA);
        usuarioCursor = sharedPreferences.getString("operador", "No Name");
        nombre = sharedPreferences.getString(Constantes.MERCADERISTA,Constantes.NODATA);
        punto_apoyo = sharedPreferences.getString(Constantes.PUNTO_APOYO,Constantes.NODATA);
        nombre_pdv = sharedPreferences.getString(Constantes.LOCAL, Constantes.NODATA);
        supervisor_exh = sharedPreferences.getString(Constantes.SUPERVISOR,Constantes.NODATA);

        Log.i("ID-CURSOR", id_servidor);
        Log.i("RE-CURSOR", reExtra);
        Log.i("CODE-CURSOR", codeExtra);
        Log.i("LOCAL-CURSOR", localExtra);
        Log.i("USER-CURSOR", usuarioCursor);
        Log.i("PUNTO APOYO EXH COLG", punto_apoyo);
    }

//    private void showAlertDialogWithWebView() {
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Información");
//
//        WebView webView = new WebView(this);
//        webView.loadUrl(Constantes.EXHIBICIONES_VISTA+ "?supervisor="+usuarioCursor+"&cp_code="+codeExtra);
//        Log.i("urlde",""+Constantes.EXHIBICIONES_VISTA+ "?supervisor="+usuarioCursor+"&cp_code="+codeExtra);
//
//        webView.setWebViewClient(new WebViewClient());
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        alert.setView(webView);
//        alert.setNegativeButton("Cerrar", (dialog, id) -> dialog.dismiss());
//        alert.show();
//    }

//    private void obtenerYMostrarPendientes() {
//        FetchPendientesTask task = new FetchPendientesTask(btnMostrarInformacion);
//        task.execute(usuarioCursor, codeExtra);
//    }
//
//    private static class FetchPendientesTask extends AsyncTask<String, Void, String> {
//        private Button btnMostrarInformacion;
//
//        public FetchPendientesTask(Button btnMostrarInformacion) {
//            this.btnMostrarInformacion = btnMostrarInformacion;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String supervisor = params[0];
//            String cpCode = params[1];
//            String result = "";
//
//            HttpURLConnection urlConnection = null;
//            try {
//                URL url = new URL("https://webecuador.azurewebsites.net/App/XploraEcuador/mantenimiento_bases/colgate_nuevo/colgate_exhibiciones/getters/get_num_pendientes.php?supervisor=" + supervisor + "&cp_code=" + cpCode);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//
//                InputStream inputStream = urlConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                result = stringBuilder.toString();
//
//                bufferedReader.close();
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//            }
//
//            return result.trim();
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            //super.onPostExecute(result);
//
//            String mensajePersonalizado = "Tienes " + result + " convenios pendientes.";
//            btnMostrarInformacion.setText(mensajePersonalizado);
//            // Actualizar el texto del botón con el resultado obtenido
//            //btnMostrarInformacion.setText(result);
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar receptor
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receptorSync);
     //   myReceiver.registrar(myReceiver);
        if(tickReceiver!=null)
            unregisterReceiver(tickReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar receptor
        IntentFilter filtroSync = new IntentFilter(Intent.ACTION_SYNC);
        LocalBroadcastManager.getInstance(this).registerReceiver(receptorSync, filtroSync);
       // myReceiver.borrarRegistro(myReceiver);

//        tickReceiver = new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
//
//                    // Verificamos si ya se realizo la sync automatica;
//                    boolean value = new SyncAutomatica(ExhibicionesColgateActivity.this).verificarSyncAutomatica();
//
//                    // Si no se ha realizado , se ejecutara el siguiente codigo
//                    if (value){
//                        //consulta si ya sincronizo en el login, no hacer doble sincronizacion
//                        if(!handler.getSyncExterna()){
//                            AsyncTaskBajarOper bajarOper = new AsyncTaskBajarOper();
//                            bajarOper.execute();
//                        }else{
//                            Log.i("Exhibiciones Colgate cds","ya sincronizo en login");
//                        }
//
//                    }
//
//                }
//            }
//        };

        registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

    }



//    private void showAlertDialogWithWebView(int totalPendientes) {
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Información");
//
//        WebView webView = new WebView(this);
//        webView.loadUrl(Constantes.EXHIBICIONES_VISTA + "?supervisor=" + usuarioCursor + "&cp_code=" + codeExtra);
//        Log.i("urlde", "" + Constantes.EXHIBICIONES_VISTA + "?supervisor=" + usuarioCursor + "&cp_code=" + codeExtra);
//
//        webView.setWebViewClient(new WebViewClient());
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        alert.setView(webView);
//        alert.setNegativeButton("Cerrar", (dialog, id) -> dialog.dismiss());
//
//        // Muestra el diálogo
//        alert.show();
//    }

   // https://www.xplora.ec/App/XploraEcuador/mantenimiento_bases/colgate_nuevo/colgate_exhibiciones/getters/get_num_pendientes.php?supervisor=AMOLVERA@LUCKY.COM.EC&cp_code=PRUEBAH
//    private void fetchPendientes() {
//        OkHttpClient client = new OkHttpClient();
//        String url = "https://www.xplora.ec/App/XploraEcuador/mantenimiento_bases/colgate_nuevo/colgate_exhibiciones/getters/get_num_pendientes.php?supervisor=" + usuarioCursor + "&cp_code=" + codeExtra;
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("HTTP Error", "Error fetching pendientes", e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonResponse = response.body().string();
//                    Gson gson = new Gson();
//                    PendientesResponse pendientesResponse = gson.fromJson(jsonResponse, PendientesResponse.class);
//                    int totalPendientes = pendientesResponse.getTotalPendientes();
//
//                    // Actualiza la UI en el hilo principal
//                    new Handler(Looper.getMainLooper()).post(() -> {
//                        // Muestra el diálogo con el WebView después de obtener los pendientes
//                        showAlertDialogWithWebView(totalPendientes);
//                    });
//                }
//            }
//        });
//    }


    public void verificarEnable(String reExtra){
        if(reExtra!=null && !reExtra.equals("")){
            switch (reExtra){
                case "Hipermercados":
                case "Supermercados":
                case "Formatos Pequeños":
                case "Droguerias":
                    //spZona.setVisibility(View.VISIBLE);
                    //layoutzona.setVisibility(View.VISIBLE);
                    /*r1.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
                    layoutpersonal.setVisibility(View.GONE);
                    layoutestrella.setVisibility(View.GONE);*/
                    //obtenerZonaExh();
                    break;
                case "Tiendas de Barrio":
                case "Bazares":
                case "Mayoristas":
                case "Autoservicios del Indirecto":
                    //spZona.setVisibility(View.GONE);
                    //layoutzona.setVisibility(View.GONE);
                    /*r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    layoutpersonal.setVisibility(View.VISIBLE);
                    layoutestrella.setVisibility(View.VISIBLE);*/
                    break;
                case "Farmacias de Barrio":
                    //spZona.setVisibility(View.GONE);
                    //layoutzona.setVisibility(View.GONE);
                    /*r1.setVisibility(View.GONE);
                    layoutpersonal.setVisibility(View.GONE);
                    layoutestrella.setVisibility(View.VISIBLE);*/
            }
        }
    }

    public void filtrarHerramienta(String re){
        List<String> herramienta = handler.getHerramientasBassa(re,fabricante);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, herramienta);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHerramienta.setAdapter(dataAdapter);
        spHerramienta.setOnItemSelectedListener(this);
    }

    public void filtrarTipoExh(String herramienta){
        List<String> tipoExh = handler.getTipoExhSupervisor(codeExtra, herramienta, fabricante);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipoExh);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoExh.setAdapter(dataAdapter);
        spTipoExh.setOnItemSelectedListener(this);
    }

    public void filtrarTipoHerramienta(String herramienta, String tipoexh){
        List<String> tipoherr = handler.getTipoHerrExhSupervisor(codeExtra, herramienta, tipoexh);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipoherr);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoHerramienta.setAdapter(dataAdapter);
        spTipoHerramienta.setOnItemSelectedListener(this);
    }


    public void filtrarCampana(String herramienta, String tipoexh, String tipoherr) {
        List<String> campana = handler.getCampana(codeExtra, herramienta, tipoexh, tipoherr);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, campana);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCampana.setAdapter(dataAdapter);
        spCampana.setOnItemSelectedListener(this);
    }
//    public void filtrarTipoNovedad(String herramienta, String tipoexh, String tipoherr) {
//        List<String> tipoNovedad = new ArrayList<>();
//        if (tipoexh.toUpperCase().contains("ADICIONAL")) {
//            tipoNovedad = handler.getNovedadAdicionalExhColgate(codeExtra, herramienta, tipoexh, tipoherr);
//        } else {
//            tipoNovedad = handler.getNovedadAdicionalExhSup(codeExtra, herramienta, tipoexh, tipoherr);
//        }
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, tipoNovedad);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spTipoNovedad.setAdapter(dataAdapter);
//        spTipoNovedad.setOnItemSelectedListener(this);
//    }


    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_exhibicion_bassa, null);
        //Title
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setTitle(R.string.title_activity_exh_cp);
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alertdialog_exhibicion_bassa,null));

        //Traer Views
        TextView tipoExh =(TextView)dialogView.findViewById(R.id.lblExhibicion);
        TextView lblTipoCabecera =(TextView)dialogView.findViewById(R.id.lblTipoCabecera);
        /*txtNumExh = (EditText)dialogView.findViewById(R.id.txtNumExh);
        textView = (TextView)dialogView.findViewById(R.id.mTextView);*/
        chckEliminar = (CheckBox) dialogView.findViewById(R.id.chckEliminar);
        chkEsCorreccion = (CheckBox) dialogView.findViewById(R.id.chkCorreccion);
        /*txtObservacion = (EditText) dialogView.findViewById(R.id.txtObservacion);*/
        imageView = (ImageView) dialogView.findViewById(R.id.imageViewExh);
        btnCamera = (Button) dialogView.findViewById(R.id.btnCamera);
        listView = (ListView) dialogView.findViewById(R.id.listView);
        /*btnGallery= (Button) dialogView.findViewById(R.id.btnGallery);
        btnGallery.setVisibility(View.GONE);*/
        btnCamera.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        //btnGallery.setOnClickListener(this);

        selectedItems.clear();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModuloExhibicion me = new ModuloExhibicion();
                String eliminar = "NO";
                if (((CheckBox)view.findViewById(R.id.chckEliminar)).isChecked()) {
                    eliminar = "SI";
                }
                String subcategoria = ((TextView)view.findViewById(R.id.lblSubcategoria)).getText().toString();
                String respuesta = "NO";
                if (((CheckBox)view.findViewById(R.id.respuesta)).isChecked()) {
                    respuesta = "SI";
                }

                me.setEliminar(eliminar);
                me.setSubcategoria(subcategoria);
                me.setRespuesta(respuesta);

                if(containsSubcategoria(selectedItems, subcategoria)){
                    selectedItems.remove(me);
                    ((CheckBox) view.findViewById(R.id.respuesta)).setChecked(false);
                }else{
                    selectedItems.add(me);
                    ((CheckBox) view.findViewById(R.id.respuesta)).setChecked(true);
                }

//                if (chckEliminar.isChecked()){
//                    eliminar = "SI";
//                }else {
//                    eliminar = "NO";
//                }
            }
        });

        showListView(codeExtra, herramienta, tipoexh, tipoherr, campana);

        if(validaPermisos()){
            btnCamera.setEnabled(true);
        }else{
            btnCamera.setEnabled(true);
        }

        //txtObservacion.addTextChangedListener(this);

        tipoExh.setText(getString(R.string.tipo_exh) +"    "+ tipoexh +"\n");
        lblTipoCabecera.setText(getString(R.string.tipo_cab) +"    "+ tipoherr +"\n");

        builder.setPositiveButton(R.string.save,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
//                        String items ="";
//                        for (String item:selectedItems){
//                            items += "-"+item+"\n";
//                        }
//                        Log.i("ITEMS","Seleccionado:\n"+items);
                        if (esFormularioValido()) {
                            enviarDatos();
                            String supervisor = usuarioCursor; // Obtén el supervisor dinámicamente
                            String cpCode = codeExtra; // Obtén el cp_code dinámicamente

//                            com.luckyapp.luckyecuador.colgatea5p.Utils.FetchPendientesTask task = new com.luckyapp.luckyecuador.colgatea5p.Utils.FetchPendientesTask(btnMostrarInformacion);
//                            task.execute(supervisor, cpCode);
                            //obtenerYMostrarPendientes(); // Actualizar pendientes después de guardar
                            //lyNovedades.setVisibility(textView.VISIBLE);
                            //lyNovedades.setEnabled(false);
                        }
                    }
                }
        );

  //      builder.setNegativeButton(R.string.cancel,null);
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
//                       chckNueva.setChecked(false);
//                       chckMantenimiento.setChecked(false);
//                       chckNueva.setEnabled(true);
//                       chckMantenimiento.setEnabled(true);
                        //lyNovedades.setVisibility(textView.VISIBLE);
                        //lyNovedades.setEnabled(false);
                    //    spTipoNovedad.setSelection(0);
                        vaciarCampos();
                    }
                }
        );

        builder.setView(dialogView);
        AlertDialog ad = builder.create();
        ad.show();

        Button button = ad.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new CustomListener(ad));
    }

    public boolean containsSubcategoria(final List<ModuloExhibicion> list, final String name){
        return list.stream().filter(o -> o.getSubcategoria().equals(name)).findFirst().isPresent();
    }

    public boolean esFormularioValido() {
        for (int i=0; i<selectedItems.size(); i++) {
            if (selectedItems.get(i).getEliminar().equalsIgnoreCase("SI")) {
                eliminar = "SI";
            }
        }
        Log.i("ELIMINAR", eliminar);
        //Log.i("MANTENIMIENTO", mantenimiento);
        //Log.i("NUEVA", nueva);

        if (selectedItems.size() <= 0) {
            Toast.makeText(getApplicationContext(), "Seleccione una Subcategoría", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (imageView.getDrawable() == null) {
//            Toast.makeText(getApplicationContext(), "Debe tomar una foto 1", Toast.LENGTH_SHORT).show();
//            return false;
//        }

//        if (imageView.getDrawable() == null &&
//            eliminar.equalsIgnoreCase("NO")) {
//            Toast.makeText(getApplicationContext(), "Debe tomar una foto", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (imageView.getDrawable() == null &&
            !chckEliminar.isChecked()) {
            Toast.makeText(getApplicationContext(), "Debe tomar una foto", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    class CustomListener implements View.OnClickListener {

        private final Dialog dialog;

        public CustomListener(Dialog dialog){
            this.dialog = dialog;
        }
        @Override
        public void onClick(View v) {
           // if (canal.equalsIgnoreCase("DIRECT TRADE")) {
                String eliminar = "NO";
                if ((chckEliminar).isChecked()) {
                    eliminar = "SI";
                }
                for(int i = 0; i < listView.getChildCount(); i++){
                    View view = listView.getChildAt(i);
                    TextView lblSubcategoria = (TextView) view.findViewById(R.id.lblSubcategoria);
                    TextView lblNumExh = (TextView) view.findViewById(R.id.lblNumExh);
                    TextView lblClasificacion = (TextView) view.findViewById(R.id.lblClasificacion);
                    CheckBox chkRespuesta = (CheckBox) view.findViewById(R.id.respuesta);

                    ModuloExhibicion me = new ModuloExhibicion();

                    String subcategoria = lblSubcategoria.getText().toString();
                    String respuesta = "NO";
                    if ((chkRespuesta).isChecked()) {
                        respuesta = "SI";
                    }

                    me.setEliminar(eliminar);
                    me.setSubcategoria(subcategoria);
                    me.setRespuesta(respuesta);

                    if(containsSubcategoria(selectedItems, subcategoria)){
                        selectedItems.remove(me);
                        (chkRespuesta).setChecked(true);
                    }else{
                        selectedItems.add(me);
                        (chkRespuesta).setChecked(true);
                    }
                }
           // }
            if (esFormularioValido()) {
                    if (enviarDatos()) {
                        dialog.dismiss();
//                        lyNovedades.setVisibility(textView.GONE);
//                        lyNovedades.setEnabled(false);
                    }
                }
        }
    }

    public boolean enviarDatos(){
        try{
//            String image = "NO_FOTO";
//            if (imageView != null && imageView.getDrawable() != null) {
//                 image = getStringImage(bitmapfinal);
//            }
            String image = "NO_FOTO";
            if (imageView != null && imageView.getDrawable() != null) { //ImageView no vacio
                Bitmap temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                image = getStringImage(temporal);
                //image = getStringImage(bitmapfinal);
            }
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fechaser = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            if (chkEsCorreccion.isChecked()) {
                escorreccion = "SI";
            } else {
                escorreccion = "NO";
            }

            novedades = "NA";
//            if (!chckNueva.isChecked()){
//                //novedades = spTipoNovedad.getSelectedItem().toString();
//            }

            do {
                String eliminar = "";
                String subcategoria = "";
                for (int i = 0; i <= selectedItems.size(); i++) {
                    eliminar = selectedItems.get(0).getEliminar().toString();
                    subcategoria = selectedItems.get(0).getSubcategoria().toString();
                }
                selectedItems.remove(0);

                String num_exh = handler.getNumExh(codeExtra, tipoexh, tipoherr, campana, subcategoria);
                String categoria = handler.getCategoriaExh(codeExtra, tipoexh, tipoherr, campana, subcategoria, num_exh);
                String observacion = handler.getObservacionExh(codeExtra, tipoexh, tipoherr, campana, subcategoria, num_exh, categoria);
                String clasificacion = handler.getClasificacionExh(codeExtra, tipoexh, tipoherr, campana, subcategoria, num_exh, categoria, observacion);
                String id_r_exh = handler.getIDExh(codeExtra, tipoexh, tipoherr, campana, subcategoria, num_exh, categoria, observacion, clasificacion, usuarioCursor);
                Log.i("ID_EXH", ":" + id_r_exh);

                String ciudad = " " + "";
                String local = " " + "";
                String usuario = " " + "";
                String fechaHora = " " + "" + " " + "";

                Bitmap temporal = null;

                if (chckEliminar.isChecked()){
                    eliminar = "SI";
                    imageView.setImageResource(0);
                }else {
                    eliminar = "NO";
                    temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                }
//                ImageMark im = new ImageMark();
//                Bitmap watermark = im.mark(temporal, ciudad, local, usuario, fechaHora, Color.YELLOW, 100, 85, false);
//                int mheight = (int) (watermark.getHeight() * (1024.0 / watermark.getWidth()) );
//                Bitmap scaled = Bitmap.createScaledBitmap(watermark, 1024, mheight, false);

            //    String image = getStringImage(temporal);

                //            String image = "NO_FOTO";
//            if (imageView != null && imageView.getDrawable() != null) {
//                 image = getStringImage(bitmapfinal);
//            }
                ContentValues values = new ContentValues();
                values.put(InsertExhBassa.Columnas.ID_PDV, id_servidor);//Extra
                values.put(InsertExhBassa.Columnas.ID_R_EXH, "");//ID REPOSITORIO EXH
                values.put(InsertExhBassa.Columnas.USUARIO, usuarioCursor);//userCursor
                values.put(InsertExhBassa.Columnas.NOMBRE, nombre);//nombre
                values.put(InsertExhBassa.Columnas.SUPERVISOR, supervisor_exh);//supervisor
                values.put(InsertExhBassa.Columnas.NOMBRE_PDV, nombre_pdv);//nombrepdv
                values.put(InsertExhBassa.Columnas.CODIGO, codeExtra);
                values.put(InsertExhBassa.Columnas.REPORTE_NUEVA, "nueva");
                values.put(InsertExhBassa.Columnas.REPORTE_MANT, "mantenimiento");
                values.put(InsertExhBassa.Columnas.HERRAMIENTA, herramienta);//Spinner
                values.put(InsertExhBassa.Columnas.TIPO_EXHIBICION, tipoexh);//Spinner
                values.put(InsertExhBassa.Columnas.FABRICANTE, fabricante);//Spinner
                values.put(InsertExhBassa.Columnas.TIPO_HERRAMIENTA, tipoherr);//Spinner
                values.put(InsertExhBassa.Columnas.TIPO_NOVEDAD, novedades);//Spinner
                values.put(InsertExhBassa.Columnas.CONVENIO, herramienta);//Spinner
                values.put(InsertExhBassa.Columnas.ELIMINAR, eliminar);//Spinner
                values.put(InsertExhBassa.Columnas.SUBCAT, subcategoria);//List
                values.put(InsertExhBassa.Columnas.CAT, categoria);//List
                values.put(InsertExhBassa.Columnas.CAMPANA, campana);//List
                values.put(InsertExhBassa.Columnas.OBSERVACION, observacion);//List
                values.put(InsertExhBassa.Columnas.CLASIFICACION, clasificacion);//List
                values.put(InsertExhBassa.Columnas.NUMEROEXH, num_exh);//Edittext
                values.put(InsertExhBassa.Columnas.RESPUESTA, escorreccion);//Edittext
                values.put(InsertExhBassa.Columnas.KEY_IMAGE, image);
                values.put(InsertExhBassa.Columnas.FECHA, fechaser);//Extra
                values.put(InsertExhBassa.Columnas.HORA, horaser);//Extra
                values.put(InsertExhBassa.Columnas.PUNTO_APOYO, punto_apoyo);//EditText

                values.put(Constantes.PENDIENTE_INSERCION, 1);

                String supervisor = usuarioCursor; // Obtén el supervisor dinámicamente
                String cpCode = codeExtra; // Obtén el cp_code dinámicamente

//                com.luckyapp.luckyecuador.colgatea5p.Utils.FetchPendientesTask task = new com.luckyapp.luckyecuador.colgatea5p.Utils.FetchPendientesTask(btnMostrarInformacion);
//                task.execute(supervisor, cpCode);
                //obtenerYMostrarPendientes();

                getContentResolver().insert(InsertExhBassa.CONTENT_URI, values);

                if (VerificarNet.hayConexion(getApplicationContext())) {
                   // SyncAdapter.sincronizarAhora(this, true, Constantes.InsertExhBassa, null, null, null, null, null);
                    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.insertExhBassa, userCursor);
                    Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                }
            } while (selectedItems.size() != 0);
            vaciarCampos();
         //   spTipoNovedad.setSelection(0);
            return true;
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            String supervisor = usuarioCursor; // Obtén el supervisor dinámicamente
            String cpCode = codeExtra; // Obtén el cp_code dinámicamente

//            com.luckyecuador.app.bassaApp.Utils.FetchPendientesTask task = new com.luckyecuador.app.bassaApp.Utils.FetchPendientesTask(btnMostrarInformacion);
//            task.execute(supervisor, cpCode);
            //obtenerYMostrarPendientes();
            return false;
        }
    }

    public void vaciarCampos(){
//        chckNueva.setChecked(false);
//        chckNueva.setEnabled(true);
        chckMantenimiento.setChecked(false);
        chckMantenimiento.setEnabled(true);
        spHerramienta.setSelection(0);
        spTipoExh.setSelection(0);
        spTipoHerramienta.setSelection(0);
       // spTipoNovedad.setSelection(0);
        imageView.setImageResource(0);
        eliminar = "NO";
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //subCatexhibicion = listTipoExh.get(position);

        alertDialog();

        /*if (spZona!=null && spZona.getVisibility()==View.VISIBLE){
            if(!spZona.getSelectedItem().toString().equals(" ")){
                zona = spZona.getSelectedItem().toString();
                alertDialog();
            }else{
                Toast.makeText(getApplicationContext(), Mensajes.MENSAJE_EXH,Toast.LENGTH_SHORT).show();
            }
        }else{
            zona = "NA";
            alertDialog();
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(adapterView == spHerramienta){
            herramienta = adapterView.getItemAtPosition(position).toString();
            filtrarTipoExh(herramienta);
        }
        if(adapterView == spTipoExh){
            tipoexh = adapterView.getItemAtPosition(position).toString();
            //Toast.makeText(getApplicationContext(),categorySelected,Toast.LENGTH_SHORT).show();
            filtrarTipoHerramienta(herramienta, tipoexh);
        }
        if(adapterView == spTipoHerramienta){
            tipoherr = adapterView.getItemAtPosition(position).toString();
            //Toast.makeText(getApplicationContext(),categorySelected,Toast.LENGTH_SHORT).show();
            filtrarCampana(herramienta, tipoexh, tipoherr);
        }
        if(adapterView == spCampana){
            //novedades = adapterView.getItemAtPosition(position).toString();
            campana = adapterView.getItemAtPosition(position).toString();
            //  filtrarCategoria(tipoExhSelected, fabricante, fabSelected, catSelected);

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        textView.setText(String.valueOf(charSequence.length()));
    }

    @Override
    public void afterTextChanged(Editable editable) {}



//    //************METODOS PARA TAKE-PHOTO Y UPLOAD
//
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
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode==RESULT_OK){
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
//    }
//
//    private void cargarImagen() {
//        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
//        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(ExhibicionesColgateActivity.this);
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
//    }
//
    //Permite hacer la imagen mas pequeña
    public void scaleImage(Bitmap bitmap, String imagePath){
        try{
            ExifInterface exifInterface = new ExifInterface(imagePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            int rotationAngle = 0;
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationAngle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationAngle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationAngle = 270;
                    break;
                default:
                    rotationAngle = 0;
                    break;
            }

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            int nh = (int) (rotatedBitmap.getHeight() * (812.0 / rotatedBitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(rotatedBitmap, 812, nh, true);
            imageView.setImageBitmap(scaled);
            bitmapfinal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
//
//    //Metodo que sube la imagen al servidor
//    public String getStringImage(Bitmap bmp){
//        String encodedImage;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //Comprime la Imagen tipo, calidad y outputstream
//        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
//        byte[] imageBytes = baos.toByteArray();
//        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
//    }
//
//    //Metodo para verificar size de la imagen
//    public String getSizeImage(Bitmap bmp){
//        String mensaje;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //Comprime la Imagen tipo, calidad y outputstream
//        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
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


//************METODOS NUEVOS PARA TAKE-PHOTO Y UPLOAD
    private void opciones(){
        final CharSequence []opciones={"Abrir Camara","Abrir Galeria","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(ExhibicionesBassaActivity.this);
        alertOpciones.setTitle("Seleccione una opcion");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Abrir Camara")) {
                    Intent n = new Intent(ExhibicionesBassaActivity.this, CameraActivity.class);
                    n.putExtra("activity", "exhibiciones_colgate");
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
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityIntent2.launch(gallery);
    }

    //Metodo que sube la imagen al servidor
//    public String getStringImage(Bitmap bmp){
//        String encodedImage;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //Comprime la Imagen tipo, calidad y outputstream
//        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
//        byte[] imageBytes = baos.toByteArray();
//        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;


    public String getStringImage(Bitmap bmp) {
        if (bmp == null) {
            // Devuelve una cadena vacía o un valor predeterminado si el Bitmap es nulo
            return "";
        }
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Comprime la Imagen tipo, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void onClick(View view) {
        if (view == btnGallery) {
            if(txtNumExh!=null && !txtNumExh.getText().toString().equals("")){
               // showFileChooser();
            }else{
                Toast.makeText(getApplicationContext(), Mensajes.MENSAJE_NUMEXH,Toast.LENGTH_SHORT).show();
            }
        }
        if (view == btnCamera) {
            //cargarImagen();
            opciones();
        }

        if (view == btnSiguiente) {
            alertDialog();
        }

      /*  if (!chckNueva.isChecked() || !chckMantenimiento.isChecked()){
            Toast.makeText(ExhibicionesColgateActivity.this, "Por favor presione Nueva o Mantenimiento.", Toast.LENGTH_SHORT).show();
        }else if(view == btnSiguiente){
            alertDialog();
        }*/


//        if (!chckNueva.isChecked() || !chckMantenimiento.isChecked()){
//            btnSiguiente.setEnabled(false);
////            spTipoNovedad.setEnabled(false);
//        }else {
//            btnSiguiente.setEnabled(true);
//            Toast.makeText(ExhibicionesColgateActivity.this, "Por favor presione Nueva o Mantenimiento.", Toast.LENGTH_SHORT).show();
//        }

//        if (view == chckNueva) {
//            if (chckNueva.isChecked()){
//                nueva = "SI";
//                mantenimiento = "NO";
//                chckMantenimiento.setEnabled(false);
////                lyNovedades.setVisibility(View.INVISIBLE);
//          //      spTipoNovedad.setEnabled(false);
//                btnSiguiente.setEnabled(true);
//                Log.i("ENTRA", nueva);
//            } else{
//                nueva = "NO";
//                chckMantenimiento.setEnabled(true);
//         //       spTipoNovedad.setEnabled(false);
//         //       lyNovedades.setVisibility(View.VISIBLE);
//            }
//        }
//        if (view == chckMantenimiento) {
//            if (chckMantenimiento.isChecked()){
//                chckNueva.setEnabled(false);
//                mantenimiento = "SI";
//                nueva = "NO";
//                btnSiguiente.setEnabled(true);
//            //    spTipoNovedad.setEnabled(true);
//           //     lyNovedades.setVisibility(View.VISIBLE);
//            } else{
//                mantenimiento = "NO";
//                chckNueva.setEnabled(true);
//                //     spTipoNovedad.setEnabled(false);
//           //     lyNovedades.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            //Mostrar puntoventa
            Snackbar.make(findViewById(R.id.coordinatorPos),"Punto de Venta : " +
                    codeExtra + " - " + localExtra, Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*LISTVIEWADAPTER*/
    public class ListViewAdapterExhColgate extends ArrayAdapter<String> {

        private List<String> subcategoria;
        private List<String> num_exhibicion;
        private List<String> clasificacion;
        public Context context;

        public ListViewAdapterExhColgate(Context context, List<String> subcategoria, List<String> num_exhibicion, List<String> clasificacion) {
            super(context, 0, subcategoria);
            this.subcategoria = subcategoria;
            this.num_exhibicion = num_exhibicion;
            this.clasificacion = clasificacion;
            this.context = context;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = convertView;

            if (null == convertView) {
                v = inflater.inflate(R.layout.list_row_exh_bassa, parent, false);
            }

            final CheckBox chckEliminar = (CheckBox) v.findViewById(R.id.chckEliminar);
            final TextView lblSubcategoria = (TextView) v.findViewById(R.id.lblSubcategoria);
            final TextView lblNumExh = (TextView) v.findViewById(R.id.lblNumExh);
            final TextView lblClasificacion = (TextView) v.findViewById(R.id.lblClasificacion);
            final CheckBox btnSave = (CheckBox) v.findViewById(R.id.respuesta);

            if (subcategoria.size() > 0) {
                lblSubcategoria.setText(subcategoria.get(position));
                lblNumExh.setText(num_exhibicion.get(position));
                lblClasificacion.setText(clasificacion.get(position));

               // if (canal.equalsIgnoreCase("DIRECT TRADE")) {
                    btnSave.setChecked(true);
                    btnSave.setEnabled(false);
                //}

                /*btnSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                       @Override
                       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//                           String selectedItem = lblSubcategoria.getText().toString();
//                           if(selectedItems.contains(selectedItem)){
//                               selectedItems.remove(selectedItem);
//                               btnSave.setChecked(false);
//                           }else{
//                               selectedItems.add(selectedItem);
//                               btnSave.setChecked(true);
//                           }
                           ModuloExhibicion me = new ModuloExhibicion();
                           String eliminar = "NO";
                           if ((chckEliminar).isChecked()) {
                               eliminar = "SI";
                           }
                           String subcategoria = lblSubcategoria.getText().toString();
                           String respuesta = "NO";
                           if ((btnSave).isChecked()) {
                               respuesta = "SI";
                           }

                           me.setEliminar(eliminar);
                           me.setSubcategoria(subcategoria);
                           me.setRespuesta(respuesta);

//                           if (reExtra.equalsIgnoreCase("INDIRECT TRADE")){
//                               Log.i("RE EXTRA SI SALE", reExtra);
                               if(containsSubcategoria(selectedItems, subcategoria)){
                                   selectedItems.remove(me);
                                   (btnSave).setChecked(false);
                               }else{
                                   selectedItems.add(me);
                                   (btnSave).setChecked(true);
                               }
//                           } else if (reExtra.equalsIgnoreCase("DIRECT TRADE")) {
//                               btnSave.setChecked(true);
//                               btnSave.setEnabled(false);
//                           }
                       }
                   }
                );*/

                if (chckMantenimiento.isChecked()){
                    chckEliminar.setEnabled(false);
                }

                chckEliminar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (chckEliminar.isChecked()){
                            eliminar = "SI";
                            imageView.setImageResource(0);
                        }else {
                            eliminar = "NO";
                        }
                    }
                });
            }
            return v;
        }
    }

    public void showListView(String codigo, String herramienta, String tipoexh, String tipoherr, String campana){
        listSubcategoria = handler.filtrarListSubcategoriaColgate(codigo,herramienta,tipoexh,tipoherr,campana);
        listNumExh = handler.filtrarListNumExhColgate(codigo,herramienta,tipoexh,tipoherr,campana);
        listClasificacion = handler.filtrarListClasificacionColgate(codigo,herramienta,tipoexh,tipoherr,campana);
        dataAdapter = new ListViewAdapterExhColgate(this,listSubcategoria,listNumExh,listClasificacion);
        listView.setAdapter(dataAdapter);
    }

    private class AsyncTaskBajarOper extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
//                SyncAdapter.sincronizarAhora(this, false, Constantes.bajar_Oper, operator, tipo_usuario, null, null, null);
                //SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, usuarioCursor, tipo_usuario, null, null, null);
               // SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.insertExhBassa, userCursor);
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
//                    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, usuarioCursor, tipo_usuario, null, null, null);
                    //SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.insertExhBassa, userCursor);

                    new GuardarLog(ExhibicionesBassaActivity.this).saveLog(usuarioCursor, "", "Sincronizacion En Puntos Finalizada");
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