package com.example.bluetoothscanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        EditText etNomeDispositivo =
                findViewById(R.id.etNomeDispositivo);

        Button btnIniciarScanner =
                findViewById(R.id.btnIniciarScanner);

        btnIniciarScanner.setOnClickListener(v -> {

            String nomeDispositivo =
                    etNomeDispositivo.getText().toString().trim();

            Intent intent = new Intent(
                    MainActivity.this,
                    ScannerActivity.class
            );

            intent.putExtra(
                    "NOME_DISPOSITIVO",
                    nomeDispositivo
            );

            startActivity(intent);
        });
    }
}