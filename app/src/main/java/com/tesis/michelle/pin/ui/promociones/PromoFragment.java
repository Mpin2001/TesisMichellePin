package com.tesis.michelle.pin.ui.promociones;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.tesis.michelle.pin.CameraActivity;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Adaptadores.ListViewAdapter;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Contracts.ContractInsertPromocion;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.ImageMark;
import com.tesis.michelle.pin.Utils.RequestPermissions;

import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PromoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private static final org.apache.commons.logging.Log log = LogFactory.getLog(PromoFragment.class);
    ImageButton btnIniPromo;
    ImageButton btnFinPromo;

    ArrayList<String> array_canal = new ArrayList<>();

   // private Spinner spCategoria;

    private TextView lPVCanterior;

    private EditText txtPVCanterior;



    //private Spinner spBrand;
    private Spinner spTipoPromocion;
    private Spinner spDescripcionPromocion;
    //private Spinner spVigencia;
   // private Spinner spSubcategoria;
    private EditText txtMecanica;
    private EditText txtOtrasMarcas;
    private Spinner spMarca;
    private Spinner spCampanaPromocion;

    private Spinner spSKU;
    private TextView txtInicioPromo;
    private TextView txtFinPromo;

    private String inicioPromoMarcaPropia = "";
    private String finPromoMarcaPropia = "";
    private String inicioPromoCompetencia = "";
    private String finPromoCompetencia = "";
    private TextView txtAnterior;
    private TextView txtActual;

    private CheckBox chkStock;
    private Button btnGuardar;
    //private ImageButton btnFecha;
    LinearLayout layoutPVCanterior;
    LinearLayout layoutPVCactual;
    LinearLayout lLayoutMecanica;
    LinearLayout lLayoutCampana;

    CheckBox checkNofoto;

    LinearLayout layoutCameraSection;

    LinearLayout idNofoto;
    private Button btnPropia;
    private Button btnCompetencia;
    private LinearLayout linear_otros;

    DatabaseHelper handler;
    public static Geocoder geocoder;

    TextView empty;
    ListView listview;

    //List<String> listProductos;
    ListViewAdapter dataAdapter;

    private static LocationManager locationManager;
    private static LocationListener locationListener;
    public static String latitud = "";
    public static String longitud = "";
    public static List<Address> addresses;
    public EditText direccion;

    String id_pdv, categoria, brand, fechaventas, tipo_promocion, vigencia, mecanica, mecanica_generalizada, observaciones, subcategoria, marca, descripcion;
    private String user,codigo_pdv, punto_venta,fecha,hora,format,canal, channel_segment, cadena, campana;
    private boolean vaFoto;

    public static ImageView imageView;
    private Bitmap bitmapfinal;
    Bitmap bitmap;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private final String CARPETA_RAIZ="bassaApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Competencia";
    String path;
    ImageButton btnCamera;

    private String tipo = "MARCA_PROPIA";
    private final String fabricante = "Bassa";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_promo, container, false);

        LoadData();

        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

        //startService(new Intent(getContext(), MyService.class));


        declaracionElementos(rootView);
        inicioPromoMarcaPropia = "";
        finPromoMarcaPropia = "";
        inicioPromoCompetencia = "";
        finPromoCompetencia = "";


        CheckBox checkNofoto = rootView.findViewById(R.id.checkNofoto);
        LinearLayout layoutCameraSection = rootView.findViewById(R.id.layoutCameraSection);

        if (checkNofoto != null && layoutCameraSection != null) {
            checkNofoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!tipo.equalsIgnoreCase("COMPETENCIA")) {
                        if (isChecked) {
                            limpiarImagen();
                            layoutCameraSection.setVisibility(View.GONE);
                        } else {
                            layoutCameraSection.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            if (!tipo.equalsIgnoreCase("COMPETENCIA")) {
                layoutCameraSection.setVisibility(checkNofoto.isChecked() ? View.GONE : View.VISIBLE);
                if (checkNofoto.isChecked()) {
                    limpiarImagen();
                }
            } else {
                layoutCameraSection.setVisibility(View.VISIBLE);
            }
        }


        btnCamera.setOnClickListener(this);
        btnIniPromo.setOnClickListener(this);
        btnFinPromo.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnPropia.setOnClickListener(this);
        btnCompetencia.setOnClickListener(this);

//        filtrarCategoria(tipo, fabricante);
        filtrarDescripcionPromocion(canal, cadena);
//        filtrarTipoPromocion(canal);




        array_canal.add(canal);
        //spSKU.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, array_canal));

        //filtrarMarca();
        //llenarSpinners();

        ListView listView = (ListView) rootView.findViewById(R.id.lvSKUCode);
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

    private void declaracionElementos(ViewGroup rootView) {
     //   spCategoria= (Spinner) rootView.findViewById(R.id.spCategoria);

        lPVCanterior= (TextView) rootView.findViewById(R.id.lPVCanterior);

        spSKU = (Spinner) rootView.findViewById(R.id.spSKU);
        //spBrand= (Spinner) findViewById(R.id.spBrand);
      //  spSubcategoria = (Spinner) rootView.findViewById(R.id.spSubcategoria);
        spMarca= (Spinner) rootView.findViewById(R.id.spMarca);
        txtOtrasMarcas= (EditText) rootView.findViewById(R.id.txtOtrasMarcas);

        empty = (TextView) rootView.findViewById(R.id.recyclerview_data_empty);
        listview = (ListView)rootView.findViewById(R.id.lvSKUCode);

        spTipoPromocion = (Spinner)rootView.findViewById(R.id.spTipoPromocion);
        spDescripcionPromocion = (Spinner)rootView.findViewById(R.id.spDescripcionPromocion);
        spCampanaPromocion = (Spinner)rootView.findViewById(R.id.spCampanaPromocion);

        //spVigencia = (Spinner)dialogView.rootView.findViewById(R.id.spVigencia);
        txtMecanica = (EditText) rootView.findViewById(R.id.txtMecanica);
        txtInicioPromo = (TextView) rootView.findViewById(R.id.txtInicioPromo);
        txtFinPromo = (TextView) rootView.findViewById(R.id.txtFinPromo);
        txtAnterior = (TextView) rootView.findViewById(R.id.txtPVCanterior);
        txtActual = (TextView) rootView.findViewById(R.id.txtPVCactual);
        chkStock = (CheckBox) rootView.findViewById(R.id.chkStock);

        btnIniPromo = (ImageButton) rootView.findViewById(R.id.btnInicioPromo);
        btnFinPromo = (ImageButton) rootView.findViewById(R.id.btnFinPromo);

        imageView = (ImageView) rootView.findViewById(R.id.ivFotoExhibiciones);
        btnCamera = (ImageButton) rootView.findViewById(R.id.ibCargarFotoExhibiciones);
        btnGuardar = (Button) rootView.findViewById(R.id.btnGuardar);

        layoutPVCanterior = (LinearLayout) rootView.findViewById(R.id.layoutPVCanterior);
        linear_otros = (LinearLayout) rootView.findViewById(R.id.linear_otros);
        layoutPVCactual = (LinearLayout) rootView.findViewById(R.id.layoutPVCactual);
        lLayoutMecanica = (LinearLayout) rootView.findViewById(R.id.lLayoutMecanica);
        lLayoutCampana = (LinearLayout) rootView.findViewById(R.id.lLayoutCampana);
        layoutCameraSection = (LinearLayout) rootView.findViewById(R.id.layoutCameraSection);
        idNofoto = (LinearLayout) rootView.findViewById(R.id.idNofoto);
        btnPropia  = (Button) rootView.findViewById(R.id.btnPropia);
        btnCompetencia  = (Button) rootView.findViewById(R.id.btnCompetencia);
        checkNofoto = (CheckBox) rootView.findViewById(R.id.checkNofoto);
        txtPVCanterior = (EditText) rootView.findViewById(R.id.txtPVCanterior);
    }

    public void filtrarCategoria(String tipo, String fabricante) {
        Log.i("JJJ","" + tipo);
        Log.i("JJJ","" + fabricante);
        Log.i("JJJ","" + canal);
        Log.i("JJJ","" + cadena);
        List<String> operadores = handler.getCategoriaPromocion(tipo, fabricante, canal, cadena);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  spCategoria.setAdapter(dataAdapter);
       // spCategoria.setOnItemSelectedListener(this);
    }

    /*public void filtrarSubCategoria(String status, String logro, String subcategoria, String presentacion) {
        List<String> operadores = handler.getSector2Promocion(status,logro,subcategoria,presentacion);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSector.setAdapter(dataAdapter);
        spSector.setOnItemSelectedListener(this);
    }*/

    public void filtrarSubcategoria(String categoria, String tipo, String fabricante) {
        List<String> operadores = handler.getSubcategoriaPromocion(categoria, tipo, fabricante, canal, cadena);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     //   spSubcategoria.setAdapter(dataAdapter);
      //  spSubcategoria.setOnItemSelectedListener(this);
    }

    public void filtrarMarca(String categoria, String subcategoria, String tipo, String fabricante) {
        List<String> operadores = handler.getMarcaPromocion(tipo, fabricante, canal, cadena);
        Log.i("TRAE EN MARCA", String.valueOf(operadores));
        if (operadores.size()==2) {
            if(!operadores.get(1).toString().equalsIgnoreCase("OTROS")){
                operadores.remove(0);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void filtrarSKU( String marca, String tipo, String fabricante) {
        List<String> operadores = handler.getSKUPromocion2( marca, tipo, fabricante, canal, cadena);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSKU.setAdapter(dataAdapter);
        spSKU.setOnItemSelectedListener(this);
    }

    public void filtrarTipoPromocion(String canal) {
        List<String> operadores = handler.getTipoPromocion(canal);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoPromocion.setAdapter(dataAdapter);
        spTipoPromocion.setOnItemSelectedListener(this);
    }

    //filtros promoción
    public void filtrarDescripcionPromocion(String canal, String cadena) {
        spDescripcionPromocion.setAdapter(null);
        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            List<String> operadores = handler.getDescripcionPromocion(canal);
            if (operadores.size() == 2) {
                operadores.remove(0);
            }
            for (int i = 0; i < operadores.size(); i++) {
                if (operadores.get(i).equalsIgnoreCase("PRODUCTO GRATIS") || operadores.get(i).equalsIgnoreCase("PROMOCIONAL GRATIS")) {
                    txtAnterior.setEnabled(true);
                    txtActual.setEnabled(true);
                } else {
                    txtAnterior.setEnabled(true);
                    txtActual.setEnabled(true);
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, operadores);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDescripcionPromocion.setAdapter(dataAdapter);
            spDescripcionPromocion.setOnItemSelectedListener(this);
        } else{
            List<String> operadores = handler.getDescripcionMarcaPropia(canal, cadena, channel_segment);
            if (operadores.size() == 2) {
                operadores.remove(0);
            }
            for (int i = 0; i < operadores.size(); i++) {
                if (operadores.get(i).equalsIgnoreCase("PRODUCTO GRATIS") || operadores.get(i).equalsIgnoreCase("PROMOCIONAL GRATIS")) {
                    txtAnterior.setEnabled(true);
                    txtActual.setEnabled(true);
                } else {
                    txtAnterior.setEnabled(true);
                    txtActual.setEnabled(true);
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, operadores);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDescripcionPromocion.setAdapter(dataAdapter);
            spDescripcionPromocion.setOnItemSelectedListener(this);
        }
    }

    public void filtrarCampanaPromocion(String canal, String cadena, String tipo_promocion) {
        List<String> operadores = handler.getCampanaPromocion(canal, cadena, channel_segment, tipo_promocion);
        Log.i("DESCRIPCION PROMO 3", operadores.size() + "");
        if (operadores.size() == 2) {
            operadores.remove(0);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCampanaPromocion.setAdapter(dataAdapter);
        spCampanaPromocion.setOnItemSelectedListener(this);

    }

//    public void filtrarCategoriaPromocion(String canal, String cadena, String tipo_promocion, String campana) {
//
//        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
//            List<String> operadores = handler.getCategoriaPromocion(tipo_promocion, fabricante, canal, cadena);
//            Log.i("DESCRIPCION PROMO 3", operadores.size() + "");
//            if (operadores.size() == 2) {
//                operadores.remove(0);
//            }
//
//            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
//                    android.R.layout.simple_spinner_item, operadores);
//            dataAdapter
//                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spCategoria.setAdapter(dataAdapter);
//            spCategoria.setOnItemSelectedListener(this);
//        } else {
//            List<String> operadores = handler.getCategoriaPromocion2(canal, cadena, channel_segment, tipo_promocion, campana);
//            Log.i("DESCRIPCION PROMO 3", operadores.size() + "");
//            if (operadores.size() == 2) {
//                operadores.remove(0);
//            }
//
//            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
//                    android.R.layout.simple_spinner_item, operadores);
//            dataAdapter
//                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spCategoria.setAdapter(dataAdapter);
//            spCategoria.setOnItemSelectedListener(this);
//        }
//
//    }


//    public void filtrarSubcategoriaPromocion(String canal, String cadena, String tipo_promocion, String campana, String categoria) {
//        spSubcategoria.setAdapter(null);
//        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
//            List<String> operadores = handler.getSubcategoriaPromocion(categoria, tipo_promocion, fabricante, canal, cadena);
//            Log.i("SUBACTEGORIA PROMO 4", operadores.size() + "");
//            if (operadores.size() == 2) {
//                operadores.remove(0);
//            }
//
//            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
//                    android.R.layout.simple_spinner_item, operadores);
//            dataAdapter
//                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spSubcategoria.setAdapter(dataAdapter);
//            spSubcategoria.setOnItemSelectedListener(this);
//        } else {
//            List<String> operadores = handler.getSubcategoriaPromocion2(canal, cadena, channel_segment, tipo_promocion, campana, categoria);
//            Log.i("SUBCATEGORIA PROMO else", operadores.size() + "");
//            if (operadores.size() == 2) {
//                operadores.remove(0);
//            }
//
//            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
//                    android.R.layout.simple_spinner_item, operadores);
//            dataAdapter
//                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spSubcategoria.setAdapter(dataAdapter);
//            spSubcategoria.setOnItemSelectedListener(this);
//        }
//
//    }


    public void filtrarMarcaPromocion(String canal, String cadena, String tipo_promocion, String campana) {
        spMarca.setAdapter(null);
        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            List<String> operadores = handler.getMarcaPromocion( tipo_promocion, fabricante, canal, cadena);
            Log.i("MARCA PROMO 4", operadores.size() + "");
            if (operadores.size() == 2) {
                operadores.remove(0);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, operadores);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMarca.setAdapter(dataAdapter);
            spMarca.setOnItemSelectedListener(this);
        } else {
            List<String> operadores = handler.getMarcaPromocion2(canal, cadena, channel_segment, tipo_promocion, campana);
            Log.i("MARCA PROMO else", operadores.size() + "");
            if (operadores.size() == 2) {
                operadores.remove(0);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, operadores);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMarca.setAdapter(dataAdapter);
            spMarca.setOnItemSelectedListener(this);
        }

    }


    public void filtrarSkuPromocion(String canal, String cadena, String tipo_promocion, String campana, String marca) {
        spSKU.setAdapter(null);
        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            List<String> operadores = handler.getSKUPromocion2( marca, tipo_promocion, fabricante, canal, cadena);
            Log.i("SKU PROMO 4", operadores.size() + "");
            if (operadores.size() == 2) {
                operadores.remove(0);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, operadores);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSKU.setAdapter(dataAdapter);
            spSKU.setOnItemSelectedListener(this);
        } else {
            List<String> operadores = handler.getSkuPromocion3(canal, cadena, channel_segment, tipo_promocion, campana, marca);
            Log.i("SKU PROMO else", operadores.size() + "");
            if (operadores.size() == 2) {
                operadores.remove(0);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, operadores);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSKU.setAdapter(dataAdapter);
            spSKU.setOnItemSelectedListener(this);
        }

    }




    public void LoadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV,Constantes.NODATA);
        user=sharedPreferences.getString(Constantes.USER,Constantes.NODATA);

        user = sharedPreferences.getString(Constantes.USER, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO, Constantes.NODATA);
        id_pdv = sharedPreferences.getString(Constantes.PHARMA_ID, Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        cadena = sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
        channel_segment = sharedPreferences.getString(Constantes.CHANNEL_SEGMENT,Constantes.NODATA);
        vaFoto = sharedPreferences.getBoolean(Constantes.VAFOTO,false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String model = adapterView.getItemAtPosition(i).toString();
        //alertDialog(model);
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

                        if (!txtFinPromo.getText().toString().trim().isEmpty()) {
                            Log.i("ENTRA", "FIN PROMO NO VACIO");
                            String fInicio = txtInicioPromo.getText().toString();
                            String fFin = txtFinPromo.getText().toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date inicio = sdf.parse(fInicio);
                            Date fin = sdf.parse(fFin);

                            System.out.println("INI PROMO: " + inicio);
                            System.out.println("INI PROMO: " + fin);
                            if (inicio.after(fin)) { //esto pregunta si hoy es una fecha posterior a bd
                                txtFinPromo.setText("");
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

    public void finPromo() {
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
                        date = dateFormat.parse(dayOfMonth + "/" + (month+1) + "/" + year);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String outDate = dateFormat.format(date);
                    txtFinPromo.setText(outDate);
                }
            },anio,mes,dia);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);

            String oldTime = txtInicioPromo.getText().toString();
            Date oldDate = formatter.parse(oldTime);
            long oldMillis = oldDate.getTime();

            // set maximum date to be selected as today
            from_dateListener.getDatePicker().setMinDate(oldMillis);
            from_dateListener.show();
        } catch (Exception e) {
            System.out.println("FIN PROMO: " + e.getMessage());
        }
    }

    public void insertData( String marca, String otras_marcas, String canal, String descripcion,
                           String mecanica, String ini_promo, String fin_promo, String stock, String pvc_anterior, String pvc_actual, String sku, String campana) {
        try {
            String image = "NO_FOTO";

            if (imageView != null && imageView.getDrawable() != null) { //ImageView no vacio

                obtenerFecha();

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

                //    image = getStringImage(bitmapfinal);
            }
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fechaser = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            ContentValues values = new ContentValues();

            String manufacurer = handler.getManufacturerPromocion( marca);
            String plataforma = handler.getPlataformaBySku(sku);

            values.put(ContractInsertPromocion.Columnas.PHARMA_ID, id_pdv);
            values.put(ContractInsertPromocion.Columnas.CODIGO, codigo_pdv);
            values.put(ContractInsertPromocion.Columnas.USUARIO, user);
            values.put(ContractInsertPromocion.Columnas.SUPERVISOR, punto_venta);
            values.put(ContractInsertPromocion.Columnas.FECHA, fechaser);
            values.put(ContractInsertPromocion.Columnas.HORA, horaser);
           // values.put(ContractInsertPromocion.Columnas.CATEGORIA, categoria);
          //  values.put(ContractInsertPromocion.Columnas.SUBCATEGORIA, subcategoria);
            values.put(ContractInsertPromocion.Columnas.MARCA, marca);
            values.put(ContractInsertPromocion.Columnas.OTRAS_MARCAS, otras_marcas);
            values.put(ContractInsertPromocion.Columnas.CANAL, canal);
//            values.put(ContractInsertPromocion.Columnas.TIPO_PROMOCION, tipo_promocion);
            values.put(ContractInsertPromocion.Columnas.DESCRIPCION_PROMOCION, descripcion);

            if(tipo.equalsIgnoreCase("MARCA_PROPIA")){
                mecanica = "";
            }
            values.put(ContractInsertPromocion.Columnas.MECANICA, mecanica);
            values.put(ContractInsertPromocion.Columnas.INI_PROMO, ini_promo);
            values.put(ContractInsertPromocion.Columnas.FIN_PROMO, fin_promo);
            values.put(ContractInsertPromocion.Columnas.AGOTAR_STOCK, stock);
            values.put(ContractInsertPromocion.Columnas.PVC_ANTERIOR, pvc_anterior);
            values.put(ContractInsertPromocion.Columnas.PVC_ACTUAL, pvc_actual);
            values.put(ContractInsertPromocion.Columnas.FOTO, image);
            values.put(ContractInsertPromocion.Columnas.MANUFACTURER, manufacurer);
            values.put(ContractInsertPromocion.Columnas.SKU, sku);
            values.put(ContractInsertPromocion.Columnas.POS_NAME, punto_venta);
            values.put(ContractInsertPromocion.Columnas.PLATAFORMA, plataforma);
            if(tipo.equalsIgnoreCase("COMPETENCIA")){
                campana = "";
            }
            values.put(ContractInsertPromocion.Columnas.CAMPANA, campana);
            values.put(Constantes.PENDIENTE_INSERCION, 1);

            getContext().getContentResolver().insert(ContractInsertPromocion.CONTENT_URI, values);

            if (VerificarNet.hayConexion(getContext())) {
                SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertPromocion, null);
                Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean esFormularioValido( String marca, String otras_marcas, String canal,
                                       String descripcion, String mecanica,
                                       String ini_promo, String fin_promo, String pvc_anterior,
                                       String pvc_actual, String sku, String stock) {
//        if (categoria.equalsIgnoreCase("SELECCIONE")) {
//            Toast.makeText(getContext(), "Debe seleccionar una categoria", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (subcategoria.equalsIgnoreCase("SELECCIONE")) {
//            Toast.makeText(getContext(), "Debe seleccionar una subcategoria", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (descripcion.equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getContext(), "Debe seleccionar un tipo de promoción", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (marca.equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getContext(), "Debe seleccionar una marca", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(marca.equalsIgnoreCase("OTROS")){
            if (otras_marcas.equalsIgnoreCase("NA") || otras_marcas.trim().isEmpty()) {
                if (linear_otros.getVisibility() == VISIBLE){
                    Toast.makeText(getContext(), "El campo 'otras marcas' no puede estar vacio", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        if (sku.equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getContext(), "Debe seleccionar un sku", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (descripcion.equalsIgnoreCase("SELECCIONE")) {
            Toast.makeText(getContext(), "Debe seleccionar un tpo de promoción", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            if (mecanica.trim().isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar una mecánica", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (ini_promo.trim().isEmpty()) {
            Toast.makeText(getContext(), "Debe ingresar una inicio de promoción", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.i("PRUEBA stock", stock);
        if (!stock.equalsIgnoreCase("SI")){
            if (fin_promo.trim().isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar una fin de promoción", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        Log.i("LOG DESCRIPCION",descripcion);
        if (!descripcion.equalsIgnoreCase("PRODUCTO GRATIS") && !descripcion.equalsIgnoreCase("PROMOCIONAL GRATIS")) {

            if (pvc_anterior.trim().isEmpty() && layoutPVCanterior.getVisibility()== VISIBLE) {
                Toast.makeText(getContext(), "Debe ingresar el PVA promocional", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (pvc_actual.trim().isEmpty() && layoutPVCactual.getVisibility()== VISIBLE) {
                Toast.makeText(getContext(), "Debe ingresar PVC actual", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        // si el checkbox esta, esto no deberia ocurrir
        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            if (checkNofoto == null) {
                Log.e("PREUBA", "El checkbox 'checkNofoto' no está inicializado en esFormularioValido.");
                if (imageView.getDrawable() == null) {
                    Toast.makeText(getContext(), "Debe tomar una foto", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (!checkNofoto.isChecked()) {
                if (imageView.getDrawable() == null) {
                    Toast.makeText(getContext(), "Debe tomar una foto", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } else {
            if (imageView.getDrawable() == null) {
                Toast.makeText(getContext(), "Debe tomar una foto", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        return true;
    }

    private boolean validaPermisos() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M) {
            return true;
        }
        if ((getContext().checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

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
                    //  tomarFotografia();
                    Intent n = new Intent(getContext(), CameraActivity.class);
                    n.putExtra("activity", "promo");
                    startActivity(n);
                }else{
                    if (opciones[i].equals("Cargar Imagen")) {
                        /*Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), COD_SELECCIONA);
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

    private void tomarFotografia() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();
        String nombreImagen="";
        if (!isCreada) {
            isCreada = fileImagen.mkdirs();
        }

        if (isCreada) {
            nombreImagen= (System.currentTimeMillis()/1000)+".jpg";
        }

        path = Environment.getExternalStorageDirectory() + File.separator + RUTA_IMAGEN + File.separator + nombreImagen;

        Log.i("PATH_FOTO", path);

        File imagen = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities = getContext().getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            Log.i("PHOTO", "ENTRA 1");
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
            Log.i("PHOTO", "ENTRA 2");
        }
        startActivityForResult(intent, COD_FOTO);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if (adapterView==spCategoria) {
//            try{
//                categoria=adapterView.getItemAtPosition(i).toString();
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
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
        if (adapterView == spMarca){
            try{
                marca =adapterView.getItemAtPosition(i).toString();
                if(marca.equalsIgnoreCase("OTROS")){
                    txtOtrasMarcas.setEnabled(true);
                } else {
                    txtOtrasMarcas.setEnabled(false);
                    txtOtrasMarcas.setText("");
                }
                filtrarSKU( marca, tipo, fabricante);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }






        if(adapterView==spDescripcionPromocion){
            descripcion =adapterView.getItemAtPosition(i).toString();
            Log.i("DESCRIPCION PROMO", descripcion);
            if (tipo.equalsIgnoreCase("COMPETENCIA")) {
                if (descripcion.equalsIgnoreCase("PRODUCTO GRATIS") || descripcion.equalsIgnoreCase("PROMOCIONAL GRATIS")) {
                    txtAnterior.setEnabled(true);
                    txtAnterior.setText("");
                    txtActual.setEnabled(true);
                    txtActual.setText("");
                } else {
                    txtAnterior.setEnabled(true);
                    txtActual.setEnabled(true);
                }
            } else {
                Log.i("DESCRIPCION PROMO 2", descripcion);
                filtrarCampanaPromocion(canal, cadena, descripcion);


            }
        }

        if(adapterView==spCampanaPromocion){
            campana =adapterView.getItemAtPosition(i).toString();
            Log.i("CAMPANA PROMO", campana);
            if (tipo.equalsIgnoreCase("COMPETENCIA")) {
                if (campana.equalsIgnoreCase("PRODUCTO GRATIS") || campana.equalsIgnoreCase("PROMOCIONAL GRATIS")) {
                    txtAnterior.setEnabled(true);
                    txtAnterior.setText("");
                    txtActual.setEnabled(true);
                    txtActual.setText("");
                } else {
                    txtAnterior.setEnabled(true);
                    txtActual.setEnabled(true);
                }
            } else {
                Log.i("CAMPANA PROMO 2", campana);
                if (tipo.equalsIgnoreCase("COMPETENCIA")) {
                    if (campana.equalsIgnoreCase("PRODUCTO GRATIS") || campana.equalsIgnoreCase("PROMOCIONAL GRATIS")) {
                        txtAnterior.setEnabled(true);
                        txtAnterior.setText("");
                        txtActual.setEnabled(true);
                        txtActual.setText("");
                    } else {
                        txtAnterior.setEnabled(true);
                        txtActual.setEnabled(true);
                    }
                    //FILTRAR DESDE BASE DE PRODUCTOS
                    filtrarMarcaPromocion(canal, cadena, descripcion, campana);
                } else {
//                    Log.i("SUBCATEGORIA PROMO 2", subcategoria);
                    filtrarMarcaPromocion(canal, cadena, descripcion, campana);

                }

            }
        }

//        if(adapterView==spCategoria){
//            categoria =adapterView.getItemAtPosition(i).toString();
//            Log.i("CATEGORIA PROMO", categoria);
//            if (tipo.equalsIgnoreCase("COMPETENCIA")) {
//                if (categoria.equalsIgnoreCase("PRODUCTO GRATIS") || categoria.equalsIgnoreCase("PROMOCIONAL GRATIS")) {
//                    txtAnterior.setEnabled(true);
//                    txtAnterior.setText("");
//                    txtActual.setEnabled(true);
//                    txtActual.setText("");
//                } else {
//                    txtAnterior.setEnabled(true);
//                    txtActual.setEnabled(true);
//                }
//                //FILTRAR DESDE BASE DE PRODUCTOS
//                filtrarSubcategoriaPromocion(canal, cadena, descripcion, campana, categoria);
//            } else {
//                Log.i("CATEGORIA PROMO 2", categoria);
//                filtrarSubcategoriaPromocion(canal, cadena, descripcion, campana, categoria);
//
//            }
//        }


//        if(adapterView==spSubcategoria){
//            subcategoria =adapterView.getItemAtPosition(i).toString();
//            Log.i("SUBCATEGORIA PROMO", subcategoria);
//            if (tipo.equalsIgnoreCase("COMPETENCIA")) {
//                if (subcategoria.equalsIgnoreCase("PRODUCTO GRATIS") || subcategoria.equalsIgnoreCase("PROMOCIONAL GRATIS")) {
//                    txtAnterior.setEnabled(true);
//                    txtAnterior.setText("");
//                    txtActual.setEnabled(true);
//                    txtActual.setText("");
//                } else {
//                    txtAnterior.setEnabled(true);
//                    txtActual.setEnabled(true);
//                }
//                //FILTRAR DESDE BASE DE PRODUCTOS
//                filtrarMarcaPromocion(canal, cadena, descripcion, campana);
//            } else {
//                Log.i("SUBCATEGORIA PROMO 2", subcategoria);
//                filtrarMarcaPromocion(canal, cadena, descripcion, campana);
//
//            }
//        }


        if (adapterView == spMarca){
            marca =adapterView.getItemAtPosition(i).toString();
            Log.i("SUBCATEGORIA PROMO", marca);
            if (tipo.equalsIgnoreCase("COMPETENCIA")) {
                if (marca.equalsIgnoreCase("PRODUCTO GRATIS") || marca.equalsIgnoreCase("PROMOCIONAL GRATIS")) {
                    txtAnterior.setEnabled(true);
                    txtAnterior.setText("");
                    txtActual.setEnabled(true);
                    txtActual.setText("");
                } else {
                    txtAnterior.setEnabled(true);
                    txtActual.setEnabled(true);
                }
                //FILTRAR DESDE BASE DE PRODUCTOS
                filtrarSkuPromocion(canal, cadena, descripcion, campana, marca);
            } else {
                Log.i("SUBCATEGORIA PROMO 2", marca);
                filtrarSkuPromocion(canal, cadena, descripcion, campana, marca);


            }
        }

//        if (adapterView==spTipoPromocion) {
//            try{
//                tipo_promocion=adapterView.getItemAtPosition(i).toString();
//                filtrarDescripcionPromocion(canal);
//
//                layoutPVCanterior.setVisibility(View.VISIBLE);
//                layoutPVCactual.setVisibility(View.VISIBLE);
//                if (tipo_promocion.equalsIgnoreCase("Producto / Promocional")) {
//                    layoutPVCanterior.setVisibility(View.GONE);
//                    layoutPVCactual.setVisibility(View.GONE);
//                    txtAnterior.setText("");
//                    txtActual.setText("");
//                }
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
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
    public void onClick(View view) {
        if (tipo != null){
            if(tipo.equalsIgnoreCase("MARCA_PROPIA")){
                inicioPromoMarcaPropia = txtInicioPromo.getText().toString();
                finPromoMarcaPropia = txtFinPromo.getText().toString();
            }
        }
        if (view == btnPropia) {
            tipo = "MARCA_PROPIA";
            btnPropia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_selected));
            btnPropia.setPadding(15, 15, 15, 15);
            btnCompetencia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_unselected));
            btnCompetencia.setPadding(15, 15, 15, 15);
            linear_otros.setVisibility(GONE);
            lPVCanterior.setHint("P.V.A promocional");
            layoutPVCactual.setVisibility(GONE);
            lLayoutCampana.setVisibility(VISIBLE);
            idNofoto.setVisibility(VISIBLE);
            lLayoutMecanica.setVisibility(GONE);

            txtInicioPromo.setText(inicioPromoMarcaPropia);
            txtFinPromo.setText(finPromoMarcaPropia);
            txtPVCanterior.setText("");

            limpiarImagen();
            if (checkNofoto != null && layoutCameraSection != null) {
                layoutCameraSection.setVisibility(checkNofoto.isChecked() ? View.GONE : View.VISIBLE);
//                if(checkNofoto.isChecked()){
//                    limpiarDatos();
//                }
            }else{
                layoutCameraSection.setVisibility(View.VISIBLE);
            }


            filtrarDescripcionPromocion(canal, cadena);

        }
        if (view == btnCompetencia) {
          //  spSubcategoria.setAdapter(null);
            spMarca.setAdapter(null);
            spSKU.setAdapter(null);
            spCampanaPromocion.setAdapter(null);
            tipo = "COMPETENCIA";
            btnPropia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_unselected));
            btnPropia.setPadding(15, 15, 15, 15);
            btnCompetencia.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_background_selected));
            btnCompetencia.setPadding(15, 15, 15, 15);
            linear_otros.setVisibility(VISIBLE);
          //  filtrarCategoria(tipo, fabricante);
//            lPVCanterior.setHint("P.V.A anterior");
//            layoutPVCactual.setVisibility(VISIBLE);
            lLayoutMecanica.setVisibility(VISIBLE);
            lLayoutCampana.setVisibility(GONE);

            txtInicioPromo.setText(inicioPromoCompetencia);
            txtFinPromo.setText(finPromoCompetencia);

            txtPVCanterior.setText("");


            idNofoto.setVisibility(GONE);
            layoutCameraSection.setVisibility(View.VISIBLE);

            limpiarImagen();
            if(checkNofoto != null){
                checkNofoto.setChecked(false);
            }
            filtrarDescripcionPromocion(canal, cadena);
            filtrarMarcaPromocion(canal, cadena, descripcion, campana); //agg nuevo
        }

        if (view == btnCamera) {
            cargarImagen();
        }
        if (view == btnIniPromo) {
            inicioPromo();
        }
        if (view == btnFinPromo) {
            String iniPromo = txtInicioPromo.getText().toString();
            if (!iniPromo.trim().isEmpty()) {
                finPromo();
            } else {
                Toast.makeText(getContext(), "Primero debe seleccionar un inicio de promoción", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == btnGuardar) {
            guardar();
        }
    }
    private void guardar() {
        String stock;
        if (chkStock.isChecked()) {
            stock = "SI";
        }else{
            stock = "NO";
        }
       // String categoria = spCategoria.getSelectedItem().toString();
      //  String subcategoria = spSubcategoria.getSelectedItem().toString();
        String marca = spMarca.getSelectedItem().toString();
        String otras_marcas = txtOtrasMarcas.getText().toString().trim();
//        String tipo_promocion = spTipoPromocion.getSelectedItem().toString();
        String descricion_promocion = spDescripcionPromocion.getSelectedItem().toString();
        String mecanica = txtMecanica.getText().toString().trim();
        String ini_promo = txtInicioPromo.getText().toString();
        String fin_promo = txtFinPromo.getText().toString();
        String pvc_anterior = txtAnterior.getText().toString().trim();
        String pvc_actual = txtActual.getText().toString().trim();
        String sku = spSKU.getSelectedItem().toString().trim();

        if (esFormularioValido( marca, otras_marcas, canal, descricion_promocion, mecanica, ini_promo, fin_promo, pvc_anterior, pvc_actual, sku, stock)) {
            insertData( marca, otras_marcas, canal, descricion_promocion, mecanica, ini_promo, fin_promo, stock, pvc_anterior, pvc_actual, sku, campana);

            limpiarDatos();
        }
    }

    private void limpiarDatos() {

        limpiarImagen();

        if (checkNofoto != null) {
            checkNofoto.setChecked(false);
        }
        if (layoutCameraSection != null) {
            layoutCameraSection.setVisibility(View.VISIBLE);
        }

       // spCategoria.setSelection(0);
       // spSubcategoria.setSelection(0);
        spMarca.setSelection(0);
        spSKU.setSelection(0);
        spTipoPromocion.setSelection(0);
        spDescripcionPromocion.setSelection(0);
        txtMecanica.setText("");
        txtInicioPromo.setText("");
        txtFinPromo.setText("");
        chkStock.setChecked(false);
        txtAnterior.setText("");
        txtActual.setText("");
        imageView.setImageResource(0);
    }

    private void limpiarImagen() {
        if (imageView != null) {
            imageView.setImageResource(0);
        }
    }
}