package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProductInfoActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice;
    private Button btnAddToCart, btnBuyNow;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        // Initialize views
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnBuyNow = findViewById(R.id.btn_buy_now);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart");

        // Get product details from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("productName");
        String price = intent.getStringExtra("productPrice");
        int imageResId = intent.getIntExtra("productImage", R.drawable.product1);

        // Set product details
        productName.setText(name);
        productPrice.setText(price);
        productImage.setImageResource(imageResId);

        // Add to Cart Button Listener
        btnAddToCart.setOnClickListener(v -> {
            addToCart(name, price, imageResId);
        });

        // Buy Now Button Listener
        btnBuyNow.setOnClickListener(v -> {
            // Create an intent to navigate to PaymentActivity
            Intent paymentIntent = new Intent(ProductInfoActivity.this, PaymentActivity.class);

            // Pass product details to the PaymentActivity
            paymentIntent.putExtra("productName", name);
            paymentIntent.putExtra("productPrice", price);
            paymentIntent.putExtra("productImage", imageResId);

            // Start the PaymentActivity
            startActivity(paymentIntent);
        });
    }

    private void addToCart(String name, String price, int imageResId) {
        // Create a unique key for each product
        String key = databaseReference.push().getKey();

        // Create a product map to store product details
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("name", name);
        productMap.put("price", price);
        productMap.put("imageResId", imageResId);

        // Save product to Firebase under the unique key
        if (key != null) {
            databaseReference.child(key).setValue(productMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, name + " added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
