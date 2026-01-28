package com.luckyecuador.app.bassaApp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jsibbold.zoomage.ZoomageView;
import com.luckyecuador.app.bassaApp.Clase.BaseEvaluacionPromotor;
import com.luckyecuador.app.bassaApp.Clase.InsertEvaluacionDemo;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.MarshMallowPermission;
import com.luckyecuador.app.bassaApp.Conexion.Mensajes;
import com.luckyecuador.app.bassaApp.Conexion.VerificarNet;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvaluacionEncuesta;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.Sync.SyncAdapter;
import com.luckyecuador.app.bassaApp.Utils.DeveloperOptions;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TimeZone;
import java.util.regex.Pattern;

//import com.github.barteksc.pdfviewer.BuildConfig;

public class EvaluacionEncuestaActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int LOCATION_REQUEST_CODE = 100;

    private String encuesta = "";
    private final String TAG = "MODULO_EVALUACION_ENCUESTA";
    private DatabaseHelper handler;
    ArrayList<BaseEvaluacionPromotor> listPreguntas;
    public String id_pdv, user, cuenta, codigo_pdv, ciudad, re, punto_venta, supervisor, fecha, pdv_apoyo, hora, canal, usuarioCursor, rol, mercaderista;
    MarshMallowPermission marshMallowPermission;

    LinearLayout layoutDetalle;
    FloatingActionButton btnSiguiente;
    ListView listview;
    TextView empty;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String latitud;
    private String longitud;
    private Geocoder geocoder;
    private List<Address> addresses;
    CustomAdapter dataAdapter;

    private int respuestas_positivas = 0;

    ArrayList<InsertEvaluacionDemo> InsertEvaluacionDemos = new ArrayList<InsertEvaluacionDemo>();

    NestedScrollView nsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_encuesta);
        setToolbar();
        handler = new DatabaseHelper(getApplicationContext(), Provider.DATABASE_NAME, null, 1);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            encuesta = bundle.getString("encuesta");
        }
        LoadData();
        marshMallowPermission = new MarshMallowPermission(this);

        layoutDetalle = (LinearLayout) findViewById(R.id.layoutDetalle);
        btnSiguiente = (FloatingActionButton) findViewById(R.id.btnSiguiente);

        listview = (ListView) findViewById(R.id.lvSKUCode);
        empty = (TextView) findViewById(R.id.recyclerview_data_empty);

        //nsv = (NestedScrollView) findViewById(R.id.scroll_view);

        listview.setClickable(false);
        geocoder = new Geocoder(this, Locale.getDefault());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    latitud = "" + location.getLatitude();
                    longitud = "" + location.getLongitude();

                    Log.i("Latitud", latitud);
                    Log.i("Longitud", longitud);

                    try {
                        if (VerificarNet.hayConexion(getApplicationContext())) {
                            addresses = geocoder.getFromLocation(Double.parseDouble(latitud), Double.parseDouble(longitud), 1);
                            if (!latitud.equals("") && !longitud.equals("")) {
                                if (addresses.size() > 0) {
                                    String direccion = addresses.get(0).getAddressLine(0);
                                    Log.i("Direccion", direccion);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("errores2", "" + e.getMessage());
                    }
                    locationManager.removeUpdates(locationListener);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {
                //Si el GPS esta deshabilitado, abrir la ventana de activacion del GPS en el dispositivo.
                Intent newIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(newIntent);
            }
        };

        // Solicitar permisos de ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
        btnSiguiente.setOnClickListener(this);
        showListView();

        Log.i(TAG,"" + listPreguntas.toString());

        /*EdgeToEdge.enable(this);
        setContentView(R.layout.activity_evaluacion_encuesta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

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

                            // Declarar la variable contentUri y asignarle el valor de filePath
                            //Uri contentUri = filePath;

                            String imagePath = getPathFromURI(filePath);

                            //Setear el ImageView con el Bitmap
                            Log.i("FilePath Galeria", filePath.getPath());
                            Log.i("Bitmap Galeria", bitmap.toString());
                            //scaleImage(bitmap);
                            scaleImage(bitmap, imagePath);

                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }

    public void showListView() {

        listPreguntas = handler.filtrarListPreguntas(encuesta, re);
        dataAdapter = new CustomAdapter(this, listPreguntas);
        if (!dataAdapter.isEmpty()) {
            empty.setVisibility(View.INVISIBLE);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(dataAdapter);
            ajustarAlturaListView();
        } else {
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Evaluación visita vendedor");
        builder.setMessage("¿Desea cerrar el módulo de Encuesta?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    finish();
                } catch (Exception e) {
                    Log.i("Salir", e.getMessage());
                }
            }
        });

        builder.setNeutralButton("NO",null);

        AlertDialog ad = builder.create();
        ad.show();*/
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        user = sharedPreferences.getString(Constantes.USER, Constantes.NODATA);
        mercaderista = sharedPreferences.getString(Constantes.NOM_MERCADERISTA, Constantes.NODATA);
        //cuenta = sharedPreferences.getString(Constantes.CUENTA, Constantes.NODATA);
        cuenta = "colgate";
        //rol = sharedPreferences.getString(Constantes.ROL, Constantes.NODATA);
        //codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.CANAL, Constantes.NODATA);
        ciudad = sharedPreferences.getString(Constantes.CIUDAD, Constantes.NODATA);
//        re = sharedPreferences.getString(Constantes.RE, Constantes.NODATA);
        re =sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);

        usuarioCursor = sharedPreferences.getString("operador","No Name");
        rol = sharedPreferences.getString(Constantes.ROL, Constantes.NODATA);
        pdv_apoyo = sharedPreferences.getString(Constantes.PUNTO_APOYO, Constantes.NODATA);
        supervisor = sharedPreferences.getString(Constantes.SUPERVISOR, Constantes.NODATA);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new DeveloperOptions().modalDevOptions(EvaluacionEncuestaActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSiguiente) {
            respuestas_positivas = 0;
            ArrayList<BaseEvaluacionPromotor> values = dataAdapter.getValues();
            boolean esValido = listadoEsValido(values);
            if (esValido) {
                insertData();
            } else {
                InsertEvaluacionDemos.clear();
            }
        }
    }

    private boolean listadoEsValido(ArrayList<BaseEvaluacionPromotor> values) {
        try {
            View view;
            TextView lblCategoria = null;
            TextView lblPregunta = null;
            RadioGroup rgCodifica = null;
            RadioGroup rgCalificacionF = null;
            RadioButton rbA = null;
            RadioButton rbB = null;
            RadioButton rbC = null;
            RadioButton rbD = null;
            RadioButton rbE = null;
            ImageView ivFoto = null;
            EditText txtComentario = null;

            EditText lblRespuesta = null;

            CheckBox cb_a = null;
            CheckBox cb_b = null;
            CheckBox cb_c = null;
            CheckBox cb_d = null;
            CheckBox cb_e = null;

            ImageView img = null;

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fecha = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String hora = hour.format(currentLocalTime);

            int listLength = listview.getChildCount();
            for (int i = 0; i < listLength; i++) {
                view = listview.getChildAt(i);

                String categoria = "";
                String pregunta ="";
                String respuesta = "";
                String foto = "NO_FOTO";
                String comentario = "";
                String tipo_pregunta = "";
                String puntaje = "0";

                if(view.findViewById(R.id.layoutPreg).isShown()){
                    tipo_pregunta= "UNICA";
                    lblCategoria = (TextView) view.findViewById(R.id.lblCategoria);
                    lblPregunta = (TextView) view.findViewById(R.id.lblPregunta);
                    rgCodifica = view.findViewById(R.id.rgCodifica);
                    rbA = view.findViewById(R.id.rba);
                    rbB = view.findViewById(R.id.rbb);
                    rbC = view.findViewById(R.id.rbc);
                    rbD = view.findViewById(R.id.rbd);
                    rbE = view.findViewById(R.id.rbe);
                    ivFoto = view.findViewById(R.id.ivFoto);
                    txtComentario = view.findViewById(R.id.txtComentario);

                    categoria = lblCategoria.getText().toString().toUpperCase();
                    pregunta = lblPregunta.getText().toString();
                    foto = "NO_FOTO";
                    comentario = "";

                    int selectedId = rgCodifica.getCheckedRadioButtonId();
                    if (selectedId == R.id.rba) {
                        puntaje = handler.getPuntajePregunta(pregunta);
                        //respuesta = "Si";
                        respuesta = rbA.getText().toString();
                        respuestas_positivas++;
                    } else if (selectedId == R.id.rbb) {
                        puntaje = handler.getPuntajePregunta(pregunta);
                        //respuesta = "No";
                        respuesta = rbB.getText().toString();
                    } else if (selectedId == R.id.rbc) {
                        puntaje = handler.getPuntajePregunta(pregunta);
                        //respuesta = "No";
                        respuesta = rbC.getText().toString();
                    } else if (selectedId == R.id.rbd) {
                        puntaje = handler.getPuntajePregunta(pregunta);
                        //respuesta = "No";
                        respuesta = rbD.getText().toString();
                    } else if (selectedId == R.id.rbe) {
                        puntaje = handler.getPuntajePregunta(pregunta);
                        //respuesta = "No";
                        respuesta = rbE.getText().toString();

                    /*if (ivFoto == null || ivFoto.getDrawable() == null) {
                        Toast.makeText(getApplicationContext(), "Debe tomar una foto en la pregunta: " + pregunta, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (txtComentario.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Debe ingresar un comentario en la pregunta: " + pregunta, Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (VerificarNet.hayConexion(getApplicationContext()) || latitud != null || longitud != null) {
                        addresses = geocoder.getFromLocation(Double.parseDouble(latitud), Double.parseDouble(longitud), 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);

                            String ciudad = address.getLocality();
                            String estado = address.getAdminArea();
                            String pais = address.getCountryName();
                            String codigoPostal = address.getPostalCode();
                            String conocidaPor = address.getFeatureName();

                            String fechaHora = "Fecha y hora: " + fecha + " " + hora;
                            String evaluador = "Evaluador: " + user;
                            String pdv_evaluado = "PDV Evaluado: " + pdvo;
                            direccion = "Dirección: " + address.getAddressLine(0);
                            coordenadas = "Coordenadas: " + latitud + ", " + longitud;

                            Bitmap temporal = ((BitmapDrawable) ivFoto.getDrawable()).getBitmap();
                            ImageMark im = new ImageMark();
                            Bitmap watermark = im.mark(temporal, fechaHora, evaluador, pdv_evaluado, direccion, coordenadas, Color.YELLOW, 100, 85, false);
                            int mheight = (int) (watermark.getHeight() * (1024.0 / watermark.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(watermark, 1024, mheight, true);
                            foto = getStringImage(scaled);

                            comentario = txtComentario.getText().toString();

                        } else {
                            Toast.makeText(getApplicationContext(), "Espere un momento mientras se obtiene la dirección", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                        return false;
                    }*/
                    } else {
                        Toast.makeText(getApplicationContext(), "Debe contestar la pregunta: " + pregunta, Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } else if (view.findViewById(R.id.layoutPregFinal).isShown()){
                    tipo_pregunta = "ESCALA";

                    lblCategoria = (TextView) view.findViewById(R.id.lbCategoriaF);
                    lblPregunta = (TextView) view.findViewById(R.id.lblPreguntaF);
                    rgCalificacionF = (RadioGroup) view.findViewById(R.id.rgCalificacion);

                    categoria = lblCategoria.getText().toString().toUpperCase();
                    pregunta = lblPregunta.getText().toString();

                    puntaje = handler.getPuntajePregunta(pregunta);

                    int selectedId = rgCalificacionF.getCheckedRadioButtonId();
//                    int puntajePorPregunta = Integer.parseInt(values.get(i).getPuntaje_por_pregunta()); // Obtener puntaje_por_pregunta de la pregunta actual
//
//                    int valorPregunta;
//
//                    switch (selectedId) {
//                        case R.id.rbMuyMala:
//                        case R.id.rbMala:
//                        case R.id.rbBuenaMala:
//                        case R.id.rbBuena:
//                        case R.id.rbMuyBuena:
//                            respuesta = String.valueOf(selectedId - R.id.rbMuyMala + 1); // Convertir el ID del RadioButton en la respuesta correspondiente (1 a 5)
//                            valorPregunta = 0;
//                            break;
//                        case R.id.rbMuyBuena6:
//                        case R.id.rbMuyBuena7:
//                        case R.id.rbMuyBuena8:
//                        case R.id.rbMuyBuena9:
//                        case R.id.rbMuyBuena10:
//                            respuesta = String.valueOf(selectedId - R.id.rbMuyBuena6 + 6); // Convertir el ID del RadioButton en la respuesta correspondiente (6 a 10)
//                            valorPregunta = 1;
//                            break;
//                        default:
//                            Toast.makeText(getApplicationContext(), "Debe contestar la pregunta: " + pregunta, Toast.LENGTH_SHORT).show();
//                            return false;
//                    }
//
//                    if (valorPregunta == 1) {
//                        // Realizar una acción basada en el valor de puntajePorPregunta
//                        // Por ejemplo:
//                        int puntajeTotal = Integer.parseInt(String.valueOf(puntajePorPregunta)) * valorPregunta;
//                        // Hacer algo con puntajeTotal
//                    }

                    if (selectedId == R.id.rbMuyMala) {
                        respuesta = "1"; //muy mala
                    } else if (selectedId == R.id.rbMala) {
                        respuesta = "2"; //mala
                    } else if (selectedId == R.id.rbBuenaMala) {
                        respuesta = "3"; //Ni buena ni mala
                    } else if (selectedId == R.id.rbBuena) {
                        respuesta = "4"; //Buena
                    } else if (selectedId == R.id.rbMuyBuena) {
                        respuesta = "5"; //Excelente
                    } else if (selectedId == R.id.rbMuyBuena6){
                        respuesta = "6";
                    }else if (selectedId == R.id.rbMuyBuena7){
                        respuesta = "7";
                    }else if (selectedId == R.id.rbMuyBuena8){
                        respuesta = "8";
                    }else if (selectedId == R.id.rbMuyBuena9){
                        respuesta = "9";
                    }else if (selectedId == R.id.rbMuyBuena10){
                        respuesta = "10";
                    } else {
                        Toast.makeText(getApplicationContext(), "Debe contestar la pregunta: " + pregunta, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    puntaje = Integer.parseInt(respuesta) >=6 ? puntaje : "0";
                } else if(view.findViewById(R.id.layoutPregAb).isShown()){
                    tipo_pregunta = "ABIERTA";

                    lblCategoria = (TextView) view.findViewById(R.id.lblCategoriaA);
                    lblPregunta = (TextView) view.findViewById(R.id.lblPreguntaA);
                    if(view.findViewById(R.id.respuestaPreg).isShown()){
                        lblRespuesta = (EditText) view.findViewById(R.id.respuestaPreg);
                    }else if(view.findViewById(R.id.respuestaPregNum).isShown()){
                        lblRespuesta = (EditText) view.findViewById(R.id.respuestaPregNum);
                    }else{
                        lblRespuesta = (EditText) view.findViewById(R.id.respuestaPregGeneral);
                    }


                    categoria = lblCategoria.getText().toString().toUpperCase();
                    pregunta = lblPregunta.getText().toString();
                    respuesta = lblRespuesta.getText().toString();

                    puntaje = "1.00";

                    if(respuesta.trim().equalsIgnoreCase("")){
                        Toast.makeText(getApplicationContext(), "Debe responder la pregunta: " + pregunta, Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } else if(view.findViewById(R.id.layoutPregMult).isShown()){
                    tipo_pregunta = "MULTIPLE";

                    lblCategoria = (TextView) view.findViewById(R.id.lblCategoriaM);
                    lblPregunta = (TextView) view.findViewById(R.id.lblPreguntaM);



                    cb_a = (CheckBox) view.findViewById(R.id.check_opta);
                    cb_b = (CheckBox) view.findViewById(R.id.check_optb);
                    cb_c = (CheckBox) view.findViewById(R.id.check_optc);
                    cb_d = (CheckBox) view.findViewById(R.id.check_optd);
                    cb_e = (CheckBox) view.findViewById(R.id.check_opte);

                    StringJoiner respJoiner = new StringJoiner(",");
                    List<CheckBox> items = new ArrayList<CheckBox>();
                    items.add(cb_a);
                    items.add(cb_b);
                    items.add(cb_c);
                    items.add(cb_d);
                    items.add(cb_e);

                    for (CheckBox item : items) {
                        if (item.isChecked()) {
                            respJoiner.add(item.getText().toString());
                        }
                    }


                    categoria = lblCategoria.getText().toString().toUpperCase();
                    pregunta = lblPregunta.getText().toString();
                    respuesta = respJoiner.toString();

                    puntaje = "1.00";

                    if(respuesta.equalsIgnoreCase("")){
                        Toast.makeText(getApplicationContext(), "Debe responder la pregunta: " + pregunta, Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } else if(view.findViewById(R.id.layoutPregFoto).isShown()){
                    tipo_pregunta = "FOTO";

                    lblCategoria = (TextView) view.findViewById(R.id.lblCategoriaFoto);
                    lblPregunta = (TextView) view.findViewById(R.id.lblPreguntaFoto);
                    img = (ImageView) view.findViewById(R.id.foto);

                    categoria = lblCategoria.getText().toString().toUpperCase();
                    pregunta = lblPregunta.getText().toString();

                    if (img != null && img.getDrawable() != null) {
                        Bitmap temporal = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        foto = getStringImage(temporal);
                    }
                    Log.i("dadsc","cd "+img);
                    Log.i("dadsc","cd2 "+img.getDrawable());

                    if (img.getDrawable() == null){
                        Toast.makeText(getApplicationContext(), "Debe tomar/cargar una foto: " + pregunta, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

                InsertEvaluacionDemo InsertEvaluacionDemo = new InsertEvaluacionDemo();
                InsertEvaluacionDemo.setCategoria(categoria);
                InsertEvaluacionDemo.setPregunta(pregunta);
                InsertEvaluacionDemo.setRespuesta(respuesta);
                InsertEvaluacionDemo.setFoto(foto);
                InsertEvaluacionDemo.setComentario(comentario);
                InsertEvaluacionDemo.setTipo_pregunta(tipo_pregunta);
                InsertEvaluacionDemo.setPuntaje(puntaje);

                InsertEvaluacionDemos.add(InsertEvaluacionDemo);
            }
        } catch (Exception e) {
            Log.e("Error Listado", e.getMessage());
        }

        return true;
    }

    public String getStringImage(Bitmap bmp){
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Comprime la Imagen tipo, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void insertData() {
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            fecha = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            hora = hour.format(currentLocalTime);

            String foto_fachada = "";

            /*if (VerificarNet.hayConexion(getApplicationContext()) || latitud != null || longitud != null) {
                addresses = geocoder.getFromLocation(Double.parseDouble(latitud), Double.parseDouble(longitud), 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);

                    String ciudad = address.getLocality();
                    String estado = address.getAdminArea();
                    String pais = address.getCountryName();
                    String codigoPostal = address.getPostalCode();
                    String conocidaPor = address.getFeatureName();

                    String fechaHora = "Fecha y hora: " + fecha + " " + hora;
                    String evaluador = "Evaluador: " + user;
                    String pdv_evaluado = "PDV Evaluado: " + pdvo;
                    direccion = "Dirección: " + address.getAddressLine(0);
                    coordenadas = "Coordenadas: " + latitud + ", " + longitud;

                    Bitmap temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ImageMark im = new ImageMark();
                    Bitmap watermark = im.mark(temporal, "Fecha y hora: " + fecha + " " + hora, "Evaluador: " + user, "PDV Evaluado: " + pdvo, direccion, coordenadas, Color.YELLOW, 100, 85, false);
                    int mheight = (int) (watermark.getHeight() * (1024.0 / watermark.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(watermark, 1024, mheight, true);
                    foto_fachada = getStringImage(scaled);
                }
            } else {
                Bitmap temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ImageMark im = new ImageMark();
                Bitmap watermark = im.mark(temporal, "Fecha y hora: " + fecha + " " + hora, "Evaluador: " + user, "PDV Evaluado: " + pdvo, "Dirección: Sin conexión a internet", "Coordenadas: Sin conexión a internet" + coordenadas, Color.YELLOW, 100, 85, false);
                int mheight = (int) (watermark.getHeight() * (1024.0 / watermark.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(watermark, 1024, mheight, true);
                foto_fachada = getStringImage(scaled);
            }*/

            String comentario = "";
            //String comentario = txtComentario.getText().toString();
            String calificacion = respuestas_positivas + "/" + InsertEvaluacionDemos.size();
            String satisfaccion = "";

            /*int selectedId = rgCalificacion.getCheckedRadioButtonId();
            if (selectedId == R.id.rbMuyMala) {
                satisfaccion = "1"; //muy mala
            } else if (selectedId == R.id.rbMala) {
                satisfaccion = "2"; //mala
            } else if (selectedId == R.id.rbBuenaMala) {
                satisfaccion = "3"; //Ni buena ni mala
            } else if (selectedId == R.id.rbBuena) {
                satisfaccion = "4"; //Buena
            } else if (selectedId == R.id.rbMuyBuena) {
                satisfaccion = "5"; //Excelente
            } else if (selectedId == R.id.rbMuyBuena6){
                satisfaccion = "6";
            }else if (selectedId == R.id.rbMuyBuena7){
                satisfaccion = "7";
            }else if (selectedId == R.id.rbMuyBuena8){
                satisfaccion = "8";
            }else if (selectedId == R.id.rbMuyBuena9){
                satisfaccion = "9";
            }else if (selectedId == R.id.rbMuyBuena10){
                satisfaccion = "10";
            }*/

            double puntaje_obtenido = 0.0;

            for (int i=0; i < InsertEvaluacionDemos.size(); i++) {
                puntaje_obtenido = puntaje_obtenido + Double.parseDouble(InsertEvaluacionDemos.get(i).getPuntaje());
            }
            String puntaje_total = puntaje_obtenido + "/" + handler.getPuntajeTotalPregunta(encuesta, re);

            for (int i=0; i < InsertEvaluacionDemos.size(); i++) {
                String categoria_pregunta = InsertEvaluacionDemos.get(i).getCategoria();
                String pregunta = InsertEvaluacionDemos.get(i).getPregunta();
                String respuesta_pregunta = InsertEvaluacionDemos.get(i).getRespuesta();
                String calificacion_individual = InsertEvaluacionDemos.get(i).getPuntaje();
                String foto_pregunta = InsertEvaluacionDemos.get(i).getFoto();
                String comentario_pregunta = InsertEvaluacionDemos.get(i).getComentario();
                String tipo_pregunta = InsertEvaluacionDemos.get(i).getTipo_pregunta();

                ContentValues values = new ContentValues();
                Log.i("asas",""+ciudad);

                values.put(ContractInsertEvaluacionEncuesta.Columnas.CODIGO, id_pdv);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.USUARIO, user);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.CIUDAD, ciudad);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.LOCAL, punto_venta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.GESTOR_ASIGNADO, mercaderista);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA, encuesta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.RE, re);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.CATEGORIA, categoria_pregunta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.PREGUNTA, pregunta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.TIPO_PREGUNTA, tipo_pregunta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.RESPUESTA, respuesta_pregunta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.FOTO_PREGUNTA, foto_pregunta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO_PREGUNTA, comentario_pregunta);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.FOTO_FACHADA, foto_fachada);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION, calificacion);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_INDIVIDUAL, calificacion_individual);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_TOTAL, puntaje_total);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.SATISFACCION, satisfaccion);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO, comentario);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.APOYO, "0");
                values.put(ContractInsertEvaluacionEncuesta.Columnas.SUPERVISOR, supervisor);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.FECHA, fecha);
                values.put(ContractInsertEvaluacionEncuesta.Columnas.HORA, hora);
                values.put(Constantes.PENDIENTE_INSERCION, 1);

                getContentResolver().insert(ContractInsertEvaluacionEncuesta.CONTENT_URI, values);
            }

            if(VerificarNet.hayConexion(getApplicationContext())) {
                //SyncAdapter.sincronizarAhora(this, true, Constantes.insertEvaluacionDemo,usuarioCursor,"",null,null,null);
                Log.i("sdvid","entrooooo"  );
                SyncAdapter.sincronizarAhora(getApplicationContext(), true, Constantes.insertEvaluacionDemo, usuarioCursor);

                Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
            }
            //generatePDF(respuestas_positivas, satisfaccion, puntaje_total);
            limpiarFormulario();
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //falla abrir pdf impl pdfviewer
    /*private void generatePDF(int respuestas_positivas, String satisfaccion, String puntaje_total) throws IOException {
        // Create a new document
        PdfDocument pdfDocument = new PdfDocument();

        // Page Info
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1920, 1).create();

        // Start a page
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Paint objects
        Paint paint = new Paint();
        Paint titlePaint = new Paint();
        Paint textPaint = new Paint();
        Paint boldTextPaint = new Paint();
        Paint redTextPaint = new Paint();
        Paint calificacionTitleTextPaint = new Paint();
        Paint calificacionTextPaint = new Paint();
        Paint satisfaccionTextPaint = new Paint();
        Paint footerTextPaint = new Paint();
        Paint bodyTextPaint = new Paint();
        Paint pTitle = new Paint();
        Paint pSubtitle = new Paint();
        Paint pBody = new Paint();

        // Load custom font
        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        Typeface robotoBold = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");

        // Load bitmap for the logo
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xplora_logotipo); // Replace with your logo
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 400, 140, false);

        // Load icons
        Bitmap iconCheck = BitmapFactory.decodeResource(getResources(), R.drawable.icon_check);
        Bitmap scaledIconCheck = Bitmap.createScaledBitmap(iconCheck, 50, 50, false);
        Bitmap iconCross = BitmapFactory.decodeResource(getResources(), R.drawable.icon_cross);
        Bitmap scaledIconCross = Bitmap.createScaledBitmap(iconCross, 50, 50, false);

        // Draw the logo
        canvas.drawBitmap(scaledBitmap, (canvas.getWidth() - scaledBitmap.getWidth()) / 2, 40, paint);

        // Title
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(36);
        titlePaint.setTypeface(robotoBold);
        canvas.drawText("EVALUACIÓN DE DEMOSTRACIÓN", (canvas.getWidth() - titlePaint.measureText("INFORME DE VISITA DEL VENDEDOR")) / 2, 300, titlePaint);

        // Subtitle
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(28);
        textPaint.setTypeface(roboto);

        // Bold text
        boldTextPaint.setColor(Color.BLACK);
        boldTextPaint.setTextSize(28);
        boldTextPaint.setTypeface(robotoBold);

        redTextPaint.setColor(Color.RED);
        redTextPaint.setTextSize(28);
        redTextPaint.setTypeface(robotoBold);

        calificacionTitleTextPaint.setColor(Color.RED);
        calificacionTitleTextPaint.setTextSize(34);
        calificacionTitleTextPaint.setTypeface(robotoBold);

        calificacionTextPaint.setColor(Color.BLACK);
        calificacionTextPaint.setTextSize(34);
        calificacionTextPaint.setTypeface(roboto);

        footerTextPaint.setTextSize(28);
        footerTextPaint.setTypeface(robotoBold);

        satisfaccionTextPaint.setTextSize(28);
        satisfaccionTextPaint.setTypeface(robotoBold);

        canvas.drawText("Evaluador: ", 40, 400, redTextPaint);
        canvas.drawText(user.toUpperCase(), 375, 400, textPaint);

        canvas.drawText("Fecha de la evaluación: ", 40, 450, redTextPaint);
        canvas.drawText(fecha, 375, 450, textPaint);

        canvas.drawText("Hora: ", 40, 500, redTextPaint);
        canvas.drawText(hora, 375, 500, textPaint);

        canvas.drawText("PDV: ", 40, 550, redTextPaint);
        canvas.drawText(punto_venta, 375, 550, textPaint);

        canvas.drawText("Promotor evaluado: ", 40, 600, redTextPaint);
        canvas.drawText(mercaderista, 375, 600, textPaint);

        canvas.drawText("Calificación: ", 40, 675, calificacionTitleTextPaint);
        canvas.drawText(puntaje_total, 375, 675, calificacionTextPaint);

        *//*canvas.drawText("Gestor responsable: ", 40, 650, redTextPaint);
        canvas.drawText(gestor, 375, 650, textPaint);*//*

        // Body Text Paint
        bodyTextPaint.setColor(Color.BLACK);
        bodyTextPaint.setTextSize(24);
        bodyTextPaint.setTypeface(roboto);

        int startY = 750;
        int lineHeight = 100;
        int footer = startY;

        TextPaint textPaintBody = new TextPaint();
        textPaintBody.setColor(Color.BLACK);
        textPaintBody.setTextSize(24);
        textPaintBody.setTypeface(roboto);

        for (int i = 0; i < InsertEvaluacionDemos.size(); i++) {
            String pregunta = (i+1) + ". " + InsertEvaluacionDemos.get(i).getPregunta();
            StaticLayout staticLayout = StaticLayout.Builder.obtain(pregunta, 0, pregunta.length(), textPaintBody, canvas.getWidth() - 150)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setLineSpacing(0.0f, 1.0f)
                    .setIncludePad(false)
                    .build();

            canvas.save();
            canvas.translate(40, startY + (i * lineHeight));
            staticLayout.draw(canvas);
            canvas.restore();

            if (InsertEvaluacionDemos.get(i).getTipo_pregunta().equalsIgnoreCase("ESCALA")) {

                canvas.drawText(InsertEvaluacionDemos.get(i).getRespuesta(),canvas.getWidth() - 90 ,   startY + (i * lineHeight) + 10, textPaintBody);

            } else {

                if (InsertEvaluacionDemos.get(i).getRespuesta().equalsIgnoreCase("SI")) {
                    canvas.drawBitmap(scaledIconCheck, canvas.getWidth() - 100, startY + (i * lineHeight) - 10, paint);
                } else {
                    canvas.drawBitmap(scaledIconCross, canvas.getWidth() - 100, startY + (i * lineHeight) - 10, paint);
                }
            }


            footer = startY + (i * lineHeight) + 50;
        }

        if (respuestas_positivas >= 9) {
            footerTextPaint.setColor(Color.GREEN);
        } else if (respuestas_positivas >= 6) {
            footerTextPaint.setColor(Color.YELLOW);
        } else {
            footerTextPaint.setColor(Color.RED);
        }

        *//*if (satisfaccion.equalsIgnoreCase("Muy mala")) {
            satisfaccionTextPaint.setColor(Color.RED);
        } else if (satisfaccion.equalsIgnoreCase("Mala")) {
            satisfaccionTextPaint.setColor(Color.RED);
        } else if (satisfaccion.equalsIgnoreCase("Ni buena ni mala")) {
            satisfaccionTextPaint.setColor(Color.YELLOW);
        } else if (satisfaccion.equalsIgnoreCase("Buena")) {
            satisfaccionTextPaint.setColor(Color.GREEN);
        } else if (satisfaccion.equalsIgnoreCase("Excelente")) {
            satisfaccionTextPaint.setColor(Color.GREEN);
        }*//*

        // Final remarks
        *//*canvas.drawText("Nivel de satisfacción del servicio:", 40, canvas.getHeight() - 100, boldTextPaint);
        canvas.drawText(satisfaccion, 850, canvas.getHeight() - 100, satisfaccionTextPaint);
        canvas.drawText("Calificación del PDV:", 40, canvas.getHeight() - 50, boldTextPaint);
        canvas.drawText(String.valueOf(respuestas_positivas) + "/" + InsertEvaluacionDemos.size(), 850, canvas.getHeight() - 50, footerTextPaint);*//*

        // Finish the page
        pdfDocument.finishPage(page);

        // Write the document to an output file
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/PDFs/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String file_name = fecha.replace("/","") + hora.replace(":", "");
        String targetPdf = directory_path + file_name + ".pdf";
        Log.i(TAG,""+targetPdf);
        File filePath = new File(targetPdf);
        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "PDF generado exitosamente!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG,""+e.getMessage());
            Toast.makeText(this, "La generación del PDF falló", Toast.LENGTH_SHORT).show();
        }

        // Close the document
        pdfDocument.close();

        // Open the PDF file
        openGeneratedPDF(filePath);
    }*/

    //falla metodo
    /*private void openGeneratedPDF(File file) {
        // Get URI for the file
        final String n = "com.luckyapp.luckyecuador.colgatea5p.fileprovider";

        Uri uri = FileProvider.getUriForFile(this,
                n + ".provider", file);
        if (uri == null) {
            Log.e("URI PDF", "Error generating file URI");
            return;
        }

        // Create an intent to view the PDF
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Lector de PDF no encontrado", Toast.LENGTH_SHORT).show();
        }
    }*/


    private void limpiarFormulario() {
        InsertEvaluacionDemos.clear();
        //imageView.setImageDrawable(null);
        //rgCalificacion.clearCheck();
        //txtComentario.setText("");
    }

    /* Adapter */
    public class CustomAdapter extends ArrayAdapter<BaseEvaluacionPromotor> implements Filterable {

        private ArrayList<BaseEvaluacionPromotor> values;
        private Context context;
        private boolean[] checkBoxState;
        private Map<Integer, Boolean> categoriasVisibles;

        public CustomAdapter(Context context, ArrayList<BaseEvaluacionPromotor> values) {
            super(context, 0, values);
            this.values = values;
            this.context = context;
            this.checkBoxState = new boolean[values.size()];
            this.categoriasVisibles = new HashMap<>();

            inicializarCategoriasVisibles();
        }

        public ArrayList<BaseEvaluacionPromotor> getValues() {
            return values;
        }

        private void inicializarCategoriasVisibles() {
            Set<String> categoriasMostradas = new HashSet<>();
            for (int i = 0; i < values.size(); i++) {
                String categoria = values.get(i).getCategoria();
                if (categoriasMostradas.contains(categoria)) {
                    categoriasVisibles.put(i, false);
                } else {
                    categoriasVisibles.put(i, true);
                    categoriasMostradas.add(categoria);
                }
            }
        }

        public class ViewHolder {
            View dividerCategoria;
            View dividerCategoriaF;
            TextView lblCategoria;
            TextView lblCategoriaF;
            TextView lblPregunta;
            TextView lblPreguntaF;
            RadioGroup rgCodifica;
            RadioGroup rgCalificacion;
            RadioButton rba;
            RadioButton rbb;
            RadioButton rbc;
            RadioButton rbd;
            RadioButton rbe;
            LinearLayout layoutFoto;
            LinearLayout layoutPregFinal;
            LinearLayout layoutPreg;
            ImageButton ibCargarFoto;
            ImageView ivFoto;
            EditText txtComentario;

            LinearLayout layoutPregImagen;
            ZoomageView zoomageViewU;

            //escala
            LinearLayout layoutPregFinalImagen;
            ZoomageView myZoomageViewFinal;


            //pregunta abierta
            LinearLayout layoutPregA;
            TextView lblCategoriaA;
            TextView lblPreguntaA;
            View dividerCategoriaA;
            EditText respuestaPreg;
            EditText respuestaPregNum;
            EditText respuestaPregGeneral;
            LinearLayout layoutPregAbImagen;
            ZoomageView myZoomageViewA;


            //pregunta multiple
            LinearLayout layoutPregM;
            TextView lblCategoriaM;
            TextView lblPreguntaM;
            View dividerCategoriaM;
            CheckBox cb_a, cb_b, cb_c, cb_d, cb_e;
            LinearLayout layoutPregMultImagen;
            ZoomageView myZoomageViewMult;

            //pregunta foto
            LinearLayout layoutPregFoto;
            TextView lblCategoriaFoto;
            TextView lblPreguntaFoto;
            View dividerCategoriaFoto;
            ImageButton btnCamera;
            ImageView imgView;
            LinearLayout layoutPregFotoImagen;
            ZoomageView myZoomageViewFoto;
        }

        @Override
        public int getViewTypeCount() {
            // TODO Auto-generated method stub
            return values.size();
        }

        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        InputFilter soloLetras = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                       return "";
                    }
                }
                return null;
            }
        };

        InputFilter soloLetras2 = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // Solo letras y espacios permitidos
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };


        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                        return "";
                    }
                }
                return null;
            }
        };

        InputFilter soloNumeros2 = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i)) && source.charAt(i) != ',') {
                        return "";
                    }
                }
                return null;
            }
        };

        InputFilter soloNumeros = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("[0-9.]*")) {
                    return null;
                }
                return "";
            }
        };




        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder vHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row_visita_mercado, parent, false);
                vHolder = new ViewHolder();

                //vista pregunta normal
                vHolder.layoutPreg = convertView.findViewById(R.id.layoutPreg);
                vHolder.dividerCategoria = convertView.findViewById(R.id.dividerCategoria);
                vHolder.lblCategoria = convertView.findViewById(R.id.lblCategoria);
                vHolder.lblPregunta = convertView.findViewById(R.id.lblPregunta);
                vHolder.rgCodifica = convertView.findViewById(R.id.rgCodifica);
                vHolder.rba = convertView.findViewById(R.id.rba);
                vHolder.rbb = convertView.findViewById(R.id.rbb);
                vHolder.rbc = convertView.findViewById(R.id.rbc);
                vHolder.rbd = convertView.findViewById(R.id.rbd);
                vHolder.rbe = convertView.findViewById(R.id.rbe);
                vHolder.layoutFoto = convertView.findViewById(R.id.layoutFoto);
                vHolder.layoutPregImagen = convertView.findViewById(R.id.layoutPregImagen);
                vHolder.zoomageViewU = convertView.findViewById(R.id.myZoomageViewU);

                //vista pregunta escala
                vHolder.layoutPregFinal = convertView.findViewById(R.id.layoutPregFinal);
                vHolder.dividerCategoriaF = convertView.findViewById(R.id.dividerCategoriaF);
                vHolder.lblCategoriaF = convertView.findViewById(R.id.lbCategoriaF);
                vHolder.lblPreguntaF = convertView.findViewById(R.id.lblPreguntaF);
                vHolder.rgCalificacion = convertView.findViewById(R.id.rgCalificacion);
                vHolder.layoutPregFinalImagen = convertView.findViewById(R.id.layoutPregFinalImagen);
                vHolder.myZoomageViewFinal = convertView.findViewById(R.id.myZoomageViewFinal);

                //vista pregunta abierta
                vHolder.layoutPregA = convertView.findViewById(R.id.layoutPregAb);
                vHolder.lblCategoriaA = convertView.findViewById(R.id.lblCategoriaA);
                vHolder.lblPreguntaA = convertView.findViewById(R.id.lblPreguntaA);
                vHolder.dividerCategoriaA = convertView.findViewById(R.id.dividerCategoriaA);
                vHolder.respuestaPreg = convertView.findViewById(R.id.respuestaPreg);
                vHolder.respuestaPregNum = convertView.findViewById(R.id.respuestaPregNum);
                vHolder.respuestaPregGeneral = convertView.findViewById(R.id.respuestaPregGeneral);
                vHolder.layoutPregAbImagen = convertView.findViewById(R.id.layoutPregAbImagen);
                vHolder.myZoomageViewA = convertView.findViewById(R.id.myZoomageViewA);


                //vista pregunta multiple
                vHolder.layoutPregM = convertView.findViewById(R.id.layoutPregMult);
                vHolder.lblCategoriaM = convertView.findViewById(R.id.lblCategoriaM);
                vHolder.lblPreguntaM = convertView.findViewById(R.id.lblPreguntaM);
                vHolder.dividerCategoriaM = convertView.findViewById(R.id.dividerCategoriaM);
                vHolder.cb_a = convertView.findViewById(R.id.check_opta);
                vHolder.cb_b = convertView.findViewById(R.id.check_optb);
                vHolder.cb_c = convertView.findViewById(R.id.check_optc);
                vHolder.cb_d = convertView.findViewById(R.id.check_optd);
                vHolder.cb_e = convertView.findViewById(R.id.check_opte);
                vHolder.layoutPregMultImagen = convertView.findViewById(R.id.layoutPregMultImagen);
                vHolder.myZoomageViewMult = convertView.findViewById(R.id.myZoomageViewMult);

                //vista pregunta foto
                vHolder.layoutPregFoto = convertView.findViewById(R.id.layoutPregFoto);
                vHolder.lblCategoriaFoto = convertView.findViewById(R.id.lblCategoriaFoto);
                vHolder.lblPreguntaFoto = convertView.findViewById(R.id.lblPreguntaFoto);
                vHolder.dividerCategoriaFoto = convertView.findViewById(R.id.dividerCategoriaFoto);
                vHolder.btnCamera = convertView.findViewById(R.id.btnCamara);
                vHolder.imgView = convertView.findViewById(R.id.foto);
                vHolder.layoutPregFotoImagen = convertView.findViewById(R.id.layoutPregFotoImagen);
                vHolder.myZoomageViewFoto = convertView.findViewById(R.id.myZoomageViewFoto);



                vHolder.ibCargarFoto = convertView.findViewById(R.id.ibCargarFoto);
                vHolder.ivFoto = convertView.findViewById(R.id.ivFoto);
                vHolder.txtComentario = convertView.findViewById(R.id.txtComentario);

                convertView.setTag(vHolder);
            } else {
                vHolder = (ViewHolder) convertView.getTag();
            }

            if (values.size() > 0) {
                String categoria = values.get(position).getCategoria();
                String puntajePorPregunta = values.get(position).getPuntaje_por_pregunta();

                Log.i("caaaa",""+values.toString());
                //vHolder.lblPregunta.setText(values.get(position).getPregunta());
//                vHolder.layoutFoto.setVisibility(View.GONE);

                Log.i("ccsaa","" +values.get(position).getTipo_pregunta());

                if (values.get(position).getTipo_pregunta().equalsIgnoreCase("ESCALA")){
                    vHolder.layoutPregFinal.setVisibility(View.VISIBLE);
                    vHolder.layoutPreg.setVisibility(View.GONE);
                    vHolder.layoutPregA.setVisibility(View.GONE);
                    vHolder.layoutPregM.setVisibility(View.GONE);
                    vHolder.layoutPregFoto.setVisibility(View.GONE);

                    vHolder.lblPreguntaF.setText(values.get(position).getPregunta());
                    if (Boolean.TRUE.equals(categoriasVisibles.get(position))) {
                        vHolder.dividerCategoriaF.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaF.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaF.setText(categoria);
                        Log.i("CATEGORIA f", categoria);
                    } else {
                        vHolder.dividerCategoriaF.setVisibility(View.GONE);
                        vHolder.lblCategoriaF.setVisibility(View.GONE);
                        vHolder.lblCategoriaF.setText(categoria);
                    }

                    if(!values.get(position).getFoto().equalsIgnoreCase("-")){
                        vHolder.layoutPregFinalImagen.setVisibility(View.VISIBLE);
                        Picasso.get().load(values.get(position).getFoto()).into(vHolder.myZoomageViewFinal);
                    }else{
                        vHolder.layoutPregFinalImagen.setVisibility(View.GONE);
                    }

                } else if(values.get(position).getTipo_pregunta().equalsIgnoreCase("UNICA")){
                    vHolder.layoutPregFinal.setVisibility(View.GONE);
                    vHolder.layoutPreg.setVisibility(View.VISIBLE);
                    vHolder.layoutPregA.setVisibility(View.GONE);
                    vHolder.layoutPregM.setVisibility(View.GONE);
                    vHolder.layoutPregFoto.setVisibility(View.GONE);

                    vHolder.rba.setText(values.get(position).getOpc_a());
                    vHolder.rbb.setText(values.get(position).getOpc_b());
                    vHolder.rbc.setText(values.get(position).getOpc_c());
                    vHolder.rbd.setText(values.get(position).getOpc_d());
                    vHolder.rbe.setText(values.get(position).getOpc_e());

                    if(values.get(position).getOpc_a() == null || values.get(position).getOpc_a().equalsIgnoreCase("-")){
                        vHolder.rba.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_b() == null || values.get(position).getOpc_b().equalsIgnoreCase("-")){
                        vHolder.rbb.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_c() == null || values.get(position).getOpc_c().equalsIgnoreCase("-")){
                        vHolder.rbc.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_d() == null || values.get(position).getOpc_d().equalsIgnoreCase("-")){
                        vHolder.rbd.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_e() == null || values.get(position).getOpc_e().equalsIgnoreCase("-")){
                        vHolder.rbe.setVisibility(View.GONE);
                    }

                    vHolder.lblPregunta.setText(values.get(position).getPregunta());
                    if (Boolean.TRUE.equals(categoriasVisibles.get(position))) {
                        vHolder.dividerCategoria.setVisibility(View.VISIBLE);
                        vHolder.lblCategoria.setVisibility(View.VISIBLE);
                        vHolder.lblCategoria.setText(categoria);
                        Log.i("CATEGORIA v", categoria);
                    } else {
                        vHolder.dividerCategoria.setVisibility(View.GONE);
                        vHolder.lblCategoria.setVisibility(View.GONE);
                        vHolder.lblCategoria.setText(categoria);
                    }


                    if(!values.get(position).getFoto().equalsIgnoreCase("-")){
                        vHolder.layoutPregImagen.setVisibility(View.VISIBLE);
                        Picasso.get().load(values.get(position).getFoto()).into(vHolder.zoomageViewU);
                    }else{
                        vHolder.layoutPregImagen.setVisibility(View.GONE);
                    }

                } else if(values.get(position).getTipo_pregunta().equalsIgnoreCase("ABIERTA")){
                    vHolder.layoutPregFinal.setVisibility(View.GONE);
                    vHolder.layoutPreg.setVisibility(View.GONE);
                    vHolder.layoutPregA.setVisibility(View.VISIBLE);
                    vHolder.layoutPregM.setVisibility(View.GONE);
                    vHolder.layoutPregFoto.setVisibility(View.GONE);

                    vHolder.lblPreguntaA.setText(values.get(position).getPregunta());
                    if (values.get(position).getTipo_campo().equals("TEXTO")){
                        vHolder.respuestaPreg.setVisibility(View.VISIBLE);
                        vHolder.respuestaPregNum.setVisibility(View.GONE);
                        vHolder.respuestaPregGeneral.setVisibility(View.GONE);
                        //vHolder.respuestaPreg.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                        //vHolder.respuestaPreg.setFilters(new InputFilter[]{soloLetras2});
                    }
                    if (values.get(position).getTipo_campo().equals("NUMERO")){
                        vHolder.respuestaPreg.setVisibility(View.GONE);
                        vHolder.respuestaPregGeneral.setVisibility(View.GONE);
                        vHolder.respuestaPregNum.setVisibility(View.VISIBLE);
                        //vHolder.respuestaPreg.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        //vHolder.respuestaPreg.setFilters(new InputFilter[]{soloNumeros});
                    }
                    if(values.get(position).getTipo_campo().equals("")){
                        vHolder.respuestaPreg.setVisibility(View.GONE);
                        vHolder.respuestaPregGeneral.setVisibility(View.VISIBLE);
                        vHolder.respuestaPregNum.setVisibility(View.GONE);
                    }

                    if (Boolean.TRUE.equals(categoriasVisibles.get(position))) {
                        vHolder.dividerCategoriaA.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaA.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaA.setText(categoria);
                        Log.i("CATEGORIA A", categoria);
                    } else {
                        vHolder.dividerCategoriaA.setVisibility(View.GONE);
                        vHolder.lblCategoriaA.setVisibility(View.GONE);
                        vHolder.lblCategoriaA.setText(categoria);
                    }

                    if(!values.get(position).getFoto().equalsIgnoreCase("-")){
                        vHolder.layoutPregAbImagen.setVisibility(View.VISIBLE);
                        Picasso.get().load(values.get(position).getFoto()).into(vHolder.myZoomageViewA);
                    }else{
                        vHolder.layoutPregAbImagen.setVisibility(View.GONE);
                    }


                } else if(values.get(position).getTipo_pregunta().equalsIgnoreCase("MULTIPLE")){
                    vHolder.layoutPregFinal.setVisibility(View.GONE);
                    vHolder.layoutPreg.setVisibility(View.GONE);
                    vHolder.layoutPregA.setVisibility(View.GONE);
                    vHolder.layoutPregFoto.setVisibility(View.GONE);
                    vHolder.layoutPregM.setVisibility(View.VISIBLE);

                    vHolder.lblPreguntaM.setText(values.get(position).getPregunta());
                    vHolder.cb_a.setText(values.get(position).getOpc_a());
                    vHolder.cb_b.setText(values.get(position).getOpc_b());
                    vHolder.cb_c.setText(values.get(position).getOpc_c());
                    vHolder.cb_d.setText(values.get(position).getOpc_d());
                    vHolder.cb_e.setText(values.get(position).getOpc_e());

                    /*if(){

                    }*/
                    Log.i("opcionesaa","a: "+values.get(position).getOpc_a());
                    Log.i("opcionesaa","b: "+values.get(position).getOpc_b());
                    Log.i("opcionesaa","c: "+values.get(position).getOpc_c());
                    Log.i("opcionesaa","d: "+values.get(position).getOpc_d());
                    Log.i("opcionesaa","e: "+values.get(position).getOpc_e());
                    Log.i("opcionesaa","-----------------------------");

                    if(values.get(position).getOpc_a() == null || values.get(position).getOpc_a().equalsIgnoreCase("-")){
                        vHolder.cb_a.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_b() == null || values.get(position).getOpc_b().equalsIgnoreCase("-")){
                        vHolder.cb_b.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_c() == null || values.get(position).getOpc_c().equalsIgnoreCase("-")){
                        vHolder.cb_c.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_d() == null || values.get(position).getOpc_d().equalsIgnoreCase("-")){
                        vHolder.cb_d.setVisibility(View.GONE);
                    }
                    if(values.get(position).getOpc_e() == null || values.get(position).getOpc_e().equalsIgnoreCase("-")){
                        vHolder.cb_e.setVisibility(View.GONE);
                    }

                    if (Boolean.TRUE.equals(categoriasVisibles.get(position))) {
                        vHolder.dividerCategoriaM.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaM.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaM.setText(categoria);
                        Log.i("CATEGORIA M", categoria);
                    } else {
                        vHolder.dividerCategoriaM.setVisibility(View.GONE);
                        vHolder.lblCategoriaM.setVisibility(View.GONE);
                        vHolder.lblCategoriaM.setText(categoria);
                    }

                    if(!values.get(position).getFoto().equalsIgnoreCase("-")){
                        vHolder.layoutPregMultImagen.setVisibility(View.VISIBLE);
                        Picasso.get().load(values.get(position).getFoto()).into(vHolder.myZoomageViewMult);
                    }else{
                        vHolder.layoutPregMultImagen.setVisibility(View.GONE);
                    }

                } else if(values.get(position).getTipo_pregunta().equalsIgnoreCase("FOTO")){
                    vHolder.layoutPregFinal.setVisibility(View.GONE);
                    vHolder.layoutPreg.setVisibility(View.GONE);
                    vHolder.layoutPregA.setVisibility(View.GONE);
                    vHolder.layoutPregFoto.setVisibility(View.VISIBLE);
                    vHolder.layoutPregM.setVisibility(View.GONE);

                    vHolder.lblPreguntaFoto.setText(values.get(position).getPregunta());
                    if (Boolean.TRUE.equals(categoriasVisibles.get(position))) {
                        vHolder.dividerCategoriaFoto.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaFoto.setVisibility(View.VISIBLE);
                        vHolder.lblCategoriaFoto.setText(categoria);
                        Log.i("CATEGORIA FOTO", categoria);
                    } else {
                        vHolder.dividerCategoriaFoto.setVisibility(View.GONE);
                        vHolder.lblCategoriaFoto.setVisibility(View.GONE);
                        vHolder.lblCategoriaFoto.setText(categoria);
                    }

                    if(!values.get(position).getFoto().equalsIgnoreCase("-")){
                        vHolder.layoutPregFotoImagen.setVisibility(View.VISIBLE);
                        Picasso.get().load(values.get(position).getFoto()).into(vHolder.myZoomageViewFoto);
                    }else{
                        vHolder.layoutPregFotoImagen.setVisibility(View.GONE);
                    }

                }
                ajustarAlturaListView();


                final ViewHolder finalv = vHolder;

                //imageView = vHolder.imgView;

                vHolder.btnCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageView = vHolder.imgView;
                        opciones();
                    }
                });


                vHolder.rgCodifica.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rba:
                                //ajustarAlturaListView();
                                break;
                            case R.id.rbb:
                                //ajustarAlturaListView();
                                break;
                            case R.id.rbc:
                                //ajustarAlturaListView();
                                break;
                            case R.id.rbd:
                                //ajustarAlturaListView();
                                break;
                            case R.id.rbe:
                                //ajustarAlturaListView();
                                break;
                            default:
                                Log.i("RADIO_BUTTON", "Ninguno seleccionado en posición: " + position);
                                break;
                        }
                    }
                });

                /*vHolder.ibCargarFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView = vHolder.ivFoto;
                        cargarImagen();
                    }
                });*/
            }



            return convertView;
        }


    }

    public static ImageView imageView;

    private void opciones(){
        final CharSequence []opciones={"Abrir Camara","Abrir Galeria","Cancelar"};
        final androidx.appcompat.app.AlertDialog.Builder alertOpciones=new androidx.appcompat.app.AlertDialog.Builder(this);
        alertOpciones.setTitle("Seleccione una opcion");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Abrir Camara")) {
                    Intent n = new Intent(getApplicationContext(), CameraActivity.class);
                    n.putExtra("activity", "encuesta");
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
    ActivityResultLauncher<Intent> startActivityIntent2;



    private Bitmap bitmap;
    private Bitmap bitmapfinal;

    private String getPathFromURI(Uri filePath) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(filePath, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void ajustarAlturaListView2() {
        ListAdapter adapter = listview.getAdapter();
        if (adapter == null) {
            return;
        }

        int count = adapter.getCount();

        int rpositivas = 0;
        int rnegativas = 0;

        try {
            View view;
            RadioGroup rgCodifica = null;
            ImageView imageView = null;

            int listLength = listview.getChildCount();
            Log.i("csaq",""+listLength);
            Log.i("csaq",""+count);
            if (count == listLength) {
                for (int i = 0; i < listLength; i++) {
                    view = listview.getChildAt(i);
                    rgCodifica = view.findViewById(R.id.rgCodifica);

                    int selectedId = rgCodifica.getCheckedRadioButtonId();
                    if (selectedId == R.id.rba) {
                        rpositivas++;
                    } else if (selectedId == R.id.rbb) {
                        rnegativas++;
                    } else {
                        rpositivas++;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Error Listado", e.getMessage());
        }

        int totalHeight = 0;
        int size_p = 500;
        int size_n = 750;

        if (rpositivas == 0 && rnegativas == 0) {
            totalHeight = 500 * count;
        } else {
            totalHeight = (size_p * rpositivas) + (size_n * rnegativas);
        }

        Log.i("totalHeight", totalHeight + "");

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight;
        listview.setLayoutParams(params);
        listview.requestLayout();
    }

    public void ajustarAlturaListView3() {
        ListAdapter adapter = listview.getAdapter();
        if (adapter == null) {
            return;
        }

        int count = adapter.getCount();

        int rpositivas = 0;
        int rnegativas = 0;
        int totalHeight = 0;
        try {
            View view;
            RadioGroup rgCodifica = null;
            LinearLayout linear1 = null;
            LinearLayout linear2 = null;
            LinearLayout linear3 = null;
            LinearLayout linear4 = null;
            LinearLayout linear5 = null;

            RadioButton rb3;
            RadioButton rb4;
            RadioButton rb5;

            CheckBox cb3;
            CheckBox cb4;
            CheckBox cb5;

            int listLength = listview.getChildCount();
            if (count == listLength) {
                for (int i = 0; i < listLength; i++) {
                    view = listview.getChildAt(i);
                    rgCodifica = view.findViewById(R.id.rgCodifica);

                    int selectedId = rgCodifica.getCheckedRadioButtonId();
                    if (selectedId == R.id.rba) {
                        rpositivas++;
                    } else if (selectedId == R.id.rbb) {
                        rnegativas++;
                    } else {
                        rpositivas++;
                    }
                }
            }
            Log.i("cda count",""+count);
            for (int i = 0; i < count; i++) {
                view = listview.getChildAt(i);
                //radios
                rb3 = view.findViewById(R.id.rbc);
                rb4 = view.findViewById(R.id.rbd);
                rb5 = view.findViewById(R.id.rbe);

                //checkbox
                cb3 = view.findViewById(R.id.check_optc);
                cb4 = view.findViewById(R.id.check_optd);
                cb5 = view.findViewById(R.id.check_opte);

                //linear
                linear1 = view.findViewById(R.id.layoutPregImagen);
                linear2 = view.findViewById(R.id.layoutPregAbImagen);
                linear3 = view.findViewById(R.id.layoutPregMultImagen);
                linear4 = view.findViewById(R.id.layoutPregFotoImagen);
                linear5 = view.findViewById(R.id.layoutPregFinalImagen);

                boolean isLinear1Visible = linear1.getVisibility() == View.VISIBLE;
                boolean isLinear2Visible = linear2.getVisibility() == View.VISIBLE;
                boolean isLinear3Visible = linear3.getVisibility() == View.VISIBLE;
                boolean isLinear4Visible = linear4.getVisibility() == View.VISIBLE;
                boolean isLinear5Visible = linear5.getVisibility() == View.VISIBLE;

                boolean isRadio3Visible = rb3.getVisibility() == View.VISIBLE;
                boolean isRadio4Visible = rb4.getVisibility() == View.VISIBLE;
                boolean isRadio5Visible = rb5.getVisibility() == View.VISIBLE;

                boolean isCheckBox3Visible = cb3.getVisibility() == View.VISIBLE;
                boolean isCheckBox4Visible = cb4.getVisibility() == View.VISIBLE;
                boolean isCheckBox5Visible = cb5.getVisibility() == View.VISIBLE;

                if (isLinear1Visible) {
                    totalHeight += 500;
                }
                if (isLinear2Visible) {
                    totalHeight += 500;
                }
                if (isLinear3Visible) {
                    totalHeight += 500;
                }
                if (isLinear4Visible) {
                    totalHeight += 500;
                }
                if (isLinear5Visible) {
                    totalHeight += 500;
                }

                if(isRadio3Visible){
                    totalHeight += 15;
                }
                if(isRadio4Visible){
                    totalHeight += 15;
                }
                if(isRadio5Visible){
                    totalHeight += 15;
                }

                if(isCheckBox3Visible){
                    totalHeight += 15;
                }
                if(isCheckBox4Visible){
                    totalHeight += 15;
                }
                if(isCheckBox5Visible){
                    totalHeight += 15;
                }

            }

        } catch (Exception e) {
            Log.e("Error Listado", e.getMessage());
        }


        int size_p = 500;
        int size_n = 750;

        if (rpositivas == 0 && rnegativas == 0) {
            totalHeight += 500 * count;
        } else {
            totalHeight += (size_p * rpositivas) + (size_n * rnegativas);
        }

        Log.i("totalHeight2", totalHeight + "");

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight;
        listview.setLayoutParams(params);
        listview.requestLayout();
    }

    public void ajustarAlturaListView() {
        ListAdapter adapter = listview.getAdapter();
        if (adapter == null) {
            return;
        }

        int count = adapter.getCount();

        int rpositivas = 0;
        int rnegativas = 0;
        int totalHeight = 0;
        try {
            View view;
            RadioGroup rgCodifica = null;
            LinearLayout linear1 = null;
            LinearLayout linear2 = null;
            LinearLayout linear3 = null;
            LinearLayout linear4 = null;
            LinearLayout linear5 = null;

            RadioButton rb3;
            RadioButton rb4;
            RadioButton rb5;

            CheckBox cb3;
            CheckBox cb4;
            CheckBox cb5;

            int listLength = listview.getChildCount();
            if (count == listLength) {
                for (int i = 0; i < listLength; i++) {
                    view = listview.getChildAt(i);
                    rgCodifica = view.findViewById(R.id.rgCodifica);

                    int selectedId = rgCodifica.getCheckedRadioButtonId();
                    if (selectedId == R.id.rba) {
                        rpositivas++;
                    } else if (selectedId == R.id.rbb) {
                        rnegativas++;
                    } else {
                        rpositivas++;
                    }
                }
            }
            int n = 900 * count;
            totalHeight += n;
        } catch (Exception e) {
            Log.e("Error Listado", e.getMessage());
        }


        int size_p = 500;
        int size_n = 750;

        if (rpositivas == 0 && rnegativas == 0) {
            totalHeight += 500 * count;
        } else {
            totalHeight += (size_p * rpositivas) + (size_n * rnegativas);
        }

        Log.i("totalHeight2", totalHeight + "");

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight;
        listview.setLayoutParams(params);
        listview.requestLayout();
    }




}