package com.tesis.michelle.pin.ui.evidencias;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.tesis.michelle.pin.CameraActivity;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Utils.DeveloperOptions;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VerificarNet;
import com.tesis.michelle.pin.Contracts.ContractInsertEvidencias;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Sync.SyncAdapter;
import com.tesis.michelle.pin.Utils.ImageMark;
import com.tesis.michelle.pin.Utils.RequestPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EvidenciasFragment extends Fragment implements View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {

    //BASE SQLITE
    DatabaseHelper handler;

    private boolean antes = false;
    private boolean despues = false;
    private final String WHATSAPP_NUMBER = "+593969415195";
    //Photo Camera
    public static ImageView ivAntes = null;
    public static ImageView ivDespues = null;
    private Bitmap bitmap;
    private Bitmap bitmapfinalAntes;
    private Bitmap bitmapfinalDespues;
    //Photo Camera
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private Spinner spCategoria;
    private Spinner spSubCategoria;

    // Nuevos elementos agregados
    private CheckBox cbAnonimo;
    private EditText etNombres;
    private EditText etCedula;
    private EditText etCelular;
    private LinearLayout layoutInfoPersonal;

    private final String CARPETA_RAIZ="bassaApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Evidencias";
    private final String fabricante = "Bassa";
    String path;

    //private EditText txtCodigo;
    private EditText txtComentario;
    private ImageButton btnCameraAntes;
    private ImageButton btnCameraDespues;
    private Button btnSave;

    private String fecha, hora,hora_antes,hora_despues ,user, marca,  id_pdv,categoria,subCategoria;

    String codigo, punto_venta,canal,subcanal,format ,comentario;
    private boolean vaFoto;

    // Variables para los nuevos campos
    private String nombres = "";
    private String cedula = "";
    private String celular = "";
    private boolean esAnonimo = false;

    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_evidencias, container, false);

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

        LoadData();

        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();

        spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);
        spSubCategoria = (Spinner) rootView.findViewById(R.id.spSubCategoria);
        ivAntes = (ImageView) rootView.findViewById(R.id.imageViewAntes);
        ivDespues = (ImageView) rootView.findViewById(R.id.imageViewDespues);
        btnCameraAntes = (ImageButton) rootView.findViewById(R.id.btnCameraLocalAntes);
        btnCameraDespues = (ImageButton) rootView.findViewById(R.id.btnCameraLocalDespues);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);

        // Inicializar nuevos elementos
        cbAnonimo = (CheckBox) rootView.findViewById(R.id.cbAnonimo);
        etNombres = (EditText) rootView.findViewById(R.id.etNombres);
        etCedula = (EditText) rootView.findViewById(R.id.etCedula);
        etCelular = (EditText) rootView.findViewById(R.id.etCelular);
        layoutInfoPersonal = (LinearLayout) rootView.findViewById(R.id.layoutInfoPersonal);

        txtComentario = (EditText) rootView.findViewById(R.id.txtComentario);

        txtComentario.setSingleLine(false);
        txtComentario.setLines(4);
        txtComentario.setMaxLines(5);
        txtComentario.setGravity(Gravity.LEFT | Gravity.TOP);
        txtComentario.setHorizontalScrollBarEnabled(false);

        btnCameraAntes.setOnClickListener(this);
        btnCameraDespues.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        // Configurar listener para el CheckBox
        cbAnonimo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                esAnonimo = isChecked;
                if (isChecked) {
                    // Ocultar campos de información personal
                    layoutInfoPersonal.setVisibility(View.GONE);
                    // Limpiar campos cuando es anónimo
                    etNombres.setText("");
                    etCedula.setText("");
                    etCelular.setText("");
                } else {
                    // Mostrar campos de información personal
                    layoutInfoPersonal.setVisibility(View.VISIBLE);
                }
            }
        });

        // Agregar TextWatchers para capturar los datos en tiempo real
        etNombres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                nombres = s.toString().trim();
            }
        });

        etCedula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                cedula = s.toString().trim();
            }
        });

        etCelular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                celular = s.toString().trim();
            }
        });

        filtrarCategoria();

        return rootView;
    }

    public void filtrarCategoria() {
        List<String> operadores = handler.getCategoriaEvidencia();
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
    }


    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA,ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION,INTERNET},100);
            }
        });
        dialogo.show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnCameraAntes) {
            this.antes = true;
            this.despues = false;
            String tipo = "antes";
            cargarImagen(tipo);
        }
        if (view == btnCameraDespues) {
            this.despues = true;
            this.antes = false;
            String tipo = "despues";
            cargarImagen(tipo);
        }
        if (view == btnSave) {
            if (esFormularioValido()) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Desea guardar la información y enviar notificación por WhatsApp?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 1. Obtener valores de los campos personales
                        String nombresTemp = etNombres.getText().toString().trim();
                        String cedulaTemp = etCedula.getText().toString().trim();
                        String celularTemp = etCelular.getText().toString().trim();

                        // 2. Verificar si están completos
                        boolean datosCompletos = !nombresTemp.isEmpty() &&
                                !cedulaTemp.isEmpty() &&
                                !celularTemp.isEmpty();

                        // 3. SI están completos → Enviar WhatsApp PRIMERO
                        if (datosCompletos) {
                            Log.d("WhatsAppDebug", "Datos completos - Enviando WhatsApp");
                            enviarWhatsAppConDatos(nombresTemp, cedulaTemp, celularTemp);
                        }

                        // 4. LUEGO guardar en BD (siempre)
                        enviarDatos();

                        // 5. Mensaje al usuario
                        if (!datosCompletos) {
                            Toast.makeText(getContext(),
                                    "Guardado sin WhatsApp (datos personales incompletos)",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });                builder.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Solo guardar sin enviar WhatsApp
                        enviarDatos();
                        Toast.makeText(getContext(),
                                "Información guardada sin enviar WhatsApp.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                android.app.AlertDialog ad = builder.create();
                ad.show();
            }
        }    }

    // Agrega este método para enviar el mensaje por WhatsApp
// Agrega este método para enviar el mensaje por WhatsApp
    private void enviarWhatsAppConDatos(String nombres, String cedula, String celular) {
        try {
            Log.d("WhatsAppDebug", "=== ENVIANDO WHATSAPP ===");

            // 1. Obtener OBSERVACIÓN del EditText
            String observacion = "";
            if (txtComentario != null) {
                observacion = txtComentario.getText().toString().trim();
            }

            // 2. Obtener TIPO DE DENUNCIA del Spinner
            String tipoDenuncia = "No especificada";
            if (spCategoria != null && spCategoria.getSelectedItem() != null) {
                tipoDenuncia = spCategoria.getSelectedItem().toString();
                if (tipoDenuncia.equalsIgnoreCase("Seleccione")) {
                    tipoDenuncia = "No especificada";
                }
            }

            // 3. Asegurar que LoadData() esté actualizado
            LoadData();

            Log.d("WhatsAppDebug", "Datos para WhatsApp:");
            Log.d("WhatsAppDebug", "- Nombres: " + nombres);
            Log.d("WhatsAppDebug", "- Cédula: " + cedula);
            Log.d("WhatsAppDebug", "- Celular: " + celular);
            Log.d("WhatsAppDebug", "- Observación: " + observacion);
            Log.d("WhatsAppDebug", "- Tipo: " + tipoDenuncia);
            Log.d("WhatsAppDebug", "- Local: " + punto_venta);

            // 4. Construir mensaje
            String mensaje = "📋 *NUEVA DENUNCIA REGISTRADA* \n\n" +
                    "👤 *INFORMACIÓN DEL DENUNCIANTE:*\n" +
                    "• *Nombres:* " + nombres + "\n" +
                    "• *Cédula:* " + cedula + "\n" +
                    "• *Celular:* " + celular + "\n\n" +
                    "⚠️ *INFORMACIÓN DE LA DENUNCIA:*\n" +
                    "• *Tipo de denuncia:* " + tipoDenuncia + "\n" +
                    "• *Observación:* " + (observacion.isEmpty() ? "Sin observación" : observacion) + "\n\n" +
                    "📍 *INFORMACIÓN ADICIONAL:*\n" +
                    "• *Lugar:* " + punto_venta + "\n" +
                    "• *Código:* " + codigo + "\n" +
                    "• *Usuario registrador:* " + user + "\n" +
                    "• *Fecha:* " + fecha + "\n\n" +
                    "🚨 *Este mensaje fue enviado automáticamente desde Barrio App*";

            // 5. Enviar por WhatsApp
            String mensajeCodificado = URLEncoder.encode(mensaje, "UTF-8");
            String url = "https://api.whatsapp.com/send?phone=" + WHATSAPP_NUMBER + "&text=" + mensajeCodificado;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "WhatsApp no instalado", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("WhatsAppDebug", "Error: " + e.getMessage(), e);
            Toast.makeText(getContext(), "Error al enviar WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }


    public void enviarDatos() {
        // Obtener los valores de los nuevos campos
        obtenerValoresCamposPersonales();
        // Verificar si los campos personales están completos
        boolean camposPersonalesCompletos = !nombres.isEmpty() &&
                !cedula.isEmpty() &&
                !celular.isEmpty();


        // Marca de Agua
        String ciudad = "Ciudad: " + handler.getCityPdv(codigo);
        String local = "Local: " + punto_venta;
        String usuario = "Usuario: " + user;
        String fechaHoraAntes = "Fecha y hora: " + fecha + " " + hora_antes;
        String fechaHoraDespues = "Fecha y hora: " + fecha + " " + hora_despues;

        // Agregar información de anonimato si aplica
        String infoAnonimo = esAnonimo ? "ANÓNIMO: SI" : "ANÓNIMO: NO";
        if (!esAnonimo && !nombres.isEmpty()) {
            infoAnonimo += "\nNOMBRES: " + nombres;
        }

        ImageMark im = new ImageMark();

        Bitmap temporalAntes = ((BitmapDrawable) ivAntes.getDrawable()).getBitmap();
        Bitmap watermarkAntes = im.mark(temporalAntes, ciudad, local, usuario, fechaHoraAntes, Color.YELLOW, 100, 85, false);
        int mheightAntes = (int) (watermarkAntes.getHeight() * (1024.0 / watermarkAntes.getWidth()) );
        Bitmap scaledAntes = Bitmap.createScaledBitmap(watermarkAntes, 1024, mheightAntes, true);

        Bitmap temporalDespues = ((BitmapDrawable) ivDespues.getDrawable()).getBitmap();
        Bitmap watermarkDespues = im.mark(temporalDespues, ciudad, local, usuario, fechaHoraDespues, Color.YELLOW, 100, 85, false);
        int mheightDespues = (int) (watermarkDespues.getHeight() * (1024.0 / watermarkDespues.getWidth()) );
        Bitmap scaledDespues = Bitmap.createScaledBitmap(watermarkDespues, 1024, mheightDespues, true);

        String foto_antes = getStringImage(scaledAntes);
        String foto_despues = getStringImage(scaledDespues);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fechaser = date.format(currentLocalTime);

        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String horaser = hour.format(currentLocalTime);
        obtenervaloresseleccinados();

        ContentValues values = new ContentValues();
        values.put(ContractInsertEvidencias.Columnas.PHARMA_ID, id_pdv);
        values.put(ContractInsertEvidencias.Columnas.CODIGO, codigo);
        values.put(ContractInsertEvidencias.Columnas.POS_NAME, punto_venta);
        values.put(ContractInsertEvidencias.Columnas.USUARIO, user);
        values.put(ContractInsertEvidencias.Columnas.MARCA, marca);
        values.put(ContractInsertEvidencias.Columnas.CATEGORIA, categoria);
        values.put(ContractInsertEvidencias.Columnas.COMENTARIO, comentario);

        // Agregar los nuevos campos a la base de datos usando las constantes del Contract
        // Si es anónimo, guardar "SI", si no, guardar "N/A"
        String valorAnonimo = esAnonimo ? "SI" : "N/A";
        values.put(ContractInsertEvidencias.Columnas.ES_ANONIMO, valorAnonimo);

        if (!esAnonimo) {
            // Si NO es anónimo, guardar los valores reales
            values.put(ContractInsertEvidencias.Columnas.NOMBRES, nombres);
            values.put(ContractInsertEvidencias.Columnas.CEDULA, cedula);
            values.put(ContractInsertEvidencias.Columnas.CELULAR, celular);
        } else {
            // Si ES anónimo, guardar "N/A" en los campos
            values.put(ContractInsertEvidencias.Columnas.NOMBRES, "N/A");
            values.put(ContractInsertEvidencias.Columnas.CEDULA, "N/A");
            values.put(ContractInsertEvidencias.Columnas.CELULAR, "N/A");
        }

        values.put(ContractInsertEvidencias.Columnas.FOTO_ANTES, foto_antes);
        values.put(ContractInsertEvidencias.Columnas.FOTO_DESPUES, foto_despues);
        values.put(ContractInsertEvidencias.Columnas.FECHA, fechaser);
        values.put(ContractInsertEvidencias.Columnas.HORA, horaser);
        values.put(Constantes.PENDIENTE_INSERCION, 1);

        getContext().getContentResolver().insert(ContractInsertEvidencias.CONTENT_URI, values);

        if (VerificarNet.hayConexion(getContext())) {
            SyncAdapter.sincronizarAhora(getContext(), true, Constantes.insertEvidencias, null);
            Toast.makeText(getContext(), Mensajes.ON_SYNC_SERVER, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), Mensajes.ON_SYNC_DEVICE, Toast.LENGTH_SHORT).show();
        }
        vaciarCampos();
    }
    private boolean esFormularioValido() {
        if (spCategoria.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
            Toast.makeText(getContext(), "Debe seleccionar una denuncia", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ivAntes.getDrawable() == null) {
            Toast.makeText(getContext(), "Por favor tomar la primera foto", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ivDespues.getDrawable() == null) {
            Toast.makeText(getContext(), "Por favor tomar la segunda evidencia", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtComentario.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Debe ingresar una observación",Toast.LENGTH_LONG).show();
            return false;
        }

        // Validar campos personales si NO es anónimo
        if (!esAnonimo) {
            if (nombres.isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar los nombres", Toast.LENGTH_SHORT).show();
                etNombres.requestFocus();
                return false;
            }

            if (cedula.isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar la cédula", Toast.LENGTH_SHORT).show();
                etCedula.requestFocus();
                return false;
            }

            if (celular.isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar el celular", Toast.LENGTH_SHORT).show();
                etCelular.requestFocus();
                return false;
            }
        }

        return true;
    }

    public void vaciarCampos(){
        spCategoria.setSelection(0);
        txtComentario.setText("");
        ivAntes.setImageResource(0);
        ivDespues.setImageResource(0);

        // Limpiar los nuevos campos
        cbAnonimo.setChecked(false);
        etNombres.setText("");
        etCedula.setText("");
        etCelular.setText("");
        layoutInfoPersonal.setVisibility(View.VISIBLE);
    }

    public void obtenervaloresseleccinados(){
        comentario = txtComentario.getText().toString();
    }

    // Método para obtener los valores de los campos personales
    public void obtenerValoresCamposPersonales() {
        // Obtener valores directamente de los EditText
        nombres = etNombres.getText().toString().trim();
        cedula = etCedula.getText().toString().trim();
        celular = etCelular.getText().toString().trim();

        // El CheckBox ya no afecta la decisión de enviar WhatsApp
        esAnonimo = cbAnonimo.isChecked();

        Log.d("WhatsAppDebug", "Obteniendo valores:");
        Log.d("WhatsAppDebug", "- Nombres: '" + nombres + "'");
        Log.d("WhatsAppDebug", "- Cédula: '" + cedula + "'");
        Log.d("WhatsAppDebug", "- Celular: '" + celular + "'");
        Log.d("WhatsAppDebug", "- Anónimo: " + esAnonimo);
    }

    //************METODOS PARA TAKE-PHOTO Y UPLOAD
    private void cargarImagen(String tipo) {
        final CharSequence[] opciones;
        if (vaFoto) {
            opciones = new CharSequence[]{"Tomar Foto", "Cancelar"};
        } else {
            opciones = new CharSequence[]{"Tomar Foto", "Cargar Imagen", "Cancelar"};
        }
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
                    Date currentLocalTime = cal.getTime();
                    DateFormat hour = new SimpleDateFormat("HH:mm:ss");

                    if(tipo.equalsIgnoreCase("antes")) {
                        hora_antes = hour.format(currentLocalTime);
                    }else if(tipo.equalsIgnoreCase("despues")){
                        hora_despues = hour.format(currentLocalTime);
                    }

                    Intent n = new Intent(getContext(), CameraActivity.class);
                    n.putExtra("activity", "evidencias");
                    n.putExtra("tipo",""+tipo);
                    startActivity(n);

                } else {
                    if (opciones[i].equals("Cargar Imagen")) {
                        openGallery();
                    } else {
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

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            String authorities=getContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
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
    public void scaleImage(Bitmap bitmap){
        try{
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
            Date currentLocalTime = cal.getTime();
            DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            String horaser = hour.format(currentLocalTime);

            int mheight = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, mheight, true);
            if (antes) {
                ivAntes.setImageBitmap(scaled);
                hora_antes = horaser;
                bitmapfinalAntes = ((BitmapDrawable) ivAntes.getDrawable()).getBitmap();
            } else if (despues) {
                ivDespues.setImageBitmap(scaled);
                hora_despues = horaser;
                bitmapfinalDespues = ((BitmapDrawable) ivDespues.getDrawable()).getBitmap();
            }
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
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public String getStringImage(Bitmap bmp){
        String encodedImage;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_pdv = sharedPreferences.getString(Constantes.IDPDV,Constantes.NODATA);
        user = sharedPreferences.getString(Constantes.USER,Constantes.NODATA);
        codigo = sharedPreferences.getString(Constantes.CODIGO, Constantes.NODATA);
        punto_venta = sharedPreferences.getString(Constantes.PDV, Constantes.NODATA);
        fecha = sharedPreferences.getString(Constantes.FECHA, Constantes.NODATA);
        hora = sharedPreferences.getString(Constantes.HORA, Constantes.NODATA);
        format = sharedPreferences.getString(Constantes.FORMAT, Constantes.NODATA);
        canal = sharedPreferences.getString(Constantes.TIPO, Constantes.NODATA);
        subcanal = sharedPreferences.getString(Constantes.SUBCANAL, Constantes.NODATA);
        vaFoto = sharedPreferences.getBoolean(Constantes.VAFOTO,false);
        Log.i("USER FOTO ACTIVITY:",user);
    }

    public void obtenerFecha(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        fecha = date.format(currentLocalTime);

        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        hora = hour.format(currentLocalTime);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        if (adapterView== spCategoria) {
            try{
                marca = adapterView.getItemAtPosition(i).toString();
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}