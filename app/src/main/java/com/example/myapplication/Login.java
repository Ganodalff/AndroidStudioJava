package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button btHome;

    private EditText txtName, txtPassword;
    private Button btEntrar;
    String[] mensagens = {"Preencha todos os campos","Login efetuado com sucesso"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        iniciarComponentes();

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = txtName.getText().toString();
                String senha = txtPassword.getText().toString();

                if(nome.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    autenticarUsuario(view);
                }
            }
        });



        btHome = findViewById(R.id.btHome);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void autenticarUsuario(View view){
        String nome = txtName.getText().toString();
        String senha = txtPassword.getText().toString();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(nome,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            telaPrincipal();
                        }
                    }, 2000);
                }else{
                    String erro;
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        erro = "Erro ao logar";
                    }
                    Toast.makeText(Login.this, "Login ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void telaPrincipal(){
        Intent intent = new Intent(Login.this,Telaprincipal.class);
        startActivity(intent);
        finish();
    }

    private void iniciarComponentes(){
        txtName = findViewById(R.id.txtNameLog);
        txtPassword = findViewById(R.id.txtPasswordLog);
        btEntrar = findViewById(R.id.btEntrar);

    }

}