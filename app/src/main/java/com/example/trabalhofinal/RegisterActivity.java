package com.example.trabalhofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editSenha;
    private Button btnRegistrar;
    private Button btnVoltar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVoltar = findViewById(R.id.btnVoltar);

        auth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
                    Toast.makeText(RegisterActivity.this, "Preencha todos os campos!!", Toast.LENGTH_SHORT).show();
                } else if(senha.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Campo senha deve conter 6 dígitos ou mais!!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, senha);
                }
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, StartActivity.class));
                finish();
            }
        });
    }
    private void registerUser(String email, String senha) {

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registro feito com sucesso!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, IndexActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Falha no registro...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}