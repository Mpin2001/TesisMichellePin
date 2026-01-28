package com.luckyecuador.app.bassaApp.ui.convenios;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
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
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.luckyecuador.app.bassaApp.Clase.Base_convenios;
import com.luckyecuador.app.bassaApp.Clase.InsertConvenios;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.MarshMallowPermission;
import com.luckyecuador.app.bassaApp.Conexion.Mensajes;
import com.luckyecuador.app.bassaApp.Conexion.VerificarNet;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertConvenios;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.R;
import com.luckyecuador.app.bassaApp.Sync.SyncAdapter;
import com.luckyecuador.app.bassaApp.Utils.DeveloperOptions;
import com.luckyecuador.app.bassaApp.Utils.GuardarLog;
import com.luckyecuador.app.bassaApp.Utils.RequestPermissions;

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

public class ConveniosFragment extends Fragment implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayList<Base_convenios> type_name_copy = new ArrayList<Base_convenios>();
    ArrayList<Base_convenios> sesion = new ArrayList<Base_convenios>();

    private Spinner spCategoria;
    private Spinner spSubcategoria;
    private Spinner spMarca;
    private Spinner spFabricante;
    ListView listview;


    //BASE SQLITE
    private String categoria, subcategoria, marca;
    private String id_pdv,user,codigo_pdv, punto_venta,fecha,hora, fechaSer, horaSer;
    private static final String URL = "https://luckyecuadorweb.blob.core.windows.net/app/AppBassa/Inserts/";
    Boolean fecha1 = false;
    DatabaseHelper handler;

    TextView txtfechav;
    ImageButton btnFecha;
    TextView empty, lblFechaDesde, lblFechaHasta;

    public ArrayList<Base_convenios> listTareas;
    CustomAdapterPrecios dataAdapter;

    private EditText txtpregular, txtppromocion;
    private String pregular, ppromocion, sku, format, presentacion;
    private String poferta;
    private String canal, subcanal, modulo, rol;
    private boolean vaFoto;

    LinearLayout layout_skuName;
    LinearLayout layout_skuDescripcion;

    //Photo Camera
    private ImageView imageView;
    private Bitmap bitmap;
    private Bitmap bitmapfinal;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;
    private static final String TAG = "LOG:";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int LOCATION_INTERVAL = 0; //3Minutos
    private static final float LOCATION_DISTANCE = 0;
    private FusedLocationProviderClient fusedLocationClient;

    private final String CARPETA_RAIZ="ToniApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Tareas";
    String path;
    Button btnCamera;

    MarshMallowPermission marshMallowPermission;

    private String tipo = "MARCA_PROPIA";
    private final String fabricante = "Arca";
    private double latitud = 0, longitud = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_convenios, container, false);

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);
        LoadData();
        new GuardarLog(getContext()).saveLog(user, "", "Ingreso al Módulo Convenios");
        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();
        marshMallowPermission = new MarshMallowPermission(getActivity());

        spCategoria =(Spinner) rootView.findViewById(R.id.spCategoria3);
        spSubcategoria =(Spinner) rootView.findViewById(R.id.spSucategoria3);
        spMarca =(Spinner) rootView.findViewById(R.id.spMarca3);

        listview = (ListView) rootView.findViewById(R.id.lvSKUCode);

        //View headerView = (View) this.getLayoutInflater().inflate(R.layout.list_row_precios_title,null,false);
        //listview.addHeaderView(headerView,null,false);

        ObtenerFecha();

        /*locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try{
                    //Setear los EditText
                    if (!latitud.equals(location.getLatitude())&&!longitud.equals(location.getLongitude()))
                    {
                        latitud = String.valueOf(location.getLatitude());
                        longitud = String.valueOf(location.getLongitude());
                    }

                    locationManager.removeUpdates(locationListener);
//                locationManager = null;
                }catch (Exception e) {
                    Toast.makeText(getContext().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) {
                //Si el GPS esta deshabilitado, abrir la ventana de activacion del GPS en el dispositivo.
                Intent newIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(newIntent);
            }
        };*/

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        //Verificar los permisos
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                }
            }
        });

        empty = (TextView) rootView.findViewById(R.id.recyclerview_data_empty);

        filtrarCategoria();
        return rootView;
    }

    public void filtrarCategoria() {
        List<String> operadores = handler.getCategoriasConvenios(codigo_pdv);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
    }
    public void filtrarSubcategoria(String categoria) {
        List<String> operadores = handler.getSubcategoriasConvenios(codigo_pdv, categoria);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }
    public void filtrarMarca(String categoria, String subcategoria) {
        List<String> operadores = handler.getMarcasConvenios(codigo_pdv, categoria, subcategoria);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    public void consultarExhibiciones(String categoria, String subcategoria, String marca){
            try{
                listTareas = handler.filtrarListExhibiciones(codigo_pdv, categoria, subcategoria, marca);
                showListView(listTareas);

            }catch (Error e){
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
        id_pdv = sharedPreferences.getString(Constantes.PHARMA_ID, Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        subcanal = sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
        modulo = sharedPreferences.getString(Constantes.MODULO, Constantes.NODATA);
        vaFoto = sharedPreferences.getBoolean(Constantes.VAFOTO,false);

        //rol = sharedPreferences.getString(Constantes.ROL, Constantes.NODATA);
    }


    public void showListView(ArrayList<Base_convenios> listTareas ) {
        Log.i("totales d",""+listTareas +"");
        dataAdapter = new CustomAdapterPrecios(getContext(), listTareas);

        if (!dataAdapter.isEmpty()) {
            empty.setVisibility(View.INVISIBLE);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(dataAdapter);
        }else{
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    public void listUpdate(ArrayList<Base_convenios> data) {
        if (!data.isEmpty()) {
            listview.setVisibility(View.VISIBLE);
            empty.setVisibility(View.INVISIBLE);
            listview.setAdapter(new CustomAdapterPrecios(getContext(), data));
        }else{
            listview.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if(parent== spCategoria){
            try{
                categoria = parent.getItemAtPosition(position).toString();
                filtrarSubcategoria(categoria);
            }catch (Exception e){
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if(parent== spSubcategoria){
            try{
                subcategoria = parent.getItemAtPosition(position).toString();
                filtrarMarca(categoria, subcategoria);
            }catch (Exception e){
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if(parent== spMarca){
            try{
                marca = parent.getItemAtPosition(position).toString();
                consultarExhibiciones(categoria, subcategoria, marca);
            }catch (Exception e){
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //if (!fechaventas.equals("") && fechaventas!=null) {
            String model = adapterView.getItemAtPosition(i).toString();
            //alertDialog(model);
        //}else{
         //   Toast.makeText(getContext(),Mensajes.FECHA,Toast.LENGTH_SHORT).show();
       // }
    }


    public void ObtenerFecha(){

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        fechaSer = date.format(currentLocalTime);

        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        horaSer = hour.format(currentLocalTime);
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

    }

    //************METODOS PARA TAKE-PHOTO Y UPLOAD


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

    private void cargarImagen() {
//        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
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
                    tomarFotografia();
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
                    /*Uri miPath=data.getData();
                    imageView.setImageURI(miPath);*/

                    Uri filePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
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
    //VER QUE HACE EL POSITION... COMO SETEAR LA FOTO TAREA A PARTIR DEL LINK
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
    }

    public class CustomAdapterPrecios extends ArrayAdapter<Base_convenios> implements Filterable {

        public ArrayList<Base_convenios> values;
        public Context context;
        boolean[] checkBoxState;

        public CustomAdapterPrecios(Context context, ArrayList<Base_convenios> values) {
            super(context, 0, values);
            this.values = values;
            checkBoxState = new boolean[values.size()];
        }

        public class ViewHolder{
            TextView tvTipoExh;
            RadioGroup rgExhibicion;
            RadioButton rbExhSi;
            RadioButton rbExhNo;
            EditText txtComentario;
            EditText txtNumConvenio;
            EditText txtNumEncontrada;
            public ImageView ivFotoC1, ivFotoC2, ivFotoC3, ivFotoC4, ivFotoC5, ivFotoC6;
            public EditText txtComentarioC1, txtComentarioC2, txtComentarioC3, txtComentarioC4, txtComentarioC5, txtComentarioC6;
            public ImageButton btnCamara1,btnCamara2,btnCamara3,btnCamara4,btnCamara5,btnCamara6;
            Button btnGuardar;
            CheckBox checkNoExiste;
            LinearLayout fotos;

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

        /*@NonNull
        @Override
        public Filter getFilter() {
            return precioFilter;
        }

        private Filter precioFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Precio> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Precio item : values) {
                        if (item.getSku_code().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);
                        }
                    }
                }
                results.values = suggestions;
                results.fabricante = suggestions.status();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Precio) resultValue).getSku_code();
            }
        };*/

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Obtener Instancia Inflater
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder vHolder = null;
            //Comprobar si el View existe
            //Si no existe inflarlo
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.list_row_convenios, parent, false); // Modificacion (list_row_option) GT
                //Obtener instancias de los elementos
                vHolder = new ViewHolder();
                vHolder.tvTipoExh = (TextView) convertView.findViewById(R.id.tvTipoExh);
                vHolder.txtNumConvenio = (EditText) convertView.findViewById(R.id.txtNumConvenio);
                vHolder.txtNumEncontrada = (EditText) convertView.findViewById(R.id.txtNumEncontrada);
                vHolder.rgExhibicion = (RadioGroup) convertView.findViewById(R.id.rgExhibicion);
                vHolder.rbExhSi = (RadioButton) convertView.findViewById(R.id.rbExhSi);
                vHolder.rbExhNo = (RadioButton) convertView.findViewById(R.id.rbExhNo);
                vHolder.btnCamara1 = (ImageButton) convertView.findViewById(R.id.btnFotoC1);
                vHolder.btnCamara2 = (ImageButton) convertView.findViewById(R.id.btnFotoC2);
                vHolder.btnCamara3 = (ImageButton) convertView.findViewById(R.id.btnFotoC3);
                vHolder.btnCamara4 = (ImageButton) convertView.findViewById(R.id.btnFotoC4);
                vHolder.btnCamara5 = (ImageButton) convertView.findViewById(R.id.btnFotoC5);
                vHolder.btnCamara6 = (ImageButton) convertView.findViewById(R.id.btnFotoC6);
                vHolder.ivFotoC1 = (ImageView) convertView.findViewById(R.id.ivFotoC1);
                vHolder.ivFotoC2 = (ImageView) convertView.findViewById(R.id.ivFotoC2);
                vHolder.ivFotoC3 = (ImageView) convertView.findViewById(R.id.ivFotoC3);
                vHolder.ivFotoC4 = (ImageView) convertView.findViewById(R.id.ivFotoC4);
                vHolder.ivFotoC5 = (ImageView) convertView.findViewById(R.id.ivFotoC5);
                vHolder.ivFotoC6 = (ImageView) convertView.findViewById(R.id.ivFotoC6);

                vHolder.txtComentarioC1 = (EditText) convertView.findViewById(R.id.txtComentarioC1);
                vHolder.txtComentarioC2 = (EditText) convertView.findViewById(R.id.txtComentarioC2);
                vHolder.txtComentarioC3 = (EditText) convertView.findViewById(R.id.txtComentarioC3);
                vHolder.txtComentarioC4 = (EditText) convertView.findViewById(R.id.txtComentarioC4);
                vHolder.txtComentarioC5 = (EditText) convertView.findViewById(R.id.txtComentarioC5);
                vHolder.txtComentarioC6 = (EditText) convertView.findViewById(R.id.txtComentarioC6);

                vHolder.txtComentario = (EditText) convertView.findViewById(R.id.txtComentario);
                vHolder.btnGuardar = (Button) convertView.findViewById(R.id.btnSave);
                vHolder.checkNoExiste = (CheckBox) convertView.findViewById(R.id.checkNoExiste);
                vHolder.fotos = (LinearLayout) convertView.findViewById(R.id.fotos);

                final ViewHolder finalv = vHolder;



                /*vHolder.btnCamara1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cargarImagen();
                        imageView = finalv.ivFotoC2;
                    }
                });*/

//                View.OnClickListener buttonClickListener = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (v.getId()) {
//                            case R.id.btnFotoC1:
//                                cargarImagen();
//                                imageView = finalv.ivFotoC1;
//                                break;
//                            case R.id.btnFotoC2:
//                                cargarImagen();
//                                imageView = finalv.ivFotoC2;
//                                break;
//                            case R.id.btnFotoC3:
//                                cargarImagen();
//                                imageView = finalv.ivFotoC3;
//                                break;
//                            case R.id.btnFotoC4:
//                                cargarImagen();
//                                imageView = finalv.ivFotoC4;
//                                break;
//                            case R.id.btnFotoC5:
//                                cargarImagen();
//                                imageView = finalv.ivFotoC5;
//                                break;
//                            case R.id.btnFotoC6:
//                                cargarImagen();
//                                imageView = finalv.ivFotoC6;
//                                break;
//                        }
//                    }
//                };

                convertView.setTag(vHolder);

            } else {
                vHolder = (ViewHolder) convertView.getTag();
            }

//            vHolder.checkNoExiste.setChecked(values.get(position).isChecked());

//            vHolder.checkNoExiste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
//                        Log.i("check","entro el check");
//                        Toast.makeText(getContext(), "Prueba", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });

//            vHolder.checkGuardar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
//
//                        Toast.makeText(getContext(),"entro el check",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            });

            if (values.size() > 0) {


                //String ruta_image = values.get(position).getEnlace();
                String tipo_exh = values.get(position).getTipo_exhibicion();
                String num_convenio = values.get(position).getNumero_exhibicion(); //num convenio
                String marca = values.get(position).getMarca();

                //vHolder.tvTipoExh.setText(tipo_exh + " MARCA " + marca);
                vHolder.tvTipoExh.setText(tipo_exh.toUpperCase());
                vHolder.txtNumConvenio.setText(num_convenio);

                /*if(!ruta_image.isEmpty()){
                    Glide.with(getContext()).load(ruta_image).into(vHolder.ivFotoC1);
                }
                if(!comentario_justificacion.isEmpty() && !ruta_image_v.isEmpty()){
                    vHolder.tvTipoExh.setBackgroundColor(Color.LTGRAY);
                    vHolder.btnGuardar.setBackgroundColor(Color.LTGRAY);
                    vHolder.btnGuardar.setEnabled(false);
                }*/

                final ViewHolder finalv = vHolder;
                ArrayList<InsertConvenios> ArrayListConvenios = new ArrayList<InsertConvenios>();


                vHolder.checkNoExiste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            Log.i("check","entro el check");
                            Toast.makeText(getContext(), "Prueba", Toast.LENGTH_SHORT).show();
                            finalv.fotos.setVisibility(View.GONE);
                            finalv.txtNumEncontrada.setText("0");
                            finalv.txtNumEncontrada.setEnabled(false);

                        }else {
                            finalv.fotos.setVisibility(View.VISIBLE);
                            finalv.txtNumEncontrada.setEnabled(true);
                            finalv.txtNumEncontrada.setText("");


                        }

                    }
                });


                vHolder.btnGuardar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        try {

                            ObtenerFecha();
                            String image = "NO_FOTO";
                            Bitmap bitmapFoto = null;
                            String id = values.get(position).getId(); //pharma_id
                            String comentario = finalv.txtComentario.getText().toString();
                            String tipo_exhib = finalv.tvTipoExh.getText().toString();
                            String num_convenio = finalv.txtNumConvenio.getText().toString();
                            String num_encontrada = finalv.txtNumEncontrada.getText().toString();
                            boolean isChecked = finalv.checkNoExiste.isChecked();

                            /* (finalv.rbExhSi.isChecked())
                                contratada = "SI";
                            else if (finalv.rbExhNo.isChecked())
                                contratada = "NO";*/

                            if (esFormularioValido(num_encontrada,isChecked)) {

                                for (int i = 0; i < ArrayListConvenios.size(); i++) {
                                    Log.i("entrO foto", ""+i);

                                    if(!num_encontrada.equals("0")){
                                        ImageView ivFoto = ArrayListConvenios.get(i).getIvFoto();
                                        bitmapFoto = ((BitmapDrawable) ivFoto.getDrawable()).getBitmap();
                                        image = getStringImage(bitmapFoto);
                                    }
                                    InsertConvenios item = ArrayListConvenios.get(i);
                                    comentario = item.getIvComent();



                                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                                    Date currentLocalTime = cal.getTime();
                                    DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                                    date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                                    String fechaser = date.format(currentLocalTime);

                                    DateFormat hour = new SimpleDateFormat("HH:mm:ss");
                                    hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                                    String horaser = hour.format(currentLocalTime);


                                    String pos_name = handler.getPosNamePdv(codigo_pdv);
                                    String latitud = handler.getLatitudPdv(codigo_pdv);
                                    String longitud = handler.getLongitudPdv(codigo_pdv);


                                    ContentValues values = new ContentValues();
                                    values.put(ContractInsertConvenios.Columnas.PHARMA_ID, id_pdv);
                                    values.put(ContractInsertConvenios.Columnas.FECHA, fechaser);
                                    values.put(ContractInsertConvenios.Columnas.HORA, horaser);
                                    values.put(ContractInsertConvenios.Columnas.CODIGO, codigo_pdv);
                                    values.put(ContractInsertConvenios.Columnas.POS_NAME, pos_name);
                                    values.put(ContractInsertConvenios.Columnas.USUARIO, user);
                                    values.put(ContractInsertConvenios.Columnas.LATITUD, latitud);
                                    values.put(ContractInsertConvenios.Columnas.LONGITUD, longitud);
                                    values.put(ContractInsertConvenios.Columnas.CATEGORIA, categoria);
                                    values.put(ContractInsertConvenios.Columnas.FABRICANTE, subcategoria);
                                    values.put(ContractInsertConvenios.Columnas.MARCA, marca);
                                    values.put(ContractInsertConvenios.Columnas.TIPO_EXHIBICION, tipo_exhib);
                                    values.put(ContractInsertConvenios.Columnas.NUMERO_EXHIBICION, num_convenio);
                                    values.put(ContractInsertConvenios.Columnas.CONTRATADA, num_encontrada);
                                    values.put(ContractInsertConvenios.Columnas.OBSERVACION, comentario);
                                    values.put(ContractInsertConvenios.Columnas.FOTO, image);
                                    values.put(ContractInsertConvenios.Columnas.MODULO, modulo);
                                    values.put(ContractInsertConvenios.Columnas.UNIDAD_DE_NEGOCIO, "");
                                    values.put(ContractInsertConvenios.Columnas.ROL, i); //indice para que no se repita el mismo nombre de foto en el servidor
                                    //finalv.btnGuardar.setEnabled(false);

                                    values.put(Constantes.PENDIENTE_INSERCION, 1);

                                    getContext().getContentResolver().insert(ContractInsertConvenios.CONTENT_URI, values);

                                    if (VerificarNet.hayConexion(getContext())) {
                                        SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertConvenios, null);
                                        Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                vaciarCampos();
                            }
                        } catch (Exception e){
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                            
                    }

                    public void vaciarCampos() {
                        finalv.rgExhibicion.clearCheck();
                        finalv.txtComentario.setText("");
                        finalv.txtNumEncontrada.setText("");
                        finalv.ivFotoC1.setImageResource(0);
                        finalv.ivFotoC2.setImageResource(0);
                        finalv.ivFotoC3.setImageResource(0);
                        finalv.ivFotoC4.setImageResource(0);
                        finalv.ivFotoC5.setImageResource(0);
                        finalv.ivFotoC6.setImageResource(0);
                        finalv.txtComentarioC1.setText("");
                        finalv.txtComentarioC2.setText("");
                        finalv.txtComentarioC3.setText("");
                        finalv.txtComentarioC4.setText("");
                        finalv.txtComentarioC5.setText("");
                        finalv.txtComentarioC6.setText("");

                        spCategoria.setSelection(0); //que se resetee al primer lugar 0
                        spSubcategoria.setSelection(0);
                        spMarca.setSelection(0);

                        //finalv.btnGuardar.setEnabled(false);
                        ArrayListConvenios.clear();
                    }

                    public boolean esFormularioValido(String nunEncontrada, boolean isCheck){

                        if(nunEncontrada.equals("")){
                            Toast.makeText(getContext(), "Debe ingresar exhibici-ones encontradas", Toast.LENGTH_SHORT).show();
                            return false;

                        } else if(!nunEncontrada.equals("0")){

                            int encontradas = Integer.parseInt(finalv.txtNumEncontrada.getText().toString());
                            int fotos_tomadas = 0;
                            boolean comentariosValidos = true;

                            if (finalv.ivFotoC1.getDrawable() != null) {
                                if(finalv.txtComentarioC1.getText().toString().trim().isEmpty()){
                                    Toast.makeText(getContext(), "Comentario requerido para Foto 1", Toast.LENGTH_SHORT).show();
                                    comentariosValidos = false;
                                } else {
                                    InsertConvenios item = new InsertConvenios();
                                    item.setIvComent(finalv.txtComentarioC1.getText().toString());
                                    item.setIvFoto(finalv.ivFotoC1);
                                    ArrayListConvenios.add(item);
                                    fotos_tomadas += 1;

                                }
//                                InsertConvenios insertFotoConvenio = new InsertConvenios();
//                                insertFotoConvenio.setIvFoto(finalv.ivFotoC1);
//                                ArrayListConvenios.add(insertFotoConvenio);
                            }
                            if (finalv.ivFotoC2.getDrawable() != null) {
                                if(finalv.txtComentarioC2.getText().toString().trim().isEmpty()){
                                    Toast.makeText(getContext(), "Comentario requerido para Foto 2", Toast.LENGTH_SHORT).show();
                                    comentariosValidos = false;
                                } else {
                                    InsertConvenios item = new InsertConvenios();
                                    item.setIvComent(finalv.txtComentarioC2.getText().toString());
                                    item.setIvFoto(finalv.ivFotoC2);
                                    ArrayListConvenios.add(item);
                                    fotos_tomadas += 1;

                                }
//                                InsertConvenios insertFotoConvenio = new InsertConvenios();
//                                insertFotoConvenio.setIvFoto(finalv.ivFotoC2);
//                                ArrayListConvenios.add(insertFotoConvenio);
                            }
                            if (finalv.ivFotoC3.getDrawable() != null) {
                                if(finalv.txtComentarioC3.getText().toString().trim().isEmpty()){
                                    Toast.makeText(getContext(), "Comentario requerido para Foto 3", Toast.LENGTH_SHORT).show();
                                    comentariosValidos = false;
                                } else {
                                    InsertConvenios item = new InsertConvenios();
                                    item.setIvComent(finalv.txtComentarioC3.getText().toString());
                                    item.setIvFoto(finalv.ivFotoC3);
                                    ArrayListConvenios.add(item);
                                    fotos_tomadas += 1;
                                }

                            }
                            if (finalv.ivFotoC4.getDrawable() != null) {
                                if(finalv.txtComentarioC4.getText().toString().trim().isEmpty()){
                                    Toast.makeText(getContext(), "Comentario requerido para Foto 4", Toast.LENGTH_SHORT).show();
                                    comentariosValidos = false;
                                } else {
                                    InsertConvenios item = new InsertConvenios();
                                    item.setIvComent(finalv.txtComentarioC4.getText().toString());
                                    item.setIvFoto(finalv.ivFotoC4);
                                    ArrayListConvenios.add(item);
                                    fotos_tomadas += 1;
                                }
//                                InsertConvenios insertFotoConvenio = new InsertConvenios();
//                                insertFotoConvenio.setIvFoto(finalv.ivFotoC4);
//                                ArrayListConvenios.add(insertFotoConvenio);

                            }
                            if (finalv.ivFotoC5.getDrawable() != null) {
                                if(finalv.txtComentarioC5.getText().toString().trim().isEmpty()){
                                    Toast.makeText(getContext(), "Comentario requerido para Foto 5", Toast.LENGTH_SHORT).show();
                                    comentariosValidos = false;
                                } else {
                                    InsertConvenios item = new InsertConvenios();
                                    item.setIvComent(finalv.txtComentarioC5.getText().toString());
                                    item.setIvFoto(finalv.ivFotoC5);
                                    ArrayListConvenios.add(item);
                                    fotos_tomadas += 1;

                                }
//                                InsertConvenios insertFotoConvenio = new InsertConvenios();
//                                insertFotoConvenio.setIvFoto(finalv.ivFotoC5);
//                                ArrayListConvenios.add(insertFotoConvenio);
                            }
                            if (finalv.ivFotoC6.getDrawable() != null) {
                                if(finalv.txtComentarioC6.getText().toString().trim().isEmpty()){
                                    Toast.makeText(getContext(), "Comentario requerido para Foto 6", Toast.LENGTH_SHORT).show();
                                    comentariosValidos = false;
                                } else {
                                    InsertConvenios item = new InsertConvenios();
                                    item.setIvComent(finalv.txtComentarioC6.getText().toString());
                                    item.setIvFoto(finalv.ivFotoC6);
                                    ArrayListConvenios.add(item);
                                    fotos_tomadas += 1;

                                }
//                                InsertConvenios insertFotoConvenio = new InsertConvenios();
//                                insertFotoConvenio
//                                ArrayListConvenios.add(insertFotoConvenio);
                            }

                            if (fotos_tomadas < encontradas){
                                Toast.makeText(getContext(), "Debe tomar al menos "+ encontradas + " fotos", Toast.LENGTH_SHORT).show();
                                ArrayListConvenios.clear();
                                return false;
                            }
                        } else {
                            InsertConvenios insertFotoConvenio = new InsertConvenios();
                            insertFotoConvenio.setIvFoto(finalv.ivFotoC6);
                            ArrayListConvenios.add(insertFotoConvenio);
                        }

                        /*if(contratada.equalsIgnoreCase("NO")){
                            if(comentario.equals("")){
                                Toast.makeText(getContext(), "Debe ingresar un comentario", Toast.LENGTH_SHORT).show();
                                //finalv.btnGuardar.setChecked(false);
                                return false;
                            }
                        }
                        if (finalv.ivFotoC2.getDrawable() == null) {
                            Toast.makeText(getContext(), "Debe tomar una foto", Toast.LENGTH_SHORT).show();
                            //finalv.btnGuardar.setEnabled(true);
                            return false;
                        }*/
                        
                        return true;
                    }

                });

            }

            final ViewHolder finalv = vHolder;

//            vHolder.checkNoExiste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
//                        Log.i("check","entro el check");
//                        Toast.makeText(getContext(), "Prueba", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });



            View.OnClickListener buttonClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btnFotoC1:
                            cargarImagen();
                            imageView = finalv.ivFotoC1;
                            break;
                        case R.id.btnFotoC2:
                            cargarImagen();
                            imageView = finalv.ivFotoC2;
                            break;
                        case R.id.btnFotoC3:
                            cargarImagen();
                            imageView = finalv.ivFotoC3;
                            break;
                        case R.id.btnFotoC4:
                            cargarImagen();
                            imageView = finalv.ivFotoC4;
                            break;
                        case R.id.btnFotoC5:
                            cargarImagen();
                            imageView = finalv.ivFotoC5;
                            break;
                        case R.id.btnFotoC6:
                            cargarImagen();
                            imageView = finalv.ivFotoC6;
                            break;
                    }
                }
            };

            vHolder.btnCamara1.setOnClickListener(buttonClickListener);
            vHolder.btnCamara2.setOnClickListener(buttonClickListener);
            vHolder.btnCamara3.setOnClickListener(buttonClickListener);
            vHolder.btnCamara4.setOnClickListener(buttonClickListener);
            vHolder.btnCamara5.setOnClickListener(buttonClickListener);
            vHolder.btnCamara6.setOnClickListener(buttonClickListener);

            //Devolver al ListView la fila creada
            return convertView;
        }


    }
}