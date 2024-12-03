import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductEditActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private FirebaseFirestore db;
    private ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit); // Layout principal da activity

        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, this::deleteProduct);
        recyclerView.setAdapter(productAdapter);

        // Adiciona o RecyclerView ao layout da activity
        LinearLayout layout = findViewById(R.id.main_layout); // Certifique-se de que você tenha um LinearLayout no layout principal
        layout.addView(recyclerView);

        db = FirebaseFirestore.getInstance();
        loadProducts();
    }

    private void loadProducts() {
        CollectionReference productsRef = db.collection("produtos");
        productsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    productList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                } else {
                    Log.w("ProductEditActivity", "Error getting documents.", task.getException());
                }
            }
        });
    }

    private void deleteProduct(Product product) {
        db.collection("produtos").document(product.getId()).delete()
                .addOnSuccessListener(aVoid -> {
                    productList.remove(product);
                    productAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Produto excluído com sucesso!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w("ProductEditActivity", "Error deleting document", e);
                    Toast.makeText(this, "Erro ao excluir produto.", Toast.LENGTH_SHORT).show();
                });
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

        private ArrayList<Product> products;
        private OnDeleteClickListener onDeleteClickListener;

        public interface OnDeleteClickListener {
            void onDeleteClick(Product product);
        }

        public ProductAdapter(ArrayList<Product> products, OnDeleteClickListener listener) {
            this.products = products;
            this.onDeleteClickListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT));

            TextView productName = new TextView(parent.getContext());
            productName.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1)); // Peso 1 para ocupar o espaço restante

            Button deleteButton = new Button(parent.getContext());
            deleteButton.setText("Excluir");
            deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            layout.addView(productName);
            layout.addView(deleteButton);

            return new ViewHolder(layout, productName, deleteButton);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Product product = products.get(position);
            holder.productName.setText(product.getNome() + " - Quantidade: " + product.getQuantidade());

            holder.deleteButton.setOnClickListener(v -> {
                onDeleteClickListener.onDeleteClick(product);
            });
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView productName;
            Button deleteButton;

            public ViewHolder(@NonNull View itemView, TextView productName, Button deleteButton) {
                super(itemView);
                this.productName = productName;
                this.deleteButton = deleteButton;
            }
        }
    }
}