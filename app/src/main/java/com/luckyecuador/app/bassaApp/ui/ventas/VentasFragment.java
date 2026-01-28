package com.luckyecuador.app.bassaApp.ui.ventas;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.luckyecuador.app.bassaApp.CameraActivity;
import com.luckyecuador.app.bassaApp.Clase.Base_campos_x_modulos;
import com.luckyecuador.app.bassaApp.Clase.Precio;
import com.luckyecuador.app.bassaApp.Clase.Ventas;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.MarshMallowPermission;
import com.luckyecuador.app.bassaApp.Conexion.Mensajes;
import com.luckyecuador.app.bassaApp.Conexion.VerificarNet;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPrecios;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVenta;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.R;
import com.luckyecuador.app.bassaApp.Sync.SyncAdapter;
import com.luckyecuador.app.bassaApp.Utilidades.DecimalDigitsInputFilter;
import com.luckyecuador.app.bassaApp.Utils.GuardarLog;

import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class VentasFragment extends Fragment implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener,  View.OnClickListener ,AdapterView.OnItemClickListener {

    private static final org.apache.commons.logging.Log log = LogFactory.getLog(VentasFragment.class);
    private boolean factura = false;
    private boolean adicional = false;
    private boolean actividad = false;

    AlertDialog alert_detalle_factura;
    ArrayList<Ventas> type_name_copy = new ArrayList<Ventas>();
    ArrayList<Ventas> sesion = new ArrayList<Ventas>();
    private Spinner spMarca;
   // private Spinner spCategoria;  //mpin
   // private Spinner spSubcategoria;
    private Spinner spPresentacion;
    /*private Spinner spSubcategoria;
    private Spinner spSegmento;*/
    private Spinner spProducto;
    /*private Spinner spTamano;
    private Spinner spCantidad;*/
    private EditText txtSKUCode;
    private EditText txtDescripcion;
    private Button btnAgregarProducto;
    private Button btnSiguiente;

    //Photo Camera
//    public static ImageView imageViewFactura;
//
//    public static ImageView imageViewAdicional;
//
//    public static ImageView imageViewActividad;
    // De:
// private ImageView imageViewFactura, imageViewAdicional, imageViewActividad;

    // A:
    public static ImageButton imageViewFactura, imageViewAdicional, imageViewActividad;
    public static ImageView ivVentas; // ya no se usara
    public static ImageButton ivFotoFactura;  ///nuevo
    public static ImageButton ivFotoAdicional;  //nuevo
    public static ImageButton ivFotoActividad;  //nuevo


    //BASE SQLITE
    private String marca,categoria, subcategoria, segmento1, segmento2, brand, tamano, cantidad, codigo, descripcion;
    private String id_pdv,user,codigo_pdv, punto_venta,fecha,hora, canal,cadena, subcanal, image, image2, image3, comentario_factura, comentario_adicional, comentario_actividad;
    private boolean vaFoto;
    DatabaseHelper handler;

    EditText txtsearch;
    EditText txtNomImp;
    TextView txtfechav, lSeleccioneProducto;
    ImageButton btnFecha;
    TextView empty;
    ListView listview;

    public ArrayList<Ventas> listProductos;
    List<String> listTarget;
    List<String> filterProducts;
    CustomAdapterVentas dataAdapter;
    ArrayList<Ventas> operadores = new ArrayList<>();

    private EditText txtventas, txtpregular, txtppromocion, txtcuotas, txtvcuotas,txtFechaVenta;
    private String fechaventas, producto,nombImp,tipoVenta, pregular, ppromocion,vstock_inicial,vstock_final, foto_adicional, foto_actividad, poferta, sku, cuotas, vcuotas, format, presentacion,cuenta;
    private String ciudad,retail,sucursal,customer_owner;

    private String tipo = "MARCA_PROPIA";
    private final String fabricante = "BASSA";
    private String tipo_foto = "";

    LinearLayout layout_skuName;
    LinearLayout layout_skuDescripcion;
    LinearLayout linearLayoutImpulso,llFechaVenta,llProducto;

    //Photo Camera
    private ImageView imageView;
    private Bitmap bitmap;
    private Bitmap bitmapfinalFactura,bitmapfinalAdicional, bitmapfinalActividad, bitmapfinal;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;
    double total_factura = 0.0;

    private final String CARPETA_RAIZ="SonyApp/"; //todo cambiar io preguntar
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Ventas";
    String path;
    //Button btnCamera;
    DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    ArrayList<Ventas> productosElements;
    String modulo = "VENTAS";


    MarshMallowPermission marshMallowPermission;

    private void mostrarVistaPrevia(Drawable drawable) {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getContext());
        alertadd.setTitle("Vista Previa");
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.vista_previa, null);
        ImageView dialog_imageview = view.findViewById(R.id.dialog_imageview);
        dialog_imageview.setImageDrawable(drawable);
        alertadd.setView(view);
        alertadd.setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dlg, int sumthin) {
                // Cerrar el diálogo
            }
        });
        alertadd.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ventas, container, false);

        // Evita que el teclado no cubra los sku
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

        LoadData();

        //  fabricante = handler.getFabricante(codigo_pdv);


        //  fabricante = "PRONACA";

        new GuardarLog(getContext()).saveLog(user, codigo_pdv, "Ingreso a Ventas");

        marshMallowPermission = new MarshMallowPermission(getActivity());

        //startService(new Intent(getContext(), MyService.class));

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

        txtNomImp = (EditText) rootView.findViewById(R.id.txtNombreImp);
        txtFechaVenta = (EditText) rootView.findViewById(R.id.txtFecha);
        btnFecha = (ImageButton) rootView.findViewById(R.id.btnFecha);
        //lSeleccioneProducto = (TextView)findViewById(R.id.lSeleccioneProducto);
        spMarca = (Spinner) rootView.findViewById(R.id.spMarca);
       // spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);
        //spSubcategoria = (Spinner) rootView.findViewById(R.id.spSubcategoria);
        spProducto  = (Spinner) rootView.findViewById(R.id.spProducto);
        btnAgregarProducto = (Button) rootView.findViewById(R.id.btnNuevoProducto);
        btnSiguiente = (Button) rootView.findViewById(R.id.btnSiguiente);
        /*spSubcategoria = (Spinner)findViewById(R.id.spSubcategoria);
        spSegmento = (Spinner)findViewById(R.id.spSegmento);*/
        //  spMarca = (Spinner)findViewById(R.id.spMarca);
        //  spPresentacion = (Spinner)findViewById(R.id.spPresentacion);
        /*spTamano = (Spinner)findViewById(R.id.spTamaño);
        spCantidad = (Spinner)findViewById(R.id.spCantidad);*/
        txtSKUCode = (EditText) rootView.findViewById(R.id.txtSKUCode);
        txtDescripcion  = (EditText) rootView.findViewById(R.id.txtDescripcionSKU);
        linearLayoutImpulso = (LinearLayout) rootView.findViewById(R.id.linearLayoutImpulso);
        llFechaVenta = (LinearLayout) rootView.findViewById(R.id.llFechaVenta);
        llProducto = (LinearLayout) rootView.findViewById(R.id.llProducto);







        if (!cuenta.equals("BASSA")){

            linearLayoutImpulso.setVisibility(View.GONE);
          //  spCategoria.setEnabled(true);
           // spSubcategoria.setEnabled(true);

        } else {
            llProducto.setVisibility(View.GONE);
         //   llFechaVenta.setVisibility(View.GONE);
            btnAgregarProducto.setVisibility(View.GONE);
            btnSiguiente.setVisibility(View.GONE);
        }


        listview = (ListView) rootView.findViewById(R.id.lvSKUCode);


        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finalv.txtCantidad.setText("PRUEBA");
                final Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog from_dateListener = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = dateFormat.parse(dayOfMonth + "/" + (month+1) + "/" + year);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String outDate = dateFormat.format(date);
                        txtFechaVenta.setText(outDate);
                    }
                },anio,mes,dia);



                    Date date = new Date();
                    from_dateListener.getDatePicker().setMaxDate(date.getTime());



                from_dateListener.show();
            }
        });


    /*    View headerView = (View) this.getLayoutInflater().inflate(R.layout.list_row_precios_title,null,false);
        listview.addHeaderView(headerView,null,false);*/

    /*    View headerView = (View) this.getLayoutInflater().inflate(R.layout.list_row_precios_title,null,false);
        listview.addHeaderView(headerView,null,false);*/

        //lSeleccioneProducto.setText("Seleccione Producto: "+ format);

        layout_skuName = (LinearLayout) rootView.findViewById(R.id.layout_skuName);
        layout_skuDescripcion = (LinearLayout) rootView.findViewById(R.id.layout_skuDescripcion);

        layout_skuName.setVisibility(View.INVISIBLE);
        layout_skuDescripcion.setVisibility(View.INVISIBLE);

        filtrarCodigoSKU();

        //  consultaGuardado();

        Log.i("LOG","cta: "+cuenta +" "+"fab: "+fabricante);


        empty = (TextView) rootView.findViewById(R.id.recyclerview_data_empty);


          //  filtrarCategoria();
        filtrarMarcasDirecto(); // mpin


        //  filtrarSubcategoria();



        listview = (ListView) rootView.findViewById(R.id.lvSKUCode);
        listview.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        btnAgregarProducto.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        dataAdapter = new CustomAdapterVentas(getContext(), operadores, listview);
        listview.setAdapter(dataAdapter);

        return rootView;
    }

//    private void setToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV,Constantes.NODATA);
        user=sharedPreferences.getString(Constantes.USER,Constantes.NODATA);

        user = sharedPreferences.getString(Constantes.USER, Constantes.NODATA);
        id_pdv = sharedPreferences.getString(Constantes.PHARMA_ID, Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        cadena = sharedPreferences.getString(Constantes.CADENA,Constantes.NODATA);
        customer_owner = sharedPreferences.getString(Constantes.CUSTOMER_OWNER,Constantes.NODATA);
        subcanal = sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
        vaFoto = sharedPreferences.getBoolean(Constantes.VAFOTO,false);

        cuenta = sharedPreferences.getString(Constantes.CUENTA,Constantes.NODATA);
//        fabricante = sharedPreferences.getString(Constantes.FABRICANTE,Constantes.NODATA);
    }



    public void filtrarCategoria() { //primero
        List<String> operadores = handler.getCategoriaVentas(fabricante,canal, subcanal);
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spCategoria.setAdapter(dataAdapter);
        //spCategoria.setOnItemSelectedListener(this);
    }

    public void filtrarSubcategoria(String categoria){ //segunda
        List<String> operadores = handler.getSubcategoriaVentas(fabricante,categoria,canal, subcanal);
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  spSubcategoria.setAdapter(dataAdapter);
        //spSubcategoria.setOnItemSelectedListener(this);
    }


    public void filtrarMarcasDirecto() {
        List<String> operadores = handler.getMarcasDirectasVentas(fabricante, canal, subcanal);
        if (operadores.size() == 2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void filtrarMarca(String categoria, String subcategoria) {
        List<String> operadores = handler.getMarcaVentas(fabricante,categoria, subcategoria,canal,subcanal);
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

    public void filtrarProducto(String marca, String fabricante, String tipo) {
        try {
            ArrayList<Ventas> productos = handler.filtrarProductosPorMarcaVentas(fabricante, marca, canal, subcanal);

            // Crear lista solo de SKUs para el spinner
            List<String> skuList = new ArrayList<>();
            skuList.add("Seleccione");

            for (Ventas producto : productos) {
                skuList.add(producto.getSku());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, skuList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProducto.setAdapter(dataAdapter);
            spProducto.setOnItemSelectedListener(this);

            // Guardar la lista de productos para usar después
            this.listProductos = productos;

        } catch (Exception e) {
            Log.e("FILTRAR_PRODUCTO", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void obtenerTipoVentas(Spinner spTipoVentaNuevo,String cuenta) {
        List<String> operadores = handler.getTipoVentas(cuenta);

        if (operadores.size()==2) {
            operadores.remove(0);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoVentaNuevo.setAdapter(dataAdapter);
        spTipoVentaNuevo.setOnItemSelectedListener(this);
    }

    //PRUEBAS
    public void filtrarCodigoSKU() {
        /*listview.getAdapter();

        txtSKUCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int fabricante, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int fabricante) {
                dataAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });*/

    }
    /*
        public void filtrarSubcategoria(){
            List<String> operadores = handler.getSubcategoriaPrecios2(canal, subcanal);
            if (operadores.size()==2){
                operadores.remove(0);
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, operadores);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSubcategoria.setAdapter(dataAdapter);
            spSubcategoria.setOnItemSelectedListener(this);
        }
    */
//    public void filtrarFabricante(String subcategoria){
//        List<String> operadores = handler.getFabricantePrecios(canal, subcanal, subcategoria);
//        if (operadores.size()==2){
//            operadores.remove(0);
//        }
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_spinner_item, operadores);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spMarca.setAdapter(dataAdapter);
//        spMarca.setOnItemSelectedListener(this);
//    }





    /*public void filtrarMarca(String subcategoria, String subcategoria){
        List<String> operadores = handler.getSegmento1(subcategoria,subcategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }

    public void filtrarSubcategoria(String subcategoria, String subcategoria, String subcategoria){
        List<String> operadores = handler.getSegmento2(subcategoria,subcategoria,subcategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSegmento.setAdapter(dataAdapter);
        spSegmento.setOnItemSelectedListener(this);
    }*/

   /* public void filtrarMarca(String categoria, String subcategoria){
        List<String> operadores = handler.getMarcaPrecios(canal, subcanal, categoria, subcategoria);
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

    public void filtrarPresentacion(String categoria, String subcategoria, String marca){
        List<String> operadores = handler.getPresentacionPrecios(canal, subcanal, categoria, subcategoria, marca);
        if (operadores.size()==2){
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPresentacion.setAdapter(dataAdapter);
        spPresentacion.setOnItemSelectedListener(this);
    }

    public void filtrartamano(String subcategoria, String subcategoria, String subcategoria, String segmento, String contenido){
        List<String> operadores = handler.getTamano(subcategoria,subcategoria,subcategoria,segmento,contenido);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTamano.setAdapter(dataAdapter);
        spTamano.setOnItemSelectedListener(this);
    }

    public void filtrarcantidad(String subcategoria, String subcategoria, String subcategoria, String segmento, String contenido, String tamano){
        List<String> operadores = handler.getCantidad(subcategoria,subcategoria,subcategoria,segmento,contenido, tamano);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCantidad.setAdapter(dataAdapter);
        spCantidad.setOnItemSelectedListener(this);
    }*/

  /*  public void consultaGuardado(){
        sesion = handler.getListGuardadoPrecios(codigo_pdv);
        for(int i = 0; i < sesion.size(); i++){
            Log.i("INFO", sesion.get(i).getSku_code()/* + " " + sesion.get(i).getPvp() + " " + sesion.get(i).getPvc();
   /*     }
    }*/



 /*   public void showListView(String categoria, String subcategoria, String brand){
        listProductos = handler.filtrarListProductos3Precios(canal, subcanal, categoria,subcategoria,brand);
        dataAdapter = new CustomAdapterPrecios(this, listProductos);
        if(!dataAdapter.isEmpty()){
            empty.setVisibility(View.INVISIBLE);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(dataAdapter);
            //listview.setOnItemClickListener(this);

            txtSKUCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    dataAdapter.getFilter().filter(txtSKUCode.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    ArrayList<Precio> type_name_filter = new ArrayList<Precio>();

                    String text = s.toString();

                    for (int i = 0; i < listProductos.size(); i++) {
                        if ((listProductos.get(i).getSku_code().toLowerCase()).contains(text.toLowerCase())) {
                            Precio p = new Precio();
                            p.setSku_code(listProductos.get(i).getSku_code());
                            p.setPvp(listProductos.get(i).getPvp().trim());
                            type_name_filter.add(p);
                        }
                    }

                    type_name_copy = type_name_filter;
                    listUpdate(type_name_copy);

                    /*PreciosActivity.this.dataAdapter.getFilter().filter(s);
                    dataAdapter.notifyDataSetChanged();*/

   /*             }
            });
        }else{
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }*/

//    public void showListView(String fabricante,String categoria,String subcategoria){
//        listProductos = handler.filtrarListProductosVentas(fabricante,categoria,subcategoria,marca,canal,subcanal);
//        dataAdapter = new CustomAdapterPrecios(getContext(), listProductos, listview);
//        if(!dataAdapter.isEmpty()){
//            empty.setVisibility(View.INVISIBLE);
//            listview.setVisibility(View.VISIBLE);
//            listview.setAdapter(dataAdapter);
//            //listview.setOnItemClickListener(this);
//
//            txtSKUCode.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    dataAdapter.getFilter().filter(txtSKUCode.getText().toString());
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    ArrayList<Ventas> type_name_filter = new ArrayList<Ventas>();
//
//                    String text = s.toString();
//
//                    for (int i = 0; i < listProductos.size(); i++) {
//                        if ((listProductos.get(i).getSku().toLowerCase()).contains(text.toLowerCase())) {
//                            Ventas v = new Ventas();
//                            v.setSku(listProductos.get(i).getSku());
//                            //      p.setPvp(listProductos.get(i).getPvp().trim());
//                            type_name_filter.add(v);
//                        }
//                    }
//
//                    type_name_copy = type_name_filter;
//                    listUpdate(type_name_copy);
//
//                    /*PreciosActivity.this.dataAdapter.getFilter().filter(s);
//                    dataAdapter.notifyDataSetChanged();*/
//                }
//            });
//        }else{
//            listview.setVisibility(View.GONE);
//            empty.setVisibility(View.VISIBLE);
//        }
//    }

    //show list nueva version
    public void showListView(String categoria, String subcategoria, String brand, String fabricante, String tipo) {
        listProductos = handler.filtrarListProductosVentas(fabricante,categoria,subcategoria,marca,canal,subcanal);
        Log.i("LIST PRODCUTOS", listProductos.toString());
        dataAdapter = new CustomAdapterVentas(getContext(), listProductos,listview);
        if (!dataAdapter.isEmpty()) {
            empty.setVisibility(View.INVISIBLE);
            listview.setVisibility(View.VISIBLE);
//            listview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, listProductos.size()*220));
            listview.setAdapter(dataAdapter);
            dataAdapter.notifyDataSetChanged();


            txtSKUCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    dataAdapter.getFilter().filter(txtSKUCode.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    ArrayList<Ventas> type_name_filter = new ArrayList<Ventas>();

                    String text = s.toString();

                    for (int i = 0; i < listProductos.size(); i++) {
                        if ((listProductos.get(i).getSku().toLowerCase()).contains(text.toLowerCase())) {
                            Ventas p = new Ventas();
                            p.setSku(listProductos.get(i).getSku());
                            p.setPvp(listProductos.get(i).getPvp());
                            p.setPvc(listProductos.get(i).getPvc());
                            type_name_filter.add(p);
                        }
                    }

                    type_name_copy = type_name_filter;
                    listUpdate(type_name_copy);

                    /*PreciosActivity.this.dataAdapter.getFilter().filter(s);
                    dataAdapter.notifyDataSetChanged();*/
                }
            });
        }else{
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    //fin show list
    public void listUpdate(ArrayList<Ventas> data) {
        if(!data.isEmpty()){
            listview.setVisibility(View.VISIBLE);
            empty.setVisibility(View.INVISIBLE);
            listview.setAdapter(new CustomAdapterVentas(getContext(), data,listview));
        }else{
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spMarca) {
            try {
                marca = adapterView.getItemAtPosition(i).toString();
                if (!marca.equals("Seleccione")) {
                    // Filtrar productos para el spinner basado en la marca seleccionada
                    filtrarProducto(marca, fabricante, tipo);
                } else {
                    // Limpiar el spinner de productos
                    limpiarSpinnerProductos();
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        // También manejar la selección del spinner de productos si es necesario
        if (adapterView == spProducto) {
            try {
                producto = adapterView.getItemAtPosition(i).toString();
                // Aquí puedes agregar lógica adicional cuando se selecciona un producto
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }





    // NUEVO METODO: Mostrar productos filtrados solo por marca
//    public void showProductosPorMarca(String marca, String fabricante, String tipo) {
//        listProductos = handler.filtrarProductosPorMarcaVentas(fabricante, marca, canal, subcanal);
//        Log.i("PRODUCTOS POR MARCA", listProductos.toString());
//
//        dataAdapter = new CustomAdapterVentas(getContext(), listProductos, listview);
//        if (!dataAdapter.isEmpty()) {
//            empty.setVisibility(View.INVISIBLE);
//            listview.setVisibility(View.VISIBLE);
//            listview.setAdapter(dataAdapter);
//            dataAdapter.notifyDataSetChanged();
//
//            // Mantener el filtro de búsqueda por SKU
//            configurarFiltroBusqueda();
//        } else {
//            listview.setVisibility(View.GONE);
//            empty.setVisibility(View.VISIBLE);
//        }
//    }

    private void limpiarSpinnerProductos() {
        List<String> emptyList = new ArrayList<>();
        emptyList.add("Seleccione");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, emptyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducto.setAdapter(dataAdapter);
    }

    // MeTODO para configurar el filtro de búsqueda (existente)
    private void configurarFiltroBusqueda() {
        txtSKUCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataAdapter.getFilter().filter(txtSKUCode.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Ventas> type_name_filter = new ArrayList<Ventas>();
                String text = s.toString();

                for (int i = 0; i < listProductos.size(); i++) {
                    if ((listProductos.get(i).getSku().toLowerCase()).contains(text.toLowerCase())) {
                        Ventas p = new Ventas();
                        p.setSku(listProductos.get(i).getSku());
                        p.setPvp(listProductos.get(i).getPvp());
                        p.setPvc(listProductos.get(i).getPvc());
                        type_name_filter.add(p);
                    }
                }

                type_name_copy = type_name_filter;
                listUpdate(type_name_copy);
            }
        });
    }

    // METODO para limpiar la lista
    private void limpiarListaProductos() {
        if (listProductos != null) {
            listProductos.clear();
        }
        if (dataAdapter != null) {
            dataAdapter.notifyDataSetChanged();
        }
        listview.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//        if(adapterView== spCategoria){
//            try{
//                categoria = adapterView.getItemAtPosition(i).toString();
//                filtrarSubcategoria(categoria);
//                //  showListView(fabricante,categoria);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//        if(adapterView== spSubcategoria){
//            try{
//                subcategoria =adapterView.getItemAtPosition(i).toString();
//                filtrarMarca(categoria, subcategoria);
////                if (cuenta.equalsIgnoreCase("FACUNDO")){
////                    showListView(categoria,subcategoria);
////                } else {
////                    filtrarProducto(marca,categoria,subcategoria);
////                }
//
//                // filtrarFabricante(subcategoria);
//                //filtrarMarca(subcategoria,subcategoria);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//        if(adapterView== spMarca){
//            try{
//                   marca = adapterView.getItemAtPosition(i).toString();
////                    fil(marca);
//                 showListView(categoria,subcategoria,marca,fabricante,tipo);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//
//
//
//
//
//
//        /*if(adapterView==spSubcategoria){
//            try{
//                subcategoria=adapterView.getItemAtPosition(i).toString();
//                filtrarSubcategoria(subcategoria,subcategoria,subcategoria);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//        if(adapterView==spSegmento){
//            try{
//                segmento=adapterView.getItemAtPosition(i).toString();
//                filtrarMarca(subcategoria,subcategoria,subcategoria,segmento);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }*/
//        /*
//        if(adapterView== spMarca){
//            try{
//                brand=adapterView.getItemAtPosition(i).toString();
//                //filtrartamano(subcategoria,subcategoria,subcategoria,segmento,contenido);
////                filtrarPresentacion(categoria,subcategoria,brand);
//                showListView(subcategoria,brand);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }*/
//
////        if(adapterView== spPresentacion){
////            try{
////                presentacion=adapterView.getItemAtPosition(i).toString();
////                //filtrartamano(subcategoria,subcategoria,subcategoria,segmento,contenido);
////                showListView(categoria,subcategoria,brand,presentacion);
////            }catch (Exception e){
////                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
////                e.printStackTrace();
////            }
////        }
//        /*if(adapterView==spTamano){
//            try{
//                tamano=adapterView.getItemAtPosition(i).toString();
//                filtrarcantidad(subcategoria,subcategoria,subcategoria,segmento,contenido,tamano);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//        if(adapterView==spCantidad){
//            try{
//                cantidad=adapterView.getItemAtPosition(i).toString();
//                showListView(subcategoria,subcategoria,subcategoria,segmento,contenido,tamano,cantidad);
//                // showListDescripcion(channel,subcategoria,subcategoria,segmento,contenido,tamano,cantidad);
//            }catch (Exception e){
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }*/
//    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //if(!fechaventas.equals("") && fechaventas!=null){
        String model = adapterView.getItemAtPosition(i).toString();
        alertDialog(model);
        //}else{
        //   Toast.makeText(getContext(),Mensajes.FECHA,Toast.LENGTH_SHORT).show();
        // }
    }

    public void alertDialog(final String skuSelected){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_precios, null);
        //Title
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setTitle("Ventas");
        builder.setView(LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_precios,null));

        //Traer Views
        TextView lblModelo=(TextView)dialogView.findViewById(R.id.lblModelo);
        //txtventas = (EditText)dialogView.findViewById(R.id.txtVentas);
        txtpregular = (EditText)dialogView.findViewById(R.id.txtPcont);
        txtppromocion = (EditText) dialogView.findViewById(R.id.txtPcredito);
        //txtcuotas=(EditText) dialogView.findViewById(R.id.txtcuota);
        //txtvcuotas=(EditText) dialogView.findViewById(R.id.txtVCuota);




        /*if(validaPermisos()){
            btnCamera.setEnabled(true);
        }else{
            btnCamera.setEnabled(false);
        }*/
        lblModelo.setText(getString(R.string.model) +" :    "+skuSelected+"\n");

        builder.setPositiveButton(R.string.save,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        pregular = txtpregular.getText().toString().trim();
                        ppromocion = txtppromocion.getText().toString();
                        if(!pregular.equals("") && pregular !=null){
                            //insertData(skuSelected, pregular, ppromocion);
                        }else{
                            Toast.makeText(getContext(),"No ingresaste valor de ventas",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        builder.setNeutralButton(R.string.cancel,null);



        //builder.setNegativeButton(R.string.cancel,null);

        builder.setView(dialogView);
        AlertDialog ad = builder.create();
        ad.show();

        Button pButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(Color.WHITE);
        //pButton.setBackgroundColor(Color.rgb(79,195,247));
        pButton.setPadding(4,2,4,2);
        Button cButton = ad.getButton(DialogInterface.BUTTON_NEUTRAL);
        cButton.setBackgroundColor(Color.parseColor("#FD000D")); // Rojo
        cButton.setTextColor(Color.WHITE);
        //cButton.setBackgroundColor(Color.rgb(79,195,247));
        cButton.setPadding(4,2,4,2);
    }

    public void insertData(String skuSelected,String nombImp,String fechaventa,String tipoVenta ,String cantidad ,String pregular, String ppromocion, String poferta,String vstock_inicial,String vstock_final ){
        try{
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fechaser = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            ContentValues values = new ContentValues();

           // String categoria = spCategoria.getSelectedItem().toString();
            //String subcategoria = spSubcategoria.getSelectedItem().toString();
            String brand = spMarca.getSelectedItem().toString();
            pregular = pregular.equals("") ? "0" : pregular;
            Log.i("total",""+ vstock_final);



            //    String subcategoria = spSubcategoria.getSelectedItem().toString();
//            String brand = spMarca.getSelectedItem().toString();
//            String presentacion = spPresentacion.getSelectedItem().toString();
            /*String segment1 = handler.getSegment1Flooring(skuSelected);
            String segment2 = handler.getSegment2Flooring(skuSelected);
            String tamano = handler.getTamanoFlooring(skuSelected);
            String cantidad = handler.getCantidadFlooring(skuSelected);*/
//            String manufacturer = handler.getManufacturerPrecios2(subcategoria,brand,skuSelected);
            String presentacion = handler.getPresentacionPrecios(skuSelected);
            String pos_name = handler.getPosNamePdv(codigo_pdv);
         //   String cantidad = handler.getPosNamePdv(codigo_pdv);



            values.put(ContractInsertVenta.Columnas.PHARMA_ID, id_pdv);
            values.put(ContractInsertVenta.Columnas.CODIGO, codigo_pdv);
            values.put(ContractInsertVenta.Columnas.USUARIO, user);
            values.put(ContractInsertVenta.Columnas.SUPERVISOR, punto_venta);
            values.put(ContractInsertVenta.Columnas.NUM_FACTURA,cantidad);
            values.put(ContractInsertVenta.Columnas.MONTO_FACTURA,pregular);
            values.put(ContractInsertVenta.Columnas.TIPO_FACTURA,brand);
            values.put(ContractInsertVenta.Columnas.CATEGORIA,categoria);
            values.put(ContractInsertVenta.Columnas.SUBCATEGORIA,subcategoria);
            values.put(ContractInsertVenta.Columnas.SKU,skuSelected);
            values.put(ContractInsertVenta.Columnas.POS_NAME, pos_name);
            values.put(ContractInsertVenta.Columnas.TOTAL_STOCK, vstock_final);
            values.put(ContractInsertVenta.Columnas.FECHA_VENTA, fechaventa);
          //  values.put(ContractInsertVenta.Columnas.KEY_IMAGE, image);
          //  values.put(ContractInsertVenta.Columnas.FOTO_ADICIONAL, foto_adicional);
          //  values.put(ContractInsertVenta.Columnas.FOTO_ACTIVIDAD, foto_actividad);
            values.put(ContractInsertVenta.Columnas.FECHA, fechaser);
            values.put(ContractInsertVenta.Columnas.HORA, horaser);



//            values.put(ContractInsertPrecios.Columnas.NOMBRE_IMPULSADOR,nombImp);
//            values.put(ContractInsertPrecios.Columnas.FECHA_VENTA,"N/A");
//            values.put(ContractInsertPrecios.Columnas.FOTO, image);

//            values.put(ContractInsertPrecios.Columnas.PRESENTACION,presentacion);
//            values.put(ContractInsertPrecios.Columnas.POFERTA,poferta);

            /*values.put(ContractInsertPrecios.Columnas.TAMANO,tamano);
            values.put(ContractInsertPrecios.Columnas.CANTIDAD,cantidad);*/
//            values.put(ContractInsertPrecios.Columnas.TIPO_VENTA,tipoVenta);
//            values.put(ContractInsertPrecios.Columnas.STOCK_INICIAL,vstock_inicial);
//            values.put(ContractInsertPrecios.Columnas.PREGULAR, pregular);
//            values.put(ContractInsertPrecios.Columnas.PPROMOCION, ppromocion);
//            values.put(ContractInsertPrecios.Columnas.STOCK_FINAL,vstock_final);
//            values.put(ContractInsertPrecios.Columnas.MANUFACTURER, fabricante);

            values.put(Constantes.PENDIENTE_INSERCION, 1);

            getContext().getContentResolver().insert(ContractInsertVenta.CONTENT_URI, values);

            if (VerificarNet.hayConexion(getContext())) {
                SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertVenta, null);
                Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
            }

            /*if(handler.SKUDuplicadoPrecios(skuSelected)){
                handler.eliminarSKUDuplicadoPrecios(skuSelected);
            }

            ContentValues values_sesion = new ContentValues();
            values_sesion.put(ContractInsertPreciosSesion.Columnas.PHARMA_ID, id_pdv);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.CODIGO, codigo_pdv);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.USUARIO, user);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.SUPERVISOR, punto_venta);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.FECHA, fechaser);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.HORA, horaser);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.CANTIDAD_ASIGNADA, sector);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.CATEGORIA, categoria);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.SEGMENTO, segment1);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.POFERTA, segment2);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.BRAND, brand);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.TAMANO, tamano);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.CANTIDAD, cantidad);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.SKU_CODE, skuSelected);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.FECHA_PROD, pregular);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.CANTIDAD_PROD, ppromocion);
            values_sesion.put(ContractInsertPreciosSesion.Columnas.MANUFACTURER, manufacturer);
            values_sesion.put(Constantes.PENDIENTE_INSERCION, 1);
            getContentResolver().insert(ContractInsertPreciosSesion.CONTENT_URI, values_sesion);*/

        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View view) {
        if (view == btnAgregarProducto){
            String productoSeleccionado = spProducto.getSelectedItem().toString();
            if (validarAgregarProducto(productoSeleccionado)) {

                // Buscar el producto en la lista para obtener su PVP
                String pvp = "0.00";
                for (Ventas producto : listProductos) {
                    if (producto.getSku().equals(productoSeleccionado)) {
                        pvp = producto.getPvp();
                        break;
                    }
                }

                // Crear nuevo objeto Ventas con el PVP
                Ventas nuevoProducto = new Ventas(productoSeleccionado);
                nuevoProducto.setPvp(pvp); // Guardar el PVP
                nuevoProducto.setTipo_venta("Seleccione"); // Valor por defecto

                operadores.add(nuevoProducto);
                spProducto.setSelection(0);
                dataAdapter.notifyDataSetChanged();


                Log.i("arraylist",""+operadores.size());

                if (operadores.size() > 0) {
                    listview.setVisibility(View.VISIBLE);

                    listview.post(new Runnable() {
                        @Override
                        public void run() {
                            ViewGroup.LayoutParams layoutParams = listview.getLayoutParams();
                            layoutParams.height = calcularAlturaTotal(listview);
                            listview.setLayoutParams(layoutParams);
                        }
                    });
                } else {
                    listview.setVisibility(View.GONE);
                }
            }
        }

        if (view == btnSiguiente){
            if (esFormularioValido()){
                guardarProductos();
                alertDialogDetalleFactura();
               // Toast.makeText(getContext(),""+total_factura,Toast.LENGTH_LONG).show();
            }
        }
    }

    private void guardarProductos() {
        View view;
        TextView tv_producto;
        Spinner sp_tipo_venta;
        EditText txt_cantidad;
        EditText txt_precio_unitario;
        EditText txt_valor_total;

       // productosElements = new ArrayList<>();
        int listLength = listview.getChildCount();

        for (int i = 0; i < listLength ; i++){

            view = listview.getChildAt(i);
            tv_producto = (TextView) view.findViewById(R.id.lblSku);
            sp_tipo_venta = (Spinner) view.findViewById(R.id.spTipoVentaNuevo);
            txt_cantidad = (EditText) view.findViewById(R.id.txt_cantidad);
            txt_precio_unitario = (EditText) view.findViewById(R.id.txt_precio_regular);
            txt_valor_total = (EditText) view.findViewById(R.id.txt_stock_final);



            String producto = tv_producto.getText().toString();

            if (operadores.get(i).getSku() == producto){

                String tipo_venta = sp_tipo_venta.getSelectedItem().toString();
                String cantidad = txt_cantidad.getText().toString();
                String precio_unitario = txt_precio_unitario.getText().toString();
                String valor_total = txt_valor_total.getText().toString();

                operadores.get(i).setTipo_venta(tipo_venta);
                operadores.get(i).setCantidad(cantidad);
                operadores.get(i).setPrecio_unitario(precio_unitario);
                operadores.get(i).setValor_total(valor_total);

            }

        }


    }


    private void alertDialogDetalleFactura() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_detalle_factura, null);

        // CORRECIÓN: Usar dialogView directamente
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setView(dialogView);

        // Traer Views
        EditText txtTotalFactura = (EditText) dialogView.findViewById(R.id.txt_total_factura);
        EditText txtNumeroFactura = (EditText) dialogView.findViewById(R.id.txt_numero_factura);
        Spinner spEntregoPromocional = (Spinner) dialogView.findViewById(R.id.spEntregoPromocional);
        LinearLayout llPromoEntregado = (LinearLayout) dialogView.findViewById(R.id.linearLayoutPromoEntregado);
        LinearLayout llCantPromoEntregado = (LinearLayout) dialogView.findViewById(R.id.linearLayoutCantPromoEntregado);
        Spinner spPromocionalEntregado = (Spinner) dialogView.findViewById(R.id.spPromocionalEntregado);
        Spinner spMecanicaVenta = (Spinner) dialogView.findViewById(R.id.spMecanicaVenta);
        EditText txtCantPromoEntregado = (EditText) dialogView.findViewById(R.id.txt_cant_promo);
        LinearLayout llNumFactura = (LinearLayout) dialogView.findViewById(R.id.llNumFactura);
        LinearLayout llMecanica = (LinearLayout) dialogView.findViewById(R.id.llMecanica);

        // Botones de cámara
        ImageButton btnCameraFactura = (ImageButton) dialogView.findViewById(R.id.btnCameraFactura);
        ImageButton btnCameraAdicional = (ImageButton) dialogView.findViewById(R.id.btnCameraAdicional);
        ImageButton btnCameraActividad = (ImageButton) dialogView.findViewById(R.id.btnCameraActividad);

        // Botones de vista previa (estos son los que muestran la imagen y sirven para preview)
        ImageButton ivFotoFactura = (ImageButton) dialogView.findViewById(R.id.ivFotoFactura);
        ImageButton ivFotoAdicional = (ImageButton) dialogView.findViewById(R.id.ivFotoAdicional);
        ImageButton ivFotoActividad = (ImageButton) dialogView.findViewById(R.id.ivFotoActividad);

        //comentarios
        EditText txtComentarioFactura = (EditText) dialogView.findViewById(R.id.txtComentarioFactura);
        EditText txtComentarioAdicional = (EditText) dialogView.findViewById(R.id.txtComentarioAdicional);
        EditText txtComentarioAcitividad = (EditText) dialogView.findViewById(R.id.txtComentarioAcitividad);


        // INICIALIZAR las variables de clase con los ImageButtons del diálogo
        imageViewFactura = ivFotoFactura;
        imageViewAdicional = ivFotoAdicional;
        imageViewActividad = ivFotoActividad;

        txtTotalFactura.setText(df.format(total_factura));

        // LISTENERS PARA VISTA PREVIA - Usan los ImageButtons locales
        ivFotoFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ivFotoFactura.getDrawable() != null) {
                    mostrarVistaPrevia(ivFotoFactura.getDrawable());
                } else {
                    Toast.makeText(getContext(), "No hay imagen de factura para mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivFotoAdicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ivFotoAdicional.getDrawable() != null) {
                    mostrarVistaPrevia(ivFotoAdicional.getDrawable());
                } else {
                    Toast.makeText(getContext(), "No hay imagen adicional para mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivFotoActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ivFotoActividad.getDrawable() != null) {
                    mostrarVistaPrevia(ivFotoActividad.getDrawable());
                } else {
                    Toast.makeText(getContext(), "No hay imagen de actividad para mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // LISTENERS PARA CÁMARA (existentes)
        btnCameraFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                factura = true;
                adicional = false;
                actividad = false;
                tipo_foto = "ventas_factura";
                cargarImagen(tipo_foto);
            }
        });

        btnCameraActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                factura = false;
                adicional = false;
                actividad = true;
                tipo_foto = "ventas_actividad";
                cargarImagen(tipo_foto);
            }
        });

        btnCameraAdicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                factura = false;
                actividad = false;
                adicional = true;
                tipo_foto = "ventas_adicional";
                cargarImagen(tipo_foto);
            }
        });


        txtTotalFactura.setText(df.format(total_factura));


        /*
        ArrayList<Base_campos_x_modulos> campos = handler.getCamposPorModulos(modulo,cuenta);
        for (int i=0; i < campos.size(); i++) {

            if (campos.get(i).getCampo().equalsIgnoreCase("FOTO_FACTURA")){
                llFotoFactura.setVisibility(View.VISIBLE);
            }

            if (campos.get(i).getCampo().equalsIgnoreCase("FOTO_ADICIONAL")){
                llFotoAdicional.setVisibility(View.VISIBLE);
            }
        }*/



        spEntregoPromocional.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getSelectedItem().toString();

                if (value.equals("Si")){
                    llPromoEntregado.setVisibility(View.VISIBLE);
                    llCantPromoEntregado.setVisibility(View.VISIBLE);
                    llNumFactura.setVisibility(View.VISIBLE);
                    llMecanica.setVisibility(View.VISIBLE);
                    txtCantPromoEntregado.setText(""); // aqui esta el uno por defecto
                    txtNumeroFactura.setVisibility(View.VISIBLE);
                    spMecanicaVenta.setVisibility(View.VISIBLE);
                } else if(value.equals("No")){
                    llPromoEntregado.setVisibility(View.GONE);
                    llCantPromoEntregado.setVisibility(View.GONE);
                    txtCantPromoEntregado.setText("");
                    txtNumeroFactura.setVisibility(View.GONE);
                    spMecanicaVenta.setVisibility(View.GONE);
                    llNumFactura.setVisibility(View.GONE);
                    llMecanica.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        obtenerPromocionalVentas(spPromocionalEntregado);
        obtenerMecanicaVentas(spMecanicaVenta);



        btnCameraFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                factura = true;
                adicional = false;
                actividad = false;
                tipo_foto = "ventas_factura";
                cargarImagen(tipo_foto); //posible campi mpin

            //    Intent n = new Intent(getContext(), CameraActivity.class);
            //    n.putExtra("activity", "ventas_factura");
            //    startActivity(n);
            }
        });


        btnCameraActividad.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        factura = false;
                        adicional = false;
                        actividad = true;
                        tipo_foto = "ventas_actividad";
                        cargarImagen(tipo_foto);

                    }
                });

        btnCameraAdicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                factura = false;
                actividad = false;
                adicional = true;
                tipo_foto = "ventas_adicional";
              //  cargarImagen(tipo_foto);
                cargarImagen(tipo_foto);

            //    Intent n = new Intent(getContext(), CameraActivity.class);
            //    n.putExtra("activity", "ventas_adicional");
            //    startActivity(n);
            }
        });




        builder.setPositiveButton("Guardar",null); /// este boton del alertdialog
        builder.setNeutralButton(R.string.cancel,null);



        builder.setView(dialogView);
        alert_detalle_factura = builder.create();

        alert_detalle_factura.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button neutralButton =  ((AlertDialog)alert_detalle_factura).getButton(AlertDialog.BUTTON_NEUTRAL);
                if (neutralButton != null) {
                    // un drawable con fondo rojo y bordes redondeados
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setShape(GradientDrawable.RECTANGLE);
                    drawable.setColor(Color.RED); // Fondo rojo
                    drawable.setCornerRadius(20); // Bordes redondeados (en píxeles)

                    neutralButton.setBackground(drawable); // Asignar el drawable como fondo
                    neutralButton.setTextColor(Color.WHITE); // Texto blanco
                }


                Button button = ((AlertDialog)alert_detalle_factura).getButton(AlertDialog.BUTTON_POSITIVE);
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setCornerRadius(20); //
                shape.setColor(Color.parseColor("#000F8F")); // color de fondo azul
                // Asignar el fondo al botón
                button.setBackground(shape);
                button.setTextColor(Color.WHITE);



                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String total_factura = txtTotalFactura.getText().toString();
                        String numero_factura = txtNumeroFactura.getText().toString();
                        String entrego_promocional = spEntregoPromocional.getSelectedItem().toString();
                        String promocional = spPromocionalEntregado.getSelectedItem().toString();
                        String cant_promocional = txtCantPromoEntregado.getText().toString();
                        String mecanica = spMecanicaVenta.getSelectedItem().toString();
                        String comentarioFactura = txtComentarioFactura.getText().toString();
                        String comentarioAdicional = txtComentarioAdicional.getText().toString();
                        String comentarioAcitividad = txtComentarioAcitividad.getText().toString();




                        if(validarDetalleFormulario(total_factura,numero_factura,entrego_promocional,promocional,cant_promocional, mecanica, comentarioFactura, comentarioAdicional, comentarioAcitividad)){
                            insertData2(total_factura,numero_factura,entrego_promocional,promocional,cant_promocional, mecanica, comentarioFactura, comentarioAdicional, comentarioAcitividad);
                        }
                    }

                    private void insertData2(String totalFactura, String numeroFactura, String entregoPromocional, String promocional, String cantPromocional, String mecanica,String coment_factura, String coment_adicional, String coment_actividad ) {
                        try{
                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                            Date currentLocalTime = cal.getTime();
                            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                            String fechaser = date.format(currentLocalTime);

                            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
                            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                            String horaser = hour.format(currentLocalTime);

                            ContentValues values = new ContentValues();

                            String fechaVenta = txtFechaVenta.getText().toString();

                            for (int i=0; i < operadores.size(); i++) {
                                String sku = operadores.get(i).getSku();

                                String marca = handler.getMarcaVentas(sku);
                                String categoria = handler.getCategoriaVentas(sku);
                                String subcategoria = handler.getSubCategoriaVentas(sku);
                                String presentacion = handler.getPresentacionVentas(sku);


                                String tipo_venta = operadores.get(i).getTipo_venta();
                                String cantidad2 = operadores.get(i).getCantidad();
                                String pvp = operadores.get(i).getPvp();
                                String tipo_factura = operadores.get(i).getTipo_factura();  //mpin
                             //   String tipo_factura = operadores.get(i).getTipo_venta();
                              //  String cantidad = operadores.get(i).getCantidad();
                                String precio_unitario = operadores.get(i).getPrecio_unitario();
                                String valor_total = operadores.get(i).getValor_total();

                               // esto mand aa guardar con N/A cuando se elije No en entrego promocional
                                if (entregoPromocional.equalsIgnoreCase("No")){
                                    promocional = "N/A";
                                    cantPromocional = "N/A";
                                    numeroFactura = "N/A";
                                    mecanica = "N/A";
                                }



                                // VERIFICAR FOTO FACTURA
                                if (imageViewFactura.getDrawable() != null && esFotoTomada(imageViewFactura.getDrawable())) {
                                    try {
                                        Bitmap bitmapFactura = ((BitmapDrawable) imageViewFactura.getDrawable()).getBitmap();
                                        int mheight = (int) (bitmapFactura.getHeight() * (1024.0 / bitmapFactura.getWidth()));
                                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapFactura, 1024, mheight, true);
                                        image = getStringImage(scaled);
                                    } catch (ClassCastException e) {
                                        // Si es VectorDrawable (ícono por defecto), usar "NO_FOTO"
                                        image = "NO_FOTO";
                                        Log.d("VentasFragment", "Factura: Usando ícono por defecto, NO_FOTO");
                                    }
                                } else {
                                    image = "NO_FOTO";
                                }

                                // VERIFICAR FOTO ADICIONAL
                                if (imageViewAdicional.getDrawable() != null && esFotoTomada(imageViewAdicional.getDrawable())) {
                                    try {
                                        Bitmap bitmapAdicional = ((BitmapDrawable) imageViewAdicional.getDrawable()).getBitmap();
                                        int mheight = (int) (bitmapAdicional.getHeight() * (1024.0 / bitmapAdicional.getWidth()));
                                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapAdicional, 1024, mheight, true);
                                        image2 = getStringImage(scaled);
                                    } catch (ClassCastException e) {
                                        image2 = "NO_FOTO";
                                        Log.d("VentasFragment", "Adicional: Usando ícono por defecto, NO_FOTO");
                                    }
                                } else {
                                    image2 = "NO_FOTO";
                                }

                                // VERIFICAR FOTO ACTIVIDAD
                                if (imageViewActividad.getDrawable() != null && esFotoTomada(imageViewActividad.getDrawable())) {
                                    try {
                                        Bitmap bitmapActividad = ((BitmapDrawable) imageViewActividad.getDrawable()).getBitmap();
                                        int mheight = (int) (bitmapActividad.getHeight() * (1024.0 / bitmapActividad.getWidth()));
                                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapActividad, 1024, mheight, true);
                                        image3 = getStringImage(scaled);
                                    } catch (ClassCastException e) {
                                        image3 = "NO_FOTO";
                                        Log.d("VentasFragment", "Actividad: Usando ícono por defecto, NO_FOTO");
                                    }
                                } else {
                                    image3 = "NO_FOTO";
                                }



                                values.put(ContractInsertVenta.Columnas.PHARMA_ID, id_pdv);
                                values.put(ContractInsertVenta.Columnas.CODIGO, codigo_pdv);
                                values.put(ContractInsertVenta.Columnas.USUARIO, user);
                                values.put(ContractInsertVenta.Columnas.SUPERVISOR,punto_venta);
                                values.put(ContractInsertVenta.Columnas.FECHA, fechaser);
                                values.put(ContractInsertVenta.Columnas.HORA, horaser);
                         //       values.put(ContractInsertVenta.Columnas.NOMBRE_IMPULSADOR,"N/A");
                                values.put(ContractInsertVenta.Columnas.FECHA_VENTA,fechaVenta);
                                values.put(ContractInsertVenta.Columnas.CATEGORIA,categoria);
                                values.put(ContractInsertVenta.Columnas.SUBCATEGORIA,subcategoria);
                              values.put(ContractInsertVenta.Columnas.MECANICA,mecanica);
                                values.put(ContractInsertVenta.Columnas.TOTAL_STOCK, valor_total);
                                values.put(ContractInsertVenta.Columnas.NUM_FACTURA, cantidad2);
                                //values.put(ContractInsertVenta.Columnas.POFERTA,"N/A");
                                //values.put(ContractInsertVenta.Columnas.BRAND,marca);
                                values.put(ContractInsertVenta.Columnas.SKU,sku);
                                values.put(ContractInsertVenta.Columnas.TIPO_VENTA,tipo_venta);
                                 values.put(ContractInsertVenta.Columnas.TIPO_FACTURA,marca);
                               //m values.put(ContractInsertVenta.Columnas.STOCK_INICIAL,"N/A");
                               //m values.put(ContractInsertVenta.Columnas.PREGULAR, precio_unitario);
                                //m values.put(ContractInsertVenta.Columnas.PPROMOCION, "N/A");
                              //m   values.put(ContractInsertVenta.Columnas.STOCK_FINAL,"N/A");
                               // values.put(ContractInsertVenta.Columnas.TO,valor_total);
                                values.put(ContractInsertVenta.Columnas.TOTAL_FACTURA,totalFactura);
                                values.put(ContractInsertVenta.Columnas.NUMERO_FACTURA,numeroFactura);
                                values.put(ContractInsertVenta.Columnas.MONTO_FACTURA,pvp);
                                values.put(ContractInsertVenta.Columnas.ENTREGO_PROMOCIONAL,entregoPromocional);
                                values.put(ContractInsertVenta.Columnas.PROMOCIONAL,promocional);
                                values.put(ContractInsertVenta.Columnas.CANT_PROMOCIONAL,cantPromocional);
                              //m   values.put(ContractInsertVenta.Columnas.MANUFACTURER, fabricante);
                                values.put(ContractInsertVenta.Columnas.POS_NAME, punto_venta);
                                values.put(ContractInsertVenta.Columnas.KEY_IMAGE, image);
                                values.put(ContractInsertVenta.Columnas.FOTO_ADICIONAL,image2);
                                values.put(ContractInsertVenta.Columnas.FOTO_ACTIVIDAD,image3);
                                values.put(ContractInsertVenta.Columnas.COMENTARIO_FACTURA,coment_factura);
                                values.put(ContractInsertVenta.Columnas.COMENTARIO_ADICIONAL,coment_adicional);
                                values.put(ContractInsertVenta.Columnas.COMENTARIO_ACTIVIDAD,coment_actividad);
                               // values.put(ContractInsertVenta.Columnas., cuenta);

                                values.put(Constantes.PENDIENTE_INSERCION, 1);

                                getContext().getContentResolver().insert(ContractInsertVenta.CONTENT_URI, values);

                                if (VerificarNet.hayConexion(getContext())) {
                                    SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertVenta, null);
                                    Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                                }


                            }

                            limpiarFormulario();
                            alert_detalle_factura.dismiss();


                        }catch (Exception e){
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    private void limpiarFormulario() {
                        txtFechaVenta.setText("");
                        spMarca.setSelection(0);
                      //  spCategoria.setSelection(0);
                        //spSubcategoria.setSelection(0);
                        spProducto.setSelection(0);
                        operadores.clear();

                        listview.setVisibility(View.GONE);

                    }



                    private boolean validarDetalleFormulario(String total_factura,String numero_factura,String entrego_promocional,String promocional,String cant_promocional, String mecanica, String coment_factura, String coment_adicional, String coment_actividad) {

//                        if (entrego_promocional.equals("No")) {
//                            if (numero_factura.trim().isEmpty()) {
//                                Toast.makeText(getContext(), "Ingrese el número de la factura", Toast.LENGTH_LONG).show();
//                                return false;
//                            }
//                        }

                        if (entrego_promocional.equals("Si")){

                            if (numero_factura.trim().isEmpty()) {
                                Toast.makeText(getContext(), "Ingrese el número de la factura", Toast.LENGTH_LONG).show();
                                return false;
                            }

                            if (promocional.equals("Seleccione")){
                                Toast.makeText(getContext(),"Escoja el promocional",Toast.LENGTH_LONG).show();
                                return false;
                            }


                            if (cant_promocional.trim().isEmpty()){
                                Toast.makeText(getContext(),"Ingrese la cantidad del promocional entregado",Toast.LENGTH_LONG).show();
                                return false;
                            }
                            if (mecanica.equals("Seleccione")){
                                Toast.makeText(getContext(),"Escoja una mecanica",Toast.LENGTH_LONG).show();
                                return false;
                            }


                        }

                        // Campos que estan por repositorio
                        ArrayList<Base_campos_x_modulos> campos = handler.getCamposPorModulos(modulo,cuenta);
                        for (int i=0; i < campos.size(); i++) {

                           // Log.i("pieri", "CADENA: " + modulo);
                            //Log.i("pieri", "CADENA: " + cuenta);

                            if (campos.get(i).getCampo().equalsIgnoreCase("FOTO_FACTURA")){
                                if (!esFotoTomada(imageViewFactura.getDrawable())) {
                                    Toast.makeText(getContext(),"No tomó la foto de la factura",Toast.LENGTH_LONG).show();
                                    return false;
                                }
                            }

                            if (campos.get(i).getCampo().equalsIgnoreCase("FOTO_ADICIONAL")){
                                if (!esFotoTomada(imageViewAdicional.getDrawable())) {
                                    Toast.makeText(getContext(),"No tomó la foto adicional",Toast.LENGTH_LONG).show();
                                    return false;
                                }
                            }

                            if (campos.get(i).getCampo().equalsIgnoreCase("FOTO_ACTIVIDAD")){
                                if (!esFotoTomada(imageViewActividad.getDrawable())) {
                                    Toast.makeText(getContext(),"No tomó la foto actividad",Toast.LENGTH_LONG).show();
                                    return false;
                                }
                            }
                        }

                        return true;
                    }

                });

            }

        });





        alert_detalle_factura.show();


    }

    // AGREGAR AQUi EL NUEVO MeTODO
    private boolean esFotoTomada(Drawable drawable) {
        if (drawable == null) {
            return false;
        }
        // Si es un VectorDrawable, es el ícono por defecto (no una foto tomada)
        return !(drawable instanceof VectorDrawable);
    }

    private void obtenerPromocionalVentas(Spinner spPromocionalEntregado) {
        List<String> operadores = handler.getPromocionalVentas();
        Log.i("MICHI", "CADENA: " + cadena);
        Log.i("MICHI1", "CADENA: " + cuenta);

        if (operadores.size()==2) {
            operadores.remove(0);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPromocionalEntregado.setAdapter(dataAdapter);
        spPromocionalEntregado.setOnItemSelectedListener(this);
    }


    private void obtenerMecanicaVentas(Spinner spMecanicaVenta) {
        List<String> operadores = handler.getMecanicaVentas2();
//        Log.i("MICHI", "CADENA: " + cadena);
//        Log.i("MICHI1", "CADENA: " + cuenta);

        if (operadores.size()==2) {
            operadores.remove(0);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMecanicaVenta.setAdapter(dataAdapter);
        spMecanicaVenta.setOnItemSelectedListener(this);
    }




    private class validarPrecio implements TextWatcher {

        private static final double MAX_VALUE = 99.99;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {


                String inputString = editable.toString().trim();
                if (!inputString.isEmpty()) {




                    double inputNumber = Double.parseDouble(inputString);
                    if (inputNumber > MAX_VALUE) {
                        editable.clear();
                        editable.append(String.valueOf(MAX_VALUE));
                    }

                }
            } catch (NumberFormatException e) {
                // Maneja el caso si el número no puede ser analizado correctamente.
                editable.delete(editable.length() - 1, editable.length());
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dia + "/" + (mes+1) + "/" + año);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    //    String outDate = dateFormat.format(date);
    //    txtFechaVenta.setText(outDate);

    //    if (!txtFechaVenta.getText().toString().isEmpty()) {
    //        spCategoria.setEnabled(true);
    //        spSubcategoria.setEnabled(true);
    //    }
    }

    //************METODOS PARA TAKE-PHOTO Y UPLOAD

    //Tomar Foto
   /* private boolean validaPermisos() {

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
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                //botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }



    private boolean esFormularioValido() {

        total_factura = 0;


        if (listview.getCount() == 0) {
            Toast.makeText(getContext(), "Debe agregar al menos 1 producto", Toast.LENGTH_LONG).show();
            return false;
        } else {
            CustomAdapterVentas adapter = (CustomAdapterVentas) listview.getAdapter();
            int itemCount = adapter.getCount();

            for (int i = 0; i < itemCount; i++) {
                View item = listview.getChildAt(i);

                TextView lblProducto = item.findViewById(R.id.lblSku);
                Spinner  spTipoVenta = item.findViewById(R.id.spTipoVentaNuevo);
                EditText txtCantidad = item.findViewById(R.id.txt_cantidad);
                EditText txtPrecio = item.findViewById(R.id.txt_precio_regular);
                EditText txtValorTotal = item.findViewById(R.id.txt_stock_final);

                String producto = lblProducto.getText().toString();
                String cantidad = txtCantidad.getText().toString();
                String precio = txtPrecio.getText().toString();
                String valorTotal = txtValorTotal.getText().toString();

                if (spTipoVenta.getSelectedItem().toString().equalsIgnoreCase("Seleccione")){
                    Toast.makeText(getContext(), "Debe escoger un tipo de venta para el producto: " + producto, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (cantidad.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Debe ingresar una cantidad valida para el producto: " + producto, Toast.LENGTH_LONG).show();
                    return false;
                }
                if (precio.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Debe ingresar un precio valido para el producto: " + producto, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (cuenta.equalsIgnoreCase("BASSA")) {
                    float p_regular = Float.parseFloat(precio);
                    if (p_regular > 299.99) {
//                        Toast.makeText(getContext(),producto + " debe tener un precio inferior a 300",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(),"El precio debe ser inferior a 300 para el producto: " + producto,Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

                if (valorTotal.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Ingrese un precio en el formato correcto para el producto: " + producto, Toast.LENGTH_LONG).show();
                    return false;
                }

                // obtenemos el valor total de todos los SKU's
                total_factura += Double.parseDouble(valorTotal);
            }
        }
        return true;
    }





    public static int calcularAlturaTotal(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        int alturaTotal = 0;

        if (adapter != null) {
            int count = adapter.getCount();
            Log.i("count",""+count);
            View view = adapter.getView(0, null, listView);
            ListView.LayoutParams params = (ListView.LayoutParams) view.getLayoutParams();
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            int alturaLinea = (view.getMeasuredHeight());

            for (int i = 0; i < count; i++) {
//                int adicional = i < 1 ? 5 : 1;
                alturaTotal += alturaLinea;
            }
        }
        Log.i("ALTURA TOTAL", alturaTotal + "");

        return alturaTotal;
    }

    private boolean validarAgregarProducto(String producto) {
        if (producto.equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getContext(), "Debe seleccionar 1 producto", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (operadores.stream().anyMatch(operador -> operador.getSku().equals(producto))) {
            Toast.makeText(getContext(), "El producto seleccionado ya se encuentra en la lista", Toast.LENGTH_SHORT).show();
            spProducto.setSelection(0);
            return false;
        }
        return true;
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getContext().getPackageName(),null);
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

    //new aggg mpin


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


    //new img bassa ventas
    private void cargarImagen(String tipo_foto) {
        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    Intent n = new Intent(getContext(), CameraActivity.class);
                    n.putExtra("activity", tipo_foto);
                    startActivity(n);
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
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
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities=getContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);
        //
    }


    public void scaleImage(ImageView imageView, Bitmap bitmap) {
        try{
            int mheight = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, mheight, true);
            imageView.setImageBitmap(scaled);
            bitmapfinal = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        } catch (Exception e) {
            androidx.appcompat.app.AlertDialog alertDialog1;
            alertDialog1 = new androidx.appcompat.app.AlertDialog.Builder(getContext()).create();
            alertDialog1.setTitle("Message");
            alertDialog1.setMessage("Notificar \t "+e.toString());
            alertDialog1.show();
            Log.e("compressBitmap", "Error on compress file");
        }
    }


    //Permite hacer la imagen mas pequeña old
    public void scaleImage(Bitmap bitmap){
        try{
            int mheight = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, mheight, true);
            if (factura && imageViewFactura != null) {
                imageViewFactura.setImageBitmap(scaled);
            } else if (adicional && imageViewAdicional != null) {
                imageViewAdicional.setImageBitmap(scaled);
            } else if (actividad && imageViewActividad != null) {
                imageViewActividad.setImageBitmap(scaled);
            }
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on compress file: " + e.getMessage());
            Toast.makeText(getContext(), "Error al procesar imagen", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo que sube la imagen al servidor
    public String getStringImage(Bitmap bmp){
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Comprime la Imagen tipo, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public class CustomAdapterVentas extends ArrayAdapter<Ventas> implements Filterable{

        public ArrayList<Ventas> values;
        public Context context;
        boolean[] checkBoxState;

        public CustomAdapterVentas(Context context, ArrayList<Ventas> values, ListView listView) {
            super(context, 0, values);
            this.values = values;
            checkBoxState = new boolean[values.size()];
        }





        public class ViewHolder{
            TextView lblSku;
            TextView lblStockInicial;
            TextView lblCantidad;
            TextView lblPrecio;
            TextView lblStockFinal;
            TextView lblValorTotal;
            CheckBox checkGuardar; //agregado GT
            //EditText txtunidad;
            EditText txt_precio_regular;
            EditText txt_stock_inicial;
            EditText txt_stock_final;
            EditText txt_precio_promocion;
            EditText txt_valor_total;

            EditText txt_cantidad;

            Spinner spTipoVentaNuevo;
            EditText txt_precio_oferta;
            public Button btnCamera;
            public ImageButton btnEliminar;
            public ImageView ivFoto;

            private Bitmap bitmap;
            // Spinner spMotivo;
        }



        @Override
        public int getViewTypeCount() {
            // TODO Auto-generated method stub
                if(getCount()<1) return 1;
                return getCount();
        }


        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub
            return position;
        }




        @SuppressLint("SuspiciousIndentation")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Obtener Instancia Inflater
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder vHolder = null;
            //Comprobar si el View existe
            //Si no existe inflarlo
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.list_row_ventas_new, parent, false); // Modificacion (list_row_option) GT
                //Obtener instancias de los elementos
                vHolder = new ViewHolder();
                vHolder.lblSku = (TextView) convertView.findViewById(R.id.lblSku);
                vHolder.lblStockInicial = (TextView) convertView.findViewById(R.id.lblStockInicial);
                vHolder.lblCantidad = (TextView) convertView.findViewById(R.id.lblCantidad);
                vHolder.lblPrecio = (TextView) convertView.findViewById(R.id.lblPrecio);
                vHolder.lblStockFinal = (TextView) convertView.findViewById(R.id.lblStockFinal);
                vHolder.lblValorTotal = (TextView) convertView.findViewById(R.id.lblValorTotal);
                vHolder.spTipoVentaNuevo = (Spinner) convertView.findViewById(R.id.spTipoVentaNuevo);
                vHolder.txt_cantidad = (EditText) convertView.findViewById(R.id.txt_cantidad);
             //   vHolder.txt_stock_inicial = (EditText) convertView.findViewById(R.id.txt_stock_inicial);
                vHolder.txt_stock_final = (EditText) convertView.findViewById(R.id.txt_stock_final);
                vHolder.txt_valor_total = (EditText) convertView.findViewById(R.id.txt_valor_total);
                vHolder.btnEliminar = (ImageButton) convertView.findViewById(R.id.btnEliminarNuevo);
                // Precio Unitario
                vHolder.txt_precio_regular = (EditText) convertView.findViewById(R.id.txt_precio_regular);
                vHolder.checkGuardar = (CheckBox) convertView.findViewById(R.id.checkGuardar);
                ViewHolder finalVHolder = vHolder;
//                if (cuenta.equalsIgnoreCase("BANCO GUAYAQUIL") ||
//                    cuenta.equalsIgnoreCase("BANCO_GUAYAQUIL")) {
//                    vHolder.txt_precio_regular.setFilters(new InputFilter[]{
//                            new DecimalDigitsInputFilter(4, 2),
//                            new InputFilter.LengthFilter(7)
//                    });
//                } else if (cuenta.equalsIgnoreCase("PINTUCO")) {
//                    vHolder.txt_precio_regular.setFilters(new InputFilter[] {
//                            new DecimalDigitsInputFilter(3, 2),
//                            new InputFilter.LengthFilter(6)
//                    });
//                } else {
//                    vHolder.txt_precio_regular.setFilters(new InputFilter[] {
//                            new DecimalDigitsInputFilter(2, 2),
//                            new InputFilter.LengthFilter(5)
//                    });
//                }

               // vHolder.btnCamera = (Button)   convertView.findViewById(R.id.ibCargarVentas);
              //  vHolder.ivFoto = (ImageView) convertView.findViewById(R.id.ivVentas);
                //vHolder.btnCamera.setOnClickListener(new View.OnClickListener() {
                 //   @Override
                //    public void onClick(View v) {
                 //      cargarImagen(tipo_foto);
                  //      ivVentas = finalVHolder.ivFoto;
                 //   }
             //   });


          // Limitar a solo 2 dígitos numéricos



                Spinner spTipoVentaNuevo = finalVHolder.spTipoVentaNuevo;
                // Agregamos los tipo de ventas del repositorio
               obtenerTipoVentas(spTipoVentaNuevo,cuenta);


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





//                if(!cuenta.equalsIgnoreCase("FACUNDO")){
//
//                    // Ocultar las vistas que no se necesitan
//                    vHolder.lblStockInicial.setVisibility(View.GONE);
//                    vHolder.lblStockFinal.setVisibility(View.GONE);
//                    vHolder.lblStockFinal.setVisibility(View.GONE);
//                    vHolder.lblValorTotal.setVisibility(View.VISIBLE);
//                    vHolder.txt_stock_inicial.setVisibility(View.GONE);
//                    vHolder.txt_stock_final.setVisibility(View.GONE);
//                    vHolder.txt_valor_total.setVisibility(View.VISIBLE);
//
//                    vHolder.lblCantidad.setText("Cantidad");
//
//                    // Ajustar los pesos de las vistas visibles
//                    LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) vHolder.lblCantidad.getLayoutParams();
//                    LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) vHolder.lblPrecio.getLayoutParams();
//                    LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) vHolder.lblValorTotal.getLayoutParams();
//                    LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) vHolder.txt_precio_regular.getLayoutParams();
//                    LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) vHolder.txt_cantidad.getLayoutParams();
//                    LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams) vHolder.txt_valor_total.getLayoutParams();
//                //    LinearLayout.LayoutParams params7 = (LinearLayout.LayoutParams) vHolder.checkGuardar.getLayoutParams();
//
//                    params1.weight = 1.75f;
//                    params2.weight = 1.75f;
//                    params3.weight = 1.75f;
//                    params4.weight = 1.75f;
//                    params5.weight = 1.75f;
//                    params6.weight = 1.75f;
//                //    params7.weight = 0.25f;
//
//                    vHolder.lblCantidad.setLayoutParams(params1);
//                    vHolder.lblPrecio.setLayoutParams(params2);
//                    vHolder.lblValorTotal.setLayoutParams(params3);
//                    vHolder.txt_precio_regular.setLayoutParams(params4);
             //       vHolder.txt_cantidad.setLayoutParams(params5);
//                    vHolder.txt_valor_total.setLayoutParams(params6);
//                //    vHolder.checkGuardar.setLayoutParams(params7);
//
//
  //                  vHolder.txt_cantidad.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(2)});
//
//                } else {
//                    vHolder.btnEliminar.setVisibility(View.GONE);
//                }


//                vHolder.txt_stock_inicial.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
////                        if (cuenta.equals("FACUNDO")) {
////                            calcularResultado(finalVHolder);
////                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });

//                vHolder.txt_cantidad.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
////                            calcularValorTotal(finalVHolder);
//
////                        if (cuenta.equals("FACUNDO")) {
////                            calcularResultado(finalVHolder);
////                        } else {
////                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        String text = s.toString();
//                        values.get(position).setCantidad(text);
//                    }
//                });

                vHolder.txt_cantidad.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.i("davidInt", "entro xd");
                        calcularValorTotal(finalVHolder);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (position < values.size()) { // Verificar límites del ArrayList
                            values.get(position).setCantidad(s.toString());
                        }

                    }
                });

                vHolder.txt_precio_regular.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                                if (!cuenta.equals("FACUNDO")){
//                                    calcularValorTotal(finalVHolder);
//                                }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String text = editable.toString();
                        values.get(position).setPrecio_unitario(text);
                    }
                });

                

                vHolder.txt_valor_total.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
//                        String text = editable.toString();
//                        values.get(position).setValor_total(text);
                    }
                });

                final ViewHolder finalv = vHolder;
                convertView.setTag(vHolder);
            } else {
                vHolder = (ViewHolder) convertView.getTag();
            }

            try {
                if (position >= 0 && position < values.size()) {
                    vHolder.lblSku.setText(values.get(position).getSku());
                    // Agregamos los valores , que se volvieron a dibujar los items
                    vHolder.txt_cantidad.setText(values.get(position).getCantidad());
                    vHolder.txt_precio_regular.setText(values.get(position).getPvp());
                    vHolder.txt_valor_total.setText(values.get(position).getValor_total());
                    // RESTAURAR LA SELECCIÓN DEL SPINNER
                    String tipoVentaGuardado = values.get(position).getTipo_venta();
                    if (tipoVentaGuardado != null && !tipoVentaGuardado.isEmpty()) {
                        // Buscar la posición del valor en el spinner
                        for (int i = 0; i < vHolder.spTipoVentaNuevo.getCount(); i++) {
                            if (vHolder.spTipoVentaNuevo.getItemAtPosition(i).toString().equals(tipoVentaGuardado)) {
                                vHolder.spTipoVentaNuevo.setSelection(i);
                                break;
                            }
                        }
                    } else {
                        vHolder.spTipoVentaNuevo.setSelection(0); // Valor por defecto
                    }
                    // AGREGAR LISTENER PARA GUARDAR LA SELECCIÓN
                    vHolder.spTipoVentaNuevo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            String selectedValue = parent.getItemAtPosition(pos).toString();
                            if (position < values.size()) {
                                values.get(position).setTipo_venta(selectedValue);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    vHolder.lblSku.setTextColor(getResources().getColor(R.color.colorWhite));
                //    vHolder.txt_cantidad.setText(vHolder.txt_cantidad.getText().toString());
                //    vHolder.txt_precio_regular.setText(vHolder.txt_precio_regular.getText().toString());
                //    vHolder.txt_valor_total.setText(vHolder.txt_valor_total.getText().toString());

                   sesion = handler.getListGuardadoPrecios3(codigo_pdv);// revisar no olvidar

                    Log.i("PRECIOS",values.get(position).getSku()/* + "-" + values.get(position).getPvp() + "-"*/);
                    Log.i("CODIGO",codigo_pdv);
                    Log.i("SESION SIZE",sesion.size()+"");

                    for(int i = 0; i < sesion.size(); i++) {
                        if (values.get(position).getSku().equals(sesion.get(i).getSku())) {
                            vHolder.lblSku.setTextColor(getResources().getColor(R.color.verde));
                            Log.i("ENTRA SESION","ENTRA SESION");
                        }else{
                            Log.i("NO ENTRA","NO ENTRA");
                        }
                    }

                    final ViewHolder finalv = vHolder;

//hoyyyyy
                //    ViewHolder finalVHolder1 = vHolder;
                    vHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Eliminar el elemento del ArrayList
                            operadores.remove(position);

                            // Actualizar el adapter existente
                            dataAdapter.notifyDataSetChanged();

                            listview.post(new Runnable() {
                                @Override
                                public void run() {
                                    ViewGroup.LayoutParams layoutParams = listview.getLayoutParams();
                                    layoutParams.height = calcularAlturaTotal(listview);
                                    listview.setLayoutParams(layoutParams);
                                }
                            });
                        }
                    });


                    vHolder.checkGuardar.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            nombImp = txtNomImp.getText().toString();
                            fechaventas = txtFechaVenta.getText().toString();
                            tipoVenta = finalv.spTipoVentaNuevo.getSelectedItem().toString();
                            cantidad = finalv.txt_cantidad.getText().toString();
                            pregular = finalv.txt_precio_regular.getText().toString().trim();
                            vstock_inicial = finalv.txt_stock_inicial.getText().toString();
                            vstock_final = finalv.txt_stock_final.getText().toString();
                            //   ppromocion = finalv.txt_precio_promocion.getText().toString();
                            //    poferta = finalv.txt_precio_oferta.getText().toString();
                            sku = finalv.lblSku.getText().toString();
                            if (((CheckBox) v).isChecked()) {
                                String image = "NO_FOTO";
                                Bitmap bitmapFoto = null;

//                                if (finalv.ivFoto != null && finalv.ivFoto.getDrawable() != null) { //ImageView no vacio
//                                    bitmapFoto = ((BitmapDrawable)finalv.ivFoto.getDrawable()).getBitmap();
//                                    image = getStringImage(bitmapFoto);
//                                }

                                if (finalv.ivFoto != null && finalv.ivFoto.getDrawable() != null) { //ImageView no vacio
                                    bitmapFoto = ((BitmapDrawable)finalv.ivFoto.getDrawable()).getBitmap();
                                    image = getStringImage(bitmapFoto);
                                }
                                if(esValidoFormulario(nombImp,fechaventas,tipoVenta,cantidad,pregular,ppromocion,vstock_inicial,vstock_final)){
                                    insertData(sku,nombImp,fechaventas,tipoVenta,cantidad, pregular, ppromocion, poferta,vstock_inicial,vstock_final);

                                 //   txtNomImp.setText("");
                                 //   txtFechaVenta.setText("");
                                    finalv.lblSku.setTextColor(getResources().getColor(R.color.verde));
                                    finalv.spTipoVentaNuevo.setSelection(0);
                                    finalv.txt_stock_inicial.setText("");
                                    finalv.txt_cantidad.setText("");
                                    finalv.txt_stock_final.setText("");
//                                    finalv.txt_precio_regular.setText("");
                                    finalv.ivFoto.setImageDrawable(null);
                                    finalv.checkGuardar.setChecked(false);

                                }else{
                                    finalv.checkGuardar.setChecked(false);
                                }


                                //   if (!pregular.trim().isEmpty()) {
                                //       double oferta = Double.parseDouble(poferta);
                                //       double oferta = Double.parseDouble(poferta);
                                //        double pvc = Double.parseDouble(ppromocion); //


                                //      if (pvc < 0) {
                            /*        } else {
                                        Toast.makeText(getContext(), "El Precio Oferta no debe ser mayor al PVC y el PVC NO debe ser menor a 0", Toast.LENGTH_LONG).show();
                                    }*/

                                //    } else {
                                //         Toast.makeText(getContext(), "No ingresaste el valor de ventas", Toast.LENGTH_LONG).show();
                                //         finalv.checkGuardar.setChecked(false);
                                //     }
                            }
                        }
                    });
                }
            }catch (Exception e){
                Log.i("EXCEPTION", e.getMessage());
            }
            //Devolver al ListView la fila creada
            return convertView;
        }



        private void calcularResultado(ViewHolder vholder) {

            // Obtener valores de los EditText
            String v1 = vholder.txt_stock_inicial.getText().toString();
            String v2 = vholder.txt_cantidad.getText().toString();

            // Realizar cálculo (en este caso, simplemente sumar los valores)
            try {

                if (!v1.isEmpty() && !v2.isEmpty()){
                    int v_stock = Integer.parseInt(v1);
                    int v_cant = Integer.parseInt(v2);
                    int resultado = v_stock - v_cant;

                    if (resultado < 0){
                        vholder.txt_stock_final.setTextColor(getResources().getColor(R.color.rojo));
                    } else {
                        vholder.txt_stock_final.setTextColor(getResources().getColor(R.color.gris));
                    }

                    // Mostrar resultado en editText3
                    vholder.txt_stock_final.setText("" +resultado);
                } else {
                    vholder.txt_stock_final.setText("");
                }

            } catch (NumberFormatException e) {
                // Manejar error si no se pueden convertir a números
                vholder.txt_stock_final.setText("Error");
            }

        }

        private void calcularValorTotal(ViewHolder vholder) {

            // Obtener valores de los EditText
            String v1 = vholder.txt_cantidad.getText().toString();
            String v2 = vholder.txt_precio_regular.getText().toString();

            Log.i("v2","" + v2);


            // Realizar cálculo (en este caso, simplemente sumar los valores)
            try {

                if (!v1.isEmpty() && !v2.isEmpty()){
                    Log.i("entra fn","entra fn");

                    int v_cant = Integer.parseInt(v1);
                    double v_precio = Float.parseFloat(v2);
                    double resultado = v_cant * v_precio;

                    Log.i("result","" + resultado);


                    // Mostrar resultado en editText3
                    vholder.txt_stock_final.setText(""+ df.format(resultado));

                } else {
                    vholder.txt_stock_final.setText("");
                }

            } catch (NumberFormatException e) {
                // Manejar error si no se pueden convertir a números
                vholder.txt_valor_total.setText("");
            }

        }

//        private void mostrarVistaPrevia(Drawable drawable) {
//            AlertDialog.Builder alertadd = new AlertDialog.Builder(context);
//            alertadd.setTitle("Vista Previa");
//            LayoutInflater factory = LayoutInflater.from(context);
//            final View view = factory.inflate(R.layout.vista_previa, null);
//            ImageView dialog_imageview = view.findViewById(R.id.dialog_imageview);
//            dialog_imageview.setImageDrawable(drawable);
//            alertadd.setView(view);
//            alertadd.setNeutralButton("Cerrar", (dlg, sumthin) -> {
//            });
//            alertadd.show();
//        }


        private boolean esValidoFormulario(String nomImp,String fechaVenta,String tipoVenta,String cantidad,String pregular,String ppromocion,String vstock_inicial,String vstock_final) {
//            Toast.makeText(getContext(),"Form Valido: " + pregular + " " + cuenta,Toast.LENGTH_SHORT).show();
            Log.i("Form Valido", pregular + " " + cuenta);

//            if (cuenta.equalsIgnoreCase("PINTUCO")) {
//                float p_regular = Float.parseFloat(pregular);
//                if (p_regular > 299.99) {
//                    Toast.makeText(getContext(),"El precio debe ser inferior a 300",Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            }
//
//            if (!cuenta.equalsIgnoreCase("BANCO_GUAYAQUIL")) {
//                float p_regular = Float.parseFloat(pregular);
//                if (p_regular > 99.99) {
//                    Toast.makeText(getContext(), "El precio debe ser inferior a 100", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            } else {
//                float p_regular = Float.parseFloat(pregular);
//                if (p_regular > 9999.99) {
//                    Toast.makeText(getContext(),"El precio debe ser inferior a 10000",Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            }

            if (cantidad.isEmpty()) {
                Toast.makeText(getContext(),"Ingrese la cantidad",Toast.LENGTH_LONG).show();
                return false;
            }
//            if (fechaVenta.isEmpty()) {
//                Toast.makeText(getContext(),"Ingrese la fecha de venta",Toast.LENGTH_LONG).show();
//                return false;
//            }

            float v_cantidad = Float.parseFloat(cantidad);
            if (v_cantidad == 0) {
                Toast.makeText(getContext(),"La cantidad no debe ser cero",Toast.LENGTH_LONG).show();
                return false;
            }

//            if (!cuenta.equalsIgnoreCase("FACUNDO")) {
//                // Validaciones para cualquier cuenta excepto "FACUNDO"
//
//                if (tipoVenta.equalsIgnoreCase("Seleccione")){
//                    Toast.makeText(getContext(),"Escoja un tipo de venta",Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//
//
//
//
//                if (pregular.trim().isEmpty()) {
//                    Toast.makeText(getContext(),"Ingrese el precio unitario",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                float p_regular = Float.parseFloat(pregular);
//                if (p_regular == 0) {
//                    Toast.makeText(getContext(),"El precio no debe ser cero",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//            } else {
//                // Validaciones específicas para cuenta "FACUNDO"
//
//                if (nomImp.isEmpty()){
//                    Toast.makeText(getContext(),"Escriba los nombres del impulsador(a)",Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//
//                /*
//                if (fechaVenta.isEmpty()){
//                    Toast.makeText(getContext(),"Escoga la fecha de la venta",Toast.LENGTH_SHORT).show();
//                    return false;
//                }*/
//
//                if (tipoVenta.equalsIgnoreCase("Seleccione")){
//                    Toast.makeText(getContext(),"Escoja un tipo de venta",Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//
//                if (vstock_inicial.isEmpty()) {
//                    Toast.makeText(getContext(),"Ingrese el stock inicial",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                float p_stock_inicial = Float.parseFloat(vstock_inicial);
//                if (p_stock_inicial == 0) {
//                    Toast.makeText(getContext(),"El stock inicial no debe ser cero",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                if (cantidad.isEmpty()) {
//                    Toast.makeText(getContext(),"Ingrese la cantidad",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                float v_cantidad = Float.parseFloat(cantidad);
//                if (v_cantidad == 0) {
//                    Toast.makeText(getContext(),"La cantidad no debe ser cero",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                if (pregular.trim().isEmpty()) {
//                    Toast.makeText(getContext(),"Ingrese el precio unitario",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                float p_regular = Float.parseFloat(pregular);
//                if (p_regular == 0) {
//                    Toast.makeText(getContext(),"El precio no debe ser cero",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                if (vstock_final.isEmpty()) {
//                    Toast.makeText(getContext(),"Ingrese los valores de stock inicial y cantidad vendida",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                float p_stock_final = Float.parseFloat(vstock_final);
//                if (p_stock_final < 0) {
//                    Toast.makeText(getContext(),"La cantidad vendida no puede ser mayor al stock inicial",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }





            /*
            if (cuenta.equalsIgnoreCase("FACUNDO")){



                if (!vstock_final.isEmpty()){
                    float p_stock_final = Float.parseFloat(vstock_final);

                    if (p_stock_final < 0){
                        Toast.makeText(getContext(),"La cant. vendida no puede ser mayor al stock inicial",Toast.LENGTH_LONG).show();
                        return false;
                    }

                } else {
                    Toast.makeText(getContext(),"Ingrese los valores de stock inicial y cant. vendida",Toast.LENGTH_LONG).show();
                    return false;
                }
            }


            if (cantidad.equalsIgnoreCase("")){
                Toast.makeText(getContext(),"Ingrese la cantidad",Toast.LENGTH_SHORT).show();
                return false;
            }

            if (cantidad.equalsIgnoreCase("0")){
                Toast.makeText(getContext(),"La cantidad no puede ser cero",Toast.LENGTH_SHORT).show();
                return false;
            }*/










            //        if (!(Integer.parseInt(cantidad) > 0)){

//                if (pregular.equalsIgnoreCase("")){
//                    Toast.makeText(getContext(),"No ingresaste el precio",Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//
//                if (pregular.equalsIgnoreCase("0") || pregular.equalsIgnoreCase("0.0") || ppromocion.equalsIgnoreCase("0.00")){
//                    Toast.makeText(getContext(),"El precio no debe ser 0",Toast.LENGTH_SHORT).show();
//                    return false;
//                }

            //         }

            return true;
        }

    }
}