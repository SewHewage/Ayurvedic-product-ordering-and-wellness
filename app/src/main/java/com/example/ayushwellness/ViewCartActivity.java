package com.example.ayushwellness;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.ayushwellness.models.Product;


import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<Product> cartProductList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        // Initialize RecyclerView
        recyclerViewCart = findViewById(R.id.recycler_view_cart);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the product list and adapter
        cartProductList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartProductList);
        recyclerViewCart.setAdapter(cartAdapter);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart");

        // Fetch cart items from Firebase
        fetchCartItems();
    }

    private void fetchCartItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartProductList.clear(); // Clear the list before adding new items
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class); // Map data snapshot to Product class
                    if (product != null) {
                        cartProductList.add(product);
                    }
                }
                // Notify the adapter about data changes
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewCartActivity.this, "Failed to load cart data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
