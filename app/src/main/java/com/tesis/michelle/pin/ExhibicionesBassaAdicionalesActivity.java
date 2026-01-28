package com.tesis.michelle.pin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.tesis.michelle.pin.Clase.Imei;
import com.tesis.michelle.pin.Clase.ListExh;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.MarshMallowPermission;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Contracts.InsertExhibicionesAdNu; //revisar david
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.GuardarLog;
//import com.tesis.michelle.pin.Utils.SyncAutomatica;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ExhibicionesBassaAdicionalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener, TextWatcher {

    ListViewAdapterExh dataAdapter;
    ArrayList<ListExh> selectedItems = new ArrayList<ListExh>();
    int size_subcat = 0;

    DatabaseHelper handler;
    Spinner spTipoHerramienta;
    Spinner spTipoExh;
    Spinner spFabricant;
    // Spinner spCategory;
    Spinner spTipoNovedad;
    //Spinner spZona;

    LinearLayout layoutpersonal;
    LinearLayout layoutestrella;
    //RelativeLayout layoutzona;

    String subCatexhibicion, tipoExhSelected, tipoHerramienta, fabSelected, catSelected, tipo_novedad;
    String zona = "-", personalizacion, estrella, numeroexh, observacion, escorreccion, clasificacion, subcat;

    CheckBox r1;
    CheckBox r2;
    EditText txtNumExh;
    EditText txtClasificacion;
    Spinner spSubategoriaPrim;
    EditText txtObservacion;
    EditText txtObsCorreccion;
    TextView textView;
    TextView tipoExh;
    CheckBox chkEsCorreccion;

    List<String> listSubcategoria;
    List<String> listNumExh;
    List<String> listClasificacion;

    ListView listView;
   /* List<String> listTipoExh; //Lista general de productos
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
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Exhibicion";
    String path;
    Button btnCamera;
    Button btnGallery;
    Button btnSiguiente;

    private String userCursor;
    MarshMallowPermission marshMallowPermission;

    //Extras
    public String id_servidor,reExtra,codeExtra,localExtra, fecha, hora, nombre, supervisor, nombre_pdv, ean, codsap, punto_apoyo;

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
        setContentView(R.layout.activity_exhibiciones_bassa_adicionales);
        setToolbar();

//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            //Extras
//            id_servidor =extras.getString(Constantes.ID);
//            reExtra = extras.getString(Constantes.RE);
//            codeExtra = extras.getString(Constantes.CODE);
//            localExtra = extras.getString(Constantes.LOCAL);
//            fecha = extras.getString(Constantes.FECHA);
//            hora = extras.getString(Constantes.HORA);
//        } else {
//            id_servidor = (String) savedInstanceState.getSerializable(Constantes.ID);
//            reExtra = (String) savedInstanceState.getSerializable(Constantes.RE);
//            codeExtra = (String) savedInstanceState.getSerializable(Constantes.CODE);
//            localExtra = (String) savedInstanceState.getSerializable(Constantes.LOCAL);
//            fecha = (String) savedInstanceState.getSerializable(Constantes.FECHA);
//            hora = (String) savedInstanceState.getSerializable(Constantes.HORA);
//        }
        LoadData();
        marshMallowPermission = new MarshMallowPermission(this);

        handler = new DatabaseHelper(this, Provider.DATABASE_NAME, null, 1);
        spTipoHerramienta = (Spinner) findViewById(R.id.spTipoHerramienta);
        spTipoExh = (Spinner) findViewById(R.id.spTipoExh);
        spFabricant = (Spinner) findViewById(R.id.spFabExh);
        //spTipoNovedad = (Spinner) findViewById(R.id.spTipoNovedad);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        //spZona = (Spinner) findViewById(R.id.spZona);
        r1 = (CheckBox)findViewById(R.id.r1);
        r2 = (CheckBox)findViewById(R.id.r2);

        layoutpersonal = (LinearLayout) findViewById(R.id.personalizacionLayout);
        layoutestrella = (LinearLayout) findViewById(R.id.estrellaLayout);
        //layoutzona = (RelativeLayout)findViewById(R.id.zonaspinner);


        // emptyView = (TextView) findViewById(R.id.recyclerview_data_emptyexh);
        // listView.setOnItemClickListener(this);

        filtrarTipoHerramienta(reExtra);

        verificarEnable(reExtra);

        btnSiguiente.setOnClickListener(this);

        new GuardarLog(getApplicationContext()).saveLog(userCursor, "", "Ingreso a Herramientas Colgate Adicionales");


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
                        SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Precio, userCursor);
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

        //no aplica segun david
//        tickReceiver = new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
//
//                    // Verificamos si ya se realizo la sync automatica;
//                    boolean value = new SyncAutomatica(ExhibicionesBassaAdicionalesActivity.this).verificarSyncAutomatica();
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

    private class AsyncTaskBajarOper extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
//                SyncAdapter.sincronizarAhora(this, false, Constantes.bajar_Oper, operator, tipo_usuario, null, null, null);
//                SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, userCursor, tipo_usuario, null, null, null);
                 SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Precio, userCursor);

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
                if (!userCursor.equals("")) {
                    alertSyncDialog();
//                    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_operadores_mercaderismo, userCursor, tipo_usuario, null, null, null);
                    SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Precio, userCursor);

                    new GuardarLog(ExhibicionesBassaAdicionalesActivity.this).saveLog(userCursor, "", "Sincronizacion En Puntos Finalizada");
                    Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),Mensajes.SYNC_NO_USER ,Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(drawerLayout, Mensajes.ERROR_RED , Snackbar.LENGTH_LONG).setAction("Action", null).show();//                Toast.makeText(getApplicationContext(),"No hay conexión de Internet.",Toast.LENGTH_SHORT).show();
            }
        }
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
        AlertDialog.Builder dialogo=new AlertDialog.Builder(ExhibicionesBassaAdicionalesActivity.this);
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
        id_servidor = sharedPreferences.getString(Constantes.ID,Constantes.NO_DATA);
        reExtra = sharedPreferences.getString(Constantes.RE,Constantes.NO_DATA);
        codeExtra = sharedPreferences.getString(Constantes.CODE,Constantes.NO_DATA);
        localExtra = sharedPreferences.getString(Constantes.LOCAL,Constantes.NO_DATA);
        fecha = sharedPreferences.getString(Constantes.FECHA,Constantes.NO_DATA);
        hora = sharedPreferences.getString(Constantes.HORA,Constantes.NO_DATA);
        userCursor = sharedPreferences.getString("operador",Constantes.NO_DATA);
        nombre = sharedPreferences.getString(Constantes.MERCADERISTA,Constantes.NODATA);
        punto_apoyo = sharedPreferences.getString(Constantes.PUNTO_APOYO,Constantes.NODATA);
        nombre_pdv = sharedPreferences.getString(Constantes.LOCAL, Constantes.NODATA);
        supervisor = sharedPreferences.getString(Constantes.SUPERVISOR,Constantes.NODATA);


    }

    public void vaciarCampos(){
        //txtCodigo.setText("");
        spTipoHerramienta.setSelection(0);
        spTipoExh.setSelection(0);
        spFabricant.setSelection(0);
        //spTipoNovedad.setSelection(0);
        r1.setChecked(false);
        r2.setChecked(false);
        imageView.setImageResource(0);
    }

    public void verificarEnable(String reExtra){
        if(reExtra!=null && !reExtra.equals("")){
            Log.i("RE", reExtra);
            switch (reExtra){
                case "HIPERMERCADOS":
                case "SUPERMERCADOS":
                case "FORMATOS PEQUEÑOS":
                case "DROGUERIAS":
                    //spZona.setVisibility(View.VISIBLE);
                    //layoutzona.setVisibility(View.VISIBLE);
                    r1.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
                    layoutpersonal.setVisibility(View.GONE);
                    layoutestrella.setVisibility(View.GONE);
                    //obtenerZonaExh();
                    break;
                case "TIENDAS DE BARRIO":
                case "BAZARES":
                case "MAYORISTAS":
                case "MINIMARKET":
                case "AUTOSERVICIOS DEL INDIRECTO":
                    //spZona.setVisibility(View.GONE);
                    //layoutzona.setVisibility(View.GONE);
                    r1.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
                    layoutpersonal.setVisibility(View.GONE);
                    layoutestrella.setVisibility(View.GONE);
                    break;
                case "FARMACIAS DE BARRIO":
                    //spZona.setVisibility(View.GONE);
                    //layoutzona.setVisibility(View.GONE);
                    r1.setVisibility(View.GONE);
                    layoutpersonal.setVisibility(View.GONE);
                    layoutestrella.setVisibility(View.GONE);
            }
        }
    }

    public void filtrarTipoHerramienta(String re){
        List<String> tipoExh = handler.getTipoHerramientas(re);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipoExh);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoHerramienta.setAdapter(dataAdapter);
        spTipoHerramienta.setOnItemSelectedListener(this);
    }

    public void filtrarTipoExh(String tipoHerramienta, String re){
        List<String> fabricante = handler.getHerramientasExhibicionesBassa(tipoHerramienta, re);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fabricante);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoExh.setAdapter(dataAdapter);
        spTipoExh.setOnItemSelectedListener(this);
    }

    public void filtrarFabricant(String tipoHerramienta, String tipoExhSelected, String re){
        List<String> fabricante = handler.getFabricanteHerramientasExhibicionesBassa(tipoHerramienta,tipoExhSelected, re);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fabricante);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFabricant.setAdapter(dataAdapter);
        spFabricant.setOnItemSelectedListener(this);
    }



//    public void filtrarFabricant(String tipoExhSelected, String re){
//        List<String> fabricante = handler.getFabExhColgateAdicionales(tipoExhSelected, re);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, fabricante);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spFabricant.setAdapter(dataAdapter);
//        spFabricant.setOnItemSelectedListener(this);
//    }


//    public void filtrarTipoNovedad(String tipoExhSelected, String fabricante) {
//        List<String> tipoNovedad = new ArrayList<>();
//        if (tipoExhSelected.toUpperCase().contains("ADICIONAL")) {
//            tipoNovedad = handler.getTipoNovedadAdicional(tipoExhSelected, fabricante);
//        } else {
//            tipoNovedad = handler.getTipoNovedad(tipoExhSelected, fabricante);
//        }
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, tipoNovedad);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spTipoNovedad.setAdapter(dataAdapter);
//        spTipoNovedad.setOnItemSelectedListener(this);
//    }

//    public void filtrarSubcategoria(String re) {
//        List<String> subcategorias = handler.getSubcategoria(re);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, subcategorias);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spSubategoriaPrim.setAdapter(dataAdapter);
//        spSubategoriaPrim.setOnItemSelectedListener(this);
//    }


   /* public void obtenerSubcatExh(String tipoExhSelected, String subCat, String fabricant){
        listTipoExh = handler.getListSubCatExh(tipoExhtemSelectedListener(this);//
    }

   /* public void filtrarCategoria(String categorySelected, String subcatSelected){

        List<String> marcas = handler.getCatExh(categorySelected, subcatSelected, reExtra);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, marcas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapSelected,subCat,fabricant,reExtra);
        dataAdapter = new ListViewAdapter(this, listTipoExh);
        if(dataAdapter!=null){
            emptyView.setText("");
            listView.setAdapter(dataAdapter);
        }else{
            emptyView.setText(getString(R.string.empty_list));
        }
    }*/

    /*public void obtenerZonaExh(){
        List<String> zonas = handler.getZonaExh();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, zonas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spZona.setAdapter(dataAdapter);
    }*/

    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_exhibicion_bassa_adicionales, null);
        //Title
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setTitle(R.string.title_activity_exh_cp_adicionales);
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alertdialog_exhibicion_bassa_adicionales,null));

        //Traer Views
        tipoExh =(TextView)dialogView.findViewById(R.id.lblExhibicion);
        spSubategoriaPrim =(Spinner) dialogView.findViewById(R.id.spSubategoriaPrim);
        txtClasificacion = (EditText) dialogView.findViewById(R.id.txtNumExh);
        textView = (TextView)dialogView.findViewById(R.id.mTextView);
        chkEsCorreccion = (CheckBox) dialogView.findViewById(R.id.chkCorreccion);
        txtObservacion = (EditText)dialogView.findViewById(R.id.txtObservacion);
        txtObsCorreccion = (EditText)dialogView.findViewById(R.id.txtObsCorreccion);
        imageView = (ImageView) dialogView.findViewById(R.id.imageViewExh);
        btnCamera = (Button) dialogView.findViewById(R.id.btnCamera);
        btnGallery= (Button) dialogView.findViewById(R.id.btnGallery);
        listView = (ListView) dialogView.findViewById(R.id.listView);
        btnGallery.setVisibility(View.GONE);
        btnCamera.setOnClickListener(this);
        imageView.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        selectedItems.clear();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ENTRA", "ENTRA");
                String selectedItemSubcat = ((TextView)view.findViewById(R.id.lblSubcategoria)).getText().toString();
                String selectedItemNumExh = ((EditText)view.findViewById(R.id.lblNumExh)).getText().toString();

                ListExh listExh = new ListExh();
                listExh.setSubcategoria(selectedItemSubcat);
                listExh.setNumExh(selectedItemNumExh);

                for (int j = 0; j < selectedItems.size(); j++){
                    if(selectedItems.get(j).getSubcategoria().contains(selectedItemSubcat)){
                        selectedItems.remove(j);
                        ((CheckBox) view.findViewById(R.id.respuesta)).setChecked(false);
                    }else{
                        selectedItems.add(listExh);
                        ((CheckBox) view.findViewById(R.id.respuesta)).setChecked(true);
                    }
                }
            }
        });

        showListView(tipoHerramienta, tipoExhSelected, fabSelected);
        cargarSucat(tipoHerramienta, tipoExhSelected, fabSelected);

//        spSubategoriaPrim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(view == spSubategoriaPrim){
//                    subcat = spSubategoriaPrim.getSelectedItem().toString();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        if(validaPermisos()){
            btnCamera.setEnabled(true);
        }else{
            btnCamera.setEnabled(true);
        }

        chkEsCorreccion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    txtObsCorreccion.setVisibility(View.VISIBLE);
                }else{
                    txtObsCorreccion.setVisibility(View.GONE);
                }
            }
        });


        txtObservacion.addTextChangedListener(this);

        tipoExh.setText(getString(R.string.tipo_exh) +"    "+ tipoExhSelected +"\n");


        builder.setPositiveButton(R.string.save,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
//                        String items ="";
//                        for (String item: selectedItems){
//                            items += "-"+item+"\n";
//                        }
//                        Log.i("ITEMS","Seleccionado:\n"+items);
                        if (esFormularioValido()) {
                            Log.i("FRM_VALIDO", "FRM_VALIDO");
                            enviarDatos();
                        }
                    }
                }
        );

        //builder.setNegativeButton(R.string.cancel,null);

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

//    public void onListItemClick(ListView parent, View v, int position, long id) {
//        if (clasificacion.equalsIgnoreCase("Primaria")) {
//            RowData item = (RowData) getListAdapter().getItem(position);
//            Intent intent = new Intent(this, NewActivity.class);
//            intent.putExtra("id", item.mId);
//            startActivity(intent);
//        }
//    }

    public boolean esFormularioValido() {
    //    if (spSubategoriaPrim.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {

            if (selectedItems.size() <= 0) {

                Toast.makeText(ExhibicionesBassaAdicionalesActivity.this, "Seleccione una Subcategoría", Toast.LENGTH_SHORT).show();
                return false;
            }



     /*   if (selectedItems.size() < size_subcat) {
            Toast.makeText(ExhibicionActivity.this, "Debe ingresar todas las Subcategoría", Toast.LENGTH_SHORT).show();
            return false;
        }*/

    //        Toast.makeText(getApplicationContext(), "Seleccione una carga primaria", Toast.LENGTH_SHORT).show();
    //        return false;

     //   }

        return true;
    }

    public boolean esFormularioValido2() {

        if (spTipoNovedad.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getApplicationContext(), "Seleccione un Tipo de Novedad", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean esFormularioValido3() {

        if (spSubategoriaPrim.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getApplicationContext(), "Seleccione una carga primaria", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    class CustomListener implements View.OnClickListener{

        private final Dialog dialog;

        public CustomListener(Dialog dialog){
            this.dialog = dialog;
        }
        @Override
        public void onClick(View v){
            if (esFormularioValido()) {
                String mValue = txtObsCorreccion.getText().toString();
                boolean subcatLleno = false;
                String subcategoria = "", numExh = "";
                subcat = spSubategoriaPrim.getSelectedItem().toString();

                Log.i("SIZE SUB", selectedItems.size()+"");
                Log.i("SUB", subcategoria);
                for (int i = 0; i < selectedItems.size(); i++) {
                    Log.i("LIST SUB", selectedItems.get(i).getSubcategoria());
                    subcategoria = selectedItems.get(i).getSubcategoria().toString();
                    numExh = selectedItems.get(i).getNumExh().toString();

                    if (subcategoria.equalsIgnoreCase(subcategoria)) {
                        if (!numExh.trim().isEmpty() || !numExh.equals("") || numExh!=null) {
                            subcatLleno = true;
                        }
                    }
                }

                if (chkEsCorreccion.isChecked() && mValue.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"La Observación de la corrección es obligatoria",Toast.LENGTH_SHORT).show();
                }else{
                    if(subcatLleno){
                        if(enviarDatos()){
                            dialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Llene la subcategoria secundaria: " + subcategoria,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public boolean enviarDatos(){
        try{
            if (imageView != null && imageView.getDrawable() != null) { //ImageView no vacio
                Bitmap temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                String image = getStringImage(temporal);

                if(image!=null && !image.equals("")){

                    if (r1.isChecked()){
                        personalizacion = "SI";
                    } else{
                        personalizacion = "NO";
                    }

                    if (r2.isChecked()){
                        estrella = "SI";
                    } else{
                        estrella = "NO";
                    }


                    if(chkEsCorreccion.isChecked()){
                        escorreccion = "SI";
                    }else{
                        escorreccion = "NO";
                    }

                    do {
                        String subcategoria = "";
                        String numExh = "";

                        Log.i("SIZE-ITEMS", selectedItems.size()+"");

                        for (int i = 0; i <= selectedItems.size(); i++) {
                            subcategoria = selectedItems.get(0).getSubcategoria().toString();
                            numExh = selectedItems.get(0).getNumExh().toString();
                        }
                        selectedItems.remove(0);

//                        numeroexh = txtNumExh.getText().toString().trim();
                        observacion = txtObservacion.getText().toString().trim();
                        // String image = getStringImage(bitmap);
                        obtenerCursorUser(reExtra);

                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                        Date currentLocalTime = cal.getTime();
                        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                        String fechaser = date.format(currentLocalTime);

                        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
                        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                        String horaser = hour.format(currentLocalTime);

                        Imei i = new Imei();
                        String imei = i.getImei(getApplicationContext());

                        String observacion_correccion = txtObsCorreccion.getText().toString();
                        String subcategoriaprim = spSubategoriaPrim.getSelectedItem().toString();
                      //  String tipo_novedad = spTipoNovedad.getSelectedItem().toString();

                        ContentValues values = new ContentValues();
                        values.put(InsertExhibicionesAdNu.Columnas.ID_PDV, id_servidor);//Extra
                        values.put(InsertExhibicionesAdNu.Columnas.USUARIO, userCursor);//userCursor
                        values.put(InsertExhibicionesAdNu.Columnas.NOMBRE, nombre);//nombre
                        values.put(InsertExhibicionesAdNu.Columnas.SUPERVISOR, supervisor);//supervisor
                        values.put(InsertExhibicionesAdNu.Columnas.NOMBRE_PDV, nombre_pdv);//nombrepdv
                        values.put(InsertExhibicionesAdNu.Columnas.CODIGO, codeExtra);
                        values.put(InsertExhibicionesAdNu.Columnas.TIPO_HERRAMIENTA, tipoHerramienta);//Spinner
                        values.put(InsertExhibicionesAdNu.Columnas.TIPO, tipoExhSelected);//Spinner
                        values.put(InsertExhibicionesAdNu.Columnas.FABRICANTE, fabSelected);//Spinner
                        values.put(InsertExhibicionesAdNu.Columnas.CATEGORIA, catSelected);//Spinner
                        values.put(InsertExhibicionesAdNu.Columnas.TIPO_NOVEDAD, "tipo_novedad");//Spinner
                        values.put(InsertExhibicionesAdNu.Columnas.SUBCAT, "");//List
                        values.put(InsertExhibicionesAdNu.Columnas.ES_CORRECCION, escorreccion);//CheckBox
                        values.put(InsertExhibicionesAdNu.Columnas.ZONAEXH, zona);//Spinner
                        values.put(InsertExhibicionesAdNu.Columnas.PERSONALIZACION, "");//Check
                        values.put(InsertExhibicionesAdNu.Columnas.ESTRELLA, "");//Check
                        values.put(InsertExhibicionesAdNu.Columnas.NUMEROEXH, numExh);//Edittext
                        values.put(InsertExhibicionesAdNu.Columnas.OBSERVACION, observacion);//Edittext
                        values.put(InsertExhibicionesAdNu.Columnas.OBSERVACION_CORRECCION, observacion_correccion);//Edittext
                        values.put(InsertExhibicionesAdNu.Columnas.KEY_IMAGE, image);
                        values.put(InsertExhibicionesAdNu.Columnas.FECHA, fechaser);//Extra
                        values.put(InsertExhibicionesAdNu.Columnas.HORA, horaser);//Extra
                        values.put(InsertExhibicionesAdNu.Columnas.IMEI, imei);//Extra
                        values.put(InsertExhibicionesAdNu.Columnas.CLASIFICACION_PRIMARIA, subcategoriaprim);//Extra
                        values.put(InsertExhibicionesAdNu.Columnas.CLASIFICACION_SECUNDARIA, subcategoria);//Extra
                        values.put(InsertExhibicionesAdNu.Columnas.PUNTO_APOYO, punto_apoyo);//EditText
                        values.put(Constantes.PENDIENTE_INSERCION, 1);

                        getContentResolver().insert(InsertExhibicionesAdNu.CONTENT_URI, values);

                        if (VerificarNet.hayConexion(getApplicationContext())) {
//                            SyncAdapter.sincronizarAhora(this, true,Constantes.insertExhAdNu, null, null, null, null, null);
                            SyncAdapter.sincronizarAhora(getApplicationContext(), false, Constantes.bajar_Precio, userCursor);

                            Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_DEVICE,Toast.LENGTH_SHORT).show();
                        }

                    } while (selectedItems.size() != 0);
                    vaciarCampos();

                }
                return true;


            } else {
                Toast.makeText(getApplicationContext(), "Por favor tomar una foto para poder ser enviado.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }


    private void obtenerCursorUser(String reExtra) {

//        if (reExtra!=null && !reExtra.equals("")){
//            handler = new DatabaseHelper(getApplicationContext(),Provider.DATABASE_NAME, null, Constantes.DB_VERSION);
//            String query = "SELECT DISTINCT " + ContractExhibicion.Columnas.USER +
//                    " FROM " + ContractExhibicion.TIPOEXH + " WHERE "+
//                    ContractExhibicion.Columnas.RE +"=?";
//            SQLiteDatabase db = handler.getReadableDatabase();
//            Cursor cursor =  db.rawQuery(query, new String[]{reExtra});
//
//            if(cursor != null && cursor.moveToFirst()){
//                userCursor = cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicion.Columnas.USER));
//                cursor.close();
//            }
//        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //    subCatexhibicion = listTipoExh.get(position);

        alertDialog();

        /*if (spZona!=null && spZona.getVisibility()==View.VISIBLE){
            if(!spZona.getSelectedItem().toString().equals(" ")){
                zona = spZona.getSelectedItem().toString();
                alertDialog();
            }else{
                Toast.makeText(getApplicationContext(), Mensajes.MENSAJE_EXH,Toast.LENGTH_SHORT).show();
            }
        }else{
            zona = "NA";1234
            alertDialog();
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(adapterView == spTipoHerramienta){
            tipoHerramienta = adapterView.getItemAtPosition(position).toString();
            filtrarTipoExh(tipoHerramienta,reExtra);
        }

        if(adapterView == spTipoExh){
            tipoExhSelected = adapterView.getItemAtPosition(position).toString();
            filtrarFabricant(tipoHerramienta,tipoExhSelected,reExtra);
        }
//        if(adapterView == spFabricant){
//            fabSelected = adapterView.getItemAtPosition(position).toString();
//            //Toast.makeText(getApplicationContext(),categorySelected,Toast.LENGTH_SHORT).show();
//            filtrarTipoNovedad(tipoExhSelected,fabSelected);
//            //  filtrarTipoNovedad(fabSelected);
//        }
        if(adapterView == spFabricant){
            fabSelected  = adapterView.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        textView.setText(String.valueOf(charSequence.length()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //************METODOS PARA TAKE-PHOTO Y UPLOAD

    //Tomar Foto
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
//
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
//        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
//        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(ExhibicionesBassaAdicionalesActivity.this);
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

    //Metodo que sube la imagen al servidor
//    public String getStringImage(Bitmap bmp){
//        String encodedImage;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //Comprime la Imagen tipo, calidad y outputstream
//        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
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

    //************METODOS NUEVOS PARA TAKE-PHOTO Y UPLOAD
    private void opciones(){
        final CharSequence []opciones={"Abrir Camara","Abrir Galeria","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(ExhibicionesBassaAdicionalesActivity.this);
        alertOpciones.setTitle("Seleccione una opcion");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Abrir Camara")) {
                    Intent n = new Intent(ExhibicionesBassaAdicionalesActivity.this, CameraActivity.class);
                    n.putExtra("activity", "exhibiciones_colgate_adicionales");
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
    public String getStringImage(Bitmap bmp){
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Comprime la Imagen tipo, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onClick(View view) {
        if (view == btnGallery) {
//            if(txtNumExh!=null && !txtNumExh.getText().toString().equals("")){
            //showFileChooser();
//            }else{
//                Toast.makeText(getApplicationContext(),Mensajes.MENSAJE_NUMEXH,Toast.LENGTH_SHORT).show();
//            }
        }
        if (view == btnCamera) {
//            if(txtNumExh!=null && !txtNumExh.getText().toString().equals("")){
            opciones();
            //cargarImagen();
//            }else{
//                Toast.makeText(getApplicationContext(),Mensajes.MENSAJE_NUMEXH,Toast.LENGTH_SHORT).show();
//            }
        }
        if (view == btnSiguiente) {
          //  if (esFormularioValido2()) {

                alertDialog();

           // }
        }

        if (view == imageView) {
            if (imageView.getDrawable() != null) {
                android.app.AlertDialog.Builder alertadd = new android.app.AlertDialog.Builder(ExhibicionesBassaAdicionalesActivity.this);
                alertadd.setTitle("Vista Previa");
                LayoutInflater factory = LayoutInflater.from(ExhibicionesBassaAdicionalesActivity.this);
                final View dialog_view = factory.inflate(R.layout.vista_previa, null);
                ImageView dialog_imageview = (ImageView) dialog_view.findViewById(R.id.dialog_imageview);
                dialog_imageview.setImageDrawable(imageView.getDrawable());
                alertadd.setView(dialog_view);
                alertadd.setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {}
                });
                alertadd.show();
            } else {
                Toast.makeText(ExhibicionesBassaAdicionalesActivity.this, "No hay foto cargada", Toast.LENGTH_SHORT).show();
            }
        }


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
    public class ListViewAdapterExh extends ArrayAdapter<String> {

        private List<String> subcategoria;
        private String num_exhibicion;
        public Context context;

        public ListViewAdapterExh(Context context, List<String> subcategoria) {
            super(context, 0, subcategoria);
            this.subcategoria = subcategoria;
            this.num_exhibicion = num_exhibicion;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = convertView;

            if (null == convertView) {
                v = inflater.inflate(R.layout.list_row_exh_adicionales, parent, false);
            }

            final TextView lblSubcategoria = (TextView) v.findViewById(R.id.lblSubcategoria);
            final EditText lblNumExh = (EditText) v.findViewById(R.id.lblNumExh);
            final CheckBox btnSave = (CheckBox) v.findViewById(R.id.respuesta);

            if (subcategoria.size() > 0) {
                lblSubcategoria.setText(subcategoria.get(position));
//                lblNumExh.setText(clasificacion);
//                spClasificacion.getSelectedItem().toString().trim();

                btnSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                           String selectedItemNumExh = lblNumExh.getText().toString().trim();

                                                           if(selectedItemNumExh.trim().isEmpty()){
                                                               Log.i("TXT", "VACIO");
                                                           }else{
                                                               Log.i("TXT", "LLENO-" + selectedItemNumExh + "-");
                                                           }

                                                           if (!selectedItemNumExh.trim().isEmpty()) {
                                                               String selectedItemSubcat = lblSubcategoria.getText().toString();

                                                               ListExh listExh = new ListExh();
                                                               listExh.setSubcategoria(selectedItemSubcat);
                                                               listExh.setNumExh(selectedItemNumExh);

                                                               boolean guardado = false;
                                                               for (int j = 0; j < selectedItems.size(); j++) {
                                                                   if (selectedItems.get(j).getSubcategoria().contains(selectedItemSubcat)) {
                                                                       selectedItems.remove(j);
                                                                       btnSave.setChecked(false);
                                                                       guardado = true;
                                                                   }
                                                               }

                                                               if (!guardado) {
                                                                   selectedItems.add(listExh);
                                                                   btnSave.setChecked(true);
                                                               }
                                                           }else{
                                                               Toast.makeText(getApplicationContext(), "Debe ingresar el numero de exhibicion", Toast.LENGTH_SHORT).show();
                                                               btnSave.setChecked(false);
                                                           }
                                                       }
                                                   }
                );
            }
            return v;
        }
    }

    public void showListView(String tipoHerramienta, String tipoExhSelected, String fabricante){
        listSubcategoria = handler.filtrarListSubcategoria2(tipoHerramienta, tipoExhSelected, fabricante, reExtra);
        listSubcategoria.remove(0);
        Log.i("LIST-SIZE", listSubcategoria.size()+"");
        size_subcat = listSubcategoria.size();
        dataAdapter = new ListViewAdapterExh(this,listSubcategoria);
        listView.setAdapter(dataAdapter);
    }

    public void cargarSucat(String tipoHerramienta, String tipoExhSelected, String fabricante){
        listSubcategoria = handler.filtrarListSubcategoria2(tipoHerramienta, tipoExhSelected, fabricante, reExtra);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listSubcategoria);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubategoriaPrim.setAdapter(dataAdapter);
        spSubategoriaPrim.setOnItemSelectedListener(this);
    }

}
