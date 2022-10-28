package com.example.prueba2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityPhoto extends AppCompatActivity {

    /*Declaracion de Varaibles */
    static final int peticion_captura_imagen = 100;
    static final int peticion_acceso_cam = 201;

    ImageView ObjetoImagen;
    Button btntakephoto;
    String PathImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ObjetoImagen = (ImageView)findViewById(R.id.imageView);
        btntakephoto = (Button) findViewById(R.id.btntakephoto);

        btntakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });
    }
    private void permisos() {
        // Validar si el permiso esta otorgado o no para tomar fotos
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            // Otorgar el permiso si no se tiene el mismo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, peticion_acceso_cam);
        }
        else
        {
            TakePhotoDir();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_cam)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                TakePhotoDir();
            }
        }
    }
    private void tomarfoto()
    {
        Intent intentfoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intentfoto.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intentfoto, peticion_captura_imagen);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if(requestCode == peticion_captura_imagen)
        {
            Bundle extras = data.getExtras();
            Bitmap imagen = (Bitmap) extras.get("data");
            ObjetoImagen.setImageBitmap(imagen);
        }*/
        if (requestCode == peticion_captura_imagen && resultCode == RESULT_OK){
            File foto = new File(PathImagen);
            ObjetoImagen.setImageURI(Uri.fromFile(foto));
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */);
        // Save a file: path for use with ACTION_VIEW intents
        PathImagen = image.getAbsolutePath();
        return image;
    }

    private void TakePhotoDir(){
        Intent Intenttakephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Intenttakephoto.resolveActivity(getPackageManager()) !=null){
            File foto = null;

            try {
                foto = createImageFile();
            }catch (Exception ex){
                ex.toString();
            }
            if (foto !=null){
                Uri fotoUri = FileProvider.getUriForFile(this, "com.example.prueba2.fileprovider", foto);
                Intenttakephoto.putExtra(MediaStore.EXTRA_OUTPUT, foto);
                startActivityForResult(Intenttakephoto, peticion_captura_imagen);
            }
        }
    }

}