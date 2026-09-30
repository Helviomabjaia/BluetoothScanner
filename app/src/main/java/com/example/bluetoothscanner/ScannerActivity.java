package com.example.bluetoothscanner;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ScannerActivity extends AppCompatActivity {

    private static final int REQUEST_BLUETOOTH = 100;

    private BluetoothAdapter bluetoothAdapter;

    private ArrayList<String> dispositivos;
    private ArrayAdapter<String> adapter;

    private ListView listDispositivos;
    private TextView tvStatus;
    private Button btnNovaProcura;

    private String nomeProcurado = "";

    private boolean receiverRegistrado = false;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = null;

                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                        device = intent.getParcelableExtra(
                                BluetoothDevice.EXTRA_DEVICE,
                                BluetoothDevice.class
                        );

                    } else {

                        device = intent.getParcelableExtra(
                                BluetoothDevice.EXTRA_DEVICE
                        );
                    }

                } catch (Exception e) {
                    return;
                }

                if (device == null) {
                    return;
                }

                try {

                    String nome = device.getName();
                    String endereco = device.getAddress();

                    if (nome == null || nome.trim().isEmpty()) {
                        nome = "Dispositivo sem nome";
                    }

                    if (endereco == null) {
                        endereco = "MAC desconhecido";
                    }

                    // Filtro pelo nome
                    if (!nomeProcurado.isEmpty()) {

                        if (!nome.toLowerCase().contains(
                                nomeProcurado.toLowerCase()
                        )) {
                            return;
                        }
                    }

                    String resultado =
                            nome + "\nMAC: " + endereco;

                    if (!dispositivos.contains(resultado)) {

                        dispositivos.add(resultado);

                        adapter.notifyDataSetChanged();

                        tvStatus.setText(
                                "Dispositivo encontrado"
                        );
                    }

                } catch (SecurityException e) {

                    Toast.makeText(
                            ScannerActivity.this,
                            "Erro de permissão Bluetooth",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            else if (
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)
            ) {

                if (dispositivos.isEmpty()) {

                    if (nomeProcurado.isEmpty()) {

                        tvStatus.setText(
                                "Pesquisa terminada. Nenhum dispositivo encontrado."
                        );

                    } else {

                        tvStatus.setText(
                                "Pesquisa terminada. Nenhum dispositivo com esse nome."
                        );
                    }

                } else {

                    tvStatus.setText(
                            "Pesquisa terminada. Encontrados: "
                                    + dispositivos.size()
                    );
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanner);

        listDispositivos =
                findViewById(R.id.listDispositivos);

        tvStatus =
                findViewById(R.id.tvStatus);

        btnNovaProcura =
                findViewById(R.id.btnNovaProcura);

        nomeProcurado =
                getIntent().getStringExtra("NOME_DISPOSITIVO");

        if (nomeProcurado == null) {
            nomeProcurado = "";
        }

        dispositivos = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dispositivos
        );

        listDispositivos.setAdapter(adapter);

        bluetoothAdapter =
                BluetoothAdapter.getDefaultAdapter();

        // Botão NOVA PROCURA
        btnNovaProcura.setOnClickListener(v -> {

            iniciarNovaPesquisa();

        });

        verificarPermissoes();
    }

    private void verificarPermissoes() {

        ArrayList<String> permissoes =
                new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED) {

                permissoes.add(
                        Manifest.permission.BLUETOOTH_SCAN
                );
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED) {

                permissoes.add(
                        Manifest.permission.BLUETOOTH_CONNECT
                );
            }

        } else {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {

                permissoes.add(
                        Manifest.permission.ACCESS_FINE_LOCATION
                );
            }
        }

        if (!permissoes.isEmpty()) {

            ActivityCompat.requestPermissions(
                    this,
                    permissoes.toArray(new String[0]),
                    REQUEST_BLUETOOTH
            );

        } else {

            iniciarPesquisa();
        }
    }

    private void iniciarNovaPesquisa() {

        try {

            if (bluetoothAdapter == null) {

                tvStatus.setText(
                        "Bluetooth não disponível"
                );

                return;
            }

            // Parar pesquisa anterior
            if (bluetoothAdapter.isDiscovering()) {

                bluetoothAdapter.cancelDiscovery();
            }

            // Limpar resultados anteriores
            dispositivos.clear();

            adapter.notifyDataSetChanged();

            tvStatus.setText(
                    "A procurar dispositivos..."
            );

            // Pequena pausa antes de iniciar novamente
            new android.os.Handler().postDelayed(
                    () -> iniciarPesquisa(),
                    500
            );

        } catch (SecurityException e) {

            tvStatus.setText(
                    "Erro de permissão Bluetooth"
            );
        }
    }

    private void iniciarPesquisa() {

        if (bluetoothAdapter == null) {

            tvStatus.setText(
                    "Bluetooth não disponível"
            );

            return;
        }

        try {

            if (!bluetoothAdapter.isEnabled()) {

                tvStatus.setText(
                        "Bluetooth desligado"
                );

                Toast.makeText(
                        this,
                        "Ligue o Bluetooth",
                        Toast.LENGTH_LONG
                ).show();

                Intent intent =
                        new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE
                        );

                startActivity(intent);

                return;
            }

            if (bluetoothAdapter.isDiscovering()) {

                bluetoothAdapter.cancelDiscovery();
            }

            dispositivos.clear();

            adapter.notifyDataSetChanged();

            tvStatus.setText(
                    "A procurar dispositivos..."
            );

            boolean iniciou =
                    bluetoothAdapter.startDiscovery();

            if (!iniciou) {

                tvStatus.setText(
                        "Não foi possível iniciar a pesquisa"
                );

                Toast.makeText(
                        this,
                        "O Android não iniciou a descoberta Bluetooth",
                        Toast.LENGTH_LONG
                ).show();
            }

        } catch (SecurityException e) {

            tvStatus.setText(
                    "Erro de permissão Bluetooth"
            );
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (!receiverRegistrado) {

            IntentFilter filter =
                    new IntentFilter();

            filter.addAction(
                    BluetoothDevice.ACTION_FOUND
            );

            filter.addAction(
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED
            );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                registerReceiver(
                        receiver,
                        filter,
                        Context.RECEIVER_EXPORTED
                );

            } else {

                registerReceiver(
                        receiver,
                        filter
                );
            }

            receiverRegistrado = true;
        }
    }

    @Override
    protected void onPause() {

        if (receiverRegistrado) {

            try {

                unregisterReceiver(receiver);

            } catch (Exception ignored) {
            }

            receiverRegistrado = false;
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        try {

            if (bluetoothAdapter != null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
                        ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.BLUETOOTH_SCAN
                        ) == PackageManager.PERMISSION_GRANTED) {

                    if (bluetoothAdapter.isDiscovering()) {

                        bluetoothAdapter.cancelDiscovery();
                    }
                }
            }

        } catch (Exception ignored) {
        }

        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == REQUEST_BLUETOOTH) {

            boolean permitido = true;

            for (int resultado : grantResults) {

                if (resultado != PackageManager.PERMISSION_GRANTED) {

                    permitido = false;
                    break;
                }
            }

            if (permitido) {

                iniciarPesquisa();

            } else {

                Toast.makeText(
                        this,
                        "Permissões Bluetooth negadas",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}