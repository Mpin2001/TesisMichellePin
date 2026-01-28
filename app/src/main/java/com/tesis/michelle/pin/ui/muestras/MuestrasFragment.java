package com.tesis.michelle.pin.ui.muestras;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

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
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tesis.michelle.pin.Clase.Muestras;
import com.tesis.michelle.pin.Clase.Precio;
import com.tesis.michelle.pin.Clase.Producto;
import com.tesis.michelle.pin.Clase.Ventas;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.MarshMallowPermission;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Conexion.VolleySingleton;
import com.tesis.michelle.pin.Contracts.ContractInsertMuestras;
import com.tesis.michelle.pin.Contracts.ContractInsertMuestras;
import com.tesis.michelle.pin.Contracts.ContractInsertVenta;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Utils.RequestPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class MuestrasFragment extends Fragment implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayList<Precio> type_name_copy = new ArrayList<Precio>();
    ArrayList<Precio> sesion = new ArrayList<Precio>();
    private Spinner spCategoria;

    public static ImageView img;
    private Spinner spSubcategoria;
    private Spinner spFabricante;
    /*private Spinner spSubcategoria;
    private Spinner spSegmento;*/
    private Spinner spMarca;
    private Spinner spSku;
    private Spinner spTipoActividad;

    /*private Spinner spTamano;
    private Spinner spCantidad;*/
    private EditText txtSKUCode;
    private EditText txt_cantidad;
    private EditText txtDescripcion;
    private Button btnPropia;
    private Button btnGuardar;
    private Button btnCompetencia;

    public static List<String> valores;
    public static List<String> valores2;

    public static ArrayList<Producto>productos;
    public AlertDialog adMsj;
    public AlertDialog adMsjMayo;

    public static boolean v2 = true;
    public static boolean v3 = true;

    //BASE SQLITE
    private String categoria, subcategoria, segmento1, segmento2, brand, actividad, tamano, codigo, descripcion;
    private String id_pdv,user,codigo_pdv, punto_venta,fecha,hora;

    DatabaseHelper handler;

    EditText txtsearch;
    TextView txtfechav, lSeleccioneProducto;
    ImageButton btnFecha;
    private String marca, tipoActividad, cantidad, observacion;
    private EditText txtCantidad, txtObservacion;
    TextView empty;
    ListView listview;

    public ArrayList<Precio> listProductos;
    List<String> listTarget;
    List<String> filterProducts;
    //CustomAdapterPrecios dataAdapter; //comentar mpin

    private EditText txtventas, txtpregular, txtppromocion, txtcuotas, txtvcuotas;
    private String fechaventas, producto, pmayorista, pregular, ppromocion, pdescuento,sku, c_descuento,  cuotas, vcuotas, format, presentacion, var_sugerido;
    private String poferta;
    private String ciudad,retail,sucursal;
    private String canal, subcanal;

    LinearLayout layout_skuName;
    LinearLayout layout_skuDescripcion;

    //Photo Camera
    private ImageView imageView;
    private Bitmap bitmap;
    private Bitmap bitmapfinal;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private final String CARPETA_RAIZ="SonyApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Ventas";
    String path;
    Button btnCamera;

    MarshMallowPermission marshMallowPermission;

    private String tipo = "MARCA_PROPIA";
    private final String fabricante = "BASSA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_muestras, container, false);
        valores = new ArrayList<>();
        valores2 = new ArrayList<>();

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);
        LoadData();
        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();
        marshMallowPermission = new MarshMallowPermission(getActivity());

        //startService(new Intent(getContext(), MyService.class));

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

        //lSeleccioneProducto = (TextView)findViewById(R.id.lSeleccioneProducto);
        spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);
        spSubcategoria = (Spinner) rootView.findViewById(R.id.spSubcategoria);
        /*spSubcategoria = (Spinner)findViewById(R.id.spSubcategoria);
        spSegmento = (Spinner)findViewById(R.id.spSegmento);*/
        spMarca = (Spinner) rootView.findViewById(R.id.spMarca);
        spSku = (Spinner) rootView.findViewById(R.id.spSku); //nuevo
        spTipoActividad = (Spinner) rootView.findViewById(R.id.spTipoActividad); //nuevo

        spFabricante = (Spinner) rootView.findViewById(R.id.spFabricante);
        /*spTamano = (Spinner)findViewById(R.id.spTamaño);
        spCantidad = (Spinner)findViewById(R.id.spCantidad);*/
        txtSKUCode = (EditText) rootView.findViewById(R.id.txtSKUCode);
        txt_cantidad = (EditText) rootView.findViewById(R.id.txt_cantidad);
        txtDescripcion  = (EditText) rootView.findViewById(R.id.txtDescripcionSKU);
        btnPropia  = (Button) rootView.findViewById(R.id.btnPropia);
        btnCompetencia  = (Button) rootView.findViewById(R.id.btnCompetencia);

        listview = (ListView) rootView.findViewById(R.id.lvSKUCode);
        btnGuardar= (Button) rootView.findViewById(R.id.btnGuardar); // boton de guardar

//        View headerView = (View) this.getLayoutInflater().inflate(R.layout.list_row_precios_title,null,false);
//        listview.addHeaderView(headerView,null,false);

        //lSeleccioneProducto.setText("Seleccione Producto: "+ format);

      //  layout_skuName = (LinearLayout) rootView.findViewById(R.id.layout_skuName);
       // layout_skuDescripcion = (LinearLayout) rootView.findViewById(R.id.layout_skuDescripcion);

     //   layout_skuName.setVisibility(View.INVISIBLE);
       // layout_skuDescripcion.setVisibility(View.INVISIBLE);

        //filtrarCodigoSKU();

       // consultaGuardado();

        empty = (TextView) rootView.findViewById(R.id.recyclerview_data_empty);
        btnGuardar.setOnClickListener(this);

        //filtrarCategoria(tipo, fabricante);
       // filtrarFabricante();
        filtrarMarca( subcategoria, tipo, fabricante, canal, subcanal);

   //     ListView listView = (ListView) rootView.findViewById(R.id.lvSKUCode);
//        listView.setOnTouchListener(new View.OnTouchListener() {
//            // Setting on Touch Listener for handling the touch inside ScrollView
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Disallow the touch request for parent scroll on touch of child view
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });

       // btnPropia.setOnClickListener(this);
        //btnCompetencia.setOnClickListener(this);

        //consultarPvc();

        return rootView;
    }


    public void respuestaPvc(JSONObject response){
        Log.i("CASO DE RESPUESTA", "SIII");
        productos = new ArrayList<>();
        if (response!=null){
            try{
                //Obtener atributo estado
                String estado = response.getString("estado");
                switch (estado){
                    case "1": //EXITO
                        Log.i("CASO DE EXITO", "SIII");


                        JSONArray mensaje = response.getJSONArray("productos_pvc");
                        Log.i("EL MENSAJE", String.valueOf(mensaje));

                        for(int i=0;i<mensaje.length();i++){
                            JSONObject jb1 = mensaje.getJSONObject(i);
                            Producto p = new Producto();

                            p.setSku(jb1.getString("sku"));
                            p.setPvc(jb1.getString("pvc"));
                            productos.add(p);
                            Log.i("CASO JB1.", String.valueOf(productos));
                        }

                        Log.i("productospvc",String.valueOf(mensaje));
                        Log.i("SE LLENA PRODUCTOS",String.valueOf(productos));
                        break;
                    case "2": //FALLIDO
                        String mensaje2 = response.getString("mensaje");
                        Log.i("apro","entro22");
                        Toast.makeText(getActivity().getApplicationContext(), mensaje2,Toast.LENGTH_LONG).show();
                        break;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"VACIO",Toast.LENGTH_LONG).show();
        }
    }

    public void consultarPvc(){
        Log.i("ENTRO AL CONSULTAR","SII");
        try{
            HashMap<String, String> map = new HashMap<>(); //
            //map.put("cod_tarea", "6BYM3");
            map.put("operator",user);
            //Log.i("usuariosl",""+punto_venta);
            // Crear nuevo objeto Json basado en el mapa
            JSONObject jobject = new JSONObject(map);

            //GET METHOD
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constantes.GET_PRODUCTOS_PVC, jobject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    respuestaPvc(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (error instanceof TimeoutError) {
                                Toast.makeText(getActivity().getApplicationContext(), Mensajes.TIME_OUT,Toast.LENGTH_SHORT).show();
                                Log.i("apro","entro2");
                            } else if (error instanceof NoConnectionError) {
                                //TODO
                                Toast.makeText(getActivity().getApplicationContext(), Mensajes.NO_RED,Toast.LENGTH_SHORT).show();
                                Log.i("apro","entro3");
                            }else if (error instanceof AuthFailureError) {
                                Log.i("apro","entro4");
                                //TODO
                            } else if (error instanceof ServerError) {
                                Log.i("apro","entro5");
                                //TODO
                                Toast.makeText(getActivity().getApplicationContext(), Mensajes.SEVER_ERROR,Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                Log.i("apro","entro6");
                                //TODO
                                Toast.makeText(getActivity().getApplicationContext(),Mensajes.RED_ERROR,Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Log.i("apro","entro7");
                                Log.i("exhs",""+error.getMessage());
                                //TODO
                            }
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

    public void onResume() {
        super.onResume();
        Log.i("ACTIVITY", "ON RESUME");
        new DeveloperOptions().modalDevOptions(getActivity());
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV,Constantes.NODATA);
        user = sharedPreferences.getString(Constantes.USER,Constantes.NODATA);

        id_pdv = sharedPreferences.getString(Constantes.PHARMA_ID,Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        subcanal = sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
    }

    public void filtrarCategoria(String tipo, String fabricante) {
        List<String> operadores = handler.getCategoriaPrecios(tipo, fabricante, codigo_pdv, canal, subcanal);
        Log.i("david4",""+operadores);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
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

    public void filtrarSubcategoria(String categoria, String tipo, String fabricante) {
        List<String> operadores = handler.getSubcategoriaPrecios(categoria, tipo, fabricante, codigo_pdv, canal, subcanal);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }

    /*public void filtrarMarca(String subcategoria, String subcategoria) {
        List<String> operadores = handler.getSegmento1(subcategoria,subcategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }

    public void filtrarSegmento(String subcategoria, String subcategoria, String subcategoria) {
        List<String> operadores = handler.getSegmento2(subcategoria,subcategoria,subcategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSegmento.setAdapter(dataAdapter);
        spSegmento.setOnItemSelectedListener(this);
    }*/

    public void filtrarMarca(String subcategoria, String tipo, String fabricante, String canal, String subcanal) {
        List<String> operadores = handler.filtrarMarcaPrecios(subcategoria, tipo, fabricante,canal,subcanal);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void filtrarProducto(String marca, String canal, String subcanal) {
        try {
            // Solo usar marca, sin fabricante
            ArrayList<Muestras> productos = handler.filtrarProductosPorMarcaMuestras( marca, canal,  subcanal);

            // Crear lista solo de SKUs para el spinner
            List<String> skuList = new ArrayList<>();
            skuList.add("Seleccione");

            for (Muestras producto : productos) {
                skuList.add(producto.getSku());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, skuList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSku.setAdapter(dataAdapter);
            spSku.setOnItemSelectedListener(this);

        } catch (Exception e) {
            Log.e("FILTRAR_PRODUCTO", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void filtrarTipoActividad(){
        List<String> operadores = handler.getTipoActividades();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i("tn","entraa");
        Log.i("tn",""+ operadores);
        spTipoActividad.setAdapter(dataAdapter);
        spTipoActividad.setOnItemSelectedListener(this);
    }
    public void filtrarFabricante() {
        List<String> operadores = handler.getFabricantesDistinct();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFabricante.setAdapter(dataAdapter);
        spFabricante.setOnItemSelectedListener(this);
    }

    /*public void filtrartamano(String subcategoria, String subcategoria, String subcategoria, String presentacion, String contenido) {
        List<String> operadores = handler.getTamano(subcategoria,subcategoria,subcategoria,presentacion,contenido);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTamano.setAdapter(dataAdapter);
        spTamano.setOnItemSelectedListener(this);
    }

    public vo
    id filtrarcantidad(String subcategoria, String subcategoria, String subcategoria, String presentacion, String contenido, String tamano) {
        List<String> operadores = handler.getCantidad(subcategoria,subcategoria,subcategoria,presentacion,contenido, tamano);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCantidad.setAdapter(dataAdapter);
        spCantidad.setOnItemSelectedListener(this);
    }*/

    public void consultaGuardado() {
        sesion = handler.getListGuardadoPrecios(codigo_pdv);
        for(int i = 0; i < sesion.size(); i++) {
            Log.i("INFO", sesion.get(i).getSku_code() + " " + sesion.get(i).getPvp() + " " + sesion.get(i).getPvc());
        }
    }

    public void showListView( String brand, String fabricante, String tipo) {
        listProductos = handler.filtrarProductosPorMarca(brand, fabricante,
                codigo_pdv, canal, subcanal, tipo);
        Log.i("LIST PRODCUTOS", listProductos.toString());
      //  dataAdapter = new CustomAdapterPrecios(getContext(), listProductos);
//        if (!dataAdapter.isEmpty()) {
//            empty.setVisibility(View.INVISIBLE);
//            listview.setVisibility(View.VISIBLE);
//            listview.setAdapter(dataAdapter);
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
//                    ArrayList<Precio> type_name_filter = new ArrayList<Precio>();
//
//                    String text = s.toString();
//
//                    for (int i = 0; i < listProductos.size(); i++) {
//                        if ((listProductos.get(i).getSku_code().toLowerCase()).contains(text.toLowerCase())) {
//                            Precio p = new Precio();
//                            p.setSku_code(listProductos.get(i).getSku_code());
//                            p.setPvp(listProductos.get(i).getPvp());
//                            p.setPvc(listProductos.get(i).getPvc());
//                            p.setDescuento(listProductos.get(i).getDescuento()); //mpin prueba
//
//                            type_name_filter.add(p);
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
    }

//    public void listUpdate(ArrayList<Precio> data) {
//        if (!data.isEmpty()) {
//            listview.setVisibility(View.VISIBLE);
//            empty.setVisibility(View.INVISIBLE);
//            listview.setAdapter(new CustomAdapterPrecios(getContext(), data));
//        }else{
//            listview.setVisibility(View.GONE);
//            empty.setVisibility(View.VISIBLE);
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//        if (adapterView== spCategoria) {
//            try{
//                categoria = adapterView.getItemAtPosition(i).toString();
//                filtrarSubcategoria(categoria, tipo, fabricante);
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//        if (adapterView== spSubcategoria) {
//            try{
//                subcategoria =adapterView.getItemAtPosition(i).toString();
//                filtrarMarca(categoria, subcategoria, tipo, fabricante);
//                //filtrarMarca(subcategoria,subcategoria);
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
        /*if (adapterView==spSubcategoria) {
            try{
                subcategoria=adapterView.getItemAtPosition(i).toString();
                filtrarSegmento(subcategoria,subcategoria,subcategoria);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if (adapterView==spSegmento) {
            try{
                presentacion=adapterView.getItemAtPosition(i).toString();
                filtrarMarca(subcategoria,subcategoria,subcategoria,presentacion);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }*/
//        if (adapterView== spFabricante) {
//            try{
//                presentacion=adapterView.getItemAtPosition(i).toString();
//                //filtrartamano(subcategoria,subcategoria,subcategoria,presentacion,contenido);
//                filtrarMarca( subcategoria, tipo, fabricante, canal, subcanal);
////                showListView(categoria,subcategoria,brand,presentacion,tipo);
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
        if (adapterView == spMarca) {
            try {
                brand = adapterView.getItemAtPosition(i).toString();
                if (!brand.equals("Seleccione")) {
                    // Filtrar productos para el spinner basado en la marca seleccionada
                    filtrarProducto(brand, canal, subcanal);
                    filtrarTipoActividad(); // moverlo
                } else {
                    // Limpiar el spinner de productos
                    limpiarSpinnerProductos();
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

//        if (adapterView == spSku) {
//            try {
//                sku = adapterView.getItemAtPosition(i).toString();
//                if (!sku.equals("Seleccione")) {
//                    // Filtrar productos para el spinner basado en la marca seleccionada
//                   // filtrarProducto(brand, canal, subcanal);
//                    filtrarTipoActividad();
//                } else {
//                    // Limpiar el spinner de productos
//                    limpiarSpinnerProductos();
//                }
//            } catch (Exception e) {
//                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }




        /*if (adapterView==spTamano) {
            try{
                tamano=adapterView.getItemAtPosition(i).toString();
                filtrarcantidad(subcategoria,subcategoria,subcategoria,presentacion,contenido,tamano);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if (adapterView==spCantidad) {
            try{
                cantidad=adapterView.getItemAtPosition(i).toString();
                showListView(subcategoria,subcategoria,subcategoria,presentacion,contenido,tamano,cantidad);
                // showListDescripcion(channel,subcategoria,subcategoria,presentacion,contenido,tamano,cantidad);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }*/
    }
    private void limpiarSpinnerProductos() {
        List<String> emptyList = new ArrayList<>();
        emptyList.add("Seleccione");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, emptyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSku.setAdapter(dataAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //if (!fechaventas.equals("") && fechaventas!=null) {
        String model = adapterView.getItemAtPosition(i).toString();
        alertDialog(model);
        //}else{
        //   Toast.makeText(getContext(),Mensajes.FECHA,Toast.LENGTH_SHORT).show();
        // }
    }

    public void alertDialog(final String skuSelected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_precios, null);
        //Title
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.setTitle("Precios");
        builder.setView(LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_precios,null));

        //Traer Views
        TextView lblModelo=(TextView)dialogView.findViewById(R.id.lblModelo);
        //txtventas = (EditText)dialogView.findViewById(R.id.txtVentas);
        txtpregular = (EditText)dialogView.findViewById(R.id.txtPcont);
        txtppromocion = (EditText) dialogView.findViewById(R.id.txtPcredito);
        //txtcuotas=(EditText) dialogView.findViewById(R.id.txtcuota);
        //txtvcuotas=(EditText) dialogView.findViewById(R.id.txtVCuota);

       /* imageView = (ImageView)dialogView.findViewById(R.id.imageViewPrec);
        btnCamera = (Button) dialogView.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(this);*/

        /*if (validaPermisos()) {
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
                        if (!pregular.equals("") && pregular !=null) {
                            //insertData(skuSelected, pregular, ppromocion);
                        }else{
                            Toast.makeText(getContext(),"No ingresaste el precio regular",Toast.LENGTH_LONG).show();
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
        cButton.setTextColor(Color.WHITE);
        //cButton.setBackgroundColor(Color.rgb(79,195,247));
        cButton.setPadding(4,2,4,2);
    }

    private void mostrarMsg() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Desea guardar la información? \n");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener valores de los spinners
                marca = spMarca.getSelectedItem().toString();
                String sku = spSku.getSelectedItem().toString();
                tipoActividad = spTipoActividad.getSelectedItem().toString();

                // Obtener cantidad si existe el campo
                // cantidad = ""; // valor por defecto

                cantidad = txt_cantidad.getText().toString().trim();


                // Obtener observación si existe el campo
//                    observacion = "";
//                    if (txtObservacion != null) {
//                        observacion = txtObservacion.getText().toString().trim();
//                    }

                // Llamar a insertData con los parámetros correctos
                insertData(marca, sku, tipoActividad, cantidad, observacion);

                limpiarDatos();
            }
        });

        builder.setNeutralButton("NO", null);

        android.app.AlertDialog ad = builder.create();
        ad.show();

    }




    public void insertData(String marca, String sku, String tipoActividad, String cantidad, String observacion) {
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();

            DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fechaser = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            ContentValues values = new ContentValues();

            // Asegúrate que estas columnas existen en tu ContractInsertMuestras
            values.put(ContractInsertMuestras.Columnas.PHARMA_ID, id_pdv);
            values.put(ContractInsertMuestras.Columnas.CODIGO, codigo_pdv);
            values.put(ContractInsertMuestras.Columnas.USUARIO, user);
            values.put(ContractInsertMuestras.Columnas.SUPERVISOR, punto_venta);
            values.put(ContractInsertMuestras.Columnas.FECHA, fechaser);
            values.put(ContractInsertMuestras.Columnas.HORA, horaser);
            values.put(ContractInsertMuestras.Columnas.MARCA, marca);
            values.put(ContractInsertMuestras.Columnas.SKU, sku);
            values.put(ContractInsertMuestras.Columnas.TIPO_ACTIVIDAD, tipoActividad);
            values.put(ContractInsertMuestras.Columnas.CANTIDAD, cantidad);
         //   values.put(ContractInsertMuestras.Columnas.OBSERVACION, observacion); //
            values.put(ContractInsertMuestras.Columnas.POS_NAME, punto_venta);
        //    values.put(ContractInsertMuestras.Columnas.ESTADO, "PENDIENTE"); //
            values.put(ContractInsertMuestras.Columnas.FECHA, fechaser);
            values.put(ContractInsertMuestras.Columnas.HORA, horaser);
            values.put(Constantes.PENDIENTE_INSERCION, 1);

            getContext().getContentResolver().insert(ContractInsertMuestras.CONTENT_URI, values);

            if (VerificarNet.hayConexion(getContext())) {
                SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertMuestras, null);
                Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
            }

            // Toast.makeText(getContext(), "Muestra guardada exitosamente", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void insertData2(String skuSelected, String pregular, String ppromocion, String pmayorista, String precio_descuento, String c_descuento) {
        try{

            String image = "NO_FOTO";
//            if (img != null && img.getDrawable() != null) {

            //Bitmap temporal = ((BitmapDrawable) img.getDrawable()).getBitmap();

            //image = getStringImage(temporal);

            Log.i("csa",""+image);

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fechaser = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            ContentValues values = new ContentValues();

         //   String categoria = spCategoria.getSelectedItem().toString();
         //   String subcategoria = spSubcategoria.getSelectedItem().toString();
            String brand = spMarca.getSelectedItem().toString();
           String fabricante = spFabricante.getSelectedItem().toString(); //new

            /*String segment1 = handler.getSegment1Flooring(skuSelected);
            String segment2 = handler.getSegment2Flooring(skuSelected);
            String tamano = handler.getTamanoFlooring(skuSelected);
            String cantidad = handler.getCantidadFlooring(skuSelected);*/
           // String manufacturer = handler.getManufacturerSoloPrecios(categoria,subcategoria,brand,skuSelected);
        //    Log.i("MANUFACTURER L", manufacturer);
            String plataforma = handler.getPlataformaBySku(skuSelected);

            values.put(ContractInsertMuestras.Columnas.PHARMA_ID, id_pdv);
            values.put(ContractInsertMuestras.Columnas.CODIGO, codigo_pdv);
            values.put(ContractInsertMuestras.Columnas.USUARIO, user);
            values.put(ContractInsertMuestras.Columnas.SUPERVISOR, punto_venta);
            values.put(ContractInsertMuestras.Columnas.FECHA, fechaser);
            values.put(ContractInsertMuestras.Columnas.HORA, horaser);
//            values.put(ContractInsertMuestras.Columnas.CATEGORIA, categoria);
//            values.put(ContractInsertMuestras.Columnas.SUBCATEGORIA, subcategoria);
            values.put(ContractInsertMuestras.Columnas.MARCA, marca);
            values.put(ContractInsertMuestras.Columnas.SKU, brand);
            values.put(ContractInsertMuestras.Columnas.TIPO_ACTIVIDAD, fabricante);
            values.put(ContractInsertMuestras.Columnas.CANTIDAD, cantidad);
            values.put(ContractInsertMuestras.Columnas.POS_NAME, c_descuento);
//            Values.put(ContractInsertMuestras.Columnas.SKU_CODE, skuSelected);
//            values.put(ContractInsertMuestras.Columnas.PREGULAR, pregular);
//            values.put(ContractInsertMuestras.Columnas.PPROMOCION, ppromocion);
//            values.put(ContractInsertMuestras.Columnas.VAR_SUGERIDO, "");
//            values.put(ContractInsertMuestras.Columnas.POFERTA, pmayorista);
//           // values.put(ContractInsertMuestras.Columnas.MANUFACTURER, manufacturer);
//            values.put(ContractInsertMuestras.Columnas.POS_NAME, punto_venta);
//            values.put(ContractInsertMuestras.Columnas.PLATAFORMA, plataforma);
//            values.put(ContractInsertMuestras.Columnas.FOTO, image); //le cambie de "" mpin
            values.put(Constantes.PENDIENTE_INSERCION, 1);

            getContext().getContentResolver().insert(ContractInsertMuestras.CONTENT_URI, values);

            if (VerificarNet.hayConexion(getContext())) {
                SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertMuestras, null);
                Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
            }

            /*if (handler.SKUDuplicadoPrecios(skuSelected)) {
                handler.eliminarSKUDuplicadoPrecios(skuSelected);
            }

            ContentValues values_sesion = new ContentValues();
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.PHARMA_ID, id_pdv);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.CODIGO, codigo_pdv);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.USUARIO, user);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.SUPERVISOR, punto_venta);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.FECHA, fechaser);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.HORA, horaser);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.CANTIDAD_ASIGNADA, sector);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.CATEGORIA, logro);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.SEGMENTO, segment1);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.POFERTA, segment2);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.MARCA, brand);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.TAMANO, tamano);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.CANTIDAD, cantidad);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.SKU_CODE, skuSelected);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.FECHA_PROD, pregular);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.CANTIDAD_PROD, ppromocion);
            values_sesion.put(ContractInsertMuestrasSesion.Columnas.MANUFACTURER, manufacturer);
            values_sesion.put(Constantes.PENDIENTE_INSERCION, 1);
            getContentResolver().insert(ContractInsertMuestrasSesion.CONTENT_URI, values_sesion);*/

            v2 = false;
            v3 = false;
//            }else{
//                Toast.makeText(getContext(), "Debe tomar una Foto", Toast.LENGTH_SHORT).show();
//            }
        }catch (Exception e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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
        String outDate = dateFormat.format(date);

        txtfechav.setText(outDate);
    }

    //************METODOS PARA TAKE-PHOTO Y UPLOAD

    //Tomar Foto
   /* private boolean validaPermisos() {

        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }*/

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

    private void cargarImagen() {
        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    tomarFotografia();
                }else{
                    if (opciones[i].equals("Cargar Imagen")) {
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
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
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            String authorities=getContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getContext(), authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);
        //
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //OutOfMemoryError
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    img.setImageURI(miPath);
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

    //Permite hacer la imagen mas pequeña
    public void scaleImage(Bitmap bitmap) {
        try{
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

    @Override
    public void onClick(View v) {
//        if (v == btnPropia) {
//            tipo = "MARCA_PROPIA";
//            btnPropia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_selected));
//            btnPropia.setPadding(15, 15, 15, 15);
//            btnCompetencia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_unselected));
//            btnCompetencia.setPadding(15, 15, 15, 15);
//            filtrarCategoria(tipo, fabricante);
//        }
//        if (v == btnCompetencia) {
//            tipo = "COMPETENCIA";
//            btnPropia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_unselected));
//            btnPropia.setPadding(15, 15, 15, 15);
//            btnCompetencia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_selected));
//            btnCompetencia.setPadding(15, 15, 15, 15);
//            filtrarCategoria(tipo, fabricante);
//        }

            if (v == btnGuardar) {
                if (esFormularioValido()) {
                    mostrarMsg();
                    // Obtener valores de los spinners

                 //   limpiarDatos();
                }
            }



    }

    private void limpiarDatos() {
        // Restablecer spinners a posición inicial
        spMarca.setSelection(0);
        spSku.setSelection(0);
        spTipoActividad.setSelection(0);

        // Limpiar campos de texto
        if (txt_cantidad != null) {
            txt_cantidad.setText("");
        }

//        if (txtObservacion != null) {
//            txtObservacion.setText("");
//        }

       // Toast.makeText(getContext(), "Formulario limpiado", Toast.LENGTH_SHORT).show();
    }
    private boolean esFormularioValido() {

        // Si es -1 , no hay una posicion seleccionada
        if (spMarca.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar una marca", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spSku.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar un sku", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (spTipoActividad.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar un tipo de actividad", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_cantidad != null) {
            String cantidadStr = txt_cantidad.getText().toString().trim();
            if (cantidadStr.isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar la cantidad", Toast.LENGTH_SHORT).show();
                return false;
            }

            try {
                int cantidadInt = Integer.parseInt(cantidadStr);
                if (cantidadInt <= 0) {
                    Toast.makeText(getContext(), "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Cantidad inválida", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


//        if (spNiveles.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
//            Toast.makeText(getContext(), "Debe seleccionar un nivel/bandeja", Toast.LENGTH_SHORT).show();
//            return false;
//        }



        // CORRECCIÓN: Cambiar && por || (OR)
//        if (imageView == null || imageView.getDrawable() == null) {
//            Toast.makeText(getContext(), "Por favor debe tomar una foto", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }





//    public class CustomAdapterPrecios extends ArrayAdapter<Precio> implements Filterable {
//
//        public ArrayList<Precio> values;
//        public Context context;
//        boolean[] checkBoxState;
//
//        public CustomAdapterPrecios(Context context, ArrayList<Precio> values) {
//            super(context, 0, values);
//            this.values = values;
//            checkBoxState = new boolean[values.size()];
//        }
//
//        public class ViewHolder{
//            TextView lblSku;
//            TextView lblEstado;
//            Button checkGuardar; //agregado GT
//            //EditText txtunidad;
//            EditText txt_precio_regular;  //PVP
//            EditText txt_precio_promocion;   //PVC
//            EditText txt_monto_descuento;   // descuento //mpin
//            CheckBox chkPrecioDescuento; //mpin
//            EditText txt_precio_oferta;
//            EditText txt_precio_mayorista;
//            EditText txt_var_sugerido;
//            ImageButton btn;
//            ImageView imageView;
//            // Spinner spMotivo;
//        }
//
//        @Override
//        public int getViewTypeCount() {
//            // TODO Auto-generated method stub
//            return values.size();
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            // TODO Auto-generated method stub
//            return position;
//        }
//
//        /*@NonNull
//        @Override
//        public Filter getFilter() {
//            return precioFilter;
//        }
//
//        private Filter precioFilter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults results = new FilterResults();
//                List<Precio> suggestions = new ArrayList<>();
//
//                if (constraint == null || constraint.length() == 0) {
//                    suggestions.addAll(values);
//                } else {
//                    String filterPattern = constraint.toString().toLowerCase().trim();
//
//                    for (Precio item : values) {
//                        if (item.getSku_code().toLowerCase().contains(filterPattern)) {
//                            suggestions.add(item);
//                        }
//                    }
//                }
//                results.values = suggestions;
//                results.fabricante = suggestions.status();
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                clear();
//                addAll((List) results.values);
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public CharSequence convertResultToString(Object resultValue) {
//                return ((Precio) resultValue).getSku_code();
//            }
//        };*/
//
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            ViewHolder vHolder;
//
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.list_row_precios, parent, false);
//
//                vHolder = new ViewHolder();
//                vHolder.lblSku = convertView.findViewById(R.id.lblSku);
//                vHolder.checkGuardar = convertView.findViewById(R.id.checkGuardar);
//                vHolder.lblEstado = convertView.findViewById(R.id.lblEstado);
//                vHolder.txt_precio_regular = convertView.findViewById(R.id.txt_precio_regular);
//                vHolder.txt_precio_promocion = convertView.findViewById(R.id.txt_precio_promocion);
//                vHolder.txt_monto_descuento = convertView.findViewById(R.id.txt_monto_descuento);
//                vHolder.chkPrecioDescuento = convertView.findViewById(R.id.chkPrecioDescuento);
//                vHolder.txt_precio_oferta = convertView.findViewById(R.id.txt_precio_oferta);
//                vHolder.txt_precio_mayorista = convertView.findViewById(R.id.txt_precio_mayorista);
//                vHolder.btn = convertView.findViewById(R.id.ibCargarPrecios);
//
//                InputFilter filter = new InputFilter() {
//                    @Override
//                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                        for (int i = start; i < end; ++i) {
//                            if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890.]*").matcher(String.valueOf(source.charAt(i))).matches()) {
//                                return "";
//                            }
//                        }
//                        return null;
//                    }
//                };
//                vHolder.txt_precio_regular.setFilters(new InputFilter[]{new InputFilter() {
//                    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
//                    @Override
//                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                        int indexPoint = dest.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
//                        if (indexPoint == -1)
//                            return source;
//
//                        int decimals = dend - (indexPoint+1);
//                        return decimals < 2 ? source : "";
//                    }
//                }
//                });
//                vHolder.txt_precio_promocion.setFilters(new InputFilter[]{new InputFilter() {
//                    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
//                    @Override
//                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                        int indexPoint = dest.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
//                        if (indexPoint == -1)
//                            return source;
//
//                        int decimals = dend - (indexPoint+1);
//                        return decimals < 2 ? source : "";
//                    }
//                }
//                });
//                vHolder.txt_monto_descuento.setFilters(new InputFilter[]{new InputFilter() {
//                    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
//                    @Override
//                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                        int indexPoint = dest.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
//                        if (indexPoint == -1)
//                            return source;
//
//                        int decimals = dend - (indexPoint+1);
//                        return decimals < 2 ? source : "";
//                    }
//                }
//                });
//                vHolder.txt_precio_regular.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(5)});
//                vHolder.txt_precio_promocion.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(5)});
//                vHolder.txt_monto_descuento.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(5)});
//
//                convertView.setTag(vHolder);
//            } else {
//                vHolder = (ViewHolder) convertView.getTag();
//            }
//
//
//            if (values.size() > 0) {
//                vHolder.lblSku.setText(values.get(position).getSku_code());
////                vHolder.txt_precio_regular.setText(values.get(position).getPvp().trim());  //pvp
//                Log.i("LO QUE HAY EN VALUES", ""+values.toString());
////                vHolder.txt_precio_sugerido.setEnabled(false);
//
//                if(tipo == "COMPETENCIA"){
//                    //vHolder.txt_var_sugerido.setEnabled(false);
//                    //vHolder.txt_var_sugerido.setText("0%");
////                    vHolder.txt_precio_sugerido.setText("$0");
//                }
//
//                sesion = handler.getListGuardadoPrecios2(codigo_pdv);
//
//                for(int i = 0; i < sesion.size(); i++) {
//                    Log.i("ENTRA SESION","ENTRA SESION");
//                    if (values.get(position).getSku_code().equals(sesion.get(i).getSku_code())) {
//                        vHolder.lblEstado.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_revelado));
//                        vHolder.lblSku.setTypeface(null, Typeface.BOLD);
//                        vHolder.lblEstado.setText("REALIZADO");
//                    }else{
//                        Log.i("NO ENTRA","NO ENTRA");
//                    }
//                }
//}
//
//
//
//
//            // ================================
//            // ✅ CARGA DE DATOS
//            // ================================
//            vHolder.lblSku.setText(values.get(position).getSku_code());
//            vHolder.txt_precio_regular.setText(values.get(position).getPvp().trim());
//
//            // ================================
//            // ✅ RESTAURAR ESTADO REAL DEL CHECKBOX
//            // ================================
//            int pos = position;
//            vHolder.chkPrecioDescuento.setOnCheckedChangeListener(null);
//
//            boolean tieneDesc = values.get(pos).isTieneDescuento();
//            vHolder.chkPrecioDescuento.setChecked(tieneDesc);
//
//            vHolder.txt_monto_descuento.setEnabled(tieneDesc);
//            vHolder.txt_monto_descuento.setVisibility(tieneDesc ? View.VISIBLE : View.GONE);
//            vHolder.txt_monto_descuento.setText(values.get(pos).getDescuento());
//
//            vHolder.chkPrecioDescuento.setOnCheckedChangeListener((buttonView, isChecked) -> {
//
//                values.get(pos).setTieneDescuento(isChecked);
//
//                vHolder.txt_monto_descuento.setEnabled(isChecked);
//                vHolder.txt_monto_descuento.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//
//                if (!isChecked) {
//                    vHolder.txt_monto_descuento.setText("");
//                    values.get(pos).setDescuento("");
//                } else {
//                    vHolder.txt_monto_descuento.requestFocus();
//                }
//            });
//            // ================================
//            //  GUARDAR TEXTO DEL DESCUENTO
//            // ================================
//            vHolder.txt_monto_descuento.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void afterTextChanged(Editable s) {
//                    values.get(pos).setDescuento(s.toString());
//                }
//
//                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            });
//
//            // ================================
//            //  BOTÓN GUARDAR
//            // ================================
//            vHolder.checkGuardar.setOnClickListener(v -> {
//                String pregular = vHolder.txt_precio_regular.getText().toString().trim();
//                String ppromocion = vHolder.txt_precio_promocion.getText().toString().trim();
//                String sku = vHolder.lblSku.getText().toString();
//
//                boolean desc = vHolder.chkPrecioDescuento.isChecked();
//                String montoDesc = vHolder.txt_monto_descuento.getText().toString().trim();
//                String tieneDescStr = desc ? "SI" : "NO";
//
//                // Validar con esFormularioValidoFoto
//                if (esFormularioValidoFoto(pregular, ppromocion, desc, montoDesc)) {
//                    // Solo si pasa la validación, proceder a guardar
//                    insertData2(sku, pregular, ppromocion, pmayorista, montoDesc, tieneDescStr);
//
//                    // Opcional: Marcar como realizado después de guardar exitosamente
//                    vHolder.lblEstado.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_revelado));
//                    vHolder.lblSku.setTypeface(null, Typeface.BOLD);
//                    vHolder.lblEstado.setText("REALIZADO");
//
//                    // Actualizar la lista de sesión localmente para reflejar el cambio
//                    // (puedes agregar lógica adicional aquí si es necesario)
//                }
//                // Si no pasa la validación, el método esFormularioValidoFoto ya muestra Toast de error
//            });
//
//
//            return convertView;
//        }
//
//
//
//        private void openGallery() {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), COD_SELECCIONA);
//        }
//
//        private boolean esFormularioValido( String s_pvp, String s_pvc, String s_pvo, String var_pvs) {
//            if (s_pvp.trim().isEmpty()) {
//                Toast.makeText(getContext(), "Debe ingresar el PVP", Toast.LENGTH_LONG).show();
//                return false;
//            }
//            if (s_pvc.trim().isEmpty()) {
//                Toast.makeText(getContext(), "Debe ingresar el PVA", Toast.LENGTH_LONG).show();
//                return false;
//            }
//
//            if (tipo.equalsIgnoreCase("MARCA_PROPIA")){
//                if (var_pvs.trim().equals("") || var_pvs.trim().isEmpty()) {
//                    Toast.makeText(getContext(), "Debe ingresar VAR(%) SUGERIDO", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }
//
////            if (s_pvo.trim().isEmpty()) {
////                Toast.makeText(getContext(), "Debe ingresar el Precio Oferta", Toast.LENGTH_LONG).show();
////                return false;
////            }
//            double pvp = Double.parseDouble(s_pvp);
//            double pvc = Double.parseDouble(s_pvc);
////            double pvo = Double.parseDouble(s_pvo);
//            if (pvc < 0) {
//                Toast.makeText(getContext(), "El PVA no debe ser menor a 0", Toast.LENGTH_LONG).show();
//                return false;
//            }
//            if (pvp > 0 && pvc > pvp) {
//                Toast.makeText(getContext(), "El valor PVA excede al PVP", Toast.LENGTH_LONG).show();
//                return false;
//            }
////            if (pvo > pvc) {
////                Toast.makeText(getContext(), "El Precio Oferta no debe ser mayor al PVC", Toast.LENGTH_LONG).show();
////                return false;
////            }
//            return true;
//        }
//        private boolean esFormularioValidoFoto(String s_pvp, String s_pvc,
//                                               boolean tieneDescuento, String montoDescuento) {
//            if (s_pvp.trim().isEmpty()) {
//                Toast.makeText(getContext(), "Debe ingresar el PVP", Toast.LENGTH_LONG).show();
//                return false;
//            }
//            if (s_pvc.trim().isEmpty()) {
//                Toast.makeText(getContext(), "Debe ingresar el PVA", Toast.LENGTH_LONG).show();
//                return false;
//            }
//
//            /*if (tipo.equalsIgnoreCase("MARCA_PROPIA")){
//                if (var_pvs.trim().equals("") || var_pvs.trim().isEmpty()) {
//                    Toast.makeText(getContext(), "Debe ingresar VAR(%) SUGERIDO", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }*/
//
////            if (s_pvo.trim().isEmpty()) {
////                Toast.makeText(getContext(), "Debe ingresar el Precio Oferta", Toast.LENGTH_LONG).show();
////                return false;
////            }
//            double pvp = Double.parseDouble(s_pvp);
//            double pvc = Double.parseDouble(s_pvc);
////            double pvo = Double.parseDouble(s_pvo);
//            if (pvc < 0) {
//                Toast.makeText(getContext(), "El PVA no debe ser menor a 0", Toast.LENGTH_LONG).show();
//                return false;
//            }
//            if (pvp > 0 && pvc > pvp) {
//                Toast.makeText(getContext(), "El valor PVA excede al PVP", Toast.LENGTH_LONG).show();
//                return false;
//            }
// if (tieneDescuento) {
//                if (montoDescuento.isEmpty() || montoDescuento.equals("$")) {
//                    Toast.makeText(getContext(), "Ingrese el monto del descuento", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                try {
//                    // Remover $ si existe
//                    String montoLimpio = montoDescuento.startsWith("$") ?
//                            montoDescuento.substring(1) : montoDescuento;
//                    double descuento = Double.parseDouble(montoLimpio);
//
//                    if (descuento <= 0) {
//                        Toast.makeText(getContext(), "El descuento debe ser mayor a 0",
//                                Toast.LENGTH_LONG).show();
//                        return false;
//                    }
//
//
//                    if (descuento > pvp) {
//                        Toast.makeText(getContext(), "El descuento no puede ser mayor al PVP",
//                                Toast.LENGTH_LONG).show();
//                        return false;
//                    }
//
//                    // Validar que el precio con descuento sea menor al PVP
//                    double precioConDescuento = pvp - descuento;
//                    if (precioConDescuento < 0) {
//                        Toast.makeText(getContext(), "El precio con descuento no puede ser negativo",
//                                Toast.LENGTH_LONG).show();
//                        return false;
//                    }
//
//                } catch (NumberFormatException e) {
//                    Toast.makeText(getContext(), "Monto de descuento inválido",
//                            Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }
//
//            return true;
//        }
//
//    }


}