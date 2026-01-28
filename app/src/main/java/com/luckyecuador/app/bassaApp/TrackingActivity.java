package com.luckyecuador.app.bassaApp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyecuador.app.bassaApp.Clase.Tracking;
import com.luckyecuador.app.bassaApp.Clase.TrackingInsert;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.Mensajes;
import com.luckyecuador.app.bassaApp.Conexion.VerificarNet;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTracking;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.Sync.SyncAdapter;
import com.luckyecuador.app.bassaApp.Utils.DeveloperOptions;
import com.luckyecuador.app.bassaApp.Utils.GuardarLog;
import com.luckyecuador.app.bassaApp.Utils.RequestPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TrackingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper handler;
    public String catSelected, marcaSelected, fabricanteSelected;
    public String userCursor, catCursor, segmentoCursor, formaCursor, tamañoCursor, varianteCursor, codskuCursor, codigoClienteCursor, rutaCursor, visualCursor;
    public boolean exist;

    public String presencia, categoria, subcategoria, marca;
    private String id_pdv, user, rol, codigo_pdv, punto_venta, fecha, hora, format, negocio, subcanal,subchannel, cuentas, modulo, canal, supervisor;


    Spinner spCategoria;
    Spinner spSubcategoria;
    Spinner spMarca;

    RecyclerView listView;

    TextView empty;

    private ImageView imageView;
    private Bitmap bitmapfinal;

    private Spinner spNegocio;

    //Bandera para utilizar ambos list<String> en un mismo listview (al filtrar)
//    boolean bandera=false;
    ArrayList<Tracking> listProductos; //Lista general de productos
    String path;
    final int COD_FOTO=20;
    final int COD_SELECCIONA=10;
    private final String CARPETA_RAIZ="ToniApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Tareas";

    //Extras
    public String id_servidor, reExtra, codeExtra, localExtra, canalExtra, formatoClienteExtra,
            ciudadExtra, provinciaExtra, reabrevExtra, formatocpExtra, supervisorExtra,
            nombreComercialExtra;
    CustomAdapterTracking dataAdapter;

    List<String> list_cuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        new DeveloperOptions().modalDevOptions(TrackingActivity.this);
        RequestPermissions requestPermissions = new RequestPermissions(getApplicationContext(), TrackingActivity.this);
        requestPermissions.showPermissionDialog();

        LoadData();
        handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);

        /*System.out.println(list_cuentas);
        list_cuentas = Arrays.asList(cuentas.split(","));*/

        new GuardarLog(TrackingActivity.this).saveLog(user, codigo_pdv, "Ingreso a Tracking");

        spNegocio =(Spinner) findViewById(R.id.spnegocio);

        spCategoria = (Spinner)findViewById(R.id.spCategoria);
        spSubcategoria = (Spinner)findViewById(R.id.spSubcategoriaT);
        spMarca = (Spinner)findViewById(R.id.spMarca);

        listView = (RecyclerView) findViewById(R.id.lvSKUCode);

        empty = (TextView)findViewById(R.id.recyclerview_data_empty);

        filtrarCategoria();

        /*if (rol.equalsIgnoreCase("DESARROLLADOR")){  //en rol desarrollador tracking se llama promociones
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("PROMOCIONES");
        }*/
    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", android.content.Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV,Constantes.NODATA);
        user = sharedPreferences.getString(Constantes.USER, Constantes.NODATA);
        //rol = sharedPreferences.getString(Constantes.ROL,Constantes.NODATA);
        id_pdv = sharedPreferences.getString(Constantes.PHARMA_ID, Constantes.NODATA);
        codigo_pdv = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        subchannel = sharedPreferences.getString(Constantes.SUBCANAL, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.CANAL, Constantes.NODATA);
        //cuentas = sharedPreferences.getString(Constantes.CUENTAS, Constantes.NODATA);
        supervisor = sharedPreferences.getString(Constantes.SUPERVISOR, Constantes.NODATA);
        subcanal = sharedPreferences.getString(Constantes.SUBCANAL, Constantes.NODATA);
        modulo = sharedPreferences.getString(Constantes.MODULO, Constantes.NODATA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ACTIVITY", "ON RESUME");
        new DeveloperOptions().modalDevOptions(TrackingActivity.this);
    }


    /*public void filtrarNegocio() {
        List<String> operadores = handler.getCategoriaListTracking();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNegocio.setAdapter(dataAdapter);
        spNegocio.setOnItemSelectedListener(this);
    }*/

    /*
     *  FILTRAR SPINNER CATEGORY
     */
    public void filtrarCategoria(){
        List<String> subcat = handler.getCategoriaTracking();
        if (subcat.size()==2) {
            subcat.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subcat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
    }
    public void filtrarSubCategoria(String categoria){
        List<String> subcat = handler.getSubCategoriaTracking(categoria);
        if (subcat.size()==2) {
            subcat.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subcat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);
    }
    public void filtrarMarca(String categoria, String subcategoria){
        List<String> subcat = handler.getMarcaTracking(categoria, subcategoria);
        if (subcat.size()==2) {
            subcat.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subcat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);
    }

    /*
     * FIlTRAR LISTVIEW AL SELECCIONAR ITEM DE SPINNER
     */
    public void filtrarListView(String categoria, String subcategoria, String marca){
        Log.i("FILTRAR LIST PARAMS","NEGOCIO: "+ categoria + " subcanal"+subcategoria );
        listProductos = handler.filtrarListProductosTracking4(categoria, subcategoria, marca);
        Log.i("List Original",listProductos.size()+"");
        dataAdapter = new CustomAdapterTracking(this, listProductos);
        Log.i("totales..",""+dataAdapter.getItemCount());
        if(dataAdapter.getItemCount() != 0){
            listView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TrackingActivity.this);
            listView.setLayoutManager(linearLayoutManager);
            listView.setHasFixedSize(true);
            CustomAdapterTracking customAdapter = new CustomAdapterTracking(this.getApplicationContext(),listProductos);
            listView.setItemViewCacheSize(100);
            listView.setAdapter(customAdapter);
        }else{
            listView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }


    public void insertData(String id, String skuSelected, String status_actividad, String exh, String implementacion_pop, String comentario ){
        try{
//            obtenerCursores(skuSelected);
            //Almacenar Datos
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("dd/MM/yyy");
            date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String fechaser = date.format(currentLocalTime);

            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            Log.i("SKU", skuSelected);
            Log.i("SUBCHANNEL", subchannel);

            String categoria = handler.getViewCategoria(id, marca);
            String vigencia = handler.getViewVigenciaTracking(id, marca);
            String ppromo = handler.getViewPrecioPromocionTracking(id, marca);
            String mecanica = handler.getViewMecanicaTracking(id, marca);
            String material_pop = handler.getViewPOPTracking(id, marca);
            String latitud = handler.getLatitudPdv(codigo_pdv);
            String longitud = handler.getLongitudPdv(codigo_pdv);

            Bitmap temporal = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            String image = getStringImage(temporal);


            Log.i("VIGENCIA", vigencia);

          /*  if(mecanica.equalsIgnoreCase("FLANKERS")){
                implementacion_pop = "-";//
            }*/

            ContentValues values = new ContentValues();
            values.put(ContractInsertTracking.Columnas.PHARMA_ID, id_pdv);
            values.put(ContractInsertTracking.Columnas.FECHA, fechaser);
            values.put(ContractInsertTracking.Columnas.HORA, horaser);//userCursor
            values.put(ContractInsertTracking.Columnas.CODIGO, codigo_pdv);
            values.put(ContractInsertTracking.Columnas.LOCAL, punto_venta);//Spinner
            values.put(ContractInsertTracking.Columnas.USUARIO, user);
            values.put(ContractInsertTracking.Columnas.LATITUD, latitud);
            values.put(ContractInsertTracking.Columnas.LONGITUD, longitud);
            values.put(ContractInsertTracking.Columnas.MECANICA, mecanica);//CheckBox
            values.put(ContractInsertTracking.Columnas.CATEGORIA, categoria);
            values.put(ContractInsertTracking.Columnas.DESCRIPCION, skuSelected);
            values.put(ContractInsertTracking.Columnas.VIGENCIA, vigencia);
            values.put(ContractInsertTracking.Columnas.STATUS_ACTIVIDAD, status_actividad);
            values.put(ContractInsertTracking.Columnas.COMENTARIO, comentario);
            values.put(ContractInsertTracking.Columnas.FOTO, image);
            values.put(ContractInsertTracking.Columnas.PRECIO_PROMOCION, ppromo);
            values.put(ContractInsertTracking.Columnas.MATERIAL_POP, material_pop);
            values.put(ContractInsertTracking.Columnas.IMPLEMENTACION_POP, implementacion_pop);
            values.put(ContractInsertTracking.Columnas.CUENTA, "");
            values.put(ContractInsertTracking.Columnas.MODULO, modulo);
            /*values.put(ContractInsertTracking.Columnas.UNIDAD_DE_NEGOCIO, negocio);
            values.put(ContractInsertTracking.Columnas.EXHIBICION, exh);*/
            values.put(Constantes.PENDIENTE_INSERCION, 1);

            getContentResolver().insert(ContractInsertTracking.CONTENT_URI, values);

            if (VerificarNet.hayConexion(getApplicationContext())) {
                SyncAdapter.sincronizarAhora(this, true,Constantes.insertTracking, null);
                Toast.makeText(getApplicationContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),Mensajes.ON_SYNC_DEVICE,Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
//        Toast.makeText(context,fabricante + cm, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*if(parent== spNegocio){
            try{
                negocio = parent.getItemAtPosition(position).toString();
                //Log.i("fabricantes queibres",""+list_fabricantes2);
                //filtrarCategoria(negocio, subcanal);
                //listview.setAdapter(null);
                filtrarListView(negocio, subcanal);
                //
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }*/

        if(parent== spCategoria){
            Log.i("entro", "spcategoria");
            try{
                categoria = parent.getItemAtPosition(position).toString();
                filtrarSubCategoria(categoria);
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        if(parent== spSubcategoria){
            Log.i("entro", "spubcategoria");
            try{
                subcategoria = parent.getItemAtPosition(position).toString();
                filtrarMarca(categoria, subcategoria);
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        if(parent== spMarca){
            Log.i("entro", "spmarca");
            try{
                marca = parent.getItemAtPosition(position).toString();
                filtrarListView(categoria, subcategoria, marca);
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void cargarImagen() {
        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final android.app.AlertDialog.Builder alertOpciones=new android.app.AlertDialog.Builder(this);
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
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N) {
            String authorities=this.getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this, authorities,imagen);
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
                    imageView.setImageURI(miPath);
                    break;
                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
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
            android.app.AlertDialog alertDialog1;
            alertDialog1 = new android.app.AlertDialog.Builder(this).create();
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


    public class CustomAdapterTracking extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private final String TAG = CustomAdapterTracking.class.getSimpleName();
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;
        private Context context;
        public ArrayList<Tracking> values;
        ArrayList<TrackingInsert> tracking = new ArrayList<>();
        ArrayList<TrackingInsert> tracking_sesion = new ArrayList<>();
        int selectedItem = -1;
        String status, exhibicion, material_pop;

        public CustomAdapterTracking(Context context, ArrayList<Tracking> values) {
            this.context = context;
            this.values = values;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_tracking_title, parent, false);
                return new HeaderViewHolder(layoutView);
            } else if (viewType == TYPE_ITEM) {
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_tracking, parent, false);
                return new ItemViewHolder(layoutView);
            }
            throw new RuntimeException("No match for " + viewType + ".");
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            Tracking mObject = values.get(position);

            if (holder instanceof HeaderViewHolder) {
                ((HeaderViewHolder) holder).lblDescripcion.setText("Descripción");
                ((HeaderViewHolder) holder).lblStatus.setText("Status Actividad");
                //      ((HeaderViewHolder) holder).lblPOP.setText("Implementación POP");
                ((HeaderViewHolder) holder).lblComentario.setText("Comentario");
            } else if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).lblId.setText(mObject.getId());
                ((ItemViewHolder) holder).txt_sku.setText(mObject.getDescripcion());

//                ((ItemViewHolder) holder).txt_sku.setTextColor(Color.parseColor("#000000"));
//                ((ItemViewHolder) holder).rbStatusSi.setChecked(false);
//                ((ItemViewHolder) holder).rbStatusNo.setChecked(false);
//                ((ItemViewHolder) holder).rbPopSi.setChecked(false);
//                ((ItemViewHolder) holder).rbPopNo.setChecked(false);
//                ((ItemViewHolder) holder).txtComentario.setText("");

                ((ItemViewHolder) holder).rbPopSi.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).rbPopNo.setVisibility(View.VISIBLE);
                if (mObject.getMecanica().equalsIgnoreCase("Flankers")) {
                    ((ItemViewHolder) holder).txt_sku.setTextColor(Color.parseColor("#ED4399"));
                    ((ItemViewHolder) holder).rbPopSi.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).rbPopNo.setVisibility(View.GONE);
                } /*else {
                    ((ItemViewHolder) holder).rbPopSi.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).rbPopNo.setVisibility(View.VISIBLE);
                }*/

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                String fecha_actual = date.format(currentLocalTime);

                tracking_sesion = handler.getListProductosTrackingGuardados(user, codigo_pdv, fecha_actual);
                for(int i = 0; i < tracking_sesion.size(); i++) {
                    if (mObject.getDescripcion().equalsIgnoreCase(tracking_sesion.get(i).getDescripcion())) {
                        ((ItemViewHolder) holder).txt_sku.setTextColor(Color.parseColor("#59C119"));
                        //((ItemViewHolder) holder).checkGuardar.setBackgroundResource(R.drawable.button_background_desactive);
                        //((ItemViewHolder) holder).checkGuardar.setEnabled(false);
                    }
                }

                Log.i("ReclyclerSize", tracking.size()+"");
                Log.i("position", position+"");
                for (int i=0; i < tracking.size(); i++) {
                    Log.i("ITEM-GRAY", tracking.get(i).getId()+"");
                    if (position == tracking.get(i).getId()) {
                        ((ItemViewHolder) holder).txt_sku.setTextColor(Color.parseColor("#59C119"));
                    }
                }
            }
        }

        public void setSelectedItem(int item) {
            TrackingInsert objTracking = new TrackingInsert();
            objTracking.setId(item);
            this.tracking.add(objTracking);
            this.selectedItem = item;
            this.notifyItemChanged(selectedItem);
        }

        private Tracking getItem(int position) {
            return values.get(position);
        }

        @Override
        public int getItemCount() {
            return values.size();
//            return Integer.MAX_VALUE;
        }

        //used to retrieve the effective item position in list
        public int getActualItemCount() {
            if (values == null) {
                values = new ArrayList<>();
            }
            return values.size();
        }
        @Override
        public int getItemViewType(int position) {
            /*if (isPositionHeader(position))
                return TYPE_HEADER;*/
            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        public void filterList(ArrayList<Tracking> filteredList) {
            listProductos = filteredList;
            Log.i("LIST",listProductos.size()+"");
            TrackingActivity.this.dataAdapter.notifyDataSetChanged();
        }

        public class HeaderViewHolder extends RecyclerView.ViewHolder{

            public TextView lblDescripcion, lblStatus, lblPOP, lblComentario;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                lblDescripcion = (TextView)itemView.findViewById(R.id.lblSku);
                lblStatus = (TextView)itemView.findViewById(R.id.lblStatus);
                //lblPOP = (TextView)itemView.findViewById(R.id.lblPOP);
                lblComentario = (TextView)itemView.findViewById(R.id.lblComentario);
            }
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageButton ibInfo;
            public TextView lblId;
            public TextView txt_sku;
            public RadioGroup rgStatus, rgExhibicion, rgPOP;
            public RadioButton rbStatusSi,rbExhSi;
            public RadioButton rbStatusNo,rbExhNo;
            public RadioButton rbPopSi;
            public RadioButton rbPopNo;
            public ImageButton ibFotoTrack;
            public ImageView ivFotoTrack;
            //  public Spinner spComentario;
            public EditText txtComentario;
            public Button checkGuardar;

            public TextView lblCategoria;
            public TextView lblDescripcion;
            public TextView lblVigencia;
            public TextView lblPrecioPromo;
            public TextView lblMecanica;
            public TextView lblPOP;

            public ItemViewHolder(View itemView) {
                super(itemView);
                lblId = (TextView)itemView.findViewById(R.id.lblId);
                txt_sku = (TextView)itemView.findViewById(R.id.lblSku);
                ibInfo = (ImageButton) itemView.findViewById(R.id.ibInfo);
                rgStatus = (RadioGroup) itemView.findViewById(R.id.rgStatus);
                rgExhibicion = (RadioGroup) itemView.findViewById(R.id.rgExhibicion);
                rgPOP = (RadioGroup) itemView.findViewById(R.id.rgMaterial);
                rbStatusSi = (RadioButton) itemView.findViewById(R.id.rbStatusSi);
                rbStatusNo = (RadioButton) itemView.findViewById(R.id.rbStatusNo);
                rbExhSi = (RadioButton) itemView.findViewById(R.id.rbExhSi);
                rbExhNo = (RadioButton) itemView.findViewById(R.id.rbExhNo);
                rbPopSi = (RadioButton) itemView.findViewById(R.id.rbMatSi);
                rbPopNo = (RadioButton) itemView.findViewById(R.id.rbMatNo);
                //   spComentario = (Spinner) itemView.findViewById(R.id.spComentario);
                txtComentario = (EditText) itemView.findViewById(R.id.txtComentario);
                ibFotoTrack = (ImageButton) itemView.findViewById(R.id.ibFotoTrack);
                ivFotoTrack = (ImageView) itemView.findViewById(R.id.ivFotoTrack);

                checkGuardar = (Button) itemView.findViewById(R.id.checkGuardar);

                ibInfo.setOnClickListener(this);
                checkGuardar.setOnClickListener(this);

                ibFotoTrack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cargarImagen();
                        imageView = ivFotoTrack;
                    }
                });

                rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (rbStatusSi.isChecked()) {
                            status = "SI";
                            rgPOP.clearCheck();
                            rbPopNo.setChecked(false);
                            rbPopSi.setEnabled(true);
                        }else if (rbStatusNo.isChecked()) {
                            status = "NO";
                            rgPOP.clearCheck();
                            rbPopNo.setChecked(true);
                            rbPopSi.setEnabled(false);
//                            List<String> comentario = handler.getComentarioTracking(status);
//                            ((ItemViewHolder) holder).spComentario.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, comentario));
                        }
                        //     List<String> comentario = handler.getComentarioTracking(status);
                        // spComentario.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, comentario));
                        //     txtComentario.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, comentario));
                    }
                });

                rgExhibicion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (rbExhSi.isChecked()) {
                            exhibicion = "SI";
                        }else if (rbStatusNo.isChecked()) {
                            exhibicion = "NO";
                        }
                    }
                });

                rgPOP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (rbPopSi.isChecked()) {
                            material_pop = "SI";
                        }else if (rbPopNo.isChecked()) {
                            material_pop = "NO";
                        }
                    }
                });

//                final int position = getAdapterPosition();
//                Log.i("POSITION", position + "");
//                notifyItemChanged(position);
            }

            @Override
            public void onClick(View v) {
                if (v==ibInfo) {
                    String id = lblId.getText().toString();
//                    final int position = getAdapterPosition();
//                    Log.i("POSITION", position + "");
                    alertDialog(id);
                }

                if (v==checkGuardar) {
                    //GET POSITION
                    final int position = getAdapterPosition();
                    //GET ELEMENTOS
                    String id = lblId.getText().toString();
                    String sku = txt_sku.getText().toString();
                    String comentario = txtComentario.getText().toString();
                    String status = "", pop = "",exh= "";
                    String image = "";
                    Bitmap bitmapFoto = null;

                    if(rbStatusSi.isChecked()){
                        status = "Si";
                    } else if (rbStatusNo.isChecked()){
                        status = "No";
                    }

                    /*if(rbExhSi.isChecked()){
                        exh = "Si";
                    } else if (rbExhNo.isChecked()){
                        exh = "No";
                    }

                    if(rbPopSi.isChecked()){
                        pop = "Si";
                    } else if (rbPopNo.isChecked()){
                        pop = "No";
                    }*/

                    if (esFormularioValido(status, pop, exh, comentario)) {
                        insertData(id, sku, status, exh, pop, comentario);
                        setSelectedItem(position);
                        limpiarCampos();
                    } else {
                        /*checkGuardar.setEnabled(false);
                        checkGuardar.setBackgroundColor(Color.GRAY);*/
                    }
                }
            }

            public void limpiarCampos(){
                //LIMPIAR CAMPOS
                rgStatus.clearCheck();
                rgPOP.clearCheck();
                rgExhibicion.clearCheck();
                rbStatusSi.setChecked(false);
                rbStatusNo.setChecked(false);
                rbExhSi.setChecked(false);
                rbExhNo.setChecked(false);
                rbPopSi.setChecked(false);
                rbPopSi.setEnabled(true);
                rbPopNo.setChecked(false);
                //    spComentario.setAdapter(null);
                txtComentario.setText("");
                //checkGuardar.setEnabled(false);
                ivFotoTrack.setImageResource(0);
            }

            public boolean esFormularioValido(String status, String pop, String exh, String comentario){
                boolean popVisible = true;
          /*      if (rbPopSi.getVisibility()==View.GONE && rbPopNo.getVisibility()==View.GONE) {
                    popVisible = false;
                }*/

                if (ivFotoTrack.getDrawable() == null) {
                    Toast.makeText(context, "Por favor tomar una foto", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(status.equals("No")){
                    if (comentario.trim().isEmpty()) {
                        Toast.makeText(context, "Debe ingresar el Comentario", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (status.trim().isEmpty()) {
                    Toast.makeText(context, "Debe seleccionar el Status de la Actividad", Toast.LENGTH_SHORT).show();
                    return false;
                }
                /*if (exh.trim().isEmpty()) {
                    Toast.makeText(context, "Debe seleccionar si hay Exhibición", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (pop.trim().isEmpty() && popVisible) {
                    Toast.makeText(context, "Debe seleccionar si hay Implementación POP", Toast.LENGTH_SHORT).show();
                    return false;
                }*/
                return true;
            }

            public void alertDialog(String id){
                AlertDialog.Builder builder = new AlertDialog.Builder(TrackingActivity.this);
//                builder.setCancelable(false);
//                LayoutInflater inflater = this.getLayoutInflater();
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.alertdialog_info_tracking, null);
                //Title
                builder.setIcon(android.R.drawable.ic_menu_set_as);
                builder.setTitle("Tracking");
                //      builder.setView(LayoutInflater.from(context).inflate(R.layout.alertdialog_exh,null));

                //Traer Views
                lblCategoria = (TextView) dialogView.findViewById(R.id.lblCategoria);
                lblDescripcion = (TextView) dialogView.findViewById(R.id.lblDescripcion);
                lblVigencia = (TextView) dialogView.findViewById(R.id.lblVigencia);
                lblPrecioPromo = (TextView) dialogView.findViewById(R.id.lblPrecioPromo);
                lblMecanica = (TextView) dialogView.findViewById(R.id.lblMecanica);
                lblPOP = (TextView) dialogView.findViewById(R.id.lblPOP);

                String categoria = handler.getViewCategoria(id, marca);
                String sku = handler.getViewSkuTracking(id, marca);
                String vigencia = handler.getViewVigenciaTracking(id, marca);
                String ppromo = handler.getViewPrecioPromocionTracking(id, marca);
                String mecanica = handler.getViewMecanicaTracking(id, marca);
                String pop = handler.getViewPOPTracking(id, marca);

                lblCategoria.setText(categoria);
                lblDescripcion.setText(sku);
                lblVigencia.setText(vigencia);
                lblPrecioPromo.setText(ppromo);
                lblMecanica.setText(mecanica);
                lblPOP.setText(pop);

                builder.setNegativeButton(R.string.cancel,null);

                builder.setView(dialogView);
                AlertDialog ad = builder.create();
                ad.show();
            }
        }
    }

}
