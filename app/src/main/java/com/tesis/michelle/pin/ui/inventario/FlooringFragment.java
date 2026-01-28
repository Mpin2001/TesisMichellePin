package com.tesis.michelle.pin.ui.inventario;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Contracts.InsertFlooring;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Adaptadores.ListViewAdapter;
import com.tesis.michelle.pin.Clase.BasePortafolioProductos;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.RequestPermissions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class FlooringFragment extends Fragment implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{

    ArrayList<BasePortafolioProductos> sesion = new ArrayList<BasePortafolioProductos>();

    ArrayList<BasePortafolioProductos> type_name_copy = new ArrayList<BasePortafolioProductos>();
    TextView txtfechav;
    ImageButton btnFecha;
    TextView empty;
    RecyclerView listview;
    //ListView listDescripcion;
    private Spinner spCategoria;
    private Spinner spSubcategoria;
    private Spinner spGramaje;
    /*private Spinner spSubcategoria;
    private Spinner spSegmento;*/
    //private Spinner spPresentacion;
    private Spinner spMarca;
    /*private Spinner spTamano;
    private Spinner spCantidad;*/
    private EditText txtSKUCode;
    private EditText txtDescripcion;


    String[] valueOfTextViewSku;
    String[] valueOfEditText;
    String[] valueOfSpinner;
    String[] valueOfSpinner2;
    String[] valueOfSpinnerT;
    String[] valueOfEditText2;
    String[] valueOfSpinner3;
    String[] valueOfEditText3;
    String[] valueOfEditText4;
    String[] valueOfSpinner4;
    String[] valueOfSpinner5;
//    String[] valueOfTextView;


    private String categoria, subcategoria, segmento1, segmento2, brand, gramaje, tamano, cantidad, codigo, descripcion, format, presentacion, celular;
    private String id_pdv,user,codigo_pdv, punto_venta,fecha,hora, entrega, semana;
    private String fechaventas, producto, fecha_producto, cantidad_producto, poferta, sku, cuotas, vcuotas;

    private final String manufacturer = "BASSA";
    private final String manufacturer2 = "Unilever S.A";
    private Button btnGuardar;

    DatabaseHelper handler;

    ArrayList<BasePortafolioProductos> listProductos;
    // List<String> listDescripcionL;

    List<String> filterProducts;
    List<String> filterTargets;

    public String venta, souv, causal, otros, canal, subcanal;
    private SharedPreferences sharedPref;

    CustomAdapterInventario dataAdapter;
    ListViewAdapter dataAdapters;

    LinearLayout layout_skuName;
    LinearLayout layout_skuDescripcion;
    List<String> listMotivo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_flooring, container, false);
        //setActionBar();
        // Evita que el teclado no cubra los sku
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        LoadData();

        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();
        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);
        spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);
        spSubcategoria = (Spinner) rootView.findViewById(R.id.spSubcategoria);
        spGramaje = (Spinner) rootView.findViewById(R.id.spGramaje);
        /*spSubcategoria = (Spinner)findViewById(R.id.spSubcategoria);
        spSegmento = (Spinner)findViewById(R.id.spSegmento);*/
        //spPresentacion = (Spinner)findViewById(R.id.spPresentacion);
        spMarca = (Spinner) rootView.findViewById(R.id.spMarca);
        /*spTamano = (Spinner)findViewById(R.id.spTamaño);
        spCantidad = (Spinner)findViewById(R.id.spCantidad);*/
        txtSKUCode = (EditText) rootView.findViewById(R.id.txtSKUCode);
        txtDescripcion  = (EditText) rootView.findViewById(R.id.txtDescripcionSKU);
        btnGuardar=(Button) rootView.findViewById(R.id.btnGuardar);

        //startService(new Intent(getContext(), MyService.class));

        /*listview = (RecyclerView)findViewById(R.id.lvSKUCode);
        listview.setHasFixedSize(true);*/

        layout_skuName = (LinearLayout) rootView.findViewById(R.id.layout_skuName);
        layout_skuDescripcion = (LinearLayout) rootView.findViewById(R.id.layout_skuDescripcion);

        empty = (TextView) rootView.findViewById(R.id.recyclerview_data_empty);

        layout_skuName.setVisibility(View.INVISIBLE);
        layout_skuDescripcion.setVisibility(View.INVISIBLE);

        //listDescripcion = (ListView)findViewById(R.id.lvDescripcion);
        listMotivo = handler.getMotivoSugerido();
     //   filtrarCategoria(manufacturer);
        filtrarBrandProv( manufacturer);


/*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String item = (String) listview.getItemAtPosition(position);
                System.out.println(item);
                //txtDescripcion.setText(item);

            }
        });
        */

        listview = (RecyclerView) rootView.findViewById(R.id.lvSKUCode);

//        listview.setOnTouchListener(new View.OnTouchListener() {
//            // Setting on Touch Listener for handling the touch inside ScrollView
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Disallow the touch request for parent scroll on touch of child view
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//
//        View headerView = (View) this.getLayoutInflater().inflate(R.layout.list_row_flooring_title,null,false);
//        listview.addHeaderView(headerView,null,false);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

    public void filtrarCategoria(String manufacturer) {
        List<String> operadores = handler.getCategoriaFlooring2(codigo_pdv, canal, subcanal, manufacturer);
        /*
        if (operadores.size()==2) {
            operadores.remove(0);
        }*/
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
    }

    public void filtrarSubcategoria(String categoria, String manufacturer) {
        List<String> operadores = handler.getSubcategoriaFlooring2(categoria, codigo_pdv, canal, subcanal, manufacturer);
        Log.i("SUBCATEGORIA DE PRECIOS", String.valueOf(operadores));
        /*
        if (operadores.size()==2) {
            operadores.remove(0);
        }*/
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }

    /*public void filtrarMarca(String subcategoria, String subcategoria) {
        List<String> operadores = handler.getSegmento1(subcategoria,subcategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }

    public void filtrarSegmento(String subcategoria, String subcategoria, String subcategoria) {
        List<String> operadores = handler.getSegmento2(subcategoria,subcategoria,subcategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSegmento.setAdapter(dataAdapter);
        spSegmento.setOnItemSelectedListener(this);
    }*/

    /*public void filtrarFabricante(String logro, String subcategoria, String manufacturer) {
        List<String> operadores = handler.getPresentacionFlooring(logro,subcategoria,manufacturer);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPresentacion.setAdapter(dataAdapter);
        spPresentacion.setOnItemSelectedListener(this);
    }*/

    public void filtrarBrandProv( String manufacturer) {
        List<String> operadores = handler.getBrandFlooring4( codigo_pdv, canal, subcanal,manufacturer);
        /*
        if (operadores.size()==2) {
            operadores.remove(0);
        }*/
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void filtrarGramaje(String categoria, String subcategoria, String marca, String manufacturer) {
        List<String> operadores = handler.getGramajeFlooring2(categoria, subcategoria, marca, codigo_pdv, canal, subcanal,manufacturer);
        /*
        if (operadores.size()==2) {
            operadores.remove(0);
        }*/
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGramaje.setAdapter(dataAdapter);
        spGramaje.setOnItemSelectedListener(this);
    }

//    public void filtrarBrand(String categoria, String subcategoria, String manufacturer) {
//        List<String> operadores = handler.getBrandFlooring2(categoria, subcategoria, codigo_pdv, canal, subcanal,manufacturer);
//        if (operadores.size()==2) {
//            operadores.remove(0);
//        }
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spMarca.setAdapter(dataAdapter);
//        spMarca.setOnItemSelectedListener(this);
//    }

    /*public void filtrartamano(String subcategoria, String subcategoria, String subcategoria, String presentacion, String contenido) {
        List<String> operadores = handler.getTamano(subcategoria,subcategoria,subcategoria,presentacion,contenido);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTamano.setAdapter(dataAdapter);
        spTamano.setOnItemSelectedListener(this);
    }

    public void filtrarcantidad(String subcategoria, String subcategoria, String subcategoria, String presentacion, String contenido, String tamano) {
        List<String> operadores = handler.getCantidad(subcategoria,subcategoria,subcategoria,presentacion,contenido, tamano);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCantidad.setAdapter(dataAdapter);
        spCantidad.setOnItemSelectedListener(this);
    }*/


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
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        celular =sharedPreferences.getString(Constantes.CELULAR,Constantes.NODATA);
        canal =sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        subcanal =sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(i2 + "/" + (i1+1) + "/" + i);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        txtfechav.setText(outDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if (adapterView== spCategoria) {
//            try{
//                categoria = adapterView.getItemAtPosition(i).toString();
//                filtrarSubcategoria(categoria, manufacturer);
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//        if (adapterView== spSubcategoria) {
//            try{
//                subcategoria = adapterView.getItemAtPosition(i).toString();
//                filtrarBrandProv(categoria, subcategoria, manufacturer, manufacturer2);
//                //filtrarMarca(subcategoria,subcategoria);
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
        /*if (adapterView== spPresentacion) {
            try{
                presentacion = adapterView.getItemAtPosition(i).toString();
                filtrarBrand(logro, subcategoria, manufacturer);
                //filtrarMarca(subcategoria,subcategoria);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if (adapterView==spSubcategoria) {
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
        if (adapterView== spMarca) {
            try{
                brand = adapterView.getItemAtPosition(i).toString();
                //showListView(categoria, subcategoria, brand, manufacturer);
                //filtrartamano(subcategoria,subcategoria,subcategoria,presentacion,contenido);
                //filtrarGramaje(categoria, subcategoria, brand, manufacturer);
                showListView(brand, manufacturer);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

//        if (adapterView== spGramaje) {
//            try{
//                gramaje = adapterView.getItemAtPosition(i).toString();
//                showListView(categoria, subcategoria, brand, gramaje, manufacturer);
//                //filtrartamano(subcategoria,subcategoria,subcategoria,presentacion,contenido);
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
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

    public void showListView(String brand, String manufacturer) {
//        listProductos = handler.filtrarListProductos3Flooring(categoria, subcategoria, brand, codigo_pdv, canal, subcanal, manufacturer);
//        dataAdapter = new CustomAdapterInventario(getContext(), listProductos);
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
//                    ArrayList<BasePortafolioProductos> type_name_filter = new ArrayList<BasePortafolioProductos>();
//
//                    String text = s.toString();
//
//                    for (int i = 0; i < listProductos.size(); i++) {
//                        if ((listProductos.get(i).getSku().toLowerCase()).contains(text.toLowerCase())) {
//                            BasePortafolioProductos p = new BasePortafolioProductos();
//                            p.setSku(listProductos.get(i).getSku());
//                            type_name_filter.add(p);
//                        }
//                    }
//
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
//
//        }else{
//            empty.setVisibility(View.VISIBLE);
//        }

        listProductos = handler.filtrarListProductos6Flooring( brand, codigo_pdv, canal, subcanal, manufacturer);
        Log.i("LISTpRODUCTOS", "t "+listProductos);
        dataAdapter = new CustomAdapterInventario(getContext(), listProductos);
        if (dataAdapter.getItemCount() != 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            empty.setVisibility(View.GONE);
            listview.setLayoutManager(linearLayoutManager);
            listview.setHasFixedSize(true);
            CustomAdapterInventario customAdapter = new CustomAdapterInventario(this.getContext(),listProductos);
            listview.setAdapter(customAdapter);
            listview.setVisibility(View.VISIBLE);




            txtSKUCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    ArrayList<BasePortafolioProductos> type_name_filter = new ArrayList<BasePortafolioProductos>();

                    String text = s.toString();

                    for (int i = 0; i < listProductos.size(); i++) {
                        if ((listProductos.get(i).getSku().toLowerCase()).contains(text.toLowerCase())) {
                            BasePortafolioProductos p = new BasePortafolioProductos();
                            p.setSku(listProductos.get(i).getSku());
                            type_name_filter.add(p);
                        }
                    }

                    type_name_copy = type_name_filter;
                    listUpdate(type_name_copy);
                    //filter(s.toString());
                    //dataAdapter.getFilter().filter(s);

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });



        }else{
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    public void listUpdate(ArrayList<BasePortafolioProductos> data) {
        if (!data.isEmpty()) {
            listview.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            listview.setAdapter(new CustomAdapterInventario(getContext(), data));
        }else{
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }
/*
    public void showListDescripcion(String status, String subcategoria,String subcategoria,String presentacion, String contenido, String tamano, String cantidad) {
        listDescripcionL = handler.filtrarListDescripcion(status,subcategoria,subcategoria,presentacion,contenido,tamano,cantidad);
        dataAdapter = new CustomAdapterPresenciaMinima(getContext(),listDescripcionL);
        if (!dataAdapter.isEmpty()) {
            listDescripcion.setAdapter(dataAdapter);
        }else{
            empty.setVisibility(View.VISIBLE);
        }
    }
*/
    /*public class CustomAdapterPresenciaMinima extends ArrayAdapter<BasePortafolioProductos> implements Filterable {

        public ArrayList<BasePortafolioProductos> values;
        public Context context;
        boolean[] checkBoxState;

        public CustomAdapterPresenciaMinima(Context context, ArrayList<BasePortafolioProductos> values) {
            super(context, 0, values);
            this.values = values;
            checkBoxState=new boolean[values.size()];
        }

        public class ViewHolder{
            TextView lblSku;
            CheckBox check; //agregado GT
            //EditText txtunidad;
            EditText txtventa;
            EditText txtsouv;
            Spinner spCausal;
            EditText txtOtros;
           // Spinner spMotivo;
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //Obtener Instancia Inflater
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder vHolder = null;
            //Comprobar si el View existe
            //Si no existe inflarlo
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.list_row_option, parent, false); // Modificacion (list_row_option) GT
                //Obtener instancias de los elementos
                vHolder = new ViewHolder();
                vHolder.lblSku = (TextView) convertView.findViewById(R.id.lblSku);
                vHolder.check = (CheckBox) convertView.findViewById(R.id.checkPresencia);
             //   vHolder.txtunidad = (EditText) convertView.findViewById(R.id.txtunidad);
                vHolder.txtventa = (EditText) convertView.findViewById(R.id.txtventa);
                vHolder.txtsouv = (EditText) convertView.findViewById(R.id.txtsouv);
                vHolder.spCausal = (Spinner) convertView.findViewById(R.id.spCausal);
                vHolder.txtOtros = (EditText) convertView.findViewById(R.id.txtDetalle);

                //cargarMotivos(vHolder);

                convertView.setTag(vHolder);

                //checkGuardar.setEnabled(true);
            } else { vHolder = (ViewHolder) convertView.getTag(); }

            if (values.size() > 0) {
                //set the data to be displayed
                vHolder.lblSku.setText(values.get(position).getSku());
               // sku = vHolder.lblSku.getText().toString();
                // checkGuardar.setEnabled(false);
                final ViewHolder finalv = vHolder;

                vHolder.spCausal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!finalv.spCausal.getSelectedItem().toString().trim().equalsIgnoreCase("OTROS")) {
                            finalv.txtOtros.setEnabled(false);
                            finalv.txtOtros.setText("-");
                        }else{
                            finalv.txtOtros.setEnabled(true);
                            finalv.txtOtros.setText("");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                if (finalv.spCausal.getSelectedItem().toString().equalsIgnoreCase("OTROS")) {
                    finalv.txtOtros.setText("-");
                }else{
                    finalv.txtOtros.setText("-");
                }

                vHolder.check.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                     //  unidad= finalv.txtunidad.getText().toString();
                        venta = finalv.txtventa.getText().toString();
                        souv = finalv.txtsouv.getText().toString();
                        causal = finalv.spCausal.getSelectedItem().toString();
                        otros = finalv.txtOtros.getText().toString();

                        if (((CheckBox)v).isChecked()) {
                            if (!venta.equals("") && !souv.equals("")) {
                                String sku =finalv.lblSku.getText().toString();
                                insertData(sku,venta,souv,causal,otros,"FECHA CADUCIDAD");
                            }else{
                                Toast.makeText(getContext(), "Verifica el Ingreso en Inventario", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
            //Devolver al ListView la fila creada
            return convertView;
        }
    }*/

    public void insertData() {
        //Almacenar Datos
        ContentValues values = new ContentValues();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fechaser = date.format(currentLocalTime);
        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String horaser = hour.format(currentLocalTime);

      //  String categoria = spCategoria.getSelectedItem().toString();
     //   String subcategoria = spSubcategoria.getSelectedItem().toString();
        String brand = spMarca.getSelectedItem().toString();

        int contador = 0;
        String mensaje = "";

        do {
            String sku = "";
            String istock = "";
            String isemana = "";
            String isugerido = "";
            String itipo = "";
            String icantidad = "";
            String ientrega = "";
            String iobservaciones = "";
            String ioTipoSug = "";
           String ioMotivoSug = "";
//            String icaducidad = "";

            for (int i = 0; i < valueOfTextViewSku.length; i++) {
                sku = valueOfTextViewSku[0].toString();
                Log.i("SKU 1", sku);
            }
            for (int i = 0; i < valueOfEditText.length; i++) {
                istock = valueOfEditText[0].toString();
                Log.i("STOCK 1", istock);
            }
//            for (int i = 0; i < valueOfSpinner.length; i++) {
//                isemana = valueOfSpinner[0].toString();
//            }
            for (int i = 0; i < valueOfSpinner2.length; i++) {
                isugerido = valueOfSpinner2[0].toString();
            }
            for (int i = 0; i < valueOfSpinnerT.length; i++) {
                itipo = valueOfSpinnerT[0].toString();
            }
            for (int i = 0; i < valueOfEditText2.length; i++) {
                icantidad = valueOfEditText2[0].toString();
                Log.i("CANTIDAD 1", icantidad);
            }
            for (int i = 0; i < valueOfSpinner4.length; i++) {
                ientrega = valueOfSpinner4[0].toString();
            }
            for (int i = 0; i < valueOfEditText4.length; i++) {
                iobservaciones = valueOfEditText4[0].toString();
            }

            for (int i = 0; i < valueOfSpinner3.length; i++) {
                ioTipoSug = valueOfSpinner3[0].toString();
            }

            for (int i = 0; i < valueOfSpinner5.length; i++) {
                ioMotivoSug = valueOfSpinner5[0].toString();
            }
//            for (int i = 0; i < valueOfTextView.length; i++) {
//                icaducidad = valueOfTextView[0].toString();
//            }

            List<String> list_textviewsku = new ArrayList<String>(Arrays.asList(valueOfTextViewSku));
            list_textviewsku.remove(0);
            valueOfTextViewSku = list_textviewsku.toArray(new String[0]);

            List<String> list_edittext = new ArrayList<String>(Arrays.asList(valueOfEditText));
            list_edittext.remove(0);
            valueOfEditText = list_edittext.toArray(new String[0]);

//            List<String> list_spinner = new ArrayList<String>(Arrays.asList(valueOfSpinner));
//            list_spinner.remove(0);
//            valueOfSpinner = list_spinner.toArray(new String[0]);

            List<String> list_spinner2 = new ArrayList<String>(Arrays.asList(valueOfSpinner2));
            list_spinner2.remove(0);
            valueOfSpinner2 = list_spinner2.toArray(new String[0]);

            List<String> list_spinner4 = new ArrayList<String>(Arrays.asList(valueOfSpinner4));
            list_spinner4.remove(0);
            valueOfSpinner4 = list_spinner4.toArray(new String[0]);

            List<String> list_spinnerT = new ArrayList<String>(Arrays.asList(valueOfSpinnerT));
            list_spinnerT.remove(0);
            valueOfSpinnerT = list_spinnerT.toArray(new String[0]);

            List<String> list_edittext2 = new ArrayList<String>(Arrays.asList(valueOfEditText2));
            list_edittext2.remove(0);
            valueOfEditText2 = list_edittext2.toArray(new String[0]);

            List<String> list_spinner3 = new ArrayList<String>(Arrays.asList(valueOfSpinner3));
            list_spinner3.remove(0);
            valueOfSpinner3 = list_spinner3.toArray(new String[0]);

            List<String> list_edittext3 = new ArrayList<String>(Arrays.asList(valueOfEditText3));
            list_edittext3.remove(0);
            valueOfEditText3 = list_edittext3.toArray(new String[0]);

            List<String> list_edittext4 = new ArrayList<String>(Arrays.asList(valueOfEditText4));
            list_edittext4.remove(0);
            valueOfEditText4 = list_edittext4.toArray(new String[0]);

            List<String> list_spinner5 = new ArrayList<String>(Arrays.asList(valueOfSpinner5));
            list_spinner5.remove(0);
            valueOfSpinner5 = list_spinner5.toArray(new String[0]);

//            List<String> list_textview = new ArrayList<String>(Arrays.asList(valueOfTextView));
//            list_textview.remove(0);
//            valueOfTextView = list_textview.toArray(new String[0]);

         //   String contenido = handler.getContenidoPrecios(sku);
            String sector = handler.getSectorPrecios(sku);
            String plataforma = handler.getPlataformaBySku(sku);

            if (!istock.trim().isEmpty()) {

                if(ientrega.equalsIgnoreCase("Seleccione")){
                    ientrega = "NA";
                }
                if(itipo.equalsIgnoreCase("Seleccione")){
                    itipo = "NA";
                }
                if (brand.equalsIgnoreCase("SURF")){
//                    icaducidad = "NA";
                    icantidad = "NA";
                }

                values.put(InsertFlooring.Columnas.PHARMA_ID, id_pdv);
                values.put(InsertFlooring.Columnas.CODIGO, codigo_pdv);
                values.put(InsertFlooring.Columnas.USUARIO, user);
                values.put(InsertFlooring.Columnas.SUPERVISOR, punto_venta);
                values.put(InsertFlooring.Columnas.FECHA, fechaser);
                values.put(InsertFlooring.Columnas.HORA, horaser);
             //   values.put(InsertFlooring.Columnas.SECTOR, sector);
             //   values.put(InsertFlooring.Columnas.CATEGORIA, categoria);
               // values.put(InsertFlooring.Columnas.SUBCATEGORIA, subcategoria);
                values.put(InsertFlooring.Columnas.PRESENTACION, "");
                values.put(InsertFlooring.Columnas.BRAND, brand);
            //    values.put(InsertFlooring.Columnas.CONTENIDO, contenido);
                values.put(InsertFlooring.Columnas.SKU_CODE, sku);
                values.put(InsertFlooring.Columnas.INVENTARIOS, istock);  //stock
                values.put(InsertFlooring.Columnas.SEMANA, "NA");
                values.put(InsertFlooring.Columnas.SUGERIDOS, icantidad);
                values.put(InsertFlooring.Columnas.TIPO, iobservaciones);//tipo_unidades -> stock_bodega
                values.put(InsertFlooring.Columnas.ENTREGA, ientrega);//sugeridos si/no
                if(ioTipoSug.equals("Seleccione")){
                    values.put(InsertFlooring.Columnas.CAUSAL, "N/A");//tipo_sugeridos
                }else{
                    values.put(InsertFlooring.Columnas.CAUSAL, ioTipoSug);//tipo_sugeridos
                }
                values.put(InsertFlooring.Columnas.OTROS, itipo);//tipo stock bodega
                values.put(InsertFlooring.Columnas.FECHA_CADUCIDAD, "NA"); //icaducidad
                values.put(InsertFlooring.Columnas.POS_NAME, punto_venta);
                values.put(InsertFlooring.Columnas.PLATAFORMA, plataforma);
                values.put(InsertFlooring.Columnas.MOTIVO_SUGERIDO, ioMotivoSug);
                if(ioMotivoSug.equals("Seleccione")) {
                    values.put(InsertFlooring.Columnas.MOTIVO_SUGERIDO, "N/A");//tipo_sugeridos
                }
                values.put(Constantes.PENDIENTE_INSERCION, 1);

                getContext().getContentResolver().insert(InsertFlooring.CONTENT_URI, values);

                if (VerificarNet.hayConexion(getContext())) {
                    SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertFlooring, null);
                    Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                }
                vaciarCampos();

            }

            if (contador == 0) {
                mensaje = "_*XPLORA*_ de _*Grupo Lucky*_ te envía la siguiente información. \n*Cliente:* " + punto_venta + "\r\n" + "*Mercaderista:* "+ user+"\r\n";
            }
            Log.i("MENSAJE ANTES", mensaje + " " + ientrega);
            if (ientrega.equalsIgnoreCase("SI")){
                //istock, icantidad, ioTipoSug;
                mensaje = mensaje + "\r\n*Sugerido:* " + sku.replace("&", "-").replace("#", "") + "\r\n*Cantidad Inventario:* "+ istock +"\r\n*Cantidad Sugerido:* " + icantidad + "\r\n" +"*Tipo Sugerido:* " + ioTipoSug + "\r\n";
                contador++;
                /*if (iobservaciones.equalsIgnoreCase("")) {
                    mensaje = mensaje + "\r\n*Sugerido:* " + sku.replace("&", "-").replace("#", "") + "\r\n*Cantidad:* "+ icantidad +" "+ itipo +"\r\n*Despacho:* " + ientrega + "\r\n";
                    //enviarMensaje(celular, mensaje);
                }else{
                    mensaje = mensaje + "\r\n*Sugerido:* " + sku.replace("&"l, "-").replace("#", "") + "\r\n*Cantidad:* "+ icantidad +" "+ itipo +"\r\n*Despacho:* " + ientrega + "\r\n*Observaciones:* " + iobservaciones +"\r\n";
                }
                contador++;*/
            }

        }while(valueOfTextViewSku.length!=0 || valueOfEditText.length!=0);
        Log.i("ANTES DE CONTADOR","SI");
        if(contador>0){
            mensaje.replace("&","-").replace("#","-");
            Log.i("MENSAJE 1", mensaje);
            enviarMensajeWhatsapp(mensaje);
        }

    }

    public void vaciarCampos(){
        //txtCodigo.setText("");
      //  spCategoria.setSelection(0);
        //spSubcategoria.setSelection(0);
        spMarca.setSelection(0);
        listview.setVisibility(View.GONE);
    }

    public void insertDataOld(String skuSelected, String semana, String esSugerido, String fecha_caducidad, String sugerido, String entrega, String causal, String observacion, String inventario) {
        //Almacenar Datos
        ContentValues values = new ContentValues();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fechaser = date.format(currentLocalTime);
        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String horaser = hour.format(currentLocalTime);

        String categoria = spCategoria.getSelectedItem().toString();
        String subcategoria = spSubcategoria.getSelectedItem().toString();
        String brand = spMarca.getSelectedItem().toString();
        String contenido = handler.getContenidoPrecios(skuSelected);
        String sector = handler.getSectorPrecios(skuSelected);
        String plataforma = handler.getPlataformaBySku(skuSelected);

//        do {
//
//        }

        values.put(InsertFlooring.Columnas.PHARMA_ID, id_pdv);
        values.put(InsertFlooring.Columnas.CODIGO, codigo_pdv);
        values.put(InsertFlooring.Columnas.USUARIO, user);
        values.put(InsertFlooring.Columnas.SUPERVISOR, punto_venta);
        values.put(InsertFlooring.Columnas.FECHA, fechaser);
        values.put(InsertFlooring.Columnas.HORA, horaser);
        values.put(InsertFlooring.Columnas.SECTOR,sector);
        values.put(InsertFlooring.Columnas.CATEGORIA,categoria);
        values.put(InsertFlooring.Columnas.SUBCATEGORIA,subcategoria);
        values.put(InsertFlooring.Columnas.PRESENTACION,"");
        values.put(InsertFlooring.Columnas.BRAND,brand);
        values.put(InsertFlooring.Columnas.CONTENIDO,contenido);
        values.put(InsertFlooring.Columnas.SKU_CODE,skuSelected);
        values.put(InsertFlooring.Columnas.INVENTARIOS,inventario);  //stock
        values.put(InsertFlooring.Columnas.SEMANA,semana);
        values.put(InsertFlooring.Columnas.SUGERIDOS,sugerido);
        values.put(InsertFlooring.Columnas.ENTREGA,entrega);
        values.put(InsertFlooring.Columnas.CAUSAL,"");
        values.put(InsertFlooring.Columnas.OTROS,observacion);
        values.put(InsertFlooring.Columnas.FECHA_CADUCIDAD,fecha_caducidad);
        values.put(InsertFlooring.Columnas.POS_NAME,punto_venta);
        values.put(InsertFlooring.Columnas.PLATAFORMA,plataforma);
        values.put(Constantes.PENDIENTE_INSERCION, 1);

        getContext().getContentResolver().insert(InsertFlooring.CONTENT_URI, values);

        if (VerificarNet.hayConexion(getContext())) {
            SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertFlooring, null);
            Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
        }

        int contador = 0;
        String mensaje = "";

        if(esSugerido.equalsIgnoreCase("SI")){
            if (contador == 0) {
//                            mensaje = "HOLA! Te saluda _*" + user + "*_ de Lucky. *Cliente:* " + format + " - " + punto_venta + "\r\n";
                mensaje = "_*XPLORA*_ de _*Grupo Lucky*_ te envía la siguiente información. *Cliente:* " + format + " - " + punto_venta + "\r\n";
            }
            if (contador <= 4) {
                mensaje = mensaje + "\r\n*Sugerido:* " + skuSelected.replace("&", "-").replace("#", "") + "\r\n*Cantidad:*  Unidades\r\n*Despacho:* " + entrega + "\r\n";
                //enviarMensaje(celular, mensaje);
            }else{
                Toast.makeText(getContext(), "Solo es posible enviar hasta 5 sku", Toast.LENGTH_SHORT).show();
            }
            contador++;
        }

    }

//    private void enviarMensajeWhatsapp(String messagestr) {
//        String phonestr = celular;
//        Log.i("NUM CELUALR", celular);
//        if (!messagestr.isEmpty() && !phonestr.isEmpty()) {
//            if (isWhatappInstalled() || isWhatappBusinessInstalled()) {
//                if (!phonestr.equals("N/A") || !phonestr.equals("NA") || !phonestr.contains("+")) {
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+phonestr+"&text="+messagestr));
//                    startActivity(i);
//                } else {
//                    Toast.makeText(getContext(),"Numero de telefono no puede ser: " + phonestr, Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(getContext(),"Whatsapp no esta instalado",Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(getContext(), "Por favor, verificar el número de teléfono o el mensaje, podrían estar vacios", Toast.LENGTH_LONG).show();
//        }
//    }

    private void enviarMensajeWhatsapp(String messagestr) {
        try {
            // 1. Intenta con WhatsApp normal
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, messagestr);
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            // Si falla, intenta con WhatsApp Business
            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, messagestr);
                intent.setPackage("com.whatsapp.w4b");
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException ex2) {
                // Si ambos fallan, muestra un mensaje de error
                Toast.makeText(getContext(), "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show();
            }
        }

        // se elimino la llamada a la variable celular
        //antes se usaba la constante actionview y ahora actionSend para compartir el contenido del mensaje
//       try{
//           Intent intent = new Intent(Intent.ACTION_SEND);
//           intent.setType("text/plain");
//           intent.putExtra(Intent.EXTRA_TEXT, messagestr);
//          intent.setPackage("com.whatsapp");
//           intent.setPackage("com.whatsapp.w4b"); //aqui no funciona porque se esta sobreescibiendo el primer intent
//           startActivity(intent);
//       }
//       catch (android.content.ActivityNotFoundException ex){
//           Toast.makeText(getContext(),"Whatsapp no esta instalado",Toast.LENGTH_SHORT).show();
//       }
    }

    private boolean isWhatappInstalled() {
        PackageManager packageManager = getContext().getPackageManager();
        boolean whatsappInstalled;
        try {
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        }catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }
        return whatsappInstalled;
    }

    private boolean isWhatappBusinessInstalled() {
        PackageManager packageManager = getContext().getPackageManager();
        boolean whatsappInstalled;
        try {
            packageManager.getPackageInfo("com.whatsapp.w4b",PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        }catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }
        return whatsappInstalled;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

//    public class CustomAdapterInventario extends ArrayAdapter<Flooring> implements Filterable {
//
//        public ArrayList<Flooring> values;
//        public Context context;
//        boolean[] checkBoxState;
//
//        public CustomAdapterInventario(Context context, ArrayList<Flooring> values) {
//            super(context, 0, values);
//            this.values = values;
//            checkBoxState = new boolean[values.size()];
//        }
//
//        public class ViewHolder{
//            TextView lblSku;
//            CheckBox checkGuardar; //agregado GT
//            TextView txtFecha;
//            EditText txtCantidad;
//            ImageButton btnFechaProd;
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
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            //Obtener Instancia Inflater
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            CustomAdapterInventario.ViewHolder vHolder = null;
//            //Comprobar si el View existe
//            //Si no existe inflarlo
//            if (null == convertView) {
//                convertView = inflater.inflate(R.layout.list_row_flooring, parent, false); // Modificacion (list_row_option) GT
//                //Obtener instancias de los elementos
//                vHolder = new CustomAdapterInventario.ViewHolder();
//                vHolder.lblSku = (TextView) convertView.findViewById(R.id.lblSku);
//                vHolder.checkGuardar = (CheckBox) convertView.findViewById(R.id.checkGuardar);
//                vHolder.txtFecha = (TextView) convertView.findViewById(R.id.txtFecha);
//                vHolder.txtCantidad = (EditText) convertView.findViewById(R.id.txtCantidad);
//                vHolder.btnFechaProd = (ImageButton) convertView.findViewById(R.id.btnFechaProd);
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
//
//                vHolder.txtCantidad.setFilters(new InputFilter[]{new InputFilter() {
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
//
//                vHolder.txtCantidad.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(5)});
//
//                final CustomAdapterInventario.ViewHolder finalv = vHolder;
//
//                convertView.setTag(vHolder);
//            } else {
//                vHolder = (CustomAdapterInventario.ViewHolder) convertView.getTag();
//            }
//
//            if (values.size() > 0) {
//                vHolder.lblSku.setText(values.get(position).getSku_code());
//
//                final CustomAdapterInventario.ViewHolder finalv = vHolder;
//
//                vHolder.btnFechaProd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //finalv.txtCantidad.setText("PRUEBA");
//                        final Calendar calendar = Calendar.getInstance();
//                        int anio = calendar.get(Calendar.YEAR);
//                        int mes = calendar.get(Calendar.MONTH);
//                        int dia = calendar.get(Calendar.DAY_OF_MONTH);
//
//                        DatePickerDialog from_dateListener = new DatePickerDialog(FlooringActivity.this, new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                                Date date = null;
//                                try {
//                                    date = dateFormat.parse(dayOfMonth + "/" + (month+1) + "/" + year);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                                String outDate = dateFormat.format(date);
//                                finalv.txtFecha.setText(outDate);
//                                dataAdapter.notifyDataSetChanged();
//                            }
//                        },anio,mes,dia);
//                        from_dateListener.show();
//                    }
//                });
//
//                vHolder.checkGuardar.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        fecha_producto = finalv.txtFecha.getText().toString().trim();
//                        cantidad_producto = finalv.txtCantidad.getText().toString();
//                        sku = finalv.lblSku.getText().toString();
//                        if (((CheckBox)v).isChecked()) {
//                            if (!fecha_producto.equals("") && fecha_producto != null &&
//                                    !cantidad_producto.equals("") && cantidad_producto != null) {
//                                insertData(sku, fecha_producto, cantidad_producto);
//                            } else {
//                                Toast.makeText(getContext(), "No ingresaste la fecha o la cantidad", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                });
//            }
//            //Devolver al ListView la fila creada
//            return convertView;
//        }
//    }

    public class CustomAdapterInventario extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private final String TAG = CustomAdapterInventario.class.getSimpleName();
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;
        private Context context;
        public ArrayList<BasePortafolioProductos> values;

        public CustomAdapterInventario(Context context, ArrayList<BasePortafolioProductos> values) {
            this.context = context;
            this.values = values;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            if (viewType == TYPE_HEADER) {
//                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_inv_title, parent, false);
//                return new HeaderViewHolder(layoutView);
//            } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_flooring, parent, false);
            return new ItemViewHolder(layoutView);
//            }
//            throw new RuntimeException("No match for " + viewType + ".");
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            BasePortafolioProductos mObject = values.get(position);
            if (holder instanceof HeaderViewHolder) {
                //((HeaderViewHolder) holder).headerTitle.setText(mObject.getSku());
//                ((HeaderViewHolder) holder).headerTitle.setText("SKU");
//                ((HeaderViewHolder) holder).headerTitle1.setText("STOCK ACTUAL");
//                ((HeaderViewHolder) holder).headerTitle2.setText("SUGERIDO");
////                ((HeaderViewHolder) holder).headerTitle3.setText("CAUSAL SUGERIDO");
//                ((HeaderViewHolder) holder).headerTitle4.setText("OBSERVACION");
//                ((HeaderViewHolder) holder).headerTitle5.setText("CADUCIDAD");
//                ((HeaderViewHolder) holder).headerTitle6.setText("CANTIDAD");
//                ((HeaderViewHolder) holder).headerTitle7.setText("ENTREGA");
//                ((HeaderViewHolder) holder).headerTitle8.setText("SEMANA");
            }else if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).txt_sku.setText(mObject.getSku());
            }

            sesion = handler.getListGuardadoInventario(codigo_pdv);
            Log.i("SESSION U", String.valueOf(sesion));
            for(int i = 0; i < sesion.size(); i++) {
                if (mObject.getSku().equals(sesion.get(i).getSku())) {
                    ((FlooringFragment.CustomAdapterInventario.ItemViewHolder) holder).txt_sku.setTextColor(ContextCompat.getColor(getContext(), R.color.color_revelado));
//                    ((FlooringFragment.CustomAdapterInventario.ItemViewHolder) holder).lblEstado.setText("REALIZADO");

                    ((FlooringFragment.CustomAdapterInventario.ItemViewHolder) holder).txt_sku.setTypeface(null, Typeface.BOLD);
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

//        public void filterList(ArrayList<BasePortafolioProductos> filteredList) {
//            listProductos = filteredList;
//            Log.i("LIST",listProductos.status()+"");
//            FlooringActivity.this.dataAdapter.notifyDataSetChanged();
//        }

        public class HeaderViewHolder extends RecyclerView.ViewHolder{

            public TextView headerTitle, headerTitle1, headerTitle2, /*headerTitle3,*/ headerTitle4, headerTitle5, headerTitle6, headerTitle7, headerTitle8;

            public HeaderViewHolder(View itemView) {
                super(itemView);
//                headerTitle = (TextView)itemView.findViewById(R.id.lblSku);
//                headerTitle1 = (TextView)itemView.findViewById(R.id.lblStock);
//                headerTitle2 = (TextView)itemView.findViewById(R.id.lblSugerido);
////                headerTitle3 = (TextView)itemView.findViewById(R.id.lblCausal);
//                headerTitle4 = (TextView)itemView.findViewById(R.id.lblDetalle);
//                headerTitle5 = (TextView)itemView.findViewById(R.id.lblCaducidad);
//                headerTitle6 = (TextView)itemView.findViewById(R.id.lblCantidadSugerido);
//                headerTitle7 = (TextView)itemView.findViewById(R.id.lblEntrega);
//                headerTitle8 = (TextView)itemView.findViewById(R.id.lblSemana);
            }

        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            public TextView txt_sku;
            //            public  TextView lblEstado;
            public Button chkGuardar;
            public EditText txtStock;
            public EditText txtStock_Bodega;
            public EditText txtSugerido;
            public Spinner spEsSugerido;
            public Spinner spSemana;
            public Spinner spTipoUnidades;
            public Spinner spMotivo;
            public Spinner spEntrega;
            public Spinner spSug;
            //            public Spinner spCausal;
            public Spinner txtObservacion;
            public TextView txtFecha;
            public ImageButton btnFechaProd;

            public ItemViewHolder(View itemView) {
                super(itemView);

                txt_sku = (TextView)itemView.findViewById(R.id.lblSku);
//                lblEstado = (TextView) itemView.findViewById(R.id.lblEstado);
                txtStock = (EditText) itemView.findViewById(R.id.txt_stock);
                txtStock_Bodega = (EditText) itemView.findViewById(R.id.txt_stock_bodega);
//                spSemana = (Spinner) itemView.findViewById(R.id.spSemana);
                //spEsSugerido = (Spinner) itemView.findViewById(R.id.sp_sugerido);
                spSug = (Spinner) itemView.findViewById(R.id.sp_sug);
                spTipoUnidades = (Spinner) itemView.findViewById(R.id.sp_tipo);
               spMotivo = (Spinner) itemView.findViewById(R.id.sp_motivo);
                txtSugerido = (EditText) itemView.findViewById(R.id.txt_cantidad_sugerida);
                spEntrega = (Spinner) itemView.findViewById(R.id.sp_entrega);
//                spCausal = (Spinner) itemView.findViewById(R.id.spCausal);
                txtObservacion = (Spinner) itemView.findViewById(R.id.sp_tiposug);
                txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
//                btnFechaProd = (ImageButton) itemView.findViewById(R.id.btnFechaProd);
                chkGuardar = (Button) itemView.findViewById(R.id.btnGuardar);
                txtSugerido.setHint("Cantidad");

                if(brand.equalsIgnoreCase("SURF")){
                    //spEsSugerido.setEnabled(false);
                }


                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listMotivo);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMotivo.setAdapter(dataAdapter);

                txtStock.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        String stock_und = editable.toString();
                        int stock = 0;


                        if (!stock_und.equals("")) {

                            stock = Integer.parseInt(stock_und);

                            if(stock <= 0){
                                String stock_bodega = txtStock_Bodega.getText().toString();
                                if(!stock_bodega.equals("")){
                                    if(Integer.parseInt(stock_bodega) > 0){
                                        Toast.makeText(getActivity(), "ABASTECER LA PERCHA Y TOMAR INVENTARIO", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            if(stock < 20){
                                String stock_bodega = txtStock_Bodega.getText().toString();
                                if(!stock_bodega.equals("")){
                                    if(Integer.parseInt(stock_bodega) == 0){
                                        spSug.setSelection(1);
                                        spSug.setEnabled(false);
                                    }else{
                                        //spSug.setSelection(0);
                                        spSug.setEnabled(true);
                                    }
                                }
                            }else{
                                //spSug.setSelection(0);
                                spSug.setEnabled(true);
                            }

                                /*if (stock == 0) {
                                    if(!brand.equalsIgnoreCase("SURF")) {
                                        spEsSugerido.setSelection(1);
                                    }
                                }*/
                        }

                    }


                });

                txtStock_Bodega.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String stock_und = s.toString();
                        int stock_bodega = 0;
                        if(!stock_und.equals("")){
                            stock_bodega = Integer.parseInt(stock_und);
                            // Validación nueva: stock en bodega no puede ser menor a 3
                            if(stock_bodega > 0 && stock_bodega < 3) {
                                Toast.makeText(getActivity(), "El stock en bodega no puede ser menor a 3", Toast.LENGTH_SHORT).show();
                                // Opcional: Limpiar el campo o deshabilitar guardar
                               // txtStock_Bodega.setText("");
                                return;
                            }


                            //cambio mpin
                            if(stock_bodega > 0){
                                String stock = txtStock.getText().toString();
                                if(!stock.equals("")){
                                    if(Integer.parseInt(stock) <= 0){
                                        Toast.makeText(getActivity(), "ABASTECER LA PERCHA Y TOMAR INVENTARIO", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            if(stock_bodega == 0){
                                String stock = txtStock.getText().toString();
                                if(!stock.equals("")){
                                    if(Integer.parseInt(stock) < 20){
                                        spSug.setSelection(1);
                                        spSug.setEnabled(false);
                                    }else{
                                        //spSug.setSelection(0);
                                        spSug.setEnabled(true);
                                    }
                                }
                            }else{
                                //spSug.setSelection(0);
                                spSug.setEnabled(true);
                            }

                        }
                    }
                });


//                spCausal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        if (!spCausal.getSelectedItem().toString().trim().equalsIgnoreCase("OTROS")) {
//                            txtOtros.setEnabled(false);
//                            txtOtros.setText("-");
//                        }else{
//                            txtOtros.setEnabled(true);
//                            txtOtros.setText("");
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {}
//                });

                txtObservacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String tipo = txtObservacion.getSelectedItem().toString();
                        txtSugerido.setHint("Cantidad "+ tipo);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                spSug.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spSug.getSelectedItem().toString().trim().equalsIgnoreCase("Si")) {
                            spEntrega.setSelection(0);
                            spEntrega.setEnabled(true);
                            spMotivo.setSelection(0); //cambios cuando le de click en motivo sugerido
                            spMotivo.setEnabled(true);
                            txtSugerido.setText("");
                            txtSugerido.setEnabled(true);
                        }else if (spSug.getSelectedItem().toString().trim().equalsIgnoreCase("No")){
                            spEntrega.setSelection(0);
                            spEntrega.setEnabled(false);
                            txtSugerido.setText("");
                            txtSugerido.setEnabled(false);
                            spMotivo.setSelection(0); //cambios cuando le de click en motivo sugerido
                            spMotivo.setEnabled(false);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                /*spEsSugerido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spEsSugerido.getSelectedItem().toString().trim().equalsIgnoreCase("SI")) {
                            spEntrega.setEnabled(true);
                            txtSugerido.setEnabled(true);
                            spTipoUnidades.setEnabled(true);
                            List<String> operadores = handler.getCausales();
                            spTipoUnidades.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, operadores));
                            Log.i("CAUSALES", String.valueOf(operadores.size()));
                          //  List<String> operadores = handler.getCausales();
                          //  spTipoUnidades.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, operadores));
                          //  txtSugerido.setText("");
                            txtObservacion.setEnabled(true);
                        }else{
                            ArrayList<String> operadores = new ArrayList<>();
                            operadores.add("Seleccione");
                            spTipoUnidades.setAdapter(new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, operadores));

                            spEntrega.setEnabled(false);
                            txtSugerido.setEnabled(false);
                            spTipoUnidades.setEnabled(false);
                            txtObservacion.setEnabled(false);
                            spTipoUnidades.setSelection(0);
                            spEntrega.setSelection(0);
                            txtObservacion.setSelection(0);
                            txtSugerido.setText("");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });*/

//                btnFechaProd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //finalv.txtCantidad.setText("PRUEBA");
//                        final Calendar calendar = Calendar.getInstance();
//                        int anio = calendar.get(Calendar.YEAR);
//                        int mes = calendar.get(Calendar.MONTH);
//                        int dia = calendar.get(Calendar.DAY_OF_MONTH);
//
//                        DatePickerDialog from_dateListener = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                                Date date = null;
//                                try {
//                                    date = dateFormat.parse(dayOfMonth + "/" + (month+1) + "/" + year);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                                String outDate = dateFormat.format(date);
//                                txtFecha.setText(outDate);
//                                dataAdapter.notifyDataSetChanged();
//                            }
//                        },anio,mes,dia);
////                        from_dateListener.getDatePicker().setSpinnersShown(true);
////                        from_dateListener.getDatePicker().setCalendarViewShown(false);
//                        from_dateListener.show();
//                    }
//                });

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view;

                        TextView skup = null;
                        EditText stockp = null;
                        EditText stockBod = null;
                        Spinner semanap = null;
                        Spinner sugeridop = null;
                        Spinner tipop = null;
                        EditText cantidadp = null;
                        Spinner entregap = null;
                        Spinner observacionp = null;
                        Spinner sug = null;
                        Spinner motivop = null;
//                        TextView caducidadp = null;

                        Boolean stockVacio = false;
                        Boolean stockEsCero = false;
                        Boolean stockEsCeroSugeridoNo = false;
                        Boolean semanaVacio = false;
                        Boolean sugeridoVacio = false;
                        Boolean tipoObsVacio = false;
                        Boolean tipoSugeridoVacio = false;
                        Boolean cantidadVacio = false;
                        Boolean cantSugeridoEsCero = false;
                        Boolean entregaVacio = false;
                        Boolean motivoVacio = false;

                        Boolean stockBodVacio = false;
                        Boolean tipo_sb = false;
                        Boolean suge = false;
                        Boolean tipo_sug = false;
                        Boolean cant_sug = false;
                        Boolean val1 = false;
                        int stock_supera = -1;
                        Boolean sug_cero = false;
//                        Boolean caducidadVacio = false;


                        int tiempo_vacio = 0;

                        int listLength = listview.getChildCount();
                        valueOfTextViewSku = new String[listLength];
                        valueOfEditText = new String[listLength];
                        valueOfSpinner = new String[listLength];
                        valueOfSpinner2 = new String[listLength];
                        valueOfSpinnerT = new String[listLength];
                        valueOfEditText2 = new String[listLength];
                        valueOfSpinner3 = new String[listLength];
                        valueOfEditText3 = new String[listLength];
                        valueOfEditText4 = new String[listLength];
                        valueOfSpinner4 = new String[listLength];
                        valueOfSpinner5 = new String[listLength];
//                        valueOfTextView = new String[listLength];

                        for (int i = 0; i < listLength; i++) {
                            view = listview.getChildAt(i);

                            skup = (TextView) view.findViewById(R.id.lblSku);
                            stockp = (EditText) view.findViewById(R.id.txt_stock);
                            stockBod = (EditText) view.findViewById(R.id.txt_stock_bodega);
//                            semanap = (Spinner) view.findViewById(R.id.spSemana);
                            sugeridop = (Spinner) view.findViewById(R.id.sp_sugerido);
                            tipop = (Spinner) view.findViewById(R.id.sp_tipo);
                            cantidadp = (EditText) view.findViewById(R.id.txt_cantidad_sugerida);
                            entregap = (Spinner) view.findViewById(R.id.sp_entrega);
                            observacionp = (Spinner) view.findViewById(R.id.sp_tiposug);
                            sug = (Spinner) view.findViewById(R.id.sp_sug);
                            motivop = (Spinner) view.findViewById(R.id.sp_motivo);
//                            caducidadp = (TextView) view.findViewById(R.id.txtFecha);


                            String valor_sku = skup.getText().toString();
//                            String valor_semana = semanap.getSelectedItem().toString()
                            String valor_stock = stockp.getText().toString();
                            String valor_stock_bodega = stockBod.getText().toString();
                            String valor_sugerido = sugeridop.getSelectedItem().toString();
                            String valor_tipo = tipop.getSelectedItem().toString();
                            String valor_cantidad = cantidadp.getText().toString();
                            String valor_entrega = entregap.getSelectedItem().toString();
                            String valor_observacion = observacionp.getSelectedItem().toString();
                            String valor_sug = sug.getSelectedItem().toString();
                            String valor_motivo = motivop.getSelectedItem().toString();
//                            String valor_caducidad = caducidadp.getText().toString();

                            valueOfTextViewSku[i] = valor_sku;
                            valueOfEditText[i] = valor_stock;
//                            valueOfSpinner[i] = valor_semana;
                            valueOfSpinner2[i] = valor_sugerido;
                            valueOfSpinnerT[i] = valor_tipo;
                            valueOfEditText2[i] = valor_cantidad;
                            valueOfSpinner3[i] = valor_entrega;
                            valueOfEditText3[i] = valor_observacion;
                            valueOfEditText4[i] = valor_stock_bodega;
                            valueOfSpinner4[i] = valor_sug;
                            valueOfSpinner5[i] = valor_motivo;
//                            valueOfTextView[i] = valor_caducidad;

                            if(!valor_stock.equalsIgnoreCase("")){
                                //VALIDACIONES NUEVAS
                                //stock percha
                                if (valueOfEditText[i].equalsIgnoreCase("")){
                                    stockVacio = true;
                                }

                                //stock bodega
                                if (valueOfEditText4[i].equalsIgnoreCase("")){
                                    stockBodVacio = true;
                                }

                                //tipo stock bodega
                                if (valueOfSpinnerT[i].equalsIgnoreCase("Seleccione")){
                                    tipo_sb = true;
                                }

                                //spinner sugerido si/no
                                if (valueOfSpinner4[i].equalsIgnoreCase("Seleccione")){
                                    suge = true;
                                }


                                //VAL 1: SI STOCK BODEGA ES MAYOR A CERO ENTONCES STOCK PERCHA DEBE SER MAYOR A CERO
                                //Log.i("caasq",""+valueOfEditText4[i]);
                                //Log.i("caasq",""+valueOfEditText[i]);
                                if(!valueOfEditText4[i].equalsIgnoreCase("") && !valueOfEditText[i].equalsIgnoreCase("")){
                                    if(Integer.parseInt(valueOfEditText4[i]) > 0 && Integer.parseInt(valueOfEditText[i]) <= 0){
                                        val1 = true;
                                    }
                                }

                                Log.i("caasq",""+val1);



                                //validacion SI/NO sugeridos
                                if(valueOfSpinner4[i].equalsIgnoreCase("Si")){
                                    //cant. sugeridos
                                    if (valueOfEditText2[i].equalsIgnoreCase("")){
                                        cant_sug = true;
                                    }

                                    //tipo sugeridos
                                    if (valueOfSpinner3[i].equalsIgnoreCase("Seleccione")){
                                        tipo_sug = true;
                                    }

                                    //motivo
                                    if (valueOfSpinner5[i].equalsIgnoreCase("Seleccione")){
                                        motivoVacio = true;
                                    }

                                    //cant. sugeridos
                                    if (!valueOfEditText2[i].equalsIgnoreCase("")){
                                        if (Integer.parseInt(valueOfEditText2[i]) < 1){
                                            sug_cero = true;
                                        }
                                    }

                                }

                                //stock percha validacion
                                int n = Integer.parseInt(valueOfEditText[i]);
                                if (n < 10){
                                    stock_supera = n;
                                }

                            }




                            //VALIDACIONES DISPONIBLES SOLO PARA LOS ITEMS LLENOS
                            Log.i("cass",""+valor_stock+" " + valor_sugerido);
                            if(!valor_stock.equalsIgnoreCase("") || !valor_sugerido.equalsIgnoreCase("Seleccione")) { /// || !valor_caducidad.equalsIgnoreCase("")

                                if (valueOfEditText[i].equalsIgnoreCase("")){
                                    stockVacio = true;
                                }

                                /*
                                if(!stockVacio){
                                    if (Integer.parseInt(valueOfEditText[i]) == 0){
                                        stockEsCero = true;
                                    }
                                }*/

//                                if (valueOfSpinner[i].equalsIgnoreCase("Semana")) {
//                                    semanaVacio = true;
//                                }
                                if(!brand.equalsIgnoreCase("SURF")){
                                    if (valueOfSpinner2[i].equalsIgnoreCase("Seleccione")) {
                                        sugeridoVacio = true;
                                    }
                                }else{
                                    valueOfEditText3[i] = "NA";

                                }

                                if (valor_sugerido.equalsIgnoreCase("SI")) {

                                    if (valueOfEditText2[i].equalsIgnoreCase("")) {
                                        cantidadVacio = true;
                                    }

                                    if(!cantidadVacio){
                                        if (Integer.parseInt(valueOfEditText2[i]) == 0){
                                            cantSugeridoEsCero = true;
                                        }
                                    }

                                    if (valueOfSpinner3[i].equalsIgnoreCase("Seleccione")) {
                                        entregaVacio = true;
                                    }
                                    if (valueOfSpinnerT[i].equalsIgnoreCase("Seleccione")) {
                                        tipoObsVacio = true;
                                    }

                                    if(valueOfEditText3[i].equalsIgnoreCase("Seleccione")){
                                        tipoSugeridoVacio = true;
                                    }

                                    if(valueOfSpinner5[i].equalsIgnoreCase("Seleccione")){
                                        motivoVacio = true;
                                    }

                                } else if (valor_sugerido.equalsIgnoreCase("NO")) {


                                    if(!stockVacio) {
                                        if (Integer.parseInt(valueOfEditText[i]) == 0) {
                                            stockEsCeroSugeridoNo = true;
                                        }
                                    }

                                    valueOfEditText2[i] = "NA";
                                    valueOfEditText3[i] = "NA";
                                    valueOfSpinner3[i] = "NA";
                                    valueOfSpinner5[i] = "NA";
                                    valueOfSpinnerT[i] = "NA";

                                }

//                                if (!valor_stock.equalsIgnoreCase("0") && !brand.equalsIgnoreCase("SURF")) {
//                                    if (valueOfTextView[i].equalsIgnoreCase("")) {
//                                        caducidadVacio = true;
//                                    }
//                                } else {
//                                    valueOfTextView[i] = "NA";
//                                }
                            }

                        }

//                        String razones = spRazones.getSelectedItem().toString();




                        Log.i("SPINNERS", categoria +" - "+ subcategoria +" - "+ brand);
                        int contador_llenos = 0;
                        if (
//                                !categoria.equalsIgnoreCase("SELECCIONE") && !categoria.equalsIgnoreCase("")
//                                && !subcategoria.equalsIgnoreCase("SELECCIONE") && !subcategoria.equalsIgnoreCase("")
//                                &&
                                        !brand.equalsIgnoreCase("SELECCIONE") && !brand.equalsIgnoreCase("")) {
                            Log.i("CANTIDAD ESTA VACIO", String.valueOf(cantidadVacio));
                            if(esFormularioValido2(stockVacio, stockBodVacio, tipo_sb, suge, cant_sug, tipo_sug, val1, sug_cero)){

                                if(stock_supera != -1){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                                    builder.setTitle("Aviso");
                                    builder.setMessage("¿ESTA SEGURO QUE LAS UNIDADES REPORTADAS EN PERCHA CORRESPONDEN A "+ stock_supera +" UNIDADES?");
                                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            insertData();
                                        }
                                    });

                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            return;
                                        }
                                    });

                                    AlertDialog ad = builder.create();
                                    ad.show();
                                }else{
                                    insertData();
                                }
                            }
                            //if(esFormularioValido(stockVacio,stockEsCero,stockEsCeroSugeridoNo,tipoSugeridoVacio,cantidadVacio,cantSugeridoEsCero,entregaVacio,sugeridoVacio,tipoObsVacio)){  //!caducidadVacio &&
                            //    insertData();
                            //} else {

//                                if (stockVacio) {
//                                    Toast.makeText(getContext(), "Llene todos los campos stock", Toast.LENGTH_SHORT).show();
//                                }
//                               if (semanaVacio) {
//                                   Toast.makeText(getContext(), "Llene todos los campos semana", Toast.LENGTH_SHORT).show();
//
//                                if(stockEsCero){
//                                    Toast.makeText(getContext(), "El stock no deber ser cero", Toast.LENGTH_SHORT).show();
//                                }
//
//                                if (cantidadVacio) {
//                                    Toast.makeText(getContext(), "Llene todos los campos cantidad sugerido", Toast.LENGTH_SHORT).show();
//                                }
//                                if (sugeridoVacio) {
//                                    Toast.makeText(getContext(), "Seleccione sugerido", Toast.LENGTH_SHORT).show();
//                                }
//                                if (tipoVacio) {
//                                    Toast.makeText(getContext(), "Seleccione tipo", Toast.LENGTH_SHORT).show();
//                                }
//                                if (entregaVacio) {
//                                    Toast.makeText(getContext(), "Llene todos los tiempos de entrega", Toast.LENGTH_SHORT).show();
//                                }
//                               if (caducidadVacio) {
//                                    Toast.makeText(getContext(), "Llene todos los campos caducidad", Toast.LENGTH_SHORT).show();
//                               }
                            //}

                        }else{
                            Toast.makeText(getContext(),"Ingrese una categoria, subcategoria y marca.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    private boolean esFormularioValido2(boolean stockVacio, boolean stockBodVacio, boolean tipo_sb, boolean suge,
                                                        boolean cant_sug, boolean tipo_sug, boolean val1, Boolean sug_cero){

                        Log.i("valorescas",""+stockVacio);
                        Log.i("valorescas",""+stockBodVacio);
                        Log.i("valorescas",""+tipo_sb);
                        Log.i("valorescas",""+suge);
                        Log.i("valorescas",""+cant_sug);
                        Log.i("valorescas",""+tipo_sug);
                        Log.i("valorescas",""+val1);
                      //  Log.i("valorescas m",""+motivo);

                        if (stockVacio) {
                            Toast.makeText(getContext(), "Llene el campo: Stock Percha", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (tipo_sb) {
                            Toast.makeText(getContext(), "Llene el campo: Tipo Stock Bodega", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (suge) {
                            Toast.makeText(getContext(), "Llene el campo: Sugerido", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (stockBodVacio) {
                            Toast.makeText(getContext(), "Llene el campo Stock Bodega", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        String stockBodegaStr = txtStock_Bodega.getText().toString();
                        if(!stockBodegaStr.isEmpty()){
                            try {
                                int stockBodega = Integer.parseInt(stockBodegaStr);
                                if(stockBodega > 0 && stockBodega < 3) {
                                    Toast.makeText(getContext(), "El stock en bodega no puede ser menor a 3", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            } catch (NumberFormatException e) {
                                // Si no es un número válido
                                Toast.makeText(getContext(), "Stock bodega no es un número válido", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }

                        if(val1){
                            Toast.makeText(getContext(), "ABASTECER LA PERCHA Y TOMAR INVENTARIO", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if(cant_sug){
                            Toast.makeText(getContext(), "Llene el campo Cant. Sugeridos", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if(sug_cero){
                            Toast.makeText(getContext(), "Cant. Sugeridos debe ser mayor a cero", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if(tipo_sug){
                            Toast.makeText(getContext(), "Llene el campo Tipo Sugeridos", Toast.LENGTH_SHORT).show();
                            return false;
                        }

//                        if(motivo){
//                            Toast.makeText(getContext(), "Llene el campo Motivo", Toast.LENGTH_SHORT).show();
//                            return false;
//                        }




                        return true;
                    }


                    private boolean esFormularioValido(Boolean stockVacio, Boolean stockEsCero,Boolean stockEsCeroSugeridoNo,Boolean tipoSugeridoVacio, Boolean cantidadVacio,Boolean cantSugeridoEsCero, Boolean entregaVacio, Boolean sugeridoVacio, Boolean tipoObsVacio) {

                        if (stockVacio) {
                            Toast.makeText(getContext(), "Llene el campo Stock Percha", Toast.LENGTH_SHORT).show();
                            return false;
                        }
/*
                        if(stockEsCero){
                            Toast.makeText(getContext(), "El stock no debe ser cero", Toast.LENGTH_SHORT).show();
                            return false;
                        }
*/

                        if(stockEsCeroSugeridoNo){
                            Toast.makeText(getContext(), "El stock no debe ser cero", Toast.LENGTH_SHORT).show();
                            return false;
                        }


                        /*if (sugeridoVacio) {
                            Toast.makeText(getContext(), "Seleccione sugerido", Toast.LENGTH_SHORT).show();
                            return false;
                        }*/

                        if (tipoObsVacio) {
                            Toast.makeText(getContext(), "Seleccione tipo de observacion", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if(tipoSugeridoVacio){
                            Toast.makeText(getContext(), "Seleccione el tipo de sugerido", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (cantidadVacio) {
                            Toast.makeText(getContext(), "Llene todos los campos cantidad sugerido", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (cantSugeridoEsCero) {
                            Toast.makeText(getContext(), "La cantidad de sugerido no debe ser cero", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (entregaVacio) {
                            Toast.makeText(getContext(), "Llene todos los tiempos de entrega", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        return true;
                    }


                });

//                chkGuardar.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        if (((CheckBox)v).isChecked()) {
//                            String skuSelected = txt_sku.getText().toString();
//                            String stock = txtStock.getText().toString().trim();
//                            String semana = spSemana.getSelectedItem().toString().trim();
//                            String esSugerido = spEsSugerido.getSelectedItem().toString().trim();
//                            String sugerido = txtSugerido.getText().toString().trim();  //cantidad sugerido
//                            String entrega = spEntrega.getSelectedItem().toString().trim();
////                            String causal = spCausal.getSelectedItem().toString().trim();
//                            String observacion = txtObservacion.getText().toString().trim();
//                            String fecha_caducidad = txtFecha.getText().toString().trim();
//
//                            if (Integer.parseInt(stock) <= 10000){
//                                if (esSugerido.equalsIgnoreCase("SI")){
//                                    if (!stock.trim().isEmpty() &&
//                                            !semana.trim().isEmpty() &&
//                                            !sugerido.trim().isEmpty() &&
//                                            !semana.equals("Seleccione") &&
//                                            !entrega.equals("Seleccione Tiempo") &&
//                                            !observacion.trim().isEmpty() &&
//                                            !fecha_caducidad.trim().isEmpty()) {
//                                        insertData(skuSelected, semana, esSugerido, fecha_caducidad, sugerido, entrega, "", observacion, stock);
//                                    } else {
//                                        Toast.makeText(getContext(),"Debe seleccionar todos los campos",Toast.LENGTH_LONG).show();
//                                        chkGuardar.setChecked(false);
//                                    }
//                                } else if (esSugerido.equalsIgnoreCase("NO")) {
//                                    if (!stock.trim().isEmpty() &&
//                                            !semana.trim().isEmpty() &&
//                                            !observacion.trim().isEmpty() &&
//                                            !fecha_caducidad.trim().isEmpty()) {
//                                        insertData(skuSelected, semana, esSugerido, fecha_caducidad, "-", "-", "", observacion, stock);
//                                    } else {
//                                        Toast.makeText(getContext(),"Debe seleccionar todos los campos",Toast.LENGTH_LONG).show();
//                                        chkGuardar.setChecked(false);
//                                    }
//                                } else {
//                                    Toast.makeText(getContext(),"Debe seleccionar sugerido",Toast.LENGTH_LONG).show();
//                                    chkGuardar.setChecked(false);
//                                }
//                            } else {
//                                Toast.makeText(getContext(),"El stock no puede ser mayor a 10 000",Toast.LENGTH_LONG).show();
//                                chkGuardar.setChecked(false);
//                            }
//
//
//                        }
//                    }
//                });
            }



        }
    }
}