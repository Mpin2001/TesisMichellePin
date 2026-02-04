package com.tesis.michelle.pin.ui.novedades;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tesis.michelle.pin.CameraActivity;
import com.tesis.michelle.pin.Clase.Base_productos_novedades;
import com.tesis.michelle.pin.Clase.Ventas;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Conexion.VolleySingleton;
import com.tesis.michelle.pin.Contracts.ContractInsertNovedades;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.GuardarLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class NovedadesFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    ArrayList<Base_productos_novedades> listProductos;
    ArrayList<Base_productos_novedades> sesion;
    ListView listview;
    private Spinner spTipoNovedad;
    private TextView txtObservacion,empty;
    private LinearLayout llListView;
    private Spinner spMarca;
    private boolean lote = false;
    private boolean factura = false;
    private boolean sku = false;
    public static TextView lblEstadoFoto;
    private ImageButton btnCamara;
    private Button btnGuardar;
    private TextView tvRelevadas;
    private CheckBox chkStock;
    public static ImageView ivFotoGeneral,ivFotoProducto;
    private DatabaseHelper databaseHelper;
    private String punto_venta,fecha,hora,format,cuenta,canal,subcanal,id_pdv,user,codigo_pdv,tipo_foto;
    private final String fabricante = "BASSA";
    private String tipo = "MARCA_PROPIA";
    final int COD_SELECCIONA=10;
    private String marca;
    final int COD_FOTO=20;
    private LinearLayout llLote, llMarca, llProducto, llCantidad, llTipo, llFechaVenta, llFechaElaboracion;
    private LinearLayout llFotoFactura, llFotoLote, llFotoSku;
    private TextView txtInicioPromo, txtFecha, txtFechaElaboracion;

    private EditText txtLote, txtCantidad,  txtComentarioLote, txtComentarioFactura, txtComentarioSku, txt_factura, txt_mecanica, txt_precio_anterior, txt_precio_promocion;
    private Spinner spProducto, spTipo, spTipoImplementacion;

    private ImageButton btnCameraLote, btnCameraFactura, btnCameraSku;
    public static ImageButton ivFotoLote, ivFotoFactura, ivFotoSku, btnFecha, btnInicioPromo ,  btnFechaEntrega;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap bitmap;
    String path;

    private Bitmap bitmapfinal;

    CustomAdapterNovedades dataAdapter;
    private int contadorNovedades =0;
    private String fechaser, horaser;

    private boolean vaFoto;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_producto_caducado_novedades, container, false);

        LoadData();

        new GuardarLog(getContext()).saveLog(user, codigo_pdv, "Ingreso a Novedades");

        asignarReferencias(rootView);
      //  actualizarContador();
        filtrarTipoNovedad();
        filtrarMarcasDirecto(); // mpin


            // Filtrar productos para el spinner basado en la marca seleccionada
        //    filtrarProducto(marca, fabricante);





        // Configurar visibilidad inicial
//        ocultarTodosLosElementos();


        // Agregar los productos
//        cargarProductos(codigo_pdv);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ACTIVITY", "ON RESUME");
    }



//    private void cargarProductos(String codigo_pdv) {
//        listProductos = databaseHelper.filtrarListProductosNovedades(codigo_pdv);
//        dataAdapter = new CustomAdapterNovedades(getContext(), listProductos);
//        if(!dataAdapter.isEmpty()){
//            empty.setVisibility(View.INVISIBLE);
//            listview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, listProductos.size()*200));
//            llListView.setVisibility(View.VISIBLE);
//            listview.setVisibility(View.VISIBLE);
//
//            listview.setAdapter(dataAdapter);
//            //listview.setOnItemClickListener(this);
///*
//            txtSKUCode.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    ArrayList<Base_portafolio_productos> type_name_filter = new ArrayList<Base_portafolio_productos>();
//
//                    String text = s.toString();
//
//                    for (int i = 0; i < listProductos.size(); i++) {
//                        if ((listProductos.get(i).getSku().toLowerCase()).contains(text.toLowerCase())) {
//                            Base_portafolio_productos p = new Base_portafolio_productos();
//                            p.setSku(listProductos.get(i).getSku());
//                            type_name_filter.add(p);
//                        }
//                    }
//                    type_name_copy = type_name_filter;
//                    listUpdate(type_name_copy);
//                    //filter(s.toString());
//                    //dataAdapter.getFilter().filter(s);
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    //filter(s.toString());
//                }
//            });
//*/
//        }else{
//            llListView.setVisibility(View.GONE);
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
private void asignarReferencias(ViewGroup rootView) {
    databaseHelper = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

    // Spinner principal
    spTipoNovedad = rootView.findViewById(R.id.spTipoNovedad);

    // Elementos básicos
    txtObservacion = rootView.findViewById(R.id.txtObservacion);
    tvRelevadas = rootView.findViewById(R.id.tv_registradas);
    btnGuardar = rootView.findViewById(R.id.btnGuardar);
    llListView = rootView.findViewById(R.id.lSKU);
    listview = rootView.findViewById(R.id.lvSKUCode);
    empty = rootView.findViewById(R.id.recyclerview_data_empty);

    // Layouts principales
    llLote = rootView.findViewById(R.id.llLote);
    llMarca = rootView.findViewById(R.id.llMarca);
    llProducto = rootView.findViewById(R.id.llProducto);
    llCantidad = rootView.findViewById(R.id.llCantidad);
    llTipo = rootView.findViewById(R.id.llTipo);
    llFechaVenta = rootView.findViewById(R.id.llFechaVenta);
    llFechaElaboracion = rootView.findViewById(R.id.llFechaElaboracion);

    // LAYOUTS DE FOTOS
    llFotoLote = rootView.findViewById(R.id.llFotoLote);
    llFotoFactura = rootView.findViewById(R.id.llFotoFactura);
    llFotoSku = rootView.findViewById(R.id.llFotoSku);


    txtLote = rootView.findViewById(R.id.txt_lote);
    spMarca = rootView.findViewById(R.id.spMarca);
    spProducto = rootView.findViewById(R.id.spProducto);
    txtCantidad = rootView.findViewById(R.id.txt_cantidad);
    txt_factura = rootView.findViewById(R.id.txt_factura);
    txt_mecanica = rootView.findViewById(R.id.txt_mecanica);
    // txtInicioPromo = rootView.findViewById(R.id.txtInicioPromo);
    spTipo = rootView.findViewById(R.id.spTipo);
    spTipoImplementacion = rootView.findViewById(R.id.spTipoImplementacion); //new
    txtFecha = rootView.findViewById(R.id.txtFecha);
    txtFechaElaboracion = rootView.findViewById(R.id.txtFechaEntrega);
    txtInicioPromo  =   rootView.findViewById(R.id.txtInicioPromo); //NEW
    txt_precio_anterior  =   rootView.findViewById(R.id.txt_precio_anterior); //NEW
    txt_precio_promocion  =   rootView.findViewById(R.id.txt_precio_promocion); //NEW
    btnFecha = rootView.findViewById(R.id.btnFecha);
    btnFechaEntrega = rootView.findViewById(R.id.btnFechaEntrega); // new agrego
    btnInicioPromo = rootView.findViewById(R.id.btnInicioPromo); //NEW
    chkStock =  rootView.findViewById(R.id.chkStock); // NEW


    // Elementos de fotos
    btnCameraLote = rootView.findViewById(R.id.btnCameraLote);
    ivFotoLote = rootView.findViewById(R.id.ivFotoLote);
    txtComentarioLote = rootView.findViewById(R.id.txtComentarioLote);

    btnCameraFactura = rootView.findViewById(R.id.btnCameraFactura);
    ivFotoFactura = rootView.findViewById(R.id.ivFotoFactura);
    txtComentarioFactura = rootView.findViewById(R.id.txtComentarioFactura);

    btnCameraSku = rootView.findViewById(R.id.btnCameraSku);
    ivFotoSku = rootView.findViewById(R.id.ivFotoSku);
    txtComentarioSku = rootView.findViewById(R.id.txtComentarioSku);

    // Eventos
    btnCameraLote.setOnClickListener(this);
    btnCameraFactura.setOnClickListener(this);
    btnCameraSku.setOnClickListener(this);
    ivFotoLote.setOnClickListener(this);
    ivFotoFactura.setOnClickListener(this);
    ivFotoSku.setOnClickListener(this);
    btnGuardar.setOnClickListener(this);
    spMarca.setOnItemSelectedListener(this);
    btnFecha.setOnClickListener(this);
    btnFechaEntrega.setOnClickListener(this);
    btnInicioPromo.setOnClickListener(this); //NEW

    setupPriceInputFilter(txt_precio_anterior);
    setupPriceInputFilter(txt_precio_promocion);
    btnFechaEntrega.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mostrarSelectorFechaElaboracion();
        }
    });


}


    private void setupPriceInputFilter(EditText editText) {
        editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editText.addTextChangedListener(new android.text.TextWatcher() {
            private boolean isEditing = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (isEditing) return;
                isEditing = true;

                try {
                    String text = s.toString();

                    // 1. Reemplazar coma por punto
                    if (text.contains(",")) {
                        text = text.replace(',', '.');
                        s.replace(0, s.length(), text);
                    }

                    // 2. Validar formato
                    if (!text.isEmpty()) {
                        // Verificar si tiene punto decimal
                        if (text.contains(".")) {
                            String[] parts = text.split("\\.");

                            // Validar parte entera (máximo 3 dígitos)
                            if (parts[0].length() > 3) {
                                s.replace(0, s.length(), parts[0].substring(0, 3) + "." + (parts.length > 1 ? parts[1] : ""));
                                return;
                            }

                            // Validar parte decimal (máximo 2 dígitos)
                            if (parts.length > 1 && parts[1].length() > 2) {
                                s.replace(0, s.length(), parts[0] + "." + parts[1].substring(0, 2));
                            }
                        } else {
                            // Solo parte entera, validar máximo 3 dígitos
                            if (text.length() > 3) {
                                s.replace(0, s.length(), text.substring(0, 3));
                            }
                        }
                    }

                } finally {
                    isEditing = false;
                }
            }
        });
    }
    public void LoadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV,Constantes.NODATA);
        user=sharedPreferences.getString(Constantes.USER,Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        subcanal = sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
       cuenta = sharedPreferences.getString(Constantes.CUENTA,Constantes.NODATA);
 //       fabricante = sharedPreferences.getString(Constantes.FABRICANTE,Constantes.NODATA);
        vaFoto = sharedPreferences.getBoolean(Constantes.VAFOTO,false);


    }

    public void filtrarTipoNovedad(){
        List<String> operadores = databaseHelper.getTipoNovedades();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i("tn","entraa");
        Log.i("tn",""+ operadores);
        spTipoNovedad.setAdapter(dataAdapter);
        spTipoNovedad.setOnItemSelectedListener(this);
    }
    public void filtrarTipoImplementacion(){
        List<String> operadores = databaseHelper.getTipoImplementacion();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i("tn","entraa");
        Log.i("tn",""+ operadores);
        spTipoImplementacion.setAdapter(dataAdapter);
        spTipoImplementacion.setOnItemSelectedListener(this);
    }

    public void filtrarTipoUnidades(){
        List<String> operadores = databaseHelper.getTipoUnidades();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i("tn","entraa");
        Log.i("tn",""+ operadores);
        spTipo.setAdapter(dataAdapter);
        spTipo.setOnItemSelectedListener(this);
    }

    public void filtrarMarcasDirecto() {
        Log.d("MARCAS_DEBUG", "fabricante: " + fabricante);
        Log.d("MARCAS_DEBUG", "canal: " + canal);
        Log.d("MARCAS_DEBUG", "subcanal: " + subcanal);
        List<String> operadores = databaseHelper.getCategoriaEvidencia();
        Log.d("MARCAS_DEBUG", "Número de marcas obtenidas: " + operadores.size());
        Log.d("MARCAS_DEBUG", "Marcas: " + operadores.toString());
        if (operadores.size() == 2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void filtrarProducto(String marca, String fabricante, String tipo) {
        try {
            ArrayList<Base_productos_novedades> productos = databaseHelper.filtrarProductosPorMarcaNovedades(fabricante, marca, canal, subcanal);

            // Crear lista solo de SKUs para el spinner
            List<String> skuList = new ArrayList<>();
            skuList.add("Seleccione");

            for (Base_productos_novedades producto : productos) {
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

    @Override
        public void onClick(View view) {
        int viewId = view.getId();

        // Resetear todas las banderas primero
        lote = false;
        factura = false;
        sku = false;

        if(viewId == R.id.btnCameraLote){
            lote = true;
            tipo_foto = "novedades_foto_lote";
            cargarImagen(tipo_foto);
        }
        else if(viewId == R.id.btnCameraFactura) {
            factura = true;
            tipo_foto = "novedades_factura";
            cargarImagen(tipo_foto);
        }
        else if(viewId == R.id.btnCameraSku) {
            sku = true;
            tipo_foto = "novedades_foto_sku";
            cargarImagen(tipo_foto);
        }
        else if(viewId == R.id.ivFotoLote) {
            if (ivFotoLote.getDrawable() != null) {
                mostrarVistaPrevia(ivFotoLote.getDrawable());
            } else {
                Toast.makeText(getContext(), "No hay imagen para mostrar", Toast.LENGTH_SHORT).show();
            }
        }
        else if(viewId == R.id.ivFotoFactura) {
            if (ivFotoFactura.getDrawable() != null) {
                mostrarVistaPrevia(ivFotoFactura.getDrawable());
            } else {
                Toast.makeText(getContext(), "No hay imagen para mostrar", Toast.LENGTH_SHORT).show();
            }
        }
        else if(viewId == R.id.ivFotoSku) {
            if (ivFotoSku.getDrawable() != null) {
                mostrarVistaPrevia(ivFotoSku.getDrawable());
            } else {
                Toast.makeText(getContext(), "No hay imagen para mostrar", Toast.LENGTH_SHORT).show();
            }
        }
        else if (viewId == R.id.btnInicioPromo) {

            inicioPromo();
        }

        else if (viewId == R.id.btnFecha) {
            // SOLUCIÓN: Verificar el tipo de novedad actual
            String tipoNovedadActual = spTipoNovedad.getSelectedItem().toString();

            if (tipoNovedadActual.equals("PROMOCIONES NO AUTORIZADAS")) {
                // Solo para PROMOCIONES aplicar esta validación
                String iniPromo = txtInicioPromo.getText().toString();
                if (!iniPromo.trim().isEmpty()) {
                    mostrarSelectorFecha();
                } else {
                    Toast.makeText(getContext(), "Primero debe seleccionar un inicio de promoción", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Para otros tipos de novedad, mostrar el selector directamente
                mostrarSelectorFecha();
            }
        }


        else if(viewId == R.id.btnGuardar){
            if (esValidoFormulario()){
                mostrarMsg();
            }
        }

    }





    public void inicioPromo() {
        try {
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
                        date = dateFormat.parse(dayOfMonth + "/" + (month + 1) + "/" + year);
                        String outDate = dateFormat.format(date);
                        txtInicioPromo.setText(outDate);

                        if (!txtFecha.getText().toString().trim().isEmpty()) {
                            Log.i("ENTRA", "FIN PROMO NO VACIO");
                            String fInicio = txtInicioPromo.getText().toString();
                            String fFin = txtFecha.getText().toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date inicio = sdf.parse(fInicio);
                            Date fin = sdf.parse(fFin);

                            System.out.println("INI PROMO: " + inicio);
                            System.out.println("INI PROMO: " + fin);
                            if (inicio.after(fin)) { //esto pregunta si hoy es una fecha posterior a bd
                                txtFecha.setText("");
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, anio, mes, dia);
            from_dateListener.show();
        } catch (Exception e) {
            System.out.println("INI PROMO: " + e.getMessage());
        }
    }


    private void mostrarSelectorFecha() {
        try {
            final Calendar calendar = Calendar.getInstance();
            int anio = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog from_dateListener = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = dateFormat.parse(dayOfMonth + "/" + (month+1) + "/" + year);
                        String outDate = dateFormat.format(date);
                        txtFecha.setText(outDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error en formato de fecha", Toast.LENGTH_SHORT).show();
                    }
                }
            }, anio, mes, dia);

            // SOLO para PROMOCIONES establecer fecha mínima
            String tipoNovedadActual = spTipoNovedad.getSelectedItem().toString();
            if (tipoNovedadActual.equals("PROMOCIONES NO AUTORIZADAS")) {
                String iniPromo = txtInicioPromo.getText().toString();
                if (!iniPromo.trim().isEmpty()) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date minDate = formatter.parse(iniPromo);
                        from_dateListener.getDatePicker().setMinDate(minDate.getTime());
                    } catch (ParseException e) {
                        Log.e("FECHA", "Error fecha inicio promoción: " + e.getMessage());
                    }
                }
            }
            // Para otros tipos (PRODUCTO EN MAL ESTADO) no se establece fecha mínima

            from_dateListener.show();

        } catch (Exception e) {
            Log.e("FECHA_SELECTOR", "Error: " + e.getMessage());
            Toast.makeText(getContext(), "No se pudo abrir el calendario", Toast.LENGTH_SHORT).show();
        }
    }


    private void mostrarSelectorFechaElaboracion() {
        try {
            final Calendar calendar = Calendar.getInstance();
            int anio = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date date = dateFormat.parse(dayOfMonth + "/" + (month + 1) + "/" + year);
                                String outDate = dateFormat.format(date);
                                txtFechaElaboracion.setText(outDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error en formato de fecha", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, anio, mes, dia);

            datePickerDialog.show();
        } catch (Exception e) {
            Log.e("FECHA_ELABORACION", "Error: " + e.getMessage());
            Toast.makeText(getContext(), "No se pudo abrir el calendario", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarMsg() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Desea guardar la información? \n");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertarRegistros();
            }
        });

        builder.setNeutralButton("NO", null);

        android.app.AlertDialog ad = builder.create();
        ad.show();

    }

    private void obtenerFecha(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        fechaser = date.format(currentLocalTime);
        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        horaser = hour.format(currentLocalTime);
    }

    private void insertarRegistros() {
        try {
            obtenerFecha();
            ContentValues values = new ContentValues();

            // Obtener valores básicos
            String tipoNovedad = spTipoNovedad.getSelectedItem().toString();
           String observacion = txtObservacion.getText().toString();

            // Obtener valores de campos específicos
            String lote = txtLote.getText().toString();
            String marca = spMarca.getSelectedItem().toString();
        //    String producto = spProducto.getSelectedItem().toString();
            String cantidad = txtCantidad.getText().toString();
            String factura = txt_factura.getText().toString();
            String mecanica = txt_mecanica.getText().toString(); //new
          //  String tipoProducto = spTipo.getSelectedItem().toString();
            String fechaVenta = txtFecha.getText().toString();
            String fecha_inicio = txtInicioPromo.getText().toString(); //new
            String fechaElaboracion = txtFechaElaboracion.getText().toString(); /// NUEVOOOOO
            String stock;
            if (chkStock.isChecked()) {
                stock = "SI";
            }else{
                stock = "NO";
            }
            String precio_anterior = txt_precio_anterior.getText().toString();//new
            String precio_promocion = txt_precio_promocion.getText().toString(); //new

            String tipo1 = spTipo.getSelectedItem().toString();
            String tipoImplementacion = "N/A"; // Valor por defecto para otros casos

            if (tipoNovedad.equals("BLOQUEO DE SKU")) {
                // Solo en este caso obtener el valor del spinner
                if (spTipoImplementacion.getSelectedItem() != null) {
                    tipoImplementacion = spTipoImplementacion.getSelectedItem().toString();
                    if (tipoImplementacion.equals("Seleccione")) {
                        tipoImplementacion = "N/A";
                    }
                }
            }


            // Obtener comentarios de fotos
            String comentarioLote = txtComentarioLote.getText().toString();
            String comentarioFactura = txtComentarioFactura.getText().toString();
            String comentarioSku = txtComentarioSku.getText().toString();

            // Procesar imágenes
            String imageLote = "NO_FOTO";
            String imageFactura = "NO_FOTO";
            String imageSku = "NO_FOTO";

            // VERIFICAR FOTO LOTE
            if (ivFotoLote.getDrawable() != null && esFotoTomada(ivFotoLote.getDrawable())) {
                try {
                    Bitmap bitmapLote = ((BitmapDrawable) ivFotoLote.getDrawable()).getBitmap();
                    int mheight = (int) (bitmapLote.getHeight() * (1024.0 / bitmapLote.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapLote, 1024, mheight, true);
                    imageLote = getStringImage(scaled);
                } catch (ClassCastException e) {
                    // Si es VectorDrawable (ícono por defecto), usar "NO_FOTO"
                    imageLote = "NO_FOTO";
                    Log.d("NovedadesFragment", "Foto Lote: Usando ícono por defecto, NO_FOTO");
                }
            } else {
                imageLote = "NO_FOTO";
            }

            // VERIFICAR FOTO FACTURA
            if (ivFotoFactura.getDrawable() != null && esFotoTomada(ivFotoFactura.getDrawable())) {
                try {
                    Bitmap bitmapFactura = ((BitmapDrawable) ivFotoFactura.getDrawable()).getBitmap();
                    int mheight = (int) (bitmapFactura.getHeight() * (1024.0 / bitmapFactura.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapFactura, 1024, mheight, true);
                    imageFactura = getStringImage(scaled);
                } catch (ClassCastException e) {
                    imageFactura = "NO_FOTO";
                    Log.d("NovedadesFragment", "Foto Factura: Usando ícono por defecto, NO_FOTO");
                }
            } else {
                imageFactura = "NO_FOTO";
            }

            // VERIFICAR FOTO SKU
            if (ivFotoSku.getDrawable() != null && esFotoTomada(ivFotoSku.getDrawable())) {
                try {
                    Bitmap bitmapSku = ((BitmapDrawable) ivFotoSku.getDrawable()).getBitmap();
                    int mheight = (int) (bitmapSku.getHeight() * (1024.0 / bitmapSku.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapSku, 1024, mheight, true);
                    imageSku = getStringImage(scaled);
                } catch (ClassCastException e) {
                    imageSku = "NO_FOTO";
                    Log.d("NovedadesFragment", "Foto SKU: Usando ícono por defecto, NO_FOTO");
                }
            } else {
                imageSku = "NO_FOTO";
            }

            // Lógica similar a insertData2
            // Por ejemplo, si no hay producto seleccionado, poner valores por defecto
            // Procesar valores según el tipo de novedad
            switch (tipoNovedad) {
                case "PRODUCTO CADUCADO":
                    // Para caducados, todos los campos son requeridos
//                    if (producto.equals("Seleccione"))
//                        producto = "N/A";
//                    if (tipo1.equals("Seleccione"))
//                        tipo1 = "N/A";
//                    if (marca.equals("Seleccione"))
//                        marca = "N/A";
                    // lote, cantidad, fechaVenta ya tienen valor o están vacíos

                    fechaVenta = "N/A"; // No aplica para caducados
                    mecanica = "N/A"; // No aplica para caducados
                    factura = "N/A"; // No aplica para caducados
                    precio_promocion = "N/A"; // No aplica para caducados
                    precio_anterior = "N/A"; // No aplica para caducados
                    stock = "N/A"; // No aplica para caducados
                    fecha_inicio = "N/A"; // No aplica para caducados
                    observacion = "N/A"; // No aplica para caducados
                    if (tipoImplementacion.equals("Seleccione")) tipoImplementacion = "N/A";
                    break;

                case "PRODUCTO EN MAL ESTADO":
                    // Similar a caducados pero con factura
                    fechaElaboracion= "N/A";
                    mecanica= "N/A";
                    stock = "N/A";
                   // if (producto.equals("Seleccione")) producto = "N/A";
                    if (tipo1.equals("Seleccione")) tipo1 = "N/A";
                    if (marca.equals("Seleccione")) marca = "N/A";
                    if (tipoImplementacion.equals("Seleccione")) tipoImplementacion = "N/A";
                    observacion = "N/A"; // No aplica para caducados
                    break;

                case "OTROS":
                    // Para OTROS, solo observación es requerido, los demás son "N/A"
                   // producto = "N/A";
                    fecha_inicio = "N/A";
                    mecanica = "N/A";
                    cantidad = "N/A";
                    tipo1 = "N/A";
                    marca = "N/A";
                    lote = "N/A";
                    factura = "N/A";
                    precio_promocion = "N/A"; // No aplica para caducados
                    precio_anterior = "N/A"; // No aplica para caducados

                    stock = "N/A";
                    fechaVenta = "N/A";
                    if (tipoImplementacion.equals("Seleccione"))
                        tipoImplementacion = "N/A";
                    break;
                case "BLOQUEO DE SKU":
                    // Para OTROS, solo observación es requerido, los demás son "N/A"
                  //  producto = "N/A";
                    cantidad = "N/A";
                    tipo1 = "N/A";
                    stock = "N/A";
                    fecha_inicio = "N/A";
                    mecanica = "N/A";
                    marca = "N/A";
                    lote = "N/A";
                    factura = "N/A";
                    fechaVenta = "N/A";
                    precio_anterior = "N/A";
                    precio_promocion = "N/A";
                    break;
                case "PROMOCIONES NO AUTORIZADAS":
                    // Para OTROS, solo observación es requerido, los demás son "N/A"
                    //producto = "N/A";
                    cantidad = "N/A";
                    if (tipo1.equals("Seleccione")) tipo1 = "N/A";
                   // marca = "N/A";
                   lote = "N/A";
                    factura = "N/A";
                    if (tipoImplementacion.equals("Seleccione")) tipoImplementacion = "N/A";
//                    imageLote = "NO_FOTO";
//                    imageSku = "NO_FOTO";

                    break;
            }


            values.put(ContractInsertNovedades.Columnas.PHARMA_ID, id_pdv);
            values.put(ContractInsertNovedades.Columnas.CODIGO, codigo_pdv);
            values.put(ContractInsertNovedades.Columnas.USUARIO, user);
            values.put(ContractInsertNovedades.Columnas.SUPERVISOR, punto_venta);
            values.put(ContractInsertNovedades.Columnas.POS_NAME, punto_venta);
            values.put(ContractInsertNovedades.Columnas.TIPO_NOVEDAD, tipoNovedad);
            values.put(ContractInsertNovedades.Columnas.MARCA, marca);
            values.put(ContractInsertNovedades.Columnas.LOTE, lote);
           // values.put(ContractInsertNovedades.Columnas.SKU, producto);
           values.put(ContractInsertNovedades.Columnas.TIPO, tipo1);
          values.put(ContractInsertNovedades.Columnas.TIPO_IMPLEMENTACION, tipoImplementacion);
          values.put(ContractInsertNovedades.Columnas.MECANICA, mecanica);
          values.put(ContractInsertNovedades.Columnas.FECHA_INICIO, fecha_inicio);
          values.put(ContractInsertNovedades.Columnas.AGOTAR_STOCK, stock);
          values.put(ContractInsertNovedades.Columnas.PRECIO_ANTERIOR, precio_anterior);
          values.put(ContractInsertNovedades.Columnas.PRECIO_PROMOCION, precio_promocion);
            values.put(ContractInsertNovedades.Columnas.FECHA_VENCIMIENTO, fechaVenta);
            values.put(ContractInsertNovedades.Columnas.FECHA_ELABORACION, fechaElaboracion);
            values.put(ContractInsertNovedades.Columnas.NUMERO_FACTURA, factura);
            values.put(ContractInsertNovedades.Columnas.COMENTARIO_LOTE, comentarioLote);
            values.put(ContractInsertNovedades.Columnas.COMENTARIO_FACTURA, comentarioFactura);
            values.put(ContractInsertNovedades.Columnas.COMENTARIO_SKU, comentarioSku);
           values.put(ContractInsertNovedades.Columnas.OBSERVACION, observacion);
            values.put(ContractInsertNovedades.Columnas.FOTO, imageLote);
//            values.put(ContractInsertNovedades.Columnas.SKU_CODE,producto);
           values.put(ContractInsertNovedades.Columnas.CANTIDAD, cantidad);
            values.put(ContractInsertNovedades.Columnas.FOTO_PRODUCTO, imageSku);
            values.put(ContractInsertNovedades.Columnas.FOTO_FACTURA, imageFactura);
            values.put(ContractInsertNovedades.Columnas.FECHA, fechaser);
            values.put(ContractInsertNovedades.Columnas.HORA, horaser);
//                        values.put(ContractInsertImpulso.Columnas.CUENTA, cuenta);
            values.put(Constantes.PENDIENTE_INSERCION, 1);

            getContext().getContentResolver().insert(ContractInsertNovedades.CONTENT_URI, values);

            if (VerificarNet.hayConexion(getContext())) {
                SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertNovedades, null);
                Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
            }

            vaciarCamposAlGuardar();
            //actualizarContador();

//                for(int i = 0 ;i < listview.getChildCount() ; i++){
//                    View v = listview.getChildAt(i);
//                    TextView lblSku = (TextView) v.findViewById(R.id.lblSku);
//                    EditText txt_cantidad = (EditText) v.findViewById(R.id.txt_cantidad);
//                    ImageView ivFotoProducto = (ImageView) v.findViewById(R.id.ivFotoProducto);
//                    String producto = lblSku.getText().toString().trim();
//                    String cantidad = txt_cantidad.getText().toString().trim();
//
//                    Log.i("4444"," " + producto + " " + cantidad);
//                    if (!cantidad.isEmpty()){
//                      //  String producto = lblSku.getText().toString();
//                        String imageProducto = "NO_FOTO";
//
//
//                        if (ivFotoProducto.getDrawable() != null && ivFotoProducto != null){
//                            Bitmap bitmapProducto = ((BitmapDrawable)ivFotoProducto.getDrawable()).getBitmap();
//                            imageProducto = getStringImage(bitmapProducto);
//                        }
//
//
//
//                    }
//                }


        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }





    }

    private boolean esFotoTomada(Drawable drawable) {
        if (drawable == null) {
            return false;
        }
        // Si es un VectorDrawable, es el ícono por defecto (no una foto tomada)
        return !(drawable instanceof VectorDrawable);
    }

    private void actualizarContador(){
        Log.i("entro acty","si");
        obtenerFecha();
        contadorNovedades = databaseHelper.contadorNovedades(codigo_pdv,user,fechaser);

        //si no hay nada en base local verifica en el servidor
        if(contadorNovedades == 0){
            if(VerificarNet.hayConexion(getContext())){
                consultarNovedadesServidor(codigo_pdv, user, fechaser, new NovedadesCallback() {
                    @Override
                    public void onResult(int contador) {
                        Log.d("Novedades", "Total novedades: " + contador);
                        contadorNovedades = contador;
                    //    actualizarTextContador();
                    }

                    @Override
                    public void onError(String mensaje) {
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }

    //    actualizarTextContador();

    }

//    public void actualizarTextContador(){
//        tvRelevadas.setText("Usted tiene "+ contadorNovedades +"/5 novedades registradas");
//
//        if(contadorNovedades<5){
//            btnGuardar.setBackgroundResource(R.drawable.button_active);
//        } else {
//            btnGuardar.setBackgroundResource(R.drawable.button_background_desactive);
//        }
//    }

    public interface NovedadesCallback {
        void onResult(int contador);
        void onError(String mensaje);
    }


    public void consultarNovedadesServidor(String codigo_pdv, String user, String fechaser, final NovedadesCallback callback) {
        try {
            JSONObject jobject = new JSONObject();
            jobject.put("codigo_pdv", codigo_pdv);
            jobject.put("user", user);
            jobject.put("fecha", fechaser);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constantes.CONSULTAR_NOVEDADES_DIARIO, jobject,
                    response -> {
                        try {
                            if ("1".equals(response.getString("estado"))) {
                                JSONArray relevadas = response.getJSONArray("relevadas");
                                Log.i("respuesta rle",relevadas+"");
                                JSONObject item = relevadas.getJSONObject(0);
                                int contadorNovedades = item.getInt("contador");
                                Log.i("respuesta rle2",contadorNovedades+"");
                                callback.onResult(contadorNovedades);
                            } else {
                                callback.onResult(0); // o manejar según el estado
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error al procesar la respuesta");
                        }
                    },
                    error -> {
                        int msg = (error instanceof TimeoutError) ? R.string.timeout :
                                (error instanceof NoConnectionError) ? R.string.no_connection :
                                        (error instanceof ServerError) ? R.string.server_error :
                                                (error instanceof NetworkError) ? R.string.network_error : 0;
                        String mensaje = (msg != 0) ? getContext().getString(msg) : "Error desconocido";
                        callback.onError(mensaje);
                    });

            request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(request);

        } catch (Exception e) {
            callback.onError("Excepción: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void vaciarCampos() {
        try {
            // NO limpiar spTipoNovedad - mantener la selección actual
            // if (spTipoNovedad != null) spTipoNovedad.setSelection(0);

            // Limpiar otros spinners
            if (spMarca != null) spMarca.setSelection(0);
            if (spProducto != null) spProducto.setSelection(0);
            if (spTipo != null) spTipo.setSelection(0);
            if (spTipoImplementacion != null) spTipoImplementacion.setSelection(0);

            // Limpiar textos
            if (txtObservacion != null) txtObservacion.setText("");
            if (txtInicioPromo != null) txtInicioPromo.setText("");
            if (txt_mecanica != null) txt_mecanica.setText("");
            if (chkStock != null) chkStock.setChecked(false);
            if (txt_precio_anterior != null) txt_precio_anterior.setText("");
            if (txtFechaElaboracion != null)
                txtFechaElaboracion.setText("");
            if (txt_precio_promocion != null) txt_precio_promocion.setText("");
            if (txtLote != null) txtLote.setText("");
            if (txtCantidad != null) txtCantidad.setText("");
            if (txt_factura != null) txt_factura.setText("");
            if (txtFecha != null) txtFecha.setText("");
            if (txtComentarioLote != null) txtComentarioLote.setText("");
            if (txtComentarioFactura != null) txtComentarioFactura.setText("");
            if (txtComentarioSku != null) txtComentarioSku.setText("");

            // Limpiar imágenes (restaurar íconos por defecto)
            if (ivFotoLote != null) ivFotoLote.setImageResource(R.drawable.ic_baseline_preview_24);
            if (ivFotoFactura != null) ivFotoFactura.setImageResource(R.drawable.ic_baseline_preview_24);
            if (ivFotoSku != null) ivFotoSku.setImageResource(R.drawable.ic_baseline_preview_24);

            // Quitar el Toast para que no aparezca cuando solo cambias de tipo
            // Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("NovedadesFragment", "Error al vaciar campos: " + e.getMessage());
        }
    }

    private void vaciarCamposAlGuardar() {
        try {
            // NO limpiar spTipoNovedad - mantener la selección actual
             if (spTipoNovedad != null) spTipoNovedad.setSelection(0);

            // Limpiar otros spinners
            if (spMarca != null) spMarca.setSelection(0);
            if (spProducto != null) spProducto.setSelection(0);
            if (spTipo != null) spTipo.setSelection(0);
            if (spTipoImplementacion != null) spTipoImplementacion.setSelection(0);

            // Limpiar textos
            if (txtObservacion != null) txtObservacion.setText("");
            if (txtInicioPromo != null) txtInicioPromo.setText("");
            if (txt_mecanica != null) txt_mecanica.setText("");
            if (chkStock != null) chkStock.setChecked(false);
            if (txt_precio_anterior != null) txt_precio_anterior.setText("");
            if (txt_precio_promocion != null) txt_precio_promocion.setText("");
            if (txtLote != null) txtLote.setText("");
            if (txtCantidad != null) txtCantidad.setText("");
            if (txt_factura != null) txt_factura.setText("");
            if (txtFecha != null) txtFecha.setText("");
            if (txtComentarioLote != null) txtComentarioLote.setText("");
            if (txtComentarioFactura != null) txtComentarioFactura.setText("");
            if (txtComentarioSku != null) txtComentarioSku.setText("");

            // Limpiar imágenes (restaurar íconos por defecto)
            if (ivFotoLote != null) ivFotoLote.setImageResource(R.drawable.ic_baseline_preview_24);
            if (ivFotoFactura != null) ivFotoFactura.setImageResource(R.drawable.ic_baseline_preview_24);
            if (ivFotoSku != null) ivFotoSku.setImageResource(R.drawable.ic_baseline_preview_24);

            // Quitar el Toast para que no aparezca cuando solo cambias de tipo
            // Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("NovedadesFragment", "Error al vaciar campos: " + e.getMessage());
        }
    }

    private boolean esValidoFormulario() {
        String tipoNovedad = spTipoNovedad.getSelectedItem().toString();

        if (tipoNovedad.equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe escoger el tipo de novedad", Toast.LENGTH_LONG).show();
            return false;
        }



//        if (txtObservacion.getText().toString().trim().isEmpty()) {
//            Toast.makeText(getContext(), "La observación no puede estar vacía", Toast.LENGTH_LONG).show();
//            return false;
//        }

        // Validaciones especificas según el tipo de novedad
        switch (tipoNovedad) {
            case "Cortes de servicios":
                if (txtLote.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "El lote no puede estar vacío", Toast.LENGTH_LONG).show();
                    return false;
                }
//                if (spMarca.getSelectedItem().toString().equals("Seleccione")) {
//                    Toast.makeText(getContext(), "Debe seleccionar una marca", Toast.LENGTH_LONG).show();
//                    return false;
//                }
                if (spProducto.getSelectedItem().toString().equals("Seleccione")) {
                    Toast.makeText(getContext(), "Debe seleccionar un sku", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (txtCantidad.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "La cantidad no puede estar vacía", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (spTipo.getSelectedItem().toString().equals("Seleccione")) {
                    Toast.makeText(getContext(), "Debe escoger un tipo de sku ", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (txtFechaElaboracion.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Debe escoger la fecha de elaboración", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (!esFotoTomada(ivFotoLote.getDrawable())) {
                    Toast.makeText(getContext(),"No tomó la foto del Lote",Toast.LENGTH_LONG).show();
                    return false;
                }
//                if (!esFotoTomada(ivFotoFactura.getDrawable())) {
//                    Toast.makeText(getContext(),"No tomó la foto de la factura",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (!esFotoTomada(ivFotoSku.getDrawable())) {
//                    Toast.makeText(getContext(),"No tomó la foto del Sku ",Toast.LENGTH_LONG).show();
//                    return false;
//                }


                break;

            case "Luz pública dañada":
//                if (txtLote.getText().toString().trim().isEmpty()) {
////                    Toast.makeText(getContext(), "El lote no puede estar vacío", Toast.LENGTH_LONG).show();
////                    return false;
//                }
//                if (spMarca.getSelectedItem().toString().equals("Seleccione")) {
//                    Toast.makeText(getContext(), "Debe seleccionar una marca", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (spProducto.getSelectedItem().toString().equals("Seleccione")) {
//                    Toast.makeText(getContext(), "Debe seleccionar un sku", Toast.LENGTH_LONG).show();
//                    return false;
//                }
                if (txtCantidad.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "La cantidad no puede estar vacía", Toast.LENGTH_LONG).show();
                    return false;
                }
//                if (spTipo.getSelectedItem().toString().equals("Seleccione")) {
//                    Toast.makeText(getContext(), "Debe escoger un tipo de sku ", Toast.LENGTH_LONG).show();
//                    return false;
//                }
                if (txtFecha.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Debe escoger una fecha ", Toast.LENGTH_LONG).show();
                    return false;
                }
//              //  if (txt_factura.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getContext(), "Debe ingresar el numero de factura", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (!esFotoTomada(ivFotoLote.getDrawable())) {
//                    Toast.makeText(getContext(),"No tomó la foto del Lote",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (!esFotoTomada(ivFotoFactura.getDrawable())) {
//                    Toast.makeText(getContext(),"No tomó la foto de la factura",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (!esFotoTomada(ivFotoSku.getDrawable())) {
//                    Toast.makeText(getContext(),"No tomó la foto del Sku ",Toast.LENGTH_LONG).show();
//                    return false;
//                }
                break;

                case "OTROS":
                if (txtObservacion.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "El comentario no puede estar vacío", Toast.LENGTH_LONG).show();
                return false;
                }
//                if (!esFotoTomada(ivFotoLote.getDrawable())) {
//                    Toast.makeText(getContext(),"No tomó la foto del Lote",Toast.LENGTH_LONG).show();
//                    return false;
//                }
                break;
            case "BLOQUEO DE SKU":
                if (spTipoImplementacion.getSelectedItem().toString().equals("Seleccione")) {
                    Toast.makeText(getContext(), "Debe escoger un tipo de implementacion ", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (txtObservacion.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "El comentario no puede estar vacío", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (!esFotoTomada(ivFotoLote.getDrawable())) {
                    Toast.makeText(getContext(),"Debe tomar la primera foto",Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            case "PROMOCIONES NO AUTORIZADAS":
                if (txt_mecanica.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Debe ingresar una mecanica", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (txtInicioPromo.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Debe escoger una fecha inicio", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (txtFecha.getText().toString().trim().isEmpty()) {

                    Toast.makeText(getContext(), "Debe escoger una fecha de vencimiento", Toast.LENGTH_LONG).show();
                    return false;
                }
//                if (!chkStock.isChecked()) {
//                    Toast.makeText(getContext(), "Debe ", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (spMarca.getSelectedItem().toString().equals("Seleccione")) {
//                    Toast.makeText(getContext(), "Debe seleccionar una marca", Toast.LENGTH_LONG).show();
//                    return false;
//                }
                if (spProducto.getSelectedItem().toString().equals("Seleccione")) {
                    Toast.makeText(getContext(), "Debe seleccionar un sku", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (txtObservacion.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "El comentario no puede estar vacío", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (txt_precio_anterior.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Ingrese el precio anterior", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (txt_precio_promocion.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Ingree el precio de la promocion", Toast.LENGTH_LONG).show();
                    return false;
                }
//                if (!esFotoTomada(ivFotoFactura.getDrawable())) {
//                    Toast.makeText(getContext(),"Tome una foto de la promoción",Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (!esFotoTomada(ivFotoLote.getDrawable())) {
//                    Toast.makeText(getContext(),"No tomó la foto del Lote",Toast.LENGTH_LONG).show();
//                    return false;
//                }
                break;

        }

        return true;
    }


//    private void cargarImagen(String tipo) {
//
//        final CharSequence[] opciones;
//        if (cuenta.equalsIgnoreCase("CLARO")) {
//            opciones = new CharSequence[]{"Tomar Foto", "Cancelar"};
//        } else {
//            opciones = new CharSequence[]{"Tomar Foto", "Cargar Imagen", "Cancelar"};
//        }
//        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
//        alertOpciones.setTitle("Seleccione una Opción");
//        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (opciones[i].equals("Tomar Foto")){
//                    Intent n = new Intent(getContext(), CameraActivity.class);
//                    n.putExtra("activity", tipo);
//                    startActivity(n);
//                    // tomarFotografia();
//                }else{
//                    if (opciones[i].equals("Cargar Imagen")){
//                        /* Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        intent.setType("image_antes/");
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

    private void cargarImagen(String tipo_foto) {
        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final android.app.AlertDialog.Builder alertOpciones=new android.app.AlertDialog.Builder(getContext());
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

    //Metodo que sube la imagen al servidor
    public String getStringImage(Bitmap bmp){
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Comprime la Imagen tipo, calidad y outputstream
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                   /* Uri miPath=data.getData();
                    imageViewAntes.setImageURI(miPath);*/
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


    //Permite hacer la imagen mas pequeña para mostrarla en el ImageView
        public void scaleImage(Bitmap bitmap){
        try{
            int mheight = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, mheight, true);

            if (lote) {
                ivFotoLote.setImageBitmap(scaled);
            } else if (factura) {
                ivFotoFactura.setImageBitmap(scaled);
            } else if (sku) {
                ivFotoSku.setImageBitmap(scaled);
            }
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on compress file: " + e.getMessage());
            Toast.makeText(getContext(), "Error al procesar imagen", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
    //vaciarCampos();
        if (adapterView.getId() == R.id.spTipoNovedad) {
            String selectedOption = adapterView.getItemAtPosition(position).toString();
            vaciarCampos();
            mostrarElementosSegunTipoNovedad(selectedOption);
            // Limpiar spinner de implementación cuando no es BLOQUEO DE SKU
//            if (!selectedOption.equals("BLOQUEO DE SKU")) {
//                limpiarSpinnerImplementacion();
//            }
        }
        else if (adapterView == spMarca) {
            try {
                marca = adapterView.getItemAtPosition(position).toString();
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
           // vaciarCampos();
        }

    }

    private void limpiarSpinnerImplementacion() {
        List<String> emptyList = new ArrayList<>();
        emptyList.add("Seleccione");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, emptyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoImplementacion.setAdapter(dataAdapter);
    }


    private void mostrarElementosSegunTipoNovedad(String tipoNovedad) {
        // Ocultar todos los elementos primero
        ocultarTodosLosElementos();

        // Mostrar elementos según el tipo de novedad
        switch (tipoNovedad) {
            case "Cortes de servicios":
                mostrarElementosCortesdeservicios();

                break;
            case "Luz pública dañada":
                mostrarElementosLuzpublicadaada();
                break;
            case "Personas sospechosas en el sector":
                mostrarElementosSospechosos();
                break;
            case "Ruido excesivo / escándalo": //PARA PREUBAS, YA QUE SERA USADO PARA MOSTRAR 'IMPLEMENTACIONES'
                mostrarElementosescandalo();
                break;
            // Agrega más casos según tus necesidades
            case "Consumo de drogas en espacios públicos":
                promocionesNoAutorizadas();
                break;
        }
    }

    private void ocultarTodosLosElementos() {
        // Ocultar todos los layouts que pueden mostrarse
        int[] layoutsToHide = {
                R.id.llLote, R.id.llMarca, R.id.llProducto, R.id.llCantidad,
                R.id.llTipo, R.id.llFechaVenta, R.id.llFotoFactura, R.id.llFechaElaboracion,
                R.id.llFotoLote, R.id.llFotoSku, R.id.llObservacion,  R.id.llTipoImplementacion,
                R.id.llMecanica,R.id.llFechaInicio, R.id.llStock, R.id.llPrecioAnterior, R.id.llPrecioPromocion,
                // Labels y textos
                R.id.lSKU,  R.id.llFactura,   R.id.lblfactura,  R.id.txt_factura, R.id.lblFotoLote
        };

        for (int layoutId : layoutsToHide) {
            View layout = getView().findViewById(layoutId);
            if (layout != null) {
                layout.setVisibility(View.GONE);
            }
        }
    }

    private void mostrarElementosCortesdeservicios() {
        // Mostrar elementos  para caducados
        mostrarLayout(R.id.llProducto);
        mostrarLayout(R.id.llCantidad);
        mostrarLayout(R.id.llLote);
        mostrarLayout(R.id.llTipo);
       // mostrarLayout(R.id.llFechaVenta);
        mostrarLayout(R.id.llFechaElaboracion);
        mostrarLayout(R.id.btnFechaEntrega);
        mostrarLayout(R.id.llMarca);
        mostrarLayout(R.id.llFotoLote); // Foto SKU
       // mostrarLayout(R.id.llFotoFactura); // Foto SKU
        mostrarLayout(R.id.llFotoSku); // Foto SKU
        mostrarLayout(R.id.llFotoSku); // Foto SKU
        mostrarLayout(R.id.lblFotoLote); // Foto SKU
        mostrarLayout(R.id.btnCameraLote);
        mostrarLayout(R.id.lblFotoFactura);
        mostrarLayout(R.id.lblFotoSku);
        mostrarLayout(R.id.txtComentarioLote);
        mostrarLayout(R.id.txtComentarioFactura);
        mostrarLayout(R.id.txtComentarioSku);

        //vaciarCampos();

    }

    private void mostrarElementosLuzpublicadaada() {
    filtrarTipoUnidades();
        // Mostrar elementos específicos para rotura
     //  mostrarLayout(R.id.llProducto);
        mostrarLayout(R.id.llCantidad);
      //  mostrarLayout(R.id.llLote);
      //  mostrarLayout(R.id.llTipo);
        mostrarLayout(R.id.llFechaVenta);
       // mostrarLayout(R.id.llMarca);
        //mostrarLayout(R.id.llFotoLote); // Foto SKU
      //  mostrarLayout(R.id.lblFotoLote); // Foto SKU
      //  mostrarLayout(R.id.llFotoFactura); // Foto SKU
     //   mostrarLayout(R.id.llFotoSku); // Foto SKU
        //mostrarLayout(R.id.llFactura); // Foto SKU
        //mostrarLayout(R.id.lblfactura); // Foto SKU
        //mostrarLayout(R.id.txt_factura); // Foto SKU
       // mostrarLayout(R.id.lblFotoFactura);
        mostrarLayout(R.id.lblFotoSku);
       // mostrarLayout(R.id.txtComentarioLote);
      //  mostrarLayout(R.id.txtComentarioFactura);
        mostrarLayout(R.id.txtComentarioSku);

    }

    private void mostrarElementosSospechosos() {
        // Mostrar elementos específicos para falta de producto
       // mostrarLayout(R.id.llProducto);
       // mostrarLayout(R.id.llFotoLote);
        mostrarLayout(R.id.llObservacion);
        mostrarLayout(R.id.llFotoFactura);
        //mostrarLayout(R.id.llFotoSku);
        //mostrarLayout(R.id.btnCameraLote); // Foto evidencia
        mostrarLayout(R.id.btnCameraFactura); // Foto evidencia
       // mostrarLayout(R.id.btnCameraSku); // Foto evidencia

        //centrarContenido();
        ocultarLayout(R.id.txtComentarioLote);
        ocultarLayout(R.id.txtComentarioFactura);
        ocultarLayout(R.id.txtComentarioSku);
        ocultarLayout(R.id.lblFotoFactura);
        ocultarLayout(R.id.lblFotoSku);
        ocultarLayout(R.id.lblFotoLote);
    }

    private void mostrarElementosescandalo() {
      //  filtrarTipoImplementacion();
        // Para implementaciones
      //  mostrarLayout(R.id.llTipoImplementacion);

        mostrarLayout(R.id.llFotoFactura);
     //   mostrarLayout(R.id.llFotoSku);
     //   mostrarLayout(R.id.llFotoLote);
       // mostrarLayout(R.id.btnCameraLote); // Foto evidencia
        mostrarLayout(R.id.btnCameraFactura); // Foto evidencia
     //   mostrarLayout(R.id.btnCameraSku); // Foto evidencia
        mostrarLayout(R.id.llObservacion);

        //centrarContenido();
        //ocultarLayout(R.id.txtComentarioLote);
        ocultarLayout(R.id.txtComentarioFactura);
       // ocultarLayout(R.id.txtComentarioSku);
        ocultarLayout(R.id.lblFotoFactura);
      //  ocultarLayout(R.id.lblFotoSku);
       // ocultarLayout(R.id.lblFotoLote);


    }

    private void promocionesNoAutorizadas() {

       mostrarLayout(R.id.llMecanica);
       mostrarLayout(R.id.llMarca);
       mostrarLayout(R.id.llProducto);
       mostrarLayout(R.id.llFechaInicio);
       mostrarLayout(R.id.llFechaVenta);
       mostrarLayout(R.id.llStock);
       mostrarLayout(R.id.llPrecioAnterior);
       mostrarLayout(R.id.llPrecioPromocion);
        mostrarLayout(R.id.llFotoFactura);
        mostrarLayout(R.id.btnCameraFactura);
        mostrarLayout(R.id.llObservacion);


        ocultarLayout(R.id.txtComentarioLote);
        ocultarLayout(R.id.txtComentarioFactura);
        ocultarLayout(R.id.txtComentarioSku);
        ocultarLayout(R.id.lblFotoFactura);
        ocultarLayout(R.id.lblFotoSku);
        ocultarLayout(R.id.lblFotoLote);
        ocultarLayout(R.id.llTipo);
        ocultarLayout(R.id.llTipoImplementacion);
    }



    private void mostrarLayout(int layoutId) {
        View layout = getView().findViewById(layoutId);
        if (layout != null) {
            layout.setVisibility(View.VISIBLE);
        }
    }

    private void ocultarLayout(int layoutId) {
        View layout = getView().findViewById(layoutId);
        if (layout != null) {
            layout.setVisibility(View.GONE); // o View.INVISIBLE
        }
    }

    private void centrarContenido() {
        // Buscar el contenedor principal y centrar su contenido
        View container = getView().findViewById(R.id.llFotoLote);
        if (container instanceof LinearLayout) {
            ((LinearLayout) container).setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class CustomAdapterNovedades extends ArrayAdapter<Base_productos_novedades> implements Filterable {

        public ArrayList<Base_productos_novedades> values;
        public Context context;

        public CustomAdapterNovedades(Context context, ArrayList<Base_productos_novedades> values) {
            super(context, 0, values);
            this.values = values;
        }

        public class ViewHolder{
            public TextView txt_sku;
            public EditText txtCantidad;
            public TextView lblEstadoFoto;
            public ImageView ivFoto;
            public ImageButton btnCamera;
            public ImageButton ibPreview;
          //  public Button chkGuardar;
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

        //        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            //Obtener Instancia Inflater
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            ViewHolder vHolder = null;
//            //Comprobar si el View existe
//            //Si no existe inflarlo
//            if (null == convertView) {
//                convertView = inflater.inflate(R.layout.list_row_novedades, parent, false); // Modificacion (list_row_option) GT
//                //Obtener instancias de los elementos
//                vHolder = new ViewHolder();
//                vHolder.txt_sku = (TextView) convertView.findViewById(R.id.lblSku);
//                vHolder.txtCantidad = (EditText) convertView.findViewById(R.id.txt_cantidad);
//                vHolder.lblEstadoFoto = (TextView) convertView.findViewById(R.id.lblEstadoFoto);
//                vHolder.ivFoto = (ImageView) convertView.findViewById(R.id.ivFotoProducto);
//                vHolder.btnCamera = (ImageButton) convertView.findViewById(R.id.ibCargarFoto);
//                vHolder.ibPreview = (ImageButton) convertView.findViewById(R.id.ibPreview);
//
//                convertView.setTag(vHolder);
//                ViewHolder finalVHolder = vHolder;
//            } else {
//                vHolder = (ViewHolder) convertView.getTag();
//            }
//
//            try {
//                if (values.size() > 0) {
//                    String sku = values.get(position).getSku();
//                    vHolder.txt_sku.setText(sku);
//
//                    sesion = databaseHelper.getListGuardadoNovedades(codigo_pdv);
//                    Log.i("SESSION U",""+sesion.size());
//                    for(int i = 0; i < sesion.size(); i++) {
//                        if (sku.equals(sesion.get(i).getSku())) {
//                           // vHolder.txt_sku.setTextColor(ContextCompat.getColor(getContext(), R.color.color_revelado));
//                            vHolder.txt_sku.setBackgroundColor(getResources().getColor(R.color.color_revelado));
//                        }
//                    }
//
//                    final ViewHolder finalv = vHolder;
//
//                    vHolder.btnCamera.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            tipo = "novedades foto producto";
//                            cargarImagen(tipo);
//                            ivFotoProducto = finalv.ivFoto;
//                            lblEstadoFoto = finalv.lblEstadoFoto;
//                        }
//                    });
//
//                    vHolder.ibPreview.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mostrarVistaPrevia(finalv.ivFoto);
//                        }
//                    });
//
//
//                    View finalConvertView = convertView;
//
//                }
//            }catch (Exception e){
//                Log.i("EXCEPTION", e.getMessage());
//            }
//            //Devolver al ListView la fila creada
//            return convertView;
//        }

    }







}