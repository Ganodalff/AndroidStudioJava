package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Telaprincipal extends AppCompatActivity {

    Button btcadEndereco,btexbEndereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaprincipal);


        btcadEndereco = findViewById(R.id.btcadEndereco);
        btexbEndereco = findViewById(R.id.btexbEndereco);

        btcadEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Telaprincipal.this,CadastrarCEP.class);
                startActivity(intent);
                finish();
            }
        });

        btexbEndereco = findViewById(R.id.btexbEndereco);

        btexbEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Telaprincipal.this,ListarCadastrosCEP.class);
                startActivity(intent);
                finish();
            }
        });
    }
}