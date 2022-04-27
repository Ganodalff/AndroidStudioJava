package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.CEP;
import com.example.myapplication.Service.services;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CadastrarCEP extends AppCompatActivity {

    EditText txtCEP,txtEstado,txtCidade,txtComplemento,txtBairro,txtNumero,txtRua;
    Button btAdicionar,btCompletar;
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cep);
        txtCEP = findViewById(R.id.txtCEP);
        txtEstado = findViewById(R.id.txtEstado);
        txtCidade = findViewById(R.id.txtCidade);
        txtComplemento = findViewById(R.id.txtComplemento);
        txtBairro = findViewById(R.id.txtBairro);
        txtRua = findViewById(R.id.txtRua);
        txtNumero = findViewById(R.id.txtNumero);
        btCompletar = findViewById(R.id.btPreencherCep);
        btAdicionar = findViewById(R.id.btcadCep);
        btCompletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtCEP.getText().toString().length() > 0 && !txtCEP.getText().toString().isEmpty() && txtCEP.getText().toString().length() == 8 ) {
                    services service = new services(txtCEP.getText().toString());
                    try {
                        CEP retorno = service.execute().get();
                        txtEstado.setText(retorno.getUf().toString());
                        txtCidade.setText(retorno.getLocalidade().toString());
                        txtRua.setText(retorno.getLogradouro().toString());
                        txtBairro.setText(retorno.getBairro().toString());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((txtCEP.getText().toString().isEmpty()) || (txtCidade.getText().toString().isEmpty()) || (txtEstado.getText().toString().isEmpty()) || (txtComplemento.getText().toString().isEmpty()) || (txtBairro.getText().toString().isEmpty()) || (txtRua.getText().toString().isEmpty()) || (txtNumero.getText().toString().isEmpty())){
                    Toast.makeText(CadastrarCEP.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    String cep = txtCEP.getText().toString();
                    String estado = txtEstado.getText().toString();
                    String cidade = txtCidade.getText().toString();
                    String logradouro = txtRua.getText().toString();
                    String bairro = txtBairro.getText().toString();
                    String complemento = txtComplemento.getText().toString();
                    String numero = txtNumero.getText().toString();



                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String,Object> usuarios = new HashMap<>();
                    usuarios.put("cep",cep);
                    usuarios.put("estado",estado);
                    usuarios.put("cidade",cidade);
                    usuarios.put("logradouro",logradouro);
                    usuarios.put("bairro",bairro);
                    usuarios.put("numero",numero);
                    usuarios.put("complemento",complemento);

                    usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("Enderecos").document(usuarioID);
                    documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CadastrarCEP.this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CadastrarCEP.this,Telaprincipal.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("db","Erro" + e.toString());
                        }
                    });
                }
            }
        });
    }
}