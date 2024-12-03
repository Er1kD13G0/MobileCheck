package com.example.mobilecheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductActivity extends AppCompatActivity {
    private EditText editTextNome, editTextQuantidade;
    private TextView VerEstoque;
    private Button buttonEstoque;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editTextNome = findViewById(R.id.editTextNome);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        buttonEstoque = findViewById(R.id.button_colocaestoque);
        VerEstoque = findViewById(R.id.textViewEstoque);

        databaseReference = FirebaseDatabase.getInstance().getReference("produtos");

        buttonEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString().trim();
                String quantidadeStr = editTextQuantidade.getText().toString(); // Removido o segundo toString()

                if (!nome.isEmpty() && !quantidadeStr.isEmpty()) {
                    int quantidade = Integer.parseInt(quantidadeStr);
                    String produtoId = databaseReference.push().getKey();
                    Product produto = new Product(nome, quantidade); // Certifique-se de que a classe Product tenha um construtor apropriado
                    databaseReference.child(produtoId).setValue(produto)
                            .addOnSuccessListener(aVoid -> Toast.makeText(ProductActivity.this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(ProductActivity.this, "Erro ao adicionar produto: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                } else {
                    Toast.makeText(ProductActivity.this, "Por favor preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // O OnClickListener para VerEstoque deve estar fora do OnClickListener do buttonEstoque
        VerEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductActivity.this, ProductEditActivity.class));
            }
        });
    }
}