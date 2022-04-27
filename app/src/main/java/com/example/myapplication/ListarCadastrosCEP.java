package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ListarCadastrosCEP extends AppCompatActivity {

    private TextView lsCEP,lsEstado,lsCidade,lsRua,lsNumero,lsBairro,lsComplemento;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cadastros_cep);

        lsCEP = findViewById(R.id.lsCEP);
        lsEstado = findViewById(R.id.lsEstado);
        lsCidade = findViewById(R.id.lsCidade);
        lsRua = findViewById(R.id.lsRua);
        lsBairro = findViewById(R.id.lsBairro);
        lsComplemento = findViewById(R.id.lsComplemento);
        lsNumero = findViewById(R.id.lsNumero);
    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Enderecos").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    lsCEP.setText(documentSnapshot.getString("cep"));
                    lsEstado.setText(documentSnapshot.getString("estado"));
                    lsCidade.setText(documentSnapshot.getString("cidade"));
                    lsBairro.setText(documentSnapshot.getString("bairro"));
                    lsRua.setText(documentSnapshot.getString("logradouro"));
                    lsNumero.setText(documentSnapshot.getString("numero"));
                    lsComplemento.setText(documentSnapshot.getString("complemento"));
                }
            }
        });
    }
}