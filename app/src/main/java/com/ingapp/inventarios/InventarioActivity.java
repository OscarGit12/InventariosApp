package com.ingapp.inventarios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.IntentIntegrator;
import com.google.zxing.integration.IntentResult;

public class InventarioActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtcodigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escanear);

        txtcodigo = (EditText)findViewById(R.id.txtcodigoplu);

    }

    public void onClick(View v){
        if(v.getId()==R.id.btnscaner){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            scanIntegrator.setPrompt("Escane Scan");
            scanIntegrator.setCameraId(0);
            scanIntegrator.setBeepEnabled(false);
            scanIntegrator.setOrientationLocked(false);
            scanIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            txtcodigo.setText(scanContent);
           // contentTxt.setText("CONTENT: " + scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
