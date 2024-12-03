package com.example.ayushwellness;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayushwellness.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageProductActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private RecyclerView productsRecyclerView;
    private ProductAdapter productsAdapter;
    private List<Product> productList;
    private DatabaseReference productsDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);

        // Initialize Firebase Database reference
        productsDatabaseRef = FirebaseDatabase.getInstance().getReference("products");

        // Initialize RecyclerView and Adapter
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productsAdapter = new ProductAdapter(productList, this);

        productsRecyclerView.setAdapter(productsAdapter);

        // Load existing products from Firebase
        loadProducts();

        // Add product button click listener
        Button addProductButton = findViewById(R.id.addProductButton);
        addProductButton.setOnClickListener(v -> onAddProductClicked());
    }

    // Method to load products from Firebase
    private void loadProducts() {
        productsDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear and reload data
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        product.setId(snapshot.getKey()); // Set the product ID from Firebase key
                        productList.add(product);
                    }
                }
                productsAdapter.notifyDataSetChanged(); // Refresh the list
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ManageProductActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method called when the "Add Product" button is clicked
    public void onAddProductClicked() {
        // Create a sample product (this could be a dialog to enter details)
        Product newProduct = new Product("New Product", "Description", 100);

        // Add product to Firebase
        String productId = productsDatabaseRef.push().getKey();
        if (productId != null) {
            productsDatabaseRef.child(productId).setValue(newProduct)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            newProduct.setId(productId); // Set product ID to match Firebase
                            productList.add(newProduct);
                            productsAdapter.notifyDataSetChanged();  // Refresh the list
                            Toast.makeText(ManageProductActivity.this, "Product added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManageProductActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onProductImageClick(Product product) {

    }

    @Override
    public void onAddToCartClick(Product product) {

    }

    @Override
    public void onBuyNowClick(Product product) {

    }

    // Method to delete product (called from adapter)
    @Override
    public void onDeleteProductClick(Product product) {
        String productId = product.getId();
        if (productId != null) {
            productsDatabaseRef.child(productId).removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            productList.remove(product);
                            productsAdapter.notifyDataSetChanged(); // Refresh the list
                            Toast.makeText(ManageProductActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManageProductActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Implement other necessary methods for the ProductAdapter click listener if needed
}
