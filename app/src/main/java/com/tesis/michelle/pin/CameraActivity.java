package com.tesis.michelle.pin;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.tesis.michelle.pin.Adaptadores.PostRecyclerAdapter;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.ui.canjes.CanjesFragment;
import com.tesis.michelle.pin.ui.evidencias.EvidenciasFragment;
import com.tesis.michelle.pin.ui.exhibiciones.ExhibicionFragment;
import com.tesis.michelle.pin.ui.logros.FotograficoFragment;
import com.tesis.michelle.pin.ui.novedades.NovedadesFragment;
import com.tesis.michelle.pin.ui.packs.PacksFragment;
import com.tesis.michelle.pin.ui.precios.PreciosFragment;
import com.tesis.michelle.pin.ui.probadores.ProbadoresFragment;
import com.tesis.michelle.pin.ui.promociones.PromoFragment;
import com.tesis.michelle.pin.ui.ventas.VentasFragment;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity{

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    int cameraSelectors = CameraSelector.LENS_FACING_BACK;

    PreviewView previewView;
    private ImageCapture imageCapture;
    private VideoCapture videoCapture;
    private Button bCapture;
    OrientationEventListener oel;
    FloatingActionButton fab;
    FloatingActionButton fab2;

    private String activity = null;
    private String tipo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        activity = getIntent().getStringExtra("activity");
        tipo = getIntent().getStringExtra("tipo");

        previewView = findViewById(R.id.previewView);
        //bCapture = findViewById(R.id.bCapture);

        fab = findViewById(R.id.fab_camera);
        fab2 = findViewById(R.id.fab_switch_camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraSelectors == CameraSelector.LENS_FACING_BACK) {
                    cameraSelectors = CameraSelector.LENS_FACING_FRONT;
                } else {
                    cameraSelectors = CameraSelector.LENS_FACING_BACK;
                }
                openCamera();
            }
        });
        //bCapture.setOnClickListener(this);
        openCamera();
    }

    private void openCamera(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());
    }

    Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                //.requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .requireLensFacing(cameraSelectors)
                .build();
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();
        oel = new OrientationEventListener(getApplicationContext(),SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int i) {
                if (i >= 225 && i < 315) {
                    imageCapture.setTargetRotation(Surface.ROTATION_90);
                } else if(i >= 135 && i < 225) {
                    imageCapture.setTargetRotation(Surface.ROTATION_180);
                } else if (i >= 45 && i < 135) {
                    imageCapture.setTargetRotation(Surface.ROTATION_270);
                } else {
                    imageCapture.setTargetRotation(Surface.ROTATION_0);
                }
            }
        };
        oel.enable();

        // Video capture use case
        videoCapture = new VideoCapture.Builder()
                .setVideoFrameRate(30)
                .build();

        // Image analysis use case
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        //imageAnalysis.setAnalyzer(getExecutor(), this);

        //bind to lifecycle:
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture, videoCapture);
    }

    private void capturePhoto() {
        long timestamp = System.currentTimeMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        //imageCapture.setTargetRotation(Surface.ROTATION_90);
        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        finish();
                        if (activity.equals("menu")) {
                            MenuNavigationActivity.imageView.setImageURI(outputFileResults.getSavedUri());
                        }
                        if (activity.equals("nuevo_pdv")) {
                            ImplementacionActivity.ivFoto.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("promo")) {
                            PromoFragment.imageView.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("precios")) {
                            PreciosFragment.img.setImageURI(outputFileResults.getSavedUri());
                            //PromoFragment.imageView.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("packs")) {
                            if(tipo.equalsIgnoreCase("packs")) {
                                PacksFragment.imageView.setImageURI(outputFileResults.getSavedUri());
                            }else if(tipo.equalsIgnoreCase("guia")){
                                PacksFragment.imageViewGuia.setImageURI(outputFileResults.getSavedUri());
                            }
                        }

                        if (activity.equals("exh")) {
                            ExhibicionFragment.imageView.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("justificar")) {
                            PostRecyclerAdapter.imageView.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("canjes")) {
                            CanjesFragment.imageView.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("logros")) {
                            FotograficoFragment.imageView.setImageURI(outputFileResults.getSavedUri());
                        }
                        if (activity.equals("encuesta")) {
                            EvaluacionEncuestaActivity.imageView.setImageURI(outputFileResults.getSavedUri());
                        }
                        if (activity.equals("ventas_factura")) {
                            VentasFragment.imageViewFactura.setImageURI(outputFileResults.getSavedUri());
                        }
                        if (activity.equals("ventas_adicional")) {
                            VentasFragment.imageViewAdicional.setImageURI(outputFileResults.getSavedUri());
                        }
                        if (activity.equals("ventas_actividad")) {
                            VentasFragment.imageViewActividad.setImageURI(outputFileResults.getSavedUri());
                        }

//                        if (activity.equals("ventas_adicional")) {
//                            VentasFragment.imageViewAdicional.setImageURI(outputFileResults.getSavedUri());
//                        }
//                        if (activity.equals("ventas_actividad")) {
//                            VentasFragment.imageViewActividad.setImageURI(outputFileResults.getSavedUri());
//                        }
//
                        if (activity.equals("novedades_foto_lote")) {
                            NovedadesFragment.ivFotoLote.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("probadores")) {
                            ProbadoresFragment.ivFotoLote.setImageURI(outputFileResults.getSavedUri());
                        }
                        if (activity.equals("novedades_factura")) {
                            NovedadesFragment.ivFotoFactura.setImageURI(outputFileResults.getSavedUri());
                        }
                        if (activity.equals("novedades_foto_sku")) {
                            NovedadesFragment.ivFotoSku.setImageURI(outputFileResults.getSavedUri());
                        }

                        if (activity.equals("evidencias")) {

                            if(tipo.equalsIgnoreCase("antes")) {
                                EvidenciasFragment.ivAntes.setImageURI(outputFileResults.getSavedUri());
                            }else if(tipo.equalsIgnoreCase("despues")){
                                EvidenciasFragment.ivDespues.setImageURI(outputFileResults.getSavedUri());
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}