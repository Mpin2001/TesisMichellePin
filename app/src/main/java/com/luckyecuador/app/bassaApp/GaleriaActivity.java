package com.luckyecuador.app.bassaApp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.luckyecuador.app.bassaApp.Adaptadores.PostRecyclerAdapter;

import java.io.IOException;

public class GaleriaActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    int COD_SELECCIONA=10;
    int COD_FOTO=20;
    Bitmap bitmap;
    Boolean value = false;

    private final String CARPETA_RAIZ="DanecApp/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Justificacion";
    String path;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openGallery();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
       // gallerylauncheractivity.launch(intent);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), COD_SELECCIONA);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                scaleImage(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Aquí puedes manejar la imagen seleccionada desde la galería
      //      PostRecyclerAdapter.imageView.setImageURI(imageUri);
             //finish();
        }
        finish();
    }


    public void scaleImage(Bitmap bitmap){
        try{
            int mheight = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, mheight, true);
            PostRecyclerAdapter.imageView.setImageBitmap(scaled);
            PostRecyclerAdapter.bitmapfinal = ((BitmapDrawable)PostRecyclerAdapter.imageView.getDrawable()).getBitmap();

        } catch (Exception e) {
            AlertDialog alertDialog1;
            alertDialog1 = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog1.setTitle("Message");
            alertDialog1.setMessage("Notificar \t "+e.toString());
            alertDialog1.show();
            Log.e("compressBitmap", "Error on compress file");
        }
    }

}
