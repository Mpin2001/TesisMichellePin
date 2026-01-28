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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_evidencias, container, false);

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME,null,1);

        LoadData();

        new DeveloperOptions().modalDevOptions(getActivity());
        RequestPermissions requestPermissions = new RequestPermissions(getContext(), getActivity());
        requestPermissions.showPermissionDialog();

        //startService(new Intent(getContext(), LocationService.class));

        spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);// cambios mpin
        spSubCategoria = (Spinner) rootView.findViewById(R.id.spSubCategoria);  // cambios mpin
        ivAntes = (ImageView) rootView.findViewById(R.id.imageViewAntes);
        ivDespues = (ImageView) rootView.findViewById(R.id.imageViewDespues);
        btnCameraAntes = (ImageButton) rootView.findViewById(R.id.btnCameraLocalAntes);
        btnCameraDespues = (ImageButton) rootView.findViewById(R.id.btnCameraLocalDespues);
        btnSave= (Button) rootView.findViewById(R.id.btnSave);

        //txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        txtComentario = (EditText) rootView.findViewById(R.id.txtComentario);

        txtComentario.setSingleLine(false);  //add this
        txtComentario.setLines(4);
        txtComentario.setMaxLines(5);
        txtComentario.setGravity(Gravity.LEFT | Gravity.TOP);
        txtComentario.setHorizontalScrollBarEnabled(false); //this

        btnCameraAntes.setOnClickListener(this);
        btnCameraDespues.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        filtrarCategoria();

        return rootView;

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
        dialogo.show();}

    @Override
    public void onClick(View view) {
        /*if (view == btnGallery) {
            showFileChooser();
        }*/
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
                builder.setMessage("¿Desea guardar la información?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarDatos();
                    }
                });

                builder.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                android.app.AlertDialog ad = builder.create();
                ad.show();
            }
        }
    }

    public void filtrarCategoria() {
        List<String> operadores = handler.getCategoriaEvidencia(fabricante);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);
    }

//    public void filtrarSubCategoria(String fabricante,String categoria) {
//        List<String> operadores = handler.getSubCategoriaEvidencia(fabricante,categoria);
//        if (operadores.size()==2) {
//            operadores.remove(0);
//        }
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operadores);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spSubCategoria.setAdapter(dataAdapter);
//        spSubCategoria.setOnItemSelectedListener(this);
//    }

    /**/
    public void enviarDatos() {

      //  obtenerFecha();

        // Marca de Agua
        String ciudad = "Ciudad: " + handler.getCityPdv(codigo);
        String local = "Local: " + punto_venta;
        String usuario = "Usuario: " + user;
        String fechaHoraAntes = "Fecha y hora: " + fecha + " " + hora_antes;
        String fechaHoraDespues = "Fecha y hora: " + fecha + " " + hora_despues;

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
        values.put(ContractInsertEvidencias.Columnas.CATEGORIA,categoria);
       // values.put(ContractInsertEvidencias.Columnas.SUBCATEGORIA,subCategoria);
        values.put(ContractInsertEvidencias.Columnas.COMENTARIO, comentario);
        values.put(ContractInsertEvidencias.Columnas.FOTO_ANTES, foto_antes);
        values.put(ContractInsertEvidencias.Columnas.FOTO_DESPUES, foto_despues);
        values.put(ContractInsertEvidencias.Columnas.FECHA, fechaser);//Extra
        values.put(ContractInsertEvidencias.Columnas.HORA, horaser);//Extra
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
            Toast.makeText(getContext(), "Debe seleccionar una marca", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (spSubCategoria.getSelectedItem().toString().equalsIgnoreCase("Seleccione")) {
//            Toast.makeText(getContext(), "Debe seleccionar una subcategoria", Toast.LENGTH_SHORT).show();
//            return false;
//        }


        if (ivAntes.getDrawable() == null) {
            Toast.makeText(getContext(), "Por favor tomar una foto de antes", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ivDespues.getDrawable() == null) {
            Toast.makeText(getContext(), "Por favor tomar una foto de después", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtComentario.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Debe ingresar una observación",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**/
    public void vaciarCampos(){
        //txtCodigo.setText("");
        spCategoria.setSelection(0);
      //  spSubCategoria.setSelection(0);
        txtComentario.setText("");
        ivAntes.setImageResource(0);
        ivDespues.setImageResource(0);
    }

    public void obtenervaloresseleccinados(){
        comentario = txtComentario.getText().toString();
    }

    //************METODOS PARA TAKE-PHOTO Y UPLOAD
    private void cargarImagen(String tipo) {
//        final CharSequence[] opciones={"Tomar Foto","Cancelar"};
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
                if (opciones[i].equals("Tomar Foto")){
                 //   tomarFotografia();
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
//                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        intent.setType("image/");
//                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);
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
//                    Uri miPath=data.getData();
//                    imageViewAntes.setImageURI(miPath);
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
        //Comprime la Imagen tipo, calidad y outputstream
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
//            return localTime;
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
               // filtrarSubCategoria(fabricante,categoria);
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

//        if (adapterView== spSubCategoria) {
//            try{
//                subCategoria = adapterView.getItemAtPosition(i).toString();
//            }catch (Exception e) {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}