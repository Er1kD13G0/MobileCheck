package com.example.mobilecheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginUsuario, loginSenha, loginEmail, loginCnpj;
    private TextView cadastroRedirectText;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        loginUsuario = findViewById(R.id.login_usuario);
        loginSenha = findViewById(R.id.login_senha);
        loginEmail = findViewById(R.id.login_email);
        loginCnpj = findViewById(R.id.login_cnpj);
        cadastroRedirectText = findViewById(R.id.cadastroRedirectText);
        loginButton = findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String senha = loginSenha.getText().toString();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if(!senha.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, senha)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Você foi logado com sucesso!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Erro no login, tente novamente!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        loginSenha.setError("A senha não pode estar vazia!");
                    }
                } else if(email.isEmpty()) {
                    loginEmail.setError("O email não pode estar vazio!");
                } else {
                    loginEmail.setError("Por favor insira um email válido");
                }
            }
        });

                cadastroRedirectText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                    }
                });
    }
}