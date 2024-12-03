package com.example.mobilecheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button ChatBotButton;
    private Button UsuariosButton;
    private Button ProdutoButton;
    private WebView WebViewChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando o botão e a WebView
        ChatBotButton = findViewById(R.id.button_chatbot);
        UsuariosButton = findViewById(R.id.button_usuarios);
        ProdutoButton = findViewById(R.id.button_produtos);
        WebViewChat = findViewById(R.id.web);     // Habilitando JavaScript no WebView
        WebSettings webSettings = WebViewChat.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Configurando o comportamento da WebView
        WebViewChat.setWebViewClient(new android.webkit.WebViewClient());

        // Configuração do clique no botão ChatBot
        ChatBotButton.setOnClickListener(view -> {
            // Carregar o URL do chatbot na WebView
            WebViewChat.loadUrl("https://cdn.botpress.cloud/webchat/v2.2/shareable.html?configUrl=https://files.bpcontent.cloud/2024/10/03/13/20241003130634-JTFL6OBA.json");
        });

        // Configuração do clique no botão Usuarios
        UsuariosButton.setOnClickListener(view -> {
            // Recuperar os dados passados pela LoginActivity
            String email = getIntent().getStringExtra("email");
            String usuario = getIntent().getStringExtra("usuario");
            String senha = getIntent().getStringExtra("senha");
            String cnpj = getIntent().getStringExtra("cnpj");

            // Passa os dados para a UserActivity
            Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
            userIntent.putExtra("email", email);
            userIntent.putExtra("usuario", usuario);
            userIntent.putExtra("senha", senha);
            userIntent.putExtra("cnpj", cnpj);
            startActivity(userIntent);
        });

        // Configuração do clique no botão Produto
        ProdutoButton.setOnClickListener(v -> {
            Intent produtosIntent = new Intent(MainActivity.this, ProductActivity.class);
            startActivity(produtosIntent);
        });
    }
}