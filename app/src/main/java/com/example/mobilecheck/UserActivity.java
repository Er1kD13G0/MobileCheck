package com.example.mobilecheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private TextView userEmailTextView;
    private TextView userSenhaTextView;
    private TextView userCNPJTextView;
    private TextView userUsuarioTextView;
    private Button LogoutButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        auth = FirebaseAuth.getInstance();

        // Referência para os TextViews que vão mostrar os campos preenchidos no login
        userEmailTextView = findViewById(R.id.user_email_textview);
        userSenhaTextView = findViewById(R.id.user_senha_textview);
        userCNPJTextView = findViewById(R.id.user_cnpj_textview);
        userUsuarioTextView = findViewById(R.id.user_user_textview);

        //Referência para o botão de Logout
        LogoutButton = findViewById(R.id.button_logout);

        // Recuperar os dados passados pela MainActivity
        String email = getIntent().getStringExtra("email");
        String usuario = getIntent().getStringExtra("usuario");
        String senha = getIntent().getStringExtra("senha");
        String cnpj = getIntent().getStringExtra("cnpj");

        // Exibir os dados nos TextViews
        if (email != null) {
            userEmailTextView.setText("E-mail: " + email);
        }
        if (usuario != null) {
            userUsuarioTextView.setText("Usuário: " + usuario);
        }
        if (senha != null) {
            userSenhaTextView.setText("Senha: " + senha);
        }
        if (cnpj != null) {
            userCNPJTextView.setText("CNPJ: " + cnpj);
        }

        //Adicionar a funcionalidade do botao de Logout
        LogoutButton.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(UserActivity.this, "Você foi desconectado!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserActivity.this, LoginActivity.class));
            finish();
        });
    }
}
