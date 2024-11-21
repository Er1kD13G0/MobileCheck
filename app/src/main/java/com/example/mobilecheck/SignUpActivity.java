package com.example.mobilecheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText cadastroUsuario, cadastroSenha, cadastroEmail, cadastroCnpj;
    private Button cadastroButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        cadastroUsuario = findViewById(R.id.cadastro_usuario);
        cadastroSenha = findViewById(R.id.cadastro_senha);
        cadastroEmail = findViewById(R.id.cadastro_email);
        cadastroCnpj = findViewById(R.id.cadastro_cnpj);
        cadastroButton = findViewById(R.id.button_cadastro);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = cadastroEmail.getText().toString().trim();
                String senha = cadastroSenha.getText().toString().trim();

                if (usuario.isEmpty()){
                    cadastroEmail.setError("O campo de email não pode estar vazio!");
                }
                if(senha.isEmpty()){
                    cadastroSenha.setError("o campo da senha não pode estar vazio!");

                }else{
                    auth.createUserWithEmailAndPassword(usuario, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Você se cadastrou com sucesso!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            } else{
                                Toast.makeText(SignUpActivity.this, "Erro no cadastro, tente novamente!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        }
    }
