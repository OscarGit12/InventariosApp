package com.ingapp.inventarios;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.IntentIntegrator;
import com.google.zxing.integration.IntentResult;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showSystemUI();
        Permiso();

    }

    public void Permiso()
    {

        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int accessFinePermission = checkSelfPermission(Manifest.permission.CAMERA);
        int accessCoarsePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (accessFinePermission == PackageManager.PERMISSION_GRANTED && accessCoarsePermission == PackageManager.PERMISSION_GRANTED) {
            //se realiza metodo si es necesario...
        } else {
            requestPermissions(perms, 100);
        }


    }

    public void onClick(View v){
        if(v.getId()==R.id.btnscan){
            Log.wtf("Menu","Btn scan");
            Intent downloadIntent = new Intent(this, InventarioActivity.class);
            startActivity(downloadIntent);
            /*
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
            sendIntent.setType("text/plain");
            */
        }else if(v.getId()==R.id.btnExportar)
        {
           Envio();


        }
    }

    /**
     * pantalla completa
     */
    private void showSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    void Envio()
    {
        Log.w("Menu","Btn Exportar");
        try {

            String nombre = "excel.csv";
            String ruta = getExternalFilesDir("").getParent()+"/files" ;
            File fil=new File(ruta);
            fil.mkdirs();
            fil = new File(ruta + "/" + nombre);
            Log.w("Menu",fil.getPath());
            if(!fil.exists())
            {
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(ruta + "/" + nombre);
                    fos.write("nombre;dato".getBytes());
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Inventario");
            intent.putExtra(Intent.EXTRA_TEXT, nombre);
            // intent.setType("text/csv");
            intent.setType("*/*");
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", fil);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            startActivity(Intent.createChooser(intent,"Elije el medio de envio"));
        }catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
            Log.e("MENU",e.getMessage());
        }
    }

/*
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        textTo = (EditText) findViewById(R.id.editTextTo);
        textSubject = (EditText) findViewById(R.id.editTextSubject);
        textMessage = (EditText) findViewById(R.id.editTextMessage);
        buttonSend.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String to = textTo.getText().toString();
            }
            String subject = textSubject.getText().toString();
            String message = textMessage.getText().toString();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("plain/text");
            File data = null;
            try
            {
                Date dateVal = new Date();
                String filename = dateVal.toString();
                data = File.createTempFile("Report", ".csv");
                FileWriter out = (FileWriter) GenerateCsv.generateCsvFile( data, "Name,Data1");
                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(data));
                i.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(i, "E-mail"));
            } catch (IOException e) { e.printStackTrace(); } } }); }
            public class GenerateCsv
            {
                public static FileWriter generateCsvFile(File sFileName,String fileContent)
                {
                    FileWriter writer = null;
                    try
                    {
                        writer = new FileWriter(sFileName);
                        writer.append(fileContent);
                        writer.flush();
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block e.printStackTrace();//
                    }finally
                    {
                        try {
                            writer.close();
                        }
                    } catch (IOException e)
                    { // TODO Auto-generated catch block e.printStackTrace(); } } return writer; } }
    }
*/
}