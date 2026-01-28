
package com.tesis.michelle.pin.ui.exhibiciones;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tesis.michelle.pin.CameraActivity;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Clase.BasePortafolioProductos;
import com.tesis.michelle.pin.Clase.Base_tipo_exh;
import com.tesis.michelle.pin.Clase.InsertExhibiciones;
import com.tesis.michelle.pin.Conexion.MarshMallowPermission;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Contracts.ContractInsertExh;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.ImageMark;
import com.tesis.michelle.pin.Utils.RequestPermissions;
import com.tesis.michelle.pin.Utils.SpinnerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ExhibicionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{

    private static final String TAG = ExhibicionFragment.class.getSimpleName();
    ArrayList<InsertExhibiciones> sesion = new ArrayList<InsertExhibiciones>();

    android.app.AlertDialog alertDialog;
    ProgressDialog progressDialog;

    //Views
    private Spinner spTipoExh;
    private Spinner spCategoria;
    private Spinner spSubcategoria;

    private Integer seleccionTipoExh = -1;

    private Spinner spNiveles;

    private Spinner spTipo;

    private Spinner spMarca;
    private Spinner spBrand;
    private Spinner spTipoExhiNuevo;
    private Spinner spTipoFabricante;
    private Button btnPropia;
    private Button btnCompetencia;

    private Button btnCamara;

    private static String p;

    ArrayList<BasePortafolioProductos> listProductos;
    CustomAdapterExhibicion dataAdapter;

    DatabaseHelper handler;

    //Photo Camera
    public ImageButton btnCamera;
    public ImageButton btnRuma;
    public ImageButton btnBiombo;
    public ImageButton btnExhibidorPiso;
    public ImageButton btnIsla;
    public static ImageView imageView;
    Bitmap bitmap;
    private Bitmap bitmapfinal;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private Button btnGuardar;


    private GridView gridView;

    GridViewAdapter gridViewAdapter;

    private final String CARPETA_RAIZ="bassaApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Exhibicion";
    String path;
    String image = "";

    MarshMallowPermission marshMallowPermission;

    private ArrayList<Base_tipo_exh> exhArrayList;
    String categoria, marca;
    private  String fabricante1;
    private  String comentario;

    private String tipoExh,nivel = " ",subcategoria, canal, subcanal;
    private String id_pdv, user, codigo_pdv, punto_venta, fecha, hora, format;
    private boolean vaFoto;

    TextView empty;
    private TextView txtObservacion;
    RecyclerView listview;



    private int posicion;


    private String tipo = "MARCA_PROPIA";

    private String tipo_exhibicion;
    private final String fabricante = "Bassa";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_exhibicion, container, false);

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);
        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();
        LoadData();



        exhArrayList = handler.getTipoExhByChannel(canal);

        marshMallowPermission = new MarshMallowPermission(getActivity());
        //  spTipoExh = (Spinner) rootView.findViewById(R.id.spTipoExh);
        spTipo = (Spinner) rootView.findViewById(R.id.spTipo);  //sera utlizado para tipo vigencia nuevo mpin
        spNiveles = (Spinner) rootView.findViewById(R.id.spCantBandejasObtenidas);
        spMarca = (Spinner) rootView.findViewById(R.id.spMarca);
        spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);
        //   spSubcategoria = (Spinner) rootView.findViewById(R.id.spSubcategoria);
        spBrand = (Spinner) rootView.findViewById(R.id.spMarca); //marca
        spTipoExhiNuevo = (Spinner) rootView.findViewById(R.id.spTipoExhiNuevo); //primer filtro actualizado mpin
        spTipoFabricante = (Spinner) rootView.findViewById(R.id.spTipoFabricante); //primer filtro actualizado mpin

        empty = (TextView) rootView.findViewById(R.id.recyclerview_data_empty);
        txtObservacion = (TextView) rootView.findViewById(R.id.txtObservacion);
        listview = (RecyclerView) rootView.findViewById(R.id.lvSKUCode);
        imageView = (ImageView) rootView.findViewById(R.id.ivFotoExhibiciones);
        btnCamera = (ImageButton) rootView.findViewById(R.id.ibCargarFotoExhibiciones);
        btnPropia  = (Button) rootView.findViewById(R.id.btnPropia);
        btnGuardar = (Button) rootView.findViewById(R.id.btnGuardar);

        btnCompetencia  = (Button) rootView.findViewById(R.id.btnCompetencia);

        gridView = rootView.findViewById(R.id.gridViewTipoExh);


        gridViewAdapter = new GridViewAdapter(rootView.getContext(),new ArrayList<Base_tipo_exh>(exhArrayList));

        filtrarTipoExhiNuevo(canal);



        //  Toast.makeText(getContext(), ""+canal, Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Collections.singletonList("Seleccione"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        gridView.setAdapter(gridViewAdapter);



        //   filtrarCategoria(tipo, fabricante);

        //  spTipoExh.setOnItemSelectedListener(this);
        spTipo.setOnItemSelectedListener(this);
        spNiveles.setOnItemSelectedListener(this);
        spTipoExhiNuevo.setOnItemSelectedListener(this); //nuevo
        spTipoFabricante.setOnItemSelectedListener(this); //nuevo




        btnCamera.setOnClickListener(this);
        btnPropia.setOnClickListener(this);
        btnCompetencia.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

//    private boolean validaPermisos() {
//        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M) {
//            return true;
//        }
//
//        if ((getContext().checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
//            (getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)) {
//            return true;
//        }
//
//        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
//            (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
//            cargarDialogoRecomendacion();
//        }else{
//            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
//        }
//
//        return false;
//    }

    public void filtrarCategoria(String tipo, String fabricante) {
        List<String> operadores = handler.getCategoriaExhibicion(tipo, fabricante);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
    }
//mpin filtro nuevo
public void filtrarTipoExhiNuevo(String canal) {
    try {
        if (spTipoExhiNuevo == null) {
                      return;
        }
        if (canal == null || canal.equals(Constantes.NODATA)) {
                     return;
        }
        ArrayList<Base_tipo_exh> operadores = handler.getTipoExhByChannel(canal);
        if (operadores == null || operadores.isEmpty()) {
                       // Asignar adapter vacío
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    new ArrayList<>(Collections.singletonList("Sin datos"))
            );
            spTipoExhiNuevo.setAdapter(emptyAdapter);
            return;
        }
        // Crear lista de nombres
        List<String> nombres = new ArrayList<>();
        nombres.add("Seleccione"); // Opción por defecto

        for (Base_tipo_exh item : operadores) {
            if (item.getExhibicion() != null && !item.getExhibicion().isEmpty()) {
                nombres.add(item.getExhibicion());

            }
        }

        // Crear y asignar adapter en el hilo principal
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        nombres
                );
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spTipoExhiNuevo.setAdapter(dataAdapter);
                spTipoExhiNuevo.setOnItemSelectedListener(ExhibicionFragment.this);

                Log.i("FILTRO_EXHI", " Adapter asignado con " + nombres.size() + " elementos");
                //Toast.makeText(getContext(), "Datos cargados: " + nombres.size() + " opciones", Toast.LENGTH_SHORT).show();
            }
        });

    } catch (Exception e) {

        e.printStackTrace();
    }
}

    public void filtrarFabricantes() {
        try {
            ArrayList<String> fabricantes = handler.getFabricantesDistinct();

            if (spTipoFabricante == null) {
                Log.e("FABRICANTE", "Spinner de fabricante es null");
                return;
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    fabricantes
            );
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spTipoFabricante.setAdapter(dataAdapter);
            spTipoFabricante.setOnItemSelectedListener(this);

            Log.i("FABRICANTE", "Fabricantes cargados: " + fabricantes.size());

        } catch (Exception e) {
            Log.e("FABRICANTE", "Error al cargar fabricantes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void filtrarSubcategoriaExhibicion(String categoria, String tipo, String fabricante) {
        List<String> operadores = handler.getSubcategoriaExhibicion(categoria, tipo, fabricante);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }

    public void onResume() {
        super.onResume();
        Log.i("ACTIVITY", "ON RESUME");
        new DeveloperOptions().modalDevOptions(getActivity());
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV,Constantes.NODATA);
        user=sharedPreferences.getString(Constantes.USER,Constantes.NODATA);
        user = sharedPreferences.getString(Constantes.USER, Constantes.NODATA);
        id_pdv = sharedPreferences.getString(Constantes.PHARMA_ID, Constantes.NODATA);
        canal =sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        subcanal =sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        vaFoto = sharedPreferences.getBoolean(Constantes.VAFOTO,false);
    }


    //************METODOS PARA TAKE-PHOTO Y UPLOAD
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {

            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri filePath = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                        scaleImage(bitmap);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case COD_FOTO:
                    MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });
                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    scaleImage(bitmap);
                    break;
            }
        }
    }

    //Permite hacer la imagen mas pequeña para mostrarla en el ImageView
    public void scaleImage(Bitmap bitmap) {
        try{

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            hora  = hour.format(currentLocalTime);


            int mheight = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, mheight, true);
            imageView.setImageBitmap(scaled);
            bitmapfinal = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        } catch (Exception e) {
            AlertDialog alertDialog1;
            alertDialog1 = new AlertDialog.Builder(getContext()).create();
            alertDialog1.setTitle("Message");
            alertDialog1.setMessage("Notificar \t "+e.toString());
            alertDialog1.show();
            Log.e("compressBitmap", "Error on compress file");
        }
    }

    private void cargarImagen() {
        final CharSequence[] opciones;
        if (vaFoto) {
            // Si vaFoto es true, excluir "Cargar Imagen"
            opciones = new CharSequence[]{"Tomar Foto", "Cancelar"};
        } else {
            // Si vaFoto es false, incluir todas las opciones
            opciones = new CharSequence[]{"Tomar Foto", "Cargar Imagen", "Cancelar"};
        }
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    //   tomarFotografia();
                    Intent n = new Intent(getContext(), CameraActivity.class);
                    n.putExtra("activity", "exh");
                    startActivity(n);


                }else{
                    if (opciones[i].equals("Cargar Imagen")) {
                        openGallery();
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,  "Seleccionar una imagen"), COD_SELECCIONA);
    }

    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if (isCreada==false) {
            isCreada=fileImagen.mkdirs();
        }

        if (isCreada==true) {
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);
    }

    //Metodo que sube la imagen al servidor
    public String getStringImage(Bitmap bmp) {
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Comprime la Imagen canal, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //String to bitmap
    public Bitmap StringToBitMap(String encodedString) {
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    //Tomar Foto

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==100) {
            if (grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                //botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")) {
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package", getContext().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
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

    public void mostrar_alert(String url,String tipoExh){

        int orientation =  getActivity().getResources().getConfiguration().orientation;

//        if(orientation == 1){
        //       getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }

        // Creamos el alertDialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Agregamos xml de tipo exh
        View dialogView = inflater.inflate(R.layout.alertdialog_tipo_exhibicion, null);
        // Agregamos xml perzonalizado del titulo de tipo exh
        View titleView = inflater.inflate(R.layout.alertdialog_title_tipo_exh, null);
        // Obtenemos el textView del AlertDialog para agregar el titulo de motivo
        TextView title = titleView.findViewById(R.id.titulo_tipo_exh);
        // Agregamos el tipo de exh en el textview
        title.setText(tipoExh);
        // title perzonalizado en el alertDialog
        builder.setCustomTitle(titleView);
        builder.setCancelable(false);


        // Obtenemos el webView del alertDialog
        WebView wv = (WebView) dialogView.findViewById(R.id.wvTipoExh);
        // Activamos los controles
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls( true );
        wv.getSettings().setDisplayZoomControls( false );
        //  wv.getSettings().setSupportZoom( true );
        //   wv.getSettings().setLoadWithOverviewMode( true );
        wv.getSettings().setUseWideViewPort( true );
        wv.setInitialScale(1);
        wv.setWebViewClient(new WebViewClient());


        if (VerificarNet.hayConexion(getContext())) {
            wv.loadUrl(url);
        } else {
            wv.loadUrl("file:///android_asset/html/pagina-error.html");
        }


        builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });


        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();



    }


    @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            // ✅ AGREGAR ESTO para manejar spTipoExhiNuevo
        if (adapterView == spTipoExhiNuevo) {
            try {
                String tipoExhibicionSeleccionada = adapterView.getItemAtPosition(i).toString();

                // ✅ CORRECCIÓN CRÍTICA: Actualizar la variable de clase
                if (!tipoExhibicionSeleccionada.equals("Seleccione")) {
                    tipoExh = tipoExhibicionSeleccionada;  // ← Esto es lo que falta
                    Log.i("TIPO_EXHI_SELECCIONADO", "Tipo guardado: " + tipoExh);
                } else {
                    tipoExh = "";  // Limpiar si selecciona "Seleccione"
                }

                filtrarFabricantes();

            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


            if (adapterView==spTipoFabricante) {
                try {
                    fabricante1 = adapterView.getItemAtPosition(i).toString();
                    filtrarMarcaExhibicion( subcategoria, tipo, fabricante1, canal, subcanal);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            /*
            if (adapterView== spSubcategoria) {
                try{
                    subcategoria = adapterView.getItemAtPosition(i).toString();
                    //  filtrarListView(categoria, subcategoria, tipo, fabricante, canal, subcanal);
                    filtrarMarcaExhibicion(categoria,subcategoria,tipo,fabricante,canal,subcanal);
                }catch (Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
    */
            if (adapterView== spMarca) {
                try{
                    marca = adapterView.getItemAtPosition(i).toString();
                    if (!marca.equals("Seleccione") && !spMarca.equals("Seleccione") ){
    //                    filtrarTipo();
                        filtrarTipoCategoria();
    //                    filtrarNiveles(tipoExh);
                    }
                }catch (Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

    //        if (adapterView== spNiveles) {
    //            try{
    //                nivel = spNiveles.getSelectedItem().toString();
    //
    //                if ((!nivel.equals("Seleccione")) && (!seleccionTipoExh.equals(-1))){
    //                }
    //
    //            }catch (Exception e) {
    //                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
    //                e.printStackTrace();
    //            }
    //        }

            if (adapterView== spTipo) {
                try{
                    tipo_exhibicion = adapterView.getItemAtPosition(i).toString();
                }catch (Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        }

    private void filtrarTipo() {

        String[] tipos = {"Seleccione", "GESTIONADA", "PAGADA", "CAMPAÑA"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tipos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(dataAdapter);
        spTipo.setOnItemSelectedListener(this);
    }
    public void filtrarTipoCategoria() {
        List<String> operadores = handler.getCategoriaTipo();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(dataAdapter);
        spTipo.setOnItemSelectedListener(this);
    }



    // Este filtro es dependiente de tipo de motivo escogida por el gestor
    private void filtrarNiveles(String tipoExh) {

        String [] siCtaNiveles ={"Seleccione","1","2","3","4","5","6"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, siCtaNiveles);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNiveles.setAdapter(dataAdapter);
        spNiveles.setOnItemSelectedListener(this);


        if((tipoExh.equalsIgnoreCase("CABECERA PAGADA") && tipo.equalsIgnoreCase("COMPETENCIA"))
                || (tipoExh.equalsIgnoreCase("CABECERA NO PAGADA") && tipo.equalsIgnoreCase("COMPETENCIA"))
                || (tipoExh.equalsIgnoreCase("EXHIBIDOR DE PISO") && tipo.equalsIgnoreCase("COMPETENCIA"))
                || (tipoExh.equalsIgnoreCase("ANEXO DE CAJA") && (canal.equalsIgnoreCase("AUTOSERVICIO")))
                || tipoExh.equalsIgnoreCase("BIOMBO")
                || tipoExh.equalsIgnoreCase("EXHIBIDOR AEREO")) {


            dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, siCtaNiveles);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNiveles.setAdapter(dataAdapter);
            spNiveles.setOnItemSelectedListener(this);

        }else{
            String [] noCtaNiveles ={"N/A"};
            dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, noCtaNiveles);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNiveles.setAdapter(dataAdapter);
            spNiveles.setOnItemSelectedListener(this);
        }



/*
        if((tipoExh.equalsIgnoreCase("CABECERA PAGADA") && tipo.equalsIgnoreCase("MARCA PROPIA"))
                || (tipoExh.equalsIgnoreCase("CABECERA NO PAGADA") && tipo.equalsIgnoreCase("MARCA PROPIA"))
                || (tipoExh.equalsIgnoreCase("EXHIBIDOR DE PISO") && tipo.equalsIgnoreCase("MARCA PROPIA"))){

            String [] noCtaNiveles ={"N/A"};
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, noCtaNiveles);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNiveles.setAdapter(dataAdapter);
        }
*/
    }



    private void filtrarMarcaExhibicion(String subcategoria, String tipo, String fabricante, String canal, String subcanal) {

        List<String> operadores = handler.filtrarMarcaExhibicion(subcategoria, tipo, fabricante,canal,subcanal);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void filtrarListView(String categoria, String subcategoria, String tipo, String fabricante, String canal, String subcanal) {
        listProductos = handler.filtrarListProductos3Exhibicion(categoria,subcategoria, tipo, fabricante, canal, subcanal);
        Log.i("List Original",listProductos.size()+"");
        dataAdapter = new CustomAdapterExhibicion(getContext(), listProductos);
        if (dataAdapter.getItemCount() != 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            listview.setLayoutManager(linearLayoutManager);
            listview.setHasFixedSize(true);
            CustomAdapterExhibicion customAdapter = new CustomAdapterExhibicion(this.getContext(),listProductos);
            listview.setAdapter(customAdapter);
        }else{
            empty.setVisibility(View.VISIBLE);
        }
    }

    public void insertData( String subcategoria, String Brand,
                           String TipoExh,String nivel,String tipo, String fabricantee, String comentario) {

        Log.i("INSERT_DATA", "TipoExh enviado: " + TipoExh);
        Log.i("INSERT_DATA", "tipoExh variable: " + tipoExh);
        if (id_pdv!=null) {
            try{

                // Marca de Agua
                String ciudad = "Ciudad: " + handler.getCityPdv(codigo_pdv);
                String local = "Local: " + punto_venta;
                String usuario = "Usuario: " + user;
                String fechaHora = "Fecha y hora: " + fecha + " " + hora;

                Bitmap temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ImageMark im = new ImageMark();
                Bitmap watermark = im.mark(temporal, ciudad, local, usuario, fechaHora, Color.YELLOW, 100, 85, false);
                int mheight = (int) (watermark.getHeight() * (1024.0 / watermark.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(watermark, 1024, mheight, true);

                image = getStringImage(scaled);


                //image = getStringImage(bitmapfinal);

                comentario = txtObservacion.getText().toString().trim();

                if (image != null && !image.equals("")) {



                 //   String plataforma = handler.getPlataformaByMarcaExh(categoria, Brand);
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                    Date currentLocalTime = cal.getTime();
                    DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                    date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                    String fechaser = date.format(currentLocalTime);

                    DateFormat hour = new SimpleDateFormat("HH:mm:ss");
                    hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                    String horaser = hour.format(currentLocalTime);
                    //Almacenar Datos
                    ContentValues values = new ContentValues();

                    values.put(ContractInsertExh.Columnas.PHARMA_ID,id_pdv);
                    values.put(ContractInsertExh.Columnas.CODIGO,codigo_pdv);
                    values.put(ContractInsertExh.Columnas.USUARIO,user);
                    values.put(ContractInsertExh.Columnas.SUPERVISOR,punto_venta);
                    values.put(ContractInsertExh.Columnas.FECHA,fechaser);
                    values.put(ContractInsertExh.Columnas.HORA,horaser);
                    values.put(ContractInsertExh.Columnas.SECTOR,"");
                  //  values.put(ContractInsertExh.Columnas.CATEGORIA,Categoria);
                    values.put(ContractInsertExh.Columnas.SUBCATEGORIA,"");
                    values.put(ContractInsertExh.Columnas.SEGMENTO,"");
                    values.put(ContractInsertExh.Columnas.BRAND,Brand);
                    values.put(ContractInsertExh.Columnas.TIPO_EXH,TipoExh);
                    values.put(ContractInsertExh.Columnas.FABRICANTE,fabricantee);
                    values.put(ContractInsertExh.Columnas.ZONA_EX,"");
                    values.put(ContractInsertExh.Columnas.NIVEL,nivel);
                    values.put(ContractInsertExh.Columnas.TIPO,tipo);
                    values.put(ContractInsertExh.Columnas.CONTRATADA,"");
                    values.put(ContractInsertExh.Columnas.CONDICION,"");
                    values.put(ContractInsertExh.Columnas.FOTO, image);
                   values.put(ContractInsertExh.Columnas.COMENTARIO_REVISOR, comentario);
                    values.put(ContractInsertExh.Columnas.POS_NAME, punto_venta);
                 //   values.put(ContractInsertExh.Columnas.PLATAFORMA, plataforma);
                    values.put(Constantes.PENDIENTE_INSERCION, 1);

                    getContext().getContentResolver().insert(ContractInsertExh.CONTENT_URI, values);

                    if (VerificarNet.hayConexion(getContext())) {
                        SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertExh, null);
                        Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Por favor tomar una foto para poder ser enviado.", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getContext(), Mensajes.ERROR,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if(dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width,height);
        }
    }

    public Dialog getDialog(){
        Dialog alert =  alertDialog;
        return alert;
    }

    private void recargarGridView(){

        // Recargamos el gridView
        gridView.setAdapter(null);
        gridViewAdapter = new GridViewAdapter(getContext(),new ArrayList<Base_tipo_exh>(exhArrayList));
        gridView.setAdapter(gridViewAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v == btnPropia) {
            tipo = "MARCA_PROPIA";
            btnPropia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_selected));
            btnPropia.setPadding(15, 15, 15, 15);
            btnCompetencia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_unselected));
            btnCompetencia.setPadding(15, 15, 15, 15);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Collections.singletonList("Seleccione"));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapter);
            spMarca.setAdapter(adapter);
            spNiveles.setAdapter(adapter);
            spTipo.setAdapter(adapter);

            //  filtrarCategoria(tipo, fabricante);
            recargarGridView();



        }
        if (v == btnCompetencia) {
            tipo = "COMPETENCIA";
            btnPropia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_unselected));
            btnPropia.setPadding(15, 15, 15, 15);
            btnCompetencia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_selected));
            btnCompetencia.setPadding(15, 15, 15, 15);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Collections.singletonList("Seleccione"));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapter);
            spMarca.setAdapter(adapter);
            spNiveles.setAdapter(adapter);
            spTipo.setAdapter(adapter);

            //   filtrarCategoria(tipo, fabricante);
            recargarGridView();
        }

        if (v == btnCamera) {
            cargarImagen();
        }

        if (v == btnGuardar){
            if (esFormularioValido()){
                insertData(subcategoria,marca,tipoExh,nivel,tipo_exhibicion, fabricante1, comentario);

                limpiarDatos();
            }
        }
    }

    private void limpiarDatos() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Collections.singletonList("Seleccione"));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spTipoExhiNuevo.setSelection(0);
        spCategoria.setSelection(0);
        spMarca.setAdapter(dataAdapter);
        spNiveles.setAdapter(dataAdapter);
        txtObservacion.setText("");
        spTipo.setAdapter(dataAdapter);
        imageView.setImageResource(0);
        seleccionTipoExh = -1;
        recargarGridView();
    }

    private boolean esFormularioValido() {

        // Si es -1 , no hay una posicion seleccionada
        if (spTipoExhiNuevo.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar un tipo de exhibicion", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spTipoFabricante.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar un fabricante", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (spMarca.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar una marca", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtObservacion.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "La observación no puede estar vacía", Toast.LENGTH_LONG).show();
            return false;
        }

//        if (spNiveles.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
//            Toast.makeText(getContext(), "Debe seleccionar un nivel/bandeja", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (spTipo.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar un tipo de vigencia", Toast.LENGTH_SHORT).show();
            return false;
        }


        // CORRECCIÓN: Cambiar && por || (OR)
        if (imageView == null || imageView.getDrawable() == null) {
            Toast.makeText(getContext(), "Por favor debe tomar una foto", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public class GridViewAdapter extends ArrayAdapter<Base_tipo_exh> {
        // Obtiene el ultimo checkbox seleccionado
        private CheckBox lastChecked = null;
        // Obtiene la posicion del ultimo checkbox seleccionado
        private int lastCheckedPos = 0;
        //Obtiene la posicion actual seleccionada
        private int selectedPosition = -1;




        public GridViewAdapter(@NonNull Context context, ArrayList<Base_tipo_exh> base_tipo_exh) {
            super(context, 0,base_tipo_exh);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            HolderView holderView;
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Collections.singletonList("Seleccione"));
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item_list,parent,false);
                holderView = new HolderView(convertView);
                convertView.setTag(holderView);

            }else{
                holderView = (HolderView) convertView.getTag();
            }



            Base_tipo_exh model = getItem(position);
            holderView.tipo_exh.setText(model.getExhibicion());
            holderView.tipo_exh.setChecked(model.isSelected);
            holderView.tipo_exh.setTag(new Integer(position));



            holderView.tipo_exh.setChecked(position == selectedPosition);
            View finalConvertView = convertView;
            holderView.tipo_exh.setOnClickListener(v -> {
                if (position == selectedPosition) {
                    holderView.tipo_exh.setChecked(false);
                    selectedPosition = -1;
                    seleccionTipoExh = selectedPosition;

                    spCategoria.setAdapter(dataAdapter);
                    spNiveles.setAdapter(dataAdapter);
                    spTipo.setAdapter(dataAdapter);
                    spTipoExhiNuevo.setAdapter(dataAdapter);
                }
                else {

                    // Obtenemos del item seleccionado la posicion y la motivo
                    selectedPosition = position;
                    seleccionTipoExh = position;
                    String exh = model.getExhibicion();
                    tipoExh = exh;


                    spNiveles.setAdapter(dataAdapter);
                    spTipo.setAdapter(dataAdapter);
                    spTipoExhiNuevo.setAdapter(dataAdapter);

                 //   filtrarCategoria(tipo,fabricante);


                    notifyDataSetChanged();

                }
            });


            // Cuando el gestor le de click al ojo
            holderView.btn_viewer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Obtenemos la url
                    String url = model.getFoto();
                    String tipoExh = model.getExhibicion();


                    mostrar_alert(url,tipoExh);

                    Dialog dialog = getDialog();

                    if(dialog != null){
                        int width = ViewGroup.LayoutParams.MATCH_PARENT;
                        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        dialog.getWindow().setLayout(width,height);
                    }
                }
            });


            return convertView;
        }

        private class HolderView{
            private final CheckBox tipo_exh;
            private final ImageButton btn_viewer;

            public HolderView(View view){
                tipo_exh = view.findViewById(R.id.cB_tipo_exh);
                btn_viewer = view.findViewById(R.id.btn_viewer);
            }
        }


    }



    public class CustomAdapterExhibicion extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private final String TAG = CustomAdapterExhibicion.class.getSimpleName();
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;
        private Context context;
        public ArrayList<BasePortafolioProductos> values;

        public CustomAdapterExhibicion(Context context, ArrayList<BasePortafolioProductos> values) {
            this.context = context;
            this.values = values;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                       if (viewType == TYPE_HEADER) {
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_exhibicion_title, parent, false);
                return new HeaderViewHolder(layoutView);
            } else if (viewType == TYPE_ITEM) {
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_exhibicion, parent, false);
                return new ItemViewHolder(layoutView);
            }
            throw new RuntimeException("No match for " + viewType + ".");
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            BasePortafolioProductos mObject = values.get(position);
            if (holder instanceof HeaderViewHolder) {
                //((HeaderViewHolder) holder).headerTitle.setText(mObject.getSku());
                ((HeaderViewHolder) holder).headerTitle.setText("MARCA");
                ((HeaderViewHolder) holder).headerTitle1.setText("TIPO EXH.");
                ((HeaderViewHolder) holder).headerTitle2.setText("ZONA");
                ((HeaderViewHolder) holder).headerTitle3.setText("CONTRATADA");
                ((HeaderViewHolder) holder).headerTitle4.setText("CONDICION");
                ((HeaderViewHolder) holder).headerTitle5.setText("FOTO");
                //((HeaderViewHolder) holder).headerTitle6.setText("VISTA");
            }else if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).txt_sku.setText(mObject.getSku());
                sesion = handler.getListGuardadoExh(codigo_pdv);
                for(int i = 0; i < sesion.size(); i++) {
                    Log.i("EXH",sesion.get(i).getTipo_exh() + " - " + sesion.get(i).getZona() + " - " + sesion.get(i).getContratada() + " - " + sesion.get(i).getCondicion());

                    if (mObject.getSku().equalsIgnoreCase(sesion.get(i).getMarca())) {
                        ((ItemViewHolder) holder).spTipoExh.setSelection(Integer.parseInt(sesion.get(i).getTipo_exh()));
                        final Handler handler = new Handler();
                        final int finalI = i;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((ItemViewHolder) holder).spZona.setSelection(Integer.parseInt(sesion.get(finalI).getZona()));
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ItemViewHolder) holder).spContratada.setSelection(Integer.parseInt(sesion.get(finalI).getContratada()));
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((ItemViewHolder) holder).spCondicion.setSelection(Integer.parseInt(sesion.get(finalI).getCondicion()));
                                                /*handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ((ItemViewHolder) holder).imageView.setImageBitmap(StringToBitMap(sesion.get(finalI).getFoto()));
                                                    }
                                                }, 1000);*/
                                            }
                                        }, 1000);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                }

            }
        }

        private BasePortafolioProductos getItem(int position) {
            return values.get(position);
        }
        @Override
        public int getItemCount() {
            return values.size();
        }
        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;
            return TYPE_ITEM;
        }
        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        public class HeaderViewHolder extends RecyclerView.ViewHolder{

            public TextView headerTitle, headerTitle1, headerTitle2, headerTitle3, headerTitle4, headerTitle5, headerTitle6;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                headerTitle = (TextView)itemView.findViewById(R.id.lblSku);
                headerTitle1 = (TextView)itemView.findViewById(R.id.lblTipoexh);
                headerTitle2 = (TextView)itemView.findViewById(R.id.lblZona);
                headerTitle3 = (TextView)itemView.findViewById(R.id.lblContratada);
                headerTitle4 = (TextView)itemView.findViewById(R.id.lblCondicion);
                headerTitle5 = (TextView)itemView.findViewById(R.id.lblFoto);
                //headerTitle6 = (TextView)itemView.findViewById(R.id.lvlVista);
            }
        }





        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView txt_sku;
            public Spinner spTipoExh;
            public Spinner spZona;
            public Spinner spContratada;
            public Spinner spCondicion;
            public ImageButton btnCamera;
            //public ImageView imageView;
            public CheckBox chkGuardar;

            public ItemViewHolder(View itemView) {
                super(itemView);
                txt_sku = (TextView)itemView.findViewById(R.id.lblSku);
                spTipoExh = (Spinner)itemView.findViewById(R.id.spTipoExh);
                spZona = (Spinner)itemView.findViewById(R.id.spZona);
                spContratada = (Spinner)itemView.findViewById(R.id.spContratada);
                spCondicion = (Spinner)itemView.findViewById(R.id.spCondicion);
                chkGuardar = (CheckBox)itemView.findViewById(R.id.checkGuardar);
                //imageView = (ImageView)itemView.findViewById(R.id.ivFotoExhibiciones);
                btnCamera = (ImageButton) itemView.findViewById(R.id.ibCargarFotoExhibiciones);

                btnCamera.setOnClickListener(this);
//                if (validaPermisos()) {
//                    btnCamera.setEnabled(true);
//                }else{
//                    btnCamera.setEnabled(false);
//                }

                final ArrayList<String> NA = new ArrayList<>();
                NA.add("N/A");

                spTipoExh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spTipoExh.getSelectedItem().toString().trim().equalsIgnoreCase("No posee exhibición")) {
//                            spZona.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, NA));
                            spContratada.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, NA));
                        }else{
                            spZona.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.comboZona))));
                            spContratada.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.combo_si_no))));
                            spCondicion.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.combo_condicion))));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

//                spZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        if (spZona.getSelectedItem().toString().trim().equalsIgnoreCase("N/A")) {
//                            spContratada.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, NA));
//                        }else{
//                            spContratada.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.combo_si_no))));
//                            spCondicion.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.combo_condicion))));
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {}
//                });

                spContratada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spContratada.getSelectedItem().toString().trim().equalsIgnoreCase("N/A")) {
                            spCondicion.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, NA));
                        }else{
                            spCondicion.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.combo_condicion))));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                chkGuardar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            final String marca = txt_sku.getText().toString();
                            final String tipo_exh = spTipoExh.getSelectedItem().toString().trim();
                            final String zona = spZona.getSelectedItem().toString().trim();
                            final String contratada = spContratada.getSelectedItem().toString().trim();
                            final String condicion = spCondicion.getSelectedItem().toString().trim();

                            if (!tipo_exh.equalsIgnoreCase("Seleccione") &&
//                                !zona.equalsIgnoreCase("Seleccione") &&
                                    !contratada.equalsIgnoreCase("Seleccione") &&
                                    !condicion.equalsIgnoreCase("Seleccione")) {

                                if (imageView != null && imageView.getDrawable() != null) {//ImageView no vacio
                                    image = getStringImage(bitmapfinal);
                                } else {
                                    Toast.makeText(getContext(),"No has tomado la foto",Toast.LENGTH_LONG).show();
                                    chkGuardar.setChecked(false);
                                }

                                if (image != null && !image.equals("")) {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                                    builder.setTitle("Confirmación");
                                    builder.setMessage("¿Desea guardar la información?");
                                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //insertData(skuSelected, codifica, codifica, canal, descripcion);
                                            //String Sector = spSector.getSelectedItem().toString();
                                            String Categoria = spCategoria.getSelectedItem().toString();
                                            String subcategoria = spSubcategoria.getSelectedItem().toString();
                                            //String segmento = spSegmento.getSelectedItem().toString();
                                            //    insertData(Categoria, subcategoria, marca, tipo_exh, zona, contratada, condicion);
                                            //limpiarRecycler();
                                        }
                                    });



                                    builder.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            chkGuardar.setChecked(false);
                                        }
                                    });

                                    android.app.AlertDialog ad = builder.create();
                                    ad.show();

                                    Button pButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
                                    //pButton.setTextColor(Color.rgb(79, 195, 247));
                                    Button cButton = ad.getButton(DialogInterface.BUTTON_NEUTRAL);
                                    //cButton.setTextColor(Color.rgb(79, 195, 247));
                                }else {
                                    Toast.makeText(getContext(), "Por favor tomar una foto para poder ser enviado.", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getContext(),"Debe seleccionar todos los campos",Toast.LENGTH_LONG).show();
                                chkGuardar.setChecked(false);
                            }

                        }
                    }
                });
            }

            public void limpiarRecycler() {
                spTipoExh.setSelection(0);
                spZona.setSelection(0);
                spContratada.setSelection(0);
                spCondicion.setSelection(0);
                imageView.setImageResource(android.R.color.transparent);
                chkGuardar.setChecked(false);
                image = "";
            }

            @Override
            public void onClick(View v) {
                if (v == btnCamera) {
                    cargarImagen();
                }
            }
        }
    }
}