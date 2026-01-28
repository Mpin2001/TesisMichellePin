package com.luckyecuador.app.bassaApp.ui.packs;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luckyecuador.app.bassaApp.CameraActivity;
import com.luckyecuador.app.bassaApp.Clase.Precio;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.MarshMallowPermission;
import com.luckyecuador.app.bassaApp.Conexion.Mensajes;
import com.luckyecuador.app.bassaApp.Conexion.VerificarNet;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPacks2;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.R;
import com.luckyecuador.app.bassaApp.Sync.SyncAdapter;
import com.luckyecuador.app.bassaApp.Utils.DeveloperOptions;
import com.luckyecuador.app.bassaApp.Utils.ImageMark;
import com.luckyecuador.app.bassaApp.Utils.RequestPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;


public class PacksFragment extends Fragment implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayList<Precio> type_name_copy = new ArrayList<Precio>();
    ArrayList<Precio> sesion = new ArrayList<Precio>();
    private Spinner spCategoria;
    private Spinner spSubcategoria;
    //    private Spinner spPresentacion;
    /*private Spinner spSubcategoria;
    private Spinner spSegmento;*/
    private Spinner spMarca;
    private Spinner spProducto;
    /*private Spinner spTamano;
    private Spinner spCantidad;*/
//    private EditText txtSKUCode;

    private Spinner spCategoriaSecundaria;
    private Spinner spSubcategoriaSecundaria;
    private Spinner spMarcaSecundaria;
    private Spinner spProductoSecundaria;
    private EditText txtSKUCodeSecundaria;
    private EditText txtPVC;


    private EditText txtDescripcion;
    private EditText txtCantidadArmada;
    private EditText txtCantidadEncontrada;
    private EditText txtDescripcionp;
    private EditText txtObservacion;

    public static ImageView imageView;
    public static ImageView imageViewGuia;
    private Button btnGuardar;

    //BASE SQLITE
    private String categoria, subcategoria, segmento1, segmento2, brand, tamano, cantidad, codigo, descripcion, categoriasec, subcategoriasec, brandsec, sku_codesec;
    private String id_pdv,user,codigo_pdv, punto_venta,fecha,hora, hora_packs, hora_guia;

    private boolean packs = false;
    private boolean guia = false;

    DatabaseHelper handler;

    EditText txtsearch;
    TextView txtfechav, lSeleccioneProducto;
    ImageButton btnFecha;
    TextView empty;
    ListView listview;

    public ArrayList<Precio> listProductos;
    List<String> listTarget;
    List<String> filterProducts;
    CustomAdapterPrecios dataAdapter;

    private EditText txtventas, txtpregular, txtppromocion, txtcuotas, txtvcuotas;
    private String fechaventas, producto, pregular, ppromocion, poferta, sku, cuotas, vcuotas, format, presentacion, canal, subcanal;
    private String ciudad,retail,sucursal;

    LinearLayout layout_skuName;
    LinearLayout layout_skuDescripcion;

    //Photo Camera
    Bitmap bitmap;
    Bitmap bitmappacks;
    Bitmap bitmapguia;
    private Bitmap bitmapfinal;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private final String CARPETA_RAIZ="bassaApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"OnPacks";
    String path;
    String image = "";

    private ImageButton btnCamera;
    private ImageButton btnCameraGuia;
    ViewGroup rootView;

    private final String fabricante = "Bassa";

    MarshMallowPermission marshMallowPermission;
    public PacksFragment() {
        // Required empty public constructor
    }

    public static PacksFragment newInstance(String param1, String param2) {
        PacksFragment fragment = new PacksFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_packs, container, false);

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

        LoadData();

        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();

        spCategoria = rootView.findViewById(R.id.spCategoria);
        spMarca = rootView.findViewById(R.id.spMarca);
        spProducto = rootView.findViewById(R.id.spProducto);

        spCategoriaSecundaria = rootView.findViewById(R.id.spCategoriaSecundaria);
        spMarcaSecundaria = rootView.findViewById(R.id.spMarcaSecundaria);
        spProductoSecundaria = rootView.findViewById(R.id.spProductoSecundario);
        txtDescripcionp = rootView.findViewById(R.id.txtDescripcionp);
        txtPVC = rootView.findViewById(R.id.txtPVC);

        txtCantidadArmada = rootView.findViewById(R.id.txtCantidad);
        txtCantidadEncontrada = rootView.findViewById(R.id.txtCantidadEncontrada);

        imageView = rootView.findViewById(R.id.ivFotoPacks);
        imageViewGuia = rootView.findViewById(R.id.ivFotoGuia);
        btnCamera = rootView.findViewById(R.id.ibCargarFotoPacks);
        btnCameraGuia = rootView.findViewById(R.id.ibCargarFotoGuia);
        btnGuardar = rootView.findViewById(R.id.btnGuardar);

//        listview = rootView.findViewById(R.id.lvSKUCode);
        btnCamera.setOnClickListener(this);
        btnCameraGuia.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);

//        View headerView = this.getLayoutInflater().inflate(R.layout.list_row_precios_title,null,false);
//        listview.addHeaderView(headerView,null,false);

        //lSeleccioneProducto.setText("Seleccione Producto: "+ format);

//        layout_skuName = rootView.findViewById(R.id.layout_skuName);
//        layout_skuDescripcion = rootView.findViewById(R.id.layout_skuDescripcion);
//
//        layout_skuName.setVisibility(View.INVISIBLE);
//        layout_skuDescripcion.setVisibility(View.INVISIBLE);

//        consultaGuardado();

        empty = rootView.findViewById(R.id.recyclerview_data_empty);

        filtrarCategoria(fabricante);
        filtrarCategoriaSec();

        ListView listView = rootView.findViewById(R.id.lvSKUCode);

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return rootView;
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV, Constantes.NODATA);
        user = sharedPreferences.getString(Constantes.USER, Constantes.NODATA);
        codigo = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO, Constantes.NODATA);
        subcanal = sharedPreferences.getString(Constantes.SUBCANAL, Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        Log.i("USER FOTO ACTIVITY:", user);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView == spCategoria) {
            try {
                categoria = adapterView.getItemAtPosition(i).toString();
                filtrarMarca(fabricante, categoria);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if (adapterView == spCategoriaSecundaria) {
            try {
                categoriasec = adapterView.getItemAtPosition(i).toString();
                filtrarMarcaSec(categoriasec);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        if (adapterView == spMarca) {
            try {
                brand = adapterView.getItemAtPosition(i).toString();
                filtrarProducto(fabricante, categoria, brand);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        if (adapterView == spMarcaSecundaria) {
            try {
                brandsec = adapterView.getItemAtPosition(i).toString();
                filtrarProductoSec(categoriasec, brandsec);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    public void consultaGuardado(){
//        sesion = handler.getListGuardadoPrecios(codigo_pdv);
//        for(int i = 0; i < sesion.size(); i++){
//            Log.i("INFO", sesion.get(i).getSku_code() + " " + sesion.get(i).getPvp() + " " + sesion.get(i).getPvc());
//        }
//    }

    public void filtrarCategoria(String fabricante){
        List<String> operadores = handler.getCategoriaPacks(fabricante);
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
    }

    public void filtrarMarca(String fabricante, String categoria){

        List<String> operadores = handler.getMarcaPacks2(fabricante,categoria);
        Log.i("GETMARCA", String.valueOf(operadores));
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void filtrarProducto(String fabricante, String categoria, String marca){
        List<String> operadores = handler.getProducto3Packs(fabricante, categoria,marca);
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducto.setAdapter(dataAdapter);
        spProducto.setOnItemSelectedListener(this);
    }

    /* PRODUCTO SECUNDARIO */

    public void filtrarCategoriaSec(){
        List<String> operadores = handler.getCategoriaPacksSec();
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoriaSecundaria.setAdapter(dataAdapter);
        spCategoriaSecundaria.setOnItemSelectedListener(this);
    }

    public void filtrarMarcaSec( String categoria){
        Log.i("CAT FILTRAR MARCA SECUNDARIA FRAGMENT", categoria);
        List<String> operadores = handler.getMarcaPacksSec2(categoria);
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarcaSecundaria.setAdapter(dataAdapter);
        spMarcaSecundaria.setOnItemSelectedListener(this);
    }

    public void filtrarProductoSec(String categoria, String marca){
        List<String> operadores = handler.getProducto3PacksSec(categoria,marca);
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProductoSecundaria.setAdapter(dataAdapter);
        spProductoSecundaria.setOnItemSelectedListener(this);
    }

    private void estadoElementos(boolean estado) {
        spCategoriaSecundaria.setEnabled(estado);
        spMarcaSecundaria.setEnabled(estado);
        spProductoSecundaria.setEnabled(estado);
        txtPVC.setEnabled(estado);
        txtCantidadArmada.setEnabled(estado);
        txtCantidadEncontrada.setEnabled(estado);
        txtDescripcionp.setEnabled(estado);
        btnCamera.setEnabled(estado);
        btnGuardar.setEnabled(estado);
        if (!estado) {
            spCategoriaSecundaria.setSelection(0);
            spMarcaSecundaria.setSelection(0);
            spProductoSecundaria.setSelection(0);
            txtPVC.setText("");
            txtCantidadArmada.setText("");
            txtCantidadEncontrada.setText("");
            txtDescripcionp.setText("");
        }
    }

    public void onClick(View v) {
        if (v == btnCamera) {
            this.packs = true;
            this.guia = false;
            String tipo = "packs";
            cargarImagen(tipo);
        }
        if (v == btnCameraGuia){
            this.packs = false;
            this.guia = true;
            String tipo = "guia";
            cargarImagen(tipo);
        }
        if (v == btnGuardar) {
            if (esFormularioValido())
                insertData();
        }
    }

    private boolean esFormularioValido() {
        if (spCategoria.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe seleccionar una categoría del producto principal", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spMarca.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe seleccionar una marca del producto principal", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spProducto.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe seleccionar un producto principal", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spCategoriaSecundaria.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe seleccionar una categoría del producto secundario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spMarcaSecundaria.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe seleccionar una marca del producto secundario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spProductoSecundaria.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe seleccionar un producto secundario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtDescripcionp.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar una descripción", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtPVC.getText().toString().trim().isEmpty() &&
                !spProductoSecundaria.getSelectedItem().toString().equalsIgnoreCase("Otros")) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar el P.V.C actual", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtCantidadArmada.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar la cantidad armada", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtCantidadEncontrada.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar la cantidad encontrada", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

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

    public void insertData(){//String skuSelected,String pregular, String ppromocion, String poferta){
        String foto_packs = null;
        String foto_guia = null;
        try{
            if (imageView != null && imageView.getDrawable() != null) { //ImageView no vacio
                obtenerFecha();

                // Marca de Agua
                String ciudad = "Ciudad: " + handler.getCityPdv(codigo_pdv);
                String local = "Local: " + punto_venta;
                String usuario = "Usuario: " + user;
                String fechaHoraPacks = "Fecha y hora: " + fecha + " " + hora_packs;
                String fechaHoraGuia = "Fecha y hora: " + fecha + " " + hora_guia;

                ImageMark im = new ImageMark();

                Bitmap temporalPacks = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Bitmap watermarkAntes = im.mark(temporalPacks, ciudad, local, usuario, fechaHoraPacks, Color.YELLOW, 100, 85, false);
                int mheightAntes = (int) (watermarkAntes.getHeight() * (1024.0 / watermarkAntes.getWidth()) );
                Bitmap scaledPacks = Bitmap.createScaledBitmap(watermarkAntes, 1024, mheightAntes, true);
                foto_packs = getStringImage(scaledPacks);

                if(imageViewGuia != null && imageViewGuia.getDrawable() != null){

                    Bitmap temporalGuia = ((BitmapDrawable) imageViewGuia.getDrawable()).getBitmap();
                    Bitmap watermarkDespues = im.mark(temporalGuia, ciudad, local, usuario, fechaHoraGuia, Color.YELLOW, 100, 85, false);
                    int mheightDespues = (int) (watermarkDespues.getHeight() * (1024.0 / watermarkDespues.getWidth()) );
                    Bitmap scaledGuia = Bitmap.createScaledBitmap(watermarkDespues, 1024, mheightDespues, true);
                    foto_guia = getStringImage(scaledGuia);
                }

            }else{
                Toast.makeText(getContext(),"No has tomado la foto",Toast.LENGTH_LONG).show();
            }

            if (foto_packs != null && !foto_packs.equals("")) {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                String fechaser = date.format(currentLocalTime);

                DateFormat hour = new SimpleDateFormat("HH:mm:ss");
                hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                String horaser = hour.format(currentLocalTime);

                ContentValues values = new ContentValues();

                if(foto_guia == null){
                    foto_guia = "NA";
                }

                String categoria = spCategoria.getSelectedItem().toString();
                //              String subcategoria = spSubcategoria.getSelectedItem().toString();
                String brand = spMarca.getSelectedItem().toString();

                String categoriasec = spCategoriaSecundaria.getSelectedItem().toString();
                String brandsec = spMarcaSecundaria.getSelectedItem().toString();
                String sku_codesec = spProductoSecundaria.getSelectedItem().toString();
                String pvc = txtPVC.getText().toString();

                String skuSelected = spProducto.getSelectedItem().toString();
                String cantidadArmada = txtCantidadArmada.getText().toString();
                String cantidadEncontrada = txtCantidadEncontrada.getText().toString();
                String descripcionp = txtDescripcionp.getText().toString();

                String manufacturer = handler.getManufacturerPrecios3(categoria,brand,skuSelected);
                String presentacion = handler.getPresentacionPacks2(skuSelected);
                String presentacionsec = handler.getPresentacionPacksSec(sku_codesec);
                String subcategoria = handler.getSubcatPacks(skuSelected);
                String subcategoriasec = handler.getSubcatPacksSec(sku_codesec);
                String pos_name = handler.getPosNamePdv(codigo_pdv);

                values.put(ContractInsertPacks2.Columnas.PHARMA_ID, id_pdv);
                values.put(ContractInsertPacks2.Columnas.CODIGO, codigo_pdv);
                values.put(ContractInsertPacks2.Columnas.USUARIO, user);
                values.put(ContractInsertPacks2.Columnas.SUPERVISOR, punto_venta);
                values.put(ContractInsertPacks2.Columnas.FECHA, fechaser);
                values.put(ContractInsertPacks2.Columnas.HORA, horaser);
                values.put(ContractInsertPacks2.Columnas.CATEGORIA, categoria);
                values.put(ContractInsertPacks2.Columnas.SUBCATEGORIA, subcategoria);
                values.put(ContractInsertPacks2.Columnas.PRESENTACION, presentacion);
                values.put(ContractInsertPacks2.Columnas.BRAND, brand);
                values.put(ContractInsertPacks2.Columnas.SKU_CODE, skuSelected);
                values.put(ContractInsertPacks2.Columnas.CATEGORIASEC, categoriasec);
                values.put(ContractInsertPacks2.Columnas.SUBCATEGORIASEC, subcategoriasec);
                values.put(ContractInsertPacks2.Columnas.PRESENTACIONSEC, presentacionsec);
                values.put(ContractInsertPacks2.Columnas.BRANDSEC, brandsec);
                values.put(ContractInsertPacks2.Columnas.SKU_CODESEC, sku_codesec);
                values.put(ContractInsertPacks2.Columnas.PVC, pvc);
                values.put(ContractInsertPacks2.Columnas.CANTIDAD_ARMADA, cantidadArmada);
                values.put(ContractInsertPacks2.Columnas.CANTIDAD_ENCONTRADA, cantidadEncontrada);
                values.put(ContractInsertPacks2.Columnas.OBSERVACION, descripcionp);
                values.put(ContractInsertPacks2.Columnas.FOTO, foto_packs);
                values.put(ContractInsertPacks2.Columnas.FOTO_GUIA, foto_guia);
                values.put(ContractInsertPacks2.Columnas.MANUFACTURER, manufacturer);
                values.put(ContractInsertPacks2.Columnas.POS_NAME, pos_name);
                values.put(Constantes.PENDIENTE_INSERCION, 1);

                getActivity().getContentResolver().insert(ContractInsertPacks2.CONTENT_URI, values);

                if (VerificarNet.hayConexion(getActivity().getApplicationContext())) {
                    SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertPacks, null);
                    Toast.makeText(getActivity().getApplicationContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                }
                vaciarCampos();
            }else {
                Toast.makeText(getActivity().getApplicationContext(), "Por favor tomar una foto para poder ser enviado.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void scaleImage(Bitmap bitmap){
        try{

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            int mheight = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, mheight, true);

            if (packs) {
                imageView.setImageBitmap(scaled);
                hora_packs = horaser;
                bitmappacks = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            } else if (guia) {
                imageViewGuia.setImageBitmap(scaled);
                hora_packs = horaser;
                bitmapguia = ((BitmapDrawable) imageViewGuia.getDrawable()).getBitmap();
            }

        } catch (Exception e) {
            AlertDialog alertDialog1;
            alertDialog1 = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
            alertDialog1.setTitle("Message");
            alertDialog1.setMessage("Notificar \t "+ e);
            alertDialog1.show();
            Log.e("compressBitmap", "Error on compress file");
        }
    }

    private void cargarImagen(String tipo) {
        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                    Date currentLocalTime = cal.getTime();
                    DateFormat hour = new SimpleDateFormat("HH:mm:ss");

                    if(tipo.equalsIgnoreCase("packs")) {
                        hora_packs = hour.format(currentLocalTime);
                    }else if(tipo.equalsIgnoreCase("guia")){
                        hora_guia = hour.format(currentLocalTime);
                    }

                    Intent n = new Intent(getContext(), CameraActivity.class);
                    n.putExtra("activity", "packs");
                    n.putExtra("tipo",""+tipo);
                    startActivity(n);
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        /* Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);*/
                        openGallery();
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), COD_SELECCIONA);
    }

    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getActivity().getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        //
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("REQUEST_CODE", requestCode + "");
        Log.i("RESULT_CODE", resultCode + "");
        if (resultCode==RESULT_OK) {

            switch (requestCode) {
                case COD_SELECCIONA:
                    Log.i("ENTRA", "GALERIA");
                   /* Uri miPath=data.getData();
                    imageView.setImageURI(miPath);*/
                    Uri filePath = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                        //Setear el ImageView con el Bitmap
                        scaleImage(bitmap);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case COD_FOTO:
                    Log.i("ENTRA", "CAMARA");
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


    public String getStringImage(Bitmap bmp){
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Comprime la Imagen tipo, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void vaciarCampos(){
        //txtCodigo.setText("");
        spCategoria.setSelection(0);
        spCategoriaSecundaria.setSelection(0);
        txtPVC.setText("");
        txtCantidadArmada.setText("");
        txtCantidadEncontrada.setText("");
        txtDescripcionp.setText("");
        imageView.setImageResource(0);
        imageViewGuia.setImageResource(0);
    }

    public class CustomAdapterPrecios extends ArrayAdapter<Precio> implements Filterable {

        public ArrayList<Precio> values;
        public Context context;
        boolean[] checkBoxState;

        public CustomAdapterPrecios(Context context, ArrayList<Precio> values) {
            super(context, 0, values);
            this.values = values;
            checkBoxState = new boolean[values.size()];
        }

        public class ViewHolder{
            TextView lblSku;
            CheckBox checkGuardar; //agregado GT
            //EditText txtunidad;
            EditText txt_precio_regular;
            EditText txt_precio_promocion;
            EditText txt_precio_oferta;
            // Spinner spMotivo;
        }



        @Override
        public int getViewTypeCount() {
            return values.size();            // TODO Auto-generated method stub

        }

        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        /*@NonNull
        @Override
        public Filter getFilter(){
            return precioFilter;
        }

        private Filter precioFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Precio> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    suggestions.addAll(values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Precio item : values){
                        if (item.getSku_code().toLowerCase().contains(filterPattern)){
                            suggestions.add(item);
                        }
                    }
                }
                results.values = suggestions;
                results.fabricante = suggestions.marca();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue){
                return ((Precio) resultValue).getSku_code();
            }
        };*/

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Obtener Instancia Inflater
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            CustomAdapterPrecios.ViewHolder vHolder = null;
            //Comprobar si el View existe
            //Si no existe inflarlo
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.list_row_precios, parent, false); // Modificacion (list_row_option) GT
                //Obtener instancias de los elementos
                vHolder = new CustomAdapterPrecios.ViewHolder();
                vHolder.lblSku = convertView.findViewById(R.id.lblSku);
                vHolder.checkGuardar = convertView.findViewById(R.id.checkGuardar);
                vHolder.txt_precio_regular = convertView.findViewById(R.id.txt_precio_regular);
                vHolder.txt_precio_promocion = convertView.findViewById(R.id.txt_precio_promocion);
                //   vHolder.txt_precio_oferta = convertView.findViewById(R.id.txt_precio_oferta);

                InputFilter filter = new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; ++i){
                            if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890.]*").matcher(String.valueOf(source.charAt(i))).matches()){
                                return "";
                            }
                        }
                        return null;
                    }
                };

                vHolder.txt_precio_regular.setFilters(new InputFilter[]{new InputFilter() {
                    final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        int indexPoint = dest.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
                        if (indexPoint == -1)
                            return source;

                        int decimals = dend - (indexPoint+1);
                        return decimals < 2 ? source : "";
                    }
                }
                });

                vHolder.txt_precio_promocion.setFilters(new InputFilter[]{new InputFilter() {
                    final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        int indexPoint = dest.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
                        if (indexPoint == -1)
                            return source;

                        int decimals = dend - (indexPoint+1);
                        return decimals < 2 ? source : "";
                    }
                }
                });

                vHolder.txt_precio_oferta.setFilters(new InputFilter[]{new InputFilter() {
                    final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        int indexPoint = dest.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
                        if (indexPoint == -1)
                            return source;

                        int decimals = dend - (indexPoint+1);
                        return decimals < 2 ? source : "";
                    }
                }
                });

                vHolder.txt_precio_regular.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(5)});
                vHolder.txt_precio_promocion.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(5)});
                vHolder.txt_precio_oferta.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(5)});

                convertView.setTag(vHolder);
            } else {
                vHolder = (CustomAdapterPrecios.ViewHolder) convertView.getTag();
            }

            if (values.size() > 0) {
                vHolder.lblSku.setText(values.get(position).getSku_code());

                sesion = handler.getListGuardadoPrecios(codigo_pdv);
                for(int i = 0; i < sesion.size(); i++) {
                    if (values.get(position).getSku_code().equals(sesion.get(i).getSku_code())) {
                        vHolder.txt_precio_regular.setText(sesion.get(i).getPvp());
                        vHolder.txt_precio_promocion.setText(sesion.get(i).getPvc());
                    }else{
                        Log.i("NO ENTRA","NO ENTRA");
                    }
                }

                final CustomAdapterPrecios.ViewHolder finalv = vHolder;

                vHolder.checkGuardar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String pregular = finalv.txt_precio_regular.getText().toString().trim();
                        String ppromocion = finalv.txt_precio_promocion.getText().toString();
                        String poferta = finalv.txt_precio_oferta.getText().toString();
                        String sku = finalv.lblSku.getText().toString();
                        if(((CheckBox)v).isChecked()) {
                            if (!pregular.equals("") && pregular != null && !poferta.equals("")) {
                                //insertData(sku, pregular, ppromocion, poferta);
                            } else {
                                Toast.makeText(getContext().getApplicationContext(), "No ingresaste el precio regular o promocion", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
            //Devolver al ListView la fila creada
            return convertView;
        }

    }
}






