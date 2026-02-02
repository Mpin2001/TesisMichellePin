package com.tesis.michelle.pin.Adaptadores;

import static android.content.Context.LOCATION_SERVICE;
import static com.tesis.michelle.pin.ServiceRastreo.LocationService.LOCATION_DISTANCE;
import static com.tesis.michelle.pin.ServiceRastreo.LocationService.LOCATION_INTERVAL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import com.tesis.michelle.pin.CameraActivity;
import com.tesis.michelle.pin.Clase.BasePharmaValue;
import com.tesis.michelle.pin.Clase.Base_pharma_value;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Conexion.VolleySingleton;
import com.tesis.michelle.pin.Contracts.ContractInsertGps;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.GaleriaActivity;
import com.tesis.michelle.pin.MenuNavigationActivity;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Sync.SyncAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "GifHeaderParser";
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private List<BasePharmaValue> mPuntoVentas;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public static ImageView imageView;
    private AlertDialog adJustificacion;
    private Double latitud = 0.0,longitud = 0.0;
    public static Bitmap bitmapfinal;
    private Context mContext;
    private EditText txtLongitud_geo;
    private EditText txtLatitud_geo;
    private AlertDialog adGeoreferencia;
    String modulo;

    public PostRecyclerAdapter(Context context, List<BasePharmaValue> postItems) {

        this.mContext = context;
        this.mPuntoVentas = postItems;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);

        locationManager = (LocationManager)  mContext.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try{

                    if (latitud != null && longitud != null){

                        //Setear los EditText
                        if (!latitud.equals(location.getLatitude())&&!longitud.equals(location.getLongitude()))
                        {
                            latitud = location.getLatitude();
                            longitud = location.getLongitude();

                            if (txtLatitud_geo != null && txtLongitud_geo != null){

                                txtLatitud_geo.setText(""+ latitud);
                                txtLongitud_geo.setText(""+longitud);

                            }

                        }

                    }

                    locationManager.removeUpdates(locationListener);
//                locationManager = null;
                }catch (Exception e) {
                    Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
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
                mContext.startActivity(newIntent);
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPuntoVentas.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPuntoVentas == null ? 0 : mPuntoVentas.size();
    }

    public void add(BasePharmaValue response) {
        mPuntoVentas.add(response);
        notifyItemInserted(mPuntoVentas.size() - 1);
        //notifyItemInserted(mPuntoVentas.size());
    }

    public void addAll(List<BasePharmaValue> postItems) {
        for (BasePharmaValue response : postItems) {
            Log.i("ADD PDV", response.getPos_id());
            add(response);
        }
    }

    private void remove(BasePharmaValue postItems) {
        int position = mPuntoVentas.indexOf(postItems);
        if (position >= 0) {
            mPuntoVentas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new BasePharmaValue());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPuntoVentas.size() - 1;
        BasePharmaValue item = getItem(position);
        if (item != null) {
            mPuntoVentas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    BasePharmaValue getItem(int position) {
        return mPuntoVentas.get(position);
    }

    public class ViewHolder extends BaseViewHolder {

        private final Context context;
        private String reSelected, localSelected, id_ruta, codigo, channel, id_servidor, fecha, hora, bateria,justificacion, pos_name, usuarioCursor, coordCursor;
        private int radioButtonSelected = -1;
        DatabaseHelper handler;

        CardView card_view;
        TextView id;
        TextView textViewTitle;
        TextView textViewTitle2;
        TextView textViewDescription;
        TextView textViewFormato;
        TextView textViewNombreComercial;
        TextView textViewProvincia;
        Button btnJustificar;

        RadioGroup radioGroup;
        EditText txtotraJustificacion;
        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            card_view = itemView.findViewById(R.id.card_view);
            id = itemView.findViewById(R.id.id);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTitle2 = itemView.findViewById(R.id.textViewTitle2);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewFormato = itemView.findViewById(R.id.textViewFormato);
            textViewNombreComercial = itemView.findViewById(R.id.textViewNombreComercial);
            textViewProvincia = itemView.findViewById(R.id.textViewProvincia);
            btnJustificar = itemView.findViewById(R.id.btnJustificar);
//            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);
            final BasePharmaValue item = mPuntoVentas.get(position);

            textViewTitle.setText(item.getPos_id());
            id.setText(item.getId());
            textViewDescription.setText(Html.fromHtml("<b>Barrio:</b> " + item.getPos_name()));
            textViewFormato.setText(Html.fromHtml("<b>Nivel:</b> " + item.getChannel()));
            textViewNombreComercial.setText(Html.fromHtml("<b>Dirección:</b> " + item.getAddress()));
            textViewProvincia.setText(Html.fromHtml("<b>Ciudad:</b> " + item.getCity()));
            codigo = item.getPos_id();
            pos_name = item.getPos_name();
            modulo = item.getModulo();
            LoadData();
            handler = new DatabaseHelper(context, Provider.DATABASE_NAME, null, 1);

            // Si ya registro su justificacion
            if (handler.tieneJustificacion(codigo, usuarioCursor)){
                btnJustificar.setEnabled(false);
                Log.i("BOTON DESACTIVADO", "SI");
                btnJustificar.setBackgroundResource(R.drawable.boton_desactivado);
            }else{
                btnJustificar.setEnabled(true);
                Log.i("BOTON DESACTIVADO", "NO");
                btnJustificar.setBackgroundResource(R.drawable.boton_justificar);
            }


//            textViewTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textViewTitle2.setVisibility(View.VISIBLE);
            if (item.getTermometro().equals("0")) {
                textViewTitle2.setVisibility(View.GONE);
//                textViewTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_termometro, 0);
            }

            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!item.getPos_id().trim().isEmpty() && !item.getPos_id().equals("")) {
                        handler = new DatabaseHelper(context, Provider.DATABASE_NAME, null, 1);
                        Log.i("PDV SELECTED", item.getPos_id());
                        id_ruta = item.getId();
                        codigo = item.getPos_id();
                        channel = item.getChannel();
                        modulo = item.getModulo();
                        Log.i("CHANNEL", channel);
                        LoadData();
                        String client = handler.getPosNamePdv(codigo);
                        alertDialog(codigo, client, channel, id_ruta, modulo);
                    } else {
                        Toast.makeText(context,"CODIGO NO VÁLIDO", Toast.LENGTH_LONG).show();
                    }
                }
            });

            btnJustificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtenerCoordenadas();
                    alertDialogJustificar();
                }
            });
        }

        private void obtenerCoordenadas() {
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                        locationListener);
            } catch (SecurityException ex) {
                Log.i(TAG, "fail to request location update, ignore", ex);
            } catch (IllegalArgumentException ex) {
                Log.d(TAG, "network provider does not exist, " + ex.getMessage());
            }
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                        locationListener);
            } catch (SecurityException ex) {
                Log.i(TAG, "fail to request location update, ignore", ex);
            } catch (IllegalArgumentException ex) {
                Log.d(TAG, "gps provider does not exist " + ex.getMessage());
            }
        }

        public void alertDialogJustificar(){

            LayoutInflater myLayout = LayoutInflater.from(context);
            View dialogView = myLayout.inflate(R.layout.alertdialog_justificacion, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("Justificación");
            builder.setCancelable(false);

            radioGroup = dialogView.findViewById(R.id.radioGroup);
            txtotraJustificacion = dialogView.findViewById(R.id.txtOtraJustificacion);
            ImageButton btnCamera = dialogView.findViewById(R.id.btnCamera);
            imageView = dialogView.findViewById(R.id.ivFotoRegistro);


            // Obtenemos las Justificaciones
            List<String> arrayJustificaciones = handler.getJustificacion();

            int contadorId = 1;

            // Recorremos las justificaciones del Repositorio
            for (String item : arrayJustificaciones) {
                // Creamos el radiobutton y le damos estilos
                RadioButton radioButton = new RadioButton(context);
                Log.i("ITEM JUSTIFICACION", item);
                radioButton.setText(item);
                //Soporta todas las versiones
                Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_light_italic);
                radioButton.setTypeface(typeface);
                radioButton.setId(contadorId);
                radioGroup.addView(radioButton);
                contadorId++;
            }



            btnCamera.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cargarImagen();
                }
            });



            //cuando se halla seleccionado
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    radioButtonSelected = checkedId;

                    // Verificar que este seleccionado un radiobutton
                    if (radioButtonSelected != -1){

                        // Obtenemos el radiobbutton seleccionado
                        RadioButton radioButtonSelected = dialogView.findViewById(checkedId);

                        //Obtenemos la justificacion
                        justificacion = radioButtonSelected.getText().toString();

                        // En caso , que se seleccione "OTRAS" se activara el editext para agregar una justificacion
                        if (justificacion.equalsIgnoreCase("OTRAS")){
                            txtotraJustificacion.setVisibility(View.VISIBLE);
                        }else{
                            txtotraJustificacion.setVisibility(View.GONE);
                        }

                    }

                }


            });

            builder.setPositiveButton("Guardar",null);


            builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    radioButtonSelected = -1;
                }
            });

            adJustificacion = builder.create();
            adJustificacion.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {

                    Button btnGuardar = ((AlertDialog)adJustificacion).getButton(AlertDialog.BUTTON_POSITIVE);
                    btnGuardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(validarFormulario()){
                                alertDialogConfirmar();
                            }


                        }
                    });

                }
            });


            adJustificacion.show();

        }

        public void alertDialogConfirmar(){


            LayoutInflater myLayout = LayoutInflater.from(context);
            View dialogView = myLayout.inflate(R.layout.alertdialog_confirmacion_justificacion, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            builder.setIcon(android.R.drawable.ic_dialog_alert);


            TextView tvPDV = dialogView.findViewById(R.id.tvPDV);
            Button btnConfirmar = dialogView.findViewById(R.id.btnConfirmar);
            Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

            AlertDialog ad = builder.create();

            tvPDV.setText(pos_name);

            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (latitud != 0.0 && longitud != 0.0){
                        if(insertJustificacion(latitud,longitud,justificacion)){
                            ad.dismiss();
                            adJustificacion.dismiss();
                            notifyDataSetChanged();
                            txtotraJustificacion.setText("");
                        }
                    }else{
                        Toast.makeText(context, "Generando las Coordenadas. , Vuelva a intentarlo.", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ad.dismiss();
                }
            });


            ad.show();
        }

        public boolean validarFormulario(){
            // Comprobamos que se haya marcado alguna de las opciones
            if (radioButtonSelected != -1) {

                // En caso que se haya escogido la opcion "OTRAS" , se habilitara el campo para escribir la justificacion
                if (justificacion.equalsIgnoreCase("OTRAS")){

                    if (txtotraJustificacion.getText().toString().equalsIgnoreCase("") || txtotraJustificacion.getText().toString().isEmpty() || txtotraJustificacion.getText().toString() == null){
                        Toast.makeText(context,"Debe escribir su justificación.",Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        justificacion = txtotraJustificacion.getText().toString();
                        return true;
                    }
                }else{
                    return true;
                }
            }else{
                Toast.makeText(context, "Debe escoger una justificación.", Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        private void cargarImagen() {

            final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
//        final CharSequence[] opciones={"Tomar Foto","Cancelar"};
            final androidx.appcompat.app.AlertDialog.Builder alertOpciones=new androidx.appcompat.app.AlertDialog.Builder(context);
            alertOpciones.setTitle("Seleccione una Opción");
            alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (opciones[i].equals("Tomar Foto")){
                        Intent n = new Intent(context, CameraActivity.class);
                        n.putExtra("activity", "justificar");
                        context.startActivity(n);

                    } else {
                        if (opciones[i].equals("Cargar Imagen")) {
                        /* Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);*/
                            //  openGallery();
                            Intent n = new Intent(context, GaleriaActivity.class);
                            context.startActivity(n);
                        } else {
                            dialogInterface.dismiss();
                        }
                    }
                }
            });
            alertOpciones.show();
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

        public boolean insertJustificacion(double latitud, double longitud, String justificacion) {
            try {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                String fechaser = date.format(currentLocalTime);

                DateFormat hour = new SimpleDateFormat("HH:mm:ss");
                hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                String horaser = hour.format(currentLocalTime);

                handler = new DatabaseHelper(context, Provider.DATABASE_NAME, null, 1);

                String registro = "JUSTIFICACION";

                String image = "";

                if (imageView != null && imageView.getDrawable() != null){
                    bitmapfinal = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    image = getStringImage(bitmapfinal);
                }else{
                    image = "NO FOTO";
                }

                Base_pharma_value bpv = handler.getInfoPDV(codigo);


                ContentValues values = new ContentValues();

                values.put(ContractInsertGps.Columnas.IDPDV, codigo);
                values.put(ContractInsertGps.Columnas.POS_NAME, bpv.getPos_name());
//                values.put(ContractInsertGps.Columnas.FOTO, bpv.getFoto());
                values.put(ContractInsertGps.Columnas.USUARIO,usuarioCursor );
                values.put(ContractInsertGps.Columnas.TIPO, registro);
                values.put(ContractInsertGps.Columnas.VERSION,context.getResources().getString(R.string.version));
                values.put(ContractInsertGps.Columnas.LATITUDE, latitud + "");
                values.put(ContractInsertGps.Columnas.LONGITUDE, longitud + "");
                values.put(ContractInsertGps.Columnas.FECHA, fechaser);
                values.put(ContractInsertGps.Columnas.HORA, horaser);
                values.put(ContractInsertGps.Columnas.CAUSAL, justificacion);
                values.put(ContractInsertGps.Columnas.FOTO, image);
                values.put(Constantes.PENDIENTE_INSERCION, 1);
                context.getContentResolver().insert(ContractInsertGps.CONTENT_URI, values);

                Log.i("TIPO REGISTRO", registro);


                Toast.makeText(context, "Registrada su "+registro+" : " + horaser + "; Coordenadas: " + latitud + ", " + longitud, Toast.LENGTH_SHORT).show();

                if (VerificarNet.hayConexion(context)) {
                    SyncAdapter.sincronizarAhora(context, true, Constantes.insertGps, null);
                    Toast.makeText(context, Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
                }
                return true;
//            } else {
//                Toast.makeText(getApplicationContext(), "Genere las Coordenadas", Toast.LENGTH_SHORT).show();
//                return false;
//            }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }


        }

        public void alertDialog(String codigo, String client, String channel, String id_ruta, String modulo) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle("Ingreso al PDV");
            builder.setMessage("¿Desea ingresar al PDV " + client + " a relevar información?");
//            builder.setPositiveButton("Interno", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    openMenu("INTERNO", channel, id_ruta);
//                }
//            });
//
//            builder.setNegativeButton("Externo",new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    openMenu("EXTERNO", channel, id_ruta);
//                }
//            });

            builder.setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String latitud_pdv =  handler.getLatitudPdv(codigo);
                    String longitud_pdv = handler.getLongitudPdv(codigo);

                    if ((latitud_pdv.equalsIgnoreCase("0") || longitud_pdv.equalsIgnoreCase("0")) && modulo.equals(Constantes.MODULO_PUNTOS_PRINCIPAL)){
                        alertDialogGeoreferencia();
                    }else{
                        openMenu("", channel, id_ruta, modulo);
                    }

                }
            });


            builder.setNeutralButton("Cancelar",null);

            AlertDialog ad = builder.create();
            ad.show();
        }

        public void openMenu(String tipo_relevo, String channel, String id_ruta, String modulo) {
//            boolean interno = false;
//            if (tipo_relevo.equalsIgnoreCase("INTERNO")) {
//                interno = true;
//            }

            String idpdv = handler.getIdPdv(codigo);
            String tipo = handler.getChannelSegmentPdv(codigo);
            String puntoventa = handler.getZonePdv(codigo);
            String subcanal = handler.getSubcanalPdv(codigo);
            String rol = handler.getRolNuevo(codigo); //cambiar hoy 19
            String ciudad = handler.getCiudad(codigo);
            String formato = handler.getFormatPdv(codigo);
            String direccion = handler.getDireccionPdv(codigo);
            String cliente = handler.getPosNamePdv(codigo);
            String zona = handler.getChannelPdv(codigo);
            String format = handler.getFormat(codigo); //Código FABRIL
            String vendedor = handler.getNombreComercial(codigo); //Vendedor Asignado
            String celular = handler.getNumeroController(codigo); //Celular Asignado (del Vendedor para el SMS)
            String supervisor = handler.getSupervisorByCodigo(codigo); //Celular Asignado (del Vendedor para el SMS)
            String channel_segment = handler.getChannelSegmentPdv2(codigo);

            boolean vaFoto = handler.validarCanales(subcanal);

            //se cae sin pos_id
            SaveData(idpdv, codigo, usuarioCursor, cliente, format, tipo, subcanal, rol, vendedor, celular, channel, id_ruta, supervisor,ciudad,vaFoto,channel_segment);
            insertData(idpdv, codigo, tipo, cliente, formato, direccion, cliente, zona);

            Intent in = new Intent(context, MenuNavigationActivity.class);
//            in.putExtra(Constantes.INTERNO, interno);
            in.putExtra(Constantes.CANAL, channel);
            in.putExtra(Constantes.SHOW_PRIORITARIOS, true);
            in.putExtra(Constantes.MODULO, modulo);
            context.startActivity(in);
            Activity activity = (Activity) context;
            activity.finish();
        }

        public void alertDialogGeoreferencia(){

            LayoutInflater myLayout = LayoutInflater.from(context);
            View dialogView = myLayout.inflate(R.layout.alertdialog_georeferencia, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            builder.setCancelable(false);
            TextView PDV = dialogView.findViewById(R.id.tv_local);
            ImageButton ibLocalizacion = dialogView.findViewById(R.id.ibLocalizacion);
            txtLongitud_geo = dialogView.findViewById(R.id.txtLongitud);
            txtLatitud_geo = dialogView.findViewById(R.id.txtLatitud);
            PDV.setText("PDV: "+pos_name);


            ibLocalizacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txtLatitud_geo.setHint("Buscando..");
                    txtLongitud_geo.setHint("Buscando..");
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                                locationListener);
                    } catch (SecurityException ex) {
                        Log.i(TAG, "fail to request location update, ignore", ex);
                    } catch (IllegalArgumentException ex) {
                        Log.d(TAG, "network provider does not exist, " + ex.getMessage());
                    }
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                                locationListener);
                    } catch (SecurityException ex) {
                        Log.i(TAG, "fail to request location update, ignore", ex);
                    } catch (IllegalArgumentException ex) {
                        Log.d(TAG, "gps provider does not exist " + ex.getMessage());
                    }
                }
            });


            builder.setPositiveButton("Guardar",null);
            builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            adGeoreferencia = builder.create();


            adGeoreferencia.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {

                    Button btnGuardar = ((AlertDialog)adGeoreferencia).getButton(AlertDialog.BUTTON_POSITIVE);
                    btnGuardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (latitud != 0.0  && longitud != 0.0){
                                actualizarCoordenadasPDV(context,codigo, latitud,longitud);
                            }else
                            {
                                Toast.makeText(context,"Por favor,genere las coordenadas",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            });


            adGeoreferencia.show();

        }

        public void actualizarCoordenadasPDV(Context context, String codigo_pdv, Double latitud,Double longitud){
            try{
                obtenerFecha();

                HashMap<String, String> map = new HashMap<>();
                map.put("codigo_pdv", codigo_pdv);
                map.put("latitud", ""+latitud);
                map.put("longitud",""+longitud);
                map.put("usuario",""+usuarioCursor);
                map.put("fecha",""+fecha);
                map.put("hora",""+hora);
                // Crear nuevo objeto Json basado en el mapa
                JSONObject jobject = new JSONObject(map);

                //GET METHOD
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constantes.UPDATE_COORDENADAS_PDV, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaActualizarCoordenadasPDV(response, context);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Log.i("ERROR JSON USER DEVICE", Mensajes.TIME_OUT);
                        } else if (error instanceof NoConnectionError) {
                            Log.i("ERROR JSON USER DEVICE", Mensajes.NO_RED);
                        }else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            Log.i("ERROR JSON USER DEVICE", Mensajes.SEVER_ERROR);
                        } else if (error instanceof NetworkError) {
                            Log.i("ERROR JSON USER DEVICE", Mensajes.RED_ERROR);
                        } else if (error instanceof ParseError) {
                            //TODO
                        }
                    }
                });

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            }catch (Exception e){
                Log.i("ERROR REQUEST USER DEVICE", e.getMessage());
                e.printStackTrace();
            }
        }


        public void procesarRespuestaActualizarCoordenadasPDV(JSONObject response, Context context){

            try{
                String estado = response.getString("estado");
                String mensaje = response.getString("mensaje");

                Log.i("estado",""+estado);

                switch (estado){
                    case "1": //EXITO

                        handler.actualizarCoordenadasPDV(codigo, latitud,longitud);
                        Toast.makeText(context,""+mensaje,Toast.LENGTH_LONG).show();

                        adGeoreferencia.dismiss();
                        //  progressBarCoordenadas();
                        openMenu("", channel, id_ruta, modulo);
                        break;
                    case "2": //FALLIDO
                        Toast.makeText(context,""+mensaje,Toast.LENGTH_LONG).show();
                        adGeoreferencia.dismiss();
                        break;
                }



            }catch (JSONException e){
                e.printStackTrace();
            }
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

        private void SaveData(String idpdv, String codigo, String user, String cliente, String format,
                              String tipo, String subcanal, String rol,String vendedor, String celular, String canal,
                              String id_ruta, String supervisor, String ciudad, Boolean vaFoto, String channel_segment) {
            try {
                obtenerFecha();
                String pharma_id = idpdv;

                if (!pharma_id.equals("") && pharma_id != null) {
                    //String format = spFormat.getSelectedItem().toString();
                    SharedPreferences sharedPref = context.getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(Constantes.USER, user);
                    editor.putString(Constantes.SUPERVISOR, supervisor);
                    editor.putString(Constantes.PHARMA_ID, pharma_id);
                    editor.putString(Constantes.CODIGO, codigo);
                    editor.putString(Constantes.PDV, cliente);
                    editor.putString(Constantes.CANAL, canal);
                    editor.putString(Constantes.SUBCANAL, subcanal);
                    editor.putString(Constantes.ROLNUEVO, rol);
                    editor.putString(Constantes.FECHA, fecha);
                    editor.putString(Constantes.HORA, hora);
                    editor.putString(Constantes.FORMAT, format);
                    editor.putString(Constantes.IDPDV, idpdv);
                    editor.putString(Constantes.TIPO, tipo);
                    editor.putString(Constantes.VENDEDOR, vendedor);
                    editor.putString(Constantes.CELULAR, celular);
                    editor.putString(Constantes.ID_RUTA, id_ruta);
                    editor.putBoolean(Constantes.FALTA_SALIDA, false);
                    editor.putString(Constantes.CIUDAD, ciudad);
                    editor.putBoolean(Constantes.VAFOTO, vaFoto);
                    editor.putString(Constantes.CHANNEL_SEGMENT, channel_segment);
                    editor.commit();

                } else {
                    Toast.makeText(context, Mensajes.DATA_NULL, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        public void insertData(String id_pdv, String codigo, String tipo, String cliente, String ubicacion, String correo, String lati, String longi) {
            if (id_pdv != null) {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("dd/MM/yyy");
                date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                String fechaser = date.format(currentLocalTime);

                DateFormat hour = new SimpleDateFormat("HH:mm:ss");
                hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
                String horaser = hour.format(currentLocalTime);

    /*            MyService x = new MyService();
                MyService.MyLocationListener y = x.new MyLocationListener();

                String latitude = y.getLatitud();
                String longitude = y.getLongitud();

                //Almacenar Datos
                ContentValues values = new ContentValues();
                values.put(ContractInsertInicial.Columnas.IDPDV, id_pdv);//SharedPreference
                values.put(ContractInsertInicial.Columnas.CODIGO, codigo);//SharedPreference
                values.put(ContractInsertInicial.Columnas.TIPO, tipo);//Spinner
                values.put(ContractInsertInicial.Columnas.DEALER, cliente);//List
                values.put(ContractInsertInicial.Columnas.UBICACION, ubicacion);//Spinner
                values.put(ContractInsertInicial.Columnas.CORREO, correo);//Spinner
                values.put(ContractInsertInicial.Columnas.LATITUD, latitude);//Spinner
                values.put(ContractInsertInicial.Columnas.LONGITUD, longitude);
                values.put(ContractInsertInicial.Columnas.FECHA, fechaser);//Extra
                values.put(ContractInsertInicial.Columnas.HORA, horaser);//Extra
                values.put(Constantes.PENDIENTE_INSERCION, 1);

                context.getContentResolver().insert(ContractInsertInicial.CONTENT_URI, values);
*/
//                if (VerificarNet.hayConexion(context)) {
//                    SyncAdapter.sincronizarAhora(context, true, Constantes.insertinicial, null);
//                    Toast.makeText(context, Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
//                }
            } else {
                Toast.makeText(context, "No se esta obteniendo el id", Toast.LENGTH_SHORT).show();
            }
        }

        public void LoadData() {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            usuarioCursor = sharedPreferences.getString(Constantes.USER,Constantes.NODATA);
            //bateria = sharedPreferences.getString(Constantes.BATERIA, Constantes.NO_DATA);
        }
    }

    public class FooterHolder extends BaseViewHolder {

        ProgressBar mProgressBar;

        FooterHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
//            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

    }

}
