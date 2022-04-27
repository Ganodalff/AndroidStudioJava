package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    Button btHome;

    private EditText txtName,txtPassword;
    private Button btCadastrar;
    String[] mensagens = {"Preencha todos os campos","Cadastro realizado com sucesso"};
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cadastro);
        btHome = findViewById(R.id.btHome);

        IniciarComponentes();
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = txtName.getText().toString();
                String senha = txtPassword.getText().toString();

                if (nome.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    CadastrarUsuario(view);
                }
            }
        });

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastro.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CadastrarUsuario(View view){
        String nome = txtName.getText().toString();
        String senha = txtPassword.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(nome,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(view,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    String erro;
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha com no mínimo 6 caracteres";
                    }catch(FirebaseAuthUserCollisionException e){
                        erro = "Essa conta já foi cadastrada";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        erro = "Insira um email válido";
                    }
                    catch(Exception e){
                        erro = "Erro ao cadastrar";
                    }
                    Snackbar snackbar = Snackbar.make(view,erro,Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }
    private void SalvarDadosUsuario(){
        String nome = txtName.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("Nome",nome);
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","Sucesso ao salvar os dados");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao salvar os dados" + e);
                    }
                });
    }

    private void IniciarComponentes(){
        txtName = findViewById(R.id.txtNameCad);
        txtPassword = findViewById(R.id.txtPasswordCad);
        btCadastrar = findViewById(R.id.btCadastro);
    }
}